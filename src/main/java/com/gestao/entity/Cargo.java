package com.gestao.entity;


// retirei o data e coloquei apenas o getter and setter, pois estava dando erro com to string
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cargos")
public class Cargo implements Serializable{
	
	
	
	private static final long serialVersionUID = -5394849746496742751L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O nome do Cargo é Obrigatório")
	@Size(min = 3, max =100, message = " O nome do cargo deve ter entre{min} e {max} caracteres")
	@Column(name = "nome", nullable = false, unique = true, length = 100)
	private String nome;
	
	@NotNull(message = "Selecione o Departamento relativo ao cargo")
	@ManyToOne
	@JoinColumn(name = "departamento_id")
	private Departamento departamento;
	
	
	@OneToMany(mappedBy = "cargo")
	private List<Funcionario> funcionarios;
	
}
