package com.sincered.pcr.device;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnalyzeService extends AnalyzeReceiceCommand{
	
	private List protocolCmd;
	private List noReturnCmd; 
	private List returnCmd;	
	public AnalyzeService() {
		this.protocolCmd = super.protocolCmd;
		this.noReturnCmd = super.noReturnCmd;
		this.returnCmd = super.returnCmd;	
	}
	public String Analyze(String receiveStr) {
		
		Iterator it = protocolCmd.iterator();
		while(it.hasNext()){
			String el = it.next().toString();		
			if(receiveStr.contains(el)){
				return super.extractReceiveStr(el, receiveStr);
			}			
		}
		String[] s = receiveStr.split(",");	
		if(s.length==12){//STATUS?
			return super.extractReceiveStr("STATUS?", receiveStr);
		}
		else if(s.length==8){ //REVTEMP?
			return super.extractReceiveStr("REVTEMP?", receiveStr);
		}
		else if(s.length==6){ //GETREV
			return super.extractReceiveStr("GETREV", receiveStr);
		}
		return null;
	}
	
	

}
