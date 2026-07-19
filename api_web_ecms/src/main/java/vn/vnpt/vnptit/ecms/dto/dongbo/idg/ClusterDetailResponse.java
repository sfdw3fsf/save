package vn.vnpt.vnptit.ecms.dto.dongbo.idg;

import java.util.List;

public class ClusterDetailResponse {
    private int code;
    private String error_msg;
    private String msg;
    private ClusterDetailData data;

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getError_msg() { return error_msg; }
    public void setError_msg(String error_msg) { this.error_msg = error_msg; }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }

    public ClusterDetailData getData() { return data; }
    public void setData(ClusterDetailData data) { this.data = data; }

    public static class ClusterDetailData {
        private String cluster_id;
        private List<ClusterNode> nodes;
        private List<ServiceType> service_types;

        public String getCluster_id() { return cluster_id; }
        public void setCluster_id(String cluster_id) { this.cluster_id = cluster_id; }

        public List<ClusterNode> getNodes() { return nodes; }
        public void setNodes(List<ClusterNode> nodes) { this.nodes = nodes; }

        public List<ServiceType> getService_types() { return service_types; }
        public void setService_types(List<ServiceType> service_types) { this.service_types = service_types; }
    }

    public static class ClusterNode {
        private String ip_address;

        public String getIp_address() { return ip_address; }
        public void setIp_address(String ip_address) { this.ip_address = ip_address; }
    }

    public static class ServiceType {
        private String service_type_name;

        public String getService_type_name() { return service_type_name; }
        public void setService_type_name(String service_type_name) { this.service_type_name = service_type_name; }
    }
}
