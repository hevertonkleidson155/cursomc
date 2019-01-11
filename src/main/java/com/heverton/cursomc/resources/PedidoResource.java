package com.heverton.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.heverton.cursomc.domain.Pedido;
import com.heverton.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")//Quando requisitarem uma /categorias
public class PedidoResource {
	
	//Cria uma objeto de PedidoService que e responsavel pelos comandos "SQL" 
	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)//Pega o id passado pelo /{id}
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {//Responde uma entidade qualquer funcao find(poderia ser outro nome) e usa o id que recebeu via get como parâmetro
		
		Pedido obj = service.buscar(id);//Cria um objeto com a resposta da função buscar contida em objeto de PedidoServices pasasndo o id que recebeu de parâmetro
		return ResponseEntity.ok().body(obj);//Resposta ok e no corpo  responde o objeto 
	}
}
