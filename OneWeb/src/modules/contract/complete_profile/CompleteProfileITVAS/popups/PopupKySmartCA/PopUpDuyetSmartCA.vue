<template>
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="hieu_ung_popup" ref="frmDuyetSmartCA" :position="position" :header="'Ký duyệt SmartCA'" showCloseIcon="true" width="50%" target="#app .main-wrapper">
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" @click="btnDuyetSmartCA_Click">
              <span class="icon one-phone"></span> Duyệt CA/SmartCA
            </a>
          </li>
          <li>
            <a href="javascript:;" @click="btnDuyetToken_Click">
              <span class="icon one-td-usb"></span> Duyệt bằng token
            </a>
          </li>
          <li>
            <a href="javascript:;" @click="btnHuyDuyetSmartCA_Click">
              <span class="icon one-file-remove"></span> Hủy duyệt
            </a>
          </li>
          <li>
            <a href="javascript:;" @click="btnHuyDuyetToken_Click">
              <span class="icon one-usb-port"></span> Hủy duyệt bằng Token
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <div class="key w110">UID người dùng</div>
            <div class="value">
                <input class='form-control' type='text' v-model='ca_uid' readonly/>
            </div>
          </div>
          <div class="info-row">
            <div class="key w110">Mật khẩu</div>
            <div class="value">
              <input class='form-control' type='password' v-model='matkhau_smartca'>
            </div>
          </div>
          <div class="info-row">
            <div class="key w110">OTP</div>
            <div class="value">
              <input class='form-control' type='text' v-model='otp'>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import DatePicker from 'vue2-datepicker'
