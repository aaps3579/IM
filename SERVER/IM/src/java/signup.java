/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import listeners.SimpleMailDemo;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import listeners.SMSsender;

/**
 *
 * @author HP_PC
 */
public class signup extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
         
       
        PrintWriter pw=response.getWriter();
        
        String USERNAME,PHONE,NAME,EMAIL,GENDER,STATUS,VERIFY_CODE;
        int x=1000+new Random().nextInt(8999);
        
        VERIFY_CODE=String.valueOf(x);
        PHONE=request.getParameter("phone");
        USERNAME=request.getParameter("username");
        EMAIL=request.getParameter("email");
        GENDER=request.getParameter("gender");
        NAME=request.getParameter("name");
        STATUS="Loving IM! ";
        System.out.println(USERNAME);
        System.out.println(PHONE);
        System.out.println(NAME);
        System.out.println(EMAIL);
        System.out.println(GENDER);
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
               
               pw.println("User Already Exists");
           }
           else
           {
              rs.moveToInsertRow();
              rs.updateString("phone",PHONE);
              rs.updateString("username",USERNAME);
              rs.updateString("email",EMAIL);
              rs.updateString("gender",GENDER);
              rs.updateString("verification_code",VERIFY_CODE);
      
             
              rs.updateString("status",STATUS);
              rs.insertRow();
              
                
                    SimpleMailDemo obj = new SimpleMailDemo(EMAIL,"IM","Your IM Verification Code is "+VERIFY_CODE);
                    SMSsender obj1=new SMSsender(PHONE,"Your IM Verification Code is "+VERIFY_CODE, "text");
                    Thread t=new Thread(obj1);
                    t.start();
    
               System.out.println("success");
               pw.println("Sucessfull");
           }
       }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        
    }
   
   
}
