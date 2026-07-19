<template src="./CommissionRate.html"></template>
<style scoped src="./CommissionRate.scss"></style>
<script>
import api from "./CommissionRateAPI.js";
export default {
  components: {
    ThongTinTyLeThuCuoc: () => import("./part/ThongTinTyLeThuCuoc.vue"),
    DanhSachTyLeThuCuoc: () => import("./part/DanhSachTyLeThuCuoc.vue"),
    DoubleTable: () => import("./part/DoubleTable.vue"),
  },
  data() {
    return {
      header: {
        title: "Tỷ lệ thù lao được hưởng",
        list: [
          // Dữ liệu -> Quản lý thu nợ -> Tham số tính lương đại lý -> Tỷ lệ thù lao được hưởng
          { name: "Dữ liệu", link: { name: "Ui.cards" } },
          { name: "Quản lý thu nợ", link: { name: "Ui.buttons" } },
          { name: "Tham số tính lương đại lý", link: { name: "Ui.buttons" } },
          { name: "Tỷ lệ thù lao được hưởng", link: { name: "Ui.buttons" } },
        ],
      },
      tyle_tps_id: null,
      form_button: {
        nhapMoi: true,
        ghiLai: true,
        huy: true,
        xoa: true,
      },
      form_thongTinTyLeThuCuoc: {
        tyLeThuCuoc: "",
        tyLePStu: 0,
        tyLePSden: 0,
        heSo: 0,
        kyHoaDon: [],
        loaiNhanVien: [],
        khuVuc: [],
      },
      table_tyLeThuCuoc: {
        currentRecord: null,
        data: null,
        loading: false,
        options: [],
        config: [
          {
            label: "STT",
            filterAble: true,
            key: "tyle_tps_id",
            width: "60px",
          },
          {
            label: "Tỷ lệ thu cước",
            filterAble: true,
            key: "tyle_tps",
          },
          {
            label: "Hệ số",
            filterAble: true,
            key: "heso_tps",
          },
        ],
        all_data: [],
      },
      table_kyHoaDon: {
        title_1: "Kỳ hóa đơn đã gán",
        title_2: "Kỳ hóa đơn chưa gán",
        loading: false,

        config: [
          {
            filterAble: true,
            key: "chukyno",
          },
        ],
        dataChuaGan: [],
      },
      table_loaiNhanVien: {
        title_1: "Loại nhân viên đã gán",
        title_2: "Loại nhân viên chưa gán",
        loading: false,

        config: [
          {
            filterAble: true,
            key: "ten",
          },
        ],
        dataChuaGan: [],
      },
      table_khuVuc: {
        title_1: "Khu vực đã gán",
        title_2: "Khu vực chưa gán",
        loading: false,

        config: [
          {
            filterAble: true,
            key: "ten_kv",
          },
        ],
        dataChuaGan: [],
      },
    };
  },
  computed: {
    danhSachTyleThuCuoc: function () {
      const dataTable =
        this.$store.getters["commissionRateCommon/danhSachTyLeThuCuocGetter"];

      this.table_tyLeThuCuoc.currentRecord = dataTable ? dataTable[0] : [];
      return dataTable || [];
    },
  },
  async created() {
    // await this.getDataTable();
    await this.getDanhsachTyle();
  },
  watch: {
    tyle_tps_id() {
      if (this.tyle_tps_id != null) this.getDataTable();
    },
  },
  methods: {
    getDanhsachTyle() {
      this.tyle_tps_id = 0;
      this.$store.dispatch("commissionRateCommon/getDanhSachTyLeThuCuoc", {"p_param": "*", "p_type":1});
    },
    async getDataTable() {
      this.table_kyHoaDon.loading = true;
      this.table_khuVuc.loading = true;
      this.table_loaiNhanVien.loading = true;
      let typeTps = this.tyle_tps_id;
      const LoaiChuKyNoChuaGan = await this.$store.dispatch(
        "TyleTPSLayDSTheoChuKyNoCommon/getCommissionRateChuKyChuaGan",
        typeTps
      );
      this.table_kyHoaDon.dataChuaGan = LoaiChuKyNoChuaGan;
      const LoaiChuKyNoDaGan = await this.$store.dispatch(
        "TyleTPSLayDSTheoChuKyNoCommon/getCommissionRateChuKyDaGan",
        typeTps
      );

      this.form_thongTinTyLeThuCuoc.kyHoaDon = LoaiChuKyNoDaGan;
      this.table_kyHoaDon.loading = false;

      const LoaiNVChuaGan = await this.$store.dispatch(
        "TyleTPSLayDSTheoLoaiNVCommon/getCommissionRateNVChuaGan",
        typeTps
      );

      this.table_loaiNhanVien.dataChuaGan = LoaiNVChuaGan;
      const LoaiNVDaGan = await this.$store.dispatch(
        "TyleTPSLayDSTheoLoaiNVCommon/getCommissionRateNVDaGan",
        typeTps
      );
      this.form_thongTinTyLeThuCuoc.loaiNhanVien = LoaiNVDaGan;

      this.table_loaiNhanVien.loading = false;

      const LoaiKVChuaGan = await this.$store.dispatch(
        "TyleTPSLayDSTheoKhuVucCommon/getCommissionRateKhuVucChuaGan",
        typeTps
      );
      this.table_khuVuc.dataChuaGan = LoaiKVChuaGan;
      const LoaiKVDaGan = await this.$store.dispatch(
        "TyleTPSLayDSTheoKhuVucCommon/getCommissionRateKhuVucDaGan",
        typeTps
      );
      this.form_thongTinTyLeThuCuoc.khuVuc = LoaiKVDaGan;
      this.table_khuVuc.loading = false;
    },
    resetForm() {
      this.tyle_tps_id = null;
      this.form_thongTinTyLeThuCuoc.tyLeThuCuoc = "";
      this.form_thongTinTyLeThuCuoc.tyLePStu = 0;
      this.form_thongTinTyLeThuCuoc.tyLePSden = 0;
      this.form_thongTinTyLeThuCuoc.heSo = 0;
      this.form_thongTinTyLeThuCuoc.kyHoaDon = [];
      this.form_thongTinTyLeThuCuoc.loaiNhanVien = [];
      this.form_thongTinTyLeThuCuoc.khuVuc = [];
    },
    btnThemMoiOnClick: function () {
      this.resetForm();
      this.form_button.nhapMoi = false;
      this.form_button.xoa = false;
      this.tyle_tps_id = 0;
    },
    btnGhiLaiOnClick() {
      if (
        this.$refs.thongTinTyLeThuCuoc.$refs.tyLeThuCuoc.checkValidate() ||
        this.$refs.thongTinTyLeThuCuoc.$refs.tyLePStu.checkValidate() ||
        this.$refs.thongTinTyLeThuCuoc.$refs.tyLePSden.checkValidate() ||
        this.$refs.thongTinTyLeThuCuoc.$refs.heSo.checkValidate()
      ) {
        return false;
      }
      if (this.form_thongTinTyLeThuCuoc.tyLeThuCuoc == "") {
        this.$toast.error('Bạn chưa nhập "Nội dung tỷ lệ thu cước phát sinh!');
        return false;
      }
      if (this.form_thongTinTyLeThuCuoc.heSo == 0) {
        this.$toast.error("Hệ số phát sinh phải lớn hơn 0!");
        return false;
      }
      if (this.form_thongTinTyLeThuCuoc.kyHoaDon.length == 0) {
        this.$toast.error("Bạn chưa gán kỳ hóa đơn!");
        return false;
      }
      if (this.form_thongTinTyLeThuCuoc.loaiNhanVien.length == 0) {
        this.$toast.error("Bạn chưa gán loại nhân viên!");
        return false;
      }
      if (this.form_thongTinTyLeThuCuoc.khuVuc.length == 0) {
        this.$toast.error("Bạn chưa gán khu vực!");
        return false;
      }

      const mapIDChuKyNo = this.formatOption(
        this.form_thongTinTyLeThuCuoc.kyHoaDon,
        "CHUKYNO"
      );
      const mapIDKV = this.formatOption(
        this.form_thongTinTyLeThuCuoc.khuVuc,
        "KHUVUC_ID"
      );
      const mapIDNV = this.formatOption(
        this.form_thongTinTyLeThuCuoc.loaiNhanVien,
        "LOAINV_ID"
      );
      console.log('mapIDChuKyNo is ', mapIDChuKyNo)
      let yourStringKHD = JSON.stringify(mapIDChuKyNo);
      let yourStringKV = JSON.stringify(mapIDKV);
      let yourStringLNV = JSON.stringify(mapIDNV);

      const objTPS = {
        is_insert: this.form_button.nhapMoi ? 0 : 1,
        tyle_tps_id: this.tyle_tps_id,
        json_tyle: [{
          TYLE_TPS: this.form_thongTinTyLeThuCuoc.tyLeThuCuoc,
          TYLE_TU: this.form_thongTinTyLeThuCuoc.tyLePStu,
          TYLE_DEN: this.form_thongTinTyLeThuCuoc.tyLePSden,
          HESO_TPS: this.form_thongTinTyLeThuCuoc.heSo,
        }],
        json_tyle_kv: mapIDKV,
        json_tyle_lnv: mapIDNV,
        json_tyle_ckn: mapIDChuKyNo,
      };

      api
        .postCommissionRate(this.axios, objTPS)
        .then((response) => {
          if (response.data.message == "Success") {
            this.$toast.success(
              (this.form_button.nhapMoi ? " Cập nhật" : " Thêm mới") +
                " tỷ lệ thù lao được hưởng"
            );
            this.getDanhsachTyle();
          } else {
            this.$toast.error(
              "Có lỗi xảy ra, vui lòng lin hệ Quản trị để được hỗ trợ!"
            );
          }
        })
        .catch((error) => {
          if (!error.response.data.message) {
            this.$toast.error(error.response.data.message);
          }
        });
    },
    formatOption(arr, name) {
      const mapData = arr.map((obj) => {
        const objNew = {};
        objNew[name] =
          name == "CHUKYNO" ? obj.id : obj[name.toLocaleLowerCase()];
        return objNew;
      });
      return mapData;
    },

    btnHuyOnClick() {
      this.resetForm();
      this.form_button.nhapMoi = true;
      this.form_button.xoa = true;
      this.getDanhsachTyle();
    },
    btnXoaOnClick() {
      if (confirm("Bạn thật sự muốn xóa dữ liệu không?")) {
        api
          .deleteCommissionRate(this.axios, { tyle_tps_id: this.tyle_tps_id })
          .then((response) => {
            if (response.data.message == "Success") {
              this.$toast.success("Xóa tỷ lệ thù lao được hưởng thành công");
              this.getDanhsachTyle();
            } else {
              this.$toast.error(
                "Có lỗi xảy ra, vui lòng lin hệ Quản trị để được hỗ trợ!"
              );
            }
          })
          .catch((error) => {
            if (!error.response.data.message) {
              this.$toast.error(error.response.data.message);
            }
          });
      }
    },
    onGetDateChecked(val) {
      this.resetForm();
      this.form_button.nhapMoi = true;
      this.form_button.xoa = true;
      this.tyle_tps_id = val.tyle_tps_id;
      this.form_thongTinTyLeThuCuoc.tyLeThuCuoc = val.tyle_tps;
      this.form_thongTinTyLeThuCuoc.tyLePStu = val.tyle_tu;
      this.form_thongTinTyLeThuCuoc.tyLePSden = val.tyle_den;
      this.form_thongTinTyLeThuCuoc.heSo = val.heso_tps;

      //  form_thongTinTyLeThuCuoc: {
      //   tyLeThuCuoc: "",
      //   tyLePStu: 0,
      //   tyLePSden: 0,
      //   heSo: 0,
      //   kyHoaDon: [],
      //   loaiNhanVien: [],
      //   khuVuc: [],
      // },
    },
  },
};
</script>
