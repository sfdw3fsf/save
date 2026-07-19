<template>
    <ejs-dialog
      :enableResize='true'
      :allowDragging='true'
      :visible='false'
      :isModal="true"
      :animationSettings='animationSettings'
      ref="dlgChonDiaChi"
      :header='"Chọn địa chỉ"'
      showCloseIcon=true
      width='1100px'
      height='400'
      :target='target'
      :close="onDialogClose"
      :open="onDialogOpen"
      >
    <div class="modal-content popup-box">
        <div class="popup-top-action">
          <div class="acept item pointer" v-on:click.prevent="doAccept">
              <span class="icon -ap icon-ion-ios-checkmark"></span> Chấp nhận
          </div>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-12 col-12">
                        <div class="info-row">
                            <div class="key w100"></div>
                            <div class="value" style="color:blue">{{data.daydiachi}}</div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w100">Tỉnh/TP <span style="color: red">*</span></div>
                            <div class="value">
                                <ejs-dropdownlist :change="onTinhChange" :filtering='onFilteringTinh'
                                    :allowFiltering="true" :dataSource='params.p_tinh_options' :fields="configs.fieldsTinh"
                                    v-model="diachi.tinh_id" :placeholder="'Chọn tỉnh'"></ejs-dropdownlist>
                                </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Quận/Huyện <span style="color: red">*</span></div>
                            <div class="value">
                                <ejs-dropdownlist :change="onQuanChange" :filtering='onFilteringQuan'
                        :allowFiltering="true" :dataSource='params.p_quan_options' :fields="configs.fieldsQuan"
                        v-model="diachi.quan_id" :placeholder="'Chọn quận'"></ejs-dropdownlist>
                            </div>
                        </div>

                        <div class="info-row">
                            <div class="key w100">Phường/Xã <span style="color: red">*</span></div>
                            <div class="value">
                                <ejs-dropdownlist :change="onPhuongChange" :filtering='onFilteringPhuong'
                          :allowFiltering="true" :dataSource='params.p_phuong_options' :fields="configs.fieldsPhuong"
                          v-model="diachi.phuong_id" :placeholder="'Chọn phường'"></ejs-dropdownlist>
                            </div>
                        </div>

                        <div class="info-row">
                            <div class="key w100">Số nhà</div>
                            <div class="value">
                                <input type="text" v-model.trim="diachi.so_nha" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="value padt7 w100">
                                <b-form-checkbox v-model="params.p_chk_pho" class="check" value='1' unchecked-value='0'>
                                    <span class="name">Đường/Phố</span>
                                </b-form-checkbox>
                            </div>
                            <div class="value">
                                <ejs-dropdownlist :change="onPhoChange" :enabled="chk_pho" :filtering='onFilteringPho' :allowFiltering="true"
                          :dataSource='params.p_pho_options' :fields="configs.fieldsPho" v-model="diachi.pho_id"
                          :placeholder="'Chọn phố'"></ejs-dropdownlist>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="value padt7 w100">
                                <b-form-checkbox v-model="params.p_chk_ap" class="check" value='1' unchecked-value='0'>
                                <span class="name">Ngõ/Ấp</span>
                                </b-form-checkbox>
                            </div>
                            <div class="value">
                                <ejs-dropdownlist :change='onApChange' :enabled="chk_ap" :filtering='onFilteringAp' :allowFiltering="true"
                                :dataSource='params.p_ap_options' :fields="configs.fieldsAp" v-model="diachi.ap_id"
                                :placeholder="'Chọn ấp'"></ejs-dropdownlist>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="value padt7 w100">
                                <b-form-checkbox v-model="params.p_chk_khu" class="check" value='1' unchecked-value='0'>
                                <span class="name">Khu</span>
                                </b-form-checkbox>
                            </div>
                            <div class="value">
                                <ejs-dropdownlist :change='onKhuChange' :enabled="chk_khu" :filtering='onFilteringKhu' :allowFiltering="true"
                                :dataSource='params.p_khu_options' :fields="configs.fieldsKhu" v-model="diachi.khu_id"
                                :placeholder="'Chọn khu'"></ejs-dropdownlist>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="value padt7 w100">
                                <b-form-checkbox v-model="params.p_chk_dacdiem" class="check" value='1' unchecked-value='0'>
                                <span class="name">Đặc điểm</span>
                                </b-form-checkbox>
                            </div>
                            <div class="value">
                                <ejs-dropdownlist :enabled="chk_dacdiem" :filtering='onFilteringDacDiem' :allowFiltering="true"
                                :dataSource='params.p_dacdiem_options' :fields="configs.fieldsDacDiem"
                                v-model="diachi.dacdiem_id" :placeholder="'Chọn đặc điểm'"></ejs-dropdownlist>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12 col-12">
                        <div class="info-row">
                            <div class="key w100">Địa chỉ</div>
                            <div class="value">
                                <textarea name="" v-model="diachimoi" class="form-control" style="height: 100px;resize: none;" readonly></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div> <!-- end body -->
    </div>
    </ejs-dialog>
  </template>
