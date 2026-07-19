<template>
  <div class="box-form">

    <div class="legend-title">Thông tin người giới thiệu</div>
    <div class="row">
      <div class="col-sm-2 col-12">
        <div class="info-row">
          <div class="key w90">Số máy/Acc</div>
          <div class="value">
            <div class="input-more-button">
              <button class="btn" @click="showModalSearchAccount">
                <span class="-ap icon-more_horiz"></span>
              </button>
              <input type="text"  :value="input_matb" @change="e=>input_matb=e.target.value" readonly class="form-control highlight" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w100">Mã thanh toán</div>
          <div class="value">
            <input type="text" v-model="item.ma_tt" readonly class="form-control highlight" />
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w90">Tên người GT</div>
          <div class="value">
            <input type="text" ref="inputTenNguoiGT" v-model="item.ten_nguoigt" class="form-control" />
          </div>
        </div>
      </div>
      <div class="col-sm-2 col-12">
        <div class="info-row">
          <div class="key w60">Số ĐT</div>
          <div class="value">
            <input type="text" ref="inputSoDT" @keypress="isNumber($event)" v-model="item.so_dt" class="form-control" />
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-2 col-12">
        <div class="info-row">
          <div class="key w90">Mã giao dịch</div>
          <div class="value">
            <input type="text" v-model="ma_gd" @keyup.enter="onEnterMaGD($event)" class="form-control highlight" />
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w100">Số tiền thưởng</div>
          <div class="value">
            <input
              type="text"
              @change="item.tru_tien = $event.target.value"
              :value="isNumeric(item.tru_tien)?item.tru_tien:1000000"
              readonly
              class="form-control tright"
              style="background-color: #bae7ff"
            />
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w90">Địa chỉ NGT</div>
          <div class="value">
            <input type="text" ref="inputDiaChi" v-model="item.diachi_ngt" class="form-control" />
          </div>
        </div>
      </div>
    </div>
     <!-- Modal -->
     <SearchAccountModal ref="searchAccountModal" @form-close="formCloseSearchAccount" />
    
  </div>
</template>
<script>
 
