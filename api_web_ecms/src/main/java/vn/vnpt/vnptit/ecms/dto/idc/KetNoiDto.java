package vn.vnpt.vnptit.ecms.dto.idc;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.common.exception.BadRequestExceptionMessage;

public class KetNoiDto {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("phieutc")
        private Integer phieuTc;
        @JsonProperty("idthicongkn")
        private Long idThiCongKn;
        @JsonProperty("idLoaiCv")
        private Integer idLoaiCv;
        @JsonProperty("trangthai_phieuYC")
        private Integer trangthai_phieuYC;
        @JsonProperty("idc")
        private Integer idc;
        @JsonProperty("nguoi_taoketnoi")
        private Integer nguoiTaoKetNoi;
        @JsonProperty("loai_ketnoi")
        private Integer loaiKetNoi;
        @JsonProperty("khoang_cach")
        private Integer khoangCach;
        @JsonProperty("hinhthuc")
        private Integer hinhThuc;
        @JsonProperty("thoidiem")
        private String thoiDiem;
        @JsonProperty("loai_vat_lieu")
        private Integer loaiVatLieu;
        @JsonProperty("trangthai_vl")
        private Integer trangThaiVl;
        @JsonProperty("lop")
        private Integer lop;
        @JsonProperty("trangthai_vh")
        private Integer trangThaiVh;
        @JsonProperty("cancu_thuchien")
        private String canCuThucHien;

        @JsonProperty("tb_nguon_id")
        private Integer tbNguon;
        @JsonProperty("tb_nguon_con")
        private Integer tbNguonCon;
        @JsonProperty("loai_port")
        private Integer loaiPort;
        @JsonProperty("port_nguon")
        private Integer portNguon;
        // dư
        @JsonProperty("rack_nguon")
        private String rackNguon;
        @JsonProperty("thanh_nguon")
        private Integer thanhNguon;
        // dư
        @JsonProperty("patch_pannel_nguon")
        private String patchPannelNguon;
        @JsonProperty("loai_o_cam_nguon")
        private Integer loaiOCamNguon;
        @JsonProperty("o_cam_nguon")
        private String ocamNguon;
        @JsonProperty("tb_nguon_phutro")
        private String tbNguonPhutro;
        @JsonProperty("flag_TB_nguon")
        private Integer flagTBNguon;

        @JsonProperty("tb_dich_id")
        private Integer tbDich;
        @JsonProperty("tb_dich_con")
        private Integer tbDichCon;
        @JsonProperty("loai_port_dich")
        private Integer loaiPortDich;
        @JsonProperty("port_dich")
        private Integer portDich;
        // dư
        @JsonProperty("rack_dich")
        private String rackDich;
        @JsonProperty("thanh_nguon_dich")
        private Integer thanhNguonDich;
        // dư
        @JsonProperty("patch_pannel_dich")
        private String patchPannelDich;
        @JsonProperty("loai_o_cam_dich")
        private Integer loaiOCamDich;
        @JsonProperty("o_cam_dich")
        private String ocamDich;
        @JsonProperty("tb_dich_phutro")
        private String tbDichPhutro;
        @JsonProperty("flag_TB_dich")
        private Integer flagTBDich;

        @JsonProperty("nguoi_cn")
        private String nguoiCn;

        public void isValid() {
                if (trangthai_phieuYC != 21) {
                        if (idc == null || nguoiTaoKetNoi == null || loaiKetNoi == null || thoiDiem == null || thoiDiem.isEmpty()
                                || (loaiPort == null && loaiOCamNguon == null) || (portNguon == null && ocamNguon == null)
                                || (loaiPortDich == null && loaiOCamDich == null)|| (portDich == null && ocamDich == null)) {
                                throw new BadRequestExceptionMessage("Không được để trống các trường bắt buộc");
                        }
                }
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public Integer getIdc() {
                return idc;
        }

        public void setIdc(Integer idc) {
                this.idc = idc;
        }

        public String getNguoiCn() { return nguoiCn; }

        public void setNguoiCn(String nguoiCn) {
                this.nguoiCn = nguoiCn;
        }

        public Integer getIdLoaiCv() { return idLoaiCv;}
}
