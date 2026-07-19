<template src="./index.html">
</template>
<style scoped>
input[type=number] {
  text-align:right;
}
input[type=text] {
  text-align:left;
}
</style>
<script>
import moment from "moment";

import breadcrumb from "@/components/breadcrumb";

export default {   
   props: {
        isShow: Boolean,
        matb:'',
        thuebaoid:0,
        dichvuvtid:0,
   },
   data : function () {
     return {
     isShowThueBaoDonVi:false,
     isShowThueBaoNhanVien:false,
     isShowGoiDADV:false,
     isShowThueBaoCungDoiCap:false,
     isShowThueBaoThongSo:false,
     dsThueBaoDonVi:[],
     dsNhanVienKhuVuc:[],
     dsThueBaoCungCap:[],
     dsThueBaoGoiDADV:[],
     dsLichSuThayDoi:[],
     dsTBMoRong:[],
      db_con: {
          chuquan_ld:'',
          cuoc_doitac:'',
          doitac:'',
          ghichu:'',
          ipv4_wan:'',
          ipv6_LAN:'',
          loaimytv:'',
          madoicap:0,
          matb_tn:'',
          ma_lt:'',
          muccuoc:'',
          password:'',
          phanloai_dv:'',
          thietbi:'',
          tenchuquan:'',
          thongtin_tc:'',
          tocdo:'',
          trangbi:'',
          diachi_mac:'',
          username:'',
        },
     }
   },
  methods: {
    showGrid: function (panel) {

      if (panel===1)
        this.isShowThueBaoDonVi=!this.isShowThueBaoDonVi;
      if (panel===2)
        this.isShowThueBaoNhanVien=!this.isShowThueBaoNhanVien;

      if (panel===3)
        this.isShowGoiDADV=!this.isShowGoiDADV;

      if (panel===4)
        this.isShowThueBaoCungDoiCap=!this.isShowThueBaoCungDoiCap;

      if (panel===5)
        this.isShowThueBaoThongSo=!this.isShowThueBaoThongSo;

    },
    getDsThueBaoDonVi : async function (thuebao_id) {      
      try {
        const rs = await this.$root.context.get(
          `web-quantri/quan-ly-danh-ba/donvi-thuebao-theoloai-donvi?pThueBaoID=${thuebao_id}&pLoaiDV=&pKieuDV=2`
        );
        if (rs.data.length>0)
            this.dsThueBaoDonVi = rs.data;
      } catch (error) {}
    },
    getDsNhanVienKhuVuc : async function (thuebao_id) {      
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danhsach-nhanvien-khuvuc-thuebao/${thuebao_id}`
        );
        if (rs.data.length>0)
            this.dsNhanVienKhuVuc = rs.data;
      } catch (error) {}
    },    
    getDsLichSuThayDoi : async function (thuebao_id) {      
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lichsu-thaydoi-thongso-kythuat/${thuebao_id}`
        );
        if (rs.data.length>0)
            this.dsLichSuThayDoi = rs.data;
      } catch (error) {}
    },   
    getDsThueBaoGoiDADV : async function (thuebao_id) {      
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/biendong_goi_da_dichvu/${thuebao_id}`
        );
        if (rs.data.length>0)
            this.dsThueBaoGoiDADV = rs.data;
      } catch (error) {}
    },    
    getDsThueBaoCungCap : async function (madoicap) {      
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danhsach-thuebao-cung-doicap/${madoicap}`
        );
        if (rs.data.length>0)
            this.dsThueBaoCungCap = rs.data;
      } catch (error) {}
    },        
    getDsThongTinMoRong: async function (thuebao_id) {
      let param = {
        p_dichvu_id: this.dichvuvtid,
        p_thuebao_id: this.thuebaoid,
      };
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danhsach-thuoctinh-thuebao/${thuebao_id}`,
          param
        );
        this.dsTBMoRong = rs.data;
      } catch (error) {}
    },
   
    hienthi_thongtin_danhba_con: async function () {
      this.loading(true);
      await this.getDsThueBaoDonVi(this.thuebaoid);
      await this.getDsNhanVienKhuVuc(this.thuebaoid);
      await this.getDsLichSuThayDoi(this.thuebaoid);
      await this.getDsThueBaoGoiDADV(this.thuebaoid);
      await this.getDsThongTinMoRong(this.thuebaoid);           //console.log(this.dsTBMoRong);
      if (this.dsTBMoRong.length>0)
      {
      let ds = this.dsTBMoRong[0];
      this.db_con.chuquan_ld=ds.CHUQUAN_LD;
      this.db_con.cuoc_doitac=ds.CUOC_DOITAC;
      this.db_con.doitac=ds.DOITAC;
      this.db_con.ghichu=ds.GHICHU;
      this.db_con.ipv4_wan=ds.IPV4_WAN;
      this.db_con.ipv6_LAN=ds.IPV6_LAN;
      this.db_con.loaimytv=ds.LOAIMYTV;
      this.db_con.madoicap=ds.MADOICAP;
      this.db_con.matb_tn=ds.MATB_TN;
      this.db_con.ma_lt=ds.MA_LT;
      this.db_con.muccuoc=ds.MUCCUOC;
      this.db_con.password=ds.PASSWORD;
      this.db_con.phanloai_dv=ds.PHANLOAI_DV;
      this.db_con.thietbi=ds.TBI;
      this.db_con.tenchuquan=ds.TENCHUQUAN;
      this.db_con.thongtin_tc=ds.THONGTIN_TC;
      this.db_con.tocdo=ds.TOCDO;
      this.db_con.trangbi=ds.TRANGBI;
      
      await this.getDsThueBaoCungCap(this.db_con.madoicap);
      }
      this.loading(false);
    },
    closeModalTTThueBao: function(data = {isUpdated: false,isShow:false}) {
            
        this.$emit('closeModalTTThueBao', data);
    },
    async onModalShown() {
      if (this.thuebaoid>0)
      {            
        await this.hienthi_thongtin_danhba_con();
      }
    },
  },
  /*
  mounted: async function () {          
          if (this.thuebaoid>0)
          {            
            await
            this.hienthi_thongtin_danhba_con();
          }
            
  },
  watch : {
    'thuebaoid': async function(newVal, oldVal) {        
      if (!!newVal)
      {
        await this.hienthi_thongtin_danhba_con();
      }
    },
  }
  */
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

#table-scroll {
  height: 410px;
  overflow: auto;
  margin-top: 20px;
}

</style>
