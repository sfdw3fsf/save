<template>
  <div>
    <div class="page-content">      
      <div class="box-form">
        <div class="legend-title">Thông tin kịch bản BNM</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">{{ cboTinh.text }}</div>
              <div class="value">
                <div class="select-custom" ref="cboTinh">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboTinh'] }"
                    v-model="cboTinh.value" class="select2"
                    :options="cboTinh.Items"
                    :disabled=cboTinh.Enabled>
                  </select2>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">{{ cboHangSX.text }}</div>
              <div class="value">
                <div class="select-custom" ref="cboHangSX">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboHangSX'] }"
                    v-model="cboHangSX.value" class="select2"
                    :options="cboHangSX.Items"
                    :disabled=cboHangSX.Enabled>
                  </select2>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">{{ cboDichVu.text }}</div>
              <div class="value">
                <div class="select-custom" ref="cboDichVu">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboDichVu'] }"
                    v-model="cboDichVu.value" class="select2"
                    :options="cboDichVu.Items"
                    :disabled=cboDichVu.Enabled>
                  </select2>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">{{ cboLoaiCH.text }}</div>
              <div class="value">
                <div class="select-custom" ref="cboLoaiCH">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboLoaiCH'] }"
                    v-model="cboLoaiCH.value" class="select2"
                    :options="cboLoaiCH.Items"
                    :disabled=cboLoaiCH.Enabled>
                  </select2>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">          
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">{{ dtpNgayTao.text }}</div>
              <div class="value">
                <DatePicker :format="dateFormat" ref="dtpNgayTao" :value-type="'format'"
                  v-model="dtpNgayTao.value"
                  :disabled=dtpNgayTao.Enabled>
                </DatePicker>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">{{ txtNguoiTao.text }}</div>
              <div class="value">
                <input type="text" class="form-control" v-bind:value="txtNguoiTao.value"
                  @change="e => txtNguoiTao.value = e.target.value"
                  :disabled=txtNguoiTao.Enabled>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="dulieu && dulieu.parrent == 'BNMScriptRequests'" class="row">
        <div class="col-sm-12 col-12">
          <div class="box-form">
            <div class="legend-title">PARAM BSS</div>
            <div class="info-row">
              <div class="value">
                <div v-html="formBSS"></div>
              </div>                  
            </div>
          </div>          
        </div>         
      </div>      

      <div class="row">
        <div class="col-sm-12 col-12">
          <div class="box-form">
            <div class="legend-title">SCRIPT</div>
            <div class="info-row">
              <div class="value">
                <textarea name="formBSS" class="form-control" style="height: 350px;" v-bind:value="formBSS"></textarea>
              </div>                  
            </div>
          </div>          
        </div>          
      </div>  
    </div>
  </div>
</template>
<style src="../../BNMScripts/BNMScripts.scss"></style>

<script>
import breadcrumb from "@/components/breadcrumb";
import moment from 'moment'
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import DataGrid from "@/components/Controls/DataGrid";
import api from '../API.js'

