#!/bin/bash
#
# We configure the distro, here before it gets imported into docker
# to reduce the number of UFS layers that are needed for the Docker container.


set -e
mkdir -p /opt/bankwithmint
mkdir -p /opt/bankwithmint/config/


cd /opt/bankwithmint

mkdir -p /log
ln -s /log logs
