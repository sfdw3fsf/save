<!-- Gọi popup call 1 trong 2 hàm openDialog1 hoặc openDialog2 -->
<!-- Su kien close form: @closeThongSoWan-->
<template src="./frmThongSoCNTT.html"></template>
<script>
import moment from 'moment'
import { LoaiHinhTB, KieuLapDat, LoaiHopDong } from '@/const/enums/index.js'
import gridCNTT from './gridCNTT'
export default {
  name: 'frmThongSoCNTT',
  components: {
    moment,
    gridCNTT
  },
  props: [ 'tinh','dt_ban','dt_thicong', 'vloaitb_id', 'thongsotc', 'thongsoban','dvvt','khachhang' ],
  data() {
    return {
      cboDoiTuongBan: {
        list: [],
        value: 0,
        Enabled: true,
      },
      cboMucCuocBan: {
        list: [],
        value: 0,
        Enabled: true,
      },
      cboDoiTuongTC: {
        list: [],
        value: 0,
        Enabled: true,
      },
      cboMucCuocTC: {
        list: [],
        value: 0,
        Enabled: true,
      },
      cboKieuLD: {
        list: [],
        value: 0,
        Enabled: true,
      },
      phanvung_id: this.$root.token.getPhanVungID(),
      Loading: false,
      position: { X: 'center', Y: 'top' },
      animationSettings: { effect: 'Zoom' },
      kieuldid: null,
    }
  },
  created() {},
  mounted() {
    this.frmThongSoCNTT_Load();
  },
  methods: {
    async tsbtnCapNhat_Click() {
      console.info('tsbtnCapNhat_Click',JSON.stringify(this.thongsoban),JSON.stringify(this.thongsotc));
      this.thongsoban.thongso_cntt = this.$refs.gridCNTTBan.ItemList;
      this.thongsoban.muccuoctb_id = this.cboMucCuocBan.value;
      this.thongsoban.doituong_id = this.cboDoiTuongBan.value;
      
      this.thongsotc.thongso_cntt = this.$refs.gridCNTTTC.ItemList;
      this.thongsotc.muccuoctb_id = this.cboMucCuocTC.value;
      this.thongsotc.doituong_id = this.cboDoiTuongTC.value;
      this.thongsotc.kieuld_id = this.cboKieuLD.value;

      this.$emit('form-close', {'thongso_ban': this.thongsoban,'thongso_tc': this.thongsotc });
    },
    async frmThongSoCNTT_Load() {
      // this.phanvung_id = this.$root.token.getPhanVungID();
      if(this.thongsotc.kieuld_id) this.cboKieuLD.value = this.thongsotc.kieuld_id;
      if(this.thongsoban.doituong_id) this.cboDoiTuongBan.value = this.thongsoban.doituong_id;
      if(this.thongsotc.doituong_id) this.cboDoiTuongTC.value = this.thongsotc.doituong_id;
      if(this.thongsoban.muccuoctb_id) this.cboMucCuocBan.value = this.thongsoban.muccuoctb_id;
      if(this.thongsotc.muccuoctb_id) this.cboMucCuocTC.value = this.thongsotc.muccuoctb_id;
      var api1 = this.$root.context.post('/web-bancheo/danhmuc/load_dm_kieuld', {
        "tinhthicong_id": this.tinh.tinhthicong_id,
        "vloaihd": LoaiHopDong.DAT_MOI,
        "ploaitb_ld": this.vloaitb_id,
        "ploaitb": this.cboKieuLD.value, //xem co can cai nay khong?
      });
      var api2 = this.$root.context.post('/web-hopdong/thaydoitocdoadsl/sp_lay_ds_doituong_theo_dieukien', {});
      var api3 = this.$root.context.post('/web-bancheo/danhmuc/load_dm_doi_tuong', {
        "tinhthicong_id": this.tinh.tinhthicong_id,
      });
      var api4 = this.$root.context.post('/web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2',  {
        "khuld_id": 0,
        "apld_id": 0,
        "phold_id": 0,
        "phuongld_id": 0,
        "loaitb_id": this.vloaitb_id,
        "doituong_id": this.cboDoiTuongBan.value,
        "tocdo_id": 0,
        "muccuoc_id": 0, // xem lai, kha nang truyen this.cboMucCuocBan.value
        "ngay": moment().format('DD/MM/YYYY'),
        "trangbi_id": 0
      });
      var api5 = this.$root.context.post('/web-bancheo/tracuu/lay_ds_mucuoc_tb_v2',  {
        "tinhthicong_id": this.tinh.tinhthicong_id,
        "khuld_id": 0,
        "apld_id": 0,
        "phold_id": 0,
        "phuongld_id": 0,
        "loaitb_id": this.vloaitb_id,
        "doituong_id": this.cboDoiTuongTC.value,
        "tocdo_id": 0,
        "muccuoc_id": 0, // xem lai, kha nang truyen this.cboMucCuocTC.value
        "ngay": moment().format('DD/MM/YYYY'),
        "trangbi_id": 0
      });

      await Promise.all([api1, api2, api3, api4, api5]).then(async ([rs1, rs2, rs3, rs4, rs5]) => { 
        this.cboKieuLD.list = rs1.data;
        if((rs1.data && rs1.data.length>0) && (!this.cboKieuLD.value || !rs1.data.find(x=>x.kieuld_id==this.cboKieuLD.value)))
          this.cboKieuLD.value = rs1.data[0].kieuld_id;
        this.cboDoiTuongBan.list = rs2.data;
        
        this.cboMucCuocBan.list = rs4.data;
        this.cboMucCuocTC.list = rs5.data;

        if((rs2.data && rs2.data.length>0) && (!this.cboDoiTuongBan.value || !rs2.data.find(x=>x.doituong_id==this.cboDoiTuongBan.value))) {
          this.cboDoiTuongBan.value = rs2.data[0].doituong_id;
          await this.cboDoiTuongBan_EditValueChanged(this.cboDoiTuongBan.value)
        }
        this.cboDoiTuongTC.list = rs3.data;
        if((rs3.data && rs3.data.length>0) && (!this.cboDoiTuongTC.value || !rs3.data.find(x=>x.doituong_id==this.cboDoiTuongTC.value))) {
          this.cboDoiTuongTC.value = rs3.data[0].doituong_id;
          await this.cboDoiTuongTC_EditValueChanged(this.cboDoiTuongTC.value)
        }

        if((this.cboMucCuocBan.list && this.cboMucCuocBan.list.length>0) && (!this.cboMucCuocBan.value || !this.cboMucCuocBan.list.find(x=>x.muccuoctb_id==this.cboMucCuocBan.value))) { 
          this.cboMucCuocBan.value = this.cboMucCuocBan.list[0].muccuoctb_id;
        }
        if((this.cboMucCuocTC.list && this.cboMucCuocTC.list.length>0) && (!this.cboMucCuocTC.value || !this.cboMucCuocTC.list.find(x=>x.muccuoctb_id==this.cboMucCuocTC.value))) {
          this.cboMucCuocTC.value = this.cboMucCuocTC.list[0].muccuoctb_id;
        }
        this.cboKieuLD.Enabled = true;
        this.cboDoiTuongBan.Enabled = true;
        this.cboMucCuocBan.Enabled = true;
        this.cboDoiTuongTC.Enabled = true;
        this.cboMucCuocTC.Enabled = true;
        // this.cboKieuLD.value = this.thongsotc.kieuld_id;
        // this.cboDoiTuongBan.value = this.thongsoban.doituong_id;
      });
    },
    async cboDoiTuongBan_EditValueChanged(val) {
      console.info(val);
      
      var rs4 = await this.$root.context.post('/web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2',  {
        "khuld_id": 0,
        "apld_id": 0,
        "phold_id": 0,
        "phuongld_id": 0,
        "loaitb_id": this.vloaitb_id,
        "doituong_id": this.cboDoiTuongBan.value,
        "tocdo_id": 0,
        "muccuoc_id": 0, // xem lai, kha nang truyen this.cboMucCuocBan.value
        "ngay": moment().format('DD/MM/YYYY'),
        "trangbi_id": 0
      });
      this.cboMucCuocBan.list = rs4.data;
      if((this.cboMucCuocBan.list && this.cboMucCuocBan.list.length>0) && (!this.cboMucCuocBan.value || !this.cboMucCuocBan.list.find(x=>x.muccuoctb_id==this.cboMucCuocBan.value))) { 
        this.cboMucCuocBan.value = this.cboMucCuocBan.list[0].muccuoctb_id;
      }
    },
    async cboDoiTuongTC_EditValueChanged(val) {
      console.info(val);
      var rs5 = await this.$root.context.post('/web-bancheo/tracuu/lay_ds_mucuoc_tb_v2',  {
        "tinhthicong_id": this.tinh.tinhthicong_id,
        "khuld_id": 0,
        "apld_id": 0,
        "phold_id": 0,
        "phuongld_id": 0,
        "loaitb_id": this.vloaitb_id,
        "doituong_id": this.cboDoiTuongTC.value,
        "tocdo_id": 0,
        "muccuoc_id": 0, // xem lai, kha nang truyen this.cboMucCuocTC.value
        "ngay": moment().format('DD/MM/YYYY'),
        "trangbi_id": 0
      });
      this.cboMucCuocTC.list = rs5.data;
      if((this.cboMucCuocTC.list && this.cboMucCuocTC.list.length>0) && (!this.cboMucCuocTC.value || !this.cboMucCuocTC.list.find(x=>x.muccuoctb_id==this.cboMucCuocTC.value))) {
        this.cboMucCuocTC.value = this.cboMucCuocTC.list[0].muccuoctb_id;
      }
    },
    async HienThi_TT(kieu) {

    },
    repositoryItemButtonEdit_ButtonClick() {
      //gan nhu khong xu ly gi?
    },
    repositoryItemCheckEdit_CheckedChanged() {
      //Chi thay doi gia tri. Co load du lieu depend field strDepandField -> xem lai
    },
    repositoryItemTextEdit_Click() {

    },
    repositoryItemTextEdit_EditValueChanged() {
      //TODO can xu ly!
    },
    repositoryItemTextEdit_EditValueChanging() {
      //Khong co gi
    },
    repositoryItemLookUpEdit_EditValueChanging() {
      //TODO can xu ly!
    },
    repositoryItemLookUpEdit_EditValueChanged() {
      //TODO can xu ly
    },
    repositoryItemLookUpEdit_leave() {
      //khong co gi
    },
    repositoryItemTextEdit_Leave() {
      ///khong co gi
    },
    repositoryItemDateEdit_EditValueChanged() {
      //TODO can xu ly
    },
    tsbtnThoat_Click() {
      this.closeDialog()
    },
    closeDialog() {
      this.$refs.dialogThongSoCNTT.hide()
    },
    showDialog() {
      this.$refs.dialogThongSoCNTT.show()
    },
    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    DongBoDuLieu(obj) {
      this.$refs.gridCNTTTC.ChangeData(obj);
    }
  }
}
</script>
<style scoped>
.box2-thongsocntt {
  /* background-color: rgb(240, 240, 240); */
  padding-top: 0px !important;
  margin-top: -10px !important;
}
.box2-thongsocntt .box-form {
  /* background-color: rgb(240, 240, 240); */
}
.box-form .legend-title {
}
.box1-thongsocntt .box-form {
  /* background-color: rgb(227, 241, 254);; */
}
.box1-thongsocntt {
  /* background-color: rgb(227, 241, 254); */
  padding-top: 0px !important;
}

.box-form .row { min-height:37px;}  
</style>
