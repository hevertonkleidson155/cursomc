package com.heverton.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.heverton.cursomc.domain.Cliente;
import com.heverton.cursomc.repositories.ClienteRepository;
import com.heverton.cursomc.security.UserSS;

public class UserDetailsServicelmpl implements UserDetailsService{
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Cliente cli = repo.findByEmail(email);
		if(cli==null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserSS(cli.getId(),cli.getEmail(),cli.getSenha(), cli.getPerfis());
	}

}
