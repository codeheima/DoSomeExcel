package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class Dom4jUtil {

	
	
	
	public static Document createByInputStream(InputStream in){
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(in);
			return document;
		}catch(Exception err){
			throw new RuntimeException(err);
		}

	}

	/**
	 * 杩斿洖鎸囧畾鑺傜偣涓嬬壒瀹氳矾寰勭殑鑺傜偣鐨勫睘鎬у�
	 * @param node  璧峰鑺傜偣 
	 * @param xpath  璺緞 
	 * @param attr  灞炴�鍚嶇О
	 * @param defaultVal  榛樿鍊�
	 * @return 灞炴�鍊�
	 */
	public static Integer attrValInteger(Node node, String xpath, String attr, Integer defaultVal) {
		String strVal = attrVal( node,  xpath,  attr,null);
		if("".equals(strVal)){
			return defaultVal;
		}
		return Integer.parseInt(strVal);
	}
	
	/**
	 * 杩斿洖鎸囧畾鑺傜偣涓嬬壒瀹氳矾寰勭殑鑺傜偣鐨勫睘鎬у�
	 * @param node  璧峰鑺傜偣 
	 * @param xpath  璺緞
	 * @param attr  灞炴�鍚嶇О
	 * @param defaultVal  榛樿鍊�
	 * @return 灞炴�鍊�
	 */
	public static String attrVal(Node node, String xpath, String attr, String defaultVal) {
		if(defaultVal == null)
			defaultVal = "";
		Node n = node.selectSingleNode(xpath);
		if(null == n)
			return defaultVal;
		Element e = (Element)n;
		String val = e.attributeValue(attr);
		if(null == val || "".equals(val))
			return defaultVal;
		return val;
	}
	
	public static String textVal(Node node, String xpath, String defaultVal) {
		if(defaultVal == null)
			defaultVal = "";
		Node n = node.selectSingleNode(xpath);
		if(null == n)
			return defaultVal;
		Element e = (Element)n;
		String val = e.getText().trim();
		if(null == val || "".equals(val))
			return defaultVal;
		return val;
	}
	
	
	public static void changeAttrVal(Node node, String xpath, String attr, String val) {
		if(val == null)
			val = "";
		Element  n = (Element)node.selectSingleNode(xpath);
		if(null == n)
			return;
		n.addAttribute(attr, val);
	}
	
	
	/**
	 * 杩斿洖鎸囧畾鑺傜偣涓嬬壒瀹氳矾寰勭殑寰堝鑺傜偣鐨勫睘鎬у�
	 * @param node  璧峰鑺傜偣 
	 * @param xpath  璺緞
	 * @param attr  灞炴�鍚嶇О
	 * @param defaultVal  榛樿鍊�
	 * @return 灞炴�鍊煎垪琛�
	 */
	public static List<String> attrValList(Node node, String xpath, String attr, String innerDefaultVal) {
		if(innerDefaultVal == null)
			innerDefaultVal = "";
		List<String> list = new ArrayList<String>();
		List<Element> eleList =  node.selectNodes(xpath);
		for(int i = 0 ; i< eleList.size(); i++){
			Element e = eleList.get(i);
			String val =e.attributeValue(attr);
			if(null == val || "".equals(val)){
				list.add(innerDefaultVal);
			}else{
				list.add(val);
			}
		}
		return list;
	}
	
	
	public static String getItemVal(Element dataEl, String nameVal) {
		Element el = (Element)dataEl.selectSingleNode("ITEM[@key='"+nameVal +"']");
		if(el ==  null)
			return "";
		String val =  el.attributeValue("val");
		if(val == null)
			return "";
		else
			return val;
		
	}
	

	
	
	
	/**
	 * 鑾峰彇瀛楃涓�
	 * @param node
	 * @return
	 */
	public static String getElStr(Node node){
		StringWriter sw = new StringWriter();
		printNode(node,sw);
		return sw.toString();
	}
	
	
	/**
	 * @param node : Node is parent class of Element or Document
	 * @param out
	 */
	public static void printNode(Node node,Writer out) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		// OutputFormat.createCompactFormat(); //绱у噾鏍煎紡
		// OutputFormat.createPrettyPrint();
	
		//鎸囧畾杈撳嚭娴佷负 PrintWriter
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(out, format);
			writer.write(node);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	

	public static void copyNodes(Element src, Element target, String xpath) {
		List<Element> list = (List<Element>)src.selectNodes(xpath);
		for(Element e : list){
			target.add((Element)e.clone());
		}
	}

	public static void delEl(Element target,String path){
		List<Element> children = target.selectNodes(path);
		if(children == null || children.size() == 0)
			return;
		Element parent = children.get(0).getParent();
		for(Element el: children){
			parent.remove(el);
		}
	}
	
	public static void delAttr(Element el, String attrName) {
		Attribute attr = el.attribute(attrName);
		if(attr != null){
			el.remove(attr);
		}
	}
	public static void delAttr(Element elSrc,String xPath, String attrName) {
		Element el = (Element)elSrc.selectSingleNode(xPath);
		if(el == null)
			return;
		
		Attribute attr = el.attribute(attrName);
		if(attr != null){
			el.remove(attr);
		}
	}

	public static void delAllChild(Element parent) {
		List<Element> list = (List<Element>)parent.elements();
		
		for(Element c : list){
			parent.remove(c);
		}
	}


	public static void findAllChildrenEl(List<Element> parents, String elName,
			List<Element> list) {
		if(parents == null || parents.size() ==0 )
			return;
		for(Element p : parents){
			if(p.getName().equals(elName)){
				list.add(p);
			}
			findAllChildrenEl(p.elements(),elName,list);
		}
		
	}
	

	
	
}
