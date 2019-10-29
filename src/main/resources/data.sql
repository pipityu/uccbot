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

INSERT INTO requests(id, name, type, start_date, end_date, status)  VALUES
(1, 'user@user.com', 'szabadság', '2019-01-01', '2019-01-11', 'várakozás');
(2, 'admin@admin.com', 'null', 'null', 'null', 'null');

INSERT INTO users (id, email, password, name, request_id) VALUES
(1, 'admin@admin.com', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'Admin',2),
(2, 'user@user.com', '$2a$10$ByIUiNaRfBKSV6urZoBBxe4UbJ/sS6u1ZaPORHF9AtNWAuVPVz1by', 'User', 1);

insert into user_role(user_id, role_id) values
(1,1),
(1,2),
--(1,3),
(2,2);