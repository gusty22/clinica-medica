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

    // Mapeamento de rotas/métodos HTTP para as permissões necessárias
    // Chave: "MÉTODO_HTTP CAMINHO_DA_URL" (ex: "POST /clinica-medica-administrativo/funcionarios")
    // O caminho da URL deve usar {id} para parâmetros de path variáveis.
    private static final Map<String, String> REQUIRED_PERMISSIONS = new HashMap<>();

    // Bloco estático para inicializar o mapeamento de permissões.
    // ESTE MAPA DEVE SER COMPLETO PARA TODAS AS ROTAS PROTEGIDAS DESTE MÓDULO.
    static {
        // PERMISSÕES DO MÓDULO ADMINISTRATIVO

        // Rotas de Funcionários
        REQUIRED_PERMISSIONS.put("POST /clinica-medica-administrativo/funcionarios", "cadastrarFuncionario");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/funcionarios/listar", "listarFuncionario");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/funcionarios/{id}", "lerFuncionario");
        REQUIRED_PERMISSIONS.put("PUT /clinica-medica-administrativo/funcionarios/{id}", "atualizarFuncionario");
        REQUIRED_PERMISSIONS.put("DELETE /clinica-medica-administrativo/funcionarios/{id}", "deletarFuncionario");

        // Rotas de Pacientes (assumindo que o Admin pode gerenciar pacientes)
        REQUIRED_PERMISSIONS.put("POST /clinica-medica-administrativo/pacientes", "cadastrarPaciente");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/pacientes/listar", "listarPaciente");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/pacientes/{id}", "lerPaciente");
        REQUIRED_PERMISSIONS.put("PUT /clinica-medica-administrativo/pacientes/{id}", "atualizarPaciente");
        REQUIRED_PERMISSIONS.put("DELETE /clinica-medica-administrativo/pacientes/{id}", "deletarPaciente");

        // Rotas de Especialidades
        REQUIRED_PERMISSIONS.put("POST /clinica-medica-administrativo/especialidades", "cadastrarEspecialidade");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/especialidades/listar", "listarEspecialidade");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/especialidades/{id}", "lerEspecialidade");
        REQUIRED_PERMISSIONS.put("PUT /clinica-medica-administrativo/especialidades/{id}", "atualizarEspecialidade");
        REQUIRED_PERMISSIONS.put("DELETE /clinica-medica-administrativo/especialidades/{id}", "deletarEspecialidade");

        // Rotas de Convênios
        REQUIRED_PERMISSIONS.put("POST /clinica-medica-administrativo/convenios", "cadastrarConvenio");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/convenios/listar", "listarConvenio");
        REQUIRED_PERMISSIONS.put("GET /clinica-medica-administrativo/convenios/{id}", "lerConvenio");
        REQUIRED_PERMISSIONS.put("PUT /clinica-medica-administrativo/convenios/{id}", "atualizarConvenio");
        REQUIRED_PERMISSIONS.put("DELETE /clinica-medica-administrativo/convenios/{id}", "deletarConvenio");

        // Rota de Actions Application (geralmente para listar permissões disponíveis, pode ser lida por perfis específicos)
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
        String method = request.getMethod(); // Obter o método HTTP
        logger.debug("Iniciando validação de acesso para a requisição: " + method + " " + path);

        // 1. Permite acesso a documentação (Swagger UI, API docs) sem autenticação/autorização
        // Os caminhos base devem ser ajustados para cada módulo, se o filtro for por módulo.
        if (path.startsWith("/clinica-medica-administrativo/swagger-ui") || path.startsWith("/clinica-medica-administrativo/v3/api-docs")) {
            logger.debug("Acesso permitido para a documentação da API: " + method + " " + path);
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

            // NOVO PASSO CRÍTICO: 4. Determinar a Ação Necessária para ESTA ROTA/MÉTODO
            // Normaliza o path para que IDs sejam substituídos por {id} para a busca no mapa.
            String normalizedPath = mapPathToGeneric(path);
            String requiredActionKey = method + " " + normalizedPath; // Cria a chave para o mapa (ex: "GET /funcionarios/{id}")
            String requiredAction = REQUIRED_PERMISSIONS.get(requiredActionKey);

            if (requiredAction == null) {
                // Se a rota/método não estiver explicitamente mapeada no REQUIRED_PERMISSIONS,
                // significa que não é uma rota protegida por permissão explícita ou é uma rota desconhecida.
                // A política de segurança padrão é NEGAR ACESSO para rotas não mapeadas.
                logger.warn("Ação não mapeada/protegida para a rota: " + requiredActionKey + ". Acesso negado por padrão.");
                throw new ActionClinicaMedicaException("Acesso negado: A rota solicitada não possui permissão definida ou é inválida.");
            }

            // O cabeçalho 'action' enviado pelo cliente NÃO É MAIS USADO para determinar a permissão.
            // A permissão é determinada pelo mapeamento da rota no servidor.
            // Se você quiser, pode logar o cabeçalho 'action' para fins de depuração, mas não o use para autorizar.
            // String acaoDoCabecalho = request.getHeader("action");
            // if (acaoDoCabecalho != null && !acaoDoCabecalho.equals(requiredAction)) {
            //    logger.warn("Mismatche de ação: Cliente enviou '" + acaoDoCabecalho + "', mas a rota exige '" + requiredAction + "'.");
            // }

            logger.debug("Validando autorização para o usuário: " + usuario + " com perfil: " + perfilUsuario.getNome() + " para a ação NECESSÁRIA: " + requiredAction);

            // 5. Verifica a permissão usando o AutorizadorService, AGORA COM A AÇÃO EXIGIDA PELA ROTA
            boolean temPermissao = autorizadorService.checkPermission(perfilUsuario, requiredAction);

            if (!temPermissao) {
                logger.warn("Acesso negado: Usuário '" + usuario + "' com perfil '" + perfilUsuario.getNome() + "' não tem permissão para realizar a ação NECESSÁRIA: '" + requiredAction + "'.");
                throw new ActionClinicaMedicaException("Acesso negado: Usuário não tem permissão para realizar a ação: " + requiredAction);
            }

            // Se tudo estiver OK (autenticado e autorizado), prossegue com a requisição
            filterChain.doFilter(request, response);

        } catch (AuthenticationClinicaMedicaException | ActionClinicaMedicaException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write(e.getMessage());
            logger.error("Erro de autenticação/autorização no filtro: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            response.getWriter().write("Erro inesperado no filtro de autorização! Detalhes: " + e.getMessage());
            logger.error("Erro inesperado no filtro de autorização: " + e.getMessage(), e);
        }
    }

    /**
     * Mapeia um caminho de URL real (ex: /app/recurso/123) para uma versão genérica
     * com placeholders para IDs (ex: /app/recurso/{id}).
     * Esta função precisa ser robusta para todos os padrões de URL com IDs em seu projeto.
     */
    private String mapPathToGeneric(String path) {
        // Padrões de URL que contêm IDs.
        // Adicione aqui todos os padrões de URL com IDs dos seus controladores
        // A ordem pode importar para padrões mais específicos vs. mais gerais.
        // Ex: /recursos/listar deve vir antes de /recursos/{id} se a rota listar não usar ID.

        // Padrões para o módulo administrativo
        // Regex para capturar IDs (apenas números, ou GUIDs se for o caso)
        // Funciona para /funcionarios/123 -> /funcionarios/{id}
        // e também para /funcionarios/listar (não deve ser mapeado para {id})
        String normalizedPath = path;

        // Lista de padrões de recursos (prefixos das rotas)
        String[] resourcePrefixes = {
                "/clinica-medica-administrativo/funcionarios/",
                "/clinica-medica-administrativo/pacientes/",
                "/clinica-medica-administrativo/especialidades/",
                "/clinica-medica-administrativo/convenios/",
                "/clinica-medica-administrativo/perfis/" // Note: Perfils -> perfis
        };

        for (String prefix : resourcePrefixes) {
            if (normalizedPath.startsWith(prefix)) {
                // Tenta extrair a parte após o prefixo (o sufixo)
                String suffix = normalizedPath.substring(prefix.length());

                // Se o sufixo for vazio, significa que é a rota base (ex: /funcionarios)
                // Se for "listar", não é um ID
                if (suffix.isEmpty() || suffix.equals("listar") || suffix.equals("actions-application")) {
                    return normalizedPath; // Retorna o caminho original se for a base ou listar/actions-application
                }

                // Tenta substituir o ID por "{id}"
                // Regex para capturar um ID numérico após o prefixo
                Pattern patternWithId = Pattern.compile("^(" + Pattern.quote(prefix) + ")(\\d+)$");
                Matcher matcher = patternWithId.matcher(normalizedPath);
                if (matcher.matches()) {
                    return matcher.group(1) + "{id}"; // Retorna o prefixo + {id}
                }
            }
        }

        // Caso o path não corresponda a nenhum padrão com ID, ou seja uma rota exata sem ID
        // Ex: /clinica-medica-administrativo/actions-application
        // Retorna o caminho original se não houver um padrão de ID
        return path;
    }
}
