<template src="./index.html"></template>
<script>
import VueBarcode from 'vue-barcode'
import VueQRCodeComponent from 'vue-qrcode-component'
export default {
  name: "QRcode",
  props: ["modalId"],
  computed: {},
  components: {
     'qr-code': VueQRCodeComponent,
    'barcode': VueBarcode
    
  },
  destroyed() {},
  watch: {

  },
  methods: {
    setData: function(data){
        if(data!=null){
          this.soqr_code = data
        }
    },
    Load_form:async function(){
       try
            {
                if (this.soqr_code == "") return;
                // this.qrcode_value = this.soqr_code
                // this.barcode_value = this.soqr_code
                let dsThongTinChiTiet = await this.ts_tc_tb_esim(this.soqr_code);
                if (dsThongTinChiTiet != null && dsThongTinChiTiet.length > 0) 
                {
                    if (dsThongTinChiTiet[0].QRSTRING != null)
                    {
                        this.qrcode_value = dsThongTinChiTiet[0].QRSTRING
                    }
                    if (dsThongTinChiTiet[0].BARCODES != null)
                    {
                      this.barcode_value = dsThongTinChiTiet[0].BARCODES
                    }
                }
            }
            catch ( exp)
            {
               this.$root.toastError("Lỗi khi thực hiện in QRCode !");
               console.log(exp)
            }
    },
    hiddenModal: function(){
    this.soqr_code= ''
    },
    focusMyElement: async function () {
      try{
        this.$root.showLoading(true)
        await this.Load_form()
      }catch(er){
        console.log(er)
      }
      finally{
         this.$root.showLoading(false)
      }
    },
   ts_tc_tb_esim: async function (soqr_code){
      try {
        let rs = await this.$root.context.post(
          "ccbs/executor/ts_tc_tb_esim",
        {
         so_tb: soqr_code
         }
        );
        return rs.data
      } catch (error) {
        return null;
      } finally {
      }
   }
 },
  data() {
    return {
     soqr_code:'',
     qrcode_value:'',
     barcode_value:''
    }
  },
   validations: {}
};
</script>
<style>
</style>