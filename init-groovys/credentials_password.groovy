import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl
import jenkins.model.Jenkins

def EMAIL_NAME= "test_jenkins2023@163.com"
def CREDENTIALS_ID = "sample_email_id"
def DESCRIPTION="Example credential"
def PASSWORD ="authorization token"


global_domain = Domain.global()
credentials_store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

credentials = new UsernamePasswordCredentialsImpl(
        CredentialsScope.GLOBAL,
        CREDENTIALS_ID,
        DESCRIPTION,
        EMAIL_NAME,
        PASSWORD)

credentials_store.addCredentials(global_domain, credentials)


