<template>
  <div>
    <div>
      <!-- <breadcrumb :header="header" /> -->
      <div class="list-actions-top">
        <ul class="list">
          <li @click="tsbtnCapNhat_Click()" :class="{'disabled':formInfo.disable}">
            <a> <span class="icon one-save"></span>Ghi lại</a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
<!--          <div class="legend-title">-->
<!--            Thông tin nhận cảnh báo hệ thống-->
<!--          </div>-->
          <div class="row">
            <!-- v-if="gridCanhBao.value.hasOwnProperty('tai_khoan')" -->

            <div class="col-12">
              <div class="info-row">
                <div class="key">
                  Tỉnh/thành phố
                </div>
                <div class="value">
                  <SelectExt :disabled="formInfo.disable" ref="cboVaiTro" v-model="cboTinh.selected" :dataSource="cboTinh.list" dataTextField="name" dataValueField="code" />
                  <!-- <input type="text" class="form-control" v-model="gridCanhBao.value.vai_tro" /> -->
                </div>
              </div>
            </div>
            <div class="col-12">
              <div class="info-row">
                <div class="key">
                  Quận/huyện
                </div>
                <div class="value">
                  <SelectExt :disabled="formInfo.disable" ref="cboVaiTro" v-model="cboQuan.selected" :dataSource="cboQuan.list" dataTextField="name" dataValueField="code" />
                  <!-- <input type="text" class="form-control" v-model="gridCanhBao.value.vai_tro" /> -->
                </div>
              </div>
            </div>
            <div class="col-12">
              <div class="info-row">
                <div class="key">
                  Phường/xã
                </div>
                <div class="value">
                  <SelectExt :disabled="formInfo.disable" ref="cboVaiTro" v-model="cboPhuong.selected" :dataSource="cboPhuong.list" dataTextField="name" dataValueField="code" />
                  <!-- <input type="text" class="form-control" v-model="gridCanhBao.value.vai_tro" /> -->
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import api from './API'
import Log from '../../../../../search/subscriber/SearchSubsInfo/Log.vue'


export default {
  emits: ['onBtnGhiLai'],
  props: {
    dulieu: {
      required: true
    },
    formInfo: {
      type: Object,
      default: {disable: false}
    }
  },
  data() {
    return {
      localLoading: 0,

      // 0 - idle || 1 - thêm mới || 2 - cập nhật
      formState: 0,
      fromData : {
        txtTinh: '',
        txtQuan: '',
        txtPhuong: '',
      },
      cboTinh: {
        list: [],
        selected: null,
      },
      cboQuan: {
        list: [],
        selected: null,
      },
      cboPhuong: {
        list: [],
        selected: null,
      }
    }
  },

  async mounted() {

    await this.load_DS_Tinh()

    // this.set_value()
  },

  computed: {
    id_tinh() {
      return this.$root.token.getPhanVungID()
    }
  },

  watch:{
    'cboQuan.selected':  async function (newVal, oldVal){
      await this.load_DS_Phuong()
    },
    'cboTinh.selected': async function(newVal, oldVal){
      //to work with changes in prop
      await this.load_DS_Quan()
    }
  },

  methods: {
    async load_DS_Tinh() {
      try {
        let rs = await api.laydstinh(this.axios)
        if (rs.data.error_code == 'BSS-00000000' && rs.data.data) {
          this.cboTinh.list = rs.data.data
          this.cboTinh.selected = this.id_tinh
        }
      }
      catch (e) {
        console.log(e)
      }
    },
    async load_DS_Quan() {
      try {
        let rs = await api.laydsquan(this.axios, {
          Code: this.cboTinh.selected
        })
        if (rs.data.error_code == 'BSS-00000000' && rs.data.data) {

          this.cboQuan.list = rs.data.data
          if(this.dulieu.data && this.dulieu.data[0].SO_GT && this.dulieu.data[0].CHUCDANH
            && this.dulieu.data[0].LOAIGT_ID) {
            // this.cboPhuong.selected = this.dulieu.data.SO_GT
            return this.cboQuan.selected = this.dulieu.data[0].CHUCDANH
            // this.cboTinh.selected = this.dulieu.data.LOAIGT_ID
          }
          this.cboQuan.selected = rs.data.data[0].code
        }
      }
      catch (e) {
        console.log(e)
      }
    },
    async load_DS_Phuong() {
      try {
        let rs = await api.laydsphuong(this.axios, {
          //Id tỉnh/thánh phố *
          "ProvinceCode": this.cboTinh.selected,
          //Id quận huyện *
          "districtCode": this.cboQuan.selected
        })
        if (rs.data.error_code == 'BSS-00000000' && rs.data.data) {
          this.cboPhuong.list = rs.data.data

          if(this.dulieu.data && this.dulieu.data[0].SO_GT && this.dulieu.data[0].CHUCDANH
            && this.dulieu.data[0].LOAIGT_ID) {
            return this.cboPhuong.selected = this.dulieu.data[0].SO_GT
            // return this.cboQuan.selected = this.dulieu.data.CHUCDANH
            // this.cboTinh.selected = this.dulieu.data.LOAIGT_ID
          }

          this.cboPhuong.selected = rs.data.data[0].code
        }
      }
      catch (e) {
        console.log(e)
      }
    },
    async tsbtnCapNhat_Click() {
      // console.log(this.dulieu.data.data[0])
      try {
        let row = {}

        row.SO_GT = this.cboPhuong.selected
        row.CHUCDANH = this.cboQuan.selected
        row.LOAIGT_ID = this.cboTinh.selected
        // row.HDTB_ID = this.hdtb_id

        if(!row.SO_GT || !row.CHUCDANH || !row.LOAIGT_ID){
          this.$toast.error('Bạn chưa nhập đủ thông tin')
          return
        }
        else {
          this.$toast.success('Cập nhật thông tin thành công!')
        }
        console.log('tsbtnCapNhat_Click')
        this.$emit('emit_func', row)
      }
      catch (e) {
        console.log(e)
      }
    },
    set_value() {
      console.log('this.formInfo')
      console.log(this.formInfo)
      if(this.dulieu.data && this.dulieu.data[0].SO_GT && this.dulieu.data[0].CHUCDANH
        && this.dulieu.data[0].LOAIGT_ID) {
        this.cboPhuong.selected = this.dulieu.data[0].SO_GT
        this.cboQuan.selected = this.dulieu.data[0].CHUCDANH
        this.cboTinh.selected = this.dulieu.data[0].LOAIGT_ID
      }
    }
  }
}
</script>
<style scoped>
.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: #9999 !important;
}

.disabledInput > a {
  color: #666 !important;
}
</style>
