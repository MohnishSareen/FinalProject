/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sheik.mamun
 */
public class DbManager {
    private Connection conn;
    
    public DbManager(String url, String user, String passwrd, String jdbcClass) throws ClassNotFoundException, SQLException{
        Class.forName(jdbcClass);
        this.conn = DriverManager.getConnection(url, user, passwrd);
    }
    
    public Connection getConnection(){
        return this.conn;
    }
}
