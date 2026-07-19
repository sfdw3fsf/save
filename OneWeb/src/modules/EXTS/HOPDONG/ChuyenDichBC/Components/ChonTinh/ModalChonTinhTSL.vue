<template>
    <b-modal
        :id="id"
        ref="popupChonTinhTSL"
        size="sm"
        body-class="p-0"
        title="Chọn tỉnh TSL"
        hide-footer
        @shown="onShownModal"
        @hidden="onHideModal"
    >
        <div class="popup-body">
            <div class="info-row">
                <div class="key w120">Chọn tỉnh đầu:</div>
                <div class="value">
                    <div class="select-custom" ref="cboTinhDau">
                        <SelectExt
                            v-model="cbo_TinhDau.selected"
                            :settings="{dropdownParent: $refs['cboTinhDau']}"
                            :dataSource="cbo_TinhDau.items"
                            dataTextField="tentinh"
                            dataValueField="tinh_id" />
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Chọn tỉnh cuối:</div>
                <div class="value">
                    <div class="select-custom" ref="cboTinhCuoi">
                        <SelectExt
                            v-model="cbo_TinhCuoi.selected"
                            :settings="{dropdownParent: $refs['cboTinhCuoi']}"
                            :dataSource="cbo_TinhCuoi.items"
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
  name: 'ChonTinhTSL',
  props: ['id'],
  data () {
    return {
      cbo_TinhDau: {
        items: [],
        selected: null
      },
      cbo_TinhCuoi: {
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
      let ds = await this.getDsTinhThiCongV3()
      this.cbo_TinhDau.items = ds
      this.cbo_TinhCuoi.items = ds
      if (ds.length > 0) {
        this.cbo_TinhDau.selected = this.cbo_TinhDau.items[0].tinh_id
        this.cbo_TinhCuoi.selected = this.cbo_TinhCuoi.items[0].tinh_id
      }
    },

    async onHideModal () {
      let item = {}
      let dsDau = []
      let tinhDau = null
      let dsCuoi = []
      let tinhCuoi = null
      if (this.cbo_TinhDau.selected != null) {
        dsDau = await this.getTtTinhThiCong(this.cbo_TinhDau.selected)
        tinhDau = this.cbo_TinhDau.items.find(e => e.tinh_id === Number(this.cbo_TinhDau.selected))
      }
      if (this.cbo_TinhCuoi.selected != null) {
        dsCuoi = await this.getTtTinhThiCong(this.cbo_TinhCuoi.selected)
        tinhCuoi = this.cbo_TinhCuoi.items.find(e => e.tinh_id === Number(this.cbo_TinhCuoi.selected))
      }
      item.tinhdau = this.tinhThiCong(dsDau)
      item.tinhcuoi = this.tinhThiCong(dsCuoi)
      if (tinhDau != null) { item.tinhdau.tentinh = tinhDau.tentinh }
      if (tinhCuoi != null) { item.tinhcuoi.tentinh = tinhCuoi.tentinh }
      this.resolvePromise({item})

      // Clean data:
      this.cbo_TinhDau = {
        items: [],
        selected: null
      }
      this.cbo_TinhCuoi = {
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

    async getDsTinhThiCongV3 () {
      try {
        this.loading(true)
        let url = `/web-hopdong/chontinh/lay-ds-tinh-thicong-v3`
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
