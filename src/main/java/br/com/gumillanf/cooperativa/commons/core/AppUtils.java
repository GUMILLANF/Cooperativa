package br.com.gumillanf.cooperativa.commons.core;

import br.com.gumillanf.cooperativa.commons.AppMessageSource;
import br.com.gumillanf.cooperativa.commons.uitls.AppValidationUtils;
import br.com.gumillanf.cooperativa.config.exception.InvalidCpfException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUtils {

    private final AppMessageSource messageSource;

    public boolean validateCpf(String cpf) throws InvalidCpfException {
        try {
            return AppValidationUtils.validCpf(cpf);
        } catch (Exception ex) {
            throw new InvalidCpfException(messageSource.getMessage("vote.invalid-cpf",
                    new Object[] { cpf }));
        }
    }

}
