# AKTIN Feasibility Plugin

This repository provides a AKTIN Feasibility Plugin, which is deployed as a 
AKTIN feasibility client using Docker.

This repository contains a docker setup, which includes an AKTIN broker `docker/aktin-broker`
, as well as the Dockerfile and build for an AKTIN Client based on this feasibility Plugin. `docker/aktin-client`


## Running the Feasibility Plugin for development

To run the Plugin in your IDE setup the "Application" as the main start file of the application and pass 
the VM option `-Djava.util.logging.config.file=src/test/resources/logging.properties`
and the CLI argument `feasibility.FeasibilityExecutionPlugin src/test/resources/sysproc.properties`
Run using java 17.

To connect the client to a AKTIN broker you can use the AKTIN broker Docker setup in `docker/aktin-broker`
of this repository.
Navigate to the respective folder and execute `docker-compose up -d` to run the AKTIN broker. 
It will be exposed on localhost:8082 by default and one can log into the admin with
username: admin, pw: changeme


### Testing the client manually

To test the client manually start a broker, and run the client in your IDE as explained above.
Log into the admin UI of the broker on `http://localhost:8082/admin/html/index.html`,
select "Generic Request" and click on "show form"

in MediaType enter `application/sq+json` and paste the content from the file in `test/resources/example-sq.json` into
the "Content" field. Click "New Request"
the Request is then published on the broker and the aktin client running in your IDE should pick up the request and execute it.

If you would like to test the whole connection of the client you will need to setup a FLARE and FHIR server, which can be provided by
starting the feasibility-triangle from here https://github.com/medizininformatik-initiative/feasibility-deploy/tree/main/feasibility-triangle








