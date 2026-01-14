package com.mycompany.vigenerecipher.controller;

import com.mycompany.vigenerecipher.model.Model;
import com.mycompany.vigenerecipher.util.CookieManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Main servlet for encryption and decryption operations.
 * Provides access to the calculation part of the model.
 * 
 * @author Zhanibek
 * @version 5.0
 */
@WebServlet(name = "CipherServlet", urlPatterns = {"/cipher"})
public class CipherServlet extends BaseServlet {
    
    /**
     * Processes both GET and POST requests for cipher operations.
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
        
        // Handle POST request (form submission)
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            handlePost(request, response);
        } 
        // Handle GET request (display form)
        else {
            handleGet(request, response);
        }
    }
    
    /**
     * Handles GET request to display the cipher form.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     */
    private void handleGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        
        writeHtmlHeader(out, "Vigenère Cipher - Encrypt/Decrypt", request);
        
        out.println("<h1>Vigenère Cipher</h1>");
        out.println("<form method='POST'>");
        out.println("<label>Key: <input type='text' name='key' required></label><br>");
        out.println("<label>Text: <textarea name='text' required></textarea></label><br>");
        out.println("<label><input type='radio' name='operation' value='encrypt' checked> Encrypt</label>");
        out.println("<label><input type='radio' name='operation' value='decrypt'> Decrypt</label><br>");
        out.println("<button type='submit'>Process</button>");
        out.println("</form>");
        
        writeHtmlFooter(out, contextPath);
    }
    
    /**
     * Handles POST request to process cipher operations.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     */
    private void handlePost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // Validate parameters
        if (!validateParameters(request, "key", "text", "operation")) {
            incrementErrorCount(request, response);
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, 
                    "Missing or incorrect parameters. Key, text, and operation are required.");
            return;
        }
        
        String key = request.getParameter("key");
        String text = request.getParameter("text");
        String operation = request.getParameter("operation");
        
        Model model = getApplicationModel();
        if (model == null) {
            incrementErrorCount(request, response);
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                    "Application model not available. Please try again later.");
            return;
        }
        
        try {
            // Perform the operation
            String result;
            if ("encrypt".equals(operation)) {
                result = model.encrypt(text);
            } else if ("decrypt".equals(operation)) {
                result = model.decrypt(text);
            } else {
                incrementErrorCount(request, response);
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, 
                        "Invalid operation. Must be 'encrypt' or 'decrypt'.");
                return;
            }
            
            // Update model with new key if different
            if (!model.getKey().equals(key)) {
                model.setKey(key);
            }
            
            // Update cookies
            incrementOperationCount(request, response);
            CookieManager.writeStringCookie(response, CookieManager.LAST_KEY_COOKIE, key);
            
            // Display result
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            String contextPath = request.getContextPath();
            
            writeHtmlHeader(out, "Cipher Result", request);
            
            out.println("<h1>Cipher Result</h1>");
            out.println("<p><strong>Operation:</strong> " + 
                       ("encrypt".equals(operation) ? "Encryption" : "Decryption") + "</p>");
            out.println("<p><strong>Key:</strong> " + key + "</p>");
            out.println("<p><strong>Input Text:</strong> " + text + "</p>");
            out.println("<p><strong>Result:</strong> " + result + "</p>");
            out.println("<p><a href='" + contextPath + "/cipher'>New Operation</a></p>");
            
            writeHtmlFooter(out, contextPath);
            
        } catch (Model.InvalidKeyException e) {
            incrementErrorCount(request, response);
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, 
                    "Invalid key: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            incrementErrorCount(request, response);
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, 
                    "Invalid input: " + e.getMessage());
        } catch (Exception e) {
            incrementErrorCount(request, response);
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                    "An unexpected error occurred: " + e.getMessage());
        }
    }
}