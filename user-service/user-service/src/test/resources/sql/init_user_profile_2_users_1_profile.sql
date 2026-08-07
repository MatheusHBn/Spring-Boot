insert into "User"
    ("id", "email", "firstName", "lastName")
values (1, 'thiagozaozao@outlook.com', 'Thiago', 'Nascimento');

insert into "User"
    ("id", "email", "firstName", "lastName")
values (2, 'matheus@gmail.com', 'Matheus', 'Henrique');

insert into "Profile"
    ("id", "description", "name")
values (1, 'Administrator', 'Admin');

insert into "UserProfile"
    ("id", "user_id", "profile_id")
values (1, 1, 1);

insert into "UserProfile"
    ("id", "user_id", "profile_id")
values (2, 2, 1);