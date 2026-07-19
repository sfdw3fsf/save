<template src='./MyTVCameraSale.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from 'vuex'
import moment from 'moment'
import Vue from 'vue'
import breadcrumb from '@/components/breadcrumb'
import { Group, Page, Filter, Sort, Resize } from '@syncfusion/ej2-vue-grids'
import { Query, Predicate } from '@syncfusion/ej2-data'
import XLSX from 'xlsx'
import ClickOutside from 'vue-click-outside'
// Vue.prototype.$eventHub = new Vue();
export default {
  components: { XLSX, breadcrumb },
  name: 'MyTVCameraSale',
  mounted() {},
  provide: {
    grid: [Group, Page, Filter, Sort, Resize]
  },
  directives: {
    ClickOutside
  },
  data() {
    return {
      header: {
        title: 'MyTVCameraSale',
        list: []
      },
      target: '.main-wrapper'
    }
  },
  methods: {
    dangkysudungcamera() {},
    async getMaTBKhachHang() {
      this.$root.toastSuccess("Lấy mã thiết bị khách hàng!");
    },
    openDialog() {
      this.$refs.popup_mytvamerasale.show()
    },
    dialogClose() {},
    dialogOpen() {
      this.$refs.popup_mytvamerasale.show()
    }
  },
  created: async function () {
    await this.getMaTBKhachHang()
    //await this.frm_Load()
  },
  watch: {},
  destroyed() {}
}
</script>