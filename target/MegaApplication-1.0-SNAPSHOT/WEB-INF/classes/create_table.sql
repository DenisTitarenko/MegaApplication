CREATE TABLE IF NOT EXISTS employees
(
    id         SERIAL PRIMARY KEY,
    name       varchar,
    sex        varchar,
    position   varchar,
    salary     int,
    dateOfHire date
)