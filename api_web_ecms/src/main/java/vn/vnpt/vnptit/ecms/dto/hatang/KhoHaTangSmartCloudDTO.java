package vn.vnpt.vnptit.ecms.dto.hatang;


import org.apache.logging.log4j.util.Strings;
import vn.vnpt.common.Utils;
import vn.vnpt.common.exception.BadRequestExceptionMessage;
import vn.vnpt.message.ModelValidation;

import java.util.ArrayList;
import java.util.List;

public class KhoHaTangSmartCloudDTO {
    private int id;
    private String mahatang_infra = "";
    private String ten = "";
    private int hinhthucdautu;
    private int loaihatang_id;
    private int hatang_phanloai_id;
    private int hatang_congnghe_id;
    private int idc_id;
    private List<Number> hatangmang_id = new ArrayList<>();
    private List<Number> thietbi_tudia_id = new ArrayList<>();
    private String admin_link = "";
    private String avatar = "";
    private String ghi_chu = "";
    private int hieu_luc = 1;
    private int donviqly_id;
    private int phongbanqly_id;
    private int nguoiqly_id;
    private int hatang_capdo_id;
    private int vcpu_nangluc;
    private int vcpu_cap;
    private int vcpu_total;
    private int vram_nangluc;
    private int vram_cap;
    private int vram_total;
    private int gpu_nangluc;
    private int gpu_cap;
    private int gpu_total;
    private List<KhoHaTangThietBiHinhThanhDTO> thietBiHinhThanhList = new ArrayList<>();

    public List<KhoHaTangThietBiHinhThanhDTO> getThietBiHinhThanhList() {
        return thietBiHinhThanhList;
    }
    public void valid() {
        boolean isValid = !Strings.isBlank(this.mahatang_infra) && !Strings.isBlank(this.ten)
                && this.hinhthucdautu > 0 && this.loaihatang_id > 0
                && this.hatang_phanloai_id > 0 && this.idc_id > 0
                && !this.hatangmang_id.isEmpty() && this.hatang_capdo_id > 0
                && this.vcpu_cap >= 0 && this.vcpu_nangluc >= 0 && this.vcpu_total >= 0
                && this.vram_cap >= 0 && this.vram_nangluc >= 0 && this.vram_total >= 0
                && this.gpu_cap >= 0 && this.gpu_nangluc >= 0 && this.gpu_total >= 0
                && this.vcpu_nangluc <= this.vcpu_total
                && this.vram_nangluc <= this.vram_total
                && this.gpu_nangluc <= this.gpu_total
//                && this.nhanvien_id > 0 && !Strings.isBlank(this.may_capnhap)
//                && !Strings.isBlank(this.nguoi_capnhap) && !Strings.isBlank(this.ip_capnhap)
                ;
        if (!isValid) throw new BadRequestExceptionMessage("Params invalid");
    }

    public List<Number> getThietbi_tudia_id() {
        return thietbi_tudia_id;
    }

    public int getId() {
        return id;
    }

    public String getMahatang_infra() {
        return Utils.trim(this.mahatang_infra);
    }

    public String getTen() {
        return Utils.trim(this.ten);
    }

    public int getHinhthucdautu() {
        return hinhthucdautu;
    }

    public int getLoaihatang_id() {
        return loaihatang_id;
    }

    public int getHatang_phanloai_id() {
        return hatang_phanloai_id;
    }

    public int getHatang_congnghe_id() {
        return hatang_congnghe_id;
    }

    public int getIdc_id() {
        return idc_id;
    }

    public List<Number> getHatangmang_id() {
        return hatangmang_id;
    }

    public String getAdmin_link() {
        return admin_link;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public int getHieu_luc() {
        return hieu_luc;
    }

    public int getDonviqly_id() {
        return donviqly_id;
    }

    public int getPhongbanqly_id() {
        return phongbanqly_id;
    }

    public int getNguoiqly_id() {
        return nguoiqly_id;
    }

    public int getHatang_capdo_id() {
        return hatang_capdo_id;
    }

    public int getVcpu_nangluc() {
        return vcpu_nangluc;
    }

    public int getVcpu_cap() {
        return vcpu_cap;
    }

    public int getVcpu_total() {
        return vcpu_total;
    }

    public int getVram_nangluc() {
        return vram_nangluc;
    }

    public int getVram_cap() {
        return vram_cap;
    }

    public int getVram_total() {
        return vram_total;
    }

    public int getGpu_nangluc() {
        return gpu_nangluc;
    }

    public int getGpu_cap() {
        return gpu_cap;
    }

    public int getGpu_total() {
        return gpu_total;
    }
}
