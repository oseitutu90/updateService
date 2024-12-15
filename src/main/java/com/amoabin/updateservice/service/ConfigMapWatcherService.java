package com.amoabin.updateservice.service;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ConfigMapWatcherService {


    private final KubernetesClient kubernetesClient;
    @Value("${kubernetes.namespace:default}")
    private String namespace;

    public ConfigMapWatcherService(KubernetesClient kubernetesClient) {
        this.kubernetesClient = kubernetesClient;
    }

    @PostConstruct
    public void startWatching() {
        log.info("Starting to watch ConfigMaps...{}", namespace);

        kubernetesClient.configMaps().inNamespace("default").watch(new Watcher<ConfigMap>() {
            @Override
            public void eventReceived(Action action, ConfigMap configMap) {
                String name = configMap.getMetadata().getName();
                log.info("ConfigMap event received: {} {}", action.name(), name);

                switch (action) {
                    case ADDED:
                    case MODIFIED:
                        handleConfigMapUpdate(configMap);
                        break;
                    case DELETED:
                        handleConfigMapDelete(name);
                        break;
                    default:
                        log.warn("Unknown event type: {}", action.name());
                }
            }

            @Override
            public void onClose(WatcherException e) {
                log.error("Watcher closed due to error: {}", e.getMessage());
            }
        });
    }

    private void handleConfigMapUpdate(ConfigMap configMap) {
        String name = configMap.getMetadata().getName();
        log.info("Updated ConfigMap {}: {}", name, configMap.getData());
        // Handle the update logic (e.g., refresh cache, reprocess configuration)
    }

    private void handleConfigMapDelete(String name) {
        log.info("Deleted ConfigMap: {}", name);
        // Handle the delete logic (e.g., remove from cache)
    }
}
