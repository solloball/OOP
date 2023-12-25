package ru.nsu.romanov.note;

import static ru.nsu.romanov.note.TypeOperation.ADD;
import static ru.nsu.romanov.note.TypeOperation.RM;
import static ru.nsu.romanov.note.TypeOperation.SHOW;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Notebook class.
 * There are three operations: add, rm and show.
 * rm, add can should have one or more argc.
 * show should have 0 or more than 2 argc.
 */
public class NoteBook {
    @Option(name = "-add", usage = "add new note")
    private boolean add;
    @Option(name = "-rm", usage = "rm note")
    private boolean rm;
    @Option(name = "-show", usage = "show notes")
    private boolean show;
    @Argument
    private List<String> arguments = new ArrayList<>();
    private TypeOperation typeOperation;

    /**
     * Main loop, which opens in shell.
     */
    public static void main(String[] args) throws CmdLineException, IOException {
        NoteBook noteBook = new NoteBook();
        noteBook.init(args);
        String output = noteBook.doMain();
        System.out.println(output);
    }

    /**
     * init and parse input data.
     *
     * @param args input data.
     * @throws CmdLineException if invalid structure of input data.
     */
    public void init(String[] args) throws CmdLineException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
            int count = 0;
            count = add ? count + 1 : count;
            count = rm ? count + 1 : count;
            count = show ? count + 1 : count;
            if (count != 1) {
                throw new CmdLineException(parser,
                        "Illegal counts of arguments\nShould be only one", null);
            }
            if (rm && arguments.isEmpty()) {
                throw new CmdLineException(parser,
                        "Illegal counts of rm arguments", null);
            }
            if (add && arguments.isEmpty()) {
                throw new CmdLineException(parser,
                        "Illegal counts of add arguments", null);
            }
            int argc = arguments.size();
            if (show && (argc > 0 && argc < 3)) {
                throw new CmdLineException(parser,
                        "Illegal counts of show arguments", null);
            }
            if (rm) {
                typeOperation = RM;
            } else if (show) {
                typeOperation = SHOW;
            } else {
                typeOperation = ADD;
            }
        } catch (CmdLineException e) {
            throw new CmdLineException(parser, e.getMessage(), e.getCause());
        }
    }

    /**
     * do operations.
     *
     * @return "" if add or rm, otherwise "Notes:" with all notes if -show.
     * @throws IOException if can't parse date, should be "dd.MM.yyyy HH:mm".
     */
    public String doMain() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder output = new StringBuilder();
        try {
            final String fileName = "Notebook.json";
            List<Note> json = mapper.readValue(new File(fileName), new TypeReference<>() {});
            switch (typeOperation) {
                case SHOW -> {
                    List<Note> toShow;
                    if (arguments.isEmpty()) {
                        toShow = json;
                    } else {
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH);
                        Date before = formatter.parse(arguments.get(0));
                        Date after = formatter.parse(arguments.get(1));
                        List<String> subStrings = new ArrayList<>();
                        for (int i = 2; i < arguments.size(); i++) {
                            subStrings.add(arguments.get(i));
                        }
                        toShow = json.stream()
                                .filter(t -> t.date().after(before)
                                && t.date().before(after)
                                && subStrings.stream().anyMatch(it -> t.name().contains(it)))
                                .toList();
                    }
                    output = new StringBuilder("Notes:\n");
                    for (var it : toShow) {
                        output.append(it.name()).append("\n");
                    }
                }
                case ADD -> {
                    for (var it : arguments) {
                        Date date = new Date();
                        Note note = new Note(it, date);
                        json.add(note);
                    }
                    mapper.writeValue(new File(fileName), json);
                }
                case RM -> {
                    arguments.forEach(it -> json
                            .removeIf(t -> Objects.equals(t.name(), it)));
                    mapper.writeValue(new File(fileName), json);
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }
}