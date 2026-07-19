<template src="./template.html"></template>
<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import VueDateExt from '@/modules/contract/setup/DeclareLandline/components/VueDateExt'
export default{
  components: {
    breadcrumb,
    VueDateExt
  },
  data() {
    return {
      searchOption: 1,
      dsThugon: [],
      //mock data for test
      dsCaiTao: [
        {id: 'ct001',ten_ct: 'Record 1',hinh_thuc: 'Hình thức 1', trang_thai: 'Đang thực hiện', nguoi_dk: 'Nguyễn Văn A', ngay_dk: '22-10-2022', ngay_batdau: '2-11-2022', ngay_ketthuc: '', soluong_thuebao: 2, ma_hs:' HSHP001'},
        {id: 'ct002',ten_ct: 'Record 2',hinh_thuc: 'Hình thức 2', trang_thai: 'Tạm ngưng', nguoi_dk: 'Nguyễn Văn A', ngay_dk: '22-10-2022', ngay_batdau: '2-11-2022', ngay_ketthuc: '', soluong_thuebao: 2, ma_hs:' HSHP001'},
        {id: 'ct003',ten_ct: 'Record 3',hinh_thuc: 'Hình thức 2', trang_thai: 'Đang thực hiện', nguoi_dk: 'Nguyễn Văn A', ngay_dk: '22-10-2022', ngay_batdau: '2-11-2022', ngay_ketthuc: '', soluong_thuebao: 2, ma_hs:' HSHP001'},
      ],
    }
  },
  methods: {
    searchOp1() {
      this.searchOption = 1
    },
    searchOp2() {
      this.searchOption = 2
    },
    searchOp3() {
      this.searchOption = 3
    },
  }
}
</script>

<style src="./style.scss" scoped></style>