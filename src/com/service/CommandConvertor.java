package com.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 
 * PCR仪器
 * 串口 是通过十六进制指令码发送指令,需要将字符串类型指令转换成十六进制(byte[])指令;
 * @author Administrator
 *
 */
public class CommandConvertor {
	
	/**获得通过串口可发送的(有效的)指令
	 * 
	 * @param str
	 * @return
	 */
	public byte[] getSendCommandbySerialPort(String str){
		return this.fromString2byte(str);
	}
	
	/**获得通过网口 可发送的(有效的)指令
	 * 网口转串口
	 * 发往网口的命令格式:头标识+内容+尾标识
	 * 头标识:7E A5 7D 20 7D 29 30 7D 20 25 80
	 * 为标识:7E
	 * 
     * 7EA5 7D20 7D29 307D 2025 80+内容十六进制+7E 
	 */
	public byte[] getSendCommandbyNetworkPort(String str){
		if(str==null)
			return null;
		byte[] bcm= this.fromString2byte(str);
		byte[] headFlag ={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x31,0x30,0x7D,0x20,0x25,(byte) 0x80};
		byte[] endFlag = {0x7d,0x2d,0x7d,0x2a,0x7E};
		byte[] result = this.add(headFlag, bcm);
		result = this.add(result, endFlag);
		
		return result;
	}
	
	/** 将String类型的指令转换成 可发送的(有效的)十六进制的byte数组
	 * 
	 * @param str
	 * @return
	 */
	public byte[] fromString2byte(String str){
		if(str==null)
			return null;
		
		byte[] b,result;
		int length = str.length();
		b = str.getBytes();
		result = new byte[length];
		for(int i=0;i<length;i++){
			result[i] = (byte)str.charAt(i);	
		}
		
		return result;
	}
	
	
	private byte[] add(byte[] original,byte[] addbytes){		
		int star,newlength;
		star = original.length;
		newlength = original.length+addbytes.length;
		byte[] bcon = Arrays.copyOf(original, newlength);
		for(int i=star,k=0;i<newlength;i++,k++){
			bcon[i] = addbytes[k];
		}		
		return bcon;
	}
	
	

}
