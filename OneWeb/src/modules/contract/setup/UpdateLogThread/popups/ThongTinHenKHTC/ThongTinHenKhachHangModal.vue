<template>
    <div class="modal-content popup-box">
      

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">
        <div class="grid-stack-box">
          <div class="box-col box-form" style="width: calc(50% - 8px);">
            <div class="legend-title">Thông tin hẹn khách hàng</div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Hẹn từ ngày</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <ejs-datetimepicker class="form-control"
                        :value="tu_ngay"
                        @change="changeTuNgay"
                        format="dd/MM/yyyy HH:mm:ss"/>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Hẹn đến ngày</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <ejs-datetimepicker class="form-control"
                        :value="den_ngay"
                        @change="changeDenNgay"
                        format="dd/MM/yyyy HH:mm:ss"/>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Nội dung hẹn</div>
              <div class="value">
                <input type="text" v-model="input_noidung" class="form-control" />
              </div>
            </div>
            <div class="legend-title mart20">
              <div class="pull-left">Danh sách thuê bao</div>
              <div class="pull-right">
                <div class="check-action">
                  <input type="checkbox" v-model="checkTuDongNhanTin" class="check" />
                  <span class="name">Tự động nhắn cho KH</span>
                </div>
              </div>
              <div class="clearfix"></div>
            </div>

            <KTableV1 :columns="columns"
                :dataSources="dataSources"
                id="id"
                tableHeight="200px"
                @dataCheckeds="dataCheckDsThueBao"
                :allowFilter="false"
                :allowCheckBox="true"/>

          </div>
          <div class="gutter gutter-horizontal" style="width: 16px;"></div>
          
          <div class="box-col box-form" style="width: calc(50% - 8px);">
            <div class="legend-title">Danh sách hẹn khách hàng</div>
            <KTableV2 :columns="columnsHen"
                :dataSources="data_build"
                @sendSMS="sendSMS"
                tableHeight="350px"
                @onSelectedRow="onSelectedRowHen"
                fieldValue="rid"
                fieldGroup="ma_tb"
                :allowFilter="false"/>
          </div>
        </div>
      </div>

    </div>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import KTableV1 from './KTableV1.vue'
