package com.vision.service;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.Utility.UtilityService;
import com.vision.entity.ServiceInfo;
import com.vision.entity.WebInfo;
import com.vision.repository.ServiceInfoRepo;
import com.vision.repository.WebInfoRepo;

@Service
public class RequestPinService 
{
	@Autowired
	private ServiceInfoRepo infoRepo;
	@Autowired
	private UtilityService utilityService;
	@Autowired
	private WebInfoRepo webRepo;
	
	public String requestPinCode(WebInfo webInfo, String host)
	{
		ServiceInfo serviceInfo = infoRepo.findByStatus("1");
		System.out.println(serviceInfo);
		try {
			
			if(serviceInfo != null ) 
			{
				String dateTime = LocalDateTime.now().toString();
				
		        System.out.println("Date time is : " + dateTime);
		        
				String dencryptedSignature = utilityService.buildSignature(serviceInfo.getApiKey(), serviceInfo.getApiSecret(), serviceInfo.getApplicationId(), serviceInfo.getCountryId(),
						serviceInfo.getOperatorId(), serviceInfo.getCpId(), webInfo.getMobileNumber().toUpperCase(), dateTime, webInfo.getLanguage().toUpperCase(), 
						serviceInfo.getShortcode(), "RequestPinCode");
				
				System.out.println("Decrypted Signature rp ---" +  dencryptedSignature);

				JSONObject json = utilityService.buildJSON(serviceInfo, webInfo, host, dencryptedSignature, dateTime);
				System.out.println("Json For request pin--- :"+ json);
				return utilityService.requestPinApiCall(json);
				
				
			}else {
				System.out.println("ServiceInfo data is null");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return "Failed";
		}
		
		return "Failed";
		
	}
	
	 public void saveUserData(WebInfo webInfo)
	 {
		 try {
			 webInfo.setStatus("0");
			 webInfo.setOperator("Zain");		
			 webRepo.save(webInfo);
		 }catch(Exception e) {
			 e.printStackTrace();	
		 }
	 }
}
