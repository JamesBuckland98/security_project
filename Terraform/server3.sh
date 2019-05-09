#!/bin/bash
cd /root
touch gitlab_project_keypair.key
cat << `EOF` >> gitlab_project_keypair.key
-----BEGIN RSA PRIVATE KEY-----
MIIEpQIBAAKCAQEA7GI4dOybS2ipy02WbSf3Pt21+cpe6dOCLNq8AUvhYJHsNOWi
qeqoQKFC/sh8xVab3mk2zmnIbDmZRfkDtuulYr/5CbYZQFuJjSjAR7RVmVrJE6pE
Fh2tzoPgyoYPvU7C9y5rr8qAqvahyYIfyOjLEQMoERDM6Qq6e5GXxz2jEIeRhwb+
Ef+NBIU2NBNfXWRTQ8rrd4JW3FdCp9USKEktx82VP/46OB2k0JT/zlmVOwoDVP5d
jL0dFVUia6bZjwOICGC9IqDReWFANtjGEalc0GyEEMX4kDccCgjrX+IoFIN6LCV6
ZiEx5p/wHFTtaNODP9KUi3sqMEK4se3qB/JvwQIDAQABAoIBAQCPX4uqr5AJjvPd
kbO5hVIbWVJ0rWAgJt9/4TPC/Yk3j4pbEyU0NlXSzXzbGDNFZiCoJ9zMGbEso0rp
+/1ax1bKrHGaiE6MkHi/m4X0d9arazsmqFEfsOSoq6h+bg3B3rG2Rvud5g9gorLC
jCBHNZtlOCrnHu+KMa2mIUqT2Di9XrJktNIN4pYx/FKt6fydd+pHzO4eOH36KOyP
IGAv89TeeAj3pe4vUiTijRhe35pHjkz2/iqpyu43HQR6Nzp5ryff2gbrVz9rOlsv
udQHNdQqIBYFBIqUSl1DrUmfR0P8uJSqo+C0haxj496U8j9uI16o7zDwtb4rtl6q
rj4c5koZAoGBAPepFvKeMJ/zUsuxevSJtT5ntGqKVgou3jMwivUo1CFdRojX1w1q
VbJc4+ldVbXiBYisMgMPh0P9wafuYw4J6j3SUTsDFTBBBrwPSIPyJskHfr1Hut/0
pgNlBB0lD44SOS/Rb3+jjzpddBn510BdD0o64m7ia14Bj4fjld7+0IV/AoGBAPRX
681Z1ksNcgQEuQpKgM2vE1x+NB3g6WzgA82TF+rLy5bZOaWQORaNLrFAn406IFYY
npdc1NT+7YDBEdcHekUhOHactEaXAD86/mATkh19mh3gTUqn7Q2UegXONWMMRNiP
Y3vvn63FoXH/Fwdf26+VFbHQVHiDoWnBwPm3iCq/AoGBAIhZFmIyh1C4VKACMVBK
NHeL/r8dNCtdDEYgdpBsjwVjZf7W3fXr0gxlqbCECzkcm7FYJBydQvpCzYhX7T8A
ZG7uyHv1wYeUdVG6FFFYUtQghT1OLWs5NODvj2lASyULkWJrKriZ4rPeWZone8Na
uJ1Ed/9WNohF0bCdsphXr5OfAoGBAPRMd7nASa6OieGUq0Grze5YTn5avFbnSduO
ep7uo6ohCrQPCkEL2tg+XmHgn+W/KReL0/y4Vx+Cpwnlf6XDly9JEMAe22v8i8Ws
gSqkRqHvs8iNPBoZnbzHJ91oGB6o5Ki1bJ08ryU6BLoKdwbrg6Wg5Kj9B4fa6+bm
TsAeDSnXAoGAbkRsu6teRXZIGdkY3Olk23Pr/9YEjJ+toPKREGaUYSCvUAw56WyT
QlRB7p12HQ7x9lr3/BRgPbuzwRZKti0d1QOdcH1FOJVvr6fIQyBN6YwV/MIcJtgK
Xg12BSLfyRWXJkL4xK49ia7VlhGx+siqELyOEVkX0XHNLhEt7KqhE7U=
-----END RSA PRIVATE KEY-----
`EOF`
chmod 400 gitlab_project_keypair.key

echo "upgrading..."
sudo yum upgrade -y

echo "updating..."
sudo yum update -y

echo "Download and add the repository..."
sudo yum install wget -y
wget https://dev.mysql.com/get/mysql80-community-release-el7-2.noarch.rpm
sudo rpm -ivh mysql80-community-release-el7-2.noarch.rpm

echo "installing mysql..."
sudo yum install mysql-server -y

echo "installing git..."
sudo yum install git -y

echo "installing gitlab server key..."
touch .ssh/known_hosts
ssh-keyscan gitlab.cs.cf.ac.uk >> .ssh/known_hosts
chmod 644 .ssh/known_hosts

# code from https://tecadmin.net/install-oracle-java-11-lts-on-centos/
echo "installing java..."
wget --no-cookies --no-check-certificate --header "Cookie: oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/11.0.2+9/f51449fcd52f4d52b93a989c5c56ed3c/jdk-11.0.2_linux-x64_bin.rpm"
sudo yum localinstall jdk-11.0.2_linux-x64_bin.rpm -y

echo "setting Java env variables..."
export JAVA_HOME=/usr/java/jdk-11.0.2
export PATH=$JAVA_HOME/bin:$PATH
java -version

echo "cloning repository..."
ssh-agent bash -c 'ssh-add gitlab_project_keypair.key; git clone git@gitlab.cs.cf.ac.uk:c1733667/year2_drones_team1.git'

sudo systemctl set-environment MYSQLD_OPTS="--skip-grant-tables"
sudo systemctl start mysqld

# create the database for the project, and set the password to empty
mysql -u root -e "UPDATE mysql.user SET authentication_string='' WHERE user='root';"

#change  server config so passwords matter
sudo systemctl set-environment MYSQLD_OPTS=""
sudo systemctl stop mysqld
sudo systemctl start mysqld


# change password to something sensible
mysql -u root -e "ALTER USER 'root'@'localhost' IDENTIFIED BY '@Password123';" --connect-expired-password

cat year2_drones_team1/Database/ASG_DRONES_CREATE_V7.sql | mysql -u root -p'@Password123'

cd /root/year2_drones_team1/Project
chmod +x ./gradlew
./gradlew assemble
./gradlew test
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
