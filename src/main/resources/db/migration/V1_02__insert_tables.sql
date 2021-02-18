INSERT INTO number_authorizations(quantity, last_authorization_date)
VALUES (3, '2021-02-14 23:59:59'),
       (3, '2021-02-24 23:59:59'),
       (3, '2021-04-20 23:59:59');

INSERT INTO users (username, password, first_name, last_name, email, age, number_authorization_id, enabled)
VALUES ('1', '$2a$10$VNVO9c3jM3PvDP6RQgqM1.Q2EyZj.YvSWTb3Xp9JitB6ZL26/EJfK', 'Alexander',
        'Ivashenko', '1@gmail.com', 26, 1, true),
       ('igor', '$2a$10$DQffgL2UDyxNHVWTi5FhjexHSC45.l9ZeWBaMNJyx.4e6iqfEMBCS', 'Igor',
        'Kruk', 'igor@gmail.com', 26, 2, true),
       ('pasha', '$2a$10$5hSIulRFp4i3fjSmO3I1aO5RNQnUqzZF20/qVsdSflCcKGEle0tSG', 'Pasha',
        'Nankovski', 'pasha@gmail.com', 26, 3, true);

INSERT INTO confirmation_tokens(user_id, token, status, issued_date)
VALUES (1, 'token', 'status', now()),
       (2, 'token', 'status', now()),
       (3, 'token', 'status', now());

INSERT INTO roles (name)
VALUES ('ROLE_EMPLOYEE'),
       ('ROLE_USER');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 2);

INSERT INTO books(name, book_condition, description)
VALUES ('In Search of Lost', 'bad',
        'Swann''s Way, the first part of A la recherche de temps perdu, Marcel Proust''s seven-part cycle'),
       ('Ulysses by James', 'good',
        'Ulysses chronicles the passage of Leopold Bloom through Dublin during an ordinary day, June 16'),
       ('Don Quixote by', 'best',
        'Alonso Quixano, a retired country gentleman in his fifties, lives in an unnamed section of La Mancha'),
       ('One Hundred Ye', 'perfect',
        'One of the 20th century''s enduring works, One Hundred Years of Solitude is a widely beloved'),
       ('The Great Gat', 'destroyed',
        'The novel chronicles an era that Fitzgerald himself dubbed the "Jazz Age".'),
       ('name1', 'bookCondition1', 'description1'),
       ('name2', 'bookCondition2', 'description2'),
       ('name3', 'bookCondition3', 'description3'),
       ('name4', 'bookCondition4', 'description4'),
       ('name5', 'bookCondition5', 'description5'),
       ('name6', 'bookCondition6', 'description6'),
       ('name7', 'bookCondition7', 'description7'),
       ('name8', 'bookCondition8', 'description8'),
       ('name9', 'bookCondition9', 'description9');

INSERT INTO order_books(status, user_id, book_id, note, start_date, end_date)
VALUES ('NEW', 1, 1, 'escaped', '2021-01-04 23:59:59', '2021-02-14 23:59:59'),
       ('OLD', 2, 2, 'brand new', '2021-01-06 23:59:59', '2021-02-24 23:59:59'),
       ('STOLEN', 3, 3, 'torn leaf', '2021-01-07 23:59:59', '2021-04-20 23:59:59'),
       ('TAKEN', 1, 4, 'escaped', '2021-01-04 23:59:59', '2021-02-14 23:59:59'),
       ('IN_LIBRARY', 2, 5, 'brand new', '2021-01-06 23:59:59', '2021-02-24 23:59:59'),
       ('STOLEN', 3, 6, 'torn leaf', '2021-01-07 23:59:59', '2021-04-20 23:59:59');
