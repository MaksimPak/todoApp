CREATE TABLE to_do_record
(
    id               UUID         NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    created_by       UUID,
    last_modified_by UUID,
    name             VARCHAR(255) NOT NULL,
    CONSTRAINT pk_todorecord PRIMARY KEY (id)
);

CREATE TABLE token
(
    id         UUID    NOT NULL,
    token      VARCHAR(255),
    token_type VARCHAR(255),
    revoked    BOOLEAN NOT NULL,
    expired    BOOLEAN NOT NULL,
    user_id    UUID,
    CONSTRAINT pk_token PRIMARY KEY (id)
);

CREATE TABLE user_account
(
    id               UUID         NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    created_by       UUID,
    last_modified_by UUID,
    firstname        VARCHAR(255),
    lastname         VARCHAR(255),
    email            VARCHAR(255) NOT NULL,
    password         VARCHAR(255) NOT NULL,
    role             VARCHAR(255) NOT NULL,
    CONSTRAINT pk_useraccount PRIMARY KEY (id)
);

ALTER TABLE token
    ADD CONSTRAINT uc_token_token UNIQUE (token);

ALTER TABLE user_account
    ADD CONSTRAINT uc_useraccount_email UNIQUE (email);

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user_account (id);