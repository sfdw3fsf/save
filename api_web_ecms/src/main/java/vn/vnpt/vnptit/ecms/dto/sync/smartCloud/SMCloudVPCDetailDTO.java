package vn.vnpt.vnptit.ecms.dto.sync.smartCloud;

import java.util.List;

public class SMCloudVPCDetailDTO {
    private String maTB;
    private Object tocDoId;
    private Object imageID;
    private Object ipPublic;
    private Object volumeType;
    private Integer offerCpu;
    private Integer offerRam;
    private Integer offerHdd;
    private Integer offerSsd;
    private Integer totalCpu;
    private Integer totalRam;
    private Integer totalHdd;
    private Integer totalSsd;
    private Integer totalIP;
    private List<AddonsDTO> addons;
    private String createDate;
    private String expireDate;
    private String status;
    private String serviceStatus;
    private List<ServiceItemsDTO> serviceItems;

    public SMCloudVPCDetailDTO() {
    }
    public SMCloudVPCDetailDTO(String maTB, Object tocDoId, Object imageID, Object ipPublic, Object volumeType,
                               Integer offerCpu, Integer offerRam, Integer offerHdd, Integer offerSsd,
                               Integer totalCpu, Integer totalRam, Integer totalHdd, Integer totalSsd,
                               Integer totalIP, List<AddonsDTO> addons, String createDate, String expireDate,
                               String status, String serviceStatus, List<ServiceItemsDTO> serviceItems) {
        this.maTB = maTB;
        this.tocDoId = tocDoId;
        this.imageID = imageID;
        this.ipPublic = ipPublic;
        this.volumeType = volumeType;
        this.offerCpu = offerCpu;
        this.offerRam = offerRam;
        this.offerHdd = offerHdd;
        this.offerSsd = offerSsd;
        this.totalCpu = totalCpu;
        this.totalRam = totalRam;
        this.totalHdd = totalHdd;
        this.totalSsd = totalSsd;
        this.totalIP = totalIP;
        this.addons = addons;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.status = status;
        this.serviceStatus = serviceStatus;
        this.serviceItems = serviceItems;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public Object getTocDoId() {
        return tocDoId;
    }

    public void setTocDoId(Object tocDoId) {
        this.tocDoId = tocDoId;
    }

    public Object getImageID() {
        return imageID;
    }

    public void setImageID(Object imageID) {
        this.imageID = imageID;
    }

    public Object getIpPublic() {
        return ipPublic;
    }

    public void setIpPublic(Object ipPublic) {
        this.ipPublic = ipPublic;
    }

    public Object getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(Object volumeType) {
        this.volumeType = volumeType;
    }

    public Integer getOfferCpu() {
        return offerCpu;
    }

    public void setOfferCpu(Integer offerCpu) {
        this.offerCpu = offerCpu;
    }

    public Integer getOfferRam() {
        return offerRam;
    }

    public void setOfferRam(Integer offerRam) {
        this.offerRam = offerRam;
    }

    public Integer getOfferHdd() {
        return offerHdd;
    }

    public void setOfferHdd(Integer offerHdd) {
        this.offerHdd = offerHdd;
    }

    public Integer getOfferSsd() {
        return offerSsd;
    }

    public void setOfferSsd(Integer offerSsd) {
        this.offerSsd = offerSsd;
    }

    public Integer getTotalCpu() {
        return totalCpu;
    }

    public void setTotalCpu(Integer totalCpu) {
        this.totalCpu = totalCpu;
    }

    public Integer getTotalRam() {
        return totalRam;
    }

    public void setTotalRam(Integer totalRam) {
        this.totalRam = totalRam;
    }

    public Integer getTotalHdd() {
        return totalHdd;
    }

    public void setTotalHdd(Integer totalHdd) {
        this.totalHdd = totalHdd;
    }

    public Integer getTotalSsd() {
        return totalSsd;
    }

    public void setTotalSsd(Integer totalSsd) {
        this.totalSsd = totalSsd;
    }

    public Integer getTotalIP() {
        return totalIP;
    }

    public void setTotalIP(Integer totalIP) {
        this.totalIP = totalIP;
    }

    public List<AddonsDTO> getAddons() {
        return addons;
    }

    public void setAddons(List<AddonsDTO> addons) {
        this.addons = addons;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public List<ServiceItemsDTO> getServiceItems() {
        return serviceItems;
    }

    public void setServiceItems(List<ServiceItemsDTO> serviceItems) {
        this.serviceItems = serviceItems;
    }

    @Override
    public String toString() {
        return "DetailVpcDto{" +
                "maTB='" + maTB + '\'' +
                ", tocDoId=" + tocDoId +
                ", imageID=" + imageID +
                ", ipPublic=" + ipPublic +
                ", volumeType=" + volumeType +
                ", offerCpu=" + offerCpu +
                ", offerRam=" + offerRam +
                ", offerHdd=" + offerHdd +
                ", offerSsd=" + offerSsd +
                ", totalCpu=" + totalCpu +
                ", totalRam=" + totalRam +
                ", totalHdd=" + totalHdd +
                ", totalSsd=" + totalSsd +
                ", totalIP=" + totalIP +
                ", addons=" + addons +
                ", createDate='" + createDate + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", status='" + status + '\'' +
                ", serviceStatus='" + serviceStatus + '\'' +
                ", serviceItems=" + serviceItems +
                '}';
    }

    // ======================== AddonsDTO ========================

    public static class AddonsDTO {
        private Integer id;
        private Object name;
        private String addonCode;
        private Integer quantity;

        public AddonsDTO() {
        }

        public AddonsDTO(Integer id, Object name, String addonCode, Integer quantity) {
            this.id = id;
            this.name = name;
            this.addonCode = addonCode;
            this.quantity = quantity;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public String getAddonCode() {
            return addonCode;
        }

        public void setAddonCode(String addonCode) {
            this.addonCode = addonCode;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "AddonsDTO{" +
                    "id=" + id +
                    ", name=" + name +
                    ", addonCode='" + addonCode + '\'' +
                    ", quantity=" + quantity +
                    '}';
        }
    }

    // ======================== ServiceItemsDTO ========================

    public static class ServiceItemsDTO {
        private Object maTB;
        private String serviceCode;
        private Integer id;
        private Integer serviceType;
        private Object projectId;
        private String status;
        private String serviceName;
        private String createDate;
        private String expiredDate;
        private Object suspendDate;
        private Object deleteDate;
        private List<DetailsDTO> details;

        public ServiceItemsDTO() {
        }

        public ServiceItemsDTO(Object maTB, String serviceCode, Integer id, Integer serviceType,
                Object projectId, String status, String serviceName, String createDate,
                String expiredDate, Object suspendDate, Object deleteDate,
                List<DetailsDTO> details) {
            this.maTB = maTB;
            this.serviceCode = serviceCode;
            this.id = id;
            this.serviceType = serviceType;
            this.projectId = projectId;
            this.status = status;
            this.serviceName = serviceName;
            this.createDate = createDate;
            this.expiredDate = expiredDate;
            this.suspendDate = suspendDate;
            this.deleteDate = deleteDate;
            this.details = details;
        }

        public Object getMaTB() {
            return maTB;
        }

        public void setMaTB(Object maTB) {
            this.maTB = maTB;
        }

        public String getServiceCode() {
            return serviceCode;
        }

        public void setServiceCode(String serviceCode) {
            this.serviceCode = serviceCode;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getServiceType() {
            return serviceType;
        }

        public void setServiceType(Integer serviceType) {
            this.serviceType = serviceType;
        }

        public Object getProjectId() {
            return projectId;
        }

        public void setProjectId(Object projectId) {
            this.projectId = projectId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getExpiredDate() {
            return expiredDate;
        }

        public void setExpiredDate(String expiredDate) {
            this.expiredDate = expiredDate;
        }

        public Object getSuspendDate() {
            return suspendDate;
        }

        public void setSuspendDate(Object suspendDate) {
            this.suspendDate = suspendDate;
        }

        public Object getDeleteDate() {
            return deleteDate;
        }

        public void setDeleteDate(Object deleteDate) {
            this.deleteDate = deleteDate;
        }

        public List<DetailsDTO> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsDTO> details) {
            this.details = details;
        }

        @Override
        public String toString() {
            return "ServiceItemsDTO{" +
                    "maTB=" + maTB +
                    ", serviceCode='" + serviceCode + '\'' +
                    ", id=" + id +
                    ", serviceType=" + serviceType +
                    ", projectId=" + projectId +
                    ", status='" + status + '\'' +
                    ", serviceName='" + serviceName + '\'' +
                    ", createDate='" + createDate + '\'' +
                    ", expiredDate='" + expiredDate + '\'' +
                    ", suspendDate=" + suspendDate +
                    ", deleteDate=" + deleteDate +
                    ", details=" + details +
                    '}';
        }

        // ======================== DetailsDTO ========================

        public static class DetailsDTO {
            private Object id;
            private String name;
            private String unit;
            private Integer quantity;

            public DetailsDTO() {
            }

            public DetailsDTO(Object id, String name, String unit, Integer quantity) {
                this.id = id;
                this.name = name;
                this.unit = unit;
                this.quantity = quantity;
            }

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public Integer getQuantity() {
                return quantity;
            }

            public void setQuantity(Integer quantity) {
                this.quantity = quantity;
            }

            @Override
            public String toString() {
                return "DetailsDTO{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", unit='" + unit + '\'' +
                        ", quantity=" + quantity +
                        '}';
            }
        }
    }
}
