<template src="./frmKhaoSatKH_LM.html"></template>
<style scope>
.e-grid .e-gridheader {
  position: -webkit-sticky;
  position: sticky;
  top: 0px;
  z-index: 1;
}

.select2-selection__rendered {
  font-weight: normal;
}

a.disabled {
  color: lightgrey !important;
  pointer-events: none;
  cursor: default;
  text-decoration: none;
}

.btn-custom.btn-second {
  background-color: #fff !important;
  border-color: #0176ff !important;
  color: #0176ff !important;
  font-weight: 600 !important;
}
.btn-second.btn {
  padding: 5px 10px !important;
  border-radius: 4px !important;
}
.legend-title{
    font-weight: 600 !important;
}
</style>
<script>
import moment from "moment";
import { mapActions } from "vuex";
import cauHoiKS from "./frmCauHoiKS.vue";
import frmLichSuHenBaoHong from "./frmLichSuHenBaoHong.vue";
import YesNoModal from "../utils/YesNoModal.vue";
import Vue from "vue";
import {
  isNullOrEmpty,
  kiemTraCauHoiKHL,
} from "../utils/functions/validators.js";
import {
  CSKH_CONST,
  FIMS_LYDO_KTL,
  LOAI_NV,
} from "../utils/functions/constants.js";

