<template src="./index.html"></template>

<script>
import AohoaComponent from './TabAoHoa/index.vue' // import component Interface
import Containerk8sComponent from './TabContainerK8s/index.vue' // import component Power
import IdgComponent from './TabIDG/index.vue' // import component TabConsole
import BackupComponent from './TabBackup/index.vue'
import { YEU_CAU_CP_TABLES } from './constants'
import YeuCauMolai from './Modal/yeucau_molai.vue'
export default {
  components: {
    AohoaComponent,
    Containerk8sComponent,
    IdgComponent,
    BackupComponent,
    YeuCauMolai
  },
  data: function() {
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
      imageItem: '',
      dsAoHoaCP: [],
      colsAoHoaCP: YEU_CAU_CP_TABLES.AO_HOA_CP_COLS,
      dsContainerK8sCP: [],
      colsContainerK8sCP: YEU_CAU_CP_TABLES.CONTAINER_CP_COLS,
      dsIDGCP: [],
      colsIDGCP: YEU_CAU_CP_TABLES.IDG_CP_COLS,
      dsBackupCP: [],
      colsBackupCP: YEU_CAU_CP_TABLES.BACKUP_CP_COLS,
      dsYeuCauCP: [],
      colsYeuCauCP: YEU_CAU_CP_TABLES.YEU_CAU_CP_COLS,
      dsNgatTaiNguyen: [
        { id: 1, name: 'Đã ngắt' },
        { id: 2, name: 'Chưa ngắt' }
      ],
      aoHoaList: [],
      containerList: [],
      IDGList: [],
      backupList: [],
      currentItem: {
        id: 0,
        phieu_id: 0,
        sothutu: 0,
        sophieu: null,
        socu_thuhoi: '',
        socu_molai: '',
        trangthai_yeucau: null,
        trangthai_yeucau_id: 0,
        hoanthanh_mayao_enable: 0,
        spdv_id: 0,
        spdv: '',
        spdv_name: '',
        cum_dichvu: '',
        hatang_mang: '',
        mucdich_sudung: '',
        donvi_yeucau_id: 0,
        donvi_yeucau_name: '',
        nguoigui_id: 0,
        nguoigui_name: '',
        f_ngay_gui: '',
        f_ngay_hoanthanh: '',
        f_ngay_thuhoi: '',
        avatar: '',
        ghi_chu: '',
        ykien_xacnhan: '',
        ngattainguyen: null,
        ykien_thuchien_thuhoi_mayao: '',
        ykien_thuchien_thuhoi_ip: '',
        ykien_thuchien_thuhoi_backup: '',
        trangthai_thuhoi: 0,
        trangthai_thuhoi_ip: '',
        nhanvien_id: 0,
        may_capnhap: '',
        nguoi_capnhap: '',
        ip_capnhap: ''
      },
      currentTab: 1, // Chỉ số tab đang chọn
      yeucau_thuhoi_items: [],
      xacnhan_yeucau_thuhoi_items: [],

      controls: {
        ykien_xacnhan: {
          enabled: false,
          invalid: true
        },
        ngattainguyen: {
          enabled: false,
          invalid: true
        },
      },
      buttons: {
   
        btnAgree: {
          enabled: false
        },
        btnReject: {
          enabled: false
        },
        btnReOpen: {
          enabled: false
        }
      }
    }
  },
  computed: {
    TRANGTHAI_PHIEU(){
      return {
        LUU_TAM: 0,
      CHO_THU_HOI: 1,
      DANG_THU_HOI: 2,
      THU_HOI_NOI_BO: 3,
      HOAN_THANH: 4,
      TU_CHOI: 5
      }
    },
    grdItems: function() {
      return this.$refs.grdItems
    },
    grdItems_haTangMang: function() {
      return this.$refs.grdItems_haTangMang
    },
    grdItems_subnet: function() {
      return this.$refs.grdItems_subnet
    },
    grdItems_history: function() {
      return this.$refs.grdItems_history
    }
  },
  watch: {
    'currentItem.ykien_xacnhan': function(value) {
      this.controls.ykien_xacnhan.invalid = value == ''
    },
    'currentItem.ngattainguyen': function(value) {
      this.controls.ngattainguyen.invalid = value == null
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.xacnhan_yeucau_thuhoi_items = await this.apiListItems()
      // init current user Info
      this.user_info.nhanvien_id = await this.$root.token?.getNhanVienID()
      this.user_info.nhanvien_ten = await this.$root.context.user.getTenNhanVien()
      this.user_info.nhanvien_may = await this.$root.token.getMachine()
      this.user_info.nhanvien_ip = await this.$root.token.getIP()
      this.user_info.phanvung_id = await this.$root.token.getPhanVungID()
      this.user_info.donvi_id = await this.$root.token.getDonViID()
      this.user_info.donvi_ten = await this.$root.context.user.getTenDonVi()
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    apiListItems: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-ds-phieu-yeucau-thuhoi')
        const mapped = rs.data.map((item, index) => ({
          sothutu: index + 1,
          id: item.ID ?? 0,
          phieu_id: item.ID ?? 0,
          sophieu: item.TEN ?? null,
          socu_thuhoi: item.SOCUTHUHOI ?? '',
          socu_molai: item.SOCUMOLAI ?? '',
          trangthai_yeucau: item.TRANGTHAI_YEUCAU ?? null,
          trangthai_yeucau_id: item.TRANGTHAI_YEUCAU_ID ?? null,
          hoanthanh_mayao_enable: 0,
          spdv_id: item.SPDV_ID ?? 0,
          spdv: item.SPDV ?? '',
          cum_dichvu: item.CUMDICHVU ?? '',
          spdv_name: item.TEN_SPDV ?? '',
          hatang_mang: item.HATANG_MANG ?? '',
          mucdich_sudung: item.MUCDICH_SD ?? '',
          donvi_yeucau_id: item.DONVI_ID ?? 0,
          donvi_yeucau_name: item.DONVI_YC ?? '',
          nguoigui_id: item.NGUOI_YC_ID ?? 0,
          nguoigui_name: item.NGUOI_GOI ?? '',
          f_ngay_gui: item.NGAY_GOI ?? '',
          f_ngay_hoanthanh: item.NGAY_HT ?? '',
          f_ngay_thuhoi: item.NGAY_THUHOI ?? '',
          avatar: '',
          ghi_chu: item.GHI_CHU ?? '',
          ykien_xacnhan: item.Y_KIEN_XACNHAN ?? '',
          ngattainguyen: item.NGAT_TAINGUYEN,
          ykien_thuchien_thuhoi_mayao: item.Y_KIEN_THUCHIEN_MAYAO ?? '',
          ykien_thuchien_thuhoi_ip: item.Y_KIEN_THUCHIEN_IP ?? '',
          ykien_thuchien_thuhoi_backup: item.Y_KIEN_THUCHIEN_BACKUP ?? '',
          trangthai_thuhoi: 0,
          trangthai_thuhoi_ip: item.TRANGTHAI_THUHOI_IP ?? '',
          nhanvien_id: 0,
          may_capnhap: '',
          nguoi_capnhap: '',
          ip_capnhap: ''
        }))

        result = mapped
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    updateItem: async function(args) {
      let result = false
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/xacnhan-phieu-yeucau-thuhoi', {
          id: args.id,
          trangThaiXacNhan: args.trangthai,
          xacNhan: args.ykien_xacnhan,
          ngat: args.ngattainguyen,
          thucHien: args.ykien_thuchien_thuhoi,
          thuHoiIp: args.trangthai_thuhoi_ip,
          soCuThuHoi: args.socu_thuhoi,
          soCuMoLai: "",

        })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    enableControls: function(args) {
      // for (const key in this.controls) {
      //   if (this.controls.hasOwnProperty(key)) {
      //     this.controls[key].enabled = args
      //   }
      // }

    },
    setCurrentItem: async function(item) {
      this.currentItem.id = item.id
      this.currentItem.phieu_id = item.phieu_id
      this.currentItem.sophieu = item.sophieu
      this.currentItem.socu_thuhoi = item.socu_thuhoi
      this.currentItem.socu_molai = item.socu_molai
      this.currentItem.trangthai_yeucau = item.trangthai_yeucau
      this.currentItem.trangthai_yeucau_id = item.trangthai_yeucau_id
      this.currentItem.hoanthanh_mayao_enable = item.hoanthanh_mayao_enable
      this.currentItem.spdv_id = item.spdv_id
      this.currentItem.spdv = item.spdv
      this.currentItem.spdv_name = item.spdv_name
      this.currentItem.cum_dichvu = item.cum_dichvu
      this.currentItem.hatang_mang = item.hatang_mang
      this.currentItem.mucdich_sudung = item.mucdich_sudung
      this.currentItem.donvi_yeucau_id = item.donvi_yeucau_id
      this.currentItem.donvi_yeucau_name = item.donvi_yeucau_name
      this.currentItem.nguoigui_id = item.nguoigui_id
      this.currentItem.nguoigui_name = item.nguoigui_name
      this.currentItem.f_ngay_gui = item.f_ngay_gui
      this.currentItem.f_ngay_hoanthanh = item.f_ngay_hoanthanh
      this.currentItem.f_ngay_thuhoi = item.f_ngay_thuhoi
      this.currentItem.attachment = item.attachment
      this.currentItem.ghi_chu = item.ghi_chu
      this.currentItem.ykien_xacnhan = item.ykien_xacnhan
      this.currentItem.ngattainguyen = item.ngattainguyen
      this.currentItem.ykien_thuchien_thuhoi_mayao = item.ykien_thuchien_thuhoi_mayao
      this.currentItem.ykien_thuchien_thuhoi_ip = item.ykien_thuchien_thuhoi_ip
      this.currentItem.ykien_thuchien_thuhoi_backup = item.ykien_thuchien_thuhoi_backup
      this.currentItem.trangthai_thuhoi = item.trangthai_thuhoi
      this.currentItem.trangthai_thuhoi_ip = item.trangthai_thuhoi_ip
      this.currentItem.yeucau_ip_enable = item.yeucau_ip_enable

      this.currentItem.nhanvien_id = this.user_info.nhanvien_id
      this.currentItem.may_capnhap = this.user_info.nhanvien_may
      this.currentItem.nguoi_capnhap = this.user_info.nhanvien_ten
      this.currentItem.ip_capnhap = this.user_info.nhanvien_ip

      this.buttons.btnAgree.enabled = item.trangthai_yeucau_id == this.TRANGTHAI_PHIEU.CHO_THU_HOI
      this.buttons.btnReject.enabled = item.trangthai_yeucau_id == this.TRANGTHAI_PHIEU.CHO_THU_HOI
      this.buttons.btnReOpen.enabled = item.trangthai_yeucau_id == this.TRANGTHAI_PHIEU.DANG_THU_HOI
      this.onShowDataZone(item.id);
      this.controls.ykien_xacnhan.enabled = item.trangthai_yeucau_id == this.TRANGTHAI_PHIEU.CHO_THU_HOI
      this.controls.ngattainguyen.enabled = item.trangthai_yeucau_id == this.TRANGTHAI_PHIEU.CHO_THU_HOI
    },
    onShowDataZone: async function(id) {
      this.dsAoHoaCP = await this.getDsAoHoaCP(id)
      this.dsContainerK8sCP = await this.getDsContainerK8sCP(id)
      this.dsIDGCP = await this.getDsIDGCP(id)
      this.dsBackupCP = await this.getDsBackupCP(id)
    },
    getDsAoHoaCP: async function(id) {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-aohoa-yeucau', { yeuCauId: id })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    getDsContainerK8sCP: async function(id) {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-k8s-yeucau', { yeuCauId: id })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    getDsIDGCP: async function(id) {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-idg-yeucau', { yeuCauId: id })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    getDsBackupCP: async function(id) {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-backup-yeucau', {
          yeuCauId: id
        })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    setCurrentItemDefault: function() {
      this.setCurrentItem({
        id: 0,
        phieu_id: 0,
        sophieu: null,
        socu_thuhoi: '',
        socu_molai: '',
        trangthai_yeucau: 0,
        trangthai_yeucau_id: 0,
        hoanthanh_mayao_enable: 0,
        spdv_id: 0,
        spdv: '',
        spdv_name: '',
        cum_dichvu: '',
        hatang_mang: '',
        mucdich_sudung: '',
        donvi_yeucau_id: 0,
        donvi_yeucau_name: '',
        nguoigui_id: 0,
        nguoigui_name: '',
        f_ngay_gui: '',
        f_ngay_hoanthanh: '',
        f_ngay_thuhoi: '',
        avatar: '',
        ghi_chu: '',
        ykien_xacnhan: '',
        ngattainguyen: null,
        ykien_thuchien_thuhoi_mayao: '',
        ykien_thuchien_thuhoi_ip: '',
        ykien_thuchien_thuhoi_backup: '',
        trangthai_thuhoi: 0,
        trangthai_thuhoi_ip: '',
        yeucau_ip_enable: 0,
        nhanvien_id: 0,
        may_capnhap: '',
        nguoi_capnhap: '',
        ip_capnhap: ''
      })
    },
    validateControls: function() {
      let msg = ''
      if (this.controls.ykien_xacnhan.invalid) {
        msg = 'Ý kiến xác nhận không được để trống.'
      }
      if (this.controls.ngattainguyen.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'Ngắt tài nguyên không được để trống.'
      }
      if (msg != '') this.$root.toastError(msg)
      return msg == ''
    },
    grdItems_RowSelected: async function(args) {
      try {
        this.$root.showLoading(true)
        this.setCurrentItem(args.data)
        // this.yeucau_thuhoi_items = await this.apiListYeuCauThuHoi(args.data.id);
      } finally {
        this.$root.showLoading(false)
      }
    },
    onRequest: async function(trangthai) {
      if (!this.validateControls()) return

      const actionMap = {
        2: 'xác nhận', // Hoàn thành
        5: 'từ chối' // Từ chối
      }

      const action = actionMap[trangthai] || 'thực hiện'

      const msg = `Bạn có muốn ${action} yêu cầu này không?`
      const confirmTitle = 'Xác nhận'

      const confirmed = await this.$confirm(msg, confirmTitle, {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Không đồng ý',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then(() => true)
        .catch(() => false)

      if (!confirmed) return

      try {
        this.$root.showLoading(true)

        // Gán trạng thái mới vào currentItem
        this.currentItem.trangthai = trangthai

        const rs = await this.updateItem(this.currentItem)
        if (rs) {
          this.setCurrentItemDefault()
          this.enableControls(false)
          this.$root.toastSuccess(`${action.charAt(0).toUpperCase() + action.slice(1)} yêu cầu thành công`)
          this.refreshData()
          this.$root.showLoading(false)
        } else {
          this.$root.toastError(`${action.charAt(0).toUpperCase() + action.slice(1)} yêu cầu thất bại`)
        }
      } finally {
        this.$root.showLoading(false)
      }
    },
    refreshData: async function() {
      this.xacnhan_yeucau_thuhoi_items = await this.apiListItems()
    },
    onReOpen: async function(args) {
      this.$refs.dialogYeuCauMoLai.dialogOpen();
    }
  }
}
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type='checkbox']:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type='checkbox']:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}
</style>
