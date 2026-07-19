<template src="./InTongHopKHL.html"></template>
<style src="./InTongHopKHL.scss"></style>
<script>
import select2 from "@/components/Select2.vue";
import { mapState } from "vuex";
import YesNoModal from "../utils/YesNoModal.vue";
import KyCuoc from "../../QLTN/components/KyCuoc.vue";
import moment from "moment";
import { currency } from "@/filters/currency";
import CauHinhSMTP from "../InTongHopKHL/popup/CauHinhSMTP.vue";
import ThongTinLienHe from "../../contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue"
import SendMail from "../InTongHopKHL/popup/SendMail.vue";
import { previewPrint } from "../../../utils/util";
import FilterDialog from '../InTongHopKHL/popup/FilterDialog.vue'

export default {
  name: "InTongHopKHL",
  components: {
    appSelect2: select2,
    confirmPopup: YesNoModal,
    appKyCuoc: KyCuoc,
    appCauHinhSMTP: CauHinhSMTP,
    ThongTinLienHe,
    appSendMail: SendMail,
    FilterDialog
  },
  data() {
    return {
      kyCuoc: moment(new Date()).add(-1, "months").format("YYYYMM") + "01",
      pDsNhanVien: [],
      pDsKhachHang: [],

      pDsNhanVienSelected: 0,
      pDsKieuInSelected: 1,
      pDotTinhCuocSelected: 0,
      pDataIn: [],
      pDsTimKiemSelected: 1,
      pTxtTimKiem: "",

      API_SUCCESS: "BSS-00000000",
      btnStatus: {
        tsbnGuiEmailEnable: false,
      },

      khachHangId: 0,
      dsEmails: [],
      smtp_dclh: "",
      smtp_email: "",
      smtp_sdt: "",
      smtp_thongtin: "",
      smtp_chuyenKhoan:"",
      checkAllKH: false,
      columnsCheckboxAllKH: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllKH" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      rowSelectedKH:[],
      columnsCheckboxKH: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"
                                    v-model="parent.rowSelectedKH"
                                    :value="data.NHOMKH_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      checkInAll: false,
      ds_danhSachKH: [],
      dsLoaiFile: []
    };
  },
  async created() {},
  async mounted() {
    // this.getValueSMTP()
    try {
      this.$root.showLoading(true);
      await this.getDsNhanVien();
      await this.getDsLoaiFile();
    } catch (e) {
    } finally {
      this.$root.showLoading(false);
    }
  },
  computed: {
    ...mapState("reg", [
      "SMTP_CSKH_SERVER",
      "SMTP_CSKH_USERNAME",
      "SMTP_CSKH_PASSWORD",
      "SMTP_CSKH_PORT",
      "SMTP_ALIAS",
      "SMTP_CSKH_DCLH",
      "SMTP_CSKH_EMAIL",
      "SMTP_CSKH_SDT",
    ]),
    danhSachKieuIn() {
      return [
        { value: 1, text: "Theo mã thanh toán" },
        { value: 2, text: "Theo mã thuê bao" },
        { value: 3, text: "Theo mã thuê bao cước phát sinh >= 0" },
        { value: 4, text: "Theo mã thuê bao - trạng thái" },
      ];
    },
    danhSachDotTinhCuoc() {
      return [
        { value: 0, text: "Tất cả" },
        { value: 1, text: "Đợt 1" },
        { value: 2, text: "Đợt 2" },
      ];
    },
    danhSachTimKiem() {
      return [
        { value: 1, text: "ID Nhóm" },
        { value: 2, text: "Mã thanh toán" },
        { value: 3, text: "Tên nhóm" },
        { value: 4, text: "Mã thuê bao" },
        { value: 5, text: "Mã số thuế" },
      ];
    },
  },
  watch: {
    checkAllKH: function (value) {
      this.rowSelectedKH = [];
      if (value) {
        for (const e of this.pDsKhachHang) {
          this.rowSelectedKH.push(e.NHOMKH_ID);
        }
      }
    },
    rowSelectedKH: function (value) {
      this.rowSelectedKH = value;
    },
    pDsNhanVienSelected: function(value){
        this.getValueSMTP()
        //this.pDsKhachHang = []
        this.checkInAll = false
    },
  },
  methods: {
    async getValueSMTP(){
        const objSMTP = localStorage.getItem('KEY_SMTP')
        const valueSMTP = JSON.parse(objSMTP)
        this.smtp_dclh = (valueSMTP && valueSMTP.SMTP_CSKH_DCLH) ? valueSMTP.SMTP_CSKH_DCLH : this.SMTP_CSKH_DCLH || "";
        this.smtp_email = (valueSMTP && valueSMTP.SMTP_CSKH_EMAIL) ? valueSMTP.SMTP_CSKH_EMAIL : this.SMTP_CSKH_EMAIL || "";
        this.smtp_sdt = (valueSMTP && valueSMTP.SMTP_CSKH_SDT) ? valueSMTP.SMTP_CSKH_SDT : this.SMTP_CSKH_SDT || "";
        this.smtp_thongtin = (valueSMTP && valueSMTP.SMTP_CSKH_THONGTIN) ? valueSMTP.SMTP_CSKH_THONGTIN : "";
        this.smtp_chuyenKhoan = (valueSMTP && valueSMTP.SMTP_CSKH_CHUYENKHOAN) ? valueSMTP.SMTP_CSKH_CHUYENKHOAN : "";


        const res = await this.$root.context.get(
          "/web-cskh/api/in-tong-hop-khl/cau-hinh-smtp",
          {
            nhanVienId: this.pDsNhanVienSelected,
          }
        );
        if (res.error_code === this.API_SUCCESS) {
          if(res.data.data.length > 0){
            let obj = res.data.data[0]
            this.smtp_dclh = obj.DIACHI_LH
            this.smtp_email = obj.EMAIL
            this.smtp_sdt = obj.SO_DT
            this.smtp_thongtin = obj.MOTA
            this.smtp_chuyenKhoan = obj.CHUYEN_KHOAN
          }
        }
    },
    handleKyHoaDon(e) {
      this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no; //"20210101"; //
    },
    async onTimKiemClicked() {
      try {
        this.$root.showLoading(true);
        await this.getDsKhachHang();
      } catch (e) {
        console.log(e);
      } finally {
        this.$root.showLoading(false);
      }
    },
    async getDsNhanVien() {
      try {
        this.pDsNhanVien = [];
        let rs = await this.$root.context.get(
          "/web-cskh/api/nhom-in-tong-hop/nhan-vien",
          {
            nhanVienId: this.$root.token.getNhanVienID(),
            pageNo: 1,
            pageSize: 10000,
          }
        );
        if (rs.error_code === this.API_SUCCESS) {
          let ds = rs.data.data || [];
          ds.forEach((e) => {
            this.pDsNhanVien.push({
              id: e.NHANVIEN_ID,
              text: e.TEN_NV,
            });
          });
          this.$nextTick(() => {
            if (this.pDsNhanVien.length > 0) {
              this.pDsNhanVienSelected = this.pDsNhanVien[0].id;
            }
          });
        }
      } catch (e) {
        console.log(e);
      }
    },
    async getDsKhachHang() {
      try {
        this.pDsKhachHang = [];
        let rs = await this.$root.context.get(
          "/web-cskh/api/in-tong-hop-khl/tim-kiem",
          {
            nhanVienId: this.pDsNhanVienSelected,
            timKiemId: this.pDsTimKiemSelected,
            giaTriTimKiem: this.pTxtTimKiem,
            kyHoaDon: this.kyCuoc,
            kieuIn: this.pDotTinhCuocSelected,
            pageNo: 1,
            pageSize: 10000,
          }
        );
        if (rs.error_code === this.API_SUCCESS) {
          this.pDsKhachHang = rs.data.data || [];
          this.pDsKhachHang.forEach((e) => {
            e.TIENNO_TEXT = currency(e.TIENNO);
          });
        //   if (this.pDsKhachHang.length > 0) {
        //     this.$nextTick(() =>
        //       this.$refs.gridDsKhachHang.setCurrentSelectedRow(0)
        //     );
        //   }
          this.ds_danhSachKH = [...this.pDsKhachHang]
        }

        if (!this.pDsKhachHang || this.pDsKhachHang.length == 0) {
          this.$root.toastError("Không có dữ liệu, vui lòng thử lại.");
        }
      } catch (e) {
        console.log(e);
      }
    },
    onGridDsKhachHang_RowSelected(item) {
      if (item) {
        this.khachHangId = item.KHACHHANG_ID;
      }
    },
    getdsEmails() {
    //   const result = this.rowSelectedKH
    //   let dsKH = this.pDsKhachHang.filter((x) =>
    //     result.includes(x.NHOMKH_ID)
    //   );
      let dsKH = this.$refs.gridDsKhachHang.getSelectedRecords() || [];
      let emails = [];
      dsKH.forEach((x) => emails.push({ id: x.NHOMKH_ID, text: x.MAIL }));
      this.dsEmails = emails;
    },
    onGuiEmailClicked() {
      this.getdsEmails();
      this.$refs.frmSendMail.openDialog();
    },
    async onExcelExport(){

        if (!this.pDsNhanVienSelected) {
          this.$root.toastError("Chưa chọn nhân viên!");
          return;
        }
        this.loading(true);
        try {
          const baseUrl = process.env.API;
          await this.axios({
          url: `${baseUrl}/web-cskh/api/in-tong-hop-khl/export-excel-nhom-in-tong-hop/?nhanVienId=${this.pDsNhanVienSelected}&kyHoaDon=${this.kyCuoc}&loaiId=1`,
          method: "GET",
          responseType: "blob",
          }).then((response) => {
          this.downloadFileZip(`InTH.xlsx`,response.data)
          });
        } catch (e) {
          console.log(e)
            this.$root.toastError("Có lỗi trong quá trình xuất excel! Mời thử lại");
        } finally {
            this.loading(false);
        }
    },


    downloadFileZip(filename, data){
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

    async onTBCTaoFile(type){
      if (this.pDsKhachHang.length <= 0 && !this.checkInAll) {
        this.$root.toastError("Không có dữ liệu để tạo file !");
        return;
      }
      const dsSelected = this.$refs.gridDsKhachHang.getSelectedRecords() || [];
      let dsKH = []
      const dsIn = this.checkInAll ? [] : dsSelected
      dsKH = dsIn.map(item => item.NHOMKH_ID)
      if (dsKH.length <= 0 && !this.checkInAll) {
        this.$root.toastError("Bạn chưa chọn dữ liệu để tạo file!");
        return;
      }
      const selectNhanvien = this.pDsNhanVien.find(item=>item.id == this.pDsNhanVienSelected)

      this.loading(true);
      try {
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-cskh/api/in-tong-hop-khl/thong-bao-cuoc-cbo`,
          method: "POST",
          responseType: "blob",
          data: {
            kyHoaDon: this.kyCuoc,
            dsNhomKH: dsKH,
            kieu: this.pDsKieuInSelected,
            dotTinh: this.pDotTinhCuocSelected,
            diaChiLH: this.smtp_dclh,
            moTa: this.smtp_thongtin,
            chuyenKhoan: this.smtp_chuyenKhoan,
            tenNhanVien:selectNhanvien.text,
            dienThoai:this.smtp_sdt,
            email:this.smtp_email,
            checkAll: this.checkInAll ? 1: 0,
            nhanVienID: this.pDsNhanVienSelected,
            kieuFile: type
          },
        }).then((response) => {
          let name = moment(this.kyCuoc,'YYYYMMDD').format('MMYYYY');
          this.downloadFileZip(`${selectNhanvien.text}_${name}.zip`,response.data)
        });
      } catch (error) {
        this.$root.toastError("Có lỗi trong quá trình tạo file! Mời thử lại");
      } finally {
        this.loading(false);
      }
    },

    onCapNhapTTLHClicked() {
      this.$refs.thongtinlienhe.show();
    },

    openFilterDialog () {
      this.pDsKhachHang = [...this.ds_danhSachKH]
      this.$refs.refFilterDialog.openDialog()
    },

    onAcceptFilterHandler(args){
        if (args !== null) {
            let ret = []
            if (args.tu_tien === null) {
            ret = this.ds_danhSachKH.filter(x => x.TIENNO <= args.den_tien)
            } else if (args.den_tien === null) {
            ret = this.ds_danhSachKH.filter(x => x.TIENNO >= args.tu_tien)
            } else {
            ret = this.ds_danhSachKH.filter(x => x.TIENNO >= args.tu_tien && x.TIENNO <= args.den_tien)
            }
            ret.sort((a,b) => (a.TIENNO - b.TIENNO))
            this.pDsKhachHang = [...ret]
        } else {
            this.pDsKhachHang = [...this.ds_danhSachKH]
        }
    },
    async getDsLoaiFile() {
      try {
        this.dsLoaiFile = [];
        let response = await this.$root.context.get("/web-cskh/api/danh-muc/loai-file-in-tong-hop?typeId=1");
        if (response.error_code == this.API_SUCCESS) {
          this.dsLoaiFile = response.data || [];
        }
      } catch (e) {
        this.dsLoaiFile = [];
      }
    },
  },
};
</script>
