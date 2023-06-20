package com.example.clj.api.dto;

import com.example.clj.model.entity.Cargo;
import com.example.clj.model.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaDTO {

    private Long id;
    private Integer codVenda;
    private Integer valor;
    private String idJogo;
    private String idCliente;
    private String nomeJogo;
    private String nomeCliente;

    public static VendaDTO create (Venda venda){
        ModelMapper modelMapper = new ModelMapper();
        VendaDTO dto = modelMapper.map(venda, VendaDTO.class);
        dto.nomeJogo = venda.getJogo().getNomeJogo();
        dto.nomeCliente = venda.getCliente().getNome();
        return dto;
    }
}
