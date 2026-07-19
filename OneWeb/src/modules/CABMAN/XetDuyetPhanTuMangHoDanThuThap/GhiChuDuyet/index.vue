<template src="./index.html"></template>
<script>
export default {
  //Form ghi chú duyệt dùng chung cho cả kết cuối và hộ dân
  name: "GhiChuDuyet",
  props: {
    id: String,
    checkedKC: Array,
    isDuyet:Boolean,
  },
  data: function () {
    return {
      key: 1,
      isShown: false,
      ghichuDuyet:"",
    }
  },
  methods: {
    onHiddenModal() {
      this.isShown = false
      this.key++
    },
    async onShownModal() {
      this.isShown = true
      if (this.isDuyet) this.ghichuDuyet = "Đạt yêu cầu"
      else this.ghichuDuyet = "Không đạt yêu cầu"
    },
    async btnXacNhan() {
      if (this.ghichuDuyet.trim().length == 0){
        this.$root.toastError("Ghi nhập ghi chú duyệt!\r\nLý do không đạt?")
        return
      }
      if (this.ghichuDuyet.length > 300) {
        this.$root.toastError("Ghi chú vượt quá độ dài cho phép.")
        return
      }
      let ghichu = this.convertDate(new Date()) + ' | ' + this.$auth.getUserName() + ': ' + this.ghichuDuyet
      let loaiDoiTuong = Array.from(new Set(this.checkedKC.map(item => item.LOAI_DT)))
      if (loaiDoiTuong.length > 1){
        this.$toast.warning("Lỗi Duyệt khi chọn không cùng đối tượng")
        return
      }
      let object = new Object()
      let dsKetCuoiID = loaiDoiTuong[0] !== 2 ? this.checkedKC.map(item => item.KETCUOI_ID).toString() : this.checkedKC.map(item => item.SDKH_ID).toString()
      switch (loaiDoiTuong[0]){
        case 0: // Hạ tầng mạng
          //set trang thai duyet
          object.doituong = 0
          object.id_ketcuois = dsKetCuoiID
          object.trang_thai_duyet = this.isDuyet ? 2 : 1
          object.ghichu = ghichu
          object.thu_thap_lai = 0
          break
        case 1: // Kết cuối đối thủ
          //set trang thai duyet
          object.doituong = 1
          object.id_ketcuois = dsKetCuoiID
          object.trang_thai_duyet = this.isDuyet ? 2 : 1
          object.ghichu = ghichu
          object.thu_thap_lai = 1
          break
        case 2: // Hộ dân
          //set trang thai duyet
          object.doituong = 2
          object.id_ketcuois = dsKetCuoiID
          object.trang_thai_duyet = this.isDuyet ? 2 : 1
          object.ghichu = ghichu
          object.thu_thap_lai = 1
          break
      }
      // update trang thai
      try {
        this.loading(true)
        await this.$root.context.post("/web-cabman/phantu_mang/capnhat_ttduyet", object)
        .then(res =>{
          if (res.error === "200" && res.error_code === "BSS-00000000" && res.data === true){
            this.$toast.success("Cập nhật thành công " + this.checkedKC.length + " đối tượng")
            this.$emit('reload')
          }
        })
      }catch (e){ this.$toast.error("Có lỗi xảy ra trong quá trình cập nhật đối tượng")}
      finally {
        this.loading(false)
        this.$bvModal.hide(this.id)
      }
    },
    convertDate:function(now){
      // return new  Date(date).toLocaleDateString("en-US", { year: 'numeric' })+ "-"+
      //   new Date(date).toLocaleDateString("en-US", { month: 'numeric' })+
      //   "-" + new  Date(date).toLocaleDateString("en-US", { day: 'numeric' })
      let date = new Date(now)
      let dd = String(date.getDate()).padStart(2, '0')
      let mm = String(date.getMonth() + 1).padStart(2, '0') //January is 0!
      let yyyy = date.getFullYear()
      let h = this.addZero(date.getHours())
      let m = this.addZero(date.getMinutes())
      let s = this.addZero(date.getSeconds());
      return  dd + '/' + mm + '/' + yyyy + ' ' + h + ':' + m + ':' + s
    },
    addZero(i) {
      if (i < 10) {
        i = "0" + i;
      }
      return i;
    }
  }
}
</script>
