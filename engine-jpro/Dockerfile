FROM adoptopenjdk:11-hotspot-focal
RUN apt-get update
RUN apt-get -y install xorg gtk2-engines libasound2 libgtk2.0-0
# Added in docker-compose.yml 
# ADD . jproserver/
CMD (cd jproserver/; ./bin/restart.sh)