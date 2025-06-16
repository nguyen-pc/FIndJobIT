package com.example.FindJobIT.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.FindJobIT.domain.Question;
import com.example.FindJobIT.domain.request.question.ReqQuestion;
import com.example.FindJobIT.service.QuestionService;
import com.example.FindJobIT.util.annotation.ApiMessage;

@Controller
@RequestMapping("/api/v1")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/questions")
    @ApiMessage("Create a new question")
    public ResponseEntity<Question> createQuestion(@RequestBody ReqQuestion question) {
        // Logic to create a question
        return ResponseEntity.ok(questionService.createQuestion(question));
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
    public ResponseEntity<?> getAllQuestions(@RequestParam("positionId") long positionId
            , @RequestParam("skillId") long skillId) {
        
            
        return ResponseEntity.ok(questionService.getAllQuestions(positionId, skillId));
    }

    



}
