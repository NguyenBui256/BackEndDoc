use backendteam;
create table authors(
                        id int auto_increment primary key,
                        author_name varchar(255) not null,
                        email varchar(255) not null,
                        author_password varchar(255) not null
);

create table books(
                      id int auto_increment primary key,
                      book_title varchar(255) not null,
                      publish_date datetime default CURRENT_TIMESTAMP,
                      publisher varchar(255) not null
);

create table libraries(
                          id int auto_increment primary key,
                          library_name varchar(255) not null,
                          address varchar(255) not null
);

create table authors_books(
                            id int auto_increment primary key,
                            authors_id int not null references authors(id),
                            books_id int not null references books(id)
);

create table libraries_books(
                             id int auto_increment primary key,
                             libraries_id int not null references libraries(id),
                             books_id int not null references books(id)
);