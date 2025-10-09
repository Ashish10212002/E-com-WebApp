package com.ashish.web_proj;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to set up a fallback mechanism for client-side routing.
 * Ensures all non-API and non-static file requests are forwarded to index.html.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Registers a view controller that forwards all non-API paths 
     * and non-static file requests back to the index.html page.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Handle root URL explicitly to ensure index.html is the default view
        registry.addViewController("/")
                .setViewName("forward:/index.html");

        // Forward paths with no trailing slash (e.g., /products) to index.html
        registry.addViewController("/{spring:[\\w-]+}")
                .setViewName("forward:/index.html"); 
        
        // Forward paths with multiple segments (e.g., /admin/dashboard) to index.html
        registry.addViewController("/**/{spring:[\\w-]+}")
                .setViewName("forward:/index.html");
    }
}
