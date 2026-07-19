<template src="./GiaoPhieuKhaoSatTB.html"></template>
<style src="./GiaoPhieuKhaoSatTB.scss"></style>
<script>
import API from "../api/GiaoNhanPhieuKhaoSatTB.js";
import { Loai_NV } from "../../../utils/Enum.js";
import ComboboxGrid from "../../QLTN/components/ComboboxGrid";
import ReadFileKSTBPopup from "./components/ReadFileKSTBPopup/ReadFileKSTBPopup.vue";
import moment from "moment";
export default {
  name: "GiaoPhieuKhaoSatThueBao",
  components: {
    appCombobox: ComboboxGrid,
    appReadFile: ReadFileKSTBPopup,
  },
  data() {
    return {
      vgiaophieu_ks_id: 0,
      hdtb_id: "",
      hdkh_id: 0,
      baohong_id: 0,
      ct_suyhao_id: 0,
      dichvuvt_id: 0,
      donviID: 0,
      kieu: 0,
      donviIDCHA: -1,
      muc_id_cha: 0,
      nodeText: "",
      donviBC: 0,
      kt_load: true,
      vnhom_id: 0,
      l_thuebao_id: "",
      vsvlan: "",
      vcvlan: "",
      vsite_id: "",
      vthuebao_id: 0,
      ma_tb: "",
      OB_HUY_LUOTGIAO_PHIEU_DAHT: -1,
      //giaophieu
      chkbDVFilter: false,
      chkDichVuVT: false,
      chkDonVi: false,
      chkDK2: false,
      chkDK3: false,
      chkTkLoaiHinh: false,
      cboDKTraCuu: [],
      cboDKTraCuuValue: "",
      cboDK2: [],
      cboDK2Value: "",
      cboDK3: [],
      cboDK3Value: "",
      cboTKDichVu: [],
      cboTKDichVuValue: "",
      cboTKLoaiHinh: [],
      cboTKLoaiHinhValue: "",
      cboDichVuVT: [],
      cboDichVuVTValue: "",
      cboDonVi: [],
      cboDonViValue: "",
      cboNguoiGiao: [],
      cboNguoiGiaoValue: "",
      txtNguoiGiao: "",
      cboNguoiNhan: [],
      cboNguoiNhanValue: "",
      cboHinhThuc: [],
      cboHinhThucValue: "",
      cboHinhThucKS_DaGiao: [],
      cboHinhThucKS_DaGiaoValue: "",
      txtTextBoxTC: "",
      txtTraCuu2: "",
      txtDK3: "",
      txtMaKH: "",
      txtMaTB: "",
      txtMa_lt: "",
      txtGhiChu_GP: "",
      dtThueBao: [],
      dtThueBaoSelectedItems: [],
      dtpTuNgay: new Date(),
      dtpDenNgay: new Date(),
      dsNhanVienGiao: [],
      dsNhanVienNhan: [],
      //dagiao
      dtPhieuKS: [],
      dtPhieuDaGiao: [],
      dtPhieuDaGiaoSelectedRow: "",
      chkNgayGiao: false,
      chkNguoiGiao: false,
      chkNguoiNhan: false,
      dtpGiaoTu: new Date(),
      dtpGiaoDen: new Date(),
      cboNguoiGiao_DG: [],
      cboNguoiGiao_DGValue: "",
      cboNguoiNhan_DG: [],
      cboNguoiNhan_DGValue: "",
      isTabGiaoPhieu: true,
      dtThueBaoTotalRows: 0,
      dtPhieuKSTotalRows: 0,
      PhieuKSSelectedRow: "",
      isActionGiaoPhieu: false
    };
  },
  watch: {
    async cboTKDichVuValue() {
      if (this.cboTKDichVuValue) {
        console.log("cboTKDichVuValue", this.cboTKDichVuValue);
        let response = await API.getDSLoaiHinh(this.axios, {
          dichVuVTId: this.cboTKDichVuValue,
        });
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data.length > 0) {
            this.cboTKLoaiHinh = response.data.data;
            this.cboTKLoaiHinhValue = response.data.data[0].LOAITB_ID;
          }
        }
      }
    },
  },
  computed: {
    getDSDTThueBao() {
      let result = [];
      if (this.chkbDVFilter) {
        if (this.chkTkLoaiHinh) {
          result = this.dtThueBao.filter(
            (e) =>
              e.DICHVUVT_ID == this.cboTKDichVuValue &&
              e.LOAITB_ID == this.cboTKLoaiHinhValue
          );
          return result;
        } else {
          result = this.dtThueBao.filter(
            (e) => e.DICHVUVT_ID == this.cboTKDichVuValue
          );
          return result;
        }
      }

      if (result.length == 0) return this.dtThueBao;
      return result;
    },
  },
  async mounted() {
    try {
      this.$root.loading(true);
      let response = await API.getDSDichVuVTGP(this.axios);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data !== undefined
      ) {
        if (response.data.data.length > 0) {
          this.cboTKDichVu = response.data.data;
          this.cboTKDichVuValue = response.data.data[0].DICHVUVT_ID;
        }
      }

      let rsDVVT = await API.getDSDVVT(this.axios);
      if (
        rsDVVT.data.error_code === "BSS-00000000" &&
        rsDVVT.data.data !== undefined
      ) {
        if (rsDVVT.data.data.length > 0) {
          this.cboDichVuVT = rsDVVT.data.data;
          this.cboDichVuVTValue = rsDVVT.data.data[0].DICHVUVT_ID;
        }
      }

      let rsSearch = await API.getOptionSearchs(this.axios);
      if (
        rsSearch.data.error_code === "BSS-00000000" &&
        rsSearch.data.data !== undefined
      ) {
        if (rsSearch.data.data.length > 0) {
          this.cboDKTraCuuValue =
            this.cboDK2Value =
            this.cboDK3Value =
              rsSearch.data.data[0].KIEUTK_ID;
          this.cboDKTraCuu = this.cboDK2 = this.cboDK3 = rsSearch.data.data;
        }
      }

      let rsDSDonViVT = await API.getDSDonViVT(this.axios);
      if (
        rsDSDonViVT.data.error_code === "BSS-00000000" &&
        rsDSDonViVT.data.data !== undefined
      ) {
        if (rsDSDonViVT.data.data.length > 0) {
          this.cboDonVi = rsDSDonViVT.data.data;
          this.cboDonViValue = rsDSDonViVT.data.data[0].DONVI_ID;
        }
      }
      //thieu tham so mac dinh
      if (this.dichvuvt_id !== 0) {
        this.chkDichVuVT = true;
        this.cboDichVuVTValue = this.dichvuvt_id;
      }
      let rsDSNVLNV = await API.getDSNVTheoLNV(this.axios, {
        dsLoaiNvId: Loai_NV.NHANVIEN_KHAOSAT_KH,
        donViId: this.$auth.getNhanVienID(),
      });
      if (
        rsDSNVLNV.data.error_code === "BSS-00000000" &&
        rsDSNVLNV.data.data !== undefined
      ) {
        if (rsDSNVLNV.data.data.length > 0) {
          let nhanVienNhan = rsDSNVLNV.data.data.find(
            (item) => item.NHANVIEN_ID == this.$auth.getNhanVienID()
          );
          if (nhanVienNhan) {
            let nhanVienNhanIdx = rsDSNVLNV.data.data.findIndex(
                (item) => item.NHANVIEN_ID == this.$auth.getNhanVienID()
            );
            rsDSNVLNV.data.data.splice(nhanVienNhanIdx, 1);
            rsDSNVLNV.data.data.unshift(nhanVienNhan);
            this.cboNguoiNhan = this.cboNguoiNhan_DG = rsDSNVLNV.data.data;
          } else {
            this.cboNguoiNhan = this.cboNguoiNhan_DG = rsDSNVLNV.data.data;
          }
        }
      }

      let rsDSNV = await API.getDSNV(this.axios, {
        nhanVienId: this.$auth.getNhanVienID(),
      });
      if (
        rsDSNV.data.error_code === "BSS-00000000" &&
        rsDSNV.data.data !== undefined
      ) {
        if (rsDSNV.data.data.length > 0) {
          this.cboNguoiGiao = this.cboNguoiGiao_DG = rsDSNV.data.data;
          let nhanVienNhan = rsDSNV.data.data.find(
            (item) => item.NHANVIEN_ID == this.$auth.getNhanVienID()
          );
          if(nhanVienNhan){
            this.txtNguoiGiao = nhanVienNhan.TEN_NV
          }
        //   if (nhanVienNhan) {
        //     let nhanVienNhanIdx = rsDSNV.data.data.findIndex(
        //         (item) => item.NHANVIEN_ID == this.$auth.getNhanVienID()
        //     );
        //     rsDSNV.data.data.splice(nhanVienNhanIdx, 1);
        //     rsDSNV.data.data.unshift(nhanVienNhan);
        //     this.cboNguoiNhan = this.cboNguoiNhan_DG = rsDSNV.data.data;
        //   } else {
        //     this.cboNguoiNhan = this.cboNguoiNhan_DG = rsDSNV.data.data;
        //   }
        }
      }

      let rsHTKS = await API.getDSHinhThucKS(this.axios, {
        kieu: 1,
      });
      if (
        rsHTKS.data.error_code === "BSS-00000000" &&
        rsHTKS.data.data !== undefined
      ) {
        if (rsHTKS.data.data.length > 0) {
          this.cboHinhThuc = this.cboHinhThucKS_DaGiao = rsHTKS.data.data.map(item => ({...item,HINHTHUC_KS_TXT: `${item.HINHTHUCKS_ID} - ${item.HINHTHUC_KS}`}));
          this.cboHinhThucValue = this.cboHinhThucKS_DaGiaoValue =
            rsHTKS.data.data[0].HINHTHUCKS_ID;
        }
      }
    } catch (error) {
      console.log(error);
    } finally {
      this.$root.loading(false);
    }
  },
  methods: {
    isNullOrEmpty(value) {
      return value === "" || value === null;
    },
    formatDate(value) {
      return moment(value).format("DD/MM/yyyy");
    },
    onDsNhanVienGiaoChanged(value) {
      if (value) this.cboNguoiGiaoValue = value;
    },
    onDsNhanVienNhanChanged(value) {
      if (value) this.cboNguoiNhanValue = value;
    },
    onCBONguoiGiaoDGChanged(value) {
      if (value) this.cboNguoiGiao_DGValue = value;
    },
    onDsNhanVienNhanDGChanged(value) {
      if (value) this.cboNguoiNhan_DGValue = value;
    },
    dtThueBaoSelectedRowChanged(value) {
      if (value) {
        this.ma_tb = this.txtMaTB = value.MA_TB;
        this.txtMa_lt = value.MA_LT;
        this.vthuebao_id = value.THUEBAO_ID;
      }
    },
    dtThueBaoSelectedItemsChanged(args) {
      this.dtThueBaoSelectedItems = args;
    },

    async dtPhieuKSSelectedRowChanged(value) {
      if (this.dtPhieuKS.length == 0) this.dtPhieuDaGiao = [];
      if (value) {
        this.PhieuKSSelectedRow = value;
        try {
          this.$root.loading(true);
          let response = await API.getDSPhieuDG(this.axios, {
            giaoPhieuKSId: value.GIAOPHIEU_KS_ID,
            trangThai: 0,
          });
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            this.dtPhieuDaGiao = [];
            if (response.data.data.length > 0) {
              this.dtPhieuDaGiao = response.data.data;
            }
          }
        } catch (error) {
          this.dtPhieuDaGiao = [];
          console.log(error);
          this.$toast.error(error.data.error_code);
        } finally {
          this.$root.loading(false);
        }
      }
    },

    dtPhieuDaGiaoSelectedRowChanged(value) {
      console.log(value);
      if (value) this.dtPhieuDaGiaoSelectedRow = value;
    },

    dtThueBaoGridChanged(args) {
      this.getDSDBTB(args.pageIndex + 1, args.pageSize);
    },
    dtPhieuKSGridChanged(args) {
      this.getDSDaGiaoKS(args.pageIndex + 1, args.pageSize);
    },
    async getDSDBTB(pageNum = 1, pageSize) {
      let conditions = [];
      let objConditionOne = {
        key: this.cboDKTraCuuValue,
        val: this.txtTextBoxTC.trim(),
      };
      conditions.push(objConditionOne);
      if (this.chkDK2) {
        conditions.push({
          key: this.cboDK2Value,
          val: this.txtTraCuu2.trim(),
        });
      }
      if (this.chkDK3) {
        conditions.push({
          key: this.cboDK3Value,
          val: this.txtDK3.trim(),
        });
      }
      try {
        this.$root.loading(true);
        let data = {
          dieuKien: JSON.stringify(conditions),
          dichVuVTId: this.chkDichVuVT ? this.cboDichVuVTValue : null,
          donViId: this.chkDonVi ? this.cboDonViValue : null,
          pageNum,
          pageSize: pageSize ? pageSize : this.$refs.gridDTThueBao.pageSize,
          totalRow: 1,
        };
        let response = await API.getDSGPKSTB(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.dtThueBao = [];
          if (response.data.data.data.length > 0) {
            this.dtThueBao = response.data.data.data;
            this.dtThueBaoTotalRows = response.data.data.totalElement;
          }
          if (this.dtThueBao.length === 0) {
            this.dtThueBaoTotalRows = 0
            !this.isActionGiaoPhieu && this.$toast.error("Không tìm thấy thông tin trong danh bạ!");
          }
        }
      } catch (error) {
        this.dtThueBao = [];
        console.log(error);
        this.$toast.error(error.data.error_code);
      } finally {
        this.$root.loading(false);
      }
    },
    async getDSDaGiaoKS(pageNum = 1, pageSize) {
      try {
        this.$root.loading(true);
        let data = {
          nguoiGiaoId: this.chkNguoiGiao
            ? this.cboNguoiGiao_DGValue.NHANVIEN_ID
            : null,
          nguoiNhanId: this.chkNguoiNhan
            ? this.cboNguoiNhan_DGValue.NHANVIEN_ID
            : null,
          tuNgay: this.chkNgayGiao ? this.formatDate(this.dtpGiaoTu) : null,
          denNgay: this.chkNgayGiao ? this.formatDate(this.dtpGiaoDen) : null,
          hinhThucKSId: this.cboHinhThucKS_DaGiaoValue,
          pageNum,
          pageSize: pageSize ? pageSize : this.$refs.gridDTPhieuKS.pageSize,
          totalRow: 1,
        };
        let response = await API.getDSDaGiaoKS(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.dtPhieuKS = [];
          this.dtPhieuDaGiao = [];
          if (response.data.data.data.length > 0) {
            this.dtPhieuKS = response.data.data.data;
            this.dtPhieuKSTotalRows = response.data.data.totalElement;
          } else {
            this.$toast.error("Không có dữ liệu!");
          }
        }
      } catch (error) {
        this.dtPhieuKS = [];
        this.dtPhieuDaGiao = [];
        console.log(error);
        this.$toast.error(error.data.error_code);
      } finally {
        this.$root.loading(false);
      }
    },
    async timKiem() {
      if (this.isTabGiaoPhieu) {
        let itemTracuu = this.cboDKTraCuu.find(
          (e) => e.KIEUTK_ID == this.cboDKTraCuuValue
        );
        let textDKTraCuu = itemTracuu ? itemTracuu.KIEU_TK : "";
        let l_thuebao_id = "";
        let vsvlan = "";
        let vcvlan = "";
        let vsite_id = "";
        if ((textDKTraCuu == "Svlan/Cvlan")) {
          vsvlan = this.txtTextBoxTC.trim();
          vcvlan = this.txtMaKH.trim();
          if (vsvlan == "" && vcvlan == "") {
            return this.$toast.error(
              "Bạn chưa nhập thông tin Svlan và Cvlan để tra cứu !"
            );
          }
        } else if (textDKTraCuu == "Site ID") {
          vsite_id = this.txtTextBoxTC.trim();
          if (!vsite_id) {
            return this.$toast.error(
              "Bạn chưa nhập thông tin Site ID để tra cứu !"
            );
          }
        }
        this.getDSDBTB();
      } else {
        this.getDSDaGiaoKS();
      }
    },
    onTimKiemClicked() {
      this.isActionGiaoPhieu = false
      this.timKiem();
    },
    async onGiaoPhieuClicked() {
      const dsSelected_ThueBao = this.$refs.gridDTThueBao.getSelectedRecords() || [];
      if (dsSelected_ThueBao.length <= 0)
        return this.$toast.error("Bạn chưa chọn phiếu để khảo sát!");
      let dsTBSelected = this.dtThueBao.filter((item) =>
        this.dtThueBaoSelectedItems.includes(item.STT)
      );
      let dsTBSelectedMap = dsTBSelected.map((item) => {
        return {
          THUEBAO_ID: item.THUEBAO_ID,
          SO_DT: item.SO_DT,
          MA_TB: item.MA_TB,
          TEN_TB: "",//item.TEN_TB,
          DIACHI_LD: "",//item.DIACHI_LD,
        };
      });
      let data = {
        nguoiGiaoId: this.$auth.getNhanVienID(),
        nguoiNhanId: this.cboNguoiNhanValue.NHANVIEN_ID,
        ghiChu: this.txtGhiChu_GP,
        hinhThucKSId: this.cboHinhThucValue,
        dsThueBao: dsTBSelectedMap,
      };
      try {
        this.$root.loading(true);
        let response = await API.postGiaoPhieu(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data[0].CODE == 1) {
            this.$toast.success(response.data.data[0].MSG);
            this.isActionGiaoPhieu = true
            this.timKiem();
          } else {
            this.$toast.error(response.data.data[0].MSG);
          }
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.error_code);
      } finally {
        this.$root.loading(false);
      }
    },
    onDocFileClicked() {
      this.$refs.appReadFileKSTB.vhinhthucks_id = this.cboHinhThucValue;
      this.$refs.appReadFileKSTB.openDialog();
    },
    async onChapNhan(value) {
      console.log(value);
      if (value.CHAP_NHAN) {
        try {
          this.$root.loading(true);
          let data = {
            hinhThucKSId: this.cboHinhThucValue,
            pageNum: 1,
            pageSize: 1000000,
            totalRow: 0,
          };
          let response = await API.getDSFileTraVe(this.axios, data);
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            console.log(response.data.data.data);
            if (response.data.data.data.length > 0) {
              this.dtThueBao = response.data.data.data;
            } else {
              this.$toast.error("Không tìm thấy thông tin trong danh bạ!");
              this.dtThueBao = [];
            }
          }
        } catch (error) {
          console.log(error);
          this.$toast.error(error.data.error_code);
        } finally {
          this.$root.loading(false);
        }
      }
    },
    //nhớ chờ gọi hàm lấy tham sô cho DAHT
    async onHuyGiaoClicked() {
      if (this.dtPhieuKS.length > 0) {
        try {
          this.$root.loading(true);
          let data = {
            nguoiDungId: this.$auth.getNguoiDungID(),
            giaoPhieuKSId: this.PhieuKSSelectedRow.GIAOPHIEU_KS_ID,
            huyLuotGiaoPhieuDAHT: this.OB_HUY_LUOTGIAO_PHIEU_DAHT,
          };
          let response = await API.postHuyGiao(this.axios, data);
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            if (response.data.data[0].CODE == 1) {
              this.$toast.success(response.data.data[0].MSG);
              this.timKiem();
            } else {
              this.$toast.error(response.data.data[0].MSG);
            }
          }
        } catch (error) {
          console.log(error);
          this.$toast.error(error.data.error_code);
        } finally {
          this.$root.loading(false);
        }
      } else {
        this.$toast.error("Không có lượt giao để hủy !");
      }
    },
    async onXoaPhieuClicked() {
      if (this.dtPhieuDaGiao.length > 0) {
        try {
          this.$root.loading(true);
          if (
            this.PhieuKSSelectedRow["NGUOIGIAO_ID"] !=
            this.$auth.getNhanVienID()
          ) {
            return this.$toast.error(
              "Bạn không được xóa phiếu do người khác giao !"
            );
          }
          let idDel = null;
          if (
            this.PhieuKSSelectedRow.HINHTHUCKS_ID != 1 &&
            this.PhieuKSSelectedRow.HINHTHUCKS_ID != 2 &&
            this.PhieuKSSelectedRow.HINHTHUCKS_ID != 3
          ) {
            return this.$toast.error(
              "Chỉ xóa phiếu với hình thức khảo sát là : Lắp đặt(1), Báo hỏng(2), Thuê bao(3)"
            );
          }
          console.log(
            "this.dtPhieuDaGiaoSelectedRow.THUEBAO_ID",
            this.dtPhieuDaGiaoSelectedRow.THUEBAO_ID
          );
          if (this.PhieuKSSelectedRow.HINHTHUCKS_ID == 1) {
            idDel = this.dtPhieuDaGiaoSelectedRow.HDKH_ID;
          } else if (this.PhieuKSSelectedRow.HINHTHUCKS_ID == 2) {
            idDel = this.dtPhieuDaGiaoSelectedRow.BAOHONG_ID;
          } else if (this.PhieuKSSelectedRow.HINHTHUCKS_ID == 3) {
            idDel = this.dtPhieuDaGiaoSelectedRow.THUEBAO_ID;
          }
          let data = {
            nguoiDungId: this.$auth.getNguoiDungID(),
            giaoPhieuKSID: this.PhieuKSSelectedRow.GIAOPHIEU_KS_ID,
            id: idDel,
          };
          let response = await API.deleteHuyGiao(this.axios, data);
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            if (response.data.data[0].CODE == 1) {
              this.$toast.success(response.data.data[0].MSG);
              this.timKiem();
            } else {
              this.$toast.error(response.data.data[0].MSG);
            }
          }
        } catch (error) {
          console.log(error);
          this.$toast.error(error.data.error_code);
        } finally {
          this.$root.loading(false);
        }
      } else {
        this.$toast.error("Không có phiếu để xóa !");
      }
    },
  },
};
</script>
