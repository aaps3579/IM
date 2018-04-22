<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@page import="java.util.ArrayList"%>
<%
    String sid = session.getId();

    ArrayList<HttpSession> al;

    if (getServletContext().getAttribute("al") == null) {
        al = new ArrayList<>();
    } else {
        al = (ArrayList<HttpSession>) getServletContext().getAttribute("al");
    }

    for (int i = 0; i < al.size(); i++) {

        if (al.get(i).getId().equals(sid)) {
            al.get(i).removeAttribute("phone");
            al.get(i).removeAttribute("mycontacts");
            al.get(i).removeAttribute("NAME");
            session.setAttribute("logout", "yes");
            session.removeAttribute("phone");
            response.sendRedirect("login.jsp");
        }

    }

%>
