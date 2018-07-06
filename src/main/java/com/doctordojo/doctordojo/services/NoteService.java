package com.doctordojo.doctordojo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.doctordojo.doctordojo.models.Note;
import com.doctordojo.doctordojo.repos.NoteRepository;

@Service
public class NoteService {
	
	private final NoteRepository noteRepo;
	
	public NoteService(NoteRepository noteRepo) {
		this.noteRepo = noteRepo;
	}
	
	public Note createNote(Note note) {
		
		return noteRepo.save(note);
		
	}
	
	public Note fineNote(Long id) {
		Optional<Note> optionalNote = noteRepo.findById(id);
		if(optionalNote.isPresent()) {
			return optionalNote.get();
		} else {
			return null;
		}
	}
	
	public Note updateNote(Note n) {
		return noteRepo.save(n);
	}

}
