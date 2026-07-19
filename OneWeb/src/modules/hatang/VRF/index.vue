<template src="./index.html"></template>

<script>

export default {
  props: {
    modalItemID: 0
  },
  data: function () {
    return {
      userInfo: {
        nhanVienId: 0,
        mayNhanVien: '',
        nhanhVienTen: '',
        IP: '',
        phanVungID: 0,
        donViId: 0,
        donViTen: ''
      },
      currentItem: {
        id: 0,
        ten: '',
        hieuLuc: 1,
        ghiChu: '',
        idcID: 0,
        rd: '',
        rtExport: '',
        rtImport: '',
        asn: '',
        thietBiMangId: 0,
        nhanVienId: 0,
        mayCapNhap: '',
        nguoiCapNhap: '',
        ipCapNhap: ''
      },
      haTangMangItem: {
        ID: 0,
        TEN: '',
        HIEU_LUC: 0,
        MA_HATANG: '',
        AVATAR: null,
        IDC_ID: 0,
        IDC_NAME: '',
        HANGTANG_CONGNGHE_ID: 0,
        HANGTANG_CONGNGHE_NAME: ''
      },
      vrfItemList: [],
      historyItemList: [],
      haTangMangItemList: [],
      zoneItemList: [],
      VLANItemList: [],
      subnetItemList: [],
      IDCSelectionList: [],
      thietBiMangSelectionList: [],
      controls: {
        txtVRF: {
          enabled: false,
          invalid: false,
        },
        chkHieuLuc: {
          enabled: false,
        },
        selecIDC: {
          enabled: false,
          invalid: false,
        },
        selecThietBiMang: {
          enabled: false,
          invalid: false
        },
        txtRD: {
          enabled: false,
        },
        txtRTExport: {
          enabled: false,
        },
        txtRTImport: {
          enabled: false,
        },
        txtASN: {
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
    grdItems_haTangMang: function () {
      return this.$refs.grdItems_haTangMang;
    }
  },
  watch: {
    "currentItem.id": function (value) {
      this.buttons.btnEdit.enabled = value > 0;
      this.buttons.btnDelete.enabled = value > 0;
    },
    "currentItem.ten": function (value) {
      this.controls.txtVRF.invalid = value == "";
    },
    "currentItem.idcID": function (value) {
      this.controls.selecIDC.invalid = value > 0;
    },
    "currentItem.thietBiMangId": function (value) {
      this.controls.selecThietBiMang.invalid = value > 0;
    }
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.vrfItemList = await this.apiListItems();
      this.IDCSelectionList = await this.apiListIDC();
      // init current user Info
      this.userInfo.nhanVienId = await this.$root.token.getNhanVienID();
      this.userInfo.nhanhVienTen = await this.$root.context.user.getTenNhanVien();
      this.userInfo.mayNhanVien = await this.$root.token.getMachine();
      this.userInfo.IP = await this.$root.token.getIP();
      this.userInfo.phanVungID = await this.$root.token.getPhanVungID();
      this.userInfo.donViId = await this.$root.token.getDonViID();
      this.userInfo.donViTen = await this.$root.context.user.getTenDonVi();
      if (this.modalItemID > 0) {
        let item = this.vrfItemList.find(x => x.ID == this.modalItemID);
        if (item != null) {
          this.setCurrentItem(item);
          this.historyItemList = await this.apiListHistory(item.ID);
          this.haTangMangItemList = await this.apiListVRFHaTangMang(item.ID);
          this.zoneItemList = await this.apiListVRFZone(item.ID);
          this.VLANItemList = await this.apiListVRFVLAN(item.ID);
          this.subnetItemList = await this.apiListVRFSubnet(item.ID);
          this.thietBiMangSelectionList = await this.apiListThietBiMang(item.IDC_ID);
        }
      }
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    apiListItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/quanly-vrf/get-all");
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListHistory: async function (vrfID) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/quanly-vrf/change-history", { id: vrfID });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListIDC: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-idc");
        result = rs.data;

      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListThietBiMang: async function (idcID) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/quanly-vrf/thietbimang-by-idc-id", { id: idcID });
        result = rs.data;

      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListVRFHaTangMang: async function (vrfID) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/quanly-vrf/hatangmang", { id: vrfID });
        result = rs.data;

      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListVRFZone: async function (vrfID) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/quanly-vrf/zone", { id: vrfID });
        result = rs.data;

      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListVRFVLAN: async function (vrfID) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/quanly-vrf/vlan", { id: vrfID });
        result = rs.data;

      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListVRFSubnet: async function (vrfID) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/quanly-vrf/subnet", { id: vrfID });
        result = rs.data;

      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiHaTangMangDetail: async function (id) {
      let result = {};
      try {
        let rs = await this.$root.context.get("/web-ecms/quanly-vrf/hatangmang-detail", { id: id });
        result = rs.data[0];

      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiSave: async function (args) {
      let result;
      try {
        result = await this.$root.context.post("/web-ecms/quanly-vrf/create-or-update", {
          id: args.id,
          ten: args.ten,
          hieuLuc: args.hieuLuc,
          ghiChu: args.ghiChu,
          idcID: args.idcID,
          rd: args.rd,
          rtExport: args.rtExport,
          rtImport: args.rtImport,
          asn: args.asn,
          thietBiMangId: args.thietBiMangId,
          nhanVienId: args.nhanVienId,
          mayCapNhap: args.mayCapNhap,
          nguoiCapNhap: args.nguoiCapNhap,
          ipCapNhap: args.ipCapNhap
        });
      } catch (e) {
        result = false;
      }
      return result;
    },
    apiDelete: async function (vrfID) {
      let result = false;
      try {
        let rs = await this.$root.context.get("/web-ecms/quanly-vrf/delete", { id: vrfID });
        result = rs.error_code == 'BSS-00000000';
      } catch (e) {
        this.$root.toastError(e.response.data.message);
        result = false;
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
      this.currentItem.hieuLuc = item.HIEU_LUC;
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.idcID = item.IDC_ID;
      this.currentItem.rd = item.RD;
      this.currentItem.rtExport = item.RT_EXPORT;
      this.currentItem.rtImport = item.RT_IMPORT;
      this.currentItem.asn = item.ASN;
      this.currentItem.thietBiMangId = item.THIETBIMANG_ID;

      this.currentItem.nhanVienId = this.userInfo.nhanVienId;
      this.currentItem.mayCapNhap = this.userInfo.mayNhanVien;
      this.currentItem.nguoiCapNhap = this.userInfo.nhanhVienTen;
      this.currentItem.ipCapNhap = this.userInfo.IP;

    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        ID: 0,
        TEN: '',
        HIEU_LUC: 1,
        GHI_CHU: '',
        IDC_ID: 0,
        RD: '',
        RT_EXPORT: '',
        RD_IMPORT: '',
        ASN: '',
        THIETBIMANG_ID: 0
      });
    },
    validateControls: function () {
      let flg = true;
      if (this.currentItem.ten == '') {
        this.$root.toastError('Tên không được để trống.');
        flg = false;
      }
      if (this.currentItem.idcID == 0) {
        this.$root.toastError('Thuộc IDC không được để trống.');
        flg = false;
      }
      if (this.currentItem.thietBiMangId == 0) {
        this.$root.toastError('Thuộc Thiết bị mạng không được để trống.');
        flg = false;
      }
      return flg;
    },
    grdItems_RowSelected: async function (args) {
      try {
        this.$root.showLoading(true);
        this.setCurrentItem(args.data);
        console.log('args.data', args.data);
        this.historyItemList = await this.apiListHistory(args.data.ID);
        this.haTangMangItemList = await this.apiListVRFHaTangMang(args.data.ID);
        this.zoneItemList = await this.apiListVRFZone(args.data.ID);
        this.VLANItemList = await this.apiListVRFVLAN(args.data.ID);
        this.subnetItemList = await this.apiListVRFSubnet(args.data.ID);
        this.thietBiMangSelectionList = await this.apiListThietBiMang(args.data.IDC_ID);
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
          this.historyItemList = await this.apiListHistory(items[0].ID);
          this.haTangMangItemList = await this.apiListVRFHaTangMang(items[0].ID);
          this.zoneItemList = await this.apiListVRFZone(items[0].ID);
          this.VLANItemList = await this.apiListVRFVLAN(items[0].ID);
          this.subnetItemList = await this.apiListVRFSubnet(items[0].ID);
          this.thietBiMangSelectionList = await this.apiListThietBiMang(items[0].IDC_ID);
        } finally {
          this.$root.showLoading(false);
        }
      } else {
        this.setCurrentItemDefault();
        this.historyItemList = [];
        this.haTangMangItemList = [];
        this.zoneItemList = [];
        this.VLANItemList = [];
        this.subnetItemList = [];
        this.thietBiMangSelectionList = [];
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
            let mess = this.currentItem.id == 0 ? 'Thêm mới thông tin thành công.' : 'Cập nhật thông tin thành công.';
            this.$root.showLoading(true);
            let rs = await this.apiSave(this.currentItem);
            if (rs.data > 0) {
              this.$root.toastSuccess(mess);
            } else if (rs.data == 0) {
              this.$root.toastError('Đối tượng thao tác không tồn tại.');
            } else if (rs.data == -1) {
              this.$root.toastError('Tên VRF đã tồn tại.');
            } else {
              this.$root.toastError('Chức năng lỗi.');
            }
            this.vrfItemList = await this.apiListItems();
            this.enableControls(!rs);
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    onCancel: async function (args) {
      let items = this.grdItems.getSelectedRecords();
      console.log('items', items);

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
        console.log('item.data', item);
        this.setCurrentItem(item);
        this.historyItemList = await this.apiListHistory(item.ID);
        this.haTangMangItemList = await this.apiListVRFHaTangMang(item.ID);
        this.zoneItemList = await this.apiListVRFZone(item.ID);
        this.VLANItemList = await this.apiListVRFVLAN(item.ID);
        this.subnetItemList = await this.apiListVRFSubnet(item.ID);
      } else {
        this.setCurrentItemDefault();
        this.historyItemList = [];
        this.haTangMangItemList = [];
        this.zoneItemList = [];
        this.VLANItemList = [];
        this.subnetItemList = [];
      }
      this.enableControls(false);
    },
    onEdit: async function (args) {
      this.enableControls(true);
    },
    onDelete: async function (args) {
      let items = this.grdItems.getSelectedRecords();
      let msg = `Bạn có muốn xóa ${items.length} mẫu tin đã chọn không?`
      let flag = await this.$confirm(msg, "Xác nhận",
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
          let isSuccess = false;
          for (const item of items) {
            isSuccess = await this.apiDelete(item.ID);
            if (!isSuccess) { break; }
          }
          if (isSuccess) {
            this.setCurrentItemDefault();
            this.vrfItemList = await this.apiListItems();
            this.enableControls(false);
            this.$root.toastSuccess("Xóa thông tin thành công");
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    onChangeIDC: async function () {
      try {
        this.$root.showLoading(true);
        this.currentItem.thietBiMangId = 0;
        this.thietBiMangSelectionList = await this.apiListThietBiMang(this.currentItem.idcID);
      } finally {
        this.$root.showLoading(false);
      }
    },
    hideModal: function () {
      this.$refs["popupHaTangMangDetail"].hide();
    },
    handleShowModal: function () {
      this.$refs["popupHaTangMangDetail"].show();
    },
    haTangMangDetail: async function (idx) {
      this.haTangMangItem = await this.apiHaTangMangDetail(this.haTangMangItemList[idx].HATANGMANG_ID);
      if (!this.haTangMangItem.AVATAR.includes("data:image")) {
        this.haTangMangItem.AVATAR = "data:image/*;base64," + this.haTangMangItem.AVATAR;
      }
      console.log('this.haTangMangItem.AVATAR', this.haTangMangItem.AVATAR);
      this.handleShowModal();

    }
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
