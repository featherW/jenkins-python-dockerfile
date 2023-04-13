#!/usr/bin/env groovy
import ru.yandex.qatools.allure.jenkins.tools.*
import hudson.tools.InstallSourceProperty
import hudson.tools.ToolProperty
import hudson.tools.ToolPropertyDescriptor
import hudson.util.DescribableList

def installation = new AllureCommandlineInstallation("allure-2.21.0", "/opt/allure-2.21.0/", null)
def allureDesc = jenkins.model.Jenkins.instance.getExtensionList(AllureCommandlineInstallation.DescriptorImpl.class)[0]
allureDesc.setInstallations(installation)
allureDesc.save()

