package br.com.monitodehabitos.monitodehabitos.infra.gateways.validations.client.create;

import br.com.monitodehabitos.monitodehabitos.domain.entities.Client;
import br.com.monitodehabitos.monitodehabitos.domain.exception.UserException;

public interface ClientValidation {
    void validate(Client data) throws UserException;
}
