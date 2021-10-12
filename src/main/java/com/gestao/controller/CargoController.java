package com.gestao.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestao.entity.Cargo;
import com.gestao.entity.Departamento;
import com.gestao.service.CargoService;
import com.gestao.service.DepartamentoService;

@Controller
@RequestMapping("/cargos")
public class CargoController {

	@Autowired
	private CargoService service;

	@Autowired
	private DepartamentoService departamentoService;

	@GetMapping("/cadastrar") // http://localhost:8080/cargos/cadastrar
	public String cadastrar(Cargo cargo) {
		return "cargo/cadastro";// *cargo é o diretorio e cadastro é o nome do arquivo sem a extensão
	}

	@GetMapping("/listar") // http://localhost:8080/cargos/listar
	public String listar(ModelMap model) {
		model.addAttribute("cargos", service.findAll());

		return "cargo/lista";// *cargo é o diretorio e cadastro é o nome do arquivo sem a extensão
	}

	@PostMapping("/salvar") // http://localhost:8080/cargos/salvar
	public String salvar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			return  "cargo/cadastro";
		}
		
		
		
		String msg;
		if (cargo.getId() == null) {
			msg = "Cargo inserido com Sucesso";
		} else {
			msg = "Cargo alterado com Sucesso";
		}
		service.saveOrUpdate(cargo);
		attr.addFlashAttribute("success", msg);
		return "redirect:/cargos/cadastrar";

	}

	@GetMapping("/editar/{id}") // http://localhost:8080/cargos/editar/12
	public String editar(@PathVariable("id") Cargo cargo, ModelMap model) {
		model.addAttribute("cargo", cargo);
		return "cargo/cadastro";
	}

	@GetMapping("/excluir/{id}") // http://localhost:8080/cargos/excluir/33
	public String excluir(@PathVariable("id") Cargo cargo, ModelMap model) {
		if (cargo.getFuncionarios().isEmpty()) {
			service.delete(cargo.getId());
			model.addAttribute("success", "Cargo excluido com sucesso!!!");
		} else {
			model.addAttribute("fail", "Cargo não pode ser excluido.Possui funcionario vinculado(s) !!!");
		}
		return listar(model);
	}

	@ModelAttribute("departamentos")
	public List<Departamento> listaDeDepartamentos() {
		return departamentoService.findAll();
	}

}
