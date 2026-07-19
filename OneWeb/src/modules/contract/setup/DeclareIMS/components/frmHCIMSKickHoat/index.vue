<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="tsbtnChapNhan.visible" @click="tsbtnChapNhan_Clicked" :class="{ disabledInput: !tsbtnChapNhan.enabled }">
          <a> <span class="icon one-check"></span>{{ tsbtnChapNhan.text }}</a>
        </li>
        <li v-if="tsbtnHuy.visible" @click="tsbtnHuy_Clicked" :class="{ disabledInput: !tsbtnHuy.enabled }">
          <a> <span class="icon one-remove-outline"></span>{{ tsbtnHuy.text }}</a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">
          <div class="pull-left">Hình thức xác nhận</div>
          <div class="pull-right">
            <div class="list-checks" style="margin-top: 10px !important; margin-bottom: 10px !important">
              <div class="item">
                <div v-for="(item, index) in rdoType.list" :key="item.id" class="check-action" style="margin-right: 10px">
                  <input type="radio" v-model="rdoType.value" :value="item.id" :disabled="!item.enabled" />
                  <span class="name">{{ item.title }}</span>
                </div>
              </div>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>

        <div>
          <div v-if="rdoType.value == 'user_pass'" class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">{{ txtUser.label }}</div>
                <div class="value">
                  <input :disabled="!txtUser.enabled" type="text" class="form-control" v-model="txtUser.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">{{ txtPass.label }}</div>
                <div class="value">
                  <input :disabled="!txtPass.enabled" type="text" class="form-control" v-model="txtPass.value" />
                </div>
              </div>
            </div>
          </div>
          <div v-if="rdoType.value == 'location'" class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">{{ txtSystemID.label }}</div>
                <div class="value">
                  <input :disabled="!txtSystemID.enabled" type="text" class="form-control" v-model="txtSystemID.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">{{ txtRack.label }}</div>
                <div class="value">
                  <input :disabled="!txtRack.enabled" type="text" class="form-control" v-model="txtRack.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">{{ txtShelf.label }}</div>
                <div class="value">
                  <input :disabled="!txtShelf.enabled" type="text" class="form-control" v-model="txtShelf.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">{{ txtSlot.label }}</div>
                <div class="value">
                  <input :disabled="!txtSlot.enabled" type="text" class="form-control" v-model="txtSlot.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">{{ txtPort.label }}</div>
                <div class="value">
                  <input :disabled="!txtPort.enabled" type="text" class="form-control" v-model="txtPort.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">{{ txtONT.label }}</div>
                <div class="value">
                  <input :disabled="!txtONT.enabled" type="text" class="form-control" v-model="txtONT.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">{{ txtCVLAN.label }}</div>
                <div class="value">
                  <input :disabled="!txtCVLAN.enabled" type="text" class="form-control" v-model="txtCVLAN.value" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import moment from 'moment'
import api from './api'

export default {
  emits: ['tsbtnChapNhan_Clicked', 'tsbtnHuy_Clicked'],
  props: {
    vhdtb_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    vloaihinh_tb: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    vma_tb: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    user: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    pass: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    systemID: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    rack: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    shelf: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    vslot: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    port: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    ONT: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    CVLAN: {
      type: [String, Number],
      defaul: null,
      require: false
    }
  },
  data() {
    return {
      localLoading: 0,
      tsbtnChapNhan: { visible: true, enabled: true, text: 'Chấp nhận' },
      tsbtnHuy: { visible: true, enabled: true, text: 'Huỷ' },
      rdoType: {
        label: '',
        value: 'user_pass',
        name: 'rdoType',
        list: [
          { enabled: true, id: 'user_pass', title: 'User/Pass' },
          { enabled: true, id: 'location', title: 'Location' }
        ]
      },

      txtUser: { visible: true, enabled: false, label: 'User', value: '' },
      txtPass: { visible: true, enabled: false, label: 'Pass', value: '' },
      txtSystemID: { visible: true, enabled: false, label: 'System ID', value: '' },
      txtRack: { visible: true, enabled: false, label: 'Rack', value: '' },
      txtShelf: { visible: true, enabled: false, label: 'Shelf', value: '' },
      txtSlot: { visible: true, enabled: false, label: 'Slot', value: '' },
      txtPort: { visible: true, enabled: false, label: 'Port', value: '' },
      txtONT: { visible: true, enabled: false, label: 'DNT', value: '' },
      txtCVLAN: { visible: true, enabled: false, label: 'CVLAN', value: '' }
    }
  },
  computed: {},
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    }
  },
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data ? result.data.data : null
      } catch (e) {
        if (e.data != null) {
          this.$toast.error(`${e.data.message}`)
        } else {
          this.$toast.error(`Có lỗi gọi API`)
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    async tsbtnChapNhan_Clicked() {
      if (!confirm('Bạn có muốn kích hoạt thuê bao không!')) return
      // if (this.rdoType.value == 'location') {
      //   let kq_sp_lay_dauso_ims_theo_tinh = await api.sp_lay_dauso_ims_theo_tinh(this.axios, {
      //     p_tentat: this.$root.token.getMaTinh()
      //   })
      //   let dauSoIMS = ''
      //   if (kq_sp_lay_dauso_ims_theo_tinh.data.data != null && kq_sp_lay_dauso_ims_theo_tinh.data.data.length) {
      //     dauSoIMS = kq_sp_lay_dauso_ims_theo_tinh.data.data[0].dauso_ims
      //   }
      //   console.log('tsbtnChapNhan_Clicked')

      //   const bd_loaitb_id_vtn = await api.fn_tt_loaihinh_tb(this.axios, {
      //     type: 3,
      //     param: this.vloaihinh_tb
      //   })

      //   if (bd_loaitb_id_vtn.hasError) {
      //     this.$toast.error('Lỗi lấy dữ liệu loại hình thuê bao!\n' + bd_loaitb_id_vtn.message)
      //     return
      //   }

      //   let kq = await api.sps_service_ims_7ts(this.axios, {
      //     msisdn: '84' + dauSoIMS + this.vma_tb,
      //     imsi: '84' + dauSoIMS + this.vma_tb,
      //     ism_res_3: '',
      //     subtype: 'SIP/POTS',
      //     systemId: this.systemID,
      //     rack: this.rack,
      //     shelf: this.shelf,
      //     slot: this.vslot,
      //     port: this.port,
      //     ont: this.ONT,
      //     cvlan: this.CVLAN
      //   })
      //   if (kq.data.error_code == 'BSS-00000000') {
      //     this.$emit('tsbtnChapNhan_Clicked', { data: this.rdoType.value })
      //   } else {
      //   }
      // } else {
      //   this.$emit('tsbtnChapNhan_Clicked', { type: this.rdoType.value })
      // }
      this.$emit('tsbtnChapNhan_Clicked', { type: this.rdoType.value })
    },
    async tsbtnHuy_Clicked() {
      this.$emit('tsbtnHuy_Clicked')
    }
  },
  async mounted() {
    console.log('frmHCIMSKickHoat')

    this.txtUser.value = this.user
    this.txtPass.value = this.pass

    this.txtSystemID.value = this.systemID
    this.txtRack.value = this.rack
    this.txtShelf.value = this.shelf
    this.txtSlot.value = this.vslot
    this.txtPort.value = this.port
    this.txtONT.value = this.ONT
    this.txtCVLAN.value = this.CVLAN
  }
}
</script>
<style scoped></style>
