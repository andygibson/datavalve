package org.jdataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jdataset.testing.junit.AbstractObjectDatasetJUnitTest;
import org.jdataset.util.GenericDataset;

public class BackedDatasetTest extends AbstractObjectDatasetJUnitTest<Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger(BackedDatasetTest.class.getName());
    
    

    public GenericDataset<Integer, BackedDataProvider<Integer>> buildTestDataset() {
    	
    	return buildTestDataset(100);
    }
    
    public GenericDataset<Integer, BackedDataProvider<Integer>> buildTestDataset(final int count) {
    	
        BackedDataProvider<Integer> ds = new BackedDataProvider<Integer>() {

        	private static final long serialVersionUID = 1L;
        	
            @Override
            protected List<Integer> fetchBackingData() {
                List<Integer> res = new ArrayList<Integer>();
                for (int i = 0; i < count; i++) {
                    res.add(i);
                }
                return res;
            }
        };
        return new GenericDataset<Integer, BackedDataProvider<Integer>>(ds);
    }

    public void testGetResultCount() {
    	IObjectDataset<Integer> ds = buildTestDataset();
        int res = ds.getResultCount();
        assertEquals(100, res);
    }

    public void testGetResultsUnbound() {
    	IObjectDataset<Integer> ds = buildTestDataset();
        List<Integer> res = ds.getResultList();
        
        assertNotNull(res);
        assertEquals(100, res.size());
        for (int i = 0; i < 10; i++) {
            assertEquals(i, res.get(i).intValue());
        }
    }

    public void testGetResultsBound() {
    	IObjectDataset<Integer> ds = buildTestDataset();
        ds.setMaxRows(12);
        List<Integer> res = ds.getResultList();

        assertNotNull(res);
        assertEquals(12, res.size());
        for (int i = 0; i < 12; i++) {
            assertEquals(i, res.get(i).intValue());
        }
    }

    public void testLast() {
    	IObjectDataset<Integer> ds = buildTestDataset();
        ds.setMaxRows(10);
        ds.last();        
        assertEquals(10, ds.getPageCount());
        int firstResult = ds.getFirstResult();
        log.fine("First result = "+firstResult);        
        assertEquals(90, firstResult);
    }
    
    public void testLastWithFractionalCount() {
    	IObjectDataset<Integer> ds = buildTestDataset(95);
        ds.setMaxRows(10);
        ds.last();
        Integer firstResult = ds.getFirstResult();        
        assertNotNull(firstResult);
        assertEquals(90, firstResult.longValue());
    }
    
    public void testLastWithNoPaging() {
    	IObjectDataset<Integer> ds = buildTestDataset(5);
        ds.setMaxRows(10);
        ds.last();
        int firstResult = ds.getFirstResult();
        log.fine("First result = "+firstResult);        
        assertEquals(0, firstResult);
    }
    
    public void testPaginationPreviousAllResults() {
    	IObjectDataset<Integer> ds = buildTestDataset(100);
        assertEquals(false, ds.isPreviousAvailable());
    }
    
    public void testPaginationNextAllResults() {
    	IObjectDataset<Integer> ds = buildTestDataset(100);
        assertEquals(false, ds.isNextAvailable());
    }
    
    public void testPaginationPreviousPaged() {
    	IObjectDataset<Integer> ds = buildTestDataset(100);
        ds.setMaxRows(10);
        assertEquals(false, ds.isPreviousAvailable());
    }
    
    public void testPaginationNextPaged() {
    	IObjectDataset<Integer> ds = buildTestDataset(100);
        ds.setMaxRows(10);
        assertEquals(true, ds.isNextAvailable());
    }
    
    public void testPaginationNextNoRead() {
    	IObjectDataset<Integer> ds = buildTestDataset(100);
        ds.setMaxRows(10);
        ds.next();
        assertEquals(true, ds.isNextAvailable());
        assertEquals(true, ds.isPreviousAvailable());
        
    }
    
    public void testPaginationPreviousNoRead() {
        IObjectDataset<Integer> ds = buildTestDataset(100);
        ds.setMaxRows(10);
        ds.previous();
        assertEquals(0,ds.getFirstResult());
        assertEquals(1, ds.getPage());
        assertEquals(true, ds.isNextAvailable());
        assertEquals(false, ds.isPreviousAvailable());        
    }
    
    public void testPaginationLastNoRead() {
    	IObjectDataset<Integer> ds = buildTestDataset(100);
        ds.setMaxRows(10);
        ds.last();
        assertEquals(false, ds.isNextAvailable());
        assertEquals(true, ds.isPreviousAvailable());        
    }
    
    public void testSmalldataset() {
    	IObjectDataset<Integer> ds = buildTestDataset(6);
        ds.setMaxRows(10);        
        assertEquals(false, ds.isNextAvailable());
        assertEquals(false, ds.isPreviousAvailable());
        ds.next();
        assertEquals(6, ds.getResultCount().intValue());
        assertEquals(false, ds.isNextAvailable());
        assertEquals(false, ds.isPreviousAvailable());
        
    }

	@Override
	public IObjectDataset<Integer> buildObjectDataset() {
		return buildTestDataset(100);
	}

	@Override
	public int getDataRowCount() {
		return 100;
	}
    
    
}
