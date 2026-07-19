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
        </ul>
      </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w120">Thay thế/Thu hồi</div>
              <div class="value col-sm-5">
                <input type="text" v-model="txtChuyenQuyen" class="form-control tright" />   
              </div>
            </div>            
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w100">Tiền</div>
              <div class="value">
                <input type="text" v-model="txtTienChuyenQuyen" disabled class="form-control tright" />
              </div>
            </div>            
          </div>
          <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w100">VAT</div>
                <div class="value">
                <input type="text" v-model="txtVATChuyenQuyen" disabled class="form-control tright" />
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
  props:{
        tien:{
            type:Number,
            default:0
        },
        vat:{
            type:Number,
            default:0
        },
         tong:{
            type:Number,
            default:0
        }
  },
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
        txtTienChuyenQuyen:  "0",        
        txtVATChuyenQuyen: "0",       
        sumtien: 0,
        tongtien: "0",
        tongvat: "0",
    };
  },
  watch: {
      txtChuyenQuyen: function(val){
          this.txtTienChuyenQuyen = Math.round(parseInt(val.replace(/\D/g,''))*0.909088889).toLocaleString();
          this.txtVATChuyenQuyen = Math.round(parseInt(val.replace(/\D/g,''))*0.090911111).toLocaleString();   
      },      
  },
  created() {},
  computed: {},
  mounted() {
    this.txtTienChuyenQuyen = this.tien.toLocaleString();
    this.txtVATChuyenQuyen = this.vat.toLocaleString();
    this.txtChuyenQuyen = this.tong.toLocaleString();  
  },
  methods: {
      formatPrice(value) {
            let val = (value / 1).toFixed(0).replace(',', '.')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
        },
       async ChiTietTienKM_CapNhap(){                
          this.$emit('acceptTienKM',
          { 
              tongtien: (this.txtTienChuyenQuyen.replace(/\D/g,'')),
              tongvat: (this.txtVATChuyenQuyen.replace(/\D/g,''))
          }); 
      },
  },
};
</script>