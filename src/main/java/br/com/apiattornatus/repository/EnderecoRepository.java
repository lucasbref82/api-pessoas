package br.com.apiattornatus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.apiattornatus.domain.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
	@Query("SELECT e FROM Endereco e WHERE e.pessoa.id = :pessoaId")
	List<Endereco> findAllByPessoaId(@Param("pessoaId") Long pessoaId);

}
