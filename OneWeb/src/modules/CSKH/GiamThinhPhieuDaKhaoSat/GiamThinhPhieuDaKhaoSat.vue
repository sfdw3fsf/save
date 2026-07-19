<template src="./GiamThinhPhieuDaKhaoSat.html"></template>
<style src="./GiamThinhPhieuDaKhaoSat.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import { mapActions, mapState, mapGetters } from "vuex";
import TableWithSearch from "../utils/TableWithSearch.vue";
import GiamThinhPhieuKhaoSatApi from "../api/GiamThinhPhieuKhaoSatApi";
import LichSuSuaPhieuKhaoSat from "./popup/LichSuSuaPhieuKhaoSat.vue";
import GuiSMSChoKhachHang from "./popup/GuiSMSChoKhachHang.vue";
import ComboboxGrid from "../../QLTN/components/ComboboxGrid";
import select2 from "@/components/Select2.vue";
import frmKhaoSatKH_HDTB from '../KhaoSat/frmKhaoSatKH_HDTB.vue'
import frmKhaoSatKH from '../KhaoSat/frmKhaoSatKH.vue'
import Vue from "vue"
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

Vue.use(DialogPlugin)
export default {
  name: "GiamThinhPhieuDaKhaoSat",
  components: {
    breadcrumb,
    TableWithSearch,
    LichSuSuaPhieuKhaoSat,
    GuiSMSChoKhachHang,
    appSelect2: select2,
    appComboboxGrid: ComboboxGrid,
    frmKhaoSatKH_HDTB,
    frmKhaoSatKH
  },
  data() {
    return {
      header: {
        title: "DANH SÁCH PHIẾU ĐÃ KHẢO SÁT PHỤC VỤ GIÁM THÍNH",
        list: [
          {
            name: "Chăm sóc khách hàng",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Giám thính phiếu đã khảo sát",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      dateConfig: {
        altInput: true,
        dateFormat: "d/m/Y",
        altFormat: "d/m/Y",
      },
      fromDate: new Date(),
      toDate: new Date(),
      today: new Date(),
      dsLoaiPhieuKhaoSatSelected: 0,
      dsDonViTrungTamVienThongSelected: "",
      dsDienThoaiVienSelected: "",
      dsPhanLoaiKhachHangSelected: "",
      dsLoaiKQGiamThinhSelected: "",
      vbtlIdSelected: 0,
      vHdkhIdSelected: 0,
      vBaoHongIdSelected: 0,
      vSoDtSelected: "",
      visible: false,
      tenDTV: "",
      tableColumns: {
        dsThueBao: [
          {
            fieldName: "MA_TB",
            headerText: "Số máy/Acc",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "FILENAME",
            headerText: "Audio",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGUOI_HOI",
            headerText: "Điện thoại viên",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NOIDUNG_CSKH",
            headerText: "Nội dung CSKH",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "LOAI_PHIEU",
            headerText: "Loại phiếu",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "LOAIHINH_TB",
            headerText: "Loại hình",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "GHICHU_KS",
            headerText: "Ghi chú KS",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGAY_HOI",
            headerText: "TG CSKH",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "DIENTHOAI_LH",
            headerText: "Số liên hệ",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "TRAM_VT",
            headerText: "Đơn vị",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "TEN_TB",
            headerText: "Tên thuê bao",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "DIACHI_LD",
            headerText: "Địa chỉ lắp đặt",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGAY_HT",
            headerText: "TG hoàn tất",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGUOI_TL",
            headerText: "Liên hệ",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "TEN_PLKH",
            headerText: "Phân loại KH",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "CHITIEU_TG",
            headerText: "Chỉ tiêu TG",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "GHICHU_TB",
            headerText: "Nội dung sửa tốt",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "GHICHU_XL",
            headerText: "Nội dung báo hư",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "LYDO_HONG",
            headerText: "Tên hư hỏng",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "LAN_KS",
            headerText: "Lần gọi",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "TEN_LOAIHD",
            headerText: "Loại HĐ",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "TEN_KIEULD",
            headerText: "Kiểu lắp đặt",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "PHIEU_ID",
            headerText: "Phiếu",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGAYHEN_TU",
            headerText: "Hẹn từ",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGAYHEN_DEN",
            headerText: "Hẹn đến",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
        dsNoiDungKhaoSat: [
          {
            fieldName: "STT",
            headerText: "STT",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "CAUHOI",
            headerText: "Câu hỏi",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "TRALOI",
            headerText: "Trả lời",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "GHICHU",
            headerText: "Ghi chú",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGAY_HOI",
            headerText: "Thời gian",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
      },
      fieldsDienThoaiVien: [
        {
          key: "selectedColumn",
          label: "",
          tdClass: "w20 center",
        },
        {
          key: "text",
          label: "Tên nhân viên",
          isSearch: true,
        },
        {
          key: "value",
          label: "Max NV",
          isSearch: true,
        },
      ],
      pDsDonVi: [],
      pDsLoaiKhachHang: [],
      pDsLoaiKetQua: [],
      pDsDienThoaiVien: [],
      pLoaiPhieuID: -1,
      pCurrentPhieuSelected: null,
    };
  },
  computed: {
    ...mapGetters("giamthinhphieudakhaosat", [
      "getdsLoaiPhieuKhaoSatGetter",
      "getdsDonViTrungTamVienThongGetter",
      "getdsDienThoaiVienGetter",
      "getdsPhanLoaiKhachHangGetter",
      "getdsLoaiKQGiamThinhGetter",
    ]),
    ...mapState("giamthinhphieudakhaosat", ["dsThueBao", "dsNoiDungKS"]),
    pDsLoaiPhieuKhaoSat() {
      return [
        { id: "0", text: "Tất cả" },
        { id: "1", text: "Thi công" },
        { id: "2", text: "Báo hỏng" },
      ];
    },
  },
  async mounted() {
    this.loading(true);
    this.clearData();
    try {
      await this.getDsDonVi();
      await this.getDsLoaiKhachHang();
      await this.getDsDienThoaiVien();
      await this.getDsLoaiKetQua();
    } catch (e) {
      console.log(e);
    } finally {
      this.loading(false);
    }
  },
  methods: {
    ...mapActions("giamthinhphieudakhaosat", [
      "getDsThueBao",
      "getNoiDungKS",
      "clearData",
    ]),
    clickOutside() {
      console.log("clicked");
    },
    toggleDTVPopup() {
      this.visible = !this.visible;
    },
    async layPhieu() {
      // console.log("layPhieu");
      // this.$refs.dsThueBao.fetchData();
      try {
        this.loading(true);
        await this.fetchData();
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },
    onDsDTVChanged(dataItem) {
      if (dataItem) this.dsDienThoaiVienSelected = dataItem.NHANVIEN_ID;
    },
    async onRowSelected(item) {
      console.log(item);
      if (item && item.length > 0) {
        this.vbtlIdSelected = item[0].TBTL_ID;
        this.vHdkhIdSelected = item[0].HDKH_ID;
        this.vBaoHongIdSelected = item[0].BAOHONG_ID;
        this.vSoDtSelected = item[0].DIENTHOAI_LH;
        await this.$refs.dsNoiDungKhaoSat.fetchData();
      }
    },
    async gridThueBao_selectedRowChanged(dataItem) {
      if (dataItem) {
        this.pCurrentPhieuSelected = dataItem
        this.pLoaiPhieuID = dataItem.LOAIPHIEU_ID
        this.vbtlIdSelected = dataItem.TBTL_ID;
        this.vHdkhIdSelected = dataItem.HDKH_ID;
        this.vBaoHongIdSelected = dataItem.BAOHONG_ID;
        this.vSoDtSelected = dataItem.DIENTHOAI_LH;
        //@fakedata
        let postData = {
          tbtlId: this.vbtlIdSelected,
          pageNo: 1,
          pageSize: 1000,
        };
        try {
          this.loading(true);
          await this.getNoiDungKS(postData);
          this.$nextTick(() => {
          if (this.dsNoiDungKS.length > 0) {
            this.$refs.gridNoiDungKS.setCurrentSelectedRow(0)
          }
        })
        } catch (e) {
          console.log(e);
        } finally {
          this.loading(false);
        }
      }
    },
    async gridNoiDungKS_selectedRowChanged(dataItem) {},
    async fetchData() {
      this.loading(true);
      let response = null;
      try {
        //@fakedata
        let postData = {
          tuNgay: moment(this.fromDate).format("DD/MM/YYYY"),
          denNgay: moment(this.toDate).format("DD/MM/YYYY"),
          loaiPhieu: this.dsLoaiPhieuKhaoSatSelected,
          donViId: this.dsDonViTrungTamVienThongSelected,
          maPlkh: this.dsPhanLoaiKhachHangSelected,
          dtVienId: this.dsDienThoaiVienSelected,
          answerId: this.dsLoaiKQGiamThinhSelected,
          pageNo: 1,
          pageSize: 1000,
        };
        await this.getDsThueBao(postData);
        this.$nextTick(() => {
          if (this.dsThueBao.length > 0) {
            this.$refs.gridThueBao.setCurrentSelectedRow(0)
          }
        })
      } catch (e) {
      } finally {
        this.loading(false);
      }

      return response;
      //console.log(response)
    },
    async fetchChiTietTraLoiKhaoSat() {
      this.loading(true);
      let response = null;
      try {
        //@fakedata
        let postData = {
          tbtlId: this.vHdkhIdSelected,
        };
        response = await GiamThinhPhieuKhaoSatApi.getChiTietTraLoiKhaoSat(
          this.axios,
          postData
        );
      } catch (e) {
      } finally {
        this.loading(false);
      }

      return response;
    },
    async getDsDonVi() {
      try {
        this.pDsDonVi = [];
        //fix cứng tham số
        let response = await this.$root.context.post(
          "/web-cskh/api/don-vi/phong",
          {
            //@fakedata
            loaiDichVuId: 5, //2 //5
            phongDichVuId: 0,
          }
        );
        this.pDsDonVi.push({id: '', text: ''})
        if (response.error_code === "BSS-00000000") {
          let ds = response.data || [];
          ds.forEach((e) => {
            this.pDsDonVi.push({
              id: e.DONVI_ID,
              text: e.TEN_DV,
            });
          });
        }
      } catch (e) {
        this.pDsDonVi = [];
      }
    },
    async getDsLoaiKhachHang() {
      try {
        this.pDsLoaiKhachHang = [];
        let response = await this.$root.context.get(
          "/web-cskh/api/danh-muc/phan-loai-kh"
        );
        this.pDsLoaiKhachHang.push({id: '', text: ''})
        if (response.error_code === "BSS-00000000") {
          let ds = response.data || [];
          ds.forEach((e) => {
            this.pDsLoaiKhachHang.push({
              id: e.MA_PLKH,
              text: e.TEN_PLKH,
            });
          });
        }
      } catch (e) {
        this.pDsLoaiKhachHang = [];
      }
    },
    async getDsLoaiKetQua() {
      try {
        this.pDsLoaiKetQua = [];
        let response = await this.$root.context.get(
          "/web-cskh/api/danh-muc/loai-ket-qua-giam-thinh"
        );
        if (response.error_code === "BSS-00000000") {
          let ds = response.data || [];
          this.pDsLoaiKetQua.push({id: '', text: ''})
          ds.forEach((e) => {
            this.pDsLoaiKetQua.push({
              id: e.TRALOI_ID,
              text: e.NOIDUNG,
            });
          });
        }
      } catch (e) {
        this.pDsLoaiKetQua = [];
      }
    },
    async getDsDienThoaiVien() {
      try {
        this.pDsDienThoaiVien = [];
        //fix cứng tham số
        let response = await this.$root.context.get(
          "/web-cskh/api/danh-muc/dien-thoai-vien?dsLoaiNvId=30,95&donViId=0"
        );
        if (response.error_code === "BSS-00000000") {
          this.pDsDienThoaiVien = response.data || [];
        }
      } catch (e) {
        this.pDsDienThoaiVien = [];
      }
    },
    
    async openSuaKSPopup() {
      if (this.dsThueBao.length <= 0) {
        this.$root.toastError("Danh sách phiếu trống!")
        return
      }

      let vtbtl_id = this.pCurrentPhieuSelected.TBTL_ID
      let loaiPhieuId = this.pCurrentPhieuSelected.LOAIPHIEU_ID
      let vhdkh_id = 0
      let vid = 0
      let vgiaophieu_ks_id = 0

      if (loaiPhieuId == 1) {
        vhdkh_id = this.pCurrentPhieuSelected.HDKH_ID
        vid = this.pCurrentPhieuSelected.HDTB_ID
      }
      else {
        vid = this.pCurrentPhieuSelected.BAOHONG_ID
      }

      if (loaiPhieuId == 1) {
        this.$refs.frmKhaoSatKH_HDTB.hdkhId = vhdkh_id
        this.$refs.frmKhaoSatKH_HDTB.hdtbId = vid
        this.$refs.frmKhaoSatKH_HDTB.giaoPhieuKsId = vgiaophieu_ks_id
        this.$refs.frmKhaoSatKH_HDTB.tbtlId = vtbtl_id
        // this.$refs.frmKhaoSatKH_HDTB.kieuKs = this.pKieuKs
        // this.$refs.frmKhaoSatKH_HDTB.moPhieu = this.pMoPhieu
        this.$refs.frmKhaoSatKH_HDTB.openDialog()
      }
      else if (loaiPhieuId == 2) {
        this.$refs.frmKhaoSatKH.giaoPhieuKsId = vgiaophieu_ks_id
        this.$refs.frmKhaoSatKH.baoHongId = vid
        this.$refs.frmKhaoSatKH.tbtlId = vtbtl_id
        this.$refs.frmKhaoSatKH.pKieuKs = this.pMoPhieu
        this.$refs.frmKhaoSatKH.openDialog()
      }
    }
  },
};
</script>
