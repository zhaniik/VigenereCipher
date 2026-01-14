package com.mycompany.vigenerecipher.listener;

import com.mycompany.vigenerecipher.model.Model;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Application context listener that initializes the model once
 * for the entire application lifecycle.
 * 
 * @author Zhanibek
 * @version 5.0
 */
@WebListener
public class ApplicationContextListener implements ServletContextListener {
    
    /**
     * Initializes the application context and creates the model object.
     * 
     * @param sce the servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Create model instance with default key
            Model model = new Model("DEFAULT");
            
            // Store model in application context
            ServletContext context = sce.getServletContext();
            context.setAttribute("applicationModel", model);
            
            System.out.println("Model initialized and stored in application context");
        } catch (Model.InvalidKeyException e) {
            System.err.println("Failed to initialize model: " + e.getMessage());
        }
    }
    
    /**
     * Cleans up resources when the application is destroyed.
     * 
     * @param sce the servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application context destroyed");
    }
}