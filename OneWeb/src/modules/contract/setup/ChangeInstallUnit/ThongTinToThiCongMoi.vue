<template>
  <div class="box-col box-form" id="boxRight">
    <div class="legend-title">
      <div class="pull-left">Thông tin tổ thi công mới</div>
      <div class="pull-right normal red">
        <span class="one-alert text-warning f20 inline vcenter"></span> Click
        chọn ô check box nếu muốn thay đổi thông tin
      </div>
      <div class="clearfix"></div>
    </div>
    <div class="info-row">
      <div class="key w100">Kiểu đơn vị</div>
      <div class="value">
        <div class="select-custom">
          <select name="" id="" class="form-control" :disabled="kieu_dv_disable" v-model="kieu_dv_selected">
            <option v-for="item in danhsach_kieu_dv" :key="item.ID" :value="item.ID">{{item.NAME}}</option>
          </select>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w100">
        <div class="check-action">
          <input type="checkbox" class="check" v-model="enableCheckBoxs.diachiLD" />
          <span class="name">Địa chỉ LĐ</span>
        </div>
      </div>
      <div class="value">
        <div class="input-more-button">
          <button class="btn" @click="chonDiaChiLd" :disabled="!enableCheckBoxs.diachiLD">
            <span class="-ap icon-more_horiz"></span>
          </button>
          <input type="text" v-model="hopdong_theo_hdkhid_v2.diachi_ld" class="form-control" :disabled="!enableCheckBoxs.diachiLD"/>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w100 nowrap red">
        <div class="check-action">
          <input type="checkbox" class="check" v-model="enableCheckBoxs.thayDoiToThiCong"/>
          <span class="name fw6">Thay đổi tổ thi công</span>
        </div>
      </div>
      <div class="value">
        <button class="btn btn-main" @click="layDanhSachTo" :disabled="!enableCheckBoxs.thayDoiToThiCong">Lấy danh sách tổ</button>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">Quận</div>
          <div class="value w20 padt7">
            <div class="check-action">
              <input type="checkbox" class="check" v-model="enableCheckBoxs.quan" :disabled="!enableCheckBoxs.thayDoiToThiCong"/>
              <span class="name"></span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom" ref="ds_quan">
                <select2 :settings="{ dropdownParent: $refs['ds_quan'] }" ref="cboQuan"
                    v-model="dulieu_diadanh.quan_selected" class="select2"
                    :options="dulieu_diadanh.danhsach_quan.map(e=> ({id: e.QUAN_ID, text: e.TEN_QUAN}))"
                    @change="changeQuan"
                    :disabled="!enableCheckBoxs.quan||!enableCheckBoxs.thayDoiToThiCong"
                    >
                </select2>
            </div>
          </div>
        </div>
      </div>

      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w70">Phường</div>
          <div class="value w20 padt7">
            <div class="check-action">
              <input type="checkbox" class="check" v-model="enableCheckBoxs.phuong" :disabled="!enableCheckBoxs.thayDoiToThiCong"/>
              <span class="name"></span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom" ref="ds_phuong">
                <select2 :settings="{ dropdownParent: $refs['ds_phuong'] }" ref="cboPhuong"
                    v-model="dulieu_diadanh.phuong_selected" class="select2"
                    :options="dulieu_diadanh.danhsach_phuong.map(e=> ({id: e.PHUONG_ID, text: e.TEN_PHUONG}))"
                    @change="changePhuong"
                    :disabled="!enableCheckBoxs.phuong||!enableCheckBoxs.thayDoiToThiCong"
                    >
                </select2>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">Phố</div>
          <div class="value w20 padt7">
            <div class="check-action">
              <input type="checkbox" class="check" v-model="enableCheckBoxs.pho" :disabled="!enableCheckBoxs.thayDoiToThiCong"/>
              <span class="name"></span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom" ref="ds_pho">
                <select2 :settings="{ dropdownParent: $refs['ds_pho'] }" ref="cboPho"
                    v-model="dulieu_diadanh.pho_selected" class="select2"
                    :options="dulieu_diadanh.danhsach_pho.map(e=> ({id: e.PHO_ID, text: e.TEN_PHO}))"
                    @change="changePho"
                    :disabled="!enableCheckBoxs.pho||!enableCheckBoxs.thayDoiToThiCong"
                    >
                </select2>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w70">Ấp</div>
          <div class="value w20 padt7">
            <div class="check-action">
              <input type="checkbox" class="check" v-model="enableCheckBoxs.ap" :disabled="!enableCheckBoxs.thayDoiToThiCong"/>
              <span class="name"></span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom" ref="ds_ap">
                <select2 :settings="{ dropdownParent: $refs['ds_ap'] }" ref="cboAP"
                    v-model="dulieu_diadanh.ap_selected" class="select2"
                    :options="dulieu_diadanh.danhsach_ap.map(e=> ({id: e.PHO_ID, text: e.TEN_PHO}))"
                    @change="changeAp"
                    :disabled="!enableCheckBoxs.ap||!enableCheckBoxs.thayDoiToThiCong"
                    >
                </select2>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">Khu</div>
          <div class="value w20 padt7">
            <div class="check-action">
              <input type="checkbox" class="check" v-model="enableCheckBoxs.khu" :disabled="!enableCheckBoxs.thayDoiToThiCong"/>
              <span class="name"></span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom" ref="ds_khu">
                <select2 :settings="{ dropdownParent: $refs['ds_khu'] }" ref="cboKhu"
                    v-model="dulieu_diadanh.khu_selected" class="select2"
                    :options="dulieu_diadanh.danhsach_khu.map(e=> ({id: e.PHO_ID, text: e.TEN_PHO}))"
                    @change="changeKhu"
                    :disabled="!enableCheckBoxs.khu||!enableCheckBoxs.thayDoiToThiCong"
                    >
                </select2>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w70">Đặc điểm</div>
          <div class="value w20 padt7">
            <div class="check-action">
              <input type="checkbox" class="check" v-model="enableCheckBoxs.dacdiem" :disabled="!enableCheckBoxs.thayDoiToThiCong"/>
              <span class="name"></span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom" ref="ds_khu">
                <select2 :settings="{ dropdownParent: $refs['ds_khu'] }" ref="cboKhu"
                    v-model="dulieu_diadanh.dacdiem_selected" class="select2"
                    :options="dulieu_diadanh.danhsach_dacdiem.map(e=> ({id: e.dacdiem_id, text: e.dacdiem}))"
                    :disabled="!enableCheckBoxs.dacdiem||!enableCheckBoxs.thayDoiToThiCong"
                    >
                </select2>
            </div>
          </div>
        </div>
      </div>
    </div>
    <KTableV3 :columns="columns"
      @onSelectedRow="onSelectedRow"
      :dataSources="dataSources"
      :rowSelectedId="rowSelectedId"
      id="id"
      :allowFilter="true" />
      <!-- Modal -->
    <DiaChiKhachHangModal  :data="dataChonDiaChi" ref="chonDiaChi" @accept="acceptDiaChiLD"/>
  </div>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ChangeInstallUnitAPI from './ChangeInstallUnitAPI'
