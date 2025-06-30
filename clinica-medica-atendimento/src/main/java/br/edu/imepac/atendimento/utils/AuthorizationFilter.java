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
import java.util.Optional;

@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final PerfilService perfilService;
    private final AutenticadorService autenticadorService;
    private final AutorizadorService autorizadorService;

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
        // Ajustado para org.apache.commons.logging.Log: concatenação manual
        logger.debug("Iniciando validação de acesso para a requisição: {" + path + "}");

        // 1. Permite acesso a documentação (Swagger UI, API docs) sem autenticação/autorização
        if (path.startsWith("/clinica-medica-administrativo/swagger-ui") || path.startsWith("/clinica-medica-administrativo/v3/api-docs")) {
            // Ajustado para org.apache.commons.logging.Log: concatenação manual
            logger.debug("Acesso permitido para a documentação da API: {" + path + "}");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 2. Tenta obter o usuário e senha dos cabeçalhos da requisição
            String usuario = Optional.ofNullable(request.getHeader("usuario"))
                    .orElseThrow(() -> new AuthenticationClinicaMedicaException("Usuário não encontrado no cabeçalho!"));
            String senha = Optional.ofNullable(request.getHeader("senha"))
                    .orElseThrow(() -> new AuthenticationClinicaMedicaException("Senha não encontrada no cabeçalho!"));

            // Autentica o funcionário e obtém o objeto Funcionario completo
            Optional<Funcionario> optionalFuncionario = autenticadorService.autenticar(usuario, senha);

            Funcionario funcionarioAutenticado = optionalFuncionario
                    .orElseThrow(() -> new AuthenticationClinicaMedicaException("Credenciais inválidas: Usuário ou senha incorretos."));

            // 3. Obtém o Perfil associado ao funcionário autenticado
            if (funcionarioAutenticado.getPerfil() == null) {
                throw new AuthenticationClinicaMedicaException("Perfil não associado ao funcionário autenticado.");
            }
            PerfilDto perfilUsuario = perfilService.buscarPerfilPorId((long) funcionarioAutenticado.getPerfil().getId());

            // 4. Obtém a ação desejada do cabeçalho "action" da requisição
            String acao = Optional.ofNullable(request.getHeader("action"))
                    .orElseThrow(() -> new ActionClinicaMedicaException("Ação não encontrada no cabeçalho! O cabeçalho 'action' é obrigatório."));

            // Ajustado para org.apache.commons.logging.Log: concatenação manual
            logger.debug("Validando autorização para o usuário: " + usuario + " com perfil: " + perfilUsuario.getNome() + " para a ação: " + acao);

            // 5. Verifica a permissão usando o AutorizadorService
            boolean temPermissao = autorizadorService.checkPermission(perfilUsuario, acao);

            if (!temPermissao) {
                // Ajustado para org.apache.commons.logging.Log: concatenação manual
                logger.warn("Acesso negado: Usuário '" + usuario + "' com perfil '" + perfilUsuario.getNome() + "' não tem permissão para realizar a ação '" + acao + "'.");
                throw new ActionClinicaMedicaException("Acesso negado: Usuário não tem permissão para realizar a ação: " + acao);
            }

            // Se tudo estiver OK (autenticado e autorizado), prossegue com a requisição
            filterChain.doFilter(request, response);

        } catch (AuthenticationClinicaMedicaException | ActionClinicaMedicaException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
            // Ajustado para org.apache.commons.logging.Log: passando apenas a mensagem como Object
            logger.error("Erro de autenticação/autorização no filtro: " + e.getMessage());
        } catch (Exception e) {
            // Captura qualquer outra exceção inesperada e retorna 500 Internal Server Error
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro inesperado no filtro de autorização! Detalhes: " + e.getMessage());
            // Ajustado para org.apache.commons.logging.Log: passando mensagem como Object e a exceção como Throwable
            logger.error("Erro inesperado no filtro de autorização: " + e.getMessage(), e);
        }
    }
}