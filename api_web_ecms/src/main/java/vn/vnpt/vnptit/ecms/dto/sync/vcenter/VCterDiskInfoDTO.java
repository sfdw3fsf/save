package vn.vnpt.vnptit.ecms.dto.sync.vcenter;

public class VCterDiskInfoDTO {
    private DiskInfoValueDTO value;

    public static class DiskInfoValueDTO {
        private ScsiDTO scsi;
        private BackingDTO backing;
        private String label;
        private String type;
        private Long capacity;

        public ScsiDTO getScsi() {
            return scsi;
        }

        public void setScsi(ScsiDTO scsi) {
            this.scsi = scsi;
        }

        public BackingDTO getBacking() {
            return backing;
        }

        public void setBacking(BackingDTO backing) {
            this.backing = backing;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getCapacity() {
            return capacity;
        }

        public void setCapacity(Long capacity) {
            this.capacity = capacity;
        }
    }

    public static class ScsiDTO {
        private Integer bus;
        private Integer unit;

        public Integer getBus() {
            return bus;
        }

        public void setBus(Integer bus) {
            this.bus = bus;
        }

        public Integer getUnit() {
            return unit;
        }

        public void setUnit(Integer unit) {
            this.unit = unit;
        }
    }

    public static class BackingDTO {
        private String vmdk_file;
        private String type;

        public String getVmdk_file() {
            return vmdk_file;
        }

        public void setVmdk_file(String vmdk_file) {
            this.vmdk_file = vmdk_file;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
