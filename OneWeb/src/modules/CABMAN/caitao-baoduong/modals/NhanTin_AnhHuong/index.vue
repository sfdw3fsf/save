<template src="./index.html"></template>
<script>
import {
  GridPlugin,
  Page,
  Filter,
  CommandColumn,
  Resize,
} from "@syncfusion/ej2-vue-grids";
export default {
  name: "NhanTin_TB_AnhHuong",
  props: ["modalId","data"],
  provide: {
    grid: [Filter, Page, CommandColumn, Resize],
  },
  created: async function () {},
  components: {
  },
  computed: {},
  methods: {
    focusMyElement: async function () {  
        if(this.data!=null)
        this.chitietcc_id  = this.data
        await this.initForm(); 
    },
    hiddenModal: function(){
     this.resetForm()
    },
    initForm: async function () {
       try {
       this.$root.showLoading(true);    
       let dsThamSo = await this.lay_ds_thamso_md()
       let det = [];
       if(dsThamSo != null && dsThamSo.length>0){
         let str_vtemp = "HT_GUI_TBAH_CTBD";
          det = dsThamSo.filter(x=>x.MA_TS == str_vtemp);
        }    
        if(det.length > 0)
          this.vts = det[0].TEN_TS
       if(this.vts == '0'){
         this.$root.toastError("Chưa thiết lập hình thức gửi tin nhắn. Liên hệ admin để cấu hình !");
         this.$bvModal.hide(this.modalId);
       }
       this.dsHinhThuc = await this.laydsHinhThucGui(this.vts);
       if(this.dsHinhThuc.length > 0){
         this.hinhthuc_id = this.dsHinhThuc[0].HT_ID
       } 
       this.dsPhieu = await this.laydsThueBaoAnhHuong(this.chitietcc_id)   
     } catch (er) {
        console.log(er)
      } finally {
        this.$root.showLoading(false);
      }
    },
    async lay_ds_thamso_md() {
                try {
                    let response = await this.$root.context.get(`/web-ecms/them_thietbi/lay_ds_thamso_md`)
                    return response.data
                } catch (err) {
                    return []
                }
            },
    async laydsHinhThucGui(kieu) {
                try {
                    let response = await this.$root.context.get("/web-cabman/CaiTaoBaoDuong/lay-ds-hinhthuc-gui",{kieu:kieu.toString()})
                    return response.data.result
                } catch (err) {
                    return []
                }
            },
    async laydsThueBaoAnhHuong(cchitiet_id) {
                try {
                    let response = await this.$root.context.get("/web-cabman/CaiTaoBaoDuong/lay-ds-thuebao-anhhuong/"+cchitiet_id)
                    return response.data.result
                } catch (err) {
                    return []
                }
            },        
    GuiTin: async function () { 
         try {
              this.$root.showLoading(true)
              let data = {
                      thueBaoID:this.hinhthuc_id,
                      chiTietCCID: this.chitietcc_id,
                }
            let response = await this.$root.context.post(`/web-cabman/CaiTaoBaoDuong/nhantin_ctbd`,data)
            let kq = response.data.result
                if (kq != "OK")
                   this.$root.toastError("Có lỗi: "+ kq)
                else
                    this.$root.toastSuccess("Đưa tin nhắn vào hàng đợi thành công!");
                } catch (err) {
                  console.log(err)
                  this.$root.toastError("Có lỗi xảy ra trong quá trình gửi tin")
                } finally {
                   this.$root.showLoading(false)
                }      
    },
    grid_phieu_selected: async function (args) {
      if(args!=null){
       this.currentItem = args.data
      }
    },
    resetForm:  function () {
      this.dsPhieu = []
    },
  },
  data: function () {
    return {
    chitietcc_id:0, 
    vts:'0', 
    dsHinhThuc:[],
    hinhthuc_id: 0,
    dsPhieu:[],
    currentItem: {},  
    };
  },

};
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
}
</style>