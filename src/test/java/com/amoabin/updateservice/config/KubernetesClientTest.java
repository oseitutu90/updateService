//package com.amoabin.updateservice.config;
//
//import io.fabric8.kubernetes.client.KubernetesClient;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//class KubernetesClientTest {
//
//    @Autowired
//    private KubernetesClient kubernetesClient;
//
//    @Test
//    void testClient() {
//        assertNotNull(kubernetesClient);
//        System.out.println(kubernetesClient.namespaces().list());
//    }
//}