package com.gestao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gestao.entity.Cargo;
import com.gestao.repository.CargoRepository;

@Service
public class CargoService {

	@Autowired
	private CargoRepository repository;

	public void saveOrUpdate(Cargo cargo) {
		repository.save(cargo);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public List<Cargo> findAll() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}

	public boolean existsByNome(String nome) {
		return repository.existsByNome(nome);
		
		

	}

}
