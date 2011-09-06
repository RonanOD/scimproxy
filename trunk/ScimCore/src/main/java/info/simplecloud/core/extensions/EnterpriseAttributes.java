package info.simplecloud.core.extensions;

import x0.scimSchemasExtensionEnterprise1.CostCenterDocument;
import x0.scimSchemasExtensionEnterprise1.DepartmentDocument;
import x0.scimSchemasExtensionEnterprise1.DivisionDocument;
import x0.scimSchemasExtensionEnterprise1.EmployeeNumberDocument;
import x0.scimSchemasExtensionEnterprise1.ManagerDocument;
import x0.scimSchemasExtensionEnterprise1.OrganizationDocument;
import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.annotations.Extension;
import info.simplecloud.core.extensions.types.Manager;
import info.simplecloud.core.handlers.ComplexHandler;
import info.simplecloud.core.handlers.StringHandler;

@Extension(schema = "urn:scim:schemas:extension:enterprise:1.0")
public class EnterpriseAttributes {

    private String  employeeNumber;
    private String  costCenter;
    private String  organization;
    private String  division;
    private String  department;
    private Manager manager;

    @Attribute(name = "employeeNumber", handler = StringHandler.class, xmlDoc = EmployeeNumberDocument.class)
    public String getEmployeeNumber() {
        return this.employeeNumber;
    }

    @Attribute(name = "costCenter", handler = StringHandler.class, xmlDoc = CostCenterDocument.class)
    public String getCostCenter() {
        return this.costCenter;
    }

    @Attribute(name = "organization", handler = StringHandler.class, xmlDoc = OrganizationDocument.class)
    public String getOrganization() {
        return this.organization;
    }

    @Attribute(name = "division", handler = StringHandler.class, xmlDoc = DivisionDocument.class)
    public String getDivision() {
        return this.division;
    }

    @Attribute(name = "department", handler = StringHandler.class, xmlDoc = DepartmentDocument.class)
    public String getDepartment() {
        return this.department;
    }

    @Attribute(name = "manager", handler = ComplexHandler.class, type = Manager.class, xmlDoc = ManagerDocument.class)
    public Manager getManager() {
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

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
