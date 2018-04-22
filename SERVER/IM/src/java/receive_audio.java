/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HP_PC
 */
public class receive_audio extends HttpServlet {
String from,to;
int flag=0;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    

    
        System.out.println("Recieve");
        PrintWriter out=resp.getWriter();
        from=req.getParameter("from");
        to=req.getParameter("to");
        long l=Long.parseLong(req.getHeader("Content-Length"));    
        String s=new Date().toString();
        s=s.replaceAll(":", "");
         FileOutputStream fos = new FileOutputStream(getServletContext().getRealPath("/audio") + "\\"+ s + ".mp3");
        InputStream dis = req.getInputStream();
        System.out.println("Length"+l);
          try 
        {
            int r,count=0;
            byte b[] = new byte[10000];
            while (true) 
            {
                r = dis.read(b, 0, 10000);
                count += r;
                fos.write(b, 0, r);
                if (count == l) 
                {
                    System.out.println("done");
                    
                    flag=1;
                    break;
                }

            }
            fos.close();

            if(flag==1)
            {
        
            
            Class.forName("com.mysql.jdbc.Driver");
           System.out.println("Registered");
           
           Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
           System.out.println("Connection Created");
           
           Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           System.out.println("Statement Created");
            
           ResultSet rs=stmt.executeQuery("select * from user_messages");
           rs.moveToInsertRow();
           rs.updateString("message", "audio");
           rs.updateString("message_from", from);
           rs.updateString("message_to", to);
           rs.updateString("type","audio");
          rs.insertRow();
         rs.close();
         rs=stmt.executeQuery("select max(ID) as ID from user_messages");
         int id;
         if(rs.next())
         {
             id=rs.getInt("ID");
             System.out.println(id);
             File f=new File(getServletContext().getRealPath("/audio")+"\\"+s+".mp3");
             File f1=new File(getServletContext().getRealPath("/audio")+"\\"+id+".mp3");
             
             f.renameTo(f1);
             out.println(id);
             System.out.println(f1.getPath()+" ---path");
             System.out.println(id);
         }
          
          
            }
        } catch (IOException | ClassNotFoundException | SQLException ex) {
        }
    }
}
