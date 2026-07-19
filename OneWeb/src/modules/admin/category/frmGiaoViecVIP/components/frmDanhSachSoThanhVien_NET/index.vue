<template>
  <div>
    <div class="list-actions-top">
      <ul class="list"></ul>
    </div>
    <div class="popup-body">
      <DataGrid ref="grcDanhBaTBI" v-bind:columns="grcDanhBaTBI.cols" v-bind:dataSource="grcDanhBaTBI.list" :showFilter="true" :showColumnCheckbox="true" :enabledSelectFirstRow="false" :allowPaging="true" :enablePagingServer="false" />
    </div>
  </div>
</template>
<script>
import api from './api'
export default {
  props: {
    hdtb_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    thuebao_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    tinh_id: {
      type: [String, Number],
      defaul: null,
      require: false
    }
  },
  data() {
    return {
      localLoading: 0,
      tinh_khdn: [97, 100],
      grcDanhBaTBI: {
        cols: [
          { fieldName: 'hdtb_ct_id', headerText: 'ID', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'so_dt_ct', headerText: 'Số thuê bao', width: 150, allowFiltering: true, allowSorting: true },
          { fieldName: 'email_ct', headerText: 'IP', width: 150, allowFiltering: true, allowSorting: true },
          { fieldName: 'trangthai_ct', headerText: 'Trạng thái thuê bao', width: 250, allowFiltering: true, allowSorting: true },
          // { fieldName: 'ngay_nghiemthu_ct', headerText: 'Ngày cập nhật', width: 200, allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: []
      }
    }
  },
  computed: {},
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    }
  },
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data ? result.data.data : null
      } catch (e) {
        console.log('LOI ROI')
        console.log(e)
        if (e.data != null) {
          this.$toast.error(`${e.data.message}`)
        } else {
          this.$toast.error(`Có lỗi gọi API`)
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    async frmDanhSachSoThanhVien_Load() {
      console.log('frmDanhSachSoThanhVien_Load')
      console.log(this.$root.token.getPhanVungID())
      if (!this.tinh_khdn.includes(this.$root.token.getPhanVungID())) {
        if (this.hdtb_id != 0) {
          let kq = await this.callApiWrapper(api.lay_ds_thietbi_idc_dangky_v2, {
            vphanvung_id: this.tinh_id,
            vhdtb_id: this.hdtb_id
          })
          
        
          this.ds_thietbi = kq
          console.log(this.ds_thietbi)
          console.log('frmDanhSachSoThanhVien_Load')
          console.log(this.ds_thietbi)
        } else {
          this.ds_thietbi = await this.callApiWrapper(api.ds_thietbi_idc_sudung, {
            vthuebao_id: this.thuebao_id
          })
        }
      } else {
        if (this.hdtb_id != 0) {
          let kq = await this.callApiWrapper(api.lay_ds_thietbi_idc_dangky_v2, {
            vphanvung_id: this.tinh_id,
            vhdtb_id: this.hdtb_id
          })
          this.ds_thietbi = kq
        } else {
          this.ds_thietbi = await this.callApiWrapper(api.ds_thietbi_idc_sudung, {
            vthuebao_id: this.thuebao_id
          })
        }
      }
      this.grcDanhBaTBI.list = this.ds_thietbi
    }
  },
  async mounted() {
    await this.frmDanhSachSoThanhVien_Load()
  }
}
</script>
<style scoped></style>
