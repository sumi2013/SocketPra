package com.sincered.pcr.device;

public class NetworkPortCommand {
	//命令首部
	// byte[] COMMAND_HEAD;
	//命令尾部  //\r\n 7E
	//byte[] COMMAND_TAIL;
	static Command cmd = new Command();
	
	
	//#以下为 工作状态查询及控制协议#-----------------------------	
	//工作状态查询命令
	public final static Command STATUS(){
		
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x29,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="STATUS?";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	};
	
	//程序停止运行命令
	public final static Command CANCEL(){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x29,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="CANCEL";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	//程序暂停运行命令
	public final static Command PAUSE(){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x28,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="PAUSE";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	//恢复暂停程序运行命令
	public final static Command RESUME(){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x29,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="RESUME";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}

	//跳过此步骤程序运行命令
	public final static Command PROSEED(){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x2A,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="PROSEED";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	//#以下为 用户程序下载协议#----------------------------------
	/**编程起始命令
	 * 
	 * 
	 * @param name  项目名称
	 * @return
	 */
	
	public final static Command PROGRAM(String name){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x31,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="PROGRAM \""+name+"\"";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
}
