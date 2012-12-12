package com.notomatoesplease.app.Persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.persistence.impl.H2PersistenceImpl;

/**
 * H2DB Persistence Tests
 * 
 */
public class H2DBTest extends AbstractDBTest {

    private static final String TEST_SIZE = "Test Size";
    private static final int TEST_SIZE_PRICE = 123;

    private static final String TEST_DOUGH = "Test Doügh";
    private static final int TEST_DOUGH_PRICE = 456;
    private static final boolean TEST_DOUGH_VISIBLE = true;

    private static final String TEST_SAUCE = "Test Säuce";
    private static final int TEST_SAUCE_PRICE = 789;
    private static final boolean TEST_SAUCE_VISIBLE = false;

    private static final String TEST_TOPPING = "Test Töpping";
    private static final int TEST_TOPPING_PRICE = 1011;
    private static final boolean TEST_TOPPING_VISIBLE = false;

    private final H2PersistenceImpl h2Impl = new H2PersistenceImpl(DATABASE_URL);
    private Connection dbConnection;

    @Test
    public void testGetSizes() {
        final List<Size> sizes = h2Impl.getSizes();
        Assert.assertNotNull(sizes);
        Assert.assertEquals(1, sizes.size());
        Assert.assertNotNull(sizes.get(0));
        Assert.assertEquals(TEST_SIZE, sizes.get(0).getName());
        Assert.assertEquals(TEST_SIZE_PRICE, sizes.get(0).getPrice());
    }

    @Test
    public void testGetDoughs() {
        final List<Dough> doughs = h2Impl.getDoughs();
        Assert.assertNotNull(doughs);
        Assert.assertEquals(1, doughs.size());
        Assert.assertNotNull(doughs.get(0));
        Assert.assertEquals(TEST_DOUGH, doughs.get(0).getName());
        Assert.assertEquals(TEST_DOUGH_PRICE, doughs.get(0).getPrice());
        Assert.assertEquals(TEST_DOUGH_VISIBLE, doughs.get(0).isVisible());
    }

    @Test
    public void testGetSauces() {
        final List<Sauce> sauces = h2Impl.getSauces();
        Assert.assertNotNull(sauces);
        Assert.assertEquals(1, sauces.size());
        Assert.assertNotNull(sauces.get(0));
        Assert.assertEquals(TEST_SAUCE, sauces.get(0).getName());
        Assert.assertEquals(TEST_SAUCE_PRICE, sauces.get(0).getPrice());
        Assert.assertEquals(TEST_SAUCE_VISIBLE, sauces.get(0).isVisible());
    }

    @Test
    public void testGetToppings() {
        final List<Topping> toppings = h2Impl.getToppings();
        Assert.assertNotNull(toppings);
        Assert.assertEquals(1, toppings.size());
        Assert.assertNotNull(toppings.get(0));
        Assert.assertEquals(TEST_TOPPING, toppings.get(0).getName());
        Assert.assertEquals(TEST_TOPPING_PRICE, toppings.get(0).getPrice());
        Assert.assertEquals(TEST_TOPPING_VISIBLE, toppings.get(0).isVisible());
    }

    @Before
    public void setUp() throws SQLException {
        dbConnection = getConnection();
        final List<String> sqlStatements = Lists.newArrayList();

        sqlStatements.add("CREATE TABLE size (\"name\" VARCHAR(50) NOT NULL PRIMARY KEY, \"price\" INT NOT NULL);");
        sqlStatements.add("CREATE TABLE dough ( \"name\" VARCHAR(50) NOT NULL PRIMARY KEY, \"price\" INT NOT NULL, \"visible\" BOOLEAN NOT NULL);");
        sqlStatements.add("CREATE TABLE sauce ( \"name\" VARCHAR(50) NOT NULL PRIMARY KEY, \"price\" INT NOT NULL, \"visible\" BOOLEAN NOT NULL);");
        sqlStatements.add("CREATE TABLE topping ( \"name\"  VARCHAR(50) NOT NULL PRIMARY KEY, \"price\" INT NOT NULL, \"visible\" BOOLEAN NOT NULL);");
        sqlStatements.add(String.format("INSERT INTO size (\"name\", \"price\") VALUES ('%s', %s);", TEST_SIZE,
                        TEST_SIZE_PRICE));
        sqlStatements.add(String.format("INSERT INTO dough (\"name\", \"price\", \"visible\") VALUES ('%s', %s, %s);",
                        TEST_DOUGH, TEST_DOUGH_PRICE, TEST_DOUGH_VISIBLE));
        sqlStatements.add(String.format("INSERT INTO sauce (\"name\", \"price\", \"visible\") VALUES ('%s', %s, %s);",
                        TEST_SAUCE, TEST_SAUCE_PRICE, TEST_SAUCE_VISIBLE));
        sqlStatements.add(String.format(
                        "INSERT INTO topping (\"name\", \"price\", \"visible\") VALUES ('%s', %s, %s);", TEST_TOPPING,
                        TEST_TOPPING_PRICE, TEST_TOPPING_VISIBLE));

        for (final String sql : sqlStatements) {
            execute(dbConnection, sql);
        }
    }

    @After
    public void tearDown() throws SQLException {
        final List<String> sqlStatements = Lists.newArrayList();

        sqlStatements.add("DROP TABLE IF EXISTS size;");
        sqlStatements.add("DROP TABLE IF EXISTS dough;");
        sqlStatements.add("DROP TABLE IF EXISTS sauce;");
        sqlStatements.add("DROP TABLE IF EXISTS topping;");

        for (final String sql : sqlStatements) {
            execute(dbConnection, sql);
        }
        closeConnection(dbConnection);
    }
}
