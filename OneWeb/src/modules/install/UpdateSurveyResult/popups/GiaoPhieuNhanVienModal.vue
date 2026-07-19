<template>
  <b-modal
    ref="popupGiaoPhieuNhanVien"
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
          <span class="icon one-notepad"></span> Giao phiếu nhân viên
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Danh sách nhân viên giao việc</div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="value w20 padt7">
                  <div class="check-action">
                    <input type="checkbox" v-model="checkTrungTam" @change="changeCheck(1)" class="check" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="key w80">Trung tâm</div>
                <div class="value">
                    <div class="select-custom" ref="ds_trungtam">
                        <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_trungtam'] }" ref="cboTrungTam"
                            v-model="trungtam_selected" class="select2"
                            :options="ds_trungtam.map(e=> ({id: e.donvi_id, text: e.ten_dv}))"
                            :disabled="!checkTrungTam"
                            @change="changeTrungTam"
                            >
                        </select2>
                    </div>  
                </div>
              </div>
            </div>

            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w60">Tổ TC</div>
                <div class="value">
                    <div class="select-custom" ref="ds_totc">
                        <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_totc'] }" ref="cboToTC"
                            v-model="totc_selected" class="select2"
                            :options="ds_totc.map(e=> ({id: e.donvi_id, text: e.ten_dv}))"
                            :disabled="disabledToTC"
                            @change="changeToTC"
                            >
                        </select2>
                    </div>  
                </div>
              </div>
            </div>

            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="value w20 padt7">
                  <div class="check-action">
                    <input type="checkbox" v-model="checkLoaiNV" @change="changeCheck(3)" class="check" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="key w100">Loại nhân viên</div>
                <div class="value">
                    <div class="select-custom" ref="ds_loai_nv">
                        <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_loai_nv'] }" ref="cboLoaiNV"
                            v-model="loai_nv_selected" class="select2"
                            :options="ds_loai_nv.map(e=> ({id: e.loainv_id, text: e.ten}))"
                            :disabled="!checkLoaiNV"
                            @change="changeLoaiNV"
                            >
                        </select2>
                    </div>  
                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="value w20 padt7">
                  <div class="check-action">
                    <input type="checkbox" v-model="checkTrungTamBH" @change="changeCheck(2)" class="check" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="key w80">Trung tâm BH</div>
                <div class="value">
                    <div class="select-custom" ref="ds_trungtam_bh">
                        <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_trungtam_bh'] }" ref="cboTrungTamBH"
                            v-model="trungtam_bh_selected" class="select2"
                            :options="ds_trungtam_bh.map(e=> ({id: e.donvi_id, text: e.ten_dv}))"
                            :disabled="!checkTrungTamBH"
                            @change="changeTrungTamBH"
                            >
                        </select2>
                    </div>  
                </div>
              </div>
            </div>
            <div class="col-sm-8 col-12">
              <div class="note text-main mart7">
                Cấu trúc nhắn tin phản hồi: LD [Cách] Mã tỉnh [Cách] ID phiếu
              </div>
            </div>
          </div>          
            <DataGrid 
              :columns="columnsNVGiaoViec" 
              :dataSource="dataSourcesNVGiaoViec"
              :showColumnCheckbox="false"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              :enabledSelectFirstRow="false"
              @rowDoubleClicked="rowDoubleClicked"

            />
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách nhân viên đã giao nhiệm vụ</div>
          <DataGrid 
              :columns="columnsNVDaGiaoViec" 
              :dataSource="dataSourcesNVDaGiaoViec"
              :showColumnCheckbox="false"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              :enabledSelectFirstRow="false"
              :commands="[{name: 'xoa', cssClass: 'btn btn-danger', width: 20, text: 'X',title:'Xóa'},]" 
              :commandColumn="{headerText: '', width: 80, cellCssClass: 'css1', firstColumn: false, show: true}"
              @commandClicked="commandClicked"
          />

        </div>
      </div>
      <NhanVienNhiemVuModal ref="nhanVienNhiemVuModal" :dsNhiemVu="dsNhiemVu" :item="rowNVGiaoViecSelected" @accept="acceptNhiemVuNhanVien"/>
    </div>
  </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import GiaoPhieuNhanVienAPI from './GiaoPhieuNhanVienAPI'
