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
public class receive_file extends HttpServlet {

    String from, to, ext;
    int flag = 0;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Recieve");
        PrintWriter out = resp.getWriter();
        from = req.getParameter("from");
        to = req.getParameter("to");
        ext = req.getParameter("ext");
        System.out.println("Extension+" + ext);
        long l = Long.parseLong(req.getHeader("Content-Length"));
        String path = req.getParameter("path");
        System.out.println(path);
        FileOutputStream fos = new FileOutputStream(getServletContext().getRealPath("/documents") + "\\" + path);
        InputStream dis = req.getInputStream();
        System.out.println("Length" + l);
        try {
            int r, count = 0;
            byte b[] = new byte[10000];
            while (true) {
                r = dis.read(b, 0, 10000);
                count += r;
                fos.write(b, 0, r);
                if (count == l) {
                    System.out.println("done");

                    flag = 1;
                    break;
                }

            }
            fos.close();

            if (flag == 1) {

                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Registered");

                Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
                System.out.println("Connection Created");

                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                System.out.println("Statement Created");

                ResultSet rs = stmt.executeQuery("select * from user_messages");
                rs.moveToInsertRow();
                rs.updateString("message", path);
                rs.updateString("message_from", from);
                rs.updateString("message_to", to);
                rs.updateString("type", "file");
                rs.insertRow();
                Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs1 = stmt1.executeQuery("select max(ID) as ID from user_messages");
                String result;
                while (rs1.next()) {
                    result = rs.getString("message");
                    out.println(result);
                    System.out.println("Result= " + result);
                }

            }
        } catch (IOException | ClassNotFoundException | SQLException ex) {
        }
    }
}
