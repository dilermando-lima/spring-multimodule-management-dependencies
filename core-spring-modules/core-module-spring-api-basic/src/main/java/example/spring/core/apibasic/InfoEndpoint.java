package example.spring.core.apibasic;

import java.util.Collections;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;
 
@Configuration
public class InfoEndpoint {
 
    /**
     * @implNote this endpoint is required on infra deployment
     * @return only a empty json {}
     */
    @Bean
    public RouterFunction<ServerResponse> info(){
        return RouterFunctions
                .route()
                .GET(
                    "/info",
                    RequestPredicates.accept(MediaType.ALL) ,
                    request -> ServerResponse
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Collections.emptyMap())
                )
                .build();
    }
}
 