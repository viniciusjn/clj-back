package com.example.clj.api.dto;

import com.example.clj.model.entity.Jogo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JogoDTO {

    private Long id;
    private String nomeJogo;
    private String generoJogo;
    private Integer classificacaoind;
    private String idPublisher;
    private String nomePublisher;

    public static JogoDTO create(Jogo jogo) {
        ModelMapper modelMapper = new ModelMapper();
        JogoDTO dto = modelMapper.map(jogo, JogoDTO.class);
        dto.nomePublisher = jogo.getPublisher().getNome();
        return  dto;
    }
}
