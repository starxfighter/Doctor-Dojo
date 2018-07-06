package com.doctordojo.doctordojo.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.doctordojo.doctordojo.models.Note;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long>{

}
