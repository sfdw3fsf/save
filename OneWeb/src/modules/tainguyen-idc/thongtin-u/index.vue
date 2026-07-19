<template src="./index.html"></template>

<script>
import ModalThongtinThietBi from './popup-thongtin-thietbi/index.vue';

export default {
  components: {
    ModalThongtinThietBi
  },
  props: {
    uId: {
      type: Number,
      required: false,
      default: 0
    }
  },
  data: function () {
    return {      
      dsTrangThai: [
        {
          TEN: 'Đã sử dụng',
          ID: 1
        },
        {
          TEN: 'Chưa sử dụng(còn trống)',
          ID: 2
        },
        {
          TEN: 'Đang lắp đặt',
          ID: 3
        }
      ],
      //Bảng máy ảo
      dsThongTinU: [],

      colsThongTinU: [
        { fieldName: 'RACKUNIT_ID', headerText: 'ID U(*)', allowFiltering: true, width: 80},
        { fieldName: 'RACK_ID', headerText: 'ID Rack(*)', allowFiltering: true, width: 90 },
        { fieldName: 'TEN_RACK', headerText: 'Tên Rack(*)', allowFiltering: true },
        { fieldName: 'LOAI_RACK', headerText: 'Loại Rack(*)', allowFiltering: true },
        { fieldName: 'MAT_SAN', headerText: 'Mặt sàn(*)', allowFiltering: true },
        { fieldName: 'PHONG_ZONE', headerText: 'Phòng/Zone', allowFiltering: true },
        { fieldName: 'VITRI', headerText: 'Vị trí U', allowFiltering: true, width: 80},
        { fieldName: 'TRANGTHAI_U', headerText: 'Trạng thái U', allowFiltering: true, width: 150 },
      ],

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
      //Biến tab
      currentTab: 1,
      currentSubTab: 1,
      currentTab2: 1,
      //Item được chọn
      currentItem: {
        rackunit_id: null,
        rack_id: '',
        ten_rack: '',
        loai_rack: null,
        ghi_chu: null,
        mat_san: null,
        phong_zone: null,
        vitri: null,
        trangthai_u: null,
        trangthai_id: null,
        thietbi_id: null
      },
      //Form controls
      controls: {
        selectedTrangThaiU: {
          invalid: false,
          enabled: false
        }
      },
      //Nút
      buttons: {
        btnSave: {
          enabled: false
        },
        btnEdit: {
          enabled: true
        }
      },
    }
  },
  computed: {
    grdItems: function () {
      return this.$refs.grdItems
    }
  },
  watch: {
    'currentItem.rackunit_id': function (value) {
      this.buttons.btnEdit.enabled = value > 0
    }
  },
  created: async function () {
    try {
      this.$root.showLoading(true)
      // init user Info
      this.userInfo.nhanVienId = await this.$root.token.getNhanVienID()
      this.userInfo.nhanVienTen = this.$root.context.user.getProperty('ten_nv')
      this.userInfo.donViId = this.$root.context.user.getProperty('donvi_id')
      this.userInfo.donViTen = this.$root.context.user.getProperty('ten_dv')
      this.userInfo.donViChaId = this.userInfo.donViId
      this.userInfo.donViChaTen = this.userInfo.donViTen
      let ds = await this.apiGetDonViCha(this.userInfo.donViId)
      if (ds != null && ds.length > 0) {
        this.userInfo.donViChaId = ds[0].DONVI_ID
        this.userInfo.donViChaTen = ds[0].TEN_DV
      }
      this.currentItem.ten_dv = this.userInfo.donViChaTen
      this.currentItem.ten_phongban = this.userInfo.donViTen
      this.currentItem.ten_nguoiqly = this.userInfo.nhanVienTen

      
      this.dsThongTinU = await this.apiListDanhSach()

    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
     async setPropsData() {
      console.log('setPropsData')
    },

    //Lấy ds đơn vị quản lý
    getDsDonVi: async function (param) {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/danhmuc/find-don-vi', param)
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    //Lấy ds cá nhân quản lý
    getDsCaNhan: async function (id) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/danhmuc/nhan-vien-don-vi?donViId=${id}`)
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
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

    apiListDanhSach: async function () {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/ha-tang/thongtin-u')        
        result = rs.data
        if (this.uId > 0) {
          result = result.filter(x => x.RACKUNIT_ID === this.uId)
        }
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    apiThongTinThietBi: async function (rackunit_id) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/ha-tang/thongtin-u/thietbi-from-u/${rackunit_id}`)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    apiSave: async function (param) {
      let result = false
      try {
        let postData = {
          trangthaiId: parseInt(param.trangthai_id)
        }

        result = await this.$root.context.post(`/web-ecms/ha-tang/thongtin-u/${param.rackunit_id}`, postData)
        result = true
        .then(res => {
          console.log('Update ok', res.data)
        })
        .catch(err => {
          console.error('Update error', err)
        })
      } catch (e) {
        console.log(e)
      }
      return result
    },

    async onNew(args) {
      try {
        // Lấy dữ liệu dòng từ Grid
        const rackunitId = args.rowData?.RACKUNIT_ID;
        if (!rackunitId) {
          this.$toast.error("Không tìm thấy RACKUNIT_ID trong dòng được chọn");
          return;
        }

        // Gọi API lấy thông tin thiết bị
        const thietbi = await this.apiThongTinThietBi(rackunitId);
        if (!thietbi || (Array.isArray(thietbi) && thietbi.length === 0)) {
          console.error("Không có dữ liệu thiết bị cho RACKUNIT_ID:", rackunitId);
          this.$toast.error("Không có dữ liệu thiết bị");
          return;
        }
        // Hiển thị modal với dữ liệu thiết bị
        this.$refs.ModalThongtinThietBi.show(thietbi[0], "ADD");
      } catch (error) {
        console.error("Lỗi khi lấy thông tin thiết bị:", error);
        this.$toast.error("Không thể tải thông tin thiết bị");
      }
    },

    onQueryCellInfo(args) {
      if (args.column.headerText === "Thao tác") {
        const btn = args.cell.querySelector(".custom-btn");
        if (btn) {
          if (!args.data.THIETBI_ID) {
            btn.classList.add("disabled-btn");
            btn.setAttribute("title", "Không có dữ liệu thiết bị");   // 👈 tooltip khi disable
          } else {
            btn.classList.remove("disabled-btn");
            btn.setAttribute("title", "Thông tin thiết bị");       // 👈 tooltip khi enable
          }
        }
      }
    },

    enableControls: function (args) {
      this.controls.selectedTrangThaiU.enabled = args

      this.buttons.btnSave.enabled = args

      if (this.currentItem.rackunit_id > 0) {
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled
      }
    },

    setCurrentItem: async function (item) {
      const trangthai_id = this.dsTrangThai.find(x => x.TEN === item.TRANGTHAI_U)?.ID ?? null;

      this.currentItem.rackunit_id = item.RACKUNIT_ID
      this.currentItem.rack_id = item.RACK_ID
      this.currentItem.ten_rack = item.TEN_RACK
      this.currentItem.loai_rack = item.LOAI_RACK
      this.currentItem.mat_san = item.MAT_SAN
      this.currentItem.phong_zone = item.PHONG_ZONE
      this.currentItem.trangthai_u = trangthai_id
      this.currentItem.trangthai_id = trangthai_id
      this.currentItem.vitri = item.VITRI
      this.currentItem.ghi_chu = item.GHI_CHU
    },

    setCurrentItemDefault: async function () {
      this.currentItem.rackunit_id = null
      this.currentItem.rack_id = null
      this.currentItem.ten_rack = null
      this.currentItem.loai_rack = null
      this.currentItem.mat_san = null
      this.currentItem.phong_zone = null
      this.currentItem.trangthai_u = null
      this.currentItem.trangthai_id = null
      this.currentItem.vitri = null
      this.currentItem.hatangidg_id = null
      
      this.currentItem.nguoiqly_id = this.userInfo.nhanVienId
      this.currentItem.ten_nguoiqly = this.userInfo.nhanVienTen
      this.currentItem.phongbanqly_id = this.userInfo.donViId
      this.currentItem.ten_phongban = this.userInfo.donViTen
      this.currentItem.donviqly_id = this.userInfo.donViChaId
      this.currentItem.ten_dv = this.userInfo.donViChaTen
    },

    validateControls: function () {
      let msg = ''
      

      if (msg != '') this.$root.toastError(msg)
      return msg == ''
    },

    grdItems_RowSelected: async function (args) {
      if (this.buttons.btnSave.enabled) {
        return
      }
      this.setCurrentItem(args.data)
    },

    grdItems_RowDeselected: async function (args) {
      if (this.buttons.btnSave.enabled) {
        return
      }
      setTimeout(() => {
        let items = this.grdItems.getSelectedRecords()
        if (!(items == null || items.length == 0)) {
          this.setCurrentItem(items[0])
        } else {
          this.setCurrentItemDefault()
        }
      }, 300)
    },

    onAddNew: async function (args) {
      await this.setCurrentItemDefault()
      this.enableControls(true)
    },

    onSave: async function (args) {
      if (this.validateControls()) {
        let flag = await this.$confirm('Bạn có muốn lưu thông tin này không ?', 'Xác nhận', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info',
          dangerouslyUseHTMLString: true
        })
          .then((s) => true)
          .catch((e) => {
            return false
          })
        if (flag) {
          try {
            this.$root.showLoading(true)
            let rs = await this.apiSave(this.currentItem)
          
            if (rs) {
              this.dsThongTinU = await this.apiListDanhSach()
              if (this.currentItem.rackunit_id == 0) {
                this.$root.toastSuccess('Thêm mới thông tin thành công ')
              } else {
                this.$root.toastSuccess('Cập nhật thông tin thành công ')
              }
            }
            this.enableControls(!rs)
          } finally {
            this.$root.showLoading(false)
          }
        }
      }
    },

   
    onEdit: async function (args) {
      this.enableControls(true)
    },
  }
}
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type='checkbox']:checked:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type='checkbox']:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
}

.custom-btn {
  background-color: transparent !important;
  color: blue !important;
  border: none !important;
}

.disabled-btn {
  opacity: 0.5;            /* mờ đi */
  cursor: not-allowed;     /* đổi con trỏ chuột */
  color: grey !important;
}
</style>
