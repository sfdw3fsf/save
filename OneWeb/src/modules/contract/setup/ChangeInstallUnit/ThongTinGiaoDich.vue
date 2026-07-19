<template>
  <div class="box-col box-form" id="boxLeft">
    <div class="legend-title">Thông tin giao dịch</div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w90">Mã GD</div>
          <div class="value">
            <div class="input-more-button">
              <button class="btn" @click="$emit('showPopupSearchContract')">
                <span class="-ap icon-more_horiz"></span>
              </button>
              <input type="text" class="form-control highlight fw6" @keyup.enter="onEnterMaGD($event)" v-model="hopdong_theo_mathuebao.maGd" @change="inputMaGDChange($event)"/>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w90">Số máy/Acc</div>
          <div class="value">
            <input type="text" readonly v-model="hopdong_theo_hdkhid_v2.ma_tb" class="form-control highlight fw6" />
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w90">Dịch vụ</div>
          <div class="value">
            <div class="select-custom">
              <select name="" class="form-control" disabled v-model="hopdong_theo_hdkhid_v2.dichvuvt_id">
                <option v-for="item in danhsach_dichvu" :key="item.ID" :value="item.ID">{{item.NAME}}</option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w90">Loại hình</div>
          <div class="value">
            <div class="select-custom">
              <select name="" id="" class="form-control" disabled v-model="hopdong_theo_hdkhid_v2.loaitb_id">
                <option v-for="item in danhsach_loaihinh_tb" :key="item.LOAITB_ID" :value="item.LOAITB_ID">{{item.LOAIHINH_TB}}</option>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w90">Loại HĐ</div>
      <div class="value">
        <input type="text" v-model="hopdong_theo_mathuebao.tenLoaiHd" class="form-control" readonly/>
      </div>
    </div>
    <div class="info-row">
      <div class="key w90">Tên thuê bao</div>
      <div class="value">
        <input type="text" v-model="hopdong_theo_hdkhid_v2.ten_tb" class="form-control" readonly/>
      </div>
    </div>
    <div class="info-row">
      <div class="key w90">Trạng thái</div>
      <div class="value">
        <input type="text" v-model="hopdong_theo_hdkhid_v2.trangthai_hd" class="form-control italic red" readonly/>
      </div>
    </div>
    <div class="info-row">
      <div class="key w90">Địa chỉ LĐ</div>
      <div class="value">
        <input type="text" v-model="hopdong_theo_hdkhid_v2.diachi_ld" class="form-control" readonly/>
      </div>
    </div>
    <div class="info-row">
      <div class="key w90">Tổ</div>
      <div class="value">
        <input type="text" v-model="toTC" class="form-control" readonly/>
      </div>
    </div>
    <div class="info-row">
      <div class="key w90">Đơn vị LĐ</div>
      <div class="value">
        <input type="text" v-model="donvi_ql" class="form-control" readonly/>
      </div>
    </div>
    <div class="info-row">
      <div class="key w90">Ghi chú</div>
      <div class="value">
        <textarea
          name=""
          class="form-control"
          style="height: 100px; resize: none"
          id=""
          cols="30"
          rows="10"
          v-model="hopdong_theo_hdkhid_v2.ghichu"
          readonly
        ></textarea>
      </div>
    </div>
  </div>
</template>
<script>
    // {
    //   "maGd": "HPG-TT/01191745",
    //   "ngayYc": "04/05/2021 12:20:41",
    //   "hdkhId": 5406069,
    //   "tenKh": "Bùi Công Anh",
    //   "diaChiKh": "Xóm 2, Xã Đông Sơn, Huyện Thủy Nguyên",
    //   "ngayLapHd": "07/06/2011 08:02:07",
    //   "soDT": "0972398541",
    //   "tenLoaiHd": "Đặt cọc mới",
    //   "trangThaiHd": "Đã hoàn thành",
    //   "tenNv": "Lê Thị Thùy Dung",
    //   "donViId": 10219,
    //   "tenDv": "Quầy hợp đồng VNPT-Cab - P2",
    //   "khachHangId": 137464,
    //   "tthdId": 6,
    //   "thueBaoId": 456722,
    //   "maKh": "HPG-000162786",
    //   "maHd": "HDDV-000449823",
    //   "chuyenMang": null
    // }
import ChangeInstallUnitAPI from './ChangeInstallUnitAPI'
import EventBus from '@/utils/eventBus'
export default {
    name:'ThongTinGiaoDich',
    components:{

    },
    data(){
        return {
            danhsach_dichvu:[],
            danhsach_loaihinh_tb:[],
            hopdong_theo_mathuebao:{},
            hopdong_theo_hdkhid_v2:{},
            toTC:'',
            donvi_ql:''

        }
    },
    methods:{
        async lay_danhsach_dichvu(){
            try{
                let response=await ChangeInstallUnitAPI.lay_danhsach_dichvu(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_dichvu=response.data.data
                }else{
                    this.danhsach_dichvu=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách dich vu')
            }
        },
        async lay_danhsach_loaihinh_tb(){
            try{
                let response=await ChangeInstallUnitAPI.lay_danhsach_loaihinh_tb(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_loaihinh_tb=response.data.data
                }else{
                    this.danhsach_loaihinh_tb=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách loại hình thuê bao')
            }
        },
        async lay_ds_donvi_theo_hdtb(hdtb_id){
            try{
                let response=await ChangeInstallUnitAPI.lay_ds_donvi_theo_hdtb(this.axios, hdtb_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    //this.danhsach_loaihinh_tb=response.data.data
                    if(response.data.data.length>0){
                      this.toTC=response.data.data[0].ten_dv
                    }
                }else{
                    this.toTC=''
                    //this.danhsach_loaihinh_tb=[]
                }
            }catch(e){
                this.$toast.error('Không lấy được tổ')
            }
        },
        async lay_ten_donvi_ql(donvi_id){
            try{
                let response=await ChangeInstallUnitAPI.lay_donvi_ql(this.axios, donvi_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data=='-1'){
                      this.donvi_ql=''
                    }else{
                      this.donvi_ql=response.data.data
                    }
                }else{
                    this.donvi_ql=''
                }
            }catch(e){
                this.$toast.error('Không lấy được thông tin đơn vị')
            }
        },
        inputMaGDChange(event){
          this.$emit('InputMaGDChange', event.target.value)
        },
        onEnterMaGD(event){
          this.$emit('onEnterMaGD', event.target.value)
        },
        receiveData(data){
          this.hopdong_theo_mathuebao=data.hopdong_theo_mathuebao
          this.hopdong_theo_hdkhid_v2=data.hopdong_theo_hdkhid_v2
          this.toTC=''
          this.lay_ds_donvi_theo_hdtb(this.hopdong_theo_hdkhid_v2.hdtb_id)
          this.donvi_ql=''
          this.lay_ten_donvi_ql(this.hopdong_theo_hdkhid_v2.donvi_id)
          this.$emit('InputMaGDChange',this.hopdong_theo_mathuebao.maGd)
        }
    },
    mounted(){
        this.lay_danhsach_dichvu()
        this.lay_danhsach_loaihinh_tb()
    },
    created () {
      EventBus.$on('hopdong_theo_hdkhid', this.receiveData)
    },
    destroyed () {
      EventBus.$off('hopdong_theo_hdkhid', this.receiveData)
    },
}
</script>