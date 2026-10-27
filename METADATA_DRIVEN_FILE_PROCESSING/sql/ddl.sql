CREATE SCHEMA IF NOT EXISTS test;

CREATE TABLE test.file_config (
    config_id         serial PRIMARY KEY,
    feed_name         varchar(100) NOT NULL UNIQUE,
    file_path         varchar(500) NOT NULL,
    file_name_pattern varchar(200) NOT NULL,   -- CUSTOMER_{yyyyMMdd}.csv
    delimiter         varchar(10)  NOT NULL,   -- ',' or '|' or 'TAB'
    has_header        boolean      NOT NULL,
    field_count       int          NOT NULL,
    field_names       text,                    -- used when has_header = false
    staging_schema    varchar(63)  NOT NULL DEFAULT 'test',
    staging_table     varchar(63)  NOT NULL,
    archive_path      varchar(500),
    is_active         boolean      NOT NULL DEFAULT true
);

CREATE TABLE test.file_load_audit (
    audit_id        bigserial PRIMARY KEY,
    feed_name       varchar(100) NOT NULL,
    file_date       date,
    full_file_path  varchar(500),
    staging_table   varchar(200),
    rows_loaded     bigint,
    status          varchar(20)  NOT NULL,     -- SUCCESS / FAILED
    error_message   text,
    job_run_id      varchar(50),
    loaded_on       timestamp    NOT NULL DEFAULT now(),
    loaded_by       varchar(100) DEFAULT current_user
);

CREATE UNIQUE INDEX ux_file_load_success
    ON test.file_load_audit (feed_name, full_file_path) WHERE status = 'SUCCESS';

INSERT INTO test.file_config
    (feed_name, file_path, file_name_pattern, delimiter, has_header, field_count, staging_table)
VALUES
    ('CUSTOMER', 'E:/Work/talend/inbound', 'CUSTOMER_{yyyyMMdd}.csv', ',', true, 5, 'stg_customer');