package vn.vnpt.vnptit.ecms.dto.sync.vcenter;


public class VCterDatastoreDTO {
    private String datastore;
    private String name;
    private String type;
    private double free_space;
    private double capacity;
    private String unit;

    public String getDatastore() {
        return datastore;
    }

    public void setDatastore(String datastore) {
        this.datastore = datastore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getFree_space() {
        return free_space;
    }

    public void setFree_space(double free_space) {
        this.free_space = free_space;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
