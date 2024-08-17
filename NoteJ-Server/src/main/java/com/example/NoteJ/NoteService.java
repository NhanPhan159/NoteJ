package com.example.NoteJ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;
    public Note createNote(Note note){
        return noteRepository.save(note);
    }
    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }
    public void deleteNote(Long id){
        noteRepository.deleteById(id);
    }
    public boolean updateNote(Long id, String newContent){
        Optional<Note> note = noteRepository.findById(id);
        if(note.isPresent()){
            note.get().setContent(newContent);
            noteRepository.save(note.get());
            return true;
        }
        return false;
//        Optional<Note> note = noteRepository.findById(id);
//        note.ifPresent(value -> {
//            value.setContent(newContent);
//            noteRepository.save(value);
//        });
    }
    public List<Note> getNoteForThisWeek(LocalDate today){
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);
        List<Note> daysInWeek = new ArrayList<Note>();
        int indexOfDayRangeInDB = 0;
        List<Note> dayRangeInDB =  noteRepository.findBetweenDate(startOfWeek,endOfWeek);
        for (int i = 0; i < 7; i++) {
            Note dayNote = new Note();
            dayNote.setContent("");
            dayNote.setDate(startOfWeek.plusDays(i));
            if( !dayRangeInDB.isEmpty() && indexOfDayRangeInDB < dayRangeInDB.size()  && dayNote.getDate().isEqual(dayRangeInDB.get(indexOfDayRangeInDB).getDate())){
                dayNote = dayRangeInDB.get(indexOfDayRangeInDB);
                indexOfDayRangeInDB++;
            }
            daysInWeek.add(dayNote);
        }
        return daysInWeek;
    }
}
