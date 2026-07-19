<template>
  <div class="col-sm-4 col-12">
    <div class="box-form">
      <div class="legend-title">Thông tin cộng tác viên VNP</div>

      <div class="info-row">
        <div class="key w80">
          <div class="check-action">
            <input type="checkbox" @change="changeCheck" v-model="checkBoxDonvi" class="check" />
            <span class="name">Đơn vị</span>
          </div>
        </div>

        <div class="value">
          <ejs-dropdownlist 
                id="dr_donvi"
                :allowFiltering="true"
                v-model="donvi_selected"
                :enabled="checkBoxDonvi"
                @change="changeDonVi"
                :dataSource="danh_sach_donvi"
                :fields="{ text: 'ten_dv', value: 'donvi_id' }" />
        </div>
      </div>
      
      <div class="info-row">
        <div class="key w80">Nhóm</div>
        <div class="value">
          <ejs-dropdownlist 
                id="dr_nhom"
                :allowFiltering="true"
                v-model="nhom_selected"
                :enabled="checkBoxDonvi"
                @change="changeNhom"
                :dataSource="danh_sach_nhom"
                :fields="{ text: 'ten', value: 'loainv_id' }" />
        </div>
      </div>

      <div class="info-row">
        <div class="key w80">Nhân viên</div>
        <div class="value">
          <ejs-dropdownlist 
                :allowFiltering="true"
                v-model="nhanvien_selected"
                :enabled="checkBoxDonvi"
                :dataSource="danhsach_nhanvien"
                :fields="{ text: 'ten_ht', value: 'nhanvien_id' }" />
        </div>
      </div>

    </div>
  </div>
</template>
<script>
import AssignAgentHomeComboApi from './AssignAgentHomeComboApi'
export default {
  name: "ThongTinCongTacVienVNP",
  props:{
      hdtb:{
          type:Object,
          default:() => ({})
      },
      all_nhanvien:{
          type: Array,
          default: () => []
      }
  },
  data() {
    return {
      donvi_selected:null,
      danh_sach_donvi:[],
      nhom_selected:null,
      danh_sach_nhom:[],
      nhanvien_selected:this.hdtb.ctv_vnp_id,
      danhsach_nhanvien:this.all_nhanvien,
      checkBoxDonvi:false
    }

  },
  methods:{
    async lay_ds_donvi_ctv(){
      try{
          let response=await AssignAgentHomeComboApi.lay_ds_donvi_ctv(this.axios)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.danh_sach_donvi=response.data.data
          }else{
              this.danh_sach_donvi=[]
          }
      }catch(e){
          this.$toast.error('Không load được danh sách đơn vị')
      }
    },
    async sp_lay_ds_loai_nv(){
      try{
          let response=await AssignAgentHomeComboApi.sp_lay_ds_loai_nv(this.axios)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.danh_sach_nhom=response.data.data
          }else{
              this.danh_sach_nhom=[]
          }
      }catch(e){
          this.$toast.error('Không load được danh sách nhóm cộng tác viên')
      }
    },
    async sp_lay_ds_nhanvien_ctv(donvi_id, loainv_id){
      try{
          this.loading(true)
          let response=await AssignAgentHomeComboApi.sp_lay_ds_nhanvien_ctv(this.axios, donvi_id, loainv_id)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.danhsach_nhanvien=response.data.data
          }else{
              this.danhsach_nhanvien=[]
          }
      }catch(e){
          this.$toast.error('Không load được danh sách nhân viên')
      }finally{
          this.loading(false)
      }
    },
    changeDonVi(){
      // if(!this.checkBoxDonvi){
      //   return
      // }
      if(this.donvi_selected!=null||this.nhom_selected!=null){
        this.danhsach_nhanvien=[]
       this.sp_lay_ds_nhanvien_ctv(this.donvi_selected!=null?this.donvi_selected:-1, this.nhom_selected!=null?this.nhom_selected:-1)
      }else{
        this.danhsach_nhanvien=this.all_nhanvien
      }
      
    },
    changeNhom(){
      // if(!this.checkBoxDonvi){
      //   return
      // }
      if(this.donvi_selected!=null||this.nhom_selected!=null){
        this.danhsach_nhanvien=[]
        this.sp_lay_ds_nhanvien_ctv(this.donvi_selected!=null?this.donvi_selected:-1, this.nhom_selected!=null?this.nhom_selected:-1)
      }else{
        this.danhsach_nhanvien=this.all_nhanvien
      }
    },
    changeCheck(){
      if(!this.checkBoxDonvi){
        this.donvi_selected=null
        this.nhom_selected=null
        this.danhsach_nhanvien=this.all_nhanvien
      }
    },
    getNhanVienID(){
      return this.nhanvien_selected
    }
  },
  mounted(){
    this.lay_ds_donvi_ctv()
    this.sp_lay_ds_loai_nv()
  },
  watch:{
    hdtb:function(newVal, oldVal){
      this.donvi_selected=null
      this.nhom_selected=null
      this.danhsach_nhanvien=this.all_nhanvien
      this.nhanvien_selected=newVal.ctv_vnp_id
    },
    all_nhanvien:function(newVal, oldVal){
      this.donvi_selected=null
      this.nhom_selected=null
      this.danhsach_nhanvien=newVal
    }
  }
}
</script>