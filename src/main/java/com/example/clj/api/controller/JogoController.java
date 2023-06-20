package com.example.clj.api.controller;

import com.example.clj.api.dto.FuncionarioDTO;
import com.example.clj.api.dto.JogoDTO;
import com.example.clj.exception.RegraNegocioException;
import com.example.clj.model.entity.Cargo;
import com.example.clj.model.entity.Funcionario;
import com.example.clj.model.entity.Jogo;
import com.example.clj.model.entity.Publisher;
import com.example.clj.service.JogoService;
import com.example.clj.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/jogos")
@RequiredArgsConstructor
@CrossOrigin
public class JogoController {

    private final JogoService service;
    private final PublisherService publisherService;

    @GetMapping()
    public ResponseEntity get(){
        List<Jogo> jogos = service.getJogos();
        return ResponseEntity.ok(jogos.stream().map(JogoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Jogo> jogo = service.getJogoById(id);
        if (!jogo.isPresent()) {
            return new ResponseEntity("Jogo não encontrado!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(jogo.map(JogoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody JogoDTO dto){
        try {
            Jogo jogo = converter(dto);
            jogo = service.salvar(jogo);
            return new ResponseEntity(jogo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody JogoDTO dto){
        if (!service.getJogoById(id).isPresent()) {
            return new ResponseEntity("Jogo não encontrado!", HttpStatus.NOT_FOUND);
        } try {
            Jogo jogo = converter(dto);
            jogo.setId(id);
            service.salvar(jogo);
            return ResponseEntity.ok(jogo);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Jogo> jogo = service.getJogoById(id);
        if (!jogo.isPresent()) {
            return new ResponseEntity("Jogo não encontrado!", HttpStatus.NOT_FOUND);
        } try {
            service.excluir(jogo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Jogo converter(JogoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Jogo jogo = modelMapper.map(dto, Jogo.class);
        if (dto.getIdPublisher() != null) {
            Optional<Publisher> publisher = publisherService.getPublisherById(Long.valueOf(dto.getIdPublisher()));
            if (!publisher.isPresent()) {
                jogo.setPublisher(null);
            } else {
                jogo.setPublisher(publisher.get());
            }
        }
        return jogo;
    }
}
