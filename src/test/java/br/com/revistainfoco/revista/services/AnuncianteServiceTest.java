package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.*;
import br.com.revistainfoco.revista.domain.dto.response.*;
import br.com.revistainfoco.revista.domain.entity.*;
import br.com.revistainfoco.revista.errors.exceptions.AnuncianteNaoEncontradoException;
import br.com.revistainfoco.revista.repository.AnuncianteRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnuncianteServiceTest {

    @Mock
    private AnuncianteRepository anuncianteRepository;

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private EstadoService estadoService;

    @Mock
    private CidadeService cidadeService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ContatoService contatoService;

    @InjectMocks
    private AnuncianteService anuncianteService;

    private Anunciante anunciante;
    private Endereco endereco;
    private Anunciante anuncianteCadastrado;
    private AnuncianteRequestDTO anuncianteRequestDTO;
    private Contato contato;
    private ContatoResponseDTO contatoResponseDTO;
    private Estado estadoCadastrado;
    private Cidade cidadeCadastrada;

    @BeforeEach
    void beaforeEach() {
        EstadoRequestDTO estadoRequestDTO = new EstadoRequestDTO("São Paulo", "SP");
        CidadeRequestDTO cidadeRequestDTO = new CidadeRequestDTO("Jaguaribe", estadoRequestDTO);
        EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO("Rua Cidade Lion", cidadeRequestDTO, "74185296", "184", "Apto: 32", "Jardim Anny");
        ContatoRequestDTO contatoRequestDTO = new ContatoRequestDTO("Ana Maria", "Braga", "112233445566", "ana@mail.com");
        anuncianteRequestDTO = new AnuncianteRequestDTO("87571657000197", "Warley Kennedy Figueiredo", "REVISTA_IN_FOCO", enderecoRequestDTO, "warley-ft@hotmail.com", "www.revista_in_foco.com", contatoRequestDTO);

        cidadeCadastrada = new Cidade(1L, "Guarulhos", estadoCadastrado);
        estadoCadastrado = new Estado(1L, "São Paulo", "SP");
        contatoResponseDTO = new ContatoResponseDTO(1L, "Ana Maria", "Braga", "112233445566", "ana@mail.com");
        contato = new Contato(1L, "Ana Maria", "Braga", "112233445566", "ana@mail.com");

        Estado estado = new Estado(1L, "São Paulo", "SP");
        Cidade cidade = new Cidade(null, "Jaguaribe", estado);
        endereco = new Endereco(1L, "Rua Cidade Lion", cidade, "74185296", "184", "Apto: 32", "Jardim Anny");
        anunciante = new Anunciante(1L, "87571657000197", "Warley Kennedy Figueiredo", "REVISTA_IN_FOCO", endereco, "warley-ft@hotmail.com", "www.revista_in_foco.com", contato);
        anuncianteCadastrado = new Anunciante(1L, "87571657000197", "Warley Kennedy Figueiredo", "REVISTA_IN_FOCO", endereco, "warley-ft@hotmail.com", "www.revista_in_foco.com", contato);
    }

    @Test
    @DisplayName(value = "Dado uma solicitação para ler todos os anunciantes cadastrados então deve retornar todos os anunciantes cadastrados")
    void DadoUmaSolicitacaoParaLerTodosOsAnunciantesCadastradosEntaoDeveRetornarTodosOsAnunciantesCadastrados() {
        List<Anunciante> anunciantes = asList(
                new Anunciante(1L, "87571657000197", "Warley Kennedy Figueiredo", "REVISTA_IN_FOCO", endereco, "warley-ft@hotmail.com", "www.revista_in_foco.com", contato),
                new Anunciante(2L, "87571657111197", "Lucas Caua De Figueiredo", "REVISTA_IN_FOCO2", endereco, "lucas-ft@hotmail.com", "www.revista_in_foco2.com", contato),
                new Anunciante(3L, "87571657222197", "Jorge Rabello", "REVISTA_IN_FOCO3", endereco, "jorge-ft@hotmail.com", "www.revista_in_foco3.com", contato)
        );

        when(anuncianteRepository.findAll()).thenReturn(anunciantes);

        List<Anunciante> anunciantesCadastrados = anuncianteService.findAll();

        Assertions.assertThat(anunciantesCadastrados)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);
    }

    @Test
    @DisplayName(value = "Dado um id quando tentar ler um anunciante pelo id então deve retornar o anunciante")
    void DadoUmIdQuandoTentarLerUmAnunciantePeloIdEntaoDeveRetornarOAnunciante() {

        when(anuncianteRepository.findById(1L)).thenReturn(Optional.of(anuncianteCadastrado));

        Anunciante anuncianteCadastrado = anuncianteService.findById(1L);

        Assertions.assertThat(anuncianteCadastrado).isNotNull();
        Assertions.assertThat(anuncianteCadastrado.getId()).isEqualTo(1L);
        Assertions.assertThat(anuncianteCadastrado.getCnpj()).isEqualTo("87571657000197");
        Assertions.assertThat(anuncianteCadastrado.getRazaoSocial()).isEqualTo("Warley Kennedy Figueiredo");
        Assertions.assertThat(anuncianteCadastrado.getNomeFantasia()).isEqualTo("REVISTA_IN_FOCO");
        Assertions.assertThat(anuncianteCadastrado.getEndereco()).isNotNull();
        Assertions.assertThat(anuncianteCadastrado.getEmail()).isEqualTo("warley-ft@hotmail.com");
        Assertions.assertThat(anuncianteCadastrado.getSite()).isEqualTo("www.revista_in_foco.com");

    }

    @Test
    @DisplayName(value = "Dado um anunciante quando tentar cadastrar então deve retornar os dados do anunciante cadastrado")
    void DadoUmAnuncianteQuandoTentarCadastrarEntaoDeveRetornarOsDadosDoAnuncianteCadastrado() {

        when(estadoService.findByNomeAndUf("São Paulo", "SP")).thenReturn(Optional.of(estadoCadastrado));
        when(cidadeService.findByNome(any())).thenReturn(Optional.of(cidadeCadastrada));
        when(contatoService.findByCelular(any())).thenReturn(Optional.of(contato));
        when(anuncianteRepository.findByCnpj(anyString())).thenReturn(Optional.empty());
        when(anuncianteRepository.save(anunciante)).thenReturn(anuncianteCadastrado);

        Anunciante anuncianteCadastrado = anuncianteService.create(anunciante);

        Assertions.assertThat(anuncianteCadastrado).isNotNull();
        Assertions.assertThat(anuncianteCadastrado.getId()).isEqualTo(1L);
        Assertions.assertThat(anuncianteCadastrado.getCnpj()).isEqualTo("87571657000197");
        Assertions.assertThat(anuncianteCadastrado.getRazaoSocial()).isEqualTo("Warley Kennedy Figueiredo");
        Assertions.assertThat(anuncianteCadastrado.getNomeFantasia()).isEqualTo("REVISTA_IN_FOCO");
        Assertions.assertThat(anuncianteCadastrado.getEndereco()).isNotNull();
        Assertions.assertThat(anuncianteCadastrado.getEmail()).isEqualTo("warley-ft@hotmail.com");
        Assertions.assertThat(anuncianteCadastrado.getSite()).isEqualTo("www.revista_in_foco.com");
    }

    @Test
    @DisplayName(value = "Dado um anunciante para atualizar quando tentar atualizar os dados então deve retornar o anunciante com os dados atualizados")
    void DadoUmAnuncianteParaAtualizarQuandoTentarAtualizarOsDadosEntaoDeveRetornarOAnuncianteComOsDadosAtualizados() {

        Anunciante anuncianteComNovosDados = new Anunciante(1L, "87571657000197", "Warley Kennedy Figueiredo", "REVISTA_IN_FOCO", endereco, "warley-ft@hotmail.com", "www.revista_in_foco.com", contato);


        when(estadoService.findByNomeAndUf(any(), any())).thenReturn(Optional.of(estadoCadastrado));
        when(cidadeService.findByNome(any())).thenReturn(Optional.of(cidadeCadastrada));
        when(anuncianteRepository.findById(1L)).thenReturn(Optional.of(anuncianteCadastrado));
        when(anuncianteRepository.save(anuncianteCadastrado)).thenReturn(anuncianteComNovosDados);

        Anunciante anuncianteAtualizado = anuncianteService.update(1L, anunciante);

        Assertions.assertThat(anuncianteAtualizado).isNotNull();
        Assertions.assertThat(anuncianteAtualizado.getId()).isEqualTo(1L);
        Assertions.assertThat(anuncianteAtualizado.getCnpj()).isEqualTo("87571657000197");
        Assertions.assertThat(anuncianteAtualizado.getRazaoSocial()).isEqualTo("Warley Kennedy Figueiredo");
        Assertions.assertThat(anuncianteAtualizado.getNomeFantasia()).isEqualTo("REVISTA_IN_FOCO");
        Assertions.assertThat(anuncianteAtualizado.getEndereco()).isNotNull();
        Assertions.assertThat(anuncianteAtualizado.getEmail()).isEqualTo("warley-ft@hotmail.com");
        Assertions.assertThat(anuncianteAtualizado.getSite()).isEqualTo("www.revista_in_foco.com");
    }

    @Test
    @DisplayName(value = "Dado um id quando tentar deletar um anunciante então deve excluir o anunciante do banco de dados")
    void DadoUmIdQuandoTentarDeletarUmAnuncianteEntaoDeveExcluirOAnuncianteDoBancoDeDados() {

        when(anuncianteRepository.findById(1L)).thenReturn(Optional.of(anuncianteCadastrado));

        assertDoesNotThrow(() -> anuncianteService.delete(1L));
    }

    @Test
    @DisplayName(value = "Dado um id de um anunciante que não está cadastrado quando tentar recuperar o anunciante pelo id então deve lançar a exception AnuncianteNaoEncontradoException")
    void DadoUmIdDeUmAnuncianteQueNaoEstaCadastradoQuandoTentarRecuperarOAnunciantePeloIdEntaoDeveLancarAExceptionAnuncianteNaoEncontradoException() {

        when(anuncianteRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        assertThrows(AnuncianteNaoEncontradoException.class, () -> anuncianteService.findById(1L));
    }

    @Test
    @DisplayName(value = "Dado um id de um anunciante que não está cadastrado quando tentar atualizar o anunciante pelo id então deve lançar a exception AnuncianteNaoEncontradoException")
    void DadoUmIdDeUmAnuncianteQueNaoEstaCadastradoQuandoTentarAtualizarOAnunciantePeloIdEntaoDeveLancarAExceptionAnuncianteNaoEncontradoException() {

        when(anuncianteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AnuncianteNaoEncontradoException.class, () -> anuncianteService.update(1L, new Anunciante()));
    }

    @Test
    @DisplayName(value = "Dado um id de um anunciante que não está cadastrado quando tentar excluir o anunciante pelo id então deve lançar a exception AnuncianteNaoEncontradoException")
    void DadoUmIdDeUmAnuncianteQueNaoEstaCadastradoQuandoTentarExcluirOAnunciantePeloIdEntaoDeveLancarAExceptionAnuncianteNaoEncontradoException() {

        when(anuncianteRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        assertThrows(AnuncianteNaoEncontradoException.class, () -> anuncianteService.delete(1L));
    }

    @Test
    @DisplayName(value = "Dado um AnuncianteRequestDTO quando tentar converter para a entidade Anunciante então deve retornar a entidade anunciante")
    void DadoUmAnuncianteRequestDTOQuandoTentarConverterParaAEntidadeAnuncianteEntaoDeveRetornarAEntidadeAnunciante() {

        when(modelMapper.map(anuncianteRequestDTO, Anunciante.class)).thenReturn(anunciante);

        Anunciante anunciante = anuncianteService.toEntity(anuncianteRequestDTO);

        Assertions.assertThat(anunciante).isNotNull();
        Assertions.assertThat(anunciante.getId()).isEqualTo(1L);
        Assertions.assertThat(anunciante.getCnpj()).isEqualTo("87571657000197");
        Assertions.assertThat(anunciante.getRazaoSocial()).isEqualTo("Warley Kennedy Figueiredo");
        Assertions.assertThat(anunciante.getNomeFantasia()).isEqualTo("REVISTA_IN_FOCO");
        Assertions.assertThat(anunciante.getEndereco()).isNotNull();
        Assertions.assertThat(anunciante.getEmail()).isEqualTo("warley-ft@hotmail.com");
        Assertions.assertThat(anunciante.getSite()).isEqualTo("www.revista_in_foco.com");
    }

    @Test
    @DisplayName(value = "Dado um AnuncianteUpdateRequestDTO quando tentar converter para a entidade Anunciante então deve retornar a entidade anunciante")
    void DadoUmAnuncianteRequestUpdateDTOQuandoTentarConverterParaAEntidadeAnuncianteEntaoDeveRetornarAEntidadeAnunciante() {
        when(modelMapper.map(anuncianteRequestDTO, Anunciante.class)).thenReturn(anuncianteCadastrado);

        Anunciante anunciante = anuncianteService.toEntity(anuncianteRequestDTO);

        Assertions.assertThat(anunciante).isNotNull();
        Assertions.assertThat(anunciante.getId()).isEqualTo(1L);
        Assertions.assertThat(anunciante.getCnpj()).isEqualTo("87571657000197");
        Assertions.assertThat(anunciante.getRazaoSocial()).isEqualTo("Warley Kennedy Figueiredo");
        Assertions.assertThat(anunciante.getNomeFantasia()).isEqualTo("REVISTA_IN_FOCO");
        Assertions.assertThat(anunciante.getEndereco()).isNotNull();
        Assertions.assertThat(anunciante.getEmail()).isEqualTo("warley-ft@hotmail.com");
        Assertions.assertThat(anunciante.getSite()).isEqualTo("www.revista_in_foco.com");
    }

    @Test
    @DisplayName(value = "Dado uma entidade Anunciante ao tentar converter para AnuncianteResponseDTO então deve retornar anuncianteResponseDTO")
    void DadoUmaEntidadeAnuncianteAoTentarConverterParaAnuncianteResponseDTOEntaoDeveRetornarAnuncianteResponseDTO() {
        EstadoResponseDTO estadoResponseDTO = new EstadoResponseDTO(1L, "Ceará", "CE");
        CidadeResponseDTO cidadeResponseDTO = new CidadeResponseDTO(1L, "Jaguaribe", estadoResponseDTO);
        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO(1L, "Rua Cidade Lion", cidadeResponseDTO, "74185296", "184", "Apto: 32", "Jardim Anny");
        AnuncianteResponseDTO anuncianteResponseDTO = new AnuncianteResponseDTO(1L, "87571657000197", "Warley Kennedy Figueiredo", "REVISTA_IN_FOCO", enderecoResponseDTO, "warley-ft@hotmail.com", "www.revista_in_foco.com", contatoResponseDTO);

        when(modelMapper.map(anunciante, AnuncianteResponseDTO.class)).thenReturn(anuncianteResponseDTO);

        AnuncianteResponseDTO anuncianteResponse = anuncianteService.toResponse(anunciante);

        Assertions.assertThat(anuncianteResponse).isNotNull();
        Assertions.assertThat(anuncianteResponse.getId()).isEqualTo(1L);
        Assertions.assertThat(anuncianteResponse.getCnpj()).isEqualTo("87571657000197");
        Assertions.assertThat(anuncianteResponse.getRazaoSocial()).isEqualTo("Warley Kennedy Figueiredo");
        Assertions.assertThat(anuncianteResponse.getNomeFantasia()).isEqualTo("REVISTA_IN_FOCO");
        Assertions.assertThat(anuncianteResponse.getEndereco()).isNotNull();
        Assertions.assertThat(anuncianteResponse.getEmail()).isEqualTo("warley-ft@hotmail.com");
        Assertions.assertThat(anuncianteResponse.getSite()).isEqualTo("www.revista_in_foco.com");
    }
}
