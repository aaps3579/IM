/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HP_PC
 */
public class verify_status extends HttpServlet {
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        PrintWriter pw=response.getWriter();
        String PHONE=request.getParameter("phone");
        String VERIFY_CODE=request.getParameter("verification_code");
        try
        {
           Class.forName("com.mysql.jdbc.Driver");
           System.out.println("Registered");
           
           Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
           System.out.println("Connection Created");
           
           Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           System.out.println("Statement Created");
           
           ResultSet rs=stmt.executeQuery("select * from users where phone='"+PHONE+"' and verification_code='"+VERIFY_CODE+"'");
           System.out.println("Query Executed");
           if(rs.next())
           {
               String s ="Approved";
               pw.println("Code Match");
               rs.updateString("verification_status",s); 
               rs.updateRow();
           }
           else
           {
               pw.println("Invalid Code");
               
           }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
   
    }
}
