<style scoped>
.list-actions-top .list > li > div.button {
    display: block;
    padding: 2px 15px;
    color: #0176FF;
    border-left: 1px solid #BAE7FF;
    text-decoration: none;
    font-weight: 600;
    cursor: pointer;
}
</style>

<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :isModal="true" :animationSettings="animationSettings" ref="dialogCreateIssue" :position="position" :header="'Tạo yêu cầu trên hệ thống CNTT360'" showCloseIcon="true" width="75%" target="#app .main-wrapper">
    <div class="dialogCreateIssue"> 
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="javascript:;" v-on:click="btnGhiLai_Click">
                <span class="icon one-save"></span> Ghi lại
              </a>
            </li>
            <li>
                <a href="javascript:;" @click="btnBieuMau_Click">
                    <span class="icon one-file-arrow-right1"></span>Biểu mẫu
                </a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
              <div class="legend-title">Nhập nội dung yêu cầu</div>

              <div class="row">
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w110">Dự án <span class="red">(*)</span></div>
                          <div class="value">
                              <div class="select-custom"> 
                                  <select2 :options="controls.cboProject.list" v-model="controls.cboProject.value" required title="Bạn chưa chọn dự án" :disabled="true">
                                  </select2>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w110">Kiểu yêu cầu <span class="red">(*)</span></div>
                          <div class="value">
                              <div class="select-custom">
                                  <select2 :options="controls.cboIssueType.list" v-model="controls.cboIssueType.value" @change="cboIssueType_EditValueChanged">
                                  </select2>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w110">Hạn xử lý <span class="red">(*)</span></div>
                          <div class="value">
                              <div class="input-icon-right">
                                  <date-picker input-class="form-control" :format="dateFormat" :value-type="'format'" v-model="controls.dtpDuedate.value"></date-picker>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="row">
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w110">Nhóm dịch vụ  <span class="red">(*)</span></div>
                          <div class="value">
                              <div class="select-custom">
                                  <select2 :options="controls.cboDonViJira.list" v-model="controls.cboDonViJira.value" :disabled="true">
                                  </select2>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w110">Sản phẩm <span class="red">(*)</span></div>
                          <div class="value">
                              <div class="select-custom">
                                  <select2 :options="controls.cboSanPhamJira.list" v-model="controls.cboSanPhamJira.value" :disabled="true">
                                  </select2>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-4 col-12" v-if="controls.cboMucDoUT.visible">
                      <div class="info-row">
                          <div class="key w110">Mức độ ưu tiên</div>
                          <div class="value">
                              <div class="select-custom">
                                  <select2 :options="controls.cboMucDoUT.list" v-model="controls.cboMucDoUT.value">
                                  </select2>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="row">
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w110">Người tạo   <span class="red">(*)</span></div>
                          <div class="value">
                              <input type="text" class="form-control" v-model="state.txtNguoiTao" readonly="true">
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w110">Đơn vị YC  <span class="red">(*)</span></div>
                          <div class="value">
                              <div class="select-custom"> 
                                  <select2 :options="controls.cboKhachHangJira.list" v-model="controls.cboKhachHangJira.value" @change="cboDonViJira_SelectedIndexChanged">
                                  </select2>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="info-row">
                  <div class="key w110">Tiêu đề  <span class="red">(*)</span></div>
                  <div class="value">
                      <input ref="txtTieude" type="text" class="form-control" v-model="controls.txtTieude" required>
                  </div>
              </div>
              <div class="info-row">
                  <div class="key w110">Mô tả  <span class="red">(*)</span></div>
                  <div class="value">
                      <textarea ref="txtMota" id="txtMota" cols="30" rows="40" class="form-control" style="height: 300px;resize: vertical;" :Value="controls.txtMota" @input="textareaInput" required title="Bạn chưa nhập mô tả" :disabled="disableMoTa"></textarea>
                  </div>
              </div>
              <div class="info-row">
                  <div class="key w110">File đính kèm</div> 
                  <div class="value">
                      <div class="input-more-button">
                          <label class="btn" for="upload-file-1">
                             <span  class="-ap icon-more_horiz"></span>  
                             <input type="file" style="opacity: 0; width: 0px;" ref="file1" id="upload-file-1" @change="changeFileUpload" multiple> 
                          </label> 
                          <input ref="txtFileDinhKem" type="text" class="form-control " v-model="controls.txtFileDinhKem" readonly="true"> 
                      </div>
                  </div>
              </div>
          </div>
      </div>
      <QL_BieuMau ref="popupQL_BieuMau"/>
    </div>
  </ejs-dialog>
