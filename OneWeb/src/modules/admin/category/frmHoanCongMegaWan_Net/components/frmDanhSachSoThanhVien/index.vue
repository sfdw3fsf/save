<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span> Danh sách số thành viên</div>
      <div @click="closeModal" class="close -ap icon-close" data-dismiss="modal"></div>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">Danh sách số thành viên</div>
        <div class="table-content">
          <DataGrid ref="danhSachThueBaoDangHoatDong" v-bind:columns="grcDanhBaTBI.header" v-bind:dataSource="grcDanhBaTBI.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true"> </DataGrid>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import DatePicker from 'vue2-datepicker'
import VueFlatPickr from 'vue-flatpickr-component'
import api from '../api'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'

import moment from 'moment'
import { async, Promise, reject } from 'q'
//
export default {
  components: {
    breadcrumb,
    VueFlatPickr,
    DatePicker
  },
  name: 'DanhSachThanhVien',
  mounted() {
    this.frmDanhSachSoThanhVien_Load()
    this.showModal()
  },
  watch: {},
  props: ['hdtb_id', 'thuebao_id', 'tinh_id'],
  data() {
    return {
      // hdtb_id : 0,
      // thuebao_id : 0,
      // lst_hdtbct : [],
      // lst_dbtbct : [],
      tdan: {},
      // kieuld_id : 0,
      settingSelect2: {
        language: 'vi'
      },
      ds_thietbi: [],

      grcDanhBaTBI: {
        list: [],
        header: [
          { fieldName: 'HDTB_CT_ID', headerText: 'ID', allowFiltering: true, width: 'auto' },
          { fieldName: 'SO_DT_CT', headerText: 'Số thuê bao', allowFiltering: true, width: 'auto' },
          { fieldName: 'TRANGTHAI_CT', headerText: 'Trạng thái thuê bao', allowFiltering: true, width: 'auto' },
          { fieldName: 'NGAY_NGHIEMTHU_CT', headerText: 'Ngày cập nhật', allowFiltering: true, width: 'auto' }
        ],
        value: {},
        isEnabled: true
      }
    }
  },
  computed: {},
  methods: {
    showAlert(mesage) {
      this.$toast.error(mesage)
    },
    showSuccess(mesage) {
      this.$toast.success(mesage)
    },

    getData: function(response) {
      if (response.data.error === 200 || response.data.error === '200' || response.data.error === '0') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },

    closeModal() {
      this.$bvModal.hide('danhSachSoThanhVienModal')
    },
    showModal() {
      this.$bvModal.show('danhSachSoThanhVienModal')
    },
    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    async frmDanhSachSoThanhVien_Load() {
      if (this.$root.token.getPhanVungID() != 100) {
        if (this.hdtb_id != 0) {
          this.ds_thietbi = this.getData(
            await api.ds_thietbi_idc_dangky(this.axios, {
              vhdtb_id: this.hdtb_id
            })
          )
          if (this.ds_thietbi) {
            this.ds_thietbi = this.UpperCasePropertyList(this.ds_thietbi)
          }
        } else {
          this.ds_thietbi = this.getData(
            await api.ds_thietbi_idc_sudung(this.axios, {
              vthuebao_id: this.thuebao_id
            })
          )

          if (this.ds_thietbi) {
            this.ds_thietbi = this.UpperCasePropertyList(this.ds_thietbi)
          }
        }
      } else {
        if (this.hdtb_id != 0) {
          this.ds_thietbi = this.getData(
            await api.ds_thietbi_idc_dangky(this.axios, {
              vhdtb_id: this.hdtb_id
            })
          )
        } else {
          this.ds_thietbi = this.getData(
            await api.ds_thietbi_idc_sudung(this.axios, {
              vthuebao_id: this.thuebao_id
            })
          )
        }
      }
      this.grcDanhBaTBI.list = this.ds_thietbi
    }
  }
}
</script>

<style>
.searchAccountDialogClass .e-dlg-header-content {
  display: none;
}
</style>
