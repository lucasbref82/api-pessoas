package br.com.apiattornatus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apiattornatus.domain.exception.NotFoundException;
import br.com.apiattornatus.domain.model.Pessoa;
import br.com.apiattornatus.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Pessoa pessoa) {
		try {
			Pessoa pessoaSalva = pessoaService.salvar(pessoa);
			return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Pessoa>> listar() {
		return ResponseEntity.ok(pessoaService.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> Buscar(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(pessoaService.buscar(id));
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
    @PutMapping("/{pessoaId}/endereco-principal/{enderecoId}")
    public ResponseEntity<?> setEnderecoPrincipal(@PathVariable Long pessoaId, @PathVariable Long enderecoId) {
        try {
			pessoaService.setEnderecoPrincipal(pessoaId, enderecoId);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
        return ResponseEntity.ok("Endereço principal atualizado com sucesso.");
    }

}
