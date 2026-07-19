<template>
  <div class="box-form">
    <div class="legend-title">
      <span class="fa fa-angle-up icon"></span>Thông tin tìm kiếm
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w110">
            <div class="check-action">
              <input type="checkbox" v-model="checkBoxs.loai_hd" class="check" />
              <span class="name">Loại HĐ/PL</span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom">
              <select v-model="loai_hd_selected" :disabled="!checkBoxs.loai_hd" class="form-control">
                <option v-for="item in danhsach_loaihd" :key="item.ID" :value="item.ID">{{item.NAME}}</option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w140">Tra cứu theo</div>
          <div class="value">
            <div class="select-custom">
              <select v-model="tieuchi_selected" class="form-control">
                <option v-for="item in danhsach_tieuchi" :key="item.ID" :value="item.ID">{{item.NAME}}</option>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w110">
            <div class="check-action">
              <input type="checkbox" v-model="checkBoxs.dichvu" class="check" />
              <span class="name">Dịch vụ</span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom">
              <select v-model="dichvu_selected" :disabled="!checkBoxs.dichvu" class="form-control">
                <option v-for="item in danhsach_dv" :key="item.ID" :value="item.ID">{{item.NAME}}</option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w60">Mã GD</div>
          <div class="value">
            <input type="text" v-model="inputValue.ma_gd" class="form-control" />
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w140">Thông tin tra cứu</div>
          <div class="value">
            <input type="text" v-model="inputValue.tt_tracu" class="form-control" />
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w90">Mã HĐ-TTKD</div>
          <div class="value">
            <input type="text" v-model="inputValue.ma_hd_ttkd" class="form-control" />
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w110">
            <div class="check-action">
              <input type="checkbox" v-model="checkBoxs.ngay_yc" class="check" />
              <span class="name">Ngày YC</span>
            </div>
          </div>
          <div class="value">
            <div class="input-icon-right">
              <!-- <flat-pickr
                  v-model="ngay_yc"
                  :config="configDate"
                  v-if="checkBoxs.ngay_yc"
                  class="form-control"/>
              <input type="text" class="form-control" v-if="!checkBoxs.ngay_yc" :disabled="true" v-model="ngay_yc">
              <span class="icon one-calendar"></span>  -->
               <k-date-picker v-model="ngay_yc" :disabled="!checkBoxs.ngay_yc" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w60">Đến ngày</div>
          <div class="value">
            <div class="input-icon-right">
              <!-- <flat-pickr
                  v-model="ngay_den"
                  :config="configDate"
                  v-if="checkBoxs.ngay_yc"
                  class="form-control"/>
              <input type="text" class="form-control" v-if="!checkBoxs.ngay_yc" :disabled="true" v-model="ngay_den">
              <span class="icon one-calendar"></span>  -->
              <k-date-picker v-model="ngay_den" :disabled="!checkBoxs.ngay_yc" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w140">
            <div class="check-action">
              <input type="checkbox" v-model="checkBoxs.trangthai_hd" class="check" />
              <span class="name">Trạng thái HĐ/PL</span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom">
              <select v-model="trangthai_hd_selected" :disabled="!checkBoxs.trangthai_hd" class="form-control">
                <option v-for="item in danhsach_trangthai_hd" :key="item.ID" :value="item.ID">{{item.NAME}}</option>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import SearchContractAPI from './SearchContractAPI'
