package com.mycompany.vigenerecipher.controller;

import com.mycompany.vigenerecipher.model.Model;
import com.mycompany.vigenerecipher.model.HistoryRecord;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet for searching operation history.
 * Allows users to search through previous encryption/decryption records.
 * 
 * @author Zhanibek
 * @version 5.0
 */

@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    
    /**
     * Displays search form.
     */
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Search History</title></head>");
        out.println("<body>");
        out.println("<h1>Search Operation History</h1>");
        out.println("<form action='" + contextPath + "/search' method='post'>");
        out.println("<label>Search Text: <input type='text' name='searchText' required></label><br>");
        out.println("<button type='submit'>Search</button>");
        out.println("</form>");
        out.println("<a href='" + contextPath + "/'>Home</a>");
        out.println("</body></html>");
    }
    
    /**
     * Processes search request and displays results.
     */
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String searchText = request.getParameter("searchText");
        HttpSession session = request.getSession(false);
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Search Results</title></head>");
        out.println("<body>");
        
        if (session == null || searchText == null || searchText.trim().isEmpty()) {
            out.println("<h1>Invalid Search Request</h1>");
            out.println("<p>Please provide valid search text.</p>");
        } else {
            Model model = (Model) session.getAttribute("cipherModel");
            if (model == null) {
                out.println("<h1>No History Found</h1>");
                out.println("<p>No cipher operations performed yet.</p>");
            } else {
                try {
                    List<HistoryRecord> results = model.searchHistory(searchText.trim());
                    out.println("<h1>Search Results for \"" + searchText + "\"</h1>");
                    out.println("<p>Found " + results.size() + " record(s)</p>");
                    
                    for (HistoryRecord record : results) {
                        out.println("<div style='border:1px solid #ccc; padding:10px; margin:10px;'>");
                        out.println("<p><strong>" + record.mode() + "</strong></p>");
                        out.println("<p>Input: " + record.input() + "</p>");
                        out.println("<p>Output: " + record.output() + "</p>");
                        out.println("<p>Key: " + record.key() + "</p>");
                        out.println("</div>");
                    }
                } catch (IllegalArgumentException e) {
                    out.println("<h1>Error</h1>");
                    out.println("<p style='color: red;'>" + e.getMessage() + "</p>");
                }
            }
        }
        
        out.println("<a href='" + contextPath + "/search'>New Search</a> | ");
        out.println("<a href='" + contextPath + "/'>Home</a>");
        out.println("</body></html>");
    }
}