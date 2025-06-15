package com.example.FindJobIT.service;

import org.springframework.stereotype.Service;

import com.example.FindJobIT.domain.Position;
import com.example.FindJobIT.repository.PositionRepository;

@Service
public class PositionService {
    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Position createPosition(Position position) {
        // Logic to create a position
        return this.positionRepository.save(position);
    }

    public void deletePosition(long id) {
        // Logic to delete a position
        if (!this.positionRepository.existsById(id)) {
            throw new IllegalArgumentException("Position with ID " + id + " does not exist.");
        }
        this.positionRepository.deleteById(id);
    }

    public Iterable<Position> getAllPositions() {
        // Logic to get all positions
        return this.positionRepository.findAll();
    }
}
