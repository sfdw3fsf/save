<script>
import HDDaKyGrid from '../components/HDDaKyGrid.vue';
import config from '../config';

export default {
  components: { HDDaKyGrid},
  name: 'DsHDDaKy',
  props:{
    isShow: false
  },
  data: () => ({
    target: '.main-wrapper',
    ...config.popup,
    filter: {
      loaiHD: 0
    },
    UiState: {
      initDataDone: false,
      isShow: false
    },
    options: {
      loaiHDOptions:[
        {
          tenloaihd: "Tiếp nhận yêu cầu ký hợp đồng",
          loaihd_id: 0
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
      setTimeout(() => {
        this.UiState.initDataDone = true;
      }, 2000);
    },
    getTT() {
      console.log("get TT");
    },
    update() {
      console.log('Sync')
    },
  },
  computed: {}
}
</script>
<template>
  <ejs-dialog 
    ref="popup"
    :enableResize='false' 
    :visible="false" 
    :header="DsHDDaKy.header"
    showCloseIcon=true
    :target="target" width='80%'
    :close="hideDialog"
    :animationSettings="animationSetting"
    height="800">
    <div class="SyncVNPContacts DsHDDaKy">
      <vue-nav>
        <ul class="list">
          <li :class="!UiState.initDataDone ? 'non-ative' : ''">
            <a @click="getTT"><span class="icon one-file-attach"></span>Lấy TT (F5) </a>
          </li>
          <li :class="!UiState.initDataDone ? 'non-ative' : ''">
            <a @click="update"><span class="icon one-change"></span>Cập nhật </a>
          </li>
          <li v-if="!UiState.initDataDone">
            <b-spinner small variant="primary"></b-spinner>
          </li>
        </ul>
      </vue-nav>
      <div class="page-content">
        <vue-card class="title">
          <b-row>
            <div class="info-row"><b>DANH SÁCH HỢP ĐỒNG ĐÃ KÝ</b></div>
          </b-row>
        </vue-card>
        <vue-card>
          <b-row>
            <b-col md="4">
              <vue-input
                labelWidth="100"
                highlight
                label="Mã giao dịch"
              ></vue-input>
            </b-col>
            <b-col md="4">
              <vue-select 
                labelWidth="80" 
                label="Loại HĐ/PL" 
                v-model="filter.loaiHD" 
                labelField="tenloaihd"
                valueField="loaihd_id" 
                :options="options.loaiHDOptions" 
              />
            </b-col>
            <b-col md="4">
              <vue-input
                labelWidth="100"
                label="Tên thuê bao"
              ></vue-input>
            </b-col>
          </b-row>
          <b-row>
            <b-col md="8">
              <vue-input
                labelWidth="100"
                label="Nhân viên YC"
              ></vue-input>
            </b-col>
            <b-col md="4">
              <vue-input
                labelWidth="100"
                label="Địa chỉ LĐ"
              ></vue-input>
            </b-col>
          </b-row>
          <b-row>
            <vue-input
              labelWidth="100"
              label="Ghi chú TB"
            ></vue-input>
          </b-row>
        </vue-card>
        <vue-card label="Danh sách thuê bao">
          <HDDaKyGrid/>
        </vue-card>
      </div>
    </div>
  </ejs-dialog>
</template>
<style>
  .SyncVNPContacts.DsHDDaKy .title.box-form {
    padding: 15px 12px 10px 12px
  }
  .SyncVNPContacts.DsHDDaKy .non-ative a {
    color: #d3d3d3 !important;
    pointer-events: none;
    cursor: default;
  }
  .SyncVNPContacts.DsHDDaKy .list-actions-top .list{ 
    overflow: hidden;
  }
</style>