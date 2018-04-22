package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class prc_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    \n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("    <link href=\"css/footer.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("    <link href=\"css/demo.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("    <link href=\"css/font-awesome.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <footer class=\"footer-distributed\">\n");
      out.write("\n");
      out.write("\t\t\t<div class=\"footer-left\">\n");
      out.write("\n");
      out.write("                            <img src=\"web.png\" width=\"80dp\" height=\"80dp\">\n");
      out.write("                            <h3><abbr title=\"Indian Messenger\">I<span>M</span></abbr></h3>\n");
      out.write("\n");
      out.write("\t\t\t\t<!--<p class=\"footer-links\">\n");
      out.write("\t\t\t\t\t<a href=\"#\">Home</a>\n");
      out.write("\t\t\t\t\t·\n");
      out.write("\t\t\t\t\t<a href=\"#\">Blog</a>\n");
      out.write("\t\t\t\t\t·\n");
      out.write("\t\t\t\t\t<a href=\"#\">Pricing</a>\n");
      out.write("\t\t\t\t\t·\n");
      out.write("\t\t\t\t\t<a href=\"#\">About</a>\n");
      out.write("\t\t\t\t\t·\n");
      out.write("\t\t\t\t\t<a href=\"#\">Faq</a>\n");
      out.write("\t\t\t\t\t·\n");
      out.write("\t\t\t\t\t<a href=\"#\">Contact</a>\n");
      out.write("\t\t\t\t</p>-->\n");
      out.write("\n");
      out.write("\t\t\t\t<p class=\"footer-company-name\">IM &copy; 2016</p>\n");
      out.write("\t\t\t</div>\n");
      out.write("\n");
      out.write("\t\t\t<div class=\"footer-center\">\n");
      out.write("\n");
      out.write("\t\t\t\t<div>\n");
      out.write("\t\t\t\t\t<i class=\"fa fa-map-marker\"></i>\n");
      out.write("                                        <p> Amritsar,Punjab</p>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\n");
      out.write("\t\t\t\t<div>\n");
      out.write("                                    <i class=\"fa fa-phone\"></i>\n");
      out.write("                                    <span class=\"glyphicon glyphicon-earphone\"></span>\n");
      out.write("\t\t\t\t\t<p>+91 9417781959</p>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\n");
      out.write("\t\t\t\t<div>\n");
      out.write("\t\t\t\t\t<i class=\"fa fa-envelope\"></i>\n");
      out.write("\t\t\t\t\t<p><a href=\"mailto:im.project2k16@gmail.com\">im.project2k16@gmail.com</a></p>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\n");
      out.write("\t\t\t</div>\n");
      out.write("\n");
      out.write("\t\t\t<div class=\"footer-right\">\n");
      out.write("\n");
      out.write("\t\t\t\t<p class=\"footer-company-about\">\n");
      out.write("\t\t\t\t\t<span>About the company</span>\n");
      out.write("\t\t\t\t\tIM is developed under 6-month training  \n");
      out.write("\t\t\t\t</p>\n");
      out.write("\n");
      out.write("\t\t\t\t<div class=\"footer-icons\">\n");
      out.write("\n");
      out.write("\t\t\t\t\t<a href=\"#\"><i class=\"fa fa-facebook\"></i></a>\n");
      out.write("\t\t\t\t\t<a href=\"#\"><i class=\"fa fa-twitter\"></i></a>\n");
      out.write("\t\t\t\t\t<a href=\"#\"><i class=\"fa fa-linkedin\"></i></a>\n");
      out.write("\t\t\t\t\t<a href=\"#\"><i class=\"fa fa-github\"></i></a>\n");
      out.write("\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\n");
      out.write("\t\t\t</div>\n");
      out.write("\n");
      out.write("\t\t</footer>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
