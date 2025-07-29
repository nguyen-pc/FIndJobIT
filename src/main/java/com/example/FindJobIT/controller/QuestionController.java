package com.example.FindJobIT.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.FindJobIT.domain.Position;
import com.example.FindJobIT.domain.Question;
import com.example.FindJobIT.domain.Skill;
import com.example.FindJobIT.domain.request.question.QuestionUpdateDTO;
import com.example.FindJobIT.domain.request.question.ReqQuestion;
import com.example.FindJobIT.domain.response.ResultPaginationDTO;
import com.example.FindJobIT.service.PositionService;
import com.example.FindJobIT.service.QuestionService;
import com.example.FindJobIT.service.SkillService;
import com.example.FindJobIT.util.annotation.ApiMessage;
import com.example.FindJobIT.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/v1")
public class QuestionController {
    private final QuestionService questionService;
    private final SkillService skillService;
    private final PositionService positionService;

    public QuestionController(QuestionService questionService, SkillService skillService,
            PositionService positionService) {
        this.questionService = questionService;
        this.skillService = skillService;
        this.positionService = positionService;
    }

    @PostMapping("/questions")
    @ApiMessage("Create a new question")
    public ResponseEntity<Question> createQuestion(@RequestBody ReqQuestion question) {
        // Logic to create a question
        return ResponseEntity.ok(questionService.createQuestion(question));
    }

    @PutMapping("/questions")
    @ApiMessage("Update a question")
    public ResponseEntity<Question> update(@Valid @RequestBody QuestionUpdateDTO questionDto)
            throws IdInvalidException {
        // Lấy câu hỏi hiện tại
        Question currentQuestion = this.questionService.getQuestionById(questionDto.getIdQuestion());
        if (currentQuestion == null) {
            throw new IdInvalidException("Question id = " + questionDto.getIdQuestion() + " không tồn tại");
        }
        // Lấy skill và position sử dụng id từ DTO
        Skill skill = skillService.fetchSkillById(questionDto.getSkillId());
        Position position = positionService.fetchPositionById(questionDto.getPositionId());

        currentQuestion.setQuestionName(questionDto.getQuestionName());
        currentQuestion.setAnswer(questionDto.getAnswer());
        currentQuestion.setSkill(skill);
        currentQuestion.setPosition(position);

        return ResponseEntity.ok().body(this.questionService.updateQuestion(currentQuestion));
    }

    @DeleteMapping("/questions/{id}")
    @ApiMessage("Delete a question by id")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") long id) {
        // Logic to delete a question
        questionService.deleteQuestion(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/questions/{id}")
    @ApiMessage("Get a question by id")
    public ResponseEntity<Question> getQuestion(@PathVariable("id") long id) {
        // Logic to get a question by id
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @GetMapping("/questions")
    public ResponseEntity<?> getAllQuestions(@RequestParam("positionId") long positionId,
            @RequestParam("skillId") long skillId) {

        return ResponseEntity.ok(questionService.getAllQuestions(positionId, skillId));
    }

    @GetMapping("/questions/all")
    @ApiMessage("fetch all questions with pagination and filtering")
    public ResponseEntity<ResultPaginationDTO> getAll(
            @Filter Specification<Question> spec,
            Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(
                this.questionService.fetchAllQuestion(spec, pageable));

    }

}
