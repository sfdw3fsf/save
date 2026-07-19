<template>
<div id="timkiemNV_CSKH" >
  <div class="list-actions-top">
    <ul class="list">
      <li>
        <a href="#" @click.prevent="timkiem"> <span class="icon one-search"></span>Tìm kiếm </a>
      </li>
      <li>
        <a href="#" @click.prevent="chapnhan"> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận </a>
      </li>
    </ul>
  </div>
  <div class="page-content">
    <div class="box-form">
      <div class="legend-title">Thông tin địa danh</div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="searchInfo.checkQuan" v-on:change="thayDoiCheck(1)">
                <span class="name">Quận/Huyện</span>
              </div>
            </div>
            <div class="value">
              <SelectExt v-model="popup.quan_id" dataTextField="TEN_QUAN" dataValueField="QUAN_ID"
                :dataSource="dsQuan.list" :disabled="!searchInfo.checkQuan"  />
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w130 nowrap">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="searchInfo.checkPhuong" v-on:change="thayDoiCheck(2)">
                <span class="name">Phường/Xã</span>
              </div>
            </div>
            <div class="value">
              <SelectExt v-model="popup.phuong_id" dataTextField="TEN_PHUONG" dataValueField="PHUONG_ID" 
              :dataSource="dsPhuong.list"  :disabled="!searchInfo.checkPhuong" />
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="searchInfo.checkPho" v-on:change="thayDoiCheck(3)">
                <span class="name">Phố</span>
              </div>
            </div>
            <div class="value">
              <SelectExt v-model="popup.pho_id" dataTextField="TEN_PHO" dataValueField="PHO_ID"
                :dataSource="dsPho.list" :disabled="!searchInfo.checkPho" />
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w130 nowrap">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="searchInfo.checkAp" v-on:change="thayDoiCheck(4)">
                <span class="name">Ấp</span>
              </div>
            </div>
            <div class="value">
              <SelectExt v-model="popup.ap_id" dataTextField="TEN_PHO" dataValueField="PHO_ID" 
              :dataSource="dsAp.list" :disabled="!searchInfo.checkAp" />
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="searchInfo.checkKhu" v-on:change="thayDoiCheck(5)">
                <span class="name">Khu</span>
              </div>
            </div>
            <div class="value">
              <SelectExt v-model="popup.khu_id" dataTextField="TEN_PHO" dataValueField="PHO_ID"
                :dataSource="dsKhu.list" :disabled="!searchInfo.checkKhu" />
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w130 nowrap">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="searchInfo.checkDacDiem" v-on:change="thayDoiCheck(6)">
                <span class="name">Đặc điểm</span>
              </div>
            </div>
            <div class="value">
              <SelectExt v-model="popup.dacdiem_id" dataTextField="DACDIEM" dataValueField="DACDIEM_ID" 
              :dataSource="dsDacdiem.list" :disabled="!searchInfo.checkDacDiem" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="box-form">
      <div class="legend-title">Danh sách nhân viên chăm sóc</div>
      <DataGrid :columns="columns_popupNV" 
                :showFiltering="true" 
                :enable-paging-server="false" 
                :allowPaging="true" 
                :dataSource="GridDsNhanVienCSKH" 
                :showColumnCheckbox="false"
                :enabledSelectFirstRow="false"
                @selectedItemsChanged="getSelectedItem"/>
    </div>
  </div>
</div>
</template>

<style>
</style>

