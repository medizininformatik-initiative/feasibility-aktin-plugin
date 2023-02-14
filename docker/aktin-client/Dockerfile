FROM ghcr.io/aktin/aktin-client:1.4

USER root
COPY run_client.sh /opt/aktin
COPY sysproc.properties /opt/aktin
COPY feasibility-aktin-1.0.jar /opt/aktin/lib
RUN chown -R 10001:10001 /opt/aktin
RUN apk update && apk upgrade && apk add bash
USER 10001
