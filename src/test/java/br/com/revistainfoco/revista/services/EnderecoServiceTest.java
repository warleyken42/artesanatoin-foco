package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.CidadeRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.EnderecoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.EnderecoUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.EstadoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.CidadeResponseDTO;
import br.com.revistainfoco.revista.domain.dto.response.EnderecoResponseDTO;
import br.com.revistainfoco.revista.domain.dto.response.EstadoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.domain.entity.Endereco;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.repository.EnderecoRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EnderecoService service;

    private Cidade cidadeMock;
    private CidadeRequestDTO cidadeRequestDTOMock;
    private Endereco enderecoMock;
    private EnderecoResponseDTO enderecoResponseDTO;

    @BeforeEach
    void beaforeEach() {
        Estado estadoMock = new Estado(1L, "São Paulo", "SP");
        cidadeMock = new Cidade(1L, "Guarulhos", estadoMock);
        EstadoRequestDTO estadoRequestDTOMock = new EstadoRequestDTO("São Paulo", "SP");
        cidadeRequestDTOMock = new CidadeRequestDTO("Guarulhos", estadoRequestDTOMock);
        EstadoResponseDTO estadoResponseDTOMock = new EstadoResponseDTO(1L, "São Paulo", "SP");
        CidadeResponseDTO cidadeResponseDTO = new CidadeResponseDTO(1L, "Guarulhos", estadoResponseDTOMock);
        enderecoMock = new Endereco(1L, "Cidade Lion", cidadeMock, "17080401", "184", "APT: 32", "Jardim Anny");
        enderecoResponseDTO = new EnderecoResponseDTO(1L, "Cidade Lion", cidadeResponseDTO, "17080401", "184", "APT: 32", "Jardim Anny");
    }

    @Test
    @DisplayName(value = "Dado um endereço para cadastrar quando tentar cadastrar deve cadastrar com sucesso e retornar o endereço cadastrado")
    void DadoUmEnderecoParaCadastrarQuandoTentarCadastrarDeveCadastrarComSucessoERetornarOEnderecoCadastrado() {

        EnderecoRequestDTO enderecoRequestDTOMock = new EnderecoRequestDTO("Cidade Lion", cidadeRequestDTOMock, "17080401", "184", "APT: 32", "Jardim Anny");

        when(modelMapper.map(enderecoRequestDTOMock, Endereco.class)).thenReturn(enderecoMock);
        when(repository.save(enderecoMock)).thenReturn(enderecoMock);
        when(modelMapper.map(enderecoMock, EnderecoResponseDTO.class)).thenReturn(enderecoResponseDTO);

        EnderecoResponseDTO enderecoCadastrado = service.create(enderecoRequestDTOMock);

        Assertions.assertThat(enderecoCadastrado).isNotNull();
        Assertions.assertThat(enderecoCadastrado.getId()).isEqualTo(1L);
        Assertions.assertThat(enderecoCadastrado.getLogradouro()).isEqualTo("Cidade Lion");
        Assertions.assertThat(enderecoCadastrado.getCep()).isEqualTo("17080401");
        Assertions.assertThat(enderecoCadastrado.getNumero()).isEqualTo("184");
        Assertions.assertThat(enderecoCadastrado.getComplemento()).isEqualTo("APT: 32");
        Assertions.assertThat(enderecoCadastrado.getBairro()).isEqualTo("Jardim Anny");
        Assertions.assertThat(enderecoCadastrado.getCidade().getNome()).isEqualTo("Guarulhos");
        Assertions.assertThat(enderecoCadastrado.getCidade().getId()).isEqualTo(1L);
        Assertions.assertThat(enderecoCadastrado.getCidade().getEstado().getNome()).isEqualTo("São Paulo");
        Assertions.assertThat(enderecoCadastrado.getCidade().getEstado().getId()).isEqualTo(1L);
        Assertions.assertThat(enderecoCadastrado.getCidade().getEstado().getUf()).isEqualTo("SP");

    }

    @Test
    @DisplayName(value = "Dado uma solicitação para ler todos os endereço cadastrados então deve retornar todos os endereços cadastrados")
    void DadoUmaSolicitacaoParaLerTodosOsEnderecoCadastradosEntaoDeveRetornarTodosOsEnderecosCadastrados() {

        List<Endereco> enderecoCadatradosMock = asList(
                new Endereco(1L, "Cidade Lion", cidadeMock, "17080401", "184", "APT: 32", "Jardim Anny"),
                new Endereco(2L, "Robru", cidadeMock, "17080402", "184", "APT: 32", "Robru")
        );

        when(repository.findAll()).thenReturn(enderecoCadatradosMock);
        when(modelMapper.map(enderecoMock, EnderecoResponseDTO.class)).thenReturn(enderecoResponseDTO);

        List<EnderecoResponseDTO> enderecosCadastrados = service.readAll();

        EnderecoResponseDTO enderecoCadastrado = enderecosCadastrados.get(0);

        Assertions.assertThat(enderecosCadastrados).isNotNull().isNotEmpty().contains(enderecoCadastrado).hasSize(2);

        Assertions.assertThat(enderecoCadastrado.getLogradouro()).isEqualTo("Cidade Lion");
    }

    @Test
    @DisplayName(value = "Dado um id quando tentar ler um endereço pelo id então deve retornar o endereço")
    void DadoUmIdQuandoTentarLerUmEnderecoPeloIdDeveRetornarOEndereco() {

        when(repository.findById(1L)).thenReturn(Optional.of(enderecoMock));

        when(modelMapper.map(enderecoMock, EnderecoResponseDTO.class)).thenReturn(enderecoResponseDTO);

        EnderecoResponseDTO enderecoCadastrado = service.readById(1L);

        Assertions.assertThat(enderecoCadastrado).isNotNull();
        Assertions.assertThat(enderecoCadastrado.getId()).isEqualTo(1L);
        Assertions.assertThat(enderecoCadastrado.getLogradouro()).isEqualTo("Cidade Lion");
    }

    @Test
    @DisplayName("Dado um endereço para atualizar quando tentar atualizar os dados então deve retornar o endereço com os dados atualizados")
    void DadoUmEnderecoParaAtualizarQuandoTentarAtualizarOsDadosEntaoDeveRetornarOEnderecoComOsDadosAtualizados() {

        Endereco enderecoComNomeErradoMock = new Endereco(1L, "Cidade Limao", cidadeMock, "17261414", "24", "", "Jagaraí");

        when(repository.findById(1L)).thenReturn(Optional.of(enderecoComNomeErradoMock));
        EnderecoUpdateRequestDTO enderecoUpdateRequestDTOMock = new EnderecoUpdateRequestDTO(
                1L, "Cidade Lion", cidadeRequestDTOMock, "17261414", "24", "", "Jagaraí");

        when(modelMapper.map(enderecoUpdateRequestDTOMock.getCidade(), Cidade.class)).thenReturn(cidadeMock);
        when(repository.save(enderecoComNomeErradoMock)).thenReturn(enderecoMock);
        when(modelMapper.map(enderecoMock, EnderecoResponseDTO.class)).thenReturn(enderecoResponseDTO);

        EnderecoResponseDTO enderecoResponseDTO = service.update(1L, enderecoUpdateRequestDTOMock);

        Assertions.assertThat(enderecoResponseDTO).isNotNull();
        Assertions.assertThat(enderecoResponseDTO.getLogradouro()).isEqualTo("Cidade Lion");
        Assertions.assertThat(enderecoResponseDTO.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName(value = "Dado um id quando tentar deletar um endereço então deve excluir o endereço do banco de dados")
    void DadoUmIdQuandoTentarDeletarUmEnderecoEntaoDeveExcluirOEnderecoDoBancoDeDados() {

        when(repository.findById(1L)).thenReturn(Optional.of(enderecoMock));

        assertDoesNotThrow(() -> service.delete(1L));
    }
}
