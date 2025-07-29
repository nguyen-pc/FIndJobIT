package com.example.FindJobIT.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.FindJobIT.domain.Position;
import com.example.FindJobIT.domain.Question;
import com.example.FindJobIT.domain.Skill;
import com.example.FindJobIT.domain.request.question.ReqQuestion;
import com.example.FindJobIT.domain.response.ResultPaginationDTO;
import com.example.FindJobIT.repository.PositionRepository;
import com.example.FindJobIT.repository.QuestionRepository;
import com.example.FindJobIT.repository.SkillRepository;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final SkillRepository skillRepository;
    private final PositionRepository positionRepository;

    public QuestionService(QuestionRepository questionRepository, SkillRepository skillRepository,
            PositionRepository positionRepository) {
        this.questionRepository = questionRepository;
        this.skillRepository = skillRepository;
        this.positionRepository = positionRepository;
    }

    public Question createQuestion(ReqQuestion question) {
        Optional<Skill> skillOptional = skillRepository.findById(question.getSkillId());
        Optional<Position> positionOptional = positionRepository.findById(question.getPositionId());
        // Logic to create a question
        if (!skillOptional.isPresent()) {
            throw new IllegalArgumentException("Skill not found");
        }
        if (!positionOptional.isPresent()) {
            throw new IllegalArgumentException("Position not found");
        }
        Question newQuestion = new Question();
        newQuestion.setSkill(skillOptional.get());
        newQuestion.setPosition(positionOptional.get());
        newQuestion.setQuestionName(question.getQuestionName());
        newQuestion.setAnswer(question.getAnswer());

        return questionRepository.save(newQuestion);
    }

    public void deleteQuestion(long id) {
        // Logic to delete a question
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (!questionOptional.isPresent()) {
            throw new IllegalArgumentException("Question with ID " + id + " does not exist.");
        }
        questionRepository.delete(questionOptional.get());
    }

    public Question getQuestionById(long id) {
        // Logic to get a question by id
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (!questionOptional.isPresent()) {
            throw new IllegalArgumentException("Question with ID " + id + " does not exist.");
        }
        return questionOptional.get();
    }
    public Question updateQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Iterable<Question> getAllQuestions(long positionId, long skillId) {
        // Logic to get all questions
        Optional<Skill> skillOptional = skillRepository.findById(skillId);
        Optional<Position> positionOptional = positionRepository.findById(positionId);
        if (!skillOptional.isPresent()) {
            throw new IllegalArgumentException("Skill with ID " + skillId + " does not exist.");
        }
        if (!positionOptional.isPresent()) {
            throw new IllegalArgumentException("Position with ID " + positionId + " does not exist.");
        }

        return questionRepository.findAllBySkillAndPosition(skillOptional.get(), positionOptional.get());
    }

    public ResultPaginationDTO fetchAllQuestion(Specification<Question> spec, Pageable pageable) {
        Page<Question> pageUser = this.questionRepository.findAll(spec, pageable);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        rs.setMeta(mt);

        rs.setResult(pageUser.getContent());

        return rs;
    }
}
