package pa.iscde.perspectives.services;

import java.util.List;
import pa.iscde.perspectives.model.Perspective;

public interface PerspectiveServices
{

	/**
	 * Returns the last perspective to be activated if one was activaed
	 * 
	 * @return Perspetive name or NULL if none was activated
	 */
	public Perspective getCurrentPerspective();

	/**
	 * Returns an array with all available perspectives
	 * 
	 * @return
	 */
	public List<Perspective> getAvailablePerspectives();

	/**
	 * Triggers refresh of perspectives list on perspectives view switcher
	 */
	public void refreshAvailablePerspectives();

	/**
	 * Triggers Create Perspective Modal Window
	 */
	public void createPerspective();

	/**
	 * Triggers refresh of loaded pidesco views
	 */
	public void refreshPidescoViews();
	/**
	 * Returns an array with all loaded pidesco views
	 * 
	 * @return
	 */
	public List<String> getLoadedPidescoViews();

	/**
	 * Changes current perspective if possible (must be a valid one)
	 * @param p
	 */
	public void changePerspective(Perspective p);
}
