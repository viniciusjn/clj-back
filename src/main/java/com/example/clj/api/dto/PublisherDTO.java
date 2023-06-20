package com.example.clj.api.dto;

import com.example.clj.model.entity.Cargo;
import com.example.clj.model.entity.Publisher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {

    private Long id;
    private String nome;
    private String estudio;
    private String cnpj;

    public static PublisherDTO create (Publisher publisher){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(publisher, PublisherDTO.class);
    }
}
