FROM ghcr.io/aktin/aktin-client:1.4

USER root
COPY sysproc.properties /opt/aktin
RUN chown -R 10001:10001 /opt/aktin
RUN apk update && apk upgrade && apk add bash
USER 10001
