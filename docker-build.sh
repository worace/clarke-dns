#!/bin/sh

sudo docker build -t worace/clarke-dns .
sudo docker push worace/clarke-dns:latest
