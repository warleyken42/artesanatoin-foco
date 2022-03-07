package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.AnuncianteRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.AnuncianteResponseDTO;
import br.com.revistainfoco.revista.domain.entity.*;
import br.com.revistainfoco.revista.errors.exceptions.AnuncianteJaCadastradoException;
import br.com.revistainfoco.revista.errors.exceptions.AnuncianteNaoEncontradoException;
import br.com.revistainfoco.revista.repository.AnuncianteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AnuncianteService {

    private final AnuncianteRepository repository;
    private final EnderecoService enderecoService;
    private final CidadeService cidadeService;
    private final EstadoService estadoService;
    private final ContatoService contatoService;
    private final ModelMapper modelMapper;

    @Autowired
    public AnuncianteService(AnuncianteRepository repository, EnderecoService enderecoService, CidadeService cidadeService, EstadoService estadoService, ContatoService contatoService, ModelMapper modelMapper) {
        this.repository = repository;
        this.enderecoService = enderecoService;
        this.cidadeService = cidadeService;
        this.estadoService = estadoService;
        this.contatoService = contatoService;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public Anunciante create(Anunciante anunciante) {
        Optional<Anunciante> anuncianteCadastrado = repository.findByCnpj(anunciante.getCnpj());
        if (anuncianteCadastrado.isPresent()) {
            throw new AnuncianteJaCadastradoException("Anunciante já cadastrado");
        }
        Endereco endereco = anunciante.getEndereco();
        Cidade cidade = anunciante.getEndereco().getCidade();
        Estado estado = anunciante.getEndereco().getCidade().getEstado();

        Optional<Estado> estadoCadastrado = estadoService.findByNomeAndUf(estado.getNome(), estado.getUf());
        Optional<Cidade> cidadeCadastrada = cidadeService.findByNome(cidade.getNome());
        boolean enderecoCadastrado = enderecoService.findAll().contains(endereco);

        if (estadoCadastrado.isPresent()) {
            Estado estadoCriado = estadoService.create(estado);
            anunciante.getEndereco().getCidade().setEstado(estadoCriado);
        }

        if (cidadeCadastrada.isPresent()) {
            Cidade cidadeCriada = cidadeService.create(cidade);
            anunciante.getEndereco().setCidade(cidadeCriada);
        }

        if (!enderecoCadastrado) {
            Endereco novoEndereco = enderecoService.create(endereco);
            anunciante.setEndereco(novoEndereco);
        }

        Optional<Contato> contatoCadastrado = contatoService.findByCelular(anunciante.getContato().getCelular());

        Contato contato;

        contato = contatoCadastrado.orElseGet(() -> contatoService.create(anunciante.getContato()));
        anunciante.setContato(contato);
        return repository.save(anunciante);
    }

    public List<Anunciante> findAll() {
        return repository.findAll();
    }

    public Anunciante findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AnuncianteNaoEncontradoException("Anunciante não encontrado"));
    }

    public Anunciante update(Long id, Anunciante anunciante) {
        Anunciante anunciantesCadastrado = this.findById(id);

        if (anunciantesCadastrado != null) {
            anunciante.setId(id);
        }

        Endereco endereco = anunciante.getEndereco();
        Cidade cidade = anunciante.getEndereco().getCidade();
        Estado estado = anunciante.getEndereco().getCidade().getEstado();
        Contato contato = anunciante.getContato();


        Optional<Estado> estadoCadastrado = estadoService.findByNomeAndUf(estado.getNome(), estado.getUf());
        Optional<Cidade> cidadeCadastrada = cidadeService.findByNome(cidade.getNome());

        Optional<Contato> contatoCadastrado = contatoService.findByCelular(contato.getCelular());
        boolean enderecoCadastrado = enderecoService.findAll().contains(endereco);


        if (!contatoCadastrado.isPresent()) {
            contatoService.create(contato);
        } else {
            Contato contatoExistente = contatoCadastrado.get();
            contatoExistente.setNome(contato.getNome());
            contatoExistente.setSobrenome(contato.getSobrenome());
            contatoExistente.setCelular(contato.getCelular());
            contatoExistente.setEmail(contato.getEmail());
            anunciante.setContato(contatoExistente);
        }

        if (!estadoCadastrado.isPresent()) {
            Estado estadoCriado = estadoService.create(estado);
            anunciante.getEndereco().getCidade().setEstado(estadoCriado);
        } else {
            Estado estadoExistente = estadoCadastrado.get();
            estadoExistente.setNome(estado.getNome());
            estadoExistente.setUf(estado.getUf());
            Estado estadoAtualizado = estadoService.update(estadoExistente.getId(), estadoExistente);
            anunciante.getEndereco().getCidade().setEstado(estadoAtualizado);
        }

        if (!cidadeCadastrada.isPresent()) {
            Cidade cidadeCriada = cidadeService.create(cidade);
            anunciante.getEndereco().setCidade(cidadeCriada);
        } else {
            Cidade cidadeExistente = cidadeCadastrada.get();
            cidadeExistente.setNome(cidade.getNome());
            Cidade cidadeAtualizada = cidadeService.update(cidadeExistente.getId(), cidade);
            assert anunciantesCadastrado != null;
            anunciantesCadastrado.getEndereco().setCidade(cidadeAtualizada);
        }


        if (!enderecoCadastrado) {
            Endereco novoEndereco = enderecoService.create(endereco);
            anunciante.setEndereco(novoEndereco);
        } else {
            assert anunciantesCadastrado != null;
            Endereco enderecoAtual = anunciantesCadastrado.getEndereco();
            enderecoAtual.setLogradouro(endereco.getLogradouro());
            enderecoAtual.setCep(endereco.getCep());
            enderecoAtual.setNumero(endereco.getNumero());
            enderecoAtual.setComplemento(endereco.getComplemento());
            enderecoAtual.setBairro(endereco.getBairro());
            Endereco enderecoAtualizado = enderecoService.update(enderecoAtual.getId(), enderecoAtual);
            anunciante.setEndereco(enderecoAtualizado);
        }
        return repository.save(anunciante);
    }

    public void delete(Long id) {
        Anunciante anuncianteCadastrado = repository.findById(id).orElseThrow(() -> new AnuncianteNaoEncontradoException("Anunciante não encontrado"));
        repository.delete(anuncianteCadastrado);
    }

    public Anunciante toEntity(AnuncianteRequestDTO anuncianteRequestDTO) {
        return modelMapper.map(anuncianteRequestDTO, Anunciante.class);
    }

    public AnuncianteResponseDTO toResponse(Anunciante anunciante) {
        return modelMapper.map(anunciante, AnuncianteResponseDTO.class);
    }
}
