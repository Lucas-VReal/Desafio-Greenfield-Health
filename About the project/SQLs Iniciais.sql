SELECT * FROM tb_user;
SELECT * FROM tb_role;
SELECT * FROM tb_users_roles;

INSERT INTO tb_role values(gen_random_uuid(), 'ROLE_ADMIN');
INSERT INTO tb_role values(gen_random_uuid(), 'ROLE_MEDIC');
INSERT INTO tb_role values(gen_random_uuid(), 'ROLE_USER');

INSERT INTO tb_user values ('634f9cd3-d6cb-42fa-b7d6-d4d64f1ef401', '$2a$10$KxxvxgoEUVFxVvBoDb4kDOl5PRGcRZk7QlWvowqYHkpqoWdD4G/bu', 'admin');

INSERT INTO tb_users_roles VALUES('634f9cd3-d6cb-42fa-b7d6-d4d64f1ef401', '124af10b-8703-4d74-bfe0-498709d57777');