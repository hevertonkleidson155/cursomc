package com.heverton.cursomc;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.heverton.cursomc.domain.Categoria;
import com.heverton.cursomc.domain.Cidade;
import com.heverton.cursomc.domain.Cliente;
import com.heverton.cursomc.domain.Endereco;
import com.heverton.cursomc.domain.Estado;
import com.heverton.cursomc.domain.Produto;
import com.heverton.cursomc.enums.TipoCliente;
import com.heverton.cursomc.repositories.CategoriaRepository;
import com.heverton.cursomc.repositories.CidadeRepository;
import com.heverton.cursomc.repositories.ClienteRepository;
import com.heverton.cursomc.repositories.EnderecoRepository;
import com.heverton.cursomc.repositories.EstadoRepository;
import com.heverton.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null,"Informatica");
		Categoria cat2 = new Categoria(null,"Escritorio");
		
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Impressora",800.00);
		Produto p3 = new Produto(null,"Mouse",40.00);
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.save(Arrays.asList(cat1,cat2));
		produtoRepository.save(Arrays.asList(p1,p2,p3));
		
		est1.getCidade().addAll(Arrays.asList(c1));
		est2.getCidade().addAll(Arrays.asList(c2,c3));
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		
		estadoRepository.save(Arrays.asList(est1,est2));
		cidadeRepository.save(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com","36378912377",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("232313213","1233123"));
		
		Endereco e1 = new Endereco(null, "Rua Flores","332","Apt. 32","Jardim","32424234",cli1,c1);
		Endereco e2 = new Endereco(null, "Av. Matos","23","Apt. 112","Centro","33424234",cli1,c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.save(Arrays.asList(cli1));
		enderecoRepository.save(Arrays.asList(e1,e2));
		
		
	}

}

