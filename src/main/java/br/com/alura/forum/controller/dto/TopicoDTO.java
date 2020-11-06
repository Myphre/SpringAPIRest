package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.com.alura.forum.modelo.Topico;

public class TopicoDTO {
/*
 * DTO = Data Transfer Objects, objetos para apresentar os dados. Evita problemas de seguranca ( como printar uma classe
 * inteira e mostrar a senha de um usuario) ;
 */
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	
	public TopicoDTO() {
		this.id = 11L;
		this.titulo =  "Titulo Padrao";
		this.mensagem = "Mensagem Padrao";
		this.dataCriacao = LocalDateTime.now();
	}
	
	public TopicoDTO( Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public static List<TopicoDTO> converter(List<Topico> topicos) {
		return topicos.stream().map(TopicoDTO::new).collect(Collectors.toList());
	}
	
	
}
