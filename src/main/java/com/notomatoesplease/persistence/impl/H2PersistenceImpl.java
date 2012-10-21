package com.notomatoesplease.persistence.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.persistence.Persistence;

public class H2PersistenceImpl implements Persistence {

    private static final Logger LOG = LoggerFactory.getLogger(H2PersistenceImpl.class);

    public H2PersistenceImpl() {
        LOG.debug("using H2 database");
    }

    @Override
    public List<Size> getSizes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Dough> getDoughs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Sauce> getSauces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Topping> getToppings() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveToppings(final List<Topping> toppings) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveDoughs(final List<Dough> doughs) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveSauces(final List<Sauce> sauces) {
        // TODO Auto-generated method stub

    }

}
