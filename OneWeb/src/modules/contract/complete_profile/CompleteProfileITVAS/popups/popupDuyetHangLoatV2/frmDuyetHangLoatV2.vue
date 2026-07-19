<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li></li>
<!--        <li>-->
<!--          <div class="input-group">-->
<!--            <input type="text" v-model="ma_gd" class="form-control" placeholder="&#45;&#45; Mã giao dịch &#45;&#45;" />-->
<!--          </div>-->
<!--        </li>-->
        <li>
          <a href="javascript:;" @click="FormLoad">
            <span class="icon one-file-arrow-right1"></span> Lấy danh sách
          </a>
        </li>
        <li v-if="ra_kichhoat==1">
          <a href="javascript:;" @click="RAKichHoatHangLoat">
            <span class="icon one-phone"></span> RA kích hoạt
          </a>
        </li>
        <li v-if="ra_kichhoat==0">
          <a href="javascript:;" @click="DuyetBangSmartCA">
            <span class="icon one-phone"></span> Duyệt bằng SmartCA
          </a>
        </li>
        <li v-if="ra_kichhoat==0">
          <a href="javascript:;" @click="DuyetBangToken">
            <span class="icon one-td-usb"></span> Duyệt bằng Token
          </a>
        </li>
        <li v-if="ra_kichhoat==0">
          <a href="javascript:;" @click="HuyDuyetBangSmartCA">
            <span class="icon one-file-remove"></span> Hủy duyệt bằng SmartCA
          </a>
        </li>
        <li v-if="ra_kichhoat==0">
          <a href="javascript:;" @click="HuyDuyetBangToken">
            <span class="icon one-usb-port"></span> Hủy duyệt bằng Token
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col">
            <div class="info-row">
              <div class="key w150 nowrap">Mã giao dịch</div>
              <div class="value">
                <input type="text" v-model="ma_gd" class="form-control" placeholder="-- Mã giao dịch --" />
              </div>
            </div>

          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="row" v-if="ra_kichhoat==0">
          <div class="col-md-4 col-sm-12">
            <label class="form-label"><strong>UID:</strong></label>
<!--            <input type="text" v-model="vuid" class="form-control" placeholder="UID">-->
            <input type="text" v-model="duyet_hang_loat_user_info.vuid" class="form-control" placeholder="UID">
          </div>
          <div class="col-md-4 col-sm-12">
            <label class="form-label"><strong>Mật khẩu:</strong></label>
<!--            <input type="password" v-model="vmatkhau" class="form-control" placeholder="Mật khẩu">-->
            <input type="password" v-model="duyet_hang_loat_user_info.vmatkhau" class="form-control" placeholder="Mật khẩu">
          </div>
          <div class="col-md-4 col-sm-12">
            <label class="form-label"><strong>OTP:</strong></label>
<!--            <input type="text" v-model="votp" class="form-control" placeholder="OTP">-->
            <input type="text" v-model="duyet_hang_loat_user_info.votp" class="form-control" placeholder="OTP">
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <br/>
            <table class="table table-striped table-bordered">
              <thead class="table-dark table-bss">
              <tr>
                <th scope="col">Mã giao dịch</th>
                <th scope="col">Mã thuê bao</th>
                <th scope="col">Tên thuê bao</th>
                <th scope="col">Kiểu yêu cầu</th>
                <th scope="col">Kết quả</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(v, k) in ds_hienthi">
                <td>{{ds_hienthi[k].ma_gd}}</td>
                <th>{{ds_hienthi[k].ma_tb}}</th>
                <th>{{ds_hienthi[k].ten_tb}}</th>
                <th>{{ds_hienthi[k].ten_kieuld}}</th>
                <td>{{ds_hienthi[k].ketqua}}</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>

      </div>
    </div>
    <ModalPromise ref="modalPromise" />
    <CATokenSign ref="frm_catoken_sign2"></CATokenSign>
  </div>
</template>

<script>

