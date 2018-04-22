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
public class delete_acc extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out=resp.getWriter();
        String PHONE=req.getParameter("phone");
         try
       {
           Class.forName("com.mysql.jdbc.Driver");
           System.out.println("Registered");
           
           Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
           System.out.println("Connection Created");
           
           Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           System.out.println("Statement Created");
           
           ResultSet rs=stmt.executeQuery("select * from users where phone='"+PHONE+"'");
           System.out.println("Query Executed");
           if(rs.next())
           {
           rs.deleteRow();
           rs.close();
           }
          // ResultSet rs1=stmt.executeQuery("select * from user_messages where message_from='"+PHONE);
           out.println("DELETE");
           
       }
         catch(Exception ex)
         {
             ex.printStackTrace();
         }



    }

}
