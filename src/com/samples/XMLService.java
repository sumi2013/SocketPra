package com.samples;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;



/**XML的服务类
 * 
 * @author Administrator
 *
 */
public class XMLService {
	
	private Logger logger = Logger.getLogger(XMLService.class);
	
	private SimpleDateFormat format;
	private FileWriter fileWriter;
	//PROGRAMXML文件夹路径
	private static String programXMLPath = System.getProperty("user.dir")+ "\\PROGRAMXML\\";
	//DATAXML文件夹路径
	private static String dataXMLPath =  System.getProperty("user.dir")+ "\\DATAXML\\";

	public XMLService() {
		 format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	}
	
	/**项目绝对与路径
	 * 
	 * @return like:D:\workspace\SocketExample
	 */
	public String getAbsolutePathWithProject(){
		return System.getProperty("user.dir");
	}

	/**创建实验编程Document,即生成一个包含program命令组的document
	 * #一致性
	 * @param map	实验者trier，实验名称title，实验时间date 格式yyyy-MM-dd HH:mm:ss 
	 * @param cmdlist	实验命令列表
	 * @return
	 */	
	public Document createProgramDocument(Map map,List cmdlist) {
        Document document = DocumentHelper.createDocument();
        Element program = document.addElement( "program" );
        program.addAttribute("trier", map.get("trier").toString())
        	   .addAttribute("title", map.get("title").toString())
        	   .addAttribute("date", map.get("date").toString());
        
        Iterator it = cmdlist.iterator();
        while(it.hasNext()){
        	 Element cmd = program.addElement( "cmd" )           
        	            .addText( it.next().toString() );
        }
        return document;
    }
	
	
	public Document createDataDocument(List<Map> datalist){
		Document document = DocumentHelper.createDocument();
		return document;
	}
	
	/**将文档写到文件
	 * 
	 * Writing a document to a file
	 * 
	 * @param document
	 * @return
	 * @throws IOException
	 */
    public boolean writeToFile(Document document){
    	
    	String fileName = createFileName(document);
    	if(fileName==null)return false;
    	
    	// 格式化输出
    	 OutputFormat format = OutputFormat.createPrettyPrint();
    	// 压缩化输出
        //OutputFormat format = OutputFormat.createCompactFormat();
    	try { 
    		
    		XMLWriter writer;	
    		fileWriter = new FileWriter( programXMLPath + fileName);
			writer = new XMLWriter(fileWriter,format);
			writer.write(document);
		    writer.close();
		} catch (IOException e) {
			logger.warn("Writing a document to a file failure!");
			return false;
		}
      
    	logger.info("Writing a document to a file successfule!");
      
        return true;
    }
    
    /**生成文件名
     * 
     * @param document
     * @return   如: fileName.xml
     */
    private String createFileName(Document document){
    	Element el = document.getRootElement();
    	
    	String fileName = null;    	
		try {
			Date d;
			d = format.parse(el.attributeValue("date"));
			format.applyPattern("yyyy-MM-dd-HHmmss");
			fileName = el.attributeValue("trier") + "_" 
    				+ el.attributeValue("title") + "_"
    				+ format.format(d) + ".xml";
			logger.info("parse date successful!");
		} catch (ParseException e) {
			logger.warn("parse date failure!");
		} finally{
			format.applyPattern("yyyy-MM-dd HH:mm:ss");
		}
    	
    	
    	return fileName;
    }

}
