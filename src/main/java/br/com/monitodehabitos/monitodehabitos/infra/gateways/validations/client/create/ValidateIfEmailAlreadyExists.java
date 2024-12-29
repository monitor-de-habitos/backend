package br.com.monitodehabitos.monitodehabitos.infra.gateways.validations.client.create;

import br.com.monitodehabitos.monitodehabitos.domain.entities.Client;
import br.com.monitodehabitos.monitodehabitos.domain.enums.UserErroEnum;
import br.com.monitodehabitos.monitodehabitos.domain.exception.UserException;
import br.com.monitodehabitos.monitodehabitos.infra.persistence.ClientEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ValidateEmailCreate")
public class ValidateIfEmailAlreadyExists implements ClientValidation {

    @Autowired
    private ClientEntityRepository clientEntityRepository;

    @Override
    public void validate(Client data) throws UserException {

        var exists = clientEntityRepository.existsByEmail(data.getEmail());
        if (exists) {
            throw new UserException(UserErroEnum.USR0005.getMessage());
        }
    }

}
