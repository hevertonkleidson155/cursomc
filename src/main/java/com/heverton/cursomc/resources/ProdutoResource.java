package com.heverton.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heverton.cursomc.domain.Categoria;
import com.heverton.cursomc.domain.Produto;
import com.heverton.cursomc.dto.ProdutoDTO;
import com.heverton.cursomc.resources.utils.URL;
import com.heverton.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")//Quando requisitarem uma /categorias
public class ProdutoResource {
	
	//Cria uma objeto de ProdutoService que e responsavel pelos comandos "SQL" 
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)//Pega o id passado pelo /{id}
	public ResponseEntity<Produto> find(@PathVariable Integer id) {//Responde uma entidade qualquer funcao find(poderia ser outro nome) e usa o id que recebeu via get como parâmetro
		
		Produto obj = service.buscar(id);//Cria um objeto com a resposta da função buscar contida em objeto de ProdutoServices pasasndo o id que recebeu de parâmetro
		return ResponseEntity.ok().body(obj);//Resposta ok e no corpo  responde o objeto 
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String  nome,
			@RequestParam(value="categorias", defaultValue="") String  categorias,
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {//Responde uma entidade qualquer funcao find(poderia ser outro nome) e usa o id que recebeu via get como parâmetro
			//ASC - Ascendente  e DESC - descendente
		
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded,ids, page, linesPerPage, orderBy, direction);//Cria um objeto com a resposta da função buscar contida em objeto de CategoriaServices pasasndo o id que recebeu de parâmetro
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(listDto);//Resposta ok e no corpo  responde o objeto  
	}
}
