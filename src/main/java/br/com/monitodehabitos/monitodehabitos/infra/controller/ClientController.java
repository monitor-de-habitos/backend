package br.com.monitodehabitos.monitodehabitos.infra.controller;


import br.com.monitodehabitos.monitodehabitos.application.useCases.Client.CreateClient;
import br.com.monitodehabitos.monitodehabitos.application.useCases.Client.DeleteClient;
import br.com.monitodehabitos.monitodehabitos.application.useCases.Client.FindClient;
import br.com.monitodehabitos.monitodehabitos.application.useCases.Client.UpdateClient;
import br.com.monitodehabitos.monitodehabitos.domain.Address;
import br.com.monitodehabitos.monitodehabitos.domain.entities.Client;
import br.com.monitodehabitos.monitodehabitos.domain.exception.UserException;
import br.com.monitodehabitos.monitodehabitos.domain.factories.FactoryClient;
import br.com.monitodehabitos.monitodehabitos.infra.controller.clientDto.CreateClientDto;
import br.com.monitodehabitos.monitodehabitos.infra.controller.clientDto.HabitResponseDto;
import br.com.monitodehabitos.monitodehabitos.infra.controller.clientDto.UpdateClientDto;
import br.com.monitodehabitos.monitodehabitos.infra.email.SendEmailCreateUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.monitodehabitos.monitodehabitos.infra.utils.ApplicationLogs.log;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final CreateClient createClient;
    private final FactoryClient factoryClient;
    private final FindClient findClient;
    private final DeleteClient deleteClient;
    private final UpdateClient updateClient;
    private final SendEmailCreateUser emailCreateUser;

    public ClientController(CreateClient createClient, FactoryClient factoryClient, FindClient findClient, DeleteClient deleteClient, UpdateClient updateClient, SendEmailCreateUser emailCreateUser) {
        this.createClient = createClient;
        this.factoryClient = factoryClient;
        this.findClient = findClient;
        this.deleteClient = deleteClient;
        this.updateClient = updateClient;
        this.emailCreateUser = emailCreateUser;
    }

    @PostMapping()
    private ResponseEntity<ResponseDto> create(@RequestBody @Valid CreateClientDto dto) {
        log.info("Início da criação do usuário::UserController");
        Client client = factoryClient.withoutCreatedatAndUpdatedatParameters(dto.email(), dto.password(), dto.name(),
                new Address(
                        dto.addressClientDto().cep(), dto.addressClientDto().street(), dto.addressClientDto().city(),
                        dto.addressClientDto().state(), dto.addressClientDto().neighborhood(), dto.addressClientDto().number(), dto.addressClientDto().complement()
                ));
        this.createClient.create(client);
        log.info("Fim da criação do usuário::UserController");
        emailCreateUser.send(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("Usuário criado com sucesso"));
    }

    @GetMapping("/{id}")
    private ResponseEntity<HabitResponseDto> findByID(@PathVariable("id") String id) {
        log.info("Início da busca do usuário por id::UserController");
        Client client = this.findClient.findClient(id);
        log.info("Fim da busca do usuário por id::UserController");
        return ResponseEntity.ok().body(new HabitResponseDto(client));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity delete(@PathVariable("id") String id) {
        log.info("Início da remoção do usuário por id::UserController");
        this.deleteClient.delete(id);
        log.info("Fim da remoção do usuário por id::UserController");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<ResponseDto> update(@PathVariable("id") String id, @RequestBody UpdateClientDto dto) throws UserException {
        log.info("Início da atualização do usuário::UserController");
        Client updatesClient = factoryClient.updateClient(dto.email(), dto.password(), dto.name(),
                new Address(
                        dto.cep(), dto.street(), dto.city(),
                        dto.state(), dto.neighborhood(), dto.number(), dto.complement()
                ));
        this.updateClient.update(id, updatesClient);
        log.info("Fim da atualização do usuário::UserController");
        return ResponseEntity.ok(new ResponseDto("Atualização realizada com sucesso."));
    }

}
