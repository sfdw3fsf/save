<script>
import api from "../api/index.js";
import CCHTChung from '../components/CCHTChung';
import DOIPASSWIFI from '../components/DOIPASS_WIFI';
import DOIPASSONT from '../components/DOIPASS_ONT';
import THAYDOICHANELBANDWIDTH from '../components/THAYDOI_CHANEL_BANDWIDTH';
import ADDBRIDGEWAN from '../components/ADD_BRIDGE_WAN';
import MAPPORT from '../components/MAP_PORT'
import {required, maxLength} from 'vuelidate/lib/validators';
import define from "../define";
import SearchAccount from '../../SearchAccount/SearchAccount';

export default {
  props: {
    value: {
      thuebao_id: {
        default: '0'
      },
      hdtb_id: {
        default: '0'
      },
      ma_tb: {
        default: ''
      },
      kieu: {
        default: 0
      },
      ddlChucNang: {
        default: 0
      }
    }
  },
  components: {
    CCHTChung,
    DOIPASSWIFI,
    DOIPASSONT,
    THAYDOICHANELBANDWIDTH,
    ADDBRIDGEWAN,
    MAPPORT,
    SearchAccount
  },
  data() {
    return {
      UiState: {
        filter: this.value.ddlChucNang,
      },
      target: '.main-wrapper',
      txtTraCuu: this.value.ma_tb,
      visible: false,

      cboChucNang: {
        DataSource: [
          {value: define.dsChucnang.TRACUU_PORT, name: '0. TRA CỨU PORT', shortname: 'Tra cứu', id: 0},
          {value: define.dsChucnang.THONGTIN_WIFI, name: '3. [GNMS]-XEM THÔNG TIN WIFI', shortname: 'Tra cứu', id: 3},
          {value: define.dsChucnang.DOIPASS_WIFI, name: '7. [GNMS]-ĐỔI PASS WIFI', shortname: '', id: 7},
          {value: define.dsChucnang.THONGTIN_ONU, name: '6. [GNMS]-THÔNG TIN ONU', shortname: 'Tra cứu', id: 6},
          {value: define.dsChucnang.TRACUU_PASS_ONU, name: '8. [GNMS]-XEM PASS LOGIN ONU', shortname: 'Tra cứu', id: 8},
          {value: define.dsChucnang.DOIPASS_ONT, name: '9. [GNMS]-ĐỔI PASS LOGIN ONU', shortname: 'Tra cứu', id: 9},
          {value: define.dsChucnang.RESET_PORT, name: '61. RESET PORT', shortname: 'Thực thi', id: 61},
          {
            value: define.dsChucnang.THONGTIN_ADVANCE_WIFI,
            name: '18. [GNMS]- LẤY THÔNG TIN CẤU HÌNH ADVANCE CỦA WIFI',
            shortname: 'Tra cứu',
            id: 18
          },
          {
            value: define.dsChucnang.RESET_BACKUP,
            name: '19. [GNMS]- KHÔI PHỤC CÀI ĐẶT GỐC THIẾT BỊ',
            shortname: 'Thực thi',
            id: 19
          },
          {
            value: define.dsChucnang.THAYDOI_CHANEL_BANDWIDTH,
            name: '20. [GNMS]- THAY ĐỔI CHANEL, BANDWIDTH',
            shortname: 'Thực thi',
            id: 20
          },
          {value: define.dsChucnang.REBOOT_ONT, name: '21. [GNMS]- RESET THIẾT BỊ', shortname: 'Thực thi', id: 21},
          {
            value: define.dsChucnang.ADD_BRIDGE_WAN,
            name: '22. [GNMS]- CÀI ĐẶT WAN BRIDGE',
            shortname: 'Thực thi',
            id: 22
          },
          {
            value: define.dsChucnang.GET_DEVICE_CONNECT,
            name: '23. [GNMS]- Lấy danh sách MAC thiết bị kết nối wifi tới ONT',
            shortname: 'Tra cứu',
            id: 23
          },
          {
            value: define.dsChucnang.MAP_PORT,
            name: '24. [GNMS]- MAPPORT FTTH, MYTV, IMS',
            shortname: 'Thực thi',
            id: 24
          },
          {
            value: define.dsChucnang.TRACUU_VISA_VASC,
            name: '10. TRA CỨU VISA, VASC, VTN',
            shortname: 'Tra cứu',
            id: 10
          },
          {
            value: define.dsChucnang.TRACUU_APP_MYTV,
            name: '15. TRA CỨU THÔNG TIN HỖ TRỢ CÀI APP MYTV',
            shortname: 'Tra cứu',
            id: 15
          }
        ],
      },
      indChucNang: this.value.ddlChucNang,
      txtResult: '',
      iServer_DK: 1,
      _show_SERVER_DK: 0,
      vthuebao_id: 0,
      vhdtb_id: 0,
      vma_tb: "",

    };
  },
  validations: {
    txtTraCuu: {
      required,
      maxLength: maxLength(200)
    }
  },
  computed: {
    vmodel: {
      get() {
        return this.value.ma_tb
      },
      set(key, value) {
        this.$emit('input', this.form_popup)
      }
    }
  },
  created() {
    //console.log(define.dsChucnang)
    this.$nextTick(async () => {
      if (this.thuebao_id != 0) {
        this.txtTraCuu = this.value.thuebao_id
      } else if (this.hdtb_id != 0) {
        this.txtTraCuu = this.value.hdtb_id
      } else {
        this.txtTraCuu = this.value.ma_tb;
      }
      this.indChucNang = this.value.ddlChucNang
      // this.loading(false)
    })
  },

  watch: {
    'visible'(newValue, oldValue) {
      if (newValue) {
        this.txtTraCuu = this.value.ma_tb
        this.$v.$reset();
        this.$v.$touch();
      }
    }
  },

  methods: {
    show() {
      this.$refs.CCHTPopup.show();
      this.visible = true
    },
    hide() {
      // this.$emit("hideDialog");
      this.visible = false
    },
    Clear() {
      this.txtTraCuu = null;
    },
    changeChucNang(item) {
      //console.log(item)
      // load form khác, thay đổi ẩn hiện filter bên dưới
      this.UiState.filter = item
    },
    searchAccountModalClose(data) {
      //console.log("TT ma tb");
      //console.log(JSON.stringify(data));
      this.txtTraCuu = data.ma_tb;
      this.$bvModal.hide("searchAccountModalId");
    }
  }
}
</script>
<style>
.popup-body .port-info {
  background-color: #ffffff !important;
}

