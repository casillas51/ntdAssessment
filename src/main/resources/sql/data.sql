insert ignore into roles (role) values ('ADMIN');
insert ignore into roles (role) values ('USER');

insert ignore into users (username, password, id_role) values ('Admin', '$2a$10$R0TuFO1A4gDMpaqjxyick.rqTyk6ttPDnkMr9HYU/3iLBomT6ojM6', 1);
insert ignore into users (username, password, id_role) values ('User1', '$2a$10$MFfcCO.wDLlmRYW3kbH/y.ZJbUzndwtYS7uh/I.ka4LagGL2GJmyG', 2);
insert ignore into users (username, password, id_role) values ('User2', '$2a$10$MFfcCO.wDLlmRYW3kbH/y.ZJbUzndwtYS7uh/I.ka4LagGL2GJmyG', 2);
insert ignore into users (username, password, id_role) values ('User3', '$2a$10$MFfcCO.wDLlmRYW3kbH/y.ZJbUzndwtYS7uh/I.ka4LagGL2GJmyG', 2);