package com.mycompany.vigenerecipher.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Home page servlet providing navigation and application overview.
 * 
 * @author Zhanibek
 * @version 5.0
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/", "/home"})
public class HomeServlet extends BaseServlet {
    
    /**
     * Processes both GET and POST requests for the home page.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        // Increment visit counter
        int visitCount = incrementVisitCount(request, response);
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        
        writeHtmlHeader(out, "Vigenère Cipher Home", request);
        
        out.println("<h1>Welcome to Vigenère Cipher Web Application</h1>");
        
        // Welcome message with visit count
        out.println("<p>Welcome! This is your visit #" + visitCount + " to our application.</p>");
        
        // Application description
        out.println("<h2>About the Application</h2>");
        out.println("<p>This application implements the classical Vigenère cipher, ");
        out.println("a polyalphabetic substitution cipher that uses a keyword for encryption.</p>");
        
        // Features
        out.println("<h2>Features</h2>");
        out.println("<ul>");
        out.println("<li>Encrypt and decrypt text using Vigenère cipher</li>");
        out.println("<li>Maintain operation history</li>");
        out.println("<li>Search through operation history</li>");
        out.println("<li>View detailed statistics</li>");
        out.println("<li>Persistent cookie-based statistics</li>");
        out.println("<li>Error handling and user feedback</li>");
        out.println("</ul>");
        
        // Navigation
        out.println("<h2>Navigation</h2>");
        out.println("<ul>");
        out.println("<li><a href='" + contextPath + "/cipher'>Cipher Tool</a> - Encrypt/decrypt text</li>");
        out.println("<li><a href='" + contextPath + "/history'>Operation History</a> - View past operations</li>");
        out.println("<li><a href='" + contextPath + "/statistics'>Statistics</a> - View application statistics</li>");
        out.println("</ul>");
        
        // How to use
        out.println("<h2>How to Use</h2>");
        out.println("<ol>");
        out.println("<li>Go to the <a href='" + contextPath + "/cipher'>Cipher Tool</a></li>");
        out.println("<li>Enter a keyword (letters only)</li>");
        out.println("<li>Enter the text to encrypt or decrypt</li>");
        out.println("<li>Choose the operation type</li>");
        out.println("<li>Click 'Process' to see the result</li>");
        out.println("</ol>");
        
        writeHtmlFooter(out, contextPath);
    }
}