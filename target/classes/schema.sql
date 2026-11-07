CREATE TABLE poi (id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, floor INT NOT NULL, x DOUBLE NOT NULL, y DOUBLE NOT NULL, z DOUBLE NOT NULL DEFAULT 0.0, label VARCHAR(255) NOT NULL);
INSERT INTO poi (floor, x, y, z, label) VALUES (1, 0.0, 0.0, 1.5, 'reception'), (1, 10.0, 0.0, 1.5, 'lobby'), (1, 10.0, 10.0, 1.5, 'demo'), (2, 0.0, 0.0, 3.0, 'desk'), (2, 5.0, 5.0, 3.0, 'meeting');
