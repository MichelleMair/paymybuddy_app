USE mydb

-- Insertion de données dans la table USER
INSERT INTO USER (username, email, password) VALUES ('John Doe', 'john.doe@example.com', 'password123');
INSERT INTO USER (username, email, password) VALUES ('Jane Smith', 'jane.smith@example.com', 'password456');

-- Insertion de données dans la table TRANSACTION
INSERT INTO TRANSACTION (sender_id, receiver_id, description, amount) VALUES (1, 2, 'Payment for dinner', 50.00);
INSERT INTO TRANSACTION (sender_id, receiver_id, description, amount) VALUES (2, 1, 'Refund for lunch', 30.00);

-- Insertion de données dans la table CONNECTION
INSERT INTO CONNECTION (userid, connectionId) VALUES (1, 2);
INSERT INTO CONNECTION (userid, connectionId) VALUES (2, 1);