export default {
  name: "frmKhaoSatKH_LM",
  components: {
    cauHoiKS,
    frmLichSuHenBaoHong,
    confirmPopup: YesNoModal,
  },
  props: {},
  computed: {
    xacNhanDieuLai() {
      return "Bạn có chắc chắn muốn điều hỏng thuê bao " + this.maTbTmp + " ?";
    },
    ketQuaKS() {
      return this.$refs["cauHoiKS"].ketQuaKS;
    },
    cauHoiKSPhu() {
      return this.$refs["cauHoiKS"].dsCauHoiPhu;
    },
    cauHoiKSChinh() {
      return this.$refs["cauHoiKS"].dsCauHoiChinh;
    },
    cauHoiKSChau() {
      return this.$refs["cauHoiKS"].dsCauHoiChau;
    },
    danhSachCauHoi() {
      return this.$refs["cauHoiKS"].fakeData.cauHoi;
    },
    lichSuKSUrl() {
      return `/#/cskh/TraCuuThueBaoKhaoSat?maTB=${this.maTbTmp}`;
    },
  },
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },

      pThamSoMacDinh: {
        BATBUOC_TRALOI_ALL_CAUHOI: false,
        KIEMTRA_CAUHOI_KHL: false,
        OUTBOUND_KHONG_DIEULAI: false,
      },

      pTxtMaGD: "",
      pTxtNgayYC: "",
      pTxtLoaiHD: "",
      pTxtLoaiHinh: "",
      pTxtTenKH: "",
      pTxtDiaChiKH: "",
      pTxtDienThoaiLH: "",
      pTxtTenNguoiTL: "",
      pTxtSoLH: "",
      pTxtGhiChu: "",

      pDsLyDoKTL: [],
      chkKoTraLoi: false,
      chkKoTraLoiSelected: "",

      maTbTmp: "",
      maTb: "",
      hdkhId: 0,
      hdtbId: 0,
      baoHongId: 0,
      tbtlId: 0,
      giaoPhieuKsId: 0,
      thueBaoId: 0,

      isNew: false,
      isDaCapNhap: false,
      isDieuLai: false,
      isDieuLaiXN: false,
      ngayKS: "",

      dsThueBao: [],
      dsLanGoiChuaTraLoi: [],
      dsTBTL: [],

      pBtnStatus: {
        btnDieuLaiVisible: true,
        btnDieuLaiEnabled: true,
      },

      styles: {
        tbh: {},
        dslh: {},
      },

      cauHoiParams: {
        nhanVienId: LOAI_NV.THICONG_DAYMAY,
        hinhThuc: 1,
      },
    };
  },
  methods: {
    ...mapActions("cskhCommon", ["getThamSoMacDinh", "getListThamSoMacDinh"]),
    async dialogOpen() {
      await this.onFormLoad();
    },
    openDialog() {
      this.$refs.frmKhaoSatKH_LM.show();
    },
    closeDialog() {
      this.$emit("onHideKhaoSat");
      this.$refs.frmKhaoSatKH_LM.hide();
    },
    initData() {
      this.pTxtMaGD = "";
      this.pTxtNgayYC = "";
      this.pTxtLoaiHD = "";
      this.pTxtLoaiHinh = "";
      this.pTxtTenKH = "";
      this.pTxtDiaChiKH = "";
      this.pTxtDienThoaiLH = "";
      this.pTxtTenNguoiTL = "";
      this.pTxtSoLH = "";
      this.pTxtGhiChu = "";
      this.tbtlId = 0;
      this.chkKoTraLoi = false;
      this.chkKoTraLoiSelected = "";
      this.isNew = false;
      this.isDaCapNhap = false;
      this.isDieuLai = false;
      this.isDieuLaiXN = false;
      this.ngayKS = "";
    },
    async onFormLoad() {
      try {
        this.$root.showLoading(true);
        this.initData();

        await this.getThamSoMacDinh()
        await this.getLyDoKTL(),
        await this.hienThiThongTinTB(),
        await this.$refs.cauHoiKS.loadData();
        if (this.pThamSoMacDinh.OUTBOUND_KHONG_DIEULAI) {
          this.pBtnStatus.btnDieuLaiVisible = false;
        }

        // this.pBtnStatus.btnDieuLaiEnabled = this.isNew
        this.matchHeight();
      } catch (e) {
        console.log(e);
      } finally {
        this.$root.showLoading(false);
      }
    },
    async getThamSoMacDinh() {
      try {
        let dsThamSoMacDinh = await this.getListThamSoMacDinh({
          dsMaTs: Object.getOwnPropertyNames(this.pThamSoMacDinh).join(),
        });
        if (dsThamSoMacDinh && dsThamSoMacDinh.length > 0) {
          Object.getOwnPropertyNames(this.pThamSoMacDinh).forEach((ts) => {
            let rs = dsThamSoMacDinh.find((x) => x.MA_TS === ts);
            this.pThamSoMacDinh[ts] = (rs ? true : false) && rs.TEN_TS == "1";
          });
        }
      } catch (e) {
      } finally {
      }
    },
    async getLyDoKTL() {
      try {
        this.pDsLyDoKTL = [];
        this.chkKoTraLoiSelected = "";
        let response = await this.$root.context.get(
          "/web-cskh/api/danh-muc/ly-do-ktl"
        );
        if (response.error_code === "BSS-00000000") {
          this.pDsLyDoKTL = response.data || [];
          if (this.pDsLyDoKTL.length > 0) {
            this.chkKoTraLoiSelected = this.pDsLyDoKTL[0].LYDOKTL_ID;
          }
        }
      } catch (e) {
        this.pDsLyDoKTL = [];
      }
    },
    async hienThiThongTinTB() {
      try {
        let rs = await this.$root.context.get(
          "/web-cskh/api/khao-sat-kh-hd-tb/thong-tin-hop-dong",
          { hdkhId: this.hdkhId }
        );
        if (rs.error_code === CSKH_CONST.API_SUCCESS) {
          let ds = rs.data.data || [];
          if (ds.length > 0) {
            this.pTxtMaGD = ds[0].MA_GD;
            this.pTxtTenKH = ds[0].TEN_KH;
            this.pTxtDiaChiKH = ds[0].DIACHI_KH;
            this.pTxtDienThoaiLH = ds[0].SO_DT;
            if (this.$root.token.getPhanVungID() != 26) {
              this.pTxtSoLH = ds[0].SO_DT;
              this.pTxtTenNguoiTL = ds[0].TEN_KH;
            }
            this.pTxtLoaiHD = ds[0].TEN_LOAIHD;
            this.pTxtNgayYC = ds[0].NGAY_YC;

            this.dsLanGoiChuaTraLoi = await this.layDsLanGoiChuaTL(
              this.hdkhId,
              this.baoHongId
            );
            // this.dsTBTL = await this.layDsTBTL(this.hdkhId, 1)
            this.pBtnStatus.btnDieuLaiEnabled = true;
            if (this.dsLanGoiChuaTraLoi.length > 0) {
              this.isNew = false;
              if (this.tbtlId == 0) {
                this.tbtlId = this.dsLanGoiChuaTraLoi[0].TBTL_ID;
              }

              this.pTxtTenNguoiTL = this.dsLanGoiChuaTraLoi[0].NGUOI_TL;
              this.pTxtSoLH = this.dsLanGoiChuaTraLoi[0].DIENTHOAI_LH;
              this.pTxtGhiChu = this.dsLanGoiChuaTraLoi[0].GHICHU;
              this.ngayKS = this.dsLanGoiChuaTraLoi[0].NGAY_HOI;

              this.chkKoTraLoi = this.dsLanGoiChuaTraLoi[0].TRANGTHAI_ID == 0;
              if (this.dsLanGoiChuaTraLoi[0].TRANGTHAI_ID > 0)
                this.pBtnStatus.btnDieuLaiEnabled = false;
              if (this.chkKoTraLoi) {
                this.isNew = true;
                this.tbtlId = 0;
                if (this.dsLanGoiChuaTraLoi[0].LYDOKTL_ID) {
                  this.chkKoTraLoiSelected =
                    this.dsLanGoiChuaTraLoi[0].LYDOKTL_ID;
                }
              }
            } else {
              this.pBtnStatus.btnDieuLaiEnabled = true;
              this.isNew = true;
              this.tbtlId = 0;
            }
          }
        }

        this.dsThueBao = await this.layDsHDTheoHdkhId(this.hdkhId);
        if (this.dsThueBao.length > 0) {
          this.pTxtLoaiHinh = this.dsThueBao[0].LOAIHINH_TB;
        }
      } catch (e) {}
    },
    /*
      1: tìm kiếm theo hợp đồng hdkh_id
      2: báo hỏng: baohong_id
      3: tbtl_id
      4: hdtb_id
   */
    async layDsTBTL(id, loai) {
      try {
        let rs = await this.$root.context.get(
          "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-thue-bao-tra-loi",
          {
            id: id,
            loai: loai,
            pageNo: 1,
            pageSize: 10000,
          }
        );
        if (rs.error_code === CSKH_CONST.API_SUCCESS) {
          return rs.data.data || [];
        }
      } catch (e) {
        console.log(e);
      }
      return [];
    },
    async layDsHDTheoHdkhId(hdkhId) {
      try {
        let rs = await this.$root.context.get(
          "/web-cskh/api/khao-sat-kh-lm/hop-dong-thue-bao-theo-hdkh",
          {
            hdkhId: hdkhId,
            pageNo: 1,
            pageSize: 10000,
          }
        );
        if (rs.error_code === CSKH_CONST.API_SUCCESS) {
          return rs.data.data || [];
        }
      } catch (e) {
        console.log(e);
      }
      return [];
    },
    async layTTHD(hdkhId) {
      try {
        let rs = await this.$root.context.get(
          "/web-cskh/api/khao-sat-kh-hd-tb/thong-tin-hop-dong",
          {
            hdkhId: hdkhId,
            pageNo: 1,
            pageSize: 10000,
          }
        );
        if (rs.error_code === CSKH_CONST.API_SUCCESS) {
          return rs.data.data || [];
        }
      } catch (e) {
        console.log(e);
      }
      return [];
    },
    async layDsLanGoiChuaTL(hdkhId, baoHongId) {
      try {
        let rs = await this.$root.context.get(
          "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-lan-goi-chua-tra-loi",
          {
            id: hdkhId,
            loai: 1,
            pageNo: 1,
            pageSize: 10000,
          }
        );
        if (rs.error_code === CSKH_CONST.API_SUCCESS) {
          return rs.data.data || [];
        }
      } catch (e) {
        console.log(e);
      }
      return [];
    },
    focusInput(name) {
      this.$nextTick(() => this.$refs[name].select());
    },
    async onGhiLaiClicked() {
    //   this.focusInput("txtMaGD");
      try {
        this.loading(true)
        if (this.isNew) {
          this.isDaCapNhap = false;
          if (await this.kiemTraDuLieu(true)) {
            let data = this.taoDuLieu(true, false);
            if (this.isDieuLai) {
              this.$bvModal
                .msgBoxConfirm(
                  `Cập nhật nội dung này sẽ điều phiếu hỏng cho thuê bao ${this.maTbTmp}. Bạn có chắc chắn muốn điều?`,
                  {
                    title: "Xác nhận hành động",
                    centered: true,
                    size: "sm",
                  }
                )
                .then((value) => {
                  if (value) {
                    this.fetchGhiLaiAction(data)
                  } else {
                    return;
                  }
                });
            } else {
                this.fetchGhiLaiAction(data)
            }
          } else {
            return;
          }
        } else {
          if (await this.kiemTraDuLieu(false)) {
            let data = this.taoDuLieu(false, false);
            if (this.isDieuLai) {
              this.$bvModal
                .msgBoxConfirm(
                  `Cập nhật nội dung này sẽ điều phiếu hỏng cho thuê bao ${this.maTbTmp}. Bạn có chắc chắn muốn điều?`,
                  {
                    title: "Xác nhận hành động",
                    centered: true,
                    size: "sm",
                  }
                )
                .then((value) => {
                  if (value) {
                    this.fetchGhiLaiAction(data)
                  } else {
                    return;
                  }
                });
            } else {
                this.fetchGhiLaiAction(data)
            }
          }
        }
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false)
      }
    },
    async fetchGhiLaiAction(data){
        this.loading(true);
        this.$root.context
            .post("/web-cskh/api/khao-sat-kh-lm/ghi-lai", data)
            .then((rs) => {
                if (rs.error_code === CSKH_CONST.API_SUCCESS) {
                    if (rs.data.ketQua == 1) {
                    this.$root.toastSuccess("Cập nhật thành công1!");
                    if (rs.data.msgError) {
                        let listMessages = rs.data.msgError.split("#");
                        listMessages.forEach((message) => {
                            let pattern = /.*\d/g;
                            let result = message.match(pattern);
                            if (
                                result &&
                                result.length > 0 &&
                                result[0].includes("1")
                            ) {
                                this.$root.toastSuccess(message.replace(result[0], ""));
                            } else {
                                this.$root.toastError(message.replace(result[0], ""));
                            }
                        });
                    }
                    this.closeDialog();
                    } else {
                        this.$root.toastError(rs.data.msgError);
                    }
                }
            })
            .catch((e) =>
                console.log(e)
            )
            .finally(() => this.loading(false));
    },
    isEmpty(str) {
      return !str || str.trim().length === 0;
    },
    async kiemTraDuLieu(themMoi) {
      if (isNullOrEmpty(this.pTxtTenNguoiTL)) {
        if (!this.pTxtTenNguoiTL && !this.chkKoTraLoi) {
          this.$root.toastError("Chưa nhập tên người trả lời !");
          this.focusInput("txtTenNguoiTL");
          return false;
        }
      }
      if (isNullOrEmpty(this.pTxtSoLH)) {
        this.$root.toastError("Chưa nhập điện thoại liên hệ !");
        this.focusInput("txtSoLH");
        return false;
      }

      if (!this.isNumeric(this.pTxtSoLH)) {
        this.$root.toastError(
          "Số điện thoại liên hệ của khách hàng phải là kiểu số!"
        );
        this.focusInput("txtSoLH");
        return false;
      }

      if (this.ketQuaKS.length > 0 && this.chkKoTraLoi) {
        this.$root.toastError(
          'Bạn đang chọn "Không trả lời" và đồng thời tích chọn trả lời trong Danh sách câu hỏi. Bạn có muốn tiếp tục?'
        );
        return false;
      }

      // if (this.ketQuaKS.length > 0 && this.chkKoTraLoi) {
      //     this.$root.toastError(
      //         'Bạn đang chọn "Không trả lời" và đồng thời tích chọn trả lời trong Danh sách câu hỏi!'
      //     );
      //     return false;
      // }

      let cauHoiKS = this.$refs["cauHoiKS"];

      // nếu check vào KTL rồi thì ko cần kiểm tra
      if (!this.chkKoTraLoi) {
        if (this.ketQuaKS.length <= 0) {
          this.$root.toastError("Bạn phải chọn câu trả lời!");
          return false;
        }

        let kiemTraQuaGio = false;
        for (let chIndex = 0; chIndex < this.ketQuaKS.length; chIndex++) {
          let traLoiId = this.ketQuaKS[chIndex].RCH_ID;
          if (this.ketQuaKS[chIndex].RCH_ID == 53) {
            kiemTraQuaGio = true;
          }

          let cauHoiPhu = this.cauHoiKSChau.filter((x) => x.RCH_ID == traLoiId);
          if (cauHoiPhu.length > 0) {
            let chpDcTL = false;
            for (let chpIndex = 0; chpIndex < cauHoiPhu.length; chpIndex++) {
              if (cauHoiPhu[chpIndex].Checked) {
                chpDcTL = true;
                break;
              }
            }
            if (!chpDcTL) {
              this.$root.toastError(
                "Bạn chọn câu trả lời '" +
                  this.ketQuaKS[chIndex].TRALOI +
                  "', mà chưa chọn lý do ! "
              );
              return false;
            }
          }

          //kiểm tra các câu trả lời ý kiến khác mà không nhập text.
          let traLoi = this.cauHoiKSChau.filter((x) => x.TRALOI_ID == traLoiId);
          if (traLoi.length > 0) {
            if (
              (traLoi[0].LOAI == 1 || traLoi[0].LOAI == 2) &&
              this.ketQuaKS[chIndex].NOIDUNG
            ) {
              this.$root.toastError(
                "Bạn chưa nhập nội dung cho câu trả lời '" +
                  this.ketQuaKS[chIndex].TRALOI +
                  " ! "
              );
              return false;
            }
          }
        }

        // fix HPG
        if (this.$root.token.getPhanVungID() == 26) {
          let c = this.dsThueBao.filter((x) => x.VPXL > 0 && x.LOAITB_ID == 24);
          if (c != null && c.length == 0 && kiemTraQuaGio) {
            this.$root.toastError(
              "Trường hợp này thi công/xử lý chưa quá giờ! Bạn hay chọn Hài long, đồng thời chuyển sang câu 'Ý kiến hài lòng của khách hàng' và chọn tiếp 'Thời gian sửa chữa, lắp đặt trên hệ thống đúng quy định của VNPT nhưng khách hàng vẫn phàn nàn chậm'"
            );
            this.focusInput("txtTenNguoiTL");
            return false;
          }
        }

        if (this.pThamSoMacDinh.BATBUOC_TRALOI_ALL_CAUHOI) {
          // Kiểm tra nhóm câu hỏi
          let cauHoiBatBuoc = this.danhSachCauHoi.filter(
            (x) => x.BATBUOC != null
          );
          console.log("cauHoiBatBuoc", cauHoiBatBuoc);
          if (cauHoiBatBuoc.length > 0) {
            let dapAnBatBuoc = this.ketQuaKS.map((x) =>
              cauHoiBatBuoc.map((x) => x.CAUHOI_ID).includes(x.CAUHOI_ID)
            );
            if (
              dapAnBatBuoc.length < cauHoiBatBuoc[0].SL_TU ||
              dapAnBatBuoc.length > cauHoiBatBuoc[0].SL_DEN
            ) {
              this.$root.toastError("Có câu hỏi đang chưa chọn câu trả lời!");
              return false;
            }
          }
        }

        if (this.pThamSoMacDinh.KIEMTRA_CAUHOI_KHL) {
          let _rch_id = this.ketQuaKS.map((x) => x.RCH_ID);

          let drRCH = this.danhSachCauHoi.filter(
            (x) => x.KIEU == 1 && _rch_id.includes(x.RCH_ID1)
          );

          if (drRCH != null && drRCH.length > 0) {
            let drRCH2 = this.danhSachCauHoi.filter(
              (x) => x.KIEU != 1 && _rch_id.includes(x.RCH_ID1)
            );
            let rs_temp = drRCH.slice();
            rs_temp.sort((a, b) => {
              return a.THU_TU < b.THU_TU ? 1 : -1;
            });

            if (
              kiemTraCauHoiKHL(this.danhSachCauHoi, rs_temp[0].RCH_ID1, 1) == 1
            ) {
              if (rs_temp.length < 2) {
                this.$root.toastError("Hãy chọn đủ câu trả lời!");
                return false;
              }

              //-> kiểm tra câu 2
              //Câu 2 chọn trả lời khác "Không hài lòng kỹ thuật" -> không cho làm
              if (
                kiemTraCauHoiKHL(this.danhSachCauHoi, rs_temp[1].RCH_ID1, 2) !=
                1
              ) {
                this.$root.toastError(
                  'Câu 1 chọn "Chưa hoàn thành lắp đặt/sửa chữa" thì câu 2 phải chọn "Không hài lòng về nhân viên kỹ thuật" với lý do "KT3"'
                );
                return false;
              } //câu 2 chọn đúng -> kiểm tra câu 3
              else {
                // Câu 3 chọn trả lời khác KT3 -> không cho làm
                if (
                  kiemTraCauHoiKHL(this.danhSachCauHoi, drRCH2[0].RCH_ID1, 3) !=
                  "1"
                ) {
                  this.$root.toastError(
                    'Câu 3 phải chọn "KT3- Chưa được lắp đặt/sửa chữa dịch vụ"'
                  );
                  return false;
                }
              }
            } else if (
              kiemTraCauHoiKHL(this.danhSachCauHoi, rs_temp[0].RCH_ID1, 1) == 2
            ) {
              if (rs_temp.length < 2) {
                this.$root.toastError("Hãy chọn đủ câu trả lời!");
                return false;
              }

              if (
                kiemTraCauHoiKHL(this.danhSachCauHoi, rs_temp[1].RCH_ID1, 2) ==
                "1"
              ) {
                if (
                  fims.Kiemtra_traloi_fiber(
                    Convert.ToInt32(drRCH2[0]["RCH_ID1"]),
                    baohong_id,
                    hdkh_id
                  ) == "1"
                ) {
                  this.$root.toastError(
                    "Thuê bao Fiber không thể chọn Không hài lòng với nguyên nhân của Media!"
                  );
                  return false;
                }
              }
            } else if (
              kiemTraCauHoiKHL(this.danhSachCauHoi, rs_temp[0].RCH_ID1, 1) ==
              "3"
            ) {
              //Câu 1 chọn KTC, KTM
              if (rs_temp.length >= 2) {
                this.$root.toastError(
                  'Với câu trả lời "Không thành công" hoặc "Không tính mẫu" không thể chọn trả lời Câu 2!'
                );
                return false;
              }
            }
          }
        }
      }

      // Kiểm tra chỉ user người khảo sát mới được sửa nội dung ks và chỉ sửa trong tháng
      if (!themMoi) {
      }

      if (this.$auth.getPhanVungID() == 28) {
        if (this.isNew) {
          //kiem tra giu phieu
        }
      }
      // this.ketQuaKS.forEach(kq => {
      //     if (kq.NOIDUNG && kq.NOIDUNG.trim().length > 500) {
      //         this.$root.toastError("Câu trả lời vượt quá 500 ký tự!");
      //         return false;
      //     }
      // })

      return await this.kiemTraKhaoSat();

      // return true;
    },

    async kiemTraKhaoSat() {
      try {
        let kq = this.ketQuaKS;
        let postData = {
          nhanVienId: this.$root.token.getNhanVienID(),
          tbtlId: this.tbtlId,
          loaiId: 1,
          dsCauHoiId: kq.map((x) => x.CAUHOI_ID).join("#"),
          dsTraLoiId: kq.map((x) => x.TRALOI_ID).join("#"),
          dsNoiDung: kq.map((x) => x.NOIDUNG).join("#"),
          dsThamSo: "",
        };
        let response = await this.$root.context.post(
          "/web-cskh/api/common/kiem-tra-ks",
          postData
        );
        if (response.error_code === CSKH_CONST.API_SUCCESS) return true;
      } catch (e) {
        console.log(e);
      }
      return false;
    },

    taoDuLieu(isThemMoi, isXuatLai) {
      let kq = this.ketQuaKS;
      let ttksId = 0;
      let lanKsToiDa = 4;

      this.isDieuLai = false;
      this.isDieuLaiXN = false;

      if (this.chkKoTraLoi) {
        ttksId = 1; //chưa trả lời
        let dsktl = this.dsLanGoiChuaTraLoi.filter((x) => x.LYDO_KTL);
        //quá số lần ks tối đa thì không gọi lại KH nữa
        if (dsktl.length + 1 > lanKsToiDa) {
          ttksId = 7;
        }
      } else {
        ttksId = 6; // đã hoàn thành
      }

      this.isDaCapNhap = true;

      let data = {
        tbtlId: isThemMoi ? 0 : this.tbtlId,
        hdkhId: this.hdkhId,
        hdtbId: this.hdtbId,
        thueBaoId: this.thueBaoId,
        nguoiTl: this.pTxtTenNguoiTL,
        dienThoaiLh: this.pTxtSoLH,
        ghiChu: this.pTxtGhiChu,

        ttksId: ttksId,
        gpksId: this.giaoPhieuKsId,

        lyDoKtlId: this.chkKoTraLoi ? this.chkKoTraLoiSelected : 0,
        dsCauHoiId: this.chkKoTraLoi
          ? ""
          : kq.map((x) => x.CAUHOI_ID).join("#"),
        dsTraLoiId: this.chkKoTraLoi
          ? ""
          : kq.map((x) => x.TRALOI_ID).join("#"),
        dsNoiDung: this.chkKoTraLoi
          ? ""
          : JSON.stringify(kq.map((x) => `${x.NOIDUNG}`)),
      };

      kq.forEach((row) => {
        if (row.DIEULAI == 1) {
          this.isDieuLai = true;
        }
        if (row.DIEULAI_XN == 1) {
          this.isDieuLaiXN = true;
        }
      });
      return data;
    },
    taoDuLieuHenKS() {
      let data = null;
      if (this.pchkHenKS_Tu || this.pchkhenKS_Den) {
        data.giaoPhieuKsId = this.giaoPhieuKsId;
        data.hdkhId = this.hdkhId;
        data.hdtbId = this.hdtbId;
        data.baoHongId = 0;

        if (this.pchkHenKS_Tu)
          data.gioHenTu = moment(this.pDtpHenKS_Tu).format("DD/MM/YYYYY");

        if (this.pchkHenKS_Den)
          data.gioHenDen = moment(this.pDtpHenKS_Den).format("DD/MM/YYYYY");

        data.nhanVienid = this.$root.token.getNhanVienID();
        data.ghiChu = this.pTxtGhiChu;
        data.kieu = 2;
      }
      return data;
    },
    onDieuLaiClicked() {
      if (this.$auth.getPhanVungID() == 28) {
        this.$root.toastError(
          "Hiện tại nghiệp vụ điều lại chưa được hỗ trợ với HCM!"
        );
        return;
      }

      if (this.phieuId <= 0) {
        this.$root.toastError("Bạn chưa chọn thuê bao để điều lại!");
        return;
      }

      this.$bvModal.show("popup-confirmDieuLai_lm");
    },
    async onDieuLaiConfirmed() {
      try {
        this.$root.showLoading(true);
        if (this.isNew) {
          if (await this.kiemTraDuLieu(true)) {
            let data = this.taoDuLieu(true, true);
            let rs = await this.$root.context.post(
              "/web-cskh/api/khao-sat-kh-lm/dieu-lai",
              data
            );
            if (rs.error_code === "BSS-00000000") {
              if (rs.data.ketQua == 1) {
                // this.$root.toastSuccess("Điều lại thành công!");
                if (rs.data.msgError) {
                  let listMessages = rs.data.msgError.split("#");
                  listMessages.forEach((message) => {
                    let pattern = /.*\d/g;
                    let result = message.match(pattern);
                    if (
                      result &&
                      result.length > 0 &&
                      result[0].includes("1")
                    ) {
                      this.$root.toastSuccess(message.replace(result[0], ""));
                    } else {
                      this.$root.toastError(message.replace(result[0], ""));
                    }
                  });
                }
                this.closeDialog();
              } else {
                this.$root.toastError(rs.data.msgError);
              }
            } else {
              this.$root.toastError("Điều lại thất bại!");
            }
          }
        } else {
          this.$root.toastError("Không thể điều lại cho phiếu đã khảo sát!");
          return;
        }
      } catch (e) {
        console.log(e);
        this.$root.toastError("Điều lại thất bại: " + e);
      } finally {
        this.$root.showLoading(false);
      }
    },
    onDsThueBaoRowSelected(item) {
      if (item) {
        this.phieuId = item.HDTB_ID;
        this.maTbTmp = item.MA_TB;
      }
    },
    onDsLanGoiRowSelected(item) {},
    isNumeric(input) {
      return /^\d+$/.test(input);
    },
    matchHeight() {
      var heightTTKH = this.$refs.boxTTKH.clientHeight + 2 + "px";
      var heightTTCN = this.$refs.boxTTCN.clientHeight + 2 + "px";
      Vue.set(this.styles.tbh, "height", heightTTKH);
      Vue.set(this.styles.dslh, "height", heightTTCN);
    },
  },
};
</script>
