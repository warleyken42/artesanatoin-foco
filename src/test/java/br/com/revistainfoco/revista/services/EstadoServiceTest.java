package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.EstadoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.EstadoUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.EstadoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.EstadoJaCadastradoException;
import br.com.revistainfoco.revista.errors.exceptions.EstadoNaoEncontradoException;
import br.com.revistainfoco.revista.repository.EstadoRepository;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadoServiceTest {

    @Mock
    private EstadoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EstadoService service;

    private Estado estadoMock;
    private EstadoRequestDTO estadoRequestDTOMock;
    private EstadoResponseDTO estadoResponseDTOMock;
    private EstadoUpdateRequestDTO estadoUpdateRequestDTO;

    @BeforeEach
    void beforeEach() {
        estadoMock = new Estado(1L, "Ceará", "CE");
        estadoRequestDTOMock = new EstadoRequestDTO("Ceará", "CE");
        estadoResponseDTOMock = new EstadoResponseDTO(1L, "Ceará", "CE");
        estadoUpdateRequestDTO = new EstadoUpdateRequestDTO(1L, "Ceará", "CE");
    }

    @Test
    @DisplayName(value = "Dado um estado quando tentar salvar no banco de dados então deve retornar o estado salvo")
    void DadoUmEstadoQuandoTentarSalvarNoBancoDeDadosEntaoDeveRetorarOEstadoSalvo() {

        when(modelMapper.map(estadoRequestDTOMock, Estado.class)).thenReturn(estadoMock);
        when(modelMapper.map(estadoMock, EstadoResponseDTO.class)).thenReturn(estadoResponseDTOMock);
        when(repository.save(estadoMock)).thenReturn(estadoMock);

        EstadoResponseDTO estadoCriado = service.create(estadoRequestDTOMock);

        verify(repository, times(1)).save(estadoMock);
        verify(modelMapper, times(1)).map(estadoMock, EstadoResponseDTO.class);
        verify(modelMapper, times(1)).map(estadoRequestDTOMock, Estado.class);

        Assertions.assertThat(estadoCriado).isNotNull();
        Assertions.assertThat(estadoCriado.getId()).isNotNull();
        Assertions.assertThat(estadoCriado.getUf()).isNotNull();
        Assertions.assertThat(estadoCriado.getNome()).isNotNull();
        Assertions.assertThat(estadoCriado.getId()).isEqualTo(1L);
        Assertions.assertThat(estadoCriado.getNome()).isEqualTo("Ceará");
        Assertions.assertThat(estadoCriado.getUf()).isEqualTo("CE");

    }

    @Test
    @DisplayName(value = "Dado uma solicitação para ler todos os estados cadastrados então deve retornar todos os estados cadastrados")
    void DadoUmaSolicitacaoParaLerTodosOsEstadosCadastradoEntaoDeveRetornarTodosOsEstadosCadastrados() {

        List<Estado> estadosCadastradosMock = asList(
                new Estado(1L, "Ceará", "CE"),
                new Estado(2L, "São Paulo", "SP"),
                new Estado(3L, "Rio de Janeiro", "RJ")
        );

        EstadoResponseDTO estadoResponseDTO = new EstadoResponseDTO(2L, "São Paulo", "SP");


        when(modelMapper.map(estadoMock, EstadoResponseDTO.class)).thenReturn(estadoResponseDTO);
        when(repository.findAll()).thenReturn(estadosCadastradosMock);

        List<EstadoResponseDTO> estadosCadastrados = service.readAll();

        verify(repository, times(1)).findAll();
        verify(modelMapper, times(1)).map(estadoMock, EstadoResponseDTO.class);

        Assertions.assertThat(estadosCadastrados)
                .isNotNull()
                .hasSize(3)
                .contains(estadoResponseDTO);
    }

    @Test
    @DisplayName(value = "Dado um id quando tentar ler um estado pelo id então deve retornar o estado")
    void DadoUmIdQuandoTentarLerUmEstadoPeloIdDeveRetornarOEstado() {

        when(repository.findById(1L)).thenReturn(Optional.of(estadoMock));
        when(modelMapper.map(estadoMock, EstadoResponseDTO.class)).thenReturn(estadoResponseDTOMock);

        EstadoResponseDTO estadoCadastrado = service.readById(1L);

        verify(repository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(estadoMock, EstadoResponseDTO.class);

        Assertions.assertThat(estadoCadastrado).isNotNull();
        Assertions.assertThat(estadoCadastrado.getUf()).isEqualTo("CE");
        Assertions.assertThat(estadoCadastrado.getId()).isEqualTo(1L);
        Assertions.assertThat(estadoCadastrado.getNome()).isEqualTo("Ceará");
    }

    @Test
    @DisplayName("Dado um estado para atualizar quando tentar atualizar os dados então deve retornar o estado com os dados atualizados")
    void DadoUmEstadoParaAtualizarQuandoTentarAtualizarOsDadosEntaoDeveRetornarOEstadoComOsDadosAtualizados() {

        Estado estadoParaAtualizarMock = new Estado(2L, "Seará", "SE");
        EstadoUpdateRequestDTO estadoParaAtualizarDTOMock = new EstadoUpdateRequestDTO(2L, "Seará", "SE");


        when(repository.findById(1L)).thenReturn(Optional.of(estadoParaAtualizarMock));
        when(repository.save(estadoMock)).thenReturn(estadoMock);
        when(modelMapper.map(estadoMock, EstadoResponseDTO.class)).thenReturn(estadoResponseDTOMock);

        EstadoResponseDTO estadoResponseDTO = service.update(1L, estadoParaAtualizarDTOMock);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(estadoMock);
        verify(modelMapper, times(1)).map(estadoMock, EstadoResponseDTO.class);

        Assertions.assertThat(estadoResponseDTO).isNotNull();
        Assertions.assertThat(estadoResponseDTO.getId()).isEqualTo(1L);
        Assertions.assertThat(estadoResponseDTO.getNome()).isEqualTo("Ceará");
        Assertions.assertThat(estadoResponseDTO.getUf()).isEqualTo("CE");
    }

    @Test
    @DisplayName(value = "Dado um id quando tentar deletar um estado então deve excluir o estado do banco de dados")
    void DadoUmIdQuandoTentarDeletarUmEstadoEntaoDeveExcluirOEstadoDoBancoDeDados() {

        when(repository.findById(1L)).thenReturn(Optional.of(estadoMock));

        assertDoesNotThrow(() -> service.delete(1L));
    }

    @Test
    @DisplayName(value = "Dado um estado já cadastrado quando tentar cadastrar novamente então deve lançar a exception EstadoJaCadastradoException")
    void DadoUmEstadoJaCadastradoQuandoTentarCadastrarNovamenteEntaoDeveLancarAExceptionEstadoJaCadastradoException() {

        when(repository.save(any())).thenThrow(EstadoJaCadastradoException.class);

        assertThrows(EstadoJaCadastradoException.class, () -> service.create(estadoRequestDTOMock));
    }

    @Test
    @DisplayName(value = "Dado um id de um estado que não está cadastrado quando tentar recuperar o estado pelo id então deve lançar a exception EstadoNaoEncontradoException")
    void DadoUmIdDeUmEstadoQueNaoEstaCadastradoQuandoTentarRecuperarOEstadoPeloIdEntaoDeveLancarAExceptionEstadoNaoEncontradoException() {

        when(repository.findById(any())).thenThrow(EstadoNaoEncontradoException.class);

        assertThrows(EstadoNaoEncontradoException.class, () -> service.readById(1L));
    }

    @Test
    @DisplayName(value = "Dado um id de um estado que não está cadastrado quando tentar atualizar o estado então deve lançar a exception EstadoNaoEncontradoException")
    void DadoUmIdDeUmEstadoQueNaoEstaCadastradoQuandoTentarAtualizarOEstadoEntaoDeveLancarAExceptionEstadoNaoEncontradoException() {

        when(repository.findById(any())).thenThrow(EstadoNaoEncontradoException.class);

        assertThrows(EstadoNaoEncontradoException.class, () -> service.update(1L, estadoUpdateRequestDTO));
    }

    @Test
    @DisplayName(value = "Dado um id de um estado que não está cadastrado quando tentar excluir o estado então deve lançar a exception EstadoNaoEncontradoException")
    void DadoUmIdDeUmEstadoQueNaoEstaCadastradoQuandoTentarExcluirOEstadoEntaoDeveLancarAExceptionEstadoNaoEncontradoException() {

        when(repository.findById(any())).thenThrow(EstadoNaoEncontradoException.class);

        assertThrows(EstadoNaoEncontradoException.class, () -> service.delete(1L));
    }
}
