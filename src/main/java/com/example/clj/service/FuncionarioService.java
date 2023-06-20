package com.example.clj.service;

import com.example.clj.exception.RegraNegocioException;
import com.example.clj.model.entity.Funcionario;
import com.example.clj.model.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService {

    private FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public List<Funcionario> getFuncionarios() {
        return repository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Funcionario salvar(Funcionario funcionario) throws RegraNegocioException {
        validar(funcionario);
        return repository.save(funcionario);
    }

    @Transactional
    public void excluir(Funcionario funcionario) {
        Objects.requireNonNull(funcionario.getId());
        repository.delete(funcionario);
    }

    public void validar(Funcionario funcionario) throws RegraNegocioException {
        if (funcionario.getCodFunc() == null || funcionario.getCodFunc().equals("")) {
            throw new RegraNegocioException("Código inválido!");
        }
        if (funcionario.getNome() == null || funcionario.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
        if (funcionario.getCpf() == null || funcionario.getCpf().equals("")) {
            throw new RegraNegocioException("CPF inválido!");
        }
        if (funcionario.getEmail() == null || funcionario.getEmail().trim().equals("")) {
            throw new RegraNegocioException("E-mail inválido!");
        }
        if (funcionario.getCargo() == null || funcionario.getCargo().getId() == null || funcionario.getCargo().getId() == 0) {
            throw new RegraNegocioException("Cargo inválido!");
        }
    }
}
