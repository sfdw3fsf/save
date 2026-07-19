<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import api from './api'
import moment from 'moment'
import Vue from 'vue'
import { DateTimePickerPlugin } from '@syncfusion/ej2-vue-calendars'
Vue.use(DateTimePickerPlugin)
export default {
  components: {},
  name: 'EditGridCell',
  props: {},
  mounted() {
    // console.log(this.data)
  },
  destroyed() {},
  computed: {},
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  data() {
    return {
      loading: false,
      data: {},
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput: true
      },
      dateFormat: 'dd/MM/yyyy',
      dateTimeFormat: 'dd/MM/yyyy HH:mm:ss',
      fields: { text: 'GIATRI', value: 'GTTT_ID' }
    }
  },
  methods: {
    onChangeValueComboBox(args) {
      if (args.itemData) this.$store.dispatch('updateDeviceType/setValueThuocTinh', { id: this.data.thuoctinh_id, gttt_id: args.itemData.GTTT_ID, giatri: args.itemData.GIATRI })
    },
    onChangeValueText(args) {
      this.$store.dispatch('updateDeviceType/setValueThuocTinh', { id: this.data.thuoctinh_id, gttt_id: null, giatri: this.data.giatri })
    },
    onChangeValueDate(args) {
      if (args.value) this.$store.dispatch('updateDeviceType/setValueThuocTinh', { id: this.data.thuoctinh_id, gttt_id: null, giatri: moment(args.value).format('DD/MM/YYYY') })
    },
    onChangeValueDateTime(args) {
      if (args.value) this.$store.dispatch('updateDeviceType/setValueThuocTinh', { id: this.data.thuoctinh_id, gttt_id: null, giatri: moment(args.value).format('DD/MM/YYYY HH:mm:ss') })
    },
    openPopupUploadImage() {
      this.$refs.editValue.blur()
      this.$parent.$parent.$parent.showPopupImageUpload(this.data)
    }
  }
}
</script>
