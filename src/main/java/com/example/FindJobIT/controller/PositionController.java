package com.example.FindJobIT.controller;

import org.checkerframework.checker.units.qual.A;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FindJobIT.domain.Position;
import com.example.FindJobIT.domain.Question;
import com.example.FindJobIT.service.PositionService;
import com.example.FindJobIT.util.annotation.ApiMessage;
import com.example.FindJobIT.util.error.IdInvalidException;

import io.micrometer.core.ipc.http.HttpSender.Response;

@Controller
@RequestMapping("/api/v1")
public class PositionController {
    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @PostMapping("/positions")
    @ApiMessage("Create a new position")
    public ResponseEntity<Position> createPosition(@RequestBody Position position) {
        // Logic to create a position
        Position createdPosition = positionService.createPosition(position);
        return ResponseEntity.status(201).body(createdPosition);
    }

    @DeleteMapping("/positions/{id}")
    @ApiMessage("Delete a position by ID")
    public ResponseEntity<Void> deletePosition(@PathVariable("id") long id) throws IdInvalidException {
        // Logic to delete a position
        positionService.deletePosition(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/positions")
    @ApiMessage("Get all positions")
    public ResponseEntity<Iterable<Position>> getAllPositions() {
        // Logic to get all positions
        Iterable<Position> positions = positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }

}
