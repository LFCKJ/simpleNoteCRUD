package com.example.simplenoteapp.service;

import com.example.simplenoteapp.dto.NoteRequest;
import com.example.simplenoteapp.entity.NoteEntity;
import com.example.simplenoteapp.mapper.NoteMapper;
import com.example.simplenoteapp.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor //생성자를 따로 안 짜도 리포지토리를 가져옴
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public NoteEntity saveNote(NoteRequest request){
       //1. 객체 생성(빌더 사용) NoteRequest를 인자로 받아 코드를 직관적으로 바꿈
        NoteEntity noteEntity = noteMapper.toEntity(request);
        //2.리포지토리를 통해 DB에 저장하고, 저장된 결과를 리턴
        return noteRepository.save(noteEntity);
    }
    //======READ
    //전체 목록 조회
    public List<NoteEntity> getAllNote(){
        return noteRepository.findAll();
    }
    //특정 아이디 조회
    public NoteEntity getNoteById(Long id) {
        return noteRepository.findById(id) // Optional<NoteEntity> 반환
                .orElseThrow(() -> new IllegalArgumentException("메모가 없습니다. ID: " + id));
    }
    //======Update
    @Transactional
    public NoteEntity updateNote(Long id, NoteRequest request){
        //1.수정할 기존 메모를 찾는다(없으면 예외처리)
        NoteEntity noteEntity = noteRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 메모가 없습니다 ID:"+id));
        //2.객체의 내용을 변경한다.
        //Entity에 데이터를 바꾸는 메서드를 따로 만들거나, 빌더 대신 Setter /  업데이트 메서드를 활용한다.
        noteEntity.update(request.getTitle(), request.getContent());
        return noteEntity;
    }
    //======DELETE
    public void deleteNote(Long id){
        //1.삭제할 메모 확인
        NoteEntity noteEntity = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메모가 없습니다 ID: " +id));
        //2.존재하면 삭제
        noteRepository.delete(noteEntity);
    }
}
