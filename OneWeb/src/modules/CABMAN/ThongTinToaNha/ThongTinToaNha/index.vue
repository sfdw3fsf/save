<template src="./index.html"></template>


<script>
export default {
  name: "ThongTinToaNha",
  props: {
    toaNhaId: Number,
    id: String,
  },
  data: function () {
    return {
      // key: 1,
      isShown: false,
      thongTinToaNha: {},

      dsDuAn:[],
      dsQuan:[],
      dsLoaiToaNha:[],
      dsDoiTuongDuAn:[],
      dsQuanLy:[],
      dsDonViTiepThi:[],
      dsQuyMo:[],
      dsLoaiCap:[],
      dsPCDT:[],
      dsSanSang:[],
      dsTinhTrang:[],
      dsPhamViDauTuMang:[]
    }
  },
  destroyed() {
    // this.key++
    this.clearData()
  },
  methods: {
    onHiddenModal() {
      this.isShown = false
      // this.key++
      this.clearData()
    },
    async onShownModal() {
      this.isShown = true
      try {
        this.loading(true)
        await this.getThongTinToaNha(this.toaNhaId)
        await this.initForm()
      }catch (e){

      }finally {
        this.loading(false)
      }
    },
    async getThongTinToaNha(toanha_id){
      await this.$root.context.get(`/web-cabman/mang-truyen-dan/thongtin_toanha/`+toanha_id)
        .then(rs =>{
          this.thongTinToaNha = rs.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin tòa nhà"))
    },
    async initForm(){
      try {
        await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-duan')
        .then(rs =>{
          this.dsDuAn = rs.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách dự án"))

        await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-quan')
        .then(rs =>{
          this.dsQuan = rs.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách quận"))

        await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-loai-toanha')
        .then(rs =>{
          this.dsLoaiToaNha = rs.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách loại tòa nhà"))

        await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-doituong-duan')
        .then(rs =>{
          this.dsDoiTuongDuAn = rs.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách đối tượng dự án"))

        await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-quanly-donvi-tiepthi', {loaidvId:3})
        .then(rs =>{
          this.dsQuanLy = rs.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách quản lý"))

        await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-quanly-donvi-tiepthi',{loaidvId:2})
        .then(rs =>{
          this.dsDonViTiepThi = rs.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách đơn vị tiếp thị"))

        await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-quymo')
          .then(rs =>{
            this.dsQuyMo = rs.data
          }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách quy mô"))

        await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-loaicap')
          .then(rs =>{
            this.dsLoaiCap = rs.data
          }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách loại cáp"))

        await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-phamvi-dautu-mang')
          .then(rs =>{
            this.dsPhamViDauTuMang = rs.data
          }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách phạm vi đầu tư mạng"))

        await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-pcdt')
          .then(rs =>{
            this.dsPCDT = rs.data
          }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách phân chia doanh thu"))

        await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-sansang')
          .then(rs =>{
            this.dsSanSang = rs.data
          }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách sẵn sàng"))

        await this.$root.context.get('/web-cabman/mang-truyen-dan/lay-ds-tinhtrang')
          .then(rs =>{
            this.dsTinhTrang = rs.data
          }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách tình trạng"))

      }catch (e){
        console.log(e)
      }
    },
    clearData(){
      this.thongTinToaNha = {}
      this.dsDuAn = []
      this.dsQuan = []
      this.dsLoaiToaNha = []
      this.dsDoiTuongDuAn = []
      this.dsQuanLy = []
      this.dsDonViTiepThi = []
      this.dsQuyMo = []
      this.dsLoaiCap = []
      this.dsPCDT = []
      this.dsSanSang = []
      this.dsTinhTrang = []
      this.dsPhamViDauTuMang = []
    }
  },
}
</script>

<style>
.thongtin-toanha .grid td.text-left.maduan-color {
  color:blue!important;
}
.thongtin-toanha .grid td.tenduan-bold {
  font-weight: bold;
}
</style>
