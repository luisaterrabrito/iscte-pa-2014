# Introduction #

Interface: PackageDiagramActionExtension

Executes an action based in Packages


# Details #
```


package pa.iscde.packagediagram.extensibility;

public interface PackageDiagramActionExtension {

public void run(String packageName);

}
```


Example:
```

@override
public void run(String packageName) {
system.out.println(packageName);
}
```