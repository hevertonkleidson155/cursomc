package com.heverton.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.heverton.cursomc.domain.Cliente;
import com.heverton.cursomc.dto.ClienteDTO;
import com.heverton.cursomc.repositories.ClienteRepository;
import com.heverton.cursomc.services.exceptions.DataIntegrityException;
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
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		}
		catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir cliente que possuem pedidos");
		}
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage,Direction.valueOf(direction),orderBy);
		
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
