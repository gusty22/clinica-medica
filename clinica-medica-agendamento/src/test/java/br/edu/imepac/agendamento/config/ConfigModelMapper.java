package br.edu.imepac.agendamento.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * **@Configuration**: Indica que esta classe declara um ou mais beans do Spring.
 * É usada para definir configurações e beans para o contexto da aplicação.
 */
@Configuration
public class ConfigModelMapper {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

