<template src="./index.html"></template>

<script>

export default {
  props: {
    p_auto: {
      type: Boolean,
      required: false,
      default: false
    },
    p_so_phieu: {
      required: false
    },
    p_phieu_yeucau: {
      required: false
    },
    p_so_card_ip: {
      required: false
    },
    p_donvi_cap: {
      required: false
    },
    p_capphat_id: {
      required: false
    },
    p_capphat_mayao_id: {
      required: false
    },
    p_hdtb_id: {
      required: false
    },
    p_ten_khachhang: {
      required: false
    }
  },
  data: function () {
    return {
      nguon_yeucau: 2,
      dataSource: [],
      dsSelected: [],
      dsLoaiHaTangLuuTru: [],
      dsLoaiLuuTru :[],
      dsHaTangLuuTru: [],
      dsBienDong: [],
      colsBienDong: [
        { fieldName: 'LOAI_BIENDONG', headerText: 'Loại biến động', allowFiltering: true},
        { fieldName: 'MAPHIEU', headerText: 'Mã phiếu', allowFiltering: true },
        { fieldName: 'DUNGLUONG', headerText: 'Dung lượng', allowFiltering: true },
        { fieldName: 'NGAY_CAP', headerText: 'Ngày cấp', allowFiltering: true }
      ],
      dsMayAo: [],
      colsMayAo: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true , width: 50},
        { fieldName: 'TENVM', headerText: 'Tên VM', allowFiltering: true },
        { fieldName: 'TEN_SPDV', headerText: 'SPDV', allowFiltering: true },
        { fieldName: 'MAYAO_IP', headerText: 'Địa chỉ IP', allowFiltering: true },
        { fieldName: 'RAM', headerText: 'RAM', allowFiltering: true },
        { fieldName: 'CPU', headerText: 'CPU', allowFiltering: true },
        { fieldName: 'NGUOI_QLY', headerText: 'Người quản trị', allowFiltering: true },
        { fieldName: 'DONVI_QLY', headerText: 'Đơn vị quản trị', allowFiltering: true },
        { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', allowFiltering: true }
      ],
      dsHistory: [],
      colsHistory: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: false , width: 50},
        { fieldName: 'TACDONG', headerText: 'Tác động', allowFiltering: true },
        { fieldName: 'FIELD', headerText: 'Trường thông tin thay đổi', allowFiltering: true },
        { fieldName: 'OLD_VALUE', headerText: 'Giá trị cũ', allowFiltering: true },
        { fieldName: 'NEW_VALUE', headerText: 'Giá trị mới', allowFiltering: true },
        { fieldName: 'NGUOI_THUCHIEN', headerText: 'Người thực hiện', allowFiltering: true },
        { fieldName: 'THOI_DIEM', headerText: 'Thời điểm', allowFiltering: true }
      ],
      dsPhieuYeuCau: [],
      userInfo: {
        phanVungId: 0,
        nhanVienId: 0,
        nhanVienTen: '',
        donViId: 0,
        donViTen: '',
        donViChaId: 0,
        donViChaTen: ''
      },
      currentTab: 1,
      currentItem: {
        id: 0,
        ten : null,
        phieuYeuCauId : 0,
        linkBackup: null,
        loaiHaTangLuuTruId: 0,
        haTangLuuTruId: 0,
        loaiLuuTruId: 0,
        dungLuong: 0,
        khaNangCapPhat :0,
        ghiChu: null,
        hieuLuc: 1,
        donViQuanLyId: 0,
        donViQuanLy: null,
        phongBanQuanLyId: 0,
        phongBanQuanLy: null,
        nguoiQuanLyId: 0,
        nguoiQuanLy: null
      },
      controls: {
        txtTen: {
          enabled: false,
          invalid: false,
        },
        cboLoaiHaTang: {
          enabled: false,
          invalid: false,
        },
        txtGhiChu: {
          enabled: false,
        },
        chkHieuLuc: {
          enabled: false,
        },
        txtDungLuong:{
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
  mounted() {
    this.dsLoaiHaTangLuuTru = [
        {ID: 1, TEN: "Tủ đĩa"},
        {ID: 2, TEN: "Tự dựng"},
      ];
      this.dsLoaiLuuTru=[
        {ID: 1, TEN: "SSD"},
        {ID: 2, TEN: "HDD"}
      ];
  },
  computed: {
    grdItems: function () {
      return this.$refs.grdItems;
    },
  },
  watch: {
    "currentItem.id": function (value) {
      this.buttons.btnEdit.enabled = value > 0 && this.nguon_yeucau == 2;
      this.buttons.btnDelete.enabled = value > 0  && this.nguon_yeucau == 2;
    }
  },
  created: async function () {
    try {
      this.$root.showLoading(true);

      this.dataSource = await this.apiListDanhSach();
      // init user Info
      this.userInfo.nhanVienId = await this.$root.token.getNhanVienID();
      this.userInfo.nhanVienTen =this.$root.context.user.getProperty('ten_nv');
      this.userInfo.donViId = this.$root.context.user.getProperty('donvi_id');
      this.userInfo.donViTen = this.$root.context.user.getProperty('ten_dv');
      this.userInfo.donViChaId = this.userInfo.donViId;
      this.userInfo.donViChaTen = this.userInfo.donViTen;
      let ds= await this.apiGetDonViCha(this.userInfo.donViId);
      if (ds!=null &&  ds.length>0){
           this.userInfo.donViChaId=ds[0].DONVI_ID;
           this.userInfo.donViChaTen=ds[0].TEN_DV;
      }
      this.dsPhieuYeuCau = await this.apiListPhieuYeuCau();
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
      console.log('setPropsData')
    },
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

    apiBienDong: async function(pId) {
      let result = [];
      if (pId==null || pId<1) {
        return result;
      }
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/tai-nguyen-nfs/bien-dong", {id:pId});
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiMayAo: async function(pId) {
      let result = [];
      if (pId==null || pId<1) {
        return result;
      }
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/tai-nguyen-nfs/may-ao", {id:pId});
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListHaTangLuuTru: async function(typeId) {
      let result = [];
      try {
        // tủ đĩa
        if (typeId == 1) {
          let rs = await this.$root.context.get("/web-ecms/danhmuc/luutru-tudia");
          result = rs.data;
        }
        // tự dựng
        else {
          let rs = await this.$root.context.get("/web-ecms/danhmuc/luutru-tudung");
          result = rs.data;
        }
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiHistory: async function(pId) {
      let result = [];
      if (pId==null || pId<1) {
        return;
      }
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/tai-nguyen-nfs/history", {id:pId});
        result = rs.data;
      } catch(e) {
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
    apiListDanhSach: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/tai-nguyen-nfs/danh-sach");
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiSave: async function (para) {
      let result = false;
      try {
        let postData ={
          id: para.id,
          ten: para.ten,
          link_backup: para.linkBackup,
          phieuyeucau_id: para.phieuYeuCauId,
          loaihatang_luutru: para.loaiHaTangLuuTruId,
          hatang_luutru_id: para.haTangLuuTruId,
          loailuutru: para.loaiLuuTruId,
          nangluc: para.khaNangCapPhat,
          dungLuong: para.dungLuong,
          ghiChu: para.ghiChu,
          hieuLuc: para.hieuLuc,
          nguoiQlyId: para.nguoiQuanLyId,
          phongBanQlyId:  para.phongBanQuanLyId,
          donViQlyId: para.donViQuanlyId
        };

        let rs = await this.$root.context.post("/web-ecms/danhmuc/tai-nguyen-nfs/cap-nhat", postData );
        result = rs.error_code=='BSS-00000000';
      } catch(e) {
          let msgErr="Lỗi : \n";
          if (e.response.data.message_detail==null)
          {
            if (e.response.data.data==null){
              msgErr=e.response.data.message;
            }else{
              let jsonObj=e.response.data.data;
              for (var key  in jsonObj) {
                msgErr+=" - " + jsonObj[key] + "\n";
              }
            }
          }else{
            msgErr=e.response.data.message_detail;
          }
          this.$root.toastError(msgErr);
          result =false;
      }
      return result;
    },
    apiDelete: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/tai-nguyen-nfs/xoa", {
          id: id,
        });
        result = rs.error_code=='BSS-00000000';
      } catch(e) {
        this.$root.toastError(e.response.data.message_detail);
            result =false;
      }
      return result;
    },
    apiListPhieuYeuCau: async function() {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/phieu-yeucau");
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    loaiLuuTruOnChange: async function(id) {
      this.dsHaTangLuuTru = await this.apiListHaTangLuuTru(id);
    },
    haTangLuuTruOnChange: function(id) {
      this.currentItem.khaNangCapPhat = this.dsHaTangLuuTru.filter(item => item.ID == id)[0].KHANANGCAP;
    },
    dungLuongValid: function(value) {
      if (this.currentItem.dungLuong > this.currentItem.khaNangCapPhat) {
        this.currentItem.dungLuong = 0;
        this.$toast.error("Dung lượng cấp phải nhỏ hơn khả năng cấp phát.")
      }
      return;
    },
    enableControls: function (args) {
      this.controls.txtTen.enabled = args;
      this.controls.txtGhiChu.enabled = args;
      this.controls.chkHieuLuc.enabled = args;
      this.controls.txtDungLuong.enabled = true;
      this.buttons.btnSave.enabled = args;
      this.buttons.btnAddNew.enabled = !args;
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled;
      this.buttons.btnDelete.enabled = !args;

      if (this.currentItem.id > 0){
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
        this.controls.txtDungLuong.enabled = false;
      }
    },
    onShowData : async function (pId) {
      if (pId > 0) {
        try {
        this.$root.showLoading(true);
        this.dsBienDong=  await this.apiBienDong(pId);
        this.dsMayAo= await this.apiMayAo(pId);
        this.dsHistory=   await this.apiHistory(pId);
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    setCurrentItem: function (item) {
      this.currentItem.id = item.ID;
      this.currentItem.ten = item.TEN;
      this.currentItem.maPhieu=item.MA_PHIEU;
      this.currentItem.phieuYeuCauId=item.PHIEUYEUCAU_ID;
      this.currentItem.linkBackup= item.LINK_BACKUP,
      this.currentItem.loaiHaTangLuuTruId= item.LOAIHATANG_LUUTRU,
      this.currentItem.haTangLuuTruId=item.HATANG_LUUTRU_ID,
      this.currentItem.loaiLuuTruId=item.LOAI_LUUTRU_ID,
      this.currentItem.dungLuong=item.DUNGLUONG,
      this.currentItem.khaNangCapPhat=item.NANGLUC,
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
      this.currentItem.donViQuanlyId= item.DONVIQLY_ID;
      this.currentItem.phongBanQuanLyId= item.PHONGBANQLY_ID;
      this.currentItem.nguoiQuanLyId= item.NGUOIQLY_ID;
      this.currentItem.donViQuanly= item.DONVI_QLY;
      this.currentItem.phongBanQuanLy= item.PHONGBAN_QLY;
      this.currentItem.nguoiQuanLy= item.NGUOI_QLY;
      this.onShowData(item.ID);
      if (this.dsHaTangLuuTru.length==0){
        this.loaiLuuTruOnChange();
      }

    },
    setCurrentItemDefault: function () {
      this.currentItem.id = 0;
      this.currentItem.ten = null;
      this.currentItem.phieuYeuCauId= 0;
      this.currentItem.linkBackup= null,
      this.currentItem.loaiHaTangLuuTruId= 0,
      this.currentItem.haTangLuuTruId=0,
      this.currentItem.loaiLuuTruId=0,
      this.currentItem.dungLuong=0,
      this.currentItem.khaNangCapPhat=0,
      this.currentItem.ghiChu = null;
      this.currentItem.hieuLuc =1;
      this.currentItem.nguoiQuanLyId= this.userInfo.nhanVienId;
      this.currentItem.nguoiQuanLy=this.userInfo.nhanVienTen;
      this.currentItem.phongBanQuanLyId= this.userInfo.donViId;
      this.currentItem.phongBanQuanLy= this.userInfo.donViTen;
      this.currentItem.donViQuanLyId= this.userInfo.donViChaId;
      this.currentItem.donViQuanLy= this.userInfo.donViChaTen;
      this.dsAoHoa=[];
      this.dsBienDong=[];
      this.dsHistory=[];
    },

    validateControls: function () {
      let flg = true;
      if (this.currentItem.ten == "" || this.currentItem.ten == null) {
        this.$root.toastError("Tên NFS không được để trống.");
        flg = false;
      }
      if (this.currentItem.dungLuong > this.currentItem.khaNangCapPhat) {
        this.$root.toastError("Giá trị cấp phát không vượt quá khả năng cấp.");
        flg = false;
      }
      return flg;
    },

    grdItems_RowSelected: async function (args) {
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
      this.setCurrentItemDefault();
      this.enableControls(true);
    },
    onSave: async function (args) {
      if (this.nguon_yeucau == 1) {
        this.$emit('xacNhanNfs', this.currentItem)
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
              this.$root.showLoading(true);
              let msg = this.currentItem.id<1 ? 'Thêm mới thông tin thành công.' : 'Cập nhật thông tin thành công.';
              let rs = await this.apiSave(this.currentItem);
              if (rs) {
                  this.dataSource = await this.apiListDanhSach();
                  this.$root.toastSuccess(msg);
              }
              this.enableControls(!rs);
            } finally {
              this.$root.showLoading(false);
            }
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
