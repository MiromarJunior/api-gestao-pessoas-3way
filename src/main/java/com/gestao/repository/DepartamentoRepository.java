package com.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestao.entity.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
	
	boolean existsByNome(String nome);
	
	

}
