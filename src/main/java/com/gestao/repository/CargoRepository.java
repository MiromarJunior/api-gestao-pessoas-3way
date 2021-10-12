package com.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestao.entity.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
	
	boolean existsByNome(String nome);
	
	

}
