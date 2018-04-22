
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import javax.servlet.http.Part;

@MultipartConfig
public class VideoUploader extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();

            
            Collection<Part> parts = request.getParts();
            String ans = "";

            String absolutepath = request.getServletContext().getRealPath("/videos");
            String name="";
            for (Part part : parts) {

                name = new Date().toString()+".mp4";
                name = name.replaceAll(":", "-");

                vmm2.FileUploader.savefileonserver(part, absolutepath, name);


                if (name.equals("not-a-file")) {
                    ans += "<br>" + "---";
                } else {
                    ans += "<br>" + name;
                }
            }
            
            
            String fphone = request.getParameter("fphone2");
            System.out.println(fphone);

            HttpSession session = request.getSession();
            String phone = session.getAttribute("phone").toString();
            
            
            
            Class.forName("com.mysql.jdbc.Driver");
           System.out.println("Registered");
           
           Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
           System.out.println("Connection Created");
           
           Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           System.out.println("Statement Created");
            
           ResultSet rs=stmt.executeQuery("select * from user_messages");
           rs.moveToInsertRow();
           rs.updateString("message", "video");
           rs.updateString("message_from", phone);
           rs.updateString("message_to", fphone);
           rs.updateString("type","video");
          rs.insertRow();
         rs.close();
         rs=stmt.executeQuery("select max(ID) as ID from user_messages");
         int id=0;
         if(rs.next())
         {
             id=rs.getInt("ID");
             System.out.println(id);
             File f=new File(getServletContext().getRealPath("/videos")+"\\"+name);
             System.out.println("Path="+f.getAbsoluteFile());
             File f1=new File(getServletContext().getRealPath("/videos")+"\\"+id+".mp4");
             f.renameTo(f1);
             Thread.sleep(1000);
             
         }
            
            
            
            
            System.out.println("File uploaded successfully");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}