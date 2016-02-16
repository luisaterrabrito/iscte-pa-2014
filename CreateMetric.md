# Introduction #

This extension point offers other plugins the ability to add new metrics to the metrics table that I provide. The extention point requires a class that implements the interface Metricable. This interface establishes that the class provided must have the name of the metric and how it is calculated. In order to create a new metric as a extension for this extension point, must be taken in account that to access the code to be analyzed, the javaServices and browserServices must be used. With the interface DefaultVisitor I allow access to the values calculated for the default metrics.

# Details #
```
public interface Metricable {

	public double calculateMetric(DefaultVisitor dv);
	
}

public interface DefaultVisitor {

	public int getMethodCounter();

	public int getPhysicalLineCounter();

	public int getStaticMethods();

	public int getInterfaceCounter();

	public int getClassCounter();

	public int getLogicalLineCounter();

	public int getAttributeCounter();	
}


```