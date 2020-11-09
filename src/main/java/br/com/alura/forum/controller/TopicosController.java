package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesTopicoDTO;
import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

//@Controller // permite mapeamento de chamadas pelo http pra chamada de uma funcao
@RestController // assume por padrao que todos os metodos sao ResponseBoddy
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	//@ResponseBody // Spring devolve o metodo diretamente, nao irei navegar pela pagina
	public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso,
			//@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordem){ Metodo manual
			@PageableDefault(page = 0, direction = Direction.DESC, size = 10, sort = "id") Pageable paginacao  ){
		//http://localhost:8080/topicos/?page=0&size=2&sort=id,asc&campo=4  EXEMPLO consulta paginacao automatica
		
		// Pageable pagiancao = PageRequest.of(pagina, qtd, Direction.ASC, ordem); Paginacao "MANUAL", pega
		//manualmente os parametros pela chamada da funcao. 
		
		if(nomeCurso == null) {
			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			return TopicoDTO.converter(topicos);
		}
		else {
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
			return TopicoDTO.converter(topicos);
		}
		
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm topicoForm, 
			UriComponentsBuilder uriCompBuilder) { /// *Form, e pra pega info da tela/usuario e lancar pro programa. 
		//o oposto do dto, que e pra pega do programa e manda pro usuario;
		Topico topico = topicoForm.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriCompBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesTopicoDTO> detalhar(@PathVariable("id") Long id) { // com o @PathVariable("id") especificando a variavel 
		//q pegara do path nao preciso colocar o parametro com o msm nome da variavel passada na url (no caso, o id)
		Optional<Topico> option = topicoRepository.findById(id);
		if(option.isPresent()) {
			return ResponseEntity.ok( new DetalhesTopicoDTO(option.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, 
			@RequestBody @Valid AtualizacaoTopicoForm topicoForm){
		
		Optional<Topico> optional = topicoRepository.findById(id);
		if(optional.isPresent()) {
			Topico topico = topicoForm.atualizar( id,  topicoRepository );
			return ResponseEntity.ok(new TopicoDTO(topico));
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletar(@PathVariable Long id){

		Optional<Topico> optional = topicoRepository.findById(id);
		if(optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();		
		}
		return ResponseEntity.notFound().build();
		

		
	}
}
