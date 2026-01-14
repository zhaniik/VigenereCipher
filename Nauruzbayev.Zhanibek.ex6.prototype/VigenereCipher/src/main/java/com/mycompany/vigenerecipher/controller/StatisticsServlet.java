package com.mycompany.vigenerecipher.controller;

import com.mycompany.vigenerecipher.model.Model;
import com.mycompany.vigenerecipher.util.CookieManager; // ДОБАВЬТЕ ЭТОТ ИМПОРТ
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for displaying comprehensive statistics about cipher operations.
 * Shows both model statistics and cookie-based session statistics.
 * 
 * @author Zhanibek
 * @version 5.0
 */
@WebServlet(name = "StatisticsServlet", urlPatterns = {"/statistics"})
public class StatisticsServlet extends BaseServlet {
    
    /**
     * Processes both GET and POST requests for statistics display.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        // Increment visit counter
        incrementVisitCount(request, response);
        
        Model model = getApplicationModel();
        if (model == null) {
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                    "Application model not available.");
            return;
        }
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        
        writeHtmlHeader(out, "Operation Statistics", request);
        
        out.println("<h1>Operation Statistics</h1>");
        
        // Get statistics from model
        int[] stats = model.getStatistics();
        int total = stats[0];
        int encryptions = stats[1];
        int decryptions = stats[2];
        
        // Display statistics
        out.println("<h2>Model Statistics</h2>");
        out.println("<ul>");
        out.println("<li>Total Operations: " + total + "</li>");
        out.println("<li>Encryptions: " + encryptions + "</li>");
        out.println("<li>Decryptions: " + decryptions + "</li>");
        
        if (total > 0) {
            double encPercent = (encryptions * 100.0) / total;
            double decPercent = (decryptions * 100.0) / total;
            out.println("<li>Encryption Rate: " + String.format("%.1f", encPercent) + "%</li>");
            out.println("<li>Decryption Rate: " + String.format("%.1f", decPercent) + "%</li>");
        }
        out.println("</ul>");
        
        // Display cookie statistics
        out.println("<h2>Session Statistics (Cookies)</h2>");
        out.println("<p>The following statistics are stored in cookies and persist between visits:</p>");
        out.println("<ul>");
        out.println("<li>Total Visits: " + 
                   CookieManager.readIntCookie(request, CookieManager.VISIT_COUNT_COOKIE) + "</li>");
        out.println("<li>Total Operations: " + 
                   CookieManager.readIntCookie(request, CookieManager.OPERATION_COUNT_COOKIE) + "</li>");
        out.println("<li>Total Errors: " + 
                   CookieManager.readIntCookie(request, CookieManager.ERROR_COUNT_COOKIE) + "</li>");
        out.println("<li>Last Key Used: " + 
                   (CookieManager.readStringCookie(request, CookieManager.LAST_KEY_COOKIE).isEmpty() ? 
                    "None" : CookieManager.readStringCookie(request, CookieManager.LAST_KEY_COOKIE)) + "</li>");
        out.println("</ul>");
        
        writeHtmlFooter(out, contextPath);
    }
}