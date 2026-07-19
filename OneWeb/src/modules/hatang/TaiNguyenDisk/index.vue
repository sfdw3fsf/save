<template src="./index.html"></template>

<script>

export default {
  props: {
    p_auto: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data: function () {
    return {
      nguon_yeucau: 2,
      current_item: {
        id: 0,
        ten: '',
        hieu_luc: 1,
        loai_disk: 0,
        dung_luong: 0
      },
      item_list: [],
      select_disk:[],
      controls: {
        text: {
          enabled: true,
          invalid: false,
        },
        selection: {
          enabled: true,
          invalid: false,
        },
        checkbox: {
          enabled: true
        },
        btn: {
          enabled: true
        },
        dung_luong: {
          enabled: true
        }
      },
      buttons: {
        btnAddNew: {
          enabled: true,
        },
        btnSave: {
          enabled: false,
        },
        btnCancel: {
          enabled: false,
        },
        btnEdit: {
          enabled: false,
        },
        btnDelete: {
          enabled: false,
        }
      },
    };
  },
  computed: {
    grdItems: function () {
      return this.$refs.grdItems;
    }
  },
  watch: {
    "current_item.id": function (value) {
      this.buttons.btnEdit.enabled = value;
      this.buttons.btnDelete.enabled = value;
    }
  },
  created: async function () {
    try {
      this.item_list = [];
      this.$root.showLoading(true);
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    async setPropsData() {
      this.select_disk = [
        { ID: 1, TEN: "SSD" },
        { ID: 2, TEN: "HDD" }
      ]
      this.nguon_yeucau = this.p_auto ? 1 : 2
    },

    apiListMayao: async function (id) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/tainguyen-backup/may-ao", { id: id });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListLoaiBackup: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/loai-backup");
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiSave: async function (args) {
      let result;
      try {
        result = await this.$root.context.post("/web-ecms/hatang/tainguyen-backup/create-or-update", {
          id: Number(args.id),
          ten: args.ten,
          hieu_luc: Number(args.hieu_luc),
          loai_disk: Number(args.loai_disk),
          dung_luong: Number(args.dung_luong)
        });
      } catch (e) {
        result = false;
      }
      return result;
    },
    enableControls: function (args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key) && key != 'dung_luong') {
          this.controls[key].enabled = args;
        }
      }
      if (this.current_item.id > 0) {
        this.controls.dung_luong.enabled = false;
      } else {
        this.controls.dung_luong.enabled = true;
      }

      this.buttons.btnSave.enabled = args;
      this.buttons.btnAddNew.enabled = args;
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled;
      this.buttons.btnDelete.enabled = args;

      if (this.current_item.id > 0)
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
    },
    setCurrentItem: async function (item) {
      this.current_item.id = item.id;
      this.current_item.ten = item.ten;
      this.current_item.hieu_luc = item.hieu_luc;
      this.current_item.loai_disk = item.loailuutru;
      this.current_item.dung_luong = item.dungluong;
    },
    setCurrentItemDefault: async function () {
      this.setCurrentItem({
        id: 0,
        ten: '',
        hieu_luc: 1,
        loai_disk: 0,
        dung_luong: 0
      });
    },
    validateControls: function () {
      let flg = true;
      if (this.current_item.ten == '' || this.current_item.ten == null) {
        this.$root.toastError('Tên disk không được để trống.');
        flg = false;
      }
      if (this.current_item.dung_luong > 0) {
        this.$root.toastError('Giá trị dung lượng không được < 0.');
        flg = false;
      }
      return flg;
    },
    // grdItems_RowSelected: async function (args) {
    //   try {
    //     this.$root.showLoading(true);
    //     if (this.buttons.btnSave.enabled && this.nguon_yeucau == 2) return;
    //     const data = args.data.length ? args.data[0] : args.data 
    //     this.setCurrentItem(data);
    //   } finally {
    //     this.$root.showLoading(false);
    //   }
    // },
    // grdItems_RowDeselected: async function () {
    //   if (this.buttons.btnSave.enabled && this.nguon_yeucau == 2) return;
    //   let items = this.grdItems.getSelectedRecords();
    //   if (!(items == null || items.length == 0)) {
    //     try {
    //       this.$root.showLoading(true);
    //       this.setCurrentItem(items[0]);
    //     } finally {
    //       this.$root.showLoading(false);
    //     }
    //   } else {
    //     this.setCurrentItemDefault();
    //   }
    // },
    onSave: async function () {
      if (this.nguon_yeucau == 1) {
        this.$emit('xacNhanDisk', this.item_list)
        this.item_list = []
      } else {
        
      }
    },
    onAddNew: async function () {
      // Push 1 bản sao
      this.item_list.push(JSON.parse(JSON.stringify(this.current_item)));

      if (this.item_list.length) {
        this.buttons.btnSave.enabled = true
      }
      await this.setCurrentItemDefault();
    },
    onDelete() {
      const selected = this.grdItems.getSelectedRecords();

      if (!selected || selected.length === 0) {
        this.$root.toastWarning('Vui lòng chọn ít nhất một dòng để xoá.');
        return;
      }

      // Lấy danh sách ID hoặc item cần xoá
      const selectedIds = selected.map(item => item.ten); // hoặc item.key tuỳ cấu trúc

      // Lọc lại danh sách item_list, giữ lại những item không bị chọn
      this.item_list = this.item_list.filter(item => !selectedIds.includes(item.ten));

      this.$root.toastSuccess('Đã xoá khỏi danh sách tạm thời.');
    },
    // grdItems_RowSelected: async function (args) {
    //   try {
    //     this.$root.showLoading(true);
    //     if (this.buttons.btnSave.enabled) return;
    //   } finally {
    //     this.$root.showLoading(false);
    //   }
    // },
    // grdItems_RowDeselected: async function () {
    //   if (this.buttons.btnSave.enabled && this.nguon_yeucau == 2) return;
    //   let items = this.grdItems.getSelectedRecords();
    //   if (!(items == null || items.length == 0)) {
    //     try {
    //       this.$root.showLoading(true);
    //       this.setCurrentItem(items[0]);
    //     } finally {
    //       this.$root.showLoading(false);
    //     }
    //   } else {
    //     this.setCurrentItemDefault();
    //   }
    // },
  },
};
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type="checkbox"]:checked:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type="checkbox"]:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
}

.e-grid * {
  font-size: 13px !important;
}

.e-grid.e-default.e-bothlines .e-headercell,
.e-grid.e-default.e-bothlines .e-detailheadercell {
  border-width: 1px 1px 0px 0px !important;
}

.info-row .key.w180 {
  width: 180px
}

.info-row .key.w50 {
  width: 50px
}

@media (min-width: 1200px) {
  .modal-xl {
    max-width: 80% !important;
  }
}

.e-toolbar .e-tbar-btn-text,
.e-toolbar .e-toolbar-items .e-toolbar-item .e-tbar-btn-text {
  display: none;
}
</style>
