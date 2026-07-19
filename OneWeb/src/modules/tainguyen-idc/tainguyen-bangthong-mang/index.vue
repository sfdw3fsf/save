<template src="./index.html"></template>

<script>
import CapPhatBangthongMang from './capphat-bangthong-mang/index.vue';
import ThuHoiBangthongMang from './thuhoi-bangthong-mang/index.vue';
import ThongTinCongViec from '../components/ThongTinCongViec/index.vue'
import DataGrid from '@/components/Controls/DataGrid/index.vue';
import ModalChuyenXuly from './popup-chuyen-xuly/index.vue';

export default {
  components: {
    ThongTinCongViec,
    CapPhatBangthongMang,
    ThuHoiBangthongMang,
    DataGrid,
    ModalChuyenXuly
  },
  data: function () {
    return {
        //Thông tin user
      userInfo: {
        phanVungId: 0,
        nhanVienId: 0,
        nhanVienTen: '',
        donViId: 0,
        donViTen: '',
        donViChaId: 0,
        donViChaTen: ''
      },
      thongTinPhieutc: {},
      currentPhieuItem: {
        id: 0,
        maCv: '',
        tenDv: '',
        nhanVienTH:'',
        ngayTao: '',
        tenCv: '',
        trangThai: '',
        maCvCha: '',
        tenKhachHang: '',
        maThueBao: '',
        ghiChu: '',
        hieuLuc: 1,
      },
      selectedIDC: null,
      selectedLayout: 1,
      layoutOptions: [],
      idcOptions: [],
      trangThaiList: [
        {
          id: 27,
          ten: 'Hoàn thành'
        },{
          id: 26,
          ten: 'Trả phiếu'
        }
      ],
      dataGrid1Columns: [
        {fieldName: 'MA_CV', headerText: 'Mã CV', textAlign: 'Left', width: 100},
        {fieldName: 'TEN_PHIEU', headerText: 'Phiếu YC', textAlign: 'Left', width: 100},
        {fieldName: 'HANG_MUC', headerText: 'Hạng mục triển khai', textAlign: 'Left', width: 100},
        {fieldName: 'LOAI_CV', headerText: 'Loại công việc', textAlign: 'Left', width: 100},
        {fieldName: 'DON_VI', headerText: 'Đơn vị thực hiện', textAlign: 'Left', width: 100},
        {fieldName: 'CA_NHAN_TH', headerText: 'Cá nhân thực hiện', textAlign: 'Left', width: 100},
        {fieldName: 'PHOI_HOP_TH', headerText: 'Phối hợp thực hiện', textAlign: 'Left', width: 100},
        {fieldName: 'THOI_GIAN_TH', headerText: 'Thời gian thực hiện', textAlign: 'Left', width: 100},
        {fieldName: 'TRANG_THAI_TH', headerText: 'Trạng thái thực hiện', textAlign: 'Left', width: 100}
      ],
       //Bảng lịch sử thay đổi
      dsHistory: [],
      colsHistory: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: false, width: 50 },
        { fieldName: 'TACDONG', headerText: 'Tên tác động', allowFiltering: true },
        { fieldName: 'FIELD', headerText: 'Trường thông tin thay đổi', allowFiltering: true },
        { fieldName: 'OLD_VALUE', headerText: 'Giá trị cũ', allowFiltering: true },
        { fieldName: 'NEW_VALUE', headerText: 'Giá trị mới', allowFiltering: true },
        { fieldName: 'NGUOI_THUCHIEN', headerText: 'Người thực hiện', allowFiltering: true },
        { fieldName: 'THOI_DIEM', headerText: 'Thời điểm', allowFiltering: true }
      ],
      controls: {
        txtTen: {
          enabled: false,
          invalid: false,
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
      currentMode: 'ADD'
    };
  },
  computed: {
    phieutc() {
      return Number(this.$route.query.phieutc)
    },
    isEditMode() {
      const isEditMode = this.$route.query.isEditMode;
      return isEditMode === 'true' || isEditMode === true;
    },
    showLayout1: function() {
      return this.selectedLayout === 1;
    },
    grdListOfWorks: function () {
      return this.$refs.grdListOfWorks;
    },
  },
  watch: {
    "currentItem.id": function (value) {
      this.buttons.btnEdit.enabled = value > 0;
      this.buttons.btnDelete.enabled = value > 0;
    },
    "currentItem.ten": function (value) {
      this.controls.txtTen.invalid = value == "";
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.userInfo.nhanVienId = await this.$root.token.getNhanVienID()
      this.userInfo.nhanVienTen = this.$root.context.user.getProperty('ten_nv')
      this.userInfo.donViId = this.$root.context.user.getProperty("donvi_id");
      this.userInfo.donViTen = this.$root.context.user.getProperty('ten_dv')
      this.userInfo.donViChaId = this.userInfo.donViId
      this.userInfo.donViChaTen = this.userInfo.donViTen

      let ds = await this.apiGetDonViCha(this.userInfo.donViId)
      if (ds != null && ds.length > 0) {
        this.userInfo.donViChaId = ds[0].DONVI_ID
        this.userInfo.donViChaTen = ds[0].TEN_DV
      }

      this.thongTinPhieutc = await this.getThongTinPhieutc();
      if (this.thongTinPhieutc.LOAICV_ID == 44) {
        this.selectedLayout = 2
      } else {
        this.selectedLayout = 1
      }

      // this.setCurrentItemDefault();
      // this.enableControls(true);
      this.layoutOptions = await this.buildLayoutOptions();
      this.idcOptions = await this.buildIDCOptions();
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    buildLayoutOptions: async function () {
      return [
        { ID: 1, TEN: 'Cấp phát băng thông mạng' },
        { ID: 2, TEN: 'Thu hồi băng thông mạng' },
      ];
    },

    // enableControls: function (args) {
    //   for (const key in this.controls) {
    //     if (this.controls.hasOwnProperty(key)) {
    //       this.controls[key].enabled = args;
    //     }
    //   }

    //   this.buttons.btnSave.enabled = args;
    //   this.buttons.btnAddNew.enabled = !args;
    //   this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled;
    //   this.buttons.btnDelete.enabled = !args;

    //   if (this.currentItem.id > 0)
    //     this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
    // },
    // setCurrentItem: function (item) {
    //   this.currentItem.id = item.id || 0;
    //   this.currentItem.ten = item.ten || '';
    //   this.currentItem.ghiChu = item.ghiChu || '';
    //   this.currentItem.hieuLuc = item.hieuLuc || 1;
    // },
    // setCurrentItemDefault: function () {
    //   this.setCurrentItem({
    //     id: 0,
    //     ten: '',
    //     ghiChu: '',
    //     hieuLuc: 1,
    //   });
    // },
    validateControls: function () {
      let msg = "";
      if (this.currentItem.ten == null || this.currentItem.ten == "") {
        msg = "Tên không được để trống.";
      }
      if (msg != "") { 
        this.$root.toastError(msg);
        return false;
      }
      return true;
    },
    onSave: async function () {
      if (this.thongTinPhieutc.TRANGTHAI_ID == 21) {
        let rs = await this.$refs.thongTinCongViec.onSave()
        if (rs) {
          await this.loadPhieuThiCongDetail(this.phieutc)
        }
      }
      if (this.thongTinPhieutc.TRANGTHAI_ID != 21) {
        if (this.thongTinPhieutc.LOAICV_ID == 44) {
          this.$refs.thuHoiBtm.onAddNew(); // gọi hàm con
        } else {
          this.$refs.capPhatBtm.onAddNew(); // gọi hàm con
        }
      }
    },
    async onHoanThanh() {
      if (true) {
        let flag = await this.$confirm(
          "Bạn có muốn lưu thông tin này không ?",
          "Xác nhận",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        ).then(async() => {
            this.$root.showLoading(true);

            await this.$root.context.post(
              `/web-ecms/ha-tang/thongtin-u/phieuthicong/${this.phieutc}/capnhat-trangthai?trangThaiId=24&nguoiCn=${this.userInfo.nhanVienTen}`
            );

            // Redirect sau khi lưu xong
            if (this.thongTinPhieutc.LOAICV_ID == 44) {
              this.$router.push({
                path: "ketqua-thuhoi-btm"
              });            
            } else {
              this.$router.push({
                path: "ketqua-capphat-btm"
              });
            }

          })
          .catch((e) => {
            this.$root.toastError("Lưu thất bại");
            this.$root.showLoading(false);
            return false;
          });
        if (flag) {
          try {
            this.$root.showLoading(true);
            this.$root.toastSuccess("Lưu thành công");
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    onXacNhanThucHien() {
      this.$confirm('Xác nhận thực hiện phiếu thi công này?', {
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Huỷ',
        type: 'warning'
      }).then(async () => {
        let rs = await this.$refs.thongTinCongViec.xacNhanThucHien()
        if (rs) {
          this.thongTinPhieutc.TRANGTHAI_ID = 22;
          if (this.thongTinPhieutc.LOAICV_ID == 44) {
            await this.$refs.thuHoiBtm.reloadCapPhatList()           
          } else {
            await this.$refs.capPhatBtm.reloadCapPhatList()           
          }
          await this.loadPhieuThiCongDetail(this.phieutc)
        }
      })
    },

    async onTraPhieu() {
      if (true) {
        let flag = await this.$confirm(
          "Bạn có muốn lưu thông tin này không ?",
          "Xác nhận",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        ).then(async() => {
            this.$root.showLoading(true);

            await this.$root.context.post(
              `/web-ecms/ha-tang/thongtin-u/phieuthicong/${this.phieutc}/capnhat-trangthai?trangThaiId=26&nguoiCn=${this.userInfo.nhanVienTen}`
            );

            // Redirect sau khi lưu xong
            if (this.thongTinPhieutc.LOAICV_ID == 44) {
              this.$router.push({
                path: "ketqua-thuhoi-btm"
              });            
            } else {
              this.$router.push({
                path: "ketqua-capphat-btm"
              });
            }
          })
          .catch((e) => {
            this.$root.toastError("Lưu thất bại");
            this.$root.showLoading(false);
            return false;
          });
        if (flag) {
          try {
            this.$root.showLoading(true);
            this.$root.toastSuccess("Lưu thành công");
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    onChuyenThucHien() {
      this.$confirm('Xác nhận chuyển phiếu thi công sang trạng thái Đang thực hiện?', {
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Huỷ',
        type: 'warning'
      }).then(async () => {
        let rs = await this.$refs.thongTinCongViec.chuyenThucHien()
        if (rs) {
          this.thongTinPhieutc.TRANGTHAI_ID = 81;
          await this.loadPhieuThiCongDetail(this.phieutc);
        }
      })
    },

    apiGetDonViCha: async function (pId) {
      let result = []
      if (pId == null || pId < 1) {
        return
      }
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/don-vi-cha', { donViId: pId })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    buildIDCOptions: async function () {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/ha-tang/thongtin-u/idcList/${this.userInfo.donViId}`)
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    getThongTinPhieutc: async function () {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/ha-tang/thongtin-u/phieutc/${this.phieutc}`)
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    onChuyenXuLy: function() {
      this.$refs.ModalChuyenXuly.show(this.thongTinPhieutc, 'ADD', this.phieutc);
    },
    chuyenXylySuccess: async function() {
      // Redirect sau khi lưu xong
      if (this.thongTinPhieutc.LOAICV_ID == 44) {
        this.$router.push({
          path: "ketqua-thuhoi-btm"
        });            
      } else {
        this.$router.push({
          path: "ketqua-capphat-btm"
        });
      }
    },
    async loadPhieuThiCongDetail(phieutcId) {
      this.isLoadingDetail = true;
      try {
        this.$root.showLoading(true)
        const rs = await this.$root.context.post('/web-ecms/idc/danhsach-congviec/chitiet/' + phieutcId)
        this.phieuThiCongDetail = rs.data[0] || {}
      } catch (error) {
        this.$root.toastError(error?.message_detail || 'Có lỗi xảy ra khi lấy thông tin phiếu thi công')
      } finally {
        this.$root.showLoading(false);
        this.isLoadingDetail = false;
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

.form-section {
  margin: 20px 0;
}

.datagrid-section {
  margin: 20px 0;
}
</style>