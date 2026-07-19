<template src="./index.template.html"></template>
<script>
import API from '../api/DeXuatGiamTruCuocAPI'

export default {
  props: {
    argument: Object
  },
  name: 'GiaoPhieuGTC',
  data() {
    return {
      parameter: {
        kieuId: 0,
        huongGiaoId: null,
        maTb: null,
        ttphId: null,
        ngaygiao: null
      },
      dsTrangThai: [],
      trangThaiId: null,
      ngayTao: null,
      ngayGiaoPhieu: new Date(),
      dsHuongGiao: [],
      huongGiaoId: null,
      dsDonViXuLy: [],
      donViXuLyId: null,
      soMayTimKiem: null,
      soMayGiaoPhieu: null,
      soCongVan: null,
      noiDungGiao: null,
      btnGiaoPhieu: {
        enabled: true
      },
      btnHuyGiao: {
        enabled: true
      },
      dsKhieuNai: [],
      kyCuocHienHanh: null,
      phieuID: null,
      maKN: null,
      nguoiXuLyId: null,
      khieunai_id: 0,
      dsnguoiXuLy: [],
      currentItem: null,
      dsPhieuSelected: [],
      pTTKNId: 16,
    }
  },
  computed: {},
  watch: {
    async huongGiaoId(value) {
      if (!value) {
        this.donViXuLyId = null
        this.dsDonViXuLy = []
        this.dsKhieuNai = []
        this.dsnguoiXuLy = []
        return
      }

      try {
        this.loading(true)
        if (value) {
          await this.layDsDonViXuLy(value)
        }
      } finally {
        this.loading(false)
      }

      if (!!this.dsDonViXuLy && this.dsDonViXuLy.length > 0) {
        this.donViXuLyId = this.dsDonViXuLy[0].ID
      }

      if (!!this.dsnguoiXuLy && this.dsnguoiXuLy.length > 0) {
        this.nguoiXuLyId = this.dsnguoiXuLy[0].NHANVIEN_ID
      }

      // if (this.load) {
      //   await this.hienThiDanhSachKhieuNai()
      // }
    }
  },
  async mounted() {
    if (!!this.argument) {
      if (this.argument.huongGiaoId != null) {
        this.parameter.huongGiaoId = this.argument.huongGiaoId
      }
      if (this.argument.ngayGiaoPhieu != null) {
        this.parameter.ngayGiaoPhieu = this.argument.ngayGiaoPhieu
      }
      if (this.argument.maKN != null) {
        this.parameter.maKN = this.argument.maKN
      }
    }

    // try {
    //   this.loading(true)
    //   await this.layDsHuongGiao()
    // } finally {
    //   this.loading(false)
    // }
    if (!!this.dsHuongGiao && this.dsHuongGiao.length > 0) {
      if (this.parameter.kieuId == 1 && this.parameter.huongGiaoId != null) {
        this.huongGiaoId = this.parameter.huongGiaoId
      } else {
        this.huongGiaoId = this.dsHuongGiao[0].huonggiao_id
      }
    }

    if (this.parameter.ngayGiaoPhieu != null) {
      this.ngayGiaoPhieu = this.ddmmyyyyToDate(this.parameter.ngayGiaoPhieu)
    } else {
      this.ngayGiaoPhieu = new Date()
    }
  },
  methods: {
    frmHoSoKhieuNai_Load() {
      this.HienThiDanhSach()
    },
    show() {
      this.$refs.frmGiaoPhieuGTC.show()
      this.frmHoSoKhieuNai_Load()
    },
    async HienThiDanhSach() {
      this.loading(true)
      try {
        let ds = []
        let params = {
          pTTKNId: this.pTTKNId,
          pTTPHId: 0,
        }
        const rs = await API.getDSKhieuNaiTheoTrangThaiv2(this.axios, params)
        ds = rs.data.data
        if (ds.length > 0) {
          this.dsKhieuNai = ds
        } else {
          this.dsKhieuNai = null
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi: ' + err.message)
      }
      this.loading(false)
    },
    async rowSelected() {
      try {
        if (this.dsKhieuNai.length < 0) {
          this.btnGiaoPhieu.enabled = true
          this.btnHuyGiao.enabled = true
          return
        }
        let selected = this.$refs.grdDSPhieu.getSelectedRecords()
        this.dsPhieuSelected = selected
        if (selected.length > 0) {
          this.currentItem = selected[0]
          this.khieunai_id = this.currentItem.KHIEUNAI_ID
          this.phieuID = this.currentItem.PHIEUKN_ID
          this.maKN = this.currentItem.MA_KN
          this.soMayTimKiem = this.currentItem.MA_TB
          this.ngayTao = this.currentItem.NGAY_TN
          // this.huongGiaoId = this.currentItem.HUONGGIAO_ID
          this.btnGiaoPhieu.enabled = this.currentItem.TTPH_ID === 1 ? true : false // 1 Tạo mới
          this.btnHuyGiao.enabled = this.currentItem.TTPH_ID === 4 ? true : false // 4 Đã giao
        }
        let data = {
          pKhieuNaiId: this.khieunai_id,
          pPhieuKNId: this.phieuID
        }
        await this.layDsHuongGiao(data)
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },
    async onClickBtnGiaoPhieu() {
      try {
        if (this.currentItem == null) {
          this.$toast.error('Bạn chưa chọn phiếu đề giao!')
          return
        }
        if (this.currentItem.KHIEUNAI_ID == '' || this.currentItem.KHIEUNAI_ID == null) {
          this.$toast.error('Không tìm thấy thông tin khiếu nại!')
          return
        }
        if (this.currentItem.PHIEUKN_ID == '' || this.currentItem.PHIEUKN_ID == null) {
          this.$toast.error('Không tìm thấy phiếu khiếu nại!')
          return
        }

        if (this.donViXuLyId == '' || this.donViXuLyId == null) {
          this.$toast.error('Bạn chưa chọn đơn vị xử lý!')
          return
        }

        if (this.nguoiXuLyId == '' || this.nguoiXuLyId == null) {
          this.$toast.error('Bạn chưa chọn người xử lý!')
          return
        }

        var isBoxConfirm = false
        await this.$bvModal
          .msgBoxConfirm('Bạn có chắc chắn muốn giao phiếu không!', {
            title: 'Thông báo',
            centered: true,
            size: 'md',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then((value) => {
            isBoxConfirm = value
          })
        if (!isBoxConfirm) {
          return
        }

        let ketQua = await this.giaoPhieuGiamTruCuoc({
          pKhieuNaiId: this.currentItem.KHIEUNAI_ID,
          pPhieuKNId: this.currentItem.PHIEUKN_ID,
          pTTKNId: this.TTKN_ID,
          pHuongGiaoId: this.huongGiaoId,
          pDonViNhanId: Number(this.donViXuLyId),
          pNhanVienNhanId: this.nguoiXuLyId
        })
        console.log(' ketQua', ketQua)
        if (ketQua.error_code == 'BSS-00000000') {
          this.$toast.success('Giao phiếu thành công!')
          await this.HienThiDanhSach()
        } else this.$toast.error(ketQua.message)
      } catch (e) {
        this.$toast.error(e.message)
      } finally {
        this.loading(false)
      }
    },
    async onClickBtnHuyGiao() {
      try {
        if (this.currentItem == null) {
          this.$toast.error('Bạn chưa chọn phiếu đề hủy giao!')
          return
        }
        if (this.currentItem.KHIEUNAI_ID == '' || this.currentItem.KHIEUNAI_ID == null) {
          this.$toast.error('Không tìm thấy thông tin khiếu nại!')
          return
        }
        if (this.currentItem.PHIEUKN_ID == '' || this.currentItem.PHIEUKN_ID == null) {
          this.$toast.error('Không tìm thấy phiếu khiếu nại!')
          return
        }
        var isBoxConfirm = false
        await this.$bvModal
          .msgBoxConfirm('Bạn có chắc chắn muốn hủy giao phiếu không!', {
            title: 'Thông báo',
            centered: true,
            size: 'md',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then((value) => {
            isBoxConfirm = value
          })
        if (!isBoxConfirm) {
          return
        }

        let dsPhieu = this.dsPhieuSelected.map((item) => {
          return {
            KHIEUNAI_ID: item.KHIEUNAI_ID,
            PHIEUKN_ID: item.PHIEUKN_ID
          }
        })
        let res = await this.huyGiaoPhieuKhieuNai({ pDSPhieu: dsPhieu })
        if (res.error_code === 'BSS-00000000') {
          this.$toast.success('Hủy giao phiếu thành công!')
          await this.HienThiDanhSach()
        } else this.$toast.error(res.message)
      } catch (e) {
        this.$toast.error(e.message)
      } finally {
        this.loading(false)
      }
    },
    async huyGiaoPhieuKhieuNai(data) {
      const rs = await API.huyGiaoPhieuKhieuNai(this.axios, data)
      let result = []
      if (rs.data.data.length != 0) result = rs.data
      return result
    },
    async layDsHuongGiao(data) {
      try {
        let response = await this.$root.context.post(`/web-gqkn/dx-giamtru-cuoc/huongiao-tieptheo`, data)
        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.dsHuongGiao = response.data
          this.huongGiaoId = this.dsHuongGiao[0].HUONGGIAO_ID
          this.TTKN_ID = this.dsHuongGiao[0].TTKN_ID
          return
        }

        throw { response: { data: response } }
      } catch (err) {
        this.dsHuongGiao = []
      }
    },
    async layDsDonViXuLy(huongGiaoId) {
      try {
        let response = await this.$root.context.get(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_lay_ds_donvi_xuly?huonggiao_id=${huongGiaoId}`
        )

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.dsDonViXuLy = response.data
          await this.layDsNguoiXuLy(this.dsDonViXuLy[0].ID)
          return
        }

        throw { response: { data: response } }
      } catch (err) {
        this.dsDonViXuLy = []
      }
    },
    async layDsNguoiXuLy(donViID) {
      try {
        let response = await this.$root.context.get(`/web-gqkn/dx-giamtru-cuoc/getDsNhanVienDonVi?pDonViId=${donViID}`)
        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.dsnguoiXuLy = response.data
          return
        }

        throw { response: { data: response } }
      } catch (err) {
        this.dsnguoiXuLy = []
      }
    },
    onChangeDV: async function(donviID) {
      try {
        if (donviID) {
          await this.layDsNguoiXuLy(donviID)
        } else {
          this.dsnguoiXuLy = []
          this.nguoiXuLyId = null
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async giaoPhieuGiamTruCuoc(data) {
      const rs = await API.giaoPhieuGiamTruCuoc(this.axios, data)
      let result = []
      if (rs.data.data.length != 0) result = rs.data
      return result
    },
    getDateElements(date) {
      date = new Date(date)

      let day = date.getDate()
      day = day > 9 ? day : '0' + day

      let month = date.getMonth() + 1
      month = month > 9 ? month : '0' + month

      let hour = date.getHours()
      hour = hour > 9 ? hour : '0' + hour

      let min = date.getMinutes()
      min = min > 9 ? min : '0' + min

      let second = date.getSeconds()
      second = second > 9 ? second : '0' + second

      return {
        y: date.getFullYear(),
        M: month,
        d: day,
        h: hour,
        m: min,
        s: second
      }
    },
    yyyymmdd(date) {
      if (!date) {
        return ''
      }

      date = this.getDateElements(date)
      return `${date.y}/${date.M}/${date.d}`
    },
    ddmmyyyy(date) {
      if (!date) {
        return ''
      }

      date = this.getDateElements(date)
      return `${date.d}/${date.M}/${date.y}`
    },
    ddmmyyyyhhmi(date) {
      if (!date) {
        return ''
      }

      date = this.getDateElements(date)
      return `${date.d}/${date.M}/${date.y} ${date.h}:${date.m}`
    },
    ddmmyyyyhhmiss(date) {
      if (!date) {
        return ''
      }

      date = this.getDateElements(date)
      return `${date.d}/${date.M}/${date.y} ${date.h}:${date.m}:${date.s}`
    },
    ddmmyyyyToDate(dateStr) {
      if (!dateStr) {
        return null
      }

      let dateElement = dateStr.split('/')
      let day = parseInt(dateStr[0])
      let month = parseInt(dateStr[1]) - 1
      let yyyy = parseInt(dateStr[2])
      return new Date(yyyy, month, day)
    },
    onclickTab: function(data) {

    },
    dialogClose() {
      console.log('dialogClose');
      this.$emit('dialogCloseAfter')
    }
  }
}
</script>
<style>
.giaophieu-khieunai .select2-container .select2-selection--single .select2-selection__rendered {
  white-space: initial;
}

.giaophieu-khieunai .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex='1'],
.giaophieu-khieunai .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex='1'] > div {
  padding: 0 !important;
}
.e-grid td.e-cellselectionbackground {
  background-color: inherit;
}
.value .select2-container--default .select2-selection--single {
  overflow: hidden;
}
</style>
