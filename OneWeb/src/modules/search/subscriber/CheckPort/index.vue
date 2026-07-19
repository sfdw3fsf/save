<template>
  <div>
    <vue-bread-crumb v-if="type == 'view'" :header="header" />
    <vue-nav>
      <ul class="list">
        <li style="width: 150px; margin: 0 10px;">
          <SelectExt :disabled="!tscboSRV_DoKiem.enabled" v-model="tscboSRV_DoKiem.value" :dataSource="tscboSRV_DoKiem.list" dataTextField="server_name" dataValueField="server_id"></SelectExt>
        </li>
        <li @click="tracuuport">
          <a> <span class="icon one-file-search"></span>Tra cứu Port </a>
        </li>
        <li @click="resetport" v-if="tsbtnReset">
          <a> <span class="icon one-reload"></span>Reset Port </a>
        </li>
        <li @click="setprofile" v-if="tsbtnSetPortProfile">
          <a> <span class="icon nc-icon-glyph ui-1_edit-74"></span>Set Profile </a>
        </li>
        <li @click="resetpassigate040H">
          <a> <span class="icon one-reload"></span>Reset Pass Igate040H </a>
        </li>
        <li @click="resetpassONT">
          <a> <span class="icon one-reload"></span>Reset Pass Ont </a>
        </li>
        <li @click="wifiinfo">
          <a> <span class="icon -ap icon-info"></span>Wifi information </a>
        </li>
        <li @click="checkAccOnline">
          <a> <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span>Check Account Online </a>
        </li>
        <li @click="khoacong">
          <a> <span class="icon ui-1_lock nc-icon-glyph"></span>Khóa cổng </a>
        </li>
        <li @click="mocong">
          <a> <span class="icon one-key"></span>Mở cổng </a>
        </li>
      </ul>
    </vue-nav>
    <div class="form-content">
      <div class="box-form" style="margin-top:10px">
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">Số máy/Acc</div>
              <div class="value">
                <input type="text" v-model="ma_tb.value" ref="maTBInput" class="form-control" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Tốc độ</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="cboTocDo.value" :allowFiltering="true" dataTextField="ten_profile" dataValueField="diffgr:id" :dataSource="cboTocDo.list"></SelectExt>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w20">
                <input type="checkbox" class="check" name="" v-model="chkMacAddress.value" />
              </div>
              <div class="value">
                <span class="name">Get the Mac address</span>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">User ONT</div>
              <div class="value">
                <input type="text" class="form-control" name="" v-model="txtUserOnt.value" ref="userONUFocus" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Pass ONT</div>
              <div class="value">
                <input type="text" class="form-control" name="" v-model="txtPassONT.value" ref="passONUFocus" />
              </div>
            </div>
          </div>
        </div>
        <div>
          <textarea v-model="txtOutPut.value" disabled name="" class="form-control port-info" style="height: 400px;resize: none;"></textarea>
        </div>
      </div>
    </div>

    <CongCuHoTro
      ref="CongCuHoTro"
      :value="{
        // vma_tb: ma_tb.value,
        thuebao_id: vthuebao_id,
        hdtb_id: vhdtb_id,
        ma_tb: ma_tb.value,
        kieu: vkieu,
        vProfile: cboTocDo.value,
        user_ont: txtUserOnt.value,
        pass_ont: txtPassONT.value,
        isGetMac: chkMacAddress.value ? 1 : 0,
        ddlChucNang: 7
      }"
    />
    <!-- <ejs-dialog 
          ref="CongCuHoTro"
          :enableResize='false' 
          :visible="false" 
          header="Công cụ hỗ trợ"
          showCloseIcon=true
          :target="target" width='80%'
          :close="hideCongCuHoTroPopup"
          :animationSettings="{effect: 'Zoom', duration: 400}"
          height="800">
            <CongCuHoTro :isShow="UiState.popup.CongCuHoTro" :ma_tb="ma_tb.value"/>
          </ejs-dialog> -->
  </div>
