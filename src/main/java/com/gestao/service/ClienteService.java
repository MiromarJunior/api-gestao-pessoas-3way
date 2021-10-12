package com.gestao.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gestao.entity.Cliente;
import com.gestao.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public void saveOrUpdate(Cliente cliente) {
		repository.save(cliente);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public List<Cliente> findAll() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}
	public List<Cliente> findByNome(String nome) {
		return repository.findByNomeContainingOrderByNomeAsc(nome);
	}

	

	public List<Cliente> findByDatas(LocalDate dataNascimento) {
		if (dataNascimento != null ) {
			return repository.findByDataNascimento(dataNascimento);
		}else {
			return null;
		}
	}
	
	
public Page<Cliente>findPaginated(int pageNo, int pageSize, String sortField, String sortDirection){
		
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
			? Sort.by(sortField).ascending()
			: Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);
		
		return repository.findAll(pageable);
		
	}

}