//frnGiaoPhieuNV chưa xong --> viết lại theo b-modal từ module contract/DeclareLandline
//https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=698575146&range=B295
import NhanVienNhiemVuModal from './NhanVienNhiemVuModal.vue'
export default {
    name:'GiaoPhieuNhanVienModal',
    props:{
      phieu_id:{
        type:Number,
        default:0
      },
      ds_phieu:{
        type:Array,
        default:()=>[]
      },
      donvi_id:{
        type:[ String, Number ],
        default:0
      },
      nhanvien_giao_id:{
        type:[ String, Number ],
        default:0
      },
      ngaygiao:{
        type:String,
        default:''
      },
      huonggiao_id:{
        type:Number,
        default:0
      },
      ma_tb:{
        type:String,
        default:''
      },
      //0 : giao việc bình thường, 1: giao việc thu hồi vật tư
      vkieugiao:{
        type:[ String, Number ],
        default:0
      }
    },
    components:{
        KTableV3,
        ActionTop,
        NhanVienNhiemVuModal
    },
    data(){
        return {
            actions:[
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'xoagiao',
                    name:'Xoá giao',
                    active: true,
                    icon_class:'one-trash'
                },
                {
                    id:'guisms',
                    name:'Gửi SMS',
                    active: true,
                    icon_class:'one-email'
                },
                {
                    id:'capnhat_kqxl',
                    name:'Cập nhật KQXL',
                    active: true,
                    icon_class:'one-reload1'
                }
                
            ],
            columnsNVGiaoViec:[
                {
                    fieldName: "ten_dv",
                    headerText: "Đơn vị",
                    allowFiltering: true
                },
                {
                    fieldName: "ma_nv",
                    headerText: "Mã nhân viên",
                    allowFiltering: true
                },
                {
                    fieldName: "ten_nv",
                    headerText: "Tên nhân viên",
                    allowFiltering: true
                },
                {
                    fieldName: "so_dt",
                    headerText: "Điện thoại",
                    allowFiltering: true
                },
                {
                    fieldName: "nhiemvu",
                    headerText: "Nhiệm vụ",
                    allowFiltering: true
                },
                {
                    fieldName: "ghichu",
                    headerText: "Ghi chú",
                    allowFiltering: true
                },
                {
                    fieldName: "ma_nd",
                    headerText: "Mã ND",
                    allowFiltering: true
                },
            ],
            dataSourcesNVGiaoViec:[],
            columnsNVDaGiaoViec:[
                {
                  fieldName: 'header_name',
                  headerText: '',
                  allowFiltering: true,
                  allowSorting: true,
                  isGroupedColumn: true
                },
                {
                  fieldName: 'ma_nv',
                  headerText: 'Mã nhân viên',
                  allowFiltering: true,
                  allowSorting: true,
                  width:100
                },
                {
                  fieldName: "ten_nv",
                  headerText: "Tên nhân viên",
                  allowFiltering: true,
                  allowSorting: true,
                  width:100
                },
                {
                  fieldName: "ngaygiao",
                  headerText: "Ngày giao",
                  allowFiltering: true,
                  allowSorting: true,
                  width:100
                },
                {
                  fieldName: "so_dt",
                  headerText: "Điện thoại",
                  allowFiltering: true,
                  allowSorting: true,
                  width:100
                },
                {
                  fieldName: "str_nhiemvu",
                  headerText: "Nhiệm vụ",
                  allowFiltering: true,
                  allowSorting: true,
                  width:120
                },
                {
                  fieldName: "kqxl",
                  headerText: "Kết quả XL",
                  allowFiltering: true,
                  allowSorting: true,
                  width:100
                },
                {
                  fieldName: "trangthai",
                  headerText: "Trạng thái",
                  allowFiltering: true,
                  allowSorting: true,
                  width:100
                },
                {
                  fieldName: "ghichu",
                  headerText: "Ghi chú",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                }
            ],
            dataSourcesNVDaGiaoViec:[],
            rowSelectedIdNVDaGiaoViec:-1,

            ds_trungtam:[],
            trungtam_selected:null,
            ds_trungtam_bh:[],
            trungtam_bh_selected:null,
            ds_totc:[],
            totc_selected:null,
            ds_loai_nv:[],
            loai_nv_selected:null,
            checkTrungTam:false,
            checkTrungTamBH:false,
            checkLoaiNV:false,
            rowNVGiaoViecSelected:null,
            dsNhiemVu:[],
            disabledToTC:true

        }
    },
    methods:{
        showModal() {
            this.$refs["popupGiaoPhieuNhanVien"].show()
        },
        hideModal() {
            this.$refs["popupGiaoPhieuNhanVien"].hide()
        },
        handleShowModal(){

          this.dataSourcesNVGiaoViec=[]
          this.dataSourcesNVDaGiaoViec=[]
          this.rowSelectedIdNVDaGiaoViec-1
          this.ds_trungtam=[]
          this.trungtam_selected=null
          this.ds_trungtam_bh=[]
          this.trungtam_bh_selected=null
          this.ds_totc=[]
          this.totc_selected=null
          this.ds_loai_nv=[]
          this.loai_nv_selected=null
          this.checkTrungTam=false
          this.checkTrungTamBH=false
          this.checkLoaiNV=false
          this.disabledToTC=true
          this.rowNVGiaoViecSelected=null
          this.dsNhiemVu=[]

          setTimeout(()=>{
            this.initDuLieu()
          }, 500)
        },
        onActionClick(action){
            if(action.id=='ghilai'){
              this.ghiLai()
            }else if(action.id=='xoagiao'){
              this.$confirm('Bạn thật sự muốn xóa tất cả các phiếu giao việc nhân viên không?', {
                confirmButtonText: "Đồng ý",
                cancelButtonText: "Hủy",
                type: "warning",
              }).then(()=>{
                this.xoaGiaoPhieuNV()
              })
            }else if(action.id=='guisms'){
              if(this.dataSourcesNVDaGiaoViec.length==0){
                this.$toast.error('Chưa có thông tin giao việc. Hãy kiểm tra lại!')
                return
              }
              this.$confirm('Bạn có chắc chắn gửi SMS đến các nhân viên thực hiện nhiệm vụ không?', {
                confirmButtonText: "Đồng ý",
                cancelButtonText: "Hủy",
                type: "warning",
              }).then(()=>{
                this.sendSMS_NV()
              })
            }else if(action.id=='capnhat_kqxl'){
              this.capNhatKQXL()
            }
        },
        // onSelectedRow(item){
        //   this.rowNVGiaoViecSelected=Object.assign({}, item)
          
        // },
        // doubleClickRow(item){
        //   this.rowNVGiaoViecSelected=Object.assign({}, item)
        //   this.$refs.nhanVienNhiemVuModal.showModal()
        // },
        // onSelectedRowNVDaGiaoViec(item){

        // },
        rowDoubleClicked(rowIndex, rowData){
          this.rowNVGiaoViecSelected=Object.assign({}, rowData)
          this.$refs.nhanVienNhiemVuModal.showModal()
        },
        async commandClicked(cmdName, rowData){
          console.log('commandClicked', cmdName)
          console.log('commandClicked', rowData)

          if(this.dataSourcesNVDaGiaoViec.length==0){
            this.$toast.error('Chưa có nhân viên được giao nhiệm vụ !')
            return
          }
          var data=this.dataSourcesNVDaGiaoViec
          const index=data.findIndex(x=>x.id==rowData.id)
          if(index>-1){
            data.splice(index,1)
            var json_giaophieu_nv=[]
            data.forEach((item)=>{
              json_giaophieu_nv.push({
                PHIEU_ID: this.phieu_id,
                NHANVIEN_TH_ID: item.nhanvien_th_id,
                NHIEMVU: item.nhiemvu,
                GHICHU: item.ghichu,
                NHANVIEN_GIAO_ID: item.nhanvien_giao_id,
                NGAYGIAO: item.ngaygiao,
                SO_DT: item.so_dt ? item.so_dt : "",
                STATUS: 0,
                NHIEMVU_ID: item.nhiemvu_id,
                KQXL_ID: 1,
              })
            })
            let result=await this.capnhat_kqxl_giaoviec(this.phieu_id, json_giaophieu_nv)
            if(result){
              this.$toast.success('Xoá nhân viên đã giao nhiệm vụ thành công !')
              await this.hienthi_danhsach_nv_dagiao()
              await this.hienthi_ds_nhanvien()
              this.$emit('giaoviec_success', true)
            }else{
              this.$toast.error('Có lỗi trong quá trình xoá')
            }
          }
        },
        acceptNhiemVuNhanVien(data){
          let index=this.dataSourcesNVGiaoViec.findIndex(x=>x.id==data.id)
          if(index>-1){
            Object.assign(this.dataSourcesNVGiaoViec[index], data)
          }
          this.dataSourcesNVGiaoViec=[].concat(this.dataSourcesNVGiaoViec)
        },
        async ghiLai(){
          let data=[]
          this.dataSourcesNVGiaoViec.forEach((item)=>{
            if(item.nhiemvu_selected!==undefined){
              item.nhiemvu_selected.forEach((nhiemvu)=>{
                data.push({
                  PHIEU_ID: this.phieu_id,
                  NHANVIEN_TH_ID: item.nhanvien_th_id,
                  NHIEMVU: nhiemvu.NHIEMVU,
                  GHICHU: item.ghichu,
                  NHANVIEN_GIAO_ID: this.nhanvien_giao_id,
                  NGAYGIAO: this.ngaygiao,
                  SO_DT: item.so_dt,
                  STATUS: 0,
                  NHIEMVU_ID: nhiemvu.NHIEMVU_ID,
                  KQXL_ID: null
                })
              })
            } 
          })

          console.log('data', data)

          let result = await this.fn_an_capnhat_giaophieu_nv({
            json_giaophieu_nv: data
          })
          if(result){
            this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công !')
            await this.hienthi_danhsach_nv_dagiao()
            await this.hienthi_ds_nhanvien()
            this.$emit('giaoviec_success', true)
          }else{
            this.$toast.error('Có lỗi trong quá trình giao việc nhân viên')
          }

        },
        async capNhatKQXL(){
          if(this.dataSourcesNVDaGiaoViec.length==0){
            this.$toast.error('Chưa có nhân viên được giao nhiệm vụ !')
            return
          }
          //tạo dữ liệu
          var json_giaophieu_nv=[]
          this.dataSourcesNVDaGiaoViec.forEach((item)=>{
            json_giaophieu_nv.push({
              PHIEU_ID: this.phieu_id,
              NHANVIEN_TH_ID: item.nhanvien_th_id,
              NHIEMVU: item.nhiemvu,
              GHICHU: item.ghichu,
              NHANVIEN_GIAO_ID: item.nhanvien_giao_id,
              NGAYGIAO: item.ngaygiao,
              SO_DT: item.so_dt ? item.so_dt : "",
              STATUS: 0,
              NHIEMVU_ID: item.nhiemvu_id,
              KQXL_ID: 1,
            })
          })
          
          let result=await this.capnhat_kqxl_giaoviec(this.phieu_id, json_giaophieu_nv)
          if(result){
            this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công !')
            await this.hienthi_danhsach_nv_dagiao()
          }else{
            this.$toast.error('Có lỗi trong quá trình cập nhật Kết quả xử lý')
          }
        },
        async xoaGiaoPhieuNV(){
          let result = await this.delete_giaophieu_nv(this.phieu_id)
          if(result){
            this.$toast.success('Xóa giao việc thành công')
            await this.hienthi_danhsach_nv_dagiao()
            await this.hienthi_ds_nhanvien()
            this.$emit('giaoviec_success', true)
          }else{
            this.$toast.error('Xóa giao việc thất bại')
          }
        },
        async sendSMS_NV(){
          for (let i = 0; i < this.dataSourcesNVDaGiaoViec.length; i++) {
            let item=this.dataSourcesNVDaGiaoViec[i]
            let vnoidung=this.ma_tb+' : ' + (item.ghichu!=null?item.ghichu:'')+' ; '+ (item.nhiemvu!=null?item.nhiemvu:'')
            if(item.so_dt!=null){
              let result=await this.sendSMS(item.so_dt, vnoidung)
            }
          }
          //Cập nhật trạng thái giao việc
          //all status truyền 1
          await this.fn_capnhat_trangthai_giaoviec_nv({
            vphieu_id: this.phieu_id,
            vstatus: 1
          })
          this.$toast.success('Đã gửi tin nhắn đến các nhân viên thực hiện!')
          await this.hienthi_danhsach_nv_dagiao()
        },
        async changeTrungTam(){
          if(this.checkTrungTam){
            this.totc_selected=null
            this.ds_totc=await this.ht_donvicon_loaidv_combobox(this.trungtam_selected, 5)
            if(this.ds_totc.length>0){
                this.totc_selected=this.ds_totc[0].donvi_id
            }
          }else{
            this.totc_selected=null
            this.ds_totc=[]
          }
          await this.hienthi_ds_nhanvien()
          
        },
        async changeTrungTamBH(){
          await this.hienthi_ds_nhanvien()
        },
        async changeToTC(){
          await this.hienthi_ds_nhanvien()
        },
        async changeLoaiNV(){
          await this.hienthi_ds_nhanvien()
        },
        async changeCheck(type){
          if(type==1){
            //trung tâm
            if(this.checkTrungTam){
              this.disabledToTC=false
              this.checkTrungTamBH=false
              this.totc_selected=null
              this.ds_totc=await this.ht_donvicon_loaidv_combobox(this.trungtam_selected, 5)
              if(this.ds_totc.length>0){
                this.totc_selected=this.ds_totc[0].donvi_id
              }
            }else{
              this.disabledToTC=true
              this.totc_selected=null
              this.ds_totc=[]
            }
            await this.hienthi_ds_nhanvien()
          }else if(type==2){
            //trung tâm bh
            if(this.checkTrungTamBH){
              this.disabledToTC=true
              this.checkTrungTam=false
            }
            await this.hienthi_ds_nhanvien()
          }else if(type==3){
            //loại nv
            await this.hienthi_ds_nhanvien()
          }
        },
        async initDuLieu(){
          //ds nhiệm vụ
          this.dsNhiemVu=await this.lay_ds_nhiemvu()
          //DONVIQL_LD = 3
          this.ds_trungtam=await this.ht_donvi_loaidv_combobox(900, 3)
          //PHONG_BANHANG_KV = 46
          this.ds_trungtam_bh=await this.ht_donvi_loaidv_combobox(900, 46)
          if(this.ds_trungtam.length>0){
            this.trungtam_selected=this.ds_trungtam[0].donvi_id
            if(this.checkTrungTam){
              //TRAM_VT = 5
              this.ds_totc=await this.ht_donvicon_loaidv_combobox(this.trungtam_selected, 5)
              if(this.ds_totc.length>0){
                this.totc_selected=this.ds_totc[0].donvi_id
              }
            }
          }
          if(this.ds_trungtam_bh.length>0){
            this.trungtam_bh_selected=this.ds_trungtam_bh[0].donvi_id
          }
          this.ds_loai_nv=await this.lay_ds_loainv()
          if(this.ds_loai_nv.length>0){
            this.loai_nv_selected=this.ds_loai_nv[0].loainv_id
            //

          }

          await this.hienthi_danhsach_nv_dagiao()

          await this.hienthi_ds_nhanvien()

        },
        async hienthi_danhsach_nv_dagiao(){
         // this.dataSourcesNVDaGiaoViec=[]

          this.dataSourcesNVDaGiaoViec=await this.lay_ds_nhanvien_theo_phieu_id(this.phieu_id, 3, this.nhanvien_giao_id)

          // data.map((item, key) => {
          //   item.kqxl = item.kqxl_id ? (item.kqxl_id == 1 ? "Tốt" : "Không tốt") : null
          //   item.stt =  key + 1
          // })

        },
        async hienthi_ds_nhanvien(){
          //this.dataSourcesNVGiaoViec=[]
          let loai_nv=(this.checkLoaiNV&&this.loai_nv_selected!=null)?this.loai_nv_selected:0

          let nv_id = 0
          if(this.checkTrungTam&&this.totc_selected!=null){
            nv_id=this.totc_selected
          }else if (this.checkTrungTamBH&&this.trungtam_bh_selected!= null){
            nv_id = this.trungtam_bh_selected
          }else{
            nv_id = this.nhanvien_giao_id
          }
          let kieu_giao=this.vkieugiao==0?1:this.vkieugiao==1?2:0

          this.dataSourcesNVGiaoViec=await this.sp_lay_ds_nhanvien_giaoviec_frmgiaoviecnv(this.phieu_id, nv_id, kieu_giao, loai_nv)
        },
        async ht_donvi_loaidv_combobox(donvi_id, loaidv_id){
            try{
                this.loading(true)
                let response=await GiaoPhieuNhanVienAPI.ht_donvi_loaidv_combobox(this.axios, donvi_id, loaidv_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async ht_donvicon_loaidv_combobox(donvi_id, loaidv_id){
            try{
                this.loading(true)
                let response=await GiaoPhieuNhanVienAPI.ht_donvicon_loaidv_combobox(this.axios, donvi_id, loaidv_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ds_loainv(){
            try{
                this.loading(true)
                let response=await GiaoPhieuNhanVienAPI.lay_ds_loainv(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ds_nhanvien_theo_phieu_id(phieu_id, kieu_id, nhanvien_id){
            try{
                this.loading(true)
                let response=await GiaoPhieuNhanVienAPI.lay_ds_nhanvien_theo_phieu_id(this.axios, phieu_id, kieu_id, nhanvien_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data.map((item, index)=>Object.assign(item,{
                      id:index+1,
                      header_name:':'+item.ma_nv+'-'+item.ten_nv+'-'+item.ten_dv,
                      kqxl:item.kqxl_id ? (item.kqxl_id == 1 ? "Tốt" : "Không tốt") : null
                    }))
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async sp_lay_ds_nhanvien_giaoviec_frmgiaoviecnv(phieu_id, nhanvien_id, kieugiao, loainv){
            try{
                this.loading(true)
                let response=await GiaoPhieuNhanVienAPI.sp_lay_ds_nhanvien_giaoviec_frmgiaoviecnv(this.axios, phieu_id, nhanvien_id, kieugiao, loainv)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data.map((item, index)=>Object.assign(item,{id:index+1}))
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async delete_giaophieu_nv(phieu_id){
            try{
                this.loading(true)
                let response=await GiaoPhieuNhanVienAPI.delete_giaophieu_nv(this.axios, phieu_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return true
                }else{
                    return false
                }
            }catch(e){
                this.loading(false)
                return false
            }
        },
        async fn_capnhat_trangthai_giaoviec_nv(data){
          try{
              this.loading(true)
              let response=await GiaoPhieuNhanVienAPI.fn_capnhat_trangthai_giaoviec_nv(this.axios, data)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                  return true
              }else{
                  return false
              }
          }catch(e){
              this.loading(false)
              return false
          }
        },
        async capnhat_kqxl_giaoviec(phieu_id, json_giaophieu_nv){
            try{
                this.loading(true)
                let response=await GiaoPhieuNhanVienAPI.capnhat_kqxl_giaoviec(this.axios, phieu_id, json_giaophieu_nv)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return true
                }else{
                    return false
                }
            }catch(e){
                this.loading(false)
                return false
            }
        },
        async sendSMS(sdt, noidung){
            try{
                this.loading(true)
                let response = await SurveyRequestAPI.sendSMS(this.axios, sdt, noidung)
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
        async lay_ds_nhiemvu(){
            try{
                this.loading(true)
                let response=await GiaoPhieuNhanVienAPI.lay_ds_nhiemvu(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async fn_an_capnhat_giaophieu_nv(data){
            try{
                this.loading(true)
                let response=await GiaoPhieuNhanVienAPI.fn_an_capnhat_giaophieu_nv(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
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
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>