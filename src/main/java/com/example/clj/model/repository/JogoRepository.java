package com.example.clj.model.repository;

import com.example.clj.model.entity.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogoRepository extends JpaRepository<Jogo, Long> {
}
