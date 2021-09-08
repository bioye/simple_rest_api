CREATE TABLE test_user (
    id INT AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);