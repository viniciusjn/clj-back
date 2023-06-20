package com.example.clj.service;

import com.example.clj.exception.RegraNegocioException;
import com.example.clj.model.entity.Publisher;
import com.example.clj.model.repository.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PublisherService {

    private PublisherRepository repository;

    public PublisherService(PublisherRepository repository) { this.repository = repository; }

    public List<Publisher> getPublishers() { return repository.findAll(); }

    public Optional<Publisher> getPublisherById(Long id) { return repository.findById(id); }

    @Transactional
    public Publisher salvar (Publisher publisher) throws RegraNegocioException{
        validar(publisher);
        return repository.save(publisher);
    }

    @Transactional
    public void excluir(Publisher publisher) {
        Objects.requireNonNull(publisher.getId());
        repository.delete(publisher);
    }

    public void validar(Publisher publisher) throws RegraNegocioException {
        if (publisher.getNome() == null || publisher.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
        if (publisher.getEstudio() == null || publisher.getEstudio().trim().equals("")) {
            throw new RegraNegocioException("Estúdio inválido!");
        }
        if (publisher.getCnpj() == null || publisher.getCnpj().equals("")) {
            throw new RegraNegocioException("CNPJ inválido!");
        }
    }
}
