package com.heverton.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.heverton.cursomc.domain.Categoria;
import com.heverton.cursomc.dto.CategoriaDTO;
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
	
	// POST localhost:8080/categorias e body (mudar no contenty type para json) {"nome": "Computadores"} -> cria a categoria com o id seguite ao que já existe
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) {
		Categoria obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	// PUT localhost:8080/categorias/1 e body {"nome": "OutroNome"} -> Atualiza a categoria com o id passado
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	// DELETE 
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)//Pega o id passado pelo /{id}
	public ResponseEntity<Void> delete(@PathVariable Integer id) {//Responde uma entidade qualquer funcao find(poderia ser outro nome) e usa o id que recebeu via get como parâmetro
		
		service.delete(id);//Cria um objeto com a resposta da função buscar contida em objeto de CategoriaServices pasasndo o id que recebeu de parâmetro
		return ResponseEntity.noContent().build();//Resposta ok e no corpo  responde o objeto  
	}
	
	// BUSCAR TODAS AS CATEGORIAS
	@RequestMapping(method=RequestMethod.GET)//Pega o id passado pelo /{id}
	public ResponseEntity<List<CategoriaDTO>> findAll() {//Responde uma entidade qualquer funcao find(poderia ser outro nome) e usa o id que recebeu via get como parâmetro
		
		List<Categoria> list = service.findAll();//Cria um objeto com a resposta da função buscar contida em objeto de CategoriaServices pasasndo o id que recebeu de parâmetro
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);//Resposta ok e no corpo  responde o objeto  
	}
	
	//Busca todos e retorna pequenas quantidades por vez como se fosse páginas
	@RequestMapping(value="/page", method=RequestMethod.GET)//Pega o id passado pelo /{id}
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {//Responde uma entidade qualquer funcao find(poderia ser outro nome) e usa o id que recebeu via get como parâmetro
			//ASC - Ascendente  e DESC - descendente
		
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);//Cria um objeto com a resposta da função buscar contida em objeto de CategoriaServices pasasndo o id que recebeu de parâmetro
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(listDto);//Resposta ok e no corpo  responde o objeto  
	}
	
}
