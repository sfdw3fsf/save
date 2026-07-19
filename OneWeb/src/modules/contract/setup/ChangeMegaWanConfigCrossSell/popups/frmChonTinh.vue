<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" ref="dialogObjChonTinh" :position="position" :close="closeDialog" :header="'Chọn tỉnh bán chéo hợp đồng'" showCloseIcon="false" width="500px" target="#app .main-wrapper">
    <div class="main-wrapper-popup main-wrapper-popup-chontinh">
      <div class="popup-body popupChuyenTo">
        <div class="box-form">
          <div class="info-row">
            <div class="key">Chọn tỉnh</div>
            <div class="value">
              <SelectExt :dataSource="dsTinh" v-model="tinh_id" dataTextField="tentinh" dataValueField="tinh_id" />
            </div>
          </div>
        </div>
        <div class="info-row">
          <p class="text-center">
            <a href="javascript:void(0);" @click.prevent="btnChapNhan_Click" class="btn btn-default btn-primary"> <span class="icon one-file-attach"></span> Chấp nhận </a>
          </p>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import api2 from './PopUpApi.js'
export default {
  components: {
    api2
  },
  name: 'frmChonTinh',
  data() {
    return {
      Loading: false,
      tinh_id: 0,
      dsTinh: [],
      position: { X: 'center', Y: 'top' },
      animationSettings: { effect: 'Zoom' }
    }
  },
  mounted() {
    // this.showDialog()
  },
  methods: {
    async layDsTinh() {
      let response = await api2.lay_ds_tinh_thicong_combobox(this.axios, {})
      if (response.data.error == '200' && response.data.error_code == 'BSS-00000000') {
        this.dsTinh = response.data.data
        if (this.dsTinh != null && this.dsTinh.length > 0) this.tinh_id = this.dsTinh[0].tinh_id
        // console.log('layDsTinh', this.dsTinh)
        return response.data.data
      }
      return []
    },
    async tinh_thicong(tinh_id) {
      let response = await api2.tinh_thicong(this.axios, { tinhthicong_id: tinh_id })
      if (response.data.error == '200' && response.data.error_code == 'BSS-00000000') {
        if (response.data.data != null && response.data.data.length > 0) {
          let tinh_tc = response.data.data[0]
          tinh_tc.tentinh = this.dsTinh.filter((x) => x.tinh_id == tinh_id)[0].tentinh
          tinh_tc.tinhthicong_id = tinh_tc.tinh_id
          return tinh_tc
        }
      }
      return {}
    },
    showDialog() {
      try {
        this.loading(true)
        Promise.all([this.layDsTinh()])
          .then((r) => {
            console.log('showDialog')
          })
          // .error((er) => {
          //   console.log(er)
          // })
          .finally(() => {
            console.log('showDialog:Promise')
          })
      } catch (err) {
        console.log('🚀 ~ file: frmChonTinh.vue ~ line 42 ~ showDialog ~ err', err)
      } finally {
        this.loading(false)
        this.$refs.dialogObjChonTinh.show()
      }
    },
    async closeDialog() {
      try {
        // let tinhtc = await this.tinh_thicong(this.tinh_id)
        // this.$emit('closeChonTinh', tinhtc)
        
      } catch (err) {
        // console.log('🚀 ~ file: frmChonTinh.vue ~ line 86 ~ closeDialogChuyenTo ~ err', err)
      }
    },
    async btnChapNhan_Click() {
      try {
        this.Loading = true
        let tinhtc = await this.tinh_thicong(this.tinh_id)
        this.$emit('closeChonTinh', tinhtc)
        this.$refs.dialogObjChonTinh.hide()
      } catch (err) {
        console.log('🚀 ~ file: frmChonTinh.vue ~ line 90 ~ btnChapNhan_Click ~ err', err)
      } finally {
        this.Loading = false
      }
    }
  }
}
</script>
