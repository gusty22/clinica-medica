// clinica-medica-administrativo/src/main/java/br/edu/imepac/administrativo/utils/AuthorizationFilter.java
package br.edu.imepac.administrativo.utils;

import br.edu.imepac.administrativo.exception.ActionClinicaMedicaException;
import br.edu.imepac.administrativo.exception.AuthenticationClinicaMedicaException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final PerfilService perfilService;
    private final AutenticadorService autenticadorService;
    private final AutorizadorService autorizadorService;

    private static final Map<String, String> REQUIRED_PERMISSIONS = new HashMap<>();

    static {
        REQUIRED_PERMISSIONS.put("POST /clinica-medica-administrativo/funcionarios", "cadastrarFuncionario");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/funcionarios/listar", "listarFuncionario");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/funcionarios/{id}", "lerFuncionario");
        REQUIRED_PERMISSIONS.put("PUT /clinica-medica-administrativo/funcionarios/{id}", "atualizarFuncionario");
        REQUIRED_PERMISSIONS.put("DELETE /clinica-medica-administrativo/funcionarios/{id}", "deletarFuncionario");

        REQUIRED_PERMISSIONS.put("POST /clinica-medica-administrativo/pacientes", "cadastrarPaciente");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/pacientes/listar", "listarPaciente");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/pacientes/{id}", "lerPaciente");
        REQUIRED_PERMISSIONS.put("PUT /clinica-medica-administrativo/pacientes/{id}", "atualizarPaciente");
        REQUIRED_PERMISSIONS.put("DELETE /clinica-medica-administrativo/pacientes/{id}", "deletarPaciente");

        REQUIRED_PERMISSIONS.put("POST /clinica-medica-administrativo/especialidades", "cadastrarEspecialidade");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/especialidades/listar", "listarEspecialidade");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/especialidades/{id}", "lerEspecialidade");
        REQUIRED_PERMISSIONS.put("PUT /clinica-medica-administrativo/especialidades/{id}", "atualizarEspecialidade");
        REQUIRED_PERMISSIONS.put("DELETE /clinica-medica-administrativo/especialidades/{id}", "deletarEspecialidade");

        REQUIRED_PERMISSIONS.put("POST /clinica-medica-administrativo/convenios", "cadastrarConvenio");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/convenios/listar", "listarConvenio");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/convenios/{id}", "lerConvenio");
        REQUIRED_PERMISSIONS.put("PUT /clinica-medica-administrativo/convenios/{id}", "atualizarConvenio");
        REQUIRED_PERMISSIONS.put("DELETE /clinica-medica-administrativo/convenios/{id}", "deletarConvenio");

        REQUIRED_PERMISSIONS.put("POST /clinica-medica-administrativo/perfis", "cadastrarFuncionario");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/perfis/listar", "listarFuncionario");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/perfis/{id}", "lerFuncionario");
        REQUIRED_PERMISSIONS.put("PUT /clinica-medica-administrativo/perfis/{id}", "atualizarFuncionario");
        REQUIRED_PERMISSIONS.put("DELETE /clinica-medica-administrativo/perfis/{id}", "deletarFuncionario");

        REQUIRED_PERMISSIONS.put("POST /clinica-medica-administrativo/prontuarios", "cadastrarProntuario");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/prontuarios/listar", "listarProntuario");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/prontuarios/{id}", "lerProntuario");
        REQUIRED_PERMISSIONS.put("PUT /clinica-medica-administrativo/prontuarios/{id}", "atualizarProntuario");
        REQUIRED_PERMISSIONS.put("DELETE /clinica-medica-administrativo/prontuarios/{id}", "deletarProntuario");

        REQUIRED_PERMISSIONS.put("POST /clinica-medica-administrativo/consultas", "cadastrarConsulta");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/consultas/listar", "listarConsulta");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/consultas/{id}", "lerConsulta");
        REQUIRED_PERMISSIONS.put("PUT /clinica-medica-administrativo/consultas/{id}", "atualizarConsulta");
        REQUIRED_PERMISSIONS.put("DELETE /clinica-medica-administrativo/consultas/{id}", "deletarConsulta");

        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/actions-application", "lerActionsApplication");
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

        if (path.startsWith("/clinica-medica-administrativo/swagger-ui") || path.startsWith("/clinica-medica-administrativo/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String usuario = Optional.ofNullable(request.getHeader("usuario"))
                    .orElseThrow(() -> new AuthenticationClinicaMedicaException("Usuário não encontrado no cabeçalho!"));
            String senha = Optional.ofNullable(request.getHeader("senha"))
                    .orElseThrow(() -> new AuthenticationClinicaMedicaException("Senha não encontrada no cabeçalho!"));

            Funcionario funcionarioAutenticado = autenticadorService.autenticar(usuario, senha)
                    .orElseThrow(() -> new AuthenticationClinicaMedicaException("Credenciais inválidas: Usuário ou senha incorretos."));

            if (funcionarioAutenticado.getPerfil() == null) {
                throw new AuthenticationClinicaMedicaException("Perfil não associado ao funcionário autenticado.");
            }
            PerfilDto perfilUsuario = perfilService.buscarPerfilPorId((long) funcionarioAutenticado.getPerfil().getId());

            String normalizedPath = mapPathToGeneric(path);
            String requiredActionKey = method + " " + normalizedPath;
            String requiredAction = REQUIRED_PERMISSIONS.get(requiredActionKey);

            if (requiredAction == null) {
                logger.warn("Ação não mapeada para a rota: " + requiredActionKey + ". Acesso negado.");
                throw new ActionClinicaMedicaException("Acesso negado: A rota solicitada não possui permissão definida ou é inválida.");
            }

            boolean temPermissao = autorizadorService.checkPermission(perfilUsuario, requiredAction);

            if (!temPermissao) {
                logger.warn("Permissão negada para o usuário '" + usuario + "' na ação '" + requiredAction + "'.");
                throw new ActionClinicaMedicaException("Acesso negado: Usuário não tem permissão para realizar a ação: " + requiredAction);
            }

            filterChain.doFilter(request, response);

        } catch (AuthenticationClinicaMedicaException | ActionClinicaMedicaException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro inesperado no filtro de autorização: " + e.getMessage());
        }
    }

    private String mapPathToGeneric(String path) {
        if (path.endsWith("/") && path.length() > 1) {
            path = path.substring(0, path.length() - 1);
        }

        Pattern pattern = Pattern.compile("(.+)/(\\d+)$");
        Matcher matcher = pattern.matcher(path);

        if (matcher.matches()) {
            return matcher.group(1) + "/{id}";
        }

        return path;
    }
}
