package info.simplecloud.scimproxy.compliance.enteties;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Result {

    @XmlElement(name = "authRequired")
    private boolean          authRequired = false;

    @XmlElement(name = "authMethods")
    private List<String>     authMethods  = new ArrayList<String>();

    @XmlElement(name = "results")
    private List<TestResult> results      = new ArrayList<TestResult>();

    @XmlElement(name = "statistics")
    private Statistics       statistics;

    public Result() {
    }

    public Result(Statistics statistics, List<TestResult> results, boolean authRequired, String... authMethods) {
        this.statistics = statistics;
        this.authRequired = authRequired;
        this.results = results;
        for (String authMethod : authMethods) {
            this.authMethods.add(authMethod);
        }
    }

}
