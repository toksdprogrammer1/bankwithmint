FROM openjdk:11

MAINTAINER Tokunbo Ojo <ojotokunboibukun@gmail.com>

EXPOSE 9083 9083

# Install and setup
COPY install.sh /root/bankwithmint/install.sh
COPY setup.sh /root/bankwithmint/setup.sh

RUN chmod +x /root/bankwithmint/setup.sh

RUN /root/bankwithmint/setup.sh


COPY src/main/resources/application-docker.properties /opt/bankwithmint/application-docker.properties

ADD target/bankwithmint.tar.gz /opt/bankwithmint
WORKDIR /opt/bankwithmint

RUN chmod +x /root/bankwithmint/install.sh
CMD  /root/bankwithmint/install.sh

ENV http_proxy http://172.25.30.117:6060/
ENV https_proxy http://172.25.30.117:6060/

RUN rm -f /etc/localtime
RUN ln -s /usr/share/zoneinfo/Africa/Lagos /etc/localtime