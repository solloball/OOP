package ru.nsu.romanov.checker.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static java.time.ZoneId.systemDefault;

public class Task {
    public String name = "";
    public Date softDeadline = new Date();
    public Date hardDeadline = new Date();
    public int score = 0;

    void name(String name) {
        this.name = name;
    }

    void score(int score) {
        this.score = score;
    }

    void soft(String date) {
        this.softDeadline = parseDate(date);
    }

    void hard(String date) {
        this.hardDeadline = parseDate(date);
    }

    private Date parseDate(String input) {
        Date res;
        SimpleDateFormat isoFormat =
                new SimpleDateFormat("dd/MM/yyyy");
        isoFormat.setTimeZone(TimeZone.getTimeZone(systemDefault()));
        try {
            res = isoFormat.parse(input);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}