package br.com.apiattornatus.service;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apiattornatus.domain.exception.NotFoundException;
import br.com.apiattornatus.domain.model.Endereco;
import br.com.apiattornatus.domain.model.Pessoa;
import br.com.apiattornatus.messages.Messages;
import br.com.apiattornatus.repository.EnderecoRepository;
import br.com.apiattornatus.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Pessoa salvar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}

	public Pessoa buscar(Long id) throws NotFoundException {
		Pessoa pessoa = buscarOuFalhar(id);
		return pessoa;
	}

	public Pessoa buscarOuFalhar(Long id) throws NotFoundException {
		Pessoa pessoa = pessoaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(MessageFormat.format(Messages.PESSOA_NAO_ENCONTRADA, id)));
		return pessoa;
	}

	public void setEnderecoPrincipal(Long pessoaId, Long enderecoId) throws NotFoundException {
		Pessoa pessoa = buscarOuFalhar(pessoaId);
		Endereco endereco = enderecoRepository.findById(enderecoId)
				.orElseThrow(() -> new NotFoundException(MessageFormat.format(Messages.ENDERECO_NAO_ENCONTRADO, enderecoId)));
		pessoa.setEnderecoPrincipal(endereco);
		pessoaRepository.save(pessoa);
	}
}