export default {
  name: "BNMRequestDetail",
  components: {
    breadcrumb,
    DatePicker,
    DataGrid
  },
  props: ['dulieu'],
  data: () => ({
    header: {
      title: "Chi Tiết Request Từ BSS",
      list: [
        {
          name: 'Chi Tiết Request Từ BSS',
          link: { 
            name: 'Ui.buttons',
            active: true 
          }
        }
      ],
    },
    btnSave: {text: 'Cập nhật', Visibled: true},
    btnDelete: {text: 'Xóa', Visibled: true},
    dateFormat: "DD/MM/YYYY HH:mm:ss",
    log_script_id: 0,
    script_bnm_id: 0,
    cboTinh: {text: 'Tỉnh', value: '', Items: [], Enabled: true},
    cboHangSX: {text: 'Hãng SX', value: '', Items: [], Enabled: true},
    cboDichVu: {text: 'Dịch vụ', value: '', Items: [], Enabled: true},
    cboLoaiCH: {text: 'Loại cấu hình', value: '', Items: [], Enabled: true},
    dtpNgayTao: {text: 'Ngày tạo', value: '', Enabled: true},
    txtNguoiTao: {text: 'User thay đổi', value: '', Enabled: true},
    formBSS: {
      txtParam1: {text: 'param1', value: ''},      
      txtParam2: {text: 'param2', value: '', readOnly: true},
      txtParam3: {text: 'param3', value: ''},
    },
  }),
  mounted () {
    this.formLoad()
  },
  methods: {
    formLoad: async function () {
      await Promise.all([
        this.getDSTinh(),
        this.getHangSX(),
        this.getDSDichVu(),
        this.getDSLoaiCH(),
      ]);

      if (this.dulieu) {
        this.log_script_id = this.dulieu?.id || null
        this.script_bnm_id = this.dulieu?.script_bnm_id || null
        this.cboTinh.value = this.dulieu?.province_id || null
        this.cboHangSX.value = this.dulieu?.vendor_id || null
        this.cboDichVu.value = this.dulieu?.service_id || null
        this.cboLoaiCH.value = this.dulieu?.config_type || null
        this.txtNguoiTao.value = this.dulieu?.user_update || null
        this.dtpNgayTao.value = moment(this.dulieu?.created_date || null).format('DD/MM/YYYY HH:mm:ss')
      }
      await this.GetDetailLogScript()
      console.log(this.dulieu)
    },
    GetDetailLogScript: async function () {      
      try {
        this.formBSS = []
        let input = {log_script_id: this.log_script_id}
        var res = await api.GetDetailLogScript(this.axios, input)
        if (res.data.errorCode != 0) {
          this.$toast.error(res.data.faultString)
          return false
        }
        this.formBSS =  res?.data?.data.script || []
      } catch (error) {
        console.log('🚀 ~ file: BNMScripts.vue:2169 ~ GetDetailLogScript ~ error:', error)
      }
    },
    getDSTinh: async function () {      
      try {
        this.cboTinh.Items = []
        var res = await api.GetListProvince(this.axios)
        if (res.data.errorCode != 0) {
          this.$toast.error(res.data.faultString)
          return false
        }
        this.cboTinh.Items =  res?.data?.data?.map(e => ({...e, text: (e.code + ' - ' + e.name)})) || []
      } catch (error) {
        console.log('🚀 ~ file: BNMScripts.vue:2169 ~ GetListProvince ~ error:', error)
      }
    },
    getHangSX: async function () {
      this.cboHangSX.Items = []
      try {
        var res = await api.GetListVendor(this.axios)
        if (res.data.errorCode != 0) {
          this.$toast.error(res.data.faultString)
          return false
        }
        this.cboHangSX.Items =  res?.data?.data?.map(e => ({...e, id: e.vendor_id, text:`${e.vendor_id} - ${e.vendor_name}`})) || []
      } catch (error) {
        console.log('🚀 ~ file: BNMScripts.vue:2169 ~ GetListVendor ~ error:', error)
      }
    },
    getDSDichVu: async function () {
      this.cboDichVu.Items = []
      try {
        var res = await api.GetListService(this.axios)
        if (res.data.errorCode != 0) {
          this.$toast.error(res.data.faultString)
          return false
        }
        this.cboDichVu.Items =  res?.data?.data?.map(e => ({...e, id: e.service_id, text:`${e.service_id} - ${e.service_name}`})) || []
      } catch (error) {
        console.log('🚀 ~ file: BNMScripts.vue:2169 ~ GetListService ~ error:', error)
      }
    },
    getDSLoaiCH: async function () {
      this.cboLoaiCH.Items = []
      try {
        var res = await api.GetListConfig(this.axios)
        if (res.data.errorCode != 0) {
          this.$toast.error(res.data.faultString)
          return false
        }
        this.cboLoaiCH.Items =  res?.data?.data?.map(e=>({...e, id: e.config_type, text:`${e.config_type} - ${e.config_name}`})) || []
      } catch (error) {
        console.log('🚀 ~ file: BNMScripts.vue:2169 ~ GetListConfig ~ error:', error)
      }
    },
    closeForm: function (val) {
      if (val) this.$emit("form-close", val)
    },
    
    clearForm: function () {
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    btnChitiet_click: function (item) {
      console.log('chitiet')
      // console.log(item)
      // this.$refs.popupBNMDetailScript.showModal(true)
      // this.popupComponent = () =>
      //   import(
      //     "@/modules/admin/bnm/BNMScriptRequests/BNMScriptRequests.vue"
      //   );
      // this.popupComponentName = "popup_DSYeuCauTaoScript";
      // this.popupComponentHeader = 'Danh sách yêu cầu tạo scripts từ BNM';
      // this.popupComponentData = {
      //   isPopup: true,
      //   ...item  
      // }; 
      // this.Popup("popupComponents");
    },
  },
  watch: {
    "dulieu"(val) {
      if (val) {
        this.log_script_id = val?.id || null
        this.script_bnm_id = val?.script_bnm_id || null
        this.cboTinh.value = val?.province_id || null
        this.cboHangSX.value = val?.vendor_id || null
        this.cboDichVu.value = val?.service_id || null
        this.cboLoaiCH.value = val?.config_type || null
        this.txtNguoiTao.value = val?.user_update || null
        this.dtpNgayTao.value = moment(val?.created_date || null).format('DD/MM/YYYY HH:mm:ss')
      }
    }
  }
}
</script>
