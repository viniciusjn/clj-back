package com.example.clj.api.controller;

import com.example.clj.api.dto.FuncionarioDTO;
import com.example.clj.api.dto.VendaDTO;
import com.example.clj.exception.RegraNegocioException;
import com.example.clj.model.entity.*;
import com.example.clj.service.ClienteService;
import com.example.clj.service.JogoService;
import com.example.clj.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vendas")
@RequiredArgsConstructor
@CrossOrigin
public class VendaController {

    private final VendaService service;
    private final JogoService jogoService;
    private final ClienteService clienteService;

    @GetMapping()
    public ResponseEntity get() {
        List<Venda> vendas = service.getVendas();
        return ResponseEntity.ok(vendas.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Venda> venda = service.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(venda.map(VendaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody VendaDTO dto) {
        try {
            Venda venda = converter(dto);
            venda = service.salvar(venda);
            return new ResponseEntity(venda, HttpStatus.CREATED);
        } catch (NumberFormatException | RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody VendaDTO dto) {
        if (!service.getVendaById(id).isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        } try {
            Venda venda = converter(dto);
            venda.setId(id);
            service.salvar(venda);
            return ResponseEntity.ok(venda);
        } catch (NumberFormatException | RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Venda> venda = service.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        } try {
            service.excluir(venda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Venda converter(VendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Venda venda = modelMapper.map(dto, Venda.class);
        if (dto.getIdJogo() != null) {
            Optional<Jogo> jogo = jogoService.getJogoById(Long.valueOf(dto.getIdJogo()));
            if (!jogo.isPresent()) {
                venda.setJogo(null);
            } else {
                venda.setJogo(jogo.get());
            }
        }
        if (dto.getIdCliente() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(Long.valueOf(dto.getIdCliente()));
            if (!cliente.isPresent()) {
                venda.setCliente(null);
            } else {
                venda.setCliente(cliente.get());
            }
        }
        return venda;
    }
}
