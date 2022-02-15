package br.com.revistainfoco.revista.resources;

import br.com.revistainfoco.revista.domain.dto.request.CidadeRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.CidadeUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.CidadeResponseDTO;
import br.com.revistainfoco.revista.errors.ErrorDetail;
import br.com.revistainfoco.revista.services.CidadeService;
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
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {

    private final CidadeService service;

    @Autowired
    public CidadeResource(CidadeService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastra uma Cidade", description = "Cadastra uma nova cidade na base de dados", tags = {"cidades"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cidade cadastrada com sucesso"),
            @ApiResponse(responseCode = "409", description = "Cidade já cadastrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CidadeResponseDTO> create(@RequestBody @Valid CidadeRequestDTO cidadeRequestDTO) {
        CidadeResponseDTO cidadeCriado = service.create(cidadeRequestDTO);
        return new ResponseEntity<>(cidadeCriado, HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna todas as cidades cadastradas", description = "Retorna todas as cidades cadastradas", tags = {"cidades"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidades encontrados"),
            @ApiResponse(responseCode = "204", description = "Não há cidades cadastradas", content = @Content(schema = @Schema()))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readAll() {
        List<CidadeResponseDTO> cidades = service.readAll();
        if (cidades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(cidades, HttpStatus.OK);
    }

    @Operation(summary = "Busca uma cidade cadastrada pelo seu id", description = "Busca uma cidade cadastrado pelo seu id", tags = {"cidades"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidade encontrada"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CidadeResponseDTO> readById(@PathVariable("id") Long id) {
        CidadeResponseDTO cidadeResponseDTO = service.readById(id);
        return new ResponseEntity<>(cidadeResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza os dados de uma cidade cadastrado", description = "Atualiza os dados de uma cidade cadastrado", tags = {"cidades"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidade atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CidadeResponseDTO> update(@PathVariable("id") Long id, @RequestBody @Valid CidadeUpdateRequestDTO cidadeUpdateRequestDTO) {
        CidadeResponseDTO cidadeResponseDTO = service.update(id, cidadeUpdateRequestDTO);
        return new ResponseEntity<>(cidadeResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Exclui uma cidade permanentemente", description = "Exclui uma cidade permanentemente", tags = {"cidades"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cidade excluida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
