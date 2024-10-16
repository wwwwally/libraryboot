create table person
(
    person_id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name      varchar NOT NULL check (length(name) > 2 AND length(name) < 30 ),
    year      int     NOT NULL check ( year > 1900 AND year < date_part('year', CURRENT_DATE) )

);

create table book
(
    book_id        int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    person_id int REFERENCES person (person_id) ON DELETE CASCADE,
    title varchar NOT NULL,
    author varchar NOT NULL,
    year int NOT NULL check ( year > 1400 AND year < date_part('year', CURRENT_DATE) )

);

SELECT name FROM person JOIN book ON person.person_id=book.person_id;

SELECT person_id, name FROM Person;

ALTER TABLE book drop COLUMN person_id;
ALTER TABLE book ADD person_id int REFERENCES person (person_id) ON DELETE SET NULL;


ALTER TABLE book ADD COLUMN taken_at TIMESTAMP