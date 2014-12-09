package pa.iscde.formulas.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pa.iscde.formulas.NewFormula;

public class NewFormulaJUnit {
	private NewFormula nf;
	private NewFormula anotherNF;
	private NewFormula otherNF;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		String [] inputs = {"a", "b", "c"};
		String algorithm = "Algorithm Example";
		String javaCode = "Java code example";
		nf = new NewFormula("Example",inputs , algorithm, javaCode);
		anotherNF = new NewFormula("Example",inputs , algorithm, javaCode);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void notNull() {
		Assert.assertNotNull(nf.inputs());
	}
	
	@Test
	public void equal() {
		Assert.assertEquals(nf.name(), anotherNF.name());
	}
	
	@Test
	public void notTheSame() {
		Assert.assertNotSame(nf, anotherNF);
	}
	
	@Test(expected=NullPointerException.class)
	public void nullPointer() {
		otherNF.name();
	}
	
	@Test
	public void shouldFail() {
		//should fail the test
		Assert.assertSame(nf, anotherNF);
	}


}
