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
public class fetch_msg extends HttpServlet {

    String PHONE,ID;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    
    {
        PrintWriter out =resp.getWriter();
        
        PHONE=req.getParameter("phone");
        ID=req.getParameter("ID");
        try
        {
            
             String result="";
        Class.forName("com.mysql.jdbc.Driver");
           System.out.println("Registered");
           
           Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
           System.out.println("Connection Created");
           
           Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           System.out.println("Statement Created");
            
           ResultSet rs=stmt.executeQuery("select * from user_messages where ( message_to='"+PHONE+"' or message_from='"+PHONE+"') and ID>'"+ID+"'");
           while(rs.next())
           {
               String from,to,msg,type,date,status;
               int id;
               id=rs.getInt("ID");
               //System.out.println(id);
               from=rs.getString("message_from");
              // System.out.println(from);
               msg=rs.getString("message");
              // System.out.println(msg);
               to=rs.getString("message_to");
                //  System.out.println(to);
               date=rs.getString("datetime");
               type=rs.getString("type");
               status=rs.getString("status");
               result=result+id+"~~"+msg+"~~"+from+"~~"+to+"~~"+date+"~~"+type+"~~"+status+"@#";
             
                
            }
             result=result.replaceAll("\n","");
            
             
           out.println(result);
           
        }
         catch(Exception ex)
         {
             ex.printStackTrace();
         }
       }

    
}
