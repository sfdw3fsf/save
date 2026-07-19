<template>
  <b-modal
    id="catoken_sign_hl"
    title="Ký dữ liệu bằng CA"
    ok-variant="danger"
    size="lg"
    no-close-on-esc
    no-close-on-backdrop
    okTitle="Ký số"
    cancelTitle="Hủy"
    ref="modal_ca_sign_hl"
    @cancel="cancelPromise(false)"
    @ok="resolvePromise(true, $event)"
    @close="cancelPromise(false)"
    :cancel-disabled='disable_button_huy'
    :ok-disabled='disable_button_kyso'
  >
    <div class ="loading_spin" v-if='is_loading'>
      <b-spinner variant="primary" label="Spinning"></b-spinner>
      <b-spinner variant="primary" type="grow" label="Spinning"></b-spinner>
      <h4>{{loading_text}}</h4>
    </div>

    <div class="" v-if='!is_loading'>
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
    </div>
  </b-modal>
</template>
<script>
import moment from 'moment/moment'
import axios from 'axios'
import forge from 'node-forge'

export default {
  name: 'CASign',
  data () {
    return {
      disable_button_kyso: true,
      disable_button_huy: true,
      is_loading: true,
      loading_text: "Đang lấy thông tin chứng thư số.....",
      key: "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+PExpY2Vuc2U+PFBoYW5NZW0+Vk5QVC1DQSBQbHVnaW48L1BoYW5NZW0+PE5ndW9pQ2FwPlZOUFQgSVQ8L05ndW9pQ2FwPjxDQU1TPio8L0NBTVM+PE5nYXlCYXREYXU+MDEvMTAvMjAyNCAwMDowMDowMDwvTmdheUJhdERhdT48TmdheUtldFRodWM+MDEvMTAvMjAzMCAyMzo1OTo1OTwvTmdheUtldFRodWM+PFNpZ25hdHVyZSB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnIyI+PFNpZ25lZEluZm8+PENhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiPjxJbmNsdXNpdmVOYW1lc3BhY2VzIFByZWZpeExpc3Q9Imh0dHA6Ly93d3cudzMub3JnL1RSLzIwMDEvUkVDLXhtbC1jMTRuLTIwMDEwMzE1IiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMS8xMC94bWwtZXhjLWMxNG4jIiAvPjwvQ2Fub25pY2FsaXphdGlvbk1ldGhvZD48U2lnbmF0dXJlTWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI3JzYS1zaGExIiAvPjxSZWZlcmVuY2UgVVJJPSIiPjxUcmFuc2Zvcm1zPjxUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjZW52ZWxvcGVkLXNpZ25hdHVyZSIgLz48L1RyYW5zZm9ybXM+PERpZ2VzdE1ldGhvZCBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvMDkveG1sZHNpZyNzaGExIiAvPjxEaWdlc3RWYWx1ZT5TQ1ZHbXR2OUNIVDJWdU5qRTRBRWtuNzNnTDQ9PC9EaWdlc3RWYWx1ZT48L1JlZmVyZW5jZT48L1NpZ25lZEluZm8+PFNpZ25hdHVyZVZhbHVlPlRYSUMxT3U1M1dYYjI1N2NzN1ZORy84RFJBRXdCYTgvSmVFRjRlbG54Wm1CRFhHZnBrY2dGbjZxMDRXMjJaaHpraE0rcFV3dEJaVGxBWnNucHlobDBBVmkvQk8rcjZWNlF4d25YbUdhcUpJY0NjalJkRmRIUjBtUXNCTGxWS3NRZ3pBdll5ZG1UU2JqeVJRNlJqMnQ3TkU4SmZUc0UzQzdHU01UVHE2OUdoVTZMUUkzMnBGU1hwT081cVlOeStMNkxlZ002bzd0RnRXazBBTUxFVzFKN0N1Z0Y4am1vWmpoSkJkd0xQN25HbVBiR0dkU1UxV1h2eUY3NGt3bHZRWlQxek9naWZyMk9sKzFTQ3VkUFd2S2dhT2hlMElaM2IrVitGQVpNeE5CeDJsNWtqUExxQ0JsZFUwMmIvYzZFWlZnMVZhbCtQYW9lVWREUnZidXZBMDd0QT09PC9TaWduYXR1cmVWYWx1ZT48S2V5SW5mbz48S2V5VmFsdWU+PFJTQUtleVZhbHVlPjxNb2R1bHVzPnAyOGNVVG5vWEc5VW83NTRwandkWkdIOG1YNFZaUWtBeWtvcWhzQXB2Z1lQeVVRRWtCRTZkOXFzWkwzNkd2MWpGWjF2S2hvbkk2ZkxFeFZyYkhEbTkrRGVLZXpWSVpxUXZMaXM2M2czaHJtelJ0K2Mvb0F6YTZPUnFlQlZWbVBoQ1hPdy9KRHJ3eEgzQ0xacmxSMVhHeG1zcWxqaTcrVk0wcEMvQ0lraWcvNXRGdS9QQ3VUY1RZL05mL3lmbU1UOTdOaXk5Tm5GTlg1Y1FxREVZNWxTQ0Rvb3kwaHBTQ25jWVFzdUxjUUpBZll6dnp3OW9vT2pGallPVXZtY2VtREMxLzBFRTlkYldsV2V0Zm5WTmI4c3pvcXNMQnJMQURCMUNoU2ZubDlvUUZ6R1FWYTBSaUo5eE93UG9KMjVqUWRWaW1FMTh2VE1aV1p0bUdOUzJyKytPUT09PC9Nb2R1bHVzPjxFeHBvbmVudD5BUUFCPC9FeHBvbmVudD48L1JTQUtleVZhbHVlPjwvS2V5VmFsdWU+PFg1MDlEYXRhPjxYNTA5U3ViamVjdE5hbWU+T0lELjAuOS4yMzQyLjE5MjAwMzAwLjEwMC4xLjE9TVNUOjEwMTY4Njk3MzgtMDEyLCBDTj1WTlBUIFNPRlRXQVJFIC0gVk5QVCBDQSAtIFRFU1QgU0lHTiwgTD1D4bqndSBHaeG6pXksIFM9SMOAIE7hu5hJLCBDPVZOPC9YNTA5U3ViamVjdE5hbWU+PFg1MDlDZXJ0aWZpY2F0ZT5NSUlHUlRDQ0JDMmdBd0lCQWdJUVZBRWtqM3lYUkVJN3dLN0tSL2NIc2pBTkJna3Foa2lHOXcwQkFRVUZBREJwTVFzd0NRWURWUVFHRXdKV1RqRVRNQkVHQTFVRUNoTUtWazVRVkNCSGNtOTFjREVlTUJ3R0ExVUVDeE1WVms1UVZDMURRU0JVY25WemRDQk9aWFIzYjNKck1TVXdJd1lEVlFRREV4eFdUbEJVSUVObGNuUnBabWxqWVhScGIyNGdRWFYwYUc5eWFYUjVNQjRYRFRFM01ESXlOekE1TXpJd01Gb1hEVEU1TURJeU56SXhNekl3TUZvd2dZb3hDekFKQmdOVkJBWVRBbFpPTVJJd0VBWURWUVFJREFsSXc0QWdUdUc3bUVreEZUQVRCZ05WQkFjTURFUGh1cWQxSUVkcDRicWxlVEVzTUNvR0ExVUVBd3dqVms1UVZDQlRUMFpVVjBGU1JTQXRJRlpPVUZRZ1EwRWdMU0JVUlZOVUlGTkpSMDR4SWpBZ0Jnb0praWFKay9Jc1pBRUJEQkpOVTFRNk1UQXhOamcyT1Rjek9DMHdNVEl3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRQ25ieHhST2VoY2IxU2p2bmltUEIxa1lmeVpmaFZsQ1FES1NpcUd3Q20rQmcvSlJBU1FFVHAzMnF4a3Zmb2EvV01Wblc4cUdpY2pwOHNURld0c2NPYjM0TjRwN05VaG1wQzh1S3pyZURlR3ViTkczNXorZ0ROcm81R3A0RlZXWStFSmM3RDhrT3ZERWZjSXRtdVZIVmNiR2F5cVdPTHY1VXpTa0w4SWlTS0QvbTBXNzg4SzVOeE5qODEvL0orWXhQM3MyTEwwMmNVMWZseENvTVJqbVZJSU9pakxTR2xJS2R4aEN5NHR4QWtCOWpPL1BEMmlnNk1XTmc1UytaeDZZTUxYL1FRVDExdGFWWjYxK2RVMXZ5ek9pcXdzR3NzQU1IVUtGSitlWDJoQVhNWkJWclJHSW4zRTdBK2duYm1OQjFXS1lUWHk5TXhsWm0yWVkxTGF2NzQ1QWdNQkFBR2pnZ0hGTUlJQndUQndCZ2dyQmdFRkJRY0JBUVJrTUdJd01nWUlLd1lCQlFVSE1BS0dKbWgwZEhBNkx5OXdkV0l1ZG01d2RDMWpZUzUyYmk5alpYSjBjeTkyYm5CMFkyRXVZMlZ5TUN3R0NDc0dBUVVGQnpBQmhpQm9kSFJ3T2k4dmIyTnpjQzUyYm5CMExXTmhMblp1TDNKbGMzQnZibVJsY2pBZEJnTlZIUTRFRmdRVWJINGh0RnNPYkxEMWswTm9DWGp5RlR2WkNRc3dEQVlEVlIwVEFRSC9CQUl3QURBZkJnTlZIU01FR0RBV2dCUUdhY0RWMVFLS0ZZMUdmZWw4NG1nS1ZheHFyekJvQmdOVkhTQUVZVEJmTUYwR0Rpc0dBUVFCZ2UwREFRRURBUUVDTUVzd0lnWUlLd1lCQlFVSEFnSXdGaDRVQUU4QVNRQkVBQzBBVUFCeUFDMEFNZ0F1QURBd0pRWUlLd1lCQlFVSEFnRVdHV2gwZEhBNkx5OXdkV0l1ZG01d2RDMWpZUzUyYmk5eWNHRXdNUVlEVlIwZkJDb3dLREFtb0NTZ0lvWWdhSFIwY0RvdkwyTnliQzUyYm5CMExXTmhMblp1TDNadWNIUmpZUzVqY213d0RnWURWUjBQQVFIL0JBUURBZ1R3TURRR0ExVWRKUVF0TUNzR0NDc0dBUVVGQndNQ0JnZ3JCZ0VGQlFjREJBWUtLd1lCQkFHQ053b0REQVlKS29aSWh2Y3ZBUUVGTUJ3R0ExVWRFUVFWTUJPQkVURmphSFZqZFhWQVoyMWhhV3d1WTI5dE1BMEdDU3FHU0liM0RRRUJCUVVBQTRJQ0FRQXlxaUpQdnZrQ01NRjNCQmlQWjNHbmVobnd2aHRtRFJKalpSUk5tYjUyMkx3OHlTcUlwbzU3Z1hJU25MZTNxV3lDMEdDV2FWcy9XSjZUSmczZkIxR2FIcDJ1Skc4aEdWMGc1TUpuZ2dBa1dJSEhia2hreVhsS2QveHh2RjdsQ1dzbGg3T0xvL0R3SnpQY0FRS2REbW9zRExrTHV0bGppcktnUDExOFhtVHBKZTljbmhUR1d4bVI0M1JYbzFwSzZNWkpTLzM1QTBhRWNVRndsa3lPSnBGdXorR3JqcTJxSzJZc1Rwdk5JbnczTFpEd1Jra1lqaG1KT0UrZnVrcWZwOFdGY0J0RUdxV0RhQmJtcWdadmNpdDlzK1JvaTNXZU9NeFVLcmkwZlNBS0RjdXhuZjRyeGlmUTBCYmFzOUhVbU5tNjVWRXNtK2Y1QjB2ZFNtNlM3WDhabzU0dVF0UmcvSGQxYUNQeTBBRmRmYUhpKzZhZHFyMlpxM3B0TUlTMTA3bENkcTI2S3lOM1JNeFl2aDVOWnNnODhKYzlJTmxHeEw1OFdEOUVCN016RmtXbW5HYjlKYzF1a0FFYVBaQTl2d1pXWlVBdHBXT0lkVHhObVV2c1pkbFZBOXZEYy9UbkpSYlVrMGFDd3ZxVldNYWpQUG96dDgvQ3Z6SHF3Y091Z2tGdWM3KzhNOVJGaStSdFpYQStrclROZ25US0pSSGQxNjNkV3N5M2ZweUZVOVBBY3lreVRLcUZmaWxGak5pRzg3WDBNWnJwNGxMUmNTdUZrZ0hZNnIvaUlrTHZGTTZESXJUSlN4RFAzYlhvOWpGbDlRT2FiU05mb1B4S200MXU1R3FwZUpYamxOTkFESStHTnRXUTZnM1oybXVZV3BTNTROVWRKTnJ5R1B5THhBPT08L1g1MDlDZXJ0aWZpY2F0ZT48L1g1MDlEYXRhPjwvS2V5SW5mbz48T2JqZWN0PjxTaWduYXR1cmVQcm9wZXJ0aWVzIHhtbG5zPSIiPjxTaWduYXR1cmVQcm9wZXJ0eSBJZD0iU2lnbmluZ1RpbWUiIFRhcmdldD0ic2lnbmF0dXJlUHJvcGVydGllcyI+PFNpZ25pbmdUaW1lPjIwMjQtMDEtMTBUMDE6Mjc6MTNaPC9TaWduaW5nVGltZT48L1NpZ25hdHVyZVByb3BlcnR5PjwvU2lnbmF0dXJlUHJvcGVydGllcz48L09iamVjdD48L1NpZ25hdHVyZT48L0xpY2Vuc2U+",
      uid: '',
      matkhau: '',
      otp: '',
      selected_serial: '',
      ds_cts: [],
      fields_cts: {value: 'serialnumber', text: 'subjectdn'},
      dulieu_ky: '',
      cancelPromise: null,
      resolvePromise: null,
      token_ca_info: {},
			list_cts: [],
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
		async KyHash(input, output, lydo) {
			let self = this;
			let data = {};
			console.log(self.token_ca_info)
			let certBase64 = self.token_ca_info.certList.find(x => x.serialNumber == self.selected_serial);
			console.log("base", certBase64);
			let dataInput = input + ":" + lydo + ":" + self.selected_serial + ":";
      console.log(dataInput)
      this.thietbi_ky_infor = {
        serial_number: this.selected_serial,
        cert_base64: certBase64.certBase64,
        dll_name: self.token_ca_info.dllname,
        matkhau: self.matkhau
      }
      let md = forge.md.sha256.create();
      md.update(dataInput);
      dataInput = md.digest().toHex();
			let kq = await vnpt_cams_plugin.signHash(self.token_ca_info.dllname, self.matkhau, certBase64.certBase64, dataInput);
			let kq_obj = JSON.parse(kq)
			console.log(kq_obj);
     // return_func(kq_obj.data);
			return kq_obj;
		},
    LayTT() {
      let self = this;
      console.log(self.token_ca_info)
      let certBase64 = self.token_ca_info.certList.find(x => x.serialNumber == self.selected_serial);
      console.log("base", certBase64);
      this.thietbi_ky_infor = {
        serial_number: self.selected_serial,
        cert_base64: certBase64.certBase64,
        dll_name: self.token_ca_info.dllname,
        matkhau: self.matkhau
      }
      return this.thietbi_ky_infor;
    },
    fn_kydulieu () {
      return 'test'
    },
    async fn_laythongtin_nhanvien_ca () {
      try {
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
      }
      catch (e) {
        throw e
      }
      finally {
      }
    },
    async fn_lay_tt_ca () {
      let data = {}
      let tokenList = [];
      let dll_list = pkcs11DriverList;
      return ''
    },
    async kyso_ca (dulieu_ky, loaiky) {
      try {
        if(loaiky == "hash")
        {
          let cert = this.token_ca_info.certList.filter(x=>x.serialNumber === this.selected_serial);
          if(!cert || cert.length == 0){
            throw 'Chứng thư bạn chọn không còn tồn tại!'
          }
        }
      }
      catch (e)
      {

      }
    },
    async laythongtin_usb_token()
    {
      try {
        //call usb token
        if(!vnpt_cams_plugin){
          throw 'Thư viện VNPT plugin chưa sẵn sàng Bạn vui lòng chờ!';
        }

        if(!base64){
          throw 'Thư viện VNPT plugin chưa sẵn sàng. Bạn vui lòng chờ!';
        }

        let pkcs11DriverList = 'vnpt-ca_csp11.dll,vnptca_p11_v8.dll,vnptca_p11_v6.dll,vnptca_p11_v5.dll,vnptca_p11_v7.dll,vnptca_p11_v81.dll,vnptca_p11_v10.dll,gclib.dll,SCardSvr.dll,vnptcav2_p11_v8.dll,vnpt-ca_cl_v1.dll,VNPT-CA_v34.dll,VNPTCACardv8csp11.dll';
        if(!pkcs11DriverList || pkcs11DriverList.length <= 0){
          throw 'Không lấy được Danh sách DRIVER!';
        }
        console.log("Đang checkPlugin vnpt_cams_plugin");
        let data = await vnpt_cams_plugin.checkPlugin();
        console.log(data);
        console.log("vnpt_cams_plugin checkPlugin", data);
        if(data != 1) {
          throw "VNPT-Cams Plugin chưa được cài đặt hoặc chưa được bật trên máy của bạn!";
        }
        console.log("VNPT-Cams Plugin đã sẵn sàng");
        //end call usb token

        //Set licenseKey
        let set_key = await vnpt_cams_plugin.setLicenseKey(this.key);
				console.log("Set key ", set_key)


        data = JSON.parse(set_key);

        if(data.code !=  1){
          throw "VNPT-Cams Plugin setLicenseKey lỗi: " + data.desc;
        }
				// end region set licensekey
        console.log("Đang getTokenInfo...");

				// lay thong tin usb token
        let token_info = await vnpt_cams_plugin.getTokenInfo(pkcs11DriverList);
				let tokenList = []
				let resData = JSON.parse(token_info);
				resData.data = JSON.parse(resData.data);
				tokenList = resData.data;
				console.log(tokenList);




        if (!tokenList || tokenList.length == 0)
        {
          throw "Bạn không cắm token hoặc không có driver token phù hợp!";
        }
				// end lay thong tin usb token

        //#region lay thon tin cts
        let certList = [];

        let dllName = tokenList[0].Driver;
        var tokenSerial = tokenList[0].SerialNumber;
				console.log(token_info)
			//	token_info =JSON.parse(token_info)
			//	token_info.dllName = dllName;
        console.log(`Đang getCertInfo.. ${dllName} ${tokenSerial}`);
        resData = await vnpt_cams_plugin.getCertInfo(dllName, tokenSerial)
				//console.log(resData)
				resData = JSON.parse(resData);
				if(resData.code == 1 && resData.data){
					certList = JSON.parse(resData.data);
				}
				else{
					throw `Không lấy được CertInfo: code = ${resData.code}`;
				}
				if(!certList || certList.length <= 0){
					throw `Không lấy được chứng thư từ USB Token!`;
				}
				console.log(certList)
				this.list_cts = certList
        this.ds_cts = certList.map((x) => { return { serialnumber: x.serialNumber , subjectdn: `${x.subjectCN} - Serial: ${x.serialNumber} - Ngày hết hạn: ${x.notAfter}` } })
        this.token_ca_info = {
          ...this.token_ca_info,
          dllname: dllName,
          tkSerial: tokenSerial,
          certList: certList
        }
      } catch(e){
        throw "VNPT-Cams Plugin chưa được cài đặt hoặc chưa được bật trên máy của bạn!";
      }
    },

    async show (dulieu_ky, kieuky, return_func, lydo) {
     // $this.loading(true)
      console.log('Test')
			let return_value2 = ""
			this.reset_form()
      this.$bvModal.show('catoken_sign_hl')
      this.is_loading = true;
      this.disable_button_kyso = true;
      this.disable_button_huy = true;
      this.loading_text = "Đang lấy thông tin nhân viên CA.....";
      await this.fn_laythongtin_nhanvien_ca()
      this.loading_text = "Đang lấy thông tin USB Token.....";
      await this.laythongtin_usb_token()
     // $this.loading(false)
      this.is_loading = false;
      this.disable_button_kyso = false;
      this.disable_button_huy = false;
      return new Promise((resolve, reject) => {
        this.resolvePromise = async (flag, event) => {
          console.log("tesst promise");
         /* while(!this.ktraloi_form())
          {
              this.$toast("error", "Chưa chọn serial number");
          }*/
          if(!this.selected_serial)
          {
            this.$toast.error("Bạn chưa chọn serial!!");
            event.preventDefault();
            return;
          }
          if(!this.matkhau)
          {
            this.$toast.error("Bạn chưa nhập mật khẩu!!");
            event.preventDefault();
            return;
          }
					let kq_kyso = ""
					if (kieuky == "hash") {
						event.preventDefault();
						kq_kyso =	await this.KyHash(dulieu_ky, kieuky, lydo)

            if(kq_kyso.code == 1) {
							//return_func(kq_kyso.data);
              return_func({
                sign_value:  kq_kyso.data,
                device_infor: {...this.thietbi_ky_infor,ca_serial: this.selected_serial}
              });
							console.log(event)
							this.$refs.modal_ca_sign_hl.hide()

						}
            else
            {
              this.$toast.error(kq_kyso.desc);
              return;
            }
					}
          if (kieuky == "lay_tt")
          {
            kq_kyso =	this.LayTT()
          }

          resolve({
            sign_value:  kq_kyso.data||kq_kyso,
            device_infor: {...this.thietbi_ky_infor,ca_serial: this.selected_serial}
          });
        }

        this.cancelPromise = () => {
          reject("Bạn đã hủy bỏ thao tác duyệt!");
        }
      })
    },
		reset_form()
		{
			this.disable_button_kyso = true
			this.disable_button_huy = true
			this.is_loading = true
			this.loading_text = "Đang lấy thông tin chứng thư số....."
			this.uid = ''
			this.matkhau = ''
			this.otp = ''
			this.selected_serial = ''
			this.ds_cts = []
			this.dulieu_ky = ''
			this.cancelPromise = null
			this.resolvePromise = null
			this.token_ca_info = {}
			this.list_cts = []
		}
  }
}
</script>

<style scoped>
.loading_spin {
  text-align: center;
  margin: auto;
}
</style>
