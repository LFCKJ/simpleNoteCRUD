package com.example.simplenoteapp.mapper;

import com.example.simplenoteapp.dto.NoteRequest;
import com.example.simplenoteapp.entity.NoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") //스프링 빈으로 등록하여 서비스에서 주입받을 수 있게 함
public interface NoteMapper {
    //매퍼 인스턴스 생성(필요한 경우 사용)
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    //1.DTO -> Entity 변환
    //만약 필드 이름이 서로 다르다면 @Mapping어노테이션을 사용
    //지금 처럼 이름이 같다면 어노테이션 없이도 자동으로 매핑된다.
    NoteEntity toEntity(NoteRequest noteRequest);

    //2.Entity -> DTO변환(나중에 조회 결과를 반환할 때 사용
    //NoteRequest toDTo(NoteEntity noteEntity);
}
