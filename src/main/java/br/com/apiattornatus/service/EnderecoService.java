package br.com.apiattornatus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apiattornatus.domain.exception.NotFoundException;
import br.com.apiattornatus.domain.model.Endereco;
import br.com.apiattornatus.domain.model.Pessoa;
import br.com.apiattornatus.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PessoaService pessoaService;

	public Endereco criarEnderecoParaPessoa(Long pessoaId, Endereco endereco) throws NotFoundException {
		Pessoa pessoa = pessoaService.buscarOuFalhar(pessoaId);
		endereco.setPessoa(pessoa);
		Endereco novoEndereco = enderecoRepository.save(endereco);
		return novoEndereco;
	}
	
	public List<Endereco> listarEnderecoPorPessoa(Long pessoaId) throws NotFoundException {
		pessoaService.buscarOuFalhar(pessoaId);
		return enderecoRepository.findAllByPessoaId(pessoaId);
	}
	

}