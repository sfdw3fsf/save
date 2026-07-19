<template>
  <div class="box-form">
    <div class="legend-title">Thông tin chủ đề khiếu nại</div>
    <div class="info-row">
      <vue-select
        ref="refloaiID"
        :labelWidth="'100'"
        v-model="loaiID"
        :options="danhSachKhuyenMai.loai ? danhSachKhuyenMai.loai : []"
        labelField="ten_loai"
        valueField="loai_id"
        label="Loại chủ đề"
        :class="!loaiID && checkSubmit?'validationloaiID' :''"
        :required="true"
      ></vue-select>
    </div>
    <div class="info-row">
      <vue-select
        :labelWidth="'100'"
        v-model="nhomID"
        :options="danhSachKhuyenMai.nhom ? danhSachKhuyenMai.nhom : []"
        labelField="ten_nhom"
        valueField="nhom_id"
        label="Nhóm chủ đề"
      ></vue-select>
    </div>
        <div class="info-row marb0" style="magi">
      <div class="key w100">Tên chủ đề <span style="color: red">*</span></div>
      <div class="value">
        <input
          ref="symbol"
          v-model="tenChuDe"
          type="text"
          class="form-control"
        />
      </div>
    </div>
    <!-- <div class="info-row">
      <vue-input
        ref="symbol"
        :required="true"
        label="Tên chủ đề"
        v-model="tenChuDe"
        :class="!tenChuDe && checkSubmit || tenChuDe && tenChuDe.trim().length ==0 && checkSubmit ||tenChuDe && tenChuDe.length >200 && checkSubmit?'validationloaiID' :''"
        :labelWidth="'100'"
      ></vue-input>
    </div> -->
    <div class="info-row">
      <div class="key w100">Giải pháp</div>
      <div class="value">
          <textarea
            rows="3"
            v-model="giaiPhap"
            class="form-control" style="resize: none;"
          ></textarea>
      </div>
    </div>
    <div class="info-row">
      <div class="key w100">Ghi chú</div>
      <div class="value">
        <textarea
          rows="3"
          v-model="ghiChu"
          :class="ghiChu && ghiChu.length >200 && checkSubmit?'validationGhiChu form-control' :'form-control'"
          style="resize: none;"
        ></textarea>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import api from './../api'
