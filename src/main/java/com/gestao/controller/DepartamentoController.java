package com.gestao.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestao.entity.Departamento;
import com.gestao.service.DepartamentoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

	@Autowired
	private DepartamentoService service;

	@GetMapping("/cadastrar") // http://localhost:8080/departamentos/cadastrar
	public String cadastrar(Departamento departamento) {
		return "departamento/cadastro";// *departamento é o diretorio e cadastro é o nome do arquivo sem a extensão
	}


	@GetMapping("/listar") // http://localhost:8080/departamentos/listar
	public String listar(ModelMap model) {
		//model.addAttribute("departamentos", service.findAll());

		return findPaginated(1, "nome", "asc", model);
	}
	

	@PostMapping("/salvar") // http://localhost:8080/departamentos/salvar
	public String salvar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "departamento/cadastro";
		}

		String msg;
		if (departamento.getId() == null) {
			msg = "Departamento inserido com Sucesso";
		} else {
			msg = "Departamento alterado com Sucesso";
		}
		service.saveOrUpdate(departamento);
		attr.addFlashAttribute("success", msg);
		return "redirect:/departamentos/cadastrar";

	}

	@GetMapping("/editar/{id}") // http://localhost:8080/departamentos/editar/12
	public String editar(@PathVariable("id") Departamento departamento, ModelMap model) {
		model.addAttribute("departamento", departamento);
		return "departamento/cadastro";
	}

	@GetMapping("/excluir/{id}") // http://localhost:8080/departamentos/excluir/33
	public String excluir(@PathVariable("id") Departamento departamento, ModelMap model) {
		if (departamento.getCargos().isEmpty()) {
			service.delete(departamento.getId());
			model.addAttribute("success", "Departamento excluido com sucesso!!!");
		} else {
			model.addAttribute("fail", "Departamento não pode ser excluido.Possui cargos vinculado(s) !!!");
		}
		return listar(model);
	}

	@GetMapping("/page/{pageNo}")
	public String findPaginated(
			@PathVariable("pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDirection, 
			ModelMap model) {
		
		int pageSize = 3;
		
		Page<Departamento> page = service.findPaginated(pageNo, pageSize, sortField, sortDirection);
		model.addAttribute("departamentos",page.getContent());
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDirection);
		model.addAttribute("reverseSortDir",sortDirection.equals("asc") ? "desc" : "asc" );


		return "departamento/lista";

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