</template>
<script>
// import define from './define'
import api from './api/index.js'
import CongCuHoTro from './popups/CongCuHoTro'
import { required, maxLength } from 'vuelidate/lib/validators'
export default {
  props: {
    type: {
      type: String,
      default: 'view'
    },
    vthuebao_id: {
      default: 0
    },
    vhdtb_id: {
      default: 0
    },
    vma_tb: {
      type: String,
      default: null
    },
    vkieu: {
      default: 0
    },
    vtinh: {
      default: 0
    },
    vmatinh: {
      default: ''
    },
    tsbtnReset: {
      type: Boolean,
      default: true
    },
    tsbtnSetPortProfile: {
      type: Boolean,
      default: true
    },
  },
  components: {
    CongCuHoTro
  },
  data() {
    return {
      // ...define,
      header: {
        title: 'Kiểm tra Port thuê bao',
        list: [
          //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
          // { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          // { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      tscboSRV_DoKiem: {
        enabled: true,
        list: [],
        value: '5'
      },
      cboTocDo: { enabled: true, list: [], value: null },
      ma_tb: {
        enabled: true,
        value: null
      },
      chkMacAddress: {
        enabled: true,
        value: null
      },
      txtUserOnt: {
        enabled: true,
        value: null
      },
      txtPassONT: {
        enabled: true,
        value: null
      },
      txtOutPut: {
        enabled: true,
        value: null
      }
      // kieu: null,
    }
  },
  async mounted() {
    await this.init()
    // this.frmCheckPort_Load()
    //this.getDsTocDo();
  },
  computed: {
    phanvungId: () => {
      const OneBSSClient = localStorage.getItem('OneBSS-Client') ? localStorage.getItem('OneBSS-Client') : null
      let phanvung_id = 28
      // if (OneBSSClient) {
      //   phanvung_id = JSON.parse(OneBSSClient)['phanvung_id'];
      // }
      return phanvung_id
    }
  },
  watch: {},
  methods: {
    async init() {
      if (this.type == 'view') {
        this.initView()
      }
      if (this.type == 'modal') {
        this.initModal()
      }

      if (this.ma_tb.value != null) {
        this.tracuuport()
      }

      var data = await api.lay_ds_server_dokiem(this.axios, {})
      if (data.data.data && data.data.data.length > 0) {
        this.tscboSRV_DoKiem.list = data.data.data
      } else {
        this.tscboSRV_DoKiem.list = []
      }
      this.tscboSRV_DoKiem.value = Number(this.vtinh) == 21 ? '3' : '5'
    },
    async initView() {},
    async initModal() {
      this.ma_tb.value = this.vma_tb
    },
    async frmCheckPort_Load() {
      this.loading(true)
      if (this.vthuebao_id != 0) {
        this.ma_tb.value = this.vthuebao_id
        this.vtbid = 1
      } else if (this.vhdtb_id != 0) {
        this.ma_tb.value = this.vhdtb_id
        this.vtbid = 2
      } else {
        this.ma_tb.value = this.ma_tb
        this.vtbid = 0
      }
      await this.getDsTocDo()

      //console.log(this.tscboSRV_DoKiem.value);
      //await this.testPort(this.vtbid);
      if (((this.ma_tb.value || '') + '').trim() != '') {
        await this.doKiem(0)
      }

      this.loading(false)
    },
    async tracuuport() {
      this.loading(true)
      try {
        const vkieu = 0

        if (!this.KiemTra_SoMayACC()) {
          return false
        }
        // Gọi API xử lý sự kiện
        await this.doKiem(0)
      } catch (error) {
        this.$toast.error(JSON.stringify(error))
      } finally {
        this.loading(false)
      }
    },
    async resetport() {
      try {
        const vkieu = 1
        if (!this.KiemTra_SoMayACC()) {
          return false
        }
        this.loading(true)
        // Gọi API xử lý sự kiện
        await this.doKiem(vkieu)
      } catch (error) {
        this.$toast.error(JSON.stringify(error))
      } finally {
        this.loading(false)
      }
    },
    async setprofile() {
      try {
        const vkieu = 2
        if (!this.KiemTra_SoMayACC()) {
          return false
        }
        this.loading(true)
        // Gọi API xử lý sự kiện
        await this.doKiem(vkieu)
      } catch (error) {
        this.$toast.error(JSON.stringify(error))
      } finally {
        this.loading(false)
      }
    },
    async resetpassigate040H() {
      try {
        const vkieu = 5
        if (!this.KiemTra_SoMayACC()) {
          return false
        }

        if (this.txtUserOnt.value == null || this.txtPassONT.value == null) {
          this.$toast.error('Bạn chưa nhập user, pass ONT')
          if (this.txtUserOnt.value == null) {
            this.$refs.userONUFocus.focus()
            return false
          } else {
            this.$refs.passONUFocus.focus()
            return false
          }
        } else {
          if (this.txtUserOnt.value != null) {
            if (`${this.txtUserOnt.value}`.trim() == '') {
              this.$toast.error('Bạn chưa nhập user ONT')
              this.$refs.userONUFocus.focus()
              return false
            }
          }
          if (this.txtPassONT.value != null) {
            if (`${this.txtPassONT.value}`.trim() == '') {
              this.$toast.error('Bạn chưa nhập pass ONT')
              this.$refs.passONUFocus.focus()
              return false
            }
          }
        }

        this.loading(true)
        // Gọi API xử lý sự kiện
        await this.doKiem(vkieu)
      } catch (error) {
        this.$toast.error(JSON.stringify(error))
      } finally {
        this.loading(false)
      }
    },
    async resetpassONT() {
      try {
        const vkieu = 4
        if (!this.KiemTra_SoMayACC()) {
          return false
        }
        if (this.txtUserOnt.value == null || this.txtPassONT.value == null) {
          this.$toast.error('Bạn chưa nhập user, pass ONT')
          if (this.txtUserOnt.value == null) {
            this.$refs.userONUFocus.focus()
            return false
          } else {
            this.$refs.passONUFocus.focus()
            return false
          }
        } else {
          if (this.txtUserOnt.value != null) {
            if (`${this.txtUserOnt.value}`.trim() == '') {
              this.$toast.error('Bạn chưa nhập user ONT')
              this.$refs.userONUFocus.focus()
              return false
            }
          }
          if (this.txtPassONT.value != null) {
            if (`${this.txtPassONT.value}`.trim() == '') {
              this.$toast.error('Bạn chưa nhập pass ONT')
              this.$refs.passONUFocus.focus()
              return false
            }
          }
        }
        this.loading(true)
        // Gọi API xử lý sự kiện
        await this.doKiem(vkieu)
      } catch (error) {
        this.$toast.error(JSON.stringify(error))
      } finally {
        this.loading(false)
      }
    },
    async wifiinfo() {
      try {
        if (!this.KiemTra_SoMayACC()) {
          return false
        }
        this.loading(true)
        // Gọi API xử lý sự kiện

        this.$refs.CongCuHoTro.show()
        // this.UiState.popup.CongCuHoTro = true;
        this.loading(false)
      } catch (e) {
        //console.log(error);
        this.$toast.error(`${e}`)
        this.loading(false)
      }
    },
    async checkAccOnline() {
      try {
        const vkieu = 3
        if (!this.KiemTra_SoMayACC()) {
          return false
        }
        this.loading(true)
        // Gọi API xử lý sự kiện
        await this.doKiem(vkieu)
      } catch (error) {
        this.$toast.error(JSON.stringify(error))
      } finally {
        this.loading(false)
      }
    },
    async khoacong() {
      try {
        if (!this.KiemTra_SoMayACC()) {
          return false
        }
        this.loading(true)
        // Gọi API xử lý sự kiện
        //
        const params = await this.getParams_input_api_khoamo()
        //console.log(params)
        // const params = {
        //                   "comm": "private",
        //                   "loaigpon": 0,
        //                   "ponid": 0,
        //                   "port": 8,
        //                   "ipgpon": "10.31.16.210",
        //                   "slot": 1,
        //                   "frame": 0,
        //                   "ontid": 17
        //                 }

        const res = await api.khoaMay_PON(this.axios, params)
        if (res.data.data && res.data.data.length > 0) {
          //this.txtOutPut.value = response.data.data
          //console.log(res);
          if (res.data.data == 'ok') {
            this.$toast.success('Khóa cổng thành công !')
            this.txtOutPut.value = res.data.data
          } else {
            const {
              data: { data, message }
            } = res
            this.txtOutPut.value = message
            this.$toast.error(message)
            //console.log(message);
          }
        }
        //this.loading(false);
      } catch (error) {
        this.$toast.error(JSON.stringify(error))
      } finally {
        this.loading(false)
      }
    },
    async mocong() {
      try {
        if (!this.KiemTra_SoMayACC()) {
          return false
        }
        this.loading(true)
        // Gọi API xử lý sự kiện
        const params = await this.getParams_input_api_khoamo()
        //console.log(params)
        // const params = {
        //                   "comm": "private",
        //                   "loaigpon": 0,
        //                   "ponid": 0,
        //                   "port": 8,
        //                   "ipgpon": "10.31.16.210",
        //                   "slot": 1,
        //                   "frame": 0,
        //                   "ontid": 17
        //                 }

        const res = await api.moMay_PON(this.axios, params)
        if (res.data.data && res.data.data.length > 0) {
          //this.txtOutPut.value = response.data.data
          //console.log(res);
          if (res.data.data == 'ok') {
            this.$toast.success('Mở khóa cổng thành công !')
            this.txtOutPut.value = res.data.data
          } else {
            const {
              data: { data, message }
            } = res
            this.txtOutPut.value = message
            this.$toast.error(message)
            //console.log(message);
          }
        }
      } catch (error) {
        this.$toast.error(JSON.stringify(error))
      } finally {
        this.loading(false)
      }
    },
    async thoat() {
      this.$bvModal
        .msgBoxConfirm('Bạn có muốn đóng không?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          try {
          } catch (error) {
            //console.log(error);
            this.$toast.error(JSON.stringify(error))
            this.loading(false)
          }
        })
    },
    Clear() {
      this.ma_tb.value = null
      this.chkMacAddress.value = null
      this.txtUserOnt.value = null
      this.txtOutPut.value = null
      this.txtPassONT.value = null
    },
    async getDsTocDo() {
      try {
        //let data = await this.axios.get("web-ccdv/HoanCongThuTienPhatSinh/SP_LAY_THONGTIN_TOCDO_ADSL_TSL?P_DICHVUVT_ID=4&P_LOAITB_ID=11&P_TOCDO_ID=515");
        api.getListProfileDSL(this.axios).then((res) => {
          this.cboTocDo.list = res.data.data
          this.cboTocDo.value = this.cboTocDo.list[0]['diffgr:id']
          //console.log(res.data.data);
        })
        //this.cboTocDo.list = data.data.data
        //console.log(data.data.data);
      } catch (err) {}
    },
    KiemTra_SoMayACC() {
      if (this.ma_tb.value == null) {
        this.$toast.error('Bạn chưa nhập "Số máy/ Acc"!')
        this.$refs.maTBInput.focus()
        return false
      } else {
        if (`${this.ma_tb.value}`.trim() == '') {
          this.$toast.error('Bạn chưa nhập "Số máy/ Acc"!')
          this.$refs.maTBInput.focus()
          return false
        } else {
          if (`${this.ma_tb.value}`.trim().length > 200) {
            this.$toast.error('Nhập "Số máy/ Acc" không được quá 200 ký tự!')
            this.$refs.maTBInput.focus()
            //this.txtThongBao.focus();
            return false
          }
        }
      }
      return true
    },
    // Lấy danh sách tham số mặc định mã tham số
    async layDSThamsoMDMaTS_SHOW_SERVER_DK() {
      try {
        //  const params = {
        //                   "p_list_of_cols": "*",
        //                   "p_order": "",
        //                   // "p_phanvung_id": this.phanvungId,
        //                   "p_phanvung_id": 28,
        //                   "p_schema": "admin",
        //                   "p_table_name": "thamso_md",
        //                   "p_where": "ma_ts = 'SHOW_SERVER_DK'"
        //                 }
        const params = { phanvung_id: this.phanvungId, ma_ts: 'SHOW_SERVER_DK' }

        const res = await api.lay_ds_thamso_md_mats(this.axios, params)
        //console.log(res)
        if (res.data.data && res.data.data.length > 0) {
          this._show_SERVER_DK = res.data.data[0]['macdinh']
        } else {
          this._show_SERVER_DK = 28
        }
        //console.log(res.data.data[0]["macdinh"]);
      } catch (err) {
        this._show_SERVER_DK = 28
      }
    },
    // Lấy danh sách tham số mặc định mã tham số
    async layDSThamsoMDMaTS_SERVER_DK() {
      //console.log(this.phanvungId)
      try {
        // const params = {
        //                   "p_list_of_cols": "macdinh",
        //                   "p_order": "",
        //                   "p_phanvung_id": this.phanvungId,
        //                   "p_schema": "admin",
        //                   "p_table_name": "thamso_md",
        //                   "p_where": "ma_ts = 'SERVER_DK'"
        //                 }
        const params = { phanvung_id: this.phanvungId, ma_ts: 'SERVER_DK' }
        const res = await api.lay_ds_thamso_md_mats(this.axios, params)
        console.log('lay_ds_thamso_md_mats')
        console.log(res)
        if (res.data.data && res.data.data.length > 0 && this.tscboSRV_DoKiem.value == null) {
          this.tscboSRV_DoKiem.value = res.data.data[0]['macdinh']
        } else {
          this.tscboSRV_DoKiem.value = Number(this.vtinh) == 21 ? '3' : '5'
        }
        //console.log(res.data.data[0]["macdinh"]);
        //console.log(res.data);
      } catch (err) {
        // this.tscboSRV_DoKiem.value = 1
      }
    },
    async doKiem(vkieu) {
      try {
        // Trường hợp thuebao_id truyền sang nhưng input lại kiểu acc
        // console.log('Kieu so: ' + this.ma_tb.value);
        // console.log(isNaN(this.ma_tb.value));
        // if (isNaN(this.ma_tb.value))
        // {
        //       this.vma_tb = this.ma_tb.value;
        //       this.vhdtb_id=0;
        //       this.vthuebao_id=0;
        // }
        // else
        // {
        //   if (this.vthuebao_id != 0){
        //       this.vthuebao_id = this.ma_tb.value;
        //       this.vhdtb_id=0;
        //       this.vma_tb ="";
        //     } else if (this.vhdtb_id != 0) {
        //       this.vhdtb_id = this.ma_tb.value;
        //       this.vthuebao_id=0;
        //       this.vma_tb ="";
        //     }
        //     else {
        //       this.vma_tb = this.ma_tb.value;
        //       this.vhdtb_id=0;
        //       this.vthuebao_id=0;
        //     }
        // }
        var vma_tb = null,
          vthuebao_id = null,
          vhdtb_id = null
        console.log('showPort')
        console.log()
        if (this.ma_tb.value != null && this.ma_tb.value != '') {
          vthuebao_id = 0
          vhdtb_id = 0
          vma_tb = this.ma_tb.value
        } else if (this.vthuebao_id != 0 && this.vthuebao_id != '') {
          vthuebao_id = this.vthuebao_id
          vhdtb_id = 0
          vma_tb = ''
        } else if (this.vhdtb_id != 0 && this.vhdtb_id != '') {
          vthuebao_id = 0
          vhdtb_id = this.vhdtb_id
          vma_tb = ''
        }
        await this.layDSThamsoMDMaTS_SHOW_SERVER_DK()
        await this.layDSThamsoMDMaTS_SERVER_DK()

        const pMacAddress = this.chkMacAddress.value ? 1 : 0
        const params = {
          show_server_dk: this._show_SERVER_DK,
          server_id: this.tscboSRV_DoKiem.value,
          vthuebao_id: `${vthuebao_id}`.trim(),
          vhdtb_id: `${vhdtb_id}`.trim(),
          vma_tb: `${vma_tb}`.trim(),
          vProfile: this.cboTocDo.value,
          user_ont: ((this.txtUserOnt.value || '') + '').trim(),
          pass_ont: (this.txtPassONT.value || '' + '').trim(),
          isGetMac: pMacAddress,
          vkieu: vkieu,
          phanvung_id: this.vtinh,
          ma_tinh: this.vmatinh
        }
        //console.log(params)
        const response = await api.doKiem(this.axios, params)

        if (response.data.data && response.data.data.length > 0) {
          this.txtOutPut.value = response.data.data
        } else {
          const {
            data: { data, message }
          } = response
          this.txtOutPut.value = message
          //console.log(message);
        }
      } catch (e) {
        this.$toast.error(`${e}`)
        this.txtOutPut.value = ''
        //console.log(err);
        //this.loading(false);
      }
    },
    async getParams_input_api_khoamo() {
      try {
        const response = await api.sp_khoamoi_may_pon_input(this.axios, { p_ma_tb: this.ma_tb.value })
        if (response.data.data && response.data.data.length > 0) {
          //this.txtOutPut.value = response.data.data
          const [params] = response.data.data
          return params
        } else {
          const {
            data: { data, message }
          } = response
          return {}
          //this.txtOutPut.value = message;
          //console.log(message);
        }
      } catch (err) {
        this.$toast.error(JSON.stringify(err))
        //this.txtOutPut.value = "";
        //console.log(err);
        //this.loading(false);
        return {}
      }
    },
    // Lấy tham số đo kiểm theo tỉnh
    async layTSDoKiemTheoTinh() {
      try {
        const res = await api.lay_ts_dokiem_theo_tinh(this.axios)
        // this.vuser_alt = res.data.data[0]["user_alt"];
        // this.vpass_alt = res.data.data[0]["pass_alt"];
        // this.vuser_xtest = res.data.data[0]["user_xtest"];
        // this.vpass_xtest = res.data.data[0]["pass_xtest"];
      } catch (err) {}
    },
    // Lấy danh sách server đo kiểm
    async layDSServerDoKiem() {
      try {
        const res = await api.lay_ds_server_dokiem(this.axios)
        // this.server_id = res.data.data[0]["server_id"];
        // this.server_name = res.data.data[0]["server_name"];
      } catch (err) {}
    }
  }
}
</script>
<style scoped>
.no-header .e-columnheader {
  display: none;
}
li.non-active a {
  color: #d3d3d3 !important;
}
.center {
  text-align: center !important;
}
.form-content {
  padding: 10px;
}
</style>
