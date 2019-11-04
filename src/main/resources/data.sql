create table if not exists persistent_logins (
     username varchar(100) not null,
     series varchar(64) primary key,
     token varchar(64) not null,
     last_used timestamp not null
);

delete from  user_role;
delete from  roles;
delete from  users;
delete from  requests;

INSERT INTO roles (id, name) VALUES
(1, 'ROLE_ADMIN'),
--(2, 'ROLE_ACTUATOR'),
(2, 'ROLE_USER');

INSERT INTO requests(id, username, type, start_date, end_date, status, name, manychat_id)  VALUES
(1, 'user@user.com', 'szabadság', '2019-01-01', '2019-01-11', 'várakozás', 'Péter Nagy', '3809668825726118'),
(2, 'admin@admin.com', 'null', 'null', 'null', 'null', 'null', '3433995213292221');

INSERT INTO users (id, email, password, name) VALUES
(1, 'admin@admin.com', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'Admin'),
(2, 'user@user.com', '$2a$10$ByIUiNaRfBKSV6urZoBBxe4UbJ/sS6u1ZaPORHF9AtNWAuVPVz1by', 'Péter Nagy'),
(3, 'user2@user.com', '$2a$10$ByIUiNaRfBKSV6urZoBBxe4UbJ/sS6u1ZaPORHF9AtNWAuVPVz1by', 'Gergo Kis');

insert into user_role(user_id, role_id) values
(1,1),
(1,2),
(2,2),
(3,2);