import KTableV2 from './KTableV2.vue'
import API from './API'
export default {
    name:'ThongTinHenKhachHangModal',
    props:{
      hdkh_id:{
        type:Number,
        default:0
      }
    },
    components:{
        ActionTop,
        KDatePicker,
        KTableV1,
        KTableV2
    },
    data(){
        return {
            actions:[
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus'
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'xoa',
                    name:'Xoá',
                    active: true,
                    icon_class:'one-trash'
                },
                {
                    id:'traphieu',
                    name:'Trả phiếu',
                    active: true,
                    icon_class:'one-file-back'
                }
            ],
            tu_ngay:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
            den_ngay:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
            columns:[
                {
                    field:'ma_tb',
                    label:'Số máy/Acc',
                    allowFilter:true,
                    checkBox:false
                },
                {
                    field:'loaihinh_tb',
                    label:'Loại hình',
                    allowFilter:true,
                    checkBox:false
                },
                {
                    field:'diemdau',
                    label:'Điểm đầu',
                    allowFilter:true,
                    checkBox:true
                },
                {
                    field:'diemcuoi',
                    label:'Điểm cuối',
                    allowFilter:true,
                    checkBox:true
                },
            ],
            dataSources:[],
            columnsHen:[
                {
                    field:'giohen_tu',
                    label:'Hẹn từ',
                    allowFilter:true,
                    checkBox:false
                },
                {
                    field:'giohen_den',
                    label:'Hẹn đến',
                    allowFilter:true,
                    checkBox:false
                },
                {
                    field:'noidung',
                    label:'Nội dung hẹn',
                    allowFilter:true,
                    checkBox:false
                },
                {
                    field:'ngay_cn',
                    label:'Ngày CN',
                    allowFilter:true,
                    checkBox:false
                },
                {
                    field:'nguoi_cn',
                    label:'Người CN',
                    allowFilter:true,
                    checkBox:false
                },
                {
                    field:'ten_dv',
                    label:'Đơn vị',
                    allowFilter:true,
                    checkBox:false
                },
                {
                    field:'diem_dau',
                    label:'Điểm đầu',
                    allowFilter:true,
                    checkBox:true
                },
                {
                    field:'diem_cuoi',
                    label:'Điểm cuối',
                    allowFilter:true,
                    checkBox:true
                },
                {
                    field:'button',
                    label:'',
                    allowFilter:true,
                    checkBox:false
                },
            ],
            dataSourcesHen:[],
            selectedRowHen:null,
            input_noidung:'',
            gdkh:false,
            hen_ld:0,  // = 0 : ; = 1 : GDV nào LHĐ thì GDV đó hẹn
            check_henld:false,
            nhantin_kythuat_sauhen:0,
            kt_thoigian_hen_thicong:false,
            quyen_duoc_hen:0,
            dataChecks:[],
            checkTuDongNhanTin:false

            
        }
    },
    computed:{
      data_build(){
        if(this.dataSourcesHen.length==0) return []
        let result=[]
        let temp = this.dataSourcesHen.map((x)=>Object.assign(x,{
                    diem_dau:x.diem_dau!=null?Number(x.diem_dau):0,
                    diem_cuoi:x.diem_cuoi!=null?Number(x.diem_cuoi):0,
                    giohen_tu:x.giohen_tu!=null?moment(x.giohen_tu,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):'',
                    giohen_den:x.giohen_den!=null?moment(x.giohen_den,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):'',
                    ngay_cn:x.ngay_cn!=null?moment(x.ngay_cn,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):''
        })).reduce((r, { ma_tb: rid, ...object }) => {
              var temp = r.find(o => o.rid === rid);
              if (!temp) r.push(temp = {rid, children: [] });
              temp.children.push(object);
              return r;
        }, [])
        //type: 1, 2 child
        temp.forEach((item)=>{
          result.push({
            id:item.rid,
            name:item.rid,
            isExpanded:true,
            checked:false,
            type:1
          })
          item.children.forEach((child)=>{
            result.push(Object.assign(child,{
              ma_tb:item.rid,
              isShow:true,
              checked:false,
              type:2
            }))
          })
        })
        return result
      }
    },
    methods:{
        show() {
            this.$refs["popupThongTinHenKhachHang"].show()
        },
        hideModal() {
            this.$refs["popupThongTinHenKhachHang"].hide()
        },
        onActionClick(action){
            if(action.id=='nhapmoi'){
              this.clickNhapMoi()
            }else if(action.id=='ghilai'){
              this.ghiDuLieu()
            }else if(action.id=='xoa'){
              this.clickXoaLichHen()
            }else if(action.id=='traphieu'){
              this.$toast.error('Tính năng hiện đang được xây dựng, thuộc [UR2.3.002] - Trả phiếu thi công')
            }
        },
        setActiveActions(kieu){
          this.actions.forEach((item)=>{
            if(item.id!='traphieu'){
                item.active=false
            }
          })
          if(kieu==-1){
            //bắt đầu
            this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
          }else if(kieu==0){
            //bắt đầu
            this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
          }else if(kieu==1){
            //thêm mới
            this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
          }else if(kieu==2){
            //huỷ
            this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
            this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
          }else if(kieu==3){
            this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
            this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
            this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
          }
        },

        handleShowModal(){
          //clear dữ liệu
          this.tu_ngay=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
          this.den_ngay=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
          this.selectedRowHen=null
          this.dataSources=[]
          this.dataSourcesHen=[]
          this.input_noidung=''
          this.gdkh=false
          this.hen_ld=0
          this.check_henld=false
          this.nhantin_kythuat_sauhen=0
          this.kt_thoigian_hen_thicong=false
          this.quyen_duoc_hen=0
          this.checkTuDongNhanTin=false

          setTimeout(()=>{
            this.initDuLieu()
          },200)
        },
        sendSMS(item){
          console.log('sendSMS',item)
          //send sms kh
        },
        changeTuNgay(data){
          this.tu_ngay=moment(new Date(data.value)).format('DD/MM/YYYY HH:mm:ss')
        },
        changeDenNgay(data){
          this.den_ngay=moment(new Date(data.value)).format('DD/MM/YYYY HH:mm:ss')
        },
        dataCheckDsThueBao(data){
          this.dataChecks=data
        },
        async ghiDuLieu(){
          let isInsert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active?false:true

          if(!(await this.kiemtra_dulieu(isInsert))){
            return
          }
          //tạo dữ liệu
          let dulieu=await this.getDuLieu(isInsert)

          let result = await this.sp_ins_hdtb_hen(JSON.stringify(dulieu.result),isInsert?1:2)
          if(result){
            this.$toast.success(isInsert?'Tạo lịch hẹn thành công':'Cập nhật lịch hẹn thành công')
            //send sms
            // if(this.checkTuDongNhanTin&&isInsert){
            //   if(dulieu.so_dt==''||dulieu.ds_matb==''||dulieu.rid==''){
            //     this.$toast.error('Không có danh sách thuê bao hoặc SĐT khách hàng để nhắn tin')
            //     return
            //   }else{
            //     //send sms hẹn kh


            //   }
            // }
            //
            await this.hienthi_ds_hdtb_hen()
          }
        },
        async kiemtra_dulieu(isInsert){
          if(this.quyen_duoc_hen==0){
            this.$toast.warning('User của bạn chưa được cấp quyền hẹn khách hàng.')
            return false
          }

          if(this.tu_ngay==null||this.tu_ngay==''){
            this.$toast.warning('Bạn chưa nhập thông tin Hẹn từ!')
            return false
          }
          if(this.den_ngay==null||this.den_ngay==''){
            this.$toast.warning('Bạn chưa nhập thông tin Hẹn đến!')
            return false
          }
          if(!isInsert){
            //Bỏ chế độ cập nhật
            this.$toast.warning('Không được cập nhật thông tin hẹn. Chỉ được hẹn mới hoặc Xóa')
            return false
          }else{

            if(this.dataChecks.length==0){
              this.$toast.warning('Bạn chưa chọn thuê bao để hẹn !')
              return false
            }
            let checkDaHoanThanh=this.dataChecks.filter(x=>x.tthd_id=="6")
            if(checkDaHoanThanh.length>0){
              this.$toast.warning('Thuê bao '+checkDaHoanThanh[0].ma_tb +' đã hoàn thành. Không được hẹn lắp đặt với thuê bao này')
              return false
            }
            let checkDaThiCong=this.dataChecks.filter(x=>x.tthd_id=="5")
            if(checkDaThiCong.length>0){
              this.$toast.warning('Thuê bao '+checkDaThiCong[0].ma_tb +' đã thi công xong. Không được hẹn lắp đặt với thuê bao này')
              return false
            }
            let checkDaThoaiTra=this.dataChecks.filter(x=>x.tthd_id=="7")
            if(checkDaThoaiTra.length>0){
              this.$toast.warning('Thuê bao '+checkDaThoaiTra[0].ma_tb +' đã thoái trả. Không được hẹn lắp đặt với thuê bao này')
              return false
            }

            if((this.hen_ld==1&&this.gdkh)||this.check_henld){
              let result=await this.lay_nhanvien_id_theo_hdkh_id(this.hdkh_id)
              if(result.length>0){
                let nhanvien_id=this.$root.getNhanVienID()
                if(result[0].nhanvien_id!=nhanvien_id){
                  this.$toast.warning('Bạn không được cập nhật ngày hẹn do user giao dịch khác lập hợp đồng !')
                  return false
                }
              }
            }

            if(moment(this.tu_ngay,'DD/MM/YYYY HH:mm:ss').add(10, 'minutes').isBefore(new Date())){
              this.$toast.warning('Hẹn từ ngày + 10p không được nhỏ hơn ngày hiện tại !')
              return false
            }
          }
          if(this.tu_ngay==null){
            this.$toast.warning('Hãy nhập ngày hẹn từ!')
            return false
          }
          if(this.den_ngay==null){
            this.$toast.warning('Hãy nhập ngày hẹn đến!')
            return false
          }
          if(moment(this.den_ngay,'DD/MM/YYYY HH:mm:ss').isBefore(moment(this.tu_ngay,'DD/MM/YYYY HH:mm:ss'))){
            this.$toast.warning('Hẹn từ ngày không được lớn hơn hẹn đến ngày !')
            return false
          }
          if(moment(this.den_ngay,'DD/MM/YYYY HH:mm:ss').isBefore(new Date())){
            this.$toast.warning('Hẹn đến ngày không được nhỏ hơn ngày hiện tại !')
            return false
          }
          if(this.kt_thoigian_hen_thicong){
            var songay = 7
            let dtSoNgay = await this.layso_ngay_hen(2)
            if(dtSoNgay.length>0){
              songay=dtSoNgay[0].songay
              if(moment(this.den_ngay,'DD/MM/YYYY HH:mm:ss').diff(moment(this.tu_ngay,'DD/MM/YYYY HH:mm:ss'), 'days')>songay){
                this.$toast.warning('Đối với phiếu thi công thời gian hẹn không quá '+songay+' ngày!')
                return false
              }
            }
          }else if(moment(this.den_ngay,'DD/MM/YYYY HH:mm:ss').isAfter(moment(new Date(),'DD/MM/YYYY HH:mm:ss').add(1,'years'))){
            this.$toast.warning('Ngày hẹn đến không được quá 1 năm so với ngày hiện tại !')
            return false
          }
          return true
        },
        async getDuLieu(isInsert){
          let result=[]
          var so_dt=''
          var rid=''
          var ds_matb=''
          if(isInsert){
            for (let i = 0; i < this.dataChecks.length; i++) {
              let key=await this.getKey('HDTB_HEN')
              let item=this.dataChecks[i]
              ds_matb+=item.ma_tb+','
              rid=key
              so_dt=item.so_dt
              result.push({
                RID:key,
                HDTB_ID:item.hdtb_id,
                GIOHEN_TU:this.tu_ngay,
                GIOHEN_DEN:this.den_ngay,
                NOIDUNG:this.input_noidung,
                NGUOI_CN:this.$root.token.getUserName(),
                NGAY_CN:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                MAY_CN:'localhost',
                DONVI_ID:this.$root.token.getDonViID(),
                NHANVIEN_ID:this.$root.token.getNhanVienID(),
                DAUCUOI_ID:item.diemcuoi?2:item.diemdau?1:0,
                KIEU:1
              })
            }
          }else{
            result.push({
              RID:this.selectedRowHen.rid,
              HDTB_ID:this.selectedRowHen.hdtb_id,
              GIOHEN_TU:this.tu_ngay,
              GIOHEN_DEN:this.den_ngay,
              NOIDUNG:this.input_noidung,
              NGUOI_CN:this.$root.token.getUserName(),
              NGAY_CN:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
              MAY_CN:'localhost',
              DONVI_ID:this.$root.token.getDonViID(),
              NHANVIEN_ID:this.$root.token.getNhanVienID(),
              DAUCUOI_ID:this.selectedRowHen.diemcuoi?2:this.selectedRowHen.diemdau?1:0,
              KIEU:0
            })
          }
          return {
            result:result,
            so_dt:so_dt,
            rid:rid,
            ds_matb:ds_matb
          }
        },
        
        onSelectedRowHen(item){
          if(item.type==1) return
          this.selectedRowHen=item
          //init dữ liệu
          //dd/MM/yyyy HH:mm:ss
          if(item.giohen_tu!=''){
            this.tu_ngay=item.giohen_tu
          }else{
            this.tu_ngay=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
          }
          if(item.giohen_den!=''){
            this.den_ngay=item.giohen_den
          }else{
            this.den_ngay=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
          }

          if(item.noidung!=null){
            this.input_noidung=item.noidung.toString()
          }else{
            this.input_noidung=''
          }

          this.setActiveActions(3)
        },
        async clickNhapMoi(){
          this.setActiveActions(1)
          this.tu_ngay=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
          this.den_ngay=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
          await this.hienthi_ds_hdtb_hen()
          await this.hienthi_ds_hdtb()
        },
        async clickXoaLichHen(){
          if(this.dataSourcesHen.length==0){
            this.$toast.warning('Không có thông tin hẹn để xóa !')
            return
          }
          if(this.selectedRowHen==null){
            this.$toast.warning('Bạn chưa chọn lần hẹn ở danh sách hẹn để xóa !')
            return
          }

          let maTinh=this.$root.token.getMaTinh()
          if(maTinh.toUpperCase()=='HCM'){
             this.$toast.warning('Bạn không được xóa thông tin hẹn !')
             return
          }

          let nguoi_cn=this.$root.token.getUserName()
          if(nguoi_cn!=this.selectedRowHen.nguoi_cn){
            this.$toast.warning('Bạn không được xóa lần hẹn do user khác tạo !')
            return
          }
          let tthd_id=this.selectedRowHen.tthd_id
          let matb=this.selectedRowHen.ma_tb
          if(tthd_id=='6'){
            this.$toast.warning('Thuê bao '+matb+' đã hoàn thành. Không được xóa thông tin hẹn')
            return
          }else if(tthd_id=='5'){
            this.$toast.warning('Thuê bao '+matb+' đã thi công xong. Không được xóa thông tin hẹn')
            return
          }else if(tthd_id=='7'){
            this.$toast.warning('Thuê bao '+matb+' đã thoái trả. Không được xóa thông tin hẹn')
            return
          }

          let rid=this.selectedRowHen.rid
          
          //xoa_hdtb_hen
          try{
            this.loading(true)
            let response = await API.xoa_hdtb_hen(this.axios, rid)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
              this.$toast.success('Xóa thông tin hẹn thành công !')
              this.selectedRowHen=null
              this.setActiveActions(-1)
              this.hienthi_ds_hdtb_hen()
            }else{
              this.$toast.error('Đã có lỗi xảy ra, xoá thông tin hẹn không thành công')
            }
          }catch(e){
            this.loading(false)
            if(e.data&&e.data.message){
              this.$toast.error(e.data.message)
            }else if(e.response&&e.response.data&&e.response.data.message){
              this.$toast.error(e.response.data.message)
            }else{
              this.$toast.error('Đã có lỗi xảy ra, xoá thông tin hẹn không thành công')
            }
          }
        },
        async initDuLieu(){
          await this.khoitao_thamso()
          await this.hienthi_ds_hdtb_hen()
          await this.hienthi_ds_hdtb()
          this.setActiveActions(-1)
        },
        async khoitao_thamso(){
          let dsThamSo=await this.getDsThamSo()
          if(dsThamSo.length>0){
            //HEN_LD
            let tt_henld=dsThamSo.find(x=>x.ma_ts=='HEN_LD')
            if(tt_henld!=undefined&&tt_henld.ten_ts=='1'){
              this.hen_ld=1
              let tt_nd=this.$root.token.getMaNhanVien()
              //NHANVIEN_KD_HENLD = 58
              let ds1=await this.sp_lay_nhanvien_theo_manv_loainv(tt_nd, 58)
              if(ds1.length>0){
                this.gdkh=true
              }
            }
            //CHECK_HEN_LD
            let tt_check_henld=dsThamSo.find(x=>x.ma_ts=='CHECK_HEN_LD')
            if(tt_check_henld!=undefined&&tt_check_henld.ten_ts=='1'){
              this.check_henld=true
            }
            //NHANTIN_KYTHUAT_KHIHEN
            let tt_nhantin_kythuat_khihen=dsThamSo.find(x=>x.ma_ts=='NHANTIN_KYTHUAT_KHIHEN')
            if(tt_nhantin_kythuat_khihen!=undefined&&tt_nhantin_kythuat_khihen.ten_ts=='1'){
              this.nhantin_kythuat_sauhen=1
            }
            //KT_THOIGIAN_HEN_BAOHONG
            let tt_kt_thoigian_hen_baohong=dsThamSo.find(x=>x.ma_ts=='KT_THOIGIAN_HEN_BAOHONG')
            if(tt_kt_thoigian_hen_baohong!=undefined&&tt_kt_thoigian_hen_baohong.ten_ts=='1'){
              this.kt_thoigian_hen_thicong=true
            }
            //CHECK_CAPNHAT_HEN
            let tt_check_capnhat_hen=dsThamSo.find(x=>x.ma_ts=='CHECK_CAPNHAT_HEN')
            if(tt_check_capnhat_hen!=undefined&&tt_check_capnhat_hen.ten_ts=='1'){
              //lay_ts_macdinh_dv
              let donvi_nd=this.$root.token.getDonViID()
              //quyen_duoc_hen
              let ttkd_capnhat_hen=await this.lay_ts_macdinh_dv(donvi_nd, 'TTKD_CAPNHAT_HEN')
              if(ttkd_capnhat_hen=='1'||ttkd_capnhat_hen=='TTKD_CAPNHAT_HEN'){
                this.quyen_duoc_hen=1
              }
            }
          }
          console.log('hen_ld', this.hen_ld)
          console.log('gdkh', this.gdkh)
          console.log('check_henld', this.check_henld)
          console.log('nhantin_kythuat_sauhen', this.nhantin_kythuat_sauhen)
          console.log('kt_thoigian_hen_thicong', this.kt_thoigian_hen_thicong)
          console.log('quyen_duoc_hen', this.quyen_duoc_hen)

        },
        async hienthi_ds_hdtb(){
          try{
            this.loading(true)
            this.dataSources=[]
            let response = await API.sp_lay_ds_hdtb_theo_hdkh(this.axios, this.hdkh_id)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.dataSources=response.data.data.map((x,index)=>Object.assign(x,{id:index+1, diemdau:false, diemcuoi: false}))
            }else{
              this.dataSources=[]
            }
          }catch(e){
            this.loading(false)
            if(e.data&&e.data.message){
              this.$toast.error(e.data.message)
            }else if(e.response&&e.response.data&&e.response.data.message){
              this.$toast.error(e.response.data.message)
            }else{
              this.$toast.error('Đã có lỗi xảy ra, không load được danh sách hdtb')
            }
          }
        },
        async hienthi_ds_hdtb_hen(){
          try{
            this.loading(true)
            this.dataSourcesHen=[]
            let response = await API.sp_lay_ds_hdtb_hen(this.axios, this.hdkh_id)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.dataSourcesHen=response.data.data.map(x=>Object.assign(x,{diemdau:(x.diemdau!=null&&x.diemdau=='1')?true:false, diemcuoi: (x.diemcuoi!=null&&x.diemcuoi=='1')?true:false}))
              if(this.dataSourcesHen.length==0){
                this.setActiveActions(1)
              }
            }else{
              this.dataSourcesHen=[]
              this.setActiveActions(1)
            }
          }catch(e){
            this.loading(false)
            if(e.data&&e.data.message){
              this.$toast.error(e.data.message)
            }else if(e.response&&e.response.data&&e.response.data.message){
              this.$toast.error(e.response.data.message)
            }else{
              this.$toast.error('Đã có lỗi xảy ra, không load được danh sách hẹn')
            }
          }
        },
        async getDsThamSo(){
            try{
                this.loading(true)
                let response = await API.sp_tt_thamso_md(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data!=null) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async sp_lay_nhanvien_theo_manv_loainv(ma_nv, loainv_id){
            try{
                this.loading(true)
                let response = await API.sp_lay_nhanvien_theo_manv_loainv(this.axios, ma_nv, loainv_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ts_macdinh_dv(donvi_id, ma_ts){
            try{
                this.loading(true)
                let response = await API.lay_ts_macdinh_dv(this.axios, donvi_id, ma_ts)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data!=null) {
                    return response.data.data
                }else{
                    return '0'
                }
            }catch(e){
                this.loading(false)
                return '0'
            }
        },
        async lay_nhanvien_id_theo_hdkh_id(hdkh_id){
          try{
              this.loading(true)
              let response = await API.lay_nhanvien_id_theo_hdkh_id(this.axios, hdkh_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  return response.data.data
              }else{
                  return []
              }
          }catch(e){
              this.loading(false)
              return []
          }
        },
        async layso_ngay_hen(loaiphieu){
          try{
              this.loading(true)
              let response = await API.layso_ngay_hen(this.axios, loaiphieu)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  return response.data.data
              }else{
                  return []
              }
          }catch(e){
              this.loading(false)
              return []
          }
        },

        async sp_ins_hdtb_hen(js_hdtb_hen, kieu){
          try{
              this.loading(true)
              let response = await API.sp_ins_hdtb_hen(this.axios, js_hdtb_hen, kieu)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                  return true
              }else{
                  if(response&&response.data&&response.data.message){
                    this.$toast.error(response.data.message)
                  }else{
                    this.$toast.error(kieu==1?'Đã xảy ra lỗi, tạo lịch hẹn không thành công':'Đã xảy ra lỗi, cập nhật lịch hẹn không thành công')
                  }
                  return false
              }
          }catch(e){
              this.loading(false)
              if(e.data&&e.data.message){
                this.$toast.error(e.data.message)
              }else if(e.response&&e.response.data&&e.response.data.message){
                this.$toast.error(e.response.data.message)
              }else{
                this.$toast.error(kieu==1?'Đã xảy ra lỗi, tạo lịch hẹn không thành công':'Đã xảy ra lỗi, cập nhật lịch hẹn không thành công')
              }
              return false
          }
        },
        async getKey(keyname){
            try{
                let response = await API.get_keys(this.axios, keyname)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        async sendSMSAPI(sdt, noidung){
            try{
                this.loading(true)
                let response = await API.sendSMS(this.axios, sdt, noidung)
                this.loading(false)
                if(response && response.data && response.data.errorCode==0) {
                    return true
                }else{
                    return false
                }
            }catch(e){
                this.loading(false)
                return false
            }
        },


    }

}
</script>