<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import API from "../api/XacMinhDiaChiThanhToanDiDong";
import { previewPrint } from "../../../utils/util";

export default {
  components: { breadcrumb },
  data() {
    return {
      header: {
        title: "DANH SÁCH THUÊ BAO CHỜ XÁC MINH ĐỊA CHỈ THANH TOÁN",
        list: [
          { name: "Quản lý đại lý", link: { name: "Ui.cards" } },
          {
            name: "Xác minh địa chỉ thanh toán di động",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      waterMark: "Chọn ngày",
      dateFormat: "dd/MM/yyyy",
      checkDaXacMinh: false,
      tuNgay: new Date(),
      denNgay: new Date(),
      form: {
        tenTT: "",
        diaChiTT: "",
        dienThoai: "",
        maNV: "",
        sdtNV: "",
        ghiChu: "",
        xacMinh: "",
      },
      dsKhuVuc: [],
      dsKhachHang: [],
      selectedItem: null,
      selectedKhuVuc: [],
      xmtt_id: 0,
      thanhtoan_id: 0,
      nhanvien_xm_id: 0,
      totalItems: 0,
    };
  },
  validations: {},
  watch: {},
  created() {
    this.fetchDSKhuVuc();
  },
  mounted() {},
  methods: {
    async NhanTin() {
      if (!this.selectedItem) {
        this.$root.toastError(
          "Bạn chưa chọn Khách hàng cần gửi tin yêu cầu xác minh!"
        );
        return;
      }
      if (this.selectedItem.XACMINH != "0") {
        this.$root.toastError("Khách hàng đã được xác minh!");
        return;
      }
      if (this.form.dienThoai.trim() == "") {
        this.$root.toastError("Bạn chưa nhập số điện thoại!");
        return;
      }
      const sNoiDung = `Thuê bao ${this.selectedItem.MA_TB.trim()}: ${this.form.tenTT}-${this.form.diaChiTT} (${this.selectedItem.DTLH}) cần xác minh địa chỉ thanh toán`;
      // console.log(sNoiDung)
      this.loading(true);
      const data = {
        sdt: this.form.dienThoai,
        noiDung: sNoiDung,
      };
      try {
        const response = await API.sendSMS(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Nhắn tin thành công!");
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    PhieuXacMinh() {
      if (!this.selectedItem) {
        this.$root.toastError("Hãy chọn thông tin để in phiếu!");
        return;
      }
      this.fetchInPhieu();
    },
    XacMinh() {
      if (!this.selectedItem) {
        this.$root.toastError("Bạn chưa chọn khách hàng cần xác minh!");
        return;
      }
      if (this.form.maNV.trim() == "" || this.nhanvien_xm_id == "0") {
        this.$root.toastError("Chưa có nhân viên xác minh!");
        return;
      }
      if (this.form.xacMinh != 1 && this.form.xacMinh != 2) {
        this.$root.toastError("Hãy chọn trạng thái xác minh!");
        return;
      }
      this.fetchXacMinh();
    },
    async fetchInPhieu() {
      this.loading(true);
      try {
        const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-thuno/api/thu-no/common/in-phieu-xac-minh/` +
            this.thanhtoan_id,
          method: "POST",
          responseType: "blob",
        }).then((response) => {
          previewPrint(response.data);
        });
      } catch (error) {
        this.$root.toastError(error.statusText);
      } finally {
        this.loading(false);
      }
    },
    async fetchXacMinh() {
      const obj = this.selectedItem;
      const data = {
        xacMinhTTId: obj.XMTT_ID,
        thanhToanId: obj.THANHTOAN_ID,
        xacMinh: this.form.xacMinh,
        ghiChu: this.form.ghiChu,
        nguoiXM: this.form.maNV.trim(), //obj.NGUOI_XM
        nhanVienXM: obj.NHANVIEN_XM_ID,
      };
      this.loading(true);
      try {
        const response = await API.updateXacMinh(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Xác minh thành công!");
          this.fetchDSKhachHang(0, 10);
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    LayDanhSach() {
      const dsSelected = this.$refs.gridKhuvuc.getSelectedRecords() || [];
      if (dsSelected.length <= 0) {
        this.$root.toastError("Bạn chưa chọn khu vực!");
        return;
      }
      this.fetchDSKhachHang(0, 10);
    },
    async fetchDSKhachHang(page, size) {
      const params = {
        dsDonVi: this.selectedKhuVuc.toString(), //"10353,10363,10350"
        tuNgay: moment(this.tuNgay).format("DD/MM/YYYY"), //01/01/2019
        denNgay: moment(this.denNgay).format("DD/MM/YYYY"), //30/03/2021
        xacMinh: this.checkDaXacMinh ? 1 : "",
        pageNum: page + 1,
        pageSize: size,
        totalRow: 1,
      };
      this.loading(true);
      try {
        const response = await API.getDanhSachThanhToan(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.data.length > 0
        ) {
          this.dsKhachHang = response.data.data.data.map((item) => ({
            ...item,
            TT_XM:
              item.XACMINH == "1"
                ? "Xác minh được"
                : item.XACMINH == "2"
                ? "Không xác minh được"
                : "Chưa xác minh",
          }));
          this.totalItems = response.data.data.totalElement;
        } else {
          this.dsKhachHang = [];
          this.totalItems = 0;
          this.resetData();
          this.$root.toastError("Không tìm thấy dữ liệu. Mời thử lại!");
        }
      } catch (error) {
        this.dsKhachHang = [];
        this.totalItems = 0;
        this.resetData();
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    khachhang_gridChanged(args) {
      this.fetchDSKhachHang(args.pageIndex, args.pageSize);
    },
    async fetchDSKhuVuc() {
      this.loading(true);
      const response = await API.getDanhSachKhuVuc(this.axios, {
        loaiDVId: 32, //Tổ thu cước
      });
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length >= 0
      ) {
        this.dsKhuVuc = response.data.data;
      }
      this.loading(false);
    },
    khuvuc_selectedItemsChanged(data) {
      if (data) {
        this.selectedKhuVuc = data;
      }
    },
    khachhang_selectedRowChanged(data) {
      if (data) {
        this.fillThongTin(data);
        this.selectedItem = data;
      } else {
        this.resetData();
        this.selectedItem = null;
      }
    },
    fillThongTin(data) {
      this.form.tenTT = data.TEN_TT;
      this.form.diaChiTT = data.DIACHI_TT;
      this.form.dienThoai = data.DTLH;
      this.form.maNV = data.MA_NV;
      this.form.sdtNV = data.SO_DT;
      this.form.xacMinh = data.XACMINH;
      if (data.XMTT_ID !== "") {
        this.form.ghiChu = data.GHICHU;
        this.xmtt_id = data.XMTT_ID;
        this.thanhtoan_id = data.THANHTOAN_ID;
        this.nhanvien_xm_id = data.NHANVIEN_XM_ID;
      }
    },
    resetData() {
      this.form.tenTT = "";
      this.form.diaChiTT = "";
      this.form.dienThoai = "";
      this.form.maNV = "";
      this.form.sdtNV = "";
      this.form.ghiChu = "";
      this.form.xacMinh = "";
    },
  },
  computed: {},
};
</script>
