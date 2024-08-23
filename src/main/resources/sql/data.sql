insert ignore into roles (id_role, role) values (NEXTVAL(roles_seq), 'ADMIN');
insert ignore into roles (id_role, role) values (NEXTVAL(roles_seq), 'USER');

insert ignore into users (id_user, username, password, id_role) values (NEXTVAL(users_seq), 'Admin', '$2a$10$R0TuFO1A4gDMpaqjxyick.rqTyk6ttPDnkMr9HYU/3iLBomT6ojM6', 1);
insert ignore into users (id_user, username, password, id_role) values (NEXTVAL(users_seq), 'User1', '$2a$10$MFfcCO.wDLlmRYW3kbH/y.ZJbUzndwtYS7uh/I.ka4LagGL2GJmyG', 2);