package com.example.NoteJ;

import com.example.NoteJ.CustomException.EmptyContentException;
import com.example.NoteJ.CustomException.IdNotFoundException;
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
    public Note createNote(Note note) throws EmptyContentException {
        if(note.getContent().isEmpty())
            throw new EmptyContentException("Empty content");
        return noteRepository.save(note);
    }
    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }
    public void deleteNote(Long id){
        noteRepository.deleteById(id);
    }
    public void updateNote(Long id, String newContent) throws IdNotFoundException {
        Optional<Note> note = noteRepository.findById(id);
        note.orElseThrow(()->new IdNotFoundException("Id Not Found"));
        note.ifPresent(value -> {
            value.setContent(newContent);
            noteRepository.save(value);
        });
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
