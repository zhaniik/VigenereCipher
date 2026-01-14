/**
 * Controller package for the Vigenère Cipher web application.
 * 
 * This package contains all the servlet controllers that manage the interaction
 * between the user interface and the business logic. Controllers handle HTTP
 * requests, validate input data, invoke appropriate model methods, and generate
 * HTML responses for the client. The design follows the Model-View-Controller
 * (MVC) architectural pattern where controllers act as intermediaries between
 * views (HTML pages) and models (business logic).
 * 
 * Each servlet in this package is responsible for a specific functionality:
 * - HomeServlet: Displays the main application homepage
 * - CipherServlet: Handles encryption and decryption operations
 * - StatisticsServlet: Shows operation statistics and usage data
 * - SearchServlet: Provides search functionality for operation history
 * - ClearHistoryServlet: Manages clearing of operation history
 * 
 * All servlets are annotated with @WebServlet for URL mapping and generate
 * HTML responses directly without relying on JSP files. This approach keeps
 * the application simple and self-contained.
 * 
 * @author Zhanibek
 * @version 5.0
 * @since December 2023
 * @see com.mycompany.vigenerecipher.model
 */
package com.mycompany.vigenerecipher.controller;