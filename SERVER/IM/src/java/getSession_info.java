/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HP_PC
 */
public class getSession_info extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  PrintWriter out = response.getWriter();
        
        String sid = request.getParameter("sid");
        
        ArrayList<HttpSession> al;

        if (getServletContext().getAttribute("al") == null) {
            al = new ArrayList<>();
        } else {
            al = (ArrayList<HttpSession>) getServletContext().getAttribute("al");
        }
        
        for (int i = 0; i < al.size(); i++) {
            HttpSession session = al.get(i);
            
            if(session.getId().equals(sid))
            {
                System.out.println(sid+" "+session.getAttribute("phone")+" in get");
                out.println(sid+" "+session.getAttribute("phone"));
            }
            
        }

    }

  
}
