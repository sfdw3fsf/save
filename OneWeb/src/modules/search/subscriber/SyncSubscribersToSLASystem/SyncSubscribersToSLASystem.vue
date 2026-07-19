<template src="./SyncSubscribersToSLASystem.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import SearchAccount from '../SearchAccount/SearchAccount'
import api from './api'

export default {
  name: 'SyncSubscribersToSLASystem',
  components: { breadcrumb, SearchAccount },
  data () {
    return {
      header: {
        title: 'Đồng bộ thuê bao lên hệ thống SLA KHTCDN',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.buttons' } }
        ]
      },
      actions: [
        { id: 'tsbtnCapNhat', icon: 'one-reload1', name: 'Đồng bộ' },
        { id: 'tsbtnCapNhatNgay', icon: 'one-reload1', name: 'Đồng bộ ngay' },
        { id: 'tsbtnThemmoi', icon: 'one-save', name: 'Thêm mới' }
      ],
      txtMaKH: '',
      gridViewDaGan: {
        headers: [
          { fieldName: 'user_name', headerText: 'User', allowFiltering: true },
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true },
          { fieldName: 'dichvu_vt', headerText: 'Dịch vụ', allowFiltering: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true },
          { fieldName: 'thuonghieu', headerText: 'Tốc độ', allowFiltering: true },
          { fieldName: 'tocdothuc', headerText: 'Tốc độ thực', allowFiltering: true },
          { fieldName: 'ip', headerText: 'IP thiết bị', allowFiltering: true },
          { fieldName: 'system', headerText: 'System', allowFiltering: true },
          { fieldName: 'rack', headerText: 'Rack', allowFiltering: true, width: 150 },
          { fieldName: 'shelf', headerText: 'Shelf', allowFiltering: true, width: 150 },
          { fieldName: 'slot', headerText: 'Slot', allowFiltering: true, width: 150 },
          { fieldName: 'port', headerText: 'Port', allowFiltering: true, width: 150 },
          { fieldName: 'vpi', headerText: 'Vpi', allowFiltering: true, width: 150 },
          { fieldName: 'vci', headerText: 'Vci', allowFiltering: true, width: 150 },
          { fieldName: 'slid', headerText: 'Slid', allowFiltering: true, width: 150 },
          { fieldName: 'adsl_port', headerText: 'Adsl Port', allowFiltering: true },
          { fieldName: 'loai_dslam', headerText: 'Loại thiết bị', allowFiltering: true },
          { fieldName: 'congnghe', headerText: 'Công nghệ', allowFiltering: true },
          { fieldName: 'vnptcode', headerText: 'VNPT Code', allowFiltering: true },
          { fieldName: '', headerText: 'Xóa', allowFiltering: false, template: this.getColumnXoa(this), width: 100 }
        ],
        dataSources: [],
        selectedItems: []
      },
      gridViewChuaGan: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true },
          { fieldName: 'dichvu_vt', headerText: 'Dịch vụ', allowFiltering: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true },
          { fieldName: 'thuonghieu', headerText: 'Tốc độ', allowFiltering: true },
          { fieldName: 'tocdothuc', headerText: 'Tốc độ thực', allowFiltering: true },
          { fieldName: 'ip', headerText: 'IP thiết bị', allowFiltering: true },
          { fieldName: 'system', headerText: 'System', allowFiltering: true },
          { fieldName: 'rack', headerText: 'Rack', allowFiltering: true, width: 150 },
          { fieldName: 'shelf', headerText: 'Shelf', allowFiltering: true, width: 150 },
          { fieldName: 'slot', headerText: 'Slot', allowFiltering: true, width: 150 },
          { fieldName: 'port', headerText: 'Port', allowFiltering: true, width: 150 },
          { fieldName: 'vpi', headerText: 'Vpi', allowFiltering: true, width: 150 },
          { fieldName: 'vci', headerText: 'Vci', allowFiltering: true, width: 150 },
          { fieldName: 'slid', headerText: 'Slid', allowFiltering: true, width: 150 },
          { fieldName: 'adsl_port', headerText: 'Adsl Port', allowFiltering: true },
          { fieldName: 'loai_dslam', headerText: 'Loại thiết bị', allowFiltering: true },
          { fieldName: 'congnghe', headerText: 'Công nghệ', allowFiltering: true },
          { fieldName: 'vnptcode', headerText: 'VNPT Code', allowFiltering: true }
        ],
        dataSources: [],
        selectedItems: []
      },
      errors: [],
    }
  },
  computed: {
    phanVungId () {
      return +this.$root.token.getPhanVungID()
    }
  },
  methods: {
    btnLayMaKH_Click () {
      this.$refs.popupSearchAccount.show()
    },
    formCloseSearchAccount (data) {
      this.$refs.popupSearchAccount.hide()
      if (data) {
        this.txtMaKH = data.ma_kh || ''
        this.$refs.txtMaKHRef.focus()
      }
    },
    async txtMaKH_KeyPress () {
      this.loading(true)
      try {
        await this.HienThi_DS_Thuebao(this.txtMaKH)
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async HienThi_DS_Thuebao (vmaKH) {
      try {
        const [dsChuaGan, dsDaGan] = await Promise.all([
          this.getDanhSachThueBaoSLA(1, vmaKH),
          this.getDanhSachThueBaoSLA(2, vmaKH)
        ])
        this.gridViewChuaGan.dataSources = dsChuaGan
        this.gridViewDaGan.dataSources = dsDaGan
      } catch (e) {
      }
    },
    async getDanhSachThueBaoSLA (kieu, maKH) {
      try {
        const response = await api.getDanhSachThueBaoSLA(
          this.axios,
          {
            vphanvung_id: this.phanVungId,
            p_kieu: kieu,
            p_ma_kh: maKH
          }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data.rs1 : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách thuê bao SLA')
        return []
      }
    },
    onClickAction (id) {
      if (id === 'tsbtnCapNhat') this.tsbtnCapNhat_Click()
      else if (id === 'tsbtnCapNhatNgay') this.tsbtnCapNhatNgay_Click()
      else this.tsbtnThemmoi_Click()
    },
    tsbtnCapNhat_Click () {
      if (this.gridViewDaGan.selectedItems.length === 0) {
        this.$root.$toast.warning('Bạn hãy chọn 1 thuê bao để đồng bộ.')
        return false
      }
      if (this.gridViewDaGan.selectedItems.some(elm => !elm.ip)) {
        this.$root.$toast.warning('Thiếu thông tin IP của thiết bị.\\n Hãy chuẩn hóa thông tin này trong phần quản lý thiết bị')
        return false
      }
      this.$confirm(
        'Bạn chắc chắn muốn đồng bộ thuê bao lên hệ thống SLA không ?',
        'Thêm thuê bao',
        { confirmButtonText: 'Có', cancelButtonText: 'Không' }
      ).then(async () => {
        this.loading(true)
        try {
          // const vnptCode = await this.getThongTinTinh(this.phanVungId, 1)
          for (const item of this.gridViewDaGan.selectedItems) {
            const dsDD = await this.getTenTat(+item.thuebao_id)
            const slaData = {
              CODE: this.txtMaKH,
              ACCOUNT: item.ma_tb,
              BANDWIDTH: +item.tocdothuc,
              SERVICESPEC: item.thuonghieu,
              SERVICETYPE: item.loaihinh_tb,
              IP: item.ip,
              SYSTEM: item.system,
              RACK: item.rack,
              SHELF: item.shelf,
              SLOT: item.slot,
              PORT: item.port,
              VPI: item.vpi,
              VCI: item.vci,
              SLID: item.slid,
              DEVICETYPE: item.loai_dslam,
              TECHTYPE: item.congnghe,
              ADSL_PORT: item.adsl_port,
              NAME: item.ten_tb,
              ADDRESS: item.diachi_tb,
              MOBILE: '',
              TYPE: '',
              VNPTCODE: item.vnptcode,
              USERNAME: '',
              PASSWORD: '',
              EMAIL: '',
              EMAILSENDER: null,
              SMS: null,
            }
            const responseSync = await this.updateThueBaoSLA(slaData)
            if (!responseSync) {
              this.errors.push(`[Mã tb: ${item.ma_tb}] Không thể đồng bộ thông tin lên hệ thống SLA KHTCDN`)
              continue
            }
            if (responseSync.errorCode !== 0) {
              this.errors.push(`[Mã tb: ${item.ma_tb}] ${responseSync.faultString}`)
            }
          }
          if (this.errors.length === 0) {
            this.$root.$toast.success('Đồng bộ thông tin lên hệ thống SLA KHTCDN thành công!')
            this.$root.$toast.success('Cập nhật thành công')
          } else {
            this.errors.forEach(error => this.$root.$toast.error(error))
          }
        } catch (e) {
        } finally {
          this.loading(false)
          this.errors = []
        }
      }).catch((e) => {})
    },
    tsbtnCapNhatNgay_Click() {
      if (this.gridViewDaGan.selectedItems.length === 0) {
        this.$root.$toast.warning('Bạn hãy chọn 1 thuê bao để đồng bộ.')
        return false
      }
      if (this.gridViewDaGan.selectedItems.some(elm => !elm.ip)) {
        this.$root.$toast.warning('Thiếu thông tin IP của thiết bị.\\n Hãy chuẩn hóa thông tin này trong phần quản lý thiết bị')
        return false
      }
      this.$confirm(
        'Bạn chắc chắn muốn đồng bộ thuê bao lên hệ thống SLA không?',
        'Đồng bộ thuê bao',
        { confirmButtonText: 'Có', cancelButtonText: 'Không' }
      ).then(async () => {
        this.loading(true)
        try {
          // const vnptCode = await this.getThongTinTinh(this.phanVungId, 1)
          for (const item of this.gridViewDaGan.selectedItems) {
            // const dsDD = await this.getTenTat(+item.thuebao_id)
            const slaData = {
              CODE: this.txtMaKH,
              ACCOUNT: item.ma_tb,
              BANDWIDTH: +item.tocdothuc,
              SERVICESPEC: item.thuonghieu,
              SERVICETYPE: item.loaihinh_tb,
              IP: item.ip,
              SYSTEM: item.system,
              RACK: item.rack,
              SHELF: item.shelf,
              SLOT: item.slot,
              PORT: item.port,
              VPI: item.vpi,
              VCI: item.vci,
              SLID: item.slid,
              DEVICETYPE: item.loai_dslam,
              TECHTYPE: item.congnghe,
              ADSL_PORT: item.adsl_port,
              NAME: item.ten_tb,
              ADDRESS: item.diachi_tb,
              MOBILE: '',
              TYPE: '',
              VNPTCODE: item.vnptcode,
              USERNAME: '',
              PASSWORD: '',
              EMAIL: '',
              EMAILSENDER: null,
              SMS: null,
            }
            const responseSync = await this.AddOrUpdateThueBao(slaData)
            if (!responseSync) {
              this.errors.push(`[Mã tb: ${item.ma_tb}] Không thể đồng bộ thông tin lên hệ thống SLA KHTCDN`)
              continue
            }
            if (responseSync.errorCode !== 0) {
              this.errors.push(`[Mã tb: ${item.ma_tb}] ${responseSync.faultString}`)
            }
          }
          if (this.errors.length === 0) {
            this.$root.$toast.success('Đồng bộ thông tin lên hệ thống SLA KHTCDN thành công!')
            this.$root.$toast.success('Cập nhật thành công')
          } else {
            this.errors.forEach(error => this.$root.$toast.error(error))
          }
        } catch (e) {
        } finally {
          this.loading(false)
          this.errors = []
        }
      }).catch((e) => {})
    },
    onSelectedItemsChangedDaGan (items) {
      this.gridViewDaGan.selectedItems = [...items]
    },
    async updateThueBaoSLA (body) {
      try {
        const response = await api.updateThueBaoSLA(this.axios, body)
        const data = await response.data
        return data
      } catch (e) {
        return null
      }
    },
    tsbtnThemmoi_Click () {
      if (this.gridViewChuaGan.selectedItems.length === 0) {
        this.$root.$toast.warning('Bạn chưa chọn thuê bao để thêm mới.')
        return false
      }
      if (this.gridViewChuaGan.selectedItems.some(elm => !elm.ip)) {
        this.$root.$toast.warning('Thiếu thông tin IP của thiết bị.\\n Hãy chuẩn hóa thông tin này trong phần quản lý thiết bị')
        return false
      }
      this.$confirm(
        'Bạn chắc chắn muốn Add thêm thuê bao lên hệ thống SLA không ?',
        'Thêm thuê bao',
        { confirmButtonText: 'Có', cancelButtonText: 'Không' }
      ).then(async () => {
        this.loading(true)
        try {
          // const vnptCode = await this.getThongTinTinh(this.phanVungId, 1)
          const ds_dbtb_user = {
            DBTB_USER: []
          }
          for (const item of this.gridViewChuaGan.selectedItems) {
            const dsDD = await this.getTenTat(+item.thuebao_id)
            const slaData = {
              CODE: this.txtMaKH,
              ACCOUNT: item.ma_tb,
              BANDWIDTH: +item.tocdothuc,
              SERVICESPEC: item.thuonghieu,
              SERVICETYPE: item.loaihinh_tb,
              IP: item.ip,
              SYSTEM: item.system,
              RACK: item.rack,
              SHELF: item.shelf,
              SLOT: item.slot,
              PORT: item.port,
              VPI: item.vpi,
              VCI: item.vci,
              SLID: item.slid,
              DEVICETYPE: item.loai_dslam,
              TECHTYPE: item.congnghe,
              PROVINCE: '',
              DISTRICT: '',
              WARD: '',
              VNPTCODE: item.vnptcode,
              NAME: item.ten_tb,
              ADDRESS: item.diachi_tb,
              ADSL_PORT: item.adsl_port,
            }
            if (item.hasOwnProperty('chuquanld_id')
              && item.hasOwnProperty('chuquan_id_tinh')
              && item.chuquanld_id !== item.chuquan_id_tinh
            ) {
              const dtBC = await this.kiemTraThueBaoBanCheo(item.thuebao_id)
              if (dtBC.length > 0) {
                const dtCT = await this.getThongTinThueBaoSLABc(dtBC[0].thuebao_kn_id)
                if (dtCT.length > 0) {
                  slaData.BANDWIDTH = +dtCT[0].tocdothuc
                  slaData.SERVICESPEC = dtCT[0].thuonghieu
                  slaData.SERVICETYPE = dtCT[0].loaihinh_tb
                  slaData.IP = dtCT[0].ip
                  slaData.SYSTEM = dtCT[0].system
                  slaData.RACK = dtCT[0].rack
                  slaData.SHELF = dtCT[0].shelf
                  slaData.SLOT = dtCT[0].slot
                  slaData.PORT = dtCT[0].port
                  slaData.VPI = dtCT[0].vpi
                  slaData.VCI = dtCT[0].vci
                  slaData.SLID = dtCT[0].slid
                  slaData.DEVICETYPE = dtCT[0].loai_dslam
                  slaData.TECHTYPE = dtCT[0].congnghe
                  slaData.NAME = dtCT[0].ten_tb
                  slaData.ADDRESS = dtCT[0].diachi_tb
                }
              }
            }
            const responseSync = await this.addThueBaoSLA(slaData)
            if (!responseSync) {
              this.errors.push(`[Mã tb: ${item.ma_tb}] Không thể đồng bộ thông tin lên hệ thống SLA KHTCDN`)
              continue
            }
            if (responseSync.errorCode !== 0) {
              this.errors.push(`[Mã tb: ${item.ma_tb}] ${responseSync.faultString}`)
            } else {
              ds_dbtb_user.DBTB_USER.push({USER_ID: +item.user_id, THUEBAO_ID: +item.thuebao_id})
            }
          }
          if (this.errors.length === 0) {
            this.$root.$toast.success('Đồng bộ thông tin lên hệ thống SLA KHTCDN thành công!')
            this.$root.$toast.success('Thêm mới thành công')
          } else {
            this.errors.forEach(error => this.$root.$toast.error(error))
          }
          if (ds_dbtb_user.DBTB_USER.length > 0) {
            await this.updateDBTBUser(JSON.stringify(ds_dbtb_user.DBTB_USER))
          }
          await this.HienThi_DS_Thuebao(this.txtMaKH)
        } catch (e) {
        } finally {
          this.loading(false)
          this.errors = []
        }
      }).catch((e) => {})
    },
    onSelectedItemsChangedChuaGan (items) {
      this.gridViewChuaGan.selectedItems = [...items]
    },
    async kiemTraThueBaoBanCheo (thueBaoId) {
      try {
        const response = await api.kiemTraThueBaoBanCheo(
          this.axios,
          {
            vphanvung_id: this.phanVungId,
            p_thuebao_id: thueBaoId
          }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data.rs1 : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy kiểm tra thuê bao bán chéo')
        return []
      }
    },
    async getThongTinThueBaoSLABc (thueBaoId) {
      try {
        const response = await api.getThongTinThueBaoSLABc(
          this.axios,
          {
            vphanvung_id: this.phanVungId,
            p_thuebao_id: thueBaoId
          }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data.rs1 : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin thuê bao SLA BC')
        return []
      }
    },
    async getTenTat (thueBaoId) {
      try {
        const response = await api.getTenTat(
          this.axios,
          {
            vphanvung_id: this.phanVungId,
            p_thuebao_id: thueBaoId
          }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data.rs1 : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy tên tắt')
        return []
      }
    },
    async getThongTinTinh (param, type) {
      try {
        const response = await api.getThongTinTinh(this.axios, { param, type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : ''
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin tỉnh')
        return ''
      }
    },
    async addThueBaoSLA (body) {
      try {
        const response = await api.addThueBaoSLA(this.axios, body)
        const data = await response.data
        return data
      } catch (e) {
        return null
      }
    },
    async AddOrUpdateThueBao (body) {
      try {
        const response = await api.AddOrUpdateThueBao(this.axios, body)
        const data = await response.data
        return data
      } catch (e) {
        return null
      }
    },
    async updateDBTBUser (dbtbUser) {
      try {
        const response = await api.updateDBTBUser(
          this.axios,
          { vphanvung_id: this.phanVungId, p_js_dbtb_user: dbtbUser }
        )
        const data = await response.data
        if (!(data.error_code === 'BSS-00000000' && data.error === '200')) {
          this.$root.$toast.error('Cập nhật dbtb không thành công')
        }
      } catch (e) {
        this.$root.$toast.error('Không thể cập nhật dbtb')
      }
    },
    getColumnXoa (parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="center">
                <span title="Xóa thuê bao">
                  <button class="btn btn-sm btn-danger" @click="parent.onClickXoaTB(data)">
                    <span class="-ap icon-close"></span>
                  </button>
                </span>
              </div>
            `,
            data () {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    onClickXoaTB (data) {
      this.$confirm(
        `Bạn chắc chắn muốn xóa thuê bao ${data.ma_tb} khỏi Account SLA không ?`,
        'Xóa thuê bao',
        { confirmButtonText: 'Có', cancelButtonText: 'Không' }
      ).then(async () => {
        const slaData = {
          CODE: this.txtMaKH,
          NAME: '',
          ADDRESS: '',
          MOBILE: '',
          TYPE: '',
          VNPTCODE: '',
          USERNAME: '',
          PASSWORD: '',
          EMAIL: '',
          EMAILSENDER: null,
          SMS: null,
          ACCOUNT: data.ma_tb,
          Accounts: [],
          BANDWIDTH: null,
          SERVICESPEC: '',
          SERVICETYPE: '',
          IP: '',
          SYSTEM: '',
          RACK: '',
          SHELF: '',
          SLOT: '',
          PORT: '',
          VPI: '',
          VCI: '',
          SLID: '',
          DEVICETYPE: '',
          TECHTYPE: ''
        }
        const responseSync = await this.removeThueBaoSLA(slaData)
        if (!responseSync) {
          this.$root.$toast.error('Không thể đồng bộ thông tin lên hệ thống SLA KHTCDN')
          return false
        }
        if (responseSync.errorCode !== 0) {
          this.$root.$toast.error(responseSync.faultString)
          return false
        }
        this.$root.$toast.success('Đồng bộ thông tin lên hệ thống SLA KHTCDN thành công!')
        await this.deleteDBTBUser(+data.thuebao_id, +data.user_id)
        await this.HienThi_DS_Thuebao(this.txtMaKH)
      }).catch((e) => {})
    },
    async removeThueBaoSLA (body) {
      try {
        const response = await api.removeThueBaoSLA(this.axios, body)
        const data = await response.data
        return data
      } catch (e) {
        this.$root.$toast.error('Không thể đồng bộ thông tin lên SLA')
      }
    },
    async deleteDBTBUser (thueBaoId, userId) {
      try {
        const response = await api.deleteDBTBUser(
          this.axios,
          { p_phanvung_id: this.phanVungId, p_thuebao_id: thueBaoId, p_user_id: userId }
        )
        const data = await response.data
        if (!(data.error_code === 'BSS-00000000' && data.error === '200')) {
          this.$root.$toast.error('Xóa dbtb không thành công')
        }
      } catch (e) {
        this.$root.$toast.error('Không thể xóa dbtb')
      }
    }
  }
}
</script>
