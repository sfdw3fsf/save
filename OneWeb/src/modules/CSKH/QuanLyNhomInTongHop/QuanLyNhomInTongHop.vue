<template src="./QuanLyNhomInTongHop.html"></template>
<style src="./QuanLyNhomInTongHop.scss"></style>
<script>
import CustomDataGrid from "../utils/CustomDataGrid.vue";
import QuanLyNhomInTongHopApi from "../api/QuanLyNhomInTongHopApi";
import ChonThanhToanTuFile from "./popup/ChonThanhToanTuFile.vue";
import BssRequiredMarker from "@/components/BssRequiredMarker";
import { formatString, isNullOrEmpty } from "../utils/functions/validators.js";
import Vue from "vue";
import { CheckBoxSelection } from '@syncfusion/ej2-vue-dropdowns'

const API_SUCCESS = "BSS-00000000";
export default {
  components: {
    appChonThanhToanTuFile: ChonThanhToanTuFile,
    datagrid: CustomDataGrid,
    BssRequiredMarker,
  },
  created() {
    this.clearData();
    this.getDsLoaiFile();
    
    
  },
  name: "QuanLyNhomInTongHop",
  provide: {
    multiselect: [CheckBoxSelection]
  },
  async mounted() {
    this.loading(true);
    //this.clearData()
    await this.getDsNVTaoNhomKH();
    await this.getDsMaNhom();
    this.loading(false);
    //this.setButtonStatus(3)
  },
  destroyed() {},
  data() {
    return {
      header: {
        title: "THÊM NHÓM VÀO DANH SÁCH IN TỔNG HỢP",
        list: [
          { name: "Chăm sóc khách hàng", link: { name: "Ui.cards" } },
          {
            name: "Quản lý nhóm in tổng hợp",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      txtSMTaoNhom: "",
      txtMaTB: "",
      txtTenNhom: "",
      txtMaIG: "",
      txtNguoiLH: "",
      txtSoFax: "",
      txtEmail: "",
      txtSDT: "",
      txtMaTT: "",
      txtDiaChi: "",
      dsNVTaoNhom: [],
      nhomKHLon: {
        NHOMKH_ID: "",
        TENNHOM: "",
      },
      maTTThuocNhom: null,
      btnNhapMoiEnable: true,
      btnCapNhapEnable: true,
      btnXoaNhomEnable: true,
      btnThemEnable: true,
      btnXoaEnable: true,
      tuDongStatus: 1,
      confirmXoa: false,

      optNhanVien: {
        dataSource: [],
        selectedValue: -1,
        nvLocSelectedValue: -1,
      },
      optMaNhom: {
        dataSource: []
      },
      dgvNhomKHL: {
        dataSource: [],
        columns: [
          {
            fieldName: "STT",
            headerText: "Stt",
            allowFiltering: true,
            allowSorting: false,
            width: 60,
          },
          {
            fieldName: "MA_KH",
            headerText: "Mã KH",
            allowFiltering: true,
            allowSorting: false,
            width: 120,
          },
          {
            fieldName: "MANHOM",
            headerText: "Mã nhóm",
            allowFiltering: true,
            allowSorting: false,
            width: 90,
          },
          {
            fieldName: "NHOMKH_ID",
            headerText: "Nhóm ID",
            allowFiltering: true,
            allowSorting: false,
            width: 90,
          },
          {
            fieldName: "TENNHOM",
            headerText: "Tên nhóm",
            allowFiltering: true,
            allowSorting: false,
            width: 250,
          },
          {
            fieldName: "LIENHE",
            headerText: "Liên hệ",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "SDT",
            headerText: "Số ĐT",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "DIA_CHI",
            headerText: "Địa chỉ",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "MAIL",
            headerText: "Email",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "FAX",
            headerText: "Số Fax",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
      },
      dgvMaTTThuocNhom: {
        dataSource: [],
        columns: [
          {
            fieldName: "STT",
            headerText: "Stt",
            allowFiltering: true,
            allowSorting: false,
            width: 60,
          },
          {
            fieldName: "MA_TT",
            headerText: "Mã TT",
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: "MAIN_GHEP",
            headerText: "Mã ghép",
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: "TD_CN",
            headerText: "Tự động CN",
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: "MST",
            headerText: "Mã số thuế",
            allowFiltering: true,
            allowSorting: false,
            width: 120,
          },
          {
            fieldName: "NGAY_CN",
            headerText: "Ngày CN",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGUOI_CN",
            headerText: "Người CN",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
      },
      dgvMaTTKoThuocNhom: {
        dataSource: [],
        columns: [
          {
            fieldName: "STT",
            headerText: "Stt",
            allowFiltering: true,
            allowSorting: false,
            width: 60,
          },
          {
            fieldName: "MA_TT",
            headerText: "Mã TT",
            allowFiltering: true,
            allowSorting: false,
            width: 120,
          },
          {
            fieldName: "TEN_TT",
            headerText: "Tên thanh toán",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "MST",
            headerText: "Mã số thuế",
            allowFiltering: true,
            allowSorting: false,
            width: 120,
          },
          {
            fieldName: "DIACHI_TT",
            headerText: "Địa chỉ thanh toán",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
      },

      customTemplate: function () {
        return {
          template: Vue.component("footerTemplate", {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } };
            },
          }),
        };
      },

      vKhachHangId: 0,
      vNhomKhId: 0,
      dsLoaiFile:[],
      IdsFile: []
    };
  },
  computed: {
    TC_TTThuocNhomAggregatesColumns() {
      return [
        {
          field: "MA_TT",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.TC_TTThuocNhomCustomAggregate,
        },
      ];
    },
    TC_TTKoThuocNhomAggregatesColumns() {
      return [
        {
          field: "MA_TT",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.TC_TTKoThuocNhomCustomAggregate,
        },
      ];
    },
  },
  watch: {},
  methods: {
    TC_TTThuocNhomCustomAggregate() {
      return this.dgvMaTTThuocNhom.dataSource.length;
    },
    TC_TTKoThuocNhomCustomAggregate() {
      return this.dgvMaTTKoThuocNhom.dataSource.length;
    },
    async onThemClicked() {
      try {
        if (this.dgvNhomKHL.length <= 0) {
          this.$root.toastError("Chưa có nhóm khách hàng in tổng hợp!");
          return;
        }

        if (!this.txtMaTT) {
          this.$root.toastError("Bạn chưa nhập mã thanh toán cần thêm!");
          return;
        }

        this.$root.showLoading(true);

        let postData = {
          nhomKhId: this.vNhomKhId,
          maTt: formatString(this.txtMaTT),
          tuDong: this.tuDongStatus,
        };

        let response = await this.$root.context.post(
          "/web-cskh/api/nhom-in-tong-hop/them-mtt",
          postData
        );
        if (response.error_code == API_SUCCESS) {
          this.$root.toastSuccess("Thêm mới thanh toán vào nhóm thành công !");

          await this.getDsTT(this.vKhachHangId, this.vNhomKhId);
        } else {
          this.$root.toastError("Có lỗi : " + response.data.message);
        }
      } catch (e) {
        console.log(e);
        this.$root.toastError("Có lỗi! " + e.response.data.message);
      } finally {
        this.$root.showLoading(false);
      }
    },

    onXoaClicked() {
      if (this.dgvMaTTThuocNhom.dataSource.length <= 0) {
        this.$root.toastError(
          "Danh sách thanh toán thuộc nhóm trống! Bạn không thể thực hiện xóa"
        );
        return;
      }
      this.$refs.xoaConfirm.show();
    },

    async onXoaConfirm() {
      try {
        this.$bvModal.hide("popup-xoaConfirm");
        this.$root.showLoading(true);
        let postData = {
          nhomKhId: this.vNhomKhId,
          thanhToanId:
            this.$refs.dgvMaTTThuocNhom.getSelectedRecords()[0].THANHTOAN_ID,
        };

        let response = await QuanLyNhomInTongHopApi.xoaMaThanhToanKhoiNhomKH(
          this.axios,
          postData
        );
        
        if (response.data.error_code == API_SUCCESS) {
          this.$root.toastSuccess(
            "Xóa thanh toán thuộc nhóm trên dữ liệu viễn thông tỉnh thành công !"
          );
          await this.getDsTT(this.vKhachHangId, this.vNhomKhId);
        } else {
          
          this.$root.toastError("Có lỗi xảy ra !" + response.data.message);
        }
      } catch (e) {
        console.log(e);
        this.$root.toastError("Có lỗi: " + e.data.message);
      } finally {
        this.$root.showLoading(false);
      }
    },
    async onExcelExport(){
        
        if (this.optNhanVien.nvLocSelectedValue == -1) {
          this.$root.toastError("Chưa chọn nhân viên!");
          return;
        }
        this.loading(true);
        try {
          const baseUrl = process.env.API;
          await this.axios({
          url: `${baseUrl}/web-cskh/api/in-tong-hop-khl/export-excel-nhom-in-tong-hop/?nhanVienId=${this.optNhanVien.nvLocSelectedValue}&kyHoaDon=20240301&loaiId=2`,
          method: "GET",
          responseType: "blob",
          }).then((response) => {
          this.downloadFile(`nhomInTH.xlsx`,response.data)
          });
        } catch (e) {
          console.log(e)
            this.$root.toastError("Có lỗi trong quá trình xuất excel! Mời thử lại");
        } finally {
            this.loading(false);
        }
    },
    downloadFile(filename, data){
        let blob = new Blob([data],{type:"octet/stream"})
        let blobUrl = URL.createObjectURL(blob);

        var element = document.createElement('a');
        element.setAttribute('href', blobUrl);
        element.setAttribute('download', filename);

        element.style.display = 'none';
        document.body.appendChild(element);

        element.click();

        document.body.removeChild(element);
    },

    onDsMaThanhToanThuocNhomRowSelected(dataItem) {
      this.maTTThuocNhom = dataItem;
    },
    onThemFileClicked() {
      if (
        this.$refs.dgvNhomKHL.getSelectedRecords() <= 0 ||
        !this.btnNhapMoiEnable
      ) {
        this.$root.toastError("Hãy chọn nhóm khách hàng!");
        return;
      }
      this.$refs.popupChonThanhToanTuFile.nhomKHId =
        this.$refs.dgvNhomKHL.getSelectedRecords()[0].NHOMKH_ID;
      this.$refs.popupChonThanhToanTuFile.tenNhom =
        this.$refs.dgvNhomKHL.getSelectedRecords()[0].TENNHOM;
      this.$refs.popupChonThanhToanTuFile.openDialog();
      //   this.$bvModal.show("popup-chonThanhToanTuFile");
    },
    onNhapMoiClicked() {
      this.setButtonStatus(1);
      this.clearData();
      this.dgvMaTTThuocNhom.dataSource = [];
      this.dgvMaTTKoThuocNhom.dataSource = [];
    },

    clearData() {
      this.txtTenNhom = "";
      this.txtMaIG = "";
      this.txtNguoiLH = "";
      this.txtSoFax = "";
      this.txtEmail = "";
      this.txtSDT = "";
      this.txtMaTB = "";
      // this.txtMaTT = ""
      this.txtDiaChi = "";
      this.IdsFile = [];
      this.vKhachHangId = 0;
      this.optMaNhom.selectedValue = "";
    //   this.optNhanVien.selectedValue = "";
      this.dgvMaTTThuocNhom.dataSource = [];
      this.dgvMaTTKoThuocNhom.dataSource = [];
    },

    async onCapNhapClicked() {
      try {
        this.$root.showLoading(true);
        if (await this.kiemTraDuLieu()) {
          let postData = {
            nhomKhId: this.btnNhapMoiEnable ? this.vNhomKhId : 0,
            khachHangId: this.vKhachHangId,
            tenNhom: formatString(this.txtTenNhom),
            maNhom: this.optMaNhom.selectedValue,
            lienHe: formatString(this.txtNguoiLH),
            mail: formatString(this.txtEmail),
            nhanVienId: this.optNhanVien.selectedValue,
            maInGhep: formatString(this.txtMaIG),
            sdt: formatString(this.txtSDT),
            fax: formatString(this.txtSoFax),
            diaChi: formatString(this.txtDiaChi),
            dsLoaiFile: this.IdsFile.toString()
          };

          let response = await this.$root.context.post(
            "/web-cskh/api/nhom-in-tong-hop/cap-nhat-nhom",
            postData
          );
          if (response.error_code == API_SUCCESS) {
            this.$root.toastSuccess("Cập nhật dữ liệu thành công !");
            

            this.optNhanVien.nvLocSelectedValue =
              this.optNhanVien.selectedValue;
            await this.getDsNhomKHLon();
          }
        }
      } catch (e) {
        console.log(e);
        this.$root.toastError("Lỗi: " + e.response.data.message);
      } finally {
        this.$root.showLoading(false);
      }
    },

    async kiemTraDuLieu() {
    //   if (isNullOrEmpty(this.txtMaNhom)) {
    //     this.$root.toastError("Mã nhóm không được để trống !");
    //     this.focusInput("txtMaNhom");
    //     return false;
    //   }
      if (isNullOrEmpty(this.txtTenNhom)) {
        this.$root.toastError("Tên nhóm không được để trống !");
        this.focusInput("txtTenNhom");
        return false;
      }
      if (isNullOrEmpty(this.txtNguoiLH)) {
        this.$root.toastError("Người liên hệ không được để trống !");
        this.focusInput("txtNguoiLH");
        return false;
      }

      if (this.txtTenNhom && this.txtTenNhom.trim().length > 150) {
        this.$root.toastError(
          "Tên nhóm nhập dữ liệu vượt quá độ dài cho phép là 150 ký tự! !"
        );
        this.focusInput("txtTenNhom");
        return false;
      }

      if (this.txtNguoiLH && this.txtNguoiLH.trim().length > 80) {
        this.$root.toastError(
          "Người liên hệ nhập dữ liệu vượt quá độ dài cho phép là 80 ký tự! !"
        );
        this.focusInput("txtNguoiLH");
        return false;
      }

      if (this.txtEmail && this.txtEmail.trim().length > 200) {
        this.$root.toastError(
          "Email nhập dữ liệu vượt quá độ dài cho phép là 200 ký tự! !"
        );
        this.focusInput("txtEmail");
        return false;
      }
      if(this.txtEmail && this.txtEmail.includes(",")){
        let isLoi = false
        let array = this.txtEmail.split(',')
        array.forEach((email)=>{
            if((!/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z0-9\-]+\.)+[a-zA-Z]{2,}))$/.test(email.trim()))) {
              this.$toast.error(`Email ${email} không hợp lệ!`);
              isLoi = true
            }
        })
        if(isLoi == true) return false
      } else {
            if((!/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z0-9\-]+\.)+[a-zA-Z]{2,}))$/.test(this.txtEmail)) && this.txtEmail.length > 0) {
                this.$toast.error("Email không hợp lệ! Nếu nhiều email thì cách nhau bởi dấu ,");
                return false;
            }
      }


      if (this.txtMaIG && this.txtMaIG.trim().length > 30) {
        this.$root.toastError(
          "Mã in ghép nhập dữ liệu vượt quá độ dài cho phép là 30 ký tự! !"
        );
        this.focusInput("txtMaIG");
        return false;
      }

      if (this.txtSDT && this.txtSDT.trim().length > 80) {
        this.$root.toastError(
          "Số điện thoại nhập dữ liệu vượt quá độ dài cho phép là 80 ký tự! !"
        );
        this.focusInput("txtSDT");
        return false;
      }

      if(this.txtSDT && this.txtSDT.includes(",")){
        let isLoi = false
        let array = this.txtSDT.split(',')
        array.forEach((sdt)=>{
            if (isNaN(sdt)) {
                this.$root.toastError(`Số điện thoại ${sdt} phải là kiểu số!`);
                isLoi = true
            }
        })
        if(isLoi == true) return false
      } else {
        if (isNaN(this.txtSDT)) {
            this.$root.toastError("Số điện thoại liên hệ của khách hàng phải là kiểu số! Nếu nhiều số điện thoại thì cách nhau bởi dấu ,");
            this.focusInput("txtSDT");
            return false;
        }
      }

      if (this.txtSoFax && this.txtSoFax.trim().length > 80) {
        this.$root.toastError(
          "Fax nhập dữ liệu vượt quá độ dài cho phép là 80 ký tự! !"
        );
        this.focusInput("txtSoFax");
        return false;
      }

      if (isNullOrEmpty(this.txtDiaChi)) {
        this.$root.toastError("Địa chỉ không được để trống !");
        this.focusInput("txtDiaChi");
        return false;
      }

      if (this.optNhanVien.selectedValue == -1) {
        //   this.$refs.optNhanVien.focus()
        this.$root.toastError("Bạn chưa chọn nhân viên !");
        return false;
      }

      if (this.dgvNhomKHL.length <= 0) {
        this.$root.toastError("Chưa có nhóm khách hàng in tổng hợp!");
        return false;
      }

      if (this.btnNhapMoiEnable == false) {
        try {
          let response = await this.$root.context.get(
            "/web-cskh/api/thue-bao-khong-khao-sat/",
            { maThueBao: this.txtSMTaoNhom }
          );
          if (response.error_code == API_SUCCESS) {
            if (response.data.data.length <= 0) {
              this.$root.toastError(
                "Không tìm thấy khách hàng có mã : '" + this.txtSMTaoNhom + "'"
              );
              return false;
            }

            this.vKhachHangId = response.data.data[0].KHACHHANG_ID;
          }
        } catch (e) {
          console.log(e);
          this.$root.toastError(
            "Không tìm thấy khách hàng có mã : '" + this.txtSMTaoNhom + "'"
          );
          return false;
        }
      }
      return true;
    },
    onXoaNhomClicked() {
      if (this.dgvNhomKHL.dataSource.length <= 0) {
        this.$root.toastError(
          "Danh sách nhóm trống! Bạn không thể thực hiện xóa nhóm"
        );
        return;
      }
      this.$refs.xoaNhomConfirm.show();
    },
    async onXoaNhomConfirm() {
      try {
        this.$bvModal.hide("popup-xoaNhomConfirm");
        this.$root.showLoading(true);
        let postData = {
          nhomKhId: this.vNhomKhId,
        };

        let response = await QuanLyNhomInTongHopApi.xoaNhom(
          this.axios,
          postData
        );
        if (response.data.error_code == API_SUCCESS) {
          this.$root.toastSuccess(
            "Xóa nhóm trên dữ liệu viễn thông tỉnh thành công !"
          );
          await this.getDsNhomKHLon();
        }
      } catch (e) {
        console.log(e);
        this.$root.toastError("Lỗi : " + e.data.message);
      } finally {
        this.$root.showLoading(false);
      }
    },
    setButtonStatus(kieu) {
      this.btnNhapMoiEnable = false;
      this.btnCapNhapEnable = false;
      this.btnXoaNhomEnable = false;
      switch (kieu) {
        case -1:
          this.btnCapNhapEnable = true;
          break;
        case 0:
          this.btnNhapMoiEnable = true;
          break;
        case 1:
          this.btnCapNhapEnable = true;
          this.btnThemEnable = false;
          this.btnXoaEnable = false;
          break;
        case 2:
          this.btnNhapMoiEnable = true;
          this.btnXoaNhomEnable = true;
          break;
        case 3:
          this.btnNhapMoiEnable = true;
          this.btnXoaNhomEnable = true;
          this.btnCapNhapEnable = true;
          this.btnThemEnable = true;
          this.btnXoaEnable = true;
          break;
      }
    },
    onDsNhomKHLonItemSelected(data) {},
    async onDsNhomKHLonSelected(dataItem) {
      try {
        this.$root.showLoading(true);
        if (dataItem) {
          this.nhomKHLon = dataItem;
          this.txtTenNhom = dataItem.TENNHOM;
          this.txtMaIG = dataItem.MAIN_GHEP;
          //this.txtSMTaoNhom = dataItem.KHACHHANG_ID;
          this.txtNguoiLH = dataItem.LIENHE;
          this.txtSoFax = dataItem.FAX;
          this.txtEmail = dataItem.MAIL;
          this.txtSDT = dataItem.SDT;
          this.optMaNhom.selectedValue = dataItem.MANHOM;
          this.txtDiaChi = dataItem.DIA_CHI;
          this.IdsFile = dataItem.DS_LOAI_FILE ? dataItem.DS_LOAI_FILE.split(',').map(Number) : [];
          this.vKhachHangId = dataItem.KHACHHANG_ID;
          this.vNhomKhId = dataItem.NHOMKH_ID;
          this.optNhanVien.selectedValue = this.optNhanVien.nvLocSelectedValue;

          await this.getDsTT(dataItem.KHACHHANG_ID, dataItem.NHOMKH_ID);
          this.setButtonStatus(3);
        }
      } catch (e) {
        console.log(e);
        this.$root.toastError("Có lỗi! " + e.response.data.message);
      } finally {
        this.$root.showLoading(false);
      }
    },

    async onDsNVTaoNhomLocChange(item) {
      this.txtSMTaoNhom = "";
      if (this.optNhanVien.nvLocSelectedValue == -1) {
        this.clearData();
        this.dgvNhomKHL.dataSource = [];
        this.dgvMaTTThuocNhom.dataSource = [];
        this.dgvMaTTKoThuocNhom.dataSource = [];
        return;
      }

      try {
        this.$root.showLoading(true);
        this.clearData();
        await this.getDsNhomKHLon();
      } catch (e) {
        console.log(e);
        this.$root.toastError("Có lỗi! " + e.response.data.message);
      } finally {
        this.$root.showLoading(false);
      }
    },
   
    async getDsNVTaoNhomKH() {
      try {
        this.optNhanVien.dataSource = [];
        this.optNhanVien.selectedValue = -1;
        let response = await this.$root.context.get(
          "/web-cskh/api/nhom-in-tong-hop/nhan-vien",
          {
            nhanVienId: this.$auth.getNhanVienID(),
            pageNo: 1,
            pageSize: 100000,
          }
        );
        if (response.error_code == API_SUCCESS) {
          let ds = response.data.data || [];
          this.optNhanVien.dataSource.push({ id: -1, text: "" });
          ds.forEach((e) => {
            this.optNhanVien.dataSource.push({
              id: e.NHANVIEN_ID,
              text: e.TEN_NV,
            });
          });
        }
      } catch (e) {
        console.log(e);
        this.$root.toastError("Có lỗi! " + e.response.data.message);
      }
    },
    async getDsMaNhom() {
      try {
        this.optMaNhom.dataSource = [];
        this.optMaNhom.selectedValue ="";
        let response = await this.$root.context.get(
          "/web-cskh/api/danh-muc/cskh", 
          {
            loaiDanhMuc: "MA_NHOM_INTH",
            thamSo: 1,
          });
        if (response.error_code == API_SUCCESS) {
          
          let ds = response.data || [];
          this.optMaNhom.dataSource.push({  id: "", text: ""});
          ds.forEach((e) => {
            this.optMaNhom.dataSource.push({
              id: e.MA_NHOM,
              text: e.MA_NHOM + ' - ' + e.TEN_NHOM,
            });
          });
        }
      } catch (e) {
        console.log(e);
        this.$root.toastError("Có lỗi! " + e.response.data.message);
      }
    },
    async getDsTTThuocNhom(data) {
      try {
        this.dgvMaTTThuocNhom.dataSource = [];
        let response = await this.$root.context.get(
          "/web-cskh/api/nhom-in-tong-hop/thanh-toan-thuoc-nhom",
          data
        );
        if (response.error_code == API_SUCCESS) {
          this.dgvMaTTThuocNhom.dataSource = response.data.data || [];
          this.dgvMaTTThuocNhom.dataSource.forEach((e, i) => (e.STT = i + 1));
        }
      } catch (e) {
        this.$root.toastError("Có lỗi! " + e.response.data.message);
      }
    },

    async getDsTTKoThuocNhom(data) {
      try {
        this.dgvMaTTKoThuocNhom.dataSource = [];
        let response = await this.$root.context.get(
          "/web-cskh/api/nhom-in-tong-hop/thanh-toan-khong-thuoc-nhom",
          data
        );
        if (response.error_code == API_SUCCESS) {
          this.dgvMaTTKoThuocNhom.dataSource = response.data.data || [];
          this.dgvMaTTKoThuocNhom.dataSource.forEach((e, i) => (e.STT = i + 1));
        }
      } catch (e) {
        this.$root.toastError("Có lỗi! " + e.response.data.message);
      }
    },

    async getDsNhomKHLon() {
      try {
        this.dgvNhomKHL.dataSource = [];
        this.dgvMaTTThuocNhom.dataSource = [];
        this.dgvMaTTKoThuocNhom.dataSource = [];

        if (this.optNhanVien.nvLocSelectedValue == -1) {
          return;
        }

        let postData = {
          nhanVienId:
            this.optNhanVien.nvLocSelectedValue != -1
              ? this.optNhanVien.nvLocSelectedValue
              : "",
          maTb: this.txtMaTB,
          pageNo: 1,
          pageSize: 10000,
        };

        let response = await this.$root.context.get(
          "/web-cskh/api/nhom-in-tong-hop/nhom-khach-hang-lon",
          postData
        );
        if (response.error_code == API_SUCCESS) {
          this.dgvNhomKHL.dataSource = response.data.data || [];
          this.dgvNhomKHL.dataSource.forEach((e, i) => (e.STT = i + 1));
        }
      } catch (e) {
        console.log(e);
        this.$root.toastError("Có lỗi! " + e.response.data.message);
      } finally {
      }
    },

    async onTxtMaTB_KeyPress() {
      if (this.optNhanVien.nvLocSelectedValue == -1) {
        this.$root.toastError("Bạn phải chọn nhân viên trước!");
        return;
      }

      if (!this.txtMaTB) {
        this.$root.toastError("Bạn hãy nhập Mã thuê bao !");
        this.focusInput("txtMaTB");
        return;
      }

      await this.getDsNhomKHLon();
    },

    focusInput(name) {
      this.$nextTick(() => this.$refs[name].select());
    },

    async getDsTT(khId, nhomId) {
      try {
        let data = {
          khId: khId,
          nhomKhId: nhomId,
          pageNo: 1,
          pageSize: 10000,
        };

        await Promise.all([
          this.getDsTTThuocNhom(data),
          this.getDsTTKoThuocNhom(data),
        ]);
      } catch (e) {
        console.log(e);
      }
    },

    async refreshDsTTThuocNhom() {
      await this.getDsTT(this.vKhachHangId, this.vNhomKhId);
    },

    async onTxtSMTaoNhom_KeyPress() {
      if (this.btnNhapMoiEnable == false) {
        this.clearData();
        this.dgvMaTTThuocNhom.dataSource = [];
        if (this.txtSMTaoNhom == "" || this.txtSMTaoNhom.trim() == "") {
          this.$root.toastError("Chưa nhập mã thuê bao!");
          this.focusInput("txtSMTaoNhom");
          return;
        }

        try {
          let response = await this.$root.context.get(
            "/web-cskh/api/thue-bao-khong-khao-sat/",
            { maThueBao: this.txtSMTaoNhom }
          );
          if (response.error_code == API_SUCCESS) {
            if (response.data.data.length <= 0) {
              this.$root.toastError(
                "Không tìm thấy khách hàng có mã : '" + this.txtSMTaoNhom + "'"
              );
              return false;
            }

            this.vKhachHangId = response.data.data[0].KHACHHANG_ID;
            this.txtTenNhom = response.data.data[0].TEN_TT;
            this.txtDiaChi = response.data.data[0].DIACHI_TT

            let response2 = await this.$root.context.get("/web-cskh/api/mig", {
              khachHangId: response.data.data[0].KHACHHANG_ID,
            });
            if (response2.error_code == API_SUCCESS) {
              if (response2.data.data.length > 0) {
                this.txtMaIG = response2.data.data[0].MAIN_GHEP;
                let maInGhepID = response2.data.data[0].MIG_ID;

                if (maInGhepID) {
                  let response3 = await this.$root.context.get(
                    "/web-cskh/api/mig/danh-sach-mtt",
                    { migId: maInGhepID }
                  );
                  if (response3.error_code == API_SUCCESS) {
                    this.dgvMaTTThuocNhom.dataSource =
                      response3.data.data || [];
                    this.dgvMaTTThuocNhom.dataSource.forEach(
                      (e, i) => (e.STT = i + 1)
                    );
                  }
                }
              }
            }
          }
        } catch (e) {
          console.log(e);
          this.$root.toastError(
            "Không tìm thấy khách hàng có mã : '" + this.txtSMTaoNhom + "'"
          );
          return false;
        }
      }
    },
    async getDsLoaiFile() {
      try {
        this.dsLoaiFile = [];
        let response = await this.$root.context.get("/web-cskh/api/danh-muc/loai-file-in-tong-hop");
        if (response.error_code == API_SUCCESS) {
          this.dsLoaiFile = response.data || [];
        }
      } catch (e) {
        this.dsLoaiFile = [];
        // this.$root.toastError("Có lỗi! " + e.response.data.message);
      }
    },
  },
};
</script>

