package br.edu.imepac.comum.services;

import br.edu.imepac.comum.models.EnumTipoFuncionario;
import br.edu.imepac.comum.models.Perfil;
import br.edu.imepac.comum.repositories.IPerfilRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PerfilService {

    private final ModelMapper modelMapper;
    private final IPerfilRepository perfilRepository;

    public PerfilService(ModelMapper modelMapper, IPerfilRepository perfilRepository) {
        this.modelMapper = modelMapper;
        this.perfilRepository = perfilRepository;
    }

    public Perfil adicionarPerfil(Perfil perfil) {
        log.info("Cadastro de perfil - service: {}", perfil);
        return perfilRepository.save(perfil);
    }

    public Perfil atualizarPerfil(Long id, Perfil perfilAtualizado) {
        log.info("Atualizando perfil com ID: {}", id);
        Perfil perfilExistente = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
        modelMapper.map(perfilAtualizado, perfilExistente);
        return perfilRepository.save(perfilExistente);
    }

    public void removerPerfil(Long id) {
        log.info("Removendo perfil com ID: {}", id);
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
        perfilRepository.delete(perfil);
    }

    public Perfil buscarPerfilPorId(Long id) {
        log.info("Buscando perfil com ID: {}", id);
        return perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
    }

    public List<Perfil> listarPerfis() {
        log.info("Listando todos os perfis");
        return perfilRepository.findAll();
    }

    public boolean verificarAutorizacao(String usuario, String senha, String acao) {
        log.info("Verificando autorização para usuário: {}, senha: {}, acao: {}", usuario, senha, acao);
        return true;
    }

    public Perfil gerarPerfilPorTipo(EnumTipoFuncionario tipo) {
        Perfil perfil = new Perfil();
        perfil.setNome(tipo.name());

        switch (tipo) {
            case ADMINISTRADOR -> {
                perfil.setCadastrarFuncionario(true);
                perfil.setLerFuncionario(true);
                perfil.setAtualizarFuncionario(true);
                perfil.setDeletarFuncionario(true);
                perfil.setListarFuncionario(true);

                perfil.setCadastrarPaciente(true);
                perfil.setLerPaciente(true);
                perfil.setAtualizarPaciente(true);
                perfil.setDeletarPaciente(true);
                perfil.setListarPaciente(true);

                perfil.setCadastrarConsulta(true);
                perfil.setLerConsulta(true);
                perfil.setAtualizarConsulta(true);
                perfil.setDeletarConsulta(true);
                perfil.setListarConsulta(true);

                perfil.setCadastrarEspecialidade(true);
                perfil.setLerEspecialidade(true);
                perfil.setAtualizarEspecialidade(true);
                perfil.setDeletarEspecialidade(true);
                perfil.setListarEspecialidade(true);

                perfil.setCadastrarConvenio(true);
                perfil.setLerConvenio(true);
                perfil.setAtualizarConvenio(true);
                perfil.setDeletarConvenio(true);
                perfil.setListarConvenio(true);

                perfil.setCadastrarProntuario(true);
                perfil.setLerProntuario(true);
                perfil.setAtualizarProntuario(true);
                perfil.setDeletarProntuario(true);
                perfil.setListarProntuario(true);
            }

            case ATENDENTE -> {
                perfil.setCadastrarFuncionario(false);
                perfil.setLerFuncionario(true);
                perfil.setAtualizarFuncionario(false);
                perfil.setDeletarFuncionario(false);
                perfil.setListarFuncionario(true);

                perfil.setCadastrarPaciente(true);
                perfil.setLerPaciente(true);
                perfil.setAtualizarPaciente(true);
                perfil.setDeletarPaciente(false);
                perfil.setListarPaciente(true);

                perfil.setCadastrarConsulta(true);
                perfil.setLerConsulta(true);
                perfil.setAtualizarConsulta(true);
                perfil.setDeletarConsulta(false);
                perfil.setListarConsulta(true);

                perfil.setCadastrarEspecialidade(false);
                perfil.setLerEspecialidade(true);
                perfil.setAtualizarEspecialidade(false);
                perfil.setDeletarEspecialidade(false);
                perfil.setListarEspecialidade(true);

                perfil.setCadastrarConvenio(false);
                perfil.setLerConvenio(true);
                perfil.setAtualizarConvenio(false);
                perfil.setDeletarConvenio(false);
                perfil.setListarConvenio(true);

                perfil.setCadastrarProntuario(false);
                perfil.setLerProntuario(true);
                perfil.setAtualizarProntuario(false);
                perfil.setDeletarProntuario(false);
                perfil.setListarProntuario(true);
            }

            case MEDICO -> {
                perfil.setCadastrarFuncionario(false);
                perfil.setLerFuncionario(false);
                perfil.setAtualizarFuncionario(false);
                perfil.setDeletarFuncionario(false);
                perfil.setListarFuncionario(false);

                perfil.setCadastrarPaciente(false);
                perfil.setLerPaciente(true);
                perfil.setAtualizarPaciente(false);
                perfil.setDeletarPaciente(false);
                perfil.setListarPaciente(true);

                perfil.setCadastrarConsulta(false);
                perfil.setLerConsulta(true);
                perfil.setAtualizarConsulta(true);
                perfil.setDeletarConsulta(false);
                perfil.setListarConsulta(true);

                perfil.setCadastrarEspecialidade(false);
                perfil.setLerEspecialidade(true);
                perfil.setAtualizarEspecialidade(false);
                perfil.setDeletarEspecialidade(false);
                perfil.setListarEspecialidade(true);

                perfil.setCadastrarConvenio(false);
                perfil.setLerConvenio(true);
                perfil.setAtualizarConvenio(false);
                perfil.setDeletarConvenio(false);
                perfil.setListarConvenio(true);

                perfil.setCadastrarProntuario(true);
                perfil.setLerProntuario(true);
                perfil.setAtualizarProntuario(true);
                perfil.setDeletarProntuario(false);
                perfil.setListarProntuario(true);
            }
        }

        return perfilRepository.save(perfil);
    }
}
