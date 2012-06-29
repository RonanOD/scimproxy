package info.simplecloud.scimproxy.compliance;



public class Result {

	public static int WARNING = 1;
	public static int ERROR = 2;
	public static int CRITICAL = 3;
	public static int SUCCESS = 4;
	
	private String name = "";
	private String message = "";
	private int status = 0;
	private String wire = "";
	
	public Result(int status, String name, String message, String wire) {
		this.status = status;
		this.name = name;
		this.message = message;
		this.setWire(wire);
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return this.status;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	

	public void setWire(String wire) {
		this.wire = wire;
	}

	public String getWire() {
		return wire;
	}


	public String toString() {
		return "status=" + status + ", name=" + name + ", message=" + message;
	}
	
	public String toJson() {
		return "{\n" + 	
					"\"status\":" + status + ",\n" +
					"\"name\":\"" + name + "\",\n" +
					"\"message\":\"" + message + "\",\n" +
					"\"wire\":\"" + wire + "\"\n" +
				"}";
	}
}