import EventBus from '@/utils/eventBus'
import ChonDiaChi from './popup/ChonDiaChi.vue'
import DiaChiKhachHangModal from '../SurveyRequest/popups/ChonDiaChi/DiaChiKhachHangModal.vue'
export default {
  name: "ThongTinToThiCongMoi",
  components:{
      KTableV3,
      ChonDiaChi,
      DiaChiKhachHangModal
  },
  data(){
      return{
          columns:[
              {
                  field: "ten_dv",
                  label: "Tổ thi công",
                  allowFilter: true
              },
              {
                  field: "ten_dvql",
                  label: "Đơn vị QL",
                  allowFilter: true
              }
          ],
          dataSources:[],
          enableCheckBoxs:{
              diachiLD: false,
              thayDoiToThiCong:false,
              quan:false,
              phuong:false,
              pho:false,
              ap:false,
              khu:false,
              dacdiem:false
          },
          dulieu_diadanh:{
              quan_selected:0,
              danhsach_quan:[],
              phuong_selected:0,
              danhsach_phuong:[],
              pho_selected:0,
              danhsach_pho:[],
              ap_selected:0,
              danhsach_ap:[],
              khu_selected:0,
              danhsach_khu:[],
              dacdiem_selected:0,
              danhsach_dacdiem:[] 
          },
          danhsach_kieu_dv:[],
          kieu_dv_selected:null,
          kieu_dv_disable:true,
          diachild:{},
          hopdong_theo_mathuebao:{},
          hopdong_theo_hdkhid_v2:{},
          dataChonDiaChi:{
            diachi_id:0,
            tinh_id:0,
            quan_id:0,
            phuong_id:0,
            pho_id:0,
            ap_id:0,
            khu_id:0,
            dacdiem_id:0,
            sonha:'',
            diachi:''
          },
          toThiCongSlected:null,
          rowSelectedId:-1
      }
  },
  methods:{
      async lay_danhsach_kieudonvi(){
        try{
            let response=await ChangeInstallUnitAPI.lay_danhsach_kieudonvi(this.axios)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.danhsach_kieu_dv=response.data.data
                if(this.danhsach_kieu_dv.length>0){
                  this.kieu_dv_selected=this.danhsach_kieu_dv[0].ID
                }
            }else{
                this.danhsach_kieu_dv=[]
            }
        }catch(e){
              this.$toast.error('Không load được danh sách kiểu đơn vị')
        }
      },
      async lay_danhsach_quan(){
        try{
            let response=await ChangeInstallUnitAPI.lay_danhsach_quan(this.axios)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.dulieu_diadanh.danhsach_quan=response.data.data
            }else{
                this.dulieu_diadanh.danhsach_quan=[]
            }
        }catch(e){
              this.$toast.error('Không load được danh sách quận')
        }
      },
      async lay_danhsach_phuong(quan_id){
        try{
            let response=await ChangeInstallUnitAPI.lay_danhsach_phuong(this.axios, quan_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.dulieu_diadanh.danhsach_phuong=response.data.data

                if(this.dulieu_diadanh.phuong_selected!=0){
                  this.dulieu_diadanh.danhsach_pho=[]
                  this.dulieu_diadanh.danhsach_ap=[]
                  this.dulieu_diadanh.danhsach_khu=[]
                  this.lay_danhsach_pho(this.dulieu_diadanh.phuong_selected)
                  this.lay_danhsach_ap(this.dulieu_diadanh.phuong_selected)
                  this.lay_danhsach_khu(this.dulieu_diadanh.phuong_selected)
                }
                

            }else{
                this.dulieu_diadanh.danhsach_phuong=[]
            }
        }catch(e){
              this.$toast.error('Không load được danh sách phường')
        }
      },
      async lay_danhsach_pho(phuong_id){
        try{
            let response=await ChangeInstallUnitAPI.lay_danhsach_pho(this.axios, phuong_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.dulieu_diadanh.danhsach_pho=response.data.data

                if(this.dulieu_diadanh.pho_selected!=0){
                  this.lay_danhsach_dacdiem(this.dulieu_diadanh.pho_selected, this.dulieu_diadanh.phuong_selected)
                }
            }else{
                this.dulieu_diadanh.danhsach_pho=[]
            }
        }catch(e){
              this.$toast.error('Không load được danh sách phố')
        }
      },
      async lay_danhsach_ap(phuong_id){
        try{
            let response=await ChangeInstallUnitAPI.lay_danhsach_ap(this.axios, phuong_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.dulieu_diadanh.danhsach_ap=response.data.data
                if(this.dulieu_diadanh.ap_selected!=0){
                  this.lay_danhsach_dacdiem(this.dulieu_diadanh.ap_selected, this.dulieu_diadanh.phuong_selected)
                }
            }else{
                this.dulieu_diadanh.danhsach_ap=[]
            }
        }catch(e){
              this.$toast.error('Không load được danh sách ấp')
        }
      },
      async lay_danhsach_khu(phuong_id){
        try{
            let response=await ChangeInstallUnitAPI.lay_danhsach_khu(this.axios, phuong_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.dulieu_diadanh.danhsach_khu=response.data.data
                if(this.dulieu_diadanh.khu_selected!=0){
                  this.lay_danhsach_dacdiem(this.dulieu_diadanh.khu_selected, this.dulieu_diadanh.phuong_selected)
                }
            }else{
                this.dulieu_diadanh.danhsach_khu=[]
            }
        }catch(e){
              this.$toast.error('Không load được danh sách khu')
        }
      },
      
      async lay_danhsach_dacdiem(pho_id, phuong_id){
        try{
            let response=await ChangeInstallUnitAPI.lay_danhsach_dacdiem(this.axios, pho_id, phuong_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.dulieu_diadanh.danhsach_dacdiem=response.data.data
            }else{
                this.dulieu_diadanh.danhsach_dacdiem=[]
            }
        }catch(e){
              this.$toast.error('Không load được danh sách đặc điểm')
        }
      },

      // "diaChi": "6/20/17 - Đội Văn - Trại Chuối - Quận Hồng Bàng - Hải Phòng",
      // "diaChiId": 18045342,
      // "tinhId": 26,
      // "quanId": 1,
      // "maQuan": "HBG",
      // "maQuanLd": "HBG",
      // "phuongId": 444,
      // "phoId": 5505,
      // "apId": 0,
      // "khuId": 0,
      // "soNha": "6/20/17",
      // "dacDiemId": 0,
      // "diaChiLdId": 18045343,
      // "tinhLdId": 26,
      // "quanLdId": 1,
      // "phuongLdId": 444,
      // "phoLdId": 5505,
      // "apLdId": 0,
      // "khuLdId": 0,
      // "soNhaLd": "6/20/17",
      // "dacDiemLdId": 0,
      // "kinhDo": 0.0,
      // "viDo": 0.0,
      // "kinhDoLd": 0.0,
      // "viDoLd": 0.0
      async lay_ds_diachi_theo_hdtbid(hdtbId){
        try{
            let response=await ChangeInstallUnitAPI.lay_ds_diachi_theo_hdtbid(this.axios, hdtbId)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                //this.dulieu_diadanh.danhsach_dacdiem=response.data.data
                if(response.data.data.length>0){
                  this.diachild=response.data.data[0]
                  console.log('diachild: ', this.diachild)
                  if(this.diachild.quanld_id!=''){
                    this.dulieu_diadanh.quan_selected=this.diachild.quanld_id

                  }else{
                    this.dulieu_diadanh.quan_selected=0
                  }

                  if(this.diachild.phuongld_id!=''){
                    this.dulieu_diadanh.phuong_selected=this.diachild.phuongld_id
                  }else{
                    this.dulieu_diadanh.phuong_selected=0
                  }

                  this.lay_danhsach_phuong(this.dulieu_diadanh.quan_selected)

                  if(this.diachild.phold_id!=''){
                    this.dulieu_diadanh.pho_selected=this.diachild.phold_id
                  }else{
                    this.dulieu_diadanh.pho_selected=0
                  }

                  if(this.diachild.apld_id!=''){
                    this.dulieu_diadanh.ap_selected=this.diachild.apld_id
                  }else{
                    this.dulieu_diadanh.ap_selected=0
                  }

                  if(this.diachild.khuld_id!=''){
                    this.dulieu_diadanh.khu_selected=this.diachild.khuld_id
                  }else{
                    this.dulieu_diadanh.khu_selected=0
                  }

                  if(this.diachild.dacdiemld_id!=''){
                    this.dulieu_diadanh.dacdiem_selected=this.diachild.dacdiemld_id
                  }else{
                    this.dulieu_diadanh.dacdiem_selected=0
                  }

                }else{
                  this.diachild={}
                }
            }else{
              this.diachild={}
                
            }
        }catch(e){
              this.$toast.error('Không danh sách địa chỉ')
        }
      },
      async lay_ds_donvi_theo_loaidv_diachild(loaidvId, phuongId, phoId, apId, khuId){
        try{
          this.loading(true)
          this.dataSources=[]
          let response=await ChangeInstallUnitAPI.lay_ds_donvi_theo_loaidv_diachild(this.axios, loaidvId, phuongId, phoId, apId, khuId)
          this.loading(false)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.dataSources=response.data.data.map((x, index)=>Object.assign(x,{id:index+1}))
              if(this.dataSources.length>0){
                this.rowSelectedId=this.dataSources[0].id
                this.onSelectedRow(this.dataSources[0])
              }
          }else{
              this.dataSources=[]
              this.$toast.error('Không tìm thấy danh sách trạm thi công')
          }
        }catch(e){
          this.loading(false)
          this.$toast.error('Đã xảy ra lỗi. Không thể lấy danh sách trạm thi công')
        }
      },
      layDanhSachTo(){
        if(!this.enableCheckBoxs.quan||this.dulieu_diadanh.quan_selected==0){
          this.$toast.error('Hãy chọn Quận!')
          return
        }
        if(!this.enableCheckBoxs.phuong||this.dulieu_diadanh.phuong_selected==0){
          this.$toast.error('Hãy chọn Phường!')
          return
        }
        //Trạm thực hiện thi công
        const TRAM_VT = 5
        this.lay_ds_donvi_theo_loaidv_diachild(TRAM_VT, this.dulieu_diadanh.phuong_selected, this.dulieu_diadanh.pho_selected, this.dulieu_diadanh.ap_selected, this.dulieu_diadanh.khu_selected)
      },
      chonDiaChiLd(){
        if(this.hopdong_theo_hdkhid_v2.hdtb_id){
          // diachi_id:0,
          //     tinh_id:0,
          //     quan_id:0,
          //     phuong_id:0,
          //     pho_id:0,
          //     ap_id:0,
          //     khu_id:0,
          //     dacdiem_id:0,
          //     sonha:'',
          //     diachi:''
          this.dataChonDiaChi={
            diachi_id:this.diachild.diachild_id,
            tinh_id:this.diachild.tinhld_id,
            quan_id:this.diachild.quanld_id,
            phuong_id:this.diachild.phuongld_id,
            pho_id:this.diachild.phold_id,
            ap_id:this.diachild.apld_id,
            khu_id:this.diachild.khuld_id,
            dacdiem_id:this.diachild.dacdiemld_id,
            sonha:this.diachild.sonha_ld,
            diachi:this.hopdong_theo_hdkhid_v2.diachi_ld
          }
          this.$refs.chonDiaChi.showModal()
        }else{
          this.$toast.error('Chưa có thuê bao để sửa !')       
        }
      },
      changePhuong(){
        this.dulieu_diadanh.pho_selected=0
        this.dulieu_diadanh.ap_selected=0
        this.dulieu_diadanh.khu_selected=0
        this.dulieu_diadanh.danhsach_pho=[]
        this.dulieu_diadanh.danhsach_ap=[]
        this.dulieu_diadanh.danhsach_khu=[]
        this.lay_danhsach_pho(this.dulieu_diadanh.phuong_selected)
        this.lay_danhsach_ap(this.dulieu_diadanh.phuong_selected)
        this.lay_danhsach_khu(this.dulieu_diadanh.phuong_selected)
      },
      changeQuan(){
        this.dulieu_diadanh.phuong_selected=0
        this.dulieu_diadanh.pho_selected=0
        this.dulieu_diadanh.ap_selected=0
        this.dulieu_diadanh.khu_selected=0
        this.dulieu_diadanh.danhsach_phuong=[]
        this.dulieu_diadanh.danhsach_pho=[]
        this.dulieu_diadanh.danhsach_ap=[]
        this.dulieu_diadanh.danhsach_khu=[]
        this.lay_danhsach_phuong(this.dulieu_diadanh.quan_selected)
      },
      changePho(){
        this.dulieu_diadanh.dacdiem_selected=0
        this.dulieu_diadanh.danhsach_dacdiem=[]
        this.lay_danhsach_dacdiem(this.dulieu_diadanh.pho_selected, this.dulieu_diadanh.phuong_selected)
      },
      changeAp(){
        this.dulieu_diadanh.dacdiem_selected=0
        this.dulieu_diadanh.danhsach_dacdiem=[]
        this.lay_danhsach_dacdiem(this.dulieu_diadanh.ap_selected, this.dulieu_diadanh.phuong_selected)
      },
      changeKhu(){
        this.dulieu_diadanh.dacdiem_selected=0
        this.dulieu_diadanh.danhsach_dacdiem=[]
        this.lay_danhsach_dacdiem(this.dulieu_diadanh.khu_selected, this.dulieu_diadanh.phuong_selected)
      },
      receiveData(data){
          this.hopdong_theo_mathuebao=data.hopdong_theo_mathuebao
          this.hopdong_theo_hdkhid_v2=data.hopdong_theo_hdkhid_v2
          this.quan_selected=0
          this.phuong_selected=0
          this.pho_selected=0
          this.ap_selected=0
          this.khu_selected=0
          this.dacdiem_selected=0
          this.danhsach_phuong=[]
          this.danhsach_pho=[]
          this.danhsach_ap=[]
          this.danhsach_khu=[]
          this.danhsach_dacdiem=[]
          this.enableCheckBoxs={
                diachiLD: false,
                thayDoiToThiCong:false,
                quan:false,
                phuong:false,
                pho:false,
                ap:false,
                khu:false,
                dacdiem:false
          },
          this.dataSources=[]
          this.diachild={

          }
          this.lay_ds_diachi_theo_hdtbid(this.hopdong_theo_hdkhid_v2.hdtb_id)
          //4614636
          //this.lay_ds_diachi_theo_hdtbid(4614636)
          //enable checkbox kiểu
          const TSL = 9
          if(this.hopdong_theo_hdkhid_v2.dichvuvt_id&&this.hopdong_theo_hdkhid_v2.dichvuvt_id==TSL){
            this.kieu_dv_disable=false
          }else{
            this.kieu_dv_disable=true
          }
      },
      acceptDiaChiLD(data){
        this.diachild.diachild_id=data.diachi_id!=undefined?data.diachi_id:0
        this.diachild.tinhld_id=data.tinh_id
        this.diachild.quanld_id=data.quan_id
        this.diachild.phuongld_id=data.phuong_id
        this.diachild.phold_id=data.pho_id
        this.diachild.apld_id=data.ap_id
        this.diachild.khuld_id=data.khu_id
        this.diachild.dacdiemld_id=data.dacdiem_id
        this.diachild.sonha_ld=data.sonha
        this.hopdong_theo_hdkhid_v2.diachi_ld=data.diachi

        console.log('acceptDiaChiLD',this.diachild)
      },
      onSelectedRow(item){
        this.toThiCongSlected=item
      },
      async capnhat_tramvattu_lapdat_v2(data){
        try{
            this.loading(true)
            let response=await ChangeInstallUnitAPI.capnhat_tramvattu_lapdat_v2(this.axios, data)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                this.$toast.success('Cập nhật dữ liệu thành công!')
                this.$emit('success_update')
            }else {
              if(response && response.data &&response.data.message){
                  //"message": "Thuê bao đã hoàn thành không được thay đổi thông tin",
                  this.$toast.error(response.data.message)
              }else{
                  this.$toast.error('Cập nhật dữ liệu không thành công!')
              }
            }
        }catch(e){
              if(e.data&&e.data.message){
                  this.$toast.error(e.data.message)
              }else if(e.response&&e.response.data&&e.response.data.message){
                  this.$toast.error(e.response.data.message)
              }else{
                  this.$toast.error('Đã xảy ra lỗi!')
              }
        }finally{
          this.loading(false)
        }
      },
      CapNhat(){
        if(!this.hopdong_theo_hdkhid_v2.hdtb_id){
          this.$toast.error('Chưa có thuê bao để sửa !')
          return
        }
        if(!this.enableCheckBoxs.diachiLD&&!this.enableCheckBoxs.thayDoiToThiCong){
          this.$toast.error('Hãy chọn sửa đổi thông tin lắp đặt hoặc tổ thi công')
          return
        }
        if(this.enableCheckBoxs.diachiLD){
          if(!this.hopdong_theo_hdkhid_v2.diachi_ld||this.hopdong_theo_hdkhid_v2.diachi_ld.trim()==''){
            this.$toast.error('Hãy nhập địa chỉ lắp đặt!')
            return
          }
        }
        if(this.enableCheckBoxs.thayDoiToThiCong&&this.dataSources.length==0){
          this.$toast.error('Hãy chọn tổ thi công muốn thay đổi!')
          return
        }

        // if(this.hopdong_theo_mathuebao.tthdId==-1||this.hopdong_theo_mathuebao.tthdId==6||this.hopdong_theo_mathuebao.tthdId==7){
        //   this.$alert('Thuê bao đã hoàn thành không được thay đổi thông tin', '', {
        //       dangerouslyUseHTMLString: true,
        //       confirmButtonText: 'OK',
        //       type: 'info'
        //   }) 
        //   return
        // }
        
        var totc_id=0
        var donvild_id=0
        if(this.enableCheckBoxs.thayDoiToThiCong&&this.toThiCongSlected){
          totc_id=this.toThiCongSlected.donvi_id
          donvild_id=this.toThiCongSlected.donviql_id
        }
        if(!this.diachild.sonha_ld){
          this.diachild.sonha_ld=''
        }
        var data={
          hdTbId: this.hopdong_theo_hdkhid_v2.hdtb_id,
          toTcId: totc_id,
          donViLdId: donvild_id,
          kieu: 0,
          diaChiLdId: this.diachild.diachild_id,
          tinhLdId: this.diachild.tinhld_id,
          quanLdId: this.diachild.quanld_id,
          phuongLdId: this.diachild.phuongld_id,
          phoLdId: this.diachild.phold_id, 
          apLdId: this.diachild.apld_id, 
          khuLdId: this.diachild.khuld_id,
          dacDiemId: this.diachild.dacdiemld_id,
          soNhaLd: this.diachild.sonha_ld,
          diaChiLd: this.hopdong_theo_hdkhid_v2.diachi_ld,
          kieuDvId: this.kieu_dv_selected         
        }
        console.log(data)
        if(this.enableCheckBoxs.diachiLD&&!this.enableCheckBoxs.thayDoiToThiCong){
          data.kieu=4
          this.$confirm('Bạn chắc chắn muốn thay đổi địa chỉ lắp đặt ?',
            'Thông báo',
            {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(() => {

              this.capnhat_tramvattu_lapdat_v2(data)

          }).catch((e) => {})
        }else if(!this.enableCheckBoxs.diachiLD&&this.enableCheckBoxs.thayDoiToThiCong){
          data.kieu=2
          this.$confirm('Bạn chắc chắn muốn thay đổi tổ thi công ?',
            'Thông báo',
            {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(() => {
              this.capnhat_tramvattu_lapdat_v2(data)
              
          }).catch((e) => {})
        }else if(this.enableCheckBoxs.diachiLD&&this.enableCheckBoxs.thayDoiToThiCong){
          data.kieu=5
          this.$confirm('Bạn chắc chắn muốn thay đổi địa chỉ lắp đặt và tổ thi công ?',
            'Thông báo',
            {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(() => {
              this.capnhat_tramvattu_lapdat_v2(data)
          }).catch((e) => {})
        }

      }
  },
  mounted(){
      this.lay_danhsach_kieudonvi()
      this.lay_danhsach_quan()
  },
  created () {
    EventBus.$on('hopdong_theo_hdkhid', this.receiveData)
  },
  destroyed () {
    EventBus.$off('hopdong_theo_hdkhid', this.receiveData)
  },
}
</script>
<style scoped>
.btn-main{
    background: #0176FF;
    color: #fff;
}
.btn-main:hover{
    background: #035FCC;
}
.btn {
    padding: 5px 10px;
    border-radius: 4px;
}
</style>