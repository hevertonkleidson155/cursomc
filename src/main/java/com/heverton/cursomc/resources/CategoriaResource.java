package com.heverton.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.heverton.cursomc.domain.Categoria;
import com.heverton.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")//Quando requisitarem uma /categorias
public class CategoriaResource {
	
	//Cria uma objeto de CategoriaService que e responsavel pelos comandos "SQL" 
	@Autowired
	private CategoriaService service;
	
	// GET localhost:8080/categorias/1 -> busca a categoria 1
	@RequestMapping(value="/{id}", method=RequestMethod.GET)//Pega o id passado pelo /{id}
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {//Responde uma entidade qualquer funcao find(poderia ser outro nome) e usa o id que recebeu via get como parâmetro
		
		Categoria obj = service.find(id);//Cria um objeto com a resposta da função buscar contida em objeto de CategoriaServices pasasndo o id que recebeu de parâmetro
		return ResponseEntity.ok().body(obj);//Resposta ok e no corpo  responde o objeto  
	}
	
	// POST localhost:8080/categorias e body {"nome": "Computadores"} -> cria a categoria com o id seguite ao que já existe
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
		
	}
	
	// PUT localhost:8080/categorias/1 e body {"nome": "OutroNome"} -> Atualiza a categoria com o id passado
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		
		obj.setId(id);
		obj = service.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
}
