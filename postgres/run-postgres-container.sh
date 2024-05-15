#!/bin/zsh

# postgres automatically creates database with the same name as `POSTGRES_USER` so that is why in
# application-local.properties there is `bookdb` database in url

docker run -d -e POSTGRES_PASSWORD=password -e POSTGRES_USER=bookdb -p 5432:5432 postgres

# then login into postgres container as per below:
# `docker exec -it <postgres-container-id> bash`
# inside the container:
# `psql -d bookdb -U bookdb`
