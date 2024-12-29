package br.com.monitodehabitos.monitodehabitos.infra.gateways.validations.client.update;

import br.com.monitodehabitos.monitodehabitos.domain.entities.Client;
import br.com.monitodehabitos.monitodehabitos.domain.enums.UserErroEnum;
import br.com.monitodehabitos.monitodehabitos.domain.exception.UserException;
import br.com.monitodehabitos.monitodehabitos.infra.persistence.ClientEntity;
import br.com.monitodehabitos.monitodehabitos.infra.persistence.ClientEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ValidateEmailUpdate")
public class ValidateIfEmailAlreadyExists implements ClientValidationUpdate {

    @Autowired
    private ClientEntityRepository clientEntityRepository;

    @Override
    public void validate(String id, Client updateClient) throws UserException {
        ClientEntity clientEntity = this.clientEntityRepository.findById(id).orElse(null);
        if(clientEntity != null && !clientEntity.getEmail().equals(updateClient.getEmail())){
            var exists = this.clientEntityRepository.existsByEmail(updateClient.getEmail());
            if(exists){
                throw new UserException(UserErroEnum.USR0005.getMessage());
            }
        }
    }
}
