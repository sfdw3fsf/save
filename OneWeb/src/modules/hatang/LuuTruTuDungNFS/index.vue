<template src="./index.html"></template>

<script>
import ModalNfsSmb from './modal-ds-tai-nguyen/index.vue';

export default {
  components: {
    ModalNfsSmb,
  },
  data: function () {
    return {
      user_info: {
        nhanvien_id: 0,
        nhanvien_may: '',
        nhanvien_ten: '',
        nhanvien_ip: '',
        phanvung_id: 0,
        donvi_id: 0,
        donvi_ten: ''
      },
      image_item: '',
      current_item: {
        id: 0,
        mahatang_infra: '',
        thanhphan: '',
        hatang_congnghe_id: 0,
        ip_cungcapdv_id: 0,
        ip_quantri_id: 0,
        ghi_chu: '',
        hieu_luc: 1,
        hdd_tong: 0,
        hdd_nangluc: 0,
        hdd_capphat: 0,
        ssd_tong: 0,
        ssd_nangluc: 0,
        ssd_capphat: 0,
        iops_tong: 0,
        iops_nangluc: 0,
        iops_capphat: 0,
        donviqly_id: 0,
        donviqly_name: '',
        phongbanqly_id: 0,
        phongbanqly_name: '',
        nguoiqly_id: 0,
        nguoiqly_name: '',
        nhanvien_id: 0,
        may_capnhap: '',
        nguoi_capnhap: '',
        ip_capnhap: ''
      },
      item_list: [],
      may_ao_items: [],
      hatang_aohoa_smartcloud_items: [],
      nfs_smb_items: [],
      thietbi_items: [],
      lichsu_thaydoi_items: [],
      select_hatang_congnghe: [],
      select_ip_cungcapdv: [],
      select_ip_quantri: [],
      recommentDataList: [],
      controls: {
        text: {
          enabled: false,
          invalid: false,
        },
        selection: {
          enabled: false,
          invalid: false,
        },
        checkbox: {
          enabled: false
        },
        btn: {
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
      this.buttons.btnEdit.enabled = value > 0;
      this.buttons.btnDelete.enabled = value > 0;
    },
    "current_item.iops_nangluc": function (value) {
      if (value > this.current_item.iops_tong) {
        this.$root.toastError("Khả năng cấp IOPS phải nhỏ hơn tổng IOPS.")
        this.current_item.iops_nangluc = this.current_item.iops_tong;
      }
    }
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      // all items smart cloud
      this.item_list = await this.apiListItems();

      // init current user Info
      this.user_info.nhanvien_id = await this.$root.token.getNhanVienID();
      this.user_info.nhanvien_ten = await this.$root.context.user.getTenNhanVien();
      this.user_info.nhanvien_may = await this.$root.token.getMachine();
      this.user_info.nhanvien_ip = await this.$root.token.getIP();
      this.user_info.phanvung_id = await this.$root.token.getPhanVungID();
      this.user_info.donvi_id = await this.$root.token.getDonViID();
      this.user_info.donvi_ten = await this.$root.context.user.getTenDonVi();

      this.select_hatang_congnghe = await this.apiListHaTangCongNghe();
      this.select_ip_cungcapdv = await this.apiListIPCungCapDV();
      this.select_ip_quantri = await this.apiListIPQuanTri();
    } finally {
      this.reGetIPQuanTri();
      this.reGetIPCungCapDV();
      this.$root.showLoading(false);
    }
  },
  methods: {
    apiListItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/luutru-tudung-nfs/get-all");
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      } finally {
        this.recommentDataList = [];
        result.forEach((item) => { this.recommentDataList.push(item.thanhphan) });
      }
      return result;
    },
    apiListHistory: async function (id) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/luutru-tudung-nfs/change-history", { id: id });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListMayao: async function (id) {
      let result = [];
      try {
        let rs = await this.$root.context.get(`/web-ecms/hatang/luutru-tudung-nfs/may-ao/${id}`);
        result = rs.data;
      } catch (e) {
        this.$root.toastError(e.response.data.message_detail);
      }
      return result;
    },
    apiListHaTangAoHoaSmartCloud: async function (id) {
      let result = [];
      try {
        let rs = await this.$root.context.get(`/web-ecms/hatang/luutru-tudung-nfs/hatang-aohoa/${id}`);
        result = rs.data;
      } catch (e) {
        this.$root.toastError(e.response.data.message_detail);
      }
      return result;
    },
    apiListNFSSMB: async function (id) {
      let result = [];
      try {
        let rs = await this.$root.context.get(`/web-ecms/hatang/luutru-tudung-nfs/nfs-smb/${id}`);
        result = rs.data;
      } catch (e) {
        this.$root.toastError(e.response.data.message_detail);
      }
      return result;
    },
    apiListThietBi: async function (id) {
      let result = [];
      try {
        let rs = await this.$root.context.get(`/web-ecms/hatang/luutru-tudung-nfs/thiet-bi/${id}`);
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail);
      }
      return result;
    },
    apiListHaTangCongNghe: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-cong-nghe");
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    reGetIPCungCapDV: async function () {
      const select_ip_cungcapdv = await this.apiListIPCungCapDV();
      this.select_ip_cungcapdv = select_ip_cungcapdv.filter((item) => item.HATANG_LUU_TRU_ID === this.current_item.id || (item.DA_GAN_MAYAO !== 1 && item.DA_GAN_THIETBI_KHAC !== 1 && item.DA_GAN_HATANG_BACKUP !== 1 && item.DA_GAN_HATANG_LUU_TRU !== 1));
    },
    apiListIPCungCapDV: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/thong-tin-thiet-bi/0/thiet-bi-ip");
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    reGetIPQuanTri: async function () {
      const select_ip_quantri = await this.apiListIPCungCapDV();
      this.select_ip_quantri = select_ip_quantri.filter((item) => item.HATANG_LUU_TRU_ID === this.current_item.id || (item.DA_GAN_MAYAO !== 1 && item.DA_GAN_THIETBI_KHAC !== 1 && item.DA_GAN_HATANG_BACKUP !== 1 && item.DA_GAN_HATANG_LUU_TRU !== 1));
      console.log("reGetIPQuanTri", this.select_ip_quantri[0]);
    },
    apiListIPQuanTri: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/thong-tin-thiet-bi/0/thiet-bi-ip");
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiSave: async function (args) {
      let result;
      try {
        result = await this.$root.context.post("/web-ecms/hatang/luutru-tudung-nfs/create-or-update", {
          id: Number(args.id),
          mahatang_infra: args.mahatang_infra,
          thanhphan: args.thanhphan,
          hatang_congnghe_id: Number(args.hatang_congnghe_id),
          ip_cungcapdv_id: Number(args.ip_cungcapdv_id),
          ip_quantri_id: Number(args.ip_quantri_id),
          ghi_chu: args.ghi_chu,
          hieu_luc: Number(args.hieu_luc),
          hdd_tong: Number(args.hdd_tong),
          hdd_nangluc: Number(args.hdd_nangluc),
          hdd_capphat: Number(args.hdd_capphat),
          ssd_tong: Number(args.ssd_tong),
          ssd_nangluc: Number(args.ssd_nangluc),
          ssd_capphat: Number(args.ssd_capphat),
          iops_tong: Number(args.iops_tong),
          iops_nangluc: Number(args.iops_nangluc),
          iops_capphat: Number(args.iops_capphat),
          donviqly_id: Number(args.donviqly_id),
          phongbanqly_id: Number(args.phongbanqly_id),
          nguoiqly_id: Number(args.nguoiqly_id),
          nhanvien_id: Number(args.nhanvien_id),
          may_capnhap: args.may_capnhap,
          nguoi_capnhap: args.nguoi_capnhap,
          ip_capnhap: args.ip_capnhap
        });
      } catch (e) {
        result = false;
      }
      return result;
    },
    apiDelete: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/luutru-tudung-nfs/delete", { id: id });
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

      if (this.current_item.id > 0)
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
    },
    setCurrentItem: async function (item) {
      this.current_item.id = item.id;
      this.current_item.mahatang_infra = item.mahatang_infra;
      this.current_item.thanhphan = item.thanhphan;
      this.current_item.hatang_congnghe_id = item.hatang_congnghe_id;
      this.current_item.ip_cungcapdv_id = item.ip_cungcapdv_id;
      this.current_item.ip_quantri_id = item.ip_quantri_id;
      this.current_item.ghi_chu = item.ghi_chu;
      this.current_item.hieu_luc = item.hieu_luc;
      this.current_item.hdd_tong = item.hdd_tong;
      this.current_item.hdd_nangluc = item.hdd_nangluc;
      this.current_item.hdd_capphat = item.hdd_capphat;
      this.current_item.ssd_tong = item.ssd_tong;
      this.current_item.ssd_nangluc = item.ssd_nangluc;
      this.current_item.ssd_capphat = item.ssd_capphat;
      this.current_item.iops_tong = item.iops_tong;
      this.current_item.iops_nangluc = item.iops_nangluc;
      this.current_item.iops_capphat = item.iops_capphat;
      this.current_item.donviqly_id = item.donviqly_id;
      this.current_item.donviqly_name = item.donviqly_name;
      this.current_item.phongbanqly_id = item.phongbanqly_id;
      this.current_item.phongbanqly_name = item.phongbanqly_name;
      this.current_item.nguoiqly_id = item.nguoiqly_id;
      this.current_item.nguoiqly_name = item.nguoiqly_name;

      this.current_item.nhanvien_id = this.user_info.nhanvien_id;
      this.current_item.may_capnhap = this.user_info.nhanvien_may;
      this.current_item.nguoi_capnhap = this.user_info.nhanvien_ten;
      this.current_item.ip_capnhap = this.user_info.nhanvien_ip;
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        id: 0,
        mahatang_infra: '',
        thanhphan: '',
        hatang_congnghe_id: 0,
        ip_cungcapdv_id: 0,
        ip_quantri_id: 0,
        ghi_chu: '',
        hieu_luc: 1,
        hdd_tong: 0,
        hdd_nangluc: 0,
        hdd_capphat: 0,
        ssd_tong: 0,
        ssd_nangluc: 0,
        ssd_capphat: 0,
        iops_tong: 0,
        iops_nangluc: 0,
        iops_capphat: 0,
        donviqly_id: this.user_info.donvi_id,
        donviqly_name: this.user_info.donvi_ten,
        phongbanqly_id: this.user_info.donvi_id,
        phongbanqly_name: this.user_info.donvi_ten,
        nguoiqly_id: this.user_info.nhanvien_id,
        nguoiqly_name: this.user_info.nhanvien_ten,
      });
    },
    validateControls: function () {
      let flg = true;
      if (this.current_item.thanhphan == '') {
        this.$root.toastError('Thành phần lưu trữ không được để trống.');
        flg = false;
      }
      if (this.current_item.hatang_congnghe_id == '') {
        this.$root.toastError('Công nghệ không được để trống.');
        flg = false;
      }
      return flg;
    },
    grdItems_RowSelected: async function (args) {
      try {
        this.$root.showLoading(true);
        this.setCurrentItem(args.data);
        this.lichsu_thaydoi_items = await this.apiListHistory(args.data.id);

        this.may_ao_items = await this.apiListMayao(args.data.id);
        this.hatang_aohoa_smartcloud_items = await this.apiListHaTangAoHoaSmartCloud(args.data.id);
        this.thietbi_items = await this.apiListThietBi(args.data.id);
        this.nfs_smb_items = await this.apiListNFSSMB(args.data.id);
        this.reGetIPCungCapDV();
        this.reGetIPQuanTri();

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
          this.lichsu_thaydoi_items = await this.apiListHistory(items[0].id);
          this.may_ao_items = await this.apiListMayao(items[0].id);
          this.hatang_aohoa_smartcloud_items = await this.apiListHaTangAoHoaSmartCloud(items[0].id);
          this.thietbi_items = await this.apiListThietBi(items[0].id);
          this.nfs_smb_items = await this.apiListNFSSMB(items[0].id);
        } finally {
          this.$root.showLoading(false);
        }
      } else {
        this.setCurrentItemDefault();
        this.lichsu_thaydoi_items = [];
        this.may_ao_items = [];
        this.hatang_aohoa_smartcloud_items = [];
        this.thietbi_items = [];
        this.nfs_smb_items = [];
      }
    },
    onAddNew: async function () {
      this.setCurrentItemDefault();
      this.lichsu_thaydoi_items = [];
      this.may_ao_items = [];
      this.hatang_aohoa_smartcloud_items = [];
      this.thietbi_items = [];
      this.nfs_smb_items = [];
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
            let mess = this.current_item.id == 0 ? 'Thêm mới thông tin thành công.' : 'Cập nhật thông tin thành công.';
            this.$root.showLoading(true);
            let rs = await this.apiSave(this.current_item);
            if (rs.data > 0) {
              this.$root.toastSuccess(mess);
              this.item_list = await this.apiListItems();
              this.enableControls(!rs);
            } else if (rs.data == 0) {
              this.$root.toastError('Đối tượng thao tác không tồn tại.');
            } else if (rs.data == -1) {
              this.$root.toastError('Thành phần lưu trữ đã tồn tại.');
            } else if (rs.data == -2) {
              this.$root.toastError('Mã hạ tầng lưu trữ đã tồn tại.');
            } else if (rs.data == -3) {
              this.$root.toastError('Thành phần và Mã hạ tầng lưu trữ đã tồn tại.');
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
        if (this.current_item.id == 0) {
          item = items[0];
        }
        else {
          item = items.find(x => x.id == this.current_item.id);
        }
      }
      if (item != null) {
        this.setCurrentItem(item);
        this.lichsu_thaydoi_items = await this.apiListHistory(item.id);
        this.may_ao_items = await this.apiListMayao(item.id);
        this.hatang_aohoa_smartcloud_items = await this.apiListHaTangAoHoaSmartCloud(item.id);
        this.thietbi_items = await this.apiListThietBi(item.id);
        this.nfs_smb_items = await this.apiListNFSSMB(item.id);
      } else {
        this.setCurrentItemDefault();
        this.lichsu_thaydoi_items = [];
        this.may_ao_items = [];
        this.hatang_aohoa_smartcloud_items = [];
        this.thietbi_items = [];
        this.nfs_smb_items = [];
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
            isSuccess = await this.apiDelete(item.id);
            if (!isSuccess) { break; }
          }
          if (isSuccess) {
            this.setCurrentItemDefault();
            this.item_list = await this.apiListItems();
            this.enableControls(false);
            this.$root.toastSuccess("Xóa thông tin thành công");
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    onFiltering: function (e) {
      const query = e.text.toLowerCase();
      e.updateData(this.recommentDataList.filter(item => item.toLowerCase().includes(query)));
    },
    openDialogNfsSmb(typeModal) {
      this.$refs.dialogNfsSmb.open(typeModal, this.current_item.id);
    },
    saveSourceSuccess: async function (typeModal) {
      if (typeModal == 'VM') {
        this.may_ao_items = await this.apiListMayao(this.current_item.id);
      }
      else if (typeModal == 'V_SMARTCLOUD') {
        this.hatang_aohoa_smartcloud_items = await this.apiListHaTangAoHoaSmartCloud(this.current_item.id);
      }
      else if (typeModal == 'NFS') {
        this.nfs_smb_items = await this.apiListNFSSMB(this.current_item.id);
      }
      else if (typeModal == 'DEVICE') {
        this.thietbi_items = await this.apiListThietBi(this.current_item.id);
      }
    },
    getHieuLucText: function (value) {
      return value == 1 ? "Hiệu lực" : "Không hiệu lực";
    },
    getColumnHieuLucTemplate: function (parent) {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return parent.getHieuLucText(this.data.HIEU_LUC);
              },
            },
          },
        };
      };
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

.info-row .key.w50 {
  width: 50px
}

@media (min-width: 1200px) {
  .modal-xl {
    max-width: 80% !important;
  }
}
</style>
