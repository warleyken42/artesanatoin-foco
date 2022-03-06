package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.AnuncioRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.AnuncioUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.AnuncioResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Anuncio;
import br.com.revistainfoco.revista.errors.exceptions.AnuncioNaoEncontradoException;
import br.com.revistainfoco.revista.repository.AnuncioRepository;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnuncioServiceTest {

    @Mock
    private AnuncioRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AnuncioService service;

    private Anuncio anuncio;
    private Anuncio anuncioCadastrado;
    private AnuncioRequestDTO anuncioRequestDTO;


    @BeforeEach
    void beforeEach() {
        anuncio = new Anuncio(null, "A4", new BigDecimal("160.0"));
        anuncioCadastrado = new Anuncio(1L, "A4", new BigDecimal("160.0"));
    }

    @Test
    @DisplayName(value = "Dado um anuncio quando tentar cadastrar então deve cadastrar com sucesso e responder com os dados do anuncio cadastrado")
    void DadoUmAnuncioQuandoTentarCadastrarEntaoDeveCadastrarComSucessoEResponderComOsDadosDoAnuncioCadastrado() {
        when(repository.save(anuncio)).thenReturn(anuncioCadastrado);

        Anuncio anuncioCadastrado = service.create(anuncio);

        Assertions.assertThat(anuncioCadastrado).isNotNull();
        Assertions.assertThat(anuncioCadastrado.getValor()).isEqualTo(new BigDecimal("160.0"));
        Assertions.assertThat(anuncioCadastrado.getTamanho()).isEqualTo("A4");
    }

    @Test
    @DisplayName(value = "Dado uma solicitação para ler todos os anuncios cadastrados então deve retornar todos os anuncios cadastrados")
    void DadoUmaSolicitacaoParaLerTodosOsAnunciosCadastradosEntaoDeveRetornarTodosOsAnunciosCadastrados() {
        List<Anuncio> anuncios = asList(
                new Anuncio(1L, "A4", new BigDecimal("160.0")),
                new Anuncio(2L, "A5", new BigDecimal("90.0")),
                new Anuncio(3L, "A6", new BigDecimal("50.0"))
        );

        when(repository.findAll()).thenReturn(anuncios);

        List<Anuncio> anunciosCadastrados = service.findAll();

        Anuncio anuncio = anunciosCadastrados.get(0);

        Assertions.assertThat(anunciosCadastrados).isNotNull();
        Assertions.assertThat(anunciosCadastrados).isNotEmpty();
        Assertions.assertThat(anunciosCadastrados.size()).isEqualTo(3);

        Assertions.assertThat(anuncio.getValor()).isEqualTo(new BigDecimal("160.0"));
        Assertions.assertThat(anuncio.getTamanho()).isEqualTo("A4");
    }

    @Test
    @DisplayName(value = "Dado um id quando tentar ler um anuncio pelo id então deve retornar o anuncio")
    void DadoUmIdQuandoTentarLerUmAnuncioPeloIdEntaoDeveRetornarOAnuncio() {
        when(repository.findById(1L)).thenReturn(Optional.of(anuncioCadastrado));

        Anuncio anuncioCadastrado = service.findById(1L);

        Assertions.assertThat(anuncioCadastrado).isNotNull();
        Assertions.assertThat(anuncioCadastrado.getId()).isEqualTo(1L);
        Assertions.assertThat(anuncioCadastrado.getValor()).isEqualTo(new BigDecimal("160.0"));
        Assertions.assertThat(anuncioCadastrado.getTamanho()).isEqualTo("A4");
    }

    @Test
    @DisplayName(value = "Dado um anuncio para atualizar quando tentar atualizar os dados então deve retornar o anuncio com os dados atualizados")
    void DadoUmAnuncioParaAtualizarQuandoTentarAtualizarOsDadosEntaoDeveRetornarOAnuncioComOsDadosAtualizados() {
        Anuncio anuncioComNovosDados = new Anuncio(1L, "A4", new BigDecimal("160.0"));

        when(repository.findById(1L)).thenReturn(Optional.of(anuncioCadastrado));
        when(repository.save(anuncioCadastrado)).thenReturn(anuncioComNovosDados);

        Anuncio anuncioAtualizado = service.update(1L, anuncio);

        Assertions.assertThat(anuncioAtualizado).isNotNull();
        Assertions.assertThat(anuncioAtualizado.getValor()).isNotNull();
        Assertions.assertThat(anuncioAtualizado.getTamanho()).isNotNull();
        Assertions.assertThat(anuncioAtualizado.getValor()).isEqualTo(new BigDecimal("160.0"));
        Assertions.assertThat(anuncioAtualizado.getTamanho()).isEqualTo("A4");
        Assertions.assertThat(anuncioAtualizado.getId()).isEqualTo(1L);

    }

    @Test
    @DisplayName(value = "Dado um id quando tentar deletar um anuncio então deve excluir o anuncio do banco de dados")
    void DadoUmIdQuandoTentarDeletarUmAnuncioEntaoDeveExcluirOAnuncioDoBancoDeDados() {

        when(repository.findById(1L)).thenReturn(Optional.of(anuncioCadastrado));

        assertDoesNotThrow(() -> service.delete(1L));
    }

    @Test
    @DisplayName(value = "Dado um id de um anuncio que não está cadastrado quando tentar recuperar o anuncio pelo id então deve lançar a exception AnuncioNaoEncontradoException")
    void DadoUmIdDeUmAnuncioQueNaoEstaCadastradoQuandoTentarRecuperarOAnuncioPeloIdEntaoDeveLancarAExceptionAnuncioNaoEncontradoException() {

        when(repository.findById(ArgumentMatchers.any())).thenThrow(AnuncioNaoEncontradoException.class);

        assertThrows(AnuncioNaoEncontradoException.class, () -> service.findById(1L));
    }

    @Test
    @DisplayName(value = "Dado um id de um anuncio que não está cadastrado quando tentar atualizar o anuncio pelo id então deve lançar a exception AnuncioNaoEncontradoException")
    void DadoUmIdDeUmAnuncioQueNaoEstaCadastradoQuandoTentarAtualizarOAnuncioPeloIdEntaoDeveLancarAExceptionAnuncioNaoEncontradoException() {

        when(repository.findById(1L)).thenThrow(AnuncioNaoEncontradoException.class);

        assertThrows(AnuncioNaoEncontradoException.class, () -> service.update(1L, new Anuncio()));
    }

    @Test
    @DisplayName(value = "Dado um id de um anuncio que não está cadastrado quando tentar excluir o anuncio pelo id então deve lançar a exception AnuncioNaoEncontradoException")
    void DadoUmIdDeUmAnuncioQueNaoEstaCadastradoQuandoTentarExcluirOAnuncioPeloIdEntaoDeveLancarAExceptionAnuncioNaoEncontradoException() {

        when(repository.findById(ArgumentMatchers.any())).thenThrow(AnuncioNaoEncontradoException.class);

        assertThrows(AnuncioNaoEncontradoException.class, () -> service.delete(1L));
    }

    @Test
    @DisplayName(value = "Dado um AnuncioRequestDTO quando tentar converter para a entidade Anuncio então deve retornar a entidade anuncio")
    void DadoUmAnuncioRequestDTOQuandoTentarConverterParaAEntidadeAnuncioEntaoDeveRetornarAEntidadeAnuncio() {

        when(modelMapper.map(anuncioRequestDTO, Anuncio.class)).thenReturn(anuncio);

        Anuncio anuncio = service.toEntity(anuncioRequestDTO);

        Assertions.assertThat(anuncio).isNotNull();
        Assertions.assertThat(anuncio.getTamanho()).isNotNull();
    }

    @Test
    @DisplayName(value = "Dado um AnuncioUpdateRequestDTO quando tentar converter para a entidade Anuncio então deve retornar a entidade anuncio")
    void DadoUmAnuncioRequestUpdateDTOQuandoTentarConverterParaAEntidadeAnuncioEntaoDeveRetornarAEntidadeAnuncio() {

        AnuncioUpdateRequestDTO anuncioUpdateRequestDTO = new AnuncioUpdateRequestDTO(1L, "A4", new BigDecimal("160.0"));

        when(modelMapper.map(anuncioUpdateRequestDTO, Anuncio.class)).thenReturn(anuncioCadastrado);

        Anuncio anuncio = service.toEntity(anuncioUpdateRequestDTO);

        Assertions.assertThat(anuncio).isNotNull();
        Assertions.assertThat(anuncio.getId()).isEqualTo(1L);
        Assertions.assertThat(anuncio.getTamanho()).isNotNull();
        Assertions.assertThat(anuncio.getValor()).isNotNull();
    }

    @Test
    @DisplayName(value = "Dado uma entidade Anuncio ao tentar converter para AnuncioResponseDTO então deve retornar anuncioResponseDTO")
    void DadoUmaEntidadeAnuncioAoTentarConverterParaAnuncioResponseDTOEntaoDeveRetornarAnuncioResponseDTO() {

        AnuncioResponseDTO anuncioResponseDTO = new AnuncioResponseDTO(1L, "A4", new BigDecimal("160.0"));

        when(modelMapper.map(anuncio, AnuncioResponseDTO.class)).thenReturn(anuncioResponseDTO);

        AnuncioResponseDTO anuncioResponse = service.toResponse(anuncio);

        Assertions.assertThat(anuncioResponse).isNotNull();
        Assertions.assertThat(anuncioResponse.getId()).isEqualTo(1L);

    }

}
