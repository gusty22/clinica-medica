INSERT INTO historicos(date_time, data)
VALUES (NOW(), 'Sample data for historico');

INSERT INTO perfis (
    nome,
    cadastrar_funcionario, ler_funcionario, atualizar_funcionario, deletar_funcionario, listar_funcionario,
    cadastrar_paciente, ler_paciente, atualizar_paciente, deletar_paciente, listar_paciente,
    cadastrar_consulta, ler_consulta, atualizar_consulta, deletar_consulta, listar_consulta,
    cadastrar_especialidade, ler_especialidade, atualizar_especialidade, deletar_especialidade, listar_especialidade,
    cadastrar_convenio, ler_convenio, atualizar_convenio, deletar_convenio, listar_convenio,
    cadastrar_prontuario, ler_prontuario, atualizar_prontuario, deletar_prontuario, listar_prontuario
) VALUES (
             'ADMINISTRADOR',
             TRUE, TRUE, TRUE, TRUE, TRUE,
             TRUE, TRUE, TRUE, TRUE, TRUE,
             TRUE, TRUE, TRUE, TRUE, TRUE,
             TRUE, TRUE, TRUE, TRUE, TRUE,
             TRUE, TRUE, TRUE, TRUE, TRUE,
             TRUE, TRUE, TRUE, TRUE, TRUE
         );

INSERT INTO perfis (
    nome,
    cadastrar_funcionario, ler_funcionario, atualizar_funcionario, deletar_funcionario, listar_funcionario,
    cadastrar_paciente, ler_paciente, atualizar_paciente, deletar_paciente, listar_paciente,
    cadastrar_consulta, ler_consulta, atualizar_consulta, deletar_consulta, listar_consulta,
    cadastrar_especialidade, ler_especialidade, atualizar_especialidade, deletar_especialidade, listar_especialidade,
    cadastrar_convenio, ler_convenio, atualizar_convenio, deletar_convenio, listar_convenio,
    cadastrar_prontuario, ler_prontuario, atualizar_prontuario, deletar_prontuario, listar_prontuario
) VALUES (
             'MEDICO',
             FALSE, FALSE, FALSE, FALSE, FALSE,
             FALSE, TRUE, FALSE, FALSE, TRUE,
             TRUE, TRUE, TRUE, FALSE, TRUE,
             FALSE, TRUE, FALSE, FALSE, TRUE,
             FALSE, FALSE, FALSE, FALSE, FALSE,
             TRUE, TRUE, TRUE, FALSE, TRUE
         );

INSERT INTO perfis (
    nome,
    cadastrar_funcionario, ler_funcionario, atualizar_funcionario, deletar_funcionario, listar_funcionario,
    cadastrar_paciente, ler_paciente, atualizar_paciente, deletar_paciente, listar_paciente,
    cadastrar_consulta, ler_consulta, atualizar_consulta, deletar_consulta, listar_consulta,
    cadastrar_especialidade, ler_especialidade, atualizar_especialidade, deletar_especialidade, listar_especialidade,
    cadastrar_convenio, ler_convenio, atualizar_convenio, deletar_convenio, listar_convenio,
    cadastrar_prontuario, ler_prontuario, atualizar_prontuario, deletar_prontuario, listar_prontuario
) VALUES (
             'ATENDENTE',
             FALSE, TRUE, FALSE, FALSE, TRUE,
             TRUE, TRUE, TRUE, FALSE, TRUE,
             TRUE, TRUE, TRUE, FALSE, TRUE,
             FALSE, TRUE, FALSE, FALSE, TRUE,
             TRUE, TRUE, TRUE, FALSE, TRUE,
             FALSE, FALSE, FALSE, FALSE, FALSE
         );
