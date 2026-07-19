<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Chọn cộng tác viên</div>
        <div @click="$emit('form-close', true)" class="close -ap icon-close" data-dismiss="modal">
        </div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" v-on:click.prevent="PopUpCongTacVien_ChapNhan">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận</a>
        </li>
        <li>
          <a href="#" v-on:click.prevent="PopUpCongTacVien_Thoat">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
          </a>
        </li>
        <li>
          <a href="#" v-on:click.prevent="getDsNhanVien_V2">
            <span class="icon nc-icon-glyph files_single-content-02"></span>Tra Cứu
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="row">
        <div class="col-sm-12 col-12">
          <div class="box-form">
            <div class="legend-title">
              <span>Thông tin tìm kiếm</span>
              <span class="pull-right" style="color: red">{{ ten_ht }}</span>
            </div>
            <div class="info-row">
              <div class="key w200">Nhóm</div>
              <div class="value">
                <div class="select-custom">
                  <Select2 v-model="nhomctv_id" :options="DmNhom_CTV" v-on:change="DmNhom_CTV_onChange"/>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w200">
                <div class="key">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="chkDonViQL_CTV" />
                    <span class="name">Đơn vị QL</span>
                  </div>
                </div>
              </div>
              <div class="value">
                <div class="select-custom">
                  <Select2 :disabled="!CTVDonViQLStatus" v-model="donviql_ctv_id" :options="DmDonViQL_CTV" v-on:change="DmDonViQL_CTV_onChange"/>
                </div>
              </div>
            </div>

            <div class="info-row">
              <div class="key w200">
                <div class="key">
                  <div class="check-action">
                    <input  type="checkbox" class="check" v-model="txtMaNV.enabled"/>
                    <span class="name">Mã nhân viên</span>
                  </div>
                </div>
              </div>
              <div class="value">
                <input v-if="txtMaNV.visible" :disabled="!txtMaNV.enabled" type="text" class="form-control" v-model="sp_lay_ds_nhanvien_gt_v2_req.p_ma_nv" @keyup.enter="getDsNhanVien_V2">
              </div>
            </div>

            <div class="info-row">
              <div class="key w200">
                <div class="key">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="txtMaThe.enabled"/>
                    <span class="name">Mã thẻ</span>
                  </div>
                </div>
              </div>
              <div class="value">
                <input v-if="txtMaThe.visible" :disabled="!txtMaThe.enabled"  type="text" class="form-control"  v-model="sp_lay_ds_nhanvien_gt_v2_req.p_ma_the" @keyup.enter="getDsNhanVien_V2">
              </div>
            </div>

            <div class="info-row">
              <div class="key w200">
                <div class="key">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="txtMaDV.enabled"/>
                    <span class="name">Mã đơn vị</span>
                  </div>
                </div>
              </div>
              <div class="value">
                <input v-if="txtMaDV.visible" :disabled="!txtMaDV.enabled"  type="text" class="form-control" v-model="sp_lay_ds_nhanvien_gt_v2_req.p_ma_dv" @keyup.enter="getDsNhanVien_V2">
              </div>
            </div>

          </div>
          <div class="box-form">
            <div class="legend-title">Danh sách nhân viên</div>
            <DataGrid v-bind:columns="PopUpCongTacVien.headers" v-bind:dataSource="PopUpCongTacVien.data"
                        :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true"
                        dataKeyField="" :showFilter="true"  @selectedRowChanged="onNhanVienSelected">
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import gridView from "@/modules/prepaid/ChoosePreapaidNumber/components/gridview.vue";
import treegridView from "@/modules/contract/setup/ManageSelfcareContract/components/treegridview.vue";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import api from "../Api";
export default {
  name: "CongTacVien",
  props: [],
  components: {
    breadcrumb,
    gridView,
    treegridView,
    moment,
    DatePicker,
  },
  data() {
    return {
      chkDonViQL_CTV: false,
      donviql_ctv_id: -1,
      CTVDonViQLStatus: false,
      DmDonViQL_CTV: [],
      nhomctv_id: -1,
      DmNhom_CTV: [],

      nhanvien_id: 0,
      ma_nv: '',
      ma_dv: '',
      diachi_nv: '',
      ma_the: '',
      so_dt: '',
      ten_dv: '',
      ten_ht: '',
      ten_nv: '',

      txtMaNV : {
        visible: true,
        enabled: false,
        value: null
      },

      txtMaThe : {
        visible: true,
        enabled: false,
        value: null
      },

      txtMaDV : {
        visible: true,
        enabled: false,
        value: null
      },

      sp_lay_ds_nhanvien_gt_v2_req :{
        p_donvi_id : -1,
        p_loainv_id : -1,
        p_ma_nv : "",
        p_ma_the : "",
        p_ma_dv : ""
      },

      PopUpCongTacVien: {
        headers: [
          { fieldName: "stt", headerText: "STT", allowFiltering: true, allowSorting: false, width: 50, },
          { fieldName: "ma_the", headerText: "Mã thẻ", allowFiltering: true, allowSorting: false, width: 100, },
          { fieldName: "ma_nv", headerText: "Mã nhân viên", allowFiltering: true, allowSorting: false, width: 100, },
          { fieldName: "ma_dv", headerText: "Mã đơn vị", allowFiltering: true, allowSorting: false, width: 100, },
          { fieldName: "ten_nv", headerText: "Tên nhân viên", allowFiltering: true, allowSorting: false, width: 100, },
          { fieldName: "ten_dv", headerText: "Tên đơn vị", allowFiltering: true, allowSorting: false, width: 100, },
        ],
        data: [],
        selected: 0,
        checked: [],
      },
    };
  },
  watch: {
    chkDonViQL_CTV: function(val){
      if(val)
      {
        this.DmDonViQL_CTV = this.DmDonViQL_CTV.filter(e=>e.id !== 0)
        this.CTVDonViQLStatus = val
      }else{
        this.CTVDonViQLStatus = val
        this.donviql_ctv_id = -1
      }
    },

    'txtMaNV.enabled'(newValue) {
      if (!newValue) {
        this.sp_lay_ds_nhanvien_gt_v2_req.p_ma_nv = '';
      }
    },

    'txtMaThe.enabled'(newValue) {
      if (!newValue) {
        this.sp_lay_ds_nhanvien_gt_v2_req.p_ma_the = '';
      }
    },

    'txtMaDV.enabled'(newValue) {
      if (!newValue) {
        this.sp_lay_ds_nhanvien_gt_v2_req.p_ma_dv = '';
      }
    }


  },
  created() {
    this.getDsNhom(),
    this.getDsDonViQL(),
    // this.DmDonViQL_CTV_onChange()
    //this.getDsNhanVien()
    this.getDsNhanVien_V2()
  },
  computed: {},
  mounted() {},
  methods: {
    async PopUpCongTacVien_Thoat() {
       this.$emit('PopUpCongTacVien_Thoat')
    },

    async PopUpCongTacVien_ChapNhan(){
        this.$emit('acceptChangeCTV',
        {
            nhanvien_id: this.PopUpCongTacVien.selected.nhanvien_id,
            ten_ht: this.PopUpCongTacVien.selected.ten_ht,
            rowData: this.PopUpCongTacVien.selected
        });
    },

    getDsNhom: async function(){
      var input = {}
      let data = this.GetData(await api.getDsNhom(this.axios, input))
      if(data && data.length > 0){
        this.DmNhom_CTV = data.filter(x=>x.NHOMLNV_ID===6).map((x)=>({id:x.LOAINV_ID,text:x.TEN}))   //NHOMLNV_ID: 6 là CTV
        this.DmNhom_CTV.unshift({id: 0,text: '--Tất cả--' })
      }
    },

    getDsDonViQL: async function(){
      var input = {}
      let data = this.GetData(await api.getDsDonViQL(this.axios, input))
      if(data && data.length > 0){
        this.DmDonViQL_CTV = data.map((x)=>({id:x.donvi_id,text:x.ten_dv}))
        this.DmDonViQL_CTV.unshift({id: 0,text: '' })
      }
    },

    getDsNhanVien: async function(){
      this.$root.loading(true)
      var input = {"donvi_id":this.donviql_ctv_id, "loainv_id":this.nhomctv_id}
      let data = this.GetData(await api.getDsNhanVien(this.axios, input))
      if(data && data.length > 0){
        this.PopUpCongTacVien.data = data
      }else{
        this.$root.toastError("Không có dữ liệu thông tin nhân viên!");
        this.PopUpCongTacVien.data = data
      }
      this.$root.loading(false)
    },

    getDsNhanVien_V2: async function(){
      this.$root.loading(true)
      var input = {
        p_donvi_id : this.donviql_ctv_id,
        p_loainv_id : this.nhomctv_id,
        p_ma_nv : this.sp_lay_ds_nhanvien_gt_v2_req.p_ma_nv,
        p_ma_the : this.sp_lay_ds_nhanvien_gt_v2_req.p_ma_the,
        p_ma_dv : this.sp_lay_ds_nhanvien_gt_v2_req.p_ma_dv
      }
      let data = this.GetData(await api.sp_lay_ds_nhanvien_gt_v2(this.axios, input))
      if(data && data.length > 0){
        this.PopUpCongTacVien.data = data
      }else{
        this.$root.toastError("Không có dữ liệu thông tin nhân viên!");
        this.PopUpCongTacVien.data = data
      }
      this.$root.loading(false)
    },

    DmNhom_CTV_onChange(loainv_id){
      this.loainv_id = loainv_id
      this.getDsNhanVien_V2()
    },

    DmDonViQL_CTV_onChange(donviql_ctv_id){
      this.donviql_ctv_id = donviql_ctv_id
      this.getDsNhanVien_V2()
    },

    onNhanVienSelected(e) {
        if (e !== null) {
            this.PopUpCongTacVien.selected = e;
            this.nhanvien_id = e.nhanvien_id
            this.ma_nv = e.ma_nv
            this.ten_ht = e.ten_ht
        }
    },

  GetData: function (response) {
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
      }
      return [];
    },
  },
};
</script>
