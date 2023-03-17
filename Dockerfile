FROM jenkins/jenkins:2.361.3-lts

USER root

ENV LC_ALL en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US.UTF-8

ENV PATH $PATH:/usr/local/python3/bin/
ENV PYTHONIOENCODING utf-8

RUN set -ex \
    && echo 'nameserver 8.8.8.8' >>  /etc/resolv.conf \
    && echo 'nameserver 114.114.114.114' >>  /etc/resolv.conf \
    command_depending_on_dns_resolution

COPY Python-3.8.0.tgz ./ 
RUN set -ex \
    && tar -xvzf Python-3.8.0.tgz && rm -f Python-3.8.0.tgz

RUN set -ex \
    && mv /etc/apt/sources.list /etc/apt/sources.list.bak \
    && echo 'deb https://mirrors.aliyun.com/debian/ bullseye main non-free contrib' > /etc/apt/sources.list \
    && echo 'deb-src https://mirrors.aliyun.com/debian/ bullseye main non-free contrib' >> /etc/apt/sources.list \
    && apt update \
    && apt install -y  build-essential libncurses5-dev zlib1g-dev libnss3-dev libgdbm-dev libssl-dev libsqlite3-dev libffi-dev libreadline-dev curl libbz2-dev \
    && mkdir /usr/local/python3 \
    #&& wget https://www.python.org/ftp/python/3.8.0/Python-3.8.0.tar.xz \
    && cd Python-3.8.0 \
    && ./configure prefix=/usr/local/python3 \
    && make && make install \
    && cd .. \
    && rm -rf Python-3.8.0 \
    && cd /usr/local/python3 \
    #&& mv /usr/bin/python /usr/bin/python.bak \
    && ln -s /usr/local/python3/bin/python3.8 /usr/bin/python \
    && ln -s /usr/local/python3/bin/pip3 /usr/bin/pip \
    && python -m pip install --upgrade pip

RUN set -x \
    && jenkins-plugin-cli --plugins  allure-jenkins-plugin
COPY allure-commandline-2.21.0.tgz /var/jenkins_home/
RUN set -ex \
    && cd /var/jenkins_home/ \
    && tar -xvzf allure-commandline-2.21.0.tgz  && rm -f allure-commandline-2.21.0.tgz \
    && ln -s /var/jenkins_home/allure-2.21.0/bin/allure /usr/bin/allure
COPY ru.yandex.qatools.allure.jenkins.tools.AllureCommandlineInstallation.xml  /var/jenkins_home/
