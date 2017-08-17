/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.servlets;

import com.group3.util.DbManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Manu
 */
public class RegistrationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
              
        String user=request.getParameter("user");
        String pass=request.getParameter("pass");
        String email=request.getParameter("email");
        String gender=request.getParameter("gender");
        String city=request.getParameter("city");
        String dcode=request.getParameter("dcode");
        String dname=request.getParameter("dname");
        
        ServletContext context=getServletContext();
        
        DbManager dbMgr=(DbManager)context.getAttribute("DbMgr");
        
        Connection con=dbMgr.getConnection();
        
       String insertEmp="insert into emp_detail"
                +"(username,password,email,city,empid,gender)"
                +"values(?,?,?,?,?,?)";
        
        String insertDep="insert into dep_detail"
                +"(dep_code,dep_name,empid,username)"
                +"values(?,?,?,?)";

       try{
        PreparedStatement ps=con.prepareStatement(insertEmp);
        ps.setString(1, user);
        ps.setString(2, pass);
        ps.setString(3, email);
        ps.setString(4, city);
        ps.setString(5, user+email);
        ps.setString(6, gender);
        ps.executeQuery();
       }catch(SQLException e){System.out.println("Error");}
        
       try{
        PreparedStatement pss=con.prepareStatement(insertDep);
        pss.setString(1, dcode);
        pss.setString(2, dname);
        pss.setString(3, user+email);
        pss.setString(4, user);
        pss.executeQuery();
    
       }catch(SQLException e)
       {System.out.print("Error");}
      
        
       RequestDispatcher rd = context.getRequestDispatcher("/Login.html");
             PrintWriter out = response.getWriter();
              out.println("<font color=white align=center> <h3>Registration complete! "
                      + "please login</h3></font>");
              rd.include(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
