package ru.nsu.romanov.note;

import java.util.Date;

/**
 * record for storing notes.
 *
 * @param name name of notes.
 * @param date date of making notes.
 */
public record Note(String name, Date date) {
}
