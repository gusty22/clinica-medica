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


INSERT INTO funcionarios (
    usuario,
    senha,
    nome,
    idade,
    sexo,
    cpf,
    rua,
    numero,
    complemento,
    bairro,
    cidade,
    estado,
    contato,
    email,
    data_nascimento,
    tipo_funcionario,
    perfil_id
) VALUES (
             'joaoSantos123',
             'senhaForte123',
             'João Santos',
             40,
             'MASCULINO',
             '111.222.333-44',
             'Rua dos Médicos',
             '150',
             'Apto 202',
             'Centro',
             'São Paulo',
             'SP',
             '11988776655',
             'joao@hospital.com',
             '1985-07-10',
             'MEDICO',
             (SELECT id FROM perfis WHERE nome = 'MEDICO')
         );


INSERT INTO funcionarios (
    usuario,
    senha,
    nome,
    idade,
    sexo,
    cpf,
    rua,
    numero,
    complemento,
    bairro,
    cidade,
    estado,
    contato,
    email,
    data_nascimento,
    tipo_funcionario,
    perfil_id
) VALUES (
             'anaSouza123',
             'senhaForte123',
             'Ana Souza',
             28,
             'FEMININO',
             '444.555.666-77',
             'Av. Central',
             '300',
             'Apto 501',
             'Centro',
             'São Paulo',
             'SP',
             '11999887766',
             'ana@hospital.com',
             '1997-10-25',
             'ADMINISTRADOR',
             (SELECT id FROM perfis WHERE nome = 'ADMINISTRADOR')
         );