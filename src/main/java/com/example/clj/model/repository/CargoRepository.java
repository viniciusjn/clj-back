package com.example.clj.model.repository;

import com.example.clj.model.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    void delete(Cargo cargo);
}
