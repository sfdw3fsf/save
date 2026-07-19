<template src="./index.template.html"></template>
<style src="./style.scss" scoped></style>
<script>
import Vue from "vue";
import moment from "moment";
import KyCuoc from "../components/KyCuoc.vue";
import { mapActions, mapState } from "vuex";
import xlsx from "xlsx";
import API from "../api/KhachHangDacBietThang";
import API2 from "../api/MoMayTheoTungSoMayAcc";
import PopupDocFileThem from "./components/popup-DocFileThem.vue";
import PopupDocFileXoa from "./components/popup-DocFileXoa.vue";
import ModalXuatDuLieu from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue";

export default {
  components: {
    appKyCuoc: KyCuoc,
    PopupDocFileThem,
    PopupDocFileXoa,
    appModalXuatDuLieu: ModalXuatDuLieu,
  },
  data() {
    return {
      header: {
        title: "THUÊ BAO ĐẶC BIỆT TRONG THÁNG",
        list: [
          { name: "Xử lý thuê bao", link: { name: "Ui.cards" } },
          {
            name: "Khách hàng đặc biệt tháng",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      isAddNew: false,
      isSaving: false,
      kyCuoc: "",
      dsThueBao: [],
      form: {
        soMay: "",
        idDichVu: "",
        tenTB: "",
        diaChi: "",
        ngayTH: new Date(),
        denNgay: new Date(),
        ghiChu: "",
        checkNgayTH: false,
        checkDenNgay: false,
      },
      waterMark: "Chọn ngày",
      dateFormat: "dd/MM/yyyy",
      fields: [
        {
          fieldName: "MA_TB",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          width: 200,
        },
        {
          fieldName: "TEN_DVVT",
          headerText: "Dịch vụ",
          allowFiltering: true,
        },
        {
          fieldName: "LOAIHINH_TB",
          headerText: "Loại hình",
          allowFiltering: true,
        },
        {
          fieldName: "KYHOADON",
          headerText: "Kỳ cước",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "NGAY_TH",
          headerText: "Ngày TH",
          allowFiltering: true,
        },
        {
          fieldName: "NGAY_CN",
          headerText: "Ngày CN",
          allowFiltering: true,
        },
        {
          fieldName: "NGUOI_CN",
          headerText: "Người CN",
          allowFiltering: true,
        },
        {
          fieldName: "GHICHU",
          headerText: "Ghi chú",
          allowFiltering: true,
        },
      ],
      isType: 1, //1: Thêm mới, 2: Cập nhật
      thongTinTemp: null,
      isCountSelected: 0,
      customTemplate: function () {
        return {
          template: Vue.component("footerTemplate", {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" disabled style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } };
            },
          }),
        };
      },
    };
  },
  validations: {},
  watch: {},
  created() {
    this.getDanhSachDichVuVienThong();
    if (this.danhSachDichVuVienThong.length > 0) {
      this.form.idDichVu = this.danhSachDichVuVienThong[0].DICHVUVT_ID;
    }
  },
  mounted() {
    this.$refs.soMay.focus();
  },
  methods: {
    ...mapActions("qltnCommon", ["getDanhSachDichVuVienThong"]),
    handleKyHoaDon(e) {
      this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
    },
    async getDanhSachThueBao(maThueBao) {
      const data = {
        kyCuoc: this.kyCuoc,
        maTB: maThueBao,
        tuNgay: this.form.checkNgayTH
          ? moment(this.form.ngayTH).format("DD/MM/YYYY")
          : "",
        denNgay: this.form.checkDenNgay
          ? moment(this.form.denNgay).format("DD/MM/YYYY")
          : "",
      };
      this.loading(true);
      try {
        const response = await API.getDanhSachThueBao(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data.length > 0) {
            this.dsThueBao = response.data.data;
            this.fillThongTin(response.data.data[0]);
            this.thongTinTemp = response.data.data[0];
            this.isType = 2;
            this.isAddNew = false;
          } else {
            this.isType = 1;
            this.$root.toastError(`Không tìm thấy thuê bao`);
            this.dsThueBao = [];
            this.thongTinTemp = null;
            this.resetThongTin();
          }
        } else {
          this.isType = 2;
          this.$root.toastError(`Không tìm thấy thuê bao`);
          this.dsThueBao = [];
          this.thongTinTemp = null;
          this.resetThongTin();
        }
      } catch (error) {
        this.isType = 2;
        this.$root.toastError(error.data.message_detail);
        this.dsThueBao = [];
        this.thongTinTemp = null;
        this.resetThongTin();
      } finally {
        this.loading(false);
        this.isCountSelected = 0;
      }
    },
    async CapNhat() {
      const item = {
        maTB: this.form.soMay,
        dichVuVTId: this.form.idDichVu,
        ghiChu: this.form.ghiChu,
        ngayTH: this.form.ngayTH
          ? moment(this.form.ngayTH).format("DD/MM/YYYY")
          : "",
      };
      const data = {
        dsKHDB: [item],
        kyCuoc: this.kyCuoc,
        kieu: 7,
      };
      this.loading(true);
      try {
        const response = await API.capNhatThueBao(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data.length >= 0) {
            this.$root.toastSuccess(`Cập nhật dữ liệu thành công!`);
            this.getDanhSachThueBao("");
          } else {
            this.$root.toastError(`Cập nhật dữ liệu không thành công!`);
          }
        } else {
          this.$root.toastError(`Cập nhật dữ liệu không thành công!`);
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    // async TaoMoi(){
    //   if(this.form.soMay.trim() === ""){
    //     this.$root.toastError(`Bạn chưa nhập mã thuê bao cần tìm kiếm!`);
    //     return;
    //   }
    //   if(this.form.idDichVu === ""){
    //     this.$root.toastError(`Dịch vụ chưa có thông tin!`);
    //     return;
    //   }
    //   if(this.form.tenTB.trim() === ""){
    //     this.$root.toastError(`Tên TB chưa có thông tin!`);
    //     return;
    //   }
    //   if(this.form.diaChi.trim() === ""){
    //     this.$root.toastError(`Địa chỉ lắp đặt chưa có thông tin!`);
    //     return;
    //   }
    //   if(moment(this.form.denNgay).diff(this.form.ngayTH,'DD/MM/YYYY') < 0){
    //     this.$root.toastError('Ngày TH phải nhỏ hơn hoặc bằng Đến ngày!');
    //     return
    //   }
    //   const item = {
    //     maTB:this.form.soMay,
    //     dichVuVTId:this.form.idDichVu,
    //     ghiChu:this.form.ghiChu,
    //     ngayTH:this.form.ngayTH
    //   }
    //   const data = {
    //     dsKHDB:[item],
    //     kyCuoc:20200301,
    //     nhanVienId:this.$auth.getNhanVienID(),
    //     kieu:7
    //   }
    //   this.loading(true)
    //   try {
    //     const response = await API.themMoiThueBao(this.axios, data);
    //     if (response.data.error_code === "BSS-00000000") {
    //       if ( response.data.data !== undefined && response.data.data.length >= 0 ){
    //         this.$root.toastSuccess(`Thêm mới thông tin thành công!`);
    //         this.getDanhSachThueBao()
    //       } else {
    //         this.$root.toastError(`Thêm mới thông tin không thành công!`);
    //       }
    //     } else {
    //       this.$root.toastError(`Thêm mới thông tin không thành công!`);
    //     }
    //   } catch (error) {
    //     this.$root.toastError(error.response.data.message_detail);
    //   } finally {
    //     this.loading(false)
    //   }
    // },
    async getThueBao() {
      let txt = this.form.soMay;
      if (this.form.soMay.trim() === "") {
        this.$root.toastError(`Bạn chưa nhập mã thuê bao cần tìm kiếm!`);
        return;
      }
      try {
        const response = await API2.getThongTinThueBao(this.axios, {
          maTB: this.form.soMay,
        });
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data.length > 0) {
            const result = this.dsThueBao.some(
              (item) => item.MA_TB === response.data.data[0].MA_TB
            );
            if (result) {
              this.$root.toastError(
                `Thuê bao ${txt} đã tồn tại trong danh sách!`
              );
              this.$refs.soMay.focus();
              return;
            }
            this.fillThongTin(response.data.data[0]);
            this.getDanhSachThueBao(this.form.soMay);
          } else {
            this.resetThongTin();
            this.$root.toastError(`Không tìm thấy thông tin thuê bao ${txt}!`);
          }
        } else {
          this.resetThongTin();
          this.$root.toastError(`Không tìm thấy thông tin thuê bao ${txt}!`);
        }
      } catch (error) {
        this.resetThongTin();
        this.$root.toastError(error.data.message_detail);
      }
    },
    async XoaThueBao() {
      const ds = this.$refs.danhsach.getSelectedRecords() || [];
      const dsResult = ds.map((item) => ({
        maTB: item.MA_TB,
        dichVuVTId: item.DICHVUVT_ID,
        ghiChu: item.GHICHU,
        ngayTH: item.NGAY_TH,
      }));
      const data = {
        dsKHDB: dsResult,
        kyCuoc: this.kyCuoc,
        kieu: 20,
      };
      this.loading(true);
      try {
        const response = await API.xoaThueBao(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data.length >= 0) {
            this.$root.toastSuccess(`Xóa thuê bao thành công!`);
            this.getDanhSachThueBao("");
          } else {
            this.$root.toastError(`Lỗi xảy ra. Mời thử lại!`);
          }
        } else {
          this.$root.toastError(`Lỗi xảy ra. Mời thử lại!`);
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    fillThongTin(data) {
      this.form.soMay = data.MA_TB;
      this.form.idDichVu = data.DICHVUVT_ID;
      this.form.tenTB = data.TEN_TB;
      this.form.ngayTH = data.NGAY_TH ? moment(data.NGAY_TH, "DD/MM/YYYY").toDate() : new Date();
      this.form.diaChi = data.DIACHI_LD;
      this.form.ghiChu = data.GHICHU;
    },
    resetThongTin() {
      this.form.soMay = "";
      this.form.idDichVu = "";
      this.form.tenTB = "";
      this.form.ngayTH = new Date();
      this.form.denNgay = new Date();
      this.form.diaChi = "";
      this.form.ghiChu = "";
    },
    selectedItemsChangedMoi(data) {
      this.isCountSelected = data.length;
      let dataGridObj = this.$refs.danhsach.$refs.grid.ej2Instances;
      dataGridObj.aggregateModule.refresh();
    },
    selectedRowChangedMoi(index, data) {
      if (data) {
        this.fillThongTin(data);
        this.thongTinTemp = data;
        this.isType = 2;
      }
    },
    LayDS() {
      this.getDanhSachThueBao("");
    },
    NhapMoi() {
      this.isType = 1;
      this.resetThongTin();
      this.isAddNew = true;
      this.$refs.soMay.focus();
    },
    GhiLai() {
      if (this.isType === 1) {
        if (this.form.soMay.trim() === "") {
          this.$root.toastError(`Bạn chưa nhập mã thuê bao cần tìm kiếm!`);
          return;
        }
        if (this.form.idDichVu === "") {
          this.$root.toastError(`Dịch vụ chưa có thông tin!`);
          return;
        }
        if (this.form.tenTB.trim() === "") {
          this.$root.toastError(`Tên TB chưa có thông tin!`);
          return;
        }
        if (this.form.diaChi.trim() === "") {
          this.$root.toastError(`Địa chỉ lắp đặt chưa có thông tin!`);
          return;
        }
      }

      if (moment(this.form.denNgay).diff(this.form.ngayTH, "DD/MM/YYYY") < 0) {
        this.$root.toastError("Ngày TH phải nhỏ hơn hoặc bằng Đến ngày!");
        return;
      }
      let msg = this.isType === 1 ? "Bạn thật sự muốn thêm thuê bao vào danh sách không ?" : "Bạn thật sự muốn cập nhật dữ liệu không ?";
      this.$confirm(msg, "Xác nhận", {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
      })
        .then(() => {
          this.CapNhat();
        })
        .catch(() => { });
    },
    Huy() {
      if (this.isType === 1) {
        this.resetThongTin();
      } else {
        this.fillThongTin(this.thongTinTemp);
        this.form.denNgay = new Date();
      }
    },
    Xoa() {
      const dsSelected = this.$refs.danhsach.getSelectedRecords() || [];
      if (dsSelected.length === 0) {
        this.$root.toastError(`Bạn chưa chọn thuê bao!`);
        return;
      }
      this.$confirm(
        `Bạn thật sự muốn xóa ${dsSelected.length} thuê bao đang được chọn không?`,
        "Xác nhận",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
        }
      )
        .then(() => {
          this.XoaThueBao();
        })
        .catch(() => { });
    },
    DocFileThem() {
      this.$bvModal.show("modal-docfilethem");
    },
    DocFileXoa() {
      this.$bvModal.show("modal-docfilexoa");
    },
    XuatFile() {
      if (this.dsThueBao.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.dsThueBao);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        // export Excel file
        xlsx.writeFile(wb, "mau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    XuatFile2() {
      this.$bvModal.show("modal-xuat-dulieu");
    },
    sendData(data) {
      if (data.msgReturn === "OK") {
        this.getDanhSachThueBao("");
      }
    },
    customAggregate() {
      return `${this.isCountSelected}/${this.dsThueBao.length}`;
    },
  },
  computed: {
    ...mapState("qltnCommon", ["danhSachDichVuVienThong"]),
    PTAggregatesColumns() {
      return [
        {
          field: "MA_TB",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.customAggregate,
        },
      ];
    },
    dsThuocTinh() {
      return this.fields.map((x) => x.fieldName);
    },
  },
};
</script>
