<template>
  <b-modal
    ref="popupKetQuaDoKiem"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
    @hide="clear_data"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Kết quả đo kiểm
        </div>
        <div class="close -ap icon-close" @click="hide"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li v-for="item in actions" :key="item.id" @click="onActionClick(item)"
              :class="{disabled: !item.active}" v-show="item.visible">
            <a style="cursor: pointer;" :class="{disabled_color: !item.active}" :ref="item.id">
              <span class="icon" :class="item.icon_class"></span> {{ item.name }}
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Kết quả do chất lượng</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">Speed Up</div>
                <div class="value">
                  <input type="number" min="0" ref="nud_speed_up" v-model="frmKetQuaDoKiem.nud_speed_up"
                         class="form-control">
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Attenuation Up</div>
                <div class="value">
                  <input type="number" min="0" ref="nud_atten_up" v-model="frmKetQuaDoKiem.nud_atten_up"
                         class="form-control">
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">SRN Up</div>
                <div class="value">
                  <input type="number" min="0" ref="nud_snr_up" v-model="frmKetQuaDoKiem.nud_snr_up"
                         class="form-control">
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">Speed Down</div>
                <div class="value">
                  <input type="number" min="0" ref="nud_speed_down" v-model="frmKetQuaDoKiem.nud_speed_down"
                         class="form-control">
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Attenuation Down</div>
                <div class="value">
                  <input type="number" min="0" ref="nud_atten_down" v-model="frmKetQuaDoKiem.nud_atten_down"
                         class="form-control">
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">SRN Down</div>
                <div class="value">
                  <input type="number" min="0" ref="nud_snr_down" v-model="frmKetQuaDoKiem.nud_snr_down"
                         class="form-control">
                </div>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w120">Ghi chú</div>
            <div class="value">
              <textarea ref="txtGhichu" v-model="frmKetQuaDoKiem.txtGhiChu" class="form-control"></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import API from './API'

