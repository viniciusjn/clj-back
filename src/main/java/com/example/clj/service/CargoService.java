package com.example.clj.service;

import com.example.clj.exception.RegraNegocioException;
import com.example.clj.model.entity.Cargo;
import com.example.clj.model.repository.CargoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class CargoService {

    private CargoRepository repository;

    public CargoService(CargoRepository repository){
        this.repository = repository;
    }

    public List<Cargo> getCargos(){
        return repository.findAll();
    }

    public Optional<Cargo> getCargoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Cargo salvar (Cargo cargo) throws RegraNegocioException {
        validar(cargo);
        return repository.save(cargo);
    }

    @Transactional
    public void excluir(Cargo cargo) {
        Objects.requireNonNull(cargo.getId());
        repository.delete(cargo);
    }

    public void validar(Cargo cargo) throws RegraNegocioException {
        if (cargo.getNome() == null || cargo.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
        if (cargo.getSalario() == null || cargo.getSalario().equals("")) {
            throw new RegraNegocioException("Salário inválido!");
        }
    }
}
