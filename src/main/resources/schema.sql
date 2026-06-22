CREATE TABLE student_batch (
    id          BIGSERIAL PRIMARY KEY,
    batch_major VARCHAR(255) NOT NULL,
    batch_name  VARCHAR(15)  NOT NULL,
    batch_year  INTEGER      NOT NULL,
    version     INTEGER,
    CONSTRAINT unique_batches UNIQUE (batch_year, batch_major)
)
;

CREATE TABLE student (
    id               BIGSERIAL PRIMARY KEY,
    npm              VARCHAR(10) NOT NULL
        CONSTRAINT uk_hb21rm231whmvidn1hpbatqxe UNIQUE,
    nik              VARCHAR(16) NOT NULL
        CONSTRAINT uk_j0gf6qfh38s97b43tgamw90hb UNIQUE,
    student_name     VARCHAR(25) NOT NULL,
    is_active        BOOLEAN     NOT NULL,
    birth_date       DATE        NOT NULL,
    student_batch_id BIGINT
        CONSTRAINT fk2oro1x1ru5b06gv863phb22ut REFERENCES student_batch,
    version          INTEGER
)
;

