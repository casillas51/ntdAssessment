CREATE TABLE roles (
	id_role INT AUTO_INCREMENT,
    role ENUM ('ADMIN','USER') NOT NULL,
    PRIMARY KEY (id_role),
    UNIQUE (role)
);

CREATE TABLE users (
	id_user INT AUTO_INCREMENT,
	username VARCHAR(25) NOT NULL,
    password VARCHAR(90) NOT NULL,
    balance DECIMAL(10,2) DEFAULT 0.0,
    status ENUM ('ACTIVE','INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_role INT NOT NULL,
    deleted boolean NOT NULL,
    PRIMARY KEY (id_user),
    UNIQUE (username),
    CONSTRAINT fk_user_role
		FOREIGN KEY (id_role)
        REFERENCES roles (id_role),
	INDEX idx_username (username)
);

CREATE TABLE tokens (
    token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valid BOOLEAN DEFAULT TRUE,
    path VARCHAR(255),
    id_user INT NOT NULL,
    PRIMARY KEY (token),
    CONSTRAINT fk_token_user
        FOREIGN KEY (id_user)
        REFERENCES users (id_user),
    INDEX idx_token (token)
);

CREATE TABLE operations (
    id_operation INT AUTO_INCREMENT,
    type ENUM('ADDITION', 'SUBTRACTION', 'MULTIPLICATION', 'DIVISION') NOT NULL,
    cost DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id_operation)
);