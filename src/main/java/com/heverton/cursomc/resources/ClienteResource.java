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

import com.heverton.cursomc.domain.Cliente;
import com.heverton.cursomc.dto.ClienteDTO;
import com.heverton.cursomc.dto.ClienteNewDTO;
import com.heverton.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes") // Quando requisitarem uma /categorias
public class ClienteResource {
	
	// Cria uma objeto de ClienteService que e responsavel pelos comandos "SQL"
	@Autowired
	private ClienteService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // Pega o id passado pelo /{id}
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {// Responde uma entidade qualquer funcao find(poderia
																	// ser outro nome) e usa o id que recebeu via get
																	// como parâmetro

		Cliente obj = service.find(id);// Cria um objeto com a resposta da função buscar contida em objeto de
										// ClienteServices pasasndo o id que recebeu de parâmetro
		return ResponseEntity.ok().body(obj);// Resposta ok e no corpo responde o objeto
	}
	
	 // POST localhost:8080/categorias e body (mudar no contenty type para json){"nome": "Computadores"} -> cria a categoria com o id seguite ao que já existe
	 @RequestMapping(method=RequestMethod.POST)
	 public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) { 
		 Cliente obj =service.fromDTO(objDto);
		 obj = service.insert(obj); 
		 URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri(); return ResponseEntity.created(uri).build(); 
	 }

	// PUT localhost:8080/categorias/1 e body {"nome": "OutroNome", "email":
	// "email@email.com"} -> Atualiza a categoria com o id passado
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);

		return ResponseEntity.noContent().build();
	}

	// DELETE
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) // Pega o id passado pelo /{id}
	public ResponseEntity<Void> delete(@PathVariable Integer id) {// Responde uma entidade qualquer funcao find(poderia
																	// ser outro nome) e usa o id que recebeu via get
																	// como parâmetro

		service.delete(id);// Cria um objeto com a resposta da função buscar contida em objeto de
							// ClienteServices pasasndo o id que recebeu de parâmetro
		return ResponseEntity.noContent().build();// Resposta ok e no corpo responde o objeto
	}

	// BUSCAR TODAS AS CATEGORIAS
	@RequestMapping(method = RequestMethod.GET) // Pega o id passado pelo /{id}
	public ResponseEntity<List<ClienteDTO>> findAll() {// Responde uma entidade qualquer funcao find(poderia ser outro
														// nome) e usa o id que recebeu via get como parâmetro

		List<Cliente> list = service.findAll();// Cria um objeto com a resposta da função buscar contida em objeto de
												// ClienteServices pasasndo o id que recebeu de parâmetro
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listDto);// Resposta ok e no corpo responde o objeto
	}

	// Busca todos e retorna pequenas quantidades por vez como se fosse páginas
	@RequestMapping(value = "/page", method = RequestMethod.GET) // Pega o id passado pelo /{id}
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {// Responde uma entidade
																						// qualquer funcao find(poderia
																						// ser outro nome) e usa o id
																						// que recebeu via get como
																						// parâmetro
																						// ASC - Ascendente e DESC - descendente

		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);// Cria um objeto com a resposta
																						// da função buscar contida em
																						// objeto de ClienteServices
																						// pasasndo o id que recebeu de
																						// parâmetro
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));

		return ResponseEntity.ok().body(listDto);// Resposta ok e no corpo responde o objeto
	}
}