<script>

import Vue from 'vue'
import { Query } from '@syncfusion/ej2-data'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import CommonsAPI from '../../api/Commons'

Vue.use(DialogPlugin)
export default Vue.extend({
  components: {
  },
  props: {
    data: {
      type: Object,
      required: false
    }
  },
  computed: {
    chk_pho () {
      return this.params.p_chk_pho === '1'
    },
    chk_ap () {
      return this.params.p_chk_ap === '1'
    },
    chk_khu () {
      return this.params.p_chk_khu === '1'
    },
    chk_dacdiem () {
      return this.params.p_chk_dacdiem === '1'
    },
    thaydoiDiaChi () {
      return this.diachi.diachimoi
    }
  },
  async created () {
  },
  mounted () {
  },
  destroyed () {
  },
  data () {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      configs: {
        fieldsTinh: { text: 'TENTINH', value: 'TINH_ID' },
        fieldsQuan: { text: 'TEN_QUAN', value: 'QUAN_ID' },
        fieldsPhuong: { text: 'TEN_PHUONG', value: 'PHUONG_ID' },
        fieldsPho: { text: 'TEN', value: 'ID' },
        fieldsAp: { text: 'TEN', value: 'ID' },
        fieldsKhu: { text: 'TEN', value: 'ID' },
        fieldsDacDiem: { text: 'DACDIEM', value: 'DACDIEM_ID' }
      },
      diachi: {
        tinh_id: null,
        quan_id: null,
        phuong_id: null,
        ap_id: null,
        pho_id: null,
        khu_id: null,
        dacdiem_id: null,
        so_nha: null
      },
      diachimoi: null,
      params: {
        p_tinh_options: [],
        p_quan_options: [],
        p_phuong_options: [],
        p_chk_pho: '0',
        p_pho_options: [],
        p_chk_ap: '0',
        p_ap_options: [],
        p_chk_khu: '0',
        p_khu_options: [],
        p_chk_dacdiem: '0',
        p_dacdiem_options: []
      }
    }
  },
  methods: {
    openDialog () {
      this.$refs.dlgChonDiaChi.show()
    },
    closeDialog () {
      this.$refs.dlgChonDiaChi.hide()
    },
    onDialogClose () {
    },
    async onDialogOpen () {
      console.log(this.data)
      this.clearData()
      if (this.data !== null) {
        if (this.data.pho_id !== null && this.data.pho_id > 0) {
          this.params.p_chk_pho = '1'
        }
        if (this.data.ap_id !== null && this.data.ap_id > 0) {
          this.params.p_chk_ap = '1'
        }
        if (this.data.khu_id !== null && this.data.khu_id > 0) {
          this.params.p_chk_khu = '1'
        }
        if (this.data.dacdiem_id !== null && this.data.dacdiem_id > 0) {
          this.params.p_chk_dacdiem = '1'
        }
        this.diachi.so_nha = this.data.so_nha
      }
      await this.getTinh()
    },
    clearData () {
      this.diachi.tinh_id = null
      this.diachi.quan_id = null
      this.diachi.phuong_id = null
      this.diachi.pho_id = null
      this.diachi.ap_id = null
      this.diachi.khu_id = null
      this.diachi.dacdiem_id = null
      this.diachi.so_nha = ''
      this.params.p_chk_pho = '0'
      this.params.p_chk_ap = '0'
      this.params.p_chk_khu = '0'
      this.params.p_chk_dacdiem = '0'
    },
    /* callback */
    onFilteringQuan: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_QUAN', 'contains', e.text, true) : query
      e.updateData(this.params.p_quan_options, query)
    },
    onFilteringPhuong: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_PHUONG', 'contains', e.text, true) : query
      e.updateData(this.params.p_phuong_options, query)
    },
    onFilteringPho: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.params.p_pho_options, query)
    },
    onFilteringAp: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.params.p_ap_options, query)
    },
    onFilteringKhu: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.params.p_khu_options, query)
    },
    onFilteringDacDiem: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.params.p_dacdiem_options, query)
    },
    onFilteringTinh: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TENTINH', 'contains', e.text, true) : query
      e.updateData(this.params.p_tinh_options, query)
    },
    doAccept () {
        let errors = []
        if (this.diachi.tinh_id === null) {
            errors.push('Hãy chọn tỉnh / thành phố')
        }
        if (this.diachi.quan_id === null) {
            errors.push('Hãy chọn quận/ huyện')
        }
        if (this.diachi.phuong_id === null) {
            errors.push('Hãy chọn phường / xã')
        }
        if (this.params.p_chk_pho === '1' && this.diachi.pho_id === null) {
            errors.push('Hãy chọn đường / phố')
        }
        if (this.params.p_chk_ap === '1' && this.diachi.ap_id === null) {
            errors.push('Hãy chọn ngõ / ấp')
        }
        if (this.params.p_chk_khu === '1' && this.diachi.khu_id === null) {
            errors.push('Hãy chọn khu')
        }
        if (this.params.p_chk_dacdiem === '1' && this.diachi.dacdiem_id === null) {
            errors.push('Hãy chọn đăc điểm')
        }
        if (errors.length > 0) {
            this.$toast.error(errors.join('\r\n'))
            return false
        }
      let res = {...this.diachi}
      res.diachimoi = this.diachimoi
      this.$emit('xacnhan', res)
      this.$refs.dlgChonDiaChi.hide()
    },
    getTinh: async function () {
      try {
        this.$root.showLoading(true)
        this.params.p_tinh_options = []
        let rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/tra-cuu/lay-danh-sach-tinh'
        )
        this.params.p_tinh_options = rs.data

        if (this.data !== null && this.data.tinh_id !== null) {
          this.diachi.tinh_id = this.data.tinh_id
        }
        if(!this.diachi.tinh_id) {
            this.diachi.tinh_id = parseInt(this.$root.token.getPhanVungID())
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    /* DANH MỤC */
    async getQuanHuyen (tinhId) {
      if(!tinhId) return []
      try {
        let rs = await this.$root.context.get("/web-cabman/bando_tuyencot/lay_ds_quan_huyen/" + tinhId)
        // const results = await CommonsAPI.getDanhMucQuanHuyen(this.axios)
        const data = await rs
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        console.log(err)
        return []
      }
    },
    async getPhuongXa (QuanId) {
      try {
        const results = await CommonsAPI.getDanhMucPhuongTheoQuan(this.axios, { pQuanId: QuanId })
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        console.log(err)
        return []
      }
    },
    async getPho (PhuongId) {
      try {
        const results = await CommonsAPI.getDanhMucPhoApKhuTheoPhuong(this.axios, { pPhuongId: PhuongId, pNhomPhoId: 1 })
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        console.log(err)
        return []
      }
    },
    async getAp (PhuongId) {
      try {
        const results = await CommonsAPI.getDanhMucPhoApKhuTheoPhuong(this.axios, { pPhuongId: PhuongId, pNhomPhoId: 2 })
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        console.log(err)
        return []
      }
    },
    async getKhu (PhuongId) {
      try {
        const results = await CommonsAPI.getDanhMucPhoApKhuTheoPhuong(this.axios, { pPhuongId: PhuongId, pNhomPhoId: 3 })
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        console.log(err)
        return []
      }
    },
    async getDacDiem () {
      try {
        // const results = await CommonsAPI.getDanhSachDonViQL(this.axios, {pNhanVienId: this.$auth.getNhanVienID()});
        let postData = {
          pPhuongId: this.diachi.phuong_id,
          pPhoId: this.params.p_chk_pho ? this.diachi.pho_id : null,
          pApId: this.params.p_chk_ap ? this.diachi.ap_id : null,
          pKhuId: this.params.p_chk_khu ? this.diachi.khu_id : null
        }
        const results = await CommonsAPI.getDanhMucDacDiem(this.axios, postData)
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        console.log(err)
        return []
      }
    },
    onTinhChange: async function (args) {
      this.params.p_quan_options = []
      this.diachi.quan_id = null
      if (args.value === null) {
        this.params.p_quan_options = []
        this.diachi.quan_id = null
        return
      }
      try {
        const res = await this.getQuanHuyen(args.value)
        this.params.p_quan_options = res
        if (this.data !== null && this.data.quan_id !== null) {
          this.diachi.quan_id = this.data.quan_id
        }
      } catch (error) {

      }
    },
    onQuanChange: async function (args) {
        this.params.p_phuong_options = []
        this.params.p_ap_options = []
        this.params.p_pho_options = []
        this.params.p_khu_options = []
        this.params.p_dacdiem_options = []

        this.diachi.phuong_id = null
        this.diachi.ap_id = null
        this.diachi.pho_id = null
        this.diachi.khu_id = null
        this.diachi.dacdiem_id = null

      if (args === null) {
        return
      }
      try {
        const res = await this.getPhuongXa(args.value)
        this.params.p_phuong_options = res
        if (this.data !== null && this.data.phuong_id !== null) {
          this.diachi.phuong_id = this.data.phuong_id
        }
      } catch (error) {

      }
    },
    async onPhuongChange (args) {
      this.diachi.pho_id = null
      this.diachi.ap_id = null
      this.diachi.khu_id = null
      this.diachi.dacdiem_id = null

      if (args.value === null) {
        this.params.p_pho_options = []
        this.params.p_ap_options = []
        this.params.p_khu_options = []
        this.params.p_dacdiem_options = []
        return
      }

      const res = await Promise.all([
        this.getPho(args.value),
        this.getAp(args.value),
        this.getKhu(args.value)
      ])

      this.params.p_pho_options = res[0]
      this.params.p_ap_options = res[1]
      this.params.p_khu_options = res[2]

      if (this.data.pho_id !== null && this.data.pho_id > 0) {
        this.diachi.pho_id = this.data.pho_id
      }
      if (this.data.ap_id !== null && this.data.ap_id > 0) {
        this.diachi.ap_id = this.data.ap_id
      }
      if (this.data.khu_id !== null && this.data.khu_id > 0) {
        this.diachi.khu_id = this.data.khu_id
      }

      const ret = await this.getDacDiem()
      this.params.p_dacdiem_options = ret

      if (this.data.dacdiem_id !== null && this.data.dacdiem_id > 0) {
        this.diachi.dacdiem_id = this.data.dacdiem_id
      }
    },
    async onPhoChange (args) {
      this.diachi.dacdiem_id = null
      if (args.value === null) {
        this.params.p_dacdiem_options = []
      }
      const ret = await this.getDacDiem()
      this.params.p_dacdiem_options = ret

      if (this.data.dacdiem_id !== null && this.data.dacdiem_id > 0) {
        this.diachi.dacdiem_id = this.data.dacdiem_id
      }
    },
    async onKhuChange (args) {
      this.diachi.dacdiem_id = null
      if (args.value === null) {
        this.params.p_dacdiem_options = []
      }
      const ret = await this.getDacDiem()
      this.params.p_dacdiem_options = ret

      if (this.data.dacdiem_id !== null && this.data.dacdiem_id > 0) {
        this.diachi.dacdiem_id = this.data.dacdiem_id
      }
    },
    async onApChange (args) {
      this.diachi.dacdiem_id = null
      if (args.value === null) {
        this.params.p_dacdiem_options = []
      }
      const ret = await this.getDacDiem()
      this.params.p_dacdiem_options = ret

      if (this.data.dacdiem_id !== null && this.data.dacdiem_id > 0) {
        this.diachi.dacdiem_id = this.data.dacdiem_id
      }
    },
  },
  watch: {
    diachi: {
      handler (val) {
        // console.log(val)
        this.diachimoi = ''
        if (this.diachi.tinh_id !== null) {
          let f = this.params.p_tinh_options.find(x => x.TINH_ID === this.diachi.tinh_id)
          if (f !== undefined) this.diachimoi = f.TENTINH
        }
        if (this.diachi.quan_id !== null) {
          let f = this.params.p_quan_options.find(x => x.QUAN_ID === this.diachi.quan_id)
          if (f !== undefined) this.diachimoi = f.TEN_QUAN + ', ' + this.diachimoi
        }
        if (this.diachi.phuong_id !== null) {
          let f = this.params.p_phuong_options.find(x => x.PHUONG_ID === this.diachi.phuong_id)
          if (f !== undefined) this.diachimoi = f.TEN_PHUONG + ', ' + this.diachimoi
        }
        if (this.diachi.pho_id !== null) {
          let f = this.params.p_pho_options.find(x => x.ID === this.diachi.pho_id)
          if (f !== undefined) this.diachimoi = f.TEN + ', ' + this.diachimoi
        }
        if (this.diachi.ap_id !== null) {
          let f = this.params.p_ap_options.find(x => x.ID === this.diachi.ap_id)
          if (f !== undefined) this.diachimoi = f.TEN + ', ' + this.diachimoi
        }
        if (this.diachi.khu_id !== null) {
          let f = this.params.p_khu_options.find(x => x.ID === this.diachi.khu_id)
          if (f !== undefined) this.diachimoi = f.TEN + ', ' + this.diachimoi
        }
        if (this.diachi.so_nha !== null && this.diachi.so_nha && this.diachi.so_nha.trim() !== '') {
          this.diachimoi = this.diachi.so_nha.trim() + ', ' + this.diachimoi
        }
      },
      deep: true
    }
  }
})
</script>
