<template src="./XuLyYeuCauVatTu.html"></template>

<script>
import API from "../api/XuLyYeuCauVatTu.js";
import Vue from "vue";
import { ListBoxPlugin } from "@syncfusion/ej2-vue-dropdowns";
import frmGiaoPhieuVT from "../frmGiaoPhieu/frmGiaoPhieu.vue";
import frmGiaoPhieuCTTC from "@/modules/contract/setup/HandoverTicket/HandoverTicket.vue";
// import frmGiaoPhieuCTTC from "./HandoverTicket/HandoverTicket.vue";
// import PopupBuyDevices from "@/modules/contract/setup/InstallNewSubs/Popups/popupBuyDevices.vue";
import PopupBuyDevices from "./frmChonTb_DHTC/popupBuyDevices.vue";
import PopupBuyDevices1 from "./components/frmFormTb/frmFormTb.vue";

Vue.use(ListBoxPlugin);
export default {
  name: "frmXuLyYeuCau_VT",
  components: {
    frmGiaoPhieuVT,
    PopupBuyDevices,
    frmGiaoPhieuCTTC,
    PopupBuyDevices1,
  },
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      isRender: false,
      gParams: {
        txtMaGD: "",
        txtMaKH: "",
        txtTenKH: "",
        txtDiaChiKH: "",
        txtGiaGoi: "",

        cboketquaxl: "",
        txtGhiChu: "",
        //props
        dsttkn_id: "",
        dshuonggiao_id: "",
        thuebao_id: "",
        khieunai_id: "",
        phieukn_id: "",
        quytrinh_id: "",
        luong_id: "",
        hdkh_id: 0,
        hdtb_id: 0,
        phieu_id: 0,
        khachhang_id: 0,
        loaihinh_tb: 0,
        vma_tt: "",
        vma_tb: "",
        vky_hd: "",
        dsCTM_TBI: [],
        dtThaoTac: [],
        dtControl: [],
        ds_khieunai: [],
        donvikn_id: "",
        tien_tbi: 0,
        vat_tbi: 0,
        tien_kmtbi: 0,
        vat_kmtbi: 0,
        tien_tgtb_tragop: 0,
        vat_tgtb_tragop: 0,
        tien_tgtb_tratruoc: 0,
        vat_tgtb_tratruoc: 0,
        kt_load: true,
        ds_tien_khoanmuc: [],
        kyhoadon: "",
        totruong: 0, // thêm biến kiểm tra có phải là tổ trưởng XLKN hay ko ?
        THEM_KHDB_KHI_KHIEUNAI: -1,
        classIconHiddenBox: "icon fa fa-angle-up",
        gridDaGiaoRowChanged: "",
        gridDaThucHienRowChanged: "",
      },
      cboketquaxl: [],
      lsbDanhSachTBI: [],
      tabDsKhieuNai: {
        tabChuaXL: 0,
        tabDaXL: 1,
        tabSelected: 0,
      },
      visible: {
        boxSearch: true,
      },
      enabled: {
        tsbtnHoanThanh: true,
        tsbtnGiaoPhieu: false,
      },
      //grid
      gridDaGiao: [],
      gridDaThucHien: [],
      gridThueBao: [],
      dsKM_HDTB: [],
      dsDC_HDTB: [],
      //end grid
      sumTemplate: function () {
        return {
          template: Vue.component("sumTemplate", {
            template: `<span class="redColor">Sum: {{data.Sum}}</span>`,
            data: function () {
              return { data: { data: {} } };
            },
          }),
        };
      },
      aggregatesColumns: [
        {
          field: "DOANHTHU",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
        {
          field: "MA_TT",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
        {
          field: "CHENHLECH",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
      ],
    };
  },
  computed: {
    buyDevicesData() {
      console.log("buyDevicesData", this.gParams.dsCTM_TBI);
      if (this.gParams.loaihinh_tb) {
        return {
          _loaitb_id: this.gParams.loaihinh_tb,
          ds: this.gParams.dsCTM_TBI,
        };
      } else {
        return { ds: this.gParams.dsCTM_TBI };
      }
    },
    getLsb() {
      if (this.lsbDanhSachTBI.length) {
        return this.lsbDanhSachTBI.join("\n");
      } else {
        return "";
      }
    },
  },
  methods: {
    onClickHiddenBox() {
      this.gParams.classIconHiddenBox = this.visible.boxSearch
        ? "icon fa fa-angle-down"
        : "icon fa fa-angle-up";
      this.visible.boxSearch = !this.visible.boxSearch;
    },
    isNullOrEmpty(value) {
      return value === null || value === "";
    },
    dateToString(value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    stringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    setFirstValue(cbo, key) {
      if (cbo.length) {
        return cbo[0][key];
      } else {
        return "";
      }
    },
    clear() {
      this.gParams.txtGhiChu = "";
      this.gParams.hdtb_id = 0;
      this.gParams.phieu_id = 0;
      this.gridThueBao = [];
      //  grcVatTuDaGan.DataSource = null;
      this.gParams.txtMaGD = "";
      this.gParams.txtGiaGoi = "0";
      this.gParams.txtTenKH = "";
      this.gParams.txtDiaChiKH = "";
      this.gParams.cboketquaxl = this.setFirstValue(
        this.cboketquaxl,
        "kqxl_id"
      );
    },
    async selectTab(value) {
      this.tabDsKhieuNai.tabSelected = value;
      if (value == this.tabDsKhieuNai.tabChuaXL) {
        this.enabled.tsbtnHoanThanh = true;
        this.enabled.tsbtnGiaoPhieu = false;
        this.gParams.txtGhiChu = "";
        this.gParams.cboketquaxl = this.setFirstValue(
          this.cboketquaxl,
          "kqxl_id"
        );
        let ds = await (
          await API.getDSPhieuCapVTMienPhi(this.axios, {
            huongGiaoId: 6824,
            loaiPhieu: 2,
          })
        ).data.data;
        this.gridDaGiao = ds;
        if (!ds.length) {
          this.clear();
        } else {
          this.hienthi_ds_dagiao(0);
        }
      } else {
        this.enabled.tsbtnHoanThanh = false;
        this.enabled.tsbtnGiaoPhieu = true;
        let ds = await (
          await API.getDSPhieuCapVTMienPhi(this.axios, {
            huongGiaoId: 6824,
            loaiPhieu: 3,
          })
        ).data.data;
        this.gridDaThucHien = ds;
        if (!ds.length) {
          this.clear();
        } else {
          this.hienthi_ds_dathuchien(0);
        }
      }
    },
    async hienthi_ds_dagiao(row) {
      this.gParams.khachhang_id = parseInt(
        this.gridDaGiao[row]["KHACHHANG_ID"]
      );
      this.gParams.txtGiaGoi = this.gridDaGiao[row]["GHICHU_HD"];
      this.gParams.txtMaKH = this.gridDaGiao[row]["MA_KH"];
      this.gParams.txtMaGD = this.gridDaGiao[row]["MA_GD"];
      this.gParams.txtTenKH = this.gridDaGiao[row]["TEN_KH"];

      this.gParams.txtDiaChiKH = this.gridDaGiao[row]["DIACHI_KH"];
      this.gParams.hdkh_id = parseInt(this.gridDaGiao[row]["HDKH_ID"]);
      this.gParams.hdtb_id = parseInt(this.gridDaGiao[row]["HDTB_ID"]);
      this.gParams.phieu_id = parseInt(this.gridDaGiao[row]["PHIEU_ID"]);
      await this.HienThiDanhSacHDTB_Phieu(
        this.gParams.hdkh_id,
        this.gParams.hdtb_id
      );
      let ds_hdtb = await (
        await API.getDSHBTBTheoHDKH(this.axios, {
          hdKdId: this.gParams.hdkh_id,
        })
      ).data.data;
      this.HienThiTTHopDongTB(ds_hdtb);
    },

    async HienThiDanhSacHDTB_Phieu(vhdkh_id, vhdtb_id) {
      let ds = await (
        await API.getDSPhuLucVTCapMienPhi(this.axios, {
          hdKdId: vhdkh_id,
          hdTbId: vhdtb_id,
          loai: 1,
        })
      ).data.data;
      this.gridThueBao = ds;
    },
    async HienThiTTHopDongTB(ds_hdtb) {
      if (ds_hdtb.length) {
        let tchd = ds_hdtb[0];
        this.gParams.loaihinh_tb = parseInt(tchd.LOAITB_ID);
        await this.LoadDSTBIHDTB();
        await this.HT_ThietBi();
      }
    },
    async LoadDSTBIHDTB() {
      this.gParams.dsCTM_TBI = [];
      let dstbi = await (
        await API.getDSThietBiHdTB(this.axios, {
          hdTbId: this.gParams.hdtb_id,
        })
      ).data.data;
      console.log();
      dstbi.forEach((element) => {
        this.gParams.dsCTM_TBI.push({
          LOAITBI_ID: element.LOAITBI_ID,
          HDTB_ID: element.HDTB_ID,
          SOLUONG: element.SOLUONG,
          TIEN: element.TIEN,
          VAT: element.VAT,
          TIEN_KM: element.TIEN_KM,
          VAT_KM: element.VAT_KM,
          TIEN_TRATRUOC: element.TIEN_TRATRUOC,
          BLOCK_DAU: element.BLOCK_DAU,
          BLOCK_TIEP: element.BLOCK_TIEP,
          GIA_BLOCK_TIEP: element.GIA_BLOCK_TIEP,
          SERIAL: element.SERIAL,
          SL_CHA: element.SL_CHA,
          SOLUONG: element.SOLUONG,
          TIEN_TRAGOP: element.TIEN_TRAGOP,
          TYLE_VAT: element.TYLE_VAT,
          TYLE_VAT_ID: element.TYLE_VAT_ID,
          VAT_BLOCK_TIEP: element.VAT_BLOCK_TIEP,
          VAT_TRAGOP: element.VAT_TRAGOP,
          VAT_TRATRUOC: element.VAT_TRATRUOC,
        });
      });
    },
    async HT_ThietBi() {
      let vsl_cha = -1;
      let soluong = 1;
      this.gParams.tien_kmtbi = 0;
      this.gParams.vat_kmtbi = 0;
      this.gParams.tien_tgtb_tragop = 0;
      this.gParams.vat_tgtb_tragop = 0;
      this.gParams.tien_tgtb_tratruoc = 0;
      this.gParams.vat_tgtb_tratruoc = 0;
      this.gParams.tien_tbi = 0;
      this.gParams.vat_tbi = 0;
      let dTyLe_KMTB = 0,
        dtien_kmtbi_tmp = 0,
        dvat_kmtbi_tmp = 0,
        dtien_tbi_tmp = 0,
        dvat_tbi_tmp = 0,
        dtien_tgtb_tratruoc_tmp = 0,
        dvat_tgtb_tratruoc_tmp = 0,
        dtien_tgtb_tragop_tmp = 0,
        dvat_tgtb_tragop_tmp = 0;
      this.lsbDanhSachTBI = [];
      this.gParams.dsCTM_TBI.forEach(async (element) => {
        let ds = await (
          await API.getTTCTThietBi(this.axios, {
            loaiTbiId: element.LOAITBI_ID,
          })
        ).data.data;
        let tenTbi = ds[0]["LOAI_TBI"] + " - Số lượng: " + element.SOLUONG;
        this.lsbDanhSachTBI.push(tenTbi);
        soluong = parseInt(element.SOLUONG);
        if (!this.isNullOrEmpty(element.TIEN)) {
          dtien_tbi_tmp = parseInt(element.TIEN);
          this.gParams.tien_tbi =
            this.gParams.tien_tbi + dtien_tbi_tmp * soluong;
        }
        if (!this.isNullOrEmpty(element.VAT)) {
          dvat_tbi_tmp = parseInt(element.VAT);
          this.gParams.vat_tbi = this.gParams.vat_tbi + dvat_tbi_tmp * soluong;
        }
        let dtTMP_KMTBI = await (
          await API.postDSKMThietBi(this.axios, {
            loaiTbiId: element.LOAITBI_ID,
            chiTietKmId: this.ds_CTKM(),
          })
        ).data.data;
        dtien_kmtbi_tmp = 0;
        dvat_kmtbi_tmp = 0;
        if (dtTMP_KMTBI.length) {
          dtien_kmtbi_tmp = parseInt(dtTMP_KMTBI[0]["TIEN_KM"]);
          dvat_kmtbi_tmp = parseInt(dtTMP_KMTBI[0]["VAT_KM"]);
          dTyLe_KMTB = parseInt(dtTMP_KMTBI[0]["TYLE_KM"]);
        } else {
          dtien_kmtbi_tmp = 0;
          dvat_kmtbi_tmp = 0;
          dTyLe_KMTB = 0;
        }
        if (dTyLe_KMTB > 0) {
          dtien_kmtbi_tmp = Math.round((dtien_tbi_tmp * dTyLe_KMTB) / 100);
          dvat_kmtbi_tmp = Math.round((dvat_tbi_tmp * dTyLe_KMTB) / 100);
        }
        if (dtTMP_KMTBI.length) {
          element.TIEN_KM = dtien_kmtbi_tmp;
          element.VAT_KM = dvat_kmtbi_tmp;
          this.gParams.tien_kmtbi += dtien_kmtbi_tmp * soluong;
          this.gParams.vat_kmtbi += dvat_kmtbi_tmp * soluong;
        }
      });
      console.log("this.lsbDanhSachTBI", this.lsbDanhSachTBI);
      await this.LayTongTien_HDTB();
      await this.HienThiTienKhoanMucMuaThietBi();
    },
    ds_CTKM() {
      let sDS = "-1";
      this.dsKM_HDTB.forEach((e) => {
        sDS += "," + e.CHITIETKM_ID;
      });
      this.dsDC_HDTB.forEach((e) => {
        sDS += "," + e.CHITIETKM_ID;
      });
      return sDS.split(",");
    },
    LayTongTien_HDTB() {
      try {
        let dtongtien = 0;
        let dtongvat = 0;

        dtongtien += this.gParams.tien_tbi;
        dtongvat += this.gParams.vat_tbi;
      } catch (error) {}
    },
    HienThiTienKhoanMucMuaThietBi() {
      if (this.gParams.ds_tien_khoanmuc.length) {
        this.gParams.ds_tien_khoanmuc.forEach((e) => {
          if (e.KHOANMUCTT_ID == "52") {
            e.TIEN = this.gParams.tien_tbi;
            e.VAT = this.gParams.vat_tbi;
          }
        });
      }
    },
    async hienthi_ds_dathuchien(row) {
      this.lsbDanhSachTBI = [] // clear thiết bị, k hiển thị nếu đã thực hiện
      this.gParams.khachhang_id = parseInt(
        this.gridDaThucHien[row]["KHACHHANG_ID"]
      );
      this.gParams.txtGiaGoi = this.gridDaThucHien[row]["GHICHU_HD"];
      this.gParams.txtMaKH = this.gridDaThucHien[row]["MA_KH"];
      this.gParams.txtMaGD = this.gridDaThucHien[row]["MA_GD"];
      this.gParams.txtTenKH = this.gridDaThucHien[row]["TEN_KH"];

      this.gParams.txtDiaChiKH = this.gridDaThucHien[row]["DIACHI_KH"];
      this.gParams.hdkh_id = parseInt(this.gridDaThucHien[row]["HDKH_ID"]);
      this.gParams.hdtb_id = parseInt(this.gridDaThucHien[row]["HDTB_ID"]);
      this.gParams.phieu_id = parseInt(this.gridDaThucHien[row]["PHIEU_ID"]);
      await this.HienThiDanhSacHDTB_Phieu(
        this.gParams.hdkh_id,
        this.gParams.hdtb_id
      );
      this.gParams.txtGhiChu = this.gridDaThucHien[row]["GHICHU"];
      try {
        this.gParams.cboketquaxl = parseInt(
          this.gridDaThucHien[row]["KQXL_ID"]
        );
        if (this.gParams.cboketquaxl == 420) {
          this.enabled.tsbtnGiaoPhieu = false;
        } else {
          this.enabled.tsbtnGiaoPhieu = true;
        }
      } catch (error) {}
    },
    TaoDuLieuCTThietBi(in_hdtb_id) {
      try {
        let vds_ctmua_tbi = [];
        if (this.gParams.dsCTM_TBI.length) {
          vds_ctmua_tbi = this.gParams.dsCTM_TBI;
        }
        vds_ctmua_tbi.forEach((element) => {
          element.HDTB_ID = in_hdtb_id;
        });
        return vds_ctmua_tbi;
      } catch (error) {}
    },
    onThueBaoQueryCellInfor(args) {
      switch (args.column.field) {
        case "SOLUONG":
          if (args.data.SOLUONG) {
            args.cell.setAttribute("style", "color: black;font-weight: bold; ");
          }
          break;
        case "DONGIA":
          if (args.data.DONGIA) {
            args.cell.setAttribute("style", "color: blue;font-weight: bold; ");
          }
          break;
        case "TIEN":
          if (args.data.TIEN) {
            args.cell.setAttribute("style", "color: red;font-weight: bold; ");
          }
          break;
      }
    },
    async btnLayTTMoi() {
      await this.reload();
    },
    async reload() {
      try {
        this.$root.loading(true);
        if (this.tabDsKhieuNai.tabSelected == this.tabDsKhieuNai.tabChuaXL) {
          this.enabled.tsbtnHoanThanh = true;
          let dt = await (
            await API.getDSPhieuCapVTMienPhi(this.axios, {
              huongGiaoId: 6824,
              loaiPhieu: 2,
            })
          ).data.data;
          this.gridDaGiao = dt;
          if (!dt.length) {
            this.clear();
          } else {
            this.hienthi_ds_dagiao(0);
          }
        } else {
          this.enabled.tsbtnHoanThanh = false;
          let dt = await (
            await API.getDSPhieuCapVTMienPhi(this.axios, {
              huongGiaoId: 6824,
              loaiPhieu: 3,
            })
          ).data.data;
          this.gridDaThucHien = dt;
          if (!dt.length) {
            this.clear();
          } else {
            this.hienthi_ds_dathuchien(0);
          }
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async tsbtnHoanThanh() {
      try {
        if (!this.gridDaGiao.length) {
          return this.$toast.error("Bạn chưa chọn phiếu xử lý!");
        }
        if (this.gParams.txtGhiChu.length > 400) {
          return this.$toast.error("Ghi chú giao giới hạn 400 ký tự!");
        }
        let dl_ct_tbi = this.TaoDuLieuCTThietBi(this.gParams.hdtb_id);
        // let json_tbi = JSON.stringify(dl_ct_tbi);
        let kq = "";
        let kqxl = parseInt(this.gParams.cboketquaxl);
        const isConfirm = await this.$bvModal.msgBoxConfirm(
          `Bạn có muốn hoàn thiện xử lý phiếu này không?`,
          {
            title: "Thông báo",
            size: "xs",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        );
        if (isConfirm) {
          this.$root.loading(true);
          kq = await (
            await API.postBanVatTuMienPhi(this.axios, {
              hdTbId: this.gParams.hdtb_id,
              kqXlId: kqxl,
              ghiChu: this.gParams.txtGhiChu,
              phieuId: this.gParams.phieu_id,
              dsCtMuaTbi: dl_ct_tbi,
            })
          ).data.data;
          let kq_num = parseInt(kq);
          if (kq_num) {
            this.$toast.success("Cập nhật kết quả duyệt thành công!");
            await this.clear();
            await this.reload();
          } else this.$toast.error("Có lỗi khi hoàn thiện:" + kq);
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    async closeGiaoPhieu(){
      await this.reload();
    },
    async tsbtnGiaoPhieu() {      

      this.isRender = true;
  
      this.gParams.quytrinh_id = this.gParams.gridDaThucHienRowChanged.QUYTRINH_ID;

      if (!this.gParams.quytrinh_id){
        this.$toast.error("Quy trình chưa được thiết lập!");
        return
      }
        
      let vhuonggiao_id = await ( await API.getDsHuongGiaoQuyTrinh(this.axios, {
          quyTrinhId: this.gParams.quytrinh_id,
          thuTuId: 3,
        })
      ).data.data[0].huongGiaoId;
      let dt_hg = await ( await API.postDsHuongGiao(this.axios, {
          quyenHuongGiao: [vhuonggiao_id],
          nhomQtId: 1,
          quyTrinhId: 0,
          loaiHdId: 0,
          dichVuVtId: 0,
        })
      ).data.data;
      let hg_id = dt_hg.filter((e) => e.HUONGGIAO_ID == vhuonggiao_id).length;

      if (hg_id) {
        if (vhuonggiao_id) {          
          this.$refs.frmGiaoPhieuCTTC.huonggiao = vhuonggiao_id;
          this.$refs.frmGiaoPhieuCTTC.txtMaGD = "";
          this.$refs.dlgGiaoPhieuCTTC.show();
        }
      } else {
        this.$refs.frmGiaoPhieuVT.parentForm = "frmXuLyYeuCau_VT"
        this.$refs.frmGiaoPhieuVT.openDialog();
      }
    },
    btnLayThietBi() {
      // this.$refs.
      this.$refs.BuyDevicesModal.show();
    },
    mapUppercase(objs) {
      let res = [];
      //  let props = Object.getOwnPropertyNames(obj[0]);
      for (const iterator of objs) {
        let o = {};
        for (const key in iterator) {
          o[key.toUpperCase()] = iterator[key];
        }
        res.push(o);
      }
      return res;
    },
    frmChonTbConfirm(data) {
      console.log(data, 1111);
      // console.log(this.mapUppercase(data));

      if (data.length) {
        this.gParams.dsCTM_TBI = this.mapUppercase(data);
        this.$refs.BuyDevicesModal.hide();
        this.HT_ThietBi();
      }
    },
    onGridDaGiaoRowChanged(data) {
      this.gParams.gridDaGiaoRowChanged = data;
      if (data) {
        let index = "";
        this.gridDaGiao.forEach((e, i) => {
          if (e.MA_GD == data.MA_GD) {
            return (index = i);
          }
        });
        this.hienthi_ds_dagiao(index);
      }
    },
    onGridDaThucHienRowChanged(data) {
      this.gParams.gridDaThucHienRowChanged = data;
      if (data) {
        let index = "";
        this.gridDaThucHien.forEach((e, i) => {
          if (e.MA_GD == data.MA_GD) {
            return (index = i);
          }
        });
        this.hienthi_ds_dathuchien(index);
      }
    },
    btntest() {
      this.$refs.BuyDevicesModal1.show();
    },
  },
  async mounted() {
    try {
      this.$root.loading(true);
      this.cboketquaxl = await (
        await API.getDSXulyCapVatTu(this.axios, {
          chungTuId: this.gParams.vchungtu_id,
        })
      ).data.data;
      this.gParams.cboketquaxl = this.setFirstValue(
        this.cboketquaxl,
        "kqxl_id"
      );
      let ds = await (
        await API.getDSPhieuCapVTMienPhi(this.axios, {
          huongGiaoId: 6824,
          loaiPhieu: 2,
        })
      ).data.data;
      this.gridDaGiao = ds;
    } catch (error) {
    } finally {
      this.$root.loading(false);
    }
  },
};
</script>

<style scoped src="./XuLyYeuCauVatTu.scss">
</style>