package br.edu.imepac.comum.services;

import br.edu.imepac.comum.models.Perfil;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActionsApplicationService {

    public List<String> getActionsApplication() {
        List<String> fields = Arrays.stream(Perfil.class.getDeclaredFields())
                .filter(field -> field.getType().equals(boolean.class))
                .map(Field::getName)
                .collect(Collectors.toList());

        System.out.println("Campos boolean em Perfil: " + fields);
        return fields;
    }

}