#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR
cd ..

if [ -e "RUNNING_PID" ]
then
  if kill -0 `cat RUNNING_PID` 
  then
    echo "killing proccess"
    kill `cat RUNNING_PID`
    sleep 5
    if [ -e "RUNNING_PID" ]
    then
      echo "now force killing"
      kill -9 `cat RUNNING_PID`
      rm RUNNING_PID
    fi
  else
    rm RUNNING_PID
  fi
fi
