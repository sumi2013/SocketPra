package com.sincered.pcr.device;


import org.apache.log4j.Logger;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.IntByReference;
/**ZNE-100TL设备底层接口包装
 * 
 * 将ZNetAdv.dll文件放入C:\WINDOWS\system32文件夹
 * 
 * 设备100-TL为单串口产品
 * 使用多串口协议
 * 
 * @author Administrator
 * 
 * 其中方法返回值说明:
 * 返回1表示成功，其余返回错误码
 * 错误码列表
 * 0	普通错误
 * 100	获取设备信息失败
 * 101	不支持的操作
 * 102	密码错误
 * 103	连接失败
 * 104	命令没有响应
 * 105	没有登录
 * 106	无效的命令码
 * 107	参数格式不对
 * 108	参数长度不对
 *
 */
public class ZNetAdvService {	
	
	public short SearchAll(){
		return ZNetAdvImpl.instance.ZN_SearchAll();		
	}	
	public short Search(String ip){		
		short result = ZNetAdvImpl.instance.ZN_Search(ip);		
		return result;
	}
	
	/** 获取搜索到的设备基本信息
	 * 
	 * @param szip  	设备IP
	 * @param szver 	设备固件版本
	 * @param szmac		设备硬件地址
	 * @param pdevtype  设备类型号
	 * @param pipmode	设备类型为多串口时有效,表示设备IP获取方式,1静态/0动态
	 * @param ptcpport  设备类型为多串口时有效,表示TCP命令端口号
	 * @return
	 */
	public short GetSearchDev(byte[] szip,byte[] szver,byte[] szmac,ByteByReference pdevtype,ByteByReference pipmode,IntByReference ptcpport){
		short result = ZNetAdvImpl.instance.ZN_GetSearchDev(szip, szver, szmac,pdevtype, pipmode,ptcpport);
		
		System.out.println("GetSearchDev:\nIP:"+
		new String(szip).trim()+
		"\nVer:"+ new String(szver).trim()+
		"\nMAC:"+new String(szmac).trim()+
		"\n设备类型:"+pdevtype.getValue()+
		"\nIP方式(1静态/0动态):"+pipmode.getValue()+
		"\nPort:"+ ptcpport.getValue());
		
		return result;
	}
	
	/** 发送命令到设备,只适用于单串口设备	[暂时用不到]
	 * 
	 * @param devtype  设备类型
	 * @param szip 	  设备IP
	 * @param port   设备端口
	 * @param szname  命令名称
	 * @param szval  命令值
	 * @param budp  1 表示以UDP方式发送  0 表示以TCP方式发送
	 * @return
	 */	
	public short SendCmd(byte devtype,String szip,int port,String szname,String szval,byte budp){
		return ZNetAdvImpl.instance.ZN_SendCmd(devtype, szip, port, szname, szval, budp);
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
		return ZNetAdvImpl.instance.ZN_LoginTCP(devtype, szip, port, szpwd);
	}
	
	/** 获取设备信息,只适用于多串口设备
	 * 
	 * 
	 * @param szname  	所要获取信息的设备参数名称
	 * 基本信息:
	 * TYPE:设备类型
	 * VERSION:设备固件版本
	 * NAME:设备名称
	 * PASS:设备密码
	 * 
	 * IP信息:
	 * IP:
	 * MARK:
	 * GATEWAY:
	 * MAC:	  
	 * IP_MODE:1 获取IP方式  -0动态获取 1静态获取
	 * DNS:
	 * 
	 * 串口配置:
	 * C1_OP:2 工作方式 - '0'TCP Service '1'TCP Client '2'RealCOM '3'UDP '4'禁用 	
	 * C1_PORT:4003 工作端口	- 4003  设备对应的端口
	 * C1_IT:0 超时断开时间
	 * C1_TCPAT: 20 心跳检测时间
	 * C1_BAUD:9600 波特率
	 * C1_DATAB:8 数据位	
	 * C1_STOPB:1 停止位
	 * C1_PARITY:0 校验位 - 0无校验位 1奇校验 2偶校验 3强制为1 4强制为0
	 * C1_SER_LEN:500 分包长度 - 最大1478
	 * C1_SER_T:50 串口帧间隔
	 * C1_BUF_CLS:0 清空串口 - '0'从不清空  '1'TCP连接时清空
	 * C1_TCP_TURBO:1 TCPTurBo - '0' 禁用 '1'启用
	 * C1_TCP_CLS:0 TCP连接断开 - '0'不断开 '1'硬件连接断开则断开
	 * C1_LINK_P:0 TCP连接密码 - '0'不校验'1'连接后校验
	 * C1_LINK_S：0 TCP连接发送信息 - '0'不发送 '1'发送设备名称 '2'发送设备IP
	 * C1_LINK_T:0 TCP连接条件-'0'无条件,上电就连 '1'串口接收到任何数据 '2'DSR On/Off
	 * C1_LINK_NUM:1 TCP连接数 - 0~4
	 * 
	 * @param szval		存储返回的参数	   
	 * @return
	 * 
	 * 
	 * 
	 * 
	 */
	public short GetDevConfigTCP(String szname,byte[] szval){
		short result = ZNetAdvImpl.instance.ZN_GetDevConfigTCP(szname,szval);
		
		System.out.println(szname+":"+new String(szval,0,szval.length ).trim());		
		
		return result;
	}	
	
