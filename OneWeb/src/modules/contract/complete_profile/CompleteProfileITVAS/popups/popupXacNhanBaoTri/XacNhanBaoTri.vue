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
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogXacNhanBaoTri" :position="position" :header="'Thông tin xác nhận bảo trì'" showCloseIcon="true" width="75%" target="#app .main-wrapper">
    <div class="dialogCreateIssue"> 
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="javascript:;" v-on:click="tsbtnGhiLai_Click">
                <span class="icon one-save"></span> Ghi lại
              </a>
            </li>           
          </ul>
        </div>
        <div class="popup-body">
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="box-form">
                <div class="row">  
                    <div class="col-sm-7 col-12">
                        <div class="info-row">
                            <div class="key">Loại HĐ</div>
                            <div class="value">
                                <input ref="txtLoaiHD" type="text" class="form-control" v-model="controls.txtLoaiHD" >
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <div class="key">{{controls.lblMa}}</div>
                            <div class="value">
                                <input ref="txtMa" type="text" class="form-control" v-model="controls.txtMa" >
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="info-row">
                            <div class="key">Số máy /Acc</div>
                            <div class="value"> 
                                <input ref="txtMaTB" type="text" class="form-control" v-model="controls.txtMaTB" >
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <div class="key">Loại hình</div>
                            <div class="value">
                                <input ref="txtLoaiHinh" type="text" class="form-control" v-model="controls.txtLoaiHinh" >
                            </div>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">Tên thuê bao</div>
                    <div class="value">
                        <input ref="txtTenTB" type="text" class="form-control" v-model="controls.txtTenTB" >
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">Địa chỉ lắp đặt</div>
                    <div class="value">
                        <input ref="txtDiaChiLD" type="text" class="form-control" v-model="controls.txtDiaChiLD" >
                    </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="box-form">
                <div class="info-row">
                    <div class="key">Người trả lời</div>
                    <div class="value">
                        <input ref="txtTenNguoiTL" type="text" class="form-control" v-model="controls.txtTenNguoiTL" >
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">Số liên hệ</div>
                    <div class="value">
                        <input ref="txtSoLH" type="text" class="form-control" v-model="controls.txtSoLH" >
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">Ghi chú</div>
                    <div class="value">
                        <input ref="txtGhiChu" type="text" class="form-control" v-model="controls.txtGhiChu" >
                    </div>
                </div>
                <div class="info-row">
                    <div class="key"></div>
                    <div class="value">
                        <input ref="lblXacNhan" type="text" class="form-control red" v-model="controls.lblXacNhan" readonly="true">
                    </div>
                </div>
              </div>
            </div>
          </div> 
      </div> 
    </div>
  </ejs-dialog>
</template>
<script> 
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment' 
import api from './PopUpApi'  
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions"; 
  
