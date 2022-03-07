package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.AnuncioRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.AnuncioResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Anuncio;
import br.com.revistainfoco.revista.errors.exceptions.AnuncioNaoEncontradoException;
import br.com.revistainfoco.revista.repository.AnuncioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnuncioService {

    private final AnuncioRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public AnuncioService(AnuncioRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Anuncio create(Anuncio anuncio) {
        return repository.save(anuncio);
    }

    public List<Anuncio> findAll() {
        return repository.findAll();
    }

    public Anuncio findById(long id) {
        return repository.findById(id).orElseThrow(() -> new AnuncioNaoEncontradoException("Anuncio não encontrado"));
    }

    public Anuncio update(Long id, Anuncio anuncio) {
        Anuncio anuncioCadastrado = this.findById(id);

        anuncioCadastrado.setTamanho(anuncio.getTamanho());
        anuncioCadastrado.setValor(anuncio.getValor());

        return repository.save(anuncioCadastrado);
    }

    public void delete(long id) {
        Anuncio anuncioCadastrado = this.findById(id);
        repository.delete(anuncioCadastrado);
    }

    public Anuncio toEntity(AnuncioRequestDTO anuncioRequestDTO) {
        return modelMapper.map(anuncioRequestDTO, Anuncio.class);
    }

    public AnuncioResponseDTO toResponse(Anuncio anuncio) {
        return modelMapper.map(anuncio, AnuncioResponseDTO.class);
    }
}
