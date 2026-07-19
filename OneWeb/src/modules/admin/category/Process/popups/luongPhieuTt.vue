<template>
<!--  <b-modal-->
<!--    id="luongPhieuTt"-->
<!--    title="Luồng phiếu - thao tác"-->
<!--    modal-class="luong-phieu-thao-tac"-->
<!--    header-class="popup-header"-->
<!--    content-class="luong-phieu-thao-tac-content"-->
<!--    body-class="luong-phieu-thao-tac-body"-->
<!--    size="xl"-->
<!--    hide-footer-->
<!--  >-->
<!--   -->
<!--  </b-modal>-->

  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li @click="tsbtnGhiLai_Click">
          <a href="javascript:void(0)"> <span class="icon one-save"></span> Ghi lại </a>
        </li>
        <li @click="tsbtnThaoTac_Click">
          <a href="javascript:void(0)"> <span class="icon one-shape"></span> Thao tác </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="info-row">
          <div class="key w90">Tỉnh</div>
          <div class="value">
            <SelectExt ref="vatDropDown" v-model="p_tinh" :allowFiltering="true" dataTextField="TENTINH" dataValueField="TINH_ID" :dataSource="dsTinh" placeholder="Chọn tỉnh cần tìm kiếm"></SelectExt>
          </div>
          <div v-if="p_tinh" class="">
            <button @click="() => {p_tinh=null}" class="btn"><span class="icon nc-icon-glyph ui-1_circle-remove"></span></button>
          </div>
        </div>
        <div class="box-move-select-table">
          <div class="box-col">
            <div class="table-content">
              <DataGrid v-bind:columns="thaoTacDaGan.header" v-bind:dataSource="thaoTacDaGan.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" :enabledSelectFirstRow="false" ref="thao_tac_da_gan"> </DataGrid>
            </div>
          </div>
          <div class="actions">
            <button class="btn sm" @click="btnHDRight">
              <span class="fa fa-angle-right"></span>
            </button>
            <button class="btn sm" @click="btnHDRightAll">
              <span class="fa fa-angle-double-right"></span>
            </button>
            <button class="btn sm" @click="btnHDLeft">
              <span class="fa fa-angle-left"></span>
            </button>
            <button class="btn sm" @click="btnHDLeftAll">
              <span class="fa fa-angle-double-left"></span>
            </button>
          </div>
          <div class="box-col">
            <div class="table-content">
              <DataGrid v-bind:columns="thaoTacChuaGan.header" v-bind:dataSource="thaoTacChuaGan.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" :enabledSelectFirstRow="false" ref="thao_tac_chua_gan"> </DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div>
    <capNhatThaoTac ref="capNhatThaoTac" />
  </div>
