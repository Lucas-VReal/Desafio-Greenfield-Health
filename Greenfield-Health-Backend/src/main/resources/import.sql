INSERT INTO tb_role values('d00ff77e-2c5e-4540-8a6d-5f13c23cd0c1', 'ROLE_ADMIN');
INSERT INTO tb_role values('58a1f86f-30f7-4f82-bd97-541207407c7c', 'ROLE_MEDIC');
INSERT INTO tb_role values('5422b5c7-8c36-4ed9-bd40-1e360669e06c', 'ROLE_PACIENT');

 --Definido o usuario 'admin' e a senha 'senha123'
INSERT INTO tb_user values ('634f9cd3-d6cb-42fa-b7d6-d4d64f1ef401', '$2a$10$KxxvxgoEUVFxVvBoDb4kDOl5PRGcRZk7QlWvowqYHkpqoWdD4G/bu', 'admin');

 --Definido o usuario 'admin' com a role 'ROLE_ADMIN'
 INSERT INTO tb_users_roles values ('634f9cd3-d6cb-42fa-b7d6-d4d64f1ef401', 'd00ff77e-2c5e-4540-8a6d-5f13c23cd0c1');

