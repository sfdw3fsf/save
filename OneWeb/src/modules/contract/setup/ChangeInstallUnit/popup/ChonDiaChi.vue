<template>
  <b-modal
    ref="popupChonDiaChi"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chọn địa chỉ
        </div>
        <div class="close -ap icon-close" @click="close"></div>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Tỉnh/TP</div>
                <div class="value">
                  <div class="select-custom">
                    <select class="form-control" v-model="result.tinh_id" @change="changeTinh($event)">
                      <option v-for="item in danhSachTinh" :key="item.tinh_id" :value="item.tinh_id">{{item.tentinh}}</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Quận/Huyện</div>
                <div class="value">
                  <div class="select-custom">
                    <select class="form-control" @change="changeQuan($event)" v-model="result.quan_id">
                      <option v-for="item in danhSachQuan" :key="item.ID" :value="item.ID">{{item.NAME}}</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Phường/Xã</div>
                <div class="value">
                  <div class="select-custom">
                    <select class="form-control" @change="changePhuong($event)" v-model="result.phuong_id">
                      <option v-for="item in danhSachPhuong" :key="item.PHUONG_ID" :value="item.PHUONG_ID" >{{item.TEN_PHUONG}}</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Số nhà</div>
                <div class="value">
                  <input type="text" v-model="result.sonha" class="form-control" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Đường/Phố</div>
                <div class="value padt7 w20">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="checkBox.pho" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <div class="select-custom">
                    <select :disabled="!checkBox.pho" @change="changePho($event)" v-model="result.pho_id" class="form-control">
                      <option v-for="item in danhSachPho" :key="item.PHO_ID"  :value="item.PHO_ID">{{item.TEN_PHO}}</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Ngõ/Ấp</div>
                <div class="value padt7 w20">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="checkBox.ap" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <div class="select-custom">
                    <select :disabled="!checkBox.ap" @change="changeAp($event) " v-model="result.ap_id" class="form-control">
                      <option v-for="item in danhSachAp" :key="item.PHO_ID" :value="item.PHO_ID">{{item.TEN_PHO}}</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Khu</div>
                <div class="value padt7 w20">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="checkBox.khu"/>
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <div class="select-custom">
                    <select :disabled="!checkBox.khu" @change="changeKhu($event)" v-model="result.khu_id" class="form-control">
                      <option v-for="item in danhSachKhu" :key="item.PHO_ID" :value="item.PHO_ID">{{item.TEN_PHO}}</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Đặc điểm</div>
                <div class="value padt7 w20">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="checkBox.dacdiem" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <div class="select-custom">
                    <select :disabled="!checkBox.dacdiem" v-model="result.dacdiem_id" class="form-control">
                      <option v-for="item in danhSachDacDiem" :key="item.dacdiem_id" :value="item.dacdiem_id">{{item.dacdiem}}</option>
                    </select>
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
                    v-model="result.diachi"
                    class="form-control"
                    style="height: 100px; resize: none"
                  ></textarea>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="group-buttons -bottom center">
          <button @click="accept" class="btn btn-second nocorner" style="width: 120px">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp
            nhận
          </button>
          <button
            class="btn btn-outline-secondary nocorner"
            style="width: 120px"
            @click="close"
          >
            <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
          </button>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
