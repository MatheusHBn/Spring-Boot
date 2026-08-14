-- Criar tabela 'profile'

set foreign_key_checks = 0;

CREATE TABLE IF NOT EXISTS profile (
                                       id BIGINT NOT NULL AUTO_INCREMENT,
                                       name VARCHAR(255) NOT NULL,
                                       description VARCHAR(255) NOT NULL,
                                       PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Criar tabela 'user'
CREATE TABLE IF NOT EXISTS user (
                                    id BIGINT NOT NULL AUTO_INCREMENT,
                                    first_name VARCHAR(255) NOT NULL,
                                    last_name VARCHAR(255) NOT NULL,
                                    email VARCHAR(255) NOT NULL,
                                    PRIMARY KEY (id),
                                    CONSTRAINT UK_user_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Criar tabela de junção 'user_profile'
CREATE TABLE IF NOT EXISTS user_profile (
                                            id BIGINT NOT NULL AUTO_INCREMENT,
                                            user_id BIGINT NOT NULL,
                                            profile_id BIGINT NOT NULL,
                                            PRIMARY KEY (id),
                                            CONSTRAINT FK_user_profile_user FOREIGN KEY (user_id) REFERENCES user (id),
                                            CONSTRAINT FK_user_profile_profile FOREIGN KEY (profile_id) REFERENCES profile (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

set foreign_key_checks = 1;