#!/usr/bin/env bash

set -e

CATALINA_HOME='/usr/share/tomcat8'
DEPLOY_TO_ROOT='true'
CONTEXT_PATH='overseer'
SERVER_HTTP_PORT=80
CODE_DEPLOY_DIR='/entropyshift/codedeploy/overseer'
WAR_STAGED_LOCATION='${CODE_DEPLOY_DIR}/${CONTEXT_PATH}.war'
HTTP_PORT_CONFIG_XSL_LOCATION='$CODE_DEPLOY_DIR/configure_http_port.xsl'

# In Tomcat, ROOT.war maps to the server root
if [[ "$DEPLOY_TO_ROOT" = 'true' ]]; then
    CONTEXT_PATH='ROOT'
fi

# Remove unpacked application artifacts
if [[ -f ${CATALINA_HOME}/webapps/${CONTEXT_PATH}.war ]]; then
    rm ${CATALINA_HOME}/webapps/${CONTEXT_PATH}.war
fi
if [[ -d ${CATALINA_HOME}/webapps/${CONTEXT_PATH} ]]; then
    rm -rfv ${CATALINA_HOME}/webapps/${CONTEXT_PATH}
fi

cp $WAR_STAGED_LOCATION $CATALINA_HOME/webapps/$CONTEXT_PATH.war


# Configure the Tomcat server HTTP connector
{ which xsltproc; } || { yum install xsltproc; } || { apt-get install xsltproc; }
cp $CATALINA_HOME/conf/server.xml $CATALINA_HOME/conf/server.xml.bak
xsltproc $HTTP_PORT_CONFIG_XSL_LOCATION $CATALINA_HOME/conf/server.xml.bak > $CATALINA_HOME/conf/server.xml

sudo service tomcat8 start

