package com.heverton.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.heverton.cursomc.domain.Cliente;
import com.heverton.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")//Quando requisitarem uma /categorias
public class ClienteResource {
	
	//Cria uma objeto de ClienteService que e responsavel pelos comandos "SQL" 
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)//Pega o id passado pelo /{id}
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {//Responde uma entidade qualquer funcao find(poderia ser outro nome) e usa o id que recebeu via get como parâmetro
		
		Cliente obj = service.find(id);//Cria um objeto com a resposta da função buscar contida em objeto de ClienteServices pasasndo o id que recebeu de parâmetro
		return ResponseEntity.ok().body(obj);//Resposta ok e no corpo  responde o objeto 
	}
}