import moment from 'moment'
import EventBus from '@/utils/eventBus'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
export default {
    name:'ThongTinTimKiem',
    components:{
      KDatePicker
    },
    data(){
      return{
        loai_hd_selected:0,
        danhsach_loaihd:[],
        tieuchi_selected:'',
        danhsach_tieuchi:[],
        trangthai_hd_selected:0,
        danhsach_trangthai_hd:[],
        dichvu_selected:0,
        danhsach_dv:[],
        checkBoxs:{
          loai_hd:true,
          dichvu:false,
          ngay_yc:true,
          trangthai_hd:true
        },
        ngay_yc: moment(new Date()).format('DD/MM/YYYY'),
        ngay_den: moment(new Date()).format('DD/MM/YYYY'),

        inputValue:{
          ma_gd:'',
          tt_tracu:'',
          ma_hd_ttkd:''
        }

      }
    },
    methods:{
      async lay_loai_hd(){
        try{
          let response=await SearchContractAPI.lay_loai_hd(this.axios)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.danhsach_loaihd=response.data.data
            if(this.danhsach_loaihd.length>0){
              this.loai_hd_selected=this.danhsach_loaihd[0].ID
            }
          }else{
            this.danhsach_loaihd=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách loại hợp đồng')
        }
      },
      async lay_tieuchi_tracuu(){
        try{
          let response=await SearchContractAPI.lay_tieuchi_tracuu(this.axios)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.danhsach_tieuchi=response.data.data
            if(this.danhsach_tieuchi.length>0){
              this.tieuchi_selected=this.danhsach_tieuchi[0].ID
            }
          }else{
            this.danhsach_tieuchi=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách tiêu chí tra cứu')
        }
      },
      async lay_trangthai_hd(){
        try{
          let response=await SearchContractAPI.lay_trangthai_hd(this.axios)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.danhsach_trangthai_hd=response.data.data
            if(this.danhsach_trangthai_hd.length>0){
              this.trangthai_hd_selected=this.danhsach_trangthai_hd[0].ID
            }
          }else{
            this.danhsach_trangthai_hd=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách trạng thái hợp đồng')
        }
      },
      async lay_dichvu(){
        try{
          let response=await SearchContractAPI.lay_dichvu(this.axios)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.danhsach_dv=response.data.data
            if(this.danhsach_dv.length>0){
              this.dichvu_selected=this.danhsach_dv[0].ID
            }
          }else{
            this.danhsach_dv=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách trạng thái hợp đồng')
        }
      },
      layThongTinTimKiem(){
        var dieukien=0
        var loaihd_id=0
        var dichvuvt_id=0
        var tthd_id=0
        var tu_ngay=''
        var den_ngay=''
        if(this.inputValue.tt_tracu!=''){
          dieukien=this.tieuchi_selected!=null?this.tieuchi_selected.replace(':values',this.inputValue.tt_tracu):0
        }
        if(this.checkBoxs.loai_hd){
          loaihd_id=this.loai_hd_selected
        }
        if(this.checkBoxs.dichvu){
          dichvuvt_id=this.dichvu_selected
        }

        if(this.checkBoxs.trangthai_hd){
          tthd_id=this.trangthai_hd_selected
        }

        if(this.checkBoxs.ngay_yc){
          tu_ngay=moment(this.ngay_yc,'DD/MM/YYYY').format('DDMMYYYY')
          den_ngay=moment(this.ngay_den, 'DD/MM/YYYY').format('DDMMYYYY')
        }

        let dk_tracuu=this.danhsach_tieuchi.find(item=>item.ID==this.tieuchi_selected)
        if(dk_tracuu==undefined){
          return
        }
        if(dk_tracuu.NAME=='Số ảo'){
          return {
            id:'so_ao',
            data:{
              dichVuVTId: dichvuvt_id,
              inHD: 0,
              loaiHDId: loaihd_id,
              maLt: this.inputValue.tt_tracu.trim(),
              tthdId: tthd_id,
              tuNgay: tu_ngay,
              denNgay: den_ngay
            }
          }
        }else if(dk_tracuu.NAME=='Site ID'){
          return {
            id:'site_id',
            data:this.inputValue.tt_tracu.trim()
          }

        }else if(dk_tracuu.NAME=='Số hợp đồng KHDN'){
          return {
            id:'so_hd_khdn',
            data:this.inputValue.tt_tracu.trim()
          }
        }else if(dk_tracuu.NAME=='Số phụ lục KHDN'){
          return {
            id:'so_pl_khdn',
            data:this.inputValue.tt_tracu.trim()
          }
        }else if(dk_tracuu.NAME=='Tên miền'){
          return {
            id:'ten_mien',
            data:this.inputValue.tt_tracu.trim()
          }
        }else {
          return {
            id:'khac',
            data: {
              dichVuVTId: dichvuvt_id,
              inHD: 0,
              loaiHDId: loaihd_id,
              dieuKien: dieukien,
              tthdId: tthd_id,
              tuNgay: tu_ngay,
              denNgay: den_ngay
            }
          }
        }
      },
      receiveData(item){
        this.inputValue.ma_gd=Object.assign({},item).ma_gd
      }
    },
    
    mounted(){
      this.lay_loai_hd()
      this.lay_tieuchi_tracuu()
      this.lay_trangthai_hd()
      this.lay_dichvu()
    },
    created () {
      EventBus.$on('search_contract', this.receiveData)
    },
    destroyed () {
      EventBus.$off('search_contract', this.receiveData)
    },
}
</script>