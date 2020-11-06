package br.com.alura.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;

//@Controller // permite mapeamento de chamadas pelo http pra chamada de uma funcao
@RestController // assume por padrao que todos os metodos sao ResponseBoddy
public class TopicosController {

	@RequestMapping("/topicos")
	//@ResponseBody // Spring devolve o metodo diretamente, nao irei navegar pela pagina
	public List<TopicoDTO> lista(){
		Topico topico = new Topico("Duvida", "Duvida com Spring", new Curso("Spring", "Programacao"));
		
		return TopicoDTO.converter(Arrays.asList(topico, topico, topico));
	}
}
