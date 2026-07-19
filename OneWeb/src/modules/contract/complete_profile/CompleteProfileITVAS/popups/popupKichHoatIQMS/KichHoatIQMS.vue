<template>
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="hieu_ung_popup" ref="frmKichHoatIQMS" :position="position" :header="'Kích hoạt IQMS'" showCloseIcon="true" width="50%" target="#app .main-wrapper">
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" @click="btnGhiLai_Click">
              <span class="icon one-file-arrow-right1"></span> Kích hoạt IQMS
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <div class="key w110">Tỉnh/Thành phố</div>
            <div class="value">
              <div class="select-custom">
                <ejs-combobox ref='cboTinh'  filterType='"Contains"' :popupHeight='300' @change='this.cboTinh_Changed' :allowFiltering='true' :fields='fields_tinh' v-model:value='tinh_id' :dataSource='this.lst_tinh'></ejs-combobox>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w110">Quận</div>
            <div class="value">
              <div class="select-custom">
                <ejs-combobox ref='cboQuan'  filterType='"Contains"' :popupHeight='300' @change='this.cboQuan_Changed' :allowFiltering='true' :fields='fields' v-model:value='quan_id' :dataSource='this.lst_quan'></ejs-combobox>
              </div>
            </div>
            <div class="key w110" style="padding-left: 10px;">Phường</div>
            <div class="value">
              <div class="select-custom">
                <ejs-combobox ref='cboPhuong' filterType='"Contains"' :popupHeight='300' @change='this.cboPhuong_Changed' :allowFiltering='true' :fields='fields' v-model:value='phuong_id' :dataSource='this.lst_phuong'></ejs-combobox>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w110">Địa chỉ</div>
            <div class="value">
              <input type="text" class="form-control" v-model="diachi_dk">
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
import axios from 'axios';
import api from '@/modules/contract/complete_profile/CompleteProfileITVAS/API/CompleteProfileAPI.js'
import {DataManager, JsonAdaptor  } from '@syncfusion/ej2-data';
import { ComboBoxPlugin  } from "@syncfusion/ej2-vue-dropdowns";
//Vue.use(ComboBoxPlugin);
export default {
  components: {
    moment,
    DatePicker,
    api,
  },
  name: 'KichHoatIQMS',
  data() {
    let self = this;
    // console.log(self.$root.token);
    return {
      fields: {text: 'Ten', value:'Id'},
      fields_tinh:{text: 'tentinh', value: 'tinh_id'},
      quan_id: null,
      ten_quan: "",
      phuong_id: null,
      ten_phuong: "",
      tinh_id: self.$root.token.getPhanVungID() * 1,
      lst_tinh: [],
      lst_quan: [],
      lst_phuong: [],
      hieu_ung_popup: { effect: 'Zoom' },
      position: { X: 'center', Y: 'center' },
      diachi_dk: "",
      diachi_ids: "",
      hdtb_id: 0
    }
  },
  methods: {
    mounted()
    {

    },
    KichHoatIQMS()
    {

      let self = this;
      this.diachi_ids = this.phuong_id + "," + this.quan_id +"," + this.tinh_id;
      //this.diachi_dk = this.$refs.cboPhuong.ej2Instances.text + ", " + this.$refs.cboQuan.ej2Instances.text + ", " + this.$refs.cboTinh.ej2Instances.text;
      let diachi = {
        mucdich_ids: self.diachi_ids,
        diachi_dt: self.diachi_dk
      };
      let req_hkd = "";
      this.loading(true);
      console.log(self.hdtb_id);
      api.fn_capnhat_diachi_iqms(axios,{vhdtb_id: self.hdtb_id, vdata: JSON.stringify(diachi)})
        .then(res => {
          if(res.data.data == "ok" && res.data.error_code == "BSS-00000000")
          {
            api.fn_tao_req_cntt(axios, {vhdtb_id: self.hdtb_id})
              .then(res => {
                req_hkd = res;
                api.fn_datmoi_iqms(axios, {
                  StrRequest: req_hkd.data.data.replace('\\r\\n', ''),
                  StrLink: "",
                  JwtToken: ""
                }).then(res => {
                  if(res.data.data.errorCode == "0")
                  {
                    this.loading(false);
                    self.$toast.success(res.data.data.errorMsg);
                    api.cap_nhat_status_hdtb(axios, {
                      vhdtb_id: self.hdtb_id,
                      vstatus: 6
                    });
                  }
                  else {
                    this.loading(false);
                    self.$toast.error(res.data.data.errorMsg);
                    console.log(res.data);
                  }
                }).catch(ex => { this.loading(false); throw ex});
              }).catch(ex=> {this.loading(false); throw ex});
          }
          else {
            this.loading(false);
            self.$toast.error(res.data.message + " - " + res.data.data);
          }
        }).catch(ex => {
        this.loading(false);
        throw ex;
      })

    },
    cboPhuong_Changed()
    {
      let self = this;
      this.diachi_dk = this.$refs.cboPhuong.ej2Instances.text + ", " + this.$refs.cboQuan.ej2Instances.text + ", " + this.$refs.cboTinh.ej2Instances.text
    },
    cboTinh_Changed()
    {
      let self = this;
      api.fn_layds_quan_iqms(axios, {tinh_id: self.tinh_id})
        .then(res => {
          self.lst_quan = res.data.data;
        }).catch(ex => {
        throw ex;
      })
    },
    cboQuan_Changed()
    {
      let self = this;
      api.fn_layds_phuong_iqms(axios, {tinh_id: self.tinh_id, quan_id: self.quan_id})
        .then(res => {
          self.lst_phuong = res.data.data;
        }).catch(ex => {
        throw ex;
      })
    },
    btnGhiLai_Click()
    {
      let self = this;
      this.KichHoatIQMS();
    },
    openDialog(dulieu)
    {
      this.resetForm();
      let self = this;
      self.hdtb_id = dulieu.hdtb_id;
      console.log(self.hdtb_id);
      let ds_tinh = '[{"phanvung_id":1,"tinh_id":89,"tentinh":"An Giang"},{"phanvung_id":3,"tinh_id":24,"tentinh":"Bắc Giang"},{"phanvung_id":4,"tinh_id":6,"tentinh":"Bắc Cạn"},{"phanvung_id":5,"tinh_id":27,"tentinh":"Bắc Ninh"},{"phanvung_id":7,"tinh_id":83,"tentinh":"Bến Tre"},{"phanvung_id":8,"tinh_id":74,"tentinh":"Bình Dương"},{"phanvung_id":9,"tinh_id":70,"tentinh":"Bình Phước"},{"phanvung_id":11,"tinh_id":95,"tentinh":"Bạc Liêu"},{"phanvung_id":12,"tinh_id":4,"tentinh":"Cao Bằng"},{"phanvung_id":13,"tinh_id":92,"tentinh":"Cần Thơ"},{"phanvung_id":16,"tinh_id":66,"tentinh":"Đắc Lắc"},{"phanvung_id":17,"tinh_id":75,"tentinh":"Đồng Nai"},{"phanvung_id":18,"tinh_id":87,"tentinh":"Đồng Tháp"},{"phanvung_id":19,"tinh_id":64,"tentinh":"Gia Lai"},{"phanvung_id":20,"tinh_id":2,"tentinh":"Hà Giang"},{"phanvung_id":22,"tinh_id":11,"tentinh":"Điện Biên"},{"phanvung_id":24,"tinh_id":33,"tentinh":"Hưng Yên"},{"phanvung_id":25,"tinh_id":35,"tentinh":"Hà Nam"},{"phanvung_id":27,"tinh_id":30,"tentinh":"Hải Dương"},{"phanvung_id":28,"tinh_id":79,"tentinh":"TP Hồ Chí Minh"},{"phanvung_id":31,"tinh_id":62,"tentinh":"Kon Tum"},{"phanvung_id":32,"tinh_id":12,"tentinh":"Lai Châu"},{"phanvung_id":33,"tinh_id":20,"tentinh":"Lạng Sơn"},{"phanvung_id":34,"tinh_id":10,"tentinh":"Lào Cai"},{"phanvung_id":35,"tinh_id":68,"tentinh":"Lâm Đồng"},{"phanvung_id":36,"tinh_id":80,"tentinh":"Long An"},{"phanvung_id":37,"tinh_id":36,"tentinh":"Nam Định"},{"phanvung_id":39,"tinh_id":37,"tentinh":"Ninh Bình"},{"phanvung_id":41,"tinh_id":54,"tentinh":"Phú Yên"},{"phanvung_id":47,"tinh_id":94,"tentinh":"Sóc Trăng"},{"phanvung_id":49,"tinh_id":14,"tentinh":"Sơn La"},{"phanvung_id":50,"tinh_id":72,"tentinh":"Tây Ninh"},{"phanvung_id":53,"tinh_id":46,"tentinh":"Huế"},{"phanvung_id":55,"tinh_id":84,"tentinh":"Trà Vinh"},{"phanvung_id":56,"tinh_id":8,"tentinh":"Tuyên Quang"},{"phanvung_id":57,"tinh_id":86,"tentinh":"Vĩnh Long"},{"phanvung_id":58,"tinh_id":26,"tentinh":"Vĩnh Phúc"},{"phanvung_id":59,"tinh_id":25,"tentinh":"Phú Thọ"},{"phanvung_id":60,"tinh_id":15,"tentinh":"Yên Bái"},{"phanvung_id":61,"tinh_id":19,"tentinh":"Thái Nguyên"},{"phanvung_id":64,"tinh_id":67,"tentinh":"Đắc Nông"},{"phanvung_id":65,"tinh_id":17,"tentinh":"Hòa Bình"},{"phanvung_id":6,"tinh_id":52,"tentinh":"Bình Định"},{"phanvung_id":14,"tinh_id":96,"tentinh":"Cà Mau"},{"phanvung_id":21,"tinh_id":1,"tentinh":"Hà Nội"},{"phanvung_id":26,"tinh_id":31,"tentinh":"Hải Phòng"},{"phanvung_id":29,"tinh_id":56,"tentinh":"Khánh Hoà"},{"phanvung_id":30,"tinh_id":91,"tentinh":"Kiên Giang"},{"phanvung_id":38,"tinh_id":40,"tentinh":"Nghệ An"},{"phanvung_id":40,"tinh_id":58,"tentinh":"Ninh Thuận"},{"phanvung_id":2,"tinh_id":77,"tentinh":"Bà Rịa - Vũng Tàu"},{"phanvung_id":46,"tinh_id":45,"tentinh":"Quảng Trị"},{"phanvung_id":10,"tinh_id":60,"tentinh":"Bình Thuận"},{"phanvung_id":15,"tinh_id":48,"tentinh":"TP. Đà Nẵng"},{"phanvung_id":23,"tinh_id":42,"tentinh":"Hà Tĩnh"},{"phanvung_id":42,"tinh_id":44,"tentinh":"Quảng Bình"},{"phanvung_id":45,"tinh_id":22,"tentinh":"Quảng Ninh"},{"phanvung_id":51,"tinh_id":34,"tentinh":"Thái Bình"},{"phanvung_id":52,"tinh_id":38,"tentinh":"Thanh Hoá"},{"phanvung_id":54,"tinh_id":82,"tentinh":"Tiền Giang"},{"phanvung_id":66,"tinh_id":93,"tentinh":"Hậu Giang"},{"phanvung_id":44,"tinh_id":51,"tentinh":"Quảng Ngãi"},{"phanvung_id":43,"tinh_id":49,"tentinh":"Quảng Nam"}]';
      let obj_tinh = JSON.parse(ds_tinh);
      self.tinh_id = obj_tinh.find(x => x.phanvung_id === self.tinh_id).tinh_id;
      this.lst_tinh = obj_tinh;

      self.cboTinh_Changed();
      this.$refs.frmKichHoatIQMS.show();
    },
    resetForm()
    {
      this.quan_id = null;
      this.ten_quan = "";
      this.phuong_id = null;
      this.ten_phuong = "";
      this.diachi_dk = "",
        this.hdtb_id = 0;
    }
  }
}
</script>

<style>

</style>
