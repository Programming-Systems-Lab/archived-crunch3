package psl.crunch3.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class SettingsServlet extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  private static final String DOC_TYPE =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"" +
      " \"http://www.w3.org/TR/html4/strict.dtd\">";


  //Initialize global variables
  public void init() throws ServletException {
  }





  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession s = request.getSession(true);
    SettingsBean b = (SettingsBean)s.getAttribute("information");
    if (b==null){
      b = new SettingsBean();
      s.setAttribute("b", b);
    }


    
     b.setAppendLinks((request.getParameter("appendLinks")));
     b.setDisplayAltTags((request.getParameter("displayAltTags")));
     b.setDisplayImageLinkAlts((request.getParameter("displayImageLinkAlts")));
     b.setHtmlOutput((request.getParameter("htmlOutput")));
     b.setIgnoreAds((request.getParameter("ignoreAds")));
     b.setIgnoreButton((request.getParameter("ignoreButton")));
     b.setIgnoreEmbed((request.getParameter("ignoreEmbed")));
     b.setIgnoreExternalStylesheets((request.getParameter("ignoreExternalStylesheets")));
     b.setIgnoreFlash((request.getParameter("ignoreFlash")));
     b.setIgnoreForms((request.getParameter("ignoreForms")));
     b.setIgnoreIframe((request.getParameter("ignoreIframe")));
     b.setIgnoreImageLinks((request.getParameter("ignoreImageLinks")));
     b.setIgnoreImages((request.getParameter("ignoreImages")));
     b.setIgnoreInput((request.getParameter("ignoreInput")));
     b.setIgnoreLinkLists((request.getParameter("ignoreLinkLists")));
     b.setIgnoreLLImageLinks((request.getParameter("ignoreLLImageLinks")));
     b.setIgnoreLLTextLinks((request.getParameter("ignoreLLTextLinks")));
     b.setIgnoreMeta((request.getParameter("ignoreMeta")));
     b.setIgnoreNoscript((request.getParameter("ignoreNoscript")));
     b.setIgnoreOnlyTextAndLinks((request.getParameter("ignoreOnlyTextAndLinks")));
     b.setIgnoreScripts((request.getParameter("ignoreScripts")));
     b.setIgnoreSelect((request.getParameter("ignoreSelect")));
     b.setIgnoreStyleAttributes((request.getParameter("ignoreStyleAttributes")));
     b.setIgnoreStyleInDiv((request.getParameter("ignoreStyleInDiv")));
     b.setIgnoreStyles((request.getParameter("ignoreStyles")));
     b.setIgnoreTableCellWidths((request.getParameter("ignoreTableCellWidths")));
     b.setIgnoreTextLinks((request.getParameter("ignoreTextLinks")));
     b.setLimitLineBreaks((request.getParameter("limitLineBreaks")));
     b.setLinkTextRatio((request.getParameter("linkTextRatio")));
     b.setMaxLineBreaks((request.getParameter("maxLineBreaks")));
     b.setMinimumTextLength((request.getParameter("minimumTextLength")));
     b.setRemoveEmptyTables((request.getParameter("removeEmptyTables")));
     b.setSubstanceButton((request.getParameter("substanceButton")));
     b.setSubstanceForm((request.getParameter("substanceForm")));
     b.setSubstanceIFrame((request.getParameter("substanceIFrame")));
     b.setSubstanceImage((request.getParameter("substanceImage")));
     b.setSubstanceInput((request.getParameter("substanceInput")));
     b.setSubstanceLinks((request.getParameter("substanceLinks")));
     b.setSubstanceSelect((request.getParameter("substanceSelect")));
     b.setSubstanceTextarea((request.getParameter("substanceTextarea")));
     b.setIgnoreTextLinks((request.getParameter("ignoreTextLinks")));
     b.setIgnoreForms((request.getParameter("ignoreForms")));
     b.setIgnoreFlash((request.getParameter("ignoreFlash")));
     
     

     b.commitSettings();


    response.setContentType(CONTENT_TYPE);

    PrintWriter out = response.getWriter();
    out.println(DOC_TYPE);
    out.println("<html>");
    out.println("<head> <title>test test</title> </head>");
    out.println("<body> <H2> " + "Changes Submitted" + "</H2> </body></html>");
     		

    
    /**  RequestDispatcher r = getServletContext().getRequestDispatcher(
          "/Form.jsp");
      r.forward(request, response);
**/

    


  }






//Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

}