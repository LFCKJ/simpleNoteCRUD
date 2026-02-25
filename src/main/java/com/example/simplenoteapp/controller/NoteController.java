package com.example.simplenoteapp.controller;

import com.example.simplenoteapp.dto.NoteRequest;
import com.example.simplenoteapp.entity.NoteEntity;
import com.example.simplenoteapp.exception.ApiResponse;
import com.example.simplenoteapp.service.NoteService;
import jakarta.validation.Valid;
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
    public ApiResponse<NoteEntity> saveNote(@Valid @RequestBody NoteRequest request){
        //1.request 에서 title과 content를 꺼낸다.
        //서비스의 파라미터가 NoteRequest로 바뀌었으므로 통째로 넘긴다.
        NoteEntity result = noteService.saveNote(request);
        return ApiResponse.success(result);
        //2.noteService.saveNote(title, content)를 호출한다.
        //3.결과 반환
    }
    //======데이터 조회(READ)
    @GetMapping("/{id}") //특정id로 조회
    public ApiResponse<NoteEntity> readNoteById(@PathVariable Long id){
        NoteEntity result = noteService.getNoteById(id);
        return ApiResponse.success(result);
    }
    //전체 조회
    @GetMapping
    public ApiResponse<List<NoteEntity>> findAll(){
        List<NoteEntity> result = noteService.getAllNote();
        return ApiResponse.success(result);
    }

    //===========데이터 수정(Update)
    @PutMapping("/{id}")
    public ApiResponse<NoteEntity> updateNote(
            @PathVariable Long id,
            @Valid @RequestBody NoteRequest request){
        NoteEntity result = noteService.updateNote(id, request);
        return ApiResponse.success(result);
    }
    //==========데이터 삭제(Delete)
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteNote(@PathVariable Long id){
         noteService.deleteNote(id);
        return ApiResponse.success("성공적으로 삭제되었습니다 ID: " +id);
    }
}
