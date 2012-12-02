package com.notomatoesplease.persistence.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        Connection con = getConnection();
        ResultSet rs = null;
        PreparedStatement prest = null;
        List<Size> resList = new ArrayList<Size>();

        try {

            if (con != null) {
                String sql = "SELECT * FROM size;";
                prest = con.prepareStatement(sql);
                rs = prest.executeQuery();

                while (rs.next()) {
                    String name = rs.getString(1);
                    int price = rs.getInt(2);
                    Size res = new Size(name, price);
                    resList.add(res);
                }

                LOG.debug("getSizes() reservations found: " + resList.size());
            }

        }
        catch (SQLException se) {
            LOG.error("getSizes() failed ... " + se);
        }
        finally {
            closeConnections(con, rs, prest);
        }

        return resList;
    }

    @Override
    public List<Dough> getDoughs() {
        Connection con = getConnection();
        ResultSet rs = null;
        PreparedStatement prest = null;
        List<Dough> resList = new ArrayList<Dough>();

        try {

            if (con != null) {
                String sql = "SELECT * FROM sauce;";
                prest = con.prepareStatement(sql);
                rs = prest.executeQuery();

                while (rs.next()) {
                    Dough dough = new Dough();
                    dough.setName(rs.getString(1));
                    dough.setPrice(rs.getInt(2));
                    dough.setVisible(rs.getBoolean(3));
                    resList.add(dough);
                }

                LOG.debug("getDoughs() reservations found: " + resList.size());
            }

        }
        catch (SQLException se) {
            LOG.error("getDoughs() failed ... " + se);
        }
        finally {
            closeConnections(con, rs, prest);
        }


        return resList;
    }

    @Override
    public List<Sauce> getSauces() {
        Connection con = getConnection();
        ResultSet rs = null;
        PreparedStatement prest = null;
        List<Sauce> resList = new ArrayList<Sauce>();

        try {

            if (con != null) {
                String sql = "SELECT * FROM dough;";
                prest = con.prepareStatement(sql);
                rs = prest.executeQuery();

                while (rs.next()) {
                    Sauce sauce = new Sauce();
                    sauce.setName(rs.getString(1));
                    sauce.setPrice(rs.getInt(2));
                    sauce.setVisible(rs.getBoolean(3));
                    resList.add(sauce);
                }

                LOG.debug("getSauces() reservations found: " + resList.size());
            }

        }
        catch (SQLException se) {
            LOG.error("getSauces() failed ... " + se);
        }
        finally {
            closeConnections(con, rs, prest);
        }

        return resList;
    }

    @Override
    public List<Topping> getToppings() {
        Connection con = getConnection();
        ResultSet rs = null;
        PreparedStatement prest = null;
        List<Topping> resList = new ArrayList<Topping>();

        try {

            if (con != null) {
                String sql = "SELECT * FROM topping;";
                prest = con.prepareStatement(sql);
                rs = prest.executeQuery();

                while (rs.next()) {
                    Topping topping = new Topping();
                    topping.setName(rs.getString(1));
                    topping.setPrice(rs.getInt(2));
                    topping.setVisible(rs.getBoolean(3));
                    resList.add(topping);
                }

                LOG.debug("getToppings() reservations found: " + resList.size());
            }

        }
        catch (SQLException se) {
            LOG.error("getToppings() failed ... " + se);
        }
        finally {
            closeConnections(con, rs, prest);
        }

        return resList;
    }

    @Override
    public void saveToppings(final List<Topping> toppings) {
        Connection con = getConnection();
        PreparedStatement prest = null;

        try {
            if (con != null) {
                String sql = "TRUNCATE TABLE topping;";
                prest = con.prepareStatement(sql);
                prest.executeUpdate();

                for (Topping topping : toppings) {
                    sql = "INSERT INTO topping (\"name\", \"price\", \"visible\") VALUES ( ?, ?, ?);";
                    prest = con.prepareStatement(sql);

                    prest.setString(1, topping.getName());
                    prest.setInt(2, topping.getPrice());
                    prest.setBoolean(3, topping.isVisible());

                    prest.executeUpdate();
                }
            }
        }
        catch (SQLException se) {
            LOG.error("saveToppings() failed ... " + se);
        }
        finally {
            closeConnections(con, null, prest);
        }
    }

    @Override
    public void saveDoughs(final List<Dough> doughs) {
        Connection con = getConnection();
        PreparedStatement prest = null;

        try {
            if (con != null) {
                String sql = "TRUNCATE TABLE dough;";
                prest = con.prepareStatement(sql);
                prest.executeUpdate();

                for (Dough dough : doughs) {
                    sql = "INSERT INTO dough (\"name\", \"price\", \"visible\") VALUES ( ?, ?, ?);";
                    prest = con.prepareStatement(sql);

                    prest.setString(1, dough.getName());
                    prest.setInt(2, dough.getPrice());
                    prest.setBoolean(3, dough.isVisible());

                    prest.executeUpdate();
                }
            }
        }
        catch (SQLException se) {
            LOG.error("saveDoughs() failed ... " + se);
        }
        finally {
            closeConnections(con, null, prest);
        }
    }

    @Override
    public void saveSauces(final List<Sauce> sauces) {
        Connection con = getConnection();
        PreparedStatement prest = null;

        try {
            if (con != null) {
                String sql = "TRUNCATE TABLE sauce;";
                prest = con.prepareStatement(sql);
                prest.executeUpdate();

                for (Sauce sauce : sauces) {
                    sql = "INSERT INTO sauce (\"name\", \"price\", \"visible\") VALUES ( ?, ?, ?);";
                    prest = con.prepareStatement(sql);

                    prest.setString(1, sauce.getName());
                    prest.setInt(2, sauce.getPrice());
                    prest.setBoolean(3, sauce.isVisible());

                    prest.executeUpdate();
                }
            }
        }
        catch (SQLException se) {
            LOG.error("saveSauces() failed ... " + se);
        }
        finally {
            closeConnections(con, null, prest);
        }
    }

    private Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");

            return DriverManager.getConnection("jdbc:h2:file:./data/pizza", "resu", "drowssap");
        }
        catch (SQLException sqle) {
            LOG.error("getConnection() driver sql error" + sqle);
            return null;
        }
        catch (ClassNotFoundException cnfe) {
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

            if(con != null) {
                con.close();
            }
        }
        catch(SQLException sqle) {
            LOG.error("closeConnections() sql close exception " + sqle);
        }
    }
}
