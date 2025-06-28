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
    bairro, cidade, complemento, contato, cpf, data_nascimento,
    email, estado, idade, nome, numero, rua, senha, sexo,
    tipo_funcionario, usuario, perfil_id
) VALUES (
             'Centro', 'São Paulo', 'Apto 101', '11999999999', '12345678901', '1985-06-01',
             'admin@clinica.com', 'SP', 39, 'Carlos Administrador', '123', 'Rua Central', 'admin123',
             'MASCULINO', 'ADMINISTRADOR', 'admincarlos', 1
         );

INSERT INTO funcionarios (
    bairro, cidade, complemento, contato, cpf, data_nascimento,
    email, estado, idade, nome, numero, rua, senha, sexo,
    tipo_funcionario, usuario, perfil_id
) VALUES (
             'Bela Vista', 'Rio de Janeiro', 'Casa', '21988888888', '23456789012', '1995-03-15',
             'atendente@clinica.com', 'RJ', 29, 'Fernanda Atendente', '456', 'Rua das Flores', 'atend123',
             'FEMININO', 'ATENDENTE', 'fernanda_a', 2
         );


INSERT INTO funcionarios (
    bairro, cidade, complemento, contato, cpf, data_nascimento,
    email, estado, idade, nome, numero, rua, senha, sexo,
    tipo_funcionario, usuario, perfil_id
) VALUES (
             'Jardins', 'Belo Horizonte', NULL, '31977777777', '34567890123', '1990-09-25',
             'medico@clinica.com', 'MG', 33, 'Dr. Rafael Médico', '789', 'Rua da Saúde', 'med123',
             'MASCULINO', 'MEDICO', 'rafael_m', 3
         );

-- CONVÊNIOS
INSERT INTO convenios (nome, descricao) VALUES
                                            ('Plano Saúde Popular', 'Cobertura básica para consultas e exames simples'),
                                            ('Super Vida', 'Abrange especialidades e internações');

-- ESPECIALIDADES
INSERT INTO especialidades (nome, descricao) VALUES
                                                 ('Clínico Geral', 'Atendimento médico básico'),
                                                 ('Pediatria', 'Atendimento especializado para crianças');


-- PACIENTE
INSERT INTO pacientes (
    bairro, cidade, complemento, contato, cpf, data_nascimento, email,
    estado, idade, nome, numero, rua, sexo, convenio_id
) VALUES (
             'Bela Vista', 'São Paulo', NULL, '11977777777', '32165498700', '1990-06-10',
             'paciente@clinica.com', 'SP', 34, 'Mariana Souza', '789', 'Rua C', 'FEMININO', 1
         );

-- PRONTUÁRIO
INSERT INTO prontuarios (exames, observacoes, receituario, paciente_id) VALUES (
                                                                                   'Hemograma, Raio-X',
                                                                                   'Paciente apresentou febre e dores no corpo.',
                                                                                   'Paracetamol 750mg 3x ao dia por 5 dias',
                                                                                   1
                                                                               );

-- CONSULTA
INSERT INTO consultas (
    data_horario, e_retorno, esta_ativa, sintomas, convenio_id,
    funcionario_id, paciente_id, prontuario_id
) VALUES (
             '2025-06-27 14:30:00.000000', b'0', b'1', 'Febre e dor de cabeça',
             1, 1, 1, 1
         );