package com.notomatoesplease.app.Persistence;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.persistence.impl.H2PersistenceImpl;

/**
 * H2DB Persistence Tests
 *
 */
public class H2DBTest {
    @Test
    public void testGetSizes() {
        H2PersistenceImpl h2Impl = new H2PersistenceImpl();
        List<Size> sizes = h2Impl.getSizes();
        Assert.assertNotNull(sizes);
        Assert.assertTrue(sizes.size() > 0);
        Assert.assertNotNull(sizes.get(0));
        Assert.assertNotNull(sizes.get(0).getName());
        Assert.assertNotNull(sizes.get(0).getPrice());
    }

    @Test
    public void testGetDoughs() {
        H2PersistenceImpl h2Impl = new H2PersistenceImpl();
        List<Dough> doughs = h2Impl.getDoughs();
        Assert.assertNotNull(doughs);
    }

    @Test
    public void testGetSauces() {
        H2PersistenceImpl h2Impl = new H2PersistenceImpl();
        List<Sauce> sauces = h2Impl.getSauces();
        Assert.assertNotNull(sauces);
    }

    @Test
    public void testGetToppings() {
        H2PersistenceImpl h2Impl = new H2PersistenceImpl();
        List<Topping> toppings = h2Impl.getToppings();
        Assert.assertNotNull(toppings);
    }
}
