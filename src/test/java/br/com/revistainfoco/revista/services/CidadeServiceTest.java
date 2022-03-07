package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.CidadeRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.EstadoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.CidadeResponseDTO;
import br.com.revistainfoco.revista.domain.dto.response.EstadoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.CidadeNaoEncontradaException;
import br.com.revistainfoco.revista.repository.CidadeRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CidadeServiceTest {

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private EstadoService estadoService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CidadeService cidadeService;
    private Cidade cidade;
    private Estado estadoCadastrado;
    private Cidade cidadeCadastrada;
    private CidadeRequestDTO cidadeRequestDTO;

    @BeforeEach
    void beforeEach() {
        Estado estado = new Estado(1L, "Ceará", "CE");
        cidade = new Cidade(null, "Jaguaribe", estado);

        estadoCadastrado = new Estado(1L, "Ceará", "CE");
        cidadeCadastrada = new Cidade(1L, "Jaguaribe", estado);

        EstadoRequestDTO estadoRequestDTO = new EstadoRequestDTO("Ceará", "CE");
        cidadeRequestDTO = new CidadeRequestDTO("Jaguaribe", estadoRequestDTO);
    }

    @Test
    @DisplayName(value = "Dado uma cidade quando tentar salvar no banco de dados então deve retornar a cidade salva")
    void DadoUmaCidadeQuandoTentarSalvarNoBancoDeDadosEntaoDeveRetorarACidadeSalva() {

        when(cidadeRepository.findByNome("Jaguaribe")).thenReturn(Optional.empty());
        when(estadoService.create(cidade.getEstado())).thenReturn(estadoCadastrado);
        when(cidadeRepository.save(cidade)).thenReturn(cidadeCadastrada);

        Cidade cidadeCadastrada = cidadeService.create(cidade);

        Assertions.assertThat(cidadeCadastrada).isNotNull();
        Assertions.assertThat(cidadeCadastrada.getId()).isEqualTo(1L);
        Assertions.assertThat(cidadeCadastrada.getNome()).isEqualTo("Jaguaribe");
        Assertions.assertThat(cidadeCadastrada.getEstado()).isNotNull();
        Assertions.assertThat(cidadeCadastrada.getEstado().getId()).isEqualTo(1L);
        Assertions.assertThat(cidadeCadastrada.getEstado().getUf()).isEqualTo("CE");
        Assertions.assertThat(cidadeCadastrada.getEstado().getNome()).isEqualTo("Ceará");
    }

    @Test
    @DisplayName(value = "Dado uma solicitação para ler todas as cidades cadastradas então deve retornar todas as cidades cadastradas")
    void DadoUmaSolicitacaoParaLerTodasAsCidadesCadastradaEntaoDeveRetornarTodasAsCidadesCadastradas() {
        List<Cidade> cidades = asList(
                new Cidade(1L, "Jaguaribe", new Estado(1L, "Ceará", "CE")),
                new Cidade(2L, "Icó", new Estado(1L, "Ceará", "CE")),
                new Cidade(2L, "Guarulhos", new Estado(2L, "São Paulo", "SP"))
        );

        when(cidadeRepository.findAll()).thenReturn(cidades);

        List<Cidade> cidadesCadastradas = cidadeService.findAll();

        Assertions.assertThat(cidadesCadastradas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);
    }

    @Test
    @DisplayName(value = "Dado um id quando tentar ler uma cidade pelo id então deve retornar a cidade")
    void DadoUmIdQuandoTentarLerUmaCidadePeloIdDeveRetornarACidade() {

        when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidadeCadastrada));

        Cidade cidadeCadastrada = cidadeService.findById(1L);

        Assertions.assertThat(cidadeCadastrada).isNotNull();
        Assertions.assertThat(cidadeCadastrada.getId()).isEqualTo(1L);
        Assertions.assertThat(cidadeCadastrada.getNome()).isEqualTo("Jaguaribe");
        Assertions.assertThat(cidadeCadastrada.getEstado()).isNotNull();
        Assertions.assertThat(cidadeCadastrada.getEstado().getId()).isEqualTo(1L);
        Assertions.assertThat(cidadeCadastrada.getEstado().getUf()).isEqualTo("CE");
        Assertions.assertThat(cidadeCadastrada.getEstado().getNome()).isEqualTo("Ceará");
    }

    @Test
    @DisplayName("Dado uma cidade para atualizar quando tentar atualizar os dados então deve retornar a cidade com os dados atualizados")
    void DadoUmACidadeParaAtualizarQuandoTentarAtualizarOsDadosEntaoDeveRetornarACidadeComOsDadosAtualizados() {

        Estado estadoComDadosAtualizados = new Estado(1L, "São Paulo", "SP");
        Cidade cidadeComNovosDados = new Cidade(1L, "Guarulhos", estadoComDadosAtualizados);

        when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidadeCadastrada));
        when(estadoService.findByNomeAndUf(estadoComDadosAtualizados.getNome(), estadoComDadosAtualizados.getUf())).thenReturn(Optional.of(estadoCadastrado));
        when(estadoService.create(estadoComDadosAtualizados)).thenReturn(estadoComDadosAtualizados);
        when(cidadeRepository.save(cidadeComNovosDados)).thenReturn(cidadeComNovosDados);

        Cidade cidadeAtualizada = cidadeService.update(1L, cidadeComNovosDados);

        Assertions.assertThat(cidadeAtualizada).isNotNull();
        Assertions.assertThat(cidadeAtualizada.getId()).isEqualTo(1L);
        Assertions.assertThat(cidadeAtualizada.getNome()).isEqualTo("Guarulhos");
        Assertions.assertThat(cidadeAtualizada.getEstado()).isNotNull();
        Assertions.assertThat(cidadeAtualizada.getEstado().getId()).isEqualTo(1L);
        Assertions.assertThat(cidadeAtualizada.getEstado().getUf()).isEqualTo("SP");
        Assertions.assertThat(cidadeAtualizada.getEstado().getNome()).isEqualTo("São Paulo");
    }

    @Test
    @DisplayName(value = "Dado um id quando tentar deletar uma cidade então deve excluir a cidade do banco de dados")
    void DadoUmIdQuandoTentarDeletarUmaCidadeEntaoDeveExcluirACidadeDoBancoDeDados() {
        when(cidadeRepository.findById(any())).thenReturn(Optional.of(cidadeCadastrada));

        assertDoesNotThrow(() -> cidadeService.delete(any()));
    }

    @Test
    @DisplayName(value = "Dado um id de uma cidade que não está cadastrada quando tentar recuperar a cidade pelo id então deve lançar a exception CidadeNaoEncontradaException")
    void DadoUmIdDeUmaCidadeQueNaoEstaCadastradaQuandoTentarRecuperarACidadePeloIdEntaoDeveLancarAExceptionCidadeNaoEncontradaException() {
        when(cidadeRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CidadeNaoEncontradaException.class, () -> cidadeService.findById(1L));
    }

    @Test
    @DisplayName(value = "Dado um id de uma cidade que não está cadastrada quando tentar atualizar então deve lançar a exception CidadeNaoEncontradaException")
    void DadoUmIdDeUmaCidadeQueNaoEstaCadastradaQuandoTentarAtualizarEntaoDeveLancarAExceptionCidadeNaoEncontradaException() {
        when(cidadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CidadeNaoEncontradaException.class, () -> cidadeService.update(1L, cidade));
    }

    @Test
    @DisplayName(value = "Dado um id de uma cidade que não está cadastrada quando tentar excluir então deve lançar a exception CidadeNaoEncontradaException")
    void DadoUmIdDeUmaCidadeQueNaoEstaCadastradaQuandoTentarExcluirEntaoDeveLancarAExceptionCidadeNaoEncontradaException() {
        when(cidadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CidadeNaoEncontradaException.class, () -> cidadeService.delete(1L));
    }

    @Test
    @DisplayName(value = "Dado um CidadeRequestDTO quando tentar converter para a entidade Cidade então deve retornar a entidade cidade")
    void DadoUmCidadeRequestDTOQuandoTentarConverterParaAEntidadeCidadeEntaoDeveRetornarAEntidadeCidade() {

        when(modelMapper.map(cidadeRequestDTO, Cidade.class)).thenReturn(cidade);

        Cidade cidade = cidadeService.toEntity(cidadeRequestDTO);

        Assertions.assertThat(cidade).isNotNull();
        Assertions.assertThat(cidade.getNome()).isEqualTo("Jaguaribe");
        Assertions.assertThat(cidade.getEstado()).isNotNull();
        Assertions.assertThat(cidade.getEstado().getId()).isEqualTo(1L);
        Assertions.assertThat(cidade.getEstado().getUf()).isEqualTo("CE");
        Assertions.assertThat(cidade.getEstado().getNome()).isEqualTo("Ceará");
    }

    @Test
    @DisplayName(value = "Dado um CidadeUpdateRequestDTO quando tentar converter para a entidade Cidade então deve retornar a entidade cidade")
    void DadoUmCidadeRequestUpdateDTOQuandoTentarConverterParaAEntidadeCidadeEntaoDeveRetornarAEntidadeCidade() {
        EstadoRequestDTO estadoRequestDTO = new EstadoRequestDTO("Ceará", "CE");
        CidadeRequestDTO cidadeUpdateRequestDTO = new CidadeRequestDTO("Jaguaribe", estadoRequestDTO);

        when(modelMapper.map(cidadeUpdateRequestDTO, Cidade.class)).thenReturn(cidadeCadastrada);

        Cidade cidade = cidadeService.toEntity(cidadeUpdateRequestDTO);

        Assertions.assertThat(cidade).isNotNull();
        Assertions.assertThat(cidade.getId()).isEqualTo(1L);
        Assertions.assertThat(cidade.getNome()).isEqualTo("Jaguaribe");
        Assertions.assertThat(cidade.getEstado()).isNotNull();
        Assertions.assertThat(cidade.getEstado().getId()).isEqualTo(1L);
        Assertions.assertThat(cidade.getEstado().getUf()).isEqualTo("CE");
        Assertions.assertThat(cidade.getEstado().getNome()).isEqualTo("Ceará");
    }

    @Test
    @DisplayName(value = "Dado uma entidade Cidade ao tentar converter para CidadeResponseDTO então deve retornar CidadeResponseDTO")
    void DadoUmaEntidadeCidadeAoTentarConverterParaCidadeResponseDTOEntaoDeveRetornarCidadeResponseDTO() {
        EstadoResponseDTO estadoResponseDTO = new EstadoResponseDTO(1L, "Ceará", "CE");
        CidadeResponseDTO cidadeResponseDTO = new CidadeResponseDTO(1L, "Jaguaribe", estadoResponseDTO);

        when(modelMapper.map(cidadeCadastrada, CidadeResponseDTO.class)).thenReturn(cidadeResponseDTO);

        CidadeResponseDTO cidadeResponse = cidadeService.toResponse(cidadeCadastrada);

        Assertions.assertThat(cidadeResponse).isNotNull();
        Assertions.assertThat(cidadeResponse.getId()).isEqualTo(1L);
        Assertions.assertThat(cidadeResponse.getNome()).isEqualTo("Jaguaribe");
        Assertions.assertThat(cidadeResponse.getEstado()).isNotNull();
        Assertions.assertThat(cidadeResponse.getEstado().getId()).isEqualTo(1L);
        Assertions.assertThat(cidadeResponse.getEstado().getUf()).isEqualTo("CE");
        Assertions.assertThat(cidadeResponse.getEstado().getNome()).isEqualTo("Ceará");
    }
}
