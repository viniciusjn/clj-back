package com.example.clj.api.dto;

import com.example.clj.model.entity.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CargoDTO {

    private Long id;
    private String nome;
    private Integer salario;

    public static CargoDTO create (Cargo cargo){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cargo, CargoDTO.class);
    }
}
