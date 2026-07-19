<template src="./Modal_TraPhieuVT.html"></template>
<script>
import {
  GridPlugin,
  Page,
  Filter,
  CommandColumn,
  Resize,
} from "@syncfusion/ej2-vue-grids";
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import {
  required,
} from "vuelidate/lib/validators";
export default {
  name: "Modal_TraPhieuVT",
  props: ["modalId","data"],
  provide: {
    grid: [Filter, Page, CommandColumn, Resize],
  },
  created: async function () {},
  components: {BssErrorMarker,BssRequiredMarker
  },
  computed: {},
  methods: {
    focusMyElement: async function () {     
        await this.initForm();           
    },
    hiddenModal: function(){
     this.resetForm()
    },
    initForm: async function () {
       try {
       this.$root.showLoading(true);    
       this.phieuId = this.data.phieu_id;
       this.hscc_id = this.data.hscc_id;
       await this.LayThongTinPhieuGiao()
      await this.LAY_DANHMUC_QLDN()
     } catch (er) {
        console.log(er)
      } finally {
        this.$root.showLoading(false);
      }
    },
    LayThongTinPhieuGiao: async function () {
        let rs = await this.$root.context.get(
        "/web-cabman/CaiTaoBaoDuong/LayThongTinPhieuGiao/"+this.phieuId
      );
      this.dsPhieu = rs.data.result;
      console.log('this.dsPhieu',this.dsPhieu)
    },
    LAY_DANHMUC_QLDN: async function () {
      let rs = await this.$root.context.get(
        "/web-cabman/CaiTaoBaoDuong/LayDsLyDo"
      );
      this.dsLydo = rs.data.result
      if( this.dsLydo !=null &&  this.dsLydo.length > 0 )
      this.dataSelected.lydo_tra = this.dsLydo[0].LYDOTRA_ID
    },
    udapteTraPhieu: async function () {
      try{
      let rs = await this.$root.context.post(
        "/web-cabman/CaiTaoBaoDuong/traphieuV2",{
          phieu_id:this.phieuId,
          hscc_id: this.hscc_id,
          lydo_tra:this.dataSelected.lydo_tra,
          noidung_tra:this.dataSelected.noiDungTra
        }
      );
      return rs.data;
      }catch (error) {
        return []
      }
    },
    grid_phieu_selected: async function (data) {
      if(data!=null){
      this.dataSelected.ngay_giao = data.NGAYGIAO
      this.dataSelected.donvi_giao = data.DONVI_GIAO
      this.dataSelected.nguoi_giao =  data.NHANVIEN_GIAO
      this.dataSelected.noidung_giao =  data.ND_THUCHIEN
      }
  
    },
    resetForm:  function () {
      this.dsPhieu = []
      this.dataSelected={
       ngay_giao: new Date(),
       ngay_tra: new Date(),
       lydo_tra:0,
       noiDungTra:'',
       donvi_giao:'',
       noidung_giao:'',
       nguoi_giao:''
     }
        this.$v.$reset()
    },
    capNhat:async  function () {
      await this.checkDieuKien()
    },
    checkDieuKien: function () {
      if (this.dsPhieu.length <= 0) return;
        this.$confirm(
            `Bạn thật sự muốn trả lại phiếu đã nhận không?`,
            "Trả phiếu vật tư",
            {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
            }
          )
            .then( async() => {
            if (!this.validateData()){
            let rs = await this.udapteTraPhieu();
                if(rs.result.RS =='OK')
                 this.$root.toastSuccess('Đã trả phiếu thành công!')
                else
                 this.$root.toastSuccess('Đã trả phiếu thất bại!')
                 this.resetForm()
            }
            })
      .catch((e) => {
      return
      });                
    },
    validateData() {
      this.$v.$touch();
      let flag=false
      let errorMessage = [];
       if (!this.$v.dataSelected.noiDungTra.required) {
        errorMessage.push("Bạn chưa nhập nội dung trả!");
        if(!flag)
         this.$refs['txtNoiDung'].focus()
         flag=true
      }
      if (!this.$v.dataSelected.lydo_tra.required) {
        errorMessage.push("Hãy chọn lý do trả!");
         if(!flag)
         this.$refs['lydo_tra'].focus()
         flag=true
      }
      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join("\n"));
       
        return true;
      } else {
        return false;
      }
    },
  },
  data: function () {
    return {
    phieuId:0, 
    hscc_id:0, 
    dsLydo:[],
    dsPhieu:[],
    currentItem: {},
    waterMark: "Chọn ngày",
    dateVal:  Date,
      dateFormat: "dd/MM/yyyy",
     dataSelected:{
       ngay_giao: new Date(),
       ngay_tra: new Date(),
       lydo_tra:0,
       noiDungTra:'',
       donvi_giao:'',
       noidung_giao:'',
       nguoi_giao:'',
       ghichu:''
     }
    };
  },
   validations: {
    dataSelected: {
      noiDungTra: { required },
      lydo_tra: { required }
    }
   }
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