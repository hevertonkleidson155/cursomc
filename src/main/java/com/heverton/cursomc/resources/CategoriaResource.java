package com.heverton.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.heverton.cursomc.domain.Categoria;
import com.heverton.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")//Quando requisitarem uma /categorias
public class CategoriaResource {
	
	//Cria uma objeto de CategoriaService que e responsavel pelos comandos "SQL" 
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)//Pega o id passado pelo /{id}
	public ResponseEntity<?> find(@PathVariable Integer id) {//Responde uma entidade qualquer funcao find(poderia ser outro nome) e usa o id que recebeu via get como parâmetro
		Categoria obj = service.buscar(id);//Cria um objeto com a resposta da função buscar contida em objeto de CategoriaServices pasasndo o id que recebeu de parâmetro
		
		return ResponseEntity.ok().body(obj);//Resposta ok e no corpo  responde o objeto 
	}
}
