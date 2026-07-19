<template src="./index.template.html"></template>
<script>
import CaySoDoThietBi from "../CaySoDoThietBiV2/index.vue";
import FormThietBiPhuTro from "./ThietBiPhuTro";
import FormBaoDuong from "./BaoDuongDinhKyTBPhuTro";
import { L10n } from "@syncfusion/ej2-base";
L10n.load({
  "vi-VN": {
    dropdowns: {
      noRecordsTemplate: "Không có dữ liệu",
    },
  },
});
export default {
  components: {
    CaySoDoThietBi,
    FormThietBiPhuTro,
    FormBaoDuong,
  },
  props: {
    Tag: {
      type: String,
      default: "3+1+1",
    },
  },
  data: function () {
    return {
      currentItem: {},
      btns: {
        nhapMoi: { enabled: false },
        ghiLai: { enabled: false },
        huyBo: { enabled: false },
        xoa: { enabled: false },
      },
    };
  },
  computed: {
    showFormThietBiPhuTro: function () {
      let rs = false;
      if (!this.checkIsNull(this.currentItem.itemAttributes))
        rs = this.currentItem.itemAttributes.level == 3;
      return rs;
    },
  },
  mounted: async function () {
    this.$root.showLoading(true);
    try {
      await this.$refs.caySoDoThietBi.loadNodes();
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    caySoDoThietBi_nodeSelected: async function (args) {
      if (args.itemData.itemAttributes.level == 3) {
        await this.$refs.formThietBiPhuTro.bindData(args.itemData);
        this.currentItem = this.$refs.formThietBiPhuTro.currentItem;
      } else {
        this.currentItem = {};
        this.btns.nhapMoi.enabled = false;
        this.btns.ghiLai.enabled = false;
        this.btns.huyBo.enabled = false;
        this.btns.xoa.enabled = false;
      }
    },
    formThietBiPhuTro_BindingComplete: function (args) {
      this.btns.nhapMoi.enabled = args.nhapMoi;
      this.btns.ghiLai.enabled = args.ghiLai;
      this.btns.huyBo.enabled = args.huyBo;
      this.btns.xoa.enabled = args.xoa;
    },
    btnNhapMoi_Click: async function (args) {
      await this.$refs.formThietBiPhuTro.createData(args);
    },
    btnGhiLai_Click: async function (args) {
      await this.$refs.formThietBiPhuTro.saveData(args);
    },
    btnHuyBo_Click: async function (args) {
      await this.$refs.formThietBiPhuTro.cancelForm(args);
      this.btns.ghiLai.enabled = false;
      this.btns.huyBo.enabled = false;
    },
    btnXoa_Click: async function (args) {
      await this.$refs.formThietBiPhuTro.deleteData(args);
    },
    checkIsNull: function (value) {
      return value == undefined || value == null;
    },
    checkIsEmpty: function (value) {
      let rs = this.checkIsNull(value);
      if (!rs) {
        rs = value.toString().trim() == "";
      }
      return rs;
    },
    getTTVTvsTO: function (args) {
      let result = null;
      if (!(args == null || args == undefined)) {
        let items = this.$refs.caySoDoThietBi.getNodes(
          args.itemType,
          args.itemID
        );
        if (!(items == null || items == undefined)) {
          if (items.length > 0) {
            let item = items[0];
            let parents = this.$refs.caySoDoThietBi.getParents(item.parentID);
            if (parents.length > 0) {
              let TTVT = parents.find((x) => x.parentID == null);
              if (TTVT != null) {
                let TO = parents.find((x) => x.parentID == TTVT.id);
                if (TO != null) {
                  result = {
                    ttvtID: TTVT.attributes.id,
                    toID: TO.attributes.id,
                  };
                }
              }
            }
          }
        }
      }
      return result;
    },
  },
};
</script>
