<template>
  <b-modal
    id="smca_sign"
    title="Ký dữ liệu bằng SmartCA"
    ok-variant="danger"
    size="lg"
    no-close-on-esc
    no-close-on-backdrop
    okTitle="Ký số"
    cancelTitle="Hủy"
    ref="frm_smca_sign"
    @cancel="cancelPromise(false)"
    @ok="resolvePromise(true, $event)"
    @close="cancelPromise(false)"
  >
    <div class="">
      <div class="info-row">
        <div class="key w110">UID</div>
        <div class="value">
          <input type="text" class="form-control" v-model="uid">
        </div>
      </div>
      <div class="info-row">
        <div class="key w110">Serial</div>
        <div class="value">
          <div class="select-custom">
            <ejs-combobox ref='cboChonCTS' :popupHeight='300' @change='this.cboChonCTS_changed' :allowFiltering='true'
                            :fields='fields_cts' v-model="selected_serial" :dataSource='this.ds_cts'></ejs-combobox>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w110">Mật khẩu</div>
        <div class="value">
          <input type="password" class="form-control" v-model="matkhau">
        </div>
      </div>
      <div class="info-row">
        <div class="key w110">OTP/Secretkey</div>
        <div class="value">
          <input type="password" class="form-control" v-model="otp">
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import moment from 'moment/moment'
import axios from 'axios'
import forge from 'node-forge'

