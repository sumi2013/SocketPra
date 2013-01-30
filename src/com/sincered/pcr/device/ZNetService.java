package com.sincered.pcr.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sincered.pcr.device.ZNetAdvService.ZNetAdvImpl;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.IntByReference;

public class ZNetService extends ZNetAdvService {

	private Logger logger=Logger.getLogger(ZNetService.class);
	
	/** 获取搜寻到得设备(转换器)基本信息
	 * 目前只有一个转换器 未测试多个设备信息的获取
	 * 
	 * 设备IP/设备固件版本号/设备MAC地址/设备类型号/设备IP获取方式/TCP命令端口
	 * IP   /VERSION /MAC     /DEVTYPE/IPMODE  /TCPPORT
	 * 
	 * @return
	 */
	public List<Map> GetSearchAllDevice() {
		List<Map> devlist = new ArrayList();
		if(super.SearchAll()==1){
		 logger.info("SearchAll success!");
		 byte[] szip=new byte[20];//设备IP
		 byte[] szver=new byte[20];//设备固件版本号
		 byte[] szmac=new byte[20];//设备MAC地址
		 ByteByReference pdevtype=new ByteByReference();//设备类型号  ZNE-100TL -34
		 ByteByReference pipmode=new ByteByReference();//设备IP获取方式  0动态 1静态
		 IntByReference ptcpport=new IntByReference();//转换器TCP命令端口		 
			 
		 try {
				Thread.sleep(1000);
		 } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
		if(super.GetSearchDev(szip, szver, szmac, pdevtype, pipmode, ptcpport)==1){
					Map m = new HashMap();
					m.put("IP", new String(szip).trim());
					m.put("VERSION", new String(szver).trim());
					m.put("MAC", new String(szmac).trim());
					String type = String.valueOf(pdevtype.getValue());
					m.put("DEVTYPE", type.equals("34")?"ZNE-100TL":pdevtype);				
					m.put("IPMODE", String.valueOf(pipmode.getValue()).equals("0")?"动态获取":"静态获取");
					m.put("TCPPORT", String.valueOf(ptcpport.getValue()));
					devlist.add(m);
				}else{
					logger.error("LoginTCP failed!");
				}
				
			
		}else{
			logger.error("LoginTCP failed!");
		}
		return devlist;
	}
	
	/** 登录设备,只适用于多串口设备
	 * 
	 * @param devtype		设备类型号	
	 * @param szip			设备IP
	 * @param port			设备TCP命令端口
	 * @param szpwd			设备密码
	 * @return
	 */
	public short LoginTCP(byte devtype,String szip,int port,String szpwd){
		short result = super.LoginTCP(devtype, szip, port, szpwd);
		if(result==1){
			logger.info("LoginTCP success!");
		}else{
			logger.info("LoginTCP failed!");
		}
		return result;
	}
	
	/** 获取设备信息
	 * 转换器登录信息
	 * @param devtype ZNE-100TL - 34
	 * @param ip
	 * @param port
	 * @param pwd
	 * @return
	 */
	public Map GetDevConfigTCP(){
		logger.info("GetDevConfigTCP start!");
		//所要获取信息的设备参数名称
		String[] g_strname= {"IP","NAME","C1_OP","C1_PORT","C1_BAUD","C1_DATAB","C1_STOPB","C1_PARITY"};
		
		Map map = new HashMap();		
		for(String s:g_strname){
			byte[] st= new byte[20];
			if(super.GetDevConfigTCP(s, st)==1){
				logger.info(s+":"+new String(st,0,st.length ).trim());
				map.put(s, new String(st,0,st.length ).trim());
			}
		}
		if(super.ExitTCP()==1){
			logger.info("ExitTCP success!");
		}else{
			logger.error("ExitTCP failed!");		
		}
		logger.info("GetDevConfigTCP end.");
		return map;
	}
	
	
	/** 更改设备信息,只适用于多串口设备
	 * 
	 * String 	   所要更改信息的设备参数名称
	 * byte[]	   存储更改的信息
	 * 
	 * map<String,byte[]>
	 * @param map
	 * @return
	 */
	public short ModifyDevConfigTCP(Map<String,byte[]> map){
		short result = 0;
		if(map.isEmpty()){
			logger.error("ModifyDevConfigTCP error:map.isEmpty!");
			return 0;
		}
		Object[] keys = map.keySet().toArray();
		for(Object o:keys){
			result = super.ModifyDevConfigTCP(o.toString(), map.get(o));
			logger.info("ModifyDevConfigTCP:"+o.toString()+"->"+new String(map.get(o)).trim()+"<"+result+">");
			logger.info("ResetDevTCP:"+super.ResetDevTCP());
			if(result==0){
				logger.error("ModifyDevConfigTCP error:"+o.toString()+"->"+new String(map.get(o)).trim());
				return 0;	
			}			
		}
		if(super.ExitTCP()==1){
			logger.info("ExitTCP success!");
		}else{
			logger.error("ExitTCP failed!");		
		}
		
		return result;
	}
}
