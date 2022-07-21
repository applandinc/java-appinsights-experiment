# Sending event data to Microsoft Azure Application Insights

This is a POC of sending event data with Java to Microsoft Azure Application
Insights without the need to use the whole agent. This is useful
if AppInsights is used as a generic telemetry platform for eg. tracking usage
in a standalone application, where we don't want to or cannot instrument
the code and only care about sending some custom events.
