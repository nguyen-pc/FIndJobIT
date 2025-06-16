package com.example.FindJobIT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.FindJobIT.domain.Position;
import com.example.FindJobIT.domain.Question;
import com.example.FindJobIT.domain.Skill;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {
    // Additional query methods can be defined here if needed
    List<Question> findAllBySkillAndPosition(Skill skill, Position position);
    
}
