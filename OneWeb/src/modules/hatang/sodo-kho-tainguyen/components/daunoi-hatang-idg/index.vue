<template src="./index.template.html"></template>

<script>
export default {
  data: function () {
    return {
      dsHT: [],
      loaiHaTang: "HTAH",
      haTangIDGID: 0,
      thongTinHaTangIDG: {
        ID: 0,
        TEN: "",
        MAHATANG_INFRA: "",
        HATANG_CONGNGHE: "",
      },
      thongTinHaTang: {
        ID: 0,
        TEN: "",
        LOAIHATANG: "",
        HATANG_CONGNGHE: "",
      },
    };
  },
  computed: {
    IS_HTAH: function () {
      return this.loaiHaTang == "HTAH";
    },
    DIEMDAU_TEXT: function () {
      return this.IS_HTAH ? "Hạ tầng ảo hóa" : "Hạ tầng cloud";
    },
  },
  created: function () {},
  methods: {
    dauNoi: async function () {
      if (this.haTangIDGID == 0) {
        return {
          result: false,
          data: null,
          msg: "Chưa chọn hạ tầng IDG cần đối nối",
        };
      }
      return await this.dauNoiHT();
    },
    loadData: async function (loaiHaTang, id) {
      this.haTangIDGID = 0;
      this.loaiHaTang = loaiHaTang;
      let item = await this.getThongTinHaTang(id);
      this.thongTinHaTang = {
        ID: 0,
        TEN: "",
        LOAIHATANG: "",
        HATANG_CONGNGHE: "",
      };
      if (item.result) {
        this.thongTinHaTang = item.data;
      } else {
        this.$root.toastError(item.msg);
      }
      await this.loadDsHT();
    },
    loadDsHT: async function () {
      this.dsHT = [];
      let item = await this.getDsHaTangIDG();
      if (item.result) {
        this.dsHT = item.data;
      } else {
        this.$root.toastError(item.msg);
      }
    },
    comboboxHaTang_Changed: async function (args) {
      let item = await this.getThongTinHaTangIDG();
      this.thongTinHaTangIDG = {
        ID: 0,
        TEN: "",
        LOAIHATANG: "",
        HATANG_CONGNGHE: "",
      };
      if (item.result) {
        this.thongTinHaTangIDG = item.data;
      } else {
        this.$root.toastError(item.msg);
      }
    },
    getThongTinHaTang: async function (id) {
      let apiUrl = "/web-ecms/hatang/sodo-logic/get-thongtin-htah";
      if (!this.IS_HTAH) {
        apiUrl = "/web-ecms/hatang/sodo-logic/get-thongtin-htcloud";
      }
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(apiUrl, {
          id: id,
        });
        result.data = rs.data;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi lấy thông tin hạ tầng";
      }
      return result;
    },
    getDsHaTangIDG: async function () {
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/sodo-logic/get-ds-htidg-chuadn",
          {
            id: this.thongTinHaTang.ID,
            dauNoiHTAH: this.IS_HTAH,
          }
        );
        result.data = rs.data;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi lấy thông tin danh sách hạ tầng IDG";
      }
      return result;
    },
    getThongTinHaTangIDG: async function () {
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/sodo-logic/get-thongtin-htidg",
          {
            id: this.haTangIDGID,
          }
        );
        result.data = rs.data;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi lấy thông tin hạ tầng";
      }
      return result;
    },
    dauNoiHT: async function () {
      if (this.IS_HTAH) {
        return await this.dauNoiHTAHvsIDG();
      } else {
        return await this.dauNoiHTCLOUDvsIDG();
      }
    },
    dauNoiHTAHvsIDG: async function () {
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/sodo-logic/daunoi-htah-htidg",
          {
            hatangaohoa_id: this.thongTinHaTang.ID,
            hatangidg_id: this.haTangIDGID,
          }
        );
        result.data = rs.data.result;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi đối nối hạ tầng ảo hóa và hạ tầng IDG";
      }
      return result;
    },
    dauNoiHTCLOUDvsIDG: async function () {
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/sodo-logic/daunoi-htcloud-htidg",
          {
            hatangsmartcloud_id: this.thongTinHaTang.ID,
            hatangidg_id: this.haTangIDGID,
          }
        );
        result.data = rs.data.result;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi đối nối hạ tầng cloud và hạ tầng IDG";
      }
      return result;
    },
  },
};
</script>
