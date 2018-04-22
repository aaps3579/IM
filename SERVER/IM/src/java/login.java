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
public class login extends HttpServlet 
{
   String PHONE,VERIFY_CODE,STATUS;

   
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
       PrintWriter out=resp.getWriter();
       PHONE=req.getParameter("phone");
       VERIFY_CODE=req.getParameter("verification_code");
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
           System.out.println("Registered");
           
           Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
           System.out.println("Connection Created");
           
           Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           System.out.println("Statement Created");
           
           ResultSet rs=stmt.executeQuery("select * from users where phone='"+PHONE+"' and verification_code='"+VERIFY_CODE+"' and verification_status='Approved'");
           System.out.println("Query Executed");
           if(rs.next())
           {
               out.println("Login Success");
               out.println(rs.getString("username"));
               System.out.println("Right");
               
           }
           else
           {   out.println("Wrong Credentials");
               System.out.println("Wrong");
           }
           
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    

    }   
}
