INSERT INTO projects (name) VALUES ('cinema');
INSERT INTO projects (name) VALUES ('briz');
INSERT INTO projects (name) VALUES ('megasot');
INSERT INTO projects (name) VALUES ('fifa21');

INSERT INTO departments (name) VALUES ('development');
INSERT INTO departments (name) VALUES ('qa');
INSERT INTO departments (name) VALUES ('hr');
INSERT INTO departments (name) VALUES ('other');

INSERT INTO employees (name, sex, position, salary, date_of_hire, department_id)
VALUES ('Terminator', 'OTHER', 'senior', 4500, TO_DATE('15/10/2012', 'DD/MM/YYYY'), 1);
INSERT INTO employees (name, sex, position, salary, date_of_hire, department_id)
VALUES ('Silvester Stallone', 'M', 'middle', 3000, TO_DATE('09/12/2016', 'DD/MM/YYYY'), 1);
INSERT INTO employees (name, sex, position, salary, date_of_hire, department_id)
VALUES ('Vasyl', 'M', 'middle', 4500, TO_DATE('03/04/2015', 'DD/MM/YYYY'), 2);
INSERT INTO employees (name, sex, position, salary, date_of_hire, department_id)
VALUES ('Sarah Connor', 'F', 'head', 2500, TO_DATE('27/09/2013', 'DD/MM/YYYY'), 3);
INSERT INTO employees (name, sex, position, salary, date_of_hire, department_id)
VALUES ('Petr', 'M', 'junior', 1300, TO_DATE('15/11/2018', 'DD/MM/YYYY'), 2);
INSERT INTO employees (name, sex, position, salary, date_of_hire, department_id)
VALUES ('tyotya Motya', 'F', 'service', 500, TO_DATE('05/11/2018', 'DD/MM/YYYY'), 4);

INSERT INTO employee_project (employee_id, project_id) VALUES (1, 2);
INSERT INTO employee_project (employee_id, project_id) VALUES (1, 3);
INSERT INTO employee_project (employee_id, project_id) VALUES (1, 1);
INSERT INTO employee_project (employee_id, project_id) VALUES (2, 4);
INSERT INTO employee_project (employee_id, project_id) VALUES (2, 2);
INSERT INTO employee_project (employee_id, project_id) VALUES (3, 2);
INSERT INTO employee_project (employee_id, project_id) VALUES (3, 1);
INSERT INTO employee_project (employee_id, project_id) VALUES (5, 3);
INSERT INTO employee_project (employee_id, project_id) VALUES (5, 4);