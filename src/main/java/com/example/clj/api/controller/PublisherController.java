package com.example.clj.api.controller;

import com.example.clj.api.dto.PublisherDTO;
import com.example.clj.exception.RegraNegocioException;
import com.example.clj.model.entity.Publisher;
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
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
@CrossOrigin
public class PublisherController {

    private final PublisherService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Publisher> publishers = service.getPublishers();
        return ResponseEntity.ok(publishers.stream().map(PublisherDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Publisher> publisher = service.getPublisherById(id);
        if (!publisher.isPresent()) {
            return new ResponseEntity<>("Publisher não encontrada!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(publisher.map(PublisherDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody PublisherDTO dto){
        try {
            Publisher publisher = converter(dto);
            publisher = service.salvar(publisher);
            return new ResponseEntity(publisher, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping({"/{id}"})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PublisherDTO dto){
        if (!service.getPublisherById(id).isPresent()) {
            return new ResponseEntity("Publisher não encontrada!", HttpStatus.NOT_FOUND);
        } try {
            Publisher publisher = converter(dto);
            publisher.setId(id);
            service.salvar(publisher);
            return ResponseEntity.ok(publisher);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Publisher> publisher = service.getPublisherById(id);
        if (!publisher.isPresent()) {
            return new ResponseEntity("Publisher não encontrada!", HttpStatus.NOT_FOUND);
        } try {
            service.excluir(publisher.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Publisher converter(PublisherDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Publisher.class);
    }
}
