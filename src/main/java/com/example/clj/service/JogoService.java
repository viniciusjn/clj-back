package com.example.clj.service;

import com.example.clj.exception.RegraNegocioException;
import com.example.clj.model.entity.Jogo;
import com.example.clj.model.repository.JogoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JogoService {

    private JogoRepository repository;

    public JogoService(JogoRepository repository) {
        this.repository = repository;
    }

    public List<Jogo> getJogos() {
        return repository.findAll();
    }

    public Optional<Jogo> getJogoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Jogo salvar (Jogo jogo) throws RegraNegocioException {
        validar(jogo);
        return repository.save(jogo);
    }

    @Transactional
    public void excluir(Jogo jogo) {
        Objects.requireNonNull(jogo.getId());
        repository.delete(jogo);
    }

    public void validar(Jogo jogo) throws RegraNegocioException {
        if (jogo.getNomeJogo() == null || jogo.getNomeJogo().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
        if (jogo.getGeneroJogo() == null || jogo.getGeneroJogo().trim().equals("")) {
            throw new RegraNegocioException("Gênero inválido!");
        }
        if (jogo.getClassificacaoind() == null || jogo.getClassificacaoind().equals("")) {
            throw new RegraNegocioException("Classificação indicativa inválida!");
        }
        if (jogo.getPublisher() == null || jogo.getPublisher().getId() == null || jogo.getPublisher().getId() == 0) {
            throw new RegraNegocioException("Publisher inválida!");
        }
    }



}
