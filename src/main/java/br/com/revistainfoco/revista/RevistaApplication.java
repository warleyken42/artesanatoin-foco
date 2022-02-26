package br.com.revistainfoco.revista;

import br.com.revistainfoco.revista.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RevistaApplication implements CommandLineRunner {


    private final EstadoService service;

    @Autowired
    public RevistaApplication(EstadoService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(RevistaApplication.class, args);
    }

    @Override
    public void run(String... args) {
        service.populateAddressTable();
    }
}
