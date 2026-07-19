<template src="./HandleInstallRequest.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'

export default {
  name: 'HandleInstallRequest',
  components: {
    breadcrumb
    // DanhSachThueBao: () => import("./part/DanhSachThueBao.vue")
  },

  data() {
    return {
      header: {
        title: 'Xử lý yêu cầu lắp đặt',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Xử lý yêu cầu lắp đặt', link: { name: 'Ui.cards' } },
        ]
      },
      vkieu: 1,
      // data header action
      tsbtnLayTT: {
        enable: true,
        visible: true
      },
      tsbtnCapNhat: {
        enable: true,
        visible: true
      },
      tsbtnXuLy: {
        enable: false,
        visible: true
      },
      tsbtnTraPhieu: {
        enable: true,
        visible: true
      },
      tsbtnHuyXuLy: {
        enable: true,
        visible: false
      },
      tsbtnChuyenLM: {
        enable: true,
        visible: false
      },

      currentTab: 'tiep_nhan', // tiep_nhan || xu_ly
      dtGridDSTiepNhan: {
        columns: [
          { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_kh', headerText: 'Tên Khách Hàng', allowFiltering: true, allowSorting: true },
          { fieldName: 'diachi_kh', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowSorting: true },
          { fieldName: 'dienthoai_lh', headerText: 'Điện thoại liên hệ', allowFiltering: true, allowSorting: true },
          { fieldName: 'ngaylap_hd', headerText: 'Ngày yêu cầu', allowFiltering: true, allowSorting: true },
          { fieldName: 'nd_giao', headerText: 'Nội dung giao', allowFiltering: true, allowSorting: true },
          {
            fieldName: 'donvi_nhan_id', headerText: 'Đơn vị nhận', allowFiltering: true, allowSorting: true, template: this.getCboDonVi(this)
          },
          { fieldName: 'ten_dv', headerText: 'Đơn vị lâp HĐ/PL', allowFiltering: true, allowSorting: true }
        ],
        dataSource: [],
        selecting: null
      },

      cboDonVi: [],
      phieuhd_id: 0,
      donvi_nhan_id: 0
    }
  },

  async created() {
    await this.hienThiDanhSach()
    await this.getcbDonVi()
  },

  methods: {
    async tsbtnLayTTClick() {
      await this.hienThiDanhSach(this.vkieu)
    },
    async tsbtnCapNhatClick() {
      if (!this.dtGridDSTiepNhan.dataSource.length) {
        return this.$toast.warning('Không có phiếu!')
      }
      try {
        this.loading(true)
        const response = await api.capNhatToXlHDTiepNhan(this.axios, { vphieuhd_id: this.phieuhd_id, vdonvi_nhan_id: this.donvi_nhan_id })

        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.loading(false)
          this.$toast.success('Cập nhật thành công!')
          await this.hienThiDanhSach(this.vkieu)
        } else {
          this.loading(false)
          this.$toast.error('Cập nhật thất bại')
        }
      } catch (error) {
        this.loading(false)
        console.log(error)
      }
    },
    async tsbtnXuLyClick() {
      try {
        if (!this.dtGridDSTiepNhan.dataSource.length)
          return this.$toast.warning('Không có phiếu!')

        if (!this.donvi_nhan_id)
          return this.$toast.warning('Không có đơn vị nhận')


        this.loading(true)

        const vhdkh_id = this.dtGridDSTiepNhan.selecting?.hdkh_id

        const response = await api.chuyenDLHDTNSangHDDatMoi(this.axios, vhdkh_id)

        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.loading(false)
          this.$toast.success('Chuyển xử lý thành công!')
          await this.hienThiDanhSach(this.vkieu)
        } else {
          this.loading(false)
          this.$toast.error('Chuyển xử lý thành công thất bại')
        }
      } catch (error) {
        this.loading(false)
        console.log(error)
      }
    },
    async tsbtnTraPhieuClick() {
      try {
        if (!this.dtGridDSTiepNhan.dataSource.length) {
          return this.$toast.warning('Không có phiếu!')
        }

        this.loading(true)

        const vphieu_cha_id = this.dtGridDSTiepNhan.selecting?.phieu_cha_id
        const vphieuhd_id = this.dtGridDSTiepNhan.selecting?.phieuhd_id

        const response = await api.traPhieuXLHDTiepNhan(this.axios, { vphieu_cha_id, vphieuhd_id })

        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.loading(false)
          this.$toast.success('Trả phiếu thành công!')
          await this.hienThiDanhSach()
        } else {
          this.loading(false)
          this.$toast.error('Trả Phiếu thất bại')
        }
      } catch (error) {
        this.loading(false)
        console.log(error)
      }
    },
    async tsbtnHuyXuLyClick() {
      try {

        if (!this.dtGridDSTiepNhan.dataSource.length) {
          return this.$toast.warning('Không có phiếu!')
        }
        this.loading(true)
        const vhdkh_id = this.dtGridDSTiepNhan.selecting?.hdkh_id
        const vhdkhtn_id = this.dtGridDSTiepNhan.selecting?.hdkh_cu_id || 0

        const response = await api.huyXuLyHDTiepNhan(this.axios, { vhdkh_id, vhdkhtn_id })

        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.loading(false)
          this.$toast.success('Huỷ xử lý thành công!')
          await this.hienThiDanhSach()
        } else {
          this.loading(false)
          this.$toast.error('Trả Phiếu thất bại')
        }
      } catch (error) {
        this.loading(false)
        console.log(error)
      }
    },

    tsbtnChuyenLMClick() {
      if (this.dtGridDSTiepNhan.dataSource.length <= 0) return
      const magd = this.dtGridDSTiepNhan.selecting.ma_gd
      if (magd) {
        this.$router.push({
          path: 'InstallNewSubs',
          query: { magd: magd }
        })
        // this.$router.push({ name: 'InstallNewSubs', params: { mgd: mgd } })
      }
    },
    rowDoubleClicked(arg) {
      if (this.vkieu === 1) return;
      if (arg.rowData) {
        const magd = arg.rowData.ma_gd
        if (magd) {

          // this.$router.push({ name: 'InstallNewSubs', params: { magd: magd } })
          this.$router.push({
            path: 'InstallNewSubs',
            query: { magd: magd }
          })
        }
      }
    },

    rowSelectedDSPhieuDXL(arg) {
      if (arg.data) {
        this.dtGridDSTiepNhan.selecting = arg.data
        this.phieuhd_id = arg.data?.phieuhd_id
        this.donvi_nhan_id = arg.data.donvi_nhan_id || 0
      }
    },

    async hienThiDanhSach() {
      await this.getDanhSachTiepNhan(this.vkieu)
    },

    //private function
    async getDanhSachTiepNhan(kieu) {
      try {
        this.loading(true)
        const response = await api.getDSTiepNhanXuLy(this.axios, kieu)
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.dtGridDSTiepNhan.dataSource = response.data.data
        } else {
          this.loading(false)
          this.dtGridDSTiepNhan.dataSource = []
          this.$toast.warning('Không có Phiếu cần xử lý!')
        }
      } catch (error) {
        console.log(error)
      }
      this.loading(false)
    },

    async getcbDonVi() {
      try {
        const response = await api.getDanhSachDonViNhan(this.axios)

        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.cboDonVi = response.data.data
        }
      } catch (error) {
        console.log(error)
      }
    },

    //template Combo Đơn vị nhận
    getCboDonVi(parent) {
      return function () {
        return {
          template: {
            components: {},
            template: `
                           <ejs-dropdownlist                           
                            :dataSource="parent.cboDonVi"                        
                            :fields="{value:'donvi_nhan_id', text: 'ten_dv'}"                   
                            :change="valueChange"
                            v-model="value"
                        ></ejs-dropdownlist>
                        `,
            data() {
              return {
                parent: parent, //this
                data: {}, //rowData
                value: ''
              }
            },
            created() {
              this.value = this.data.donvi_nhan_id
            },
            methods: {
              valueChange(arg) {
                this.parent.dtGridDSTiepNhan.dataSource.forEach((item) => {
                  if (this.data.hdkh_id == item.hdkh_id) {
                    this.parent.donvi_nhan_id = arg.value
                    item.donvi_nhan_id = arg.value
                  }
                })
              }
            }
          }
        }
      }
    }
  },

  watch: {
    currentTab(val) {
      if (val === 'tiep_nhan') {
        this.tsbtnCapNhat.visible = true
        this.tsbtnXuLy.visible = true
        this.tsbtnTraPhieu.visible = true
        this.tsbtnHuyXuLy.visible = false
        this.tsbtnChuyenLM.visible = false
        this.vkieu = 1
      }
      if (val === 'xu_ly') {
        this.tsbtnCapNhat.visible = false
        this.tsbtnXuLy.visible = false
        this.tsbtnTraPhieu.visible = false
        this.tsbtnHuyXuLy.visible = true
        this.tsbtnChuyenLM.visible = true
        this.vkieu = 2;

      }
      this.$refs.dtGridDSTiepNhan.grid.refreshColumns();
      this.hienThiDanhSach()
    },
    donvi_nhan_id(val) {
      if (val != 0) {
        this.tsbtnXuLy.enable = true;
        // this.tsbtnHuyXuLy.enable = true;
      } else {
        this.tsbtnXuLy.enable = false;
        // this.tsbtnHuyXuLy.enable = false;
      }
    }
  }
}
</script>