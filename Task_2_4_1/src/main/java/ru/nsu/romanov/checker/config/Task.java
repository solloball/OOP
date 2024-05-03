package ru.nsu.romanov.checker.config;

import static java.time.ZoneId.systemDefault;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Contains info about task.
 */
public class Task {
    public String name = "";
    public Date softDeadline = new Date();
    public Date hardDeadline = new Date();
    public int points = 1;

    /**
     * Setter for name.
     *
     * @param name name to set.
     */
    void name(String name) {
        this.name = name;
    }

    /**
     * Setter for points.
     *
     * @param points points to set.
     */
    void points(int points) {
        this.points = points;
    }

    /**
     * Setter for soft deadline.
     *
     * @param date date to set.
     */
    void soft(String date) {
        this.softDeadline = parseDate(date);
    }

    /**
     * Setter for hard deadline.
     *
     * @param date date to set.
     */
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