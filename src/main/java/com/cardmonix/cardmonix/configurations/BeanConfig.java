package com.cardmonix.cardmonix.configurations;import com.fasterxml.jackson.databind.ObjectMapper;import org.modelmapper.ModelMapper;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.http.HttpHeaders;import org.springframework.web.client.RestTemplate;@Configurationpublic class BeanConfig {    @Bean    public RestTemplate restTemplate() {        return new RestTemplate();    }    @Bean    public HttpHeaders httpHeaders() {        return new HttpHeaders();    }    @Bean    public ModelMapper modelMapper() {        return new ModelMapper();    }    @Bean    public ObjectMapper objectMapper(){        return new ObjectMapper();    }}