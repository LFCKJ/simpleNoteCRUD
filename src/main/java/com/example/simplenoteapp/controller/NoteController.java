package com.example.simplenoteapp.controller;

import com.example.simplenoteapp.dto.NoteRequest;
import com.example.simplenoteapp.entity.NoteEntity;
import com.example.simplenoteapp.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
