package com.teamwork.teamwork.config;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration // Tells Spring this is a configuration class
public class CloudinaryConfig {

    @Bean // Creates a Cloudinary object that can be injected anywhere
    public Cloudinary cloudinary() {

        // Load variables from .env file
        Dotenv dotenv = Dotenv.load();

        // Create config map for Cloudinary
        Map<String, String> config = new HashMap<>();

        // Get values from .env and store in a variable
        String cloudName = dotenv.get("CLOUDINARY_CLOUD_NAME");
        String apiKey = dotenv.get("CLOUDINARY_API_KEY");
        String apiSecret = dotenv.get("CLOUDINARY_API_SECRET");

        // put/add in new map config... Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);

        // Return Cloudinary instance
        return new Cloudinary(config);
    }
}
