package vn.vnpt.vnptit.ecms.dto.dongbo.idg;

import java.util.List;

public class ClusterSearchResponse {
    private int code;
    private String error_msg;
    private String msg;
    private ClusterResponseData data;

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getError_msg() { return error_msg; }
    public void setError_msg(String error_msg) { this.error_msg = error_msg; }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }

    public ClusterResponseData getData() { return data; }
    public void setData(ClusterResponseData data) { this.data = data; }

    public static class ClusterResponseData {
        private int total;
        private int offset;
        private int limit;
        private List<ClusterData> content;

        public int getTotal() { return total; }
        public void setTotal(int total) { this.total = total; }

        public int getOffset() { return offset; }
        public void setOffset(int offset) { this.offset = offset; }

        public int getLimit() { return limit; }
        public void setLimit(int limit) { this.limit = limit; }

        public List<ClusterData> getContent() { return content; }
        public void setContent(List<ClusterData> content) { this.content = content; }
    }

    public static class ClusterData {
        private String cluster_id;
        private String cluster_name;
        private String rancher_code;
        private String rancher_name;
        private Integer rancher_type;
        private String cluster_description;
        private String region_id;
        private String region_name;
        private String state;
        private Double used_percent_pods;
        private Double used_percent_memory;
        private Double used_percent_cpu;
        private Double allocatable_cpu;
        private Double allocatable_memory;
        private Double used_cpu;
        private Double used_memory;
        private Double limit_cpu;
        private Double limit_memory;
        private String service_type_avatar_paths;

        // Getters and Setters
        public String getCluster_id() { return cluster_id; }
        public void setCluster_id(String cluster_id) { this.cluster_id = cluster_id; }

        public String getCluster_name() { return cluster_name; }
        public void setCluster_name(String cluster_name) { this.cluster_name = cluster_name; }

        public String getRancher_code() { return rancher_code; }
        public void setRancher_code(String rancher_code) { this.rancher_code = rancher_code; }

        public String getRancher_name() { return rancher_name; }
        public void setRancher_name(String rancher_name) { this.rancher_name = rancher_name; }

        public Integer getRancher_type() { return rancher_type; }
        public void setRancher_type(Integer rancher_type) { this.rancher_type = rancher_type; }

        public String getCluster_description() { return cluster_description; }
        public void setCluster_description(String cluster_description) { this.cluster_description = cluster_description; }

        public String getRegion_id() { return region_id; }
        public void setRegion_id(String region_id) { this.region_id = region_id; }

        public String getRegion_name() { return region_name; }
        public void setRegion_name(String region_name) { this.region_name = region_name; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public Double getUsed_percent_pods() { return used_percent_pods; }
        public void setUsed_percent_pods(Double used_percent_pods) { this.used_percent_pods = used_percent_pods; }

        public Double getUsed_percent_memory() { return used_percent_memory; }
        public void setUsed_percent_memory(Double used_percent_memory) { this.used_percent_memory = used_percent_memory; }

        public Double getUsed_percent_cpu() { return used_percent_cpu; }
        public void setUsed_percent_cpu(Double used_percent_cpu) { this.used_percent_cpu = used_percent_cpu; }

        public Double getAllocatable_cpu() { return allocatable_cpu; }
        public void setAllocatable_cpu(Double allocatable_cpu) { this.allocatable_cpu = allocatable_cpu; }

        public Double getAllocatable_memory() { return allocatable_memory; }
        public void setAllocatable_memory(Double allocatable_memory) { this.allocatable_memory = allocatable_memory; }

        public Double getUsed_cpu() { return used_cpu; }
        public void setUsed_cpu(Double used_cpu) { this.used_cpu = used_cpu; }

        public Double getUsed_memory() { return used_memory; }
        public void setUsed_memory(Double used_memory) { this.used_memory = used_memory; }

        public Double getLimit_cpu() { return limit_cpu; }
        public void setLimit_cpu(Double limit_cpu) { this.limit_cpu = limit_cpu; }

        public Double getLimit_memory() { return limit_memory; }
        public void setLimit_memory(Double limit_memory) { this.limit_memory = limit_memory; }

        public String getService_type_avatar_paths() { return service_type_avatar_paths; }
        public void setService_type_avatar_paths(String service_type_avatar_paths) { this.service_type_avatar_paths = service_type_avatar_paths; }
    }
}
