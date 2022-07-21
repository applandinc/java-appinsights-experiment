/* based on:
https://github.com/Azure/azure-sdk-for-java/blob/b5f92f843173c1c0dc83efedb236482b63f7709f/sdk/monitor/azure-monitor-opentelemetry-exporter/src/main/java/com/azure/monitor/opentelemetry/exporter/implementation/builders/EventTelemetryBuilder.java
https://github.com/Azure/azure-sdk-for-java/blob/b5f92f843173c1c0dc83efedb236482b63f7709f/sdk/monitor/azure-monitor-opentelemetry-exporter/src/main/java/com/azure/monitor/opentelemetry/exporter/implementation/builders/AbstractTelemetryBuilder.java
*/

package land.app.experiment.appinsights;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import com.azure.monitor.opentelemetry.exporter.implementation.models.MonitorBase;
import com.azure.monitor.opentelemetry.exporter.implementation.models.TelemetryEventData;
import com.azure.monitor.opentelemetry.exporter.implementation.models.TelemetryItem;

public class EventTelemetryBuilder {
  private String name;
  private Map<String, String> properties = new HashMap<String, String>();
  private Map<String, Double> measurements = new HashMap<String, Double>();

  public EventTelemetryBuilder(String name) {
    this.name = name;
  }

  public EventTelemetryBuilder property(String key, String value) {
    properties.put(key, value);
    return this;
  }

  public EventTelemetryBuilder measurement(String key, double value) {
    measurements.put(key, value);
    return this;
  }

  public TelemetryItem build() {
    TelemetryEventData data = new TelemetryEventData();
    data.setVersion(2);
    if (name != null)
      data.setName(name);
    if (!properties.isEmpty())
      data.setProperties(properties);
    if (!measurements.isEmpty())
      data.setMeasurements(measurements);

    MonitorBase base = new MonitorBase();
    base.setBaseData(data);
    base.setBaseType("EventData");

    TelemetryItem item = new TelemetryItem();
    item.setTime(OffsetDateTime.now());
    item.setData(base);
    item.setName("Event");
    item.setVersion(1);

    return item;
  }
}
