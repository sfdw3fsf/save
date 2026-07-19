<template src='./PopupMyTVCameraSale.html'></template>
<!-- <style scoped src='./PopupMyTVCameraSale.scss'></style> -->
<script>
import { mapActions, mapState, mapGetters, mapMutations } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import Vue from 'vue'

import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { Query, Predicate } from '@syncfusion/ej2-data'
import MyTVCameraSale from './MyTVCameraSale/MyTVCameraSale'

export default {
  components: { breadcrumb, MyTVCameraSale },
  name: 'popupMyTVCameraSale',
  mounted() {
    var f = this.$refs.frmMyTVCameraSale
    f.openDialog()
  },

  data() {
    return {}
  },

  computed: {},
  methods: {
    openForm() {
      var f = this.$refs.frmMyTVCameraSale
      f.openDialog()
    }
  },
  created: async function () {}
}
</script>