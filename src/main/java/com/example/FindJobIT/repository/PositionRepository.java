package com.example.FindJobIT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.FindJobIT.domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long>,
        JpaSpecificationExecutor<Position> {
    // Additional query methods can be defined here if needed

}
