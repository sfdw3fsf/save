<template src="./index.html"></template>

<script>

export default {
  props: {
    modalItemID: 0
  },
  data: function () {
    return {
      currentItem: {
        id: 0,
        ten: '',
        idcId: 0,
        tenIDC: '',
        thietBiId: 0,
        tenThietBi: '',
        vrfId: 0,
        tenVRF: '',
        zoneId: 0,
        tenZone: '',
        ghiChu: '',
        hieuLuc: 1,
      },
      dsSelected: [],
      vlanItemList: [],
      haTangMangItemList: [],
      subnetItemList: [],
      lichSuItemList: [],

      idcSelectionList: [],
      zoneSelectionList: [],
      vrfSelectionList: [],

      controls: {
        txtVlan: {
          enabled: false,
          invalid: false,
        },
        chkHieuLuc: {
          enabled: false,
        },
        selectIDC: {
          enabled: false,
          invalid: false,
        },
        selectZone: {
          enabled: false,
          invalid: false
        },
        selectVRF: {
          enabled: false,
          invalid: false
        },
        txtThietBi: {
          enabled: false,
          invalid: false
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
    grdItems_haTangMang: function () {
      return this.$refs.grdItems_haTangMang;
    },
    grdItems_subnet: function () {
      return this.$refs.grdItems_subnet;
    },
    grdItems_history: function () {
      return this.$refs.grdItems_history;
    },
  },
  watch: {
    "currentItem.id": function (value) {
      this.buttons.btnEdit.enabled = value > 0;
      this.buttons.btnDelete.enabled = value > 0;
    },
    "currentItem.ten": function (value) {
      this.controls.txtVlan.invalid = value == "";
    },
    "currentItem.idcId": function (value) {
      this.controls.selectIDC.invalid = value == 0;
    },
    "currentItem.zoneId": function (value) {
      this.controls.selectZone.invalid = value == 0;
    },
    "currentItem.vrfId": function (value) {
      this.controls.selectVRF.invalid = value == 0;
    },
    "currentItem.tenThietBi": function (value) {
      this.controls.txtThietBi.invalid = value == "";
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.vlanItemList = await this.getVlanItems();
      this.idcSelectionList = await this.getIdcItemsCat();

      if (this.modalItemID > 0) {
        let item = this.vlanItemList.find(x => x.ID == this.modalItemID);
        if (item != null) {
          this.haTangMangItemList = await this.getHaTangItems(item.ID);
          this.subnetItemList = await this.getSubnetItems(item.ID);
          this.lichSuItemList = await this.getLichSuItems(item.ID);
          this.setCurrentItem(item);
        }
        this.$refs.grdItems.selectRow(item);
      }
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
    getVlanItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/vlan/lay-danhsach");
        result = rs.data;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    getLichSuItems: async function (vlanID) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/vlan/dm_lienquan", {
          id: vlanID,
          danhmuc: "VLAN_LICHSU"
        });
        result = rs.data;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    getHaTangItems: async function (vlanID) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/vlan/dm_lienquan", {
          id: vlanID,
          danhmuc: "VLAN_HATANGMANG"
        });
        result = rs.data;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    getSubnetItems: async function (vlanID) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/vlan/dm_lienquan", {
          id: vlanID,
          danhmuc: "VLAN_SUBNET"
        });
        result = rs.data;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    getIdcItemsCat: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "IDC",
          thamSo1: null,
          thamSo2: null
        });
        result = rs.data;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    getVrfItemsCat: async function (idcId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "VRF-IDC",
          thamSo1: idcId,
          thamSo2: null
        });
        result = rs.data;

      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    getZoneItemsCat: async function (vrfId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "HATANG_ZONE",
          thamSo1: vrfId,
          thamSo2: null
        });
        result = rs.data;

      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    getThietBiItemsCat: async function (vrfId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "HATANG_THIETBI",
          thamSo1: vrfId,
          thamSo2: null
        });
        result = rs.data;

      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    updateItem: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/vlan/capnhat", {
          id: args.id == 0 ? null : args.id,
          ten: args.ten,
          idc_id: args.idcId,
          thietbi_id: args.thietBiId,
          vrf_id: args.vrfId,
          zone_id: args.zoneId,
          ghi_chu: args.ghiChu,
          hieu_luc: args.hieuLuc
        });
        result = rs.data.result;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    deleteItem: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/vlan/xoa", { id: id });
        result = rs.data.result;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    enableControls: function (args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args;
        }
      }

      this.buttons.btnSave.enabled = args;
      this.buttons.btnAddNew.enabled = !args;
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled;
      this.buttons.btnDelete.enabled = !args;

      if (this.currentItem.id > 0)
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
    },
    setCurrentItem: async function (item) {
      this.currentItem.id = item.ID;
      this.currentItem.ten = item.TEN;
      this.currentItem.idcId = item.IDC_ID;
      this.currentItem.tenIDC = item.TEN_IDC;
      this.currentItem.thietBiId = item.THIETBI_ID;
      this.currentItem.tenThietBi = item.TEN_THIETBI;
      this.currentItem.vrfId = item.VRF_ID;
      this.currentItem.tenVRF = item.TEN_VRF;
      this.currentItem.zoneId = item.ZONE_ID;
      this.currentItem.tenZone = item.TEN_ZONE;
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
      if (this.currentItem.vrfId != null) {
        this.vrfSelectionList = await this.getVrfItemsCat(this.currentItem.idcId);
      }
      else {
        this.vrfSelectionList = [];
      }
      if (this.currentItem.zoneId != null) {
        this.zoneSelectionList = await this.getZoneItemsCat(this.currentItem.vrfId);
      }
      else {
        this.zoneSelectionList = [];
      }
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        ID: 0,
        TEN: '',
        IDC_ID: 0,
        TEN_IDC: '',
        THIETBI_ID: 0,
        TEN_THIETBI: '',
        VRF_ID: 0,
        TEN_VRF: '',
        ZONE_ID: 0,
        TEN_ZONE: '',
        GHI_CHU: '',
        HIEU_LUC: 1,
      });
    },
    validateControls: function () {
      let msg = "";
      if (this.controls.txtVlan.invalid) {
        msg = "Tên không được để trống.";
      }
      if (this.controls.selectIDC.invalid) {
        if (msg != "") msg += "\n\r";
        msg += "IDC không được để trống.";
      }
      if (this.controls.selectZone.invalid) {
        if (msg != "") msg += "\n\r";
        msg += "Zone không được để trống.";
      }
      if (this.controls.selectVRF.invalid) {
        if (msg != "") msg += "\n\r";
        msg += "VRF không được để trống.";
      }
      if (this.controls.txtThietBi.invalid) {
        if (msg != "") msg += "\n\r";
        msg += "Thiết bị không được để trống.";
      }
      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },
    grdItems_RowSelected: async function (args) {
      try {
        this.$root.showLoading(true);
        this.setCurrentItem(args.data);
        this.haTangMangItemList = await this.getHaTangItems(args.data.ID);
        this.subnetItemList = await this.getSubnetItems(args.data.ID);
        this.lichSuItemList = await this.getLichSuItems(args.data.ID);
        if (args.data.VRF_ID != null) {
          this.vrfSelectionList = await this.getVrfItemsCat(args.data.IDC_ID);
        }
        else {
          this.vrfSelectionList = [];
        }
        if (args.data.ZONE_ID != null) {
          this.zoneSelectionList = await this.getZoneItemsCat(args.data.VRF_ID);
        }
        else {
          this.zoneSelectionList = [];
        }
      } finally {
        this.$root.showLoading(false);
      }

    },
    grdItems_RowDeselected: async function () {
      let items = this.grdItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        try {
          this.$root.showLoading(true);
          this.setCurrentItem(items[0]);
          this.haTangMangItemList = await this.getHaTangItems(items[0].ID);
          this.subnetItemList = await this.getSubnetItems(items[0].ID);
          this.lichSuItemList = await this.getLichSuItems(items[0].ID);
          if (items[0].VRF_ID != null) {
            this.vrfSelectionList = await this.getVrfItemsCat(items[0].IDC_ID);
          }
          else {
            this.vrfSelectionList = [];
          }

          if (items[0].ZONE_ID != null) {
            this.zoneSelectionList = await this.getZoneItemsCat(items[0].VRF_ID);
          }
          else {
            this.zoneSelectionList = [];
          }
        } finally {
          this.$root.showLoading(false);
        }
      } else {
        this.setCurrentItemDefault();
        this.lichSuItemList = [];
        this.haTangMangItemList = [];
        this.subnetItemList = [];
      }
    },
    onAddNew: async function () {
      this.setCurrentItemDefault();
      this.enableControls(true);
    },
    onSave: async function () {
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
            let rs = await this.updateItem(this.currentItem);
            if (rs) {
              if (this.currentItem.id === 0) {
                this.$root.toastSuccess("Thêm VLAN thành công");
              }
              else {
                this.$root.toastSuccess("Cập nhật VLAN thành công");
              }
              this.vlanItemList = await this.getVlanItems();
            }
            else {
              if (this.currentItem.id === 0) {
                this.$root.toastError("Thêm VLAN thất bại");
              }
              else {
                this.$root.toastError("Cập nhật VLAN thất bại");
              }
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
        if (this.currentItem.id == 0) {
          item = items[0];
        }
        else {
          item = items.find(x => x.ID == this.currentItem.id);
        }
      }
      if (item != null) {
        console.log('item', item);
        this.setCurrentItem(item);
        this.haTangMangItemList = await this.getHaTangItems(item.ID);
        this.subnetItemList = await this.getSubnetItems(item.ID);
        this.lichSuItemList = await this.getLichSuItems(item.ID);
      } else {
        this.setCurrentItemDefault();
        this.lichSuItemList = [];
        this.haTangMangItemList = [];
        this.subnetItemList = [];
      }
      this.enableControls(false);
    },
    onEdit: async function (args) {
      this.enableControls(true);
    },
    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
    },
    onDelete: async function (args) {
      if (this.dsSelected.length <= 0) {
        this.$toast.error('Chưa chọn danh mục cần xoá.')
        return
      }
      let items = this.grdItems.getSelectedRecords();
      let msg = `Bạn có muốn xóa dữ liệu không?`
      let flag = await this.$confirm(msg, "Xác nhận",
        {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Không đồng ý",
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
          // this.$root.showLoading(true);
          // let rs = await this.deleteItem(this.currentItem.id);
          // if (rs) {
          //   this.setCurrentItemDefault();
          //   this.vlanItemList = await this.getVlanItems();
          //   this.enableControls(false);
          //   this.$root.toastSuccess("Xóa VLAN thành công");
          // } else {
          //   this.$root.toastError("Xóa VLAN thất bại");
          // }
          this.$root.showLoading(true)
          let promises = []
          for (let item of this.dsSelected) {
            promises.push(await this.deleteItem(item.ID))
          }
          let res = await Promise.all(promises)
          let ok = res.filter((x) => x === true)

          if (ok.length > 0) {
            this.$toast.success(`Đã xoá thành công ${ok.length} / ${res.length} mục`)
          } else {
            this.$toast.error(`Đã phát sinh lỗi khi xoá danh mục.`)
          }
          this.vlanItemList = await this.getVlanItems()
          this.enableControls(false)
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    onChangeIDC: async function () {
      if (this.currentItem.idcId != 0) {
        try {
          this.$root.showLoading(true);
          this.vrfSelectionList = await this.getVrfItemsCat(this.currentItem.idcId);
          this.currentItem.vrfId = 0;
          this.currentItem.zoneId = 0;
          this.currentItem.thietBiId = 0;

        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    onChangeVrf: async function () {
      if (this.currentItem.vrfId != 0) {
        try {
          this.$root.showLoading(true);
          this.currentItem.zoneId = 0;
          this.zoneSelectionList = await this.getZoneItemsCat(this.currentItem.vrfId);
          let rs = await this.getThietBiItemsCat(this.currentItem.vrfId);
          if (rs[0] != null) {
            this.currentItem.thietBiId = rs[0].ID;
            this.currentItem.tenThietBi = rs[0].TEN;
          } else {
            this.currentItem.thietBiId = 0;
            this.currentItem.tenThietBi = '';
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    // hideModal: function() {
    //   this.$refs["popupHaTangMangDetail"].hide();
    // },
    // handleShowModal: function() {
    //   this.$refs["popupHaTangMangDetail"].show();
    // },
    // haTangMangDetail: async function(idx) {
    //   this.haTangMangItem = await this.apiHaTangMangDetail(this.haTangMangItemList[idx].HATANGMANG_ID);
    //   if (!this.haTangMangItem.AVATAR.includes("data:image")) {
    //     this.haTangMangItem.AVATAR = "data:image/*;base64," + this.haTangMangItem.AVATAR;
    //   }
    //   console.log('this.haTangMangItem.AVATAR', this.haTangMangItem.AVATAR);
    //   this.handleShowModal();

    // }
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
</style>