</style>
<template>
  <ejs-dialog
    ref="CCHTPopup"
    :enableResize='false'
    :visible="false"
    header="<span class='icon one-eye'></span><span>&nbsp;Công cụ hỗ trợ</span>"
    showCloseIcon=true
    :close="hide"
    :target="target" width='80%'
    :animationSettings="{effect: 'Zoom', duration: 400}"
    height="800">

    <div class="popup-body">
      <vue-card label="Chọn chức năng">
        <b-row>
          <b-col sm="7" cols="12">
            <vue-select labelWidth="0"
                        ref="chucnang"
                        v-model="indChucNang"
                        :options="cboChucNang.DataSource"
                        labelField="name"
                        valueField="id"
                        @input="changeChucNang">
            </vue-select>
          </b-col>
          <b-col sm="5" cols="6">
            <div class="info-row">
              <div>
                <button class="btn btn-sm" @click="$bvModal.show('searchAccountModalId')">
                  <span class="-ap icon-more_horiz"></span>
                </button>
              </div>
              <div class="value">
                <input type="text" v-model="txtTraCuu" class="form-control">
                <div class="text-sm mt-2 text-danger" v-if="$v.txtTraCuu.$error">
                  <div class="error" v-if="!$v.txtTraCuu.required">Bạn chưa nhập "Số máy/ Acc"</div>
                  <div class="error" v-if="!$v.txtTraCuu.maxLength">Nhập "Số máy/ Acc" không được quá 200 ký tự!</div>
                </div>
              </div>

            </div>
          </b-col>
        </b-row>
        <hr/>
        <b-row>
          <DOIPASSWIFI v-if="UiState.filter == 7" :value="{
                          ma_tb:this.txtTraCuu,
                          thuebao_id:this.value.thuebao_id,
                          hdtb_id:this.value.hdtb_id,
                          kieu:this.value.kieu,
                          vProfile: this.value.vProfile,
                          user_ont:this.value.user_ont,
                          pass_ont:this.value.pass_ont,
                          isGetMac:this.value.isGetMac,
                          ddlChucNang:UiState.filter,
                          isValid: this.$v.$invalid}"/>
          <DOIPASSONT v-else-if="UiState.filter == 9" :value="{
                          ma_tb:this.txtTraCuu,
                          thuebao_id:this.value.thuebao_id,
                          hdtb_id:this.value.hdtb_id,
                          kieu:this.value.kieu,
                          vProfile: this.value.vProfile,
                          user_ont:this.value.user_ont,
                          pass_ont:this.value.pass_ont,
                          isGetMac:this.value.isGetMac,
                          ddlChucNang:UiState.filter,
                          isValid: this.$v.$invalid}"/>
          <THAYDOICHANELBANDWIDTH v-else-if="UiState.filter == 20" :value="{
                          ma_tb:this.txtTraCuu,
                          thuebao_id:this.value.thuebao_id,
                          hdtb_id:this.value.hdtb_id,
                          kieu:this.value.kieu,
                          vProfile: this.value.vProfile,
                          user_ont:this.value.user_ont,
                          pass_ont:this.value.pass_ont,
                          isGetMac:this.value.isGetMac,
                          ddlChucNang:UiState.filter,
                          isValid: this.$v.$invalid}"/>
          <ADDBRIDGEWAN v-else-if="UiState.filter == 22" :value="{
                          ma_tb:this.txtTraCuu,
                          thuebao_id:this.value.thuebao_id,
                          hdtb_id:this.value.hdtb_id,
                          kieu:this.value.kieu,
                          vProfile: this.value.vProfile,
                          user_ont:this.value.user_ont,
                          pass_ont:this.value.pass_ont,
                          isGetMac:this.value.isGetMac,
                          ddlChucNang:UiState.filter,
                          isValid: this.$v.$invalid}"/>
          <MAPPORT v-else-if="UiState.filter == 24" :value="{
                          ma_tb:this.txtTraCuu,
                          thuebao_id:this.value.thuebao_id,
                          hdtb_id:this.value.hdtb_id,
                          kieu:this.value.kieu,
                          vProfile: this.value.vProfile,
                          user_ont:this.value.user_ont,
                          pass_ont:this.value.pass_ont,
                          isGetMac:this.value.isGetMac,
                          ddlChucNang:UiState.filter,
                          isValid: this.$v.$invalid}"/>
          <CCHTChung :value="{
                          ma_tb:this.txtTraCuu,
                          thuebao_id:this.value.thuebao_id,
                          hdtb_id:this.value.hdtb_id,
                          kieu:this.value.kieu,
                          vProfile: this.value.vProfile,
                          user_ont:this.value.user_ont,
                          pass_ont:this.value.pass_ont,
                          isGetMac:this.value.isGetMac,
                          ddlChucNang:UiState.filter,
                          isValid: this.$v.$invalid}" v-else/>
        </b-row>
      </vue-card>

      <b-modal
        id="searchAccountModalId"
        modal-class=""
        header-class="popup-header"
        body-class="none-padding p-0"
        class="modal-dialog"
        hide-footer
        hide-header
        hide-header-close
        scrollable
        size="xl"

      >
        <SearchAccount ref="searchAccount"
                       @form-close="searchAccountModalClose"
                       :isPopup="true"
        ></SearchAccount>

      </b-modal>
    </div>
  </ejs-dialog>
</template>
