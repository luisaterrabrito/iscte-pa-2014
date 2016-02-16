# Introduction #

This extension allows you to provide your own customized layout to Call Graph viewer.


# Details #

You will need to implement CustomLayout, and override the method getCustomLayout() and return a LayoutAlgorithm object. This object can be processed as you want.
Example:

> @Override
> public LayoutAlgorithm getCustomLayout() {
> > SpringLayoutAlgorithm layout = new SpringLayoutAlgorithm();
> > layout.setEntityAspectRatio(3.0);
> > layout.setStyle(2);
> > return layout;

> }