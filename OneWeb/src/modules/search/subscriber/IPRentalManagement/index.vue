<template src='./index.html'></template>

<script>
import breadcrumb from "@/components/breadcrumb";
import api from "./api/index.js";
import { TextBox } from '@syncfusion/ej2-inputs'
export default {
  components : {breadcrumb},  
  name: 'IPRentalManagement',
  data () {
    return {
      header: {
        title: "Quản lý thuê IP",
        list: [
          { name: "Đăng ký", link: { name: "Ui.cards" } },
          {
            name: "Quản lý thuê IP",
            link: { name: "Ui.buttons" },
          },
        ],
      },

      thongTinTB : {
          dichVuVT : [],
          dichVuVTSelected : "",
          loaiHinhTB : "",
          soMay: "",
          thuongHieu: "",
          soLuongIPTinh : "",
          soLuongIPMP: ""
      },
      thongTinThueIP : {
          soLuongIPTinhThue: "",
          soLuongBlockThue: ""
      },
      
      thuebao_id: 0,
      tocdo_id: 0,
      thaotac_id: 2,
      activeLayTT: false,
      activeNhapMoi: false,
      activeGhiLai: false,
      activeHuyBo: false,
      activeXoa: false,  
      
      

      tableConfig: {
          tableCollumns: [
             { 
                fieldName: "THAO_TAC",
                headerText: "Thao tác",
                allowFiltering: true,
                allowSorting: false,
                textAlign: "",
            },
            {
                fieldName: "ACCOUNT",
                headerText: "Số máy/Account",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "TEN_TB",
                headerText: "Tên thuê bao",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "THUONG_HIEU",
                headerText: "Thương hiệu",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "SL_IP_THUE",
                headerText: "Số lượng IP thuê",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "SL_BLOCK_THUE",
                headerText: "Số lượng Block thuê",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "NGUOI_CN",
                headerText: "Người CN",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "NGAY_CN",
                headerText: "Ngày CN",
                allowFiltering: true,
                allowSorting: false,
            },
          ],
          tableData: [],
      }


    }
  },

   async created() {
    console.log(this.$root.token.getNhanVienID());
    console.log(this.$root.token.getPhanVungID());
    // this.loading(true);

    let res = await api.lay_dm_thue_ip(this.axios, {});
    this.thongTinTB.dichVuVT = await res.data.data ? res.data.data : []
    this.thongTinTB.dichVuVTSelected = this.thongTinTB.dichVuVT[0].dichvuvt_id
  },
  mounted() {},
  computed: {},
  watch: {
      "thongTinTB.dichVuVTSelected" :function(newVal){
          console.log('thay đổi thao tác');
        //   this.thaotac_id = newVal
      }
  },
  methods: {
      async btnLayTT_Click(){
        await this.HT_TT_THUE_IP();
      },

      btnNhapMoi_Click(){
          this.thaotac_id = 1
          this.Clear();
          this.SetButton(0);
      },

      async btnCapNhat_Click(){
          let thaotac;
          if(this.activeNhapMoi){
              thaotac = 2;
          }else{
              thaotac = 1;
          }
          if(!this.KT_DL(thaotac)) return;
          await this.CapNhat();

      },

      async btnHuyBo_Click(){
          await this.HT_TT_THUE_IP();
      },

      async btnXoa_Click(){

        if(this.tableConfig.tableData.length < 1) return false;
        this.$confirm(`Bạn muốn xóa thông tin thuê IP ko`, {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }).then(async () => {
          this.thaotac_id = 3
          this.CapNhat();
        });
      },

      async CapNhat(){
         let bodyAPI = {
           vthaotac: this.thaotac_id,
           vthue_ip: this.taoXML()  
         }
         let kq = await api.capnhat_thue_ip(this.axios, bodyAPI);
         if(await kq.data.error_code == "BSS-00000000"){
             this.$toast.success("Cập nhật thành công");
             this.HT_TT_THUE_IP();
         }else{
             this.$toast.error("Cập nhật thất bại, " + kq.data.message);
         }   
      },

      taoXML(){
         let str = ""
         str += "<DocumentElement>"
         str += "<thue_ip>"
         str += "<THUEBAO_ID>" + this.thuebao_id +"</THUEBAO_ID>" 
         str += "<SL_IPTINH>" + this.thongTinThueIP.soLuongIPTinhThue + "</SL_IPTINH>"
         str += "<SL_BLOCK>"+ this.thongTinThueIP.soLuongBlockThue + "</SL_BLOCK>"
         str += "<TOCDO_ID>" + this.tocdo_id + "</TOCDO_ID>"
         str += "</thue_ip>"
         str += "</DocumentElement>" 
         return str
      },
      
      async txtMA_TB_KeyPress(){
          await this.HT_TT_THUE_IP();
      },
       
      async HT_TT_THUE_IP(){
         this.thaotac_id = 2
         if(this.thongTinTB.dichVuVTSelected == ""){
             this.$toast.warning("Bạn chưa chọn loại dịch vụ!");
             return false
         }
         if(this.thongTinTB.soMay == ""){
             this.$toast.warning("Bạn chưa nhập số máy/account!");
             return false;
         }
         let bodyApi = {
             vma_tb: this.thongTinTB.soMay,
             vdichvuvt_id: this.thongTinTB.dichVuVTSelected
         }
         let res = await api.lay_tt_thue_ip(this.axios, bodyApi);
         let data = await res.data.data
         if(data.vds_tb){
             let vds_tb = data.vds_tb[0]
             if(vds_tb.loai == "1"){
                this.thongTinTB.soMay = vds_tb.ma_tb
                this.thongTinTB.loaiHinhTB = vds_tb.loaihinh_tb
                this.thongTinTB.thuongHieu = vds_tb.thuonghieu
                this.thongTinTB.soLuongIPTinh = vds_tb.iptinh
                this.thongTinTB.soLuongIPMP = vds_tb.sl_ip_mp
                this.thongTinTB.dichVuVTSelected = vds_tb.dichvuvt_id
                this.thongTinThueIP.soLuongIPTinhThue = vds_tb.sl_iptinh
                this.thongTinThueIP.soLuongBlockThue = vds_tb.sl_block
                this.vtocdo_id = vds_tb.tocdo_id
                this.thuebao_id = vds_tb.thuebao_id
                this.SetButton(1)
             }else{
                 this.thongTinTB.soMay = vds_tb.ma_tb
                 this.thongTinTB.loaiHinhTB = vds_tb.loaihinh_tb
                 this.thongTinTB.thuongHieu = vds_tb.thuonghieu
                 this.thongTinTB.soLuongIPTinh = vds_tb.iptinh
                 this.thongTinTB.soLuongIPMP = vds_tb.sl_ip_mp
                 this.vtocdo_id = vds_tb.tocdo_id
                 this.thuebao_id = vds_tb.thuebao_id
                 this.SetButton(0)
             }

             let vlog_db_ip = data.vlog_db_ip
             this.tableConfig.tableData = []
             if(vlog_db_ip && vlog_db_ip.length > 0){
                for(let i = 0; i < vlog_db_ip.length; i++){
                    let obj = {
                        THAO_TAC: vlog_db_ip[i].thaotac,
                        ACCOUNT: vlog_db_ip[i].ma_tb,
                        TEN_TB: vlog_db_ip[i].ten_tb,
                        THUONG_HIEU: vlog_db_ip[i].thuonghieu,
                        SL_IP_THUE: vlog_db_ip[i].sl_iptinh,
                        SL_BLOCK_THUE: vlog_db_ip[i].sl_block,
                        NGUOI_CN: vlog_db_ip[i].nguoi_cn,
                        NGAY_CN: vlog_db_ip[i].ngay_cn,
                    }

                    this.tableConfig.tableData.push(obj);
                }

             }
             
             
             
         }else{
             this.$toast.error("Không tìm thấy thông tin thuê bao!");
             this.Clear()
             this.SetButton(0)
             return false;
         }

      },

      Clear(){
        this.thongTinTB.soMay = "";
        this.thongTinTB.loaiHinhTB = "";
        this.thongTinTB.thuongHieu = ""
        this.thongTinTB.soLuongIPTinh = "0"
        this.thongTinTB.soLuongIPMP = "0"
        this.thongTinThueIP.soLuongIPTinhThue = "0"
        this.thongTinThueIP.soLuongBlockThue = "0"
        this.thuebao_id = 0;
        this.tocdo_id = 0;
        this.tableConfig.tableData = []
      },
      
      KT_DL(thaotac){
         console.log(thaotac); 
         if(!this.thongTinThueIP.soLuongIPTinhThue) {
             this.$toast.warning("Bạn chưa nhập số lượng IP tĩnh thuê");
             this.$refs.ipTinhThueRef.focus();
             return false;
         }
         if(!this.thongTinThueIP.soLuongBlockThue) {
             this.$toast.warning("Bạn chưa nhập số lượng BLOCK IP tĩnh thuê");
             this.$refs.blockThueRef.focus();
             return false;
         }

         if(Number(this.thongTinThueIP.soLuongIPTinhThue) > 0 && Number(this.thongTinThueIP.soLuongBlockThue) > 0){
             this.$toast.warning("Bạn không thể nhập số lượng IP tĩnh thuê và số lượng block thuê > 0");
             return false;
         }

         if(thaotac == 1){
             if(Number(this.thongTinTB.soLuongIPTinh) == 0){
                 this.$toast.warning("Không thể thuê IP với tốc độ IP động");
                 return false
             }

             if(Number(this.thongTinThueIP.soLuongIPTinhThue) == 0 && Number(this.thongTinThueIP.soLuongBlockThue) == 0){
                 this.$toast.warning("Bạn không thể nhập số lượng IP tĩnh thuê và số lượng block thuê = 0");
                 return false
             }
         }
         return true
      },
      
      SetButton(kieu){
        if(kieu == 0){
            //nhap moi, chua co du lieu
            this.activeNhapMoi = true
            this.activeGhiLai = false 
            this.activeXoa  = true
            this.activeHuyBo  = false
        }else{
            // TH con lai
            this.activeNhapMoi = false
            this.activeGhiLai = false
            this.activeXoa = false
            this.activeHuyBo = false          
        }
      }
  }
}
</script>
<style>
.disabled {
  /* pointer-events: none; */
  cursor: no-drop;
  opacity: 0.7;
}

.disabled-color {
  color: gray !important;
}
</style>