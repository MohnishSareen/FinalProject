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
@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class UpdateServlet extends HttpServlet {

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
        
        HttpSession session=request.getSession(false);
        String unameSess=(String)session.getAttribute("log"); 
        
        ServletContext context=getServletContext();
        
        DbManager dbMgr=(DbManager)context.getAttribute("DbMgr");
        
        Connection con=dbMgr.getConnection();
        
        RequestDispatcher rd=null;
        
        String butPar=request.getParameter("button");
          
        if(butPar.equals("Update"))
        {
        String email=request.getParameter("email");
        String city=request.getParameter("city");
        
        String updateEmp = "UPDATE emp_detail SET email =?,city=?,empid=? WHERE username =?";
        String updateDep = "update dep_detail set empid=? where username=?";
        
       try{
        PreparedStatement ps=con.prepareStatement(updateEmp);
        ps.setString(1,email);
        ps.setString(2,city);
        ps.setString(3,unameSess+email);
        ps.setString(4,unameSess);
        ps.executeUpdate();
        
        PreparedStatement pss=con.prepareStatement(updateDep);
        pss.setString(1,unameSess+email);
        pss.setString(2,unameSess);
        pss.executeUpdate();
        
        rd = context.getRequestDispatcher("/Login.html");
           PrintWriter out = response.getWriter();
              out.println("<font color=white align=center> <h3>Update Completed! "
                      + "please login</h3></font>");
              rd.include(request, response);
       }catch(SQLException e)
       {System.out.print("Error");}
       }
        else if(butPar.equals("See Details"))
        {
           try{
           
           String selectQuery="select email,emp_detail.empid,city,gender,dep_code,dep_name from emp_detail inner join dep_detail on emp_detail.username=? and dep_detail.username=?";
           PreparedStatement ps=con.prepareStatement(selectQuery);
           ps.setString(1,unameSess);
           ps.setString(2,unameSess);
           ResultSet rs=ps.executeQuery();
           
           String em="",ct="",dc="",dn="",gn="",id="";
           
           while(rs!=null & rs.next()){
           em=rs.getString("email");
           ct=rs.getString("city");
           id=rs.getString("empid");
           gn=rs.getString("gender");
           dc=rs.getString("dep_code");
           dn=rs.getString("dep_name");
           }
          
           response.setContentType("text/html;charset=UTF-8");
           
           rd=context.getRequestDispatcher("/show.html");
           PrintWriter out = response.getWriter(); 
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Details</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h4>----User Detail----</h4><br>");
            out.println("<form action='delete' method=POST>");
            out.print("<table border='1'><tr><th>Usename</th><th>EmpolyeeID</th><th>Email</th><th>City</th><th>Gender</th><th>Department Code</th><th>Department Name</th><th>Delete</th></tr>");
            out.println("<tr><td>"+unameSess+"</td><td>"+id+"</td><td>"+em+"</td><td>"+ct+"</td><td>"+gn+"</td><td>"+dc+"</td><td>"+dn+"</td><td><input type='submit' value='Delete' name='button'</td></tr></table>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
           
           rd.include(request, response);
          }catch(SQLException e){
              System.out.println("------Error aa Geya-----");
          }
        }
        
        else if(butPar.equals("Delete"))
        {
           try{
           
           String deleteQuery="delete from emp_detail where username=?";
           PreparedStatement ps=con.prepareStatement(deleteQuery);
           ps.setString(1,unameSess);
           ps.executeUpdate();
           
           response.setContentType("text/html;charset=UTF-8");
           
           rd=context.getRequestDispatcher("/Login.html");
           PrintWriter out = response.getWriter(); 
           out.println("<font color=white align=center> <h3>Delete Successfully! "
                      + "please login</h3></font>");
           rd.include(request, response);
          }catch(SQLException e){
              System.out.println("------Error aa Geya-----");
          }
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
