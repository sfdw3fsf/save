<template src="./index.html"></template>

<script>
import { required } from 'vuelidate/lib/validators'
import { Page, Filter, CommandColumn, Resize, Sort, Freeze } from "@syncfusion/ej2-vue-grids"
import XLSX from "xlsx";
import { L10n } from '@syncfusion/ej2-base'
import ModalPopupDSThueBao from "./PopupTraCuuThueBao.vue"
L10n.load({
  'vi-VN': {
    treegrid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})

export default {
  components: {ModalPopupDSThueBao},
  provide: {
    grid: [Filter, Page, CommandColumn, Resize, Sort, Freeze]
  },
  data: function () {
    return {
      dsDichVu: [],
      dsLoaiHinh: [],
      dsHoTro: [],
      dsChiTiet: [],
      cboDichVu: null,
      cboLoaiHinh: null,
      diaChiInput: null,
      dichVuInput : null,
      loaiHinhInput : null,
      ngayTiepNhanInput : null,
      trangThaiInput : null,
      tenThueBaoInput: null,
      maHoTroInput: null,
      soMayInput: null,
      showGridHoTro: false,
      showGridChiTiet: false,
      isDisabledNhapMoi: false,
      isShowTraCuu : false,
      hoTroSelected: null,
      TTObject: {
        p_dichvuvt_id: null,
        p_loaitb_id: null,
        p_ma_timkiem: null,
        p_ma_ht: null,
        p_ten_tb: null,
        p_diachi: null,
        p_ngay_tn: null
      },
    }
  },
  validations: {
    tienDoHoTro: {
      required
    }
  },
  created: async function () {
    this.showGridHoTro = true;
    this.showGridChiTiet = true;
    try {
      this.loading(true);
      await this.loadDsDichVu();
      if(this.cboDichVu){
        await this.loadDsLoaiHinh();
      }
    } catch (e) {
    } finally {
      this.loading(false);
    }
  },
  destroyed() {
    this.showGridHoTro = false;
    this.showGridChiTiet = false;
  },
  methods: {
    btnXuatDS: function () {
      try {
        this.loading(true);
        let dsXuatExcel = [];
        if (this.dsHoTro.length > 0) {
          dsXuatExcel = [...this.dsHoTro];
          dsXuatExcel = dsXuatExcel.map(item => ({
            "Mã hỗ trợ": item.MA_HT,
            "Số máy/Acc": item.MA_TB,
            "Dịch vụ": item.TEN_DVVT,
            "Loại hình": item.LOAIHINH_TB,
            "Ngày tiếp nhận": item.NGAY_TN,
            "Điện thoại LH": item.DIENTHOAI_LH,
            "Địa chỉ LH": item.DIACHI_KH,
            "Trạng thái": item.TRANGTHAI_HT,
            "Loại HT": item.LOAI_HT,
            "Yêu cầu HT": item.YC_HOTRO,
            "Chủ đề cấp 1": item.CHUDE_HT_CAP1,
            "Chủ đề cấp 2": item.CHUDE_HT_CAP2,
            "Nội dung HT": item.NOIDUNG_HT,
            "Tên thuê bao": item.TEN_TB,
            "Địa chỉ thuê bao": item.DIACHI_LD,
          }));
        } else {
          // this.$root.toastError("Danh sách không có dữ liệu để xuất!")
          dsXuatExcel = [{
            "Mã hỗ trợ": null,
            "Số máy/Acc": null,
            "Dịch vụ": null,
            "Loại hình": null,
            "Ngày tiếp nhận": null,
            "Điện thoại LH": null,
            "Địa chỉ LH": null,
            "Trạng thái": null,
            "Loại HT": null,
            "Yêu cầu HT": null,
            "Chủ đề cấp 1": null,
            "Chủ đề cấp 2": null,
            "Nội dung HT": null,
            "Tên thuê bao": null,
            "Địa chỉ thuê bao": null,
          }];
        }
        let ds = XLSX.utils.json_to_sheet(dsXuatExcel);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, ds, "DS_HOTRO"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "Tra cứu danh sách hỗ trợ_" + this.convertDate(new Date()) + ".xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    btnXuatCT: function () {
      try {
        this.loading(true);
        let dsXuatExcel = [];
        if (this.dsChiTiet.length > 0) {
          dsXuatExcel = [...this.dsChiTiet];
          dsXuatExcel = dsXuatExcel.map(item => ({
            "Đơn vị giao": item.TEN_DONVI_GIAO,
            "Nhân viên giao": item.TEN_NHANVIEN_GIAO,
            "ĐTNV giao": item.SO_DT_NHANVIEN_GIAO,
            "Ngày giao": item.NGAYGIAO,
            "Nội dung giao": item.ND_GIAO,
            "Đơn vị nhận": item.TEN_DONVI_NHAN,
            "NV trả": item.TEN_NHANVIEN_TRA,
            "Ngày trả": item.NGAYTRA,
            "Nội dung trả": item.ND_TRA,
            "Trạng thái phiếu'": item.TRANGTHAI_PH,
            "Nhân viên thực hiện": item.TEN_NHANVIEN_THUCHIEN,
            "ĐTNV thực hiện": item.SO_DT_NHANVIEN_THUCHIEN,
            "Ngày TH": item.NGAY_TH,
            "Nội dung HT": item.NOIDUNG_TH,
            "Người CN": item.NGUOI_CN,
            "Ngày CN": item.NGAY_CN,
          }));
        } else {
          // this.$root.toastError("Danh sách không có dữ liệu để xuất!")
          dsXuatExcel = [{
            "Đơn vị giao": null,
            "Nhân viên giao": null,
            "ĐTNV giao": null,
            "Ngày giao": null,
            "Nội dung giao": null,
            "Đơn vị nhận": null,
            "NV trả": null,
            "Ngày trả": null,
            "Nội dung trả": null,
            "Trạng thái phiếu'": null,
            "Nhân viên thực hiện": null,
            "ĐTNV thực hiện": null,
            "Ngày TH": null,
            "Nội dung HT": null,
            "Người CN": null,
            "Ngày CN": null,
          }];
        }
        let ds = XLSX.utils.json_to_sheet(dsXuatExcel);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, ds, "DS_CHITIET"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "Tra cứu chi tiết hỗ trợ_" + this.convertDate(new Date()) + ".xlsx");
      } catch (error) {
        this.$root.toastError(error);
      } finally {
        this.loading(false);
      }
    },

    queryCellInfoEvent: function (args) {
      if (!!args.column.field) {
        args.cell.title = args.cell.innerHTML
      }
    },

    loadDsDichVu: async function () {
      try {
        this.loading(true);
        this.dsDichVu = await this.getDsDichVu();
        this.cboDichVu = this.dsDichVu[0].DICHVUVT_ID;
      } catch (e) {
        this.$root.toastError(e);
      } finally {
        this.loading(false);
      }
    },

    getDsDichVu: async function () {
      let rs = [];
      try {
        let r = await this.$root.context.get(
          "/web-quantri/danhmuc-chung/CSS_DICHVU_VT"
        );
        rs = r.data;
        rs.unshift({ DICHVUVT_ID: -1 , TEN_DVVT: 'Tất cả' });
      }
      catch (e) {
        if (e) {
          console.log(e)
          this.$root.toastError(e);
        } else {
          this.$root.toastError("Có lỗi xảy ra khi tải danh sách sự cố");
        }
      }
      return rs;
    },

    loadDsLoaiHinh: async function () {
      try {
        this.loading(true);
        this.dsLoaiHinh = await this.getDsLoaiHinh();
        this.cboLoaiHinh = this.dsLoaiHinh[0].ID;
      } catch (e) {
        this.$root.toastError(e);
      } finally {
        this.loading(false);
      }
    },

    getDsLoaiHinh: async function () {
      let rs = [];
      if(this.cboDichVu != -1){
      try {
        let r = await this.$root.context.get(
          "/web-thicong/danhmuc/lay_ds_loaihinh_thuebao_dichvu?dichVuVtId=" + this.cboDichVu
        );
        rs = r.data;
      }
      catch (e) {
        if (e) {
          console.log(e)
          this.$root.toastError(e);
        } else {
          this.$root.toastError("Có lỗi xảy ra khi tải danh sách sự cố");
        }
      }}
      else{
        rs.push({ ID: -1 , NAME: 'Tất cả' });
      }
      return rs;
    },

    // loadDsTrangThai: async function (){
    //   try {
    //     this.loading(true);
    //     this.dsTrangThai = await this.getDsTrangThai();
    //     this.cboTrangThai = this.dsTrangThai[0].TTHT_ID;
    //   } catch (e) {
    //     this.$root.toastError(e);
    //   } finally {
    //     this.loading(false);
    //   }
    // },

    // getDsTrangThai: async function () {
    //   let rs = [];
    //   try {
    //     let r = await this.$root.context.post(
    //       "/web-cskh/api/xuly-hotro-kh/dm-tt-hotro"
    //     );
    //     // let r = await this.$root.context.get(
    //     //   "/web-cskh/api/danh-muc/ho-tro?loaiDanhMuc=TRANGTHAI_HT"
    //     // );
    //     rs = r.data;
    //   }
    //   catch (e) {
    //     if (e) {
    //       console.log(e)
    //       this.$root.toastError(e);
    //     } else {
    //       this.$root.toastError("Có lỗi xảy ra khi tải danh sách sự cố");
    //     }
    //   }
    //   return rs;
    // },

    getDsHoTro: async function (objectSearch) {
      let rs = [];
      try {
        let r = await this.$root.context.post(
          "/web-cskh/api/xuly-hotro-kh/ds-ht-kh", objectSearch
        );
        rs = r.data;
      }
      catch (e) {
        if (e) {
          console.log(e)
          this.$root.toastError(e);
        } else {
          this.$root.toastError("Có lỗi xảy ra khi tải danh sách sự cố");
        }
      }
      return rs;
    },

    loadDsHoTro: async function (objectSearch){
      try {
        this.loading(true);
        this.dsHoTro = await this.getDsHoTro(objectSearch);
      } catch (e) {
        this.$root.toastError(e);
      } finally {
        this.loading(false);
      }
    },

    loadDsChiTiet: async function (id){
      try {
        this.loading(true);
        this.dsChiTiet = await this.getDsChiTiet(id);
      } catch (e) {
        this.$root.toastError(e);
      } finally {
        this.loading(false);
      }
    },

    getDsChiTiet: async function (id) {
      let rs = [];
      try {
        let r = await this.$root.context.post(
          "/web-cskh/api/xuly-hotro-kh/ct-hk-kh", { id: id }
        );
        rs = r.data;
      }
      catch (e) {
        if (e) {
          console.log(e)
          this.$root.toastError(e);
        } else {
          this.$root.toastError("Có lỗi xảy ra khi tải danh sách sự cố");
        }
      }
      return rs;
    },

    btnTimKiem: async function () {
      try {
        this.loading(true);
        let objectSearch = new Object()
        objectSearch.p_dichvuvt_id = this.cboDichVu ? this.cboDichVu : null;
        objectSearch.p_loaitb_id = this.cboLoaiHinh ? this.cboLoaiHinh : null;
        objectSearch.p_ma_timkiem = this.soMayInput ? this.soMayInput : "";
        objectSearch.p_ma_ht = this.maHoTroInput ? this.maHoTroInput : "";
        objectSearch.p_ten_tb = this.tenThueBaoInput ? this.tenThueBaoInput : "";
        objectSearch.p_diachi = this.diaChiInput ? this.diaChiInput : "";
        // objectSearch.p_ngay_tn = this.cboNgayTN ? this.convertDate(this.cboNgayTN) : null;
        // objectSearch.p_trangthai_ht = this.cboTrangThai ? this.cboTrangThai : null;
        objectSearch.p_ngay_tn = null;
        objectSearch.p_trangthai_ht = null;
        // if (this.validateData(objectSearch)) return
        await this.loadDsHoTro(objectSearch)
      } catch (e) {
        if (e) {
          console.log(e)
          this.$root.toastError(e);
        } else {
          this.$root.toastError("Có lỗi xảy ra khi tải danh sách sự cố");
        }
      } finally {
        this.loading(false);
      }

    },

    validateData(data) {
      this.$v.$touch();
      let errorMessage = []
      if (data.donViId == null) errorMessage.push("Bạn chưa chọn đơn vị")
      if (data.nguonSuCoId == null) errorMessage.push("Bạn chưa chọn nguồn sự cố")
      if (data.trangThaiSuCoID == null) errorMessage.push("Bạn chưa chọn trạng thái sự cố")
      if (data.tuNgayYC == null && this.allowBHDate === true) errorMessage.push("Bạn chưa chọn Từ ngày yêu cầu")
      if (errorMessage.length > 0) {
        this.$root.toastError(errorMessage.join('\n'))
        return true
      } else {
        return false
      }
    },
    showTraCuuTB(){
      this.$bvModal.show("popupTraCuuTB");
    },
    popupTraCuu(){
      this.$bvModal.hide("popupTraCuuTB");
    },

    btnXuatThoat: async function () {

    },

    async SCrow_selected(args) {
      this.isDisabledNhapMoi = true
      this.hoTroSelected = args.data
      if (this.hoTroSelected) {
        this.dichVuInput = this.hoTroSelected.TEN_DVVT;
        this.loaiHinhInput = this.hoTroSelected.LOAIHINH_TB;
        this.ngayTiepNhanInput = this.hoTroSelected.NGAY_TN;
        this.trangThaiInput = this.hoTroSelected.TRANGTHAI_HT;
        this.tenThueBaoInput = this.hoTroSelected.TEN_TB;
        this.diaChiInput = this.hoTroSelected.DIACHI_LD;
        try {
          this.loading(true);
          await this.loadDsChiTiet(this.hoTroSelected.HTKH_ID);
        } catch (err) {
          console.log(err);
        } finally {
          this.loading(false);
        }
      }
    },
    convertDate: function (date) {
      var date = new Date(date)
      var dd = String(date.getDate()).padStart(2, '0')
      var mm = String(date.getMonth() + 1).padStart(2, '0') //January is 0!
      var yyyy = date.getFullYear()
      return date = dd + '/' + mm + '/' + yyyy
    },
    clearTTObject() {
      this.TTObject.p_dichvuvt_id = null;
      this.TTObject.p_loaitb_id = null;
      this.TTObject.p_ma_timkiem = null;
      this.TTObject.p_ma_ht = null;
      this.TTObject.p_ten_tb = null;
      this.TTObject.p_diachi = null;
      this.TTObject.p_ngay_tn = null;
    },
    ChapNhanTB(thueBaoSelected){
      // console.log(thueBaoSelected);
      this.soMayInput = thueBaoSelected.MA_TB;
    },
  },
  computed: {
    isDisabled: function () {
      return !this.isDisabledNhapMoi;
    }
  },
  watch: {

  },
  async mounted() {
    try {
      this.$store.dispatch('setHeader', {
        title: 'TRA CỨU CHI TIẾT XỬ LÝ HỖ TRỢ'
      })
    } catch (e) {
      console.log(e);
    }
  }
}


</script>

<style>
.info-row .value.calender-tientrinhsuco {
  display: flex;
  align-items: center;
  flex-wrap: nowrap;
}

.page-content {
  top: 50px;
}

.info-row .key {
    width: 150px;
}

.modal-body.body-content {
    padding: 0px;
}

.box-form.popup {
  margin: 5px;
  width: auto;
}

</style>