</template>
<script>
import moment from 'moment'
import Vue from 'vue'
import API from '../API'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import gridView from '@/components/Shared/gridview.vue'
import capNhatThaoTac from './capNhatThaoTac.vue'
export default {
  props: ['luongId'],
  components: {
    API,
    gridView,
    capNhatThaoTac
  },
  name: 'luongPhieuTt',
  async mounted() {
    this.luong_id_data = this.luongId
    // this.frmLuongPhieuTT_Load()
    this.frmLuongPhieuTT_Load()
  },
  data() {
    return {
      dsTinh: [],
      p_tinh: null,
      luong_dv: [],
      dsLUONGDV: [],
      loaidv_id: 0,
      idx_data: 0,
      luong_id_data: 0,
      load: true,
      settingSelect2: {
        language: 'vi'
      },
      cboLoaiDv: {
        list: [],
        value: 0
      },
      thaoTacDaGan: {
        list: [],
        header: [
          { fieldName: 'THAOTAC', headerText: 'Thao tác đã gán', allowFiltering: true, width: 'auto' },
          { fieldName: 'CODE', headerText: 'Code', allowFiltering: true, width: 'auto' },
          { headerText: 'ENABLE', textAlign: 'Center', template: this.enableTemplateDaGan(this), allowFiltering: true, width: 'auto' }
        ],
        value: {},
        isEnabled: true
      },
      thaoTacChuaGan: {
        list: [],
        header: [
          { fieldName: 'THAOTAC', headerText: 'Thao tác chưa gán', allowFiltering: true, width: 'auto' },
          { fieldName: 'CODE', headerText: 'Code', allowFiltering: true, width: 'auto' },
          { headerText: 'ENABLE', textAlign: 'Center', template: this.enableTemplateChuaGan(this), allowFiltering: true, width: 'auto' }
        ],
        value: {},
        isEnabled: true
      }
    }
  },
  watch: {
    luongId(val) {
      this.luong_id_data = val
      this.frmLuongPhieuTT_Load()
    }
  },
  methods: {
    enableTemplateDaGan(parent) {
      return function() {
        return {
          template: {
            template: `<div class="template_checkbox"><input type="checkbox" class="check" v-model="data.ENABLE" @change="onValueChanged"/></div>`,

            data() {
              return {
                data: {}
              }
            },
            methods: {
              onValueChanged(event) {
                parent.thaoTacDaGan.list[this.data.index].ENABLE = event.target.checked ? 1 : 0
              }
            }
          }
        }
      }
    },
    enableTemplateChuaGan(parent) {
      return function() {
        return {
          template: {
            template: `<div class="template_checkbox"><input disabled="true" type="checkbox" class="check" v-model="data.ENABLE" @change="onValueChanged"/></div>`,

            data() {
              return {
                data: {}
              }
            },
            methods: {
              onValueChanged(event) {
                // parent.thaoTacChuaGan.list[this.data.index].ENABLE = event.target.checked ? 1 : 0
              }
            }
          }
        }
      }
    },
    show() {
      this.$bvModal.show('luongPhieuTt')
      this.frmLuongPhieuTT_Load()
    },
    ShowAlert(mesage) {
      this.$toast.error(mesage)
    },
    showSuccess(mesage) {
      this.$toast.success(mesage)
    },
    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async frmLuongPhieuTT_Load() {
      console.log('this.luongId is ', this.luongId)
      let obj = {
        LUONG_ID: this.luongId
      }

      let input = {
        p_ds_para: JSON.stringify(obj)
      }

      let data = this.GetData(await API.fn_lay_ds_thaotac_theo_luong(this.axios, input))
      data = JSON.parse(data)
      console.log('fn_lay_ds_thaotac_theo_luong')
      console.log(data)
      if (data && data.ERROR_CODE) {
        console.log(data.RESULT.LTT_DAGAN)
        if (data.RESULT.LTT_DAGAN && data.RESULT.LTT_DAGAN.length > 0) {
          // let arrDaGan = data.RESULT.LTT_DAGAN.filter(item => {
          //     return item.ENABLE == 1
          // })
          this.thaoTacDaGan.list = data.RESULT.LTT_DAGAN
        } else {
          this.thaoTacDaGan.list = []
        }
        if (data.RESULT.LTT_CHUAGAN && data.RESULT.LTT_CHUAGAN.length > 0) {
          // let arrChuaGan =  data.RESULT.LTT_CHUAGAN.filter(item => {
          //     return item.ENABLE == 1
          // })
          this.thaoTacChuaGan.list = data.RESULT.LTT_CHUAGAN
        } else {
          this.thaoTacChuaGan.list = []
        }
      }
      var tinhData = await API.CSS_TINH(this.axios, {})
      this.dsTinh = tinhData.data.data
      this.dsTinh = this.dsTinh.sort((a, b) => a.TINH_ID > b.TINH_ID ? 1 : -1);
      this.dsTinh = this.dsTinh.sort((a, b) => a.PHANVUNG_ID == this.$root.token.getPhanVungID() ? -1 : 1);
      this.p_tinh = (this.dsTinh.find(e => e.PHANVUNG_ID == this.$root.token.getPhanVungID())).PHANVUNG_ID
    },

    async cboLoaiDV_SelectedValueChanged() {
      // if (this.cboLoaiDv.list.length <= 0) return;
      if (this.load) return
      let loaidv_id = this.cboLoaiDv.value
      let obj = {
        // LUONG_ID : this.luong_id,
        // LOAIDV_ID : this.cboLoaiDv.value
        LUONG_ID: this.luong_id_data,
        LOAIDV_ID: this.cboLoaiDv.value
      }
      let input = {
        p_ds_para: JSON.stringify(obj)
      }
      let data = this.GetData(await API.fn_lay_dvi_theo_luong_dv(this.axios, input))
      data = JSON.parse(data)
      if (data) {
        this.donViChuaGan.list = data.RESULT.LDV_CHUAGAN ? data.RESULT.LDV_CHUAGAN : []
        this.donViDaGan.list = data.RESULT.LDV_DAGAN ? data.RESULT.LDV_DAGAN : []
      }
    },
    tsbtnThaoTac_Click() {
      this.$refs.capNhatThaoTac.show()
    },
    async tsbtnGhiLai_Click() {
      let luong_tt = []
      console.log('thaoTacDaGan is ', this.thaoTacDaGan.list)
      if (this.thaoTacDaGan.list.length > 0) {
        this.thaoTacDaGan.list.map((item) => {
          luong_tt.push({
            LUONG_ID: this.luong_id_data,
            THAOTAC_ID: item.THAOTAC_ID,
            ENABLE: item.ENABLE
          })
        })
      }
      let obj = {
        LUONG_ID: this.luong_id_data,
        LUONG_TT: luong_tt
      }
      let input = {
        p_ds_para: JSON.stringify(obj)
      }

      let data = this.GetData(await API.fn_ins_luong_tt(this.axios, input))
      data = JSON.parse(data)
      if (data) {
        if (data.ERROR_CODE) {
          this.showSuccess('Cập nhật dữ liệu thành công !')
          this.frmLuongPhieuTT_Load()
        }
      }
    },
    btnHDRight() {
      // var items = this.listTable.hop_dong_thu_tien.list.filter(a => a.ischecked);
      let items = this.$refs.thao_tac_da_gan.getSelectedRecords()
      if (items.length > 0) {
        for (var item of items) {
          this.thaoTacChuaGan.list.push({
            THAOTAC: item.THAOTAC,
            CODE: item.CODE,
            THAOTAC_ID: item.THAOTAC_ID,
            ENABLE: item.ENABLE
          })
        }
      }
      var temps = this.thaoTacDaGan.list.filter((a) => !items.includes(a))
      this.thaoTacDaGan.list = []
      this.thaoTacDaGan.list = temps
    },

    btnHDRightAll() {
      if (this.thaoTacDaGan.list.length > 0) {
        for (var item of this.thaoTacDaGan.list) {
          this.thaoTacChuaGan.list.push({
            THAOTAC: item.THAOTAC,
            CODE: item.CODE,
            THAOTAC_ID: item.THAOTAC_ID,
            ENABLE: item.ENABLE
          })
        }
        this.thaoTacDaGan.list = []
      }
    },

    btnHDLeft() {
      // var items = this.listTable.hop_dong_chua_tra_tien.list.filter(a => a.ischecked);
      let items = this.$refs.thao_tac_chua_gan.getSelectedRecords()
      if (items.length > 0) {
        for (var item of items) {
          this.thaoTacDaGan.list.push({
            THAOTAC: item.THAOTAC,
            CODE: item.CODE,
            THAOTAC_ID: item.THAOTAC_ID,
            ENABLE: item.ENABLE
          })
        }
      }
      var temps = this.thaoTacChuaGan.list.filter((a) => !items.includes(a))
      this.thaoTacChuaGan.list = []
      this.thaoTacChuaGan.list = temps
    },

    btnHDLeftAll() {
      if (this.thaoTacChuaGan.list.length > 0) {
        for (var item of this.thaoTacChuaGan.list) {
          this.thaoTacDaGan.list.push({
            THAOTAC: item.THAOTAC,
            CODE: item.CODE,
            THAOTAC_ID: item.THAOTAC_ID,
            ENABLE: item.ENABLE
          })
        }
        this.thaoTacChuaGan.list = []
      }
    }
  }
}
</script>
