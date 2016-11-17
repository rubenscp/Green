delete from person;

ALTER SEQUENCE person_id_seq RESTART WITH 1;

INSERT INTO person (user_name, email, name, password, workplace_name, workplace_initials, language, theme, green_person_profile_initials, last_update_date, last_experiment_public_identifier) 
VALUES ('rubenscp', 'rubenscp@gmail.com', 'Rubens de Castro Perera', '123', 'Embrapa Arroz e Feijão', 'CNPAF', 'pt_br', 'redmond', 'USR', '2016-11-15 0:0:0.0', 0);