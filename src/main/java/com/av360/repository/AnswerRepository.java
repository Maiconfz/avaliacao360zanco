package com.av360.repository;

import com.av360.domain.Answer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Answer entity.
 */
@SuppressWarnings("unused")
public interface AnswerRepository extends JpaRepository<Answer,Long> {

}
