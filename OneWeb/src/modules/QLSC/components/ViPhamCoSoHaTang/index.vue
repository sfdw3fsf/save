<template src="./index.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import {actionName, statePropertyName} from '../../store/ViPhamCoSoHaTang'
import {mapActions, mapState} from 'vuex'
import downloadexcel from 'vue-json-excel'
import BssLocalPagination from '../../../../components/BssLocalPagination'
import XLSX from 'xlsx'

export default {
  components: {BssLocalPagination, downloadexcel, breadcrumb},
  name: 'ViPhamCoSoHaTang',
  computed: {
    ...mapState('qlsc/viPhamCsht', statePropertyName)
  },
  destroyed () {
    this.clearState()
  },
  methods: {
    ...mapActions('qlsc/viPhamCsht', actionName),
    resetForm () {
      if (this.dvvp && this.dvvp.length > 0) {
        this.cboDonViViPham = -1
      }

      if (this.dvxl && this.dvxl.length > 0) {
        this.cboDonViXL = -1
      }

      if (this.kqxl && this.kqxl.length > 0) {
        this.cboQetQuaXL = -1
      }
      this.isCreateNew = true,
      this.bienPhapXl = null,
        this.ngayViPham = null,
        this.ghiChu = null,
        this.mayCn = null,
        this.ngayCn = null,
        this.ngayXl = null,
        this.nguoiCn = null,
        this.noiDungViPham = null
      this.setDisableBtn(false, false, false, false)
    },
    cancelForm () {
      this.resetForm()
    },
    themMoi: async function () {
      let dataIns = {
        kqXuLyId: this.cboQetQuaXL,
        donVi_Vp_Id: this.cboDonViViPham,
        donViXlId: this.cboDonViXL,
        bienPhapXl: this.bienPhapXl,
        ngayVp: this.convertDate2String(this.ngayViPham),
        ghiChu: this.ghiChu,
        mayCn: this.mayCn,
        ngayCn: this.ngayCn,
        ngayXl: this.convertDate2String(this.ngayXl),
        nguoiCn: this.nguoiCn,
        noiDungViPham: this.noiDungViPham
      }
      if (!dataIns.donVi_Vp_Id) {
        this.$toast.error('Trường đơn vị vi phạm bắt buộc nhập dữ liệu.')
        return
      }
      if (!dataIns.donViXlId) {
        this.$toast.error('Trường đơn vị xử lý bắt buộc nhập dữ liệu.')
        return
      }
      if (!dataIns.kqXuLyId) {
        this.$toast.error('Trường kết quả xử lý bắt buộc nhập dữ liệu.')
        return
      }
      if (!dataIns.noiDungViPham) {
        this.$toast.error('Trường nội dung vi phạm bắt buộc nhập dữ liệu.')
        return
      }
      if (!dataIns.ngayVp) {
        this.$toast.error('Trường ngày vi phạm bắt buộc nhập dữ liệu.')
        return
      }
      if (!dataIns.ngayXl) {
        this.$toast.error('Trường ngày xử lý bắt buộc nhập dữ liệu.')
        return
      }
      if (this.isCreateNew) {
        try {
          let response = await this.themViPham(dataIns)
          console.log('responseThem', response.data.error_code)
          if (response.data.error_code == 'BSS-00000000') {
            this.$toast.success('Thêm mới thành công!')
            await this.layDsViPhamCSHT()
          } else {
            this.$toast.error(response.message_detail)
          }
          //   this.$toast.success('Thêm mới thành công!')
        } catch (err) {
          if (err.response) {
            let message = err.response.data.message
            let message_detail = err.response.message_detail
            if (message_detail !== null) {
              this.$toast.error(message_detail)
            } else if (message !== null) {
              this.$toast.error(message)
            }
          }
        } finally {

        }

      } else {
        await this.capNhatViPham({...dataIns, vpcsht_id: this.viPhamId})
        this.$toast.success('Cập nhật thành công!')
        await this.layDsViPhamCSHT()
      }
    },
    xoaViPhamAA: async function () {
      if (!this.isDisableBtnXoa) {
        this.modalXoa = !this.modalXoa
      }
    },
    async xacNhanXoa() {
      try {
        let rs = await this.xoaViPham(this.viPhamId)
        this.loading(true)
          // .then((res) => {
          //   console.log(res)
          //   if (res.error_code === 'BSS-00000000') {
          //     this.$toast.success('Xóa thành công!')
          //     this.resetForm()
          //     this.layDsViPhamCSHT()
          //   } else {
          //     this.$toast.error(res.error)
          //     this.layDsViPhamCSHT()
          //   }
          //   this.$root.loading(false)
          // })
        console.log('responseXoa', rs)
        this.setDisableBtn(false, true, true, true)
        if (rs[0].RESULT == 1) {
          this.$toast.success('Xóa thành công')
          await this.layDsViPhamCSHT()
        } else {
          this.$toast.error('Xóa thất bại!')
        }
      } catch (err) {
        console.log('responseXoaErr', err)
        let message = err.response.data.message
        let message_detail = err.response.data.message_detail
        if (message_detail !== null) {
          this.$toast.error(message_detail)
        } else if (message !== null) {
          this.$toast.error(message)
        }
      } finally {
        this.loading(false)
      }
    },
    xuatFile: function () {
      if (this.dataExcel != null) {
        let data = XLSX.utils.json_to_sheet(this.dataExcel);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, "DANHSACH_VIPHAM_CSHT"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "export.xlsx");
      } else this.$toast.error("Không có dữ liệu!");
    },
    convertDate2String (date) {
      let dd = String(date.getDate()).padStart(2, '0')
      let mm = String(date.getMonth() + 1).padStart(2, '0') //January is 0!
      let yyyy = date.getFullYear()
      return yyyy + '/' + mm + '/' + dd
    },
    setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
      this.isDisableBtnNhapMoi = isNhapmoi
      this.isDisableBtnGhiLai = isGhilai
      this.isDisableBtnHuy = isHuy
      this.isDisableBtnXoa = isXoa
    },
    selectViPham: function (val, index) {
      this.isCreateNew = false
      this.viPhamId = val.VPCSHT_ID
      this.cboDonViViPham = val.DOITAC_ID
      this.cboDonViXL = val.DONVI_XL_ID
      this.cboQetQuaXL = val.KQXL_ID
      this.bienPhapXl = val.BIENPHAP_XL
      this.ngayViPham = val.NGAY_VP
      this.ghiChu = val.GHICHU
      this.mayCn = val.MAY_CN
      this.ngayXl = val.NGAY_XL
      this.nguoiCn = val.NGUOI_CN
      this.noiDungViPham = val.NOIDUNG_VIPHAM
      this.setDisableBtn(false, false, false, false)
    }
  },
  created () {
    this.clearState()
    this.createApiManager()
  },
  async mounted () {
    try {
      this.loading(true)
      await this.layDsDonViViPham()
      await this.layDsDonViXl()
      await this.layDsKetQuaXl()
      let dataDown = await this.layDsViPhamCSHT()
      console.log('dataDown', dataDown)
      this.dataExcel = Object.values(dataDown.data)
      await this.layDsDonViXl()
      await this.layDsKetQuaXl()
      this.resetForm()
    } catch (err) {
      console.log(err)
    } finally {
      this.loading(false)
    }
  },
  data () {
    return {
      header: {
        title: 'Quản lý vi phạm cơ sở hạ tầng',
        list: [
          {
            name: 'Lập hợp đồng',
            link: {name: 'Ui.cards'}
          },
          {
            name: 'Lắp đặt mới',
            link: {name: 'Ui.buttons'}
          }
        ]
      },
      isDisableBtnNhapMoi: false,
      isDisableBtnGhiLai: false,
      isDisableBtnHuy: false,
      isDisableBtnXoa: false,
      dataExcel: null,
      isCreateNew: true,
      viPhamId: null,
      cboDonViViPham: 0,
      cboDonViXL: 0,
      cboQetQuaXL: 0,
      bienPhapXl: null,
      ngayViPham: null,
      ghiChu: null,
      kqXuLy: null,
      mayCn: null,
      ngayCn: null,
      ngayXl: null,
      nguoiCn: null,
      noiDungViPham: null,
      modalXoa: false,
      denNgay: null,
      tuNgay: null
    }
  }
}
</script>
