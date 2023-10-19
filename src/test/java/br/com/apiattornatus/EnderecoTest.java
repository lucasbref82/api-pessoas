package br.com.apiattornatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.apiattornatus.controller.EnderecoController;
import br.com.apiattornatus.domain.exception.NotFoundException;
import br.com.apiattornatus.domain.model.Endereco;
import br.com.apiattornatus.domain.model.Pessoa;

@SpringBootTest
@AutoConfigureMockMvc
public class EnderecoTest {

    @Autowired
    private EnderecoController enderecoController;



    @Test
    public void testCriarEnderecoParaPessoa() throws NotFoundException {
        Endereco endereco = 
        		Endereco.builder()
        		.cep("12345-678")
        		.numero(42l)
        		.logradouro("Rua de Teste")
        		.build();

        ResponseEntity<?> responseEntity = enderecoController.criarEnderecoParaPessoa(1L, endereco);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        
        assertTrue(responseEntity.getBody() instanceof Endereco);
        Endereco enderecoCriado = (Endereco) responseEntity.getBody();
        assertEquals("12345-678", enderecoCriado.getCep());
        assertEquals(42, enderecoCriado.getNumero());
        assertEquals("Rua de Teste", enderecoCriado.getLogradouro());
    }
    
    @Test
    public void testBuscarEnderecosParaPessoa() throws NotFoundException {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);

        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(new Endereco());
        enderecos.add(new Endereco());

        ResponseEntity<?> responseEntity = enderecoController.buscarEnderecosParaPessoa(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertTrue(responseEntity.getBody() instanceof List);
        List<Endereco> enderecosRetornados = (List<Endereco>) responseEntity.getBody();

        assertEquals(1, enderecosRetornados.size());
    }
}
