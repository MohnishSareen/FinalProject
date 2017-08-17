/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.listeners;

import com.group3.util.DbManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author sheik.mamun
 */
public class NewServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    System.out.print("context initialized");
    
        ServletContext contxt = sce.getServletContext();
        String usr = (String) contxt.getInitParameter("user");
        String pwd = (String) contxt.getInitParameter("password");
        String  url = (String) contxt.getInitParameter("url");
        String className = (String) contxt.getInitParameter("className");
        DbManager dbMgr=null;
        try{
           dbMgr= new DbManager(url, usr, pwd, className);
           contxt.setAttribute("DbMgr", dbMgr);
        }catch(Exception e){
        System.out.println("DB Failure");
         }
        
        
    
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
 System.out.print("context initialized");        
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
