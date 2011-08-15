package info.simplecloud.core.extensions;

import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.annotations.Extension;
import info.simplecloud.core.handlers.StringHandler;

@Extension(schema = "enterprise")
public class EnterpriseAttributes {

    private String employeeNumber;
    private String costCenter;
    private String organization;
    private String division;
    private String department;
    private String manager;

    @Attribute(name = "employeeNumber", handler = StringHandler.class)
    public String getEmployeeNumber() {
        return this.employeeNumber;
    }

    @Attribute(name = "costCenter", handler = StringHandler.class)
    public String getCostCenter() {
        return this.costCenter;
    }

    @Attribute(name = "organization", handler = StringHandler.class)
    public String getOrganization() {
        return this.organization;
    }

    @Attribute(name = "division", handler = StringHandler.class)
    public String getDivision() {
        return this.division;
    }

    @Attribute(name = "department", handler = StringHandler.class)
    public String getDepartment() {
        return this.department;
    }

    @Attribute(name = "manager", handler = StringHandler.class)
    public String getManager() {
        return this.manager;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
