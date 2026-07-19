
<template src="./index.html"></template>
<script>
import kycuoc from '../components/KyCuoc'
export default {
    components:{kycuoc},
    data() {
        return {
            dsDichVu:[],
            dsLoaiHinh:[],
            dsPhieu:[],            
            form:{
                dichVuVT_ID:0,
                loaiHinhTB_ID:0,     
                daTaoPhieu:0,
                denNgay:new Date(),
                tuNgay:new Date(),
                
            },
        }
    },
    async created(){
        await this.getDichvuVT();
    },
    methods:{
        getDichvuVT: async function(){
        try { 
            var rs = await this.$root.context.get( 
            `web-thuno/api/thu-no/common/dich-vu-vien-thong`
            );
            this.dsDichVu=rs.data; 
            }
        catch (e)
            {
                console.log(e);
            }
        },
         getLoaiHinhTB: async function(dichvuid){
        try { 
            var rs = await this.$root.context.get( 
            `web-thuno/api/thu-no/common/loai-hinh-thue-bao/dich-vu-vien-thong/` + dichvuid
            );
            this.dsLoaiHinh=rs.data; 
            }
        catch (e)
            {
                console.log(e);
            }
        },
       getHDThanhLyTamDung:async function() {

           try { 
               const data = {
                pDichVuVTId: this.dichVuVT_ID,
                pNguoiCN: this.$auth.getUserName(),
                pKyHoaDon: 20211001,
                pKieu: 2,
                pageNo: 1,
                pageSize: 2000,
                totalRow: 1
            };
            var rs = await this.$root.context.get( 
            `web-thuno/api/thu-no/danh-sach-thanh-ly-thue-bao-tam-dung/lay-danh-sach-thanh-ly-tam-dung-theo-dieu-kien`,data
            );
            this.dsPhieu=rs.data; 
            }
        catch (e)
            {
                console.log(e);
            }
       },
    },
   watch: {
    "form.dichVuVT_ID" :async function(newval) {
        await this.getLoaiHinhTB(newval)
        await this.getHDThanhLyTamDung();
      }
    },
};
</script>