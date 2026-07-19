<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span> Chi tiết tiền theo khoản mục</div>
      <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popup-ChiTietTienKM')"></div>      
    </div>
    <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" v-on:click.prevent="ChiTietTienKM_CapNhap">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
            </a>
          </li>
          <li>
            <a href="#" @click="$bvModal.hide('popup-ChiTietTienKM')">
              <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
            </a>
          </li>
        </ul>
      </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w120">Tách nhập</div>
              <div class="value col-sm-5">
                <input type="text" :value="txtChuyenQuyen | numeral" @keyup="txtChuyenQuyen_change" class="form-control tright" />   
              </div>
            </div>            
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w100">Tiền</div>
              <div class="value">
                <input type="text" :disabled="true" @keyup="txtTienChuyenQuyen_change" :value="txtTienChuyenQuyen | numeral" class="form-control tright" />
              </div>
            </div>            
          </div>
          <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w100">VAT</div>
                <div class="value">
                <input type="text" :disabled="true" @keyup="txtVATChuyenQuyen_change" :value="txtVATChuyenQuyen | numeral" class="form-control tright" />
                </div>
            </div>            
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
export default {
  name: "ChiTietTienKM",
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
        txtChuyenQuyen: 0,        
        txtTienChuyenQuyen: 0,        
        txtVATChuyenQuyen: 0,       
        sumtien: 0,
        tongtien: 0,
        tongvat: 0,
    };
  },
  watch: {
      txtChuyenQuyen: function(val){
          this.txtTienChuyenQuyen = Math.round(val*0.91,2);
          this.txtVATChuyenQuyen = Math.round(val*0.09,2);
      },      
  },
  created() {},
  computed: {
    txtTienChuyenQuyen_dp() {
      return this.numberWithCommas(this.txtTienChuyenQuyen)
    },
    txtVATChuyenQuyen_dp() {
      return this.numberWithCommas(this.txtVATChuyenQuyen)
    },
    txtChuyenQuyen_dp() {
      return this.numberWithCommas(this.txtChuyenQuyen)
    }
  },
  mounted() {},
  methods: {
        txtChuyenQuyen_change(event) {
          console.log(event.target.value.replaceAll(",", " "))
          this.txtChuyenQuyen = event.target.value.replaceAll(",", "")
        },
        txtTienChuyenQuyen_change(event) {
          this.txtTienChuyenQuyen = event.target.value.replaceAll(",", "")
        },
        txtVATChuyenQuyen_change(event) {
          this.txtVATChuyenQuyen = event.target.value.replaceAll(",", "")
        },
       async ChiTietTienKM_CapNhap(){                
          this.$emit('acceptTienKM',
          { 
              tongtien: (this.txtTienChuyenQuyen),
              tongvat: (this.txtVATChuyenQuyen),
              tong: this.txtChuyenQuyen
          });           
      },
      numberWithCommas(n) {
        var parts = n.toString().split(".");
        return (
          parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
          (parts[1] ? "." + parts[1] : "")
        );
      }
  },
};
</script>