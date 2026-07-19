<template src="./index.template.html"></template>
<script>
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue, maxLength } from 'vuelidate/lib/validators'

export default {
  name: 'ModalDonViLoi',
  props: { modalID: null },
  components: {
    BssErrorMarker, BssRequiredMarker
  },
  validations: {
    focusedRow:
    {
      NOIDUNG: {
        required,
        maxLength: maxLength(200)
      }
    }
  },
  created: async function () {
  },
  data: function () {
    return {
      khieunai_id: null,
      dsNhanvien: [],
      dsNhanvienLoi: [],
      focusedRow: {
        NOIDUNG: '',
        TEN_NV: ''
      },
      dsNhanvienChon: [],
      dsNhanvienLoiChon: []
    }
  },
  watch: {

  },
  computed: {

  },
  methods: {
    hiddenModal () { this.$v.$reset() },
    tsbtnThoat () { this.closeModal() },
    async tsbtnXoaNV_Click () {
      try {
        if (this.dsNhanvienLoi.length === 0) return false
        this.$root.showLoading(true)
        console.log('this.dsNhanvienLoi', this.dsNhanvienLoi)
        var index = this.dsNhanvienLoi.length - 1
        for (let i = this.dsNhanvienLoi.length - 1; i >= 0; i--) {
          if (this.dsNhanvienLoi[i].CHON === 1) {
            this.dsNhanvien.push({
              CHON: 0,
              MA_DV: this.dsNhanvien[index].MA_DV,
              TEN_DV: this.dsNhanvien[index].TEN_DV,
              DONVI_ID: this.dsNhanvien[index].DONVI_ID
            })
            this.dsNhanvienLoi.splice(i, 1)
          }
        }
        try {
          let data = {
            VPHANVUNG_ID: this.$root.token.getPhanVungID(),
            VKHIEUNAI_ID: this.khieunai_id,
            VNHANVIEN_ID: null,
            VNOIDUNG: null,
            VKIEU: 3,
            VLOAI: 2,
            PROCEDURE_NAME: 'CABMAN.PKG_SINHDN.SP_CAPNHAT_LOINHANVIEN'
          }
          console.log('tsbtnXoaNV_Click', data)
          let rs = await this.$root.context.post(
            '/web-cabman/ban-do-mang-cap/execute1', data
          )
          for (let index = 0; index < this.dsNhanvienLoi.length; index++) {
            const e = this.dsNhanvienLoi[index]
            let data = {
              VPHANVUNG_ID: this.$root.token.getPhanVungID(),
              VKHIEUNAI_ID: this.khieunai_id,
              VNHANVIEN_ID: e.DONVI_ID,
              VNOIDUNG: e.NOIDUNG,
              VKIEU: 1,
              VLOAI: 2,
              PROCEDURE_NAME: 'CABMAN.PKG_SINHDN.SP_CAPNHAT_LOINHANVIEN'
            }
            console.log('tsbtnGhiLai_Click', data)
            let rs = await this.$root.context.post(
              '/web-cabman/ban-do-mang-cap/execute1', data
            )
          }
        } catch (error) {
          this.$root.showLoading(false)
        }
      } catch (error) {
        this.$root.showLoading(false)
      }
      this.$root.showLoading(false)
    },
    async tsbtnGhiLai_Click () {
      try {
        this.$v.$touch()
        if (this.$v.$invalid && !this.$v.focusedRow.NOIDUNG.required) {
          this.$root.showLoading(false)
          this.$root.$toast.error('Bạn chưa nhập nội dung !')
          this.$refs.NOIDUNG.focus()
          return false
        }

        if (this.$v.$invalid && !this.$v.focusedRow.NOIDUNG.maxLength) {
          this.$root.$toast.error('Nội dung nhập không vượt quá 200 ký tự !')
          this.$root.showLoading(false)
          this.$refs.NOIDUNG.focus()
          return false
        }

        this.$root.showLoading(true)
        await this.load_dsnvloi()
        if (this.dsNhanvien.length === 0) {
          this.$root.showLoading(false)
          return false
        }
        if (this.dsNhanvien.length > 0) {
          if (this.focusedRow.NOIDUNG === '') {
            this.$root.$toast.error('Bạn chưa nhập nội dung !')
            this.$root.showLoading(false)
            return
          }
          var index = this.dsNhanvien.length - 1
          while (index >= 0) {
            if (this.dsNhanvien[index].CHON === 1) {
              this.dsNhanvienLoi.push({
                KHIEUNAI_ID: null,
                CHON: 1,
                MA_DV: this.dsNhanvien[index].MA_DV,
                TEN_DV: this.dsNhanvien[index].TEN_DV,
                DONVI_ID: this.dsNhanvien[index].DONVI_ID
              })
              this.dsNhanvien.splice(index, 1)
            }
            index -= 1
          }
          if (this.dsNhanvienLoi.length === 0) { return false }
          for (let index = 0; index < this.dsNhanvienLoi.length; index++) {
            const e = this.dsNhanvienLoi[index]
            if (e.KHIEUNAI_ID === null && e.CHON === 1) {
              let data = {
                VPHANVUNG_ID: this.$root.token.getPhanVungID(),
                VKHIEUNAI_ID: this.khieunai_id,
                VNHANVIEN_ID: e.DONVI_ID,
                VNOIDUNG: this.focusedRow.NOIDUNG,
                VKIEU: 1,
                VLOAI: 2,
                PROCEDURE_NAME: 'CABMAN.PKG_SINHDN.SP_CAPNHAT_LOINHANVIEN'
              }
              console.log('tsbtnGhiLai_Click', data)
              var rs = await this.$root.context.post(
                '/web-cabman/ban-do-mang-cap/execute1', data
              )
            } else {
              let data = {
                VPHANVUNG_ID: this.$root.token.getPhanVungID(),
                VKHIEUNAI_ID: this.khieunai_id,
                VNHANVIEN_ID: e.DONVI_ID,
                VNOIDUNG: this.focusedRow.NOIDUNG,
                VKIEU: 2,
                VLOAI: 2,
                PROCEDURE_NAME: 'CABMAN.PKG_SINHDN.SP_CAPNHAT_LOINHANVIEN'
              }
              var rs = await this.$root.context.post(
                '/web-cabman/ban-do-mang-cap/execute1', data
              )
            }
          }
          await this.load_dsnvloi()
        } else {
          this.dsNhanvienLoi = []
        }
      } catch (error) {
        console.log('error', error)
        this.$root.showLoading(false)
      }
      this.$root.showLoading(false)
    },
    griddsNhanvien_rowDeselected (args) {
      try {
        this.dsNhanvienChon = this.$refs.griddsNhanvien.getSelectedRecords()
        this.dsNhanvien[args.rowIndex].CHON = 0
      } catch (error) {

      }
    },
    griddsNhanvien_rowSelected (args) {
      try {
        this.dsNhanvienChon = this.$refs.griddsNhanvien.getSelectedRecords()
        this.dsNhanvien[args.rowIndex].CHON = 1
      } catch (error) {

      }
    },
    griddsNhanvienLoi_rowDeselected (args) {
      try {
        this.dsNhanvienLoiChon = this.$refs.griddsNhanvienLoi.getSelectedRecords()
        this.dsNhanvienLoi[args.rowIndex].CHON = 0
      } catch (error) {
      }
    },
    griddsNhanvienLoi_rowSelected (args) {
      try {
        this.dsNhanvienLoiChon = this.$refs.griddsNhanvienLoi.getSelectedRecords()
        this.dsNhanvienLoi[args.rowIndex].CHON = 1
      } catch (error) {
      }
    },
    griddsNhanvienLoi_SelectedRowChanged (data) {
      console.log('griddsNhanvienLoi_SelectedRowChanged', data)
      this.focusedRow = data
    },
    async load_dsnvloi () {
      try {
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/execute1', {VPHANVUNG_ID: this.$root.token.getPhanVungID(), VKHIEUNAI_ID: this.khieunai_id, VLOAI: 2, PROCEDURE_NAME: 'CABMAN.PKG_SINHDN.SP_LAY_DS_LOI_KHIEUNAI'}
        )
        this.dsNhanvienLoi = rs.data
        console.log('this.dsNhanvienLoi', this.dsNhanvienLoi)
        if (this.dsNhanvienLoi.length > 0) {

        }
      } catch (error) {
        this.dsNhanvienLoi = []
      }
    },
    async shownModal () {
      try {
        this.$v.$reset()
        if (this.khieunai_id === null) return
        this.$root.showLoading(true)
        if (this.dsNhanvien.length === 0) {
          var rs = await this.$root.context.post(
            '/web-cabman/ban-do-mang-cap/execute1', {VPHANVUNG_ID: this.$root.token.getPhanVungID(), VKHIEUNAI_ID: this.khieunai_id, VLOAI: 2, PROCEDURE_NAME: 'CABMAN.PKG_SINHDN.SP_DS_NHANVIEN_PHAT'}
          )
          this.dsNhanvien = rs.data
          console.log('this.dsNhanvien', this.dsNhanvien)
        }
        await this.load_dsnvloi()
        this.$root.showLoading(false)
      } catch (error) {
        this.dsNhanvien = []
        this.$root.showLoading(false)
      }
    },
    closeModal () {
      this.resolvePromise({ok: false, data: null})
      this.$bvModal.hide(this.modalID)
    },
    show (khieunai_id) {
      return new Promise((resolve, reject) => {
        this.khieunai_id = khieunai_id
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    }
  },
  mounted: function () {
  }
}
</script>
<style>
</style>
