<template>
  <b-modal
    ref="popupGuiYC"
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
          <span class="icon one-notepad"></span>Tạo yêu cầu trên hệ thống
          CNTT360
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Nhập nội dung yêu cầu</div>

          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w110">Dự án <span class="red">(*)</span></div>
                <div class="value">
                  <div class="select-custom" ref="ds_duan">
                      <select2 :settings="{ dropdownParent: $refs['ds_duan'] }" 
                          v-model="duan_selected" class="select2"
                          :options="ds_duan"
                          :disabled="true"
                          >
                      </select2>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w110">
                  Kiểu yêu cầu <span class="red">(*)</span>
                </div>
                <div class="value">
                  <div class="select-custom" ref="ds_kieu_yc">
                      <select2 :settings="{ dropdownParent: $refs['ds_kieu_yc'] }" 
                          v-model="kieu_yc_selected" class="select2"
                          :options="ds_kieu_yc.map(e=> ({id: e.id, text: e.name}))"
                          @change="changeKieuYC"
                          >
                      </select2>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w110">
                  Hạn xử lý <span class="red">(*)</span>
                </div>
                <div class="value">
                  <div class="input-icon-right">
                    <k-date-picker v-model="han_xuly"/>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w110">
                  Nhóm dịch vụ <span class="red">(*)</span>
                </div>
                <div class="value">
                  <div class="select-custom" ref="ds_nhomdv">
                      <select2 :settings="{ dropdownParent: $refs['ds_nhomdv'] }" 
                          v-model="donvi_jija_selected" class="select2"
                          :options="ds_donvi_jira.map(e=> ({id: e.JR_DANHMUC_ID, text: e.JR_TEN_DANHMUC}))"
                          :disabled="true"
                          @change="changeDonViYC"
                          >
                      </select2>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w110">
                  Sản phẩm <span class="red">(*)</span>
                </div>
                <div class="value">
                  <div class="select-custom" ref="ds_sanpham">
                      <select2 :settings="{ dropdownParent: $refs['ds_sanpham'] }" 
                          v-model="sanpham_selected" class="select2"
                          :options="ds_sanpham.map(e=> ({id: e.SANPHAM_ID, text: e.LOAIHINH_TB}))"
                          :disabled="true"
                          >
                      </select2>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-sm-4 col-12" v-show="visableMucDoUuTien">
              <div class="info-row">
                <div class="key w110">
                  Mức độ ưu tiên <span class="red">(*)</span>
                </div>
                <div class="value">
                  <div class="select-custom" ref="ds_mucdo_uutien">
                      <select2 :settings="{ dropdownParent: $refs['ds_mucdo_uutien'] }" 
                          v-model="mucdo_uutien_selected" class="select2"
                          :options="ds_mucdo_uutien.map(e=> ({id: e.SANPHAM_ID, text: e.LOAIHINH_TB}))"
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
                <div class="key w110">
                  Người tạo <span class="red">(*)</span>
                </div>
                <div class="value">
                  <input type="text" class="form-control" :value="input_nguoi_tao" @change="e=>input_nguoi_tao=e.target.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w110">
                  Đơn vị YC <span class="red">(*)</span>
                </div>
                <div class="value">
                  <div class="select-custom" ref="ds_donvi_yc">
                      <select2 :settings="{ dropdownParent: $refs['ds_donvi_yc'] }" 
                          v-model="khachhang_jira_selected" class="select2"
                          :options="ds_khachhang_jira.map(e=> ({id: e.JR_KHACHHANG_ID, text: e.JR_TEN_KH}))"
                          
                          >
                      </select2>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w110">Tiêu đề <span class="red">(*)</span></div>
            <div class="value">
              <input type="text" :value="input_tieu_de" @change="e=>input_tieu_de=e.target.value" class="form-control" />
            </div>
          </div>

          <div class="info-row">
            <div class="key w110">Mô tả <span class="red">(*)</span></div>
            <div class="value">
              <textarea
                name=""
                id=""
                cols="30"
                rows="10"
                class="form-control"
                :value="input_mota" @change="e=>input_mota=e.target.value"
                style="height: 100px; resize: vertical">Tình trạng hỏng: Hỏng modem</textarea>
            </div>
          </div>

          <div class="info-row">
            <div class="key w110">File đính kèm</div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn" @click="chooseFile">
                    <span class="-ap icon-more_horiz"></span>
                </button>
                <input ref="fileInput" id="file" type="file" @change="onChangeFile($event)" style="display:none" class="form-control ">
                <input type="text" class="form-control" readonly :value="file_full_path" @change="e=>file_full_path=e.target.value"/>
              </div>
            </div>
          </div>

        </div>
      </div>
      <!-- Modal -->
      <QLBieuMauModal ref="qlBieuMau"/>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import moment from 'moment'
