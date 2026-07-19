<template src='./frmGiaHanHD.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import Vue from "vue";
import frmGiaHanHDAPI from "../api/frmGiaHanHDAPI";
import { actionName, statePropertyName } from "../store/frmgiahanhd";
import select2 from "@/components/Select2.vue";

export default {
  components: { appSelect2: select2 },
  name: "frmGiaHanHD",
  mounted() {},
  provide: {
    grid: [],
  },
  computed: {
    ...mapState("frmgiahanhd", statePropertyName),
  },
  data() {
    return {
      params: {
        p_dtpNgayHH: new Date(),
        p_dtpNgayGH: new Date(new Date().getTime() + 1 * 24 * 60 * 60 * 1000),
        p_txtGhiChu: "",
      },
      Enabled: {
        tsbtnNhapMoi: false,
        tsbtnGhiLai: false,
        tsbtnXoa: false,
        tsbtnHuyBo: false,
      },

      grcGiaHanHD: [],

      hdms_id: 0,
      ngay_gh_cu: new Date(),
      dt_ngay_gh_moi: "",
    };
  },
  methods: {
    ...mapActions("frmgiahanhd", actionName),

    // chuyển Date thành type string
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_StringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    async LayDuLieu() {
      let dt = this.TRACUU_HD_THEO_DK({ hdmsId: this.hdms_id });
      dt.length
        ? (this.ngay_gh_cu = this.f_StringToDate(
            dt[0]["NGAY_HH"],
            "DD/MM/YYYY hh:mm:ss"
          ))
        : "";
    },
    async SetButton(kieu) {
      this.Enabled.tsbtnNhapMoi = false;
      this.Enabled.tsbtnGhiLai = false;
      this.Enabled.tsbtnXoa = false;
      this.Enabled.tsbtnHuyBo = false;
      //Bat dau
      if (kieu == -1) {
        this.Enabled.tsbtnNhapMoi = true;
      }
      //Bat dau
      if (kieu == 0) {
        this.Enabled.tsbtnNhapMoi = true;
        // grvGiaHanHD.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
        await this.Clear();
      }
      //Them moi
      if (kieu == 1) {
        this.Enabled.tsbtnGhiLai = true;
        this.Enabled.tsbtnHuyBo = true;
        await this.Clear();
      }
      //Huy
      if (kieu == 2) {
        this.Enabled.tsbtnNhapMoi = true;
        this.Enabled.tsbtnXoa = true;
        // grvGiaHanHD.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
        await this.Clear();
      }
      //Edit
      if (kieu == 3) {
        this.Enabled.tsbtnNhapMoi = true;
        this.Enabled.tsbtnXoa = true;
        this.Enabled.tsbtnGhiLai = true;
        this.Enabled.tsbtnHuyBo = true;
      }
    },
    async Clear() {
      await this.LayDuLieu();
      this.params.p_txtGhiChu = "";
      this.params.p_dtpNgayGH = new Date(
        this.ngay_gh_cu.getTime() + 1 * 24 * 60 * 60 * 1000
      );
      this.params.p_dtpNgayHH = this.ngay_gh_cu;
      // dtpNgayGH.BackColor = Color.White;
      // errorProvider1.Clear();
      // dtpNgayGH.EditValueChanged += dtpNgayGH_EditValueChanged;
    },
    // Kiểm tra dữ liệu nhập vào
    async KiemTra_DuLieu() {
      let ok = true;
      if (this.Enabled.tsbtnNhapMoi != false) {
        let dt = this.grcGiaHanHD;
        // let q = Math.max(...dt.map(a=>a["NGAY_GH_CU"]))
        // var q=dt.AsEnumerable().Max(x => x["NGAY_GH_CU"]);
        let q = [
          ...dt.map((a) =>
            this.f_StringToDate(a["NGAY_GH_CU"], "DD/MM/YYYY hh:mm:ss")
          ),
        ].reduce(function (a, b) {
          return a > b ? a : b;
        });
        if (
          this.f_DateToString(
            this.f_StringToDate(this.params.p_dtpNgayHH, "DD/MM/YYYY hh:mm:ss")
          ) != this.f_DateToString(q)
        ) {
          this.$toast.error("Chỉ có thể sửa thông tin mới nhất");
          // grvGiaHanHD.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
          ok = false;
        }
      }
      if (
        this.f_StringToDate(this.f_DateToString(this.params.p_dtpNgayGH)) <=
        this.f_StringToDate(this.f_DateToString(this.params.p_dtpNgayHH))
      ) {
        // errorProvider1.SetError(dtpNgayGH, "Ngày gia hạn phải lớn hơn ngày hết hạn ");
        // this.$refs.dtpNgayGH.focusIn();
        this.$toast.error("Ngày gia hạn phải lớn hơn ngày hết hạn");
        // dtpNgayGH.BackColor = Color.LightYellow;
        ok = false;
      } else {
        // errorProvider1.SetError(dtpNgayGH, "");
      }

      // foreach (Control c in panel_TCTT.Controls)
      // {
      //     if (errorProvider1.GetError(c).Length > 0)
      //         ok = false;
      // }

      return ok;
    },
    async CreateTable(hdmsId, ngayGH, ngayGHCu, ghiChu) {
      let dt_temp = {
        hdmsId: hdmsId,
        nhanVienId: this.$auth.getNhanVienID(),
        donViId: this.$auth.getDonViID(),
        ngayGH: ngayGH,
        ngayGHCu: ngayGHCu,
        ghiChu: ghiChu,
        nguoiDungId: this.$auth.getNguoiDungID(),
        nguoiCN: this.$auth.getUserName(), //ttnd.ten_nd
        mayCN: this.$auth.getUserName(), //ttnd.may_cn
        ipCN: "10.59.90.123",
      };
      return dt_temp;
    },
    async HienThiLS_GiaHan() {
      // try {
      this.grcGiaHanHD = [];
      this.grcGiaHanHD = await this.LAY_LICHSU_GIAHAN_HD({
        // phanVungId: this.$auth.getPhanVungID(),
        hdmsId: this.hdms_id,
      });
      this.grcGiaHanHD.length == 0 ? await this.SetButton(0) : "";
      // } catch (error) {
      //     this.$toast.error("Lỗi khi danh sách đăng ký !")
      // }
    },
    async GhiLai() {
      // try {
      if (!(await this.KiemTra_DuLieu())) {
        return;
      }

      let GH_HD = await this.CreateTable(
        this.hdms_id,
        this.f_DateToString(this.params.p_dtpNgayGH),
        this.f_DateToString(this.params.p_dtpNgayHH),
        this.params.p_txtGhiChu
      );

      if (this.Enabled.tsbtnNhapMoi == false) {
        GH_HD.kieu = 0;
        await this.I_U_D_HD_GIANHAN(0, GH_HD);
        // HopDong.INSERT_UPDATE_DELETA_HD_GIAHAN(0, GH_HD);
        // Message_Box.ShowTB("Gia hạn hợp đồng thành công !");
      } else {
        GH_HD.kieu = 1;
        this.I_U_D_HD_GIANHAN(1, GH_HD);
        //HopDong.INSERT_UPDATE_DELETA_HD_GIAHAN(1, GH_HD);

        //  Message_Box.ShowTB("Cập nhật gia hạn hợp đồng thành công !");
      }
      await this.HienThiLS_GiaHan();
      // } catch (error) {
      //     this.$toast.error("Có lỗi: ")
      // }
    },
    async I_U_D_HD_GIANHAN(kieu, dt) {
      dt.kieu = kieu;
      var t = await this.giahan_hdms(dt);
      if (t != null && t.toString() == "1") {
        this.$toast.success("Thao tác gia hạn HĐ thành công");
      } else {
        this.$toast.error("Thao tác gia hạn HĐ thất bại" + t.toString());
      }
    },
    // Sự kiện nút xóa
    async Xoa() {
      try {
        this.loading(true);
        let GH_HD = await this.CreateTable(
          this.hdms_id,
          this.f_DateToString(this.params.p_dtpNgayGH),
          this.f_DateToString(this.params.p_dtpNgayHH),
          this.params.p_txtGhiChu
        );
        GH_HD.kieu = 2;
        await this.I_U_D_HD_GIANHAN(2, GH_HD);
        await this.HienThiLS_GiaHan();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async frmGiaHanHD_Load() {},
    async tsbtnNhapMoi_Click() {
      await this.SetButton(1);
    },
    async tsbtnHuyBo_Click() {
      await this.SetButton(0);
    },
    async tsbtnGhiLai_Click() {
      await this.GhiLai();
    },
    async tsbtnXoa_Click() {
      await this.Xoa();
    },
    async dtpNgayGH_EditValueChanged() {
      if (this.params.p_dtpNgayGH <= this.params.p_dtpNgayHH) {
        // errorProvider1.SetError(dtpNgayGH, "NGày gia hạn phải lớn hơn ngày hết hạn ");
        this.$toast.error("Ngày gia hạn phải lớn hơn ngày hết hạn");
        // this.$refs.dtpNgayGH.focusIn()
        // dtpNgayGH.BackColor = Color.LightYellow;
      } else {
        // dtpNgayGH.BackColor = Color.White;
        // errorProvider1.SetError(dtpNgayGH, "");
      }
      this.$refs.txtGhiChu.focus();
    },
    async grvGiaHanHD_FocusedRowChanged(row) {
      if (!row) {
        await this.SetButton(0);
        return;
      }
      await this.SetButton(3);
      if (this.Enabled.tsbtnNhapMoi != false) {
        let dt = this.grcGiaHanHD;
        // var q = dt.AsEnumerable().Max(x => x["NGAY_GH_CU"]);
        // let q = Math.max(...dt.map(a=>a["NGAY_GH_CU"]))
        let q = [
          ...dt.map((a) =>
            this.f_StringToDate(a["NGAY_GH_CU"], "DD/MM/YYYY hh:mm:ss")
          ),
        ].reduce(function (a, b) {
          return a > b ? a : b;
        });
        if (
          this.f_DateToString(
            this.f_StringToDate(row.NGAY_GH_CU, "DD/MM/YYYY hh:mm:ss")
          ) != this.f_DateToString(q)
        ) {
          await this.SetButton(-1);
          this.params.p_dtpNgayHH = this.f_StringToDate(
            row.NGAY_GH_CU,
            "DD/MM/YYYY hh:mm:ss"
          );
          this.params.p_dtpNgayGH = this.f_StringToDate(
            row.NGAY_GH,
            "DD/MM/YYYY hh:mm:ss"
          );
          this.params.p_txtGhiChu = row.GHICHU || "";
          return;
        }
      }
      this.params.p_dtpNgayHH = this.f_StringToDate(
        row.NGAY_GH_CU,
        "DD/MM/YYYY hh:mm:ss"
      );
      this.params.p_dtpNgayGH = this.f_StringToDate(
        row.NGAY_GH,
        "DD/MM/YYYY hh:mm:ss"
      );
      this.dt_ngay_gh_moi = this.f_StringToDate(
        row.NGAY_GH,
        "DD/MM/YYYY hh:mm:ss"
      );
      this.params.p_txtGhiChu = row.GHICHU || "";
    },

    async ShowDialog() {
      this.$refs.popupGiaHanHD.show();
    },
    async tsbtnThoat_Click() {
      await this.$parent.Close_tsbtnGiaHan();
    },
  },
  created: async function () {},
  watch: {},
  destroyed() {},
};
</script>
<style>
</style>