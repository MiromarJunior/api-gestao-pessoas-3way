package com.gestao.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gestao.entity.Funcionario;
import com.gestao.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repository;

	public void saveOrUpdate(Funcionario funcionario) {
		repository.save(funcionario);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public List<Funcionario> findAll() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}

	public List<Funcionario> findByNome(String nome) {
		return repository.findByNomeContainingOrderByNomeAsc(nome);
	}

	public List<Funcionario> findByCargo(Long id) {
		return repository.findByCargo(id);
	}

	public List<Funcionario> findByDatas(LocalDate dataEntrada, LocalDate dataSaida) {
		if (dataEntrada != null && dataSaida != null) {
			return repository.findByDataEntradaDataSaida(dataEntrada, dataSaida);
		}else if(dataEntrada !=null) {
			return repository.findByDataEntrada(dataEntrada);
			
		}else if(dataSaida != null) {
			return repository.findByDataSaida(dataSaida);
		}else {
			return null;
		}
	}
	
public Page<Funcionario>findPaginated(int pageNo, int pageSize, String sortField, String sortDirection){
		
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
			? Sort.by(sortField).ascending()
			: Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);
		
		return repository.findAll(pageable);
		
	}
	
	
	
	

}
