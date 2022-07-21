package land.app.experiment.appinsights;

import java.util.Arrays;
import java.util.Objects;

import com.azure.monitor.opentelemetry.exporter.implementation.ApplicationInsightsClientImpl;
import com.azure.monitor.opentelemetry.exporter.implementation.ApplicationInsightsClientImplBuilder;
import com.azure.monitor.opentelemetry.exporter.implementation.models.ExportResult;
import com.azure.monitor.opentelemetry.exporter.implementation.models.TelemetryItem;

import reactor.core.publisher.Mono;

/* Based on:
https://github.com/Azure/azure-sdk-for-java/blob/4a53adf6274ced5af8243983042b5e32bac85bd7/sdk/monitor/azure-monitor-opentelemetry-exporter/src/main/java/com/azure/monitor/opentelemetry/exporter/AzureMonitorExporterBuilder.java
*/

public class AppInsightsClient {
  private String instrumentationKey;
  private ApplicationInsightsClientImpl client;

  public AppInsightsClient(String connectionString) {
    Objects.requireNonNull(connectionString);

    ApplicationInsightsClientImplBuilder builder = new ApplicationInsightsClientImplBuilder();

    for (String setting : connectionString.split(";")) {
      String[] kv = setting.split("=");
      if (kv.length != 2)
        continue;

      switch (kv[0]) {
        case "InstrumentationKey":
          instrumentationKey = kv[1];
          break;
        case "IngestionEndpoint":
          builder.host(kv[1]);
          break;
      }
    }

    client = builder.buildClient();
  }

  public Mono<ExportResult> track(TelemetryItem item) {
    item.setInstrumentationKey(instrumentationKey);
    /* TODO: add context (session and user id) */
    return client.trackAsync(Arrays.asList(item));
  }
}
