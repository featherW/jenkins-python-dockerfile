import jenkins.model.*
import hudson.security.*

def USER = "admin"
def PASSWORD = "admin"

def instance = Jenkins.getInstance()
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount(USER, PASSWORD)
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)
Instance.save()
