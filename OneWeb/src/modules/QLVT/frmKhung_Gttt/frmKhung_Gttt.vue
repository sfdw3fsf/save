<template src='./frmKhung_Gttt.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import { Group, Page, Filter, Sort, Resize } from "@syncfusion/ej2-vue-grids";
import api from "../api/frmKhung_GtttAPI";

export default {
  components: { breadcrumb },
  name: "frmKhung_Gttt",
  provide: {
    grid: [Group, Page, Filter, Sort, Resize],
  },
  mounted() {},
  computed: {},
  data() {
    return {
      isDisableNhapMoi: false,
      isDisableXoa: false,

      txtCanhBao: "",
      cboCanhBao: [],
      txtSoLuong: "",
      txtGiaTriTu: "",
      txtGiaTriDen: "",
      checkBoxHieuLuc: false,

      vrtt_id: 0,
      vkhung_id: 0,

      dsTTKTT: [],
      dsKhungGTTT: [],
    };
  },
  methods: {
    async loadgrid_ttktt() {
      let res = await api.layDSThuocTinh(this.axios, 0);
      this.dsTTKTT = res.data.data;
    },

    async loadgrid_giatri() {
      let res = await api.layDSGiaTriThuocTinhGan(this.axios, this.vrtt_id);
      this.dsKhungGTTT = res.data.data;
    },

    async onClickGhiLai() {
      if (this.txtGiaTriTu == "" || this.txtGiaTriDen == "") {
        this.$toast.error("Chưa nhập gía trị từ - đến!");
        return;
      }

      if (this.txtGiaTriTu == this.txtGiaTriDen) {
        this.$toast.error("Giá trị từ và giá trị đến không được trùng nhau!");
        return;
      }
      if (this.txtGiaTriTu > this.txtGiaTriDen) {
        this.$toast.error("Giá trị từ phải  bé hơn giá trị đến!");
        return;
      }
      this.capNhat(this.isDisableNhapMoi ? 1 : 0);
      if (this.isDisableNhapMoi) {
        this.setButton(0);
      }
    },

    async capNhat(kieu) {
      this.loading(true);
      let body = {
        rttId: this.vrtt_id,
        giaTriTu: this.txtGiaTriTu,
        giaTriDen: this.txtGiaTriDen,
        hieuLuc: this.checkBoxHieuLuc ? 1 : 0,
        khungId: this.vkhung_id,
        kieu: kieu,
      };

      let message = "Cập nhật giá trị thuộc tính thành công!";
      if (kieu == 1) {
        message = "Thêm mới giá trị thuộc tính thành công!";
      } else if (kieu == 2) {
        message = "Xóa gía trị thuộc tính thành công!";
      }

      try {
        var res = await api.capNhatKhungGTTTT(this.axios, body);
        if (res.data.error == 200) {
          this.$toast.success(message);
          this.loadgrid_giatri();
        } else {
          this.$toast.error(res.data.data);
        }
      } catch (error) {
        this.$toast.error(error.data.message_detail);
      }
      this.loading(false);
    },

    async onClickXoa() {
      if (this.vkhung_id == 0) {
        return;
      }
      this.$confirm(
        `Bạn có chắc chắn muốn xóa gía trị thuộc tính?`,
        "Thông báo",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
        }
      )
        .then(async () => {
          this.capNhat(2);
        })
        .catch(() => {
          return;
        });
    },

    onClickNhapMoi() {
      this.setButton(1);
    },

    gridKhungGTTT_onSelectedRowChanged(data) {
      if (data) {
        this.vkhung_id = data.KHUNG_ID;
        this.txtGiaTriTu = data.GIATRI_TU;
        this.txtGiaTriDen = data.GIATRI_DEN;
        this.checkBoxHieuLuc = data.HIEULUC == 1 ? true : false;
      }
    },

    async gridTTKTT_onSelectedRowChanged(data) {
      if (data) {
        this.vrtt_id = data.RTT_ID;
        this.txtGiaTriTu = "";
        this.txtGiaTriDen = "";
        this.vkhung_id = 0;
        this.checkBoxHieuLuc = false;
        await this.loadgrid_giatri();
        if (this.dsKhungGTTT.length == 0) {
          this.setButton(1);
        } else {
          this.setButton(0);
        }
      }
    },

    onClickHuy() {
      this.setButton(0);
      this.txtGiaTriTu = "";
      this.txtGiaTriDen = "";
      this.checkBoxHieuLuc = false;
    },

    setButton(kieu) {
      if (kieu == 1) {
        this.isDisableNhapMoi = true;
        this.isDisableXoa = true;
        this.txtGiaTriTu = "";
        this.txtGiaTriDen = "";
        this.checkBoxHieuLuc = false;
      } else {
        this.isDisableNhapMoi = false;
        this.isDisableXoa = false;
      }
    },
  },

  created: async function () {
    this.loading(true);
    await this.loadgrid_ttktt();
    this.loading(false);
  },

  watch: {},
  destroyed() {},
};
</script>
