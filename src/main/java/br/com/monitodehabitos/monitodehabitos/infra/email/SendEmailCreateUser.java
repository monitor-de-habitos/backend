package br.com.monitodehabitos.monitodehabitos.infra.email;

import br.com.monitodehabitos.monitodehabitos.infra.controller.clientDto.CreateClientDto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class SendEmailCreateUser {
    private final SendEmail send;


    public SendEmailCreateUser(SendEmail send) {
        this.send = send;
    }

    @Async("asyncSendEmail")
    public void send(CreateClientDto create) {
        send.sendEmail(
                "Usuário criado com sucesso",
                create.email(),
                "Parabéns!" + create.name() + ", sua conta foi criada com sucesso, utilize sua senha e e-mail. E boa sorte."
        );
    }
}
