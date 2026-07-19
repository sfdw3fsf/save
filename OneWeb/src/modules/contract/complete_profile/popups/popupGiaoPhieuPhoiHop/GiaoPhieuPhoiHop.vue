<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogGiaoPhieuPhoiHop" :position="position" :header="'Giao phiếu phối hợp'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
    <div class="">
      
      <div class="list-actions-top">
          <ul class="list">
              <li>
                  <a href="#">
                      <span class="icon nc-icon-glyph files_refresh"></span> Nạp lại
                  </a>
              </li>
              
          </ul>
      </div>
      <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title">Giao phiếu</div>
                        <div class="info-row">
                            <div class="key w100">
                                Đơn vị phối hợp
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <select name="" id="" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                            <div class="value w80 nowrap">
                                <button class="btn btn-second"  v-on:click="btnGiaoPhieuPH_Click" :class="{'disabled':(!controls.btnGiaoPhieuPH.enabled)}">Giao phiếu</button>

                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Danh sách phiếu đã giao</div>
                        <div class="table-content">
                             <DataGrid v-bind:columns="DsPhieu.headers" v-bind:dataSource="DsPhieu.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="false"> </DataGrid>
                        </div> 
                    </div>
                    
                </div> 
    </div>
  </ejs-dialog>
</template>
<script>
import XLSX from 'xlsx'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment' 
import api2 from '@/modules/contract/complete_profile/popups/popupGiaoPhieuPhoiHop/PopUpApi'
export default {
  components: {
    XLSX,
    moment,
    DatePicker, 
    api2
  },
  name: 'GiaoPhieuPhoiHop',
  data() {
    return {
      Loading: false,
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      showTimePanel: false,
      tabIndex: 0,
      state: {},
      controls: {
        btnGiaoPhieuPH:{ enabled: false,  }
      }, 
      DsPhieu: {
        headers: [
          {
            fieldName: '',
            headerText: 'ID',
            allowFiltering: false,
            allowSorting: true
          },
          {
            fieldName: '',
            headerText: 'Ngày giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: '',
            headerText: 'Người giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: '',
            headerText: 'Nhiệm vụ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: '',
            headerText: 'Đơn vị nhận',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: '',
            headerText: 'Trạng thái phiếu',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: '',
            headerText: 'Hủy giao',
            allowFiltering: true,
            allowSorting: false
          }, 
        ],
        data: []
      },
      PhieuId: 0,
      HdTbId: 0,
      ThueBaoId: 0,
      BhId: 0,
      tag_hdbh: -1,
      Tag: ''
    }
  },
  created: async function () {
     
  },
  mounted: function () { 
    this.pageLoad();
  },
  watch: {
     
  },
  methods: {
    
    dialogClose() {},
    openDialog(phieu_id, hdtb_id) {
      this.PhieuId = phieu_id
      this.HdTbId = hdtb_id
      this.BhId = 0
      this.Loading = true;

      // Promise.all([this.lay_tt_baohong_chuyento_chung()])
      //   .then((values) => {
      //     console.log('Promise.all', values)
      //   })
      //   .catch((error) => {
      //     console.log(`Error in promises ${error}`)
      //   })
      //   .finally((e) => {
      //     this.Loading = false
      //     this.$refs.dialogObjChuyenTo.show()
      //   })
      console.log('this.$refs.dialogGiaoPhieuPhoiHop.show()');
      this.$refs.dialogGiaoPhieuPhoiHop.show()
    },
    async pageLoad(){
      await this.NAP_THONGTIN_PHIEU_HIENTAI();
      if (this.CURRENT_PHIEU == null) return;

      await this.NAP_DS_PHIEU();
      await this.NAP_DS_TOKT();
    }, 
    async NAP_THONGTIN_PHIEU_HIENTAI(){
      var input = {  "phieu_id": this.PhieuId }
      let data = this.GetData(await api2.lay_ds_file_hdtb(this.axios, input));
      
      this.state.CURRENT_PHIEU = rs[0]; 
      this.state.IS_PHIEUCHINH = CURRENT_PHIEU["IS_PHIEUCHINH"].toString() == "1"; 
      this.controls.btnGiaoPhieuPH.enabled = this.state.IS_PHIEUCHINH;  
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async NAP_DS_PHIEU(){
      var input = {  "phieu_id": this.PhieuId }
      let data = this.GetData(await api2.sp_nap_ds_phieu_giaophieuphoihop(this.axios, input));
      this.DsPhieu.data = data; 
    },
    async NAP_DS_TOKT(){
      //TODO: PENDING do liên quan đến bán chéo
    },
    btnGiaoPhieuPH_Click(){

    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>