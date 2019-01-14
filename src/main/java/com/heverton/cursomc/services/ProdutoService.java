package com.heverton.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.heverton.cursomc.domain.Categoria;
import com.heverton.cursomc.domain.Produto;
import com.heverton.cursomc.repositories.CategoriaRepository;
import com.heverton.cursomc.repositories.ProdutoRepository;
import com.heverton.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	//CRIA OS METODOS USANDO O REPOSITORIO QUE E QUEM FAZ O CRUD

	
	@Autowired
	private ProdutoRepository repo; //Cria um objeto de repositorio
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto buscar(Integer id) {
		Produto obj = repo.findOne(id);//Usa o obj de repositorio para ir buscar no banco o id
		if(obj==null){
			throw new ObjectNotFoundException("NOT_FOUND");
		}
		return obj;
	}
	
	
	public Page<Produto> search (String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage,Direction.valueOf(direction),orderBy);
		List<Categoria> categorias = categoriaRepository.findAll(ids);
		
		return repo.search(nome, categorias,pageRequest);
	}
	
	
}
