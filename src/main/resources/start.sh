#!/bin/bash

nohup java -jar csiapi-0.0.1-SNAPSHOT.jar --server.port=8080 &
tail -f nohup.out