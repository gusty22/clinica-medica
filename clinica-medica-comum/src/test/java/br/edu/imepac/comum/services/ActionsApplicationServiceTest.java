package br.edu.imepac.comum.services;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionsApplicationServiceTest {

    private final ActionsApplicationService service = new ActionsApplicationService();

    @Test
    void testGetActionsApplication_shouldReturnBooleanFieldsNames() {
        List<String> result = service.getActionsApplication();
        assertTrue(service.getActionsApplication().contains("lerActionsApplication"));
    }
}
