<template>
    <b-modal
          ref="popupNgayTongDongLuc"
          id="popupNgayTongDongLuc" style="width:400px"
          hide-footer body-class="modal-body p-0" >
    <div class="modal-content popup-box">
      <div class="popup-body">
        <div class="box-form">
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key" style="width:150px">Ngày tổng động lực</div>
                    <div class="value">
                    <vue-date v-model="ngayTongDongLuc" :format="dateFormat" type="Date"
                    labelWidth="90"></vue-date>
                    </div>
              </div>
            </div>
          </div>         
          <div class="group-buttons -bottom center">          
            <button @click="btnXacNhan_Click()" class="btn btn-primary" style="width: 120px">
              Chọn
            </button>
            <button @click="hideModal()" class="btn btn-secondary" style="width: 120px">
              Hủy bỏ
            </button>
          </div>
  
        </div>
      </div>
    </div>
    </b-modal>
  </template>
  
  <style>
  input.disabled,
  select.disabled,
  .disabled input,
  .disabled select,
  .disabled /deep/ .select2-selection {
    background-color: #eee;
    color: lightgray;
  }
  
  .e-grid .e-gridheader .e-sortfilter .e-headercelldiv,
  .e-grid .e-gridheader .e-stackedheadercelldiv,
  .e-grid .e-gridheader .e-headercell .e-headercelldiv.e-headerchkcelldiv {
    padding: 0 5px 0 5px !important;
  }
  
  @media (min-width: 992px) {
    .modal .modal-huge {
      max-width: 95% !important;
      width: 100% !important;
    }
  }
  
  @media (min-width: 576px) {
    .modal .modal-huge {
      max-width: 95% !important;
      width: 100% !important;
    }
  }
  </style>
  
  <script>
  import breadcrumb from "@/components/breadcrumb";
  import VueFlatPickr from "vue-flatpickr-component";
  import moment from "moment";
  import DatePicker from "vue2-datepicker";
  import "vue2-datepicker/locale/vi.js";
  import "vue2-datepicker/index.css";
  import DataGrid from "@/components/Controls/DataGrid";
  import {
    required,
    minLength,
    integer,
    maxValue,
  } from "vuelidate/lib/validators";;
  
  export default {
    name: "popupNgayTongDongLuc",
    components: {
      required,
      minLength,
      integer,
      maxValue,
      DatePicker,
      VueFlatPickr,
      breadcrumb,
      DataGrid
    },
    props: ["dulieu"],
    data() {
      return {
        ngayTongDongLuc:'' ,
        cd_id: '',  
        soSrv : '',
        dateFormat: 'DD/MM/YYYY',  
        ds_chien_dich : []
      };
    },
    watch: {
      
    },
    computed: {},
    created() {
    },
    mounted() {
    },
    methods: { 
      showModal(cd_id,soSrv, ds_chien_dich) {
        this.cd_id = cd_id;
        this.soSrv = soSrv;
        this.ds_chien_dich = ds_chien_dich;
          this.$refs["popupNgayTongDongLuc"].show()
      },
      hideModal() {
          this.$refs["popupNgayTongDongLuc"].hide()
      },  
      btnXacNhan_Click: async function () {
        if (this.ngayTongDongLuc == null && this.ngayTongDongLuc == "") {
          this.$toast.warning("Bạn chưa nhập số phiếu tối đa cho 1 nhân viên");
        }        
        try{
          let arrdstb = []
          this.ds_chien_dich.forEach(item => {
            arrdstb.push({
                "thuebao_id" : item.thuebao_id,
              })
          })
          arrdstb = JSON.stringify(arrdstb);
          let response = await this.axios.post("web-quantri/chien-dich-b2a/fn_capnhat_ngay_tl",{
            "vcd_id" : this.cd_id,
            "vsrv" : this.soSrv,
            "vloai" : 1,
            "vngay_tl" : this.ngayTongDongLuc,
            "vdsthuebao_id" : arrdstb,            
          })
          if (
            response &&
            response.data &&
            response.data.error_code &&
            response.data.error_code == "BSS-00000000" &&
            response.data.data && response.data.data.length > 0
          ) {
            this.$toast.success("Cập nhật ngày tổng lực thành công");
          } else if (
            response &&
            response.data &&
            response.data.error_code &&
            response.data.error_code == "BSS-00000204"
          ) {
            this.$toast.error("Lỗi cập nhật ngày tổng lực: " + response.data.message_detail);
          } else {
            this.$toast.error("Lỗi cập nhật ngày tổng lực: " + response.data.message_detail);
          }
        }catch(err){
          this.$toast.error("Lỗi cập nhật ngày tổng lực: " + err.data.message_detail);
        }
        this.hideModal();
      },      
      cd_update(cd){
        this.cd_id = cd;
      }  
    },
  };
  </script>