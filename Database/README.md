# SETUP DATABASE

## Documentation marklogic 
https://www.marklogic.com/blog/building-a-marklogic-docker-container/

## Command Docker 
docker load < marklogic_image.tar <br>
docker run -d --name=marklogic -p 8000-9000:8000-9000 marklogic:10.0-6-installed

## Access Database URL
http://localhost:8001<br>
Username = Admin<br>
Password = Admin<br>

## Activation API java

curl --anyauth --user Admin:Admin -i -X POST \
    -d'{"rest-api":{"name":"ProjectBDD"}}' \
    -H "Content-type: application/json" \
    http://localhost:8002/LATEST/rest-apis


