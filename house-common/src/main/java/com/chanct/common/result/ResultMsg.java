package com.chanct.common.result;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

public class ResultMsg {
	public static final String errorMsgKey = "errorMsg";
	public static final String successMsgKey = "successMsg";
	private String errorMsg;
	private String successMsg;
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getSuccessMsg() {
		return successMsg;
	}
	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}
	
	public boolean isSuccess() {
		return errorMsg == null;
	}
	
	//构建错误对象
	public static ResultMsg errorMsg(String msg) {
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setErrorMsg(msg);
		return resultMsg;
	}
	
	//构建成功对象
	public static ResultMsg successMsg(String msg) {
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setSuccessMsg(msg);
		return resultMsg;
	}
	
	//将返回结果转换成map对象
	public Map<String,String> asMap(){
		Map<String,String> map = Maps.newHashMap();//使用guava工具
		map.put(successMsgKey, successMsg);
		map.put(errorMsgKey, errorMsg);
		return map;
		
	}
	
	//将结果信息转化成url连接的参数   www.baidu.com?k=1&v=2
	public String asUrlParams() {
		Map<String,String> map = asMap();
		Map<String,String> newMap = Maps.newHashMap();
		map.forEach((k,v) -> 
		{if(v!=null)
			try {
				newMap.put(k,URLEncoder.encode(v,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
			}
		});
		return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
	}
}
