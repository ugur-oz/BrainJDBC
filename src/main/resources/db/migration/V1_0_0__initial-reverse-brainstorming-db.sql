CREATE TABLE PROBLEM (
                         id BIGSERIAL NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         description VARCHAR NOT NULL);

CREATE TABLE WORSENING (
                           id BIGSERIAL NOT NULL AUTO_INCREMENT PRIMARY KEY,
                           description VARCHAR NOT NULL,
                           problem_id INT NOT NULL,
                           CONSTRAINT FK_WORSENING_PROBLEM FOREIGN KEY (problem_id) REFERENCES problem(id)
);

CREATE TABLE SOLUTION (
                          id BIGSERIAL NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          description VARCHAR NOT NULL,
                          worsening_id INT NOT NULL,
                          CONSTRAINT FK_SOLUTION_WORSENING FOREIGN KEY (worsening_id) REFERENCES worsening(id)
);