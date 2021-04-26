package edu.nuist.qdb.service.ajax.result;

import java.util.Map;

import lombok.Data;

@Data
public class Result {
	private String flow;
	private String action;
	private Map<String, ResultMessage> results;
	
	public Result() {}
	
	public ResultMessage getMessage(String flag) {
		return results.get(flag);
	}
}

