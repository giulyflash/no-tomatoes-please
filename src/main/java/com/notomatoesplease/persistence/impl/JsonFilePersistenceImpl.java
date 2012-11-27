package com.notomatoesplease.persistence.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.persistence.Persistence;
import com.notomatoesplease.util.GsonUtil;

public class JsonFilePersistenceImpl implements Persistence {

    private static final Logger LOG = LoggerFactory.getLogger(JsonFilePersistenceImpl.class);

    public JsonFilePersistenceImpl() {
        LOG.debug("using JSON files");
    }

    @Override
    public List<Size> getSizes() {
        // TODO: remove hard coded string and move it to a properties file
        final String json = readJsonFromFile("target/classes/json/sizes.json");

        final Type listType = new TypeToken<ArrayList<Size>>() {
        }.getType();

        return GsonUtil.fromJson(json, listType);
    }

    @Override
    public List<Dough> getDoughs() {
        // TODO: remove hard coded string and move it to a properties file
        final String json = readJsonFromFile("target/classes/json/doughs.json");

        final Type listType = new TypeToken<ArrayList<Dough>>() {
        }.getType();

        return GsonUtil.fromJson(json, listType);
    }

    @Override
    public List<Sauce> getSauces() {
        // TODO: remove hard coded string and move it to a properties file
        final String json = readJsonFromFile("target/classes/json/sauces.json");

        final Type listType = new TypeToken<ArrayList<Sauce>>() {
        }.getType();

        return GsonUtil.fromJson(json, listType);
    }

    @Override
    public List<Topping> getToppings() {
        // TODO: remove hard coded string and move it to a properties file
        final String json = readJsonFromFile("target/classes/json/toppings.json");

        final Type listType = new TypeToken<ArrayList<Topping>>() {
        }.getType();

        return GsonUtil.fromJson(json, listType);
    }

    @Override
    public void saveToppings(final List<Topping> toppings) {
        // TODO: remove hard coded string and move it to a properties file
        try {
            Files.write(GsonUtil.toJson(toppings), new File("target/classes/json/toppings.json"), Charsets.UTF_8);
        } catch (final IOException e) {
            LOG.error("error while saving toppings", e);
        }
    }

    @Override
    public void saveDoughs(final List<Dough> doughs) {
        // TODO: remove hard coded string and move it to a properties file
        try {
            Files.write(GsonUtil.toJson(doughs), new File("target/classes/json/doughs.json"), Charsets.UTF_8);
        } catch (final IOException e) {
            LOG.error("error while saving doughs", e);
        }
    }

    @Override
    public void saveSauces(final List<Sauce> sauces) {
        // TODO: remove hard coded string and move it to a properties file
        try {
            Files.write(GsonUtil.toJson(sauces), new File("target/classes/json/sauces.json"), Charsets.UTF_8);
        } catch (final IOException e) {
            LOG.error("error while saving sauces", e);
        }
    }

    private String readJsonFromFile(final String filename) {
        try {
            return Files.toString(new File(filename), Charsets.UTF_8);
        } catch (final IOException e) {
            LOG.error("error while reading file ", e);
        }
        return null;
    }
}
