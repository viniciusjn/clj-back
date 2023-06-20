package com.example.clj.api.dto;

import com.example.clj.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FuncionarioDTO {

    private Long id;
    private Integer codFunc;
    private String nome;
    private String cpf;
    private String email;
    private String idCargo;
    private String nomeCargo;

    public static FuncionarioDTO create (Funcionario funcionario) {
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioDTO dto = modelMapper.map(funcionario, FuncionarioDTO.class);
        dto.nomeCargo = funcionario.getCargo().getNome();
        return dto;
    }
}


