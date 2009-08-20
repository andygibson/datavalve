package org.jdataset.db;

import junit.framework.TestCase;

public class RestrictionBuilderTest extends TestCase {
	
	public void testLogicalOpFinder() {

/*		//Pattern p = Pattern.compile("\\A[ (]*(and|or)[ (]*",Pattern.CASE_INSENSITIVE);
		//Pattern p = Pattern.compile("(and|or)[ (]*(.)?",Pattern.CASE_INSENSITIVE);
		Pattern p = Pattern.compile("\\A[ (]*\\b(and|or)\\b[ (]*.*",Pattern.CASE_INSENSITIVE);
		
		assertTrue(p.matcher(" AND  ").matches());
		assertFalse(p.matcher("x=s  AND y=ui").matches());
		assertFalse(p.matcher("x=s  ANDY.d=ds").matches());
		assertFalse(p.matcher("ANDs").matches()); 
		assertTrue(p.matcher("and (x=r").matches());		
		assertTrue(p.matcher("and x = r").matches());		
		assertTrue(p.matcher("or x = y").matches());*/
		
		
		RestrictionBuilder builder = new RestrictionBuilder(null);
		assertTrue(builder.startsWithLogicalOperator("AND"));
		assertTrue(builder.startsWithLogicalOperator("OR"));
		assertTrue(builder.startsWithLogicalOperator("  AND"));
		assertTrue(builder.startsWithLogicalOperator("  OR"));
		assertTrue(builder.startsWithLogicalOperator("  AND  "));
		assertTrue(builder.startsWithLogicalOperator("  OR  "));
		
		assertTrue(builder.startsWithLogicalOperator("or x = y"));
		assertTrue(builder.startsWithLogicalOperator(" AND x = y"));
		assertTrue(builder.startsWithLogicalOperator(" OR x = y"));
		assertTrue(builder.startsWithLogicalOperator(" AND(x = y)"));
		assertTrue(builder.startsWithLogicalOperator(" OR(x = y)"));

		assertFalse(builder.startsWithLogicalOperator("ore.x =2"));
		assertFalse(builder.startsWithLogicalOperator("sand =2"));
		assertFalse(builder.startsWithLogicalOperator(" ore.x =2"));
		assertFalse(builder.startsWithLogicalOperator(" sand =2"));
		assertFalse(builder.startsWithLogicalOperator("   ore.x =2"));
		assertFalse(builder.startsWithLogicalOperator("   sand =2"));
		assertFalse(builder.startsWithLogicalOperator("   (ore.x =2"));
		assertFalse(builder.startsWithLogicalOperator("   (sand =2"));
		
		
	}

}

