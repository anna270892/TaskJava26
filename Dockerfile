FROM mysql:latest

ENV MYSQL_RANDOM_ROOT_PASSWORD=yes

ENV MYSQL_DATABASE=app

ENV MYSQL_USER=app

ENV MYSQL_PASSWORD=pass

ADD schema.sql /docker-entrypoint-initdb.d/

EXPOSE 3306