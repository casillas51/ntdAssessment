CREATE TABLE IF NOT EXISTS roles (
	id_role INT AUTO_INCREMENT,
    role ENUM ('ADMIN','USER') NOT NULL,
    PRIMARY KEY (id_role),
    UNIQUE (role)
);

CREATE TABLE IF NOT EXISTS users (
	id_user INT AUTO_INCREMENT,
	username VARCHAR(25) NOT NULL,
    password VARCHAR(90) NOT NULL,
    status ENUM ('ACTIVE','INACTIVE') DEFAULT 'ACTIVE',
    id_role INT NOT NULL,
    PRIMARY KEY (id_user),
    UNIQUE (username),
    CONSTRAINT fk_user_role
		FOREIGN KEY (id_role)
        REFERENCES roles (id_role),
	INDEX idx_username (username)
);

CREATE TABLE IF NOT EXISTS tokens (
    id_token INT AUTO_INCREMENT,
    token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valid BOOLEAN DEFAULT TRUE,
    id_user INT NOT NULL,
    PRIMARY KEY (id_token),
    CONSTRAINT fk_token_user
        FOREIGN KEY (id_user)
        REFERENCES users (id_user),
    INDEX idx_token (token)
);