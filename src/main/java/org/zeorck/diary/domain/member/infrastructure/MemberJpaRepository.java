package org.zeorck.diary.domain.member.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zeorck.diary.domain.member.domain.Member;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);
}
