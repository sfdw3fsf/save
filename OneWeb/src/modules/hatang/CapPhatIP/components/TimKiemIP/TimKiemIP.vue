<template src="./TimKiemIP.html"></template>

<script>

import Vue from 'vue'
import { Query } from '@syncfusion/ej2-data'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import Enum from "../../enum"

Vue.use(DialogPlugin)

export default Vue.extend({
  props: {

  },
  data() {
    return {
      animationSettings: { effect: 'Zoom' },
      selectionSettings: { checkboxOnly: true, type: 'Multiple' },
      target: '.main-wrapper',
      hatang_mang: null,
      hatangmangOptions: [],
      phanloai_ip: null,
      phanloaiIPOptions: [],
      ip_store: [],
      ipstoreOptions: [],
      subnet: null,
      subnetOptions: [],
      ip: null,
      dsIPAddress: []
    }
  },
  methods: {
    openDialog () {
      this.$refs.dlgTimKiemIP.show()
    },
    closeDialog () {
      this.clearForm()
      this.clearResult()

      this.$refs.dlgTimKiemIP.hide()
    },
    async onDialogOpen () {
      await this.loadDanhMuc()
    },
    async onDialogClose () {
      this.clearForm()
      this.clearResult()

    },
    async onHaTangMangChange (args) {
      console.log('onHaTangMangChange', args.value, this.phanloai_ip)
      if (args.value) {
        await this.loadSubnet(args.value, this.phanloai_ip)
      }
    },
    async onPhanLoaiIPChange (args) {
      console.log('onPhanLoaiIPChange', this.hatang_mang, args.value)
      if (args.value) {
        await this.loadSubnet(this.hatang_mang, args.value)
      }
    },
    async onSubnetChange (args) {
      await this.loadIPStore(this.hatang_mang, this.phanloai_ip, args.value)
    },
    clearForm () {
      this.phanloai_ip = null
      this.hatang_mang = null
      this.subnet = null
      this.subnetOptions = null
      this.ip_store = null
      this.ipstoreOptions = []
      this.ip = null
    },
    clearResult () {
      this.dsIPAddress = []
    },
    doAccept () {
      let ds = this.$refs.dsIPAddress.getSelectedRecords()
      console.log(ds)
      if (ds.length <= 0) {
        this.$toast.error('Chưa chọn IP trong danh sách.')
        return
      }
      let da_capphat = ds.filter(x => x.TRANGTHAI_CAPPHAT === Enum.TRANGTHAI_CAPHAT.DA_CAPPHAT).map(x => x.IP)
      if (da_capphat.length > 0) {
        this.$toast.error('IP ' + da_capphat.join(',') + ' đã cấp phát, vui lòng chọn IP khác.')
        return
      }
      this.$emit('onSelectRowsAccept', ds)
      this.closeDialog()
    },
    onRecordClick () {

    },
    onCheckBoxChange () {

    },

    async doSearch () {
      try {
        this.dsIPAddress = []
        let postData = {
          "table": "IPADDRESS",
          "conds": JSON.stringify({
            hatangmang_id: this.hatang_mang,
            phanloai_ip_id: this.phanloai_ip,
            subnet_id: this.subnet,
            ip_store: this.ip_store.length > 0 ? this.ip_store.join(',') : null
          })
        }

        const rs = await this.$root.context.post(
          `/web-ecms/danhmuc/tinhtrang-ip/get-danhmuc-khac`, postData
        );
        if (rs.data && rs.data.length > 0) {
          this.dsIPAddress = rs.data
        } else {

        }
      } catch (err) {
        console.log(err)
      }
    },
    async loadSubnet(hatangmang_id, phanloai_ip_id) {
      try {
        if (hatangmang_id == null || phanloai_ip_id == null) return

        this.subnetOptions = []
        this.subnet = null

        let postData = {
          "table": "SUBNET",
          "conds": JSON.stringify({
              "hatangmang_id" : hatangmang_id,
              "phanloai_ip_id": phanloai_ip_id
          })
        }
        const rs = await this.$root.context.post(
          `/web-ecms/danhmuc/tinhtrang-ip/get-danhmuc-khac`, postData
        );
        if (rs.data && rs.data.length > 0) {
          this.subnetOptions = rs.data
        } else {

        }

      } catch (error) {
      }
    },
    async loadIPStore(hatangmang_id, phanloai_ip_id, subnet_id) {
      try {
        if (hatangmang_id === null || phanloai_ip_id === null || subnet_id === null) return

        this.ipstoreOptions = []
        this.ip_store = []

        let postData = {
          "table": "IPSTORE",
          "conds": JSON.stringify({
              "hatangmang_id" : hatangmang_id,
              "phanloai_ip_id": phanloai_ip_id,
              "subnet_id": subnet_id,
          })
        }
        const rs = await this.$root.context.post(
          `/web-ecms/danhmuc/tinhtrang-ip/get-danhmuc-khac`, postData
        );
        if (rs.data && rs.data.length > 0) {
          this.ipstoreOptions = rs.data
        } else {

        }

      } catch (error) {
      }
    },
    async loadDanhMuc () {
      try {
        const rs = await this.$root.context.post(
          `/web-ecms/danhmuc/tinhtrang-ip/get-danhmuc`, {"ds" : "|HATANGMANG|PHANLOAIIP"}
        );
        if (rs.data && rs.data.length > 0) {
          let ds = rs.data
          this.hatangmangOptions = ds.filter(x => x.LOAI === 'HATANGMANG')
          this.phanloaiIPOptions = ds.filter(x => x.LOAI === 'PHANLOAIIP')

          if (this.phanloaiIPOptions.length > 0) {
            this.phanloai_ip = this.phanloaiIPOptions[0].ID
          }
        } else {

        }

      } catch (error) {
      }
    },
    onFilteringHaTangMang (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.hatangmangOptions, query)
    },
    onFilteringPhanLoaiIP (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.phanloaiIPOptions, query)
    },
    onFilteringIPStore (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.ipstoreOptions, query)
    },
    onFilteringSubnet (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.subnetOptions, query)
    },
  }
})

</script>
