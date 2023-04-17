import hudson.model.FreeStyleProject
import hudson.plugins.emailext.ExtendedEmailPublisher
import hudson.plugins.emailext.plugins.recipients.ListRecipientProvider
import hudson.plugins.emailext.plugins.trigger.FailureTrigger
import hudson.tasks.Shell
import hudson.triggers.TimerTrigger
import jenkins.model.Jenkins
import ru.yandex.qatools.allure.jenkins.AllureReportPublisher
import ru.yandex.qatools.allure.jenkins.config.ResultsConfig

def PROJECT_NAME = "sample"
def PROJECT_DESCRIPTION = "a sample"
def SHELL_SCRIPT = "pytest /var/sample.py  --alluredir=allure-results"
def CRON = "H/30 * * * * "

// 创建任务
def parent = Jenkins.getInstance()
def project = new FreeStyleProject(parent, PROJECT_NAME)
project.setDescription(PROJECT_DESCRIPTION)
def script = new Shell(SHELL_SCRIPT)
project.getBuildersList().add(script)

// 设置allure-report
def config_list = [new ResultsConfig("allure-results")]
def allure_publisher = new AllureReportPublisher(config_list)
project.getPublishersList().add(allure_publisher)

// 设置邮件
def email_publisher = new ExtendedEmailPublisher()
def provider_list = [new ListRecipientProvider()]
FailureTrigger trigger = new FailureTrigger(
        provider_list,
        "\$DEFAULT_RECIPIENTS",
        "\$DEFAULT_REPLYTO",
        "\$DEFAULT_SUBJECT",
        "\$DEFAULT_CONTENT",
        null, 0,
        "text/html");
email_publisher.configuredTriggers.add(trigger)
project.getPublishersList().add(email_publisher)

def cron = new TimerTrigger(CRON)
project.addTrigger(cron)


project.save()
parent.reload()
