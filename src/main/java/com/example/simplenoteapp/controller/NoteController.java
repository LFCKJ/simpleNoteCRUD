package com.example.simplenoteapp.controller;

import com.example.simplenoteapp.dto.NoteRequest;
import com.example.simplenoteapp.entity.NoteEntity;
import com.example.simplenoteapp.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes") //공통 주소 설정
public class NoteController {
    private final NoteService noteService;

    //=====데이터 생성(저장)
    @PostMapping
    public NoteEntity saveNote(@RequestBody NoteRequest request){
        //1.request 에서 title과 content를 꺼낸다.
        return noteService.saveNote(request.getTitle(), request.getContent());
        //2.noteService.saveNote(title, content)를 호출한다.
        //3.결과 반환
    }
    //======데이터 조회(READ)
    @GetMapping("/{id}") //특정id로 조회
    public NoteEntity readNoteById(@PathVariable Long id){
        return noteService.getNoteById(id);
    }
    @GetMapping
    public List<NoteEntity> findAll(){
        return noteService.getAllNote();
    }
    //===========데이터 수정(Update)
    @PutMapping("/{id}")
    public NoteEntity updateNote(
            @PathVariable Long id,
            @RequestBody NoteRequest request){
        return noteService.updateNote(id, request);
    }
    //==========데이터 삭제(Delete)
    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable Long id){
        noteService.deleteNote(id);
        return "성공적으로 삭제되었습니다 ID:" +id;
    }
}
