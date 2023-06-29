import hudson.plugins.emailext.ExtendedEmailPublisherDescriptor
import hudson.plugins.emailext.MailAccount
import hudson.tasks.Mailer
import hudson.tasks.SMTPAuthentication
import jenkins.model.Jenkins
import jenkins.model.JenkinsLocationConfiguration

def JENKINS_URL = ""
def EMAIL_NAME= "test_jenkins2023@163.com"
def EMAIL_SUFFIX = "@163.com"
def EMAIL_PORT = "465"
def EMAIL_SERVER = "smtp.163.com"
def CREDENTIALS_ID = "sample_email_id"
def CONTENT_TYPE = "text/html"
def EMAIL_SUBJECT = "jenkins build notify:\${PROJECT_NAME} - Build # \${BUILD_NUMBER} - \${BUILD_STATUS}!"
def EMAIL_BODY = """
<!DOCTYPE html>    
<html>    
<head>    
<meta charset="UTF-8">    
<title>\${JOB_NAME}-no.\${BUILD_NUMBER}</title>    
</head>    
    
<body leftmargin="8" marginwidth="0" topmargin="8" marginheight="4"    
    offset="0">    
    <table width="95%" cellpadding="0" cellspacing="0"  style="font-size: 11pt; font-family: Tahoma, Arial, Helvetica, sans-serif">    
        <tr>    
            This email is automatically sent by the system, no reply required!<br/>            
            Hello everyone, the following is the construction information of the \${PROJECT_NAME}</br> 
            <td><font color="#CC0000">build result - \${BUILD_STATUS}</font></td>   
        </tr>    
        <tr>    
            <td><br />    
            <b><font color="#0B610B">build info</font></b>    
            <hr size="2" width="100%" align="center" /></td>    
        </tr>    
        <tr>    
            <td>    
                <ul>    
                    <li>project name: \${PROJECT_NAME}</li>    
                    <li>build number: \${BUILD_NUMBER}</li>    
                    <li>trigger cause: \${CAUSE}</li>    
                    <li>build status: \${BUILD_STATUS}</li>    
                    <li>build log: <a href="\${BUILD_URL}console" rel="external nofollow" >\${BUILD_URL}console</a ></li>    
                    <li>build url: <a href="\${BUILD_URL}" rel="external nofollow" >\${BUILD_URL}</a ></li>    
                    <li>work list: <a href="\${PROJECT_URL}ws" rel="external nofollow" >\${PROJECT_URL}ws</a ></li>    
                    <li>project url: <a href="\${PROJECT_URL}" rel="external nofollow" >\${PROJECT_URL}</a ></li>
                    <li>test report: <a href="\${PROJECT_URL}allure" rel="external nofollow" >\${PROJECT_URL}allure</a ></li>        
                </ul>    

<h4><font color="#0B610B">failure case</font></h4>
<hr size="2" width="100%" />
\$FAILED_TESTS<br/>

<hr size="2" width="100%" />
change list: <a href="\${PROJECT_URL}changes" rel="external nofollow" >\${PROJECT_URL}changes</a ><br/>

            </td>    
        </tr>    
    </table>    
</body>    
</html>
"""

// Manage Jenkins->Configure System->Jenkins Location
def jenkinsLocationConfiguration = JenkinsLocationConfiguration.get()
jenkinsLocationConfiguration.setAdminAddress(EMAIL_NAME)
jenkinsLocationConfiguration.setUrl(JENKINS_URL)
jenkinsLocationConfiguration.save()

// Extended E-mail Notification
def instance = Jenkins.getInstance()
def extmailServer = instance.getDescriptor("hudson.plugins.emailext.ExtendedEmailPublisher")

MailAccount mailAccount = new MailAccount();
mailAccount.setSmtpHost(EMAIL_SERVER)
mailAccount.setSmtpPort(EMAIL_PORT)
mailAccount.setCredentialsId(CREDENTIALS_ID)
mailAccount.setUseSsl(true)
extmailServer.setMailAccount(mailAccount)
extmailServer.setDefaultRecipients(EMAIL_NAME)
extmailServer.setDefaultContentType(CONTENT_TYPE)
extmailServer.setDefaultSuffix(EMAIL_SUFFIX)
extmailServer.setDefaultSubject(EMAIL_SUBJECT)
extmailServer.setDefaultBody(EMAIL_BODY)

instance.save()
instance.reload()
