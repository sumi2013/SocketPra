package com.sincered.pcr.device;

/**网口命令
 * 
 * 命令默认大写
 * 
 * @author Administrator
 *
 */
public class NetworkPortCommand {
	//命令首部
	// byte[] COMMAND_HEAD;
	//命令尾部  //\r\n 7E
	//byte[] COMMAND_TAIL;
	static Command cmd = new Command();
	
	
	
	//#以下为 工作状态查询及控制协议#-----------------------------	
	/**工作状态查询命令
	 * 
	 * @return
	 *  \0x16 + "DW4D",BLOCK,ON,30.4,81.2,80.6,82.0,72.9,1,2,30,11 + \0x0D + \0x0A
		\0x16:A200 \0x18:A300
		"DW4D"程序名称【下载时候的名字】
		BLOCK运行模式【BLOCK、CALC】
		ON运行状态【ON，OFF，PAU，E01，E02，E03，E04】
		30.4热盖温度
		81.2左路温度【BLOCK：左路样品台温度；CALC：左路样品温度】
		80.6中路温度【中路样品台温度】
		82.0右路温度【BLOCK：右路样品台温度；CALC：右路样品温度】
		72.9样品温度【中路样品温度】
		1反应到第几步骤了
		2反应到第几个周期了
		30当前步骤运行了多久时间（剩余多久时间）
		11当前样品台的型号识别位（11代表9677模块）【老的命令中此位为预测结束时间】此数据没有
		
		实际检测数据:
		"test122",CALC,ON,32.6,94.2,91.5,94.0,94.2,1,4,180,\#2\#13\#10	
		       "test122".....						  ,\#2\#13\#10	
		16 22746573743....42E392C322C322C37332C 020D0A
	 *
	 */
	public final static Command STATUS(){
		
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x29,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="STATUS?";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	};
	
