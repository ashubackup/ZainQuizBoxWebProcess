package com.vision.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.Utility.UtilityService;
import com.vision.entity.ServiceInfo;
import com.vision.entity.WebInfo;
import com.vision.repository.ServiceInfoRepo;



@Service
public class PinValidationService {
	@Autowired
	private ServiceInfoRepo infoRepo;
	@Autowired
	private UtilityService utilityService;
	
	public String validatePinCode(WebInfo webInfo, String host)
	{
		
		ServiceInfo serviceInfo = infoRepo.findByStatus("1");
		
		System.out.println(serviceInfo);

		String flag="Failed";
		try {
			
			if(serviceInfo != null ) 
			{	
				String dateTime = LocalDateTime.now().toString();
				System.out.println("date Time is :-- " + dateTime);
				String dencryptedSignature = utilityService.buildSignature(serviceInfo.getApiKey(), serviceInfo.getApiSecret(), serviceInfo.getApplicationId(), serviceInfo.getCountryId(),
						serviceInfo.getOperatorId(), serviceInfo.getCpId(), webInfo.getMobileNumber().toUpperCase(), dateTime, webInfo.getLanguage().toUpperCase(), 
						serviceInfo.getShortcode(), webInfo.getCode(), "ValidatePinCode");
				
				System.out.println("DecryptedSignature valid pin: "+ dencryptedSignature);

				JSONObject json = utilityService.buildJSON(serviceInfo, webInfo, dencryptedSignature, dateTime);
				
				flag = utilityService.validatePinApi(json);
				//flag="Success";
				
				//--------subscription api call------------
				if(flag.equalsIgnoreCase("Success")) 
				{
					System.out.println("SUB api calling -------");
					String subDencryptedSignature = utilityService.buildSignature(serviceInfo.getApiKey(), serviceInfo.getApiSecret(), serviceInfo.getApplicationId(), serviceInfo.getCountryId(),
							serviceInfo.getOperatorId(), serviceInfo.getCpId(), webInfo.getMobileNumber().toUpperCase(), dateTime, webInfo.getLanguage().toUpperCase(), 
							serviceInfo.getShortcode(), "Subscribe");
					
					System.out.println("DecryptedSignature for subscription api-------" + subDencryptedSignature);
					
					JSONObject jsonForSubRequest =  utilityService.buildJSON(serviceInfo, webInfo, host, subDencryptedSignature, dateTime);
					
					System.out.println("Json for sub request pin " + jsonForSubRequest);
					
					flag = utilityService.subscriptionApiCall(jsonForSubRequest);
					
				}else if(flag.equalsIgnoreCase("4")) {
					System.out.println("User already a subscriber and flag is ----- " + flag);
					flag="Success";
				}
				return flag;
			}else {
				System.out.println("ServiceInfo data is null");
				return flag;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return flag;
		}
		
	}
	
	public String urlEncode(String value) {
	    try {
	        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
	                .replace("+", "%20");
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	        return null;
	    }catch(Exception e) {
	    	e.printStackTrace();
	        return null;
	    }
	}
}