<script>
import api from '../API.js'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
export default {
  name:'timkiemNV_CSKH',
  components:{
    ActionTop,
    KDatePicker,
    KRequiredMarker
  },
  props: ["dulieu"],
  data(){
    return{
      nhanvien_b2a_id:'',
      diachi_b2a:'',
      diachi_index:'',
      format:{
        huyen:'',
        phuong:'',
        apkhupho:'',
        dacdiem:''
      },
      ckeck_moivao:true,
      popup:{
        quan_id:null,
        phuong_id: null,
        pho_id: null,
        ap_id: null,
        khu_id: null,
        dac_diem_id: null
      },
      searchInfo:{
        checkQuan: false,
        checkPhuong: false,
        checkPho: false,
        checkAp: false,
        checkKhu: false,
        checkDacDiem: false
      },
      phanvung_id:'',
      dsTinh:{
        list:[],
      },
      dsQuan:{
        list:[],
      },
      dsPhuong:{
        list:[],
      },
      dsPho:{
        list:[],
      },
      dsAp:{
        list:[],
      },
      dsKhu:{
        list:[],
      },
      dsDacdiem:{
        list:[],
      },
      ten_nv_selected:'',
      selected_list:'',
      columns_popupNV: [
        {fieldName: 'ma_nv', headerText: 'Mã nhân viên', allowfiltering: true},
        {fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowfiltering: true},
        {fieldName: 'so_dt', headerText: 'Điện thoại', allowfiltering: true},
        {fieldName: 'ma_dv', headerText: 'Mã đơn vị', allowfiltering: true},
        {fieldName: 'ten_dv', headerText: 'Tên đơn vị', allowfiltering: true},
      ],
      GridDsNhanVienCSKH:[],
    }
  },
  async mounted () {
    this.loading(true)
    this.phanvung_id =  this.dulieu.phanvung_id
    this.diachi_index = this.dulieu.diachi
    await this.getTinh()
    await this.getQuan()
    await this.initDulieu()
    this.loading(false)
    // this.timkiem()

  },
  methods:{
    thayDoiCheck(num){
      if (num == 1) this.checkQuan = !this.checkQuan;
      if (num == 2) this.checkPhuong = !this.checkPhuong;
      if (num == 3) this.checkPho = !this.checkPho;
      if (num == 4) this.checkAp = !this.checkAp;
      if (num == 5) this.checkKhu = !this.checkKhu;
      if (num == 6) this.checkDacDiem = !this.checkDacDiem;
    },
    getSelectedItem: function (data) {
        this.selected_list = data
        console.log('hàng NV được chọn',this.selected_list)
        let ma_nv = data[0].ma_nv
        let ten_nv = data[0].ten_nv
        this.ten_nv_selected = ma_nv + '-'+ ten_nv
        this.nhanvien_b2a_id = data[0].nhanvien_id
    },
    timkiem(){
      this.GridDsNhanVienCSKH=[]
      this.layDanhSach_NV_CSKH()
    },
    chapnhan(){
      console.log('this.ten_nv_selected',this.ten_nv_selected)
      if (this.ten_nv_selected === ''){
        this.$root.$toast.error('Chọn nhân viên B2A')
        return
      }
      if (this.searchInfo.checkQuan){
        const selectedQuan = this.dsQuan.list.find(quan => quan.QUAN_ID == this.popup.quan_id);
        this.diachi_b2a = selectedQuan.TEN_QUAN
        if (this.searchInfo.checkPhuong){
        const selectedPhuong = this.dsPhuong.list.find(phuong => phuong.PHUONG_ID == this.popup.phuong_id);
        this.diachi_b2a = selectedPhuong.TEN_PHUONG +','+this.diachi_b2a
          if (this.searchInfo.checkPho){
          const selectedPho = this.dsPho.list.find(pho => pho.PHO_ID == this.popup.pho_id);
          this.diachi_b2a = selectedPho.TEN_PHO +','+this.diachi_b2a
            
          }
          if (this.searchInfo.checkAp){
          const selectedPho = this.dsAp.list.find(pho => pho.PHO_ID == this.popup.ap_id);
          this.diachi_b2a = selectedPho.TEN_PHO +','+this.diachi_b2a
          }
          if (this.searchInfo.checkKhu){
          const selectedPho = this.dsKhu.list.find(pho => pho.PHO_ID == this.popup.khu_id);
          this.diachi_b2a = selectedPho.TEN_PHO +','+this.diachi_b2a
          }
          if (this.searchInfo.checkDacDiem){
          const selectedDacDiem = this.dsDacdiem.list.find(dacdiem => dacdiem.DACDIEM_ID == this.popup.dacdiem_id);
          this.diachi_b2a = selectedDacDiem.DACDIEM +','+this.diachi_b2a
          }

        }
      }

      this.$emit('form-close', this.ten_nv_selected,this.nhanvien_b2a_id,this.diachi_b2a);

    },
    async initDulieu(){
      console.log('Dữ liệu popup nhận được:',this.dulieu)
      // this.phanvung_id =  this.dulieu.phanvung_id
      // this.diachi_index = this.dulieu.diachi
      //Index địa chỉ khách hàng lên popup
      let formats = this.diachi_index.split(',').map(format => format.trim());
      console.log(formats);

      this.format.huyen = formats[formats.length - 2]
      let quan = this.dsQuan.list.find(q => q.TEN_QUAN === this.format.huyen);
      if (quan) {
        this.popup.quan_id=quan.QUAN_ID
        this.searchInfo.checkQuan=true
      }
      

    },
    GetDataList: function (response) {
      console.log(response)
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    getTinh: async function () {
      let data = this.GetDataList(await api.lay_thongtin_tinh(this.axios))
      this.dsTinh.list = data
    },
    getQuan: async function () {
      this.dsQuan.list = this.GetDataList(await api.lay_ds_quan_huyen(this.axios, this.phanvung_id))

    },
    getDacDiem: async function () {
      let params = {
        phuong_id: this.popup.phuong_id == null ? 0 : this.popup.phuong_id,
        pho_id: this.popup.pho_id == null ? 0 : this.popup.pho_id,
        ap_id: this.popup.ap_id == null ? 0 : this.popup.ap_id,
        khu_id: this.popup.khu_id == null ? 0 : this.popup.khu_id
      }
      this.dsDacdiem.list = this.GetDataList(await api.lay_ds_dacdiem(this.axios, params))
      if (this.ckeck_moivao)
      {
        this.layDanhSach_NV_CSKH()
        this.ckeck_moivao=false
      }

    },
    async updatePhoApKhu() {
      if(this.popup.phuong_id==null){
        this.popup.phuong_id=0
      }
      let response = this.GetDataList(await api.lay_ds_pho_ap_khu(this.axios, this.popup.phuong_id));
      this.dsPho.list = response.dsPho;
      this.dsAp.list = response.dsAp;
      this.dsKhu.list = response.dsKhu;
    },
    async layDanhSach_NV_CSKH(){
      console.log('Input',this.popup.quan_id,this.popup.phuong_id,this.popup.pho_id,this.popup.ap_id,this.popup.khu_id,this.popup.dacdiem_id)
      try{
        this.loading(true)
        if(!this.searchInfo.checkQuan){
          this.popup.quan_id = null
        }
        if(!this.searchInfo.checkPhuong){
          this.popup.phuong_id = null
        }
        if(!this.searchInfo.checkPho){
          this.popup.pho_id = null 
        }
        if(!this.searchInfo.checkAp){
          this.popup.ap_id = null
        }
        if(!this.searchInfo.checkKhu){
          this.popup.khu_id = null
        }


        const Input = {
          p_quan_id: this.popup.quan_id == null ? 0 : parseInt(this.popup.quan_id),
          p_phuong_id: this.popup.phuong_id == null ? 0 : parseInt(this.popup.phuong_id),
          p_pho_id: this.popup.pho_id == null ? 0 : parseInt(this.popup.pho_id),
          p_vap_id: this.popup.ap_id == null ? 0 : parseInt(this.popup.ap_id),
          p_khu_id: this.popup.khu_id == null ? 0 : parseInt(this.popup.khu_id),
          p_dacdiem_id: this.popup.dacdiem_id == null ? 0 : parseInt(this.popup.dacdiem_id)
        }
        const response = await api.layDanhSach_NV_CSKH(this.axios, Input);
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          console.log('DsNhanVienCSKH',response.data.data)
          this.GridDsNhanVienCSKH = response.data.data
        }
        //  else {
        //   this.$toast.error(response.data.message)
        // }
      }catch (error){
        this.$toast.error('Xảy ra lỗi khi lấy danh sách NV')
      }finally{
        this.loading(false);
      }
    },
    

  },
  watch: {
    'popup.quan_id': {
      handler: async function(val, oldVal) {
        if (val !== oldVal) {
          this.timkiem()
          this.searchInfo.checkPhuong=false
          this.dsPhuong.list = this.GetDataList(await api.lay_ds_phuong_xa(this.axios, this.popup.quan_id));
          this.popup.phuong_id=null
          let formats = this.diachi_index.split(',').map(format => format.trim());
          this.format.phuong = formats[formats.length - 3]
          console.log('xã ds:',this.dsPhuong.list)
          let phuong = this.dsPhuong.list.find(q => q.TEN_PHUONG === this.format.phuong);
          if (phuong) {
            this.popup.phuong_id=phuong.PHUONG_ID
            this.searchInfo.checkPhuong=true
          }
          this.popup.ap_id=null
          this.popup.pho_id=null
          this.popup.khu_id=null
          this.popup.dacdiem_id=null
        }
      }
    },
    'popup.phuong_id': {
      handler: async function(val, oldVal) {
        if (val !== oldVal) {
          this.timkiem()
          await this.updatePhoApKhu();
          this.searchInfo.checkAp=false
          this.searchInfo.checkKhu=false
          this.searchInfo.checkPho=false
          let formats = this.diachi_index.split(',').map(format => format.trim());
          this.format.apkhupho = formats[formats.length - 4]

          let ap = this.dsAp.list.find(q => q.TEN_PHO === this.format.apkhupho);
          let khu = this.dsKhu.list.find(q => q.TEN_PHO === this.format.apkhupho);
          let pho = this.dsPho.list.find(q => q.TEN_PHO === this.format.apkhupho);
          if (ap) {
            this.popup.ap_id=ap.PHO_ID
            this.searchInfo.checkAp=true

          }else if(khu) {
            this.popup.khu_id=khu.PHO_ID
            this.searchInfo.checkKhu=true
          }else if(pho) {
            this.popup.pho_id=pho.PHO_ID
            this.searchInfo.checkPho=true
          }
        }
      }
    },
    'popup.pho_id': {
      handler: async function(val, oldVal) {
        if (val !== oldVal) {
          this.timkiem()
          await this.getDacDiem();
        }
      }
    },
    'popup.khu_id': {
      handler: async function(val, oldVal) {
        if (val !== oldVal) {
          this.timkiem()
          await this.getDacDiem();
        }
      }
    },
    'popup.ap_id': {
      handler: async function(val, oldVal) {
        if (val !== oldVal) {
          this.timkiem()
          await this.getDacDiem();
        }
      }
    }
  },
}
</script>