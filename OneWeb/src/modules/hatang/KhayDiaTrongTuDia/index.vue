<template src="./index.template.html"></template>

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
        donvi_ten: '',
        donvi_cha_id: 0,
        donvi_cha_ten: ''
      },
      current_item: {
        id: 0,
        thietbi_id: 0,
        thietbi_ma: '',
        mathietbi: '',
        sothutu: 0,
        ten: '',
        kyhieu: '',
        serial_number: '',
        ip_mgmt_id: 0, 
        ip_controller_id: 0, 
        ip_controller_name: '', 
        ghi_chu: '',
        hieu_luc: 1,
        hdd_tong: 0,
        hdd_capphat: 0,
        ssd_tong: 0,
        ssd_capphat: 0,
        trangthaisd: 0,
        maduan: '',
        tenduan: '',
        sothe: '',
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
      lichsu_thaydoi_items: [],
      select_trangthai_sudung: [],
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
      this.buttons.btnEdit.enabled = value > 0;
      this.buttons.btnDelete.enabled = value > 0;
    },
    "current_item.hdd_tong": function(value) {
      if (value < 0 || value < this.current_item.hdd_capphat) {
        this.$root.toastError('Số lượng HDD tổng phải lớn hơn số đã cắm và lớn hơn hoặc bằng 0.');
        this.current_item.hdd_tong = value < this.current_item.hdd_capphat ? this.current_item.hdd_capphat : 0;
      }
    },
    "current_item.ssd_tong": function(value) {
      if (value < 0 || value < this.current_item.ssd_capphat) {
        this.$root.toastError('Số lượng SSD tổng phải lớn hơn số đã cắm và lớn hơn hoặc bằng 0.');
        this.current_item.ssd_tong = value < this.current_item.ssd_capphat ? this.current_item.ssd_capphat : 0;
      }
    },
    "current_item.hdd_capphat": function(value) {
      if (value > this.current_item.hdd_tong || value < 0) {
        this.$root.toastError('Số lượng HDD đã cắm phải bé hơn tổng số lượng và lớn hơn hoặc bằng 0.');
        this.current_item.hdd_capphat = value < 0 ? 0 : this.current_item.hdd_tong;
      }
    },
    "current_item.ssd_capphat": function(value) {
      if (value > this.current_item.ssd_tong || value < 0) {
        this.$root.toastError('Số lượng SSD đã cắm phải bé hơn tổng số lượng và lớn hơn hoặc bằng 0.');
        this.current_item.ssd_capphat = value < 0 ? 0 : this.current_item.ssd_tong;
      }
    }
  },
  created: async function () {
    try {
      this.$root.showLoading(true);

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
      if (ds != null &&  ds.length > 0){
           this.user_info.donvi_cha_id = ds[0].DONVI_ID;
           this.user_info.donvi_cha_ten = ds[0].TEN_DV;
      }
      // init selection items
      this.select_trangthai_sudung = [
        {ID: 1, TEN: "Đang sử dụng"},
        {ID: 2, TEN: "Chưa sử dụng"},
        {ID: 3, TEN: "Hỏng"},
      ];

      // init current data
      let data = await this.apiDetailById(this.$route.params.id);
      this.setCurrentItem(data);
      this.lichsu_thaydoi_items = await this.apiListHistory(this.$route.params.id);
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    apiDetailById: async function(id) {
      let result = [];
      if (id==null || id<1) {
        this.$toast.error('Khay đĩa không tồn tại/đã xoá.');
        return;
      }
      try {
        let rs = await this.$root.context.get(`/web-ecms/khaydia-tudia/detail/${id}`);
        if (Array.isArray(rs.data) && rs.data.length > 0) {
          result = rs.data[0];
        } else {
          this.$toast.error('Khay đĩa trong tủ đĩa không tồn tại.');
        }
      }catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiGetDonViCha: async function(pId) {
      let result = [];
      if (pId==null || pId<1) {
        return;
      }
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/don-vi-cha", {donViId:pId});
        result = rs.data;
      }catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListHistory: async function(id) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/khaydia-tudia/change-history", {id:id});
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiSave: async function (args) {
      let result ;
      try {
        result = await this.$root.context.post("/web-ecms/khaydia-tudia/create-or-update", {
          id: Number(args.id),
          thietbi_id: Number(args.thietbi_id),
          mathietbi: args.mathietbi,
          sothutu: Number(args.sothutu),
          ten: args.ten,
          kyhieu: args.kyhieu,
          serial_number: args.serial_number,
          ip_mgmt_id: Number(args.ip_mgmt_id), 
          ip_controller_id: Number(args.ip_controller_id), 
          ghi_chu: args.ghi_chu,
          hieu_luc: args.hieu_luc,
          hdd_tong: Number(args.hdd_tong),
          hdd_capphat: Number(args.hdd_capphat),
          ssd_tong: Number(args.ssd_tong),
          ssd_capphat: Number(args.ssd_capphat),
          trangthaisd: Number(args.trangthaisd),
          maduan: args.maduan,
          tenduan: args.tenduan,
          sothe: args.sothe,
          donviqly_id: Number(args.donviqly_id),
          phongbanqly_id: Number(args.phongbanqly_id),
          nguoiqly_id: Number(args.nguoiqly_id),
          nhanvien_id: Number(args.nhanvien_id),
          may_capnhap: args.may_capnhap,
          nguoi_capnhap: args.nguoi_capnhap,
          ip_capnhap: args.ip_capnhap,
        });
      } catch(e) {
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

      if (this.current_item.id > 0) {
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
      }
    },
    setCurrentItem: async function (item) {
      this.current_item.id = item.id,
      this.current_item.thietbi_id = item.thietbi_id,
      this.current_item.thietbi_ma = item.thietbi_ma,
      this.current_item.mathietbi = item.mathietbi,
      this.current_item.sothutu = item.sothutu,

      this.current_item.ten = item.ten,
      this.current_item.kyhieu = item.kyhieu,
      this.current_item.serial_number = item.serial_number,
      this.current_item.ip_mgmt_id = item.ip_mgmt_id, 
      this.current_item.ip_controller_id = item.ip_controller_id, 
      this.current_item.ip_controller_name = item.ip_controller_name, 
      this.current_item.ghi_chu = item.ghi_chu,
      this.current_item.hieu_luc = item.hieu_luc
      this.current_item.hdd_tong = item.hdd_tong,
      this.current_item.hdd_capphat = item.hdd_capphat,
      this.current_item.ssd_tong = item.ssd_tong,
      this.current_item.ssd_capphat = item.ssd_capphat,
      this.current_item.trangthaisd = item.trangthaisd,
      this.current_item.maduan = item.maduan,
      this.current_item.tenduan = item.tenduan,
      this.current_item.sothe = item.sothe,

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
    validateControls: function () {
      let flg = true;
      if (this.current_item.kyhieu == '' || this.current_item.kyhieu == null) {
        this.$root.toastError('Ký hiệu thiết bị không được để trống.');
        flg = false;
      }
      if (this.current_item.ten == '' || this.current_item.ten == null) {
        this.$root.toastError('Tên thiết bị không được để trống.');
        flg = false;
      }
      return flg;
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
            let rs = await this.apiSave(this.current_item);
            if (rs.data > 0) {
                this.$root.toastSuccess('Cập nhật thông tin thành công.');
                this.lichsu_thaydoi_items = await this.apiListHistory(this.current_item.id);
                this.enableControls(!rs);
            } else if (rs.data == 0) {
                this.$root.toastError('Đối tượng thao tác không tồn tại.');
            } else {
              this.$root.toastError('Chức năng lỗi.');
            }
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    onCancel: async function () {
      // get item by id
      let data = await this.apiDetailById(this.current_item.id);
      this.setCurrentItem(data);
      this.enableControls(false);
    },
    onEdit: async function (args) {
      this.current_item.donviqly_id = this.user_info.donvi_cha_id,
      this.current_item.donviqly_name = this.user_info.donvi_cha_ten,
      this.current_item.phongbanqly_id = this.user_info.donvi_id,
      this.current_item.phongbanqly_name = this.user_info.donvi_ten,
      this.current_item.nguoiqly_id = this.user_info.nhanvien_id,
      this.current_item.nguoiqly_name = this.user_info.nhanvien_ten,
      this.enableControls(true);
    },
    validInput: function(type) {
      if (type == 'HDD' && this.current_item.hdd_capphat > this.current_item.hdd_tong) {
        this.$toast.error("Số lượng HDD cắm phải bé hơn tổng số lượng.")
        this.current_item.hdd_capphat = 0;
      } else if (type == 'SSD' && this.current_item.ssd_capphat > this.current_item.ssd_tong) {
        this.$toast.error("Số lượng SSD cắm phải bé hơn tổng số lượng.")
        this.current_item.ssd_capphat = 0;
      }
      return;
    }
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
.e-toolbar .e-tbar-btn-text,
.e-toolbar .e-toolbar-items .e-toolbar-item .e-tbar-btn-text {
  display: none;
}
</style>
