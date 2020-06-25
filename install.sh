#!/bin/bash
#
# We configure the distro, here before it gets imported into docker
# to reduce the number of UFS layers that are needed for the Docker container.
#

#Change OS time zone
mv -f /etc/localtime /etc/localtime.bak
ln -s /usr/share/zoneinfo/Africa/Lagos /etc/localtime

#download keystore from repo
set -e
cd /opt/bankwithmint/config/


cd /opt/bankwithmint/

java -jar -Dspring.profiles.active=docker bankwithmint.jar