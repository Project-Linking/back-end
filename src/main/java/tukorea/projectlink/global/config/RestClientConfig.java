package tukorea.projectlink.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class RestClientConfig {
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .messageConverters(converter -> converter.addAll(List.of(new FormHttpMessageConverter(), new MappingJackson2HttpMessageConverter())))
                .build();
    }
}
