package com.amoabin.updateservice.config;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.KubernetesClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KubernetesClientConfig {

    @Bean
    public KubernetesClient kubernetesClient() {
        try {
            return new KubernetesClientBuilder().build();
        } catch (KubernetesClientException e) {
            // Log exception and handle specific client issues if required
            throw new RuntimeException("Failed to create Kubernetes client", e);
        }
    }
}