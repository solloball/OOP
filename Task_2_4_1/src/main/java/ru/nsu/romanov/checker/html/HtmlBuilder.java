package ru.nsu.romanov.checker.html;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.io.Charsets;
import org.jsoup.Jsoup;
import ru.nsu.romanov.checker.config.Config;
import ru.nsu.romanov.checker.kernel.StudentInfo;

/**
 * Html builder.
 */
public class HtmlBuilder {
    /**
     * Run build.
     *
     * @param map map of all student info.
     * @param config config of checker.
     * @throws IOException can throw IOException.
     */
    public void build(Map<String, StudentInfo> map, Config config) throws IOException {
        StringBuilder res = new StringBuilder();
        res.append(Jsoup.parse(Files.asCharSource(
                new File(Objects
                        .requireNonNull(this
                                .getClass()
                                .getClassLoader()
                                .getResource("template.html"))
                        .getPath()), Charsets.toCharset("UTF8")).read()));

        var sortedTask = config.getTasks();
        var sortedStudent = config.getStudents();

        // Build html
        sortedStudent.sort(Comparator.comparing(o -> o.group));
        sortedTask.sort(Comparator.comparing(o -> o.name));

        sortedStudent.forEach(student -> {
            res.append("<h1>\n")
                .append(student.group)
                .append(" ")
                .append(student.name);

            for (var task : sortedTask) {
                var taskInfo = map.get(student.name).getTaskInfo(task.name);

                // Skip it
                if (taskInfo.getTotal() == 0) {
                    continue;
                }

                res.append("<h2>\n")
                    .append(task.name)
                    .append("</h2>\n");

                res.append("<h2>\n");

                if (taskInfo.getTotal() == taskInfo.getSuc()) {
                    res.append("✅ ");
                } else {
                    res.append("❌ ");
                }

                // Summary
                res.append("Summary: ")
                    .append(taskInfo.getSuc())
                    .append(" tasks finished successfully from ")
                    .append(taskInfo.getTotal())
                    .append("</h2>\n");

                // Build
                if (!taskInfo.getBuild().equals("+")) {
                    res.append("<h2>\n")
                        .append("❌ failed to finish build: \n")
                        .append(taskInfo.getBuild())
                        .append("</h2>\n")
                        .append("</h1>\n");
                } else {
                    res.append("<h2>\n")
                        .append("✅ Build finished successfully!!!")
                        .append("</h2>\n");
                }

                // Javadoc
                if (taskInfo.getJavadoc().equals("+")) {
                    res.append("<h2>\n")
                        .append("✅ Javadoc created successfully\n")
                        .append("<a href=\"")
                        .append(taskInfo.getJavadocLink())
                        .append("\">javadoc link</a>\n")
                        .append("</h2>\n");
                } else if (!taskInfo.getJavadoc().equals("-")) {
                    res.append("<h2>\n")
                        .append("❌ Failed to create javadoc:\n")
                        .append(taskInfo.getJavadoc())
                        .append("</h2>\n");
                }

                // Tests
                if (taskInfo.getTests().equals("+")) {
                    res.append("<h2>\n")
                        .append("✅ Test runs with percents: ")
                        .append(taskInfo.getTestsPercents())
                        .append("\n<a href=\"")
                        .append(taskInfo.getTestsLink())
                        .append("\">tests link</a>\n")
                        .append("</h2>\n");
                } else if (!taskInfo.getTests().equals("-")) {
                    res.append("<h2>\n")
                        .append("❌ Failed finish run tests:\n")
                        .append(taskInfo.getTests())
                        .append("</h2>\n");
                }

                // Jacoco
                if (taskInfo.getTests().equals("+")) {
                    res.append("<h2>\n")
                            .append("✅ Jacoco build coverage: ")
                            .append(taskInfo.getJacocoPercents())
                            .append("\n<a href=\"")
                            .append(taskInfo.getJacocoLink())
                            .append("\">jacoco link</a>\n")
                            .append("</h2>\n");
                } else if (!taskInfo.getJacoco().equals("-")) {
                    res.append("<h2>\n")
                            .append("❌ Failed to build jacoco report:\n")
                            .append(taskInfo.getJacoco())
                            .append("</h2>\n");
                }

                // Linter
                if (taskInfo.getLinter().equals("+")) {
                    res.append("<h2>\n")
                        .append("✅ Linter finished successfully: ")
                        .append("</h2>\n");
                } else if (!taskInfo.getJacoco().equals("-")) {
                    res.append("<h2>\n")
                            .append("❌ Linter failed: ")
                            .append(taskInfo.getLinter())
                            .append("</h2>\n");
                }

                // Date
                if (new Date().before(task.softDeadline)) {
                    res.append("<h2>\n")
                            .append("✅ Soft deadline")
                            .append("</h2>\n");
                } else {
                    res.append("<h2>\n")
                            .append("❌ Soft deadline")
                            .append("</h2>\n");
                }
                if (new Date().before(task.hardDeadline)) {
                    res.append("<h2>\n")
                        .append("✅ Hard deadline")
                        .append("</h2>\n");
                } else {
                    res.append("<h2>\n")
                            .append("❌ Hard deadline")
                            .append("</h2>\n");
                }


                // Points with extra points
                int sum = taskInfo.getTotal() == taskInfo.getSuc() ? task.points : 0;
                res.append("<h2>\n")
                        .append("Summary points for this task: ")
                        .append(sum)
                        .append(" / ")
                        .append(task.points)
                        .append("</h2>\n");

                config.getExtraPoints().stream().filter(extraPoints ->
                        extraPoints.taskName.equals(task.name)
                        && extraPoints.studentName.equals(student.name))
                        .forEach(extraPoints -> {
                            res.append("<h2>\n")
                                    .append("Extra points: ")
                                    .append(extraPoints.points)
                                    .append(" for ")
                                    .append(extraPoints.type)
                                    .append("\nComment: ")
                                    .append(extraPoints.comment)
                                    .append("</h2>\n");
                        });
            }

            res.append("</h1>\n");
        });

        res.append("</body>\n");
        res.append("</html>\n");


        // Write html to file
        try (FileWriter fileWriter = new FileWriter("index.html")) {
            fileWriter.write(res.toString());
        }
    }
}
