package info.simplecloud.scimproxy.compliance.enteties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestResult {

	public static int WARNING     = 1;
    public static int ERROR       = 2;
    public static int CRITICAL    = 3;
    public static int SUCCESS     = 4;
    public static int SKIPPED     = 5;

    @XmlElement(name = "name")
    String            name        = "";

    @XmlElement(name = "message")
    String            message     = "";

    @XmlElement(name = "status_text")
    String            statusText  = "Failed";

    @XmlElement(name = "status_label")
    String            statusLabel = "label-important";

    @XmlElement(name = "wire")
    String            wire        = "";

    public TestResult() {

    }

    public TestResult(int status, String name, String message, String wire) {
        this.name = name;
        this.message = message;
        this.wire = wire;

        if (status == SUCCESS) {
        	this.statusText = "Success";
        	this.statusLabel = "label-success";
        } else if (status == SKIPPED) {
        	this.statusText = "Skipped";
        	this.statusLabel = "label-info";
        } else {
        	this.statusText = "Failed";
        	this.statusLabel = "label-important";
        }
        	
    }

    public boolean isSuccess() {
        return "Success".equals(this.statusText);
    }

	public boolean isSkipped() {
		return "Skipped".equals(this.statusText);
	}
}
