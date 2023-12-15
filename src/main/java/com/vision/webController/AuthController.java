package com.vision.webController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vision.entity.WebInfo;
import com.vision.service.AuthenticationService;

@RestController
@RequestMapping("/api")
public class AuthController {
	
	@Autowired
	private AuthenticationService authService;
	

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<?> login(@RequestBody WebInfo user) 
    {
    	Map<String,String> response = new HashMap<>();
        System.out.println("User Log in info :---- " + user);
        String flag="Failed";
        try {
        	
        	if(user != null)
            {
            	flag = authService.authenticatUser(user);
            	
            }
        	response.put("response", flag);
        	return ResponseEntity.ok(response);
        	
        }catch(Exception e)
        {
        	e.printStackTrace();
        	response.put("response", flag);
        	return ResponseEntity.ok(response);
        }
    }
    
    @PostMapping("/checkUser")
	@CrossOrigin
	public ResponseEntity<?> checkSubscriber(@RequestBody WebInfo user)
	{
		Map<String,String> response = new HashMap<>();
        System.out.println("User checking first :---- " + user);
        String flag="Failed";
        try {
        	
        	if(user != null)
            {
            	flag = authService.checkAlreadySubUser(user);
            	
            }
        	response.put("response", flag);
        	return ResponseEntity.ok(response);
        	
        }catch(Exception e)
        {
        	e.printStackTrace();
        	response.put("response", flag);
        	return ResponseEntity.ok(response);
        }	
	}
}
