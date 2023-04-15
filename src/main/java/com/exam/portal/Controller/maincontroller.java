package com.exam.portal.Controller;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
@Controller
public class maincontroller  {
	
	
    @GetMapping("/publics")
    @ResponseBody
    public String getPublic() {
        return "index";
    }
    @ResponseBody
    @RequestMapping(value="/savecode/{mode}" , method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> saveCode(@PathVariable String mode, @RequestBody String data) throws IOException {
    	System.out.println("savecode\\mode is called");
    	File saveFile = new ClassPathResource("static").getFile();
   	    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator);
        String currentPath = String.format(path+"\\runners\\%s\\A.%s", mode, mode);
        System.out.println(currentPath);
        System.out.println("data is "+data);
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse the JSON string into a JsonNode object
        JsonNode jsonNode = objectMapper.readTree(data);

        // Get the value of the "data" field as a string
       String ndata = jsonNode.get("data").asText();
       System.out.println("data is "+ndata);
        
        FileWriter writer = new FileWriter(currentPath);
        writer.write(ndata);
        System.out.println("data saved sucessfully");
        writer.close();
        return Collections.singletonMap("sucess","ok");
    }
    
    @ResponseBody
    @RequestMapping(value="/saveinput/{mode}", method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> saveInput(@PathVariable String mode, @RequestBody String data) throws IOException {
    	System.out.println("saveinput/mode is called");
    	File saveFile = new ClassPathResource("static").getFile();
   	    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator);
        //System.out.println(path);
        String currentPath = String.format(path+"\\runners\\%s\\A.txt", mode);
        //System.out.println(currentPath);
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse the JSON string into a JsonNode object
        JsonNode jsonNode = objectMapper.readTree(data);

        // Get the value of the "data" field as a string
        String ndata = jsonNode.get("data").asText();
        FileWriter writer = new FileWriter(currentPath);
        writer.write(ndata);
        writer.close();
        return Collections.singletonMap("sucess","ok");
    }
    
    @ResponseBody
    @RequestMapping(value="/getcode/{mode}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> getCode(@PathVariable String mode) throws IOException {
    	System.out.println("getcode/mode is called");
    	File saveFile = new ClassPathResource("static/templates").getFile();

        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator);
        //System.out.println(path+"\\A.%s");
        String currentPath = String.format(path+"\\A.%s", mode);
       // System.out.println(currentPath);
        Scanner scanner = new Scanner(new File(currentPath));
        String result = scanner.useDelimiter("\\A").next();
        scanner.close();
       // System.out.println(result);
        return Collections.singletonMap("data",result);
    }
     
    @ResponseBody
    @RequestMapping(value="/run/{mode}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> runCode(@PathVariable String mode) throws IOException {
    	System.out.println("run/mode is called");
    	File saveFile = new ClassPathResource("static").getFile();
   	    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator);
        String currentPath = String.format(path+"\\runners\\%s", mode);
        String[] cmd;
        System.out.println(currentPath);
        if (mode.equals("cpp")) {
            cmd = new String[]{"cmd", "/c", "g++ -DLOCAL -std=c++17 A.cpp -o A && ./A < A.txt 2>&1"};
        } else if (mode.equals("py")) {
            cmd = new String[]{"cmd", "/c", "python3 A.py < A.txt"};
        } else if (mode.equals("java")) {
            cmd = new String[]{"cmd", "/c", "javac A.java && java A < A.txt"};
        } else {
            return Collections.singletonMap("success","Invalid mode");
        }
        
        Process process = Runtime.getRuntime().exec(cmd, null, new File(currentPath));
        InputStream inputStream = process.getInputStream();
        InputStream errorStream = process.getErrorStream();
        
        String output = readStream(inputStream);
        String error = readStream(errorStream);
          
        System.out.println(output);
        System.out.println(error);
        if (error.isEmpty()) {
            return Collections.singletonMap("success",output);
        } else {
            return Collections.singletonMap("success",error);
        }
    }
    
    private String readStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        System.out.println("hello from reader");
        String line;
        while ((line = reader.readLine()) != null) {
        	//System.out.println(line);
            builder.append(line).append("\n");
        }
        reader.close();
        return builder.toString();
    }
}
