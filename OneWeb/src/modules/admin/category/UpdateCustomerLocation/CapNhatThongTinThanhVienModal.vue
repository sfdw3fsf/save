<template>
  <b-modal
    ref="popupCapNhatThongTinThanhVien"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Cập nhật thông tin thành viên
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions"  @onActionClick="onActionClick"/>

      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin cập nhật</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w70">Tên TV</div>
                <div class="value">
                  <input type="text" :value="input_ten_tv" @change="e=>input_ten_tv=e.target.value" class="form-control" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Quan hệ TV</div>
                <div class="value">
                    <div class="select-custom" ref="ds_quanhe">
                        <select2 :settings="{ dropdownParent: $refs['ds_quanhe'] }" ref="cboQuanHe"
                            v-model="quanhe_tv_selected" class="select2"
                            :options="danhsach_quanhe_tv.map(e=> ({id: e.QUANHETV_ID, text: e.QUANHE_TV}))"
                            >
                        </select2>
                    </div>
                </div>
                
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w70">Ngày sinh</div>
                <div class="value">
                  <div class="input-icon-right">
                    <k-date-picker v-model="ngay_sinh"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w50">Số ĐT</div>
                <div class="value">
                  <input type="text" ref="inputSoDT" :value="input_so_dt" @change="e=>input_so_dt=e.target.value" class="form-control" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Ngành nghề</div>
                <div class="value">
                  <input type="text" :value="input_nganh_nghe" @change="e=>input_nganh_nghe=e.target.value" class="form-control" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w70">Email</div>
                <div class="value">
                  <input type="text" ref="inputEmail" :value="input_email" @change="e=>input_email=e.target.value" class="form-control" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w50"></div>
                <div class="value padt7">
                  <div class="check-action">
                    <input type="checkbox" v-model="checkBoxGioiTinh" class="check" />
                    <span class="name">Nam</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Ghi chú</div>
                <div class="value">
                  <input type="text" :value="input_ghichu" @change="e=>input_ghichu=e.target.value" class="form-control" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách thành viên</div>
          <!-- <KTableV3 :columns="columns"
            :dataSources="dataSources"
            id="tvhd_id"
            :rowSelectedId="rowSelectedId"
            @onSelectedRow="onSelectedRow"
            :allowFilter="true"/> -->
          <KDataGrid
              :columns="columns"
              :dataSource="dataSources"
              :showColumnCheckbox="false"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              :enabledSelectFirstRow="true"
              @selectedItemsChanged="gridDsChanged"/>
        </div>
      </div>
      <CapNhatDichVuThanhVienModal ref="capnhat_dichvu_thanhvien" :hodan_id="hodan_id" :tvhd_id="tvhd_id_selected" />
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import UpdateCustomerLocationAPI from './UpdateCustomerLocationAPI'
import CapNhatDichVuThanhVienModal from './CapNhatDichVuThanhVienModal.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'CapNhatThongTinThanhVienModal',
    props:{
      hodan_id:{
        type:Number,
        default: -1,
        required: true
      }
    },
    components:{
        ActionTop,
        KDatePicker,
        CapNhatDichVuThanhVienModal,
        KDataGrid
    },
    data(){
        return{
            actions:[
                {
                    id:'capnhat',
                    name:'Cập nhật',
                    active: true,
                    icon_class:'one-reload1'
                },
                {
                    id:'dichvu',
                    name:'Dịch vụ',
                    active: true,
                    icon_class:'one-save'
                }
            ],
            columns:[
                {
                    fieldName:'ten_tv',
                    headerText:'Tên TV',
                    allowFiltering:true
                },
                {
                    fieldName:'ngaysinh',
                    headerText:'Ngày sinh',
                    allowFiltering:true
                },
                {
                    fieldName:'quanhe_tv',
                    headerText:'Quan hệ TV',
                    allowFiltering:true
                },
                {
                    fieldName:'nganhnghe',
                    headerText:'Ngành nghề',
                    allowFiltering:true
                },
                {
                    fieldName:'s_gioitinh',
                    headerText:'Giới tính',
                    allowFiltering:true
                },
                {
                    fieldName:'so_dt',
                    headerText:'Số ĐT',
                    allowFiltering:true
                },
                {
                    fieldName:'email',
                    headerText:'Email',
                    allowFiltering:true
                }
            ],
            dataSources:[],
            ngay_sinh: moment(new Date()).format('DD/MM/YYYY'),
            danhsach_quanhe_tv:[],
            quanhe_tv_selected:null,

            input_ten_tv:'',
            input_email:'',
            checkBoxGioiTinh:false,
            input_so_dt:'',
            input_nganh_nghe:'',
            input_ghichu:'',
            tvhd_id_selected:0,
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='capnhat'){
              this.CapNhat()
            }else if(action.id=='dichvu'){
              if(this.tvhd_id_selected==0){
                this.$toast.error('Hãy chọn thành viên để cập nhật dịch vụ!')
                return
              }
              this.$refs.capnhat_dichvu_thanhvien.showModal()
            }
        },
        showModal() {
            this.$refs["popupCapNhatThongTinThanhVien"].show()
        },
        hideModal() {
            this.$refs["popupCapNhatThongTinThanhVien"].hide()
        },
        handleShowModal(){
          //init data show modal
          this.danhsach_quanhe_tv=[]
          this.quanhe_tv_selected=null
          this.dataSources=[]
          this.input_ten_tv=''
          this.input_email=''
          this.checkBoxGioiTinh=false
          this.input_so_dt=''
          this.input_nganh_nghe=''
          this.input_ghichu=''
          this.tvhd_id_selected=0
          this.rowSelectedId=-1
          setTimeout(()=>{
            this.initDuLieu()
          },500)
          
        },
        async initDuLieu(){
          await this.lay_danhsach_quanhe_tv()
          await this.hienthi_ds_thanhvien_hodan()
          
        },
        async hienthi_ds_thanhvien_hodan(){
          //this.dataSources=[]
          this.dataSources=await this.lay_ds_thanhvien_theo_hodan_idV1(this.hodan_id)
        },
        gridDsChanged(data){
          if(this.dataSources.length<=0||data.length<=0){
            return
          }
          this.onSelectedRow(data[0])
        },
        onSelectedRow(item){
            this.tvhd_id_selected=item.tvhd_id
            this.input_ten_tv=item.ten_tv?item.ten_tv.toString():''
            this.input_email=item.email?item.email.toString():''
            this.checkBoxGioiTinh=item.gioitinh==0?true:false
            this.input_so_dt=item.so_dt?item.so_dt.toString():''
            this.input_nganh_nghe=item.nganhnghe?item.nganhnghe.toString():''
            this.input_ghichu=item.ghichu?item.ghichu.toString():''
            this.ngay_sinh=item.ngaysinh?item.ngaysinh:''
            if(item.quanhetv_id!=null&&item.quanhetv_id.toString().trim()!=''){
              this.quanhe_tv_selected=item.quanhetv_id
            }else{
              this.quanhe_tv_selected=null
            }
                  
        },
        CapNhat(){
          if(this.dataSources.length==0){
            this.$toast.error('Danh sách trống, không có dữ liệu để cập nhật!')
            return
          }
          if(this.tvhd_id_selected==-1){
            this.$toast.error('Hãy chọn thành viên để cập nhật!')
            return
          }
          if(this.input_so_dt.trim().length<10||this.input_so_dt.trim().length>11){
            this.$toast.error('Bạn phải nhập số di động vào (Số điện thoại từ 10-11 ký tự)!')
            this.$refs.inputSoDT.focus()
            return
          }
          if(this.input_so_dt&&!this.validatePhoneNumber(this.input_so_dt.trim())){
            this.$toast.error('Số điện thoại phải là kiểu số!')
            this.$refs.inputSoDT.focus()
            return
          }
          if(this.input_email.trim().length>0&&!this.validateEmail(this.input_email.trim())){
            this.$toast.error('Địa chỉ email không hợp lệ!')
            this.$refs.inputEmail.focus()
            return
          }

          let nguoi_cn=this.$root.token.getUserName()
          //chưa rõ format ngày sinh truyền qua api
          let data={
            vtvhd_id: this.tvhd_id_selected,
            vten_tv: this.input_ten_tv,
            vngaysinh: this.ngay_sinh,
            vso_dt: this.input_so_dt.trim(),
            vemail: this.input_email.trim(),
            vgioitinh: this.checkBoxGioiTinh?0:1,
            vquanhetv_id: this.quanhe_tv_selected,
            vnganhnghe: this.input_nganh_nghe.trim(),
            vghichu: this.input_ghichu.trim(),
            vnguoi_cn: nguoi_cn,
            vmay_cn: "localhost",
            vip_cn: "127.0.0.1"
          }
          this.capnhat_tt_thanhvien(data)
        },
        
        validatePhoneNumber(input_str) {
            var re = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/im;
            return re.test(input_str);
        },
        validateEmail(email){
          return String(email)
            .toLowerCase()
            .match(
              /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            )
        },
        async lay_danhsach_quanhe_tv(){
            try{
                let response = await UpdateCustomerLocationAPI.lay_danhsach_quanhe_tv(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_quanhe_tv=response.data.data
                    if(this.danhsach_quanhe_tv.length>0){
                        this.quanhe_tv_selected=this.danhsach_quanhe_tv[0].QUANHETV_ID
                    }
                }else{
                    this.danhsach_quanhe_tv=[]
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không load được danh sách quan hệ thành viên')
                }
            } 
        },
        async lay_ds_thanhvien_theo_hodan_idV1(hodan_id){
            try{
                this.loading(true)
                let response = await UpdateCustomerLocationAPI.lay_ds_thanhvien_theo_hodan_id(this.axios, hodan_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  return response.data.data.map(item=>Object.assign(item,{
                    ngaysinh:(item.ngaysinh!=null&&item.ngaysinh.toString().trim()!='')?moment(item.ngaysinh,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):null
                  }))
                }else{
                  return []
                }
            }catch(e){
              this.loading(false)
              return[]
            }
        },
        async capnhat_tt_thanhvien(data){
            try{
                this.loading(true)
                let response = await UpdateCustomerLocationAPI.capnhat_tt_thanhvien(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Cập nhật kết quả thành công!')

                    await this.hienthi_ds_thanhvien_hodan()
                }else{
                    if(response&&response.data&&response.data.message){
                      this.$toast.error(response.data.message)
                    }else{
                      this.$toast.error('Cập nhật kết quả lỗi')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                  this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                  this.$toast.error(e.response.data.message)
                }else{
                  this.$toast.error('Cập nhật kết quả  lỗi')
                }
            }
        }

        
    },
    mounted(){

    }
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
    white-space: normal !important;
    word-break: break-all !important;
}
</style>