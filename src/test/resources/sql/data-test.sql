insert into roles (id_role, role) values (next value for roles_seq, 'ADMIN');
insert into roles (id_role, role) values (next value for roles_seq, 'USER');

insert into users (id_user, username, status, password, id_role, deleted) values (next value for users_seq, 'Admin', 'ACTIVE', '$2a$10$R0TuFO1A4gDMpaqjxyick.rqTyk6ttPDnkMr9HYU/3iLBomT6ojM6', 1, false);
insert into users (id_user, username, status, password, id_role, deleted) values (next value for users_seq, 'User1', 'ACTIVE', '$2a$10$MFfcCO.wDLlmRYW3kbH/y.ZJbUzndwtYS7uh/I.ka4LagGL2GJmyG', 2, false);
insert into users (id_user, username, status, password, id_role, deleted) values (next value for users_seq, 'User2', 'ACTIVE', '$2a$10$MFfcCO.wDLlmRYW3kbH/y.ZJbUzndwtYS7uh/I.ka4LagGL2GJmyG', 2, false);
insert into users (id_user, username, status, password, id_role, deleted) values (next value for users_seq, 'User3', 'ACTIVE', '$2a$10$MFfcCO.wDLlmRYW3kbH/y.ZJbUzndwtYS7uh/I.ka4LagGL2GJmyG', 2, false);
insert into users (id_user, username, status, password, id_role, deleted) values (next value for users_seq, 'UserDelete', 'ACTIVE', '$2a$10$MFfcCO.wDLlmRYW3kbH/y.ZJbUzndwtYS7uh/I.ka4LagGL2GJmyG', 2, false);