// param prop object data
// {
//     tinh_id:0,
//     quan_id:0,
//     phuong_id:0,
//     pho_id:0,
//     ap_id:0,
//     khu_id:0,
//     dacdiem_id:0,
//     sonha:'',
//     diachi:''
// }
import ChangeInstallUnitAPI from '../ChangeInstallUnitAPI'
export default {
  name: "ChonDiaChi",
  props: {
      
      data:{
          type:Object,
          default:{}
      }
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
          }
      }
  },
  methods: {
    close() {
      this.$refs["popupChonDiaChi"].hide()
    },
    showModal() {
      this.$refs["popupChonDiaChi"].show()
    },
    hideModal() {
      this.$refs["popupChonDiaChi"].hide()
    },
    accept(){
        this.$emit('accept', this.result)
        this.hideModal()
    },
    handleShowModal(){
        this.layDanhSachTinh()
    },
    async layDanhSachTinh(){
        try{
          let response = await ChangeInstallUnitAPI.lay_danhsach_tinh(this.axios)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.danhSachTinh=response.data.data
            if(this.result.tinh_id&&this.result.tinh_id!=0){
               this.layDanhSachQuanTheoTinh(this.result.tinh_id)
            } 
          }else{
            this.danhSachTinh=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách tỉnh')
        }      
    },
    async layDanhSachQuanTheoTinh(tinh_id){
        try{
          let response = await ChangeInstallUnitAPI.lay_danhsach_quan_theo_tinh(this.axios, tinh_id)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.danhSachQuan=response.data.data
            if(this.result.quan_id&&this.result.quan_id!=0){
                this.layDanhSachPhuongTheoQuan(this.result.quan_id)
            }
          }else{
            this.danhSachQuan=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách quận/huyện')
        } 
    },
    async layDanhSachPhuongTheoQuan(quan_id){
        try{
          let response = await ChangeInstallUnitAPI.lay_danhsach_phuong(this.axios, quan_id)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.danhSachPhuong=response.data.data
            if(this.result.phuong_id&&this.result.phuong_id!=0){
                this.lay_danhsach_pho(this.result.phuong_id)
                this.lay_danhsach_ap(this.result.phuong_id)
                this.lay_danhsach_khu(this.result.phuong_id)       
            }
            
          }else{
            this.danhSachPhuong=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách phường')
        } 
    },
    async lay_danhsach_pho(phuong_id){
        try{
            let response=await ChangeInstallUnitAPI.lay_danhsach_pho(this.axios, phuong_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.danhSachPho=response.data.data
                if(this.result.pho_id&&this.result.pho_id!=0){
                    this.lay_danhsach_dacdiem(this.result.pho_id, phuong_id)
                }
            }else{
                this.danhSachPho=[]
            }
        }catch(e){
              this.$toast.error('Không load được danh sách phố')
        }
      },
    async lay_danhsach_ap(phuong_id){
        try{
            let response=await ChangeInstallUnitAPI.lay_danhsach_ap(this.axios, phuong_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.danhSachAp=response.data.data
                if(this.result.ap_id&&this.result.ap_id!=0){
                    this.lay_danhsach_dacdiem(this.result.ap_id, phuong_id)
                }
            }else{
                this.danhSachAp=[]
            }
        }catch(e){
              this.$toast.error('Không load được danh sách ấp')
        }
    },
    async lay_danhsach_khu(phuong_id){
        try{
            let response=await ChangeInstallUnitAPI.lay_danhsach_khu(this.axios, phuong_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.danhSachKhu=response.data.data
                if(this.result.khu_id&&this.result.khu_id!=0){
                    this.lay_danhsach_dacdiem(this.result.khu_id, phuong_id)
                }
            }else{
                this.danhSachKhu=[]
            }
        }catch(e){
              this.$toast.error('Không load được danh sách khu')
        }
    },
      
    async lay_danhsach_dacdiem(pho_id, phuong_id){
        try{
            let response=await ChangeInstallUnitAPI.lay_danhsach_dacdiem(this.axios, pho_id, phuong_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.danhSachDacDiem=response.data.data
            }else{
                this.danhSachDacDiem=[]
            }
        }catch(e){
              this.$toast.error('Không load được danh sách đặc điểm')
        }
    },
    changeTinh(event){
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
        this.layDanhSachQuanTheoTinh(event.target.value)
    },
    changeQuan(event){
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
        this.layDanhSachPhuongTheoQuan(event.target.value)
    },
    changePhuong(event){
        this.result.pho_id=0
        this.result.ap_id=0
        this.result.khu_id=0
        this.result.dacdiem_id=0
        this.danhSachPho=[]
        this.danhSachAp=[]
        this.danhSachKhu=[]
        this.danhSachDacDiem=[]

        this.lay_danhsach_pho(event.target.value)
        this.lay_danhsach_ap(event.target.value)
        this.lay_danhsach_khu(event.target.value)
    },
    changePho(event){
        this.result.dacdiem_id=0
        this.danhSachDacDiem=[]
        this.lay_danhsach_dacdiem(event.target.value, this.result.phuong_id)
    },
    changeAp(event){
        this.result.dacdiem_id=0
        this.danhSachDacDiem=[]
        this.lay_danhsach_dacdiem(event.target.value, this.result.phuong_id)
    },
    changeKhu(event){
        this.result.dacdiem_id=0
        this.danhSachDacDiem=[]
        this.lay_danhsach_dacdiem(event.target.value, this.result.phuong_id)
    }
  },
  mounted(){

  },
  watch: {
      data: function(newVal, oldVal){
          this.result=newVal
          this.checkBox={
              pho:false,
              ap:false,
              khu:false,
              dacdiem:false
          }
      }
  }
}
</script>
<style scoped>
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
</style>