	/**程序停止运行命令	『下位机不回传上位机』
	 * 
	 * @return
	 */
	public final static Command CANCEL(){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x29,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="CANCEL";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	//程序暂停运行命令		『下位机不回传上位机』
	public final static Command PAUSE(){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x28,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="PAUSE";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	//恢复暂停程序运行命令	『下位机不回传上位机』
	public final static Command RESUME(){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x29,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="RESUME";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}

	//跳过此步骤程序运行命令	『下位机不回传上位机』
	public final static Command PROSEED(){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x2A,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="PROSEED";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**#以下为 用户程序下载协议#----------------------------------
		 本部分的协议要求上位机发送一条命令后，下位机回传收到的命令用于校验，上位机在收到数据后发送OK表示确认，下位机收到OK后，则刚才的通讯语句有效，例如：
		过程N：
		上位机 发送：		TEMP 95.0,30 + \0x0D + \0x0A
		下位机 收到后回传：	TEMP 95.0,30 + \0x0D + \0x0A
		上位机 收到后确认：	OK + \0x0D + \0x0A
		这样的过程为完成一条下载语句。
	 *
	 */

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
	
	/**运行模式命令
	 * 
	 * @param model 模式
	 * CALC/BLOCK
	 * @return
	 */
	public final static Command METHOD(String model){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x2D,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="METHOD "+model;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**梯度步骤命令
	 * 
	 * @param Ltem  左列温度    精确小数点后一位
	 * @param Rtem	右列温度    精确小数点后一位
	 * @param sec	时间	       单位:秒
	 * @return
	 */
	public final static Command GRAD(float Ltem,float Rtem,int sec){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x33,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="GRAD "+Ltem+","+Rtem+","+sec;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**恒温步骤命令
	 * 
	 * @param tem	恒温温度  精确小数点后一位
	 * @param sec	时间	     单位:秒
	 * @return
	 */
	public final static Command TEMP(float tem,int sec){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x2E,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="TEMP  "+tem+","+sec;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**跳转步骤命令
	 * 
	 * @param step   跳转步骤 1~30
	 * @param num	   跳转次数 1~100
	 * @return
	 */
	public final static Command GOTO(int step,int num){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x2A,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="GOTO "+step+","+num;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}

	/**结束编程命令
	 * 
	 * @return
	 */
	public final static Command END(){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x25,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="END";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}

	/**热盖编程命令
	 * 
	 * @param state	热盖状态：CONSTANT/OFF
	 * @param Temp	热盖温度 	范围：0.0 ～ 112.0
	 * @param closeTemp	热盖自动关闭温度	范围：0.0 ～ 112.0
	 * @return
	 */
	public final static Command HOTLID(String state,float Temp,float closeTemp){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x3D,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="HOTLID \""+state+"\","+Temp+","+closeTemp;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}

	/**热盖关盖时压力设置命令
	 * 
	 * @param value	压力值	 范围:0
	 * @return
	 */
	public final static Command LIDFORCE(int value){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x2C,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="LIDFORCE "+value;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**热盖角度设置命令
	 * 
	 * @param value 压力值		范围：10
	 * @return
	 */
	public final static Command LIDANGLE(int value){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x2C,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="LIDANGLE "+value;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**试验溶液体积命令
	 * 
	 * @param vol	液体体积	单位：uL	范围：0 ～ 100
	 * @param size	管材		范围：50 ～ 200
	 * @param flag	标志位		1
	 * @return
	 */
	public final static Command VOLUME(int vol,int size,int flag){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x31,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="VOLUME "+vol+","+size+","+flag;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**步骤速率命令
	 * 
	 * @param rate	速率值		范围：0.0 ～ 5.0
	 * @return
	 */
	public final static Command RATE(float rate){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x2A,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="RATE "+rate;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**步骤温度循环递变命令
	 * 
	 * @param inc	递变值		范围：-9.9 ～ 9.9
	 * @return
	 */
	public final static Command INC(float inc){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x29,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="INC "+inc;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**步骤时间循环递变命令
	 * 
	 * @param ext	递变值		范围：-1000 ～ 1000
	 * @return
	 */
	public final static Command EXT(int ext){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x27,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="EXT "+ext;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**步骤自动暂停命令
	 * 
	 * @return
	 */
	public final static Command HLD(){
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x26,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="HLD";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**收到命令正确命令 ok『下位机不再回传上位机』
	 * 
	 * @return
	 */
	public final static Command OK(){	
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x26,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="OK";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**运行程序命令『下位机不再回传上位机』
	 * 
	 * @param name 名称
	 * @return
	 */
	public final static Command RUN(String name){	
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x35,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="RUN \""+name+"\",TEST,ON";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	//#以下为 校准及其他辅助协议#----------------------------------
	/**温浴不开热盖程序命令
	 * 
	 * @param temp	温度	范围：0.0 – 99.9
	 * @return
	 */
	public final static Command INCUBATE(float temp){	
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x2F,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="INCUBATE "+temp;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**温浴开热盖程序命令
	 * 
	 * @param temp	温度	范围：0.0 – 99.9
	 * @return
	 */
	public final static Command INCUBATEHOT(float temp){	
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x32,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="INCUBATEHOT "+temp;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**获取通道参数配置命令
	 * 
	 * @param value	样品台通道	范围：1 - 3
	 * @return
	 * 下位机返回：
		!95.0,40.0,800,8,0,18 + \0x0D + \0x0A
		95.0 95度校准值
		40.0 40度校准值
		800	P参数
		8	I参数
		0	D参数
		18	AME参数
	 *
	 */
	public final static Command GETREV(int value){	
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x2A,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="GETREV "+value;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**获取样品台参数及运行状态命令
	 * 
	 * @return
	 * 下位机回传：
		“0,34.8,35.0,34.9,0,0,0,27.1 + \0x0D + \0x0A
		34.8：左路样品台温度
		35.0：中路样品台温度
		34.9：右路样品台温度
		27.1：热盖温度
	 *
	 */
	public final static Command REVTEMP(){	
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x2A,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="REVTEMP?";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**运行调试程序命令
	 * 
	 * @param Ltemp	左路温度	范围：0.0 – 99.9
	 * @param Mtemp 中路温度	范围：0.0 – 99.9
	 * @param Rtemp 右路温度	范围：0.0 – 99.9
	 * @return
	 */
	public final static Command SETBLKTEMP(float Ltemp,float Mtemp,float Rtemp){	
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x3B,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="SETBLKTEMP "+Ltemp+","+Mtemp+","+Rtemp;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**设定传感器校温参数命令
	 * 
	 * @param value		通道	范围：1 – 3
	 * @param _95temp	95度校准温度   温度范围：0.0 – 99.9
	 * @param _40temp	40度校准温度   温度范围：0.0 – 99.9
	 * @return
	 */
	public final static Command SETSENPAR(int value,float _95temp,float _40temp){	
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x37,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="SETBLKTEMP "+value+","+_95temp+","+_40temp;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**设定PID系数命令
	 * 
	 * @param value	通道	范围：1 – 3
	 * @param P		P参数	参数范围：0 –9999
	 * @param I 	I参数	参数范围：0 –9999
	 * @param D 	D参数	参数范围：0 –9999
	 * @param AME 	AME参数	参数范围：0 –9999
	 * @return
	 */
	public final static Command SETPTPAR(int value,int P,int I,int D,int AME){	
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x37,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="SETPTPAR "+value+","+P+","+I+","+D+","+AME;
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**关闭样品台结束调试程序命令
	 * 
	 * @return
	 */
	public final static Command BLKCLOSE(){	
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x2B,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="BLKCLOSE";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
	/**模式传输数据命令
	 * 本命令发送后只有重启仪器可以恢复
	 * @return
	 */
	public final static Command DISBLOCK(){	
		byte[] COMMAND_HEAD={0x7E,(byte) 0xA5,0x7D,0x20,0x7D,0x2B,0x30,0x7D,0x20,0x25,(byte) 0x80};
		String COMMAND="DISBLOCK";
		byte[] COMMAND_TAIL={0x7D,0x2D,0x7D,0x2A,0x7E};
		
		cmd.setCOMMAND_HEAD(COMMAND_HEAD);
		cmd.setCOMMAND(COMMAND);
		cmd.setCOMMAND_TAIL(COMMAND_TAIL);		
		return cmd;		
	}
	
}
