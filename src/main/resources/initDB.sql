
drop table if exists users cascade;
create table users(
                      id serial primary key,
                      name varchar(255),
                      email varchar(255),
                      password varchar(255)
);

drop table if exists recipes cascade;
create table recipes(
                        id serial primary key,
                        name varchar(255),
                        parent_id int,
                        create_date timestamp,
                        description text,
                        user_id int,
                        image_link varchar(255)
);