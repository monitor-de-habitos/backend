package br.com.monitodehabitos.monitodehabitos.infra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloWorld {
    @GetMapping("/")
    public ResponseEntity<ResponseDto> helloWorld(){
        return ResponseEntity.ok(new ResponseDto("Api funcionando corretamente, Hello world"));
    }
}