	/** 更改设备信息,只适用于多串口设备
	 * 
	 * @param szname 所要更改信息的设备参数名称
	 * @param szval	   存储更改的信息
	 * @return
	 */
	public short ModifyDevConfigTCP(String szname,byte[] szval){
		short result = ZNetAdvImpl.instance.ZN_ModifyDevConfigTCP(szname,szval);
		
		//System.out.println(szname+":"+new String(szval,0,szval.length ).trim());		
		
		return result;
	}	
	
	public short ResetDevTCP(){
		return ZNetAdvImpl.instance.ZN_ResetDevTCP();
		
	}
	
	public short ExitTCP(){
		return ZNetAdvImpl.instance.ZN_ExitTCP();
	}
	
	
	public interface ZNetAdvImpl extends Library{
		/**搜索dll的路包括：
		 * 1.项目根路径; 如:D:\XSD\workspace\SocketExample
		 * 2.OS全局路径; 如:C:\WINDOWS 、C:\WINDOWS\system32
		 * 3.path指定的路径;
		 * 
		 */
		ZNetAdvImpl instance=(ZNetAdvImpl)Native.loadLibrary("ZNetAdv", ZNetAdvImpl.class);
	
		public short ZN_SearchAll();		
		public short ZN_Search(String szip);
		public short ZN_GetSearchDev(byte[] szip,byte[] szver,byte[] szmac,ByteByReference pdevtype,ByteByReference pipmode,IntByReference ptcpport);//获取搜索到的设备
		
		public short ZN_LoginTCP(byte devtype,String szip,int port,String szpwd);
		public short ZN_GetDevConfigTCP(String szname,byte[] szval);
		public short ZN_ModifyDevConfigTCP(String szname,byte[] szval);
		//更改设备信息后需调用此函数复位设备以应用新设置
		public short ZN_ResetDevTCP();
		public short ZN_ExitTCP();
		
		
		public short ZN_SendCmd(byte devtype,String szip,int port,String szname,String szval,byte budp);//发送命令
		
		/*
		  
		DWORD __stdcall ZN_GetDevConfigUDP(char* szname,char* szval);//获取设备配置参数
		DWORD __stdcall ZN_ResetModifyConfigUDP();
		DWORD __stdcall ZN_SetModifyConfigUDP(char* szname,char* szval);
		DWORD __stdcall ZN_ModifyDevUDPbyIP(char* szip,char* szpwd,BYTE devtype);//更改设备配置
		DWORD __stdcall ZN_GetDevInfoUDPbyIP(String szip,int devtype);//获取设备信息
		
	
		

		HANDLE __stdcall ZN_TCPInit(char* szip,int port);
		DWORD __stdcall ZN_GetAD(HANDLE hd,int* padc0,int *padc1);
		DWORD __stdcall ZN_GetIO(HANDLE hd,BYTE* pdirection, BYTE* pelevel);
		DWORD __stdcall ZN_SetIODir(HANDLE hd,BYTE dir);
		DWORD __stdcall ZN_SetIOLevel(HANDLE hd,BYTE level);
		DWORD __stdcall ZN_TCPUninit(HANDLE hd)*/
	}


	
}

