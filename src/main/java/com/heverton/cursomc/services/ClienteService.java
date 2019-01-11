package com.heverton.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heverton.cursomc.domain.Cliente;
import com.heverton.cursomc.repositories.ClienteRepository;
import com.heverton.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	//CRIA OS METODOS USANDO O REPOSITORIO QUE E QUEM FAZ O CRUD
	@Autowired
	private ClienteRepository repo; //Cria um objeto de repositorio
	
	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id);//Usa o obj de repositorio para ir buscar no banco o id
		if(obj==null){
			throw new ObjectNotFoundException("NOT_FOUND");
		}
		return obj;
	}
	
	
}
