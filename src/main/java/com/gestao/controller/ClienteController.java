package com.gestao.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestao.entity.Cliente;
import com.gestao.enumeration.UF;
import com.gestao.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;

	@GetMapping("/cadastrar") // http://localhost:8080/clientes/cadastrar
	public String cadastrar(Cliente cliente) {
		return "cliente/cadastro";// *cliente é o diretorio e cadastro é o nome do arquivo sem a extensão
	}

	@GetMapping("/listar") // http://localhost:8080/clientes/listar
	public String listar(ModelMap model) {
		

		return findPaginated(1, "nome", "asc", model);// *cliente é o diretorio e cadastro é o nome do arquivo sem a extensão
	}

	@GetMapping("/buscar/nome")
	public String findByNome(@RequestParam("nome") String nome, ModelMap model) {
		model.addAttribute("clientes", service.findByNome(nome));

		return "cliente/lista";
	}

	@GetMapping("/buscar/data")
	public String findByDatas(
			@RequestParam(value = "nascimento", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate nascimento,

			ModelMap model) {
		model.addAttribute("clientes", service.findByDatas(nascimento));
		return "cliente/lista";
	}

	@PostMapping("/salvar") // http://localhost:8080/clientes/salvar
	public String salvar(@Valid Cliente cliente, BindingResult result,RedirectAttributes attr) {
		
		if(result.hasErrors() ) {
			return "cliente/cadastro";
		}
		String msg;
		if (cliente.getId() == null) {
			msg = "Cliente inserido com Sucesso";
		} else {
			msg = "Cliente alterado com Sucesso";
		}
		service.saveOrUpdate(cliente);
		attr.addFlashAttribute("success", msg);
		return "redirect:/clientes/cadastrar";

	}

	@GetMapping("/editar/{id}") // http://localhost:8080/clientes/editar/12
	public String editar(@PathVariable("id") Cliente cliente, ModelMap model) {
		model.addAttribute("cliente", cliente);
		return "cliente/cadastro";
	}

	@GetMapping("/excluir/{id}") // http://localhost:8080/clientes/excluir/33
	public String excluir(@PathVariable("id") Cliente cliente, ModelMap model) {

		service.delete(cliente.getId());
		model.addAttribute("success", "Cliente excluido com sucesso!!!");

		return listar(model);
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(
			@PathVariable("pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDirection, 
			ModelMap model) {
		
		int pageSize = 3;
		
		Page<Cliente> page = service.findPaginated(pageNo, pageSize, sortField, sortDirection);
		model.addAttribute("clientes",page.getContent());
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDirection);
		model.addAttribute("reverseSortDir",sortDirection.equals("asc") ? "desc" : "asc" );


		return "cliente/lista";

	}
	
	
	
	

	@ModelAttribute("ufs")
	public UF[] listaDeestados() {
		return UF.values();
	}

}
