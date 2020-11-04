CREATE TABLE inventory
(
id INT NOT NULL Primary Key AUTO_INCREMENT, 
toy_name VARCHAR(20), 
toy_price DOUBLE, 
toy_qty INT
);


INSERT INTO inventory (toy_name, toy_price, toy_qty) VALUES 
('NAME1', 1.1, 1),
('NAME2', 2.2, 2),
('NAME3', 3.3, 3),
('NAME4', 4.4, 4),
('NAME5', 5.5, 5),
('NAME6', 6.6, 6),
('NAME7', 7.7, 7),
('NAME8', 8.8, 8),
('NAME9', 9.9, 9),
('NAME10', 10.10, 10);
