package edu.nuist.qdb.service.ajax.result;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Configuration
@Component
@PropertySource(value = {"classpath:/application.yml"} )
@ConfigurationProperties(prefix = "resultmap")
@Data
public class ResultMap {
	private List<Result> results;
	private HashMap<String , LinkedList<Result> > map = new  HashMap<>();
	
//	@PostConstruct
	public void reS() {
		for(Result r : this.results) {
			if( map.get(r.getFlow()) == null) {
				LinkedList<Result> tmp = new LinkedList<Result>();
				tmp.add(r);
				map.put(r.getFlow(), tmp);
			}else {
				map.get(r.getFlow()).add(r);
			}
		}
	}
	
	public ResultMessage getRstMsg(String flow, String action, String flag) {
//		LinkedList<Result> results = map.get(flow);
//		ResultMessage rst = null;
//		for(Result r : results) {
//			if(r.getAction().equals(action)) {
//				return r.getMessage(flag);
//			}
//		}
		return null;
	}
}	
