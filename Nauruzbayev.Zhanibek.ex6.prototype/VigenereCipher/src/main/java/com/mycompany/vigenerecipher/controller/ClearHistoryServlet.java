package com.mycompany.vigenerecipher.controller;

import com.mycompany.vigenerecipher.model.Model;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for clearing operation history.
 * Removes all stored encryption/decryption records.
 * 
 * @author Zhanibek
 * @version 5.0
 */

@WebServlet(name = "ClearHistoryServlet", urlPatterns = {"/clear"})
public class ClearHistoryServlet extends HttpServlet {
    
    
    /**
     * Processes clear history request.
     * Clears the operation history from user session.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String message;
        
        if (session != null) {
            Model model = (Model) session.getAttribute("cipherModel");
            if (model != null) {
                model.clearHistory();
                message = "History cleared successfully.";
            } else {
                message = "No history to clear.";
            }
        } else {
            message = "No session found.";
        }
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Clear History</title></head>");
        out.println("<body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<a href='" + contextPath + "/'>Back to Home</a>");
        out.println("</body></html>");
    }
    /**
     * Redirects GET requests to home page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.sendRedirect(request.getContextPath() + "/");
    }
}