package vn.vnpt.vnptit.ecms.dto.hatang;


import org.apache.logging.log4j.util.Strings;
import vn.vnpt.common.Utils;

import java.util.ArrayList;
import java.util.List;

public class KhoHaTangIDGThietBiDTO {
    private List<KhoHaTangThietBiHinhThanhDTO> thietBiHinhThanhList = new ArrayList<>();

    public List<KhoHaTangThietBiHinhThanhDTO> getThietBiHinhThanhList() {
        return thietBiHinhThanhList;
    }
}
