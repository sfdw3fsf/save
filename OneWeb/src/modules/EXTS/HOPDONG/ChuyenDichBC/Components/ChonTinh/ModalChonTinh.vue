<template>
    <b-modal
        :id="id"
        ref="popupChonTinh"
        size="sm"
        body-class="p-0"
        title="Chọn tỉnh bán chéo hợp đồng"
        hide-footer
        @shown="onShownModal"
        @hidden="onHideModal"
    >
        <div class="popup-body">
            <div class="info-row">
                <div class="key w80">Chọn tỉnh:</div>
                <div class="value">
                    <div class="select-custom" ref="cboTinh">
                        <SelectExt
                            v-model="cbo_Tinh.selected"
                            :settings="{dropdownParent: $refs['cboTinh']}"
                            :dataSource="cbo_Tinh.items"
                            dataTextField="tentinh"
                            dataValueField="tinh_id" />
                    </div>
                </div>
            </div>
            <div class="group-buttons -bottom center">
                <button class="btn btn-second nocorner" @click="onChapNhan">
                    <span class="icon one-save"></span> Chấp nhận
                </button>
            </div>
        </div>
    </b-modal>
</template>
<script>
export default {
  name: 'ChonTinh',
  props: ['id'],
  data () {
    return {
      cbo_Tinh: {
        items: [],
        selected: null
      },
      resolvePromise: {},
      rejectPromise: {}
    }
  },

  methods: {

    async show () {
      return new Promise((resolve, reject) => {
        this.$bvModal.show(this.id)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },

    async onShownModal () {
      this.cbo_Tinh.items = await this.getDsTinhThiCong()
      if (this.cbo_Tinh.items.length > 0) { this.cbo_Tinh.selected = this.cbo_Tinh.items[0].tinh_id }
    },

    async onHideModal () {
      let ds = []
      let tinh = null
      if (this.cbo_Tinh.selected != null) {
        ds = await this.getTtTinhThiCong(this.cbo_Tinh.selected)
        tinh = this.cbo_Tinh.items.find(e => e.tinh_id === Number(this.cbo_Tinh.selected))
      }
      let item = this.tinhThiCong(ds)
      if (tinh != null) { item.tentinh = tinh.tentinh }
      this.resolvePromise({item})
      this.cbo_Tinh = {
        items: [],
        selected: null
      }
    },

    onChapNhan () {
      this.$bvModal.hide(this.id)
    },

    tinhThiCong (ds) {
      let tinh = {
        user: '',
        pass: '',
        nhanvien_id: 0,
        donvi_id: 0,
        matinh: '',
        TNS: '',
        tinhthicong_id: 0,
        ip_dll: ''
      }
      if (ds.length > 0) {
        tinh.user = ds[0].ten_truycap
        tinh.pass = ds[0].mat_khau
        tinh.nhanvien_id = ds[0].nhanvien_id
        tinh.donvi_id = ds[0].donvi_id
        tinh.matinh = ds[0].matinh
        tinh.TNS = ds[0].tns_string
        tinh.tinhthicong_id = ds[0].tinh_id
        tinh.mavung = ds[0].ma_vung
        tinh.ip_dll = ds[0].ip_dll
      }
      return tinh
    },

    async getDsTinhThiCong () {
      try {
        this.loading(true)
        let url = `/web-hopdong/chontinh/lay-ds-tinh-thicong`
        let response = await this.$root.context.get(url)
        if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
          return response.data
        } return []
      } catch (error) {
        console.log(error)
        return []
      } finally {
        this.loading(false)
      }
    },

    async getTtTinhThiCong (tinhId) {
      try {
        this.loading(true)
        let url = `/web-hopdong/chontinh/lay-tt-tinh-thicong?tinhId=${tinhId}`
        let response = await this.$root.context.get(url)
        if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
          return response.data
        } return []
      } catch (error) {
        console.log(error)
        return []
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>