export default {
  name: 'SmartCASign',
  data () {
    return {
      uid: '',
      matkhau: '',
      otp: '',
      selected_serial: '',
      ds_cts: [],
      fields_cts: {text: 'serial_number', value: 'serial_number'},
      dulieu_ky: '',
      cancelPromise: null,
      resolvePromise: null,
      cert_ca: "",
      thietbi_ky_infor: {}
    }
  },
  methods: {
    cboChonCTS_changed () {
      console.log('Test')
      //goi modal
      // cv b
      return 'test'
    },
    fn_kydulieu () {
      return 'test'
    },
    async fn_laythongtin_nhanvien_ca () {
      try {
        this.loading(true)
        let tt_nd = {
          nhanvien_id: this.$root.token.getNhanVienID() * 1,
          nguoidung_id: this.$root.token.getNguoiDungID() * 1,
          phanvung_id: this.$root.token.getPhanVungID() * 1,
          donvi_id: this.$root.token.getDonViID() * 1,
          may_cn: this.$root.token.getMachine(),
          ip_cn: this.$root.token.getIP(),
          ngay_cn: moment(new Date()).format('DD/MM/YYYY hh:mm A'),
          ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
            'DD/MM/YYYY hh:mm A'
          ),
          nguoi_cn: this.$root.token.getUserName(),
          ma_nd: this.$root.token.getUserName(),
          ma_tinh: this.$root.token.getMaTinh()
        }
        let kq = await axios.post('/web-thicong/dvcntt/laythongtinnguoiduyet', {
          ma_nd: tt_nd.ma_nd
        })
        console.log(kq)
        this.uid = kq.data.data[0].so_gt
        await this.fn_laythongtin_serial_smca(this.uid)
      }
      catch (e) {
        throw e
      }
      finally {
        this.loading(false)
      }
    },
    async fn_laythongtin_serial_smca (uid) {
      let kq = await axios.post('/web-thicong/vnptsmartca/LayThongTinCTSSmartCA_v2', {
        user_id: uid
      })
      this.ds_cts = kq.data.data.data.user_certificates
    },
    uuidv4() {
      return "10000000-1000-4000-8000-100000000000".replace(/[018]/g, c =>
        (+c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> +c / 4).toString(16)
      );
    },
    async gui_yc_kyso(transaction_id, input)
    {
     try {
       console.log("uckyso")
        let self = this;
        let obj_taoyc_ky = {
          user_id: self.uid,
          password: self.matkhau,
          otp: self.otp,
          transaction_id: transaction_id,
          transaction_desc: "ky_so_smca_"+transaction_id,
          serial_number: self.selected_serial,
          data_to_be_signed: input
        };
        let tao_yc_smca = await axios.post("/web-thicong/vnptsmartca/GuiYeuCauKySmartCA", obj_taoyc_ky);
        console.log(tao_yc_smca)
        return tao_yc_smca.data.data.data;
      }
      catch (e)
      {
        throw e;
      }
    },
    xuly_exception(e, e_name)
    {
      let loi = e.message;
      if (!loi)
      {
        loi = e.request
        console.log(loi)
        if(loi)
        {
          return  e.request.responseText
        }
        if(!loi)
        {
          loi = JSON.stringify(e)
        }
      }
      return "Lỗi: " + loi;
    },
    async confirm_kyso(transaction_id, sad)
    {
      try {
        console.log("confir")
        let self = this;
        let confirm_req = {
          sp_password: "ZmI4OTY1NDQ-ZWU1Ni00Njgz",
          sp_id: "4683-638193082754533115.apps.smartcaapi.com",
          user_id: self.uid,
          password: self.matkhau,
          transaction_id: transaction_id,
          sad: sad
        }
        console.log(confirm_req);
        let confirm_yc = await axios.post("/web-thicong/vnptsmartca/ConfirmKySoSmartCA", confirm_req)
        console.log(confirm_yc);
        let signature_value = confirm_yc.data.data.data.signatures[0].signature_value;
        console.log(this.ds_cts)
        let certBase64 = self.ds_cts.find(x => x.serial_number == self.selected_serial);
        certBase64 = certBase64.cert_data;
        this.cert_ca = certBase64;
        let sig = signature_value
        return sig;
      }
      catch (e)
      {
        console.log(this.xuly_exception(e));
        throw e;
      }
    },
    async kyso_smartca (transaction_id, input, lydo) {
      try {
          let self = this;
          let dataInput = input + ":" + lydo + ":" + self.selected_serial + ":";
          dataInput = dataInput.replaceAll("::",":");
          console.log(dataInput);
          let md = forge.md.sha256.create();
          md.update(dataInput);
          dataInput = md.digest().toHex();
          //console.log(dataInput)
          let yc_ky = await this.gui_yc_kyso(transaction_id, dataInput);
          console.log(yc_ky);
          return yc_ky;
      }
      catch (e)
      {
        throw e;
      }
    },
    reset_form()
    {
      this.dulieu_ky = ""
    },
    async show (input, sign_type, return_func, lydo) {
      console.log('Test')
      this.reset_form()
      this.$bvModal.show('smca_sign')
      await this.fn_laythongtin_nhanvien_ca()
      return new Promise( (resolve, reject) => {
        this.resolvePromise = async (flag, event) => {
          if(this.uid == "")
          {
            event.preventDefault();
            this.$toast.error("Bạn chưa nhập UID!");
            return;
          }
          if(this.selected_serial == "")
          {
            event.preventDefault();
            this.$toast.error("Bạn chưa chọn chứng thư số!");
            return;
          }
          if(this.uid == "")
          {
            event.preventDefault();
            this.$toast.error("Bạn chưa nhập UID!");
            return;
          }
          if(this.matkhau == "")
          {
            event.preventDefault();
            this.$toast.error("Bạn chưa nhập mật khẩu!");
            return;
          }
          if(this.otp == "")
          {
            event.preventDefault();
            this.$toast.error("Bạn chưa OTP/Secret Key!");
            return;
          }
          let transaction_id = this.uuidv4();
          let sad = "";
					let sign_value = ""
					try {
            event.preventDefault();
            sad = await this.kyso_smartca(transaction_id, input, lydo);
            console.log(sad)
						sign_value = await this.confirm_kyso(sad.transaction_id,sad.sad)
            this.thietbi_ky_infor = {
              serial_number: this.selected_serial,
              cert_base64: this.cert_ca
            }
            this.$refs.frm_smca_sign.hide();
            this.$toast.success("Ký số thành công!");
						return_func({
              sign_value: sign_value,
              device_infor: this.thietbi_ky_infor
            })
          }
          catch (e)
          {
            let loi = this.xuly_exception(e);
            console.log(loi)
            this.$toast.error(loi)
            return;
          }
          resolve({
            sign_value: sign_value,
            device_infor: this.thietbi_ky_infor
          });
        }

        this.cancelPromise = () => {
          console.log("huyr")
          reject("Bạn đã hủy thao tác ký!");
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
