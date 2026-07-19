<template src="./index.html">
</template>

<script>
export default {
  name: 'giaophieu',

  data: function (){
    return{
      danhSachDonViXL: [],
      dsHuongGiao: [],
      dsPhieuDaGiao: [],
      dsPhieuChuaGiao:[],
      dsThamSoMd: [],
      dsDonVi:[],

      huonggiao_id: null,
      maMay: null,
      tenTb: null,
      diaChiTb: null,
      congVanGiao: null,
      noiDungGiao: null,
      ghiChu: null,
      maKhieuNai: null,
    }
  },

  created: function () {
    this.getDsHuongGiao()
    this.getDsDonViXLTheoHuongGiao()
    this.getDsPhieuDaGiao()
    this.getDsPhieuChuaGiao()
    this.getDsThamSoMd()
    this.getDsPhieuDaGiaoTheoPhieuId()
  },

  methods: {

    getDsDonViXLTheoHuongGiao: async function(){
      this.$root.showLoading(true)
      try {
        let rs = await this.$root.context.get(
          '/web-gqkn/GiaoPhieuKhieuNai/gqkn_lay_ds_donvi_xuly?huonggiao_id=859866968'
        )
        this.danhSachDonViXL = rs.data;
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },

    getDsHuongGiao: async function(){
      this.$root.showLoading(true)
      try {
        let rs = await this.$root.context.get(
          '/web-gqkn/GiaoPhieuKhieuNai/gqkn_lay_ds_huong_giao'
        )
        this.dsHuongGiao = rs.data;
        if (this.dsHuongGiao.length > 0){
          this.huonggiao_id = this.dsHuongGiao[0].KIEULD_ID
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },

    getDsPhieuDaGiao: async function(){
      this.$root.showLoading(true)
      let data = {
        vdichvuvt_id: '12',
        vhuonggiao_id: '1',
        vkyhoadon: '12',
        vloaihd_id: '',
        vma_gd: '',
        vma_tb: '',
        vngay_giao: ''
      }
      try {
        let rs = await this.$root.context.post(
          '/web-gqkn/GiaoPhieuXulyKhieuNai/gqkn_lay_ds_khieunai_dagiao', data
        )
        this.dsPhieuDaGiao = rs.data;
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },

    getDsPhieuDaGiaoTheoPhieuId: async function(){
      this.$root.showLoading(true)
      try {
        let rs = await this.$root.context.post(
          '/web-gqkn/GiaoPhieuXulyKhieuNai/gqkn_lay_ds_khieunai_dagiao?id=554592'
        )
        this.dsPhieuDaGiao = rs.data;
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },

    getDsPhieuChuaGiao: async function(){
      this.$root.showLoading(true)
      let data = {
        vdichvuvt_id: '12',
        vhuonggiao_id: '1',
        vkyhoadon: '12',
        vloaihd_id: '',
        vma_gd: '',
        vma_tb: '',
        vngay_giao: ''
      }
      try {
        let rs = await this.$root.context.post(
          '/web-gqkn/GiaoPhieuXulyKhieuNai/gqkn_lay_ds_khieunai_chuagiao', data
        )
        this.dsPhieuChuaGiao = rs.data;
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },

    getDsThamSoMd: async function(){
      this.$root.showLoading(true)
      try {
        let rs = await this.$root.context.get(
          '/web-gqkn/GiaoPhieuKhieuNai/gqkn_lay_ds_thamso_md'
        )
        this.dsThamSoMd = rs.data;
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },

    async grid_selectedRowChanged (dataItem) {
      try {
        if (dataItem) {
          await this.getDanhBaKhTheoMaKH(dataItem.MA_TB)
          await this.getDanhSachDonVi(dataItem.DONVI_ID, this.maDichVuVienThong)
          this.maDichVuVienThong = dataItem.DICHVUVT_ID
          this.maLoaiHinhTb = dataItem.LOAITB_ID
          this.tenTb = dataItem.TEN_TB
          this.soAo = dataItem.MA_LT
          this.ngaySD = dataItem.NGAY_SD
          this.diaChiLD = dataItem.DIACHI_LD

          console.log('ahaahahahah', dataItem.DONVI_ID, this.maDichVuVienThong)
        }
      } catch (err) {
        console.log(err)
      } finally {
      }
    },

    convertDate2String (date) {
      let dd = String(date.getDate()).padStart(2, '0')
      let mm = String(date.getMonth() + 1).padStart(2, '0') //January is 0!
      let yyyy = date.getFullYear()
      return yyyy + '/' + mm + '/' + dd
    },

    clearForm () {
      this.$refs.table.grid_RecordClick(-1,null);


    },

  },
}
</script>

<style scoped>

</style>
