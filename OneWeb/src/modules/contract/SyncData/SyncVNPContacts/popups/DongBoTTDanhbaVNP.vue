<script>
import TTDanhbaGrid from '../components/TTDanhbaGrid.vue'
import API from '../api';
// Import global components
import moment from "moment"
import { required, requiredIf} from 'vuelidate/lib/validators'
import config from '../config';

export default {
  components: { TTDanhbaGrid },
  name: 'DongBoTTDanhbaVNP',
  props:{
    isShow: false
  },
  validations: {
    filter: {
      note: {
        required: requiredIf(function() {
          return this.UiState.nhomhuyCB
        })
      }
    }
  },
  data: () => ({
    target: '.main-wrapper',
    ...config.popup,
    filter: {
      tuNgay: moment(new Date()).format('DD/MM/YYYY'),
      trangthai: 0,
      nhomhuy: 0,
      lydohuy: 0,
      note: ""
    },
    UiState: {
      nhomhuyCB: false,
      initDataDone: false,
      isShow: false
    },
    options: {
      trangthaiOptions:[
        {
          tentt: "Tất cả",
          tt_id: 0
        }
      ],
      cancelGroupOptions:[
        {
          label:"I-Nhóm lý do phía khách hàng",
          value: 0
        }
      ],
      reasonsCancelOptions: [
        {
          label: "1. Đi vắng/đi công tác",
          value: 0
        }
      ]
    }
  }),
  watch: {
    'UiState.isShow'(newValue, oldValue) {
      if(newValue == true) {
        this.init()
      }
    }
  },
  methods: {
    showDialog() {
      this.$refs.popup.show();
      this.UiState.isShow = true;
    },
    hideDialog() {
      this.UiState.isShow = false;
    },
    async init() {
      this.UiState.initDataDone = false;

      this.filter.note = '';
      this.filter.tuNgay= moment(new Date()).format('DD/MM/YYYY');
      this.UiState.nhomhuyCB = false;
      this.$v.$reset()
      
      await this.loadNhomHuy();
      await this.loadLydoHuy();
      setTimeout(() => {
        this.UiState.initDataDone = true;
      }, 2000);

    },
    async loadNhomHuy() {
      console.log('load nhóm hủy')
    },
    async loadLydoHuy() {
      console.log('load lý do hủy')
    },
    async getDL() {
      console.log("get DL");
      this.$v.$reset();
      console.log(this.$v.filter.note.$error)
    },
    async sync() {
      console.log(this.$v.filter.note.$error)
      console.log('Sync');
      this.$v.$touch();
      if (this.$v.$invalid) {
        this.$toast.error(`Đã có lỗi nhập thông tin. Kiểm tra thông báo lỗi tại các trường nhập`)
        return;
      }
    },
    async changeNhomHuy() {
      console.log('Change nhóm hủy')
      this.loading(true);
      await this.loadLydoHuy();
      this.loading(false);
    }
  },
  computed: {}
}
</script>
<template>
  <ejs-dialog 
    ref="popup"
    :enableResize='false' 
    :visible="false" 
    :header="DongBoTTDanhbaVNP.header"
    showCloseIcon=true
    :target="target" 
    width='80%'
    :close="hideDialog"
    :animationSettings="animationSetting"
    height="800">
     <div class="SyncVNPContacts DongBoTTDanhbaVNP">
      <vue-nav>
        <ul class="list">
          <li :class="!UiState.initDataDone ? 'non-ative' : ''">
            <a @click="getDL"><span class="icon one-file-attach"></span>Lấy DL </a>
          </li>
          <li :class="!UiState.initDataDone ? 'non-ative' : ''">
            <a @click="sync"><span class="icon one-change"></span>Đồng bộ </a>
          </li>
          <li v-if="!UiState.initDataDone">
            <b-spinner small variant="primary"></b-spinner>
          </li>
        </ul>
      </vue-nav>
      <div class="page-content">
        <vue-card class="filter">
          <b-row>
            <b-col md="5">
              <div class="info-row"><b>ĐỒNG BỘ TRẠNG THÁI DANH BẠ VINAPHONE VỀ TỈNH</b></div>
            </b-col>
            <b-col md="4">
              <div class="info-row">
                <vue-select 
                  labelWidth="80" 
                  label="Trạng thái" 
                  v-model="filter.trangthai" 
                  labelField="tentt"
                  valueField="tt_id" 
                  :options="options.trangthaiOptions" />
              </div>
            </b-col >
            <b-col md="3">
              <div class="info-row">
                <div class="key w70"><b>Từ ngày</b></div>
                <vue-date format="DD/MM/YYYY" type="date" v-model="filter.tuNgay"></vue-date>
              </div>
            </b-col>
          </b-row>
        </vue-card>
        <vue-card label="Thông tin cập nhật">
          <b-row>
            <b-col md=6>
              <div class="info-row">
                <div class="value padt4 w20">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="UiState.nhomhuyCB" >
                    <span class="name"></span>
                  </div>
                </div>
                <div class="key w70">Nhóm hủy</div>
                <vue-select
                  labelWidth="0"
                  v-model="filter.nhomhuy"
                  labelField="label"
                  valueField="value"
                  :options="options.cancelGroupOptions"
                  :disable="!UiState.nhomhuyCB"
                  @input="changeNhomHuy"
                />
              </div>
            </b-col>
            <b-col md="6">
              <div class="info-row">
                <vue-select 
                  labelWidth="100" 
                  label="Lý do hủy" 
                  v-model="filter.lydohuy" 
                  labelField="label"
                  valueField="value" 
                  :disable="!UiState.nhomhuyCB"
                  :options="options.reasonsCancelOptions"/>
                </div>
            </b-col>
          </b-row>
          <b-row>
            <div class="info-row note">
              <div class="key w100">Ghi chú</div>
              <ejs-textbox v-model="filter.note"></ejs-textbox>
              <div class="text-sm mt-2 text-red" v-if="$v.filter.note.$error">
                <div class="error" v-if="!$v.filter.note.required">Ghi chú là bắt buộc khi đã tick chọn nhóm hủy</div>
              </div>
            </div>
          </b-row>
        </vue-card>
        <vue-card>
          <TTDanhbaGrid/>
        </vue-card>
      </div>
    </div>
  </ejs-dialog>
</template>

<style>
  .SyncVNPContacts.DongBoTTDanhbaVNP .filter.box-form {
    padding: 16px 12px 0px 12px;
  }
  .SyncVNPContacts.DongBoTTDanhbaVNP .non-ative a {
    color: #d3d3d3 !important;
    pointer-events: none;
    cursor: default;
  }
  .SyncVNPContacts.DongBoTTDanhbaVNP .list-actions-top .list{ 
    overflow: hidden;
  }

  .SyncVNPContacts.DongBoTTDanhbaVNP .error,
  .SyncVNPContacts.DongBoTTDanhbaVNP .note .e-input-group  {
    margin-left: 3px;
  }

</style>