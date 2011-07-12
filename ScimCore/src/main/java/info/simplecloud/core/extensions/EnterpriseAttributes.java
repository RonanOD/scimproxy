package info.simplecloud.core.extensions;

import info.simplecloud.core.Attribute;
import info.simplecloud.core.coding.handlers.StringHandler;

public class EnterpriseAttributes {

    private String employeeNumber;
    private String costCenter;
    private String organization;
    private String division;
    private String department;
    private String manager;

    @Attribute(schemaName = "employeeNumber", codingHandler = StringHandler.class)
    public String getEmployeeNumber() {
        return this.employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    @Attribute(schemaName = "costCenter", codingHandler = StringHandler.class)
    public String getCostCenter() {
        return this.costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    @Attribute(schemaName = "organization", codingHandler = StringHandler.class)
    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Attribute(schemaName = "division", codingHandler = StringHandler.class)
    public String getDivision() {
        return this.division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Attribute(schemaName = "department", codingHandler = StringHandler.class)
    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Attribute(schemaName = "manager", codingHandler = StringHandler.class)
    public String getManager() {
        return this.manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
