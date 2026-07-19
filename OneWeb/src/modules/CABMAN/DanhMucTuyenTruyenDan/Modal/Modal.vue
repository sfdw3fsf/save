<template src="./Modal.html"></template>

<script>
import Vue from 'vue';
import { ColorPickerPlugin } from '@syncfusion/ej2-vue-inputs';

Vue.use(ColorPickerPlugin);

export default {
  name: 'Modal',
  props: ['modalId'],

  methods: {
    onChangeTocboLoaiDanhMuc: async function (id) {
      this.$root.showLoading(true)
      try {
        await this.napLoaiDm(id)
        // this.ldt = napLoaiDt.data
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    napLoaiDm: async function (id) {
      this.$root.showLoading(true)
      // this.ldt = null
      try {
        var napLoaiDmTd = await this.$root.context.get(`/web-cabman/danhmuc/tuyentd/napLoaiDmTd?loaiDm=${id}`)
        console.log('napLoaiDmTd', napLoaiDmTd)
        console.log('Loại DM', this.cboLoaiDanhMuc)
        this.ldt = napLoaiDmTd.data
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    napLoaiDt: async function () {
      this.$root.showLoading(true)
      this.ldt = null
      try {
        var napLoaiDt = await this.$root.context.get(`/web-cabman/danhmuc/tuyentd/napLoaiDoiTuong`)
        this.ldt = napLoaiDt.data
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    onclickTuyenTd: function (row) {
      // this.isCreateNew = false*
      this.maTuyen = row.LOAITTD_ID
      this.tenTuyen = row.LOAI_TUYEN
      this.heThong = row.HETHONG
    },
    onclickLoaiCap: function (row) {
      // this.isCreateNew = false
      this.maLoaiCap = row.LOAICAP_ID
      this.tenLoaiCap = row.LOAI_CAP
      this.phanLoai = row.PHANLOAI
    },
    onclickLoaiTbi: function (row) {
      // this.isCreateNew = false
      this.loaiTbiId = row.LOAITBI_ID
      this.maLoaiTbi = row.MA_LOAITBI
      this.tenLoaTbi = row.LOAI_TBI
    },
    onclickLoaiDt: function (row) {
      // this.isCreateNew = false
      this.maLoaiDt = row.LOAIDT_ID
      this.tenLoaiDt = row.TEN_LDT
      this.tenLoaiDt = row.LOAITUYENTD_ID
      this.lenhSelect = row.LENH_SELECT
      this.truongKhoa = row.TRUONG_KHOA
      this.truongTen = row.TRUONG_TEN
      this.loaiTbiId = row.LOAITB_ID
      this.kichThuoc = row.KICHTHUOC
      this.phongChu = row.PHONGCHU
      this.coChu = row.COCHU
      this.kieuChu = row.KIEUCHU
      // this.  = MAUNEN
      // this.  = MAUCHU
      // this.  = MAUVIEN
      this.coVien = row.COVIEN
      // this.  = HINHNEN
      this.phongChuChon = row.PHONGCHU_CHON
      this.coChuChon = row.COCHU_CHON
      this.kieuChuChon = row.KIEUCHU_CHON
      // this.  = MAUNEN_CHON
      // this.  = MAUCHU_CHON
      // this.  = MAUVIEN_CHON
      this.coVienChon = row.COVIEN_CHON
      // this. = HINHNEN_CHON
    },

    themMoi: async function () {
      this.$root.showLoading(true)
      // if (isCreateNew) {
      if (this.cboLoaiDanhMuc == 0) {
        if (this.tenTuyen == null){
          this.$root.toastError('Tên tuyến không được để trống')
          this.$root.showLoading(false)
          return
        }
        if (this.cboLoaiTuyen == null){
          this.$root.toastError('Tên loại tuyến không được để trống')
          this.$root.showLoading(false)
          return
        }
        try {
          console.log('matuyen', this.maTuyen)
          var them = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/themDmTruyenTruyenDan?loaiTuyen=${this.tenTuyen}?heThong=${this.cboLoaiTuyen}`)
            .then((res) => {
              console.log('res1111', res)
              if (res.error_code === 'BSS-00000000') {
                this.$toast.success('Thêm thành công!')
                this.resetForm()
                this.napLoaiDm(0)
              } else {
                this.$toast.error(res.error)
                this.napLoaiDm(0)
              }
              this.$root.showLoading(false)
            })
          console.log('dataIns', them)
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      }
        // else if (this.cboLoaiDanhMuc == 1) {
        //   try {
        //     var them = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/xoaDanhMucTuyenTd?id=${this.maTuyen}`)
        //       .then((res) => {
        //         console.log(res)
        //         if (res.error_code === 'BSS-00000000') {
        //           this.$toast.success('Xóa thành công!')
        //           this.resetForm()
        //           this.napLoaiDm(1)
        //         } else {
        //           this.$toast.error(res.error)
        //           this.napLoaiDm(1)
        //         }
        //         this.$root.showLoading(false)
        //       })
        //     console.log('dataIns', them)
        //   } catch (error) {
        //   } finally {
        //     this.$root.showLoading(false)
        //   }
      // }
      else if (this.cboLoaiDanhMuc == 2) {
        if (this.maLoaiCap == null){
          this.$root.toastError('Tên loại cáp không được để trống')
          this.$root.showLoading(false)
          return
        }
        try {
          var them = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/themLoaiCap?loaiCap=${this.maLoaiCap}?phanLoai=1`)
            .then((res) => {
              console.log(res)
              if (res.error_code === 'BSS-00000000') {
                this.$toast.success('Thêm thành công!')
                this.resetForm()
                this.napLoaiDm(2)
              } else {
                this.$toast.error(res.error)
                this.napLoaiDm(2)
              }
              this.$root.showLoading(false)
            })
          console.log('dataIns', them)
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      }
      else if (this.cboLoaiDanhMuc == 3) {
        try {
          let dataIns = {
            maLoaiThietBi: 'loại tb test',
            loaiThietBi: 'loại tb test',
            nhomThietBiId: 1
          }
          var them = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/themLoaiThietBi`, dataIns)
            .then((res) => {
              console.log(res)
              if (res.error_code === 'BSS-00000000') {
                this.$toast.success('Thêm mới thành công!')
                this.resetForm()
                this.napLoaiDm(3)
              } else {
                this.$toast.error(res.error)
                this.napLoaiDm(3)
              }
              this.$root.showLoading(false)
            })
          console.log('dataIns', them)
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      }
      else {
        if (this.maLoaiCap == null){
          this.$root.toastError('Tên loại cáp không được để trống')
          this.$root.showLoading(false)
          return
        }
        try {
          let dataIns = {
            coChu: this.coChu,
            coChuChon: this.coChuChon,
            coVien: this.coVien,
            coVienChon: this.coVienChon,
            hinhNen: null,
            hinhNenChon: null,
            kichThuoc: this.kichThuoc,
            kieuChu: this.kieuChu,
            kieuChuChon: this.kieuChuChon,
            lenhSelect: this.lenhSelect,
            loaiTbId: this.loaiTbiId,
            loaiTuyenTdId: 1,
            mauChu: this.mauChu,
            mauChuChon: this.mauChuChon,
            mauNen: this.mauNen,
            mauNenChon: this.mauNenChon,
            mauVien: this.mauVien,
            mauVienChon: this.mauVienChon,
            phongChu: this.phongChu,
            phongChuChon: this.phongChuChon,
            tenLoaiDt: this.tenLoaiDt,
            truongKhoa: this.truongKhoa,
            truongTen: this.truongTen
          }
          console.log(dataIns)
          var them = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/themLoaiDoiTuong`, dataIns)
            .then((res) => {
              console.log(res)
              if (res.error_code === 'BSS-00000000') {
                this.$toast.success('Thêm mới thành công!')
                this.resetForm()
                this.napLoaiDm(4)
              } else {
                this.$toast.error(res.error)
                this.napLoaiDm(4)
              }
              this.$root.showLoading(false)
            })
          console.log('dataIns', them)
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      }
      // }
      // else {
      //   if (this.cboLoaiDanhMuc == 0) {
      //     try {
      //       console.log('matuyen', this.maTuyen)
      //       var capNhat = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/capNhatLoaiTuyenTruyenDan?id=${this.maTuyen}?loaiTuyen=${this.tenTuyen}?heThong=${this.heThong}`)
      //         .then((res) => {
      //           console.log(res)
      //           if (res.error_code === 'BSS-00000000') {
      //             this.$toast.success('Thêm thành công!')
      //             this.resetForm()
      //             this.napLoaiDm(0)
      //           } else {
      //             this.$toast.error(res.error)
      //             this.napLoaiDm(0)
      //           }
      //           this.$root.showLoading(false)
      //         })
      //       console.log('dataIns', capNhat)
      //     } catch (error) {
      //     } finally {
      //       this.$root.showLoading(false)
      //     }
      //   } else if (this.cboLoaiDanhMuc == 1) {
      //     try {
      //       var capNhat = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/xoaDanhMucTuyenTd?id=${this.maTuyen}`)
      //         .then((res) => {
      //           console.log(res)
      //           if (res.error_code === 'BSS-00000000') {
      //             this.$toast.success('Xóa thành công!')
      //             this.resetForm()
      //             this.napLoaiDm(1)
      //           } else {
      //             this.$toast.error(res.error)
      //             this.napLoaiDm(1)
      //           }
      //           this.$root.showLoading(false)
      //         })
      //       console.log('dataIns', capNhat)
      //     } catch (error) {
      //     } finally {
      //       this.$root.showLoading(false)
      //     }
      //   } else if (this.cboLoaiDanhMuc == 2) {
      //     try {
      //       var capNhat = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/capNhatLoaiCap?id=${this.maLoaiCap}?loaiCap=${this.tenLoaiCap}?phanLoai=${this.phanLoai}`)
      //         .then((res) => {
      //           console.log(res)
      //           if (res.error_code === 'BSS-00000000') {
      //             this.$toast.success('Xóa thành công!')
      //             this.resetForm()
      //             this.napLoaiDm(2)
      //           } else {
      //             this.$toast.error(res.error)
      //             this.napLoaiDm(2)
      //           }
      //           this.$root.showLoading(false)
      //         })
      //       console.log('dataIns', capNhat)
      //     } catch (error) {
      //     } finally {
      //       this.$root.showLoading(false)
      //     }
      //   } else if (this.cboLoaiDanhMuc == 3) {
      //     try {
      //       let dataUpd = {
      //         kieuDslam: this.kieuDslam,
      //         kieuDslamId: this.kieuDslamId
      //       }
      //       var capNhat = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/xoaLoaiThietBi?id=${this.loaiTbiId}`)
      //         .then((res) => {
      //           console.log(res)
      //           if (res.error_code === 'BSS-00000000') {
      //             this.$toast.success('Xóa thành công!')
      //             this.resetForm()
      //             this.napLoaiDm(3)
      //           } else {
      //             this.$toast.error(res.error)
      //             this.napLoaiDm(3)
      //           }
      //           this.$root.showLoading(false)
      //         })
      //       console.log('dataIns', capNhat)
      //     } catch (error) {
      //     } finally {
      //       this.$root.showLoading(false)
      //     }
      //   } else {
      //     try {
      //       var capNhat = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/xoaLoaiDoiTuong?id=${this.maLoaiDt}`)
      //         .then((res) => {
      //           console.log(res)
      //           if (res.error_code === 'BSS-00000000') {
      //             this.$toast.success('Xóa thành công!')
      //             this.resetForm()
      //             this.napLoaiDm(4)
      //           } else {
      //             this.$toast.error(res.error)
      //             this.napLoaiDm(4)
      //           }
      //           this.$root.showLoading(false)
      //         })
      //       console.log('dataIns', capNhat)
      //     } catch (error) {
      //     } finally {
      //       this.$root.showLoading(false)
      //     }
      //   }
      // }
    },

    xoa: function () {
      // if (!this.isDisableBtnXoa) {
      this.modalXoa = !this.modalXoa
      // }
    },

    xacNhanXoa: async function () {
      this.$root.showLoading(true)
      if (this.cboLoaiDanhMuc == 0) {
        try {
          console.log('matuyen', this.maTuyen)
          var xoa = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/xoaDanhMucTuyenTd?id=${this.maTuyen}`)
            .then((res) => {
              console.log(res)
              if (res.error_code === 'BSS-00000000') {
                this.$toast.success('Xóa thành công!')
                this.resetForm()
                this.napLoaiDm(0)
              } else {
                this.$toast.error(res.error)
                this.napLoaiDm(0)
              }
              this.$root.showLoading(false)
            })
          console.log('dataIns', xoa)
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      }
        // else if (this.cboLoaiDanhMuc == 1) {
        //   try {
        //     var xoa = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/xoaDanhMucTuyenTd?id=${this.maTuyen}`)
        //       .then((res) => {
        //         console.log(res)
        //         if (res.error_code === 'BSS-00000000') {
        //           this.$toast.success('Xóa thành công!')
        //           this.resetForm()
        //           this.napLoaiDm(1)
        //         } else {
        //           this.$toast.error(res.error)
        //           this.napLoaiDm(1)
        //         }
        //         this.$root.showLoading(false)
        //       })
        //     console.log('dataIns', xoa)
        //   } catch (error) {
        //   } finally {
        //     this.$root.showLoading(false)
        //   }
      // }
      else if (this.cboLoaiDanhMuc == 2) {
        try {
          var xoa = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/xoaLoaiCap?id=${this.maLoaiCap}`)
            .then((res) => {
              console.log(res)
              if (res.error_code === 'BSS-00000000') {
                this.$toast.success('Xóa thành công!')
                this.resetForm()
                this.napLoaiDm(2)
              } else {
                this.$toast.error(res.error)
                this.napLoaiDm(2)
              }
              this.$root.showLoading(false)
            })
          console.log('dataIns', xoa)
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      } else if (this.cboLoaiDanhMuc == 3) {
        try {
          var xoa = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/xoaLoaiThietBi?id=${this.loaiTbiId}`)
            .then((res) => {
              console.log(res)
              if (res.error_code === 'BSS-00000000') {
                this.$toast.success('Xóa thành công!')
                this.resetForm()
                this.napLoaiDm(3)
              } else {
                this.$toast.error(res.error)
                this.napLoaiDm(3)
              }
              this.$root.showLoading(false)
            })
          console.log('dataIns', xoa)
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        try {
          var xoa = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/xoaLoaiDoiTuong?id=${this.maLoaiDt}`)
            .then((res) => {
              console.log(res)
              if (res.error_code === 'BSS-00000000') {
                this.$toast.success('Xóa thành công!')
                this.resetForm()
                this.napLoaiDm(4)
              } else {
                this.$toast.error(res.error)
                this.napLoaiDm(4)
              }
              this.$root.showLoading(false)
            })
          console.log('dataIns', xoa)
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    resetForm () {
        this.isCreateNew = true,
        this.isDisableBtnNhapMoi = false,
        this.isDisableBtnGhiLai = true,
        this.isDisableBtnHuy = true,
        this.isDisableBtnXoa = true,
        this.maLoaiDt = null,
        this.tenLoaiDt = null,
        this.heThong = null,
        this.loaiCapId = null,
        this.loaiTbiId = null,
        this.maLoaiTbi = null,
        this.loaiDtId = null,
        this.loaiDt = null,
        this.maTuyen = null,
        this.tenTuyen = null,
        this.maLoaiTuyen = null,
        this.tenLoaiTuyen = null,
        this.maLoaiCap = null,
        this.tenLoaiCap = null,
        this.tenLoaTbi = null,
        this.ghiChu = null,
        this.lenhSelect = null,
        this.truongKhoa = null,
        this.truongTen = null,
        this.kichThuoc = null,
        this.phongChu = null,
        this.coChu = 0,
        this.kieuChu = null,
        this.coVien = 0,
        this.phongChuChon = null,
        this.coChuChon = 0,
        this.kieuChuChon = null,
        this.coVienChon = 0,
        this.phanLoai = null
      this.setDisableBtn(false, false, true, true)
    },

    huy(){
      this.resetForm();
    },

    setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
      this.isDisableBtnNhapMoi = isNhapmoi
      this.isDisableBtnGhiLai = isGhilai
      this.isDisableBtnHuy = isHuy
      this.isDisableBtnXoa = isXoa
    }
  },

  created: function () {
    this.onChangeTocboLoaiDanhMuc(0)
    this.isCreateNew = true
  },

  data: function () {
    return {
      ldt: null,
      cboLoaiDanhMuc: 0,
      cboLoaiTuyen: 0,
      isCreateNew: true,
      isDisableBtnNhapMoi: false,
      isDisableBtnGhiLai: true,
      isDisableBtnHuy: true,
      isDisableBtnXoa: true,
      maLoaiDt: 0,
      tenLoaiDt: null,
      heThong: null,
      loaiCapId: 0,
      loaiTbiId: 0,
      maLoaiTbi: 0,
      loaiDtId: null,
      loaiDt: null,
      maTuyen: null,
      tenTuyen: null,
      maLoaiTuyen: 0,
      tenLoaiTuyen: null,
      maLoaiCap: 0,
      tenLoaiCap: null,
      tenLoaTbi: null,
      ghiChu: null,
      lenhSelect: null,
      truongKhoa: null,
      truongTen: null,
      kichThuoc: 0,
      phongChu: null,
      coChu: 0,
      kieuChu: null,
      coVien: 0,
      phongChuChon: null,
      coChuChon: 0,
      kieuChuChon: null,
      coVienChon: 0,
      phanLoai: null,
      modalXoa: false,
      category: {
        dinhDanhLDM: 0,
        dinhDanhLC: 0,
        dinhdanhLTTD: 0,
        dinhDanhLTB: 0,
        dinhDanhLTBK: 0,
        dinhDanhLDT: 0,
        duLieu: 0,
        chuDeN: 0,
        chuDeS: 0
      },
    }
  },

  computed: {},

  destroyed () {
  },
}
</script>

