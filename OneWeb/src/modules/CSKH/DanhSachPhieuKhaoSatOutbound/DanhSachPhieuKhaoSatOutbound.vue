<template src="./DanhSachPhieuKhaoSatOutbound.html"></template>
<style src="./DanhSachPhieuKhaoSatOutbound.scss"></style>
<script>
import moment from "moment";
import { mapActions, mapGetters } from "vuex";
import CustomDataGrid from "../utils/CustomDataGrid.vue";
import DanhSachPhieuKhaoSatOutboundApi from "../api/DanhSachPhieuKhaoSatOutboundApi";
import NhanVienQuanLy from "./popup/NhanVienQuanLy.vue";
import XuatFile from "../utils/XuatFile.vue";
import frmKhaoSatKH from "../KhaoSat/frmKhaoSatKH.vue"
import frmKhaoSatKH_TB from "../KhaoSat/frmKhaoSatKH_TB.vue"
import frmKhaoSatKH_HDTB from "../KhaoSat/frmKhaoSatKH_HDTB.vue"
import CapNhapTTLH from "../TraCuuKhachHangLon/popup/CapNhapTTLH.vue"
import select2 from "@/components/Select2.vue";
// import TraCuuTBKSModal from "./popup/TraCuuTBKSModal.vue"
import  ThongTinLienHe from "../../contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue"
import BssErrorMarker from '@/components/BssErrorMarker'

