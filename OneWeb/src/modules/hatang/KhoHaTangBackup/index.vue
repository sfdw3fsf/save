<template src="./index.html"></template>


<script>
import DialogThietBi from "../ThietBiHinhThanhKhoHaTang/index.vue"

export default {
  components: {
    DialogThietBi
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
        donViTen: '',
        donViChaId: 0,
        donViChaTen: ''
      },
      imageItem: '',
      currentItem: {
        id: 0,
        maHaTangInfra: '',
        ten: '',
        hinhThucDauTu: 0,
        loaiHaTangId: 0,
        loaiHaTangName: '',
        idcId: 0,
        haTangMangId: null,
        adminLink: '',
        avatar: '',
        ghiChu: '',
        hieuLuc: 0,
        donViQLyId: 0,
        donViQLyName: '',
        phongBanQLyId: 0,
        phongBanQLyName: '',
        nguoiQLyId: 0,
        nguoiQLyName: '',
        nhanVienId: 0,
        mayCapNhap: '',
        nguoiCapNhap: '',
        ipCapNhap: '',
        ipCungCapDichVu: null,
        ipReplicaData: null,
        ipQuanTri: null,
      },
      backupItemList: [],
      thietBiItemList: [],
      tuDiaItemList: [],
      ipItemList: [],
      spdvItemList: [],
      historyItemList: [],
      IDCSelectionList: [],
      loaiHaTangSelectionList: [],
      haTangMangSelectionList: [],
      hinhThucDauTuSelectionList: [],
      donViQlySelectionList: [],
      phongBanQlySelectionList: [],
      nguoiQlySelectionList: [],
      ipAddressSelectionList: [],
      thietBiHinhThanhHaTangList: [],
      controls: {
        txtMaHaTangInfra: {
          enabled: false,
          invalid: false,
        },
        chkHieuLuc: {
          enabled: false,
        },
        txtTen: {
          enabled: false,
          invalid: false,
        },
        selectIDC: {
          enabled: false,
          invalid: false
        },
        selectLoaiHaTang: {
          enabled: false,
          invalid: false
        },
        selectHaTangMang: {
          enabled: false,
          invalid: false
        },
        selectHinhThucDauTu: {
          enabled: false,
          invalid: false
        },
        txtAdminLink: {
          enabled: false,
        },
        txtGhiChu: {
          enabled: false,
        },
        selectDonViQly: {
          enabled: false
        },
        selectPhongBanQly: {
          enabled: false
        },
        selectNguoiQly: {
          enabled: false
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
        },
        btnAddImage: '',
        imgAvatar: 'hidden',
      },
      sttColumns: [
        {
          field: 'SOTHUTU',
          headerText: 'STT',
          headerAlign: 'Center',
          cssClass: "text-center",
          allowFiltering: true
        },
      ],
      tenTuDiaColumns: [
        {
          field: 'TEN',
          headerText: 'Tên tủ đĩa',
          headerAlign: 'Center',
          cssClass: "text-center",
          allowFiltering: true
        },
      ],
      SDDColumns: [
        {
          field: 'SSD_TONG',
          headerText: 'Tổng',
          width: '30%',
          headerAlign: 'Center',
          cssClass: "text-center",
          allowFiltering: false
        },
        {
          field: 'SSD_NANGLUC',
          headerText: 'Khả năng cấp',
          width: '40%',
          headerAlign: 'Center',
          cssClass: "text-center",
          allowFiltering: false
        },
        {
          field: 'SSD_CAPPHAT',
          headerText: 'Đã cấp',
          width: '30%',
          headerAlign: 'Center',
          cssClass: "text-center",
          allowFiltering: false
        }
      ],
      HDDColumns: [
        {
          field: 'HDD_TONG',
          headerText: 'Tổng',
          width: '30%',
          headerAlign: 'Center',
          cssClass: "text-center",
          allowFiltering: false
        },
        {
          field: 'HDD_NANGLUC',
          headerText: 'Khả năng cấp',
          width: '40%',
          headerAlign: 'Center',
          cssClass: "text-center",
          allowFiltering: false
        },
        {
          field: 'HDD_CAPPHAT',
          headerText: 'Đã cấp',
          width: '30%',
          headerAlign: 'Center',
          cssClass: "text-center",
          allowFiltering: false
        }
      ],
      IOPSColumns: [
        {
          field: 'IOPS_TONG',
          headerText: 'Tổng',
          width: '30%',
          headerAlign: 'Center',
          cssClass: "text-center",
          allowFiltering: false
        },
        {
          field: 'IOPS_NANGLUC',
          headerText: 'Khả năng cấp',
          width: '40%',
          headerAlign: 'Center',
          cssClass: "text-center",
          allowFiltering: false
        },
        {
          field: 'IOPS_CAPPHAT',
          headerText: 'Đã cấp',
          width: '30%',
          headerAlign: 'Center',
          cssClass: "text-center",
          allowFiltering: false
        }
      ],
      multipleSelection: [
        {
          multiple: true
        }
      ]
    };
  },
  computed: {
    grdItems: function () {
      return this.$refs.grdItems;
    },
    grdItems_haTangMang: function () {
      return this.$refs.grdItems_haTangMang;
    },
    grdItems_tuDia: function () {
      return this.$refs.grdItems_tuDia;
    }
  },
  watch: {
    "currentItem.id": function (value) {
      this.buttons.btnEdit.enabled = value > 0;
      this.buttons.btnDelete.enabled = value > 0;
    },
  },
  mounted: async function () {
    // init current user Info
    this.userInfo.nhanVienId = await this.$root.token.getNhanVienID();
    this.userInfo.nhanhVienTen = await this.$root.context.user.getTenNhanVien();
    this.userInfo.mayNhanVien = await this.$root.token.getMachine();
    this.userInfo.IP = await this.$root.token.getIP();
    this.userInfo.phanVungID = await this.$root.token.getPhanVungID();
    this.userInfo.donViId = await this.$root.token.getDonViID();
    this.userInfo.donViTen = await this.$root.context.user.getTenDonVi();
    this.userInfo.donViChaId = this.userInfo.donViId;
    this.userInfo.donViChaTen = this.userInfo.donViTen;
    let ds = await this.apiGetDonViCha(this.userInfo.donViId);
    if (ds != null && ds.length > 0) {
      this.userInfo.donViChaId = ds[0].DONVI_ID;
      this.userInfo.donViChaTen = ds[0].TEN_DV;
    }
  },
  created: async function () {
    try {
      this.$root.showLoading(true);

      this.backupItemList = await this.apiListItems();
      this.loaiHaTangSelectionList = await this.apiListLoaiHaTang();
      this.IDCSelectionList = await this.apiListIDC();
      this.ipAddressSelectionList = await this.apiListIPAddress();
      this.hinhThucDauTuSelectionList = [
        { ID: 1, TEN: 'Hợp tác' },
        { ID: 2, TEN: 'Đầu tư nội bộ' },
        { ID: 3, TEN: 'Thuê quản trị' },
        { ID: 4, TEN: 'Khác' }
      ];

    } finally {
      this.reGetIPAddressList();
      this.$root.showLoading(false);
    }
  },
  methods: {
    reGetIPAddressList: async function () {
      const ipAddressList = await this.apiListIPAddress();
      this.ipAddressSelectionList = ipAddressList.filter((item) => item.HATANG_BACKUP_ID === this.currentItem.id || (item.DA_GAN_MAYAO !== 1 && item.DA_GAN_THIETBI_KHAC !== 1 && item.DA_GAN_HATANG_BACKUP !== 1 && item.DA_GAN_HATANG_LUU_TRU !== 1));
      this.ipAddressSelectionList.forEach(item => {
        item.id = item.ID;
        item.label = item.IP;
      });
    },
    apiGetDonViCha: async function (pId) {
      let result = [];
      if (pId == null || pId < 1) {
        return;
      }
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/don-vi-cha", { donViId: pId });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/kho-hatang-backup/get-all");
        result = rs.data;
        console.log('apiListItems', result);
        if (rs.error_code === 'BSS-00000000') {
          result.forEach(element => {
            // THUOC HATANGMANG
            let HATANGMANG_ID = [];
            let HATANGMANG_NAME = [];
            const HATANGMANG = JSON.parse(element.HATANGMANG)
            HATANGMANG.forEach(item => {
              HATANGMANG_ID.push(item.HATANGMANG_ID);
              HATANGMANG_NAME.push(item.HATANGMANG_NAME);
            });
            element.HATANGMANG_ID = HATANGMANG_ID;
            element.HATANGMANG_NAME = HATANGMANG_NAME.join(', ');

            // IP DICH VU
            let IP_CUNGCAPDICHVU_ID = [];
            const IP_CUNGCAPDICHVU = JSON.parse(element.IP_DICHVU)
            IP_CUNGCAPDICHVU.forEach(item => {
              IP_CUNGCAPDICHVU_ID.push(item.ID);
            });
            element.IP_CUNGCAPDICHVU_ID = IP_CUNGCAPDICHVU_ID;
            // IP_REPLICA
            let IP_REPLICA_ID = [];
            const IP_REPLICA = JSON.parse(element.IP_REPLICA)
            IP_REPLICA.forEach(item => {
              IP_REPLICA_ID.push(item.ID);
            });
            element.IP_REPLICA_ID = IP_REPLICA_ID;
            // IP QUAN TRI
            let IP_QUANTRI_ID = [];
            const IP_QUANTRI = JSON.parse(element.IP_QUANTRI)
            IP_QUANTRI.forEach(item => {
              IP_QUANTRI_ID.push(item.ID);
            });
            element.IP_QUANTRI_ID = IP_QUANTRI_ID;
          });
        };
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListHistory: async function (id) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/kho-hatang-backup/change-history", { id: id });
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
    apiListHaTangMangByIDCId: async function (idcID) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-mang-by-idc-id", { id: idcID });
        result = rs.data;
        result.forEach(element => {
          element.id = element.ID;
          element.label = element.TEN;
        });
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListLoaiHaTang: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/loai-ha-tang");
        result = rs.data;

      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListThietBiByBackupId: async function (id) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/kho-hatang-backup/thiet-bi", { id: id });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListTuDiaByBackupId: async function (id) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/kho-hatang-backup/tu-dia", { id: id });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListIPByBackupId: async function (id) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/kho-hatang-backup/ip", { id: id });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListSPDVByBackupId: async function (id) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/kho-hatang-backup/san-pham-dich-vu", { id: id });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListIPAddress: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/thong-tin-thiet-bi/0/thiet-bi-ip");
        result = rs.data;
        result.forEach(element => {
          element.id = element.ID;
          element.label = element.IP;
        });
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      console.log('apiListIPAddress', result);
      return result;
    },
    apiSave: async function (args, thietbi_list, thietbi_tudia_list) {
      let result;
      try {
        result = await this.$root.context.post("/web-ecms/kho-hatang-backup/create-or-update", {
          id: args.id,
          maHaTangInfra: args.maHaTangInfra,
          ten: args.ten,
          hinhThucDauTu: args.hinhThucDauTu,
          loaiHaTangId: args.loaiHaTangId,
          idcId: args.idcId,
          haTangMangId: args.haTangMangId.map(item => item),
          ipCungCapDichVu: args.ipCungCapDichVu.map(item => item),
          ipReplicaData: args.ipReplicaData.map(item => item),
          ipQuanTri: args.ipQuanTri.map(item => item),
          adminLink: args.adminLink,
          avatar: args.avatar,
          ghiChu: args.ghiChu,
          hieuLuc: args.hieuLuc,
          donViQLyId: args.donViQLyId,
          phongBanQLyId: args.phongBanQLyId,
          nguoiQLyId: args.nguoiQLyId,
          nhanVienId: args.nhanVienId,
          mayCapNhap: args.mayCapNhap,
          nguoiCapNhap: args.nguoiCapNhap,
          ipCapNhap: args.ipCapNhap,
          thietBiHinhThanhList: thietbi_list.map(item => {
            return {
              id: item.ID,
              thietbi_id: item.THIETBI_ID,
              ngay_cn: item.F_NGAY_CN
            }
          }),
          thietBiTuDiaDauNoiList: thietbi_tudia_list.map(item => {
            return {
              id: item.ID,
              thietbi_id: item.THIETBI_ID,
              sothutu: item.SOTHUTU
            }
          })
        });
      } catch (e) {
        result = false;
      }
      return result;
    },
    apiDelete: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.get("/web-ecms/kho-hatang-backup/delete", { id: id });
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
      this.currentItem.maHaTangInfra = item.MAHATANG_INFRA;
      this.currentItem.ten = item.TEN;
      this.currentItem.hinhThucDauTu = item.HINHTHUCDAUTU;
      this.currentItem.loaiHaTangId = item.LOAIHATANG_ID;
      this.currentItem.loaiHaTangName = item.LOAIHATANG_NAME;
      this.currentItem.idcId = item.IDC_ID;
      this.currentItem.haTangMangId = item.HATANGMANG_ID;
      this.currentItem.ipCungCapDichVu = item.IP_CUNGCAPDICHVU_ID;
      this.currentItem.ipReplicaData = item.IP_REPLICA_ID;
      this.currentItem.ipQuanTri = item.IP_QUANTRI_ID;
      this.currentItem.adminLink = item.ADMIN_LINK;
      this.currentItem.avatar = item.AVATAR;
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
      this.currentItem.donViQLyId = item.DONVIQLY_ID;
      this.currentItem.donViQLyName = item.DONVIQLY_NAME;
      this.currentItem.phongBanQLyId = item.PHONGBANQLY_ID;
      this.currentItem.phongBanQLyName = item.PHONGBANQLY_NAME;
      this.currentItem.nguoiQLyId = item.NGUOIQLY_ID;
      this.currentItem.nguoiQLyName = item.NGUOIQLY_NAME;

      this.currentItem.nhanVienId = this.userInfo.nhanVienId;
      this.currentItem.mayCapNhap = this.userInfo.mayNhanVien;
      this.currentItem.nguoiCapNhap = this.userInfo.nhanhVienTen;
      this.currentItem.ipCapNhap = this.userInfo.IP;
      if (this.currentItem.avatar != null && this.currentItem.avatar != '') {
        this.imageItem = "data:image/*;base64," + this.currentItem.avatar;
        this.buttons.btnAddImage = 'hidden';
        this.buttons.imgAvatar = 'img-fluid';
      } else {
        this.buttons.btnAddImage = '';
        this.buttons.imgAvatar = 'hidden';
      }
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        ID: 0,
        MAHATANG_INFRA: '',
        TEN: '',
        HINHTHUCDAUTU: 0,
        LOAIHATANG_ID: 0,
        LOAIHATANG_NAME: '',
        IDC_ID: 0,
        HATANGMANG_ID: null,
        IP_CUNGCAPDICHVU_ID: null,
        IP_REPLICA_ID: null,
        IP_QUANTRI_ID: null,
        ADMIN_LINK: '',
        AVATAR: '',
        GHI_CHU: '',
        HIEU_LUC: 1,
        DONVIQLY_ID: this.userInfo.donViChaId,
        DONVIQLY_NAME: this.userInfo.donViChaTen,
        PHONGBANQLY_ID: this.userInfo.donViId,
        PHONGBANQLY_NAME: this.userInfo.donViTen,
        NGUOIQLY_ID: this.userInfo.nhanVienId,
        NGUOIQLY_NAME: this.userInfo.nhanhVienTen
      });
      this.image_item = '';
    },
    validateControls: function () {
      let flg = true;
      if (this.currentItem.maHaTangInfra == '') {
        this.$root.toastError('Mã hạ tầng không được để trống.');
        flg = false;
      }
      if (this.currentItem.ten == '') {
        this.$root.toastError('Tên không được để trống.');
        flg = false;
      }
      if (this.currentItem.idcId == 0) {
        this.$root.toastError('Thuộc IDC không được để trống.');
        flg = false;
      }
      if (this.currentItem.loaiHaTangId == 0) {
        this.$root.toastError('Loại hạ tầng không được để trống.');
        flg = false;
      }
      if (this.currentItem.haTangMangId == null || this.currentItem.haTangMangId == '') {
        this.$root.toastError('Thuộc hạ tầng mạng không được để trống.');
        flg = false;
      }
      if (this.currentItem.hinhThucDauTu == 0) {
        this.$root.toastError('Hình thức đầu tư không được để trống.');
        flg = false;
      }
      if (this.currentItem.ipCungCapDichVu == null || this.currentItem.ipCungCapDichVu.length == 0) {
        this.$root.toastError('IP cung cấp dịch vụ không được để trống.');
        flg = false;
      }
      if (this.currentItem.ipReplicaData == null || this.currentItem.ipReplicaData.length == 0) {
        this.$root.toastError('IP replica data không được để trống.');
        flg = false;
      }
      if (this.currentItem.ipQuanTri == null || this.currentItem.ipQuanTri.length == 0) {
        this.$root.toastError('IP quản trị không được để trống.');
        flg = false;
      }
      return flg;
    },
    grdItems_RowSelected: async function (args) {
      console.log('grdItems_RowSelected X');
      try {
        this.$root.showLoading(true);
        this.setCurrentItem(args.data);
        this.historyItemList = await this.apiListHistory(args.data.ID);
        this.thietBiItemList = await this.apiListThietBiByBackupId(args.data.ID);
        this.tuDiaItemList = await this.apiListTuDiaByBackupId(args.data.ID);
        this.ipItemList = await this.apiListIPByBackupId(args.data.ID);
        this.spdvItemList = await this.apiListSPDVByBackupId(args.data.ID);
        this.reGetIPAddressList();

        this.haTangMangSelectionList = await this.apiListHaTangMangByIDCId(this.currentItem.idcId);
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
          this.thietBiItemList = await this.apiListThietBiByBackupId(items[0].ID);
          this.tuDiaItemList = await this.apiListTuDiaByBackupId(items[0].ID);
          this.ipItemList = await this.apiListIPByBackupId(items[0].ID);
          this.spdvItemList = await this.apiListSPDVByBackupId(items[0].ID);

          this.haTangMangSelectionList = await this.apiListHaTangMangByIDCId(this.currentItem.idcId);
        } finally {
          this.$root.showLoading(false);
        }
      } else {
        this.setCurrentItemDefault();
        this.historyItemList = [];
        this.thietBiItemList = [];
        this.tuDiaItemList = [];
        this.ipItemList = [];
        this.spdvItemList = [];
        this.haTangMangSelectionList = [];
      }
    },
    onAddNew: async function () {
      this.setCurrentItemDefault();
      this.enableControls(true);
      this.haTangMangSelectionList = [];
      this.historyItemList = [];
      this.spdvItemList = [];
      this.tuDiaItemList = [];
      this.thietBiItemList = [];
      this.buttons.btnAddImage = '';
      this.buttons.imgAvatar = 'hidden';
    },
    onSave: async function () {
      console.log('onSave: ', this.currentItem)
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
            let rs = await this.apiSave(this.currentItem, this.thietBiItemList, this.tuDiaItemList);
            if (rs.data > 0) {
              this.$root.toastSuccess(mess);
              this.backupItemList = await this.apiListItems();
              this.enableControls(!rs);
            } else if (rs.data == 0) {
              this.$root.toastError('Đối tượng thao tác không tồn tại.');
            } else if (rs.data == -1) {
              this.$root.toastError('Tên hạ tầng backup đã tồn tại.');
            } else if (rs.data == -2) {
              this.$root.toastError('Mã hạ tầng backup đã tồn tại.');
            } else if (rs.data == -3) {
              this.$root.toastError('Tên và Mã hạ tầng backup đã tồn tại.');
            } else {
              this.$root.toastError('Chức năng lỗi.');
            }
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
        this.setCurrentItem(item);
        this.historyItemList = await this.apiListHistory(item.ID);
        this.thietBiItemList = await this.apiListThietBiByBackupId(item.ID);
        this.tuDiaItemList = await this.apiListTuDiaByBackupId(item.ID);
        this.ipItemList = await this.apiListIPByBackupId(item.ID);
        this.spdvItemList = await this.apiListSPDVByBackupId(item.ID);

        this.haTangMangSelectionList = await this.apiListHaTangMangByIDCId(this.currentItem.idcId);

      } else {
        this.setCurrentItemDefault();
        this.historyItemList = [];
        this.thietBiItemList = [];
        this.tuDiaItemList = [];
        this.ipItemList = [];
        this.spdvItemList = [];
        this.haTangMangSelectionList = [];
      }
      this.enableControls(false);
    },
    onEdit: async function (args) {
      this.currentItem.donViQLyId = this.userInfo.donViChaId;
      this.currentItem.donViQLyName = this.userInfo.donViChaTen;
      this.currentItem.phongBanQLyId = this.userInfo.donViId;
      this.currentItem.phongBanQLyName = this.userInfo.donViTen;
      this.currentItem.nguoiQLyId = this.userInfo.nhanVienId;
      this.currentItem.nguoiQLyName = this.userInfo.nhanhVienTen;
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
            this.backupItemList = await this.apiListItems();
            this.enableControls(false);
            this.$root.toastSuccess("Xóa thông tin thành công");
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    hideModal: function () {
      this.$refs["popupHaTangMangDetail"].hide();
    },
    handleShowModal: async function () {
      this.$refs["popupHaTangMangDetail"].show();
    },
    haTangMangDetail: async function (idx) {
      this.haTangMangItem = await this.apiHaTangMangDetail(this.haTangMangItemList[idx].HATANGMANG_ID);
      if (!this.haTangMangItem.AVATAR.includes("data:image")) {
        this.haTangMangItem.AVATAR = "data:image/*;base64," + this.haTangMangItem.AVATAR;
      }
      this.handleShowModal();

    },
    chooseFiles(event) {
      event.preventDefault();
      this.$refs.fileUpload.click();
    },
    onFileChanged: function (event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();

        // Khi FileReader hoàn thành việc đọc file
        reader.onload = () => {
          const fullBase64 = reader.result;
          this.currentItem.avatar = fullBase64.split(',')[1]; // Chuỗi Base64 (bao gồm 'data:image/...;base64,')
          this.imageItem = fullBase64;
          this.buttons.btnAddImage = 'hidden';
          this.buttons.imgAvatar = 'img-fluid';
        };

        reader.onerror = (error) => {
          console.error('Lỗi khi đọc file:', error);
        };

        reader.readAsDataURL(file);
      };
    },
    onChangeIDC: async function () {
      try {
        this.$root.showLoading(true);
        this.currentItem.haTangMangId = null;
        this.thietBiItemList = [];
        this.tuDiaItemList = [];
        this.haTangMangSelectionList = await this.apiListHaTangMangByIDCId(this.currentItem.idcId);
      } finally {
        this.$root.showLoading(false);
      }
    },
    themThietBiHinhThanhKho: async function (loai_hatang) {
      if (this.currentItem.idcId && this.currentItem.idcId > 0) {
        let refs = this.$refs.grdItems_thietbi;
        if (loai_hatang === 'BACKUP_THIETBI') {
          refs.dialogOpen(loai_hatang, this.thietBiItemList.map(item => item.THIETBI_ID));
        } else if (loai_hatang === 'BACKUP_TUDIA') {
          refs.dialogOpen(loai_hatang, this.tuDiaItemList.map(item => item.THIETBI_ID));
        }
        console.log(refs);
      } else {
        this.$toast.error('Thuộc IDC không được để trống!');
        return;
      }
    },
    xacNhanThemThietBi: function (data) {
      console.log('xacNhanThemThietBi: ', data);
      let loai_hatang = data.loai_hatang;
      if (loai_hatang === 'BACKUP_THIETBI') {
        data.data.forEach(item => {
          this.thietBiItemList.push({
            ID: item.ID,
            MATHIETBI: item.MATHIETBI,
            THIETBI_ID: item.THIETBI_ID,
            F_NGAY_CN: item.F_NGAY_CN,
            NGUOI_CN: this.userInfo.nhanhVienTen
          })
        });
      } else if (loai_hatang === 'BACKUP_TUDIA') {
        data.data.forEach(item => {
          this.tuDiaItemList.push(item)
        });
        this.tuDiaItemList.forEach((item, index) => {
          item.SOTHUTU = index + 1;
        });
      }
      this.$refs.grdItems_thietbi.hideModal();
      return;
    },
    grid_onCommandClicked_thietBi: function (name, dataItem) {
      if (!this.controls.txtMaHaTangInfra.enabled) {
        return;
      }
      if (name === 'XOA') {
        this.thietBiItemList = this.thietBiItemList.filter(item => !(item.ID === dataItem.ID && item.THIETBI_ID === dataItem.THIETBI_ID));
      }
    },
    grid_onCommandClicked_thietbi_tudia: function (name, dataItem) {
      if (!this.controls.txtMaHaTangInfra.enabled) {
        return;
      }
      if (name === 'XOA') {
        this.tuDiaItemList = this.tuDiaItemList.filter(item => !(item.ID === dataItem.ID && item.THIETBI_ID === dataItem.THIETBI_ID));
      }
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

@media (min-width: 1200px) {
  .modal-xl {
    max-width: 80% !important;
  }
}

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
