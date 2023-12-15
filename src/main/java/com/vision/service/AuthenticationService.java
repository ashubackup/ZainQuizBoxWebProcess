package com.vision.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.entity.TblSubscription;
import com.vision.entity.WebInfo;
import com.vision.repository.TblSubscriptionRepo;

@Service
public class AuthenticationService {
	
	@Autowired
	private TblSubscriptionRepo subRepo;
	
	public String authenticatUser(WebInfo webInfo)
	{
		String flag="Failed";
		try {
			List<TblSubscription> userInfoList = subRepo.findByAni(webInfo.getMobileNumber());
			if(!userInfoList.isEmpty())
			{
				
				for (TblSubscription userInfo : userInfoList) 
				{
					System.out.println("password "+ userInfo.getPassword());
					if(userInfo.getAni().equalsIgnoreCase(webInfo.getMobileNumber()) && userInfo.getPassword().equalsIgnoreCase(webInfo.getPassword()))
					{
						System.out.println("ani and pass match-----");
//						if(subRepo.checkNextBilledDate(userInfo.getAni()) > 0)
//						{
//							return "Success";
//						}else {
//							return flag;
//						}
						
						return "Success";
					}
				}
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return flag;
		}
		return flag;
	}
	
	
	
	
	public String checkAlreadySubUser(WebInfo webInfo)
	{
		String flag="Failed";
		try {
			List<TblSubscription> userInfoList = subRepo.findByAni(webInfo.getMobileNumber());
			if(!userInfoList.isEmpty())
			{
				for (TblSubscription userInfo : userInfoList) 
				{
					
					
					
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return flag;
		}
		return flag;
	}
}
