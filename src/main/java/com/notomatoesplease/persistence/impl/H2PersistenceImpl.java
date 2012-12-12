package com.notomatoesplease.persistence.impl;

import static com.notomatoesplease.util.PizzaUtil.DOUGH_UTIL;
import static com.notomatoesplease.util.PizzaUtil.SAUCE_UTIL;
import static com.notomatoesplease.util.PizzaUtil.TOPPING_UTIL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.persistence.Persistence;

public class H2PersistenceImpl implements Persistence {

    private static final Logger LOG = LoggerFactory.getLogger(H2PersistenceImpl.class);
    private final String url;

    public H2PersistenceImpl() {
        this("jdbc:h2:file:classes/database/pizza");
    }

    @VisibleForTesting
    public H2PersistenceImpl(final String url) {
        Preconditions.checkNotNull(url, "url must not be null");
        this.url = url;
        LOG.debug("using H2 database");
    }

    @Override
    public List<Size> getSizes() {
        final Connection con = getConnection();
        ResultSet rs = null;
        PreparedStatement prest = null;
        final List<Size> resList = new ArrayList<Size>();

        try {

            if (con != null) {
                final String sql = "SELECT * FROM size;";
                prest = con.prepareStatement(sql);
                rs = prest.executeQuery();

                while (rs.next()) {
                    final String name = rs.getString(1);
                    final int price = rs.getInt(2);
                    final Size res = new Size(name, price);
                    resList.add(res);
                }

                LOG.debug("getSizes() reservations found: " + resList.size());
            }

        } catch (final SQLException se) {
            LOG.error("getSizes() failed ... " + se);
        } finally {
            closeConnections(con, rs, prest);
        }

        return resList;
    }

    @Override
    public List<Dough> getDoughs() {
        final Connection con = getConnection();
        ResultSet rs = null;
        PreparedStatement prest = null;
        final List<Dough> resList = new ArrayList<Dough>();

        try {

            if (con != null) {
                final String sql = "SELECT * FROM dough ORDER BY \"name\";";
                prest = con.prepareStatement(sql);
                rs = prest.executeQuery();

                while (rs.next()) {
                    final Dough dough = new Dough();
                    dough.setName(rs.getString(1));
                    dough.setPrice(rs.getInt(2));
                    dough.setVisible(rs.getBoolean(3));
                    resList.add(dough);
                }

                LOG.debug("getDoughs() reservations found: " + resList.size());
            }

        } catch (final SQLException se) {
            LOG.error("getDoughs() failed ... " + se);
        } finally {
            closeConnections(con, rs, prest);
        }

        return resList;
    }

    @Override
    public List<Sauce> getSauces() {
        final Connection con = getConnection();
        ResultSet rs = null;
        PreparedStatement prest = null;
        final List<Sauce> resList = new ArrayList<Sauce>();

        try {

            if (con != null) {
                final String sql = "SELECT * FROM sauce ORDER BY \"name\";";
                prest = con.prepareStatement(sql);
                rs = prest.executeQuery();

                while (rs.next()) {
                    final Sauce sauce = new Sauce();
                    sauce.setName(rs.getString(1));
                    sauce.setPrice(rs.getInt(2));
                    sauce.setVisible(rs.getBoolean(3));
                    resList.add(sauce);
                }

                LOG.debug("getSauces() reservations found: " + resList.size());
            }

        } catch (final SQLException se) {
            LOG.error("getSauces() failed ... " + se);
        } finally {
            closeConnections(con, rs, prest);
        }

        return resList;
    }

    @Override
    public List<Topping> getToppings() {
        final Connection con = getConnection();
        ResultSet rs = null;
        PreparedStatement prest = null;
        final List<Topping> resList = new ArrayList<Topping>();

        try {

            if (con != null) {
                final String sql = "SELECT * FROM topping ORDER BY \"name\";";
                prest = con.prepareStatement(sql);
                rs = prest.executeQuery();

                while (rs.next()) {
                    final Topping topping = new Topping();
                    topping.setName(rs.getString(1));
                    topping.setPrice(rs.getInt(2));
                    topping.setVisible(rs.getBoolean(3));
                    resList.add(topping);
                }

                LOG.debug("getToppings() reservations found: " + resList.size());
            }

        } catch (final SQLException se) {
            LOG.error("getToppings() failed ... " + se);
        } finally {
            closeConnections(con, rs, prest);
        }

        return resList;
    }

