package pa.iscde.perspectives.extensibility;

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
