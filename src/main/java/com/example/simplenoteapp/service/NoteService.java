package com.example.simplenoteapp.service;

import com.example.simplenoteapp.entity.NoteEntity;
import com.example.simplenoteapp.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor //생성자를 따로 안 짜도 리포지토리를 가져옴
public class NoteService {
    private final NoteRepository noteRepository;
    public NoteEntity saveNote(String title, String content){
       //1. 객체 생성(빌더 사용)
        NoteEntity noteEntity = NoteEntity.builder()
                .title(title)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        //2.리포지토리를 통해 DB에 저장하고, 저장된 결과를 리턴
        return noteRepository.save(noteEntity);
    }
}
