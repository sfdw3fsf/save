<template>
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="hieu_ung_popup" ref="frmCapNhatDV" :position="position" :header="'Cập nhật đơn vị nhận phiếu'" showCloseIcon="true" width="50%" target="#app .main-wrapper">
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" @click="btnGhiLai_Click">
              <span class="icon one-file-arrow-right1"></span> Cập nhật
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div v-for='item in this.dulieu' :key='item.loaidvi_id' class="info-row">
            <div class="key w110">{{item.tenloai_dvi}}</div>
            <div class="value">
              <div class="select-custom">
                <ejs-combobox ref='cboTinh' :popupHeight='300' :filterType='"Contains"' :allowFiltering='true' :fields='fields_donvi' v-model:value='output[item.loaidvi_id]' :dataSource='item.ds_donvi'></ejs-combobox>
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
import axios from 'axios';
import api from '../LiquidateAPI'
import {DataManager, JsonAdaptor  } from '@syncfusion/ej2-data';
import { ComboBoxPlugin  } from "@syncfusion/ej2-vue-dropdowns";
//Vue.use(ComboBoxPlugin);
export default {
  components: {
    moment,
    DatePicker,
    api,
  },
  name: 'popupCapNhatDV',
  data() {
    let self = this;
    // console.log(self.$root.token);
    return {
      fields_donvi:{text: 'ten_donvi', value: 'donvi_id'},
      hieu_ung_popup: { effect: 'Zoom' },
      position: { X: 'center', Y: 'center' },
      dulieu:[],
      hdtb_id: 0,
      output:{}
    }
  },
  methods: {

    async btnGhiLai_Click()
    {

      this.loading(true);
      try {

        let self = this;
        let dulieu_ghi = [];
        for (let i in self.output) {
          dulieu_ghi = [
            ...dulieu_ghi,
            { loaidvi_id: i * 1, donvi_id: self.output[i] }
          ]
        }
        ;
        let kq = await api.capnhat_hdtb_dvi_cntt({
          vhdtb_id: self.hdtb_id,
          vdata: JSON.stringify(dulieu_ghi)
        });
        if(kq.data.error_code == 'BSS-00000000')
        {
          let kq_api = JSON.parse(kq.data.data);
          if(kq_api.error_code == 200)
          {
            this.$toast.success('Cap nhat thanh cong');
          }
          else
          {
            this.$toast.error('Cap nhat that bai! ' + kq_api.error_message);
          }
        }
      }
      catch (ex)
      {
        this.$toast.error(ex);
      }
      finally {
        this.loading(false);
      }
    },
    async formLoad(dulieu_in)
    {
      let self = this;
      let kq = await api.lay_luong_dv_hdtb(dulieu_in);
      this.dulieu = JSON.parse(kq.data.data);
      this.dulieu.forEach((item)=>{
        self.output[item.loaidvi_id] = item.donvi_md;
      });
      console.log(this.dulieu);

    },
    async openDialog(dulieu_in)
    {
      this.resetForm();
      this.hdtb_id = dulieu_in.hdtb_id;
      if(!this.hdtb_id)
      {
          this.$toast.error("Ban can ghi lai hop dong truoc");
          return;
      }
      await this.formLoad(dulieu_in);
      this.$refs.frmCapNhatDV.show();

    },
    resetForm()
    {
      this.dulieu =[];
      this.hdtb_id= 0;
      this.output = {}
    }
  }
}
</script>

<style>

</style>
