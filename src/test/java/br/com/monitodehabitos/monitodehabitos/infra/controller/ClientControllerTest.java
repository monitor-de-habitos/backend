package br.com.monitodehabitos.monitodehabitos.infra.controller;

import br.com.monitodehabitos.monitodehabitos.application.gateway.ClientRepository;
import br.com.monitodehabitos.monitodehabitos.application.useCases.Client.DeleteClient;
import br.com.monitodehabitos.monitodehabitos.application.useCases.Client.FindClient;
import br.com.monitodehabitos.monitodehabitos.application.useCases.Client.UpdateClient;
import br.com.monitodehabitos.monitodehabitos.domain.entities.Client;
import br.com.monitodehabitos.monitodehabitos.domain.factories.FactoryClient;
import br.com.monitodehabitos.monitodehabitos.infra.email.SendEmailCreateUser;
import br.com.monitodehabitos.monitodehabitos.infra.gateways.ClientEntityMapper;
import br.com.monitodehabitos.monitodehabitos.infra.infras.security.TokenService;
import br.com.monitodehabitos.monitodehabitos.infra.persistence.ClientEntity;
import br.com.monitodehabitos.monitodehabitos.infra.persistence.UserEntity;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClientRepository clientRepository;
    @InjectMocks
    private FactoryClient factoryClient;
    @InjectMocks
    private FindClient findClient;
    @InjectMocks
    private DeleteClient deleteClient;
    @InjectMocks
    private UpdateClient updateClient;
    @MockBean
    private ClientEntityMapper clientEntityMapper;
    @InjectMocks
    private SendEmailCreateUser emailCreateUser;
    @InjectMocks
    private TokenService tokenService;



    @Test
    @DisplayName("Should give back status 201, created.")
    void scenario01() throws Exception {
        //Arrange
        String json =
                """
                                {
                                  "email": "cliente@exemplo.com",
                                  "password": "senha@Segura123",
                                  "name": "João da Silva",
                                  "addressClientDto": {
                                    "cep": "01001-000",
                                    "street": "Rua da Alegria",
                                    "city": "São Paulo",
                                    "state": "SP",
                                    "neighborhood": "Centro",
                                    "number": "123",
                                    "complement": "Apto 45"
                                  }
                                }
                        """;
        //Act
        var response = mockMvc.perform(
                post("/client")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        assertEquals(201, response.getStatus());
    }


    public String login() throws Exception {
       try{
           ClientEntity client = new ClientEntity(
                   UUID.randomUUID().toString(),
                   "teste@email.com",
                   "123456789",
                   "MatheusMozart",
                   null,
                   null,
                   null,
                   null,
                   null,
                   null
           );
           System.out.println((UserEntity) client);
           var token = tokenService.generateToken((UserEntity) client);
           System.out.println(token.toString());
           return token;
       }catch(
              Exception ex
       ){
           throw new RuntimeException(ex.getMessage());
       }

    }


    @Test
    @DisplayName("Should give back status 200 to findClient.")
    void scenario02() throws Exception {
        //Arrange
        ClientEntity client = new ClientEntity(
                UUID.randomUUID().toString(),
                "teste@email.com",
                "123456789",
                "MatheusMozart",
                null,
                null,
                null,
                null,
                null,
                null
        );
        var token = tokenService.generateToken((UserEntity) client);
        System.out.println(token);
        //Act
        var response = mockMvc.perform(
                get("/client")
                        .header("Authorization", "Bearer " + token)
        ).andReturn().getResponse();

        //Assert
        assertEquals(200, response.getStatus());
    }
/*
    @Test
    @DisplayName("Should give back status 204 to delete client - no content.")
    void scenario03() throws Exception {

        //Arrange
        Long id = 1L;
        //Act
        var response = mockMvc.perform(
                delete("/client/1", id)
        ).andReturn().getResponse();

        //Assert
        assertEquals(204, response.getStatus());
    }

    @Test
    @DisplayName("Should give back status 200 when updating a client successfully.")
    void scenario04() throws Exception {

        // Arrange
        Long id = 1L;
        //Arrange
        String json =
                """
                                {
                                  "email": "client-atualizado@exemplo.com"
                                
                                }
                        """;

        // Act
        var response = mockMvc.perform(
                put("/client/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
    }
*/
}