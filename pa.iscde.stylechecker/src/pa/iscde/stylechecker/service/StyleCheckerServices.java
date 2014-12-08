package pa.iscde.stylechecker.service;

import pt.iscte.pidesco.projectbrowser.model.PackageElement;

/**
 * Service containing the operations offered by the StyleChecker view.
 */
public interface StyleCheckerServices {

	/**
	 * Id of the refresh tool
	 */
	public static final String REFRESH_TOOL_ID = "pt.iscte.pidesco.projectbrowser.refresh";
	
	/**
	 * Obtain the root of the tree.
	 * @return non-null reference
	 */
	PackageElement getRootPackage();

	/**
	 * Activate StyleRule
	 * @param ruleId (non-null) id of the filter to activate
	 */
	void activateRule(String ruleId);

	/**
	 * Deactivate Rule
	 * @param ruleId (non-null) id of the rule to deactivate
	 */
	void deactivateRule(String ruleId);

	/**
	 * Add a style checker listener. If listener already added, does nothing.
	 * @param listener (non-null) reference to the listener
	 */
	void addListener(StyleCheckerRuleListener listener);

	/**
	 * Remove a style checker listener. If listener does not exist, does nothing.
	 * @param listener (non-null) reference to the listener
	 */
	void removeListener(StyleCheckerRuleListener listener);

}
