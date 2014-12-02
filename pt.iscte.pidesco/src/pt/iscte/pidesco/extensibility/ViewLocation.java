package pt.iscte.pidesco.extensibility;

import org.eclipse.core.runtime.Assert;

/**
 * Describes the location of a view.
 */
public final class ViewLocation {
	
	/**
	 * View position with respect to another relative view
	 */
	public enum Position {
		TOP, BOTTOM, LEFT, RIGHT;
	}
	
	private final String viewId;
	private final String relativeViewId;
	private final Position position;
	private final double ratio;
	
	/**
	 * Creates a location relative to an existing view.
	 * @param viewId (non-null) view id
	 * @param relativeViewId view id to which the view position is relative to; if null, the view is relative to the editor area
	 * @param position position to the relative view
	 * @param ratio percentage of the available space to fill in; value must be within ]0.0, 1.0[
	 */
	public ViewLocation(String viewId, String relativeViewId, Position position, double ratio) {
		Assert.isNotNull(viewId, "view id cannot be null");
		Assert.isNotNull(position, "position cannot be null");
		Assert.isTrue(ratio > 0.0 && ratio < 1.0, "ratio must be in the range ]0.0, 1.0[");
	
		this.viewId = viewId;
		this.relativeViewId = relativeViewId;
		this.position = position;
		this.ratio = ratio;
	}
	
	/**
	 * Creates a location relative to the editor area.
	 * @param viewId (non-null) view id
	 * @param position position to the relative view
	 * @param ratio percentage of the available space to fill in; value must be within ]0.0, 1.0[
	 */
	public ViewLocation(String viewId, Position position, double ratio) {
		this(viewId, null, position, ratio);
	}
	
	/**
	 * Returns the view id
	 * @return (non-null)
	 */
	public String getViewId() {
		return viewId;
	}

	/**
	 * Returns the relative view id
	 * @return may be null, meaning that the relative view is the editor area
	 */
	public String getRelativeViewId() {
		return relativeViewId;
	}

	/**
	 * Returns the position to which the view is place in relation with the relative view
	 * @return (non-null)
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Returns the ratio of available space that the view fills in.
	 * @return value in the range ]0.0, 1.0[
	 */
	public double getRatio() {
		return ratio;
	}	
}