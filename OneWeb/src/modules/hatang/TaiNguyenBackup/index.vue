<template src="./index.html"></template>

<script>
import DialogMayAo from '../ComponentThemMayAo/index.vue'

export default {
  components: {
    DialogMayAo
  },
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
      user_info: {
        nhanvien_id: 0,
        nhanvien_may: '',
        nhanvien_ten: '',
        nhanvien_ip: '',
        phanvung_id: 0,
        donvi_id: 0,
        donvi_ten: '',
        donvi_cha_id: 0,
        donvi_cha_ten: ''
      },
      current_item: {
        id: 0,
        ma: '',
        ten: '',
        hatangbackup_id: 0,
        backup_loai_id: 0,
        tansuat_id: '',
        ip_backup_id: 0,
        link_backup: '',
        phieuyeucau_id: 0,
        phieuyeucau_name: '',
        ghi_chu: '',
        hieu_luc: 1,
        loaihatang_luutru: 0,
        hatang_luutru_id: 0,
        nangluc: 0,
        loailuutru: 0,
        dungluong: 0,
        donviqly_id: 0,
        donviqly_name: '',
        phongbanqly_id: 0,
        phongbanqly_name: '',
        nguoiqly_id: 0,
        nguoiqly_name: '',
        nhanvien_id: 0,
        may_capnhap: '',
        nguoi_capnhap: '',
        ip_capnhap: '',
        has_caphat_backup: 0
      },
      item_list: [],
      may_ao_items: [],
      lichsu_thaydoi_items: [],
      backup_biendong_items: [],
      select_hatang_backup: [],
      select_ip_backup: [],
      select_loai_backup: [],
      select_tansuat_backup: [],
      select_loai_hatang_luutru: [],
      select_loai_luutru: [],
      select_hatang_luutru: [],
      select_phieuyeucau: [],
      recommentDataList: [],
      mayao_remove_ids: [],
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
        },
        dungluong: {
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
      this.buttons.btnEdit.enabled = value > 0 && this.nguon_yeucau == 2;
      this.buttons.btnDelete.enabled = value > 0 && this.nguon_yeucau == 2;
    }
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      // all items
      this.item_list = await this.apiListItems();

      // init current user Info
      this.user_info.nhanvien_id = await this.$root.token.getNhanVienID();
      this.user_info.nhanvien_ten = await this.$root.context.user.getTenNhanVien();
      this.user_info.nhanvien_may = await this.$root.token.getMachine();
      this.user_info.nhanvien_ip = await this.$root.token.getIP();
      this.user_info.phanvung_id = await this.$root.token.getPhanVungID();
      this.user_info.donvi_id = await this.$root.token.getDonViID();
      this.user_info.donvi_ten = await this.$root.context.user.getTenDonVi();

      this.user_info.donvi_cha_id = this.user_info.donvi_id;
      this.user_info.donvi_cha_ten = this.user_info.donvi_ten;
      let ds = await this.apiGetDonViCha(this.user_info.donvi_id);
      if (ds != null && ds.length > 0) {
        this.user_info.donvi_cha_id = ds[0].DONVI_ID;
        this.user_info.donvi_cha_ten = ds[0].TEN_DV;
      }


      // init selection items
      this.select_loai_hatang_luutru = [
        { ID: 1, TEN: "Tủ đĩa" },
        { ID: 2, TEN: "Tự dựng" },
      ];
      this.select_loai_luutru = [
        { ID: 1, TEN: "SSD" },
        { ID: 2, TEN: "HDD" },
      ];
      this.select_ip_backup = await this.apiListIPBackup();
      this.select_loai_backup = await this.apiListLoaiBackup();
      this.select_hatang_backup = await this.apiListHaTangBackup();
      this.select_tansuat_backup = await this.apiListTanSuatBackup();

    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    async setPropsData() {
      this.buttons.btnAddNew.enabled = false;
      this.buttons.btnSave.enabled = true;
      this.buttons.btnEdit.enabled = false;
      this.buttons.btnDelete.enabled = false;
      this.nguon_yeucau = this.p_auto ? 1 : 2
    },

    grid_onCommandClicked_thietbi(name, dataItem) {
      if (!this.buttons.btnSave.enabled) return
      if (name == 'XOA') {
        this.mayao_remove_ids.push(dataItem.mayao_id)
        this.may_ao_items = this.may_ao_items.filter(item => item.id !== dataItem.id)
      }
    },
    reloadData: async function () {
      try {
        this.$root.showLoading(true);
        setTimeout(async () => {
          this.mayao_remove_ids = [];
          this.may_ao_items = await this.apiListMayao(this.current_item.id);
        }, 1000);
      } finally {
        this.$root.showLoading(false);
      }
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
        let rs = await this.$root.context.get("/web-ecms/hatang/tainguyen-backup/get-all");
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      } finally {
        this.recommentDataList = [];
        result.forEach((item) => { this.recommentDataList.push(item.ten) });
      }
      return result;
    },
    apiListHistory: async function (id) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/tainguyen-backup/change-history", { id: id });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
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
    apiListBienDong: async function (id) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/tainguyen-backup/bien-dong", { id: id });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListHaTangBackup: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/hatang-backup");
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
    apiListTanSuatBackup: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/tansuat-backup");
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListPhieuYeuCau: async function (trangthai) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/tainguyen-backup/phieu-capphat", { trangthai: trangthai });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListIPBackup: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ip-cungcapdv");
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListHaTangLuuTru: async function (type) {
      let result = [];
      try {
        // tủ đĩa
        if (type == 1) {
          let rs = await this.$root.context.get("/web-ecms/danhmuc/luutru-tudia");
          result = rs.data;
        }
        // tự dựng
        else {
          let rs = await this.$root.context.get("/web-ecms/danhmuc/luutru-tudung");
          result = rs.data;
        }
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
          ma: args.ma,
          ten: args.ten,
          hatangbackup_id: Number(args.hatangbackup_id),
          backup_loai_id: Number(args.backup_loai_id),
          tansuat_id: Number(args.tansuat_id),
          ip_backup_id: Number(args.ip_backup_id),
          link_backup: args.link_backup,
          phieuyeucau_id: Number(args.phieuyeucau_id),
          ghi_chu: args.ghi_chu,
          hieu_luc: Number(args.hieu_luc),
          loaihatang_luutru: Number(args.loaihatang_luutru),
          hatang_luutru_id: Number(args.hatang_luutru_id),
          nangluc: Number(args.nangluc),
          loailuutru: Number(args.loailuutru),
          dungluong: Number(args.dungluong),
          donviqly_id: Number(args.donviqly_id),
          phongbanqly_id: Number(args.phongbanqly_id),
          nguoiqly_id: Number(args.nguoiqly_id),
          nhanvien_id: Number(args.nhanvien_id),
          may_capnhap: args.may_capnhap,
          nguoi_capnhap: args.nguoi_capnhap,
          ip_capnhap: args.ip_capnhap,
        });

        this.$root.context.post("/web-ecms/hatang/tainguyen-backup//xoa-mayao-backup", {
          backupId: this.current_item.id,
          mayAoIds: JSON.stringify(this.mayao_remove_ids)
        });
      } catch (e) {
        result = false;
      }
      return result;
    },
    apiDelete: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/tainguyen-backup/delete", { id: id });
        result = rs.error_code == 'BSS-00000000';
      } catch (e) {
        this.$root.toastError(e.response.data.message);
        result = false;
      }
      return result;
    },
    enableControls: function (args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key) && key != 'dungluong') {
          this.controls[key].enabled = args;
        }
      }
      if (this.current_item.id > 0) {
        this.controls.dungluong.enabled = false;
      } else {
        this.controls.dungluong.enabled = true;
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
      this.current_item.ma = item.ma;
      this.current_item.ten = item.ten;
      this.current_item.hatangbackup_id = item.hatangbackup_id;
      this.current_item.backup_loai_id = item.backup_loai_id;
      this.current_item.tansuat_id = item.tansuat_id;
      this.current_item.ip_backup_id = item.ip_backup_id;
      this.current_item.link_backup = item.link_backup;
      this.current_item.phieuyeucau_id = item.phieuyeucau_id;
      this.current_item.phieuyeucau_name = item.phieuyeucau_name;
      this.current_item.ghi_chu = item.ghi_chu;
      this.current_item.hieu_luc = item.hieu_luc;
      this.current_item.loaihatang_luutru = item.loaihatang_luutru;
      this.current_item.hatang_luutru_id = item.hatang_luutru_id;
      this.current_item.nangluc = item.nangluc;
      this.current_item.loailuutru = item.loailuutru;
      this.current_item.dungluong = item.dungluong;
      this.current_item.has_caphat_backup = item.has_caphat_backup;

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

      this.lichsu_thaydoi_items = await this.apiListHistory(item.id);
      this.may_ao_items = await this.apiListMayao(item.id);
      this.backup_biendong_items = await this.apiListBienDong(item.id);
      this.select_hatang_luutru = await this.apiListHaTangLuuTru(item.loaihatang_luutru);
      this.select_phieuyeucau = await this.apiListPhieuYeuCau(-1)
    },
    setCurrentItemDefault: async function () {
      this.setCurrentItem({
        id: 0,
        ma: '',
        ten: '',
        hatangbackup_id: 0,
        backup_loai_id: 0,
        tansuat_id: '',
        ip_backup_id: 0,
        link_backup: '',
        phieuyeucau_id: 0,
        phieuyeucau_name: '',
        ghi_chu: '',
        hieu_luc: 1,
        loaihatang_luutru: 0,
        hatang_luutru_id: 0,
        nangluc: 0,
        loailuutru: 0,
        dungluong: 0,
        has_caphat_backup: 0,
        donviqly_id: this.user_info.donvi_cha_id,
        donviqly_name: this.user_info.donvi_cha_ten,
        phongbanqly_id: this.user_info.donvi_id,
        phongbanqly_name: this.user_info.donvi_ten,
        nguoiqly_id: this.user_info.nhanvien_id,
        nguoiqly_name: this.user_info.nhanvien_ten,
      });
      this.lichsu_thaydoi_items = [];
      this.may_ao_items = [];
      this.backup_biendong_items = [];
      this.select_hatang_luutru = [];
      this.select_phieuyeucau = await this.apiListPhieuYeuCau(-1);
    },
    validateControls: function () {
      let flg = true;
      if (this.current_item.ma == '' || this.current_item.ma == null) {
        this.$root.toastError('Mã tài nguyên không được để trống.');
        flg = false;
      }
      if (this.current_item.ten == '' || this.current_item.ten == null) {
        this.$root.toastError('Tên tài nguyên không được để trống.');
        flg = false;
      }
      if (!(this.current_item.hatangbackup_id > 0)) {
        this.$root.toastError('Hạ tầng backup không được để trống.');
        flg = false;
      }
      if (!(this.current_item.backup_loai_id > 0)) {
        this.$root.toastError('Loại backup không được để trống.');
        flg = false;
      }
      if (this.current_item.nangluc < this.current_item.dungluong
        || (this.current_item.dungluong != 0 && this.current_item.nangluc == null)) {
        this.$root.toastError('Giá trị cấp phát không vượt quá khả năng cấp.');
        flg = false;
      }
      return flg;
    },
    grdItems_RowSelected: async function (args) {
      try {
        this.$root.showLoading(true);
        if (this.buttons.btnSave.enabled && this.nguon_yeucau == 2) return;
        const data = args.data.length ? args.data[0] : args.data 
        this.setCurrentItem(data);
      } finally {
        this.$root.showLoading(false);
      }
    },
    grdItems_RowDeselected: async function () {
      if (this.buttons.btnSave.enabled && this.nguon_yeucau == 2) return;
      let items = this.grdItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        try {
          this.$root.showLoading(true);
          this.setCurrentItem(items[0]);
        } finally {
          this.$root.showLoading(false);
        }
      } else {
        this.setCurrentItemDefault();
      }
    },
    onAddNew: async function () {
      this.select_phieuyeucau = await this.apiListPhieuYeuCau(4);
      this.setCurrentItemDefault();
      this.enableControls(true);
    },
    onSave: async function () {
      if (this.nguon_yeucau == 1) {
        this.$emit('xacNhanBackup', this.current_item)
      } else {
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
              } else if (rs.data == 0) {
                  this.$root.toastError('Đối tượng thao tác không tồn tại.');
              } else if (rs.data == -1){
                this.$root.toastError('Tên tài nguyên đã tồn tại.');
              } else if (rs.data == -2){
                this.$root.toastError('Mã tài nguyên đã tồn tại.');
              } else if (rs.data == -3){
                this.$root.toastError('Tên và mã tài nguyên đã tồn tại.');
              } else {
                this.$root.toastError('Chức năng lỗi.');
              }
              this.item_list = await this.apiListItems();
              this.enableControls(!rs);
            } finally {
              this.$root.showLoading(false);
            }
          }
        }
      }
    },
    onCancel: async function (args) {
      this.select_phieuyeucau = await this.apiListPhieuYeuCau(-1)
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
        this.backup_biendong_items = await this.apiListBienDong(item.id);
      } else {
        this.setCurrentItemDefault();
        this.lichsu_thaydoi_items = [];
        this.may_ao_items = [];
        this.backup_biendong_items = [];
      }
      this.enableControls(false);
    },
    onEdit: async function (args) {
      this.current_item.donviqly_id = this.user_info.donvi_cha_id;
      this.current_item.donviqly_name = this.user_info.donvi_cha_ten;
      this.current_item.phongbanqly_id = this.user_info.donvi_id;
      this.current_item.phongbanqly_name = this.user_info.donvi_ten;
      this.current_item.nguoiqly_id = this.user_info.nhanvien_id;
      this.current_item.nguoiqly_name = this.user_info.nhanvien_ten;
      this.enableControls(true);
    },
    onDelete: async function (args) {
      let items = this.grdItems.getSelectedRecords();
      if (items.length == 0) {
        return this.$root.toastError('Chọn mẫu tin muốn xóa!');
      }
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
    loaiLuuTruOnChange: async function (event) {
      this.select_hatang_luutru = await this.apiListHaTangLuuTru(event);
    },
    onFiltering: function (e) {
      const query = e.text.toLowerCase();
      e.updateData(this.recommentDataList.filter(item => item.toLowerCase().includes(query)));
    },
    haTangLuuTruOnChange: function (e) {
      this.current_item.nangluc = this.select_hatang_luutru.filter(item => item.ID == e)[0].KHANANGCAP;
      // this.current_item.dungluong = 0;
    },
    dungLuongValid: function (value) {
      if (this.current_item.dungluong > this.current_item.nangluc) {
        this.$toast.error("Dung lượng cấp phải nhỏ hơn khả năng cấp phát.")
        this.current_item.dungluong = 0;
      }
      return;
    },
    openDialogMayAo: function () {
      const refs = this.$refs.grdItems_may_ao;
      refs.dialogOpen();
    },
    xacNhanThemMayAo: function (data) {
      console.log('xacNhanThemMayAo', data);
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

.e-toolbar .e-tbar-btn-text,
.e-toolbar .e-toolbar-items .e-toolbar-item .e-tbar-btn-text {
  display: none;
}
</style>
