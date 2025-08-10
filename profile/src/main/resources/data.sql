
INSERT INTO profiles (name, email, address, occupation) VALUES
('User 1', 'user1@example.com', 1, 2),
('User 2', 'user2@example.com', 1, 2),
('User 3', 'user3@example.com', 1, 2),
('User 4', 'user4@example.com', 1, 2),
('User 5', 'user5@example.com', 1, 2),
('User 6', 'user6@example.com', 1, 2),
('User 7', 'user7@example.com', 1, 2),
('User 8', 'user8@example.com', 2, 3),
('User 9', 'user9@example.com', 2, 3),
( 'User 10', 'user10@example.com', 2, 3),
( 'User 11', 'user11@example.com', 2, 3),
( 'User 12', 'user12@example.com', 2, 3),
( 'User 13', 'user13@example.com', 2, 3),
( 'User 14', 'user14@example.com', 2, 3),
( 'User 15', 'user15@example.com', 2, 3),
( 'User 16', 'user16@example.com', 3, 6),
( 'User 17', 'user17@example.com', 3, 6),
( 'User 18', 'user18@example.com', 3, 6),
( 'User 19', 'user19@example.com', 3, 6),
( 'User 20', 'user20@example.com', 3, 6);

-- Reset the sequence for the ID column to the next available value after manual inserts.
ALTER TABLE profiles ALTER COLUMN id RESTART WITH 21;