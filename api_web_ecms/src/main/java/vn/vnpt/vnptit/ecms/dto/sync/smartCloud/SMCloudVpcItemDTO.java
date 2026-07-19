package vn.vnpt.vnptit.ecms.dto.sync.smartCloud;

public class SMCloudVpcItemDTO {
    private Long id;
    private Integer serviceType;
    private String serviceCode;
    private String maTB;
    private String name;
    private String status;
    private String email;
    private Long projectId;
    private Long customerId;

    public SMCloudVpcItemDTO() {
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getServiceType() { return serviceType; }
    public void setServiceType(Integer serviceType) { this.serviceType = serviceType; }

    public String getServiceCode() { return serviceCode; }
    public void setServiceCode(String serviceCode) { this.serviceCode = serviceCode; }

    public String getMaTB() { return maTB; }
    public void setMaTB(String maTB) { this.maTB = maTB; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
}
