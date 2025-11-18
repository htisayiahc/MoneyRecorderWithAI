package com.example.MoneyRecorderAIver.Repository;

import com.example.MoneyRecorderAIver.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}

