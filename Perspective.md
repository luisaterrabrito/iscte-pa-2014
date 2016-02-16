# Introduction #

This extensions allows other plugins to add new perpective(s) to the perpective manager plugin. A perpective must own a **name** (short readable String), a **icon** and, of course, the **layout**.


# Details #


A **perspective** is made up of **views**.
Views can have other views as their children.

Each **view** has the following attributes:
  * view\_id -> Unique ID for the view
  * scale\_factor -> Defines how much the view will occupy in relation to it's parent view
  * position -> Position relative to parent view TOP, BOTTOM, LEFT, RIGHT

If the view doesn't have a parent view, the view is positioned relative to the the Code Editor View.

It is possible to develop custom behaviours to your perspective. To do so you must supply a class that implements the PerspectiveListener, available below:
```
public interface PerspectiveListener
{
	/**
	 * Event occurs <b>before</b> activating the perspective
	 */
	public void onPerspectiveActivation();
	/**
	 * Event occurs <b>after</b> activating the perspective
	 */
	public void afterPerspectiveActivation();
	/**
	 * Event occurs <b>before</b> deactivating the perspective
	 */
	public void onPerspectiveDeactivation();
	/**
	 * Event occurs <b>after</b> deactivating the perspective
	 */
	public void afterPerspectiveDeactivation();
}

```

# Example #

![http://puu.sh/eTsCh/c01cb3c161.png](http://puu.sh/eTsCh/c01cb3c161.png)

Perspective (Name: Eclipse Like)

1 View (position="LEFT",scale\_factor="0.5",view\_id="pt.iscte.pidesco.projectbrowser.tree")

2 View (postition="RIGHT",scale\_factor="0.5"view\_id="pa.iscde.teams")

2.1 View (postition="BOTTOM",scale\_factor="0.5",view\_id="pa.iscde.perspectives.perpectives-switcher")