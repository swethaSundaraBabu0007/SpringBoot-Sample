package com.itech.employeeDemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ItechController {
    @RequestMapping(value = "/test1")
    public Map testJson(){
        Map<String,String>map=new HashMap<>();
        map.put("1","pikachu");
        return map;
    }

}
