<template>
  <div class="box-form">
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w120">Nhóm người dùng</div>
          <div class="value">
            <ejs-dropdownlist 
                allowFiltering="true"
                v-model="nhomNDSelected"
                @change="changeNhomND"
                :filtering="onFilteringNhomND"
                :dataSource="danhSachNhomND"
                :fields="{ text: 'TEN_NHOM', value: 'NHOM_ND_ID' }" />
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w70">Tìm kiếm</div>
          <div class="value">
            <input type="text" @keyup.enter="enterSearch($event)" class="form-control highlight" />
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w120">Ngày gạch lùi</div>
          <div class="value">
            <input type="text" v-model="itemNhomNDSelected.NGAY_GL" @keypress="isNumber($event)"  class="form-control highlight red" value="2" />
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key">Ngày gạch tiến</div>
          <div class="value">
            <input type="text" v-model="itemNhomNDSelected.NGAY_GT" @keypress="isNumber($event)" class="form-control highlight red" value="2" />
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w70">Ngày xóa</div>
          <div class="value">
            <input type="text" v-model="itemNhomNDSelected.NGAY_XL" @keypress="isNumber($event)" class="form-control highlight" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import GrantUserPermissionAPI from './GrantUserPermissionAPI'
import { DataManager,Query,ODataV4Adaptor,Predicate } from "@syncfusion/ej2-data"
export default {
  name: "ThongTinNhomND",
  data(){
      return{
          danhSachNhomND:[],
          nhomNDSelected:-1,
          itemNhomNDSelected:{}
      }
  },
  methods:{
      onFilteringNhomND(e){
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('TEN_NHOM', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.danhSachNhomND, query);
      },
      isNumber(evt) {
          evt = (evt) ? evt : window.event;
          var charCode = (evt.which) ? evt.which : evt.keyCode;
          if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
              evt.preventDefault()
          } else {
              return true
          }
      },
      async lay_danhsach_nhom_nd(){
        try{
          let response = await GrantUserPermissionAPI.lay_danhsach_nhom_nd(this.axios)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.danhSachNhomND=response.data.data
            if(this.danhSachNhomND.length>0){
                this.nhomNDSelected=this.danhSachNhomND[0].NHOM_ND_ID
                // this.itemNhomNDSelected=this.danhSachNhomND[0]
                // this.$emit('changeNhomNguoiDung', this.itemNhomNDSelected)
            }
            
          }else{
            this.danhSachNhomND=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách nhóm người dùng')
        } 
      },
      async fn_nhomnd_get_id_by_ten_nhom(ten_nhom){
        try{
          let response = await GrantUserPermissionAPI.fn_nhomnd_get_id_by_ten_nhom(this.axios, ten_nhom)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            if(response.data.data.nhomnd_id!=null){
                this.nhomNDSelected=response.data.data.nhomnd_id
                // this.itemNhomNDSelected=this.danhSachNhomND.find(item=>item.NHOM_ND_ID==this.nhomNDSelected)
                // if(this.itemNhomNDSelected!=undefined){
                //     this.$emit('changeNhomNguoiDung', this.itemNhomNDSelected)
                // }
            }else{
              this.$toast.error('Không tìm thấy thông tin')
            }
          }else{
            if(response && response.data &&response.data.message){
              this.$toast.error(response.data.message)
            }else{
              this.$toast.error('Đã xảy ra lỗi, không thể tìm kiếm')
            }
          }
        }catch(error){
            if(error.data&&error.data.message){
              this.$toast.error(error.data.message)
            }else if(error.response&&error.response.data&&error.response.data.message){
              this.$toast.error(error.response.data.message)
            }else{
              this.$toast.error('Đã xảy ra lỗi, không thể tìm kiếm')
            }
        }
      },
      async ghilai_ds_quyen_gan_nhomnd(data){
        try{
          this.loading(true)
          let response = await GrantUserPermissionAPI.ghilai_ds_quyen_gan_nhomnd(this.axios, data)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
              this.$toast.success('Cập nhật dữ liệu thành công')
          }else{
            if(response && response.data &&response.data.message){
                this.$toast.error(response.data.message)
            }else{
                this.$toast.error('Đã xảy ra lỗi, không thể ghi dữ liệu')
            }
          }
        }catch(error){
          if(error.data&&error.data.message){
              this.$toast.error(error.data.message)
          }else if(error.response&&error.response.data&&error.response.data.message){
            this.$toast.error(error.response.data.message)
          }else{
            this.$toast.error('Đã xảy ra lỗi, không thể ghi dữ liệu')
          }
        }finally{
          this.loading(false)
        }
      },
      ghiDuLieu(){
          if(!this.itemNhomNDSelected){
              return
          }
          this.ghilai_ds_quyen_gan_nhomnd({
              ngay_xoa:this.itemNhomNDSelected.NGAY_XL,
              nhom_nd_id:this.itemNhomNDSelected.NHOM_ND_ID,
              ngay_tien: this.itemNhomNDSelected.NGAY_GT,
              ngay_lui: this.itemNhomNDSelected.NGAY_GL
          })
      },
      enterSearch(event){
          let value=event.target.value.trim()
          if(value.length==0){
              return
          }
          this.fn_nhomnd_get_id_by_ten_nhom(value)   
      },
      changeNhomND(){
          this.itemNhomNDSelected=this.danhSachNhomND.find(item=>item.NHOM_ND_ID==this.nhomNDSelected)
          if(this.itemNhomNDSelected!=undefined){
              this.$emit('changeNhomNguoiDung', this.itemNhomNDSelected)
          }
      }
  },
  mounted(){
      this.lay_danhsach_nhom_nd()
  }
}
</script>