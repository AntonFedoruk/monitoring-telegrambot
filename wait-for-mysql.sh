#!/bin/sh
# wait-for-mysql.sh

until docker container exec -it monitoring-telegrambot_mtb-db_1 mysqladmin ping -P 3306 -proot | grep "mysqld is alive" ; do
  >&2 echo "MySQL is unavailable - waiting for it... 😴"
  sleep 3
done

#*************************
#set -e
#
#host="$1"
#root_pwd="$2"
#shift
#
#echo "Waiting for MySQL connection ..."
#
#until mysql -h"$host" -uroot -p"$root_pwd" &> /dev/null
#do
#  echo "DB is unavailable yet - sleeping"
#  sleep 1
#done
#
#>&2 echo "MySQL is up - executing command"
#exec "$@"

#*********************************
#
#set -e
#
#host="$1"
#shift
#
#until MYSQLPASSWORD=$POSTGRES_PASSWORD psql -h "$host" -U "postgres" -c '\q'; do
#  >&2 echo "Postgres is unavailable - sleeping"
#  sleep 1
#done
#
#>&2 echo "Postgres is up - executing command"
#exec "$@"
#
#
#*********************************
#
#echo "Waiting for mysql"
#until mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD" &> /dev/null
#do
#  printf "."
#  sleep 1
#done
#
#echo -e "\nmysql ready"
#
#*********************************
#
#set -eu
#
#echo "Checking DB connection ..."
#
#i=0
#until [ $i -ge 10 ]
#do
#  nc -z app-db 3306 && break
#
#  i=$(( i + 1 ))
#
#  echo "$i: Waiting for DB 1 second ..."
#  sleep 1
#done
#
#if [ $i -eq 10 ]
#then
#  echo "DB connection refused, terminating ..."
#  exit 1
#fi
#
#echo "DB is up ..."