export default {
  name: 'KetQuaDoKiem',
  props: {
    vhdtb_id: {
      type: Number,
      default: 0
    },
    vbaohong_id: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      actions: [
        {
          id: 'tsbtnCapNhat',
          name: 'Cập nhật',
          active: true,
          icon_class: 'one-save',
          visible: true
        },
        {
          id: 'tsbtnXoa',
          name: 'Xóa (F8)',
          active: true,
          icon_class: 'one-trash',
          visible: true
        }
      ],
      frmKetQuaDoKiem: {
        nud_speed_up: '',
        nud_atten_up: '',
        nud_snr_up: '',
        txtGhiChu: '',
        nud_speed_down: '',
        nud_atten_down: '',
        nud_snr_down: ''
      },
      nhapmoi: true,
      dsChatLuong: [],
      dsChatLuongKH: []
    }
  },
  methods: {
    clear_data () {
      this.frmKetQuaDoKiem.txtGhiChu = ''
      this.frmKetQuaDoKiem.nud_snr_up = ''
      this.frmKetQuaDoKiem.nud_speed_up = ''
      this.frmKetQuaDoKiem.nud_speed_down = ''
      this.frmKetQuaDoKiem.nud_snr_down = ''
      this.frmKetQuaDoKiem.nud_atten_up = ''
      this.frmKetQuaDoKiem.nud_atten_down = ''
      this.nhapmoi = true
      this.dsChatLuongKH = []
      this.dsChatLuong = []
    },
    hide () {
      this.$refs.popupKetQuaDoKiem.hide()
    },
    handleShowModal () {
      this.initData()
    },
    onActionClick (action) {
      if (action.id === 'tsbtnCapNhat') {
        this.tsbtnCapNhat_Click()
      } else if (action.id === 'tsbtnXoa') {
        this.tsbtnXoa_Click()
      }
    },
    showModal () {
      this.$refs.popupKetQuaDoKiem.show()
    },
    async tsbtnCapNhat_Click () {
      try {
        let resp = null
        if (this.vhdtb_id !== 0) {
          if (this.nhapmoi === true) {
            this.TaoDuLieu_CHATLUONG_HDTB()
            let request = {
              'vkieu': '0',
              'ds_chatluong': JSON.stringify(this.dsChatLuong)
            }
            resp = await API.CAPNHAT_CHATLUONG_HDTB(this.axios, request)
          } else {
            this.TaoDuLieu_CHATLUONG_HDTB()
            let request = {
              'vkieu': '1',
              'ds_chatluong': JSON.stringify(this.dsChatLuong)
            }
            resp = await API.CAPNHAT_CHATLUONG_HDTB(this.axios, request)
          }
        }
        if (this.vbaohong_id !== 0) {
          if (this.nhapmoi === true) {
            this.TaoDuLieu_CHATLUONG_BH()
            let request = {
              'vkieu': '0',
              'ds_chatluong': JSON.stringify(this.dsChatLuongKH)
            }
            resp = await API.CAPNHAT_CHATLUONG_BH(this.axios, request)
          } else {
            let request = {
              'vkieu': '1',
              'ds_chatluong': JSON.stringify(this.dsChatLuongKH)
            }
            resp = await API.CAPNHAT_CHATLUONG_BH(this.axios, request)
          }
        }
        if (resp && resp.data && resp.data.error_code === 'BSS-00000000') {
          this.$root.$toast.success('Cập nhật thông tin đo kiểm thành công !')
          this.setActiveButton('tsbtnXoa', true)
        } else {
          this.$root.$toast.warning('Có lỗi ' + resp.data.message_detail)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error)
      }
    },
    async initData () {
      if (this.vhdtb_id !== 0 || this.vbaohong_id !== 0) {
        let data = this.GetData(await API.SP_DS_CHATLUONG(this.axios, {
          'vhdtb_id': this.vhdtb_id.toString(),
          'vbaohong_id': this.vbaohong_id.toString()
        }))
        if (data.length > 0) {
          this.frmKetQuaDoKiem.nud_speed_up = data[0].speed_up
          this.frmKetQuaDoKiem.nud_atten_up = data[0].attenuation_up
          this.frmKetQuaDoKiem.nud_snr_up = data[0].snr_up
          this.frmKetQuaDoKiem.nud_speed_down = data[0].speed_down
          this.frmKetQuaDoKiem.nud_atten_down = data[0].attenuation_down
          this.frmKetQuaDoKiem.nud_snr_down = data[0].snr_down
          this.frmKetQuaDoKiem.txtGhiChu = data[0].ghichu
          this.nhapmoi = false
          this.setActiveButton('tsbtnXoa', true)
        } else {
          this.frmKetQuaDoKiem.nud_speed_up = ''
          this.frmKetQuaDoKiem.nud_speed_down = ''
          this.frmKetQuaDoKiem.nud_atten_up = ''
          this.frmKetQuaDoKiem.nud_atten_down = ''
          this.frmKetQuaDoKiem.nud_snr_up = ''
          this.frmKetQuaDoKiem.nud_snr_down = ''
          this.frmKetQuaDoKiem.txtGhiChu = ''
          this.setActiveButton('tsbtnXoa', false)
        }
      }
    },
    TaoDuLieu_CHATLUONG_HDTB () {
      this.dsChatLuong = []
      let row = {}
      row.HDTB_ID = this.vhdtb_id
      row.SPEED_UP = this.frmKetQuaDoKiem.nud_speed_up
      row.SPEED_DOWN = this.frmKetQuaDoKiem.nud_speed_down
      row.ATTENUATION_UP = this.frmKetQuaDoKiem.nud_atten_up
      row.ATTENUATION_DOWN = this.frmKetQuaDoKiem.nud_atten_down
      row.SNR_UP = this.frmKetQuaDoKiem.nud_snr_up
      row.SNR_DOWN = this.frmKetQuaDoKiem.nud_snr_down
      row.GHICHU = this.frmKetQuaDoKiem.txtGhiChu.trim()
      row.MAY_CN = 'admin'
      row.NGUOI_CN = this.$root.token.getUserName()
      row.IP_CN = '127.0.0.1'
      this.dsChatLuong.push(row)
    },
    TaoDuLieu_CHATLUONG_BH () {
      this.dsChatLuongKH = []
      let row = {}
      this.BAOHONG_ID = this.vbaohong_id
      row.SPEED_UP = this.frmKetQuaDoKiem.nud_speed_up
      row.SPEED_DOWN = this.frmKetQuaDoKiem.nud_speed_down
      row.ATTENUATION_UP = this.frmKetQuaDoKiem.nud_atten_up
      row.ATTENUATION_DOWN = this.frmKetQuaDoKiem.nud_atten_down
      row.SNR_UP = this.frmKetQuaDoKiem.nud_snr_up
      row.SNR_DOWN = this.frmKetQuaDoKiem.nud_snr_down
      row.GHICHU = this.frmKetQuaDoKiem.txtGhiChu.trim()
      row.MAY_CN = 'admin'
      row.NGUOI_CN = this.$root.token.getUserName()
      row.IP_CN = '127.0.0.1'
      this.dsChatLuongKH.push(row)
    },
    async tsbtnXoa_Click () {
      try {
        if (this.vhdtb_id !== 0) {
          let request = {
            'vkieu': '2',
            'ds_chatluong': JSON.stringify([{'HDTB_ID': this.vhdtb_id}])
          }
          await API.CAPNHAT_CHATLUONG_HDTB(this.axios, request).then(response => {
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
              this.clear_data()
              this.$root.$toast.success('Xóa thông tin đo kiểm thành công !')
              this.setActiveButton('tsbtnXoa', false)
            } else {
              this.$root.$toast.error('Có lỗi xảy ra khi xóa thông tin đo kiểm: ' + response.data.message_detail)
            }
          })
        }
        if (this.vbaohong_id !== 0) {
          let request = {
            'vkieu': '2',
            'ds_chatluong': JSON.stringify([{'BAOHONG_ID': this.vbaohong_id}])
          }
          await API.CAPNHAT_CHATLUONG_BH(this.axios, request).then(response => {
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
              this.clear_data()
              this.$root.$toast.success('Xóa thông tin đo kiểm thành công !')
              this.setActiveButton('tsbtnXoa', false)
            } else {
              this.$root.$toast.error('Có lỗi xảy ra khi xóa thông tin đo kiểm: ' + response.data.message_detail)
            }
          })
        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ' + error)
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return []
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    setActiveButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }
    }
  }
}
</script>

<style scoped>
.disabled_color {
  color: gray !important;
}
</style>
