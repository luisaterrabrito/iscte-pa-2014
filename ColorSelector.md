# Introduction #

Interface: PackageDiagramColorExtension

Change letter color and background color depending on the package name


# Details #
```


import org.eclipse.swt.graphics.Color;

public interface PackageDiagramColorExtension {

/**
* Change color letter depending on the package name
* @param packageName Package name to change
* @return color Selected color
*/

Color changeColorLetter(String packageName);

Color changeColorBackground (String packageName);
}```