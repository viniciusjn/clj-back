package com.example.clj.service;

import com.example.clj.exception.RegraNegocioException;
import com.example.clj.model.entity.Funcionario;
import com.example.clj.model.entity.Venda;
import com.example.clj.model.repository.FuncionarioRepository;
import com.example.clj.model.repository.VendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VendaService {

    private VendaRepository repository;

    public VendaService(VendaRepository repository) {
        this.repository = repository;
    }

    public List<Venda> getVendas() {
        return repository.findAll();
    }

    public Optional<Venda> getVendaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Venda salvar(Venda venda) throws RegraNegocioException {
        validar(venda);
        return repository.save(venda);
    }

    @Transactional
    public void excluir(Venda venda) {
        Objects.requireNonNull(venda.getId());
        repository.delete(venda);
    }

    public void validar(Venda venda) throws RegraNegocioException {
        if (venda.getCodVenda() == null || venda.getCodVenda().equals("")) {
            throw new RegraNegocioException("Código inválido!");
        }
        if (venda.getValor() == null || venda.getValor().equals("")) {
            throw new RegraNegocioException("Valor inválido!");
        }
        if (venda.getJogo() == null || venda.getJogo().getId() == null || venda.getJogo().getId() == 0) {
            throw new RegraNegocioException("Jogo inválido!");
        }
        if (venda.getCliente() == null || venda.getCliente().getId() == null || venda.getCliente().getId() == 0) {
            throw new RegraNegocioException("Cliente inválido!");
        }
    }
}
