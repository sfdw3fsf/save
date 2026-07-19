<template src='./StandardizeNumber.html'>

</template>

<script>
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'

export default {
  name: 'StandardizeNumber',
  components: {
    breadcrumb,
    SearchAccount
  },
  data() {
    return {
      header: {
        title: 'Chuẩn hóa số liên hệ'
      },

      khachhang_id: 0,
      thanhtoan_id: 0,
      thuebao_id: 0,
      txtSoDTMoi: '',
      txtMaKH: '',
      txtTelKH: '',
      txtTenKH: '',
      txtDiaChiKH: '',
      txtGhiChuKH: '',

      //datagrid
      grvThanhToan: [],
      grvThanhToan_cols: [
        { fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, allowSorting: false },
        { fieldName: 'ten_tt', headerText: 'Tên TT', allowFiltering: true, allowSorting: false },
        { fieldName: 'diachi_tt', headerText: 'Địa chỉ TT', allowFiltering: true, allowSorting: false },
        { fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true, allowSorting: false }
      ],
      grvThueBao: [],
      grvThueBao_selectedIds: [],
      grvThueBao_cols: [
        { fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, allowSorting: false },
        { fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true, allowSorting: false },
        { fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', allowFiltering: true, allowSorting: false },
        { fieldName: 'diachi_tb', headerText: 'Địa chỉ TB', allowFiltering: true, allowSorting: false },
        { fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true, allowSorting: false },
        { fieldName: 'ttlh_id', headerText: 'Liên hệ ID', allowFiltering: true, allowSorting: false }
      ]
    }
  },
  methods: {
    btnLayMaKH_Click() {
      this.$refs['popupSearchAccount'].show()
    },

    acceptSearchAccount(item) {
      this.$refs['popupSearchAccount'].hide()
      if (item === undefined || item === null) {
        return
      }

      console.log('acceptSearchAccount: ' + item)
      this.txtMaKH = item.ma_tb
      this.txtMaKH_KeyPress()
    },

    async txtMaKH_KeyPress() {
      if (this.txtMaKH) {
        await this.HienThiTT_DBKH(this.txtMaKH.replaceAll(' ', ''))
      }
    },

    async HienThiTT_DBKH(txtMaKH) {
      this.loading(true)
      try {
        let rs = await this.axios.post('web-hopdong/bss_group1/lay_danhba_kh_theo_makh', {
          in_ma_tb: txtMaKH
        })

        let { data } = rs.data
        if (data.length > 0) {
          this.txtMaKH = data[0]['ma_kh']
          this.khachhang_id = parseInt(data[0]['khachhang_id'])
          this.txtTenKH = data[0]['ten_kh']
          this.txtDiaChiKH = data[0]['diachi_kh']
          this.txtTelKH = data[0]['so_dt']
          this.grvThanhToan = await this.LAY_DS_TT_THEO_KHACHHANG_ID(this.khachhang_id)
          this.grvThueBao = await this.LAY_DS_TB_THEO_KHACHHANG_ID(this.khachhang_id)
        } else {
          this.khachhang_id = 0
          this.$root.$toast.error('Không tìm thấy thông tin! Bạn hãy nhập lại thông tin tìm kiếm')
        }
      } catch (e) {
        console.log('HienThiTT_DBKH: ' + e)
      } finally {
        this.loading(false)
      }
    },

    async LAY_DS_TT_THEO_KHACHHANG_ID(khachhang_id) {
      try {
        let rs = await this.axios.get(`web-ccdv/chuanhoa_solh/lay_ds_tt_theo_khachhang_id?khachhang_id=${khachhang_id}`)
        return rs.data.data || []
      } catch (e) {
        console.log('LAY_DS_TT_THEO_KHACHHANG_ID: ' + e)
      }
    },

    async LAY_DS_TB_THEO_KHACHHANG_ID(khachhang_id) {
      try {
        this.grvThueBao_selectedIds = []
        let rs = await this.axios.get(`web-ccdv/chuanhoa_solh/lay_ds_tb_theo_khachhang_id?khachhang_id=${khachhang_id}`)
        return rs.data.data || []
      } catch (e) {
        console.log('LAY_DS_TT_THEO_KHACHHANG_ID: ' + e)
      }
    },

    async btnCapNhatKH_Click() {
      try {
        this.loading(true)
        if (this.khachhang_id == 0) {
          this.$root.$toast.error('Chưa có thông tin khách hàng')
          return
        }

        if (!this.KiemTraSoDiDong(this.txtSoDTMoi)) return
        let rs = await this.axios.post('web-ccdv/chuanhoa_solh/sp_frmchuanhoalh_capnhat_kh', {
          khachhang_id: this.khachhang_id,
          sodt_cu: this.txtTelKH,
          sodt_moi: this.txtSoDTMoi,
          ghichu: this.txtGhiChuKH
        })

        if (rs.data.error == 200) {
          this.$root.$toast.success('Cập nhật thành công !')
        } else {
          this.$root.$toast.error(rs.data.message)
        }
      } catch (ex) {
        this.$root.$toast.error('Có lỗi trong quá trình cập nhật thông tin liên hệ khách hàng. Lỗi :' + ex)
      } finally {
        this.loading(false)
      }
    },

    KiemTraSoDiDong(vtel) {
      if (vtel.trim().length !== 10) {
        this.$root.$toast.error('Số điện thoại phải có 10 chữ số.')
        return false
      }

      if (!/^\d+$/.test(vtel.trim())) {
        this.$root.$toast.error('Số điện thoại phải là kiểu số!')
        return false
      }
      if (vtel.substring(0, 1) != '0') {
        this.$root.$toast.error('Số điện thoại phải bắt đầu là số 0')
        return false
      }
      return true
    },

    async btnCapNhatTT_Click() {
      try {
        this.loading(true)
        let lstDanhSach = this.$refs.grvThanhToan.getSelectedRecords()
        if (lstDanhSach.length > 0) {
          if (!this.KiemTraSoDiDong(this.txtSoDTMoi)) return
          for (const tt of lstDanhSach) {
            await this.axios.post('web-ccdv/chuanhoa_solh/sp_frmchuanhoalh_capnhat_tt', {
              thanhtoan_id: tt.thanhtoan_id,
              sodt_cu: this.txtTelKH,
              sodt_moi: this.txtSoDTMoi,
              ghichu: this.txtGhiChuKH
            })
          }

          this.$root.$toast.success('Cập nhật thành công!')
          this.grvThanhToan = await this.LAY_DS_TT_THEO_KHACHHANG_ID(this.khachhang_id)
        } else {
          this.$root.$toast.error('Không có thanh toán/ chưa chọn thanh toán để cập nhật !')
        }
      } catch (ex) {
        this.$root.$toast.error('Có lỗi trong quá trình cập nhật thông tin liên hệ khách hàng. Lỗi :' + ex)
      } finally {
        this.loading(false)
      }
    },

    async btnCapNhatTB_Click() {
      try {
        this.loading(true)
        if (this.grvThueBao.length > 0) {
          if (!this.KiemTraSoDiDong(this.txtSoDTMoi)) return
          for (let i = 0; i < this.grvThueBao.length; i++) {
            let row = this.grvThueBao[i]
            let ma_tb = row.ma_tb;
            if (!this.grvThueBao_selectedIds.includes(ma_tb)) {
              if (row['ttlh_id']) {
                await this.axios.post('web-ccdv/chuanhoa_solh/chuanhoa_tt_lienhe_thuebao',
                  {
                    type: 1,
                    ttlh_id: row['ttlh_id'],
                    sodt_cu: '',
                    sodt_moi: '',
                    thuebao_id: 0,
                    ghichu: '',
                    khachhang_id: 0,
                    ten_kh: '',
                    diachi_kh: ''
                  })
              }
            } else {
              if (row['ttlh_id']) {
                await this.axios.post('web-ccdv/chuanhoa_solh/chuanhoa_tt_lienhe_thuebao',
                  {
                    type: 2,
                    ttlh_id: row['ttlh_id'],
                    sodt_cu: row.so_dt,
                    sodt_moi: this.txtSoDTMoi,
                    thuebao_id: row.thuebao_id,
                    ghichu: this.txtGhiChuKH,
                    khachhang_id: this.khachhang_id,
                    ten_kh: this.txtTenKH,
                    diachi_kh: this.txtDiaChiKH
                  })
              } else {
                await this.axios.post('web-ccdv/chuanhoa_solh/chuanhoa_tt_lienhe_thuebao',
                  {
                    type: 3,
                    ttlh_id: 0,
                    sodt_cu: row.so_dt,
                    sodt_moi: this.txtSoDTMoi,
                    thuebao_id: row.thuebao_id,
                    ghichu: this.txtGhiChuKH,
                    khachhang_id: this.khachhang_id,
                    ten_kh: this.txtTenKH,
                    diachi_kh: this.txtDiaChiKH
                  })
              }
            }
          }
          this.$root.$toast.success('Cập nhật thành công !')
          this.grvThueBao = await this.LAY_DS_TB_THEO_KHACHHANG_ID(this.khachhang_id)
        } else
          this.$root.$toast.error('Không có thanh toán/ chưa chọn thanh toán để cập nhật !')

      } catch (ex) {
        this.$root.$toast.error('Có lỗi trong quá trình cập nhật thông tin liên hệ khách hàng. Lỗi :' + ex)
      } finally {
        this.loading(false)
      }
    },

    grvThueBao_rowSelected(args) {
      if (args) {
        let data = args.data
        if (data.length > 1) {
          for (let i = 0; i < data.length; i++) {
            if (!this.grvThueBao_selectedIds.includes(data[i].ma_tb)) {
              this.grvThueBao_selectedIds.push(data[i].ma_tb)
            }
          }
        } else {
          if (!this.grvThueBao_selectedIds.includes(args.data.ma_tb)) {
            this.grvThueBao_selectedIds.push(args.data.ma_tb)
          }
        }
      }
    },

    grvThueBao_rowDeselected(args) {
      if (args) {
        let data = args.data
        if (data.length > 1) {
          // for (let i = 0; i < data.length; i++) {
          //   let ma_tb = this.grvThueBao_selectedIds.indexOf(data[i].ma_tb);
          //   this.grvThueBao_selectedIds.splice(ma_tb, 1)
          // }
          this.grvThueBao_selectedIds = []
        } else {
          let ma_tb = args.data.ma_tb
          let index = this.grvThueBao_selectedIds.indexOf(ma_tb)
          if (index > -1) {
            this.grvThueBao_selectedIds.splice(index, 1)
          }
        }
      }
    }
  }
}
</script>

<style scoped>
.page-content {
  top: 40px;
}

/deep/ .modal-body .page-content {
  position: inherit;
}
</style>
