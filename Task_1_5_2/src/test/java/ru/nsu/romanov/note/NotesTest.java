package ru.nsu.romanov.note;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.CmdLineException;


/**
 * All test for NoteBook.
 */
class NotesTest {

    @Test
    void emptyString_expectedCmdLineException() {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {};
        Assertions.assertThrows(CmdLineException.class, () -> noteBook.init(input));
    }

    @Test
    void emptyArgs_expectedCmdLineException() {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {""};
        Assertions.assertThrows(CmdLineException.class, () -> noteBook.init(input));
    }

    @Test
    void unknownOperation_expectedCmdLineException() {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-shown"};
        Assertions.assertThrows(CmdLineException.class, () -> noteBook.init(input));
    }

    @Test
    void severalOperation_expectedCmdLineException() {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-show", "-add"};
        Assertions.assertThrows(CmdLineException.class, () -> noteBook.init(input));
    }

    @Test
    void severalOperationDiffOrder_expectedCmdLineException() {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-add", "-show"};
        Assertions.assertThrows(CmdLineException.class, () -> noteBook.init(input));
    }

    @Test
    void allOperationsInput_expectedCmdLineException() {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-add", "newNote", "-show", "-rm", "newNote"};
        Assertions.assertThrows(CmdLineException.class, () -> noteBook.init(input));
    }

    @Test
    void addEmptyArguments_expectedCmdLineException() {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-add"};
        Assertions.assertThrows(CmdLineException.class, () -> noteBook.init(input));
    }

    @Test
    void addSimpleTest() throws CmdLineException, IOException {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-add", "newNote"};
        noteBook.init(input);
        noteBook.doMain();
        ObjectMapper mapper = new ObjectMapper();
        String fileName = "Notebook.json";
        List<Note> json = mapper.readValue(new File(fileName), new TypeReference<>() {});
        Assertions.assertEquals(1, json.size());
        Assertions.assertEquals("newNote", json.get(0).name());
        noteBook = new NoteBook();
        input = new String[] {"-rm", "newNote"};
        noteBook.init(input);
        noteBook.doMain();
    }

    @Test
    void addSeveralTime() throws CmdLineException, IOException {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-add", "newNote", "anotherNote"};
        noteBook.init(input);
        noteBook.doMain();
        ObjectMapper mapper = new ObjectMapper();
        String fileName = "Notebook.json";
        List<Note> json = mapper.readValue(new File(fileName), new TypeReference<>() {});
        Assertions.assertEquals(2, json.size());
        Assertions.assertEquals("newNote", json.get(0).name());
        Assertions.assertEquals("anotherNote", json.get(1).name());
        noteBook = new NoteBook();
        input = new String[] {"-rm", "newNote", "anotherNote"};
        noteBook.init(input);
        noteBook.doMain();
    }

    @Test
    void rmEmptyArguments_expectedCmdLineException() {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-rm"};
        Assertions.assertThrows(CmdLineException.class, () -> noteBook.init(input));
    }

    @Test
    void rmBasicTest() throws CmdLineException, IOException {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-add", "toRm"};
        noteBook.init(input);
        noteBook.doMain();
        noteBook = new NoteBook();
        input = new String[] {"-rm", "toRm"};
        noteBook.init(input);
        noteBook.doMain();
        ObjectMapper mapper = new ObjectMapper();
        String fileName = "Notebook.json";
        List<Note> json = mapper.readValue(new File(fileName), new TypeReference<>() {});
        Assertions.assertEquals(0, json.size());
    }

    @Test
    void rmSeveralTime() throws CmdLineException, IOException {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-add", "toRm", "toRmAlso"};
        noteBook.init(input);
        noteBook.doMain();
        noteBook = new NoteBook();
        input = new String[] {"-rm", "toRm", "toRmAlso"};
        noteBook.init(input);
        noteBook.doMain();
        ObjectMapper mapper = new ObjectMapper();
        String fileName = "Notebook.json";
        List<Note> json = mapper.readValue(new File(fileName), new TypeReference<>() {});
        Assertions.assertEquals(0, json.size());
    }

