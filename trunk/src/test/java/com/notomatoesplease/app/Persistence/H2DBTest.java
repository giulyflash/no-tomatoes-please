package com.notomatoesplease.app.Persistence;

import java.util.List;

import junit.framework.Assert;

import com.notomatoesplease.domain.Size;
import com.notomatoesplease.persistence.impl.H2PersistenceImpl;

/**
 * H2DB Persistence Tests
 *
 */
public class H2DBTest {

    //@Test
    public void testGetSizes() {
        H2PersistenceImpl h2Impl = new H2PersistenceImpl();
        List<Size> sizes = h2Impl.getSizes();
        Assert.assertNotNull(sizes);
        Assert.assertTrue(sizes.size() > 0);
        Assert.assertNotNull(sizes.get(0));
        Assert.assertNotNull(sizes.get(0).getName());
        Assert.assertNotNull(sizes.get(0).getPrice());
    }

    public void testApp() {
        Assert.assertNotNull(null);
    }
}
