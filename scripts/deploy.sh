#!/bin/bash
set -e

[[ -z $1 ]] && ( printf "Server IP are not provided, aborting.."; exit 1 )
# TODO check if server is up

SERVER_IP=$1
WAR_PATH="target/game.war"

[[ -f $WAR_PATH ]] || ( printf "Build artifact not found, aborting..."; exit 1 )

printf "Undeploying existing app on Tomcat server...\\n"
STATUS=$(
  curl -s \
  -u "$TOMCAT_PROD_USER:$TOMCAT_PROD_PASSWORD" \
  -X GET \
  "http://$SERVER_IP:8080/manager/text/undeploy?path=/api"
  )
printf "[%s]\\n" "$STATUS"

printf "Deploying new artifacts from Travis...\\n"
STATUS=$(
  curl -s \
  -u "$TOMCAT_PROD_USER:$TOMCAT_PROD_PASSWORD" \
  -X PUT \
  -F "file=@$WAR_PATH" "http://$SERVER_IP:8080/manager/text/deploy?path=/api"
  )
printf "[%s]\\n" "$STATUS"

printf "Deploying job done."
exit 0