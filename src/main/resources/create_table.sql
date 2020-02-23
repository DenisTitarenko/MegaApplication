CREATE TABLE IF NOT EXISTS employees
(
ID int,
name varchar(40),
sex char(1),
workDate date,
department varchar,
salary decimal(10, 2),
PRIMARY KEY (ID)
)