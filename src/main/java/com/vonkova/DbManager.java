package com.vonkova;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 * User: vonkad
 * Date: 12/1/11
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class DbManager {
    private Connection connection;
    private Statement statement;
    private ResultSet rs;

    public Connection getConnection() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/students");
            return ds.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("failed at getting connection", e);
        }
    }

    public ResultSet query(String sql) {
        if (connection == null){
            connection = getConnection();
        }
        if (rs != null || statement != null) {
            throw new RuntimeException("previous query not finished");
        }
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            return rs;
        } catch (Exception e) {
            throw new RuntimeException("failed at query: " + sql, e);
        }
    }

    public int update(String sql) {
        if (connection == null){
            connection = getConnection();
        }
        if (rs != null || statement != null) {
            throw new RuntimeException("previous query not finished");
        }
        try {
            statement = connection.createStatement();
            int nr = statement.executeUpdate(sql);
            return nr;
        } catch (Exception e) {
            throw new RuntimeException("failed at update: " + sql, e);
        }
    }


    public void finalizeQuery() {
        try {
            if (rs != null){
                rs.close();
                rs = null;
            }
            if (statement!=null){
                statement.close();
                statement = null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                if (rs!=null && statement!=null){
                    finalizeQuery();
                }
                this.connection.close();
                this.connection = null;
            } catch (Exception e) {
                throw new RuntimeException("failed to close connection", e);
            }
        }
    }
}
