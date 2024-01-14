package com.example.M2T8Lesson6.repositories;

import com.example.M2T8Lesson6.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
