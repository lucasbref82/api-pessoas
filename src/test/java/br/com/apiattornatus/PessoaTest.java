package br.com.apiattornatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.apiattornatus.domain.exception.NotFoundException;
import br.com.apiattornatus.domain.model.Cidade;
import br.com.apiattornatus.domain.model.Endereco;
import br.com.apiattornatus.domain.model.Pessoa;
import br.com.apiattornatus.service.PessoaService;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PessoaService pessoaService;

	@Test
	public void testListarPessoas() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/pessoas").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testeBuscarPessoa() throws NotFoundException {
		Pessoa pessoa = pessoaService.buscar(Long.valueOf(1));

		assertEquals("Lucas Brenner", pessoa.getNome());

		assertEquals(LocalDate.of(1999, 11, 30), pessoa.getDataNascimento());

		assertNotNull(pessoa.getEnderecos());
	}

	@Test
	public void testCriarPessoa() {
		try {
			Pessoa pessoa = 
					Pessoa.builder()
					.nome("Pessoa Teste")
					.dataNascimento(LocalDate.of(2006, 5, 4))
					.enderecoPrincipal(new Endereco(3L, "39874-707", 72L, "Casa", null, new Cidade(2L, null)))
					.build();

			Pessoa pessoaCriada = pessoaService.salvar(pessoa);

			assertNotNull(pessoaCriada);

			assertEquals("Pessoa Teste", pessoaCriada.getNome());

			assertNotNull(pessoaCriada.getEnderecoPrincipal());

			assertEquals(LocalDate.of(2006, 5, 4), pessoaCriada.getDataNascimento());

			assertNotNull(pessoaCriada.getId());

			assertTrue(pessoaCriada.getId() > 0);

			assertEquals(pessoa.getNome(), pessoaCriada.getNome());

			assertEquals(pessoa.getDataNascimento(), pessoaCriada.getDataNascimento());
		} catch (Exception e) {
			fail("Erro ao criar pessoa: " + e.getMessage());
		}
	}
}
