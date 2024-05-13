#!/bin/zsh

docker build . -t hibernate-tutorial-mysql-db

docker run -d hibernate-tutorial-mysql-db

# and after this run:
# docker exec -it hibernate-tutorial-mysql-db bash
# 
# https://dev.mysql.com/doc/refman/8.0/en/connecting.html
# mysql -h localhost -u bookadmin -ppassword bookdb

