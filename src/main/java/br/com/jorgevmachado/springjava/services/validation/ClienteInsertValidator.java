package br.com.jorgevmachado.springjava.services.validation;

import br.com.jorgevmachado.springjava.domain.Cliente;
import br.com.jorgevmachado.springjava.domain.enumerations.TipoCliente;
import br.com.jorgevmachado.springjava.dto.ClienteInsertDTO;
import br.com.jorgevmachado.springjava.repositories.ClienteRepository;
import br.com.jorgevmachado.springjava.resources.exceptions.FieldMessage;
import br.com.jorgevmachado.springjava.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteInsertDTO> {

    @Autowired
    private ClienteRepository repository;

    @Override
    public void initialize(ClienteInsert constraintAnnotation) { }

    @Override
    public boolean isValid(ClienteInsertDTO objDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        if (objDTO.getTipo().equals(TipoCliente.PESSSOAFISICA.getCodigo()) &&
                !BR.isValidCPF(objDTO.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (objDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) &&
                !BR.isValidCNPJ(objDTO.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        Cliente cliente = repository.findByEmail(objDTO.getEmail());
        if (cliente != null) {
            list.add(new FieldMessage("email", "E-mail já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
