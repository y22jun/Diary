package org.zeorck.diary.domain.diary.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zeorck.diary.domain.diary.domain.Diary;

public interface DiaryJpaRepository extends JpaRepository<Diary, Long> {

}
