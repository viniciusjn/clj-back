package com.example.clj.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Funcionario extends Pessoa{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer codFunc;

    @ManyToOne
    private Cargo cargo;
}
