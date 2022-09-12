INSERT INTO tb_role values('d00ff77e-2c5e-4540-8a6d-5f13c23cd0c1', 'ROLE_ADMIN');
INSERT INTO tb_role values('58a1f86f-30f7-4f82-bd97-541207407c7c', 'ROLE_MEDIC');
INSERT INTO tb_role values('5422b5c7-8c36-4ed9-bd40-1e360669e06c', 'ROLE_PACIENT');

 --Definido o usuario 'admin' e a senha 'senha123'
INSERT INTO tb_user values ('634f9cd3-d6cb-42fa-b7d6-d4d64f1ef401', '$2a$10$KxxvxgoEUVFxVvBoDb4kDOl5PRGcRZk7QlWvowqYHkpqoWdD4G/bu', 'admin');

 --Definido o usuario 'admin' com a role 'ROLE_ADMIN'
 INSERT INTO tb_users_roles values ('634f9cd3-d6cb-42fa-b7d6-d4d64f1ef401', 'd00ff77e-2c5e-4540-8a6d-5f13c23cd0c1');

--Criando usuário 'teste' para ser usado nos CRUDS e a senha 'senha123'
INSERT INTO tb_user values ('e70af46a-eff0-4c4f-b0ae-d9638886be8f', '$2a$10$KxxvxgoEUVFxVvBoDb4kDOl5PRGcRZk7QlWvowqYHkpqoWdD4G/bu', 'teste');

--Criando Medico1
INSERT INTO medicos values('c708fc61-f60d-412b-ad5e-3932e0b03f24', '15/08/2000', '000.111.222-33', '000-0', 'medico1@gmail.com', 'true', 'Medico1', 'Masculino');

--Criando Paciente1
INSERT INTO paciente values ('1b3b89a1-8b6b-4cf4-b377-3f56b41edf68', '111.222.333-44', '14/06/1992', 'Paciente1');

--Criando Paciente2
INSERT INTO paciente values ('75aae34c-40fb-4f33-86e8-c436e5fd03c1', '222.333.444-55', '10/06/1988', 'Paciente2');

--Criando Medicamentos
INSERT INTO medicamentos values('f48256da-2c87-4397-a6d4-cbf68825e08b', '100 mg', '2 vezes ao dia', 'dipirona', '1 cx');
INSERT INTO medicamentos values('b49f1e2a-0c2a-4781-87a9-96026d5947c5', '150 mg', '1 vez ao dia', 'dorflex', '0,5 cx');

--Criando Prescricao Teste com Médico 1 e Paciente 1
INSERT INTO prescricoes values ('4ca5ed2f-6fca-47f6-a05f-a89bbf509920', 'prescricao teste', 'c708fc61-f60d-412b-ad5e-3932e0b03f24', '1b3b89a1-8b6b-4cf4-b377-3f56b41edf68');

