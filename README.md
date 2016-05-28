## Clarke Coin DNS Server

This is a simple Ring web API to help with discovering peer nodes on the clarke coin network.
Nodes can submit their address to the registry and retrieve a list of other nodes currently online.

Consult the [Swagger Docs](http://159.203.204.18/index.html#/api) for the API.

### Live Instance:

[http://159.203.204.18/](http://159.203.204.18/)

## Usage

### Run the application locally

`lein run`

### Deploying

First, make sure you are logged in to docker hub

```
sudo docker login
```

```
sudo docker build -t worace/clarke-dns .
sudo docker push worace/clarke-dns:latest
ssh root@159.203.204.18
# these will be run on the host machine
docker pull worace/clarke-dns:latest
docker ps -q --filter ancestor=worace/clarke-dns | xargs docker stop
docker run worace/clarke-dns:latest
```

## License

Copyright © Horace Williams
