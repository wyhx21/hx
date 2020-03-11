package org.layz.hx.core.pojo.response;

public class JsonObjectResponse extends JsonResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9072784240862811742L;
	private Object data;
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
}
