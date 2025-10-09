package com.ashish.web_proj;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to set up a fallback mechanism for client-side routing.
 * This ensures that paths used by React Router, which do not correspond to
 * actual server endpoints, are always forwarded back to index.html.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Registers a view controller that forwards all non-API paths 
     * and non-static file requests back to the root ('/').
     * The React application's index.html handles the routing from there.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Forwards paths that look like they could be React routes (e.g., /products or /user/123)
        // to the root path ('/').
        
        // This handles paths with no trailing slash
        registry.addViewController("/{spring:[\\w-]+}")
                .setViewName("forward:/"); 
        
        // This handles paths with multiple segments (e.g., /admin/dashboard)
        registry.addViewController("/**/{spring:[\\w-]+}")
                .setViewName("forward:/");
        
        // Note: Requests starting with /api (your controllers) or 
        // requests for existing static assets (e.g., /main.js) are naturally ignored by these patterns.
    }
}
