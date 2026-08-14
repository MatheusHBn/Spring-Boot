insert into user
(id, email, first_name, last_name)
values (1, 'thiagozaozao@outlook.com', 'Thiago', 'Nascimento');

insert into user
(id, email, first_name, last_name)
values (2, 'matheus@gmail.com', 'Matheus', 'Henrique');

insert into profile
(id, description, name)
values (1, 'Administrator', 'Admin');

insert into user_profile
(id, user_id, profile_id)
values (1, 1, 1);

insert into user_profile
(id, user_id, profile_id)
values (2, 2, 1);