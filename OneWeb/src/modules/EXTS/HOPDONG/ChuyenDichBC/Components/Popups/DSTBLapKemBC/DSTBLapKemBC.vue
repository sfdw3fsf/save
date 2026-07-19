<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="tsbtnCapNhan_Click">
            <span class="icon one-save"></span>Chấp nhận
          </a>
        </li>
      </ul>

    </div>
    <div class="page-content">
      <div class="table-content">
        <DataGrid
          v-bind:dataSource="dgvDSTBTruyNhap.list"
          v-bind:columns="dgvDSTBTruyNhap.cols"
          ref="dgvDSTBTruyNhap"
          :panelDataHeight= "'150'"
          :showFilter="true"
          :enablePagingServer="false"
          :enabledSelectFirstRow="true"
          :allowPaging="true"



        />

      </div>

    </div>
  </div>
</template>

<script>
import api from './api'
import '@/assets/vendor/jquery/split'
import Vue from 'vue'
import moment from 'moment'
import {DialogPlugin} from '@syncfusion/ej2-vue-popups'
import {LoaiHopDong, DichVuVienThong, TrangThaiHD, LOAI_DV, TrangThaiTB, LoaiHinhTB} from '@/const/enums'
Vue.use(DialogPlugin)
export default {
name: "DSTBLapKemBC",
  props:{
    hdkh_id:{
      type:Number,
      default:0,
    },
    vkieuld_id:{
      type:Number,
      default:0,
    },

  },
  data: function () {
    return {
      loaitb_id:0,
      KieuLapDat: {
        THAYDOI_CONGNGHE_TOCDO_AON_GPON: 885,
        THAYDOI_MUCCUOC_ADSL_TK: 13081,
        THAYDOI_KIEUTRA_MYTV: 270,
        THAYDOI_TOCDO_ADSL_TK: 13080,
        THAYDOI_TOCDO_MUCCUOC_ADSL_TK: 13082,
        THAYDOI_CONGNGHE_TOCDO_GPON_AON: 886,
        THAYDOI_TOCDOTHUC_ADSL: 655,
        DOIGOI_FSECURE: 668,
      },
      dataResonse :{
        "ma_tn":"",
        "ma_doicap":"",
        "loaitb_id":0,
        "thuebao_id":0,
      },
      dgvDSTBTruyNhap:{
        list:[],
        cols:[
          {
            fieldName: "ma_tb",
            headerText: "Số máy/Acc",
            allowFilter: true,
            allowSorting: false,
            freeze: "left"

          },
          {
            fieldName: "loaihinh",
            headerText: "Loại hình TB",
            allowFilter: true,
            allowSorting: false,
            freeze: "left"

          },

          {
            fieldName: "madoicap",
            headerText: "Mã đôi cáp",
            allowFilter: true,
            allowSorting: false,
            freeze: "left"

          },
          // {
          //   fieldName: "ten_kieuld",
          //   headerText: "Kiểu lắp đặt",
          //   allowFilter: true,
          //   allowSorting: false,
          //   freeze: "left"
          //
          // },
        ],
      },
    }
  },
  methods:{
    async frmDSTBLapKemBC_Load(){
      try{
        let response = await api.lay_ds_thuebao_tn_theo_hdkhid(this.axios,this.hdkh_id);
        if(response.data.error_code === 'BSS-00000000' && response.data.data){
          this.dgvDSTBTruyNhap.list = response.data.data;
        }
      }
      catch (e){
        console.log(e);
      }

    },
    async tsbtnCapNhan_Click(){

        this.dataResponse.ma_tn = item.ma_tb;
        this.dataResponse.ma_doicap = item.madoicap;
        this.dataResponse.loaitb_id = item.loaitb_id;
        this.dataResponse.thuebao_id = item.thuebao_id;


        this.$emit("accept", this.dataResponse);


    },

  },
}

</script>

<style scoped>

</style>
