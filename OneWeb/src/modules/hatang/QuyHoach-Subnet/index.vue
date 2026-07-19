<template src="./index.html"></template>

<script>
export default {
  data: function () {
    return {
      dataSource: [],
      dsSelected: [],
      currentItem: {
        id: 0,
        ten: "",
        ghiChu: null,
        hieuLuc: 1,
      },
      controls: {
        txtTen: {
          enabled: false,
          invalid: false,
        },
        chkHieuLuc: {
          enabled: false,
        },
        txtGhiChu: {
          enabled: false,
        },
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
          enabled: true,
        },
      },
    };
  },
  computed: {
    grdItems: function () {
      return this.$refs.grdItems;
    },
  },
  watch: {
    "currentItem.id": function (value) {
      this.buttons.btnEdit.enabled = value > 0;
      this.buttons.btnDelete.enabled = value > 0;
    },
    "currentItem.ten": function (value) {
      this.controls.txtTen.invalid = value == "";
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.dataSource = await this.apiListDanhSach();
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
    apiListDanhSach: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/quy-hoach-subnet/danh-sach");
        result = rs.data;
      } catch(e) {
        const errorMessage = error.response?.data?.message_detail || error.message_detail || error.message || 'Có lỗi xảy ra khi xóa phân loại hạ tầng'
        this.$toast.error(errorMessage)
      }
      return result;
    },
    apiSave: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/quy-hoach-subnet/cap-nhat", {
          id: args.id,
          ten: args.ten?.trim(),
          ghiChu: args.ghiChu?.trim(),
          hieuLuc: args.hieuLuc,
        });
        result = rs.error_code=='BSS-00000000';
      } catch(e) {
            this.$root.toastError(e.response.data.message);
            result =false;
      }
      return result;
    },
    apiDelete: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/quy-hoach-subnet/xoa", {
          id: id,
        });
        result = rs.error_code=='BSS-00000000';
      } catch(e) {
        this.$root.toastError(e.response.data.message_detail);
        result =false;
      }
      return result;
    },
    enableControls: function (args) {
      this.controls.txtTen.enabled = args;
      this.controls.chkHieuLuc.enabled = args;
      this.controls.txtGhiChu.enabled = args;

      this.buttons.btnSave.enabled = args;
      this.buttons.btnAddNew.enabled = !args;
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled;
      this.buttons.btnDelete.enabled = !args;

      if (this.currentItem.id > 0)
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
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
        GHI_CHU: "",
        HIEU_LUC: 1,
      });
    },
    validateControls: function () {
      let msg = "";
      if (this.controls.txtTen.invalid) {
        msg = "Tên không được để trống.";
      }
      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },
    grdItems_RowSelected: async function (args) {
      if (this.buttons.btnSave.enabled) {
        return;
      }
      this.setCurrentItem(args.data);
    },
    grdItems_RowDeselected: async function (args) {
      if (this.buttons.btnSave.enabled) {
        return;
      }
      let items = this.grdItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0]);
      } else {
        this.setCurrentItemDefault();
      }
    },
    onAddNew: async function (args) {
      this.enableControls(true);
      this.setCurrentItemDefault();
    },
    onSave: async function (args) {
      if (this.validateControls()) {
        let flag = await this.$confirm(
          "Bạn có muốn lưu thông tin này không ?",
          "Xác nhận",
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
            let rs = await this.apiSave(this.currentItem);
            if (rs) {
                this.dataSource = await this.apiListDanhSach();
                this.$root.toastSuccess("Cập nhật thông tin thành công ");
            }
            this.enableControls(!rs);
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },

    onCancel: async function (args) {
      let items = this.grdItems.getSelectedRecords();
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
    onEdit: async function (args) {
      this.enableControls(true);
    },
    onDelete: async function (args) {
      //this.dsSelected.length
      let items = this.grdItems.getSelectedRecords();
      if (items.length > 0) {
        let msg=`Bạn có muốn xóa ${items.length} mẫu tin đã chọn không?`
        let flag = await this.$confirm(msg,"Xác nhận",
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
            let  isSuccess=false;
            for(const item of items){
              isSuccess = await this.apiDelete(item.ID);
              if (!isSuccess){ break;}
            }
            if (isSuccess) {
              this.setCurrentItemDefault();
              this.dataSource = await this.apiListDanhSach();
              this.enableControls(false);
              this.$root.toastSuccess("Xóa thông tin thành công");
            }
          } finally {
            this.$root.showLoading(false);
          }
        } else {
          let msg=`Vui lòng chọn mẫu tin cần xoá`
          let flag = await this.$confirm(msg,"Xác nhận",
            {
              confirmButtonText: "Tiếp tục",
              type: "warning",
              dangerouslyUseHTMLString: true,
            }
          )
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
