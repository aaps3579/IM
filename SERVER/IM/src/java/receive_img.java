/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HP_PC
 */
public class receive_img extends HttpServlet {
String from,to;
int flag=0;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Receive");
        PrintWriter out=resp.getWriter();
        from=req.getParameter("from");
        to=req.getParameter("to");
         long l=Long.parseLong(req.getHeader("Content-Length"));    
        String s=new Date().toString(); 
       // s=s.replace(" ", ",");
        s=s.replace(":", "-");
        System.out.println(s);
        FileOutputStream fos = new FileOutputStream(getServletContext().getRealPath("/images") + "\\"+ s + ".jpg");
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
           rs.updateString("message", "image");
           rs.updateString("message_from", from);
           rs.updateString("message_to", to);
           rs.updateString("type","image");
          rs.insertRow();
         rs.close();
         rs=stmt.executeQuery("select max(ID) as ID from user_messages");
         int id=0;
         if(rs.next())
         {
             id=rs.getInt("ID");
             System.out.println(id);
             File f=new File(getServletContext().getRealPath("/images")+"\\"+s+".jpg");
             File f1=new File(getServletContext().getRealPath("/images")+"\\"+id+".jpg");
             f.renameTo(f1);
             Thread.sleep(1000);
             
            Image orgimage = ImageIO.read(new File(getServletContext().getRealPath("/images") + "\\" + id+".jpg"));
            BufferedImage bi = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();

            g2d.drawImage(orgimage, 0, 0, 200, 200, null);
            g2d.dispose();

            ImageIO.write(bi, "png", new File(getServletContext().getRealPath("/images") + "\\" + id+"_small.jpg"));
             out.println(id);
         }
          
          
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

            
        

        
        
    
    }

    
}
