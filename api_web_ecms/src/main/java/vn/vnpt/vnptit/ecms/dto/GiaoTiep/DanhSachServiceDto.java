package vn.vnpt.vnptit.ecms.dto.GiaoTiep;

public class DanhSachServiceDto {
    private Long typeOLT;
    private Long serviceId;
    private String serviceName;
    private String serviceCommand;
    private Long checkONT;

    public Long getTypeOLT() {
        return typeOLT;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceCommand() {
        return serviceCommand;
    }

    public Long getCheckONT() {
        return checkONT;
    }
}
