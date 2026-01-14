package com.mycompany.vigenerecipher.controller;

import com.mycompany.vigenerecipher.model.Model;
import com.mycompany.vigenerecipher.util.CookieManager; // ДОБАВЬТЕ ЭТОТ ИМПОРТ
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Base servlet providing common functionality for all application servlets.
 * Handles common tasks like error handling, cookie management, and model access.
 * 
 * @author Zhanibek
 * @version 5.0
 */
public abstract class BaseServlet extends HttpServlet {
    
    /**
     * Gets the application model from the servlet context.
     * 
     * @return the shared model instance
     */
    protected Model getApplicationModel() {
        return (Model) getServletContext().getAttribute("applicationModel");
    }
    
    /**
     * Handles both GET and POST requests by delegating to processRequest method.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Handles both GET and POST requests by delegating to processRequest method.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Processes HTTP requests. Must be implemented by subclasses.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
    
    /**
     * Increments the visit counter cookie.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the new visit count
     */
    protected int incrementVisitCount(HttpServletRequest request, HttpServletResponse response) {
        return CookieManager.incrementCookie(request, response, CookieManager.VISIT_COUNT_COOKIE);
    }
    
    /**
     * Increments the operation counter cookie.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the new operation count
     */
    protected int incrementOperationCount(HttpServletRequest request, HttpServletResponse response) {
        return CookieManager.incrementCookie(request, response, CookieManager.OPERATION_COUNT_COOKIE);
    }
    
    /**
     * Increments the error counter cookie.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the new error count
     */
    protected int incrementErrorCount(HttpServletRequest request, HttpServletResponse response) {
        return CookieManager.incrementCookie(request, response, CookieManager.ERROR_COUNT_COOKIE);
    }
    
    /**
     * Sends an error response to the client.
     * 
     * @param response the HTTP response
     * @param statusCode the HTTP status code
     * @param message the error message
     * @throws IOException if an I/O error occurs
     */
    protected void sendError(HttpServletResponse response, int statusCode, String message) 
            throws IOException {
        response.sendError(statusCode, message);
    }
    
    /**
     * Writes the HTML header including cookie statistics.
     * 
     * @param out the PrintWriter to write to
     * @param title the page title
     * @param request the HTTP request
     */
    protected void writeHtmlHeader(PrintWriter out, String title, HttpServletRequest request) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + title + "</title>");
        out.println("<style>");
        out.println("body { font-family: Arial; margin: 20px; }");
        out.println(".cookie-stats { background: #f0f0f0; padding: 10px; margin-bottom: 20px; }");
        out.println(".error { color: red; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        
        // Display cookie statistics
        String cookieStats = CookieManager.getCookieStatistics(request);
        out.println("<div class='cookie-stats'>Cookie Statistics: " + cookieStats + "</div>");
    }
    
    /**
     * Writes the HTML footer with navigation links.
     * 
     * @param out the PrintWriter to write to
     * @param contextPath the application context path
     */
    protected void writeHtmlFooter(PrintWriter out, String contextPath) {
        out.println("<hr>");
        out.println("<p>");
        out.println("<a href='" + contextPath + "/'>Home</a> | ");
        out.println("<a href='" + contextPath + "/cipher'>Cipher</a> | ");
        out.println("<a href='" + contextPath + "/history'>History</a> | ");
        out.println("<a href='" + contextPath + "/statistics'>Statistics</a>");
        out.println("</p>");
        out.println("</body>");
        out.println("</html>");
    }
    
    /**
     * Validates that required parameters are present and not empty.
     * 
     * @param request the HTTP request
     * @param paramNames the parameter names to validate
     * @return true if all parameters are valid, false otherwise
     */
    protected boolean validateParameters(HttpServletRequest request, String... paramNames) {
        for (String paramName : paramNames) {
            String value = request.getParameter(paramName);
            if (value == null || value.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}