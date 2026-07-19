<template src="./index.html"></template>

<script>


export default {
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
        phieu_id: 0,
        sophieu: null,
        socu_thuhoi: "",
        trangthai_yeucau: null,
        hoanthanh_enable: 0,
        spdv_id: 0,
        spdv: '',
        spdv_name: "",
        cumdichvu_id: 0,
        cumdichvu_name: '',
        donvi_yeucau_id: 0,
        donvi_yeucau_name: '',
        nguoigui_id: 0,
        nguoigui_name: '',
        f_ngay_gui: '',
        f_ngay_hoanthanh: "",
        f_ngay_thuhoi: "",
        avatar: "",
        ghi_chu: "",
        ykien_xacnhan: '',
        ngattainguyen: 0,
        f_ngattainguyen: '',
        ykien_thuchien_thuhoi: '',
        trangthai_thuhoi: 0,
        trangthai_thuhoi_ip: 0,
        path_file: '',
        download_file: '',
        name_file: '',
        nhanvien_id: 0,
        may_capnhap: '',
        nguoi_capnhap: '',
        ip_capnhap: ''
      },
      yeucau_thuhoi_items: [],
      thuhoi_mayao_items: [],
      select_trangthai_thuhoi_ip: [],
      buttons: {
        btnAddImage: '',
        imgAvatar: 'hidden',
        btnComplete: {
          enabled: false,
        },
        btnRequestIPRecall: {
          enabled: false,
        },
      },
    };
  },
  computed: {
    grdItems: function () {
      return this.$refs.grdItems;
    }
  },
  // watch: {
  // },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.thuhoi_mayao_items = await this.apiListItems();
      this.select_trangthai_thuhoi_ip = [
        {ID: 1, TEN: "Đang thu hồi"},
        {ID: 2, TEN: "Đã thu hồi"},
      ]

    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    apiListItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/thu-hoi-ip/get-all");
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListYeuCauThuHoi: async function(id) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/thu-hoi-ip/danhsach-yeucau/"+id);
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiHoanThanhThuHoiMayAo: async function(id, trangthai_thuhoi) {
      let result = [];
      try {
        const trangThaiDaThuHoiIP = 2;
        let rs = await this.$root.context.get("/web-ecms/hatang/thu-hoi-ip/hoanthanh-thuhoi-ip"
        , {id: id, trangthai_thuhoi: trangThaiDaThuHoiIP});
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiDownFile: async function(path_file) {
          let result = {};
          try {
            let rs = await this.$root.context.post("/web-ecms/storage/getPresignedUrl", { fileSource: path_file });
            result = rs.data;
          } catch(e) {
          }
          return result;
    },
    setCurrentItem: async function (item) {
      this.current_item.id = item.id;
      this.current_item.phieu_id = item.phieu_id;
      this.current_item.sophieu = item.sophieu;
      this.current_item.socu_thuhoi = item.socu_thuhoi;
      this.current_item.trangthai_yeucau = item.trangthai_yeucau
      this.current_item.hoanthanh_enable = item.hoanthanh_enable
      this.current_item.spdv_id = item.spdv_id
      this.current_item.spdv = item.spdv
      this.current_item.spdv_name = item.spdv_name
      this.current_item.cumdichvu_id = item.cumdichvu_id
      this.current_item.cumdichvu_name = item.cumdichvu_name
      this.current_item.donvi_yeucau_id = item.donvi_yeucau_id
      this.current_item.donvi_yeucau_name = item.donvi_yeucau_name
      this.current_item.nguoigui_id = item.nguoigui_id
      this.current_item.nguoigui_name = item.nguoigui_name
      this.current_item.f_ngay_gui = item.f_ngay_gui
      this.current_item.f_ngay_hoanthanh = item.f_ngay_hoanthanh
      this.current_item.f_ngay_thuhoi = item.f_ngay_thuhoi
      this.current_item.attachment = item.attachment
      this.current_item.ghi_chu = item.ghi_chu
      this.current_item.ykien_xacnhan = item.ykien_xacnhan
      this.current_item.ngattainguyen = item.ngattainguyen
      this.current_item.f_ngattainguyen = item.f_ngattainguyen
      this.current_item.ykien_thuchien_thuhoi = item.ykien_thuchien_thuhoi
      this.current_item.trangthai_thuhoi = item.trangthai_thuhoi
      this.current_item.trangthai_thuhoi_ip = item.trangthai_thuhoi_ip
      this.current_item.yeucau_ip_enable = item.yeucau_ip_enable
      this.current_item.path_file = item.path_file
      this.current_item.name_file = item.name_file
      
      this.current_item.nhanvien_id = this.user_info.nhanvien_id;
      this.current_item.may_capnhap = this.user_info.nhanvien_may;
      this.current_item.nguoi_capnhap = this.user_info.nhanvien_ten;
      this.current_item.ip_capnhap = this.user_info.nhanvien_ip;

    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        id: 0,
        phieu_id: 0,
        sophieu: null,
        socu_thuhoi: "",
        trangthai_yeucau: 0,
        hoanthanh_enable: 0,
        spdv_id: 0,
        spdv: '',
        spdv_name: "",
        cumdichvu_id: 0,
        cumdichvu_name: '',
        donvi_yeucau_id: 0,
        donvi_yeucau_name: '',
        nguoigui_id: 0,
        nguoigui_name: '',
        f_ngay_gui: '',
        f_ngay_hoanthanh: "",
        f_ngay_thuhoi: "",
        path_file: '',
        download_file: '',
        name_file: '',
        avatar: "",
        ghi_chu: "",
        ykien_xacnhan: '',
        ngattainguyen: 0,
        f_ngattainguyen: '',
        ykien_thuchien_thuhoi: '',
        trangthai_thuhoi: 0,
        trangthai_thuhoi_ip: 0,
        yeucau_ip_enable: 0,
        nhanvien_id: 0,
        may_capnhap: '',
        nguoi_capnhap: '',
        ip_capnhap: ''
      });
      this.image_item = '';
    },
    validateControls: function () {
      let flg = true;
      return flg;
    },
    grdItems_RowSelected: async function (args) {
      try {
        this.$root.showLoading(true);      
        this.buttons.btnComplete.enabled = (args.data.hoanthanh_enable == 1);
        this.buttons.btnRequestIPRecall.enabled = (args.data.yeucau_ip_enable == 1);
        this.yeucau_thuhoi_items = await this.apiListYeuCauThuHoi(args.data.id);
        if (args.data.path_file) {
          let file_data = await this.apiDownFile(args.data.path_file);
          this.current_item.download_file = file_data.result;
        }
        this.setCurrentItem(args.data);
      } finally {
        this.$root.showLoading(false);
      }

    },
    grdItems_RowDeselected: async function () {
      let items = this.grdItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        try {
          this.$root.showLoading(true);
          this.buttons.btnComplete.enabled = (items[0].hoanthanh_enable == 1);
          this.buttons.btnRequestIPRecall.enabled = (items[0].yeucau_ip_enable == 0);
          this.yeucau_thuhoi_items = await this.apiListYeuCauThuHoi(items[0].id);
          if (items[0].path_file) {
            let file_data = await this.apiDownFile(items[0].path_file);
            this.current_item.download_file = file_data.result;
          }
          this.setCurrentItem(items[0]);
        } finally {
          this.$root.showLoading(false);
        }
      } else {
        this.setCurrentItemDefault();
        this.yeucau_thuhoi_items = [];
        this.buttons.btnComplete.enabled = false;
        this.buttons.btnRequestIPRecall.enabled = false;
      }
    },
    onComplete: async function (args) {
      if (this.current_item.trangthai_thuhoi_ip != 1 && this.current_item.trangthai_thuhoi_ip != 2) {
          this.$root.toastError("Trạng thái thu hồi IP là bắt buộc.")
          return;
      }
      let flag = await this.$confirm("Bạn có muốn hoàn thành thu hồi máy ảo này không?","Xác nhận",
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
            let rs = await this.apiHoanThanhThuHoiMayAo(this.current_item.id, this.current_item.trangthai_thuhoi_ip);
            if (rs > 0) {
              this.thuhoi_mayao_items = await this.apiListItems();
              this.$root.toastSuccess('Hoàn thành thu hồi ip thành công');
            } else {
              this.$root.toastError('Chức năng lỗi.');
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
.e-grid *{
  font-size:13px !important;
}
.e-grid.e-default.e-bothlines .e-headercell, .e-grid.e-default.e-bothlines .e-detailheadercell {
  border-width: 1px 1px 0px 0px !important;
}
.info-row .key.w180 {
  width: 180px
}
.info-row .key.w50 {
  width: 50px
}
@media (min-width: 1200px){
  .modal-xl {
    max-width: 80% !important;
  }
}

.file-item {
  position: relative;
}

/* Image Styles */
.file-image {
  width: 30px;
  height: 30px;
  object-fit: cover;
  border-radius: 8px;
}

/* Close Icon Styles */
.close-icon {
  position: absolute;
  top: -8px;
  right: -8px;
  width: 18px;
  height: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: white;
  border-radius: 50%;
  color: black;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  transition: color 0.3s, background-color 0.3s;
}

.close-icon:hover {
  color: #1e90ff; /* Blue color */
  background-color: rgba(30, 144, 255, 0.2);
}
</style>
