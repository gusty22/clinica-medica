// clinica-medica-comum/src/main/java/br/edu/imepac/comum/services/AutorizadorService.java
package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.perfil.PerfilDto;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field; // Import para reflexão

@Service
public class AutorizadorService {

    /**
     * Verifica se um Perfil possui uma permissão específica.
     * @param perfil O DTO do perfil do usuário.
     * @param acao A string que representa a permissão (ex: "cadastrarFuncionario").
     * @return true se o perfil tiver a permissão, false caso contrário.
     */
    public boolean checkPermission(PerfilDto perfil, String acao) {
        if (perfil == null || acao == null || acao.trim().isEmpty()) {
            return false; // Perfil ou ação inválidos
        }

        // Para administradores, permite todas as ações.
        // A comparação de nome do perfil pode ser case-insensitive se desejar.
        if ("ADMINISTRADOR".equalsIgnoreCase(perfil.getNome())) {
            return true;
        }

        try {
            // Usa reflexão para acessar o campo booleano correspondente à ação
            // O nome da ação (acao) deve corresponder exatamente ao nome do campo no PerfilDto
            Field permissionField = PerfilDto.class.getDeclaredField(acao);
            permissionField.setAccessible(true); // Permite acesso a campos privados

            // Retorna o valor booleano do campo para o perfil dado
            return permissionField.getBoolean(perfil);
        } catch (NoSuchFieldException e) {
            // Se a 'ação' não corresponder a um campo de permissão no PerfilDto,
            // significa que a ação é inválida ou não reconhecida para autorização direta via Perfil.
            // Logar este erro é crucial para depuração.
            System.err.println("Ação de permissão desconhecida no PerfilDto: " + acao);
            return false;
        } catch (IllegalAccessException e) {
            // Erro de acesso ao campo, geralmente não deveria acontecer com setAccessible(true)
            System.err.println("Erro de acesso ao campo de permissão no PerfilDto: " + acao + " - " + e.getMessage());
            return false;
        }
    }
}