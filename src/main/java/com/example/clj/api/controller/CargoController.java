package com.example.clj.api.controller;

import com.example.clj.api.dto.CargoDTO;
import com.example.clj.exception.RegraNegocioException;
import com.example.clj.exception.RegraNegocioException;
import com.example.clj.model.entity.Cargo;
import com.example.clj.service.CargoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cargos")
@RequiredArgsConstructor
@CrossOrigin
public class CargoController {

    private final CargoService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Cargo> cargos = service.getCargos();
        return ResponseEntity.ok(cargos.stream().map(CargoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cargo> cargo = service.getCargoById(id);
        if (!cargo.isPresent()) {
            return new ResponseEntity("Cargo não encontrado!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cargo.map(CargoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody CargoDTO dto){
        try {
            Cargo cargo = converter(dto);
            cargo = service.salvar(cargo);
            return new ResponseEntity(cargo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping({"/{id}"})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CargoDTO dto){
        if (!service.getCargoById(id).isPresent()) {
            return new ResponseEntity("Cargo não encontrado!", HttpStatus.NOT_FOUND);
        } try {
            Cargo cargo = converter(dto);
            cargo.setId(id);
            service.salvar(cargo);
            return ResponseEntity.ok(cargo);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Cargo> cargo = service.getCargoById(id);
        if (!cargo.isPresent()) {
            return new ResponseEntity("Cargo não encontrado!", HttpStatus.NOT_FOUND);
        } try {
            service.excluir(cargo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Cargo converter(CargoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Cargo.class);
    }
}
