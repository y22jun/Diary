package org.zeorck.diary.domain.diary.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zeorck.diary.domain.diary.domain.Diary;

public interface DiaryJpaRepository extends JpaRepository<Diary, Long> {
    Page<Diary> findByMemberId(Long memberId, Pageable pageable);
}
