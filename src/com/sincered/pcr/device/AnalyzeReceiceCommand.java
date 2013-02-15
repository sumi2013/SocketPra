package com.sincered.pcr.device;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.omg.CosNaming.NamingContextPackage.NotFound;

import com.sun.istack.internal.logging.Logger;

public class AnalyzeReceiceCommand {
	
	private Logger logger =  Logger.getLogger(AnalyzeReceiceCommand.class);	

	/**用户程序下载协议	需发送OK确认  	
	 * 返回Str中 包含发送关键词
	 * 截取方法:关键词开头  - "\r"结尾
	 */
	protected static List protocolCmd = 
			Arrays.asList("PROGRAM","METHOD","GRAD","TEMP","GOTO","END","HOTLID","LIDFORCE",
					"LIDANGLE","VOLUME","RATE","INC","EXT","HLD");
	
	/**没有回传数据的命令
	 * 
	 */
	protected static List noReturnCmd = 
			Arrays.asList("PAUSE","RESUME","PROSEED","CANCEL","OK","RUN","SETBLKTEMP","SETSENPAR","SETPTPAR",
					"BLKCLOSE","DISBLOCK");

	/**回传数据的命令
	 * 
	 */
	protected static List returnCmd = 
			Arrays.asList("REVTEMP?","GETREV","STATUS?");
	
	/**解析发送命令后获得的字串
	 * 
	 * @param sendCmd		发送的命令
	 * @param receiveStr	接收到的字串
	 * @return				若接收的字串格式正确,且有返回值,则解析接收到的字串,返回解析后的字串;
	 * 						否则返回空值 null
	 */
	public String Analyze(Command sendCmd,String receiveStr){
		
		if(sendCmd==null || receiveStr==null)
			return null;
		String extractReceiveStr = this.extractReceiveStr(sendCmd.getCOMMAND(), receiveStr);
		if(extractReceiveStr!=null)
			return extractReceiveStr;
		
		return null;		
	}
	
	/**判断接收字串(仪器回传信息)是否符合规范
	 * 
	 * @param sendCmd		发送的命令
	 * @param receiveStr	接收到的字串
	 * @return				若接收的字串格式正确,返回true;
	 * 						否则返回false;
	 */
	public boolean isRecive(Command sendCmd,String receiveStr){
		if(sendCmd==null || receiveStr==null)
			return false;
		String result = this.Analyze(sendCmd, receiveStr);
		if(result!=null)
			return true;
		
		return false;
	}
	
	/**截取接收的字串
	 * 
	 * @see 去掉接收的字串中包含的首部和尾部,取其有效内容
	 * 
	 * @param sendCmdKeyword	发送的命令关键词
	 * @param receiveStr		接收到的字串
	 * @return					截取后的字串
	 */
	protected String extractReceiveStr(String sendCmdKeyword,String receiveStr){
	
		if(receiveStr ==null)return null;
		
		//用户程序下载协议  	有返回值
		if(protocolCmd.contains(sendCmdKeyword)){
			//返回信息是否有效
			if(receiveStr.contains(sendCmdKeyword)){				
				return receiveStr.substring(receiveStr.indexOf(sendCmdKeyword),
						receiveStr.indexOf("\r"));				
			}else
				return null;
		}
		//没有返回数据的命令
		else if(noReturnCmd.contains(sendCmdKeyword)){
			return null;
		}
		//回传数据的命令
		else if(returnCmd.contains(sendCmdKeyword)){
			if(sendCmdKeyword.contains("STATUS")){
				if(receiveStr.indexOf("")!=-1)
			    	return receiveStr.substring(receiveStr.indexOf("")+1, receiveStr.lastIndexOf(","));
				else
					return null;
		    	   
			}
			else if(sendCmdKeyword.contains("REVTEMP")){
				 if(receiveStr.indexOf("\"")!=-1)
			    	return receiveStr.substring(receiveStr.indexOf("\"")+1, receiveStr.lastIndexOf("\r"));
				 else 
					 return null;		    	
			}
			else if(sendCmdKeyword.contains("GETREV")){
				 if(receiveStr.indexOf("!")!=-1)
			    	   return receiveStr.substring(receiveStr.indexOf("!")+1, receiveStr.lastIndexOf("\r"));
				 else 
					 return null;
			}
		}
	
		
		return null;
				
	}
}