import EventBus from '@/utils/eventBus'
import ChangeIntroducePeopleAPI from './ChangeIntroducePeopleAPI'
import SearchAccountModal from '../SurveyRequest/popups/SearchAccountModal.vue'
export default {
    name:'ThongTinNguoiGioiThieu',
    components:{
        SearchAccountModal
    },
    data(){
        return{
            item:{},
            ma_gd:'',
            input_matb:'',

        }
    },
    methods:{
        onEnterMaGD(event){
            if(event.target.value.trim()!=''){
                EventBus.$emit('change_introduce_people',{action:'enter_magd', value:event.target.value.trim()})
            }
        },
        receiveData(data){
            if(data.action=='receive-item'){
                this.item= Object.assign({},data.value)
                //init
                this.input_matb=this.item.ma_tb_gt?this.item.ma_tb_gt.toString():''
            }else if(data.action=='ghilai'){
                //check
                this.capnhat_thongtin_gt()
            }else if(data.action=='huy'){
                this.item={}
                this.ma_gd=''
            }
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
        async capnhat_thongtin_gt(){
            try{
                if(!this.isCheck()){
                  return
                }
                this.loading(true)
                //check phải là thuê bao firber không với vkieu=3
                let responseCheck1=await ChangeIntroducePeopleAPI.lay_ds_hd_tuvan(this.axios, this.ma_gd, this.item.hdkh_id, 3)
                if(responseCheck1 && responseCheck1.data && responseCheck1.data.error_code && (responseCheck1.data.error_code == 'BSS-00000000'||responseCheck1.data.error_code == 'BSS-00000204') && responseCheck1.data.data) {
                    if(responseCheck1.data.data.length<=0){
                      this.$toast.error('Thuê bao không phải là Fiber. Không thể ghi lại !')
                      return
                    }
                }else{
                  if(responseCheck1 && responseCheck1.data &&responseCheck1.data.message){
                    this.$toast.error(responseCheck1.data.message)
                  }else{
                    this.$toast.error('Đã có lỗi xảy ra, cập nhật không thành công!')
                  }
                  return
                }
                //check 
                let responseCheck2=await ChangeIntroducePeopleAPI.lay_ds_hd_tuvan(this.axios, this.ma_gd, this.item.hdkh_id, 2)
                if(responseCheck2 && responseCheck2.data && responseCheck2.data.error_code && (responseCheck2.data.error_code == 'BSS-00000000'||responseCheck2.data.error_code == 'BSS-00000204') && responseCheck2.data.data) {
                    if(responseCheck2.data.data.length>0){
                      this.$toast.error('HĐ đã hoàn công! Không thể cập nhật!')
                      return
                    }
                }else{
                  if(responseCheck2 && responseCheck2.data &&responseCheck2.data.message){
                    this.$toast.error(responseCheck2.data.message)
                  }else{
                    this.$toast.error('Đã có lỗi xảy ra, cập nhật không thành công!')
                  }
                  return
                }

                let nguoi_cn=this.$root.token.getUserName()
                let data={
                    vma_gd: this.ma_gd?this.ma_gd.toString().trim():'',
                    vhdkh_id:this.item.hdkh_id,
                    vnguoi_cn: nguoi_cn,
                    vthanhtoan_id: this.item.tt?this.item.tt.toString().trim():'',
                    ten_ngt: this.item.ten_nguoigt?this.item.ten_nguoigt.toString().trim():'',
                    diachi_ngt: this.item.diachi_ngt?this.item.diachi_ngt.toString().trim():'',
                    vso_dt: this.item.so_dt?this.item.so_dt.toString().trim():'',
                    vma_tb: this.input_matb.trim()
                }

                let response=await ChangeIntroducePeopleAPI.capnhat_thongtin_gt_v2(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.$toast.success('Cập nhật thông tin thành công!')
                    EventBus.$emit('change_introduce_people',{action:'enter_magd', value:this.ma_gd})
                    // this.$alert(response.data.data, 'Thông báo', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'info'
                    // }).then((value)=>{
                    //     EventBus.$emit('change_introduce_people',{action:'enter_magd', value:this.ma_gd})
                    // })
                    
                }else{
                    if(response && response.data &&response.data.message){
                      this.$toast.error(response.data.message)
                    }else{
                      this.$toast.error('Đã có lỗi xảy ra, cập nhật không thành công!')
                    }
                }
            }catch(e){
              this.$toast.error('Đã có lỗi xảy ra, cập nhật không thành công!')
            }finally{
                this.loading(false)
            }
        },
        isCheck(){
          if(this.input_matb.trim()==''){
            this.$toast.error('Chưa có thông tin thuê bao gán !')
            return false
          }
          if(!this.item.ma_tt||this.item.ma_tt.toString().trim()==''){
            this.$toast.error('Chưa có thông tin thanh toán gán !')
            return false
          }
          if(!this.item.ten_nguoigt||this.item.ten_nguoigt.toString().trim()==''){
            this.$toast.error('Chưa có tên người giới thiệu !')
            this.$refs.inputTenNguoiGT.focus()
            return false
          }
          if(!this.item.diachi_ngt||this.item.diachi_ngt.toString().trim()==''){
            this.$toast.error('Chưa có địa chỉ người giới thiệu !')
            this.$refs.inputDiaChi.focus()
            return false
          }
          if(!this.item.so_dt||this.item.so_dt.toString().trim()==''){
            this.$toast.error('Chưa có số điện thoại người giới thiệu !')
            this.$refs.inputSoDT.focus()
            return false
          }
          if(this.item.tt==null||this.item.tt.toString().trim()==''){
            this.$toast.error('Chưa có thông tin thuê bao gán !')
            return false
          }
          return true 
        },
        showModalSearchAccount(){
            this.$refs.searchAccountModal.showModal()
        },
        formCloseSearchAccount(data){
          console.log('formCloseSearchAccount',data)
          if(data&&data.ma_tb){
            this.input_matb=data.ma_tb
            this.onEnterMaTB()
          }
        },
        onEnterMaTB(){

        },
        isNumeric(n) {
          return !isNaN(parseFloat(n)) && isFinite(n);
        }
    },
    created () {
        EventBus.$on('change_introduce_people', this.receiveData)
    },
    destroyed () {
        EventBus.$off('change_introduce_people', this.receiveData)
    },
}
</script>
