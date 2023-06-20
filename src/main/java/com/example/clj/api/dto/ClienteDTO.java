package com.example.clj.api.dto;

import com.example.clj.model.entity.Cliente;
import com.example.clj.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClienteDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;


    public static ClienteDTO create (Cliente cliente) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cliente, ClienteDTO.class);
    }
}
