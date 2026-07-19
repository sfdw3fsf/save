<template>
  <b-modal ref="popupNguoiDungIPCC" size="lg" hide-header hide-header-close hide-footer body-class="p-0" no-close-on-backdrop >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Ghép nối IPCC</div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">       
        <div class="box-form">
           <!-- <div class="row">         
          </div> -->
          <div class="row">
            <div class="col-sm-6 col-6">
              <div class="info-row">
                <div class="key w75" style="font-size: 110%">User</div>
                <div class="value">
                  <input :disabled="!txtMaND.enabled" type="text" class="form-control" v-model="txtMaND.value" >
                </div>
              </div>            
            </div>
            <div class="col-sm-6 col-6">
              <div class="info-row">
                <div class="key w90" style="font-size: 110%">Tên User</div>
                <div class="value">
                  <input :disabled="!txtTenND.enabled" type="text" class="form-control" v-model="txtTenND.value" >
                </div>
              </div>            
            </div>
            <div class="col-sm-6 col-6">
              <div class="info-row">
                <div class="key w75" style="font-size: 110%">User IPCC</div>
                <div class="value">
                  <input :disabled="!txtAgent.enabled" type="text" class="form-control" v-model="txtAgent.value"  />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-6">
            <!-- <div class="col-12" style="padding: 0 10px;"> -->
              <div class="info-row">
                <div class="key w90 " style="font-size: 110%">Line</div>
                <div class="value">
                  <input :disabled="!txtLine.enabled" type="text" class="form-control" v-model="txtLine.value" />
                </div>
              </div>
            </div>
            <!-- :selectionSettings="{ enableToggle: false, checkboxOnly: false, persistSelection: true, checkboxMode: 'ResetOnRowClick' }" -->
            <DataGrid
              ref="gridIPCC"
              panelDataHeight="300px"
              v-bind:columns="gridIPCC.header"
              v-bind:dataSource="gridIPCC.list"
              :enabledSelectFirstRow="false"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="false"
              :allowPaging="true"
              @rowSelected="change"
            />
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import UserAPI from './UserAPI'
// import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
export default {
  name: 'NguoiDungIPCCModal',
  props: {
    danhsach: {
      type: Array,
      default: () => [],
    },
    p_nguoidung_id:{
        type:Number,
        default:0
      },
      p_ma_nd:{
        type:String,
        default:''
      },
       p_ten_nd:{
        type:String,
        default:''
       }
  },
  components: {
    ActionTop,
    KDataGrid
   },
  data() {
    return {
     
      user_ipcc:'',
      actions: [
      {
          id: 'tracuu',
          name: 'Tra cứu',
          active: true,
          icon_class: 'icon one-search'          
        },
        {
          id: 'ghilai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-save'
        },
        {
          id: 'xoa',
          name: 'Xoá',
          active: true,
          icon_class: 'one-trash'
        }
       
      ],
      gridIPCC: {
        header: [
          {
            fieldName: 'user_ipcc',
            headerText: 'User IPCC',
            textAlign: 'Left',
            allowFiltering: true,
            allowSorting: true
          },
          {
            fieldName: 'line',
            headerText: 'Line',
            textAlign: 'Left',
            allowFiltering: true,
            allowSorting: true
          }
        ],

        list: [],
        selected: [],
        value: null
      },
      txtTenND: {enabled:false, value:''},
      txtMaND: {enabled:true, value:''},
      txtAgent: { enabled: true, value: '' },
      txtLine: { enabled: true, value: '' },          
      dataSources: [],
      rowSelected: null,
    }
  },
  async mounted() {
    // console.log(this.$refs)
    // console.log(this.p_ma_nd);
    // console.log('mounted');
    // await this.layDuLieu();
  },
  methods: {
    showModal() {      
      this.loading(true)
      this.$refs['popupNguoiDungIPCC'].show()
      this.txtMaND.value = this.p_ma_nd
      this.txtTenND.value = ''
      // this.txtLine.value = 0
      this.layDuLieu()
      this.loading(false)
    },

    hideModal() {
      this.$refs['popupNguoiDungIPCC'].hide()
    },

    onActionClick(action) {
      if (action.id == 'ghilai') {
        this.ghiDuLieu()
      } else if (action.id == 'xoa') {
        this.xoaDulieu()
        
      }
      else if (action.id == 'tracuu') {
        this.traCuu()
      }
    },

     kiemTra_ky_tu(str) {
      const specialChars = /[!@#$%^&*()+\=\\{};':"\\|,<>\/?]+/;
     return specialChars.test(str);
    },

    kiemTra_khoangTrang(str){
      const khoangTrang=/\s/
      return khoangTrang.test(str)
    },
    
    kiemTra_chuCai(str){
      const charset = /[0123456789]/;     
      return charset.test(str);
    },

    kiemTra(str){
      if(this.kiemTra_ky_tu(str)|| this.kiemTra_khoangTrang(str)) return true
      else return false
    },

    async ghiDuLieu() {          
      if(this.kiemTra(this.txtAgent.value)||this.kiemTra(this.txtMaND.value)){        
        this.$toast.warning('User hoặc User IPCC chứa ký tự không hợp lệ!')
        return
      } 
          
      if(this.txtAgent.value.length > 50||this.txtMaND.value.length > 50){
        this.$toast.warning('User và User IPCC tối đa 50 kí tự!')
        return
      }
      
      if(!this.txtMaND.value.length||!this.txtAgent.value.length){
        this.$toast.warning('Chưa nhập User hoặc User IPCC')
        return
      }
     
      if(!this.kiemTra_chuCai(this.txtLine.value)||this.kiemTra(this.txtLine.value)||!this.txtLine.value.length){
        this.$toast.warning('Line không hợp lệ! ')
        return
      }
      if(this.txtLine.value.length>8){
        this.$toast.warning('Line tối đa 8 ký tự!')
        return
      }
      try {
        
        this.loading(true)
        let kq = await UserAPI.gan_useripcc_v2(this.axios, { p_ma_nv: this.txtMaND.value, p_agent: this.txtAgent.value, p_line:this.txtLine.value })        
        if (kq.data.error_code == 'BSS-00000000') {
          this.$toast.success('Gắn thành công')
          this.txtAgent.value = ''
          this.txtLine.value = 0
          await this.layDuLieu()
        } else {
          this.$toast.error('${kq.data.message}')
        }       
      } catch (e) {
        console.log("err", e)
        if(e.data&&e.data.message){
          this.$toast.error(e.data.message)
        }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }
      } 
      finally {
        this.loading(false)
      }
    },

    async xoaDulieu() {
      // console.log('Xóa Agent')
      if(this.user_ipcc=='') this.$toast.error('Chưa chọn User IPCC')
      else{
                console.log(this.user_ipcc)
                if (!confirm('Bạn có chắc chắn muốn xóa User IPCC ${this.user_ipcc} không?')) return
                console.log('xác nhận')
                try {
                  this.loading(true)
                  let kq = await UserAPI.xoa_useripcc_v2(this.axios, {
                    p_user_ipcc: this.user_ipcc
                  })
                  if ((kq.data.error_code = 'BSS-00000000')) {
                    this.$toast.success('Xóa thành công!')
                    this.user_ipcc=''
                    this.layDuLieu()                   
                  } else {
                    this.$toast.error('Xóa không thành công!')
                  }
                } catch (e) {
                  console.log("err", e)                  
                 if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }
                } finally {
                  this.loading(false)
                }
              }
    },
    async traCuu(){
      this.layDuLieu()
    },
   change(e){
    this.user_ipcc=e.data.user_ipcc
   },

   async layDuLieu() {
      try {
        this.loading(true)
        let kq = await UserAPI.lay_ds_agent_v2(this.axios, { p_ma_nd: this.txtMaND.value })
        if (kq.data.data && kq.data.data.length) {
          this.gridIPCC.list = kq.data.data          
        } else {
          this.gridIPCC.list = []
        }
        let kq1 = await UserAPI.lay_ten_nd_by_ndid(this.axios, { p_ma_nd: this.txtMaND.value })
        if ((kq1.data.error_code == 'BSS-00000000')) {
          this.$toast.success('Lấy dữ liệu thành công!')
          this.txtTenND.value = kq1.data.data [0]["ten_nd"]
        } else {
          this.txtTenND.value = ''
          this.$toast.warning('Mã người dùng không tồn tại')
        }
      } catch (e) {
        console.log("err", e)
        if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }
      } finally {
        this.loading(false)
      }     
    },
  }

}
</script>