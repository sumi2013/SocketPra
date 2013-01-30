package com.sincered.pcr.device;

import java.util.Arrays;

public class Command {

	//命令首部
	private byte[] COMMAND_HEAD; 
	//命令尾部
	private byte[] COMMAND_TAIL;
	//命令
	private String COMMAND;
	
	
	
	public byte[] getCOMMAND_HEAD() {
		return COMMAND_HEAD;
	}

	public void setCOMMAND_HEAD(byte[] cOMMAND_HEAD) {
		COMMAND_HEAD = cOMMAND_HEAD;
	}

	public byte[] getCOMMAND_TAIL() {
		return COMMAND_TAIL;
	}

	public void setCOMMAND_TAIL(byte[] cOMMAND_TAIL) {
		COMMAND_TAIL = cOMMAND_TAIL;
	}

	public String getCOMMAND() {
		return COMMAND;
	}

	public void setCOMMAND(String cOMMAND) {
		COMMAND = cOMMAND;
	}

	//获得可发送命令
	public byte[] getSendCommand(){
		if(this.COMMAND==null)
			return null;
		
		byte[] b,result;
		int length = this.COMMAND.length();
		b = this.COMMAND.getBytes();
		result = new byte[length];
		for(int i=0;i<length;i++){
			result[i] = (byte)this.COMMAND.charAt(i);	
		}
		
		byte[] tem = add(COMMAND_HEAD, result);
		tem = add(tem, COMMAND_TAIL);
		
		
		return tem;
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
