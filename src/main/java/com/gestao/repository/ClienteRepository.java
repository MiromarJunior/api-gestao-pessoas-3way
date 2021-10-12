package com.gestao.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestao.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findByNomeContainingOrderByNomeAsc(String nome);

	@Query("SELECT c FROM Cliente c WHERE c.dataNascimento >= :dataNascimento ")
	List<Cliente> findByDataNascimento(@Param("dataNascimento") LocalDate dataNascimento);

}
