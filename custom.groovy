#!/usr/bin/env groovy
import jenkins.*
import hudson.*
import jenkins.model.*
import hudson.security.*
import ru.yandex.qatools.allure.jenkins.tools.*
import hudson.tools.InstallSourceProperty
import hudson.tools.ToolProperty
import hudson.tools.ToolPropertyDescriptor
import hudson.util.DescribableList

// 设置admin账户
def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin","admin")
instance.setSecurityRealm(hudsonRealm)
def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)
instance.save()

// 设置环境变量
properties = Jenkins.getInstance().getGlobalNodeProperties()
nodes = properties.getAll(hudson.slaves.EnvironmentVariablesNodeProperty.class)
if ( nodes == null || nodes.size() == 0 ) {
    property = new hudson.slaves.EnvironmentVariablesNodeProperty();
    properties.add(property)
    nodes = properties.getAll(hudson.slaves.EnvironmentVariablesNodeProperty.class)
} 
envVars= nodes.get(0).getEnvVars()
envVars.put("PATH", "/bin/:/usr/bin:/usr/local/bin:/usr/local/python3/bin/")
Jenkins.getInstance().save()



// 设置allure
def installation = new AllureCommandlineInstallation("allure-2.21.0", "/opt/allure-2.21.0/", null)
def allureDesc = jenkins.model.Jenkins.instance.getExtensionList(AllureCommandlineInstallation.DescriptorImpl.class)[0]
allureDesc.setInstallations(installation)
allureDesc.save()
