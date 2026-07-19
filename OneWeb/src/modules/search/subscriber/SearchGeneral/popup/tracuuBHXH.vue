<template>
 <div class="modal-content popup-box">        
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="#" @click.prevent="btnTimKiem_Click">
                      <span class="icon one-mobile-"></span> Tìm kiếm
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="btbLamMoi_Click">
                      <span class="icon one-file-refresh"></span> Làm mới
                    </a>
                </li>
            </ul>
        </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">Thông tin tìm kiếm</div>
        <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Số máy</div>
            <div class="value">
              <input type="text" v-model= "ma_tb" class="form-control highlight" @keyup.enter="tracuu">
            </div>
          </div>         
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
              <div class="key w90">Tỉnh</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="listbox.list_tinh.value" :allowFiltering="true" dataTextField="TENTINH"
                    dataValueField="TENTAT" :dataSource="listbox.list_tinh.list" class="highlight">
                  </SelectExt>
                </div>
              </div>
          </div>          
        </div>
        </div>
      </div>   
     
      <div class="box-form">
          <div class="legend-title">Thông tin trên hệ thống BHXH</div>
          <div class="row">
            <div class="table-dv-content">
                        <table id="db_cntt" class="table-result table-gachle">
                            <tbody>
                                <tr>
                                    <td> AM </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.dhsxkd_am_ho_ten" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td> Tên khách hàng </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.ten_khach_hang" readonly="true" />
                                        </div>
                                    </td>
                                </tr>   
                                <tr>
                                    <td> Mã số thuế </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.ma_so_thue" readonly="true" />
                                        </div>
                                    </td>
                                </tr>                             
                                <tr>
                                    <td> Mã BHXH </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.ma_don_vi" readonly="true" />
                                        </div>
                                    </td>
                                </tr>  
                                <tr>
                                    <td> Địa chỉ </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.dia_chi" readonly="true" />
                                        </div>
                                    </td>
                                </tr>  
                                <tr>
                                    <td> Email </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.email" readonly="true" />
                                        </div>
                                    </td>
                                </tr>  
                                <tr>
                                    <td> Số di động </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.sdt_di_dong" readonly="true" />
                                        </div>
                                    </td>
                                </tr>  
                                <tr>
                                    <td> Số cố định </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.sdt_co_dinh" readonly="true" />
                                        </div>
                                    </td>
                                </tr> 
                                <tr>
                                    <td> Họ tên người ký tờ khai </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.ho_ten_nguoi_ky_to_khai" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td> Chức danh người ký tờ khai </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.chuc_danh_nguoi_ky_to_khai" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td> Ngày ký hợp đồng </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0 text-right"
                                                v-model="content.ngay_ky_hop_dong" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td> Người ký hợp đồng </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.nguoi_ky_hop_dong" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td> Số hợp đồng </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.so_hop_dong" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td> Khối đơn vị </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.ma_khoi_don_vi" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td> Loại hình kinh doanh </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.ma_loai_hinh_kinh_doanh" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td> Loại gói dịch vụ </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.ma_loai_goi_dich_vu" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td> Gói dịch vụ </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.ma_goi_dich_vu" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td> Ngày bắt đầu sử dụng </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0 text-right"
                                                v-model="content.ngay_bat_dau_su_dung" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td> Ngày kết thúc sử dụng </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0 text-right"
                                                v-model="content.ngay_ket_thuc_su_dung" readonly="true" />
                                        </div>
                                    </td>
                                </tr>                                
                                <tr>
                                    <td> Số tháng khuyến mại </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0 text-right"
                                                v-model="content.so_thang_khuyen_mai" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                                 <tr>
                                    <td> Tên đăng nhập tài khoản cha </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.ten_dang_nhap_tai_khoan_cha" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                                 <tr>
                                    <td> Tên đăng nhập tài khoản AM Backend </td>
                                    <td class="padt0 padb0" colspan="2">
                                        <div class="value">
                                            <input type="text" class="form-control bg-none h24 bor0"
                                                v-model="content.ten_dang_nhap_am_backend" readonly="true" />
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
              </div>
            </div>
        </div>    

    </div>
  </div>
</template>
<script>

