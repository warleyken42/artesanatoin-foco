package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.EstadoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.EstadoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.EstadoJaCadastradoException;
import br.com.revistainfoco.revista.errors.exceptions.EstadoNaoEncontradoException;
import br.com.revistainfoco.revista.repository.EstadoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstadoServiceTest {

    @Mock
    private EstadoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EstadoService service;

    @Test
    public void DadoUmEstadoQuandoTentarSalvarNoBancoDeDadosEntaoDeveRetorarOEstadoSalvo() {
        EstadoRequestDTO estadoRequestDTOMock = new EstadoRequestDTO("Sao Paulo", "SP");
        EstadoResponseDTO estadoCadastradoResponseDTOMock = new EstadoResponseDTO(new BigInteger("1"), "Sao Paulo", "SP");
        Estado estadoMock = new Estado(null, "São Paulo", "SP");
        Estado estadoCadastradoMock = new Estado(new BigInteger("1"), "São Paulo", "SP");

        when(modelMapper.map(estadoRequestDTOMock, Estado.class)).thenReturn(estadoMock);
        when(modelMapper.map(estadoCadastradoMock, EstadoResponseDTO.class)).thenReturn(estadoCadastradoResponseDTOMock);

        when(repository.save(estadoMock)).thenReturn(estadoCadastradoMock);

        EstadoResponseDTO estadoCriado = service.create(estadoRequestDTOMock);

        Assertions.assertThat(estadoCriado).isNotNull();
        Assertions.assertThat(estadoCriado.getId()).isNotNull();
        Assertions.assertThat(estadoCriado.getUf()).isNotNull();
        Assertions.assertThat(estadoCriado.getNome()).isNotNull();

        Assertions.assertThat(estadoCriado.getId()).isEqualTo(new BigInteger("1"));
        Assertions.assertThat(estadoCriado.getNome()).isEqualTo("Sao Paulo");
        Assertions.assertThat(estadoCriado.getUf()).isEqualTo("SP");

    }

    @Test
    public void DadoUmaSolicitacaoParaLerTodosOsEstadosCadastradoEntaoDeveRetornarTodosOsEstadosCadastrados() {

        List<Estado> estadosCadastradosMock = asList(
                new Estado(new BigInteger("1"), "Ceará", "CE"),
                new Estado(new BigInteger("2"), "São Paulo", "SP"),
                new Estado(new BigInteger("3"), "Rio de Janeiro", "RJ")
        );

        when(repository.findAll()).thenReturn(estadosCadastradosMock);

        List<EstadoResponseDTO> estadosCadastrados = service.readAll();

        Assertions.assertThat(estadosCadastrados).isNotNull();
    }

    @Test
    public void DadoUmIdQuandoTentarLerUmEstadoPeloIdDeveRetornarOEstado() {
        Estado estadoMock = new Estado(new BigInteger("1"), "Ceará", "CE");
        EstadoResponseDTO estadoResponseDTOMock = new EstadoResponseDTO(new BigInteger("1"), "Ceará", "CE");

        when(repository.findById(new BigInteger("1"))).thenReturn(Optional.of(estadoMock));

        when(modelMapper.map(estadoMock, EstadoResponseDTO.class)).thenReturn(estadoResponseDTOMock);
        EstadoResponseDTO estadoCadastrado = service.readById(new BigInteger("1"));

        Assertions.assertThat(estadoCadastrado).isNotNull();
        Assertions.assertThat(estadoCadastrado.getUf()).isEqualTo("CE");
        Assertions.assertThat(estadoCadastrado.getId()).isEqualTo(new BigInteger("1"));
        Assertions.assertThat(estadoCadastrado.getNome()).isEqualTo("Ceará");
    }

    @Test
    public void DadoUmEstadoParaAtualizarQuandoTentarAtualizarOsDadosEntaoDeveRetornarOEstadoAtualizado() {
        Estado estadoComNomeErradoMock = new Estado(new BigInteger("1"), "cera", "SP");
        Estado estadoAtualizadoComNomeCorretoMock = new Estado(new BigInteger("1"), "Ceará", "CE");
        EstadoRequestDTO estadoComNomeErradoRequestDTOMock = new EstadoRequestDTO( "cera", "SP");
        EstadoResponseDTO estadoComNomeCorretoResponseMock = new EstadoResponseDTO(new BigInteger("1"), "Ceará", "CE");


        when(repository.findById(new BigInteger("1"))).thenReturn(Optional.of(estadoComNomeErradoMock));
        when(repository.save(estadoComNomeErradoMock)).thenReturn(estadoAtualizadoComNomeCorretoMock);
        when(modelMapper.map(estadoAtualizadoComNomeCorretoMock, EstadoResponseDTO.class)).thenReturn(estadoComNomeCorretoResponseMock);

        EstadoResponseDTO estadoResponseDTO = service.update(new BigInteger("1"), estadoComNomeErradoRequestDTOMock);

        Assertions.assertThat(estadoResponseDTO).isNotNull();
    }

    @Test
    public void DadoUmIdQuandoTentarDeletarOEstadoDeveExcluirDoBancoDeDados() {
        Estado estadoParaDeletar = new Estado(new BigInteger("1"), "Ceará", "CE");

        when(repository.findById(new BigInteger("1"))).thenReturn(Optional.of(estadoParaDeletar));

        assertDoesNotThrow(() -> service.delete(new BigInteger("1")));
    }

    @Test
    public void DadoUmEstadoJaCadastradoQuandoTentarCadastrarNovamenteEntaoDeveLancarErro() {
        Estado estadoDuplicado = new Estado(new BigInteger("1"), "Ceará", "CE");
        EstadoRequestDTO estadoRequestDTO = new EstadoRequestDTO( "Ceará", "CE");

        when(repository.save(any())).thenThrow(EstadoJaCadastradoException.class);

        assertThrows(EstadoJaCadastradoException.class, () -> service.create(estadoRequestDTO));
    }

    @Test
    public void DadoUmIdDeUmEstadoQueNaoEstaCadastradoQuandoTentarLerEntaoDeveLancarErro() {
        when(repository.findById(any())).thenThrow(EstadoNaoEncontradoException.class);

        assertThrows(EstadoNaoEncontradoException.class, () -> service.readById(new BigInteger("1")));
    }

    @Test
    public void DadoUmIdDeUmEstadoQueNaoEstaCadastradoQuandoTentarAtualizarEntaoDeveLancarErro() {
        EstadoRequestDTO estadoRequestDTO = new EstadoRequestDTO("cera", "SP");

        when(repository.findById(any())).thenThrow(EstadoNaoEncontradoException.class);

        Estado estadoParaAtualizar = new Estado(new BigInteger("1"), "cera", "SP");

        assertThrows(EstadoNaoEncontradoException.class, () -> service.update(new BigInteger("1"), estadoRequestDTO));
    }

    @Test
    public void DadoUmIdDeUmEstadoQueNaoEstaCadastradoQuandoTentarExcluirEntaoDeveLancarErro() {
        when(repository.findById(any())).thenThrow(EstadoNaoEncontradoException.class);

        assertThrows(EstadoNaoEncontradoException.class, () -> service.delete(new BigInteger("1")));
    }
}
