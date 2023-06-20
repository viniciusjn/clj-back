package com.example.clj.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer codVenda;
    private Integer valor;

    @ManyToOne
    private Jogo jogo;

    @ManyToOne
    private  Cliente cliente;
}
