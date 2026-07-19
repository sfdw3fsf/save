<template>
  <div class="box-form">
    <div class="legend-title">Thông tin vai trò</div>
    <!-- <div class="info-row marb0">
      <div class="value">
        <vue-input
          ref="refVaiTro"
          v-model="vaiTro"
          :required="true"
          label="Vai trò"
          :labelWidth="'80'"
          :autofocus="true"
        ></vue-input>

      </div>
    </div> -->
    <div class="info-row marb0">
      <div class="key w80">Vai trò <span style="color: red">*</span></div>
      <div class="value">
        <input
          ref="refVaiTro"
          v-model="vaiTro"
          type="text"
          class="form-control"
        />
      </div>
    </div>
  </div>
</template>
<script>
import api from './../api'
import { mapActions, mapState, mapGetters } from 'vuex'
export default {
  props: {
    checkRecord: {
      type: Boolean
    },
    checkDelete: {
      type: Boolean
    },
    checkCancel: {
      type: Boolean
    }
  },
  data () {
    return {
      vaiTro: null,
      vaiTroID: null,
      kieu: 0
    }
  },
  computed: {
    ...mapState('contractorRole', ['danhSachVaiTro', 'isLoading', 'dataItem', 'isShowModal'])
  },
  watch: {
    checkRecord: 'handleSubmitForm',
    dataItem: 'handleDataItem',
    checkDelete: 'handleDelete',
    checkCancel: 'handleCheckCancel'

  },
  methods: {
    ...mapActions('contractorRole', ['layDanhSachVaiTro']),
    handleDelete () {
      
      this.$bvModal
        .msgBoxConfirm(
          `Bạn thật sự muốn thay đổi vai trò ${this.vaiTro} ?`,
          {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          }
        )
        .then(async (v) => {
          if (v) {
            api
              .xoaVaiTro(this.axios, { vaitro_id: this.vaiTroID })
              .then((res) => {
                this.$root.showLoading(true)
                if (res && res.data && res.data.message == 'Success') {
                  this.vaiTro = ''
                  this.layDanhSachVaiTro()
                  this.$store.commit('contractorRole/SET_DATA_ITEM', {})
                  this.$toast.success(`Xóa dữ liệu thành công`)
                  this.$root.showLoading(false)
                } else {
                  this.$root.showLoading(true)
                  this.$toast.error(`Xóa dữ liệu lỗi`)
                }
              })
              .catch((e) => {
                this.$toast.error(`Xóa dữ liệu lỗi`)
                this.$root.showLoading(false)
              })
          }
        })
    },
    handleDataItem () {
      this.vaiTro =
        this.dataItem && this.dataItem.vaitro ? this.dataItem.vaitro : null
      this.vaiTroID =
        this.dataItem && this.dataItem.vaitro_id
          ? this.dataItem.vaitro_id
          : null
      console.log('this.vaiTroID', this.dataItem)
    },
    handleCheckCancel () {
      // this.$store.commit('contractorRole/SET_DATA_ITEM', {})
      this.$store.commit('contractorRole/SET_SHOW_MODAL', !this.isShowModal)
    },
    handleSubmitForm () {
      if (!this.isValidForm()) {
        this.$refs.refVaiTro.focus()
        this.$toast.error(`Vai trò không thể bỏ trống`)
        return
      }

      if (this.vaiTro.length > 200) {
        this.$refs.refVaiTro.focus()
        this.$toast.error(`Vai trò không thể vượt quá 200 ký tự`)
        return
      }
      this.$root.showLoading(true)
      if (this.dataItem.vaitro_id && this.dataItem.vaitro_id > 0) {
        this.kieu = 1
        this.saveRole()
        this.updateCurrentRow()
      } else {
        api
          .getKeys(this.axios, {
            keyname: 'VAITRO_ID',
            numblocksize: 1,
            numretry: 10
          })
          .then((res) => {
            if (res && res.data && res.data.message == 'Success') {
              this.$root.showLoading(false)
              this.vaiTroID = res.data.data
              this.kieu = 0
              this.saveRole()
              this.goToZezoPage()
            } else {
              this.$root.showLoading(false)
            }
          })
          .catch((e) => {
            this.$toast.error(`Tạo mới dữ liệu lỗi`)
            this.$root.showLoading(false)
          })
      }
    },
    goToZezoPage() {
      console.log('run')
      this.$emit('go-to')
    },
    updateCurrentRow() {
      this.$emit('update-row')
    },
    saveRole () {
      const params = {
        json_vaitro: JSON.stringify({
          VAITRO_ID: this.vaiTroID,
          VAITRO: this.vaiTro
        }),
        kieu: this.kieu
      }
      api
        .luuVaiTro(this.axios, params)
        .then((res) => {
          if (res && res.data && res.data.message == 'Success') {
            this.vaiTro = ''
            // this.$store.commit('contractorRole/SET_DATA_ITEM', {})
            this.layDanhSachVaiTro()
            this.$toast.success(
              `${this.kieu == 1 ? 'Cập nhật' : 'Tạo mới'} dữ liệu thành công!`
            )
            this.$root.showLoading(false)
          } else {
            this.$root.showLoading(false)
          }
        })
        .catch((e) => {
          this.$toast.error(
            `${this.kieu == 1 ? 'Cập nhật' : 'Tạo mới'} dữ liệu lỗi`
          )
          this.$root.showLoading(false)
        })
    },
    isValidForm () {
      if (!this.vaiTro) {
        return false
      }
      return true
    }
  }
}
</script>
