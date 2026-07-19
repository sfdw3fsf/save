package vn.vnpt.vnptit.ecms.dto;

public class LoaiDSLamObjectDtoIn {
    private  String p_nhom_dslam;
    private  String p_loai_dslam;
    private  Long p_thamso;
    private Integer pageNumber = 1;
    private Integer pageSize = 10;

    public void setP_nhom_dslam(String p_nhom_dslam) {
        this.p_nhom_dslam = p_nhom_dslam;
    }

    public void setP_loai_dslam(String p_loai_dslam) {
        this.p_loai_dslam = p_loai_dslam;
    }

    public void setP_thamso(Long p_thamso) {
        this.p_thamso = p_thamso;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getP_nhom_dslam() {
        return p_nhom_dslam;
    }

    public String getP_loai_dslam() {
        return p_loai_dslam;
    }

    public Long getP_thamso() {
        return p_thamso;
    }

}
