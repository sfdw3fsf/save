<template src="./index.template.html"></template>
<script>
export default {
  props: {
    thongTinThueBao: {},
    thongTinGianDo: [],
    thongTinBaoHong: []
  },
  mounted() {
    this.$nextTick(() => {
      setTimeout(() => {
        this.$emit("domRendered");
      }, 1000);
    });
  },
  computed: {},
  methods: {
    getSplitters: function () {
      let rs = [];
      if (this.thongTinGianDo != null) {
        for (let i = 0; i < this.thongTinGianDo.length; i++) {
          const gianDo = this.thongTinGianDo[i];
          if (gianDo.TT_SP1 != null || gianDo.TT_SP2 != null) {
            let ck = false;
            for (let j = 0; j < rs.length; j++) {
              const item = rs[j];
              if (gianDo.TT_SP1 != null) {
                if (item.SP1 == gianDo.TT_SP1) {
                  ck = true;
                  break;
                }
              } else if (gianDo.TT_SP2 != null) {
                if (item.SP2 == gianDo.TT_SP2) {
                  ck = true;
                  break;
                }
              }
            }
            if (!ck)
              rs.push({
                SP1: gianDo.TT_SP1,
                SP2: gianDo.TT_SP2,
                SP1_HTML: this.getSplitter1Row(gianDo.TT_SP1),
                SP2_HTML: this.getSplitter2Row(gianDo.TT_SP2),
              });
          }
        }
      }
      return rs;
    },
    getSoMayText: function () {
      let dvvt_id = this.thongTinThueBao.DICHVUVT_ID;
      let loaitb_id = this.thongTinThueBao.LOAITB_ID;
      let text = "Số máy:";
      if (!(dvvt_id == "1" || dvvt_id == "11")) {
        if (loaitb_id != "61") text = "Mã DVụ:";
      }
      return text;
    },
    getSoMayValue: function () {
      let dvvt_id = this.thongTinThueBao.DICHVUVT_ID;
      let loaitb_id = this.thongTinThueBao.LOAITB_ID;
      let text = this.thongTinThueBao.MA_LT;
      if (dvvt_id == "4") {
        if (loaitb_id == "61") text = this.thongTinThueBao.MA_TB;
      } else {
        text = this.thongTinThueBao.MA_TB;
      }
      return text;
    },
    getSoThueBaoText: function () {
      let tbcungcap = this.thongTinThueBao.TB_CUNGCAP;
      if (tbcungcap != undefined) {
        let tbs = tbcungcap.split(",");
        if (tbs.length > 0) {
          let txt = tbs[0].split(":");
          if (txt.length > 0) return txt[0];
        }
      }
      return "";
    },
    getSoThueBaoValue: function () {
      let tbcungcap = this.thongTinThueBao.TB_CUNGCAP;
      if (tbcungcap != undefined) {
        let tbs = tbcungcap.split(",");
        if (tbs.length > 0) {
          let txt = tbs[0].split(":");
          if (txt.length > 1) {
            return txt[1];
          }
        }
      }
      return "";
    },
    getSoThueBao2Text: function () {
      let tbcungcap = this.thongTinThueBao.TB_CUNGCAP;
      if (tbcungcap != undefined) {
        let tbs = tbcungcap.split(",");
        if (tbs.length > 1) {
          let txt = tbs[1].split(":");
          if (txt.length > 0) return txt[0];
        }
      }
      return "";
    },
    getSoThueBao2Value: function () {
      let tbcungcap = this.thongTinThueBao.TB_CUNGCAP;
      if (tbcungcap != undefined) {
        let tbs = tbcungcap.split(",");
        if (tbs.length > 1) {
          let txt = tbs[1].split(":");
          if (txt.length > 1) {
            return txt[1];
          }
        }
      }
      return "";
    },
    getSoThueBaoTDText: function () {
      let dvvt_id = this.thongTinThueBao.DICHVUVT_ID;
      let loaitb_id = this.thongTinThueBao.LOAITB_ID;
      let text = "NE:";
      if (dvvt_id == "1") {
        text = "NE:";
      } else if (loaitb_id == "11") {
        text = "TB/TD:";
      } else {
        text = "";
      }
      return text;
    },
    getSoThueBaoTDValue: function () {
      let dvvt_id = this.thongTinThueBao.DICHVUVT_ID;
      let loaitb_id = this.thongTinThueBao.LOAITB_ID;
      let text = "NE:";
      if (dvvt_id == "1") {
        text = this.thongTinThueBao.SONE;
      } else if (loaitb_id == "11") {
        textt = this.thongTinThueBao.TD_TB;
      } else {
        text = "";
      }
      return text;
    },
    getThueBaoCungCap: function () {
      let tbcungcap = this.thongTinThueBao.TB_CUNGCAP;
      if (tbcungcap != undefined) {
        let dsTbCungCap = "";
        let tbs = tbcungcap.split(",");
        for (let i = 0; i < tbs.length; i++) {
          let txt = tbs[i].split(":");
          if (txt.length > 1) {
            if (i > 0) dsTbCungCap += ", ";
            dsTbCungCap += "[" + txt[0] + "] " + txt[1];
          }
        }
        return dsTbCungCap != "" ? dsTbCungCap : "(Không có)";
      }
      return "";
    },
    getSplitter1Row: function (value) {
      if (value != null) return "<td colspan='8'>" + value + "</td>";
      return "";
    },
    getSplitter2Row: function (value) {
      if (value != null) {
        let t1 = "SPLITTER:";
        let t2 = "Thông tin Splitter ngọn: ";
        if (value.startsWith("UGAIN:")) {
          t1 = "UGAIN:";
          t2 = "Thông tin Ugain: ";
        }
        return (
          "<td colspan='8'><span class='text-bold'>" +
          t2 +
          "</span>" +
          value.replace(t1, "") +
          "</td>"
        );
      }
      return "";
    },
    getDsCap: function (splitter) {
      let rs = [];
      if (this.thongTinGianDo != null) {
        for (let i = 0; i < this.thongTinGianDo.length; i++) {
          const gianDo = this.thongTinGianDo[i];
          if (gianDo.TT_SP1 != null || gianDo.TT_SP2 != null) {
            let ck = false;
            if (gianDo.TT_SP1 != null) {
              if (splitter.SP1 == gianDo.TT_SP1) {
                ck = true;
              }
            } else if (gianDo.TT_SP2 != null) {
              if (splitter.SP2 == gianDo.TT_SP2) {
                ck = true;
              }
            }
            if (ck) rs.push(gianDo);
          }
        }
      }
      return rs;
    },
  },
};
</script>
