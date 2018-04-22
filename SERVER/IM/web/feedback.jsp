<%@page import="listeners.SimpleMailDemo"%>
<%
    String EMAIL=request.getParameter("email");
    String MESSAGE=request.getParameter("feed");
    SimpleMailDemo obj = new SimpleMailDemo("amanpreet.03579@gmail.com","IM",EMAIL+":-Feedback is "+MESSAGE);
%>
ok