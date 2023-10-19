package br.com.apiattornatus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apiattornatus.domain.exception.NotFoundException;
import br.com.apiattornatus.domain.model.Endereco;
import br.com.apiattornatus.service.EnderecoService;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
	
	@Autowired
	private EnderecoService enderecoService;
	
    @PostMapping("/pessoa/{pessoaId}")
    public ResponseEntity<?> criarEnderecoParaPessoa(
        @PathVariable Long pessoaId,
        @RequestBody Endereco endereco) {
        Endereco novoEndereco;
		try {
			novoEndereco = enderecoService.criarEnderecoParaPessoa(pessoaId, endereco);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEndereco);
    }
    
    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<?> buscarEnderecosParaPessoa(@PathVariable Long pessoaId){
    	List<Endereco> endereco;
		try {
			endereco = enderecoService.listarEnderecoPorPessoa(pessoaId);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
    	return ResponseEntity.ok(endereco); 
    }

}
