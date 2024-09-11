-- This file contains the initial data that will be inserted into the database when the application starts.

-- Insert values into roles table
insert ignore into roles (id_role, role) values (NEXTVAL(roles_seq), 'ADMIN');
insert ignore into roles (id_role, role) values (NEXTVAL(roles_seq), 'USER');

-- Insert values into users table
insert ignore into users (id_user, username, password, id_role, deleted) values (NEXTVAL(users_seq), 'Admin', '$2a$10$R0TuFO1A4gDMpaqjxyick.rqTyk6ttPDnkMr9HYU/3iLBomT6ojM6', 1, false);
insert ignore into users (id_user, username, password, id_role, deleted) values (NEXTVAL(users_seq), 'User1', '$2a$10$MFfcCO.wDLlmRYW3kbH/y.ZJbUzndwtYS7uh/I.ka4LagGL2GJmyG', 2, false);

-- Insert values into operations table based on OperationTypeEnum
insert ignore into operations (id_operation, type, cost) values (NEXTVAL(operations_seq), 'ADDITION', 1.00);
insert ignore into operations (id_operation, type, cost) values (NEXTVAL(operations_seq), 'SUBTRACTION', 1.00);
insert ignore into operations (id_operation, type, cost) values (NEXTVAL(operations_seq), 'MULTIPLICATION', 1.50);
insert ignore into operations (id_operation, type, cost) values (NEXTVAL(operations_seq), 'DIVISION', 2.00);
insert ignore into operations (id_operation, type, cost) values (NEXTVAL(operations_seq), 'SQUARE_ROOT', 2.50);
insert ignore into operations (id_operation, type, cost) values (NEXTVAL(operations_seq), 'RANDOM_STRING', 3.00);