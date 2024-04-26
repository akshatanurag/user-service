CREATE TABLE address
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    is_deleted BIT(1) NULL,
    city       VARCHAR(255) NULL,
    street     VARCHAR(255) NULL,
    zipcode    VARCHAR(255) NULL,
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    is_deleted BIT(1) NULL,
    email      VARCHAR(255) NULL,
    user_name  VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    number     VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_address
(
    user_id    BIGINT NOT NULL,
    address_id BIGINT NOT NULL
);

ALTER TABLE user_address
    ADD CONSTRAINT fk_useadd_on_address FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE user_address
    ADD CONSTRAINT fk_useadd_on_user FOREIGN KEY (user_id) REFERENCES user (id);