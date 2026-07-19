package vn.vnpt.vnptit.ecms.dto.sync.vcenter;


import java.util.List;

public class VCterIpInfoDTO {
    private IpInfoValueDTO value;

    public static class IpInfoValueDTO {
        private FullNameDTO full_name;
        private String name;
        private String ip_address;
        private String family;
        private String host_name;

        public FullNameDTO getFull_name() {
            return full_name;
        }

        public void setFull_name(FullNameDTO full_name) {
            this.full_name = full_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIp_address() {
            return ip_address;
        }

        public void setIp_address(String ip_address) {
            this.ip_address = ip_address;
        }

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public String getHost_name() {
            return host_name;
        }

        public void setHost_name(String host_name) {
            this.host_name = host_name;
        }
    }

    public static class FullNameDTO {
        private List<String> args;
        private String default_message;
        private String id;

        public List<String> getArgs() {
            return args;
        }

        public void setArgs(List<String> args) {
            this.args = args;
        }

        public String getDefault_message() {
            return default_message;
        }

        public void setDefault_message(String default_message) {
            this.default_message = default_message;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public IpInfoValueDTO getValue() {
        return value;
    }

    public void setValue(IpInfoValueDTO value) {
        this.value = value;
    }
}
