/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import java.util.ArrayList;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class Session_listener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent hse)
    {
        System.out.println("SessionId"+hse.getSession().getId());
        HttpSession session=hse.getSession();
        ArrayList<HttpSession> al;
        if(hse.getSession().getServletContext().getAttribute("al")==null)
        {
            al=new ArrayList<>();
            
        }
        else
        {
            al=(ArrayList<HttpSession>)hse.getSession().getServletContext().getAttribute("al");
            
        }
        al.add(session);
        hse.getSession().getServletContext().setAttribute("al", al);
        System.out.println("Session Added"+hse.getSession().getId());
        
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse)
    {
        System.out.println("session destroyed " + hse.getSession().getId());
        HttpSession session = hse.getSession();
    }
}
