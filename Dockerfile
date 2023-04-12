FROM jenkins/jenkins:2.361.3-lts

USER root

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US.UTF-8

ENV PATH $PATH:/usr/local/python3/bin/
ENV PYTHONIOENCODING utf-8

# 设置网络
RUN set -ex \
    && echo 'nameserver 8.8.8.8' >>  /etc/resolv.conf \
    && echo 'nameserver 114.114.114.114' >>  /etc/resolv.conf \
    command_depending_on_dns_resolution

# 设置源
RUN mv /etc/apt/sources.list /etc/apt/sources.list.bak
RUN echo 'deb https://mirrors.aliyun.com/debian/ bullseye main non-free contrib' > /etc/apt/sources.list 
RUN echo 'deb-src https://mirrors.aliyun.com/debian/ bullseye main non-free contrib' >> /etc/apt/sources.list 
RUN apt update

# 安装python
RUN apt install -y  vim build-essential libncurses5-dev zlib1g-dev libnss3-dev libgdbm-dev libssl-dev libsqlite3-dev libffi-dev libreadline-dev curl libbz2-dev 
COPY Python-3.8.0.tgz ./ 
RUN tar -xvzf Python-3.8.0.tgz && rm -f Python-3.8.0.tgz
RUN mkdir /usr/local/python3
RUN cd Python-3.8.0/ \
    && ./configure prefix=/usr/local/python3 \
    && make && make install
RUN cd .. && rm -rf Python-3.8.0
RUN ln -s /usr/local/python3/bin/python3.8 /usr/bin/python
RUN ln -s /usr/local/python3/bin/pip3 /usr/bin/pip
RUN python -m pip install --upgrade pip

# 安装allure-commandline
COPY allure-commandline-2.21.0.tgz /opt/
RUN cd /opt/ \
    && tar -xvzf allure-commandline-2.21.0.tgz  && rm -f allure-commandline-2.21.0.tgz
RUN ln -s /opt/allure-2.21.0/bin/allure /usr/bin/allure
RUN chown -R jenkins:jenkins /opt/allure-2.21.0/

# 安装python依赖包
RUN pip install pytest \
    && pip install allure-pytest

USER jenkins
# 安装jenkins插件
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/plugins.txt

# groovy初始化jenkins
COPY custom.groovy /usr/share/jenkins/ref/init.groovy.d/custom.groovy

# 修改jenkins的配置状态为已配置
RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state

# 构建命令
# docker build -f Dockerfile -t myjenkins:1.0.0 .



