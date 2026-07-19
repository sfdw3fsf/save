<template src="./index.template.html"></template>

<script>
export default {
  data: function () {
    return {
      dataItems: [],
      dataDelete: [],
      currentItem: {
        id: 0,
        ten: "",
        ghiChu: null,
        hieuLuc: 1,
      },
      lengthGrid: 0,
      controls: {
        txtName: {
          enabled: false,
          invalid: false,
        },
        checBoxHieuLuc: {
          enabled: false,
        },
        txtGhiChu: {
          enabled: false,
        },
      },
      buttons: {
        btnNhapMoi: {
          enabled: true,
        },
        btnGhiLai: {
          enabled: false,
        },
        btnHuyBo: {
          enabled: false,
        },
        btnSua: {
          enabled: false,
        },
        btnXoa: {
          enabled: false,
        },
      },
    };
  },
  computed: {
    gridItems: function () {
      return this.$refs.gridItems;
    },
  },
  watch: {
    "currentItem.id": function (value) {
      this.buttons.btnSua.enabled = value > 0;
      this.buttons.btnXoa.enabled = value > 0;
    },
    "currentItem.ten": function (value) {
      this.controls.txtName.invalid = value == "";
    }
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.dataItems = await this.getItems();
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    getHieuLucText: function (value) {
      return value == 1 ? "Hiệu lực" : "Không hiệu lực";
    },
    getColumnHieuLucTemplate: function () {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return this.$parent.$parent.$parent.getHieuLucText(this.data.HIEU_LUC);
              },
            },
          },
        };
      };
    },
    getItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/he-dieuhanh/get-items");
        result = rs.data;
        this.lengthGrid = result.length;
      } catch {}
      return result;
    },
    addItem: async function (args) {
      let result = 0;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/he-dieuhanh/add", {
          ten: args.ten?.trim(),
          ghiChu: args.ghiChu?.trim(),
          hieuLuc: args.hieuLuc,
        });
        result = rs.data.result;
      } catch(e) {
        if(!(e.response == null || e.response == undefined)){
          if(!(e.response.data == null || e.response.data == undefined)){
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    updateItem: async function (args) {
      let result = 0;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/he-dieuhanh/update", {
          id: args.id,
          ten: args.ten?.trim(),
          ghiChu: args.ghiChu?.trim(),
          hieuLuc: args.hieuLuc,
        });
        result = rs.data.result;
      } catch(e) {
        if(!(e.response == null || e.response == undefined)){
          if(!(e.response.data == null || e.response.data == undefined)){
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    deleteItem: async function (ids) {
      let result = 0;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/he-dieuhanh/delete", {
          ids: ids,
        });
        result = rs.data.result;
      } catch(e) {
        if(!(e.response == null || e.response == undefined)){
          if(!(e.response.data == null || e.response.data == undefined)){
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    enableControls: function (args) {
      this.controls.txtName.enabled = args;
      this.controls.checBoxHieuLuc.enabled = args;
      this.controls.txtGhiChu.enabled = args;

      this.buttons.btnGhiLai.enabled = args;
      this.buttons.btnNhapMoi.enabled = !args;
      this.buttons.btnHuyBo.enabled = this.buttons.btnGhiLai.enabled;
      if (this.currentItem.id > 0)
        this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled;
    },
    setCurrentItem: function (item) {
      this.currentItem.id = item.ID;
      this.currentItem.ten = item.TEN;
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        ID: 0,
        TEN: "",
        GHI_CHU: null,
        HIEU_LUC: 1,
      });
    },
    validateControls: function () {
      let msg = "";
      if (this.controls.txtName.invalid) {
        msg = "Tên hệ điều hành không được để trống.";
      }
      if (this.currentItem.ten.length > 100) {
        msg = "Tên hệ điều hành không được dài quá 100 ký tự.";
      }
      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },
    gridItems_RowSelected: async function (args) {
      let ds = this.$refs.gridItems.getSelectedRecords();
      this.dataDelete = ds.map(item => item.ID);
      if (args.data.length = 1) {
        this.setCurrentItem(args.data);
      }
      if (Number(args.row?.length || 0) == Number(this.lengthGrid)) {
        this.setCurrentItem(ds[0]);
      }
    },
    gridItems_RowDeselected: async function (args) {
      let items = this.gridItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0]);
        this.dataDelete = items.map(item => item.ID);
      } else {
        this.setCurrentItemDefault();
        this.dataDelete = [];
      }
    },
    btnGhiLai_Click: async function (args) {
      if (this.validateControls()) {
        let flag = await this.$confirm(
          "Có chắc bạn muốn lưu lại hệ điều hành đang chọn?",
          "Xác nhận thực hiện",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        )
          .then((s) => true)
          .catch((e) => {
            return false;
          });
        if (flag) {
          try {
            this.$root.showLoading(true);
            if (this.currentItem.id == 0) {
              let rs = await this.addItem(this.currentItem);
              if (rs === -1) {
                this.$root.toastError("Tên hệ điều hành không được trùng");
              } else if (rs === 0) {
                this.$root.toastError("Thêm hệ điều hành thất bại");
              }
              else {
                this.dataItems = await this.getItems();
                this.$root.toastSuccess("Thêm hệ điều hành thành công");
              }
              this.enableControls(!(rs > 0));
            } else {
              let rs = await this.updateItem(this.currentItem);
              if (rs === -1) {
                this.$root.toastError("Tên hệ điều hành không được trùng");
              } else if (rs === -2) {
                this.$root.toastError("Danh mục hệ điều hành không tồn tại");
              } else if (rs === 0) {
                this.$root.toastError("Cập nhật hệ điều hành thất bại");
              } else {
                this.dataItems = await this.getItems();
                this.$root.toastSuccess("Cập nhật hệ điều hành thành công");
              }
              this.enableControls(!(rs === 1));
            }
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    btnNhapMoi_Click: async function (args) {
      this.enableControls(true);
      this.setCurrentItemDefault();
    },
    btnHuyBo_Click: async function (args) {
      let items = this.gridItems.getSelectedRecords();
      let item = null;
      if (!(items == null || items.length == 0)) {
        if(this.currentItem.id == 0){
          item = items[0];
        }
        else {
          item = items.find(x => x.ID == this.currentItem.id);
        }
      }
      if (item != null) {
        this.setCurrentItem(item);
      } else {
        this.setCurrentItemDefault();
      }
      this.enableControls(false);
    },
    btnSua_Click: async function (args) {
      this.enableControls(true);
    },
    btnXoa_Click: async function (args) {
      let flag = await this.$confirm(
        "Có chắc bạn muốn xóa hệ điều hành đang chọn?",
        "Xác nhận thực hiện",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
          dangerouslyUseHTMLString: true,
        }
      )
        .then((s) => true)
        .catch((e) => {
          return false;
        });
      if (flag) {
        try {
          this.$root.showLoading(true);
          let rs = await this.deleteItem(this.dataDelete.join(','));
          if (rs === 1) {
            this.setCurrentItemDefault();
            this.dataItems = await this.getItems();
            this.enableControls(false);
            this.$root.toastSuccess("Xóa hệ điều hành thành công");
          } else if (rs === -2) {
            this.$root.toastError("Danh mục hệ điều hành không tồn tại");
          } 
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
  },
};
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type="checkbox"]:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type="checkbox"]:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}
</style>
