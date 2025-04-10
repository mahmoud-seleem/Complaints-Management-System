package com.example.Complaints.Management.System.core.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiServiceImp {

//    private final WebClient.Builder builder = WebClient.builder();
    private final RestTemplate restTemplate = new RestTemplate();

    public Object getRandomJoke() {
//        WebClient webClient = builder.baseUrl("https://v2.jokeapi.dev").build();
//
//        Mono<Object> response = webClient.get()
//                .uri("/")
//                .retrieve()
//                .bodyToMono(Object.class);
//        return response.block();  // This blocks for simplicity

        String url = "https://v2.jokeapi.dev/joke/Programming?type=single"; // Random programming joke
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
        }
    }


