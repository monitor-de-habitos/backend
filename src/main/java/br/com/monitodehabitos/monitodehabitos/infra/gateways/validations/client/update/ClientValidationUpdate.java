package br.com.monitodehabitos.monitodehabitos.infra.gateways.validations.client.update;

import br.com.monitodehabitos.monitodehabitos.domain.entities.Client;
import br.com.monitodehabitos.monitodehabitos.domain.exception.UserException;

public interface ClientValidationUpdate {
    void validate(String id, Client updateClient) throws UserException;
}
