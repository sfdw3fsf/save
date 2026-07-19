<template>
  <b-modal
        ref="popupCB"
        id="popupCB" style="width:500px" :title="'Cấu hình cảnh báo!'"
		hide-footer body-class="modal-body p-0"
    >
  <div class="modal-content popup-box">
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key" style="width:150px">Số phiếu tối đa/1NV</div>
								<div class="value">
									<input ref="txtSoPhieu" type="number" min="1" style="width:50px" v-model="txtSoPhieu" 
                      v-on:keypress="isNumber($event)" @change="e => txtSoPhieu = e.target.value">
                    Phiếu/Ngày
								</div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key" style="width:150px">Thông báo hiển thị</div>
								<div class="value">
									Vượt quá số phiếu/1 nhân viên/ngày
								</div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key" style="width:150px"></div>
								<div class="value">
                  Số phiếu tối đa:
									<input ref="txtSoPhieu" type="text" style="width:50px" readonly  v-bind:value="txtSoPhieu"
										@change="e => txtSoPhieu = e.target.value" >
                    Phiếu/Ngày
								</div>
            </div>
          </div>
        </div>
        <div class="group-buttons -bottom center">          
          <button @click="btnXacNhan_Click()" class="btn btn-primary" style="width: 120px">
            Cập nhật
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
import api from "../API.js"

export default {
  name: "popupCauHinhCanhBao",
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
      txtSoPhieu: '',
      cd_id: '',
      selected_list: [],      
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
    showModal(cd_id) {
      this.cd_id = cd_id;
        this.$refs["popupCB"].show()
    },
    hideModal() {
        this.$refs["popupCB"].hide()
    },  
    btnXacNhan_Click: async function () {
      if (this.txtSoPhieu == null || this.txtSoPhieu == "") {
        this.$toast.warning("Bạn chưa nhập số phiếu tối đa cho 1 nhân viên");
      }else {
        try{
          let response = await this.axios.post("web-quantri/chien-dich-b2a/fn_capnhat_sl",{
            vcd_id : this.cd_id,
            vsl : this.txtSoPhieu,
            vloai : 2
          })
          if (
            response &&
            response.data &&
            response.data.error_code &&
            response.data.error_code == "BSS-00000000" &&
            response.data.data && response.data.data.length > 0
          ) {
            this.$toast.success("Cập nhật cấu hình cảnh báo thành công");
          } else if (
            response &&
            response.data &&
            response.data.error_code &&
            response.data.error_code == "BSS-00000204"
          ) {
            this.$toast.error("Lỗi cập nhật cấu hình cảnh báo: " + response.data.message_detail);
          } else {
            this.$toast.error("Lỗi cập nhật cấu hình cảnh báo: " + response.data.message_detail);
          }
        }catch(err){
          this.$toast.error("Lỗi cập nhật cấu hình cảnh báo: " + err.data.message_detail);
        }
        this.hideModal();
      }
    },    
    cd_update(cd){
      this.cd_id = cd;
    },
    isNumber: function(evt) {
        evt = (evt) ? evt : window.event;
        var charCode = (evt.which) ? evt.which : evt.keyCode;        
        if ((charCode > 31 && (charCode < 49 || charCode > 57) && evt.target.value =='')) {
            evt.preventDefault();
        } else {
            return true;            
        }
    },      
  },
};
</script>