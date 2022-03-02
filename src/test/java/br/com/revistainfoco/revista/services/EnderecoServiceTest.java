package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.domain.entity.Endereco;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.repository.EnderecoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository repository;

    @Mock
    private CidadeService cidadeService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EnderecoService enderecoService;

    @Test
    @DisplayName(value = "Dado um endereco quando tentar cadastrar entao deve retornar os dados do endeerco cadastrado")
    void DadoUmEnderecoQuandoTentarCadastrarEntaoDeveRetornarOsDadosDoEndeercoCadastrado() {
        Estado estadoCadastrado = new Estado(1L, "São Paulo", "SP");
        Cidade cidadeCadastrada = new Cidade(1L, "Guarulhos", estadoCadastrado);
        Endereco endereco = new Endereco(
                null,
                "Rua Cidade Lion",
                cidadeCadastrada,
                "07094190",
                "184",
                "Apto 32",
                "Jardim Anny"
        );

        Endereco enderecoCriado = new Endereco(
                1L,
                "Rua Cidade Lion",
                cidadeCadastrada,
                "07094190",
                "184",
                "Apto 32",
                "Jardim Anny"
        );

        when(repository.save(endereco)).thenReturn(enderecoCriado);

        Endereco enderecoCadastrado = enderecoService.create(endereco);

        Assertions.assertThat(enderecoCadastrado).isNotNull();
        Assertions.assertThat(enderecoCadastrado.getId()).isEqualTo(1L);
        Assertions.assertThat(enderecoCadastrado.getLogradouro()).isEqualTo("Rua Cidade Lion");

        Assertions.assertThat(enderecoCadastrado.getCidade().getId()).isEqualTo(1L);
        Assertions.assertThat(enderecoCadastrado.getCidade().getNome()).isEqualTo("Guarulhos");

        Assertions.assertThat(enderecoCadastrado.getCidade().getEstado().getId()).isEqualTo(1L);
        Assertions.assertThat(enderecoCadastrado.getCidade().getEstado().getNome()).isEqualTo("São Paulo");
        Assertions.assertThat(enderecoCadastrado.getCidade().getEstado().getUf()).isEqualTo("SP");

        Assertions.assertThat(enderecoCadastrado.getCep()).isEqualTo("07094190");
        Assertions.assertThat(enderecoCadastrado.getNumero()).isEqualTo("184");
        Assertions.assertThat(enderecoCadastrado.getComplemento()).isEqualTo("Apto 32");
        Assertions.assertThat(enderecoCadastrado.getBairro()).isEqualTo("Jardim Anny");
    }
}