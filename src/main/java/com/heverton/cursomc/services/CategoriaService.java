package com.heverton.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heverton.cursomc.domain.Categoria;
import com.heverton.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	//CRIA OS METODOS USANDO O REPOSITORIO QUE E QUEM FAZ O CRUD
	@Autowired
	private CategoriaRepository repo; //Cria um objeto de repositorio
	
	public Categoria buscar(Integer id) {
		Categoria obj = repo.findOne(id);//Usa o obj de repositorio para ir buscar no banco o id
		return obj;
	}
}
