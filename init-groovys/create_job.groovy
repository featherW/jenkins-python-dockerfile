#!/usr/bin/env groovy

import jenkins.model.*
import hudson.tasks.Shell
import hudson.model.FreeStyleProject
import ru.yandex.qatools.allure.jenkins.AllureReportPublisher
import ru.yandex.qatools.allure.jenkins.config.ResultsConfig


def parent = Jenkins.getInstance()
def project = new FreeStyleProject(parent, "sample")
project.setDescription("default sample")
def script = new Shell("pytest /var/sample.py  --alluredir=allure-results")
project.getBuildersList().add(script)
def config = new ResultsConfig("allure-results")
def config_list = [config]
def publisher = new AllureReportPublisher(config_list)
project.getPublishersList().add(publisher)


project.save()
parent.reload()
