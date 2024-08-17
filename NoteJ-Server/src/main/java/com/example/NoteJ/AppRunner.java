package com.example.NoteJ;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    NoteService noteService;

    @Override
    public void run(String... args) throws Exception {

//        Note note = new Note();
//        note.setDate(LocalDate.now());
//        note.setDayOfWeek("Monday");
//        note.setContent("to do 1");
//        noteService.createNote(note);

        List<Note> users = noteService.getAllNotes();
        users.forEach((u) -> System.out.println(u.getContent()));
    }

}