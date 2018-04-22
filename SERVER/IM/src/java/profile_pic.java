
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class profile_pic extends HttpServlet 
{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int count = 0;
        PrintWriter out = resp.getWriter();
        String PHONE = req.getParameter("phone");
         long l=Long.parseLong(req.getHeader("Content-Length"));
        FileOutputStream fos = new FileOutputStream(getServletContext().getRealPath("/ProfilePics") + File.separator + PHONE + ".jpg");
        InputStream dis = req.getInputStream();

        try 
        {
            int r;
            byte b[] = new byte[10000];
            while (true) 
            {
                r = dis.read(b, 0, 10000);
                count += r;
                fos.write(b, 0, r);
                if (count == l) 
                {
                    System.out.println("done");
                    out.println("Success");
                    break;
                }

            }
            fos.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
