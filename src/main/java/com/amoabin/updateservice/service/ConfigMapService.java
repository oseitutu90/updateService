//package com.amoabin.updateservice.service;
//
//import io.fabric8.kubernetes.api.model.ConfigMap;
//import io.fabric8.kubernetes.client.KubernetesClient;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//@Slf4j
//@Service
//public class ConfigMapService {
//
//
//    private final KubernetesClient kubernetesClient;
//
//
//    public ConfigMapService(KubernetesClient kubernetesClient) {
//        this.kubernetesClient = kubernetesClient;
//    }
//
//    public ConfigMap getConfigMap(String name, String namespace) {
//        try {
//            ConfigMap configMap = kubernetesClient.configMaps().inNamespace(namespace).withName(name).get();
//            if (configMap == null) {
//                throw new RuntimeException("ConfigMap not found: " + name);
//            }
//            log.info("Fetched ConfigMap {}: {}", name, configMap.getData());
//            return configMap;
//        } catch (Exception e) {
//            log.error("Error fetching ConfigMap: {}", e.getMessage());
//            throw new RuntimeException("Failed to fetch ConfigMap: " + name, e);
//        }
//    }
//}