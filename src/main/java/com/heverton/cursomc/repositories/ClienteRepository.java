package com.heverton.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.heverton.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	//Fazer CRUD da tabela CATEGORIA
	
	@Transactional(readOnly=true) //Faz com que seja só um read
	Cliente findByEmail(String email); //Só com isso o spring identifica que é um select por email sem precisar montar nada
	
}
