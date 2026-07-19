<template src="./frmGiaoPhieu.html"></template>

<script>
import API from "../api/frmGiaoPhieu.js";
import moment from "moment";
import Vue from "vue";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";

Vue.use(DatePickerPlugin);
export default {
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      editSettings: { allowEditing: true, mode: "Batch" },

      parentForm: "",      
      gParams: {
        giaophieu_id: 0,
        donvi_nhan_id: "",
        dtThaoTac: [],
        dt: [],
        ds: [],
        ds_dvn: [],
        phieu_cha_id: 0,
        trangthai: 0,
        huonggiao_id: 0,
        hg_id: 0, //reset 0
        ttph_id: 0,
        bGiaoPhieu: false,
        loaidv_nhan_id: "",
        load: false,
        loaiphieu_id: 0,
        baocao_id: 0,
        quyettoan_id: 0,
        chungtu_id: 0,
        dangky_id: 0,
        donhang_id: 0,
        denghi_id: 0,
        ma_phieu: "",
        sophieu: "",
        dsTP: null,
        dsBC: null,
        parameters: [],
        dtDVNV_GP: [],
        gp_ds: false,
        cboTrangThai: "",
        dtpNgayGiao: new Date(),
        cboHuongGiao: "",
        txtMaOTPChon: "",
        txtNoiDung: "",
        cboDonVi: "",
        txtGhiChu: "",
        cboNhanVienXL: "",
        isShowMultiSelect: false,
        lay_chungtu: false,
        colSoPhieuCT: true,
        gridDSPhieuRowSelected: null,
        gridDSPhieuItemSelected: [],
        loi: "",
        tsSoHoa: "",
        taikhoan_ks: "",
        matkhau_ks: "",
        tuTinh: null,
        denTinh: null
      },
      cboTrangThai: [
        { TRANGTHAI_ID: 1, TRANGTHAI: "Chưa giao" },
        { TRANGTHAI_ID: 2, TRANGTHAI: "Đã giao" },
      ],
      enabledComponent: {
        tsbtnTimKiem: true,
        tsbtnGiaoPhieu: true,
        tsbtnHuyGiao: true,
      },
      cboHuongGiao: [],
      DSTinh: [],
      cboDonVi: [],
      cboNhanVienXL: [],
      gridDSPhieu: [],
      saveMota: "", // truyền mota ở FE qua BE
      saveFileJson: "", // truyền fileJson ở FE qua BE
      isShowPass: false
    };
  },
  methods: {
    /* init and reset form */
    openDialog() {
      this.$refs.dialogGiaoPhieu.show();
    },
    async dialogOpened() {
      await this.frmGiaoPhieu_Load();
    },
    closeDialog() {
      if (
        [
          "NhapDeNghiVatTu",
          "DeNghiQuyetToanVatTu",
          "NhapXuatVT",
          "XacNhanDeNghi",
          "frmXuLyYeuCau_VT"
        ].includes(this.parentForm)
      ) {
        this.$parent.closeGiaoPhieu();
        this.parentForm = "";
      }
    },
    /* end init and reset form */

    /* constructor */
    frmGiaoPhieuHG(huongGiaoId) {      
      this.gParams.huonggiao_id = huongGiaoId;
    },
    frmGiaoPhieuLP(vloaiphieu_id, vphieu_cha_id, vquyettoan_id, vma_phieu) {
      this.gParams.loaiphieu_id = vloaiphieu_id;
      this.gParams.giaophieu_id = vphieu_cha_id;
      this.gParams.quyettoan_id = vquyettoan_id;
      this.gParams.ma_phieu = vma_phieu;
    },
    frmGiaoPhieuXLYCVT(
      _ma_gd,
      _kieu_id,
      _huonggiao_id,
      _hdtb_id,
      ttph,
      ngaygiao
    ) {
      this.gParams.loaiphieu_id = vloaiphieu_id;
      this.gParams.giaophieu_id = vphieu_cha_id;
      this.gParams.quyettoan_id = vquyettoan_id;
      this.gParams.ma_phieu = vma_phieu;
    },
    async frmGiaoPhieu_Load() {      
      this.DSTinh = await ( await API.getDSTinh(this.axios,[])).data.data;
      this.gParams.cboTrangThai = this.setFirstValue(
        this.cboTrangThai,
        "TRANGTHAI_ID"
      );
      await this.LAY_DS_HUONGGIAO();
      if (this.gParams.donvi_nhan_id == 0) {
        this.gParams.donvi_nhan_id = this.$auth.getDonViID();
      }
      this.gParams.cboHuongGiao = this.setFirstValue(
        this.cboHuongGiao,
        "huongGiaoId"
      );
      await this.LAY_DS_DONVI_GIAOPHIEU(
        this.gParams.huonggiao_id,
        this.$auth.getNhanVienID(),
        this.gParams.donvi_nhan_id
      );
      
      if (
        this.cboDonVi.filter((e) => e.DONVI_ID == this.gParams.donvi_nhan_id)
          .length
      ) {
        this.gParams.cboDonVi = this.gParams.donvi_nhan_id;
      }else{
        this.gParams.cboDonVi = this.setFirstValue(this.cboDonVi, "DONVI_ID");
      }

      await this.HienThi_DanhSach();
      if (this.PHAILAM("GIAOPHIEU_KS_DS")) {
        this.gParams.gp_ds = true;
        this.gParams.isShowMultiSelect = true;
      }

      this.tsSoHoa = await this.LAY_GT_THAMSO_MD_MATS("TS_QLVT_SOHOA_NEW")

      this.gParams.load = true;

      this.DSTinh = await ( await API.getDSTinh(this.axios,[])).data.data;
    },
    /* end  constructor*/

    /* methods*/
    isNullOrEmpty(value) {
      return value === null || value === "";
    },
    dateToString(value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    stringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    async tsbtnTimKiem() {
      //console.log(this.gParams.cboTrangThai);
      await this.HienThi_DanhSach();
    },
    async tsbtnGiaoPhieu() {
      await this.GiaoPhieu();
    },
    async tsbtnHuyGiao() {
      try{
        if (!this.gParams.gp_ds) {        
            /* huy don le */
          this.$root.loading(true);
          this.gParams.bGiaoPhieu = false;
          let k = this.gParams.gridDSPhieuRowSelected;
          let recordSelecteds = this.$refs.gridViewDSPhieu.getSelectedRecords();
          let isChoosed = recordSelecteds.some((e) => e.MA_PHIEU == k.MA_PHIEU);
          if (isChoosed) {
            this.gParams.giaophieu_id = parseInt(k.PHIEU_ID);
            this.gParams.ttph_id = parseInt(k.TTPH_ID);
            this.gParams.bGiaoPhieu = true;
          }
          if (this.gParams.bGiaoPhieu) {
            let list_mota = [];
            if (this.PHAILAM("XOA_FILE")) {
              //pending cho api
              let mota_phieutrc =
                (await (
                  await API.getMotaGP(this.axios, {
                    phieuId: this.gParams.giaophieu_id,
                  })
                ).data.data) || "";
              if (!this.isNullOrEmpty(mota_phieutrc)) {
                list_mota = JSON.parse(mota_phieutrc);
                list_mota.forEach((element) => {
                  element.filename = element.url.substring(
                    element.url.lastIndexOf("/") + 1
                  );
                  console.log(element.filename);
                });
              }
            }
            let kt = await (
              await API.postHuyPhieu(this.axios, {
                phieuId: this.gParams.giaophieu_id,
              })
            ).data.data;
            if (kt == "OK") {
              if (list_mota.length) {
                try {
                  list_mota.forEach(async (element) => {
                    await API.deleteFileObjectStorage(this.axios, {
                      fileSource: element.url,
                    });
                  });
                } catch (error) {}
              }
              this.$toast.success("Đã hủy giao phiếu thành công!");
            } else {
              this.$toast.error(kt);
            }
          } else {
            this.$toast.error("Bạn chưa chọn phiếu để hủy");
          }
            /* end huy don le */
          
        } else {        
            /* huy giao danh sách */
          if (this.gridDSPhieu.length == 0) {
            return this.$toast.error("Không có phiếu để thực hiện");
          }
          let ds_chk = this.$refs.gridViewDSPhieu.getSelectedRecords();
          if (!ds_chk.length)
            return this.$toast.error("Bạn chưa chọn phiếu để thực hiện");

          let dem = 0;
          let boqua = false;
          let list_mota = [];
          ds_chk.forEach(async (e) => {
            let p_giaophieu_id = parseInt(e["PHIEU_ID"]);
            let p_ttph_id = parseInt(e["TTPH_ID"]);
            if (this.PHAILAM("XOA_FILE")) {
              list_mota = [];
              let mota_phieutrc = await (
                await API.getMotaGP(this.axios, {
                  phieuId: p_giaophieu_id,
                })
              ).data.data;
              if (!this.isNullOrEmpty(mota_phieutrc)) {
                list_mota = JSON.parse(mota_phieutrc);
              }
            }
            let kt = await (
              await API.postHuyPhieu(this.axios, {
                phieuId: p_giaophieu_id,
              })
            ).data.data;
            if (kt == "OK") {
              if (list_mota.length) {
                try {
                  list_mota.forEach(async (element) => {
                    await API.deleteFileObjectStorage(this.axios, {
                      fileSource: element.url,
                    });
                  });
                } catch (error) {}
              }
            } else {
              if (!boqua) {                
                
                var isBoxConfirm = false;
                await this.$bvModal.msgBoxConfirm(`Huỷ phiếu bị lỗi: ${kt} . Bạn có muốn tiếp tục không?`, {
                  title: "Thông báo",
                  centered: true,
                  size: "md",
                  okTitle: "Đồng ý",
                  cancelTitle: "Hủy",
                })
                .then((value) => {
                  if (value) {
                    isBoxConfirm = true;
                  } 
                });

                if (!isBoxConfirm) {
                  return;
                }
                await this.HienThi_DanhSach();
                return 
              }
            }
          });
          
          
        }
        await this.HienThi_DanhSach();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async btnLayLaiOTP() {
      if (!this.gParams.gp_ds) {
        let vgiaophieu_id = 0;
        if (!this.gParams.gridDSPhieuRowSelected) {
          this.$toast.error("Bạn chưa chọn phiếu giao");
          return;
        } else {
          vgiaophieu_id = parseInt(
            this.gParams.gridDSPhieuRowSelected["PHIEU_ID"]
          );
          await API.getOTP(this.axios, { phieuId: vgiaophieu_id });
          this.$toast.success("Đã gửi OTP đến số máy người dùng ");
        }
      } else {
        let dt = this.$refs.gridViewDSPhieu.getSelectedRecords();
        let dsPh = [];
        if (dt.length) {
          for (const item of dt) {
            dsPh.push({ PHIEU_ID: item.PHIEU_ID });
          }
          await API.getOTPDS(this.axios, { dsPhieu: JSON.stringify(dsPh) });
          this.$toast.success("Đã gửi OTP đến số máy người dùng ");
        } else {
          this.$toast.error("Bạn chưa chọn phiếu giao");
          return;
        }
      }
    },
    setFirstValue(cbo, key) {
      if (cbo.length) {
        return cbo[0][key];
      } else {
        return "";
      }
    },
    async gridViewDSPhieuFocusedRowChanged(args) {
      this.gParams.gridDSPhieuRowSelected = args;
      if (args) {
        try {
          this.gParams.txtNoiDung = args["ND_THUCHIEN"];
          this.gParams.txtGhiChu = args["GHICHU"];
          this.gParams.giaophieu_id = parseInt(args["PHIEU_ID"]);
          this.gParams.dtDVNV_GP = await (
            await API.getDSDonViNVGP(this.axios, {
              phieuId: this.gParams.giaophieu_id,
              huongGiaoTiepTheoId: this.gParams.huonggiao_id,
            })
          ).data.data;       
          if (this.gParams.dtDVNV_GP && this.gParams.dtDVNV_GP.length) {
            this.gParams.cboDonVi = this.gParams.dtDVNV_GP[0]["DONVI_ID"] == null ? "" : this.gParams.dtDVNV_GP[0]["DONVI_ID"].toString();           
          }
        } catch (error) {
          this.$toast.error(error.data.message)
        }
      }
    },
    gridViewDSPhieuSelectItemChanged(args) {
      this.gParams.gridDSPhieuItemSelected = args;
    },
    async LAY_DS_HUONGGIAO() {
      try {
        this.loading(true);
        let response = await API.getDSHuongGiao(this.axios, {
          huongGiaoId: this.gParams.huonggiao_id,
        });
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.cboHuongGiao = response.data.data;
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async LAY_DS_DONVI_GIAOPHIEU(huonggiao_id, nhanvien_id, donvi_nhan_id) {
      try {
        this.loading(true);
        let response = await API.getDSDonViGP(this.axios, {
          huongGiaoId: huonggiao_id,
          nhanVienId: nhanvien_id,
          donViId: donvi_nhan_id,
        });
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.cboDonVi = response.data.data;
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async HienThi_DanhSach() {
      if (this.gParams.cboTrangThai == 1) {
        this.enabledComponent.tsbtnHuyGiao = false;
        this.enabledComponent.tsbtnTimKiem = true;
        this.enabledComponent.tsbtnGiaoPhieu = true;
      } else {
        this.enabledComponent.tsbtnHuyGiao = true;
        this.enabledComponent.tsbtnTimKiem = false;
        this.enabledComponent.tsbtnGiaoPhieu = false;
      }
      if (!this.gParams.cboHuongGiao) return;
      this.gParams.trangthai = parseInt(this.gParams.cboTrangThai);
      this.gParams.huonggiao_id = parseInt(this.gParams.cboHuongGiao);
      this.gParams.dt = [];
      switch (this.gParams.trangthai) {
        case 1:
          this.gParams.ds = await this.getDSGiaoPhieu(
            this.gParams.huonggiao_id,
            this.$auth.getNhanVienID(),
            this.$auth.getDonViID(),
            1
          );
          break;
        case 2:
          this.gParams.ds = await this.getDSGiaoPhieu(
            this.gParams.huonggiao_id,
            this.$auth.getNhanVienID(),
            this.$auth.getDonViID(),
            2
          );
          break;
      }
      this.gridDSPhieu = this.gParams.ds;
    },
    async getDSGiaoPhieu(huongGiaoId, nhanVienId, donViId, ttphId) {
      try {
        this.loading(true);
        let response = await API.getDSGiaoPhieu(this.axios, {
          huongGiaoId: huongGiaoId,
          nhanVienId: nhanVienId,
          donViId: donViId,
          ttphId: ttphId,
        });
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          return response.data.data || [];
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    PHAILAM(code) {
      if (this.gParams.dtThaoTac.length) {
        let result = this.gParams.dtThaoTac.filter(
          (e) => e.ENABLE == 1 && e.CODE == code
        );
        if (result.length) {
          return true;
        }
      }
      return false;
    },
    clearText() {
      this.gParams.txtGhiChu = "";
      this.gParams.txtNoiDung = "";
      this.gParams.txtMaOTPChon = "";
      this.gParams.dtpNgayGiao = new Date();
    },
    async cboTrangThaiChanged(object) {
      if (object.value) {
        if (this.gParams.load) {
          await this.HienThi_DanhSach();
        }
        if (this.gridDSPhieu.length <= 0) {
          this.gParams.txtMaOTPChon = "";
          this.gParams.txtNoiDung = "";
        }
      }
    },
    async cboHuongGiaoChanged(object) {      
      if (object.value) {
        try {
          this.$root.loading(true);
          this.gParams.huonggiao_id = parseInt(object.value);
          let response = await API.getDSThaoTacHuongGiao(this.axios, {
            huongGiaoId: this.gParams.huonggiao_id,
          });
          this.gParams.dtThaoTac = response.data.data;
          if (this.PHAILAM("DENGHI_CHUNGTU")) {
            this.gParams.lay_chungtu = true;
            this.gParams.colSoPhieuCT = true;
          } else {
            this.gParams.lay_chungtu = false;
            this.gParams.colSoPhieuCT = false;
          }
          console.log(this.gParams.lay_chungtu  , " =  this.gParams.lay_chungtu ")
          await this.LAY_DS_DONVI_GIAOPHIEU(
            this.gParams.huonggiao_id,
            this.$auth.getNhanVienID(),
            this.$auth.getDonViID()
          );
          this.gParams.cboDonVi = this.setFirstValue(this.cboDonVi, "DONVI_ID");
       
        } catch (error) {
          console.log(error);
          this.$toast.error(error.error_code);
        } finally {
          this.$root.loading(false);
        }
      }
    },
    async cboDonViChanged(object) {
      if (object.value) {
        try {
          this.$root.loading(true);
          let donviId = parseInt(object.value);
          let response = await API.getDSNVGPThaoTac(this.axios, {
            huongGiaoId: this.gParams.huonggiao_id,
            donViId: donviId,
          });
          this.cboNhanVienXL = response.data.data;           
          if (
            this.gParams.dtDVNV_GP.length &&
            this.gParams.dtDVNV_GP[0].hasOwnProperty("NHANVIEN_ID") && 
            this.gParams.dtDVNV_GP[0]["NHANVIEN_ID"] != null &&
            this.gParams.dtDVNV_GP[0]["NHANVIEN_ID"] != ""
          ) {
            this.gParams.cboNhanVienXL =  this.gParams.dtDVNV_GP[0]["NHANVIEN_ID"]
          }else{
            this.gParams.cboNhanVienXL = this.setFirstValue(
              this.cboNhanVienXL,
              "NHANVIEN_ID"
            );
          }
        } catch (error) {
          console.log(error);
          this.$toast.error(error.error_code);
        } finally {
          this.$root.loading(false);
        }
      }
    },
    async LAY_GT_THAMSO_MD_MATS(maTs) {
      let rs = await API.getGTThamSoMDMATS(this.axios, maTs)
      if (rs.data.data == null || rs.data.data.length == 0) {
        return "0"
      } else {
        return rs.data.data[0].TEN_TS
      }
    },
    async GiaoPhieu() {
      let response = await API.getDateCurrent(this.axios, {});
      let dateSystem = this.stringToDate(response.data.data);
      let nam_giao = dateSystem.getFullYear();
      let thang_giao = dateSystem.getMonth();
      let ngay_giao = dateSystem.getDate();
      let ngay_ht = this.gParams.dtpNgayGiao.getDate();
      let thang_ht = this.gParams.dtpNgayGiao.getMonth();
      let nam_ht = this.gParams.dtpNgayGiao.getFullYear();
      if (nam_ht < nam_giao) {
        this.$toast.error("Ngày giao không được lớn hơn ngày hiện tại");
        this.gParams.dtpNgayGiao = dateSystem;
        return;
      } else {
        if (nam_giao == nam_ht) {
          if (thang_giao > thang_ht) {
            this.$toast.error("Tháng giao không được lớn hơn tháng hiện tại");
            this.gParams.dtpNgayGiao = dateSystem;
            return;
          } else {
            if (thang_giao == thang_ht) {
              if (ngay_giao > ngay_ht) {
                this.$toast.error("Ngày giao không được lớn hơn ngày hiện tại");
                this.gParams.dtpNgayGiao = dateSystem;
                return;
              }
            }
          }
        }
      }
     
      if (!this.gParams.gp_ds) {
        /* Giao đơn lẻ */
        let ttph_id;
        this.gParams.bGiaoPhieu = false;
        let ma_otp = "";
        let fileJson = "";
        let huonggiao_id_phieu = 0;
        if (this.gParams.gridDSPhieuRowSelected) {
          let phieu = this.gParams.gridDSPhieuRowSelected;         
          huonggiao_id_phieu = parseInt(phieu["HUONGGIAO_ID"]);
          this.gParams.giaophieu_id = parseInt(phieu["PHIEU_ID"]);
          this.gParams.phieu_cha_id = this.isNullOrEmpty(phieu["PHIEU_CHA_ID"])  ? 0 : parseInt(phieu["PHIEU_CHA_ID"]);
          this.gParams.loaiphieu_id = parseInt(phieu["LOAIPHIEU_ID"]);
          this.gParams.quyettoan_id = this.isNullOrEmpty(phieu["QUYETTOAN_ID"]) ? 0 : parseInt(phieu["QUYETTOAN_ID"]);
          this.gParams.chungtu_id = this.isNullOrEmpty(phieu["CHUNGTU_ID"]) ? 0 : parseInt(phieu["CHUNGTU_ID"]);
          this.gParams.denghi_id = this.isNullOrEmpty(phieu["DENGHI_ID"]) ? 0 : parseInt(phieu["DENGHI_ID"]);
          this.gParams.donhang_id = this.isNullOrEmpty(phieu["DONHANG_ID"]) ? 0 : parseInt(phieu["DONHANG_ID"]);
          this.gParams.dangky_id = this.isNullOrEmpty(phieu["DANGKY_ID"]) ? 0 : parseInt(phieu["DANGKY_ID"]);
          this.gParams.ma_phieu = phieu["MA_PHIEU"];
          ttph_id = phieu["TTPH_ID"];
          if (this.PHAILAM("DENGHI_CHUNGTU")) {
            this.gParams.lay_chungtu = true;
            this.gParams.sophieu = phieu["SOPHIEU"];
          }
          this.gParams.bGiaoPhieu = true;
        }
       
        if (this.gParams.bGiaoPhieu) {
          let KTXacThucKySo = await (await API.getKiemTraXacThucKySo(this.axios, {
              phieuGiaoId: this.gParams.giaophieu_id,
              huongGiaoId: huonggiao_id_phieu,
            })
          ).data.data;
          if(KTXacThucKySo != 1){
            ma_otp = this.gParams.txtMaOTPChon.trim();
            if (this.isNullOrEmpty(ma_otp)) {
              return this.$toast.error(
                "Vui lòng nhập OTP để thực hiện giao phiếu"
              );
            }
          }
         
          let noidung_giao = this.gParams.txtNoiDung;
          let ghi_chu = "";
          let donvi_id = this.gParams.cboDonVi;
          let nhanvien_nhan_id = this.gParams.cboNhanVienXL;
          let gp = await this.GiaoPhieu_KS(
            ma_otp,
            this.gParams.giaophieu_id,
            huonggiao_id_phieu,
            this.gParams.huonggiao_id,
            noidung_giao,
            ghi_chu,
            donvi_id,
            nhanvien_nhan_id,
            fileJson,
            this.gParams.loaiphieu_id,
            this.gParams.denghi_id,
            this.gParams.chungtu_id,
            this.gParams.quyettoan_id,
            this.gParams.ma_phieu,
            this.gParams.sophieu
          );
        
          if (gp == "OK") {
            this.$toast.success("Đã giao phiếu thành công");
            this.clearText();
            await this.HienThi_DanhSach();
          } else if (gp == "WAIT") { // Chờ ký số
            
          }else{
            this.$toast.error(gp);
          }
        } else {
          this.$toast.error("Bạn chưa chọn phiếu giao");
        }
        /* End Giao đơn lẻ */
      } else {
        let dt = this.gridDSPhieu;
        if (!dt.length) {
          return this.$toast.error("Không có phiếu để thực hiện");
        }
        let ds_chk = this.$refs.gridViewDSPhieu.getSelectedRecords();
        if (!ds_chk.length) {
          return this.$toast.error("Bạn chưa chọn phiếu để thực hiện");
        }
        if (this.PHAILAM("DENGHI_CHUNGTU")) {
          this.gParams.lay_chungtu = true;
          console.log(this.gParams.lay_chungtu , " = this.gParams.lay_chungtu")
        }
        let boqua = false;
        let ma_otp = this.gParams.txtMaOTPChon.trim();
        if (this.isNullOrEmpty(ma_otp)) {
          return this.$toast.error("Vui lòng nhập OTP để thực hiện giao phiếu");
        }
        let noidung_giao = this.gParams.txtNoiDung.trim();
        let donvi_id = parseInt(this.gParams.cboDonVi);
        let nhanvien_nhan_id = parseInt(this.gParams.cboNhanVienXL);
        let ghi_chu = "";
        let dem = 0;
        let tong = ds_chk.length;
        for (const dr of ds_chk) {
          let ttph_id;
          let bGiaoPhieu = false;
          let fileJson = "";
          let huonggiao_id_phieu = 0;

          huonggiao_id_phieu = parseInt(dr["HUONGGIAO_ID"].toString());
          let p_giaophieu_id = parseInt(dr["PHIEU_ID"].toString());
          let p_phieu_cha_id = this.isNullOrEmpty(dr["PHIEU_CHA_ID"].toString())
            ? 0
            : parseInt(dr["PHIEU_CHA_ID"].toString());
          let p_loaiphieu_id = parseInt(dr["LOAIPHIEU_ID"].toString());
          let p_quyettoan_id = this.isNullOrEmpty(dr["QUYETTOAN_ID"].toString())
            ? 0
            : parseInt(dr["QUYETTOAN_ID"].toString());
          let p_chungtu_id = this.isNullOrEmpty(dr["CHUNGTU_ID"].toString())
            ? 0
            : parseInt(dr["CHUNGTU_ID"].toString());
          let p_denghi_id = this.isNullOrEmpty(dr["DENGHI_ID"].toString())
            ? 0
            : parseInt(dr["DENGHI_ID"].toString());
          let p_donhang_id = this.isNullOrEmpty(dr["DONHANG_ID"].toString())
            ? 0
            : parseInt(dr["DONHANG_ID"].toString());
          let p_dangky_id = this.isNullOrEmpty(dr["DANGKY_ID"].toString())
            ? 0
            : parseInt(dr["DANGKY_ID"].toString());
          let p_ttph_id = parseInt(dr["TTPH_ID"].toString());
          let p_ma_phieu = dr["MA_PHIEU"].toString();
          let p_sophieu = "";
          if (this.gParams.lay_chungtu) p_sophieu = dr["SOPHIEU"].toString();

          bGiaoPhieu = true;
          let gp = await this.GiaoPhieu_KS(
            ma_otp,
            p_giaophieu_id,
            huonggiao_id_phieu,
            this.gParams.huonggiao_id,
            noidung_giao,
            ghi_chu,
            donvi_id,
            nhanvien_nhan_id,
            fileJson,
            p_loaiphieu_id,
            p_denghi_id,
            p_chungtu_id,
            p_quyettoan_id,
            p_ma_phieu,
            p_sophieu
          );

          if (gp == "OK") {
            dem += 1;
          } else {
            if (!boqua) {
              let isConfirm = await this.$bvModal.msgBoxConfirm(
                `Giao phiếu bị lỗi: ${gp} . Bạn có muốn tiếp tục không?`,
                {
                  okVariant: "success",
                  okTitle: "Đồng ý",
                  cancelTitle: "Hủy",
                  centered: true,
                }
              );
              if (isConfirm) {
                boqua = true;
              } else {
                this.clearText();
                await this.HienThi_DanhSach();
                return 
              }
            }
          }
        }
        this.$toast.success(`Đã giao phiếu thành công ${dem}/${tong}`);
        this.clearText();
        await this.HienThi_DanhSach();
      }
    },
    async xacNhanKySoSmartCA(){
      this.$bvModal.hide('popupNhapUser')
      try{
        this.loading(true)
        // Đoan này đã call rồi, comment lúc quên đở thắc mắc
        // if (string.IsNullOrEmpty(mota) || mota == "[]")
        //     tdan.EXECUTE_SQL(@"update {?DB12}.giaophieu set mota = :vmota where phieu_id = :vphieu_id and (mota is null or mota = '[]')", "vmota", fileJson, "vphieu_id", giaophieu_id);
        let jsonFileNew = await ( await API.kySoSmartCAPhieuVT(this.axios, {
              taiKhoanKs: this.gParams.taikhoan_ks,
              matKhauKs: this.gParams.matkhau_ks,
              phieuIds: this.gParams.giaophieu_id,
              moTa: this.saveMota,
              fileJson: this.saveFileJson
            })
        ).data.data;

        if (!jsonFileNew.startsWith("ERR")) {
          let result = await (await API.postGiaoPhieu(this.axios, {
            phieuGiaoId: this.gParams.giaophieu_id,
            huongGiaoId: this.gParams.huonggiao_id,
            ndGiao: this.gParams.txtNoiDung,
            ghiChu: "",
            otp: "",
            donViNhanId: this.gParams.cboDonVi,
            nhanVienNhanId: this.gParams.cboNhanVienXL,
            moTa: jsonFileNew,
            themCks: 0,
            donViId: this.$auth.getDonViID(),
            nguoiCn: this.$auth.getUserName(),
            nhanVienId: this.$auth.getNhanVienID(),
            mayCn: "",
            ipCn: "",
          })).data.data;   
          
          if(!this.gParams.gp_ds) {
            if (result == "OK") {              
              this.$toast.success("Đã giao phiếu thành công");
              this.clearText();
              await this.HienThi_DanhSach();
            } else {
              this.$toast.error(result);
            }
          }else{
            if (result == "OK") {              
              this.$toast.success("Đã giao phiếu thành công");             
            } else {
              this.$toast.error(result);
            }
          }        
        } else {
          this.$toast.error("Ký số không thành công: " +  jsonFileNew)
        }
      }catch(e){        
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },

    async GiaoPhieu_KS(
      ma_otp,
      giaophieu_id,
      huonggiao_id_phieu,
      huonggiao_id,
      noidung_giao,
      ghi_chu,
      donvi_id,
      nhanvien_nhan_id,
      fileJson,
      p_loaiphieu_id,
      denghi_id,
      chungtu_id,
      quyentoan_id,
      maphieu,
      sophieu
    ) {
      try {
        this.$root.loading(true);
        let isKySo = await (
          await API.getKiemTraBoQuaKySo(this.axios, {
            phieuGiaoId: giaophieu_id,
            huongGiaoId: huonggiao_id_phieu,
          })
        ).data.data;
        
        if (isKySo[0] == 1) {
          let result = await (
            await API.postGiaoPhieu(this.axios, {
              phieuGiaoId: giaophieu_id,
              huongGiaoId: huonggiao_id,
              ndGiao: noidung_giao,
              ghiChu: ghi_chu,
              otp: ma_otp,
              donViNhanId: donvi_id,
              nhanVienNhanId: nhanvien_nhan_id,
              moTa: fileJson,
              themCks: 0,
              donViId: this.$auth.getDonViID(),
              nguoiCn: this.$auth.getUserName(),
              nhanVienId: this.$auth.getNhanVienID(),
              mayCn: "",
              ipCn: "",
            })
          ).data.data;
          return result;
        } else {
          let mota = "";
          let dt = await API.getThongTinPhieu(this.axios, {
            phieuId: giaophieu_id,
          });
          if (dt.data.data.length) {
            mota = dt.data.data[0]["MOTA"];
          } else {
            return this.$toast.error("Không có thông tin phiếu!");
          }
          this.gParams.loi = "";
          if (this.isNullOrEmpty(mota) || mota == "[]") {
            fileJson = await this.TaoJsonMota(
              huonggiao_id_phieu,
              p_loaiphieu_id,
              giaophieu_id,
              denghi_id,
              chungtu_id,
              quyentoan_id,
              maphieu,
              sophieu
            );            
          } else {
            fileJson = mota;
          }
          
          if (this.gParams.loi != "") {
            return this.gParams.loi;
          }
          if (this.isNullOrEmpty(fileJson)){
            return "Không tìm thấy dữ liệu File Json";
          }

          let KTXacThucKySo = await (await API.getKiemTraXacThucKySo(this.axios, {
              phieuGiaoId: this.gParams.giaophieu_id,
              huongGiaoId: huonggiao_id_phieu,
            })
          ).data.data;
          
          if(KTXacThucKySo == 1){
            this.gParams.taikhoan_ks = await ( await API.getTaiKhoanKySo(this.axios,[])).data.data;
            this.$root.loading(false);     
            this.saveFileJson = fileJson  
            this.saveMota = mota
            this.$bvModal.show('popupNhapUser')
            return "WAIT" // chờ ký số
          }else{
            let jsonFileNew = await API.postAnhChuKyPhieuVT(this.axios, {
                phieuId: giaophieu_id,
                moTa: fileJson,
              });    
            
            if (!jsonFileNew.data.data.startsWith("ERR")) {
              let result = await API.postGiaoPhieu(this.axios, {
                phieuGiaoId: giaophieu_id,
                huongGiaoId: huonggiao_id,
                ndGiao: noidung_giao,
                ghiChu: ghi_chu,
                otp: ma_otp,
                donViNhanId: donvi_id,
                nhanVienNhanId: nhanvien_nhan_id,
                moTa: jsonFileNew.data.data,
                themCks: 0,
                donViId: this.$auth.getDonViID(),
                nguoiCn: this.$auth.getUserName(),
                nhanVienId: this.$auth.getNhanVienID(),
                mayCn: "",
                ipCn: "",
              });
              return result.data.data;
            } else {
              return "Thêm ảnh chữ ký không thành công!";
            }
          }
        }
      } catch (error) {
        this.$toast.error(error.data.message);          
      } finally {
        this.$root.loading(false);
      }
    },
    async TaoJsonMota(
      huonggiao_id_phieu,
      loaiphieu_id,
      giaophieu_id,
      denghi_id,
      chungtu_id,
      quyentoan_id,
      ma_phieu,
      sophieu
    ) {
      try {
        let dsBaoCao = [];
        let fileJson = "";
        let response = await API.getDSPhieuBC(this.axios, {
          loaiPhieuId: loaiphieu_id,
          huongGiaoId: huonggiao_id_phieu,
        });
        let dtPhieuBC = response.data.data;
        if (dtPhieuBC.length) {
          for (const item of dtPhieuBC) {
            dsBaoCao.push({
              loaiphieubc_id: parseInt(item.loaiPhieuBcId),
              baocao_id: parseInt(item.baoCaoId),
              ma_bc: item.maBaoCao,
            });
          }
        } else {
          this.gParams.loi =
            "Chưa thiết lập loại phiếu, hướng giao. Vui lòng liên hệ admin!";
          return "";
        }
        
        let err = false; 
        let dataReport = []       
        for(let item of dsBaoCao){
          let loaiphieubc_id = item.loaiphieubc_id;
          let baocao_id = item.baocao_id;
          let ma_bc = item.ma_bc;
          dataReport.push({
            loaiphieubc_id: loaiphieubc_id,
            baocao_id: baocao_id,
            ma_bc: ma_bc,
            giaophieu_id: giaophieu_id,
            denghi_id: denghi_id,
            chungtu_id: chungtu_id,
            quyentoan_id: quyentoan_id,
            ma_phieu: ma_phieu,
            sophieu: sophieu,
            loaiphieu_id: loaiphieu_id,
            tsSoHoa: this.tsSoHoa,
            lay_chungtu: this.gParams.lay_chungtu ? 1 : 0
          })
          // let mota = await this.XuatPdfPhieu(
          //   loaiphieubc_id,
          //   baocao_id,
          //   ma_bc,
          //   giaophieu_id,
          //   denghi_id,
          //   chungtu_id,
          //   quyentoan_id,
          //   ma_phieu,
          //   sophieu,
          //   loaiphieu_id,
          //   this.tsSoHoa,
          // );
          // if (mota != null && mota != "") {
          //   lstMota.push(mota);
          // } else{
          //   err = true;
          //   break
          // } 
        };
     
        let response_rp = await API.postXuatReport(this.axios, dataReport);
        console.log(response_rp , " = response_rp")
        let lstMota = response_rp.data.data
        // if (err) {
        //   this.gParams.loi = "Có lỗi khi upload file ký số. Vui lòng thử lại! ";
        //   return "";
        // }
        fileJson = JSON.stringify(lstMota);
        return fileJson;
      } catch (error) {       
        this.$toast.error(error.data.message)
        this.gParams.loi =
          "Có lỗi trong quá trình xử lý dữ liệu : " + err.error_code;
        return "";
      }
    },
    async XuatPdfPhieu(
      loaiphieubc_id,
      baocao_id,
      ma_bc,
      phieu_id,
      denghi_id,
      chungtu_id,
      quyentoan_id,
      ma_phieu,
      sophieu,
      loaiphieu_id,
      tsSoHoa
    ) {
      try {
        let response = await API.postXuatReport(this.axios, {
          loaiphieubc_id,
          baocao_id,
          ma_bc,
          giaophieu_id: phieu_id,
          denghi_id,
          chungtu_id,
          quyentoan_id,
          ma_phieu,
          sophieu,
          loaiphieu_id,
          tsSoHoa,
          lay_chungtu :  this.gParams.lay_chungtu ? 1 : 0
        });
        return response.data.data;
      } catch (error) {
        this.$toast.error(error.data.message);
        return null;
      }
    },
    async TaoMoiFileKySo(phieu_cha_id) {
      if (!phieu_cha_id) {
        this.$toast.error("Không có thông tin phiếu!");
        return this.$emit("eventFileKySo", "ERR");
      }
      let nhanvien_giao_id = 0;
      let huonggiao_id = 0;
      let fileJson = "";
      let dt = (
        await API.getThongTinPhieu(this.axios, {
          phieuId: phieu_cha_id,
        })
      ).data.data;
      if (dt.length) {
        nhanvien_giao_id = dt[0]["NHANVIEN_GIAO_ID"];
        huonggiao_id = this.isNullOrEmpty(dt[0]["HUONGGIAO_ID"])
          ? 0
          : parseInt(dt[0]["HUONGGIAO_ID"]);
      } else {
        this.$toast.error("Không có thông tin phiếu!");
        return this.$emit("eventFileKySo", "ERR");
      }
      fileJson = await this.TaoJsonMotaPhieuCha();
      if (this.isNullOrEmpty(fileJson)) {
        // this.$toast.error("Có lỗi khi tạo dữ liệu file!");
        return this.$emit("eventFileKySo", "Có lỗi khi tạo dữ liệu file!");
      }
    },
    TaoJsonMotaPhieuCha() {
      return "";
    },
    /* end methods*/
  },
  mounted() {},
};
</script>

<style src="./frmGiaoPhieu.scss">
</style>