import API from '../API'
export default {
    name:'tracuu_BHXH',
    props: ['ma_tb'],

    components:{
       API
    },
    data(){
        return {
          content:{
              dhsxkd_am_ho_ten:'',
              ten_khach_hang:'',
              ma_so_thue:'',
              ma_don_vi:'',
              dia_chi:'',
              email:'',
              sdt_di_dong:'',
              sdt_co_dinh:'',
              ho_ten_nguoi_ky_to_khai:'',
              chuc_danh_nguoi_ky_to_khai:'',
              ngay_ky_hop_dong:'',
              nguoi_ky_hop_dong:'',
              so_hop_dong:'',
              ma_khoi_don_vi:'',
              ma_loai_hinh_kinh_doanh:'',
              ma_loai_goi_dich_vu:'',
              ma_goi_dich_vu:'',
              ngay_bat_dau_su_dung:'',
              ngay_ket_thuc_su_dung:'',
              so_thang_khuyen_mai:'',
              ten_dang_nhap_tai_khoan_cha:'',
              ten_dang_nhap_am_backend:'',
          },
          listbox:{
                list_tinh:{
                  list:[],
                  value:""
                },                
          }
        }
    },
    
  mounted () {
    API.css_tinh(this.axios,null).then(response =>{
      if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
        this.listbox.list_tinh.list = response.data.data
        this.listbox.list_tinh.value = 'HPG'
      }           
      else
        this.listbox.list_tinh.list = [];
    });
    // this.tracuu()
    
  },
 methods: {      
      async tracuu() {        
        try {
            if (this.ma_tb){          
                var data = {
                    "dhsxkd_loai_thue_bao_id": 132,
                    "dhsxkd_ma_thue_bao": this.ma_tb,
                    "dhsxkd_ma_tinh": this.listbox.list_tinh.value
                }
                let  formData= {}     
                await API.lay_thongtin_ivan(this.axios,data).then(response=> {                
                    if(response && response.data && response.data.data && response.data.errorCode == 0){                        
                        formData = JSON.parse(response.data.data.data);                        
                        for (let key in formData) {
                        this.$set(this.content, key, formData[key]);
                        }                        
                    } 
                    else {
                        this.$toast.error('BHXH: '+response.data.faultString);
                    }               
                });      
                if (Object.keys(formData).length>0){
                    this.content.ten_am = formData.dhsxkd_am_ho_ten + " - " + formData.dhsxkd_am_sdt + " - " + formData.dhsxkd_am_phong_ban;
                        
                        
                    this.content.ma_huyen = await API.fn_tt_capdo_dv({param:formData.ma_huyen, type:1})
                    
                    this.content.ma_khoi_don_vi = await API.fn_tt_loai_cts({param:formData.ma_khoi_don_vi, type:1})
                    
                    if (formData.ma_loai_hinh_kinh_doanh){
                        const nn_id = await API.fn_map_nganhnghe_bhxh({"vkieu": "0","vthamso": formData.ma_loai_hinh_kinh_doanh})
                        if (nn_id){
                            this.content.ma_loai_hinh_kinh_doanh = await api.fn_tt_nganhnghe({param:nn_id, type:1})
                        }
                        
                    }  
                    this.content.ma_loai_goi_dich_vu = await API.fn_tt_phanloai_dv({param:formData.ma_loai_goi_dich_vu, type:1})
                    
                    this.content.ma_goi_dich_vu = await  API.fn_tt_tocdo_adsl({param:formData.ma_goi_dich_vu, type:1})
                }    

            }
        }
        catch(ex){
            console.log(ex)
        }
      },
      async btnTimKiem_Click(){
        this.tracuu()
      },
      async btbLamMoi_Click(){
        //this.ma_tb=''
        this.content={
              dhsxkd_am_ho_ten:'',
              ten_khach_hang:'',
              ma_so_thue:'',
              ma_don_vi:'',
              dia_chi:'',
              email:'',
              sdt_di_dong:'',
              sdt_co_dinh:'',
              ho_ten_nguoi_ky_to_khai:'',
              chuc_danh_nguoi_ky_to_khai:'',
              ngay_ky_hop_dong:'',
              nguoi_ky_hop_dong:'',
              so_hop_dong:'',
              ma_khoi_don_vi:'',
              ma_loai_hinh_kinh_doanh:'',
              ma_loai_goi_dich_vu:'',
              ma_goi_dich_vu:'',
              ngay_bat_dau_su_dung:'',
              ngay_ket_thuc_su_dung:'',
              so_thang_khuyen_mai:'',
              ten_dang_nhap_tai_khoan_cha:'',
              ten_dang_nhap_am_backend:'',
        }
        this.listbox.list_tinh.value=''
      },
      async closeModal(){

      }

 },
  watch: { 
      
  },

}
</script>
<style scoped>
.table-dv-content{
    max-height:385px !important;
    overflow: scroll;
}
</style>