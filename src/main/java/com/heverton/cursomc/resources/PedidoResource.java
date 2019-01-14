package com.heverton.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.heverton.cursomc.domain.Categoria;
import com.heverton.cursomc.domain.Pedido;
import com.heverton.cursomc.dto.CategoriaDTO;
import com.heverton.cursomc.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos") // Quando requisitarem uma /categorias
public class PedidoResource {

	// Cria uma objeto de PedidoService que e responsavel pelos comandos "SQL"
	@Autowired
	private PedidoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // Pega o id passado pelo /{id}
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {// Responde uma entidade qualquer funcao find(poderia
																	// ser outro nome) e usa o id que recebeu via get
																	// como parâmetro

		Pedido obj = service.buscar(id);// Cria um objeto com a resposta da função buscar contida em objeto de
										// PedidoServices pasasndo o id que recebeu de parâmetro
		return ResponseEntity.ok().body(obj);// Resposta ok e no corpo responde o objeto
	}

	// POST localhost:8080/categorias e body (mudar no contenty type para json)
	// {"nome": "Computadores"} -> cria a categoria com o id seguite ao que já
	// existe
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
		
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
