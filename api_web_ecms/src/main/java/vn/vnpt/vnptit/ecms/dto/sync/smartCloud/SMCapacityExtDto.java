package vn.vnpt.vnptit.ecms.dto.sync.smartCloud;

import java.util.List;

public class SMCapacityExtDto {

    private List<CapacityNodeDto> totals;
    private List<CapacityNodeDto> details;

    public List<CapacityNodeDto> getTotals() {
        return totals;
    }

    public void setTotals(List<CapacityNodeDto> totals) {
        this.totals = totals;
    }

    public List<CapacityNodeDto> getDetails() {
        return details;
    }

    public void setDetails(List<CapacityNodeDto> details) {
        this.details = details;
    }

    public static class CapacityNodeDto {
        private String resourceType;
        private Double realValue;
        private Double value;
        private Double usageValue;
        private Double usageRealValue;
        private String unit;
        private String name;

        public String getResourceType() {
            return resourceType;
        }

        public void setResourceType(String resourceType) {
            this.resourceType = resourceType;
        }

        public Double getRealValue() {
            return realValue;
        }

        public void setRealValue(Double realValue) {
            this.realValue = realValue;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public Double getUsageValue() {
            return usageValue;
        }

        public void setUsageValue(Double usageValue) {
            this.usageValue = usageValue;
        }

        public Double getUsageRealValue() {
            return usageRealValue;
        }

        public void setUsageRealValue(Double usageRealValue) {
            this.usageRealValue = usageRealValue;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
