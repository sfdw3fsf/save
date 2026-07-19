<template src="./index.template.html"></template>

<script>
export default {
  data: function () {
    return {
      dsHT: [],
      loaiHaTang: "HTAH",
      haTangID: 0,
      haTangMang: {
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
  },
  watch: {
    loaiHaTang: async function (val) {
      this.haTangID = 0;
      this.thongTinHaTang = {
        ID: 0,
        TEN: "",
        LOAIHATANG: "",
        HATANG_CONGNGHE: "",
      };
      await this.loadDsHT();
    },
  },
  created: function () {},
  methods: {
    dauNoi: async function () {
      if (this.haTangID == 0) {
        return {
          result: false,
          data: null,
          msg: "Chưa chọn hạ tầng cần đối nối",
        };
      }
      return await this.dauNoiHTM();
    },
    loadData: async function (id) {
      this.haTangID = 0;
      let item = await this.getThongTinHaTangMang(id);
      this.haTangMang = {
        ID: 0,
        TEN: "",
        MAHATANG_INFRA: "",
        HATANG_CONGNGHE: "",
      };
      if (item.result) {
        this.haTangMang = item.data;
      } else {
        this.$root.toastError(item.msg);
      }
      await this.loadDsHT();
    },
    loadDsHT: async function () {
      this.dsHT = [];
      let item = await this.getDsHaTangChuaDNbyHTM();
      if (item.result) {
        this.dsHT = item.data;
      } else {
        this.$root.toastError(item.msg);
      }
    },
    comboboxHaTang_Changed: async function (args) {
      let item = await this.getThongTinHaTang();
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
    },
    getThongTinHaTangMang: async function (id) {
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/sodo-logic/get-thongtin-htm",
          {
            id: id,
          }
        );
        result.data = rs.data;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi lấy thông tin hạ tầng mạng";
      }
      return result;
    },
    getThongTinHaTang: async function () {
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
          id: this.haTangID,
        });
        result.data = rs.data;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi lấy thông tin hạ tầng";
      }
      return result;
    },
    getDsHaTangChuaDN: async function () {
      let apiUrl = "/web-ecms/hatang/sodo-logic/get-ds-htah-chuadn";
      if (!this.IS_HTAH) {
        apiUrl = "/web-ecms/hatang/sodo-logic/get-ds-htcloud-chuadn";
      }
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(apiUrl);
        result.data = rs.data;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi lấy thông tin danh sách hạ tầng";
      }
      return result;
    },
    getDsHaTangChuaDNbyHTM: async function () {
      let apiUrl = "/web-ecms/hatang/sodo-logic/get-ds-htah-chuadn-by-htm";
      if (!this.IS_HTAH) {
        apiUrl = "/web-ecms/hatang/sodo-logic/get-ds-htcloud-chuadn-by-htm";
      }
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(apiUrl, {
          id: this.haTangMang.ID,
        });
        result.data = rs.data;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi lấy thông tin danh sách hạ tầng";
      }
      return result;
    },
    dauNoiHTM: async function () {
      if (this.IS_HTAH) {
        return await this.dauNoiHTMvsHTAH();
      } else {
        return await this.dauNoiHTMvsHTCLOUD();
      }
    },
    dauNoiHTMvsHTAH: async function () {
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/sodo-logic/daunoi-htm-htah",
          {
            hatangmang_id: this.haTangMang.ID,
            hatangaohoa_id: this.haTangID,
          }
        );
        result.data = rs.data.result;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi đối nối hạ tầng mạng và hạ tầng ảo hóa";
      }
      return result;
    },
    dauNoiHTMvsHTCLOUD: async function () {
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/sodo-logic/daunoi-htm-htcloud",
          {
            hatangmang_id: this.haTangMang.ID,
            hatangsmartcloud_id: this.haTangID,
          }
        );
        result.data = rs.data.result;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi đối nối hạ tầng mạng và hạ tầng cloud";
      }
      return result;
    },
  },
};
</script>