import axios from 'axios'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import { mapActions, mapState } from 'vuex'
import smartca from '../../API/smartCAFunction';
import api from '@/modules/contract/complete_profile/CompleteProfileITVAS/API/CompleteProfileAPI.js'
import {DataManager, JsonAdaptor  } from '@syncfusion/ej2-data';
import { ComboBoxPlugin  } from "@syncfusion/ej2-vue-dropdowns";
//Vue.use(ComboBoxPlugin);
export default {
  components: {
    moment,
    DatePicker
  },
  name: 'DuyetSmartCA',
  data() {
    let self = this;

    // console.log(self.$root.token);
    return {
      ca_uid: "",
      otp: "",
      hieu_ung_popup: { effect: 'Zoom' },
      position: { X: 'center', Y: 'center' },
      hdtb_id: 0,
      dulieu: {},
      frmCompleteProfileITVAS: {}
    }
  },
  computed: {
    ...mapState(
      {
        user_ca_infor: state => state.KySmartCA.user_ca_infor,
        matkhau_smca: state => state.KySmartCA.matkhau_smca,
        dulieu_smca: state => state.KySmartCA.dulieu_smca
      }
    ),
    matkhau_smartca: {
      get () {
        return this.matkhau_smca
      },
      set (value) {
        this.setMatKhau(value)
      }
    }
  },
  methods: {
    ...mapActions(['layThongTinNhanVienCA','setMatKhau','LayThongTinSmartCAUser']),
    async btnHuyDuyetSmartCA_Click()
    {
      try {
        this.loading(true);
        let self = this;
        console.log(this.matkhau_smca);
        try {
          await this.LayThongTinSmartCAUser({
            user_id: self.ca_uid
          });
        }
        catch
        {
          throw new Error("Không lấy được thông tin SmartCA")
        }

        let serial = self.dulieu_smca.data.user_certificates[0].serial_number;
        let transaction_id = self.dulieu_smca.data.user_certificates[0].transaction_id;
        let cert_data = self.dulieu_smca.data.user_certificates[0].cert_data;
        let req_duyet = await axios.post("/web-thicong/vnptsmartca/taoreqduyetsmca", {hdtb_id: self.dulieu.hdtb_id});
        let taohash_req = {
          ma_gd: self.dulieu.ma_gd,
          serial: serial,
          duyet:"0",
          lydo:"Huy Duyet CA",
          uid: req_duyet.data.data.uid,
          req_id: req_duyet.data.data.req_id
        }



        let hash_data = await axios.post("/web-thicong/vnptsmartca/TaoMessageDuyetSMCA", taohash_req);
        let data_hash = hash_data.data.data.data_sha256;

        console.log(data_hash);

        let obj_taoyc_ky = {
          user_id: self.ca_uid,
          password: self.matkhau_smca,
          otp: self.otp,
          transaction_id: transaction_id,
          transaction_desc: "Huy Duyet CA",
          serial_number: serial,
          data_to_be_signed: data_hash
        };

        let tao_yc_smca = await axios.post("/web-thicong/vnptsmartca/GuiYeuCauKySmartCA", obj_taoyc_ky);
        console.log(tao_yc_smca.data.data);
        let confirm_req = {
          sp_password: "ZmI4OTY1NDQ-ZWU1Ni00Njgz",
          sp_id: "4683-638193082754533115.apps.smartcaapi.com",
          user_id: self.ca_uid,
          password: self.matkhau_smca,
          transaction_id: transaction_id,
          sad: tao_yc_smca.data.data.data.sad
        }
        let confirm_yc = await axios.post("/web-thicong/vnptsmartca/ConfirmKySoSmartCA", confirm_req)
        let signature_value = confirm_yc.data.data.data.signatures[0].signature_value;
        let duyet_smca_req = {
          body:{
            refRequestId:  self.dulieu.ma_gd,
            requestcertId: req_duyet.data.data.req_id,
            status: 1,
            uid: req_duyet.data.data.uid,
            reason: "Huy Duyet CA",
            caSerial: serial
          },
          cert: cert_data,
          signature: signature_value
        }
        let duyet_smca_v2 = await  axios.post("/web-thicong/vnptsmartca/duyet_smca_v2", duyet_smca_req)
        console.log(duyet_smca_v2)
        if (duyet_smca_v2.data.data.code != 0)
        {
          console.log(a);
          throw duyet_smca_v2.data.data.message || duyet_smca_v2.data.message;
        }
        this.$toast.success("Hủy Duyệt thành công!");

      }
      catch (e)
      {
        console.log(e);
        let loi = e.data.message || e.data || JSON.stringify(e.data) || "lỗi";
        this.$toast.error(loi);
      }
      finally {
        this.loading(false);
      }
    },
    async btnHuyDuyetToken_Click()
    {
      try {
        this.loading(true);
        let self = this;
        let req_duyet = await axios.post("/web-thicong/vnptsmartca/taoreqduyetsmca", {hdtb_id: self.dulieu.hdtb_id});
        this.loading(false);
        await smartca.LayThongTinTokenDuyet(self.frmCompleteProfileITVAS);
        let obj_token = smartca.LayThongTinTokenExist();

        let taohash_req = {
          ma_gd: self.dulieu.ma_gd,
          serial: obj_token.serialnumber,
          duyet:"0",
          lydo:"Huy Duyet CA",
          uid: req_duyet.data.data.uid,
          req_id: req_duyet.data.data.req_id
        }
        this.loading(true);
        let hash_data = await axios.post("/web-thicong/vnptsmartca/TaoMessageDuyetSMCA", taohash_req);
        let data_hash = hash_data.data.data.data_sha256;
        this.loading(false);
        let sign_value = await smartca.KyHash(data_hash);
        let duyet_smca_req = {
          body:{
            refRequestId:  self.dulieu.ma_gd,
            requestcertId: req_duyet.data.data.req_id,
            status: 1,
            uid: req_duyet.data.data.uid,
            reason: "Huy Duyet CA",
            caSerial: obj_token.serialnumber
          },
          cert: obj_token.cert,
          signature: sign_value
        }
        this.loading(true);
        let duyet_smca_v2 = await  axios.post("/web-thicong/vnptsmartca/duyet_smca_v2", duyet_smca_req)
        this.$toast.success("Hủy duyệt thành công!");
      }
      catch (e)
      {
        console.log(e);
        let loi = e.data.message || e.data || JSON.stringify(e.data) || "lỗi";
        this.$toast.error(loi);
      }
      finally {
        this.loading(false);
      }
    },
    async btnDuyetSmartCA_Click()
    {
      try {
        this.loading(true);
        let self = this;
        console.log(this.matkhau_smca);
        try {
          await this.LayThongTinSmartCAUser({
            user_id: self.ca_uid
          });
        }
        catch
        {
          throw new Error("Không lấy được thông tin SmartCA")
        }

        let serial = self.dulieu_smca.data.user_certificates[0].serial_number;
        let transaction_id = self.dulieu_smca.data.user_certificates[0].transaction_id;
        let cert_data = self.dulieu_smca.data.user_certificates[0].cert_data;



        let req_duyet = await axios.post("/web-thicong/vnptsmartca/taoreqduyetsmca", {hdtb_id: self.dulieu.hdtb_id});
        let taohash_req = {
          ma_gd: self.dulieu.ma_gd,
          serial: serial,
          duyet:"0",
          lydo:"Duyet CA",
          uid: req_duyet.data.data.uid,
          req_id: req_duyet.data.data.req_id
        }

        if (this.dulieu.kieuld_id == 14074)
        {
          taohash_req = {
            uid: req_duyet.data.data.uid,
            ma_tb: self.dulieu.ma_tb,
            email: req_duyet.data.data.email,
            phone: req_duyet.data.data.sdt,
            reason: "doi email-sdt",
            ra_serial: req_duyet.data.data.ra_serial,
            ca_serial: serial,
            ra_staffname: req_duyet.data.data.ra_staff,
            ra_staffid: req_duyet.data.data.ra_id
          }
        }


        let hash_data = "";
        let tran_desc = "Duyet CA"
        if (this.dulieu.kieuld_id == 14074) {
          hash_data = await axios.post("/web-thicong/vnptsmartca/TaoMessageDuyetSMCADoiEmail", taohash_req);
          tran_desc = "Doi thong tin Email SDT"
        }
        else
        {
          hash_data = await axios.post("/web-thicong/vnptsmartca/TaoMessageDuyetSMCA", taohash_req);
        }
        let data_hash = hash_data.data.data.data_sha256;

        console.log(data_hash);

        let obj_taoyc_ky = {
          user_id: self.ca_uid,
          password: self.matkhau_smca,
          otp: self.otp,
          transaction_id: transaction_id,
          transaction_desc: tran_desc,
          serial_number: serial,
          data_to_be_signed: data_hash
        };

        let tao_yc_smca = await axios.post("/web-thicong/vnptsmartca/GuiYeuCauKySmartCA", obj_taoyc_ky);
        console.log(tao_yc_smca.data.data);
        let confirm_req = {
          sp_password: "ZmI4OTY1NDQ-ZWU1Ni00Njgz",
          sp_id: "4683-638193082754533115.apps.smartcaapi.com",
          user_id: self.ca_uid,
          password: self.matkhau_smca,
          transaction_id: transaction_id,
          sad: tao_yc_smca.data.data.data.sad
        }
        let confirm_yc = await axios.post("/web-thicong/vnptsmartca/ConfirmKySoSmartCA", confirm_req)
        console.log(confirm_yc);
        let signature_value = confirm_yc.data.data.data.signatures[0].signature_value;
        let duyet_smca_req = {
          body:{
            refRequestId:  self.dulieu.ma_gd,
            requestcertId: req_duyet.data.data.req_id,
            status: 0,
            uid: req_duyet.data.data.uid,
            reason: "Duyet CA",
            caSerial: serial
          },
          cert: cert_data,
          signature: signature_value
        }
        let duyet_smca_v2 = "";
        if (this.dulieu.kieuld_id == 14074) {
          duyet_smca_req = {
            uid: req_duyet.data.data.uid,
            ma_tb: self.dulieu.ma_tb,
            email: req_duyet.data.data.email,
            phone: req_duyet.data.data.sdt,
            reason: "doi email-sdt",
            raSerial: req_duyet.data.data.ra_serial,
            caSerial: serial,
            raStaffName: req_duyet.data.data.ra_staff,
            raStaffID: req_duyet.data.data.ra_id,
            cert: cert_data,
            signature: signature_value,
            fileDocuments: req_duyet.data.data.file_hs,
            hdtb_id: self.dulieu.hdtb_id
          }

          duyet_smca_v2 = await axios.post("/web-thicong/vnptsmartca/duyet_doi_email_smca", duyet_smca_req)
        }
        else {
          duyet_smca_v2 = await axios.post("/web-thicong/vnptsmartca/duyet_smca_v2", duyet_smca_req)
        }
        if (duyet_smca_v2.data.data.code != 0)
        {
          throw duyet_smca_v2.data.data.message;
        }
        this.$toast.success("Duyệt thành công!");

      }
      catch (e)
      {
        console.log(e.message);
        let mes = e.message||e.data.message || e.data ||JSON.stringify(e.data)
        console.log(mes);
        this.$toast.error("Lỗi!" + mes);
        throw  e;
      }
      finally {
        this.loading(false);
      }
    },
    async btnDuyetToken_Click()
    {
      try {
        this.loading(true);
        let self = this;
        let req_duyet = await axios.post("/web-thicong/vnptsmartca/taoreqduyetsmca", {hdtb_id: self.dulieu.hdtb_id});
        this.loading(false);
        await smartca.LayThongTinTokenDuyet(self.frmCompleteProfileITVAS);
        let obj_token = smartca.LayThongTinTokenExist();

        let taohash_req = {
          ma_gd: self.dulieu.ma_gd,
          serial: obj_token.serialnumber,
          duyet:"0",
          lydo:"Duyet CA",
          uid: req_duyet.data.data.uid,
          req_id: req_duyet.data.data.req_id
        }

        // xu ly cho doi email
        if (this.dulieu.kieuld_id == 14074)
        {
          taohash_req = {
            uid: req_duyet.data.data.uid,
            ma_tb: self.dulieu.ma_tb,
            email: req_duyet.data.data.email,
            phone: req_duyet.data.data.sdt,
            reason: "doi email-sdt",
            ra_serial: req_duyet.data.data.ra_serial,
            ca_serial: obj_token.serialnumber,
            ra_staffname: req_duyet.data.data.ra_staff,
            ra_staffid: req_duyet.data.data.ra_id
          }
        }
        this.loading(true);
        //let hash_data = await axios.post("/web-thicong/vnptsmartca/TaoMessageDuyetSMCA", taohash_req);
        let tran_desc = "Duyet CA"
        let hash_data = "";
        if (this.dulieu.kieuld_id == 14074) {
          hash_data = await axios.post("/web-thicong/vnptsmartca/TaoMessageDuyetSMCADoiEmail", taohash_req);
          tran_desc = "Doi thong tin Email SDT"
        }
        else
        {
          hash_data = await axios.post("/web-thicong/vnptsmartca/TaoMessageDuyetSMCA", taohash_req);
        }
        let data_hash = hash_data.data.data.data_sha256;
        this.loading(false);
        let sign_value = await smartca.KyHash(data_hash);
        let duyet_smca_req = {
          body:{
            refRequestId:  self.dulieu.ma_gd,
            requestcertId: req_duyet.data.data.req_id,
            status: 0,
            uid: req_duyet.data.data.uid,
            reason: tran_desc,
            caSerial: obj_token.serialnumber
          },
          cert: obj_token.cert,
          signature: sign_value
        }
        this.loading(true);
       // let duyet_smca_v2 = await  axios.post("/web-thicong/vnptsmartca/duyet_smca_v2", duyet_smca_req)
        let duyet_smca_v2 = "";
        if (this.dulieu.kieuld_id == 14074) {
          duyet_smca_req = {
            uid: req_duyet.data.data.uid,
            ma_tb: self.dulieu.ma_tb,
            email: req_duyet.data.data.email,
            phone: req_duyet.data.data.sdt,
            reason: "doi email-sdt",
            raSerial: req_duyet.data.data.ra_serial,
            caSerial: obj_token.serialnumber,
            raStaffName: req_duyet.data.data.ra_staff,
            raStaffID: req_duyet.data.data.ra_id,
            cert: obj_token.cert,
            signature: sign_value,
            fileDocuments: req_duyet.data.data.file_hs,
            hdtb_id: self.dulieu.hdtb_id
          }

          duyet_smca_v2 = await axios.post("/web-thicong/vnptsmartca/duyet_doi_email_smca", duyet_smca_req)
        }
        else {
          duyet_smca_v2 = await axios.post("/web-thicong/vnptsmartca/duyet_smca_v2", duyet_smca_req)
        }
        this.$toast.success("Duyệt thành công!");
      }
      catch (e)
      {
        console.log(e);
        let loi = e.data.message || e.data || JSON.stringify(e.data) || "lỗi";
        this.$toast.error(loi);
      }
      finally {
        this.loading(false);
      }

    },
    async openDialog(frmcall, dulieu)
    {
      this.frmCompleteProfileITVAS = frmcall;
      this.dulieu = dulieu;
     // this.resetForm();
      try {
        let self = this;
        let obj = await this.$root.token.getThongTinNguoiDung();
        console.log(obj);
        console.log(self.$root.user)
        //gọi lấy thông tin smartca
        try {
          await self.layThongTinNhanVienCA({
            ma_nd: obj.ma_nd
          });
        }
        catch {
          throw new Error("Không lấy được thông tin SmartCA ký số");
        }

        console.log(this.user_ca_infor[0]);
        this.ca_uid = this.user_ca_infor[0].so_gt;

        this.$refs.frmDuyetSmartCA.show();
      }
      catch (e)
      {
        throw e;
      }
    },
    resetForm()
    {
        this.hdtb_id = 0;
    }
  }
}
</script>

<style>

</style>
