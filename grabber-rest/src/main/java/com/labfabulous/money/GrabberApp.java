package com.labfabulous.money;

import com.labfabulous.money.api.Entity;
import com.labfabulous.money.service.IdentifierFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@SpringBootApplication
@Controller
public class GrabberApp {

    @Autowired
    private IdentifierFinderService finder;

    @RequestMapping(value = "/",
                    method = RequestMethod.POST,
                    consumes = "text/plain",
                    produces = "application/json")
    @ResponseBody
    List<Entity> parse(@RequestBody String text) {
        return finder.findEntities(text);
    }

    public static void main(String[] args) {
        SpringApplication.run(GrabberApp.class, args);
    }
}
