<style scoped>
.list-actions-top .list > li > div.button {
    display: block;
    padding: 2px 15px;
    color: #0176FF;
    border-left: 1px solid #BAE7FF;
    text-decoration: none;
    font-weight: 600;
    cursor: pointer;
}
</style>

<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogBieuMau" :position="position" :header="'Quản lý biểu mẫu'" showCloseIcon="true" width="75%" target="#app .main-wrapper">
    <div class="">  
        <div class="popup-body">
          <div class="box-form"> 
              <div class="row">
                   <DataGrid v-bind:columns="[ 
                                    {fieldName: 'ten_loaihd', headerText: 'Loại HD', allowFiltering: true, customAttributes: { class: 'bold' }, autoFit: true},
                                    {fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true, type: 'dateTime', format: 'dd/MM/yyyy hh:mm a', autoFit: true},
                                    {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true , allowHtml : false, autoFit: true},
                                    {fieldName: 'url', headerText: 'Tên File', allowFiltering: true , allowHtml : true, autoFit: true},
                                ]"
                        v-bind:dataSource="grvDS_BieuMau.list" 
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :showFilter="true">
                  </DataGrid>
              </div> 
          </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script> 
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment' 
import api from './PopUpApi' 
  
export default {
  components: { 
    moment,
    DatePicker, 
    api, 
  },
  name: 'QL_BieuMau',
  data() {
    return {
      Loading: false,
      dateFormat: 'DD/MM/YYYY',
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
      state: { 
      }, 
      // tt_nd: {
      //   nhanvien_id:  this.$root.token.getNhanVienID() * 1,
      //   nguoidung_id: this.$root.token.getNguoiDungID() * 1,
      //   phanvung_id: this.$root.token.getPhanVungID() * 1,
      //   donvi_id: this.$root.token.getDonViID() * 1,
      //   may_cn: this.$root.token.getMachine(),
      //   ip_cn: this.$root.token.getIP(),
      //   ngay_cn: moment(new Date()).format("DD/MM/YYYY hh:mm A"),
      //   ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
      //     "DD/MM/YYYY hh:mm A"
      //   ),
      //   ten_dv: "",
      //   nguoi_cn: this.$root.token.getUserName()
      // },
      grvDS_BieuMau: {
        list: []
      }
      
    }
  },
  created: async function () {
     
  },
  mounted: function () { 
    
  },
  watch: {
     
  },
  methods: {
 
    dialogClose() {},
    openDialog() { 
         
      this.$refs.dialogBieuMau.show();
      this.pageLoad();
    },
    hideDialog () {
      this.$refs.dialogBieuMau.hide()
    },
    async pageLoad(){
      this.loading(true); 
      let data = this.GetData(await api.sp_nap_ds_bieumau_frm_qlbieumau(this.axios, {})); 
      if(data && data.length > 0){
       this.grvDS_BieuMau.list = data.map((x) => {
            x.url = `<a target="_blank" href=${x.url}>${x.url}</a>`
            return x;
          }); 
      }
      this.loading(false);
    },
    GetData: function (response) { 
      if (response.data.error === 200 || response.data.error === '200' || response.data.errorCode === 0) {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return null
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
  }, 
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>