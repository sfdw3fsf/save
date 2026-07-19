<template>
  <div class="row">
    <div class="col-sm-6 col-12">
      <div class="legend-title">Tham số mặc định</div>
      <div class="info-row">
        <div class="key w70">Dịch vụ VT</div>
        <div class="value">
            <multiselect v-model="thamso_macdinh.dichvu_vt_selected"
              :options="thamso_macdinh.dichvu_vt"
              placeholder="Lựa chọn"
              :showLabels="false"
              :allowEmpty="false"
              label="TEN_DVVT"
              track-by="DICHVUVT_ID">
              <template #noOptions>
                  Không tìm thấy thông tin
              </template>
            </multiselect>
        </div>
      </div>
      <div class="info-row">
        <div class="key w70">Đơn vị QL</div>
        <div class="value">
          <multiselect v-model="thamso_macdinh.donvi_ql_selected"
              :options="thamso_macdinh.donvi_ql"
              placeholder="Lựa chọn"
              :showLabels="false"
              :allowEmpty="false"
              label="ten_dv"
              track-by="donvi_id">
              <template #noOptions>
                  Không tìm thấy thông tin
              </template>
            </multiselect>
        </div>
      </div>
      <div class="info-row">
        <div class="key w70">Quận</div>
        <div class="value">
          <multiselect v-model="thamso_macdinh.quan_selected"
              :options="thamso_macdinh.quan"
              placeholder="Lựa chọn"
              @input="changeQuan"
              :showLabels="false"
              :allowEmpty="false"
              label="TEN_QUAN"
              track-by="QUAN_ID">
              <template #noOptions>
                  Không tìm thấy thông tin
              </template>
          </multiselect>
        </div>
      </div>
      <div class="info-row">
        <div class="key w70">Phường</div>
        <div class="value">
          <div class="form-control text pad10"  style="height: 250px; overflow-y: scroll;">
            <div class="check-action" v-for="item in thamso_macdinh.phuong" :key="item.PHUONG_ID" style="width:100%;">
              <input type="checkbox" v-model="item.CHECK" @change="changeCheckBox($event, item)" class="check" />
              <span class="name">{{item.TEN_PHUONG}}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-sm-6 col-12">
      <div class="legend-title">Tham số hóa đơn</div>
      <div class="info-row">
        <div class="key">Máy in nhân viên</div>
        <div class="value">
          <multiselect v-model="thamso_hoadon.may_in_nhanvien_selected"
              :options="thamso_hoadon.may_in_nhanvien"
              placeholder="Lựa chọn"
              :showLabels="false"
              :allowEmpty="false"
              label="ten_nv"
              track-by="nhanvien_id">
              <template #noOptions>
                  Không tìm thấy thông tin
              </template>
          </multiselect>
        </div>
      </div>
      <div class="info-row">
        <div class="key">Máy in VAT</div>
        <div class="value">
          <div class="select-custom">
            <select class="form-control">
              <option></option>
            </select>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key">Quyển</div>
            <div class="value">
              <input type="text" v-model="thamso_hoadon.input_quyen" class="form-control" />
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w60">Seri</div>
            <div class="value">
              <input type="text" v-model="thamso_hoadon.input_seri" class="form-control" />
            </div>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key"></div>
        <div class="value">
          <div class="list-checks-v">
            <div class="item mart7 marb10">
              <div class="check-action">
                <input type="checkbox" disabled v-model="thamso_hoadon.check_giao_phieu" class="check" />
                <span class="name">Giao phiếu TĐ khi thanh toán</span>
              </div>
            </div>
            <div class="item mart7 marb10">
              <div class="check-action">
                <input type="checkbox" v-model="thamso_hoadon.check_inhoadon" class="check" />
                <span class="name">In hóa đơn VAT ngay khi thanh toán</span>
              </div>
            </div>
            <div class="item mart7 marb10">
              <div class="check-action">
                <input type="checkbox" v-model="thamso_hoadon.check_hienthi_thanhbentrai" class="check" />
                <span class="name">Hiển thị thanh bên trái</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="legend-title">Tham số hiển thị</div>
      <div class="list-checks-v">
        <div class="item mart7 marb10">
          <div class="check-action">
            <input type="radio" name="hienthi" value="1" :checked="thamso_hienthi.radio_hienthi==1" @change="changeHienThi($event)" class="check" />
            <span class="name">Không hiển thị thông báo</span>
          </div>
        </div>
        <div class="item mart7 marb10">
          <div class="check-action">
            <input type="radio" name="hienthi" value="2" :checked="thamso_hienthi.radio_hienthi==2" @change="changeHienThi($event)" class="check" />
            <span class="name">Hiển thị thông báo phiếu giao đến</span>
          </div>
        </div>
        <div class="item mart7 marb10">
          <div class="check-action">
            <input type="radio" name="hienthi" value="3" :checked="thamso_hienthi.radio_hienthi==3" @change="changeHienThi($event)" class="check" />
            <span class="name">Hiển thị thông báo phiếu trả lại</span>
          </div>
        </div>
        <div class="item mart7 marb10">
          <div class="check-action">
            <input type="checkbox" v-model="thamso_hienthi.check_hienthi_thongbao" class="check" />
            <span class="name">Hiển thị thông báo khi có hồ sơ gửi đến</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import DefaultParameterAPI from './DefaultParameterAPI'
