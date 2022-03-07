package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.entity.Contato;
import br.com.revistainfoco.revista.repository.ContatoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContatoServiceTest {

    @Mock
    private ContatoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ContatoService service;

    private Contato contato;
    private Contato contatoCadastrado;

    @BeforeEach
    void beforeEach() {
        contato = new Contato(null, "André", "Lucena", "11951482366", "andre@mail.com");
        contatoCadastrado = new Contato(1L,  "André", "Lucena", "11951482366", "andre@mail.com");
    }

    @Test
    @DisplayName("Dado um contato quando tentar salvar o contato então deve salvar com sucesso e retornar o contato salvo")
    void DadoUmContatoQuandoTentarSalvarOContatoEntaoDeveSalvarERetornarOSDadosDoContatoSalvo() {

        when(repository.save(contato)).thenReturn(contatoCadastrado);

        Contato contatoSalvo = service.create(contato);

        Assertions.assertThat(contatoSalvo).isNotNull();
    }

    @Test
    @DisplayName("Dado uma solicitação para consultar todos os contatos salvos então deve retornar todos os contatos salvos")
    void DadoUmSolicitacaoParaConsultarTodosOsContatosSalvosEntaoDeveRetornarTodosOsContatosSalvos() {

        List<Contato> contatos = asList(
                new Contato(1L, "André", "Farias", "11815237866", "andre@mail.com"),
                new Contato(2L, "Mathias", "de Souza", "11815237833", "mds@mail.com")
        );

        when(repository.findAll()).thenReturn(contatos);

        List<Contato> contatosSalvos = service.findAll();

        Assertions.assertThat(contatosSalvos)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    @DisplayName("Dado uma solicitação para consultar um contato pelo seu id então deve retornar os dados do contato")
    void DadoUmaSolicitacaoParaConsultarUmContatoPeloSeuIdEntaoDeveRetornarOsDadosDoContato() {

        when(repository.findById(1L)).thenReturn(Optional.of(contatoCadastrado));

        Contato contatosSalvos = service.findById(1L);

        Assertions.assertThat(contatosSalvos).isNotNull();
    }

    @Test
    @DisplayName("Dado uma solicitação para consultar um contato pelo seu celular então deve retornar os dados do contato")
    void DadoUmaSolicitacaoParaConsultarUmContatoPeloSeuCelularEntaoDeveRetornarOsDadosDoContato() {

        when(repository.findByCelular("11951482366")).thenReturn(Optional.of(contatoCadastrado));

        Optional<Contato> contatosSalvos = service.findByCelular("11951482366");

        Assertions.assertThat(contatosSalvos).isNotNull();
    }

    @Test
    @DisplayName("Dado uma solicitação para atualizar os dados de um contato então deve atualizar os dados com sucesso e retornar o ontato com os dados atualizados")
    void DadoUmaSolicitacaoParaAtualizarOsDadosDeUmContatoEntaoDeveAtualizarOsDadosComSucessoERetornarOsDadosAtualizados() {
        Contato contatoComNovosDados = new Contato(1L, "Ana", "Maria", "1167452233", "ana@mail.com");

        when(repository.findById(1L)).thenReturn(Optional.of(contatoCadastrado));
        when(repository.save(contatoComNovosDados)).thenReturn(contatoComNovosDados);

        Contato contatoAtualizado = service.update(1L, contatoComNovosDados);

        Assertions.assertThat(contatoAtualizado).isNotNull();
    }
}