import Vue from 'vue'
export default {
  name: "DanhSachPhieuKhaoSatOutbound",
  components: {
    appSelect2: select2,
    appNhanVienQuanLy: NhanVienQuanLy,
    datagrid: CustomDataGrid,
    appModalXuatDuLieu: XuatFile,
    frmKhaoSatKH,
    frmKhaoSatKH_TB,
    frmKhaoSatKH_HDTB,
    appCapNhapTTLH: CapNhapTTLH,
    ThongTinLienHe,
    BssErrorMarker
    // TraCuuTBKSModal
  },
  created() {
    this.clearData();
  },
  async mounted() {
    try {
      this.loading(true);
      await Promise.all([
        this.onLoadTrangThaiLuotGiao(),
        this.onLoadTrangThaiPhieu(),
        this.onLoadLoaiNhomKhaoSat(),
        // this.onLoadThongTinCanhBao(),
        // this.onLoadThamSoMacDinh(),
      ]);
      // await this.getdsLoaiNhomKhaoSat();
      // await this.getdsTrangThaiLuotGiaoKhaoSat();
      // await this.getdsTrangThaiPhieu();
      //@fakedata

      // this.timer = setInterval(() => {

      // }, this.THAMSO_THOIGIAN_TIMKIEM_OUTBOUND);
      this.btnBoQuaVisible = true;

    } catch (e) {
    } finally {
      this.loading(false);
    }
  },
  data() {
    return {
      API_SUCCESS: "BSS-00000000",
      chk_from_date: false,
      today: new Date(),
      fromDate: new Date(),
      toDate: new Date(),
      vid: -1,
      vloai: -1,
      luotGiaoKSSelected: null,
      phieuGiaoKSSelected: null,
      phieuKSId: null,
      phieuKSLoai: null,
      phieuKSKieuKS: null,
      HDBHId: -1,
      tag_TuDong_LayTT: 0,
      timer: null,
      tt_btb_LayPhieu: -1,
      btnLayPhieuVisible: true,
      btnBoQuaVisible: true,
      btnLayPhieuText: "Lấy phiếu",
      btnLayPhieuClass: "nc-icon-outline media-1_button-play icon",
      txtThongBao: "Đang lấy phiếu: 0 - Chưa gọi: 0 - Chưa XLTG: 0 - Gọi gấp: 0 - Đang gọi: 0",
      pCurrentPhieuKS: null,
      pCurrentDaGiao: null,
      pHdkhId: 0,
      pHdtbId: 0,
      pGiaoPhieuKsId: 0,
      pKieuKs: 0,
      pMoPhieu: 0,
      pThueBaoId: 0,
      pBaoHongId: 0,
      pLoai: 0,
      pKhachHangId: 0,

      globalParams: {
        vGiaoPhieuKsId: 0,
        vTrangThai: 0,
        vKieuKs: 0,
        vMoPhieu: 0,
        vLoai: 0,
        vCoHen: -1,

        hdkhId: 0,
        hdtbId: 0,
        baoHongId: 0,
        ctSuyHaoId: 0,
        thueBaoId: 0,

      },

      cboTrangThaiLuotGiao: {
        options: [],
        selectedValue: -1,
      },

      cboTrangThaiPhieu: {
        options: [],
        selectedValue: -1,
      },

      cboLoaiNhomKhaoSat: {
        options: [],
        selectedValue: -1,
      },

      thongTinCanhBao: {
          "DANG_LAYPHIEU": 0,
          "CHUA_GOI": 0,
          "CHUA_XLTG": 0,
          "GOI_GAP": 0,
          "DANG_GOI": 0
      },

      gridPhieuKhaoSat: {
        dataSource: [],
        selectedRow: null
      },

      gridPhieuDaGiaoKhaoSat: {
        dataSource: [],
        selectedRow: null
      },

      thamSoMacDinh: {
        LAYPHIEU_OUTBOUND_TUDONG: 0,
        KS_THIETLAP_TGLAYPHIEU: 0,
        THAMSO_THOIGIAN_TIMKIEM_OUTBOUND: 0,
        KHONG_TAMDUNG_LAYPHIEU: 0,
      },

      sumTemplate: function() {
        return { template: Vue.component('sumTemplate', {
            template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data: function () {return {data: {data: {}}};}
          })
        }
      },
      countTemplate: function() {
        return { template: Vue.component('countTemplate', {
            template: `<input type="text" class="form-control tright" :value="data.Count" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data: function () {return {data: {data: {}}};}
          })
        }
      },
      invalidTuNgay: false,
      invalidDenNgay: false
    };
  },
  computed: {
    ...mapGetters("danhsachphieukhaosatoutbound", [
      "getdsLuotGiaoNhanKhaoSatGetter",
      "getdsPhieuDaGiaoKhaoSatGetter"
    ]),
    khachHangId() {
      return this.pKhachHangId
    },
    LGAggregatesColumns () {
      return [
        { field: 'SOLUONG_TB', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'SL_DA_KS', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'SL_CHUA_TL', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }
      ]
    },
    txtCanhBao() {
      return `Đang lấy phiếu: ${this.thongTinCanhBao.DANG_LAYPHIEU} - Chưa gọi: ${this.thongTinCanhBao.CHUA_GOI} - Chưa XLTG: ${this.thongTinCanhBao.CHUA_XLTG} - Gọi gấp: ${this.thongTinCanhBao.GOI_GAP} - Đang gọi: ${this.thongTinCanhBao.DANG_GOI}`;
    }
  },
  watch: {
    fromDate: {
      handler: async function (value) {
        if(value) this.invalidTuNgay = false
      },
    },
    toDate: {
      handler: async function (value) {
        if(value) this.invalidDenNgay = false
      },
    },
  },
  methods: {
    ...mapActions("danhsachphieukhaosatoutbound", [
      "getdsLuotGiaoNhanKhaoSat",
      "getdsPhieuDaGiaoKhaoSat",
      "clearData"
    ]),
    initData() {
      this.globalParams = {
        vGiaoPhieuKsId: 0,
        vTrangThai: 0,
        vKieuKs: 0,
        vMoPhieu: 0,
        vLoai: 0,
        vCoHen: -1,

        hdkhId: 0,
        hdtbId: 0,
        baoHongId: 0,
        ctSuyHaoId: 0,
        thueBaoId: 0,
      }
    },
    async onRowDSLuotGiaoKhaoSatSelected(row) {
      // console.log('onRowDSLuotGiaoKhaoSatSelected', row)
      // if (row && row.length > 0) {
      //   let item = this.getdsLuotGiaoNhanKhaoSatGetter.find(x => x.GIAOPHIEU_KS_ID == row[row.length - 1])
      //   this.luotGiaoKSSelected = null;
      //   this.phieuKSLoai = -1;
      //   this.phieuKSId = -1;
      //   this.phieuKSKieuKS = -1;
      //   this.pCurrentPhieuKS = item
      //   if (item) {
      //     this.luotGiaoKSSelected = item;
      //     this.phieuKSId = item.GIAOPHIEU_KS_ID;
      //     this.phieuKSLoai = item.LOAI;
      //     this.phieuKSKieuKS = item.KIEU_KS;

      //     this.pGiaoPhieuKsId = item.GIAOPHIEU_KS_ID;
      //     this.pKieuKs = item.KIEU_KS
      //     this.pMoPhieu = item.MOPHIEU

      //     this.pLoai = item.LOAI

      //     let postData = {
      //       giaoPhieuKsId: this.phieuKSId,
      //       kieuKs: this.phieuKSKieuKS,
      //       loai: this.phieuKSLoai,
      //       trangThai: this.cboTrangThaiPhieu.selectedValue,
      //       pageNo: 1,
      //       pageSize: 10000
      //     };

      //     try {
      //       this.$root.showLoading(true)
      //       await this.getdsPhieuDaGiaoKhaoSat(postData)
      //       if (this.getdsPhieuDaGiaoKhaoSatGetter.length > 0) {
      //         this.$refs.dsPhieuDaGiaoKhaoSat.setCurrentSelectedRow(0)
      //       }
      //     }
      //     catch (e) {
      //       console.log(e)
      //     }
      //     finally {
      //       this.$root.showLoading(false)
      //     }
      //   }
      // }

    },
    async onDsLuotGiaoNhanRowChanged(item) {
      if(!item) return;
        this.gridPhieuKhaoSat.selectedRow = item
        this.globalParams.vGiaoPhieuKsId = item.GIAOPHIEU_KS_ID
        this.globalParams.vTrangThai = this.cboTrangThaiPhieu.selectedValue
        this.globalParams.vKieuKs = item.KIEU_KS
        this.globalParams.vMoPhieu = item.MOPHIEU
        this.globalParams.vLoai = item.LOAI
        this.globalParams.vCoHen = item.CO_HEN

        let data = {
            giaoPhieuKsId: this.globalParams.vGiaoPhieuKsId,
            kieuKs: this.globalParams.vKieuKs,
            loai: this.globalParams.vLoai,
            trangThai: this.cboTrangThaiPhieu.selectedValue,
        }

        try {
            this.$root.showLoading(true)
            this.gridPhieuDaGiaoKhaoSat = {
            dataSource: [],
            selectedRow: null
            }
            let response = await this.$root.context.post('/web-cskh/api/phieu-khao-sat/phieu-da-giao?pageNo=1&pageSize=10000', data)
            if (response.error_code === this.API_SUCCESS) {
            this.gridPhieuDaGiaoKhaoSat.dataSource = response.data.data || []
            this.gridPhieuDaGiaoKhaoSat.dataSource.forEach((e, index) => e.STT = index + 1)
            }
        }
        catch (e) {
            console.log(e)
            this.$root.toastError("Có lỗi: " + e.message)
        }
        finally {
            this.$root.showLoading(false)
        }
    },
    onRowDSPhieuDaGiaoKhaoSatSelected(items) {
      // if (items && items.length > 0) {
      //   let item = this.getdsPhieuDaGiaoKhaoSatGetter.find(x => x.STT == items[items.length - 1])
      //   this.pCurrentDaGiao = item
      //   this.phieuGiaoKSSelected = null;
      //   this.HDBHId = -1;
      //   if (item) {
      //     this.phieuGiaoKSSelected = item;
      //     if (this.pLoai == 1) {
      //       this.pHdkhId = item.HDKH_ID
      //       this.pHdtbId = item.HDTB_ID
      //       this.HDBHId = item.HDKH_ID;
      //       this.vloai = 2;
      //     }
      //     else if (this.pLoai == 2) {
      //       this.pBaoHongId = item.BAOHONG_ID
      //       this.HDBHId = item.KHACHHANG_ID;
      //       this.vloai = 1;
      //     }
      //     else {
      //       this.pThueBaoId = item.THUEBAO_ID
      //     }
      //   }
      // }
    },
    async onDsPhieuKhaoSatRowChanged(item) {
      if(!item) return;
      this.gridPhieuDaGiaoKhaoSat.selectedRow = item
      if (this.globalParams.vLoai == 1) {
        this.globalParams.hdkhId = item.HDKH_ID
        this.globalParams.hdtbId = item.HDTB_ID
      }
      else if (this.globalParams.vLoai == 2) {
        this.globalParams.baoHongId = item.BAOHONG_ID
      }
      else {
        this.globalParams.thueBaoId = item.THUEBAO_ID
      }
    },
    onNVQLClicked() {
      // if (!this.pCurrentDaGiao) {
      //   this.$root.toastError("Không có dữ liệu hiển thị !")
      //   return
      // }

      // if (this.pLoai == 1) {
      //   this.HDBHId = this.pCurrentDaGiao.HDKH_ID;
      //   this.vloai = 2;

      //   this.$refs.popupNVQL.vid = this.HDBHId
      //   this.$refs.popupNVQL.vloai = 2
      //   this.$refs.popupNVQL.openDialog()
      // }
      // else if (this.pLoai == 2) {
      //   this.HDBHId = this.pCurrentDaGiao.KHACHHANG_ID;
      //   this.vloai = 1;

      //   this.$refs.popupNVQL.vid = this.HDBHId
      //   this.$refs.popupNVQL.vloai = 1
      //   this.$refs.popupNVQL.openDialog()
      // }
      if (this.gridPhieuDaGiaoKhaoSat.dataSource.length <= 0) {
        this.$root.toastError("Không có dữ liệu hiển thị !")
        return
      }

      if (this.globalParams.vLoai == 1) {
        this.$refs.popupNVQL.vid = this.gridPhieuDaGiaoKhaoSat.selectedRow.HDKH_ID
        this.$refs.popupNVQL.vloai = 2
        this.$refs.popupNVQL.openDialog()
      }
      else if (this.globalParams.vLoai == 2) {
        this.$refs.popupNVQL.vid = this.gridPhieuDaGiaoKhaoSat.selectedRow.KHACHHANG_ID
        this.$refs.popupNVQL.vloai = 2
        this.$refs.popupNVQL.openDialog()
      }
    },
    async fetchDSPhieuDaGiaoKhaoSat(pageNo, pageSize) {
      this.loading(true);
      try {
        let postData = {
          giaoPhieuKsId: this.phieuKSId,
          kieuKs: this.phieuKSKieuKS,
          loai: this.phieuKSLoai,
          trangThai: this.cboTrangThaiPhieu.selectedValue
        };

        let response = await DanhSachPhieuKhaoSatOutboundApi.getdsPhieuDaGiaoKhaoSat(
          this.axios,
          pageNo,
          pageSize,
          postData
        );
        return response;
      } catch (e) {
      } finally {
        this.loading(false);
      }
    },
    async fetchData() {
      try {
        this.loading(true);
        if(!$('#txtTuNgay').val()){
          this.$root.toastError("Từ ngày không được trống!");
          this.invalidTuNgay = true;
        //   this.$nextTick(() => this.$refs.refTN.focusIn());
          return
        }
        if(!$('#txtDenNgay').val()){
          this.$root.toastError("Đến ngày không được trống!");
          this.invalidDenNgay = true;
        //   this.$nextTick(() => this.$refs.refDN.focusIn());
          return
        }
        if (!this.fromDate) {
          this.$root.toastError("Từ ngày không đúng định dạng!")
          return
        }
        if (!this.toDate) {
          this.$root.toastError("Đến ngày không đúng định dạng!")
          return
        }

        if (moment(this.toDate).diff(this.fromDate, "DD/MM/YYYY") < 0) {
          this.$root.toastError("Từ ngày phải nhỏ hơn hoặc bằng Đến ngày")
          return
        }

        await this.onLoadLuotGiaoNhanKhaoSat();
      } catch (e) {
      } finally {
        this.loading(false);
      }
    },
    async fetchDSLuotGiaoKhaoSat(pageNo, pageSize) {
      this.loading(true);
      let response = null;
      try {
        let postData = {
          tuNgay: this.chk_from_date
            ? moment(this.fromDate).format("DD/MM/YYYY")
            : "",
          denNgay: moment(this.toDate).format("DD/MM/YYYY"),
          nguoiNhanId: this.$auth.getNhanVienID(),
          loaiNhomId: this.cboLoaiNhomKhaoSat.selectedValue,
          ttLuotGiaoId: this.cboTrangThaiLuotGiao.selectedValue
        };
        response = await DanhSachPhieuKhaoSatOutboundApi.getdsLuotGiaoNhanKhaoSat(
          this.axios,
          pageNo,
          pageSize,
          postData
        );
      } catch (e) {
      } finally {
        this.loading(false);
      }

      return response;
      //console.log(response)
    },
    async layPhieuKS() {
      if (this.tt_btb_LayPhieu == 0 && this.thamSoMacDinh.KHONG_TAMDUNG_LAYPHIEU == 1) {
        this.$root.toastError("Không thể tạm dừng!");
        return;
      }
      if (this.tt_btb_LayPhieu == 1) {
        if (this.thamSoMacDinh.KS_THIETLAP_TGLAYPHIEU == 1) {
          //@fakedata var kiemtra = new FIMS_Facade().GET_VALUE_FUNC("{?DB9}.kiemtra_tg_layphieu")
          // var kiemtra = new FIMS_Facade().GET_VALUE_FUNC("{?DB9}.kiemtra_tg_layphieu");
          // if (kiemtra.ToString() != "1")
          // {
          //     Message_Box.ShowWarning(kiemtra.ToString());
          //     return;
          // }
        }
        this.btnLayPhieuText = "Tạm dừng";
        this.btnLayPhieuClass = "nc-icon-outline media-1_button-pause icon"
        this.tt_btb_LayPhieu = 0
      } else {
        this.btnLayPhieuText = "Lấy phiếu";
        this.btnLayPhieuClass = "nc-icon-outline media-1_button-play icon"
        this.tt_btb_LayPhieu = 1
      }

      if (this.tt_btb_LayPhieu == 0) {
        try {
          await this.$root.context.get('/web-cskh/api/phieu-khao-sat/dang-ky', { nhanVienId: this.$auth.getNhanVienID() })
        }
        catch (e) {
          console.log(e)
        }
      }
      if (this.tt_btb_LayPhieu == 1) {
        try {
          await this.$root.context.get("/web-cskh/api/phieu-khao-sat/dung-lay-phieu", {nhanVienId: this.$auth.getNhanVienID()})
        }
        catch (e) {
          console.log(e)
        }
      }
    },
    async boQuaPhieuKS() {
      try {
        if (this.getdsLuotGiaoNhanKhaoSatGetter.length > 0) {
          if (!this.luotGiaoKSSelected)
            return;
          if ( this.luotGiaoKSSelected.KIEU_KS == 2 || this.luotGiaoKSSelected.CO_HEN > 0 ) {
            if (this.tt_btb_LayPhieu == 1) {
              let postData = {
                giaoPhieuKsId: vgiaophieu_ks_id,
                nhanVienId: this.$auth.getNhanVienID()
              };
              await DanhSachPhieuKhaoSatOutboundApi.boQuaPhieuKS(
                this.axios,
                postData
              );
              //fims.Boqua_phieu_khaosat(vgiaophieu_ks_id, -1);
            } else {
              let postData = {
                giaoPhieuKsId: vgiaophieu_ks_id,
                nhanVienId: this.$auth.getNhanVienID()
              };
              await DanhSachPhieuKhaoSatOutboundApi.boQuaPhieuKS(
                this.axios,
                postData
              );
              //fims.Boqua_phieu_khaosat(vgiaophieu_ks_id, tt_nd.nhanvien_id);
            }
            this.$root.toastSuccess("Bỏ qua phiếu thành công!")
          } else {
            this.$root.toastError("Không thể bỏ qua phiếu giao nhân công!");
          }
          await this.fetchData();
        }
      }
      catch (e) {
        this.$root.toastError("Có lỗi: " + e.response.data.message)
      }
      finally {
        this.$root.showLoading(false)
      }

    },
    async tuDongLayPhieu() {},
    thongBaoChuaCoChucNang() {
      this.$root.toastError("Hiện 2 chức năng xuất file và xuất excel trên PTTB HCM không thực hiện được")
    },
    onKhaoSatClicked() {
      //@fakedata
      // this.$refs.frmKhaoSatKH_TB.thueBaoId = this.pThueBaoId
      //   this.$refs.frmKhaoSatKH_TB.giaoPhieuKsId = this.pGiaoPhieuKsId
      //   this.$refs.frmKhaoSatKH_TB.openDialog()
      //   return
      if (!this.gridPhieuKhaoSat.selectedRow || this.gridPhieuDaGiaoKhaoSat.dataSource.length < 1) {
        this.$root.toastError("Không có thông tin để khảo sát!")
        return
      }
      this.pLoai = this.globalParams.vLoai;
      if (this.pLoai == 1) {
        if(this.globalParams.hdkhId < 1 && this.globalParams.hdtbId < 1){
          this.$root.toastError("Bạn chưa chọn thông tin để khảo sát!")
          return
        }
        this.$refs.frmKhaoSatKH_HDTB.hdkhId = this.globalParams.hdkhId
        this.$refs.frmKhaoSatKH_HDTB.hdtbId = this.globalParams.hdtbId
        this.$refs.frmKhaoSatKH_HDTB.giaoPhieuKsId = this.globalParams.vGiaoPhieuKsId
        this.$refs.frmKhaoSatKH_HDTB.kieuKs = this.globalParams.vKieuKs
        this.$refs.frmKhaoSatKH_HDTB.moPhieu = this.globalParams.vMoPhieu
        this.$refs.frmKhaoSatKH_HDTB.openDialog()

        return
      }
      else if (this.pLoai == 2) {
        if(this.globalParams.baoHongId < 1){
          this.$root.toastError("Không có thông tin báo hỏng để khảo sát!")
          return
        }
        this.$refs.frmKhaoSatKH.giaoPhieuKsId = this.globalParams.vGiaoPhieuKsId
        this.$refs.frmKhaoSatKH.baoHongId = this.globalParams.baoHongId
        this.$refs.frmKhaoSatKH.pMoPhieu = this.globalParams.vMoPhieu
        this.$refs.frmKhaoSatKH.pKieuKs = this.globalParams.vKieuKs
        this.$refs.frmKhaoSatKH.openDialog()
        return
      }
      else {
        //frmKhaoSatKH_TB
        if(this.globalParams.thueBaoId < 1){
          this.$root.toastError("Không có thông tin thuê bao để khảo sát!")
          return
        }
        this.$refs.frmKhaoSatKH_TB.thueBaoId = this.globalParams.thueBaoId
        this.$refs.frmKhaoSatKH_TB.giaoPhieuKsId = this.globalParams.vGiaoPhieuKsId
        this.$refs.frmKhaoSatKH_TB.openDialog()
        return
      }
    },
    onCapNhapTTLHClicked() {
      if (this.gridPhieuDaGiaoKhaoSat.dataSource.length <= 0) {
        this.$root.toastError("Không có dữ liệu hiển thị !")
        return
      }
      this.pHdkhId = this.gridPhieuDaGiaoKhaoSat.selectedRow.HDKH_ID
      this.pKhachHangId = this.gridPhieuDaGiaoKhaoSat.selectedRow.KHACHHANG_ID
      if(this.pHdkhId) {
        this.$refs.thongtinlienhe.show()
      } else {
        this.$root.toastError("Không có KHACHHANG_ID !")
        return
      }
    },
    async onLoadTrangThaiLuotGiao() {
      try {
        this.cboTrangThaiLuotGiao.options = []
        let response = await this.$root.context.get('/web-cskh/api/danh-muc/tt-luot-giao-khao-sat')
        if (response.error_code === this.API_SUCCESS) {
          response.data.forEach(x =>
            this.cboTrangThaiLuotGiao.options.push({
              id: x.ttLuotId,
              text: x.trangThaiLuot
            })
          )
        }

        this.$nextTick(() => {
          this.cboTrangThaiLuotGiao.selectedValue = (this.cboTrangThaiLuotGiao.options && this.cboTrangThaiLuotGiao.options.length > 0) ? 1 : -1;
        })
      }
      catch (e) {
        console.log(e)
      }
      finally {

      }
    },
    async onLoadTrangThaiPhieu() {
      try {
        this.cboTrangThaiPhieu.options = []
        let response = await this.$root.context.get('/web-cskh/api/danh-muc/trang-thai-phieu')
        if (response.error_code === this.API_SUCCESS) {
          response.data.forEach(x =>
            this.cboTrangThaiPhieu.options.push({
              id: x.ttPhieuId,
              text: x.trangThaiPhieu
            })
          )
        }

        this.$nextTick(() => {
          this.cboTrangThaiPhieu.selectedValue = (this.cboTrangThaiPhieu.options && this.cboTrangThaiPhieu.options.length > 0) ? 1 : -1;
        })
      }
      catch (e) {
        console.log(e)
      }
      finally {

      }
    },
    async onLoadLoaiNhomKhaoSat() {
      try {
        this.cboLoaiNhomKhaoSat.options = []
        let response = await this.$root.context.get('/web-cskh/api/danh-muc/loai-nhom-khao-sat')
        if (response.error_code === this.API_SUCCESS) {
          response.data.forEach(x =>
            this.cboLoaiNhomKhaoSat.options.push({
              id: x.nhomId,
              text: x.tenNhom
            })
          )
        }

        this.$nextTick(() => {
          this.cboLoaiNhomKhaoSat.selectedValue = (this.cboLoaiNhomKhaoSat.options && this.cboLoaiNhomKhaoSat.options.length > 0) ? 0 : -1;
        })
      }
      catch (e) {
        console.log(e)
      }
      finally {

      }
    },
    async onLoadThongTinCanhBao() {
      try {
        this.thongTinCanhBao = {
            "DANG_LAYPHIEU": 0,
            "CHUA_GOI": 0,
            "CHUA_XLTG": 0,
            "GOI_GAP": 0,
            "DANG_GOI": 0
        }
        let response = await this.$root.context.get('/web-cskh/api/phieu-khao-sat/lay-thong-tin-canh-bao')
        if (response.error_code === this.API_SUCCESS && response.data && response.data.data && response.data.data.length > 0) {
          this.thongTinCanhBao = response.data.data[0]
        }
      }
      catch (e) {
        console.log(e)
      }
      finally {

      }
    },
    async onLoadThamSoMacDinh() {
      try {
        this.thamSoMacDinh = {
          LAYPHIEU_OUTBOUND_TUDONG: 0,
          KS_THIETLAP_TGLAYPHIEU: 0,
          THAMSO_THOIGIAN_TIMKIEM_OUTBOUND: 0,
          KHONG_TAMDUNG_LAYPHIEU: 0,
        }
        let dsMaThamSo = [
          'KS_THIETLAP_TGLAYPHIEU',
          'THAMSO_THOIGIAN_TIMKIEM_OUTBOUND',
          'KHONG_TAMDUNG_LAYPHIEU',
          'LAYPHIEU_OUTBOUND_TUDONG'
        ]

        let response = await this.$root.context.get('/web-cskh/api/danh-muc/tham-so-mac-dinh', {dsMaTs: dsMaThamSo.join()})
        if (response.error_code === this.API_SUCCESS) {
          let dsThamSo = response.data || []

          let KS_THIETLAP_TGLAYPHIEU = dsThamSo.find( x => x.hasOwnProperty("MA_TS") && x.MA_TS == "KS_THIETLAP_TGLAYPHIEU" );
          if (KS_THIETLAP_TGLAYPHIEU)
            this.thamSoMacDinh.KS_THIETLAP_TGLAYPHIEU = parseInt(KS_THIETLAP_TGLAYPHIEU.TEN_TS);

          let THAMSO_THOIGIAN_TIMKIEM_OUTBOUND = dsThamSo.find( x => x.hasOwnProperty("MA_TS") && x.MA_TS == "THAMSO_THOIGIAN_TIMKIEM_OUTBOUND" );
          if (THAMSO_THOIGIAN_TIMKIEM_OUTBOUND)
            this.thamSoMacDinh.THAMSO_THOIGIAN_TIMKIEM_OUTBOUND = parseInt(THAMSO_THOIGIAN_TIMKIEM_OUTBOUND.TEN_TS);

          let KHONG_TAMDUNG_LAYPHIEU = dsThamSo.find( x => x.hasOwnProperty("MA_TS") && x.MA_TS == "KHONG_TAMDUNG_LAYPHIEU" );
          if (KHONG_TAMDUNG_LAYPHIEU)
            this.thamSoMacDinh.KHONG_TAMDUNG_LAYPHIEU = parseInt(KHONG_TAMDUNG_LAYPHIEU.TEN_TS);

          let LAYPHIEU_OUTBOUND_TUDONG = dsThamSo.find( x => x.hasOwnProperty("MA_TS") && x.MA_TS == "LAYPHIEU_OUTBOUND_TUDONG" );
          if (LAYPHIEU_OUTBOUND_TUDONG)
            this.thamSoMacDinh.LAYPHIEU_OUTBOUND_TUDONG = parseInt(LAYPHIEU_OUTBOUND_TUDONG.TEN_TS);
        }


        if (this.thamSoMacDinh.LAYPHIEU_OUTBOUND_TUDONG == 1) {
          this.tt_btb_LayPhieu = 1;
          this.btnLayPhieuVisible = true;
        } else {
          this.btnLayPhieuVisible = false;
        }
      }
      catch (e) {
        console.log(e)
      }
      finally {

      }
    },
    async onLoadLuotGiaoNhanKhaoSat() {
      try {
        this.gridPhieuKhaoSat = {
          dataSource: [],
          selectedRow: null
        }

        this.$root.showLoading(true)
        let postData = {
          tuNgay: this.chk_from_date
            ? moment(this.fromDate).format("DD/MM/YYYY")
            : "",
          denNgay: moment(this.toDate).format("DD/MM/YYYY"),
          nguoiNhanId: this.$auth.getNhanVienID(),
          loaiNhomId: this.cboLoaiNhomKhaoSat.selectedValue,
          ttLuotGiaoId: this.cboTrangThaiLuotGiao.selectedValue,
          pageNo: 1,
          pageSize: 10000
        };

        let response = await this.$root.context.post('/web-cskh/api/phieu-khao-sat/luot-giao-nhan?pageNo=1&pageSize=10000', postData)
        if (response.error_code === this.API_SUCCESS) {
          this.gridPhieuKhaoSat.dataSource = response.data.data || []
          this.gridPhieuKhaoSat.dataSource.forEach((e, index) => e.STT = index + 1)
        }
      }
      catch (e) {
        console.log(e)
        this.$root.toastError("Có lỗi: " + e.message)
      }
      finally {
        this.$root.showLoading(false)
      }
    }
  },
  beforeDestroy() {
    clearInterval(this.timer);
  }
};
</script>
