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
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstadoServiceTest {

    @Mock
    private EstadoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EstadoService service;

    private Estado estadoCadastrado;
    private Estado estado;
    private EstadoRequestDTO estadoRequestDTO;

    @BeforeEach
    void beforeEach() {
        estado = new Estado(null, "Ceará", "CE");
        estadoCadastrado = new Estado(1L, "Ceará", "CE");
        estadoRequestDTO = new EstadoRequestDTO("Ceará", "CE");
    }

    @Test
    @DisplayName(value = "Dado um estado quando tentar salvar no banco de dados então deve retornar o estado salvo")
    void DadoUmEstadoQuandoTentarSalvarNoBancoDeDadosEntaoDeveRetorarOEstadoSalvo() {
        when(repository.save(any())).thenReturn(estadoCadastrado);

        Estado estadoCadastrado = service.create(estado);

        Assertions.assertThat(estadoCadastrado).isNotNull();
        Assertions.assertThat(estadoCadastrado.getId()).isEqualTo(1L);
        Assertions.assertThat(estadoCadastrado.getNome()).isEqualTo("Ceará");
        Assertions.assertThat(estadoCadastrado.getUf()).isEqualTo("CE");
    }

    @Test
    @DisplayName(value = "Dado uma solicitação para ler todos os estados cadastrados então deve retornar todos os estados cadastrados")
    void DadoUmaSolicitacaoParaLerTodosOsEstadosCadastradoEntaoDeveRetornarTodosOsEstadosCadastrados() {
        List<Estado> estados = asList(
                new Estado(1L, "Ceará", "CE"),
                new Estado(2L, "São Paulo", "SP"),
                new Estado(3L, "Rio de Janeiro", "RJ")
        );

        when(repository.findAll()).thenReturn(estados);

        List<Estado> estadosCadastrados = service.findAll();

        Assertions.assertThat(estadosCadastrados).isNotNull();
        Assertions.assertThat(estadosCadastrados).isNotEmpty();
        Assertions.assertThat(estadosCadastrados.size()).isEqualTo(3);
    }

    @Test
    @DisplayName(value = "Dado um id quando tentar ler um estado pelo id então deve retornar o estado")
    void DadoUmIdQuandoTentarLerUmEstadoPeloIdDeveRetornarOEstado() {
        when(repository.findById(1L)).thenReturn(Optional.of(estadoCadastrado));

        Estado estadoCadastrado = service.findById(1L);

        Assertions.assertThat(estadoCadastrado).isNotNull();
        Assertions.assertThat(estadoCadastrado.getId()).isEqualTo(1L);
        Assertions.assertThat(estadoCadastrado.getNome()).isEqualTo("Ceará");
        Assertions.assertThat(estadoCadastrado.getUf()).isEqualTo("CE");
    }

    @Test
    @DisplayName("Dado um estado para atualizar quando tentar atualizar os dados e houver uma duplicidade então deve lançar a exception EstadoJaCadastradoException")
    void DadoUmEstadoParaAtualizarQuandoTentarAtualizarOsDadosEHouverUmaDuplicidadeEntaoDeveLancarAExceptionEstadoJaCadastradoException() {
        when(repository.findById(1L)).thenReturn(Optional.of(estadoCadastrado));
        when(repository.save(estadoCadastrado)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(EstadoJaCadastradoException.class, () -> service.update(1L, estadoCadastrado));
    }

    @Test
    @DisplayName("Dado um estado para atualizar quando tentar atualizar os dados então deve retornar o estado com os dados atualizados")
    void DadoUmEstadoParaAtualizarQuandoTentarAtualizarOsDadosEntaoDeveRetornarOEstadoComOsDadosAtualizados() {
        Estado estadoComNovosDados = new Estado(1L, "São Paulo", "SP");

        when(repository.findById(1L)).thenReturn(Optional.of(estadoCadastrado));
        when(repository.save(estadoCadastrado)).thenReturn(estadoComNovosDados);

        Estado estadoAtualizado = service.update(1L, estadoCadastrado);

        Assertions.assertThat(estadoAtualizado).isNotNull();
        Assertions.assertThat(estadoAtualizado.getId()).isEqualTo(1L);
        Assertions.assertThat(estadoAtualizado.getNome()).isEqualTo("São Paulo");
        Assertions.assertThat(estadoAtualizado.getUf()).isEqualTo("SP");
    }

    @Test
    @DisplayName(value = "Dado um id quando tentar deletar um estado então deve excluir o estado do banco de dados")
    void DadoUmIdQuandoTentarDeletarUmEstadoEntaoDeveExcluirOEstadoDoBancoDeDados() {
        when(repository.findById(any())).thenReturn(Optional.of(estadoCadastrado));

        assertDoesNotThrow(() -> service.delete(any()));
    }

    @Test
    @DisplayName(value = "Dado um estado já cadastrado quando tentar cadastrar novamente então deve lançar a exception EstadoJaCadastradoException")
    void DadoUmEstadoJaCadastradoQuandoTentarCadastrarNovamenteEntaoDeveLancarAExceptionEstadoJaCadastradoException() {
        when(repository.save(estado)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(EstadoJaCadastradoException.class, () -> service.create(estado));
    }

    @Test
    @DisplayName(value = "Dado um id de um estado que não está cadastrado quando tentar recuperar o estado pelo id então deve lançar a exception EstadoNaoEncontradoException")
    void DadoUmIdDeUmEstadoQueNaoEstaCadastradoQuandoTentarRecuperarOEstadoPeloIdEntaoDeveLancarAExceptionEstadoNaoEncontradoException() {
        when(repository.findById(any())).thenThrow(EstadoNaoEncontradoException.class);

        assertThrows(EstadoNaoEncontradoException.class, () -> service.findById(any()));
    }

    @Test
    @DisplayName(value = "Dado um id de um estado que não está cadastrado quando tentar atualizar o estado então deve lançar a exception EstadoNaoEncontradoException")
    void DadoUmIdDeUmEstadoQueNaoEstaCadastradoQuandoTentarAtualizarOEstadoEntaoDeveLancarAExceptionEstadoNaoEncontradoException() {
        when(repository.findById(any())).thenThrow(EstadoNaoEncontradoException.class);

        assertThrows(EstadoNaoEncontradoException.class, () -> service.update(any(), estadoCadastrado));

    }

    @Test
    @DisplayName(value = "Dado um id de um estado que não está cadastrado quando tentar excluir o estado então deve lançar a exception EstadoNaoEncontradoException")
    void DadoUmIdDeUmEstadoQueNaoEstaCadastradoQuandoTentarExcluirOEstadoEntaoDeveLancarAExceptionEstadoNaoEncontradoException() {
        when(repository.findById(1L)).thenThrow(EstadoNaoEncontradoException.class);

        assertThrows(EstadoNaoEncontradoException.class, () -> service.delete(1L));
    }

    @Test
    @DisplayName(value = "Dado um nome de estado e uma uf ao tentar recuperar o estado cadastrado então deve retornar os dados do estado")
    void DadoUmNomeDeEstadoEUmaUfAoTentarRecuperarOEstadoCadastradoEntaoDeveRetornarOsDadosDoEstado() {

        when(repository.findByNomeAndUf(any(), any())).thenReturn(Optional.of(estadoCadastrado));

        Estado estadoCadastrado = service.findByNomeAndUf(anyString(), anyString());

        Assertions.assertThat(estadoCadastrado).isNotNull();
        Assertions.assertThat(estadoCadastrado.getId()).isEqualTo(1L);
        Assertions.assertThat(estadoCadastrado.getNome()).isEqualTo("Ceará");
        Assertions.assertThat(estadoCadastrado.getUf()).isEqualTo("CE");
    }

    @Test
    @DisplayName(value = "Dado um nome de estado e uma uf quando não encontrar o estado cadastrado então deve lançar EstadoNaoEncontradoException")
    void DadoUmNomeEeEstadEUmaUfQuandoNaoEncontrarOEstadoCadastradoEntaoDeveLancarEstadoNaoEncontradoException() {

        when(repository.findByNomeAndUf(any(), any())).thenReturn(Optional.empty());

        assertThrows(EstadoNaoEncontradoException.class, () -> service.findByNomeAndUf(anyString(), anyString()));
    }

    @Test
    @DisplayName(value = "Dado um EstadoRequestDTO quando tentar converter para a entidade Estado então deve retornar a entidade Estado")
    void DadoUmEstadoRequestDTOQuandoTentarConverterParaAEntidadeEstadoEntaoDeveRetornarAEntidadeEstado() {

        when(modelMapper.map(estadoRequestDTO, Estado.class)).thenReturn(estado);

        Estado estado = service.toEntity(estadoRequestDTO);

        Assertions.assertThat(estado).isNotNull();
        Assertions.assertThat(estado.getNome()).isEqualTo("Ceará");
        Assertions.assertThat(estado.getUf()).isEqualTo("CE");
    }

    @Test
    @DisplayName(value = "Dado um EstadoUpdateRequestDTO quando tentar converter para a entidade Estado então deve retornar a entidade Estado")
    void DadoUmEstadoRequestUpdateDTOQuandoTentarConverterParaAEntidadeEstadoEntaoDeveRetornarAEntidadeEstado() {
        EstadoUpdateRequestDTO estadoUpdateRequestDTO = new EstadoUpdateRequestDTO(1L, "Ceará", "CE");

        when(modelMapper.map(estadoUpdateRequestDTO, Estado.class)).thenReturn(estadoCadastrado);

        Estado estado = service.toEntity(estadoUpdateRequestDTO);

        Assertions.assertThat(estado).isNotNull();
        Assertions.assertThat(estado.getId()).isEqualTo(1L);
        Assertions.assertThat(estado.getNome()).isEqualTo("Ceará");
        Assertions.assertThat(estado.getUf()).isEqualTo("CE");
    }

    @Test
    @DisplayName(value = "Dado uma entidade Estado ao tentar converter para EstadoResponseDTO então deve retornar EstadoResponseDTO")
    void DadoUmaEntidadeEstadoAoTentarConverterParaEstadoResponseDTOEntaoDeveRetornarEstadoResponseDTO() {
        EstadoResponseDTO estadoResponseDTO = new EstadoResponseDTO(1L, "Ceará", "CE");

        when(modelMapper.map(estadoCadastrado, EstadoResponseDTO.class)).thenReturn(estadoResponseDTO);

        EstadoResponseDTO estadoResponse = service.toResponse(estadoCadastrado);

        Assertions.assertThat(estadoResponse).isNotNull();
        Assertions.assertThat(estadoResponse.getId()).isEqualTo(1L);
        Assertions.assertThat(estadoResponse.getNome()).isEqualTo("Ceará");
        Assertions.assertThat(estadoResponse.getUf()).isEqualTo("CE");
    }

}
