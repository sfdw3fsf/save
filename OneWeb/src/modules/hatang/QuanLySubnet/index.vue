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
        ten: '', // Tên subnet
        subnet: '', // Tên subnet
        ipId: 0, // Loại Ip
        plIpId: 0, // Phân loại Ip
        idcId: 0, // Thuộc IdC
        gateWay: '', //Gateway
        tenIDC: '',
        thuocHaTangId: 0, // Thuộc hạ tầng mạng
        thietBiId: 0, // Thuộc thiết bị
        tenThietBi: '',
        vlanId: 0, // Thuộc VlanId
        vrfId: 0,
        subnetId: 0,// SubnetId
        subnetMarkId: null, // SubnetMarkId
        mucDichId: 0, // Mục đích sử dụng
        tenVRF: '',
        zoneId: 0, // Thuộc zone
        tenZone: '',
        ghiChu: '',
        thamChieu: '',
        hieuLuc: 1,
      },
      ipSelectionList: [],
      zoneSelectionList: [],
      plIpSelectionList: [], // Phân loại Ip
      vlanSelectionList: [], // thuộc Vlan
      subnetSelectionList: [], //thuộc subnet
      subnetMarkSelecttionList: [], // subnet mark
      mucDichSelectionList: [], //Mục đích sử dụng
      thuocHaTangSelectionList: [],
      haTangMangItemList: [],
      subnetItemList: [],
      lichSuItemList: [],

      dsSubnet: [], //
      idcSelectionList: [],
      vrfSelectionList: [],
      thietBiSelectionList: [],

      haTangAoHoaList: [],
      subnetConList: [],
      ipStoreList: [],
      lichSuList: [],

      controls: {

        chkHieuLuc: {
          enabled: false,
        },
        selectIDC: {
          enabled: false,
          invalid: false,
        },
        selectIP: {
          enabled: false,
          invalid: false,
        },

        selectPlIP: {
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
        selectMucDich: {
          enabled: false,
          invalid: false
        },
        selectSubnet: {
          enabled: false,
          invalid: false
        },
        selectSubnetMark: {
          enabled: false,
          invalid: false
        },
        selectThuocHaTang: {
          enabled: false,
          invalid: false
        },
        selectThietBi: {
          enabled: false,
          invalid: false
        },
        selectVlan: {
          enabled: false,
          invalid: false
        },
        txtThietBi: {
          enabled: false,
          invalid: false
        },
        txtTen: {
          enabled: false,
          invalid: false
        },
        txtSubnet: {
          enabled: false,
          invalid: false
        },
        txtGateWay: {
          enabled: false,
          invalid: false
        },
        txtThamChieu: {
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
    // grdItems_haTangMang: function () {
    //   return this.$refs.grdItems_haTangMang;
    // },
    // grdItems_subnet: function () {
    //   return this.$refs.grdItems_subnet;
    // },
    // grdItems_history: function () {
    //   return this.$refs.grdItems_history;
    // },
  },
  watch: {
    "currentItem.id": function (value) {
      this.buttons.btnEdit.enabled = value > 0;
      this.buttons.btnDelete.enabled = value > 0;
    },
    "currentItem.ten": function (value) {
      this.controls.txtTen.invalid = !value;
    },
    "currentItem.subnet": function (value) {
      this.controls.txtSubnet.invalid = !this.validateSubnetFormat(value);
    },
    "currentItem.gateWay": function (value) {
      this.controls.txtGateWay.invalid = !value;
    },
    "currentItem.idcId": async function (value) {
      this.currentItem.vlanId = 0;
      this.currentItem.vrfId = 0;
      this.vlanSelectionList = await this.getVlanItems();
    },
    "currentItem.zoneId": async function (value) {
      this.currentItem.vlanId = 0;
      this.vlanSelectionList = await this.getVlanItems();

    },
    "currentItem.vlanId": function (value) {
      this.controls.selectVlan.invalid = value == 0;
    },
    "currentItem.vrfId": async function (value) {
      this.controls.selectVRF.invalid = value == 0;
      if (value > 0) {
        this.vlanSelectionList = await this.getVlanItems();
      } else {
        this.currentItem.vlanId = 0;
      }
    },
    // "currentItem.tenThietBi": function (value) {
    //   this.controls.txtThietBi.invalid = value == "";
    // },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.ipSelectionList = await this.getIpList();
      this.plIpSelectionList = await this.getPlIpList();
      this.zoneSelectionList = await this.getZoneList();
      this.subnetSelectionList = await this.getSubnetList();
      this.mucDichSelectionList = await this.getMucDichList();
      this.subnetMarkSelecttionList = await this.getSubnetMarkAll()
      this.thietBiSelectionList = await this.getThietBiList();
      this.thuocHaTangSelectionList = await this.getThuocHaTangList();
      this.idcSelectionList = await this.getIdcItemsCat();
      this.dsSubnet = await this.getDsSubnet();

      if (this.modalItemID > 0) {
        let item = this.dsSubnet.find(x => x.ID == this.modalItemID);
        if (item != null) {
          this.setCurrentItem(item);
          this.haTangMangItemList = await this.getHaTangItems(item.ID);
          this.subnetItemList = await this.getSubnetItems(item.VLAN_ID);
          this.haTangAoHoaList = await this.getHaTangAoHoa(item.ID);
          this.subnetConList = await this.getSubnetConList(item.ID);
          this.ipStoreList = await this.getIpStoreList(item.ID);
          this.lichSuList = await this.getLichSuList(item.ID);
          this.vlanSelectionList = await this.getVlanItems();
          if (item.VRF_ID != null) {
            this.vrfSelectionList = await this.getVrfByIdIDC(item.IDC_ID);
          }
          else {
            this.vrfSelectionList = [];
          }
        }
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
        const body = {
          p_idc_id: this.currentItem.idcId,
          p_vrf_id: this.currentItem.vrfId,
          p_zone_id: this.currentItem.zoneId,
          p_thietbi_id: this.currentItem.thietBiId,
        }
        let rs = await this.$root.context.post("/web-ecms/quanly-subnet/ds-vlan", body);
        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getDsSubnet: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/quanly-subnet/ds-subnet");
        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getVlanList: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "HATANG_VLAN",
          thamSo1: null,
          thamSo2: null
        });
        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getIpList: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "IP_LOAI",
          thamSo1: null,
          thamSo2: null
        });
        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getPlIpList: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "PHAN_LOAI_IP",
          thamSo1: null,
          thamSo2: null
        });
        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getZoneList: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "ZONE",
          thamSo1: null,
          thamSo2: null
        });
        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getSubnetList: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "SUBNET",
          thamSo1: null,
          thamSo2: null
        });
        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getMucDichList: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "SUBNET_QUYHOACH",
          thamSo1: null,
          thamSo2: null
        });
        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getThuocHaTangList: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "HATANG_MANG",
          thamSo1: null,
          thamSo2: null
        });
        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getThietBiList: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "HATANG_THIETBI",
          thamSo1: null,
          thamSo2: null
        });
        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
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
        this.handleApiError(e);
      }
      return result;
    },
    getHaTangAoHoa: async function (subnetid) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/quanly-subnet/ds-hatang-aohoa", {
          id: subnetid
        });

        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getSubnetConList: async function (subnetid) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/quanly-subnet/ds-subnet-con", {
          id: subnetid
        });

        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getIpStoreList: async function (subnetid) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/quanly-subnet/ds-ipstore", {
          id: subnetid
        });

        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getLichSuList: async function (subnetid) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/quanly-subnet/ds-lichsu", {
          id: subnetid
        });

        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
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
        this.handleApiError(e);
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
        this.handleApiError(e);
      }
      return result;
    },
    getVrfByIdIDC: async function (idcId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "VRF-BY-IDC",
          thamSo1: idcId,
          thamSo2: null
        });

        result = rs.data;

      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getHaTangMangbyIDC: async function (idcId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "HATANGMANG_BY_IDC_ID",
          thamSo1: idcId,
          thamSo2: null
        });

        result = rs.data;

      } catch (e) {
        this.handleApiError(e);
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
    getSubnetMarkList: async function (plip) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "SUBNET_MARK_BY_PLIP",
          thamSo1: plip,
          thamSo2: null
        });
        result = rs.data;

      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getSubnetMarkAll: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "HATANG_SUBNET_MARK",
          thamSo1: null,
          thamSo2: null
        });
        result = rs.data;

      } catch (e) {
        this.handleApiError(e);
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
        this.handleApiError(e);
        // if (!(e.response == null || e.response == undefined)) {
        //   if (!(e.response.data == null || e.response.data == undefined)) {
        //     this.$root.toastError(e.response.data.message_detail);
        //   }
        // }
      }
      return result;
    },
    updateItem: async function (args) {
      let result = true;
      try {
        let rs = await this.$root.context.post("/web-ecms/quanly-subnet/update-subnet", {
          id: args.id == 0 ? null : args.id,
          ten: args.ten,
          ip_loai_id: args.ipId,
          ip_phanloai_id: args.plIpId,
          subnet: args.subnet,
          subnet_mask_id: args.subnetMarkId,
          gateway: args.gateWay,
          idc_id: args.idcId,
          vrf_id: args.vrfId,
          zone_id: args.zoneId,
          vlan_id: args.vlanId,
          subnet_id: args.subnetId,
          hatangmang_id: args.thuocHaTangId,
          thietbi_id: args.thietBiId,
          subnet_quyhoach_id: args.mucDichId,
          thamchieu: args.thamChieu,
          ghi_chu: args.ghiChu,
          hieu_luc: args.hieuLuc
        });
      } catch (e) {
        this.handleApiError(e);
        result = false;
      }
      this.enableControls(true);
      return result;
    },
    deleteItem: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/quanly-subnet/xoa", { id: id });
        result = rs.data.result;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    enableControls: function (args) {
      // for (const key in this.controls) {
      //   if (this.controls.hasOwnProperty(key)) {
      //     this.controls[key].enabled = args;
      //   }
      // }
      Object.keys(this.controls).forEach(key => {
        this.controls[key].enabled = args;
      });
      this.dsSubnet = this.getDsSubnet();
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
      this.currentItem.ipId = item.IP_LOAI_ID;
      this.currentItem.plIpId = item.IP_PHANLOAI_ID;
      this.currentItem.subnetMarkId = item.SUBNET_MASK_ID;
      this.currentItem.subnetId = item.SUBNET_ID;
      // this.currentItem.tenSubnetMark = item.SUBNETMARK;
      this.currentItem.gateWay = item.GATEWAY;
      this.currentItem.subnet = item.SUBNET;
      this.currentItem.idcId = item.IDC_ID;
      //this.currentItem.tenIDC = item.TEN_IDC;
      this.currentItem.thietBiId = item.THIETBI_ID;
      this.currentItem.tenThietBi = item.TEN_THIETBI;
      this.currentItem.mucDichId = item.SUBNET_QUYHOACH_ID;
      // this.currentItem.tenVRF = item.TEN_VRF;
      this.currentItem.zoneId = item.ZONE_ID;
      this.currentItem.tenZone = item.TEN_ZONE;
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
      this.currentItem.thamChieu = item.THAMCHIEU;
      this.currentItem.thuocHaTangId = item.HATANGMANG_ID;
      setTimeout(() => {
        this.currentItem.vrfId = item.VRF_ID;
        this.currentItem.vlanId = item.VLAN_ID;
      }, 500)

      this.haTangAoHoaList = [];
      this.subnetConList = [];
      this.ipStoreList = [];
      this.lichSuList = [];
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        ID: 0,
        TEN: '', // Tên subnet
        SUBNET: '', // Tên subnet
        IP_LOAI_ID: 0, // Loại Ip
        IP_PHANLOAI_ID: 0, // Phân loại Ip
        IDC_ID: 0, // Thuộc IdC
        GATEWAY: '', //Gateway
        TEN_IDC: '',
        HATANGMANG_ID: 0, // Thuộc hạ tầng mạng
        THIETBI_ID: 0, // Thuộc thiết bị
        TEN_THIETBI: '',
        VLAN_ID: 0, // Thuộc VlanId
        VRF_ID: 0,
        SUBNET_ID: 0,// SubnetId
        SUBNET_MASK_ID: 0, // SubnetMarkId
        TEN_VRF: '',
        ZONE_ID: 0, // Thuộc zone
        TEN_ZONE: '',
        GHI_CHU: '',
        THAMCHIEU: '',
        HIEU_LUC: 1,
        SUBNET_QUYHOACH_ID: 0, // Mục đích sử dụng

      });
    },
    validateControls: function () {
      let msg = "";

      if (this.controls.txtTen.invalid) {
        msg = "Tên Subnet không được để trống.";
      }
      if (this.controls.selectIP.invalid) {
        if (msg != "") msg += "\n\r";
        msg += "Loại Ip không được để trống.";
      }
      if (this.controls.txtSubnet.invalid) {
        if (msg != "") msg += "\n\r";
        msg += "Subnet không đúng định dạng IPv4 (vd: 192.168.1.0/24) hoặc IPv6 (vd: 2001:db8::/32).";
      }
      if (this.currentItem.zoneId == 0) {
        if (msg != "") msg += "\n\r";
        msg += "Zone không được để trống.";
      }
      if (this.currentItem.vlanId == 0) {
        if (msg != "") msg += "\n\r";
        msg += "Vlan không được để trống.";
      }


      // if (this.controls.selectPlIP.invalid) {
      //   if (msg != "") msg += "\n\r";
      //   msg += "Phân loại Ip không được để trống.";
      // }
      //  if (this.controls.txtGateWay.invalid) {
      //   if (msg != "") msg += "\n\r";
      //   msg += "Gateway không được để trống.";
      // }

      // if (this.controls.selectIDC.invalid) {
      //   if (msg != "") msg += "\n\r";
      //   msg += "IDC không được để trống.";
      // }
      // if (this.controls.selectZone.invalid) {
      //   if (msg != "") msg += "\n\r";
      //   msg += "Zone không được để trống.";
      // }
      // if (this.controls.selectVRF.invalid) {
      //   if (msg != "") msg += "\n\r";
      //   msg += "VRF không được để trống.";
      // }
      // if(this.controls.selectVlan.invalid) {
      //   if (msg != "") msg += "\n\r";
      //   msg += "VLAN không được để trống.";
      // }


      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },
    grdItems_RowSelected: async function (args) {
      console.log("grdItems_RowSelected", args);
      try {
        this.currentItem.id = args.data.ID;
        this.$root.showLoading(true);
        this.setCurrentItem(args.data);
        this.haTangAoHoaList = [];
        this.subnetConList = [];
        this.ipStoreList = [];
        this.lichSuList = [];

        this.haTangMangItemList = await this.getHaTangItems(args.data.ID);

        this.haTangAoHoaList = await this.getHaTangAoHoa(args.data.ID);
        this.subnetConList = await this.getSubnetConList(args.data.ID);
        this.ipStoreList = await this.getIpStoreList(args.data.ID);
        this.lichSuList = await this.getLichSuList(args.data.ID);

        // Lấy cho 3 tab ở đây

        if (args.data.VRF_ID != null) {
          this.vrfSelectionList = await this.getVrfByIdIDC(args.data.IDC_ID);
        }
        else {
          this.vrfSelectionList = [];
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
          this.subnetItemList = await this.getSubnetItems(items[0].VLAN_ID);


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
              if (this.currentItem.id == 0) {
                this.$root.toastSuccess("Thêm Subnet thành công");

              }
              else {
                this.$root.toastSuccess("Cập nhật Subnet thành công");
              }
            }
            else {
              if (this.currentItem.id == 0) {
                this.$root.toastError("Thêm Subnet thất bại");
              }
            }
            this.enableControls(!rs);
            this.dsSubnet = await this.getDsSubnet();

          } finally {
            this.$root.showLoading(false);
          }
        }
      } else {
        this.$root.toastError("Chưa nhập đủ thông tin");
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
        this.setCurrentItem(item);
        this.haTangMangItemList = await this.getHaTangItems(item.ID);
        this.subnetItemList = await this.getSubnetItems(item.VLAN_ID);

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
    onDelete: async function (args) {
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
          this.$root.showLoading(true);
          let rs = await this.deleteItem(this.currentItem.id);
          if (rs) {
            this.setCurrentItemDefault();

            this.enableControls(false);
            this.$root.toastSuccess("Xóa Subnet thành công");
          } else {
            this.$root.toastError("Xóa Subnet thất bại");
          }
        } finally {
          this.$root.showLoading(false);
          this.dsSubnet = await this.getDsSubnet();
        }
      }
    },
    onChangeIDC: async function () {
      if (this.currentItem.idcId != 0) {
        try {
          this.$root.showLoading(true);

          this.vrfSelectionList = await this.getVrfByIdIDC(this.currentItem.idcId);
          this.thuocHaTangSelectionList = await this.getHaTangMangbyIDC(this.currentItem.idcId);
          this.vlanSelectionList = await this.getVlanItems();

        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    onChangePLIP: async function () {
      if (this.currentItem.plIpId != 0) {
        try {
          this.$root.showLoading(true);
          this.subnetMarkSelecttionList = await this.getSubnetMarkList(this.currentItem.plIpId);



        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    onChangeVrf: async function () {
      this.vlanSelectionList = await this.getVlanItems();
      const rs = this.vrfSelectionList.find((item) => item.ID == this.currentItem.vrfId);
      console.log(rs);
      if (rs != null) {
        this.currentItem.thietBiId = rs.THIETBI_ID;
        this.currentItem.tenThietBi = rs.TEN_THIETBI;
      }
    },
    onChangeZone: async function () {
      this.vlanSelectionList = await this.getVlanItems();
    },
    onChangeThietBi: async function () {
      this.vlanSelectionList = await this.getVlanItems();
    },
    handleApiError: function (e) {
      if (e?.response?.data?.message_detail) {
        this.$root.toastError(e.response.data.message_detail);
      }
    },
    validateSubnetFormat: function (subnet) {
      if (!subnet) return false;

      // IPv4 validation (e.g., 192.168.1.0)
      const ipv4Regex = /^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

      // IPv6 validation (e.g., 2001:db8::1)
      const ipv6Regex = /^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$|^([0-9a-fA-F]{1,4}:){1,7}:$|^([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}$|^([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}$|^([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}$|^([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}$|^([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}$|^[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})$|^:((:[0-9a-fA-F]{1,4}){1,7}|:)$/;

      // IPv6 compressed format validation (e.g., ::1, 2001:db8::)
      const ipv6CompressedRegex = /^::$|^::1$|^([0-9a-fA-F]{1,4}::)|^(([0-9a-fA-F]{1,4}:){1,6}:)$/;

      return ipv4Regex.test(subnet) || ipv6Regex.test(subnet) || ipv6CompressedRegex.test(subnet);
    },
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
