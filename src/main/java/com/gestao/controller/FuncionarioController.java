package com.gestao.controller;

import java.time.LocalDate;
import java.util.List;

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

import com.gestao.entity.Cargo;
import com.gestao.entity.Funcionario;
import com.gestao.enumeration.UF;
import com.gestao.service.CargoService;
import com.gestao.service.FuncionarioService;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioService service;

	@Autowired
	private CargoService cargoService;

	@GetMapping("/cadastrar") // http://localhost:8080/funcionarios/cadastrar
	public String cadastrar(Funcionario funcionario) {
		return "funcionario/cadastro";// *funcionario é o diretorio e cadastro é o nome do arquivo sem a extensão
	}

	@GetMapping("/listar") // http://localhost:8080/funcionarios/listar
	public String listar(ModelMap model) {
		

		return findPaginated(1, "nome", "asc", model);// *funcionario é o diretorio e cadastro é o nome do arquivo sem a extensão
	}

	@GetMapping("/buscar/nome")
	public String findByNome(@RequestParam("nome") String nome, ModelMap model) {
		model.addAttribute("funcionarios", service.findByNome(nome));

		return "funcionario/lista";
	}

	@GetMapping("/buscar/cargo")
	public String findByNome(@RequestParam("id") Long id, ModelMap model) {
		model.addAttribute("funcionarios", service.findByCargo(id));

		return "funcionario/lista";
	}


	@GetMapping("/buscar/data")
	public String findByDatas(
			@RequestParam(value = "entrada", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate entrada,
			@RequestParam(value = "saida", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate saida,
			ModelMap model) {
		model.addAttribute("funcionarios", service.findByDatas(entrada, saida));
		return "funcionario/lista";
	}
	
	
	

	@PostMapping("/salvar") // http://localhost:8080/funcionarios/salvar
	public String salvar(@Valid Funcionario funcionario,BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			return "funcionario/cadastro";
		}
		
		String msg;
		if (funcionario.getId() == null) {
			msg = "Funcionario inserido com Sucesso";
		} else {
			msg = "Funcionario alterado com Sucesso";
		}
		service.saveOrUpdate(funcionario);
		attr.addFlashAttribute("success", msg);
		return "redirect:/funcionarios/cadastrar";

	}

	@GetMapping("/editar/{id}") // http://localhost:8080/funcionarios/editar/12
	public String editar(@PathVariable("id") Funcionario funcionario, ModelMap model) {
		model.addAttribute("funcionario", funcionario);
		return "funcionario/cadastro";
	}

	@GetMapping("/excluir/{id}") // http://localhost:8080/funcionarios/excluir/33
	public String excluir(@PathVariable("id") Funcionario funcionario, ModelMap model) {

		service.delete(funcionario.getId());
		model.addAttribute("success", "Funcionario excluido com sucesso!!!");

		return listar(model);
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(
			@PathVariable("pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDirection, 
			ModelMap model) {
		
		int pageSize = 3;
		
		Page<Funcionario> page = service.findPaginated(pageNo, pageSize, sortField, sortDirection);
		model.addAttribute("funcionarios",page.getContent());
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDirection);
		model.addAttribute("reverseSortDir",sortDirection.equals("asc") ? "desc" : "asc" );


		return "funcionario/lista";

	}
	
	
	
	
	
	

	@ModelAttribute("cargos")
	public List<Cargo> listaDeCargos() {
		return cargoService.findAll();
	}

	@ModelAttribute("ufs")
	public UF[] listaDeestados() {
		return UF.values();
	}

}
