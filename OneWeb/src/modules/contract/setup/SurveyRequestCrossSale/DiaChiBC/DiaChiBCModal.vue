<template>
  <b-modal
    ref="popupDiaChiBC"
    size="lg"
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
          <span class="icon one-notepad"></span> Chọn địa chỉ
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="row">
							<div class="col-sm-12 col-12">
                <div class="info-row">
                    <div class="key w100"></div>
                    <div class="value" style="color:blue">{{diachi_bandau}}</div>
								</div>
							</div>
						</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Tỉnh/TP <k-required-marker/></div>
                <div class="value">
                  <div class="select-custom" ref="ds_tinh">
                      <select2 id="idSelect2Tinh" :z-index="2000" :settings="{ dropdownParent: $refs['ds_tinh'] }" ref="cboTinh"
                          v-model="result.tinh_id" class="select2"
                          :options="danhSachTinh.map(e=> ({id: e.tinh_id, text: e.tentinh}))"
                          @change="changeTinh"
                      >
                      </select2>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Quận/Huyện <k-required-marker/></div>
                <div class="value">
                  <div class="select-custom" ref="ds_quan">
                      <select2 id="idSelect2Quan" :z-index="2000" :settings="{ dropdownParent: $refs['ds_quan'] }" ref="cboQuan"
                          v-model="result.quan_id" class="select2"
                          :options="danhSachQuan.map(e=> ({id: e.quan_id, text: e.ten_quan}))"
                          @change="changeQuan"
                      >
                      </select2>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Phường/Xã <k-required-marker/></div>
                <div class="value">
                  <div class="select-custom" ref="ds_phuong">
                      <select2 id="idSelect2Phuong" :z-index="2000" :settings="{ dropdownParent: $refs['ds_phuong'] }" ref="cboPhuong"
                          v-model="result.phuong_id" class="select2"
                          :options="danhSachPhuong.map(e=> ({id: e.phuong_id, text: e.ten_phuong}))"
                          @change="changePhuong"
                      >
                      </select2>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Số nhà</div>
                <div class="value">
                  <input type="text" v-model="result.sonha" class="form-control bold" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Đường/Phố</div>
                <div class="value padt7 w20">
                  <div class="check-action">
                    <input type="checkbox" class="check" @change="changeCheck(1)" v-model="checkBox.pho" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <select2 id="idSelect2Pho" :z-index="2000" :settings="{ dropdownParent: $refs['ds_pho'] }" ref="cboPho"
                        v-model="result.pho_id" class="select2"
                        :options="danhSachPho.map(e=> ({id: e.pho_id, text: e.ten_pho}))"
                        @change="changePho"
                        :disabled="!checkBox.pho"
                    >
                  </select2>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Ngõ/Ấp</div>
                <div class="value padt7 w20">
                  <div class="check-action">
                    <input type="checkbox" class="check" @change="changeCheck(2)" v-model="checkBox.ap" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <select2 id="idSelect2Ap" :z-index="2000" :settings="{ dropdownParent: $refs['ds_ap'] }" ref="cboAp"
                        v-model="result.ap_id" class="select2"
                        :options="danhSachAp.map(e=> ({id: e.pho_id, text: e.ten_pho}))"
                        @change="changeAp"
                        :disabled="!checkBox.ap"
                    >
                    </select2>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Khu</div>
                <div class="value padt7 w20">
                  <div class="check-action">
                    <input type="checkbox" class="check" @change="changeCheck(3)" v-model="checkBox.khu"/>
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value"> 
                  <select2 id="idSelect2Khu" :z-index="2000" :settings="{ dropdownParent: $refs['ds_khu'] }" ref="cboKhu"
                      v-model="result.khu_id" class="select2"
                      :options="danhSachKhu.map(e=> ({id: e.pho_id, text: e.ten_pho}))"
                      @change="changeKhu"
                      :disabled="!checkBox.khu"
                  >
                  </select2>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Đặc điểm</div>
                <div class="value padt7 w20">
                  <div class="check-action">
                    <input type="checkbox" class="check" :disabled="disabledCheckDacDiem" v-model="checkBox.dacdiem" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <div class="select-custom" ref="ds_dacdiem">
                      <select2 id="idSelect2DacDiem" :z-index="2000" :settings="{ dropdownParent: $refs['ds_dacdiem'] }" ref="cboDacDiem"
                          v-model="result.dacdiem_id" class="select2"
                          :options="danhSachDacDiem.map(e=> ({id: e.dacdiem_id, text: e.dacdiem}))"
                          :disabled="!checkBox.dacdiem"
                      >
                      </select2>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w100">Địa chỉ</div>
                <div class="value">
                  <textarea
                    v-model="input_diachi"
                    class="form-control bold"
                    
                    style="height: 100px; resize: none"
                  ></textarea>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="group-buttons -bottom center">
          <button
            class="btn btn-outline-secondary nocorner"
            style="width: 120px"
            v-b-modal.ModalChonToaDo
          >
            <span class="icon one-map-location1"></span> Vị trí
          </button>
          <button @click="accept" class="btn btn-second nocorner" style="width: 120px">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp
            nhận
          </button>
        </div>
      </div>
      <!-- Modal -->
      <ModalChonToaDo  idModalChonToaDo="ModalChonToaDo" @XacNhanToaDo="XacNhanToaDo" :position="getPosition" :diachi="result.diachi"></ModalChonToaDo>
    </div>
  </b-modal>
