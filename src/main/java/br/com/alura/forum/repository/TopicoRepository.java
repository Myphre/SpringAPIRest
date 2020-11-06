package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{

	List<Topico> findByCursoNome(String nomeCurso); // findBy + nome da entidade relacionada ou de algum atributo da tabela;
	// pode-se fazer tb findByCurso_Nome , para indicar que Curso é uma tabela e Nome um atributo da tabela Curso
	// findByCursoCategoriaNome -> Curso se relaciona com Categoria que pegara o atributo nome
	
	
	/*
	 * criar o proprio metodo:
	 @Query("query baseada em jpql "
	 nome da funcao... List<Topico> encontraPorCursoNome(@Param("nomeCurso") String nomeCurso); 
	 */

}