import { mapState, mapActions } from 'vuex'
import smartca from '../../API/smartCAFunction';
import ModalPromise from '@/modules/contract/complete_profile/CompleteProfileITVAS/components/ModalPromise.vue'
import CATokenSign from './CASignHL.vue'
import axios from 'axios'
import forge from 'node-forge'
export default {
  name: 'frmDuyetHangLoatV2',
  data () {
    return {
      ds_hienthi: [],
      ma_gd: "",
      vuid: "",
      vmatkhau: "",
      votp: ""
    }
  },
  components:{
    CATokenSign,
    ModalPromise
  },
  computed: {
    ...mapState(
      {
        user_ca_infor: state => state.KySmartCA.user_ca_infor,
        matkhau_smca: state => state.KySmartCA.matkhau_smca,
        dulieu_smca: state => state.KySmartCA.dulieu_smca,
        ds_hdtb: state => state.DuyetHangLoatV2.ds_hdtb,
        ra_kichhoat: state => state.DuyetHangLoatV2.ra_kichhoat,
        duyet_hang_loat_user_info: state => state.KySmartCA.duyet_hang_loat_user_info
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
    ...mapActions(['layThongTinNhanVienCA','setMatKhau','LayThongTinSmartCAUser','TaoRequestSMCA','TaoMessageDuyetSMCA','KyBangSmartCA','setCAUID','KichHoatRASmartCA']),
    FormLoad () {
      if(this.ra_kichhoat == 1)
      {
        if (this.ma_gd) {
          this.ds_hienthi = this.ds_hdtb.filter(x => x.loaitb_id == 288 && x.ma_gd == this.ma_gd)

        } else {
          this.ds_hienthi = this.ds_hdtb.filter(x => x.loaitb_id == 288 && x.status == 0)
          //&& (x.kieuld_id == 14045 || x.kieuld_id == 14073) && x.status == 0
        }
      }
      else {
        if (this.ma_gd) {
          this.ds_hienthi = this.ds_hdtb.filter(x => x.status == 3 && x.loaitb_id == 288 && x.ma_gd == this.ma_gd)

        } else {
          this.ds_hienthi = this.ds_hdtb.filter(x => x.status == 3 && x.loaitb_id == 288 && (x.kieuld_id == 14045 || x.kieuld_id == 14073))
        }
      }
      for (let i in this.ds_hienthi)
      {
        this.ds_hienthi[i] = {
          ...this.ds_hienthi[i],
          ketqua: '... Chờ xử lý ...'
        }
      }
    },
    async RAKichHoatHangLoat()
    {
      this.vuid = this.duyet_hang_loat_user_info.vuid
      this.vmatkhau = this.duyet_hang_loat_user_info.vmatkhau
      this.votp = this.duyet_hang_loat_user_info.votp
      const isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn có chắc là sẽ kích hoạt hàng loạt không?`,
        {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy bỏ",
          centered: true,
        }
      );
      if(!isConfirm) return;
      try {
        this.loading(true)
        let nguoidung = this.$root.token.getUserName();
        for (let i = 0; i < this.ds_hienthi.length;i++) {
          try {
            if (this.KichHoatRASmartCA({
              nv_thaotac: nguoidung,
              hdtb_id: this.ds_hienthi[i].hdtb_id
            })) {
              this.ds_hienthi.splice(i, 1, {
                ...this.ds_hienthi[i],
                ketqua: 'RA thành công'
              })
            }
          } catch (ex) {
            let loi = ex.data.message || ex.data || JSON.stringify(ex.data) || "lỗi"
            this.ds_hienthi.splice(i, 1, {...this.ds_hienthi[i], ketqua: loi})
          }
        }
      }
      catch (e)
      {
        let loi = e.data.message || e.data || JSON.stringify(e.data) || "lỗi"
        this.$toast.error(loi);
      }
      finally {
        this.loading(false)
      }
    },
    async KyBangToken(data_hash)
    {
      console.log(data_hash)
      return smartca.KyHash(data_hash);
    },
    uuidv4() {
      return "10000000-1000-4000-8000-100000000000".replace(/[018]/g, c =>
        (+c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> +c / 4).toString(16)
      );
    },
    async DuyetBangSmartCA () {
      this.vuid = this.duyet_hang_loat_user_info.vuid
      this.vmatkhau = this.duyet_hang_loat_user_info.vmatkhau
      this.votp = this.duyet_hang_loat_user_info.votp
      const isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn có chắc là sẽ duyệt không?`,
        {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy bỏ",
          centered: true,
        }
      );
      if(!isConfirm) return;
      let self = this

      try {
        this.loading(true)
        try {
          await this.LayThongTinSmartCAUser({
            user_id: self.vuid
          })
        } catch (ex) {
          throw new Error("Không lấy được thông tin SmartCA")
        }
        this.setCAUID(this.vuid);
        this.setMatKhau(this.vmatkhau)
        let serial = self.dulieu_smca.data.user_certificates[0].serial_number
        let transaction_id = self.dulieu_smca.data.user_certificates[0].transaction_id
        let cert_data = self.dulieu_smca.data.user_certificates[0].cert_data
        let taohash_req = ""
        let kq_tao_req_duyet = ""
        let kq_tao_messsage_duyet = ''
        let signature_value = ''
        for (let i = 0; i < this.ds_hienthi.length; i++) {
          try {
            let transaction_id_gen = this.uuidv4()
            try {
             /* this.ds_hienthi[i] = {
                ...this.ds_hienthi[i],
                ketqua: "Đang xử lý"
              }*/
             // this.$set(this.ds_hienthi, i, {...this.ds_hienthi[i], ketqua: 'Đang xử lý'})

              this.ds_hienthi.splice(i,1,{...this.ds_hienthi[i], ketqua: 'Đang xử lý'});
              kq_tao_req_duyet = await this.TaoRequestSMCA(this.ds_hienthi[i].hdtb_id)
              taohash_req = {
                ma_gd: this.ds_hienthi[i].ma_gd,
                serial: serial,
                duyet: "0",
                lydo: kq_tao_req_duyet.data.lydo,
                uid: kq_tao_req_duyet.data.uid,
                req_id: kq_tao_req_duyet.data.req_id
              }
            } catch (e) {
              throw e
            }
          /*  try {
              kq_tao_messsage_duyet = await this.TaoMessageDuyetSMCA(taohash_req)

            } catch (e) {
              throw e
            }*/


            let yc_ky_hash =  kq_tao_req_duyet.data.input_hash + ":" + kq_tao_req_duyet.data.lydo + ":" + serial + ":"

            let md = forge.md.sha256.create();
            md.update(yc_ky_hash);
            let dataInput = md.digest().toHex();

            try {
              let obj_taoyc_ky = {
                user_id: self.vuid,
                password: self.vmatkhau,
                otp: self.votp,
                transaction_id: transaction_id_gen,
                transaction_desc: "Duyet CA",
                serial_number: serial,
                data_to_be_signed: dataInput
              }
              let kq_ky = await this.KyBangSmartCA(obj_taoyc_ky)
              signature_value = kq_ky;
            } catch (e) {
              throw e
            }

            try {
              let duyet_smca_req = kq_tao_req_duyet.data.body_duyet
              duyet_smca_req.body = {
                ...duyet_smca_req.body,
                caSerial: serial
              };
              duyet_smca_req = {
                ...duyet_smca_req,
                cert: cert_data,
                signature: signature_value
              };

             /* let duyet_smca_req = {
                body:{
                  refRequestId:  self.ds_hienthi[i].ma_gd,
                  requestcertId: kq_tao_req_duyet.data.req_id,
                  status: 0,
                  uid: kq_tao_req_duyet.data.uid,
                  reason: "Duyet CA",
                  caSerial: serial
                },
                cert: cert_data,
                signature: signature_value
              }*/
              let duyet_smca_v2 = await  axios.post("/web-thicong/vnptsmartca/duyet_smca_v2", duyet_smca_req)
              if (duyet_smca_v2.data.data.code != 0)
              {
                throw duyet_smca_v2.data.data.message;
              }
              else {
                this.ds_hienthi.splice(i,1,{...this.ds_hienthi[i], ketqua: 'Duyệt thành công'})
              }
            }
            catch (e)
            {
              throw e
            }
          }
          catch (ex)
          {
            let loi = ex.data.message || ex.data || JSON.stringify(ex.data) || "lỗi"
            this.ds_hienthi[i].ketqua = loi
            //this.$toast.error(loi);
           // throw  ex;
            this.ds_hienthi.splice(i,1,{...this.ds_hienthi[i], ketqua: loi})
          }
        }
        console.log(transaction_id)
      }
      catch (e)
      {
        let loi = e.data.message || e.data || JSON.stringify(e.data) || "lỗi";
        this.$toast.error(loi);
        //this.$set(this.ds_hienthi, i, {...this.ds_hienthi[i], ketqua: loi})

      }
      finally {
        this.loading(false)
      }
    },
    async HuyDuyetBangSmartCA () {
      this.vuid = this.duyet_hang_loat_user_info.vuid
      this.vmatkhau = this.duyet_hang_loat_user_info.vmatkhau
      this.votp = this.duyet_hang_loat_user_info.votp
      const isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn có chắc là sẽ hủy duyệt không?`,
        {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy bỏ",
          centered: true,
        }
      );
      if(!isConfirm) return;
      let self = this

      try {
        this.loading(true)
        try {
          await this.LayThongTinSmartCAUser({
            user_id: self.vuid
          })
        } catch (ex) {
          throw new Error("Không lấy được thông tin SmartCA")
        }
        this.setCAUID(this.vuid);
        this.setMatKhau(this.vmatkhau)
        let serial = self.dulieu_smca.data.user_certificates[0].serial_number
        let transaction_id = self.dulieu_smca.data.user_certificates[0].transaction_id
        let cert_data = self.dulieu_smca.data.user_certificates[0].cert_data
        let taohash_req = ""
        let kq_tao_req_duyet = ""
        let kq_tao_messsage_duyet = ''
        let signature_value = ''
        for (let i = 0; i < this.ds_hienthi.length; i++) {
          try {
            let transaction_id_gen = this.uuidv4()
            try {
              /* this.ds_hienthi[i] = {
                 ...this.ds_hienthi[i],
                 ketqua: "Đang xử lý"
               }*/
              // this.$set(this.ds_hienthi, i, {...this.ds_hienthi[i], ketqua: 'Đang xử lý'})
              this.ds_hienthi.splice(i,1,{...this.ds_hienthi[i], ketqua: 'Đang xử lý'});
              kq_tao_req_duyet = await this.TaoRequestSMCA(this.ds_hienthi[i].hdtb_id)
              taohash_req = {
                ma_gd: this.ds_hienthi[i].ma_gd,
                serial: serial,
                duyet: "0",
                lydo: "Huy Duyet CA",
                uid: kq_tao_req_duyet.data.uid,
                req_id: kq_tao_req_duyet.data.req_id
              }
            } catch (e) {
              throw e
            }
            try {
              kq_tao_messsage_duyet = await this.TaoMessageDuyetSMCA(taohash_req)

            } catch (e) {
              throw e
            }
            try {
              let obj_taoyc_ky = {
                user_id: self.vuid,
                password: self.vmatkhau,
                otp: self.votp,
                transaction_id: transaction_id_gen,
                transaction_desc: "Huy Duyet CA",
                serial_number: serial,
                data_to_be_signed: kq_tao_messsage_duyet.data_sha256
              }
              let kq_ky = await this.KyBangSmartCA(obj_taoyc_ky)
              signature_value = kq_ky;
            } catch (e) {
              throw e
            }

            try {
              let duyet_smca_req = {
                body:{
                  refRequestId:  self.ds_hienthi[i].ma_gd,
                  requestcertId: kq_tao_req_duyet.data.req_id,
                  status: 1,
                  uid: kq_tao_req_duyet.data.uid,
                  reason: "Huy Duyet CA",
                  caSerial: serial
                },
                cert: cert_data,
                signature: signature_value
              }
              let duyet_smca_v2 = await  axios.post("/web-thicong/vnptsmartca/duyet_smca_v2", duyet_smca_req)
              if (duyet_smca_v2.data.data.code != 0)
              {
                throw duyet_smca_v2.data.data.message;
              }
              else {
                this.ds_hienthi.splice(i,1,{...this.ds_hienthi[i], ketqua: 'Hủy duyệt thành công'})
              }
            }
            catch (e)
            {
              throw e
            }
          }
          catch (ex)
          {
            let loi = ex.data.message || ex.data || JSON.stringify(ex.data) || "lỗi"
            this.ds_hienthi[i].ketqua = loi
            //this.$toast.error(loi);
            // throw  ex;
            this.ds_hienthi.splice(i,1,{...this.ds_hienthi[i], ketqua: loi})
          }
        }
        console.log(transaction_id)
      }
      catch (e)
      {
        let loi = e.data.message || e.data || JSON.stringify(e.data) || "lỗi";
        this.$toast.error(loi);
        //this.$set(this.ds_hienthi, i, {...this.ds_hienthi[i], ketqua: loi})

      }
      finally {
        this.loading(false)
      }
    },
    async HuyDuyetBangToken () {
      this.vuid = this.duyet_hang_loat_user_info.vuid
      this.vmatkhau = this.duyet_hang_loat_user_info.vmatkhau
      this.votp = this.duyet_hang_loat_user_info.votp
      let self = this
      const isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn có chắc là sẽ hủy duyệt không?`,
        {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy bỏ",
          centered: true,
        }
      );
      if(!isConfirm) return;
      try {
        this.loading(true)

        await smartca.LayThongTinTokenDuyet_v2(this);
        let obj_token = smartca.LayThongTinTokenExist();
        let serial = obj_token.serialnumber;

        let taohash_req = ""
        let kq_tao_req_duyet = ""
        let kq_tao_messsage_duyet = ''
        let signature_value = ''
        for (let i = 0; i < this.ds_hienthi.length; i++) {
          try {
            try {
              /* this.ds_hienthi[i] = {
                 ...this.ds_hienthi[i],
                 ketqua: "Đang xử lý"
               }*/
              // this.$set(this.ds_hienthi, i, {...this.ds_hienthi[i], ketqua: 'Đang xử lý'})
              this.ds_hienthi.splice(i,1,{...this.ds_hienthi[i], ketqua: 'Đang xử lý'});
              kq_tao_req_duyet = await this.TaoRequestSMCA(this.ds_hienthi[i].hdtb_id)
              taohash_req = {
                ma_gd: this.ds_hienthi[i].ma_gd,
                serial: serial,
                duyet: "0",
                lydo: "Huy Duyet CA",
                uid: kq_tao_req_duyet.data.uid,
                req_id: kq_tao_req_duyet.data.req_id
              }
            } catch (e) {
              throw e
            }
            try {
              kq_tao_messsage_duyet = await this.TaoMessageDuyetSMCA(taohash_req)
              console.log(kq_tao_messsage_duyet)
            } catch (e) {
              throw e
            }
            try {
              let kq_ky = await this.KyBangToken(kq_tao_messsage_duyet.data_sha256)
              console.log(kq_ky);
              signature_value = kq_ky;
            } catch (e) {
              console.log(e);
              throw e
            }

            try {
              let duyet_smca_req = {
                body:{
                  refRequestId:  self.ds_hienthi[i].ma_gd,
                  requestcertId: kq_tao_req_duyet.data.req_id,
                  status: 1,
                  uid: kq_tao_req_duyet.data.uid,
                  reason: "Huy Duyet CA",
                  caSerial: serial
                },
                cert: cert_data,
                signature: signature_value
              }
              let duyet_smca_v2 = await  axios.post("/web-thicong/vnptsmartca/duyet_smca_v2", duyet_smca_req)
              if (duyet_smca_v2.data.data.code != 0)
              {
                throw duyet_smca_v2.data.data.message;
              }
              else {
                this.ds_hienthi.splice(i,1,{...this.ds_hienthi[i], ketqua: 'Hủy duyệt thành công'})
              }
            }
            catch (e)
            {
              throw e
            }
          }
          catch (ex)
          {
            let loi = ex.data.message || ex.data || JSON.stringify(ex.data) || "lỗi"
            this.ds_hienthi[i].ketqua = loi
            //this.$toast.error(loi);
            // throw  ex;
            this.ds_hienthi.splice(i,1,{...this.ds_hienthi[i], ketqua: loi})
          }
        }
        console.log(transaction_id)
      }
      catch (e)
      {
        let loi = e.data.message || e.data || JSON.stringify(e.data) || "lỗi";
        this.$toast.error(loi);
        //this.$set(this.ds_hienthi, i, {...this.ds_hienthi[i], ketqua: loi})

      }
      finally {
        this.loading(false)
      }
    },
    async DuyetBangToken () {
      this.vuid = this.duyet_hang_loat_user_info.vuid
      this.vmatkhau = this.duyet_hang_loat_user_info.vmatkhau
      this.votp = this.duyet_hang_loat_user_info.votp
      const isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn có chắc là sẽ duyệt không?`,
        {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy bỏ",
          centered: true,
        }
      );
      if(!isConfirm) return;
      let self = this

      try {
       // this.loading(true)

        let kq = await this.$refs.frm_catoken_sign2.show("1", "lay_tt", (x) => {
          console.log("591", x);
        }, "Duyet CA");
        console.log("593",kq)

       // let obj_token = smartca.LayThongTinTokenExist();
        let serial = kq.device_infor.ca_serial;
        let certbase64 = kq.device_infor.cert_base64
        let dll_name = kq.device_infor.dll_name
        let matkhau = kq.device_infor.matkhau
        let taohash_req = ""
        let kq_tao_req_duyet = ""
        let kq_tao_messsage_duyet = ''
        let signature_value = ''
        //return;
        for (let i = 0; i < this.ds_hienthi.length; i++) {
          try {
            try {
              /* this.ds_hienthi[i] = {
                 ...this.ds_hienthi[i],
                 ketqua: "Đang xử lý"
               }*/
              // this.$set(this.ds_hienthi, i, {...this.ds_hienthi[i], ketqua: 'Đang xử lý'})
              this.ds_hienthi.splice(i,1,{...this.ds_hienthi[i], ketqua: 'Đang xử lý'});
              kq_tao_req_duyet = await this.TaoRequestSMCA(this.ds_hienthi[i].hdtb_id)
              taohash_req = {
                ma_gd: this.ds_hienthi[i].ma_gd,
                serial: serial,
                duyet: "0",
                lydo: "Duyet CA",
                uid: kq_tao_req_duyet.data.uid,
                req_id: kq_tao_req_duyet.data.req_id
              }
            } catch (e) {
              throw e
            }
            try {
              kq_tao_messsage_duyet = await this.TaoMessageDuyetSMCA(taohash_req)

            } catch (e) {
              throw e
            }
            try {
              let kq_ky = await this.KyHash_token(kq_tao_messsage_duyet.data_sha256, matkhau, dll_name, serial, certbase64)
              signature_value = kq_ky;
              console.log(signature_value)
            } catch (e) {
              throw e
            }

            try {
              let duyet_smca_req = {
                body:{
                  refRequestId:  self.ds_hienthi[i].ma_gd,
                  requestcertId: kq_tao_req_duyet.data.req_id,
                  status: 0,
                  uid: kq_tao_req_duyet.data.uid,
                  reason: "Duyet CA",
                  caSerial: serial
                },
                cert: certbase64,
                signature: signature_value
              }
              let duyet_smca_v2 = await axios.post("/web-thicong/vnptsmartca/duyet_smca_v2", duyet_smca_req)
              if (duyet_smca_v2.data.data.code != 0)
              {
                throw duyet_smca_v2.data.data.message;
              }
              else {
                this.ds_hienthi.splice(i,1,{...this.ds_hienthi[i], ketqua: 'Duyệt thành công'})
              }
            }
            catch (e)
            {
              console.log("664",e);
              throw e
            }
          }
          catch (ex)
          {
            let loi = ex.data.message || ex.data || JSON.stringify(ex.data) || "lỗi"
            this.ds_hienthi[i].ketqua = loi
            //this.$toast.error(loi);
            // throw  ex;
            this.ds_hienthi.splice(i,1,{...this.ds_hienthi[i], ketqua: loi})
          }
        }
        console.log(transaction_id)
      }
      catch (e)
      {
        let loi = e.data.message || e.data || JSON.stringify(e.data) || "lỗi";
        this.$toast.error(loi);
        //this.$set(this.ds_hienthi, i, {...this.ds_hienthi[i], ketqua: loi})

      }
      finally {
        this.loading(false)
      }
    },
    async KyHash_token(input, matkhau, dll_name, serial, cert_base64) {
      console.log(input);
      let kq = await vnpt_cams_plugin.signHash(dll_name, matkhau, cert_base64, input);
      let kq_obj = JSON.parse(kq)
      console.log(kq_obj);
      // return_func(kq_obj.data);
      return kq_obj.data;
    },
  }
}
</script>

<style scoped>
.table-dark.table-bss {
  --bs-table-bg: #0366cc;
  --bs-table-striped-bg: #0366cc;
  --bs-table-striped-color: #fff;
  --bs-table-active-bg: #0366cc;
  --bs-table-active-color: #fff;
  --bs-table-hover-bg: #0366cc;
  --bs-table-hover-color: #fff;
  color: #fff;
  border-color: #0366cc;
}
.table-dark td, .table-dark th, .table-dark thead th {
  border-color: #0366cc;
}
</style>
