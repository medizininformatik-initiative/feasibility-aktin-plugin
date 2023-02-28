#!/usr/bin/env bash

LOG_LEVEL=${LOG_LEVEL:-INFO}
LOG_LEVEL_FEASIBILITY=${LOG_LEVEL_FEASIBILITY:-FINE}

echo "Setting log level to $LOG_LEVEL"
sed -i 's/^\.level.*/.level='$LOG_LEVEL'/g' logging.properties

echo "Setting log level feasibility to $LOG_LEVEL_FEASIBILITY"
sed -i 's/^feasibility\.level.*/feasibility.level='$LOG_LEVEL_FEASIBILITY'/g' logging.properties

echo "Running aktin client from console..."

java $JAVA_OPTS -cp lib/\* -Djava.util.logging.config.file=logging.properties org.aktin.broker.client.live.CLI feasibility.FeasibilityExecutionPlugin sysproc.properties
