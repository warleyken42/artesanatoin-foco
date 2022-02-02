package br.com.revistainfoco.revista.resources;

import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/estado")
public class EstadoResource {

    //TODO: Implementar documentacao com swagger openapi

    private final EstadoService service;

    @Autowired
    public EstadoResource(EstadoService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Estado> create(@RequestBody Estado estado) {
        Estado estadoCriado = service.create(estado);
        return new ResponseEntity<>(estadoCriado, HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Estado>> readAll() {
        List<Estado> estados = service.readAll();
        return new ResponseEntity<>(estados, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Estado> readById(@PathVariable("id") BigInteger id) {
        Estado estado = service.readById(id);
        return new ResponseEntity<>(estado, HttpStatus.OK);
    }

    @PatchMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Estado> update(@PathVariable("id") BigInteger id, @RequestBody Estado estado) {
        Estado estadoAtualizado = service.update(id, estado);
        return new ResponseEntity<>(estadoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") BigInteger id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