</template>
<script>
// param prop object data
// {
  //  diachi_id:0,
//     tinh_id:0,
//     quan_id:0,
//     phuong_id:0,
//     pho_id:0,
//     ap_id:0,
//     khu_id:0,
//     dacdiem_id:0,
//     sonha:'',
//     diachi:''
//     kinhdo:0,
//     vido:0
// }
import API from './API'
import ModalChonToaDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
export default {
  name: "DiaChiBCModal",
  components:{
    ModalChonToaDo,
    KRequiredMarker
  },
  props: {
      data:{
          type:Object,
          default:{}
      },
      tinh_bc:{
        type:[Number, String],
        default:0
      }
  },
  computed:{
    diachi_created(){
      if(this.them_text_vietnam==1){
        return this.sonha_created+this.pho_created+this.ap_created+this.khu_created+this.phuong_created+this.quan_created+this.tinh_created + ', Việt Nam'
      } else{
        return this.sonha_created+this.pho_created+this.ap_created+this.khu_created+this.phuong_created+this.quan_created+this.tinh_created 
      }
      
    },
    sonha_created(){
      return (this.result.sonha!=undefined&&this.result.sonha.trim()!='')?this.result.sonha.trim()+', ':''
    },
    pho_created(){
      if(!this.result.pho_id||!this.checkBox.pho){
        return ''
      }
      let pho=this.danhSachPho.find(x=>x.pho_id==this.result.pho_id)
      if(pho!=undefined){
        return pho.ten_pho+', '
      }
      return ''
    },
    ap_created(){
      if(!this.result.ap_id||!this.checkBox.ap){
        return ''
      }
      let ap=this.danhSachAp.find(x=>x.pho_id==this.result.ap_id)
      if(ap!=undefined){
        return ap.ten_pho+', '
      }
      return ''
    },
    khu_created(){
      if(!this.result.khu_id||!this.checkBox.khu){
        return ''
      }
      let khu=this.danhSachKhu.find(x=>x.pho_id==this.result.khu_id)
      if(khu!=undefined){
        return khu.ten_pho+', '
      }
      return ''
    },
    phuong_created(){
      if(!this.result.phuong_id){
        return ''
      }
      let phuong=this.danhSachPhuong.find(x=>x.phuong_id==this.result.phuong_id)
      if(phuong!=undefined){
        return phuong.ten_phuong+', '
      }
      return ''
    },
    quan_created(){
      if(!this.result.quan_id){
        return ''
      }
      let quan=this.danhSachQuan.find(x=>x.quan_id==this.result.quan_id)
      if(quan!=undefined){
        return quan.ten_quan+', '
      }
      return ''
    },
    tinh_created(){
      if(!this.result.tinh_id){
        return ''
      }
      let tinh=this.danhSachTinh.find(x=>x.tinh_id==this.result.tinh_id)
      if(tinh!=undefined){
        return tinh.tentinh
      }
      return ''
    },
    
    getPosition () {
      return {
        lat: parseFloat(this.result.vido?this.result.vido:0),
        lng: parseFloat(this.result.kinhdo?this.result.kinhdo:0)
      }
    },
  },
  data(){
      return {
          result:this.data,
          danhSachTinh:[],
          danhSachQuan:[],
          danhSachPhuong:[],
          danhSachPho:[],
          danhSachAp:[],
          danhSachKhu:[],
          danhSachDacDiem:[],
          checkBox:{
              pho:false,
              ap:false,
              khu:false,
              dacdiem:false
          },
          disabledCheckDacDiem:true,
          diachi_bandau:'',
          input_diachi:'',
          them_text_vietnam:0
      }
  },
  methods: {
    showModal() {
      this.$refs["popupDiaChiBC"].show()
    },
    hideModal() {
      this.$refs["popupDiaChiBC"].hide()
    },
    accept(){
      if(!this.checkBox.pho&&!this.checkBox.ap&&!this.checkBox.khu){
        this.$toast.error('Hãy chọn Phố, Ấp, Khu!')
        return
      }
      var pho_id=0
      var ap_id=0
      var khu_id=0

      if(this.checkBox.pho&&this.danhSachPho.length>0){
        pho_id=1
      }
      if(this.checkBox.ap&&this.danhSachAp.length>0){
        ap_id=1
      }
      if(this.checkBox.khu&&this.danhSachKhu.length>0){
        khu_id=1
      }
      if(pho_id==0&&ap_id==0&&khu_id==0){
        this.$toast.error('Hãy chọn Phố, Ấp, Khu!')
        return
      }
      //check dữ liệu trả về
      let data=Object.assign({}, this.result)
      this.$emit('accept', Object.assign(data,{
        pho_id:this.checkBox.pho?this.result.pho_id:0,
        ap_id:this.checkBox.ap?this.result.ap_id:0,
        khu_id:this.checkBox.khu?this.result.khu_id:0,
        dacdiem_id:this.checkBox.dacdiem?this.result.dacdiem_id:0,
        diachi:this.input_diachi
      }))
      this.hideModal()
    },
    handleShowModal(){
      this.result=this.data
      this.checkBox={
          pho:false,
          ap:false,
          khu:false,
          dacdiem:false
      }
      this.disabledCheckDacDiem=true
      this.clear()
      this.diachi_bandau=""+this.result.diachi
      this.them_text_vietnam=0
      setTimeout(()=>{
        this.initDuLieu()
        
      },200)
      
    },
    clear(){
      this.danhSachTinh=[]
      this.danhSachQuan=[]
      this.danhSachPhuong=[]
      this.danhSachPho=[]
      this.danhSachAp=[]
      this.danhSachKhu=[]
      this.danhSachDacDiem=[]
    },
    async initDuLieu(){
      this.layDanhSachTinh()
      let ds_ts=await this.sp_lay_ds_thamso_md({
        tinhthicong_id: this.tinh_bc,
        p_kieu_id: 0
      })
      let det=ds_ts.find(x=>x.ma_ts=='THEM_VN_DIACHI')
      if(det&&det.ten_ts=='1'){
        this.them_text_vietnam=1
      }
    },
    XacNhanToaDo (position) {
      this.result.vido = position.lat
      this.result.kinhdo = position.lng
    },
    async layDanhSachTinh(){
      try{
        this.loading(true)
        let response = await API.lay_danhsach_tinh(this.axios,{
          tinhthicong_id: this.tinh_bc,
          tinh_id: this.tinh_bc
        })
        this.loading(false)
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.danhSachTinh=response.data.data
          this.result.tinh_id=this.tinh_bc
          this.layDanhSachQuanTheoTinh(this.result.tinh_id)
          //this.danhSachTinh=response.data.data
          // if(this.result.tinh_id&&this.result.tinh_id!=0){
          //   //this.danhSachTinh=result.filter(x=>x.tinh_id==this.result.tinh_id).map(x=>Object.assign(x,{tentinh: x.tentinh.split('-').length>1?x.tentinh.split('-')[1]:x.tentinh}))
            
          // }else if(this.tinh_bc!=0){
          //   this.result.tinh_id=this.tinh_bc
          //   //this.danhSachTinh=result.filter(x=>x.tinh_id==this.result.tinh_id).map(x=>Object.assign(x,{tentinh: x.tentinh.split('-').length>1?x.tentinh.split('-')[1]:x.tentinh}))
          //   this.layDanhSachQuanTheoTinh(this.result.tinh_id)
          // }else{
          //   this.result.tinh_id=this.$root.token.getPhanVungID()
          //   this.danhSachTinh=result.filter(x=>x.tinh_id==this.result.tinh_id).map(x=>Object.assign(x,{tentinh: x.tentinh.split('-').length>1?x.tentinh.split('-')[1]:x.tentinh}))
          //   this.layDanhSachQuanTheoTinh(this.result.tinh_id)
          // }
        }else{
          this.danhSachTinh=[]
        }
      }catch(e){
        this.loading(false)
        this.$toast.error('Không load được danh sách tỉnh')
      }      
    },
    async layDanhSachQuanTheoTinh(tinh_id){
      try{
        this.loading(true)
        let response = await API.lay_danhsach_quan_theo_tinh(this.axios, {
          tinhthicong_id: tinh_id,
          tinh_id: tinh_id
        })
        this.loading(false)
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.danhSachQuan=response.data.data
            if(this.result.quan_id&&this.result.quan_id!=0){
              this.layDanhSachPhuongTheoQuan(this.result.quan_id)
            }else{
              if(this.danhSachQuan.length>0){
                this.result.quan_id=this.danhSachQuan[0].quan_id
                this.layDanhSachPhuongTheoQuan(this.result.quan_id)
              }else{
                this.result.quan_id=0
              }
            }
        }else{
          this.danhSachQuan=[]
          this.result.quan_id=0
        }
      }catch(e){
        this.loading(false)
        this.$toast.error('Không load được danh sách quận/huyện')
      } 
    },
    async layDanhSachPhuongTheoQuan(quan_id){
      try{
        this.loading(true)
        let response = await API.lay_danhsach_phuong(this.axios, {
          tinhthicong_id: this.tinh_bc,
          quan_id: quan_id
        })
        this.loading(false)
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.danhSachPhuong=response.data.data
          if(this.result.phuong_id&&this.result.phuong_id!=0){
              await this.lay_danhsach_pho(this.result.phuong_id)
              await this.lay_danhsach_ap(this.result.phuong_id)
              await this.lay_danhsach_khu(this.result.phuong_id)       
          }else{
            if(this.danhSachPhuong.length>0){
              this.result.phuong_id=this.danhSachPhuong[0].phuong_id
              await this.lay_danhsach_pho(this.result.phuong_id)
              await this.lay_danhsach_ap(this.result.phuong_id)
              await this.lay_danhsach_khu(this.result.phuong_id)
            }else{
              this.result.phuong_id=0
            }
          }
        }else{
          this.danhSachPhuong=[]
          this.result.phuong_id=0
        }
      }catch(e){
        this.loading(false)
        this.$toast.error('Không load được danh sách phường')
      } 
    },
    async lay_danhsach_pho(phuong_id){
        try{
          this.loading(true)
          let response=await API.lay_danhsach_pho(this.axios, {
            tinhthicong_id: this.tinh_bc,
            phuong_id: phuong_id,
            nhompho_id: 1
          })
          this.loading(false)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.danhSachPho=response.data.data
            if(this.result.pho_id&&this.result.pho_id!=0){
              this.checkBox.pho=true
              this.lay_danhsach_dacdiem(this.result.pho_id, phuong_id)
            }else{
              this.checkBox.pho=false
              if(this.danhSachPho.length>0){
                this.result.pho_id=this.danhSachPho[0].pho_id
                //this.lay_danhsach_dacdiem(this.result.pho_id, phuong_id)
              }else{
                this.result.pho_id=0
              }
            }
          }else{
            this.danhSachPho=[]
            this.result.pho_id=0
          }
        }catch(e){
          this.loading(false)
          this.$toast.error('Không load được danh sách phố')
        }
    },
    async lay_danhsach_ap(phuong_id){
        try{
          this.loading(true)
          let response=await API.lay_danhsach_ap(this.axios, {
            tinhthicong_id: this.tinh_bc,
            phuong_id: phuong_id,
            nhompho_id: 2
          })
          this.loading(false)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.danhSachAp=response.data.data
              if(this.result.ap_id&&this.result.ap_id!=0){
                this.checkBox.ap=true
                this.lay_danhsach_dacdiem(this.result.ap_id, phuong_id)
              }else{
                this.checkBox.ap=false
                if(this.danhSachAp.length>0){
                  this.result.ap_id=this.danhSachAp[0].pho_id
                }else{
                  this.result.ap_id=0
                }
              }
          }else{
              this.danhSachAp=[]
              this.result.ap_id=0
          }
        }catch(e){
          this.loading(false)
          this.$toast.error('Không load được danh sách ấp')
        }
    },
    async lay_danhsach_khu(phuong_id){
        try{
          this.loading(true)
          let response=await API.lay_danhsach_khu(this.axios, {
            tinhthicong_id: this.tinh_bc,
            phuong_id: phuong_id,
            nhompho_id: 3
          })
          this.loading(false)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.danhSachKhu=response.data.data
              if(this.result.khu_id&&this.result.khu_id!=0){
                this.checkBox.khu=true
                this.lay_danhsach_dacdiem(this.result.khu_id, phuong_id)
              }else{
                this.checkBox.khu=false
                if(this.danhSachKhu.length>0){
                  this.result.khu_id=this.danhSachKhu[0].pho_id
                }else{
                  this.result.khu_id=0
                }
              }
          }else{
              this.danhSachKhu=[]
              this.result.khu_id=0
          }
        }catch(e){
          this.loading(false)
          this.$toast.error('Không load được danh sách khu')
        }
    },
    async lay_danhsach_dacdiem(pho_id, phuong_id){
        try{
          this.loading(true)
          let response=await API.lay_danhsach_dacdiem(this.axios, pho_id, phuong_id)
          this.loading(false)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.danhSachDacDiem=response.data.data
              if(this.result.dacdiem_id&&this.result.dacdiem_id!=0){
                this.checkBox.dacdiem=true
              }else{
                this.checkBox.dacdiem=false
              }
          }else{
              this.danhSachDacDiem=[]
          }
        }catch(e){
          this.loading(false)
          this.$toast.error('Không load được danh sách đặc điểm')
        }
    },
    changeTinh(){
      this.result.quan_id=0
      this.result.phuong_id=0
      this.result.pho_id=0
      this.result.ap_id=0
      this.result.khu_id=0
      this.result.dacdiem_id=0
      this.danhSachQuan=[]
      this.danhSachPhuong=[]
      this.danhSachPho=[]
      this.danhSachAp=[]
      this.danhSachKhu=[]
      this.danhSachDacDiem=[]
      this.layDanhSachQuanTheoTinh(this.result.tinh_id)
    },
    changeQuan(){
      this.result.phuong_id=0
      this.result.pho_id=0
      this.result.ap_id=0
      this.result.khu_id=0
      this.result.dacdiem_id=0
      this.danhSachPhuong=[]
      this.danhSachPho=[]
      this.danhSachAp=[]
      this.danhSachKhu=[]
      this.danhSachDacDiem=[]
      this.layDanhSachPhuongTheoQuan(this.result.quan_id)
    },
    async changePhuong(){
      this.result.pho_id=0
      this.result.ap_id=0
      this.result.khu_id=0
      this.result.dacdiem_id=0
      this.danhSachPho=[]
      this.danhSachAp=[]
      this.danhSachKhu=[]
      this.danhSachDacDiem=[]
      await this.lay_danhsach_pho(this.result.phuong_id)
      await this.lay_danhsach_ap(this.result.phuong_id)
      await this.lay_danhsach_khu(this.result.phuong_id)
    },
    changePho(){
      this.result.dacdiem_id=0
      this.danhSachDacDiem=[]
      this.lay_danhsach_dacdiem(this.result.pho_id, this.result.phuong_id)
    },
    changeAp(){
      this.result.dacdiem_id=0
      this.danhSachDacDiem=[]
      this.lay_danhsach_dacdiem(this.result.ap_id, this.result.phuong_id)
    },
    changeKhu(){
      this.result.dacdiem_id=0
      this.danhSachDacDiem=[]
      this.lay_danhsach_dacdiem(this.result.khu_id, this.result.phuong_id)
    },
    changeCheck(kieu){
      if(kieu==1){
        if(this.result.pho_id>0&&this.checkBox.pho){
          this.lay_danhsach_dacdiem(this.result.pho_id, this.result.phuong_id)
        }
      }else if(kieu==2){
        if(this.result.ap_id>0&&this.checkBox.ap){
          this.lay_danhsach_dacdiem(this.result.ap_id, this.result.phuong_id)
        }
      }else if(kieu==3){
        if(this.result.khu_id>0&&this.checkBox.khu){
          this.lay_danhsach_dacdiem(this.result.khu_id, this.result.phuong_id)
        }
      }
    },
    async sp_lay_ds_thamso_md(data){
        try{
            let response = await SurveyRequestCrossSaleAPI.sp_lay_ds_thamso_md(this.axios, data)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return []
            }
        }catch(e){
            return []
        }
    },
  },
  mounted(){

  },
  watch: {
      data: function(newVal, oldVal){
          this.result=newVal
          this.diachi_bandau=""+this.result.diachi
      },
      diachi_created(val){
        this.input_diachi=val
      },
      'checkBox.pho':function(val){
        if(val||this.checkBox.ap||this.checkBox.khu){
          this.disabledCheckDacDiem=false
        }else{
           this.disabledCheckDacDiem=true
           this.checkBox.dacdiem=false
        }
      },
      'checkBox.ap':function(val){
        if(this.checkBox.pho||val||this.checkBox.khu){
          this.disabledCheckDacDiem=false
        }else{
          this.disabledCheckDacDiem=true
          this.checkBox.dacdiem=false
        }
      },
      'checkBox.khu':function(val){
        if(this.checkBox.pho||this.checkBox.ap||val){
          this.disabledCheckDacDiem=false
        }else{
          this.disabledCheckDacDiem=true
          this.checkBox.dacdiem=false
        }
      }
  }
}
</script>
<style>
.btn-second {
    background-color: #fff;
    border-color: #0176FF;
    color: #0176FF;
    font-weight: 600;
}
.btn-second:hover {
    background-color: #0176FF;
    border-color: #0176FF;
    color: #fff;
    font-weight: 600;
}
.btn-outline-secondary {
    background-color: #fff;
    color: #6c757d;
    border-color: #6c757d;
}
.btn-outline-secondary:hover {
    background-color: #6c757d;
    color: #fff;
    border-color: #6c757d;
}
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
#select2-idSelect2Tinh-container, #select2-idSelect2Quan-container, #select2-idSelect2Pho-container, #select2-idSelect2Khu-container,
#select2-idSelect2Phuong-container,  #select2-idSelect2Ap-container, #select2-idSelect2DacDiem-container {
  font-weight: bold !important;
}
</style>
