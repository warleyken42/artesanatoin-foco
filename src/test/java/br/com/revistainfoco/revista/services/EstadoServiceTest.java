package br.com.revistainfoco.revista.services;

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

    @InjectMocks
    private EstadoService service;

    @Test
    public void DadoUmEstadoQuandoTentarSalvarNoBancoDeDadosEntaoDeveRetorarOEstadoSalvo() {
        Estado estadoMock = new Estado(null, "Sao Paulo", "SP");

        Estado estadoCadastradoMock = new Estado(new BigInteger("1"), "Sao Paulo", "SP");

        when(repository.save(estadoMock)).thenReturn(estadoCadastradoMock);

        Estado estadoCriado = service.create(estadoMock);

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

        List<Estado> estadosCadastrados = service.readAll();

        Assertions.assertThat(estadosCadastrados).isNotNull();
    }

    @Test
    public void DadoUmIdQuandoTentarLerUmEstadoPeloIdDeveRetornarOEstado() {
        Estado estado = new Estado(new BigInteger("1"), "Ceará", "CE");

        when(repository.findById(new BigInteger("1"))).thenReturn(Optional.of(estado));

        Estado estadoCadastrado = service.readById(new BigInteger("1"));

        Assertions.assertThat(estadoCadastrado).isNotNull();
        Assertions.assertThat(estadoCadastrado.getUf()).isEqualTo("CE");
        Assertions.assertThat(estadoCadastrado.getId()).isEqualTo(new BigInteger("1"));
        Assertions.assertThat(estadoCadastrado.getNome()).isEqualTo("Ceará");
    }

    @Test
    public void DadoUmEstadoParaAtualizarQuandoTentarAtualizarOsDadosEntaoDeveRetornarOEstadoAtualizado() {
        Estado estadoMock = new Estado(new BigInteger("1"), "cera", "SP");

        Estado estadoAtualizadoMock = new Estado(new BigInteger("1"), "Ceará", "CE");

        when(repository.findById(new BigInteger("1"))).thenReturn(Optional.of(estadoMock));
        when(repository.save(estadoMock)).thenReturn(estadoAtualizadoMock);

        Estado estado = service.update(new BigInteger("1"), estadoMock);

        Assertions.assertThat(estado).isNotNull();
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

        when(repository.save(any())).thenThrow(EstadoJaCadastradoException.class);

        assertThrows(EstadoJaCadastradoException.class, () -> service.create(estadoDuplicado));
    }

    @Test
    public void DadoUmIdDeUmEstadoQueNaoEstaCadastradoQuandoTentarLerEntaoDeveLancarErro() {
        when(repository.findById(any())).thenThrow(EstadoNaoEncontradoException.class);

        assertThrows(EstadoNaoEncontradoException.class, () -> service.readById(new BigInteger("1")));
    }

    @Test
    public void DadoUmIdDeUmEstadoQueNaoEstaCadastradoQuandoTentarAtualizarEntaoDeveLancarErro() {
        when(repository.findById(any())).thenThrow(EstadoNaoEncontradoException.class);

        Estado estadoParaAtualizar = new Estado(new BigInteger("1"), "cera", "SP");

        assertThrows(EstadoNaoEncontradoException.class, () -> service.update(new BigInteger("1"), estadoParaAtualizar));
    }

    @Test
    public void DadoUmIdDeUmEstadoQueNaoEstaCadastradoQuandoTentarExcluirEntaoDeveLancarErro() {
        when(repository.findById(any())).thenThrow(EstadoNaoEncontradoException.class);

        assertThrows(EstadoNaoEncontradoException.class, () -> service.delete(new BigInteger("1")));
    }
}
