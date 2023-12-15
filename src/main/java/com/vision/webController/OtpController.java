package com.vision.webController;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.vision.entity.WebInfo;
import com.vision.service.PinValidationService;

@RestController
public class OtpController 
{
	@Autowired
	private PinValidationService validationService;
	
	@PostMapping("/otp")
	@CrossOrigin
	public ResponseEntity<?> otpController(@RequestBody WebInfo webInfo, @RequestHeader Map<String,String> header)
	{
		Map<String,String> response = new HashMap<>();
		String flag="Error";
		try {
			if(webInfo != null)
			{
				
				System.out.println("evinaRequestId-----" + webInfo.getEvinaRequestId());
				System.out.println("Web Info is ---"+ webInfo);
				JSONObject jsonObject = new JSONObject(header);
				
				
				System.out.println("Header data ---: " + jsonObject.get("host"));
				String[] parts = jsonObject.get("host").toString().split(":");
		        String host = parts[0];
				
				flag = validationService.validatePinCode(webInfo, host);
				//flag="Success";
				
				
				response.put("response",flag);
				return ResponseEntity.ok(response);
			}else {
				System.out.println("WAP Data is null");
				response.put("response",flag);
				return ResponseEntity.ok(response);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Error");
		}
		
	}

}
