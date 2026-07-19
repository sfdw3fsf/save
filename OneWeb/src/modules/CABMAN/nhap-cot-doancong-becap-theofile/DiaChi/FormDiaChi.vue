<template src="./FormDiaChi.template.html"></template>
<script>
export default {
  data: function () {
    return {
      dsTinh: [],
      dsQuan: [],
      dsPhuong: [],
      dsPho: [],
      dsAp: [],
      dsKhu: [],
      dsDacDiem: [],
      CHKBOX_PHO: false,
      CHKBOX_AP: false,
      CHKBOX_KHU: false,
      CHKBOX_DACDIEM: false,
      DIACHI_ID: 0,
      TINH_ID: 0,
      QUAN_ID: 0,
      PHUONG_ID: 0,
      PHO_ID: 0,
      AP_ID: 0,
      KHU_ID: 0,
      DACDIEM_ID: 0,
      SONHA: "",
      DIACHI: "",
    };
  },
  methods: {
    getTinh: async function () {
      let rs = await this.$root.context.get(
        "/web-cabman/bando_tuyencot/lay_thongtin_tinh"
      );
      return rs.data;
    },
    getDsQuan: async function () {
      if (this.TINH_ID > 0) {
        let rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_quan_huyen/" + this.TINH_ID
        );
        return rs.data;
      } else {
        return [];
      }
    },
    getDsPhuong: async function () {
      if (this.QUAN_ID > 0) {
        let rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_phuong_xa/" + this.QUAN_ID
        );
        return rs.data;
      } else {
        return [];
      }
    },
    getDsPhoApKhu: async function () {
      if (this.PHUONG_ID > 0) {
        let rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/" + this.PHUONG_ID
        );
        return rs.data;
      } else {
        return { dsKhu: [], dsPho: [], dsAp: [] };
      }
    },
    getDsDacDiem: async function () {
      if (
        this.PHUONG_ID > 0 ||
        this.PHO_ID > 0 ||
        this.AP_ID > 0 ||
        this.KHU_ID > 0
      ) {
        let rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_dacdiem",
          {
            phuong_id: this.PHUONG_ID,
            pho_id: this.PHO_ID,
            ap_id: this.AP_ID,
            khu_id: this.KHU_ID,
          }
        );
        return rs.data;
      } else {
        return [];
      }
    },
    bindTinh: async function () {
      let tinh = await this.getTinh();
      this.dsTinh = [];
      if (tinh != null) {
        this.dsTinh.push(tinh);
      }
    },
    bindQuan: async function () {
      this.dsQuan = await this.getDsQuan();
      this.dsQuan.unshift({ QUAN_ID: 0, TEN_QUAN: "Chưa xác định" });
    },
    bindPhuong: async function () {
      this.dsPhuong = await this.getDsPhuong();
      this.dsPhuong.unshift({ PHUONG_ID: 0, TEN_PHUONG: "Chưa xác định" });
    },
    bindKhuPhoAp: async function () {
      let data = await this.getDsPhoApKhu();
      this.dsKhu = data.dsKhu;
      this.dsKhu.unshift({ PHO_ID: 0, TEN_PHO: "(Lựa chọn ...)" });
      this.dsPho = data.dsPho;
      this.dsPho.unshift({ PHO_ID: 0, TEN_PHO: "(Lựa chọn ...)" });
      this.dsAp = data.dsAp;
      this.dsAp.unshift({ PHO_ID: 0, TEN_PHO: "(Lựa chọn ...)" });
    },
    bindDacDiem: async function () {
      this.dsDacDiem = await this.getDsDacDiem();
      this.dsDacDiem.unshift({ DACDIEM_ID: 0, DACDIEM: "(Lựa chọn ...)" });
    },
    bindData: async function (arg) {
      this.$root.showLoading(true);
      try {
        let t = await this.bindTinh();
        if (arg.TINH_ID == null) this.TINH_ID = 0;
        else this.TINH_ID = arg.TINH_ID;
        t = await this.bindQuan();
        if (arg.QUAN_ID == null) this.QUAN_ID = 0;
        else this.QUAN_ID = arg.QUAN_ID;
        t = await this.bindPhuong();
        if (arg.PHUONG_ID == null) this.PHUONG_ID = 0;
        else this.PHUONG_ID = arg.PHUONG_ID;
        t = await this.bindKhuPhoAp();
        if (arg.PHO_ID == null) this.PHO_ID = 0;
        else this.PHO_ID = arg.PHO_ID;
        this.CHKBOX_PHO = this.PHO_ID > 0;
        if (arg.AP_ID == null) this.AP_ID = 0;
        else this.AP_ID = arg.AP_ID;
        this.CHKBOX_AP = this.AP_ID > 0;
        if (arg.KHU_ID == null) this.KHU_ID = 0;
        else this.KHU_ID = arg.KHU_ID;
        this.CHKBOX_KHU = this.KHU_ID > 0;
        t = await this.bindDacDiem();
        if (arg.DACDIEM_ID == null) this.DACDIEM_ID = 0;
        else this.DACDIEM_ID = arg.DACDIEM_ID;
        this.CHKBOX_DACDIEM = this.DACDIEM_ID > 0;
        if (arg.DIACHI_ID == null) this.DIACHI_ID = 0;
        else this.DIACHI_ID = arg.DIACHI_ID;
        if (arg.SONHA == null) this.SONHA = "";
        else this.SONHA = arg.SONHA;
        if (arg.DIACHI == null) this.DIACHI = "";
        else this.DIACHI = arg.DIACHI;
      } finally {
        this.$root.showLoading(false);
      }
    },
    selectTinh_OnChange: async function () {
      this.$root.showLoading(true);
      try {
        let t = await this.bindQuan();
        this.QUAN_ID = 0;
        t = await this.bindPhuong();
        this.PHUONG_ID = 0;
        t = await this.bindKhuPhoAp();
        this.KHU_ID = 0;
        this.PHO_ID = 0;
        this.AP_ID = 0;
        t = await this.bindDacDiem();
        this.DACDIEM_ID = 0;
        this.getDiaChi();
      } finally {
        this.$root.showLoading(false);
      }
    },
    selectQuan_OnChange: async function () {
      this.$root.showLoading(true);
      try {
        let t = await this.bindPhuong();
        this.PHUONG_ID = 0;
        t = await this.bindKhuPhoAp();
        this.KHU_ID = 0;
        this.PHO_ID = 0;
        this.AP_ID = 0;
        t = await this.bindDacDiem();
        this.DACDIEM_ID = 0;
        this.getDiaChi();
      } finally {
        this.$root.showLoading(false);
      }
    },
    selectPhuong_OnChange: async function () {
      this.$root.showLoading(true);
      try {
        let t = await this.bindKhuPhoAp();
        this.KHU_ID = 0;
        this.PHO_ID = 0;
        this.AP_ID = 0;
        t = await this.bindDacDiem();
        this.DACDIEM_ID = 0;
        this.getDiaChi();
      } finally {
        this.$root.showLoading(false);
      }
    },
    selectKhuPhoAp_OnChange: async function () {
      this.$root.showLoading(true);
      try {
        let t = await this.bindDacDiem();
        this.DACDIEM_ID = 0;
        this.getDiaChi();
      } finally {
        this.$root.showLoading(false);
      }
    },
    getDiaChi: function () {
      let diaChi = "";
      let tinh = this.dsTinh.find((x) => x.TINH_ID == this.TINH_ID);
      if (tinh != null) diaChi = ", " + tinh.TENTINH;
      let quan = this.dsQuan.find((x) => x.QUAN_ID == this.QUAN_ID);
      if (quan != null) diaChi = ", " + quan.TEN_QUAN + diaChi;
      let phuong = this.dsPhuong.find((x) => x.PHUONG_ID == this.PHUONG_ID);
      if (phuong != null) diaChi = ", " + phuong.TEN_PHUONG + diaChi;
      if (this.PHO_ID > 0) {
        let pho = this.dsPho.find((x) => x.PHO_ID == this.PHO_ID);
        if (pho != null) diaChi = ", " + pho.TEN_PHO + diaChi;
      }
      if (this.AP_ID > 0) {
        let ap = this.dsAp.find((x) => x.PHO_ID == this.AP_ID);
        if (ap != null) diaChi = ", " + ap.TEN_PHO + diaChi;
      }
      if (this.KHU_ID > 0) {
        let khu = this.dsKhu.find((x) => x.PHO_ID == this.KHU_ID);
        if (khu != null) diaChi = ", " + ap.TEN_PHO + diaChi;
      }
      if (this.DACDIEM_ID > 0) {
        let dacdiem = this.dsDacDiem.find(
          (x) => x.DACDIEM_ID == this.DACDIEM_ID
        );
        if (dacdiem != null) diaChi = ", " + dacdiem.TEN_PHO + diaChi;
      }
      if (!(this.SONHA == undefined || this.SONHA == ""))
        diaChi = ", " + this.SONHA + diaChi;
      if (diaChi.length > 2) this.DIACHI = diaChi.substring(2, diaChi.length);
      else this.DIACHI = "";
    },
  },
};
</script>
