package com.teatown.software.springboot.demo.helloworld;

import io.micrometer.observation.annotation.ObservationKeyValue;
import io.micrometer.observation.annotation.Observed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/hello-world")
@RestController
public class HelloWorldController {


    // todo copy this application into a skeleton repository so I have a functional SpringBoot application within seconds

    // todo
    //  1 does tracing work w/o the annotations ??
    //      => NOO
    //  2 GO ON HERE do I have to annotate the whole path or does it automatically work once the controller API was annotated ??
    //      =>

    @Observed(name = "greetings.get")
    @GetMapping(path = "greetings", produces = "application/json")
    public ResponseEntity<String> greetings(@ObservationKeyValue("name") @RequestParam(required = false) final String name, @ObservationKeyValue("user") @AuthenticationPrincipal final User user) {
//    public ResponseEntity<String> greetings(@RequestParam(required = false) final String name, @AuthenticationPrincipal final User user) {

        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (name == null || name.isEmpty()) {
            return ResponseEntity.ok("Hello World!");
        }

        return ResponseEntity.ok("Hello " + name + "!");
    }

}
