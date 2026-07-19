<template>
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>{{ dulieu.title }}</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" @click.prevent="btnGhiLai">
              <span class="icon one-save"></span> Chấp nhận
            </a>
          </li>
          <!-- <li>
            <a href="javascript:;" @click.prevent="btnHuy">
              <span class="icon one-users"></span> Hủy
            </a>
          </li> -->
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <div class="key">Nội dung</div>
            <div class="value">
              <input
                type="text"
                v-model="txtNoiDung.value"
                ref="txtNoiDung"
                class="form-control"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
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
  </style>
  <script>
  import breadcrumb from "@/components/breadcrumb";
  import VueFlatPickr from "vue-flatpickr-component";
  import moment from "moment";
  import DatePicker from "vue2-datepicker";
  import "vue2-datepicker/locale/vi.js";
  import "vue2-datepicker/index.css";
  import {
    required,
    minLength,
    integer,
    maxValue,
  } from "vuelidate/lib/validators";
  import {
    LoaiHopDong,
    TrangThaiHD,
    DichVuVienThong,
    listDichVuCNTT,
    TRANGBI,
    KieuLapDat,
    KHOANMUC_TT,
    LoaiHinhTB,
    Loai_KM,
    NHOM_LNV,
    LOAI_DV,
  } from "@/modules/admin/category/MultiServicePackage/enum.js";
  import { Checkbox } from "element-ui";
  export default {
    name: "popupNhapText",
    components: {
      required,
      minLength,
      integer,
      maxValue,
      DatePicker,
      VueFlatPickr,
      breadcrumb,
    },
    props: ["dulieu"],
    data() {
      return {
        txtNoiDung: { value: "", isReadonly:false},
      };
    },
    watch: {    
      
    },
    computed: {},
    async mounted() {
      await this.form_Load()
    },
    methods: {
      form_Load: async function () {
        try {
          
        } catch (ex) {
          console.log(ex);
        }
      },
      btnGhiLai: function () {
        this.closeForm();
      },
      closeForm() {
        this.$emit("form-close", this.txtNoiDung.value);
      },
      formatPrice(value) {
        let val = (value / 1).toFixed(0).replace(".", ",");
        return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
      },
      validateNumber: (event) => {
        let keyCode = event.keyCode;
        if (keyCode < 48 || keyCode > 57) {
          event.preventDefault();
        }
      },
      ShowAlert(mesage, val) {
        if (val == 0) {
          this.$toast.success(mesage);
        } else if (val == 1) {
          this.$toast.warning(mesage);
        } else if (val == 2) {
          this.$toast.error(mesage);
        }
      },
      ShowSuccess: function (msg) {
        this.$toast.success(msg);
      },
      ShowError: function (msg) {
        this.$toast.error(msg);
      },
    },
  };
  </script>