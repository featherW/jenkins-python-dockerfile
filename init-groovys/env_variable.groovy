#!/usr/bin/env groovy
import jenkins.*
import hudson.*
import jenkins.model.*

def JENKINS_URL = InetAddress.localHost.hostAddress + ":18080"
def jenkinsLocationConfiguration = JenkinsLocationConfiguration.get()
jenkinsLocationConfiguration.setUrl(JENKINS_URL)
jenkinsLocationConfiguration.save()

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

