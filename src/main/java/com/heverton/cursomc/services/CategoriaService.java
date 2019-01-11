package com.heverton.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.heverton.cursomc.domain.Categoria;
import com.heverton.cursomc.repositories.CategoriaRepository;
import com.heverton.cursomc.services.exceptions.DataIntegrityException;
import com.heverton.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	//CRIA OS METODOS USANDO O REPOSITORIO QUE E QUEM FAZ O CRUD
	@Autowired
	private CategoriaRepository repo; //Cria um objeto de repositorio
	
	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);//Usa o obj de repositorio para ir buscar no banco o id
		if(obj==null){
			throw new ObjectNotFoundException("NOT_FOUND");
		}
		return obj;
	}
	
	public Categoria insert(Categoria obj) {
		
		obj.setId(null); //Se nao colocar o id nulo e passarem o id no objeto que ta chegando o metodo save vai achar que e uma atualizacao e nao insersao
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		}
		catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir categorias que possuem produtos");
		}
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
}
