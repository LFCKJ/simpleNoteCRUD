package com.example.simplenoteapp.repository;

import com.example.simplenoteapp.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

}
