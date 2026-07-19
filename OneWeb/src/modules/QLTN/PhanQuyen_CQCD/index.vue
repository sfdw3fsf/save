<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import API from "../api/PhanQuyen_CQCD";
import { mapActions, mapState } from "vuex";

export default {
  components: {},
  data() {
    return {
      idDichVuVT: "",
      maTB: "",
      tenTB: "",
      diaChiTB: "",
      dsDaPhan: [],
      thuebao_id: 0,
      enabledNhapMoi: true,
      enabledGhiLai: false,
    };
  },
  watch: {},
  created() {
    this.getDanhSachDichVuVienThong();
    this.getDanhSachDaPhan("", 2);
  },
  mounted() {},
  methods: {
    ...mapActions("qltnCommon", ["getDanhSachDichVuVienThong"]),
    thuebao_selectedRowChanged(data) {
      if (data) {
        this.maTB = data.MA_TB;
        this.tenTB = data.TEN_TB;
        this.diaChiTB = data.DIACHI_TB;
        this.idDichVuVT = data.DICHVUVT_ID;
        this.thuebao_id = data.THUEBAO_ID;
      }
    },
    resetForm() {
      this.maTB = "";
      this.thuebao_id = 0;
      this.tenTB = "";
      this.diaChiTB = "";
      this.idDichVuVT = "";
    },
    NhapMoi() {
      this.enabledNhapMoi = false;
      this.enabledGhiLai = true;
      this.resetForm();
      this.$refs.maTB.focus();
    },
    CapNhat() {
      this.enabledNhapMoi = true;
      this.enabledGhiLai = false;
      if (this.maTB.trim() == "") {
        this.$root.toastError("Bạn chưa nhập mã thuê bao!");
        this.$refs.maTB.focus();
        return;
      }
      if (this.thuebao_id == 0) {
        this.$root.toastError("Bạn chưa lấy thông tin thuê bao!");
        this.$refs.maTB.focus();
        return;
      }
      this.capNhatQuyenCQCD(0, this.thuebao_id);
    },
    async capNhatQuyenCQCD(cdtbId, vthuebao) {
      try {
        this.loading(true);
        const data = {
          chuyenQuyenCDTBId: cdtbId,
          thueBaoId: vthuebao,
        };
        const response = await API.capNhatCQ(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          const msg =
            cdtbId == 0 ? "Cập nhật thành công!" : "Xóa dữ liệu thành công!";
          this.$root.toastSuccess(msg);
          this.getDanhSachDaPhan("", 2);
          this.resetForm();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async getDanhSachDaPhan(matb, loai) {
      try {
        this.loading(true);
        const params = {
          maTB: matb,
          loai: loai,
        };
        const response = await API.getDanhSachCQ(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          if (loai == 1) {
            this.enabledNhapMoi = false;
            this.enabledGhiLai = true;
            this.thuebao_selectedRowChanged(response.data.data[0]);
          } else {
            this.dsDaPhan = response.data.data;
          }
          this.$refs.maTB.focus();
        } else {
          if (loai == 1) {
            this.$root.toastError("Không tìm thấy thông tin của thuê bao!");
            // this.dsDaPhan = [];
            this.resetForm();
          }
        }
      } catch (error) {
        // this.dsDaPhan = [];
        this.$root.toastError(error.data.message_detail);
        this.resetForm();
      } finally {
        this.loading(false);
      }
    },
    onEnterMaTB() {
      if (this.maTB.trim() == "") return;
      this.getDanhSachDaPhan(this.maTB, 1);
    },
    gridThueBaoCommand(arg) {
      this.onDeleteRowHandler(arg.rowData);
    },
    onDeleteRowHandler(e) {
      this.$confirm(`Bạn có thực sự muốn xóa thuê bao này!`, "Xác nhận", {
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Hủy",
      })
        .then(() => {
          this.capNhatQuyenCQCD(e.CHUYENQUYENCDTB_ID, e.THUEBAO_ID);
        })
        .catch(() => {});
    },
  },
  computed: {
    ...mapState("qltnCommon", ["danhSachDichVuVienThong"]),
  },
};
</script>