</template>
<script> 
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment' 
import api from './PopUpApi' 
import QL_BieuMau from './QL_BieuMau.vue'
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions"; 
  
export default {
  components: { 
    moment,
    DatePicker, 
    api, 
    QL_BieuMau
  },
  name: 'CreateIssue',
  data() {
    return {
      disableMoTa: false,
      Loading: false,
      dateFormat: 'DD/MM/YYYY',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      showTimePanel: false,
      tabIndex: 0,
      state: { 
      }, 
      tt_nd: {
        nhanvien_id:  this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format("DD/MM/YYYY hh:mm A"),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
          "DD/MM/YYYY hh:mm A"
        ),
        ten_dv: "",
        nguoi_cn: this.$root.token.getUserName()
      },
      JiraService: {
        DEFAULT_PROJECT_ID: 10101,
        CUSTOM_FIELD_DANHMUC_DV_ID: 12600,
        CUSTOM_FIELD_DANHMUC_KH_ID: 10329,
        SERVICE_USER: "dhsxkd_api",
        SERVICE_PASS: "*********"
      }, 
      controls: {
        cboProject: {
          list: [],
          value: 0
        },
        cboIssueType: {
          list: [],
          value: 0
        },
        cboSanPhamJira: {
          list: [],
          value: 0
        },
        cboDonViJira: {
          list: [],
          value: 0
        },
        cboKhachHangJira: {
          list: [],
          value: 0
        },
        cboMucDoUT: {
          list: [],
          value: 0,
          visible: true
        },
        dtpDuedate: {
          enabled: true,
          value: moment(new Date()).format("DD/MM/YYYY"),
        }, 
        txtFileDinhKem: "",
        txtTieude: "",
        txtMota: "",
        txtMotaTemp: ""
      }
    }
  },
  created: async function () {
     
  },
  mounted: function () { 
    
  },
  watch: {
     
  },
  methods: {
 
    dialogClose() {},
    openDialog(data) { 
      try{ 
        if(!data){
          return;
        }
        this.controls.txtTieude = "";
        this.controls.txtMota = "";
        this.controls.txtMotaTemp = ""; 
        this.controls.txtFileDinhKem = "";
        this.$refs.file1.value= '';

        this.state =  {
          baohong_id: data.baohong_id ? data.baohong_id : -1,
          phieu_baohong_id: data.phieu_baohong_id ? data.phieu_baohong_id : -1,
          hdtb_id: data.hdtb_id ? data.hdtb_id : -1,
          phieu_id: data.phieu_id ? data.phieu_id : -1,
          loai: data.loai ? data.loai : 1, 
          kieu: data.kieu ?  data.kieu : 1,
          vip_giamsat: data.vip_giamsat ? data.vip_giamsat : false,
        };
        console.log(this.state);
        this.$refs.dialogCreateIssue.show();
        this.pageLoad();
      }catch(ex){
        commonFn.showException(this, ex);
      }
    },
    hideDialog () {
      this.$refs.dialogCreateIssue.hide()
    },
    async textareaInput(e){ 
      this.controls.txtMota = e.target.value  
    },
    async pageLoad(){
      try{ 
          this.loading(true) 
          this.tt_nd.may_cn = await this.$root.token.getMachine();
          this.tt_nd.ip_cn = await this.$root.token.getIP();
          
          this.$refs.txtMota.value = "";

          let ds_dv = []
          let sp_id = 0;
          let dv_id = 0;
          let loaitb_id = 0;
          let hdtb_id = this.state.hdtb_id; 
          
          let req = {
            "kieu": this.state.kieu,
            "hdtb_id": this.state.hdtb_id,
            "baohong_id": this.state.baohong_id,
            "tinh_id": this.tt_nd.phanvung_id
          }
          ds_dv = this.GetData(await api.lay_thongtin_jira(this.axios, req)); 
          
          if(ds_dv && ds_dv.length > 0){
              sp_id = Number(ds_dv[0]["sanpham_id"]);
              dv_id = Number(ds_dv[0]["jr_danhmuc_id"]);
              loaitb_id = Number(ds_dv[0]["loaitb_id"]);
              //region cuongpq kiểm tra ẩn mô tả với loại hình dịch vụ CNTT
              let svalue = this.GetData(await this.axios.get(`/web-hopdong/vbn/fn_kiemtra_chophep_sua_mota_it360?vloaitb_id=${loaitb_id}`));
              if (svalue != "OK")
              {
                this.disableMoTa = true;
              }
              else {
                this.disableMoTa = false;
              }
              //end region
          }
          else{
            this.$toast.warning("Dịch vụ này không tích hợp lên IT360 !");
            this.loading(false);
            this.hideDialog();
            return;
          }
    
          this.state.txtNguoiTao = this.JiraService.SERVICE_USER;
          await this.NAP_PROJECT_MACDINH(); 
          await this.NAP_DS_DONVI_SANPHAM_JIRA(dv_id);
          await this.NAP_DS_KHACHHANG_JIRA();

          // for (var it of this.controls.cboDonViJira.list)
          // {
          //     if (it.id == dv_id)
          //     {
          //         this.controls.cboDonViJira.value = it.id;
          //         break;
          //     }
          // } 
          
          for (var it of this.controls.cboSanPhamJira.list)
          {
              if (it.id == sp_id)
              { 
                  this.controls.cboSanPhamJira.value = it.id;
                  break;
              }
          }
           
          if (this.state.kieu == 2 || this.state.kieu == 4)
          {
              await this.Load_Thongtin_baohong(this.state.baohong_id, this.state.phieu_baohong_id);
              this.state.loaihd_id = -1;
          }
          else if (this.state.kieu == 3)
              await this.VIP_NAP_THONGTIN_MOTA_TINH(hdtb_id, loaitb_id);
          else
              await this.NAP_THONGTIN_MOTA(hdtb_id, loaitb_id);
          await this.NAP_DS_ISSUETYPE();

          this.loading(false); 
      }catch(ex){
        commonFn.showException(this, ex);
      }
      this.loading(false)  
    },  
    async cboIssueType_EditValueChanged(e){

      try{
        let data = this.GetData(await api.sp_lay_mucdo_uutien_frmcreateissue(this.axios, {vissue_type_id: e}));
        if(data && data.length > 0){
          this.controls.cboMucDoUT.visible = true;
          this.controls.cboMucDoUT.list = data.map((x) => {return {id: x.mdut_id, text: x.mucdo_ut}});
          this.controls.cboMucDoUT.value = this.controls.cboMucDoUT.list[0].id; 
        }
        else
        {
          this.controls.cboMucDoUT.visible = false;
          this.controls.cboMucDoUT.list = [];
        }
      }catch(ex){
        commonFn.showException(this, ex);
      } 
    },
    async NAP_PROJECT_MACDINH(){
      try{  
        let data = this.GetData(await api.Get_Project(this.axios, {projectidorkey: this.JiraService.DEFAULT_PROJECT_ID}));
      
        if(data){
          this.controls.cboProject.list = [{id: data.id*1, text: data.name}];
          this.controls.cboProject.value = this.controls.cboProject.list[0].id; 
        }         
      }catch(ex){
        commonFn.showException(this, ex); 
      } 
    },
    async NAP_DS_ISSUETYPE(){
      try{  
        let data = this.GetData(await api.Get_IssueTypes_V2(this.axios, { "vloaihd_id": this.state.loaihd_id }));
        if(data && data.length > 0){
          this.controls.cboIssueType.list = data.map((x)=> {return {id: x.id*1, text: x.name}});
          this.controls.cboIssueType.value = this.controls.cboIssueType.list[0].id;        
          if (this.state.kieu == 5)
          {
            this.controls.cboIssueType.value = 10207; 
          }
          if (this.state.kieu == 6)
          {
            this.controls.cboIssueType.value = 10211; 
          }
          await this.cboIssueType_EditValueChanged(this.controls.cboIssueType.value);
        }         
      }catch(ex){
        commonFn.showException(this, ex);
      } 
    },
    async NAP_DS_DONVI_SANPHAM_JIRA(dv_id){  
      let data = this.GetData(await api.jira_get_customfield_options(this.axios, { "customFieldId": this.JiraService.CUSTOM_FIELD_DANHMUC_DV_ID })); 
      
      if(data && data.length > 0){
        this.state.danhmuc_dv = data;
        this.controls.cboDonViJira.list = data.map((x) => {return {id: x.JR_DANHMUC_ID, text: x.JR_TEN_DANHMUC }});
        this.controls.cboDonViJira.value = dv_id; 
        await this.NAP_DS_SANPHAM_THEO_DONVI(dv_id);
      }
      
    },
    async NAP_DS_SANPHAM_THEO_DONVI(danhmuc_id){
       console.log("danhmuc_id", danhmuc_id)
       if(!danhmuc_id) return;
       if(!this.state.danhmuc_dv) return;
       let donviObj = this.state.danhmuc_dv.filter(x=>x.JR_DANHMUC_ID == danhmuc_id); 
       if(donviObj && donviObj.length > 0){
          this.controls.cboSanPhamJira.list = donviObj.map((x) => {return {id: x.SANPHAM_ID, text: x.LOAIHINH_TB}});
          this.controls.cboSanPhamJira.value = this.controls.cboSanPhamJira.list[0].id;
       }
    }, 
    async NAP_DS_KHACHHANG_JIRA(){ 
      let data = this.GetData(await api.jira_get_customfield_options(this.axios, { "customFieldId": this.JiraService.CUSTOM_FIELD_DANHMUC_KH_ID }));   
      if(data && data.length > 0){
        this.controls.cboKhachHangJira.list = data.map((x) =>  {return {id: x.JR_KHACHHANG_ID, text: x.JR_TEN_KH }});
        let kh =  data.find(x => x.JR_TINH_ID == this.tt_nd.phanvung_id); 
        if(kh)
          this.controls.cboKhachHangJira.value = kh.JR_KHACHHANG_ID; 
      }
    },
    async VIP_NAP_THONGTIN_MOTA_TINH(hdtb_id, loaitb_id){ 
      let data = this.GetData(await api.sp_vip_nap_thongtin_mota_tinh_frm_create_issue(this.axios, { "hdtb_id": this.state.hdtb_id, "vnhanvien_id": this.tt_nd.nhanvien_id })); 
   
      if(data && data.length > 0){
        this.controls.txtTieude = data[0].tieude; 
        this.controls.txtMota = data[0].mota;   
      } 
    },
    async NAP_THONGTIN_MOTA(hdtb_id, loaitb_id){
       try{ 
          let data = this.GetData(await api.fn_lay_thongtin_tieude_jira(this.axios, { "hdtb_id": hdtb_id }));  
          this.controls.txtTieude = data;
          let data1 = this.GetData(await api.lay_thongtin_kh_it_360(this.axios, { "vhdtb_id": hdtb_id, "vloaitb_id": loaitb_id, "vnhanvien_id": this.tt_nd.nhanvien_id})); 
          this.controls.txtMota = data1;  
          this.$refs.txtMota.value = data1;
       }catch(ex){
         console.error(ex);
       } 
    },
    async Load_Thongtin_baohong(vbaohong_id, vphieu_id){
      this.controls.txtTieude = "";
      this.controls.txtMota = "";
      this.controls.txtFileDinhKem = "";
      
      let data = this.GetData(await api.sp_lay_thongtin_tieudo_mota_baohong_frm_create_issue(this.axios, { "vphieu_id": vphieu_id, "vkieu": this.state.kieu })); 
      if(data && data.length > 0){
        data = commonFn.LowerCasePropertyList(data)
        this.controls.txtTieude = data[0].tieude;
        this.controls.txtMota = data[0].mota;
        this.$refs.txtMota.value = data[0].mota;
      }
    },
    async cboDonViJira_SelectedIndexChanged(e)
    { 
       await this.NAP_DS_SANPHAM_THEO_DONVI(e);
    },
    async btnBieuMau_Click(){
      this.$refs.popupQL_BieuMau.openDialog();
    },
    async  btnGhiLai_Click(){ 
      await this.KIEMTRA_VA_TAOISSUE(); 
    },
    async KIEMTRA_VA_TAOISSUE(){
      
      if(this.controls.txtTieuDe === ""){
        this.$toast.error("Bạn chưa nhập tiêu đề");
        this.$refs.txtTieuDe.focus();
        return;
      }
     
      if(this.controls.txtMota === ""){
        this.$toast.error("Bạn chưa nhập mô tả");
        this.$refs.txtMota.focus();
        return;
      }
      console.log("KIEMTRA_VA_TAOISSUE");
      this.loading(true);
      try{  
        if(this.state.hdtb_id && this.state.hdtb_id > 0){
          let svalue = this.GetData(await api.kiemtra_gui_yc_it360(this.axios, {"vhdtb_id": this.state.hdtb_id, "vkieu": 1}));
          if (svalue != "OK")
          {
              this.$toast.warning(svalue); 
              this.loading(false)
              return;
          }

          if ((!this.controls.txtFileDinhKem || this.controls.txtFileDinhKem == "")  && this.state.loai == 1)
          {
              svalue = this.GetData(await api.kiemtra_attach_file_it360(this.axios, {"vhdtb_id": this.state.hdtb_id}));
              if (svalue != "OK")
              {
                  this.$toast.warning(svalue);
                  this.$refs.txtFileDinhKem.focus();
                  this.loading(false)
                  return;
              }
          }
        }
        
        //lấy thông tin lables
        let labels = this.GetData(await api.sp_lay_thongtin_label_jira(this.axios, { "v_hdtb_id": this.state.hdtb_id, "v_baohong_id": this.state.baohong_id }));
        console.log("labels", labels) 
        let mucdo_ut = (this.controls.cboMucDoUT.list.length > 0 ? this.controls.cboMucDoUT.value : "-1");
        if(labels && labels.length > 0){
          labels = [labels[0].MA_KH,  labels[0].MA_TB, labels[0].LOAIHINH]
        }else labels = [];
        let customfield_14300_id = this.controls.cboDonViJira.value;
        let customfield_14300_child_id = this.controls.cboSanPhamJira.value;
        let customfield_10329_id = this.controls.cboKhachHangJira.value;
        let issuetype = this.controls.cboIssueType.value;
        let req = { 
          "description": this.controls.txtMota,
          "duedate": moment(this.controls.dtpDuedate.value, "DD/MM/YYYY").format("YYYY-MM-DD"),
          "issuetype": issuetype.toString(),
          "labels": labels, 
          "project": this.controls.cboProject.value.toString(),
          "reporter": this.state.txtNguoiTao,
          "summary": this.controls.txtTieude
        }

        // CODE theo DHSXKD cu
        // if (mucdo_ut != "-1" && issuetype == "10207"){
        //   req.customfield_10328 =  {
        //                                 id: customfield_14300_id,
        //                                 child:  {
        //                                     id: customfield_14300_child_id
        //                                 }
        //                             } 
        //     req.customfield_10329 = { id : customfield_10329_id }
        //     req.ustomfield_12601 =  { id : mucdo_ut }
        // }
        // else if (mucdo_ut != "-1" && issuetype == "10211"){
        //   req.customfield_10328 =   {
        //                                   id: customfield_14300_id,
        //                                   child: {
        //                                       id: customfield_14300_child_id
        //                                   }
        //                               }
        //     req.customfield_10329 =  { id: customfield_10329_id }
        //     req.customfield_10335 =  { id: mucdo_ut }
        // }else{
        //       req.customfield_10328 =  { 
        //                                   id: customfield_14300_id,
        //                                   child:  {
        //                                       id: customfield_14300_child_id
        //                                   }
        //                                 };
        //       req.customfield_10329 =  {
        //           id: customfield_10329_id
        //       };
        // }

        //CODE sua theo moi api chua dap ung se bo sung sau
        if (mucdo_ut != "-1" && issuetype == "10207"){
          req.customfield_10328_id =  customfield_14300_id.toString();
          req.customfield_10328_child_id = customfield_14300_child_id.toString(); 
           
          req.customfield_10329_id = customfield_10329_id.toString();
          req.customfield_12601_id = mucdo_ut .toString()
        }
        else if (mucdo_ut != "-1" && issuetype == "10211"){
          req.customfield_10328_id = customfield_14300_id.toString();
          req.customfield_10328_child_id = customfield_14300_child_id.toString();
          req.customfield_10329_id = customfield_10329_id.toString();
          req.customfield_10335_id = mucdo_ut.toString();
        }else{
          req.customfield_10328_id = customfield_14300_id.toString();
          req.customfield_10328_child_id= customfield_14300_child_id.toString();
          req.customfield_10329_id =  customfield_10329_id.toString();
        }
        
        let data = this.GetData(await api.Creat_Issue(this.axios, req)); 
        // if(data && data == "ok"){
        //     //test
        //     data = {"id":"1366844","key":"BSS-47312","self":"https://cntt.vnpt.vn/rest/api/2/issue/1366844"};
        // } 
        console.log("Creat_Issue", data);
        if(data && data.id && Number(data.id) > 0){ 
            console.log("this.state.files", this.state.files)
            if (this.state.files && this.state.files.length > 0)
            {  
              try{ 
                let formData = new FormData(); 
                for(var file of this.state.files) {
                  formData.append('files', file);
                }
                formData.append('issueKeyOrID', data.key); 
                formData.append('username', ""); 
                formData.append('password', ""); 
                let attRes = this.GetData(await api.add_Issue_Attachments(this.axios, formData, { headers: { 'Content-Type': 'multipart/form-data' }})); 
                if(!attRes || (attRes.length && attRes.length == 0) || (attRes.length > 0 && (!attRes[0].id || attRes[0].id <= 0 ))){
                  this.$toast.success("Lỗi upload file đính kèm");
                }
              }
              catch(ex){
                commonFn.showException(this, ex);
              }
                

            }
            req = {
              "issueKeyOrID": data.key,
              "login": {
                "password": "",
                "username": ""
              },
              "participant": this.tt_nd.nguoi_cn
            }
            let add_Issue = this.GetData(await api.add_Issue_Participant(this.axios, req));  // Chuyen vi tri theo yeu cau cua DuyBN
            req = {
              "issueKeyOrID": data.key
            }
            let issueInfo = this.GetData(await api.Get_Issue_Info(this.axios, req))
            console.log("issueInfo", issueInfo);
            if(!issueInfo){
              issueInfo = data;
            }
            
            if (this.state.hdtb_id > 0){   
              req = {
                  "vloai": this.state.loai,
                  "vhdtb_id": this.state.hdtb_id,
                  "vbaohong_id": 0,
                  "vphieu_id": this.state.phieu_id,
                  "vvip_xl_tinh": "1",
                  "vtinh_id": this.tt_nd.phanvung_id,
                  "vissue_id": data.id,
                  "vkey": data.key,
                  "vuser_tao": this.JiraService.SERVICE_USER,
                  "vtieude": this.controls.txtTieude,
                  "vnoidung": this.controls.txtMota,
                  "vnguoi_cn": this.tt_nd.nguoi_cn
              }         
              if(this.state.kieu ==3){ 
                req.vvip_xl_tinh = 1;
                let rsMap = this.GetData(await api.fn_insert_jira_giaophieu_issue(this.axios, req))
                if(rsMap != "OK")
                {
                  this.$toast.error("Có lỗi khi cập nhật yêu cầu vào hợp đồng"); 
                }
              }
              else{
                req.vvip_xl_tinh = 0;
                req.vtinh_id = -1;
                let rsMap = this.GetData(await api.fn_insert_jira_giaophieu_issue(this.axios, req))
                if(rsMap != "OK")
                {
                  this.$toast.error("Có lỗi khi cập nhật yêu cầu vào hợp đồng"); 
                }
                if (this.state.vip_giamsat)
                {
                  if (this.tt_nd.phanvung_id != 97){
                    req = {
                        "vhdtb_id": this.state.hdtb_id,
                        "vdonvi_giao_id": this.tt_nd.donvi_id,
                        "vnhanvien_giao_id": this.tt_nd.nhanvien_id,
                        "vmay_cn": this.tt_nd.may_cn,
                        "vnguoi_cn": this.tt_nd.nguoi_cn,
                        "vip_cn": this.tt_nd.ip_cn,
                    }
                    let ds_khdn = this.GetData(await api.lay_tt_giao_vip_giamsat_it360(this.axios, req));
                    if (ds_khdn && ds_khdn.length > 0)
                    { 
                        req = {
                          tinhthicong_id: 97,
                          vjson_khdn: JSON.stringify(commonFn.UpperCasePropertyList(ds_khdn))
                        }
                        await api.tinh_insert_vip_net(this.axios, req);
                    }
                  }
                  else{
                    req.vtinh_id = 97;
                    req.vdonvi_giao_id = this.tt_nd.donvi_id;
                    req.vnhanvien_giao_id = this.tt_nd.nhanvien_id;
                    req.vmay_cn = this.tt_nd.may_cn;
                    req.vnguoi_cn = this.tt_nd.nguoi_cn;
                    req.vip_cn = this.tt_nd.ip_cn;
                    this.GetData(await api.giaophieuvip_cntt(this.axios, req));
                  }
                }
              }
            }

            if (this.state.baohong_id > 0)
            {
              let dem = "";
              req = {
                    "vdsphieu": data, 
                    "vbaohong_id": this.state.baohong_id,
                    "vphieu_id": this.state.phieu_id,
                    "vtieude": this.controls.txtTieude,
                    "vnoidung": this.controls.txtMota,
                    "vnhanvien_id": this.tt_nd.nhanvien_id,
                    "vnguoi_cn": this.tt_nd.nguoi_cn,
                    "vmay_cn": this.tt_nd.may_cn,
                    "vip_cn": this.tt_nd.ip_cn,
                    "vuser_tao": this.JiraService.SERVICE_USER,
              }
              if (this.state.kieu == 2)
              { 
                req.vphieu_id = this.state.phieu_baohong_id;
                dem = this.GetData(await api.giaophieu_jira_baohong(this.axios, req));
              }
              else if (this.state.kieu == 4)
              {
                req.vphieu_id = this.state.phieu_baohong_id;
                req.tinh_id = this.tt_nd.phanvung_id;
                dem = this.GetData(await api.giaophieu_jira_baohong(this.axios, req)); 
              }
              if (!dem || dem.toString().indexOf("ok") < 0)
              {
                  throw "Lỗi đẩy dữ liệu IT360! " + dem;
              }
              else
              {
                if (this.state.kieu == 4){
                  await api.giaophieu_ph(this.axios, {phieu_tinh_id: this.state.phieu_baohong_id}) 
                }

                let phieu_moi_id = dem.toString().replaceAll("ok-", "")*1;
                let dsTT_KHDN = [];
                if (this.state.kieu == 2){
                  dsTT_KHDN = this.GetData(await api.lay_ds_phieu_phoihop_khdn(this.axios, {vphieu_id: phieu_moi_id}));
                }
                else if (this.state.kieu == 4){
                  dsTT_KHDN = this.GetData(await api.lay_ds_phieu_phoihop_khdn(this.axios, {vphieu_id: phieu_moi_id, tinh_id: this.tt_nd.phanvung_id}));
                }
                if (dsTT_KHDN  && dsTT_KHDN.length > 0)
                {
                  if (dsTT_KHDN[0]["giao_vip"] == "1")
                  {
                    //Đẩy sang Vip giám sát
                    //TODO: Chưa có API
                  }
                } 
              }
            } 

            this.$emit("onSuccess", issueInfo);
            this.$toast.success("Tạo yêu cầu thành công"); 
            this.hideDialog();
        } 
        else{
          if(data.errorMessages && data.errorMessages.length > 0){
            this.$toast.error("Có lỗi khi tạo yêu cầu lên hệ thống IT360. " + JSON.stringify(data.errors[0]));
            return;
          }else if(data.errors && data.errors.length > 0){
            this.$toast.error("Có lỗi khi tạo yêu cầu lên hệ thống IT360. " + JSON.stringify(data.errors[0]));
            return;
          }else
            this.$toast.error("Có lỗi khi tạo yêu cầu lên hệ thống IT360. " + JSON.stringify(data)); 
        } 
      }catch(ex){
        commonFn.showException(this, ex);
      }
      this.loading(false)
    },
    async changeFileUpload(e){
       console.log("changeFileUpload", e); 
       let files = this.$refs.file1.files;        
       this.controls.txtFileDinhKem = "";
       if(files && files.length > 0){
          this.state.files = files;
          this.controls.txtFileDinhKem = Array.from(files).map((x) => {return x.name}).join();
       } 
       console.log(this.controls.txtFileDinhKem ); 
    },
    GetData: function (response) { 
      if (response.data.error === 200 || response.data.error === '200' || response.data.faultCode == "") {
        return response.data.data
      } else {
        console.log(response.data.error_code)
        if(response.data && response.data.data)
          return response.data.data
      }
      return null
    },
    
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }, 
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>