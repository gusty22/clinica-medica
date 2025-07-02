package br.edu.imepac.agendamento.utils;

import br.edu.imepac.agendamento.exception.ActionClinicaMedicaException;
import br.edu.imepac.agendamento.exception.AuthenticationClinicaMedicaException;
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
        REQUIRED_PERMISSIONS.put("POST /clinica-medica-agendamento/consultas", "cadastrarConsulta");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-agendamento/consultas/listar", "listarConsulta");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-agendamento/consultas/{id}", "lerConsulta");
        REQUIRED_PERMISSIONS.put("PUT /clinica-medica-agendamento/consultas/{id}", "atualizarConsulta");
        REQUIRED_PERMISSIONS.put("DELETE /clinica-medica-agendamento/consultas/{id}", "deletarConsulta");
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
        logger.debug("Iniciando validação de acesso para a requisição: " + method + " " + path);

        if (path.startsWith("/clinica-medica-agendamento/swagger-ui") || path.startsWith("/clinica-medica-agendamento/v3/api-docs")) {
            logger.debug("Acesso permitido para a documentação da API: " + method + " " + path);
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
                logger.warn("Ação não mapeada/protegida para a rota: " + requiredActionKey + ". Acesso negado por padrão.");
                throw new ActionClinicaMedicaException("Acesso negado: A rota solicitada não possui permissão definida ou é inválida.");
            }

            logger.debug("Validando autorização para o usuário: " + usuario + " com perfil: " + perfilUsuario.getNome() + " para a ação NECESSÁRIA: " + requiredAction);

            boolean temPermissao = autorizadorService.checkPermission(perfilUsuario, requiredAction);

            if (!temPermissao) {
                logger.warn("Acesso negado: Usuário '" + usuario + "' com perfil '" + perfilUsuario.getNome() + "' não tem permissão para realizar a ação NECESSÁRIA: '" + requiredAction + "'.");
                throw new ActionClinicaMedicaException("Acesso negado: Usuário não tem permissão para realizar a ação: " + requiredAction);
            }

            filterChain.doFilter(request, response);

        } catch (AuthenticationClinicaMedicaException | ActionClinicaMedicaException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
            logger.error("Erro de autenticação/autorização no filtro: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro inesperado no filtro de autorização! Detalhes: " + e.getMessage());
            logger.error("Erro inesperado no filtro de autorização: " + e.getMessage(), e);
        }
    }

    private String mapPathToGeneric(String path) {
        String resourcePrefix = "/clinica-medica-agendamento/consultas/";

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
