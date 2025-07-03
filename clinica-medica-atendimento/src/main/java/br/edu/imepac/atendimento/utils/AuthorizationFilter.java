package br.edu.imepac.atendimento.utils;

import br.edu.imepac.atendimento.exception.ActionClinicaMedicaException;
import br.edu.imepac.atendimento.exception.AuthenticationClinicaMedicaException;
import br.edu.imepac.comum.dtos.perfil.PerfilDto;
import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.services.AutenticadorService;
import br.edu.imepac.comum.services.AutorizadorService;
import br.edu.imepac.comum.services.PerfilService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final PerfilService perfilService;
    private final AutenticadorService autenticadorService;
    private final AutorizadorService autorizadorService;

    private static final Map<String, String> REQUIRED_PERMISSIONS = new HashMap<>();

    static {
        REQUIRED_PERMISSIONS.put("POST /clinica-medica-atendimento/prontuarios", "cadastrarProntuario");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-atendimento/prontuarios/listar", "listarProntuario");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-atendimento/prontuarios/{id}", "lerProntuario");
        REQUIRED_PERMISSIONS.put("PUT /clinica-medica-atendimento/prontuarios/{id}", "atualizarProntuario");
        REQUIRED_PERMISSIONS.put("DELETE /clinica-medica-atendimento/prontuarios/{id}", "deletarProntuario");
    }

    public AuthorizationFilter(
            PerfilService perfilService,
            AutenticadorService autenticadorService,
            AutorizadorService autorizadorService
    ) {
        this.autenticadorService = autenticadorService;
        this.autorizadorService = autorizadorService;
        this.perfilService = perfilService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        if (path.startsWith("/clinica-medica-atendimento/swagger-ui") || path.startsWith("/clinica-medica-atendimento/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String usuario = Optional.ofNullable(request.getHeader("usuario"))
                    .orElseThrow(() -> new AuthenticationClinicaMedicaException("Usuário não encontrado no cabeçalho!"));
            String senha = Optional.ofNullable(request.getHeader("senha"))
                    .orElseThrow(() -> new AuthenticationClinicaMedicaException("Senha não encontrada no cabeçalho!"));

            Optional<Funcionario> optionalFuncionario = autenticadorService.autenticar(usuario, senha);

            Funcionario funcionarioAutenticado = optionalFuncionario
                    .orElseThrow(() -> new AuthenticationClinicaMedicaException("Credenciais inválidas: Usuário ou senha incorretos."));

            if (funcionarioAutenticado.getPerfil() == null) {
                throw new AuthenticationClinicaMedicaException("Perfil não associado ao funcionário autenticado.");
            }
            PerfilDto perfilUsuario = perfilService.buscarPerfilPorId((long) funcionarioAutenticado.getPerfil().getId());

            String normalizedPath = mapPathToGeneric(path);
            String requiredActionKey = method + " " + normalizedPath;
            String requiredAction = REQUIRED_PERMISSIONS.get(requiredActionKey);

            if (requiredAction == null) {
                throw new ActionClinicaMedicaException("Acesso negado: A rota solicitada não possui permissão definida ou é inválida.");
            }

            boolean temPermissao = autorizadorService.checkPermission(perfilUsuario, requiredAction);

            if (!temPermissao) {
                throw new ActionClinicaMedicaException("Acesso negado: Usuário não tem permissão para realizar a ação: " + requiredAction);
            }

            filterChain.doFilter(request, response);

        } catch (AuthenticationClinicaMedicaException | ActionClinicaMedicaException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro inesperado no filtro de autorização! Detalhes: " + e.getMessage());
        }
    }

    private String mapPathToGeneric(String path) {
        String resourcePrefix = "/clinica-medica-atendimento/prontuarios/";

        if (path.startsWith(resourcePrefix)) {
            String suffix = path.substring(resourcePrefix.length());

            if (suffix.isEmpty() || suffix.equals("listar")) {
                return path;
            }

            if (suffix.matches("\\d+")) {
                return resourcePrefix + "{id}";
            }
        }

        return path;
    }
}