package com.heverton.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heverton.cursomc.domain.ItemPedido;
import com.heverton.cursomc.domain.PagamentoComBoleto;
import com.heverton.cursomc.domain.Pedido;
import com.heverton.cursomc.enums.EstadoPagamento;
import com.heverton.cursomc.repositories.ClienteRepository;
import com.heverton.cursomc.repositories.ItemPedidoRepository;
import com.heverton.cursomc.repositories.PagamentoRepository;
import com.heverton.cursomc.repositories.PedidoRepository;
import com.heverton.cursomc.repositories.ProdutoRepository;
import com.heverton.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	//CRIA OS METODOS USANDO O REPOSITORIO QUE E QUEM FAZ O CRUD

	
	@Autowired
	private PedidoRepository repo; //Cria um objeto de repositorio
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido buscar(Integer id) {
		Pedido obj = repo.findOne(id);//Usa o obj de repositorio para ir buscar no banco o id
		if(obj==null){
			throw new ObjectNotFoundException("NOT_FOUND");
		}
		return obj;
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteRepository.findOne(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip: obj.getItens()) {
			ip.setDesonto(0.0);
			ip.setProduto(produtoRepository.findOne(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.save(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		System.out.println("Passou aqui");
		return obj;
	}
		
	}


