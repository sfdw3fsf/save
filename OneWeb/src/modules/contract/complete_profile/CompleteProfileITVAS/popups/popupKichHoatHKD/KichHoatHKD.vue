<template>
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="hieu_ung_popup" ref="frmKichHoatHKD" :position="position" :header="'Kích hoạt HKD'" showCloseIcon="true" width="50%" target="#app .main-wrapper">
    <div class="">
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="javascript:;" @click="btnGhiLai_Click">
                <span class="icon one-file-arrow-right1"></span> Kích hoạt
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
                  <ejs-combobox ref='cboTinh' :popupHeight='300' @change='this.cboTinh_Changed' :allowFiltering='true' :fields='fields_tinh' v-model:value='tinh_id' :dataSource='this.lst_tinh'></ejs-combobox>
                </div>
              </div>
            </div>
            <div class="info-row">
                  <div class="key w110">Quận</div>
                  <div class="value">
                      <div class="select-custom">
                        <ejs-combobox ref='cboQuan' :popupHeight='300' @change='this.cboQuan_Changed' :allowFiltering='true' :fields='fields' v-model:value='quan_id' :dataSource='this.lst_quan'></ejs-combobox>
                      </div>
                  </div>
                  <div class="key w110" style="padding-left: 10px;">Phường</div>
                  <div class="value">
                      <div class="select-custom">
                        <ejs-combobox ref='cboPhuong' :popupHeight='300' @change='this.cboPhuong_Changed' :allowFiltering='true' :fields='fields' v-model:value='phuong_id' :dataSource='this.lst_phuong'></ejs-combobox>
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
  name: 'KichHoatHKD',
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
    KichHoatHKD()
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
      api.fn_capnhat_diachi_hkd(axios,{vhdtb_id: self.hdtb_id, vdata: JSON.stringify(diachi)})
        .then(res => {
          if(res.data.data == "ok" && res.data.error_code == "BSS-00000000")
          {
            api.fn_tao_req_cntt(axios, {vhdtb_id: self.hdtb_id})
              .then(res => {
                req_hkd = res;
                api.fn_datmoi_hkd(axios, {
                  StrRequest: req_hkd.data.data.replace('\\r\\n', ''),
                  StrLink: "",
                  JwtToken: ""
                }).then(res => {
                  if(res.data.data.errCode == "200")
                  {
                    this.loading(false);
                    self.$toast.success(res.data.data.errMsg);
                    api.cap_nhat_status_hdtb(axios, {
                      vhdtb_id: self.hdtb_id,
                      vstatus: 6
                    });
                  }
                  else {
                    this.loading(false);
                    self.$toast.error(res.data.data.errMsg);
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
      api.fn_layds_quan(axios, {tinh_id: self.tinh_id})
        .then(res => {
          self.lst_quan = res.data.data;
        }).catch(ex => {
          throw ex;
      })
    },
    cboQuan_Changed()
    {
      let self = this;
      api.fn_layds_phuong(axios, {tinh_id: self.tinh_id, quan_id: self.quan_id})
        .then(res => {
          self.lst_phuong = res.data.data;
        }).catch(ex => {
        throw ex;
      })
    },
    btnGhiLai_Click()
    {
      let self = this;
      this.KichHoatHKD();
    },
    openDialog(dulieu)
    {
        this.resetForm();
        let self = this;
        api.lay_ds_tinh_thicong_hkd(axios, {})
          .then(res => {
              self.lst_tinh = res.data.data;
              self.tinh_id = self.$root.token.getPhanVungID() * 1,
              self.hdtb_id = dulieu.hdtb_id;
              self.cboTinh_Changed();
              //self.$refs.cboQuan.dataSource = self.lst_quan;
            // this.$refs.cboTinh.refresh();
           //  console.log(this.lst_tinh);
             this.$refs.frmKichHoatHKD.show();
          }).catch(ex => {
            console.log(ex);
            this.$refs.frmKichHoatHKD.show();
        })
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
