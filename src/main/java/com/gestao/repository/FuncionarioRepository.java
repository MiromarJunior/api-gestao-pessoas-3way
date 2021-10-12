package com.gestao.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestao.entity.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	List<Funcionario> findByNomeContainingOrderByNomeAsc(String nome);
	
	@Query("SELECT f FROM Funcionario f WHERE f.cargo.id = :id")
	List<Funcionario> findByCargo(@Param("id") Long id);
	
	List<Funcionario> findByDataEntrada(LocalDate dataEntrada);
	
	List<Funcionario> findByDataSaida(LocalDate dataSaida);
	
	@Query("SELECT f FROM Funcionario f WHERE f.dataEntrada >= :dataEntrada AND f.dataSaida <= :dataSaida")
	List<Funcionario> findByDataEntradaDataSaida(
			@Param("dataEntrada")LocalDate dataEntrada,
			@Param("dataSaida")LocalDate dataSaida);
}
