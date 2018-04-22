/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HP_PC
 */
public class send_msg extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    
    {
        PrintWriter out=resp.getWriter();
           try
        {
            String msg_to,msg,msg_from,type="text";
            
            Class.forName("com.mysql.jdbc.Driver");
           System.out.println("Registered");
           
           Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
           System.out.println("Connection Created");
           
           Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           System.out.println("Statement Created");
            
           ResultSet rs=stmt.executeQuery("select * from user_messages");
           rs.moveToInsertRow();
           msg_to=req.getParameter("chat_name");
           msg_from=req.getParameter("username");
           msg=req.getParameter("msg");
           rs.updateString("message", msg);
           rs.updateString("message_to", msg_to);
           rs.updateString("message_from", msg_from);
           rs.updateString("type", type);
            rs.insertRow();
            out.println(msg_from+"->"+msg+" to"+ msg_to+",");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
     }

}
