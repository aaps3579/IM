
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
public class change_pic extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
           
            HttpSession session = request.getSession();
            String phone = session.getAttribute("phone").toString();
           
            
            Collection<Part> parts = request.getParts();
            String ans = "";
            
            File f = new File(getServletContext().getRealPath("/ProfilePics")+File.separator+phone+".jpg");

            if(f.exists())
            {
                f.delete();
                System.out.println("file deleted");
            }
            String absolutepath = request.getServletContext().getRealPath("/ProfilePics");
            String name="";
            for (Part part : parts) {

                name = phone+".jpg";
                
                vmm2.FileUploader.savefileonserver(part, absolutepath, name);


                if (name.equals("not-a-file")) {
                    ans += "<br>" + "---";
                } else {
                    ans += "<br>" + name;
                }
            }
            System.out.println("new file created");
            out.println("./ProfilePics/"+phone+".jpg");
             System.out.println("File uploaded successfully");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
