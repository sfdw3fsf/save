<template src="./index.html"></template>
<script>
import {actionName, statePropertyName, getterName} from './Rack'
import {mapActions, mapState, mapGetters} from 'vuex'
import {maxLength, required} from 'vuelidate/lib/validators'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import DataGridCustom from './DataGrid/index.vue'
export default {
  components: {BssRequiredMarker, DataGridCustom},
  name: 'Rack',
  computed: {
    ...mapState('ecms/rack', statePropertyName),
    ...mapGetters('ecms/rack', getterName)
  },
  methods: {
    ...mapActions('ecms/rack', actionName),
    onClickButtonNhapMoi () {
      this.oldValue.rack = this.rack
      this.oldValue.shelf = this.shelf
      this.rack = null
      this.shelf = null
      this.$refs.rack.focus()
      this.setDisableBtn(false, true, true, false)
    },
    async onClickButtonHuyBo () {
      await this.layDsRackAction()
      this.rack = this.oldValue.rack
      this.shelf = this.oldValue.shelf
      this.updatePaging(this.dsRack.length)
      this.tableThongTinRack.data = this.dsRack
    },
    grid_selectedRowChanged (dataItem) {
      if (!dataItem) {
        this.rack = null
        this.shelf = null
        this.rackId = null
        return
      }
      this.rack = dataItem.RACK
      this.shelf = dataItem.SHELF
      this.rackId = dataItem.RACK_ID
    },
    validateData (data) {
      this.$v.$touch()
      let errorMessage = []
      console.log(this.$refs)
      console.log(this.$v)
      console.log(data)
      if (this.$v.rack.$error) {
        this.$refs.rack.focus()

        if (!data.rack.toString().trim()) {
          errorMessage.unshift('Rack không được để trống!')
          this.$refs.rack.focus()
        } else if (data.rack.toString().length > 18) {
          this.$refs.rack.focus()
          errorMessage.unshift('Rack: Dữ liệu không hợp lệ')
        }
      }

      if (this.$v.shelf.$error) {
        this.$refs.shelf.focus()

        if (!data.shelf.toString().trim()) {
          errorMessage.unshift('Shelf không được để trống!')
          this.$refs.shelf.focus()
        } else if (data.shelf.toString().length > 10) {
          this.$refs.shelf.focus()
          errorMessage.unshift('Shelf: Dữ liệu không hợp lệ')
        }
      }
      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return true
      } else {
        return false
      }
    },
    onClickButtonGhiLai: async function () {
      let data = {
        rack: this.rack,
        shelf: this.shelf,
        rack_ID: this.rackId
      }
      if (this.validateData(data)) {
        return
      }
      try {
        this.loading(true)
        let result = null
        if (!this.buttonState.btnNhapMoiEnabled) {
          result = await this.themMoiAction(data)
          this.$toast.success('Nhập mới thành công')
          await this.layDsRackAction()
          this.tableThongTinRack.data = this.dsRack
          this.rack = result.RACK
          this.shelf = result.SHELF
          this.rackId = result.RACK_ID
          this.buttonState.btnNhapMoiEnabled = !this.buttonState.btnNhapMoiEnabled
          this.buttonState.btnXoaEnabled = true
        } else {
          if (data.rack_ID === null || data.rack_ID === '' || data.rack_ID === undefined) {
            this.$toast.error('Chưa chọn rack')
            return
          }
          result = await this.capNhatAction(data)
          await this.layDsRackAction()
          let soThuTu = 1
          let dsRackTmp = []
          for (var i = 0; i < this.dsRack.length; i++) {
            if (this.dsRack[i].RACK_ID !== data.rack_ID) {
              dsRackTmp.push(JSON.parse(JSON.stringify(this.dsRack[i])))
            }
          }
          dsRackTmp.forEach(element => {
            soThuTu += 1
            element.STT = soThuTu
          })
          result.data.data.STT = 1
          dsRackTmp.unshift(result.data.data)
          this.tableThongTinRack.data = dsRackTmp
          this.rack = result.data.data.RACK
          this.shelf = result.data.data.SHELF
          this.$toast.success('Cập nhật thành công')
        }
        this.loading(true)
      } catch (err) {
        this.$toast.error(err)
        return
      } finally {
        this.loading(false)
      }
    },
    xoa: function () {
      console.log('xoa')
      if (!this.btnXoaEnabled) {
        this.modalXoa = !this.modalXoa
      }
    },
    xacNhanXoa: async function () {
      try {
        this.loading(true)
        await this.xoaAction(this.rackId)
        this.$toast.success('Xóa thành công')
        this.rack = null
        this.shelf = null
      } catch (err) {
        this.$toast.error(err)
        return
      } finally {
        this.modalXoa = false
        this.loading(false)
      }
      await this.layDsRackAction()
      this.tableThongTinRack.data = this.dsRack
    },
    async onClickButtonXoa () {
      if (!this.buttonState.btnXoaEnabled) {
        return
      }
      try {
        this.loading(true)
        await this.xoaAction(this.rackId)
        this.$toast.success('Xóa thành công')
        this.rack = null
        this.shelf = null
      } catch (err) {
        this.$toast.error(err)
        return
      } finally {
        this.loading(false)
      }
      await this.layDsRackAction()
      this.tableThongTinRack.data = this.dsRack
    },
    setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
      this.buttonState.btnNhapMoiEnabled = isNhapmoi
      this.buttonState.btnGhiLaiEnabled = isGhilai
      this.buttonState.btnHuyBoEnabled = isHuy
      this.buttonState.btnXoaEnabled = isXoa
    },
    onClickButtonHuy: async function () {
      this.buttonState.btnNhapMoiEnabled = true
      this.buttonState.btnGhiLaiEnabled = true
      this.buttonState.btnHuyBoEnabled = true
      this.buttonState.btnXoaEnabled = true
      this.loading(true)
      await this.layDsRackAction()
      if (this.dsRack && this.dsRack.length > 0) {
        this.rack = this.dsRack[0].RACK
        this.shelf = this.dsRack[0].SHELF
      }
      // this.updatePaging(this.dsRack.length)
      this.tableThongTinRack.data = this.dsRack
      this.loading(false)
    },
    clear () {
      this.rack = null
      this.shelf = null
      this.tableThongTinRack = null
      this.dsRack = []
    }
  },
  async mounted () {
    this.$store.dispatch('setHeader', {
      title: 'Rack',
      list: [
        {
          name: 'Lập hợp đồng',
          link: {name: 'Ui.cards'}
        },
        {
          name: 'Lắp đặt mới',
          link: {name: 'Ui.buttons'}
        }
      ]
    })
    try {
      this.loading(true)
      await this.layDsRackAction()
      if (this.dsRack && this.dsRack.length > 0) {
        this.rack = this.dsRack[0].RACK
        this.shelf = this.dsRack[0].SHELF
      }
      // this.updatePaging(this.dsRack.length)
      this.tableThongTinRack.data = this.dsRack
    } catch (err) {
      console.log(err)
    } finally {
      this.loading(false)
    }
  },
  data () {
    return {
      oldValue: {
        rack: null,
        shelf: null
      },
      tableKey: 1,
      rack: null,
      shelf: null,
      tableThongTinRack: {data: [], selected: 0},
      dsRackVue: [],
      buttonState: {
        btnNhapMoiEnabled: true,
        btnGhiLaiEnabled: true,
        btnXoaEnabled: true,
        btnHuyBoEnabled: true,
        btnSinhIPEnabled: true
      },
      modalXoa: false,
      isFirstRow: true
    }
  },
  validations: {
    rack: {
      required,
      maxlength: maxLength(18)
    },
    shelf: {
      required,
      maxlength: maxLength(10)
    },
    loaiModule: {
      required,
      maxlength: maxLength(30)
    }
  },
  created () {
    this.clearState()
    this.createApiManager()
  },
  destroyed () {
    this.clearState()
  }
}
</script>
<style scoped src="./index.scss">
</style>