export default {
  components: { 
    moment,
    DatePicker, 
    api,  
  },
  name: 'XacNhanBaoTri',
  data() {
    return {
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
       
      controls: {
        txtMaTB: "",
        txtTenNguoiTL: "",
        txtSoLH: "",
        txtGhiChu: "",
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
      if(!data){
        return;
      }
    
      this.state =  { 
        hdtb_id: data.hdtb_id ? data.hdtb_id: -1,
        tag: data.tag ? data.tag : -1,
        baohong_id: 0,
        hinhthuc: 31
      };
      console.log(this.state);
      this.$refs.dialogXacNhanBaoTri.show();
      this.pageLoad();
    },
    hideDialog () {
      this.$refs.dialogXacNhanBaoTri.hide()
    },
    
    async pageLoad(){
      try{ 
        this.loading(true)
        this.tt_nd.may_cn = await this.$root.token.getMachine();
        this.tt_nd.ip_cn = await this.$root.token.getIP();
        this.$refs.txtTenNguoiTL.focus();
        await this.HienThiThongTinTB();
        await this.HienThiCH();  
      }catch(ex){
        console.error(ex);
        this.$toast.error(ex);
      }
      this.loading(false) 
    },  
    async HienThiThongTinTB(){
      let vid=0;
      if (this.state.tag == 1)
          vid = this.state.hdtb_id;
      if (this.state.tag == 2)
          vid = this.state.baohong_id;
      this.state.isNew = true;
      this.state.tbtl_id = 0;
      this.controls.lblXacNhan = "Chưa xác nhận bảo trì";
      let req = {
          "tag": this.state.tag,
          "id": vid,
          "nhanvien_id": this.tt_nd.nhanvien_id
      }
      let dtList = this.GetData(await api.lay_thuebao_xacnhan_baotri(this.axios, req));
      
      if (dtList && dtList.length > 0)
      { 
        this.controls.txtMaTB = dtList[0]["ma_tb"];
        if (this.state.tag == 1)
        {
            this.controls.lblMa = "Mã GD";
            this.controls.txtMa = dtList[0]["ma_gd"];
        }
        if (this.state.tag == 2)
        {
            this.controls.lblMa = "Mã BD";
            this.controls.txtMa = dtList[0]["ma_bh"];
        }
        
        this.controls.txtTenTB = dtList[0]["ten_tb"];
        this.controls.txtDiaChiLD = dtList[0]["diachi_ld"];
        this.controls.txtLoaiHinh = dtList[0]["loaihinh_tb"];
        this.controls.txtLoaiHD = dtList[0]["ten_loaihd"];
        this.state.tbtl_id = Number(dtList[0]["tbtl_id"]);  
      }

      let ds_nv = this.GetData(await api.sp_lay_nhanvien_theo_nhanvien_id(this.axios, {params: {nhanvien_id: this.tt_nd.nhanvien_id}})); 
      if(ds_nv & ds_nv.length > 0){
        this.controls.txtTenNguoiTL = ds_nv[0]["ten_nv"];
        this.controls.txtSoLH = ds_nv[0]["so_dt"];
      }

      if (this.state.tbtl_id > 0)
      {
          this.state.isNew = false;
          this.controls.lblXacNhan = "Đã xác nhận bảo trì";
          let dt_tl = this.GetData(await api.sp_tb_traloi_theo_tbtl_id(this.axios, {params: {tbtl_id: this.state.tbtl_id}})); 
          if (dt_tl && dt_tl.length > 0)
          {
              this.controls.txtGhiChu = dt_tl[0]["ghichu"];
          }
      }
    },
    async HienThiCH(){
      //Loai_NV.NHANVIEN_KHAOSAT_KH = 95
      let dt = this.GetData(await api.lay_ds_cauhoi_theoloainv(this.axios, {loainv_id: 95, hinhthucks_id: this.state.hinhthuc}));  
      let dtCTTBTL = []
      if (this.state.isNew && this.state.isNew == false){
        dtCTTBTL = this.GetData(await api.sp_ct_tbtraloi(this.axios, {params: {tbtl_id: this.state.tbtl_id}}));   
      }

      let listQuestion1 = {}
      listQuestion1.dt = dt;
      listQuestion1.dtCTTBTL = dtCTTBTL;
      listQuestion1.isNew = this.state.isNew;
      this.controls.listQuestion1 = listQuestion1;
    },
    async TaoDuLieu(themmoi, xuatlai){ 
      let row = {}
      let dsTb = []
      if (themmoi == true)
        this.state.tbtl_id = await commonFn.GetKey(this.axios, "TBTL_ID");
      
      row.TBTL_ID = this.state.tbtl_id;
      row.BAOHONG_ID = this.state.baohong_id;
      row.HDKH_ID = 0;
      row.HINHTHUC = this.state.hinhthuc;
      row.NGUOI_TL = this.controls.txtTenNguoiTL;
      row.DIENTHOAI_LH = this.controls.txtSoLH;
      row.GHICHU = this.controls.txtGhiChu;
      row.TRANGTHAI = 1;
      row.NGAY_HOI = this.tt_nd.ngay_cn;
      row.NGUOI_CN = this.tt_nd.nguoi_cn;
      row.MAY_CN = this.tt_nd.may_cn;
      row.NHANVIEN_ID = this.tt_nd.nhanvien_id;
      row.HDTB_ID = this.state.hdtb_id;
      row.NGUOIDUNG_ID = this.tt_nd.nguoidung_id;
      row.IP_CN = this.tt_nd.ip_cn;
      dsTb.push(row);

      //var rs = listQuestion1.GetKetQua();
      let rs = [];
      let dsCT = [];
      for(const item of dsCT){
        if(item["traloi_id"] && item["traloi_id"] > 0){
          let rowct ={}
          rowct.CAUHOI_ID = item["cauhoi_id"];
          rowct.TRALOI_ID = item["traloi_id"];
          rowct.TBTL_ID = tbtl_id;
          rowct.NOIDUNG = item["noidung"];
          dsCT.push(rowct);
        }        
      }

      return { dsTb: dsTb, dsCT: dsCT}
    },       
    async KiemTra_DuLieu()
    {
      if (this.controls.txtTenNguoiTL == "")
      {
          this.$toast.error("Chưa nhập tên người trả lời !");
          this.$refs.txtTenNguoiTL.focus();
          return false;
      }
      if (this.controls.txtSoLH == "")
      {
          this.$toast.error("Chưa nhập điện thoại liên hệ !");
          this.$refs.txtTenNguoiTL.focus();
          return false;
      }

      return true;
    },
    async tsbtnGhiLai_Click(e){
      try{ 
        
        if (await this.KiemTra_DuLieu(true))
        {               
            let dt = await this.TaoDuLieu(this.state.isNew, false);                
            console.log("TaoDuLieu", dt);
            let req = {
              "dstb": JSON.stringify(dt.dsTb),
              "dsct": JSON.stringify(dt.dsCT),
              "tbtl_id": this.state.tbtl_id,
              "isnew": this.state.isNew == true ? 1 : 0
            }
            this.loading(true)
            let res = this.GetData(await api.fn_ghilai_xacnhanbaotri(this.axios, req));  
            if(res == 1){
              this.$toast.success("Cập nhật thành công!"); 
              this.hideDialog();
              this.$emit("xacnhanbaotri_success")  
            }else{
                this.$toast.error(res); 
            }
        }
        else{
          this.loading(false)   
          return;
        }    
      }catch(ex){
        console.error(ex);
        this.$toast.error(ex);
      }
      this.loading(false)      
       
    },
    GetData: function (response) { 
      if (response.data.error === 200 || response.data.error === '200' || response.data.error === '204' || response.data.errorCode === 0) {
        return response.data.data
      } else {
        console.log(response.data.error_code)
        console.log(response.data.faultString)
        if(response.data && response.data.faultString){
          throw response.data.faultString;
        }
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
    },
    LowerCasePropertyList(obj) {
      if(!obj) return [];
      return obj.map( function( item ){
          for(var key in item){
              var upper = key.toLowerCase();
              // check if it already wasn't uppercase
              if( upper !== key ){
                  item[ upper ] = item[key];
                  delete item[key];
              }
          }
          return item;
      });
    },
    UpperCasePropertyList(obj) {
      if(!obj) return [];
      return obj.map( function( item ){
          for(var key in item){
              var upper = key.toUpperCase();
              // check if it already wasn't uppercase
              if( upper !== key ){
                  item[ upper ] = item[key];
                  delete item[key];
              }
          }
          return item;
      });
    },
  }, 
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>