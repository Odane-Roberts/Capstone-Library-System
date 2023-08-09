package dev.odane.capstoneproject.mapper;

import dev.odane.capstoneproject.DTOs.MemberDTO;
import dev.odane.capstoneproject.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberDTO memberToMemberDTO(Member member);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "borrowedBooks", ignore = true)
    Member memberDTOToMember(MemberDTO member);

}
