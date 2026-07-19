<template>
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="hieu_ung_popup" ref="frmGiaoPhieu_IT" :position="position" :header="'Giao phiếu IT'" showCloseIcon="true" width="50%" target="#app .main-wrapper" :close="poup_close">
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" @click="btnGiaoPhieu_Click">
              <span class="icon one-file-arrow-right1"></span> Giao phiếu
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <div class="key w110">Nội dung giao</div>
            <div class="value">
              <input type="text" class="form-control" v-model="noidung_giao">
            </div>
          </div>
          <div v-for='item in this.dulieu' :key='item.loaidvi_id' class="info-row">
            <div class="key w110">{{item.tenloai_dvi}}</div>
            <div class="value">
              <div class="select-custom">
                <ejs-combobox ref='cboTinh' :popupHeight='300' :filterType='"Contains"' :allowFiltering='true' :fields='fields_donvi' v-model='donvinhan_id' :dataSource='item.ds_donvi'></ejs-combobox>
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
import {DataManager, JsonAdaptor  } from '@syncfusion/ej2-data';
import { ComboBoxPlugin  } from "@syncfusion/ej2-vue-dropdowns";
//Vue.use(ComboBoxPlugin);
export default {
  components: {
    moment,
    DatePicker
  },
  props: ['reload_grid'],
  name: 'PopupGiaoPhieuCNTT',
  data () {
    return {
      fields_donvi: {text: 'ten_donvi', value: 'donvi_id'},
      hieu_ung_popup: { effect: 'Zoom' },
      position: { X: 'center', Y: 'center' },
      dulieu: [],
      dulieu_input: {},
      hdtb_id: 0,
      output: {},
      donvinhan_id: 0,
      noidung_giao: ""
    }
  },
  methods: {
    poup_close()
    {
      this.$emit("reload_grid")
    },
    emit_function()
    {
      this.$refs.frmGiaoPhieu_IT.close()
      this.$refs.frmGiaoPhieu_IT.hide()
    },
    lay_luong_dv_hdtb (data) {
      return axios.post('web-thicong/dvcntt/lay_luong_dv_hdtb_giaophieu_cntt', data)
    },
    giao_phieu_cntt (data) {
      try {
        return axios.post('/web-thicong/dvcntt/giaophieu_tudong_quytrinh_it', data)
      }
      catch (e)
      {
        throw e;
      }

    },
    async btnGiaoPhieu_Click()
    {
      try {
        let self = this;
        console.log('====================>self')
        console.log(self)
        let obj = {
          phanvung_id: self.dulieu_input.tinh_id,
          hdtb_id: self.dulieu_input.hdtb_id,
          user_cn: self.$root.token.getUserName(),
          donvinhan_id: self.donvinhan_id,
          noidung_giao: self.noidung_giao
        }
        let kq = await this.giao_phieu_cntt(obj);
        console.log(kq);
        if (kq.data.data == "ok")
        {
          self.$toast.success("Giao phiếu thành công");
          this.$emit('emit_function')
        }
        else
          self.$toast.error("Giao phiếu thất bại " + kq.data.data);
      }
      catch (e)
      {
        let loi = e.message || e.error;
        this.$toast.error(loi);
      }
    },
    async formLoad(dulieu_in)
    {
      let self = this
      this.dulieu_input = {
        ...dulieu_in,
        ...self.dulieu_input
      };
      console.log(this.dulieu);
      let obj = {
        phanvung_id: dulieu_in.tinh_id,
        hdtb_id: dulieu_in.hdtb_id
      }
      let kq = await self.lay_luong_dv_hdtb(obj)
      this.dulieu = JSON.parse(kq.data.data)
      this.dulieu.forEach((item) => {
        self.output[item.loaidvi_id] = item.donvi_md
      })
      //Hoan fix lại, khi nhấn giao phiếu thì hiển thị mặc định đối tượng nhận là đối tượng ID trong HDTB_DV 
      this.donvinhan_id = this.dulieu[0].donvi_md

    },
    async openDialog (dulieu_in)
    {
      this.resetForm()
      this.hdtb_id = dulieu_in.hdtb_id
      if (!this.hdtb_id)
      {
        this.$toast.error("Ban can ghi lai hop dong truoc")
        return
      }
      await this.formLoad(dulieu_in)
      this.$refs.frmGiaoPhieu_IT.show()
    },
    resetForm ()
    {
      this.dulieu = []
      this.dulieu_input = {}
      this.hdtb_id = 0
      this.output = {}
      this.donvinhan_id = 0;
      this.noidung_giao = "";
    }
  }
}
</script>

<style>

</style>
