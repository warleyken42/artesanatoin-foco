package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.*;
import br.com.revistainfoco.revista.domain.dto.response.CidadeResponseDTO;
import br.com.revistainfoco.revista.domain.dto.response.EnderecoResponseDTO;
import br.com.revistainfoco.revista.domain.dto.response.EstadoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.domain.entity.Endereco;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.EnderecoNaoEncontradoException;
import br.com.revistainfoco.revista.repository.EnderecoRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private CidadeService cidadeService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EnderecoService enderecoService;

    private Endereco endereco;
    private Cidade cidade;
    private Endereco enderecoCadastrado;
    private Estado estado;
    private EstadoRequestDTO estadoRequestDTO;
    private CidadeRequestDTO cidadeRequestDTO;
    private EnderecoRequestDTO enderecoRequestDTO;


    @BeforeEach
    void beaforeEach() {
        estado = new Estado(1L, "São Paulo", "SP");
        cidade = new Cidade(null, "Jaguaribe", estado);
        endereco = new Endereco(1L, "Rua Cidade Lion", cidade, "74185296", "184", "Apto: 32", "Jardim Anny");
        enderecoCadastrado = new Endereco(1L, "Rua Cidade Lion", cidade, "74185296", "184", "Apto: 32", "Jardim Anny");
        cidadeRequestDTO = new CidadeRequestDTO("Guarulhos", estadoRequestDTO);
        estadoRequestDTO = new EstadoRequestDTO("São Paulo", "SP");
    }

    @Test
    @DisplayName(value = "Dado uma solicitação para ler todos os endereços cadastrados então deve retornar todos os endereços cadastrados")
    void DadoUmaSolicitacaoParaLerTodosOsEnderecoCadastradosEntaoDeveRetornarTodosOsEnderecosCadastrados() {
        List<Endereco> enderecos = asList(
                new Endereco(1L, "Rua Cidade Lion", new Cidade(1L, "Guarulhos", new Estado(1L, "São Paulo", "SP")), "74185296", "184", "Apto: 32", "Jardim Anny"),
                new Endereco(2L, "Rua Manoel Lionel dos Santos", new Cidade(2L, "Guaianases", new Estado(1L, "São Paulo", "SP")), "14725836", "19", "Casa", "Jardim Robru"),
                new Endereco(3L, "Rua Rocilda Diógenes", new Cidade(3L, "Jaguaribe", new Estado(2L, "Ceará", "CE")), "63475000", "94", "Casa", "Cruzeiro")

        );

        when(enderecoRepository.findAll()).thenReturn(enderecos);

        List<Endereco> enderecosCadastrados = enderecoService.findAll();

        Assertions.assertThat(enderecosCadastrados).isNotNull();
        Assertions.assertThat(enderecosCadastrados).isNotEmpty();
        Assertions.assertThat(enderecosCadastrados.size()).isEqualTo(3);
    }


    @Test
    @DisplayName(value = "Dado um id quando tentar ler um endereço pelo id então deve retornar o endereço")
    void DadoUmIdQuandoTentarLerUmEnderecoPeloIdDeveRetornarOEndereco() {

        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(enderecoCadastrado));

        Endereco enderecoCadastrado = enderecoService.findById(1L);

        Assertions.assertThat(enderecoCadastrado).isNotNull();
        Assertions.assertThat(enderecoCadastrado.getId()).isEqualTo(1L);
        Assertions.assertThat(enderecoCadastrado.getLogradouro()).isEqualTo("Rua Cidade Lion");
        Assertions.assertThat(enderecoCadastrado.getCidade()).isNotNull();
        Assertions.assertThat(enderecoCadastrado.getCep()).isEqualTo("74185296");
        Assertions.assertThat(enderecoCadastrado.getNumero()).isEqualTo("184");
        Assertions.assertThat(enderecoCadastrado.getComplemento()).isEqualTo("Apto: 32");
        Assertions.assertThat(enderecoCadastrado.getBairro()).isEqualTo("Jardim Anny");


    }

    @Test
    @DisplayName(value = "Dado um endereco quando tentar cadastrar entao deve retornar os dados do endereço cadastrado")
    void DadoUmEnderecoQuandoTentarCadastrarEntaoDeveRetornarOsDadosDoEnderecoCadastrado() {
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

        when(enderecoRepository.save(endereco)).thenReturn(enderecoCriado);

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

    @Test
    @DisplayName(value = "Dado um endereço para atualizar quando tentar atualizar os dados então deve retornar o endereço com os dados atualizados")
    void DadoUmEnderecoParaAtualizarQuandoTentarAtualizarOsDadosEntaoDeveRetornarOEnderecoComOsDadosAtualizados() {

        Endereco enderecoComNovosDados = new Endereco(1L, "Cidade Lion", cidade, "17261414", "24", "", "Jagaraí");

        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(enderecoCadastrado));
        when(enderecoRepository.save(enderecoCadastrado)).thenReturn(enderecoComNovosDados);

        Endereco enderecoAtualizado = enderecoService.update(1L, endereco);

        Assertions.assertThat(enderecoAtualizado).isNotNull();
        Assertions.assertThat(enderecoAtualizado.getLogradouro()).isEqualTo("Cidade Lion");
        Assertions.assertThat(enderecoAtualizado.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName(value = "Dado um id quando tentar deletar um endereço então deve excluir o endereço do banco de dados")
    void DadoUmIdQuandoTentarDeletarUmEnderecoEntaoDeveExcluirOEnderecoDoBancoDeDados() {

        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(enderecoCadastrado));

        assertDoesNotThrow(() -> enderecoService.delete(1L));
    }

    @Test
    @DisplayName(value = "Dado um id de um endereço que não está cadastrado quando tentar recuperar o endereço pelo id então deve lançar a exception EnderecoNaoEncontradoException")
    void DadoUmIdDeUmEnrecoQueNaoEstaCadastradoQuandoTentarRecuperarOEnderecoPeloIdEntaoDeveLancarAExceptionEnderecoNaoEncontradoException() {

        when(enderecoRepository.findById(ArgumentMatchers.any())).thenThrow(EnderecoNaoEncontradoException.class);

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.findById(1L));
    }

    @Test
    @DisplayName(value = "Dado um id de um endereço que não está cadastrado quando tentar atualizar o endereço pelo id então deve lançar a exception EnderecoNaoEncontradoException")
    void DadoUmIdDeUmEnrecoQueNaoEstaCadastradoQuandoTentarAtualizarOEnderecoPeloIdEntaoDeveLancarAExceptionEnderecoNaoEncontradoException() {

        when(enderecoRepository.findById(1L)).thenThrow(EnderecoNaoEncontradoException.class);

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.update(1L, new Endereco()));
    }

    @Test
    @DisplayName(value = "Dado um id de um endereço que não está cadastrado quando tentar excluir o endereço pelo id então deve lançar a exception EnderecoNaoEncontradoException")
    void DadoUmIdDeUmEnderecoQueNaoEstaCadastradoQuandoTentarExcluirOEnderecoPeloIdEntaoDeveLancarAExceptionEnderecoNaoEncontradoException() {

        when(enderecoRepository.findById(ArgumentMatchers.any())).thenThrow(EnderecoNaoEncontradoException.class);

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.delete(1L));
    }

    @Test
    @DisplayName(value = "Dado um EnderecoRequestDTO quando tentar converter para a entidade Endereco então deve retornar a entidade endereco")
    void DadoUmEnderecoRequestDTOQuandoTentarConverterParaAEntidadeEnderecoEntaoDeveRetornarAEntidadeEndereco() {

        when(modelMapper.map(enderecoRequestDTO, Endereco.class)).thenReturn(endereco);

        Endereco endereco = enderecoService.toEntity(enderecoRequestDTO);

        Assertions.assertThat(endereco).isNotNull();
        Assertions.assertThat(endereco.getId()).isEqualTo(1L);
        Assertions.assertThat(endereco.getLogradouro()).isEqualTo("Rua Cidade Lion");
        Assertions.assertThat(endereco.getCidade()).isNotNull();
        Assertions.assertThat(endereco.getCep()).isEqualTo("74185296");
        Assertions.assertThat(endereco.getNumero()).isEqualTo("184");
        Assertions.assertThat(endereco.getComplemento()).isEqualTo("Apto: 32");
        Assertions.assertThat(endereco.getBairro()).isEqualTo("Jardim Anny");
    }

    @Test
    @DisplayName(value = "Dado um EnderecoUpdateRequestDTO quando tentar converter para a entidade Endereco então deve retornar a entidade endereco")
    void DadoUmEnderecoRequestUpdateDTOQuandoTentarConverterParaAEntidadeEnderecoEntaoDeveRetornarAEntidadeEndereco() {
        EstadoUpdateRequestDTO estadoUpdateRequestDTO = new EstadoUpdateRequestDTO(1L, "Ceará", "CE");
        CidadeUpdateRequestDTO cidadeUpdateRequestDTO = new CidadeUpdateRequestDTO(1L, "Jaguaribe", estadoUpdateRequestDTO);
        EnderecoUpdateRequestDTO enderecoUpdateRequestDTO = new EnderecoUpdateRequestDTO(1L, "Rua Cidade Lion", cidadeUpdateRequestDTO, "74185296", "184", "Apto: 32", "Jardim Anny");


        when(modelMapper.map(enderecoUpdateRequestDTO, Endereco.class)).thenReturn(enderecoCadastrado);

        Endereco endereco = enderecoService.toEntity(enderecoUpdateRequestDTO);

        Assertions.assertThat(endereco).isNotNull();
        Assertions.assertThat(endereco.getId()).isEqualTo(1L);
        Assertions.assertThat(endereco.getLogradouro()).isEqualTo("Rua Cidade Lion");
        Assertions.assertThat(endereco.getCidade()).isNotNull();
        Assertions.assertThat(endereco.getCep()).isEqualTo("74185296");
        Assertions.assertThat(endereco.getNumero()).isEqualTo("184");
        Assertions.assertThat(endereco.getComplemento()).isEqualTo("Apto: 32");
        Assertions.assertThat(endereco.getBairro()).isEqualTo("Jardim Anny");
    }

    @Test
    @DisplayName(value = "Dado uma entidade Endereco ao tentar converter para EnderecoResponseDTO então deve retornar enderecoResponseDTO")
    void DadoUmaEntidadeEnderecoAoTentarConverterParaEnderecoResponseDTOEntaoDeveRetornarEnderecoResponseDTO() {
        EstadoResponseDTO estadoResponseDTO = new EstadoResponseDTO(1L, "Ceará", "CE");
        CidadeResponseDTO cidadeResponseDTO = new CidadeResponseDTO(1L, "Jaguaribe", estadoResponseDTO);
        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO(1L, "Rua Cidade Lion", cidadeResponseDTO, "74185296", "184", "Apto: 32", "Jardim Anny");

        when(modelMapper.map(endereco, EnderecoResponseDTO.class)).thenReturn(enderecoResponseDTO);

        EnderecoResponseDTO enderecoResponse = enderecoService.toResponse(endereco);

        Assertions.assertThat(enderecoResponse).isNotNull();
        Assertions.assertThat(enderecoResponse.getId()).isEqualTo(1L);
        Assertions.assertThat(enderecoResponse.getLogradouro()).isEqualTo("Rua Cidade Lion");
        Assertions.assertThat(enderecoResponse.getCidade()).isNotNull();
        Assertions.assertThat(enderecoResponse.getCep()).isEqualTo("74185296");
        Assertions.assertThat(enderecoResponse.getNumero()).isEqualTo("184");
        Assertions.assertThat(enderecoResponse.getComplemento()).isEqualTo("Apto: 32");
        Assertions.assertThat(enderecoResponse.getBairro()).isEqualTo("Jardim Anny");
    }

}
