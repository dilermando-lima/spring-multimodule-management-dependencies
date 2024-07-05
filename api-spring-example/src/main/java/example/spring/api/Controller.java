package example.spring.api;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/endpointA")
public class Controller {
    
    @GetMapping
    public ResponseEntity<Map<String,String>> anyRequest(){
        return ResponseEntity.ok(Map.of("Status","failed sucessfully!!!"));
    }

}
