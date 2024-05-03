package ru.nsu.romanov.checker.kernel;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.jsoup.Jsoup;
import ru.nsu.romanov.checker.config.Config;
import ru.nsu.romanov.checker.config.Student;
import ru.nsu.romanov.checker.config.Task;

/**
 * Main logic for checker, run checks and writes them in task info.
 */
public class Checker {
    /**
     * Run clone repo.
     *
     * @param config config of checker.
     */
    public void gitClone(Config config) {
        System.out.println("Start cloning...");
        config.getStudents().forEach(student -> {
            System.out.println(
                    "Student: "
                    + student.name
                    + " "
                    + student.repo
                    + " "
                    + student.nickname);
            if (student.name.isEmpty()
                    || student.group.isEmpty()
                    || student.repo.isEmpty()
                    || student.nickname.isEmpty()) {
                throw new IllegalStateException("Empty student's field in config");
            }

            String cloneDirectoryPath = pathRepo(student);
            System.out.println("Cloning "
                    + student.repo
                    + " into "
                    + cloneDirectoryPath);

            try {
                Runtime.getRuntime().exec("git clone "
                        + student.repo
                        + " "
                        + cloneDirectoryPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("Successfully finished!");
    }

    /**
     * Run build task.
     *
     * @param config checker config.
     * @return map of all student info.
     */
    public Map<String, StudentInfo> build(Config config) {
        Map<String, StudentInfo> res = prepareDict(config);
        System.out.println("Start building...");
        for (var check : config.getChecks()) {
            Task task = config.getTasks()
                    .stream()
                    .filter(task1 -> task1.name.equals(check.taskName))
                    .findFirst()
                    .orElse(null);
            if (task == null) {
                System.out.println("There is no candidate task with name: " + check.taskName);
                continue;
            }
            Student student = config.getStudents()
                            .stream()
                            .filter(student1 -> student1.name.equals(check.studentName))
                            .findFirst()
                            .orElse(null);
            if (student == null) {
                System.out.println("There is no candidate student with name: " + check.studentName);
                continue;
            }

            var taskInfo = res.get(student.name).getTaskInfo(task.name);

            System.out.println(
                "\n"
                + student.name
                + "\n");
            taskInfo.setBuild(
                        gradleRun(task, student, "build",
                                List.of("-x", "test")));
            if (taskInfo.getBuild().equals("+")) {
                taskInfo.success();
            } else {
                taskInfo.fail();
            }
        }

        return res;
    }

    /**
     * Run full testing: build, javadoc, linter, test, jacoco.
     *
     * @param config checker config.
     * @return map of all student info.
     */
    public Map<String, StudentInfo> full(Config config) {
        Map<String, StudentInfo> res = prepareDict(config);
        for (var check : config.getChecks()) {
            // Check for existing task and student
            Task task = config.getTasks()
                    .stream()
                    .filter(task1 -> task1.name.equals(check.taskName))
                    .findFirst()
                    .orElse(null);
            if (task == null) {
                System.out.println("There is no candidate task with name: " + check.taskName);
                continue;
            }
            Student student = config.getStudents()
                    .stream()
                    .filter(student1 -> student1.name.equals(check.studentName))
                    .findFirst()
                    .orElse(null);
            if (student == null) {
                System.out.println("There is no candidate student with name: " + check.studentName);
                continue;
            }

            System.out.println(
                    "Start checking "
                    + student.name
                    + " labs\n"
            );


            // Build
            var taskInfo = res.get(student.name).getTaskInfo(task.name);
            taskInfo.setBuild(gradleRun(
                    task,
                    student,
                    "build",
                    List.of("-x", "test")));

            if (!taskInfo.getBuild().equals("+")) {
                taskInfo.fail();
                break;
            }
            taskInfo.success();

            // Test
            taskInfo.setTests(gradleRun(
                    task,
                    student,
                    "test",
                    Collections.emptyList()));
            if (taskInfo.getTests().equals("+")) {
                taskInfo.success();
                var link = testHtml(student, task);
                taskInfo.setTestsLink(link);
                try {
                    taskInfo.setTestsPercents(
                            Objects.requireNonNull(
                                    Objects.requireNonNull(
                                            Jsoup
                                            .parse(new File(link))
                                            .getElementById("successRate"))
                                            .select("div.percent")
                                            .first())
                                            .text());
                    var per = taskInfo.getTestsPercents();
                    if (Integer.parseInt(per, 0, per.lastIndexOf("%"), 10) != 100) {
                        taskInfo.setTests("Not every tests finished successfully");
                    }
                } catch (Exception e) {
                    taskInfo.setTests("failed to parse "
                        + link
                        + " file:\n"
                        + e.getMessage());
                }
            } else {
                taskInfo.fail();
            }

            // Javadoc
            taskInfo.setJavadoc(gradleRun(
                    task,
                    student,
                    "javadoc",
                    Collections.emptyList()));
            if (taskInfo.getJavadoc().equals("+")) {
                taskInfo.success();
                taskInfo.setJavadocLink(javadocPath(student, task));
            } else {
                taskInfo.fail();
            }

            // Jacoco
            taskInfo.setJacoco(gradleRun(
                    task,
                    student,
                    "jacocoTestReport",
                    Collections.emptyList()));
            if (taskInfo.getJacoco().equals("+")) {
                taskInfo.success();
                String link = jacocoReportsPath(student, task);
                taskInfo.setJacocoLink(link);

                try {
                    taskInfo.setJacocoPercents(
                                Objects.requireNonNull(
                                        Objects.requireNonNull(
                                                Jsoup.parse(new File(link))
                                                        .getElementById("c0"))

                                        .text()));
                    var per = taskInfo.getJacocoPercents();
                    if (Integer.parseInt(per, 0, per.lastIndexOf(" "), 10)
                            < config.getCoverage()) {
                        taskInfo.setJacoco("Coverage is less than "
                            + config.getCoverage());
                    }
                } catch (IOException e) {
                    taskInfo.setJacoco("failed to parse "
                            + link
                            + " file:\n"
                            + e.getMessage());
                }
            } else {
                taskInfo.fail();
            }

            // Linter
            taskInfo.setLinter(linter(student, task));
            if (taskInfo.getLinter().equals("+")) {
                taskInfo.success();
            } else {
                taskInfo.fail();
            }

            System.out.println("Stop checking "
                + task.name
                + " of "
                + student.name);
        }
        return res;
    }

    /**
     * Prepare map for using.
     *
     * @param config checker config.
     * @return prepared config.
     */
    private Map<String, StudentInfo> prepareDict(Config config) {
        Map<String, StudentInfo> res = new HashMap<>();
        config.getStudents().forEach(student -> {
            res.put(student.name, new StudentInfo());
            config.getTasks().forEach(task ->
                res.get(student.name)
                    .addTask(new TaskInfo(task.name))
            );
        });
        return res;
    }

    /**
     * Run task linter.
     *
     * @param student student.
     * @param task task.
     * @return log of linter, otherwise +.
     */
    private String linter(Student student, Task task) {
        System.out.println(
                "START\n"
                        + "linter"
                        + " tasks of student: "
                        + student.name
                        + ", nickname: "
                        + student.nickname
                        + "\nTask: "
                        + task.name);
        var linterConfig = pathRepo(student) + "/.github/google_checks.xml";
        var codePath = pathRepo(student)
                + "/"
                + task.name
                + "/src/main/java";

        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();
        AuditListener listener = new DefaultLogger(byteArrayOutputStream,
                AbstractAutomaticBean.OutputStreamOptions.NONE);
        com.puppycrawl.tools.checkstyle.Checker checker =
                new com.puppycrawl.tools.checkstyle.Checker();
        checker.addListener(listener);
        checker.setLocaleLanguage("EN");

        try (Stream<Path> stream = Files.walk(Path.of(codePath))) {
            Configuration configuration =
                    ConfigurationLoader.loadConfiguration(linterConfig,
                            new PropertiesExpander(new Properties()));
            checker.setModuleClassLoader(this.getClass().getClassLoader());
            checker.configure(configuration);
            checker.process(stream.filter(file -> !Files.isDirectory(file)
                    && getFileExtension(file.toFile()).equals(".java"))
                    .map(Path::toFile)
                    .collect(Collectors.toList()));
            checker.destroy();
        } catch (CheckstyleException | IOException e) {
            return e.getMessage();
        }

        String res =  byteArrayOutputStream.toString();
        if (res.equals("Starting audit...\nAudit done.\n")) {
            return "+";
        }
        return byteArrayOutputStream.toString();

    }

    /**
     * Run gradle task.
     *
     * @param task task.
     * @param student student.
     * @param typeOperation type operation, like build or test.
     * @param args list argument for running.
     * @return log of running, otherwise +.
     */
    private String gradleRun(
            Task task,
            Student student,
            String typeOperation,
            List<String> args) {

        System.out.println(
                "START\n"
                + typeOperation
                + " task of student: "
                + student.name
                + ", nickname: "
                + student.nickname
                + "\nTask: "
                + task.name);
        var taskPath =  pathRepo(student) + "/" +  task.name;
        System.out.println(
                "Task path: "
                + taskPath);

        try (ProjectConnection connection = GradleConnector.newConnector()
                .forProjectDirectory(new File(taskPath))
                .connect()) {

            connection.newBuild()
                    .forTasks(typeOperation)
                    .setStandardOutput(System.out)
                    .addArguments(args)
                    .run();
        } catch (RuntimeException e) {
            System.out.println(
                typeOperation
                + " task: "
                + task.name
                + " of student: "
                + student.nickname
                + " failed\n"
                + e.getMessage()
                + e.getCause());
            return task.name
                    + "failed\n"
                    + e.getMessage()
                    + e.getCause().getMessage();
        }
        System.out.println("END");
        return "+";
    }

    /**
     * Get path to repo.
     *
     * @param student student.
     * @return path to repo.
     */
    private String pathRepo(Student student) {
        return "repos/"
                + student.group
                + '/' + student.nickname;
    }

    /**
     * Get jacoco reports.
     *
     * @param student student.
     * @param task task.
     * @return path to jacoco html.
     */
    private String jacocoReportsPath(Student student, Task task) {
        return pathRepo(student)
                + "/"
                + task.name
                + "/build/reports/jacoco/test/html/index.html";
    }

    /**
     * Get tests html.
     *
     * @param student student.
     * @param task task.
     * @return path to html.
     */
    private String testHtml(Student student, Task task) {
        return pathRepo(student)
                + "/"
                + task.name
                + "/build/reports/tests/test/index.html";
    }

    /**
     * Get javadoc path.
     *
     * @param student student.
     * @param task task.
     * @return path to javadoc.
     */
    private String javadocPath(Student student, Task task) {
        return pathRepo(student)
                + "/"
                + task.name
                + "/build/docs/javadoc/allpackages-index.html";
    }

    /**
     * Get extensions of file.
     *
     * @param file file to check.
     * @return -1 if there is no dot in filename.
     */
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

}
