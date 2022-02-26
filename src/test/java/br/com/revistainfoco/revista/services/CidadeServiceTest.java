package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.CidadeRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.CidadeUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.EstadoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.EstadoUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.CidadeResponseDTO;
import br.com.revistainfoco.revista.domain.dto.response.EstadoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.CidadeJaCadastradaException;
import br.com.revistainfoco.revista.errors.exceptions.CidadeNaoEncontradaException;
import br.com.revistainfoco.revista.repository.CidadeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CidadeServiceTest {

    @Mock
    private CidadeRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CidadeService service;

    private Estado estadoMock;
    private Cidade cidadeMock;
    private CidadeRequestDTO cidadeRequestDTOMock;
    private CidadeResponseDTO cidadeResponseDTOMock;
    private CidadeUpdateRequestDTO cidadeUpdateRequestDTOMock;

    @BeforeEach
    void beaforeEach() {
        estadoMock = new Estado(1L, "Ceará", "CE");
        cidadeMock = new Cidade(1L, "Jaguaribe", estadoMock);

        EstadoRequestDTO estadoRequestDTOMock = new EstadoRequestDTO("Ceará", "CE");
        cidadeRequestDTOMock = new CidadeRequestDTO("Ceará");

        EstadoResponseDTO estadoResponseDTOMock = new EstadoResponseDTO(1L, "Ceará", "CE");
        cidadeResponseDTOMock = new CidadeResponseDTO(1L, "Jaguaribe");

        EstadoUpdateRequestDTO estadoUpdateRequestDTOMock = new EstadoUpdateRequestDTO(1L, "Ceará", "CE");
        cidadeUpdateRequestDTOMock = new CidadeUpdateRequestDTO(1L, "Jaguaribe");
    }


    @Test
    @DisplayName(value = "Dado uma cidade quando tentar salvar no banco de dados então deve retornar a cidade salva")
    void DadoUmaCidadeQuandoTentarSalvarNoBancoDeDadosEntaoDeveRetorarACidadeSalva() {

        when(modelMapper.map(cidadeRequestDTOMock, Cidade.class)).thenReturn(cidadeMock);
        when(modelMapper.map(cidadeMock, CidadeResponseDTO.class)).thenReturn(cidadeResponseDTOMock);

        when(repository.save(cidadeMock)).thenReturn(cidadeMock);

        CidadeResponseDTO cidadeCriada = service.create(cidadeRequestDTOMock);

        Assertions.assertThat(cidadeCriada).isNotNull();
        Assertions.assertThat(cidadeCriada.getId()).isNotNull();
        Assertions.assertThat(cidadeCriada.getNome()).isNotNull();

        Assertions.assertThat(cidadeCriada.getId()).isEqualTo(1L);
        Assertions.assertThat(cidadeCriada.getNome()).isEqualTo("Jaguaribe");

    }

    @Test
    @DisplayName(value = "Dado uma solicitação para ler todas as cidades cadastradas então deve retornar todas as cidades cadastradas")
    void DadoUmaSolicitacaoParaLerTodasAsCidadesCadastradaEntaoDeveRetornarTodasAsCidadesCadastradas() {

        List<Cidade> cidadesCadastradosMock = asList(
                new Cidade(1L, "Jaguaribe", new Estado()),
                new Cidade(2L, "Ico", new Estado())
        );

        when(repository.findAll()).thenReturn(cidadesCadastradosMock);
        when(modelMapper.map(cidadeMock, CidadeResponseDTO.class)).thenReturn(cidadeResponseDTOMock);

        List<CidadeResponseDTO> cidadesCadastradas = service.readAll();

        CidadeResponseDTO cidadeCadastrada = cidadesCadastradas.get(0);

        Assertions.assertThat(cidadesCadastradas)
                .isNotNull()
                .isNotEmpty()
                .contains(cidadeCadastrada)
                .hasSize(2);

        Assertions.assertThat(cidadeCadastrada.getNome()).isEqualTo("Jaguaribe");
        Assertions.assertThat(cidadeCadastrada.getId()).isEqualTo(1L);

        verify(repository, times(1)).findAll();


    }

    @Test
    @DisplayName(value = "Dado um id quando tentar ler uma cidade pelo id então deve retornar a cidade")
    void DadoUmIdQuandoTentarLerUmaCidadePeloIdDeveRetornarACidade() {

        when(repository.findById(1L)).thenReturn(Optional.of(cidadeMock));

        when(modelMapper.map(cidadeMock, CidadeResponseDTO.class)).thenReturn(cidadeResponseDTOMock);

        CidadeResponseDTO cidadeCadastrado = service.readById(1L);

        Assertions.assertThat(cidadeCadastrado).isNotNull();
        Assertions.assertThat(cidadeCadastrado.getId()).isEqualTo(1L);
        Assertions.assertThat(cidadeCadastrado.getNome()).isEqualTo("Jaguaribe");
    }

    @Test
    @DisplayName("Dado uma cidade para atualizar quando tentar atualizar os dados então deve retornar a cidade com os dados atualizados")
    void DadoUmACidadeParaAtualizarQuandoTentarAtualizarOsDadosEntaoDeveRetornarACidadeComOsDadosAtualizados() {

        Cidade cidadeComNomeErradoMock = new Cidade(1L, "jaguaribi", new Estado(1L, "Sear", "SE"));

        when(repository.findById(1L)).thenReturn(Optional.of(cidadeComNomeErradoMock));
        when(repository.save(cidadeComNomeErradoMock)).thenReturn(cidadeMock);
        when(modelMapper.map(cidadeMock, CidadeResponseDTO.class)).thenReturn(cidadeResponseDTOMock);

        CidadeResponseDTO cidadeResponseDTO = service.update(1L, cidadeUpdateRequestDTOMock);

        Assertions.assertThat(cidadeResponseDTO).isNotNull();
        Assertions.assertThat(cidadeResponseDTO.getNome()).isEqualTo("Jaguaribe");
        Assertions.assertThat(cidadeResponseDTO.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName(value = "Dado um id quando tentar deletar uma cidade então deve excluir a cidade do banco de dados")
    void DadoUmIdQuandoTentarDeletarUmaCidadeEntaoDeveExcluirACidadeDoBancoDeDados() {

        when(repository.findById(1L)).thenReturn(Optional.of(cidadeMock));

        assertDoesNotThrow(() -> service.delete(1L));
    }

    @Test
    @DisplayName(value = "Dado uma cidade já cadastrada quando tentar cadastrar novamente então deve lançar a exception CidadeJaCadastradaException")
    void DadoUmACidadeJaCadastradaQuandoTentarCadastrarNovamenteEntaoDeveLancarAExceptionCidadeJaCadastradaException() {

        when(repository.save(any())).thenThrow(CidadeJaCadastradaException.class);

        assertThrows(CidadeJaCadastradaException.class, () -> service.create(cidadeRequestDTOMock));
    }

    @Test
    @DisplayName(value = "Dado um id de uma cidade que não está cadastrada quando tentar recuperar a cidade pelo id então deve lançar a exception CidadeNaoEncontradaException")
    void DadoUmIdDeUmaCidadeQueNaoEstaCadastradaQuandoTentarRecuperarACidadePeloIdEntaoDeveLancarAExceptionCidadeNaoEncontradaException() {

        when(repository.findById(ArgumentMatchers.any())).thenThrow(CidadeNaoEncontradaException.class);

        assertThrows(CidadeNaoEncontradaException.class, () -> service.readById(1L));
    }

    @Test
    @DisplayName(value = "Dado um id de uma cidade que não está cadastrada quando tentar atualizar então deve lançar a exception CidadeNaoEncontradaException")
    void DadoUmIdDeUmaCidadeQueNaoEstaCadastradaQuandoTentarAtualizarEntaoDeveLancarAExceptionCidadeNaoEncontradaException() {

        when(repository.findById(ArgumentMatchers.any())).thenThrow(CidadeNaoEncontradaException.class);

        assertThrows(CidadeNaoEncontradaException.class, () -> service.update(1L, cidadeUpdateRequestDTOMock));
    }

    @Test
    @DisplayName(value = "Dado um id de uma cidade que não está cadastrada quando tentar excluir então deve lançar a exception CidadeNaoEncontradaException")
    void DadoUmIdDeUmaCidadeQueNaoEstaCadastradaQuandoTentarExcluirEntaoDeveLancarAExceptionCidadeNaoEncontradaException() {
        when(repository.findById(ArgumentMatchers.any())).thenThrow(CidadeNaoEncontradaException.class);

        assertThrows(CidadeNaoEncontradaException.class, () -> service.delete(1L));
    }
}
