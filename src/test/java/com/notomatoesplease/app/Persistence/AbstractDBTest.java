package com.notomatoesplease.app.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDBTest {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractDBTest.class);

    static final String DATABASE_URL = "jdbc:h2:file:target/test-database/pizza";

    protected Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");

            return DriverManager.getConnection(DATABASE_URL, "resu", "drowssap");
        } catch (final Exception ex) {
            LOG.error("error occured", ex);
            return null;
        }
    }

    protected void closeConnection(final Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (final Exception ex) {
            LOG.error("error occured", ex);
        }
    }

    protected void execute(final Connection con, final String sql) throws SQLException {
        PreparedStatement prest = null;
        try {
            if (con != null) {
                prest = con.prepareStatement(sql);
                con.prepareCall(sql);
                prest.execute();
            }
        } catch (final Exception ex) {
            LOG.error("error occured", ex);
        } finally {
            if (prest != null) {
                prest.close();
            }
        }
    }

}
