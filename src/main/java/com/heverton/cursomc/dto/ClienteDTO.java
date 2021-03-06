package com.heverton.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.heverton.cursomc.domain.Cliente;
import com.heverton.cursomc.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L; 
	
	private Integer id;
	@NotEmpty(message="O campo nome não pode ser vazio")
	@Length(min=5,max=120, message="Nome muito curto, favor corrigir o campo nome")
	private String nome;
	
	@NotEmpty(message="O campo nome não pode ser vazio")
	@Email(message="Email inválido")
	//@Column(unique=true) - torna o campo único no banco. Não deixando inserir dado repetido. Mas você não vai ter controle caso dê erro para fazer uma respota bonitinha
	private String email;
	
	
	public ClienteDTO() {
		
	}

	public ClienteDTO(Cliente obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
