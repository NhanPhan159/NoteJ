package com.example.NoteJ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    private ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }
    @PostMapping
    private ResponseEntity<Void> creteNote(@RequestBody Note newCashCardRequest, UriComponentsBuilder ucb) {
        Note savedCashCard = noteService.createNote(newCashCardRequest);
        URI locationOfNewCashCard = ucb
                .path("note/{id}")
                .buildAndExpand(savedCashCard.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteNoteById(@PathVariable("id") Long id){
        noteService.deleteNote(id);
        return new ResponseEntity<String>(String.format("The note with id - %d is deleted", id), HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    private ResponseEntity<?> updateNote(@PathVariable("id") Long id, @RequestBody Note newContent){
        if(noteService.updateNote(id,newContent.getContent())){
            return ResponseEntity.ok("the note is updated");
        }
        return ResponseEntity.badRequest().body("Id is not found");
    }
    @GetMapping("/range")
    private ResponseEntity<List<Note>> getNoteForThisWeek(@RequestParam LocalDate today){
        return ResponseEntity.ok(noteService.getNoteForThisWeek(today));
    }
}
