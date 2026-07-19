package vn.vnpt.vnptit.ecms.dto.sync.vcenter;


import java.util.List;

public class SyncVMwareInfoDTO {
    // 1. Thông tin cơ bản
    private String vmCode;              // ERD: MA_MAYAO
    private String vmName;              // ERD: TEN_MAYAO
    private Long guestOS;               // ERD: Tên hệ điều hành     => OneBSS tự define + mapping

    // 2. Năng lực máy ảo
    private Integer slCoreCpu;      // ERD: SL_CORECPU
    private Long dlRam;             // ERD: DL_RAM


    // 3. Danh sách thiết bị lưu trữ & IP (Collections)
    private List<SyncDiskDTO> diskList;
    private List<SyncNetworkIpDTO> networkIpList;

}

