package br.com.revistainfoco.revista.resources;

import br.com.revistainfoco.revista.domain.dto.request.AnuncioRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.AnuncioUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.AnuncioResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Anuncio;
import br.com.revistainfoco.revista.errors.ErrorDetail;
import br.com.revistainfoco.revista.services.AnuncioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/anuncios")
public class AnuncioResource {

    private final AnuncioService service;

    @Autowired
    public AnuncioResource(AnuncioService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastra um anuncio", description = "Cadastra um novo anuncio na base de dados", tags = {"anuncios"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Anuncio cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Anuncio já cadastrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnuncioResponseDTO> create(@RequestBody @Valid AnuncioRequestDTO anuncioRequestDTO) {
        Anuncio anuncioCadastrado = service.create(service.toEntity(anuncioRequestDTO));
        return new ResponseEntity<>(service.toResponse(anuncioCadastrado), HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna todos os anuncios cadastrados", description = "Retorna todos os anuncios cadastrados", tags = {"anuncios"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anuncio encontrados"),
            @ApiResponse(responseCode = "204", description = "Não há anuncios cadastrados", content = @Content(schema = @Schema()))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        List<AnuncioResponseDTO> anunciosCadastrados = new ArrayList<>();
        service.findAll().forEach(anuncio -> {
            anunciosCadastrados.add(service.toResponse(anuncio));
        });
        if (anunciosCadastrados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(anunciosCadastrados, HttpStatus.OK);
    }

    @Operation(summary = "Busca um anucio cadastrado pelo seu id", description = "Busca um anuncio cadastrado pelo seu id", tags = {"anuncios"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anuncio encontrado"),
            @ApiResponse(responseCode = "404", description = "Anuncio não encontrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnuncioResponseDTO> findById(@PathVariable("id") Long id) {
        Anuncio anuncioCadastrado = service.findById(id);
        return new ResponseEntity<>(service.toResponse(anuncioCadastrado), HttpStatus.OK);
    }

    @Operation(summary = "Atualiza os dados de um anuncio cadastrado", description = "Atualiza os dados de um anuncio cadastrado", tags = {"anuncios"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anuncio atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anuncio não encontrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnuncioResponseDTO> update(@PathVariable("id") Long id, @RequestBody @Valid AnuncioUpdateRequestDTO anuncioUpdateRequestDTO) {
        Anuncio anuncioAtualizado = service.update(id, service.toEntity(anuncioUpdateRequestDTO));
        return new ResponseEntity<>(service.toResponse(anuncioAtualizado), HttpStatus.OK);
    }

    @Operation(summary = "Exclui um anuncio permanentemente", description = "Exclui um anuncio permanentemente", tags = {"anuncios"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Anuncio excluido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anuncio não encontrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
