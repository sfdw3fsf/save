<template src="./index.html"></template>
<style scoped src="./index.scss"></style>
<script>
import Vue from 'vue'
import moment from "moment";
import { mapActions, mapState, mapGetters } from "vuex";
import KyCuoc from "../components/KyCuoc.vue";
import { GRID_COLUM_DANHSACH, GRID_COLUM_DANHSACH_CHITIET } from './Constan.js'
import CommonsAPI from '../api/Commons'
import { DialogUtility } from '@syncfusion/ej2-popups'

Vue.use(DialogUtility)
let DialogObj = undefined



export default {
  name: "XacNhanCongNoTheoDanhSach",
  components: {

    //appKyCuoc: KyCuoc,        
    PopupXacNhanXoaPhieu: () => import('../components/PopupInXoaSuaPhieuThanhToan/PopupXacNhanXoaPhieu'),

  },

  data() {
    return {
      kiemtra_matkhau: true,
      fieldsDanhSach: GRID_COLUM_DANHSACH,
      fieldsDanhSachChiTiet: GRID_COLUM_DANHSACH_CHITIET,
      fieldsNganHang: { text: 'TEN_NH', value: 'NGANHANG_ID' },

      config: {
        dateConfig: {
          value: new Date(),
          format: "dd/MM/yyyy",
          max: new Date()
        },
        kyCuocConfig: {
          value: moment(new Date()).subtract(1, 'months').endOf('month').toDate(),
          format: "MM/yyyy",
          max: moment(new Date()).subtract(1, 'months').endOf('month').toDate(),
        }
      },

      tuNgay: new Date(),
      denNgay: new Date(),
      kyCuoc: moment(new Date()).subtract(1, 'months').endOf('month').toDate(),
      p_kycuoc: '',
      dsGachNo: [],
      dsGachNoChiTiet: [],
      maND: this.$auth.getUserName(),
      phieuSelected: [],
      dsSelected: [],
      nganHangId:0,
      dsNganHang:[],
    };
  },
  async created() {
    await this.getKiemTraMatKhau();
    await this.getDSNganHang();
  },
  computed: {

  },
  methods: {

    handleKyCuoc(e) {
      this.pKyCuoc = moment(e.p_kyhoadon).format("yyyyMM") + e.p_chuky_no;
    },

    tsbtnTimKiem_Click: async function () {
      this.dsGachNo=[]
      this.dsGachNoChiTiet=[]
      let input = {
        pKyCuoc: moment(this.kyCuoc).format("yyyyMM") + "01",
        pMaND: this.maND,
        pTuNgay: moment(this.tuNgay).format("DD/MM/yyyy"),
        pDenNgay: moment(this.denNgay).format("DD/MM/yyyy"),
        pNganHangId:this.nganHangId
      }
      await this.getDsGachNoDanhSach(input);
      
    },
    putDeleteGachNoDanhSach: async function (params) {

      try {
        this.loading(true);
        const rs = await this.$root.context.post(
          `/web-thuno/api/thu-no/gach-no-tien-mat/capnhat-danhsach-xoaphieu-gachno-theo-danhsach`, params
        );        
        this.loading(false);        
      } catch (error) {
        this.loading(false);
      }
    },
    getDsGachNoDanhSach: async function (params) {

      try {
        this.loading(true);
        const rs = await this.$root.context.post(
          `/web-thuno/api/thu-no/gach-no-tien-mat/lay-danhsach-gachno-theo-danhsach`, params
        );
        
        if (rs.data.length>0)
            this.dsGachNo = rs.data;
        else
          this.$toast.error('Không có dữ liệu, đề ghị thử lại')
        
        this.loading(false);
      } catch (error) {
        this.loading(false);
      }
    },
    onDanhSachChange: async function (item) {
      //console.log('item',item);
      this.p_kycuoc = moment(this.kyCuoc).format("yyyyMM") + "01";
      try {
        this.loading(true);
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/gach-no-tien-mat/lay-danhsach-gachno-theo-danhsach-chitiet?pKyCuoc=${this.p_kycuoc}&pDanhSachId=${item.DANHSACH_ID}`
        );
        if (rs.data.data.length>0)
          this.dsGachNoChiTiet = rs.data.data;
        else
          this.$toast.error('Không có dữ liệu, đề ghị thử lại')
        this.loading(false);
      } catch (error) {
        this.loading(false);
      }
    },
    onDSChiTietCheck(item) {
      //console.log('danhsach;',item);

      if (!item.isHeaderCheckboxClicked && item.isInteracted) {
        console.log('check all')
      }

      this.phieuSelected = [...item]
    },
    async doDelete() {
      let tongTien = 0
      let tongPhieu = 0
      //this.phieuSelected = this.$refs.dsPhieuTToan.getSelectedRecords()

      if (this.phieuSelected.length <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu để xóa.')
        return false
      }

      let errors = []
      let user = this.$auth.getUserName()
      let dongy = false;
      var isBoxConfirm = false;
      await this.$bvModal.msgBoxConfirm("Bạn có muốn xóa danh sách phiếu phiếu đã chọn này không!", {
        title: "Thông báo",
        centered: true,
        size: "md",
        okTitle: "Đồng ý",
        cancelTitle: "Hủy",
      })
        .then((value) => {
          isBoxConfirm = value
        });
      if (!isBoxConfirm) {
        return;
      }
      this.p_kycuoc = moment(this.kyCuoc).format("yyyyMM") + "01";
      console.log('ds phieu', this.phieuSelected)
      this.dsSelected=this.phieuSelected.filter(item=>item.PHIEU_ID!=null)
      this.phieuSelected=[...this.dsSelected]

      // this.XPParams = {
      //   tongTien: currency(tongTien),
      //   tongPhieu: currency(tongPhieu)
      // }

      //this.dsPhieuCanXoa = this.$refs.dsPhieuTToan.getSelectedRecords()

      this.$refs.refXoaPhieuTT.openDialog()
    },
    // confirmXoaPhieu(){
    //   console.log(DialogObj);
    //   if(DialogObj !== undefined) DialogObj.hide() 
    //     this.$toast.success('Bạn xóa thành cong' );
    // },
    async deletePhieuThuHandler(flag) {
      if (flag) // xóa thành công
      {
        let pDanhSachId='';
        this.dsSelected=[];
        this.phieuSelected.forEach(item => {
          this.dsSelected.push(item.PHIEU_ID)
          pDanhSachId=item.DANHSACH_ID;
        });

        let param={
          "pDanhSachId":pDanhSachId,
          "pKyCuoc":moment(this.kyCuoc).format("yyyyMM") + "01",
          "pDanhSachPhieu":this.dsSelected.join(','),
        }
        this.putDeleteGachNoDanhSach(param)
        this.params.f_changed = true
        await this.tsbtnTimKiem_Click()
      }
    },
    async getKiemTraMatKhau() {
      try {
        const res = await CommonsAPI.getThamSoMacDinh(this.axios, { pMaThamSo: 'KHONG_KT_MATKHAU_XOA_GACHNO' })
        const data = await res.data

        if (data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0) {
          let ts = Number(data.data[0].TEN_TS)
          if (ts === 1) {
            this.kiemtra_matkhau = false
          }
        }
      } catch (error) {
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else console.log(error)
      }
    },
    async getDSNganHang () {
      try {
        const res = await CommonsAPI.getDSNganHangGachNo(this.axios)
        const data = await res.data
        
        if (data.error_code === 'BSS-00000000' && data !== undefined) {
          this.dsNganHang = data.data
        }
      } catch (error) {
        if (error.data !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else {
          this.$toast.error(error.data)
        }
      } finally {}
      console.log('nganhang',this.dsNganHang)
    },
    rowDataBound: function (args) {
      console.log(args);
      if (args.data.TONGSO_LOI>0)
      {
      args.row.classList.remove('e-altrow');
      args.row.classList.add("bg-info");
      args.row.classList.add("text-white");      
      }
    }
  }
};
</script>
