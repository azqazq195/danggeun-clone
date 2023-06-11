CREATE TABLE tb_user
(
    id          BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(20)  NOT NULL UNIQUE,
    name        VARCHAR(10)  NOT NULL,
    role        VARCHAR(10)  NOT NULL,
    age         INTEGER,
    password    VARCHAR(255) NOT NULL,
    created_at  DATETIME(6)  NOT NULL,
    modified_at DATETIME(6)  NOT NULL,
    deleted_at  DATETIME(6)
);

CREATE TABLE token
(
    email         VARCHAR(255) NOT NULL PRIMARY KEY,
    access_token  VARCHAR(255),
    refresh_token VARCHAR(255),
    expired_at    DATETIME(6)
);
