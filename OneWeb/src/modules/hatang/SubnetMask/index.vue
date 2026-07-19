<template src="./index.html"></template>

<script>
export default {
  data: function () {
    return {
      dataSource: [],
      DSLoaiIP: [],
      dsSelected: [],
      currentItem: {
        id: null,
        phanLoaiId: null,
        subnetMask: "",        
        ghiChu: "",
        hieuLuc: 1,
        ipStore: 1,
      },
      controls: {
        subnetMask: {
          enabled: false,
          invalid: false,
        }, 
        phanLoaiId: {
          enabled: false,
          invalid: false,
        },      
        chkHieuLuc: {
          enabled: false,
        },
        ipStore: {
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
          enabled: false,
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
    },    "currentItem.subnetMask": function (value) {    
      this.controls.subnetMask.invalid = !this.validateSubnetMaskFormat(value);
    },
    "currentItem.phanLoaiId": function (value) {     
      this.controls.phanLoaiId.invalid = value == null;
    },
    
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.DSLoaiIP = await this.apiListLoaiIP();
      this.dataSource = await this.apiListDanhSach();           
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    
    apiListDanhSach: async function () {
      let result = [];
      try {       
        const rs = await this.$root.context.post(`/web-ecms/danhmuc/subnetmask/danhsach`);      
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListLoaiIP: async function () {
      let result = [];
      try {       
        const rs = await this.$root.context.post(`/web-ecms/danhmuc/subnetmask/phanloai-ip`);      
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiSave: async function (args) {
      let result = false;  
      try {
        let rs = await this.$root.context.post(`/web-ecms/danhmuc/subnetmask/capnhat`, {
          id: args.id,
          subnetmask: args.subnetMask,          
          phanLoaiIP: args.phanLoaiId,
          ghiChu: args.ghiChu,
          hieuLuc: args.hieuLuc,
          ipStore: args.ipStore,
        });
        result = rs.error_code=='BSS-00000000';
      } catch(e) {
        this.$root.toastError(e.response.data.message_detail);
        result =false;
      }
      return result;
    },
    apiDelete: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post(`/web-ecms/danhmuc/subnetmask/xoa/${id}`);
        result = rs.error_code=='BSS-00000000';
      } catch(e) {
        this.$root.toastError(e.response.data.message_detail);
        result =false;
      }
      return result;
    },
    enableControls: function (args) {
      this.controls.subnetMask.enabled = args;    
      this.controls.phanLoaiId.enabled = args; 
      this.controls.chkHieuLuc.enabled = args;
      this.controls.txtGhiChu.enabled = args;
      this.controls.ipStore.enabled = args;

      this.buttons.btnSave.enabled = args;
      this.buttons.btnAddNew.enabled = !args;
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled;
      this.buttons.btnDelete.enabled = !args;

      if (this.currentItem.id > 0)
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
    },
    setCurrentItem: function (item) {
      this.currentItem.id = item.ID;
      this.currentItem.subnetMask = item.SUBNETMASK;      
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
      this.currentItem.ipStore = item.IP_STORE_CHECK;
      this.currentItem.phanLoaiId = item.IP_PHANLOAI_ID;
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        ID: null,
        SUBNETMASK: "",       
        GHI_CHU: "",
        HIEU_LUC: 1,
        IP_STORE_CHECK: 1,
        IP_PHANLOAI_ID: null
      });
    },    validateControls: function () {
      let msg = "";
      if (this.controls.subnetMask.invalid) {
        msg += "Subnet mask không đúng định dạng (VD: 255.255.255.0 hoặc /24).";
      }  
      if (this.controls.phanLoaiId.invalid) {
        msg += " Chưa chọn phân loại IP.";
      }  
      if(this.currentItem.subnetMask.length > 20){
        msg += " Subnet mask không được quá 20 ký tự.";
      }
        
          
      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },
    grdItems_RowSelected: async function (args) {
      this.setCurrentItem(args.data);
    },
    grdItems_RowDeselected: async function (args) {
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
        if(this.currentItem.id == null){
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
          this.$root.showLoading(false);        }
      }
    },
    validateSubnetMaskFormat: function (subnetMask) {
      if (!subnetMask) return false;

      // CIDR notation validation (e.g., /24, /16, /8)
      const cidrRegex = /^\/([0-9]|[1-2][0-9]|3[0-2])$/;
      
      // Dotted decimal notation validation (e.g., 255.255.255.0)
      const dottedDecimalRegex = /^((255|254|252|248|240|224|192|128|0)\.){3}(255|254|252|248|240|224|192|128|0)$/;
      
      // Check if it's valid CIDR notation
      if (cidrRegex.test(subnetMask)) {
        return true;
      }
      
      // Check if it's valid dotted decimal notation
      if (dottedDecimalRegex.test(subnetMask)) {
        // Additional check for valid subnet mask values
        const parts = subnetMask.split('.');
        const validMaskValues = [0, 128, 192, 224, 240, 248, 252, 254, 255];
        
        // Convert to binary and check if it's a valid subnet mask
        let binaryMask = '';
        for (let part of parts) {
          if (!validMaskValues.includes(parseInt(part))) {
            return false;
          }
          binaryMask += parseInt(part).toString(2).padStart(8, '0');
        }
        
        // Check if binary mask has consecutive 1s followed by consecutive 0s
        const pattern = /^1*0*$/;
        return pattern.test(binaryMask);
      }
      
      return false;
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
