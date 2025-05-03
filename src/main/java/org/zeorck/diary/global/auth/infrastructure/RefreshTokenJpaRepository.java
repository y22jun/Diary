package org.zeorck.diary.global.auth.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zeorck.diary.global.auth.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findFirstByMemberIdOrderByIdDesc(Long id);

    void deleteByMemberId(Long memberId);
}
