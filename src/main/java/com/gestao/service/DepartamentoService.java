package com.gestao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gestao.entity.Departamento;
import com.gestao.repository.DepartamentoRepository;

@Service
public class DepartamentoService {

	@Autowired
	private DepartamentoRepository repository;

	public void saveOrUpdate(Departamento departamento) {
		repository.save(departamento);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public List<Departamento> findAll() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}

	public boolean existsByNome(String nome) {
		return repository.existsByNome(nome);

	}
	
	public Page<Departamento>findPaginated(int pageNo, int pageSize, String sortField, String sortDirection){
		
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
			? Sort.by(sortField).ascending()
			: Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);
		
		return repository.findAll(pageable);
		
	}

}
