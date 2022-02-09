package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.CidadeRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.CidadeResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.CidadeJaCadastradoException;
import br.com.revistainfoco.revista.errors.exceptions.CidadeNaoEncontradoException;
import br.com.revistainfoco.revista.repository.CidadeRepository;
import org.assertj.core.api.Assertions;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CidadeServiceTest {
    @Mock
    private CidadeRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CidadeService service;

    @Test
    public void DadoUmaCidadeQuandoTentarSalvarNoBancoDeDadosEntaoDeveRetorarOCidadeSalvo() {
        CidadeRequestDTO cidadeRequestDTOMock = new CidadeRequestDTO("Jaguaribe");
        CidadeResponseDTO cidadeCadastradoResponseDTOMock = new CidadeResponseDTO(1L, "Jaguaribe");
        Cidade cidadeMock = new Cidade(null, "Jaguaribe", new Estado());
        Cidade cidadeCadastradoMock = new Cidade(1L, "Jaguaribe", new Estado());

        when(modelMapper.map(cidadeRequestDTOMock, Cidade.class)).thenReturn(cidadeMock);
        when(modelMapper.map(cidadeCadastradoMock, CidadeResponseDTO.class)).thenReturn(cidadeCadastradoResponseDTOMock);

        when(repository.save(cidadeMock)).thenReturn(cidadeCadastradoMock);

        CidadeResponseDTO cidadeCriada = service.create(cidadeRequestDTOMock);

        Assertions.assertThat(cidadeCriada).isNotNull();
        Assertions.assertThat(cidadeCriada.getId()).isNotNull();
        Assertions.assertThat(cidadeCriada.getNome()).isNotNull();

        Assertions.assertThat(cidadeCriada.getId()).isEqualTo(1L);
        Assertions.assertThat(cidadeCriada.getNome()).isEqualTo("Jaguaribe");

    }

    @Test
    public void DadoUmaSolicitacaoParaLerTodosAsCidadesCadastradaEntaoDeveRetornarTodosOsCidadesCadastrados() {

        List<Cidade> cidadesCadastradosMock = asList(
                new Cidade(1L, "Jaguaribe", new Estado()),
                new Cidade(2L, "Ico", new Estado())
        );

        when(repository.findAll()).thenReturn(cidadesCadastradosMock);

        List<CidadeResponseDTO> cidadesCadastrados = service.readAll();

        Assertions.assertThat(cidadesCadastrados).isNotNull();
    }

    @Test
    public void DadoUmIdQuandoTentarLerUmCidadePeloIdDeveRetornarOCidade() {
        Cidade cidadeMock = new Cidade(1L, "Jaguaribe", new Estado());
        CidadeResponseDTO cidadeResponseDTOMock = new CidadeResponseDTO(1L, "Jaguaribe");

        when(repository.findById(1L)).thenReturn(Optional.of(cidadeMock));

        when(modelMapper.map(cidadeMock, CidadeResponseDTO.class)).thenReturn(cidadeResponseDTOMock);
        CidadeResponseDTO cidadeCadastrado = service.readById(1L);

        Assertions.assertThat(cidadeCadastrado).isNotNull();
        Assertions.assertThat(cidadeCadastrado.getId()).isEqualTo(1L);
        Assertions.assertThat(cidadeCadastrado.getNome()).isEqualTo("Jaguaribe");
    }

    @Test
    public void DadoUmCidadeParaAtualizarQuandoTentarAtualizarOsDadosEntaoDeveRetornarOCidadeAtualizado() {
        Cidade cidadeComNomeErradoMock = new Cidade(1L, "Jaguaribe", new Estado());
        Cidade cidadeAtualizadoComNomeCorretoMock = new Cidade(1L, "Ceará", new Estado());
        CidadeRequestDTO cidadeComNomeErradoRequestDTOMock = new CidadeRequestDTO("cera");
        CidadeResponseDTO cidadeComNomeCorretoResponseMock = new CidadeResponseDTO(1L, "Jaguaribe");


        when(repository.findById(1L)).thenReturn(Optional.of(cidadeComNomeErradoMock));
        when(repository.save(cidadeComNomeErradoMock)).thenReturn(cidadeAtualizadoComNomeCorretoMock);
        when(modelMapper.map(cidadeAtualizadoComNomeCorretoMock, CidadeResponseDTO.class)).thenReturn(cidadeComNomeCorretoResponseMock);

        CidadeResponseDTO cidadeResponseDTO = service.update(1L, cidadeComNomeErradoRequestDTOMock);

        Assertions.assertThat(cidadeResponseDTO).isNotNull();
    }

    @Test
    public void DadoUmIdQuandoTentarDeletarOCidadeDeveExcluirDoBancoDeDados() {
        Cidade cidadeParaDeletar = new Cidade(1L, "Jaguaribe", new Estado());

        when(repository.findById(1L)).thenReturn(Optional.of(cidadeParaDeletar));

        assertDoesNotThrow(() -> service.delete(1L));
    }

    @Test
    public void DadoUmACidadeJaCadastradAQuandoTentarCadastrarNovamenteEntaoDeveLancarErro() {
        CidadeRequestDTO cidadeRequestDTO = new CidadeRequestDTO("Jaguaribe");

        when(repository.save(any())).thenThrow(CidadeJaCadastradoException.class);

        assertThrows(CidadeJaCadastradoException.class, () -> service.create(cidadeRequestDTO));
    }

    @Test
    public void DadoUmIdDeUmCidadeQueNaoEstaCadastradoQuandoTentarLerEntaoDeveLancarErro() {
        when(repository.findById(ArgumentMatchers.any())).thenThrow(CidadeNaoEncontradoException.class);

        assertThrows(CidadeNaoEncontradoException.class, () -> service.readById(1L));
    }

    @Test
    public void DadoUmIdDeUmCidadeQueNaoEstaCadastradoQuandoTentarAtualizarEntaoDeveLancarErro() {
        CidadeRequestDTO cidadeRequestDTO = new CidadeRequestDTO("Jaguaribe");

        when(repository.findById(ArgumentMatchers.any())).thenThrow(CidadeNaoEncontradoException.class);

        Cidade cidadeParaAtualizar = new Cidade(1L, "Jaguaribe", new Estado());

        assertThrows(CidadeNaoEncontradoException.class, () -> service.update(1L, cidadeRequestDTO));
    }

    @Test
    public void DadoUmIdDeUmCidadeQueNaoEstaCadastradoQuandoTentarExcluirEntaoDeveLancarErro() {
        when(repository.findById(ArgumentMatchers.any())).thenThrow(CidadeNaoEncontradoException.class);

        assertThrows(CidadeNaoEncontradoException.class, () -> service.delete(1L));
    }
}
