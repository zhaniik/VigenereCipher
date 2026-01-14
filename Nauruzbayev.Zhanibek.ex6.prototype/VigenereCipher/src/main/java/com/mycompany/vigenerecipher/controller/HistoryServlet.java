package com.mycompany.vigenerecipher.controller;

import com.mycompany.vigenerecipher.model.HistoryRecord;
import com.mycompany.vigenerecipher.model.Model;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet for displaying calculation history stored in the model.
 * Provides access to the history data collection in the model.
 * 
 * @author Zhanibek
 * @version 5.0
 */
@WebServlet(name = "HistoryServlet", urlPatterns = {"/history"})
public class HistoryServlet extends BaseServlet {
    
    /**
     * Processes both GET and POST requests for history display.
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
        
        writeHtmlHeader(out, "Operation History", request);
        
        out.println("<h1>Operation History</h1>");
        
        // Check if search parameter is provided
        String searchText = request.getParameter("search");
        if (searchText != null && !searchText.trim().isEmpty()) {
            displaySearchResults(out, model, searchText);
        } else {
            displayAllHistory(out, model);
        }
        
        // Search form
        out.println("<h2>Search History</h2>");
        out.println("<form method='GET'>");
        out.println("<label>Search Text: <input type='text' name='search'></label>");
        out.println("<button type='submit'>Search</button>");
        out.println("</form>");
        
        writeHtmlFooter(out, contextPath);
    }
    
    /**
     * Displays all operation history.
     * 
     * @param out the PrintWriter to write to
     * @param model the application model
     */
    private void displayAllHistory(PrintWriter out, Model model) {
        List<HistoryRecord> history = model.getHistory().searchRecords("");
        
        if (history.isEmpty()) {
            out.println("<p>No operations in history.</p>");
        } else {
            out.println("<p>Total operations: " + history.size() + "</p>");
            out.println("<table border='1' style='border-collapse: collapse; width: 100%;'>");
            out.println("<tr><th>Mode</th><th>Key</th><th>Input</th><th>Output</th></tr>");
            
            for (HistoryRecord record : history) {
                out.println("<tr>");
                out.println("<td>" + record.mode() + "</td>");
                out.println("<td>" + record.key() + "</td>");
                out.println("<td>" + record.input() + "</td>");
                out.println("<td>" + record.output() + "</td>");
                out.println("</tr>");
            }
            
            out.println("</table>");
        }
    }
    
    /**
     * Displays search results from history.
     * 
     * @param out the PrintWriter to write to
     * @param model the application model
     * @param searchText the text to search for
     */
    private void displaySearchResults(PrintWriter out, Model model, String searchText) {
        try {
            List<HistoryRecord> results = model.searchHistory(searchText);
            
            out.println("<h2>Search Results for: " + searchText + "</h2>");
            
            if (results.isEmpty()) {
                out.println("<p>No matching records found.</p>");
            } else {
                out.println("<p>Found " + results.size() + " matching record(s):</p>");
                out.println("<table border='1' style='border-collapse: collapse; width: 100%;'>");
                out.println("<tr><th>Mode</th><th>Key</th><th>Input</th><th>Output</th></tr>");
                
                for (HistoryRecord record : results) {
                    out.println("<tr>");
                    out.println("<td>" + record.mode() + "</td>");
                    out.println("<td>" + record.key() + "</td>");
                    out.println("<td>" + record.input() + "</td>");
                    out.println("<td>" + record.output() + "</td>");
                    out.println("</tr>");
                }
                
                out.println("</table>");
            }
        } catch (IllegalArgumentException e) {
            out.println("<p class='error'>Search error: " + e.getMessage() + "</p>");
        }
    }
}