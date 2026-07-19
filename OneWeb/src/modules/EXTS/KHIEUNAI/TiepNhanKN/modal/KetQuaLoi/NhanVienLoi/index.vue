<template src="./index.template.html"></template>
<script>
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue, maxLength } from 'vuelidate/lib/validators'

export default {
  name: 'ModalNhanVienLoi',
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
    hiddenModal () {
      this.$v.$reset()
      this.focusedRow.NOIDUNG = ''
    },
    tsbtnThoat () { this.closeModal() },
    async tsbtnXoaNV_Click () {
      try {
        if (this.dsNhanvienLoi.length === 0) return false
        this.$root.showLoading(true)
        var index = this.dsNhanvienLoi.length - 1
        for (let i = this.dsNhanvienLoi.length - 1; i >= 0; i--) {
          if (this.dsNhanvienLoi[i].CHON === 1) {
            this.dsNhanvien.push({
              CHON: 0,
              MA_NV: this.dsNhanvien[index].MA_NV,
              TEN_NV: this.dsNhanvien[index].TEN_NV,
              TEN_DV: this.dsNhanvien[index].TEN_DV,
              NHANVIEN_ID: this.dsNhanvien[index].NHANVIEN_ID
            })
            this.dsNhanvienLoi.splice(i, 1)
          }
        }
        try {
          let data = {
            VKHIEUNAI_ID: this.khieunai_id,
            VNHANVIEN_ID: null,
            VNOIDUNG: null,
            VKIEU: 3,
            VLOAI: 1
          }
          let rs = await this.$root.context.post(
            '/web-gqkn/tiep-nhan-khieu-nai/capnhat_loinhanvien', data
          )
          for (let index = 0; index < this.dsNhanvienLoi.length; index++) {
            const e = this.dsNhanvienLoi[index]
            let data = {
              VKHIEUNAI_ID: this.khieunai_id,
              VNHANVIEN_ID: e.NHANVIEN_ID,
              VNOIDUNG: e.NOIDUNG,
              VKIEU: 1,
              VLOAI: 1
            }
            let rs = await this.$root.context.post(
              '/web-gqkn/tiep-nhan-khieu-nai/capnhat_loinhanvien', data
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
                MA_NV: this.dsNhanvien[index].MA_NV,
                TEN_NV: this.dsNhanvien[index].TEN_NV,
                TEN_DV: this.dsNhanvien[index].TEN_DV,
                NHANVIEN_ID: this.dsNhanvien[index].NHANVIEN_ID
              })
              this.dsNhanvien.splice(index, 1)
            }
            index -= 1
          }
          if (this.dsNhanvienLoi.length === 0) {
          }
          for (let index = 0; index < this.dsNhanvienLoi.length; index++) {
            const e = this.dsNhanvienLoi[index]
            if (e.KHIEUNAI_ID === null && e.CHON === 1) {
              let data = {
                VKHIEUNAI_ID: this.khieunai_id,
                VNHANVIEN_ID: e.NHANVIEN_ID,
                VNOIDUNG: this.focusedRow.NOIDUNG,
                VKIEU: 1,
                VLOAI: 1
              }
              var rs = await this.$root.context.post(
                '/web-gqkn/tiep-nhan-khieu-nai/capnhat_loinhanvien', data
              )
            } else {
              let data = {
                VKHIEUNAI_ID: this.khieunai_id,
                VNHANVIEN_ID: e.NHANVIEN_ID,
                VNOIDUNG: this.focusedRow.NOIDUNG,
                VKIEU: 2,
                VLOAI: 1
              }
              var rs = await this.$root.context.post(
                '/web-gqkn/tiep-nhan-khieu-nai/capnhat_loinhanvien', data
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
        this.dsNhanvien.forEach(element => {
          let chk = false
          for (let index = 0; index < this.dsNhanvienChon.length; index++) {
            const e = this.dsNhanvienChon[index]
            if (e.NHANVIEN_ID === element.NHANVIEN_ID) {
              chk = true
              break
            }
          }
          if (chk) {
            element.CHON = 1
          } else {
            element.CHON = 0
          }
        })
      } catch (error) {

      }
    },
    griddsNhanvien_rowSelected (args) {
      try {
        this.dsNhanvienChon = this.$refs.griddsNhanvien.getSelectedRecords()
        this.dsNhanvien.forEach(element => {
          let chk = false
          for (let index = 0; index < this.dsNhanvienChon.length; index++) {
            const e = this.dsNhanvienChon[index]
            if (e.NHANVIEN_ID === element.NHANVIEN_ID) {
              chk = true
              break
            }
          }
          if (chk) {
            element.CHON = 1
          } else {
            element.CHON = 0
          }
        })
      } catch (error) {

      }
    },
    griddsNhanvienLoi_rowDeselected (args) {
      try {
        this.dsNhanvienLoiChon = this.$refs.griddsNhanvienLoi.getSelectedRecords()
        this.dsNhanvienLoi.forEach(element => {
          let chk = false
          for (let index = 0; index < this.dsNhanvienLoiChon.length; index++) {
            const e = this.dsNhanvienLoiChon[index]
            if (e.NHANVIEN_ID === element.NHANVIEN_ID) {
              chk = true
              break
            }
          }
          if (chk) {
            element.CHON = 1
          } else {
            element.CHON = 0
          }
        })
      } catch (error) {
      }
    },
    griddsNhanvienLoi_rowSelected (args) {
      try {
        this.dsNhanvienLoiChon = this.$refs.griddsNhanvienLoi.getSelectedRecords()
        this.dsNhanvienLoi.forEach(element => {
          let chk = false
          for (let index = 0; index < this.dsNhanvienLoiChon.length; index++) {
            const e = this.dsNhanvienLoiChon[index]
            if (e.NHANVIEN_ID === element.NHANVIEN_ID) {
              chk = true
              break
            }
          }
          if (chk) {
            element.CHON = 1
          } else {
            element.CHON = 0
          }
        })
      } catch (error) {
      }
    },
    griddsNhanvienLoi_SelectedRowChanged (data) {
      console.log('griddsNhanvienLoi_SelectedRowChanged', data)
      this.focusedRow = data
    },
    async load_dsnvloi () {
      try {
        var rs = await this.$root.context.post('/web-gqkn/tiep-nhan-khieu-nai/lay_ds_loi_khieunai', {VKHIEUNAI_ID: this.khieunai_id, VLOAI: 1})
        this.dsNhanvienLoi = rs.data
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
          var rs = await this.$root.context.post('/web-gqkn/tiep-nhan-khieu-nai/ds_nhanvien_phat', {VKHIEUNAI_ID: this.khieunai_id, VLOAI: 1})
          console.log('🚀 ~ file: index.vue:241 ~ shownModal:', rs.data)
          this.dsNhanvien = rs.data
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
