# Changelog

## v2.0.0

### Features

* Make cql fhir client socket timeout configurable
* Update SQ Schema to SQ v3

## v1.6.0

### Bugfixes

* Fix websocket timeout - Update to base AKTIN Client 1.5.1 
* Improve error handling ([#9](https://github.com/medizininformatik-initiative/feasibility-aktin-plugin/pull/9))

## v1.5.0

Initial Version
Updates AKTIN to AKTIN client base version 1.5.0 and adds a specific feasibility plugin.

### Features

* Update Obfuscation to Caching Laplace Obfuscation and Rounding to nearest 10.
* Structured Query validation
* CQL validation
* hard rate limit
* From new AKTIN client version: Allow switch between polling and websocket