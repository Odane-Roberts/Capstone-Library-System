package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.model.Member;

import java.util.List;

public interface MemberService {
    List<Member> findAllMembers();
    Member findById(Long id);
    Member addMember(Member member);
    void removeMember(Member member);
    Member updateMember(Member member);

//    void deactivateMember(Member member);
//
//    void activateMember(Member member);
}
