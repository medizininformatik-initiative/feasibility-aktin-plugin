#FROM ghcr.io/aktin/aktin-client:1.4
FROM aktin-client-test:0.0.3

USER root
COPY run_client.sh /opt/aktin
COPY sysproc.properties /opt/aktin
COPY feasibility-aktin-1.0.jar /opt/aktin/lib
RUN chown -R 10001:10001 /opt/aktin
RUN cp libs/* /opt/aktin/libs

USER 10001
