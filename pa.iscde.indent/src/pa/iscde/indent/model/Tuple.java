package pa.iscde.indent.model;


/**
 * Represents a package for pair of regular expression and rule.
 */
public class Tuple { 
	  public final String regex; 
	  public final String replacement;
	  
	  /**
	   * Default Constructor.
	   * 
	   * @param String holder to a regular expression.
	   * @param String holder to for a rule to be used as a map to replace.
	   */
	  public Tuple(String regex, String replacement) { 
	    this.regex = regex; 
	    this.replacement = replacement; 
	  } 
	} 