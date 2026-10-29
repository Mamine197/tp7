package com.example.demo.config;

import com.example.demo.ws.CompteRestJaxRSAPI;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/")
public class MyConfig extends ResourceConfig {

    public MyConfig() {
        register(CorsFilter.class);
        register(CompteRestJaxRSAPI.class);
    }
}
