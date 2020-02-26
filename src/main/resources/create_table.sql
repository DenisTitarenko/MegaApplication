CREATE TABLE IF NOT EXISTS employees
(
    id         SERIAL,
    name       varchar,
    sex        varchar,
    position   varchar,
    salary     int,
    dateOfHire date
)