package com.example.MoneyRecorderAIver.Repository;

import com.example.MoneyRecorderAIver.Entity.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
    List<IncomeEntity> findByUserId(Long userId);
}