    @Override
    public void saveToppings(final List<Topping> toppings) {
        final Connection con = getConnection();
        PreparedStatement prest = null;

        try {
            if (con != null) {
                String sql = "TRUNCATE TABLE topping;";
                prest = con.prepareStatement(sql);
                prest.executeUpdate();

                final List<Topping> sortedToppings = Lists.newArrayList(TOPPING_UTIL.sortByName(toppings));
                for (final Topping topping : sortedToppings) {
                    sql = "INSERT INTO topping (\"name\", \"price\", \"visible\") VALUES ( ?, ?, ?);";
                    prest = con.prepareStatement(sql);

                    prest.setString(1, topping.getName());
                    prest.setInt(2, topping.getPrice());
                    prest.setBoolean(3, topping.isVisible());

                    prest.executeUpdate();
                }
            }
        } catch (final SQLException se) {
            LOG.error("saveToppings() failed ... " + se);
        } finally {
            closeConnections(con, null, prest);
        }
    }

    @Override
    public void saveDoughs(final List<Dough> doughs) {
        final Connection con = getConnection();
        PreparedStatement prest = null;

        try {
            if (con != null) {
                String sql = "TRUNCATE TABLE dough;";
                prest = con.prepareStatement(sql);
                prest.executeUpdate();

                final List<Dough> sortedDoughs = Lists.newArrayList(DOUGH_UTIL.sortByName(doughs));
                for (final Dough dough : sortedDoughs) {
                    sql = "INSERT INTO dough (\"name\", \"price\", \"visible\") VALUES ( ?, ?, ?);";
                    prest = con.prepareStatement(sql);

                    prest.setString(1, dough.getName());
                    prest.setInt(2, dough.getPrice());
                    prest.setBoolean(3, dough.isVisible());

                    prest.executeUpdate();
                }
            }
        } catch (final SQLException se) {
            LOG.error("saveDoughs() failed ... " + se);
        } finally {
            closeConnections(con, null, prest);
        }
    }

    @Override
    public void saveSauces(final List<Sauce> sauces) {
        final Connection con = getConnection();
        PreparedStatement prest = null;

        try {
            if (con != null) {
                String sql = "TRUNCATE TABLE sauce;";
                prest = con.prepareStatement(sql);
                prest.executeUpdate();

                final List<Sauce> sortedSauces = Lists.newArrayList(SAUCE_UTIL.sortByName(sauces));
                for (final Sauce sauce : sortedSauces) {
                    sql = "INSERT INTO sauce (\"name\", \"price\", \"visible\") VALUES ( ?, ?, ?);";
                    prest = con.prepareStatement(sql);

                    prest.setString(1, sauce.getName());
                    prest.setInt(2, sauce.getPrice());
                    prest.setBoolean(3, sauce.isVisible());

                    prest.executeUpdate();
                }
            }
        } catch (final SQLException se) {
            LOG.error("saveSauces() failed ... " + se);
        } finally {
            closeConnections(con, null, prest);
        }
    }

    private Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");

            return DriverManager.getConnection(url, "resu", "drowssap");
        } catch (final SQLException sqle) {
            LOG.error("getConnection() driver sql error" + sqle);
            return null;
        } catch (final ClassNotFoundException cnfe) {
            LOG.error("getConnection() driver not found error" + cnfe);
            return null;
        }
    }

    private void closeConnections(final Connection con, final ResultSet rs, final PreparedStatement prest) {
        try {
            if (rs != null) {
                rs.close();
            }

            if (prest != null) {
                prest.close();
            }

            if (con != null) {
                con.close();
            }
        } catch (final SQLException sqle) {
            LOG.error("closeConnections() sql close exception " + sqle);
        }
    }
}
