/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import listeners.SimpleMailDemo;
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
import listeners.SMSsender;

/**
 *
 * @author HP_PC
 */
public class forgot_code extends HttpServlet {

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
               String EMAIL=rs.getString("email");
               String VERIFY_CODE=rs.getString("verification_code");
                SimpleMailDemo obj = new SimpleMailDemo(EMAIL,"IM","Your IM Verification Code is "+VERIFY_CODE);
                   
           
             //  SimpleMailDemo obj = new SimpleMailDemo("amanpreet.03579@gmail.com","IM","Your IM Verification Code is ");
       
       //             SimpleMailDemo obj = new SimpleMailDemo(EMAIL,"IM","Your IM Verification Code is "+VERIFY_CODE);
                    SMSsender obj1=new SMSsender(PHONE,"Your IM Verification Code is "+VERIFY_CODE, "text");
                    Thread t=new Thread(obj1);
                    t.start();
                    out.println("OK");
           }
           else
           {
               out.println("NO");
               
           }
           
       }
           catch(Exception ex)
           {
               ex.printStackTrace();
           }
        
        
                    
        
        
    }

   
}
