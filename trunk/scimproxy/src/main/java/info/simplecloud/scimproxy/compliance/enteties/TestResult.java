package info.simplecloud.scimproxy.compliance.enteties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class TestResult {

	public static int WARNING = 1;
	public static int ERROR = 2;
	public static int CRITICAL = 3;
	public static int SUCCESS = 4;
	
	@XmlElement(name = "name")
	private String name = "";
	
	@XmlElement(name = "message")
	private String message = "";
	
	private int status = 0;
	
	@XmlElement(name = "status_text")
    private String statusText = "Failed";
	
	@XmlElement(name = "status_label")
    private String statusLabel = "label-important";
	
	@XmlElement(name = "wire")
	private String wire = "";
	
	public TestResult(){
	    
	}
	
	public TestResult(int status, String name, String message, String wire) {
		this.status = status;
		this.name = name;
		this.message = message;
		this.wire = wire;
		
		this.statusText = (status==SUCCESS ? "Success" : "Failed");
		this.statusLabel = (status==SUCCESS ? "label-success" : "label-important");
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

    public int getStatus() {
        return this.status;
    }
}
