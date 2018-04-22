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
public class validate_phone extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String phone  = request.getParameter("phone");
        String sid = request.getParameter("id");
        String mycontacts=request.getParameter("my_contacts");
        String username=request.getParameter("name");
        System.out.println(username);
       
        System.out.println("request recieved : "+phone);
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
                System.out.println(sid+" matches "+phone);
                al.get(i).setAttribute("phone", phone);
                al.get(i).setAttribute("NAME", username);
                System.out.println("my contacts "+mycontacts);
                al.get(i).setAttribute("mycontacts", mycontacts);
                break;
            }
            
        }
        getServletContext().setAttribute("al", al);
        
    
    }

    
}
