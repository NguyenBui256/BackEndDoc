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

# create table users(
#     id int auto_increment primary key,
#     username varchar(255) unique not null,
#     password varchar(255) not null,
#     dob varchar(255),
#     gender varchar(255),
#     location text
# );

create table users(
                      id int auto_increment primary key,
                      username varchar(50) not null unique,
                      password varchar(255) not null,
                      enabled boolean not null
);

create table authorities (
                     username varchar(50) not null,
                     authority varchar(50) not null,
                     constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);