    @Test
    void showEmptyArguments_expectedCmdLineException() {
        NoteBook noteBook = new NoteBook();
        Assertions.assertThrows(CmdLineException.class,
                () -> noteBook.init(new String[] {"-show", "one"}));
        NoteBook noteBook2 = new NoteBook();
        Assertions.assertThrows(CmdLineException.class,
                () -> noteBook2.init(new String[] {"-show", "one", "two"}));
    }

    @Test
    void showInvalidDate_expectedRuntimeException() throws CmdLineException {
        NoteBook noteBook = new NoteBook();
        noteBook.init(new String[] {"-show", "one", "two", "three"});
        Assertions.assertThrows(RuntimeException.class, noteBook::doMain);
    }

    @Test
    void showInvalidDate2_expectedRuntimeException() throws CmdLineException {
        NoteBook noteBook = new NoteBook();
        noteBook.init(new String[] {"-show", "11/12/2003 14:05", "22/33/6034 7:30", "three"});
        Assertions.assertThrows(RuntimeException.class, noteBook::doMain);
    }

    @Test
    void showBasic() throws CmdLineException, IOException {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-add", "toShow"};
        noteBook.init(input);
        noteBook.doMain();
        noteBook = new NoteBook();
        input = new String[] {"-show"};
        noteBook.init(input);
        Assertions.assertEquals("Notes:\ntoShow\n", noteBook.doMain());
        noteBook = new NoteBook();
        input = new String[] {"-rm", "toShow"};
        noteBook.init(input);
        noteBook.doMain();
    }

    @Test
    void showBasicWithAllArguments() throws CmdLineException, IOException {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-add", "toShow new note"};
        noteBook.init(input);
        noteBook.doMain();
        noteBook = new NoteBook();
        input = new String[] {"-show", "13.12.2021 14:05", "31.12.2023 14:05", "new"};
        noteBook.init(input);
        Assertions.assertEquals("Notes:\ntoShow new note\n", noteBook.doMain());
        noteBook = new NoteBook();
        input = new String[] {"-rm", "toShow new note"};
        noteBook.init(input);
        noteBook.doMain();
    }

    @Test
    void showBasicWithAllArgumentsSeveralNotes() throws CmdLineException, IOException {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-add", "toShow new note", "note os cool!!"};
        noteBook.init(input);
        noteBook.doMain();
        noteBook = new NoteBook();
        input = new String[] {"-show", "13.12.2021 14:05", "31.12.2023 14:05", "note"};
        noteBook.init(input);
        Assertions.assertEquals(
                "Notes:\ntoShow new note\nnote os cool!!\n" ,noteBook.doMain());
        noteBook = new NoteBook();
        input = new String[] {"-rm", "toShow new note", "note os cool!!"};
        noteBook.init(input);
        noteBook.doMain();
    }

    @Test
    void showBasicWithAllArgumentsDate() throws CmdLineException, IOException {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-add", "toShow new note"};
        noteBook.init(input);
        noteBook.doMain();
        noteBook = new NoteBook();
        input = new String[] {"-show", "13.12.2025 14:05", "31.12.2025 14:05", "note"};
        noteBook.init(input);
        Assertions.assertEquals("Notes:\n" ,noteBook.doMain());
        noteBook = new NoteBook();
        input = new String[] {"-rm", "toShow new note"};
        noteBook.init(input);
        noteBook.doMain();
    }

    @Test
    void showBasicWithAllArgumentsDate2() throws CmdLineException, IOException {
        NoteBook noteBook = new NoteBook();
        String[] input = new String[] {"-add", "toShow new note"};
        noteBook.init(input);
        noteBook.doMain();
        noteBook = new NoteBook();
        input = new String[] {"-show", "13.12.2007 14:05", "31.12.2007 14:05", "note"};
        noteBook.init(input);
        Assertions.assertEquals("Notes:\n" ,noteBook.doMain());
        noteBook = new NoteBook();
        input = new String[] {"-rm", "toShow new note"};
        noteBook.init(input);
        noteBook.doMain();
    }
}