export default {
  props: {
    checkRecord: {
      type: Boolean
    },
    checkCancel: {
      type: Boolean
    },
    checkDelete: {
      type: Boolean
    },
    checkNewEntry: {
      type: Boolean
    }
  },
  data () {
    return {
      nhomID: null,
      loaiID: null,
      tenChuDe: null,
      giaiPhap: null,
      ghiChu: null,
      checkSubmit: false
    }
  },
  computed: {
    ...mapState('complaintTopic', [
      'danhSachKhuyenMai',
      'isLoading',
      'dataItem'
    ])
  },
  mounted() {
    this.layDanhSachKhuyenMai()
  },
  watch: {
    dataItem: 'handleDataItem',
    checkRecord: 'handleSubmitForm',
    checkCancel: 'handleCheckCancel',
    checkDelete: 'handleCheckDelete',
    checkNewEntry: 'handleCheckNewEntry'
  },
  methods: {
    ...mapActions('complaintTopic', ['layDanhSachKhuyenMai']),
    handleDataItem () {
      console.log('this.dataItem', this.dataItem)
      this.loaiID = this.dataItem.loai_id
      this.nhomID = this.dataItem.nhom_id
      this.tenChuDe = this.dataItem.tenchude
      this.giaiPhap = this.dataItem.giaiphap_kp
      this.ghiChu = this.dataItem.ghichu
    },
    handleCheckNewEntry () {
      this.$refs.symbol.focus()
    },
    //     warningInput () {
    //   this.$refs.refCacLoaiSoID.checkValidate()
    //   this.$refs.refDichVuID.checkValidate()
    //   this.$refs.refCacDonViID.checkValidate()
    //   this.$refs.refTongDaiID.checkValidate()
    //   this.$refs.refQuanHuyenId.checkValidate()
    //   this.$refs.refPhuongID.checkValidate()
    //   this.$refs.refSoKetThuc.checkValidate()
    //   this.$refs.refSoBatDau.checkValidate()
    // },
    isValidForm () {
      if (!this.loaiID && !this.tenChuDe) {
        this.$toast.error(`Loại chủ đề,tên chú đề không thể bỏ trống`)
        return false
      }
      if (!this.loaiID) {
        this.$toast.error(` Loại chủ đề không thể bỏ trống`)
        return false
      }
      if (!this.tenChuDe || this.tenChuDe.trim().length == 0) {
        this.$toast.error(`Tên chủ đề khiếu nại không được để trống!`)
        return false
      }
      if (this.ghiChu && this.ghiChu.length > 200 && this.tenChuDe && this.tenChuDe.length > 200) {
        this.$toast.error(` Trường tên chủ đề, ghi chú dữ liệu vượt quá độ dài cho phép`)
        return false
      }
      if (this.tenChuDe && this.tenChuDe.length > 100) {
        this.$toast.error(` Trường tên chủ đề dữ liệu vượt quá độ dài cho phép`)
        return false
      }
      if (this.ghiChu && this.ghiChu.length > 100) {
        this.$toast.error(` Trường ghi chú dữ liệu vượt quá độ dài cho phép`)
        return false
      }
      if (this.giaiPhap && this.giaiPhap.length > 1000) {
        this.$toast.error(` Trường giải pháp dữ liệu vượt quá độ dài cho phép`)
        return false
      }
      return true
    },
    handleCheckCancel () {
      // this.nhomID = null
      // this.loaiID = null
      // this.tenChuDe = null
      // this.giaiPhap = null
      // this.ghiChu = null
      this.$emit('getDanhSachKhuyenMai', 1)
      this.$store.commit('complaintTopic/SET_DATA_ITEM', this.danhSachKhuyenMai.ds[0])
    },
    handleCheckDelete () {
      this.$confirm('Bạn thực sự muốn xóa dữ liệu không?', 'Thông báo', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy',
        type: 'warning'
      }).then(() => {
        this.$root.showLoading(true)
        api.apiXoa(this.axios, {
          chude_id: this.dataItem.chudekn_id
        }).then(res => {
          if (res && res.data && res.data.message == 'Success') {
            this.$root.showLoading(false)
            this.$emit('getDanhSachKhuyenMai', 0)
            this.$toast.success(`Xóa dữ liệu  thành công!`)
          } else {
            this.$toast.error(`Xóa dữ liệu  lỗi`)
            this.$root.showLoading(false)
          }
        }).catch((e) => {
          this.$toast.error(`Xóa dữ liệu lỗi`)
          this.$root.showLoading(false)
        })
      }).catch(() => {
      })
    },
    handleSubmitForm () {
      this.checkSubmit = true
      if (!this.isValidForm()) {
        this.handleCheckNewEntry()
        return
      }
      this.$root.showLoading(true)
      api.apiRecord(this.axios, {
        chk_new: this.dataItem.chudekn_id && this.dataItem.chudekn_id > 0 ? 0 : 1,
        chude_id: this.dataItem.chudekn_id && this.dataItem.chudekn_id > 0 ? this.dataItem.chudekn_id : 0,
        ghichu: this.ghiChu ? this.ghiChu.trim() : null,
        ten_chude: this.tenChuDe ? this.tenChuDe.trim() : null,
        giaiphap: this.giaiPhap ? this.giaiPhap.trim() : null,
        loai: this.loaiID,
        nhom: this.nhomID
      }).then(res => {
        if (this.dataItem.chudekn_id && this.dataItem.chudekn_id > 0) {
          this.$emit('getDanhSachKhuyenMai', 1)
        } else {
          this.$emit('getDanhSachKhuyenMai', 0)
        }

        this.$root.showLoading(false)
        this.$toast.success(`${this.dataItem.chudekn_id && this.dataItem.chudekn_id > 0 ? 'Cập nhật' : 'Thêm mới'} dữ liệu thành công!`)
        if (res && res.data && res.data.message == 'Success') {
        } else {
          this.$toast.error(`${this.dataItem.chudekn_id && this.dataItem.chudekn_id > 0 ? 'Cập nhật' : 'Thêm mới'} dữ liệu lỗi`)
          this.$root.showLoading(false)
        }
      }).catch((e) => {
        this.$toast.error(`Cập nhật dữ liệu lỗi`)
        this.$root.showLoading(false)
      })
    }
  }
}
</script>
<style scoped>
>>> .validationloaiID input {
  border: 1px solid red;
}
>>> .validationGhiChu textarea {
   border: 1px solid red;
}
>>> .outside {
  opacity: 0 !important;
}
>>> .marb0 {
  margin-bottom: 5px !important;
}
</style>
