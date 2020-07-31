package com.jeefw.fastpdf.core.document.helper;

import java.util.Map;

import com.jeefw.fastpdf.core.document.elements.ItextXMLElement;
import com.jeefw.fastpdf.core.utils.DimensionUtils;


 /**
 * @package com.jeefw.fastpdf.core.document.helper
 * @className: DimensionHelper
 * @description: TODO
 * @author : <a href="mailto:hnxyhcwdl1003@163.com">wandalong</a>
 * @date : 2014-1-22
 * @time : 下午2:15:45 
 */
public final class DimensionHelper {
	
	private static DimensionHelper instance = null;
	private DimensionHelper(){
	}
	
	public static DimensionHelper getInstance(){
		instance = instance==null?new DimensionHelper():instance;
		return instance;
	}
	
	public double getPercent(ItextXMLElement element,String css_key){
		return this.getPercent(element.styles(), css_key);
	}
	
	public double getPercent(Map<String,Object> styles,String css_key){
		Object percent = styles.get(css_key);
		if(percent instanceof String && percent.toString().indexOf("%")>-1){
			return DimensionUtils.unitParse(percent.toString());
		}else{
			return 0;
		}
	}
	
	public double getFixed(ItextXMLElement element,String css_key){
		return this.getPercent(element.styles(), css_key);
	}
	
	public double getFixed(Map<String,Object> styles,String css_key){
		Object percent = styles.get(css_key);
		if(percent instanceof String){
			return DimensionUtils.unitParse(percent.toString());
		}else{
			return 0;
		}
	}
	
}



