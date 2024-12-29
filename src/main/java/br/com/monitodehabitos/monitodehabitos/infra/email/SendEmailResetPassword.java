package br.com.monitodehabitos.monitodehabitos.infra.email;

import br.com.monitodehabitos.monitodehabitos.infra.controller.clientDto.CreateClientDto;
import br.com.monitodehabitos.monitodehabitos.infra.controller.clientDto.ResetPassordDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class SendEmailResetPassword {
    private final SendEmail send;
    public SendEmailResetPassword(SendEmail send) {
        this.send = send;
    }

    @Async("asyncSendEmail")
    public void send(ResetPassordDTO reset) {
        send.sendEmail(
                "Reset de senha",
                reset.email(),
                "O código para resetar sua senha é: " + reset.code()
        );
    }
}
