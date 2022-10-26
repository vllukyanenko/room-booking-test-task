# vladyslav.lukianenko test project

Additional libraries:
1) io.jsonwebtoken - need for working with JWT;
2) org.mapstruct - not bad entity mapper. Need to mapping Dtos to entities;
3) swagger.doc - to providing api documentation;


## Getting started
1) create db schema in local DB
2) add you local DB credentials to application-dev.yml;
3) choose dev profile at maven plugin;
4) If needed, you can change application time booking set up at application.yml in section booking.time 
5) run mvn package 
6) Run app

Additional:
swagger dock link http://localhost:8080/swagger-ui/index.html

user credentials
login: user@aimp.com
password: user
--------------------
admin credentials
login: admin@aimp.com
password: admin
---------------------

