<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjThongSoKT"  :position="position"  :close="closeDialog" :header="'Thông số kỹ thuật'" showCloseIcon="true" width="60%" target="#app .main-wrapper">
    <div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="Thoat"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát</a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông số kỹ thuật thuê bao</div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row" v-show="!Loading"><span v-html="thongSoKTHtml"></span></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import api from '../Api.js'
export default {
  components: {},
  name: 'ThongSoKyThuat',
  props: ['ptarget','thuebaoid'],
  data() {
    return {
      Loading: false,
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      thongSoKT: []
    }
  },
  watch: {
    Loading: function (val) {
      this.$root.showLoading(val)
    }
  },
  created() {},
  computed: {
    target: function () {
      if (this.ptarget != null && this.ptarget != '') {
        return this.ptarget
      } else {
        return '#app .main-wrapper-tskt'
      }
    },
    thongSoKTHtml: function () {
      let outHtml = ''
      if (this.thongSoKT != null && this.thongSoKT.length > 0) {
        outHtml = this.thongSoKT.map((item) => item.ten_truong + ' : ' + (item.giatri == null ? ' ' : item.giatri)).join('<br/>')
        //         this.thongSoKT.forEach(function (item) {
        //           outHtml = outHtml + item.ten_truong + ':' + item.giatri + ','
        //         })
      } else {
        outHtml = 'Chưa có dữ liệu'
      }
      return outHtml
    }
  },
  methods: {
    async lay_ds_thongtin_kythuat(thuebao_id) {
      var input = { vthuebao_id: thuebao_id }
      this.thongSoKT = this.GetData(await api.lay_ds_thongtin_kythuat(this.axios, input))
    },
    openDialog: function (thuebao_id) {
      this.Loading = true
      this.thongSoKT = []
      Promise.all([this.lay_ds_thongtin_kythuat(thuebao_id)])
        .then((responses) => {})
        .catch((err) => {
          console.log(err)
          //this.ShowError(err)
        })
        .finally(() => {
          this.Loading = false
          this.$refs.dialogObjThongSoKT.show()
        })
    },
    Thoat: function () {
      this.$refs.dialogObjThongSoKT.hide()
    },
    closeDialog: function () {
      this.$emit('closeThongSoKT')
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>
<style scoped>
</style>