package edu.nuist.qdb.service.ajax.result;

import lombok.Data;

@Data
public class ResultMessage {
	private int code;
	private String msg;
<<<<<<< HEAD

	private Object obj;

	public ResultMessage copy() {
		ResultMessage msg = new ResultMessage();
		msg.setCode(this.code);
		msg.setMsg(this.msg);
		return msg;
	}
}


=======
	
	private Object obj;
	
	public ResultMessage copy() {
		ResultMessage msg = new ResultMessage();
		msg.setCode(this.code);
		msg.setMsg( this.msg );
		return msg;
	}
}
>>>>>>> a7a530f01556a76c37fc3d841bf315bb56a50e21
