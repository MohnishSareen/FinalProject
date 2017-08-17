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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Manu
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            String butPar=request.getParameter("button");
            RequestDispatcher rs=null;
            if(butPar.equals("Login")){
                try {
                   String userName = request.getParameter("user");
                    String password = request.getParameter("pass");
            
                    HttpSession session=request.getSession();
                    session.setAttribute("log", userName);
            
                    ServletContext context =  getServletContext();
                    DbManager dbMgr = (DbManager)context.getAttribute("DbMgr");
                     Connection conn = dbMgr.getConnection(); 
                      PreparedStatement stmnt  =
                    conn.prepareStatement("select empid from "
                            + "emp_detail where username =? and password = ?");

                      stmnt.setString(1, userName);
                      stmnt.setString(2, password);
                       ResultSet result= stmnt.executeQuery();
                    String empId="";
                    while(result!=null & result.next()){
                    empId = result.getString("empid");
                    }
                            
                    response.setContentType("text/html;charset=UTF-8");
            
                    PrintWriter out = response.getWriter();
                    if(empId == null || empId==""){
                    RequestDispatcher rd = context.getRequestDispatcher("/Login.html");
                      out.println("<font color=white align=center><h3> User not found</h3></font>");
                    rd.include(request, response);
                    }
                    else{
                    RequestDispatcher rd = context.getRequestDispatcher("/Update.html");
                    out.println("<font color=white align=center><h3> User found! Please Update your Details</h3></font>");
                    rd.include(request, response);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                }}
            else if(butPar.equals("New User")){
                ServletContext ct=getServletContext();
                rs=ct.getRequestDispatcher("/Registration.html");
                rs.forward(request, response);
            }
    
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
         processRequest(request, response);
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
        processRequest(request, response);
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
