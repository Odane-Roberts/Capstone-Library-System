package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.exception.MemberNotFoundException;
import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.model.memberStatus;
import dev.odane.capstoneproject.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository repository;

    public MemberServiceImpl(MemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Member> findAllMembers() {
        return repository.findAll();
    }

    @Override
    public Member findById(Long id) {
        return repository.findById(id).orElseThrow(() ->new MemberNotFoundException("Member not found "+id));
    }

    @Override
    public Member addMember(Member member) {
        return repository.save(member);
    }

    @Override
    public void removeMember(Member member) {
        repository.delete(member);
    }

    @Override
    public Member updateMember(Member member) {
        return repository.save(member);
    }

    @Override
    public void deactivateMember(Member member) {
        member.setStatus(memberStatus.INACTIVE);
    }
    @Override
    public void activateMember(Member member) {
        member.setStatus(memberStatus.ACTIVE);
    }
}
