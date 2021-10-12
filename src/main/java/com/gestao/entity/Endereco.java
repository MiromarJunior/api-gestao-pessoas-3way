package com.gestao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gestao.enumeration.UF;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "enderecos")
public class Endereco implements Serializable {

	private static final long serialVersionUID = 7339452821970613472L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 250)
	@Column(nullable = false, length = 250)
	private String logradouro;
	
	@NotBlank
	@Size(min = 3, max = 200)
	@Column(nullable = false, length = 200)
	private String bairro;
	
	@NotBlank
	@Size(min = 3, max = 150)
	@Column(nullable = false, length = 150)
	private String cidade;
	
	@NotNull(message = "{NotNull.endereco.uf}")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 2)
	private UF uf;
	
	@NotBlank
	@Size(min = 9, max = 9, message = "{Size.endereco.cep}")
	@Column(nullable = false, length = 9)
	private String cep;

	@Column(length = 150)
	private String complemento;

	@NotNull(message = "{NotNull.endereco.numero}")
	@Digits(integer = 5, fraction = 0)
	@Column(length = 5)
	private Integer numero;
	
	

}
