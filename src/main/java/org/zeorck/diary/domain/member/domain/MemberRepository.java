package org.zeorck.diary.domain.member.domain;

public interface MemberRepository {

    void save(Member member);

    boolean existsByEmail(String email);

    Member findByEmail(String email);

}
