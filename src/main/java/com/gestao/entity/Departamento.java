package com.gestao.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "departamentos")
public class Departamento implements Serializable{
	
	
	private static final long serialVersionUID = -1248492119310651043L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotBlank(message = "Informe o nome do Departamento")
	@Size(min = 3, max =100, message = " O nome do departamamento deve ter entre{min} e {max} caracteres")
	@Column(name = "nome", nullable = false, unique = true, length = 100)
	private String nome ;
	
	@OneToMany(mappedBy = "departamento")
	private List<Cargo> cargos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