export default {
    name:'ThamSoMacDinh',
    components:{

    },
    data(){
        return{
          thamso_macdinh:{
            dichvu_vt:[],
            dichvu_vt_selected:null,
            donvi_ql:[],
            donvi_ql_selected:null,
            quan:[],
            quan_selected:null,
            phuong:[],
          },
          thamso_hoadon:{
            input_mayin_nhanvien:'',
            may_in_nhanvien:[],
            may_in_nhanvien_selected:null,
            may_in_vat:[],
            may_in_vat_selected:null,
            input_quyen:'',
            input_seri:'',
            check_giao_phieu:false,
            check_inhoadon:false,
            check_hienthi_thanhbentrai:false

          },
          thamso_hienthi:{
            check_hienthi_thongbao:false,
            radio_hienthi:1
          },
          count:0,
          dataCheckPhuong:[]
          // GlobalVar:{
          //   THONGBAO_HOSO_GUITOI:false
          // }
        }
    },
    methods:{
      async initDuLieu(){
        this.thamso_macdinh.dichvu_vt=await this.lay_danhsach_dichvu_vt()
        this.thamso_macdinh.donvi_ql=await this.lay_danhsach_donvi()
        
        this.thamso_macdinh.quan=[]
        this.thamso_macdinh.quan=await this.lay_danhsach_quan()
        this.thamso_macdinh.quan.unshift({
          QUAN_ID: 0,
          TEN_QUAN:'Tất cả'
        })
        this.thamso_macdinh.quan_selected=this.thamso_macdinh.quan[0]
      
        let donvi_nd=this.$root.token.getDonViID()
        await this.lay_danhsach_nhanvien_donvi(donvi_nd)
        //lấy tham số khởi tạo
        this.count=0
        await this.sp_lay_tham_so_mac_dinh('QUAN')
        await this.sp_lay_tham_so_mac_dinh('DICHVU_VT')
        await this.sp_lay_tham_so_mac_dinh('DONVIQL_ID')
        await this.sp_lay_tham_so_mac_dinh('INNGAY_HD_VAT')
        await this.sp_lay_tham_so_mac_dinh('GAOPHIEU_TD_KHI_TT')
        await this.sp_lay_tham_so_mac_dinh('HIENTHI_LISTBAR')
        await this.sp_lay_tham_so_mac_dinh('NOTIFY_MESSAGE_GDEN')
        await this.sp_lay_tham_so_mac_dinh('NOTIFY_MESSAGE_TLAI')
        await this.sp_lay_tham_so_mac_dinh('MAYIN_VAT')
        await this.sp_lay_tham_so_mac_dinh('NOTIFY_MESSAGE_HOSO_DIENTU')

        console.log('count', this.count)
        if(this.count==2){
          this.thamso_hienthi.radio_hienthi=1
        }
        

        if(this.thamso_macdinh.quan_selected!=null){
          if(this.thamso_macdinh.quan_selected.QUAN_ID==0){
            this.dataCheckPhuong=[0]
            this.thamso_macdinh.phuong=[]
            this.thamso_macdinh.phuong.push({
              PHUONG_ID: 0,
              TEN_PHUONG:'Chưa xác định',
              CHECK:true
            })
            return
          }
          await this.lay_danhsach_phuong_theo_quan(this.thamso_macdinh.quan_selected.QUAN_ID)
          if(this.thamso_macdinh.phuong.length>0){
            this.dataCheckPhuong=[]
            await this.sp_lay_tham_so_mac_dinh('PHUONG')
            //
            if(this.dataCheckPhuong.length>0){
              this.dataCheckPhuong.forEach((item)=>{
                const index=this.thamso_macdinh.phuong.findIndex(x=>x.PHUONG_ID==item)
                if(index>-1){
                  this.thamso_macdinh.phuong[index].CHECK=true
                }
              })
            }

          }
        }
        //this.thamso_hienthi.check_hienthi_thongbao=this.GlobalVar.THONGBAO_HOSO_GUITOI

        //lấy tham số phường và check list

      },
      async clearDuLieu(){
        this.thamso_macdinh={
            dichvu_vt:[],
            dichvu_vt_selected:null,
            donvi_ql:[],
            donvi_ql_selected:null,
            quan:[],
            quan_selected:null,
            phuong:[],
        }
        this.thamso_hoadon={
            input_mayin_nhanvien:'',
            may_in_nhanvien:[],
            may_in_nhanvien_selected:null,
            may_in_vat:[],
            may_in_vat_selected:null,
            input_quyen:'',
            input_seri:'',
            check_giao_phieu:false,
            check_inhoadon:false,
            check_hienthi_thanhbentrai:false
        }
        this.thamso_hienthi={
          check_hienthi_thongbao:false,
          radio_hienthi:1
        }
        this.count=0
        this.dataCheckPhuong=[]
      },
      async capnhat_thamso(){
        await this.capnhat_thamso_macdinh('MAYIN_VAT', this.thamso_hoadon.may_in_vat_selected!=null?this.thamso_hoadon.may_in_vat_selected:null)
        await this.capnhat_thamso_macdinh('QUAN', this.thamso_macdinh.quan_selected!=null?this.thamso_macdinh.quan_selected.QUAN_ID:null)
        await this.capnhat_thamso_macdinh('DICHVU_VT', this.thamso_macdinh.dichvu_vt_selected!=null?this.thamso_macdinh.dichvu_vt_selected.DICHVUVT_ID:null)
        await this.capnhat_thamso_macdinh('DONVIQL_ID', this.thamso_macdinh.donvi_ql_selected!=null?this.thamso_macdinh.donvi_ql_selected.donvi_id:null)
        
        await this.capnhat_thamso_macdinh('INNGAY_HD_VAT', this.thamso_hoadon.check_inhoadon?1:0)
        await this.capnhat_thamso_macdinh('GAOPHIEU_TD_KHI_TT', this.thamso_hoadon.check_giao_phieu?1:0)
        await this.capnhat_thamso_macdinh('HIENTHI_LISTBAR',this.thamso_hoadon.check_hienthi_thanhbentrai?1:0)

        await this.capnhat_thamso_macdinh('NOTIFY_MESSAGE_GDEN',this.thamso_hienthi.radio_hienthi==2?1:0)
        await this.capnhat_thamso_macdinh('NOTIFY_MESSAGE_TLAI',this.thamso_hienthi.radio_hienthi==3?1:0)
        await this.capnhat_thamso_macdinh('NOTIFY_MESSAGE_HOSO_DIENTU',this.thamso_hienthi.check_hienthi_thongbao?1:0)
        // tham số phường checked
        this.dataCheckPhuong=this.thamso_macdinh.phuong.filter(x=>x.CHECK==true).map(x=>{
          return x.PHUONG_ID
        })
        console.log('capnhat phuong', this.dataCheckPhuong)
        await this.capnhat_thamso_macdinh('PHUONG',this.dataCheckPhuong.toString())
        this.$toast.success('Cập nhật tham số thành công!')

      },
      changeHienThi(event){
        this.thamso_hienthi.radio_hienthi=event.target.value
      },
      async changeQuan(){
        //await this.lay_danhsach_phuong_theo_quan(this.thamso_macdinh.quan_selected.QUAN_ID)
        if(this.thamso_macdinh.quan_selected!=null){
          if(this.thamso_macdinh.quan_selected.QUAN_ID==0){
            this.dataCheckPhuong=[0]
            this.thamso_macdinh.phuong=[]
            this.thamso_macdinh.phuong.push({
              PHUONG_ID: 0,
              TEN_PHUONG:'Chưa xác định',
              CHECK:true
            })
            return
          }
          await this.lay_danhsach_phuong_theo_quan(this.thamso_macdinh.quan_selected.QUAN_ID)
          if(this.thamso_macdinh.phuong.length>0){
            this.dataCheckPhuong=[]
            //
            await this.sp_lay_tham_so_mac_dinh('PHUONG')
            if(this.dataCheckPhuong.length>0){
              this.dataCheckPhuong.forEach((item)=>{
                const index=this.thamso_macdinh.phuong.findIndex(x=>x.PHUONG_ID==item)
                if(index>-1){
                  this.thamso_macdinh.phuong[index].CHECK=true
                }
              })
            }
          }
        }
      },
      changeCheckBox(event, item){
        const index=this.thamso_macdinh.phuong.findIndex(x=>x.PHUONG_ID==item.PHUONG_ID)
        if(index>-1){
          this.thamso_macdinh.phuong[index].CHECK=event.target.checked
        }
      },
      async lay_danhsach_dichvu_vt(){
          try{
            this.loading(true)
            let response=await DefaultParameterAPI.lay_danhsach_dichvu_vt(this.axios)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              return response.data.data.sort((a, b)=>a.DICHVUVT_ID-b.DICHVUVT_ID)
            }else{
              return []
            }
          }catch(e){
            this.loading(false)
            return []
          }
      },
      async lay_danhsach_donvi(){
          try{
            this.loading(true)
            let response=await DefaultParameterAPI.lay_danhsach_donvi(this.axios)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              return response.data.data.sort((a, b)=>a.donvi_id-b.donvi_id)
            }else{
              return []
            }
          }catch(e){
            this.loading(false)
            return []
          }
      },
      async lay_danhsach_quan(){
          try{
            this.loading(true)
            let response=await DefaultParameterAPI.lay_danhsach_quan(this.axios)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              return response.data.data.sort(function(a, b) {
                  return a.QUAN_ID - b.QUAN_ID
              })
            }else{
              return []
            }
          }catch(e){
            this.loading(false)
            return []
          }
      },
      async lay_danhsach_phuong_theo_quan(quan_id){
          try{
            this.loading(true)
            this.thamso_macdinh.phuong=[]
            let response=await DefaultParameterAPI.lay_danhsach_phuong_theo_quan(this.axios, quan_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              // response.data.data.forEach((item)=>{
              //   this.thamso_macdinh.phuong.push({
              //     PHUONG_ID: item.PHUONG_ID,
              //     TEN_PHUONG: item.TEN_PHUONG,
              //     CHECK:false
              //   })
              // })
              this.thamso_macdinh.phuong=response.data.data.sort(function(a, b) {
                  return a.PHUONG_ID - b.PHUONG_ID
              }).map(item=>{
                return {
                  PHUONG_ID: item.PHUONG_ID,
                  TEN_PHUONG: item.TEN_PHUONG,
                  CHECK:false
                }
              })
            }else{
              this.thamso_macdinh.phuong=[]
            }
          }catch(e){
            this.$toast.error('Không load được danh sách phường theo quận')
          }finally{
            this.loading(false)
          }
      },
      async lay_danhsach_nhanvien_donvi(donvi_id){
          try{
            this.loading(true)
            this.thamso_hoadon.may_in_nhanvien=[]
            let response=await DefaultParameterAPI.lay_danhsach_nhanvien_donvi(this.axios, donvi_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.thamso_hoadon.may_in_nhanvien=response.data.data
              if(this.thamso_hoadon.may_in_nhanvien.length>0){
                this.thamso_hoadon.may_in_nhanvien_selected=this.thamso_hoadon.may_in_nhanvien[0]
              }
            }else{
              this.thamso_hoadon.may_in_nhanvien=[]
            }
          }catch(e){
            this.$toast.error('Không load được danh sách nhân viên theo đơn vị')
          }finally{
            this.loading(false)
          }
      },
      async sp_lay_tham_so_mac_dinh(ma_ts){
          try{
            this.loading(true)
            let response=await DefaultParameterAPI.sp_lay_tham_so_mac_dinh(this.axios, ma_ts)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data && response.data.data.length>0) {
              let result=response.data.data[0]
              if(ma_ts=='QUAN'){
                if(result.giatri!=null){
                  let giatri=Number(result.giatri)
                  let value=this.thamso_macdinh.quan.find(x=>x.QUAN_ID==giatri)
                  this.thamso_macdinh.quan_selected=value!=undefined?value:this.thamso_macdinh.quan[0]

                }else{
                  this.thamso_macdinh.quan_selected=this.thamso_macdinh.quan[0]
                }
                
              }else if(ma_ts=='DICHVU_VT'){

                if(result.giatri!=null){
                  let giatri=Number(result.giatri)
                  let value=this.thamso_macdinh.dichvu_vt.find(x=>x.DICHVUVT_ID==giatri)
                  this.thamso_macdinh.dichvu_vt_selected=value!=undefined?value:null

                }else{
                  this.thamso_macdinh.dichvu_vt_selected=null
                }

              }else if(ma_ts=='DONVIQL_ID'){
                if(result.giatri!=null){
                  let giatri=Number(result.giatri)
                  let value=this.thamso_macdinh.donvi_ql.find(x=>x.donvi_id==giatri)
                  this.thamso_macdinh.donvi_ql_selected=value!=undefined?value:null
                }else{
                  this.thamso_macdinh.donvi_ql_selected=null
                }

              }else if(ma_ts=='INNGAY_HD_VAT'){
                if(result.giatri=='1'){
                  this.thamso_hoadon.check_inhoadon=true
                }else{
                  this.thamso_hoadon.check_inhoadon=false
                }

              }else if(ma_ts=='GAOPHIEU_TD_KHI_TT'){
                if(result.giatri=='1'){
                  this.thamso_hoadon.check_giao_phieu=true
                }else{
                  this.thamso_hoadon.check_giao_phieu=false
                }

              }else if(ma_ts=='HIENTHI_LISTBAR'){
                if(result.giatri=='1'){
                  this.thamso_hoadon.check_hienthi_thanhbentrai=true
                }else{
                  this.thamso_hoadon.check_hienthi_thanhbentrai=false
                }

              }else if(ma_ts=='NOTIFY_MESSAGE_GDEN'){
                if(result.giatri=='1'){
                  this.thamso_hienthi.radio_hienthi=2
                }else{
                  this.count++
                  //this.thamso_hienthi.radio_hienthi=-1
                }
              }else if(ma_ts=='NOTIFY_MESSAGE_TLAI'){
                if(result.giatri=='1'){
                  this.thamso_hienthi.radio_hienthi=3
                }else{
                  this.count++
                  //this.thamso_hienthi.radio_hienthi=-1
                }
              }else if(ma_ts=='MAYIN_VAT'){
                // select giá trị máy in combobox đầu
              }else if(ma_ts=='NOTIFY_MESSAGE_HOSO_DIENTU'){
                if(result.giatri=='1'){
                  this.thamso_hienthi.check_hienthi_thongbao=true
                }else{
                  this.thamso_hienthi.check_hienthi_thongbao=false
                }
              }else if(ma_ts=='PHUONG'){
                if(result.giatri&&result.giatri.trim()!=''){
                  this.dataCheckPhuong=result.giatri.split(',').map(x=>{
                    return Number(x)
                  })
                  console.log(this.dataCheckPhuong)
                }else{
                  this.dataCheckPhuong=[]
                }
              }
            }else{
              //handle rỗng
              if(ma_ts=='QUAN'){
                this.thamso_macdinh.quan_selected=this.thamso_macdinh.quan[0]
              }else if(ma_ts=='DICHVU_VT'){
                let dichvu=this.thamso_macdinh.dichvu_vt.find(x=>x.DICHVUVT_ID==1)
                this.thamso_macdinh.dichvu_vt_selected=dichvu!=undefined?dichvu:null
              }else if(ma_ts=='DONVIQL_ID'){
                this.thamso_macdinh.donvi_ql_selected=null
              }else if(ma_ts=='INNGAY_HD_VAT'){
                this.thamso_hoadon.check_inhoadon=false
              }else if(ma_ts=='GAOPHIEU_TD_KHI_TT'){
                this.thamso_hoadon.check_giao_phieu=false
              }else if(ma_ts=='HIENTHI_LISTBAR'){
                this.thamso_hoadon.check_hienthi_thanhbentrai=false
              }else if(ma_ts=='NOTIFY_MESSAGE_GDEN'){
                this.count++
                //this.thamso_hienthi.radio_hienthi=-1
              }else if(ma_ts=='NOTIFY_MESSAGE_TLAI'){
                this.count++
                //this.thamso_hienthi.radio_hienthi=-1
              }else if(ma_ts=='MAYIN_VAT'){
                
              }else if(ma_ts=='NOTIFY_MESSAGE_HOSO_DIENTU'){
                this.thamso_hienthi.check_hienthi_thongbao=false
              }else if(ma_ts=='PHUONG'){
                this.dataCheckPhuong=[]
              }
            }
          }catch(e){
            this.$toast.error('Không load được thông tin tham số '+ma_ts)
          }finally{
            this.loading(false)
          }
      },
      async capnhat_thamso_macdinh(ma_ts, giatri){
          try{
            this.loading(true)
            let response=await DefaultParameterAPI.capnhat_thamso_macdinh(this.axios, ma_ts, giatri)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
              //success
            }else{
              this.$toast.error('Đã xảy ra lỗi, cập nhật thông tin tham số '+ma_ts+' không thành công')
            }
          }catch(e){
            this.$toast.error('Đã xảy ra lỗi, cập nhật thông tin tham số '+ma_ts+' không thành công')
          }finally{
            this.loading(false)
          }
      },
      lay_danhsach_may_in(){
        //chưa tìm thấy giải pháp vue

      },


    },
    
}
</script>
<style>
.multiselect, .multiselect__input, .multiselect__single {
    font-size: 1rem !important;
}
.multiselect__tags{
    min-height: 32px !important;
    border: 1px solid #ced4da !important;
    padding: 6px 32px 0 6px;
    border-radius: 4px;
}
.multiselect__placeholder {
    margin-bottom: 5px!important;
    padding-top: 0px!important;
}
.multiselect__select{
    top: 0px!important;
    padding: 10px 9px!important;
}
.multiselect--disabled{
  background: none!important;
}
.multiselect--disabled .multiselect__current, .multiselect--disabled .multiselect__select{
  background: none!important;
}
</style>