import API from './API'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import QLBieuMauModal from './QLBieuMauModal.vue'
//frmCreateIssue dùng api jira
//https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=698575146&range=B801
export default {
    name:'GuiYCModal',
    props:{
      vip_giamsat:{// Dùng chức năng tại tỉnh
        type:Boolean,
        default: false
      },
      tinh_id:{
        type:Number,
        default:-1
      },
      vphieu_id:{
        type:Number,
        default:-1
      },
      loai:{// 1 : PTTB, 2 : Báo hỏng
        type:Number,
        default:1
      },
      vloaihd_id:{
        type:Number,
        default:-1
      },
      user_tao:{
        type:String,
        default:''
      },
      hdtb_id:{
        type:Number,
        default:-1
      },

      // / kieu = 1 : pttb tai tỉnh
      // / kieu = 2 :  báo hỏng, 
      // / kieu = 3 : Vip gửi yc cho Tỉnh ( TTKD -> Vip (IT360) -> TTKD -> HTHS )
      // / kieu = 4 : Vip gửi yc cho Tỉnh báo hỏng ( TTKD -> Vip (IT360) -> TTKD -> HTHS )
      // / kieu = 5 : ttkd gửi yêu cầu service request (TTKD -> IT-360(yêu cầu vnpt-it hỗ trợ) -> HTHS)
      // / kieu = 6: ttkd gửi yêu cầu Change request
      kieu:{
        type:Number,
        default:1
      },
      baohong_id:{
        type:Number,
        default:-1
      },
      phieu_baohong_id:{
        type:Number,
        default:-1
      }
    },
    components:{
        ActionTop,
        KDatePicker,
        QLBieuMauModal
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
                    id:'bieumau',
                    name:'Biểu mẫu',
                    active: true,
                    icon_class:'one-file-arrow-right1'
                }
            ],
            JiraService: {
              DEFAULT_PROJECT_ID: 10101,
              CUSTOM_FIELD_DANHMUC_DV_ID: 12600,
              CUSTOM_FIELD_DANHMUC_KH_ID: 10329,
              SERVICE_USER: "dhsxkd_api",
              SERVICE_PASS: "*********"
            },
            ds_duan:[],
            duan_selected:null,
            input_nguoi_tao:'',
            input_tieu_de:'',
            input_mota:'',
            file_full_path:'',
            file:null,
            ds_donvi_jira:[],
            donvi_jija_selected:null,
            han_xuly:'',
            danhmuc_dv:[],
            ds_sanpham:[],
            sanpham_selected:null,
            ds_khachhang_jira:[],
            khachhang_jira_selected:null,
            loaihd_id:-1,
            ds_kieu_yc:[],
            kieu_yc_selected:null,
            ds_mucdo_uutien:[],
            mucdo_uutien_selected:null,
            visableMucDoUuTien:false,
            lastestIssue:null,
            v_user_tao:''

        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='ghilai'){
              this.KIEMTRA_VA_TAOISSUE()
            }else if(action.id=='bieumau'){
              this.$refs.qlBieuMau.showModal()
            }
        },
        showModal() {
            this.$refs["popupGuiYC"].show()
        },
        hideModal() {
            this.$refs["popupGuiYC"].hide()
        },
        async initDuLieu(){
          this.loaihd_id=this.vloaihd_id
          this.v_user_tao=this.user_tao
          //lay_thongtin_jira
          var sp_id = 0
          var dv_id = 0
          var loaitb_id = 0

          let ds_dv=await this.lay_thongtin_jira({
            kieu: this.kieu,
            hdtb_id: this.hdtb_id,
            baohong_id: this.baohong_id,
            tinh_id: this.$root.token.getPhanVungID()
          })
          
          if(ds_dv && ds_dv.length > 0){
              sp_id = Number(ds_dv[0].sanpham_id);
              dv_id = Number(ds_dv[0].jr_danhmuc_id);
              loaitb_id = Number(ds_dv[0].loaitb_id);
          }else{
            this.$toast.error('Dịch vụ này không tích hợp lên IT360 !')
            this.hideModal()
            return
          }

          this.input_nguoi_tao=this.JiraService.SERVICE_USER

          await this.NAP_PROJECT_MACDINH()
          await this.NAP_DS_DONVI_SANPHAM_JIRA()

          await this.NAP_DS_KHACHHANG_JIRA()

          let nhomdv=this.ds_donvi_jira.find(x=>x.JR_DANHMUC_ID==dv_id)
          if(nhomdv){
            this.donvi_jija_selected=dv_id
            await this.NAP_DS_DONVI_SANPHAM_JIRA(this.donvi_jija_selected)
          }
          let sanpham=this.ds_sanpham.find(x=>x.SANPHAM_ID==sp_id)
          if(sanpham){
            this.sanpham_selected=sp_id
          }

          if(this.kieu==2||this.kieu==4){
            await this.Load_Thongtin_baohong(this.baohong_id, this.phieu_baohong_id)
            this.loaihd_id=-1
          }else if(this.kieu==3){
            await this.VIP_NAP_THONGTIN_MOTA_TINH(this.hdtb_id, loaitb_id)
          }else{
            await this.NAP_THONGTIN_MOTA(this.hdtb_id, loaitb_id)
          }
          await this.NAP_DS_ISSUETYPE()
          
        },
        handleShowModal(){
          this.ds_duan=[]
          this.duan_selected=null
          this.input_nguoi_tao=''
          this.input_tieu_de=''
          this.input_mota=''
          this.file_full_path=''
          this.ds_donvi_jira=[]
          this.donvi_jija_selected=null
          this.han_xuly=moment(new Date()).format('DD/MM/YYYY')
          this.danhmuc_dv=[]
          this.ds_khachhang_jira=[]
          this.khachhang_jira_selected=null
          this.ds_kieu_yc=[]
          this.kieu_yc_selected=null
          this.ds_mucdo_uutien=[]
          this.mucdo_uutien_selected=null
          this.visableMucDoUuTien=false
          this.lastestIssue=null
          this.v_user_tao=''

          setTimeout(()=>{
            this.initDuLieu()
          }, 500)
        },
        chooseFile(){
            this.$refs.fileInput.value=null
            document.getElementById("file").click()
        },
        onChangeFile(event){
            this.file_full_path = event.target.value.split('\\').pop()
            this.file=event.target.files[0]
            // console.log('file', this.file)
            //this.file_full_path=(window.URL || window.webkitURL).createObjectURL(this.file)
            //this.readFileExcel()
        },
        async NAP_PROJECT_MACDINH(){
          let data_duan=await this.get_projects({
            projectidorkey:this.JiraService.DEFAULT_PROJECT_ID
          })
          if(data_duan){
            this.ds_duan=[{id: data_duan.id, text: data_duan.name}]
            this.duan_selected=this.ds_duan[0].id
          }
        },
        async NAP_DS_DONVI_SANPHAM_JIRA(){
          let data=await this.jira_get_customfield_options({
            customFieldId:this.JiraService.CUSTOM_FIELD_DANHMUC_DV_ID
          })
          if(data.length>0){
            this.danhmuc_dv=data
            this.ds_donvi_jira=data
            this.donvi_jija_selected=this.ds_donvi_jira[0].JR_DANHMUC_ID
            await this.NAP_DS_SANPHAM_THEO_DONVI(this.donvi_jija_selected)
          }
        },
        async NAP_DS_SANPHAM_THEO_DONVI(id){
          this.ds_sanpham=this.danhmuc_dv.filter(x=>x.JR_DANHMUC_ID==id)
          this.sanpham_selected=this.ds_sanpham[0].SANPHAM_ID
        },
        async NAP_DS_KHACHHANG_JIRA(){
          this.ds_khachhang_jira=[]
          let data=await this.jira_get_customfield_options({
            customFieldId:this.JiraService.CUSTOM_FIELD_DANHMUC_KH_ID
          })
          this.ds_khachhang_jira=data
          let ds_kh=this.ds_khachhang_jira.find(x=>x.JR_TINH_ID==this.$root.token.getPhanVungID())
          if(ds_kh){
            this.khachhang_jira_selected=ds_kh.JR_KHACHHANG_ID
          }
        },
        async changeDonViYC(){
          await this.NAP_DS_SANPHAM_THEO_DONVI(this.donvi_jija_selected)
        },
        async changeKieuYC(){

        },
        async KIEMTRA_VA_TAOISSUE(){
          if(!this.duan_selected){
            this.$toast.error('Chưa chọn dự án!')
            return
          }
          if(!this.kieu_yc_selected){
            this.$toast.error('Chưa chọn kiểu yêu cầu!')
            return
          }
          if(!this.donvi_jija_selected){
            this.$toast.error('Chưa chọn đơn vị tiếp nhận phiếu yêu cầu!')
            return
          }
          if(!this.sanpham_selected){
            this.$toast.error('Chưa chọn sản phẩm!')
            return
          }
          if(!this.khachhang_jira_selected){
            this.$toast.error('Chưa xác định được khách hàng gửi yêu cầu!')
            return
          } 
          if(!this.han_xuly||this.han_xuly==''){
            this.$toast.error('Hãy nhập thời hạn xử lý')
            return
          }
          if(moment(this.han_xuly,'DD/MM/YYYY').isBefore(moment(new Date()))){
            this.$toast.error('Hạn xử lý không được sau ngày hiện tại!')
            return
          }
          if(this.input_tieu_de.trim()==''){
            this.$toast.error('Chưa nhập tiêu đề!')
            return
          }
          if(this.input_mota.trim()==''){
            this.$toast.error('Chưa nhập mô tả!')
            return
          }
          //
          if(this.hdtb_id>0){
            let check1=await this.kiemtra_gui_yc_it360({
              vhdtb_id:this.hdtb_id,
              vkieu:1
            })
            if(check1!='OK'){
              this.$toast.error(check1)
              return
            }

            if((!this.file_full_path||this.file_full_path=='')&&this.loai==1){
              let check2=await this.kiemtra_attach_file_it360({
                vhdtb_id:this.hdtb_id
              })
              if(check2!='OK'){
                this.$toast.error(check2)
                return
              }
            }
          }
          let labels=await this.sp_lay_thongtin_label_jira({
            v_hdtb_id: this.hdtb_id,
            v_baohong_id: this.baohong_id
          })
          let mucdo_ut = '-1'
      
          let customfield_14300_id = this.donvi_jija_selected
          let customfield_14300_child_id = this.sanpham_selected
          let customfield_10329_id = this.khachhang_jira_selected
          let issuetype = this.kieu_yc_selected

          var data={
            description: this.input_mota,
            duedate: moment(this.han_xuly, "DD/MM/YYYY").format("YYYY-MM-DD"),
            issuetype: issuetype,
            labels: labels, 
            project: this.duan_selected,
            reporter: this.input_nguoi_tao,
            summary: this.input_tieu_de
          }
          if (mucdo_ut != "-1" && issuetype == "10207"){
              data.customfield_10328 = {
                  id: customfield_14300_id,
                  child:  {
                      id: customfield_14300_child_id
                  }
              } 
              data.customfield_10329 = { id : customfield_10329_id }
              data.ustomfield_12601 =  { id : mucdo_ut }
          }
          else if (mucdo_ut != "-1" && issuetype == "10211"){
            data.customfield_10328 = {
              id: customfield_14300_id,
              child: {
                  id: customfield_14300_child_id
              }
            }
            data.customfield_10329 =  { id: customfield_10329_id }
            data.customfield_10335 =  { id: mucdo_ut }
          }else{
              data.customfield_10328 =  { 
                id: customfield_14300_id,
                child:  {
                    id: customfield_14300_child_id
                }
              }
              data.customfield_10329 =  {
                  id: customfield_10329_id
              }
          }
          //creat_issue
          let resultCreate=await this.creat_issue(data)
          if(resultCreate&&!resultCreate.errors&&this.file_full_path!=''&&this.file){
            //upload file
            var formData = new FormData()
            formData.append('files', this.file) 
            formData.append('issueKeyOrID', resultCreate.data.key);
            formData.append('username', "")
            formData.append('password', "")
            let resultUpload=await this.add_issue_attachments(formData, { headers: { 'Content-Type': 'multipart/form-data' }})

            //update participant
            let dataParticipant={
              issueKeyOrID: resultCreate.data.key,
              login: {
                  password: "",
                  username: ""
              },
              participant:this.$root.token.getUserName()
            }
            let resultParticipant=await this.add_issue_participant(dataParticipant)
            if(resultParticipant&&resultParticipant.errors){
              this.$toast.error('Có lỗi khi thêm participant '+JSON.stringify(resultParticipant.errors))
              return
            }
          }

          if(resultCreate.errors){
            this.$toast.error('Có lỗi khi tạo yêu cầu lên hệ thống IT360. '+JSON.stringify(resultCreate.errors))
            return
          }
          this.$toast.success('Tạo yêu cầu thành công')

          //get_issue_info
          let issueInfo=await this.get_issue_info({
            issueKeyOrID: resultCreate.data.key
          })
          if(issueInfo.errors){
            this.lastestIssue={
              id:resultCreate.data.id,
              key:resultCreate.data.key,
              self:resultCreate.data.self
            }
          }else{
            this.lastestIssue=issueInfo.data
          }

          //Lưu id issue và hdtb_id vào bảng map
          this.v_user_tao = this.JiraService.SERVICE_USER

          if(this.hdtb_id>0){
            var reqGP = {
                  vloai: this.loai,
                  vhdtb_id: this.hdtb_id,
                  vbaohong_id: 0,
                  vphieu_id: this.vphieu_id,
                  vvip_xl_tinh: 1,
                  vtinh_id: this.$root.token.getPhanVungID(),
                  vissue_id: resultCreate.data.id,
                  vkey: resultCreate.data.key,
                  vuser_tao: this.JiraService.SERVICE_USER,
                  vtieude: this.input_tieu_de,
                  vnoidung: this.input_mota,
                  vnguoi_cn: this.$root.token.getUserName()
            }  
            if(this.kieu==3){
              reqGP.vvip_xl_tinh=1
              let rsMap=await this.fn_insert_jira_giaophieu_issue(reqGP)
              if(rsMap!='OK'){
                this.$toast.error("Có lỗi khi cập nhật yêu cầu vào hợp đồng trên ĐHSX:"+rsMap)
              }
            }else{
              reqGP.vvip_xl_tinh=0
              reqGP.vtinh_id=-1
              let rsMap=await this.fn_insert_jira_giaophieu_issue(reqGP)
              if(rsMap!='OK'){
                this.$toast.error("Có lỗi khi cập nhật yêu cầu vào hợp đồng trên ĐHSX:"+rsMap)
              }
              if(this.vip_giamsat){
                if(this.$root.token.getPhanVungID()!=100){
                  let ds_khdn=await this.lay_tt_giao_vip_giamsat_it360({
                    vhdtb_id: this.hdtb_id,
                    vdonvi_giao_id: this.$root.token.getDonViID(),
                    vnhanvien_giao_id: this.$root.token.getNhanVienID(),
                    vmay_cn: 'localhost',
                    vnguoi_cn: this.$root.token.getUserName(),
                    vip_cn: "127.0.0.1"
                  })
                  if(ds_khdn.length>0){
                    // Tinh_TC tinh_khdn = new BanCheoBase().tinh_thicong(100);
                    // string js_ds_khdn = Newtonsoft.Json.JsonConvert.SerializeObject(ds_khdn);
                    // new BanCheoBase().EXECUTE_NON_QUERY(tinh_khdn, "css_" + tinh_khdn.matinh + ".tinh_insert_vip_net", "vjson_khdn", js_ds_khdn);
                  }
                }else{
                  reqGP.vtinh_id=100
                  await this.giaophieuvip_cntt(reqGP)
                }
              }
            }
          }

          if(this.baohong_id>0){
            //sau này tính
          }

          this.hideModal()
        },
        async Load_Thongtin_baohong(baohong_id, phieu_baohong_id){
          this.input_tieu_de=''
          this.input_mota=''
          this.file_full_path=''
          let data=await this.sp_lay_thongtin_tieudo_mota_baohong_frm_create_issue({
            vphieu_id: phieu_baohong_id, 
            vkieu: this.kieu
          })
          if(data.length>0){
            this.input_tieu_de = data[0].tieude
            this.input_mota = data[0].mota
          }
        },
        async VIP_NAP_THONGTIN_MOTA_TINH(hdtb_id, loaitb_id){
          this.input_tieu_de=''
          this.input_mota=''
          this.file_full_path=''
          let data=await this.sp_vip_nap_thongtin_mota_tinh_frm_create_issue({
            hdtb_id: hdtb_id, 
            vnhanvien_id: this.$root.token.getNhanVienID()
          })
          if(data.length>0){
            this.input_tieu_de = data[0].tieude
            this.input_mota = data[0].mota
          }

        },
        async NAP_THONGTIN_MOTA(hdtb_id, loaitb_id){
          this.input_tieu_de=''
          this.input_mota=''
          this.file_full_path=''
          let dataTitle=await this.fn_lay_thongtin_tieude_jira({
            hdtb_id:hdtb_id
          })
          this.input_tieu_de=dataTitle
          let dataMota=await this.lay_thongtin_kh_it_360({
            vhdtb_id:hdtb_id,
            vloaitb_id:loaitb_id,
            vnhanvien_id:this.$root.token.getNhanVienID()
          })
          this.input_mota=dataMota
        },
        async NAP_DS_ISSUETYPE(){
          //get_issue_types_v2
          this.ds_kieu_yc=await this.get_issue_types_v2({
            vloaihd_id:this.loaihd_id
          })
          if(this.kieu==5){
            this.kieu_yc_selected=10207
          }else if(this.kieu==6){
            this.kieu_yc_selected=10211
          }else {
            this.kieu_yc_selected=this.ds_kieu_yc.length>0?this.ds_kieu_yc[0].id:null
          }
          //
          await this.changeKieuYC()


        },
        async get_projects(data){
            try{
                this.loading(true)
                let response = await API.get_projects(this.axios, data)
                this.loading(false)
                if(response && response.data&&response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async lay_thongtin_jira(data){
            try{
                this.loading(true)
                let response = await API.lay_thongtin_jira(this.axios, data)
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
        async jira_get_customfield_options(data){
            try{
                this.loading(true)
                let response = await API.jira_get_customfield_options(this.axios, data)
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
        async sp_lay_thongtin_tieudo_mota_baohong_frm_create_issue(data){
            try{
                this.loading(true)
                let response = await API.sp_lay_thongtin_tieudo_mota_baohong_frm_create_issue(this.axios, data)
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
        async sp_vip_nap_thongtin_mota_tinh_frm_create_issue(data){
            try{
                this.loading(true)
                let response = await API.sp_vip_nap_thongtin_mota_tinh_frm_create_issue(this.axios, data)
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
        async fn_lay_thongtin_tieude_jira(data){
            try{
                this.loading(true)
                let response = await API.fn_lay_thongtin_tieude_jira(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                this.loading(false)
                return ''
            }
        },
        async lay_thongtin_kh_it_360(data){
            try{
                this.loading(true)
                let response = await API.lay_thongtin_kh_it_360(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                this.loading(false)
                return ''
            }
        },
        async get_issue_types_v2(data){
            try{
                this.loading(true)
                let response = await API.get_issue_types_v2(this.axios, data)
                this.loading(false)
                if(response && response.data&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async sp_lay_mucdo_uutien_frmcreateissue(data){
            try{
                this.loading(true)
                let response = await API.sp_lay_mucdo_uutien_frmcreateissue(this.axios, data)
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
        async kiemtra_gui_yc_it360(data){
            try{
                this.loading(true)
                let response = await API.kiemtra_gui_yc_it360(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                this.loading(false)
                return ''
            }
        },
        async kiemtra_attach_file_it360(data){
            try{
                this.loading(true)
                let response = await API.kiemtra_attach_file_it360(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                this.loading(false)
                return ''
            }
        },
        async sp_lay_thongtin_label_jira(data){
            try{
                this.loading(true)
                let response = await API.sp_lay_thongtin_label_jira(this.axios, data)
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
        async creat_issue(data){
            try{
                this.loading(true)
                let response = await API.creat_issue(this.axios, data)
                this.loading(false)
                if(response && response.data&&response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async add_issue_attachments(data, header){
            try{
                this.loading(true)
                let response = await API.add_issue_attachments(this.axios, data, header)
                this.loading(false)
                if(response && response.data&&response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async add_issue_participant(data){
            try{
                this.loading(true)
                let response = await API.add_issue_participant(this.axios, data)
                this.loading(false)
                if(response && response.data&&response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async get_issue_info(data){
            try{
                this.loading(true)
                let response = await API.get_issue_info(this.axios, data)
                this.loading(false)
                if(response && response.data&&response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async fn_insert_jira_giaophieu_issue(data){
            try{
                this.loading(true)
                let response = await API.fn_insert_jira_giaophieu_issue(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    if(response&&response.data&&response.data.message){
                      return response.data.message
                    }else{
                      return 'Đã xảy ra lỗi'
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                  return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                  return e.response.data.message
                }else{
                  return 'Đã xảy ra lỗi'
                }
            }
        },
        async lay_tt_giao_vip_giamsat_it360(data){
            try{
                this.loading(true)
                let response = await API.lay_tt_giao_vip_giamsat_it360(this.axios, data)
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
        async giaophieuvip_cntt(data){
            try{
                this.loading(true)
                await API.giaophieuvip_cntt(this.axios, data)
                this.loading(false)
            }catch(e){
                this.loading(false)
                return []
            }
        },
        




    }
}
</script>