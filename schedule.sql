CREATE TABLE TODO
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(20),
    contents   VARCHAR(200),
    password   VARCHAR(20),
    date       DATETIME,
    updateDate DATETIME,
    userid     INT,
    deleted   BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE USER
(
    id         int AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(20),
    email      VARCHAR(50),
    regiDate   DATETIME,
    updateDate DATETIME,
    deleted   BOOLEAN NOT NULL DEFAULT FALSE
);