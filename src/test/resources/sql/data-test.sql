insert into roles (id_role, role) values (next value for roles_seq, 'ADMIN');
insert into roles (id_role, role) values (next value for roles_seq, 'USER');

insert into users (id_user, username, status, password, balance, id_role, deleted) values (next value for users_seq, 'Admin', 'ACTIVE', '$2a$10$R0TuFO1A4gDMpaqjxyick.rqTyk6ttPDnkMr9HYU/3iLBomT6ojM6', 0.0, 1, false);
insert into users (id_user, username, status, password, balance, id_role, deleted) values (next value for users_seq, 'User1', 'ACTIVE', '$2a$10$MFfcCO.wDLlmRYW3kbH/y.ZJbUzndwtYS7uh/I.ka4LagGL2GJmyG', 25.2, 2, false);
insert into users (id_user, username, status, password, balance, id_role, deleted) values (next value for users_seq, 'User2', 'ACTIVE', '$2a$10$MFfcCO.wDLlmRYW3kbH/y.ZJbUzndwtYS7uh/I.ka4LagGL2GJmyG', 30.0, 2, false);
insert into users (id_user, username, status, password, balance, id_role, deleted) values (next value for users_seq, 'User3', 'ACTIVE', '$2a$10$MFfcCO.wDLlmRYW3kbH/y.ZJbUzndwtYS7uh/I.ka4LagGL2GJmyG', 40.4, 2, false);
insert into users (id_user, username, status, password, balance, id_role, deleted) values (next value for users_seq, 'UserDelete', 'ACTIVE', '$2a$10$MFfcCO.wDLlmRYW3kbH/y.ZJbUzndwtYS7uh/I.ka4LagGL2GJmyG', 27.3, 2, false);

INSERT INTO operations (id_operation, type, cost) VALUES (next value for operations_seq, 'ADDITION', 10.0);
INSERT INTO operations (id_operation, type, cost) VALUES (next value for operations_seq, 'SUBTRACTION', 15.0);
INSERT INTO operations (id_operation, type, cost) VALUES (next value for operations_seq, 'MULTIPLICATION', 20.0);
INSERT INTO operations (id_operation, type, cost) VALUES (next value for operations_seq, 'DIVISION', 25.0);