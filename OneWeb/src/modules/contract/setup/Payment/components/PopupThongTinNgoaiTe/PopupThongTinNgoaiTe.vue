<template src="./PopupThongTinNgoaiTe.html"></template>
<script>
import api from '../../Api'
import Vue from 'vue'
import DataGridC from '@/components/Shared/DataGrid/index.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import DataGridCustom from '../../../DeclareLandline/components/DataGridCustom'
const LoaiTyGia = {
  CO_DINH: 1,
  BIEN_DONG_NGAY_CUOI_THANG: 2,
  BIEN_DONG_KHI_PHAT_HANH_HOA_DON: 3,
  BIEN_DONG_THANG_GHI_NHAN_DOANH_THU: 4
}
export default {
  name: 'PopupThongTinNgoaiTe',
  components: {
    DataGridC,
    KDataGrid,
    DataGridCustom
  },
  data() {
    return {
      isShowModal: false,
      animationSettings: {},
      position: { X: 'center', Y: 'top' },
      target: '#app .main-wrapper', // .main-wrapper-payment
      txtMucThue: 0,
      txtTyGia: 0,
      txtTyGiaEnable: false,
      txtMucDieuKien: '',
      hdkhId: '',
      actions: [
        {
          id: 'btnGhiLai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-save'
        }
      ],
      nganHang: {
        data: [],
        selected: '',
        enable: false
      },
      loaiTien: {
        data: [],
        selected: ''
      },
      tyGia: {
        data: [],
        selected: 0
      },
      kyCuoc: {
        selected: '',
        data: []
      },
      dsThueBao: {
        headers: [
          {
            headerText: '',
            width: 20,
            allowFiltering: false,
            textAlign: 'Center',
            headerTemplate: () => {
              return {
                template: Vue.component('action-check-all', {
                  template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.dsThueBao.isCheckAll" class="check check-enabled" :disabled="true"/>
                                <span class="name"></span>
                            </div>
                        `,
                  data: function() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent
                    }
                  }
                })
              }
            },
            template: () => {
              return {
                template: Vue.component('action-check', {
                  template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check check-enabled"
                                    v-model="parent.dsThueBao.checked"
                                    @change="onChangeProcessed(data)"
                                    :disabled="true"
                                    :value="data.hdtb_id"
                                    />
                                <span class="name"></span>
                            </div>
                        `,
                  data() {
                    return { data: {} }
                  },
                  methods: {
                    onChangeProcessed(value) {
                      // var obj = this.parent.dsThueBao.data.find((item) => item.hdtb_id == value.hdtb_id)
                      // obj.checked = true
                    }
                  },

                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false,
            width: 90,
            textAlign: 'left'
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên thuê bao',
            allowFiltering: true,
            allowSorting: false,
            width: 90
          },
          {
            fieldName: 'diachi_tb',
            headerText: 'Địa chỉ thuê bao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_kh',
            headerText: 'Tên KH',
            allowFiltering: true,
            allowSorting: false,
            width: 90
          },
          {
            fieldName: 'diachi_kh',
            headerText: 'Địa chỉ KH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ky_cuoc_name',
            headerText: 'Số ngày kỳ cước',
            allowFiltering: true,
            allowSorting: false,
            width: 90
          }
        ],
        data: [],
        checked: [],
        isCheckAll: false
      }
    }
  },
  watch: {
    'dsThueBao.isCheckAll': {
      handler: function(value) {
        console.log('dsThueBao.isCheckAll', value)
        if (value) {
          this.dsThueBao.data.forEach((item) => {
            item.checked = true
          })
          this.dsThueBao.checked = this.dsThueBao.data.map((item) => item.hdtb_id)
          return
        } else {
          this.dsThueBao.checked = []
          this.dsThueBao.data.forEach((item) => {
            item.checked = false
          })
          return
        }
      },
      deep: true
    },
    'dsThueBao.checked': {
      handler: function(value) {
        console.log('dsThueBao.checked', value)
        this.dsThueBao.isCheckAll = value.length > 0 && value.length === this.dsThueBao.data.length
      },
      deep: true
    },
    'tyGia.selected': {
      handler: function(value) {
        if (value == LoaiTyGia.CO_DINH) {
          this.txtTyGiaEnable = true
          this.nganHang.enable = false
          this.nganHang.selected = ''
        } else {
          this.nganHang.enable = true
          this.txtTyGiaEnable = false
          this.txtTyGia = ''
        }
      },
      deep: true
    }
  },
  methods: {
    show() {
      this.$refs.refPopupThongTinNgoaiTe.show()
    },
    hide() {
      this.$refs.refPopupThongTinNgoaiTe.hide()
    },
    onActionClick(action) {
      switch (action.id) {
        case 'btnGhiLai':
          this.onGhiLai()
          break
      }
    },
    onClose: function() {
      this.dsThueBao.checked = []
      this.dsThueBao.data = []
      this.dsThueBao.isCheckAll = false
      this.chkCheckDate = false
      this.txtMucThue = 0
      this.txtTyGia = 0
      this.txtTyGiaEnable = false
      this.nganHang.selected = ''
      this.hdkhId = ''
      this.kyCuoc.selected = ''
      this.tyGia.selected = ''
      this.loaiTien.selected = ''
    },
    onShow: function() {
      this.initData()
    },

    async fetchInitData() {
      try {
        this.loading(true)
        this.nganHang.data = this.getData(await api.lay_tt_nganhang(this.axios))
        this.loaiTien.data = this.getData(await api.lay_tt_loaitien(this.axios))
        this.tyGia.data = this.getData(await api.lay_ds_loai_tygia(this.axios))
        await this.layThongTinTyGiaDSHDTB()
        this.loading(false)
      } catch (e) {
        console.log(e)
      }
    },
    getData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async initData() {
      await this.fetchInitData()
      this.kyCuoc.data = [
        { id: 1, name: '30 ngày' },
        { id: 2, name: 'Số ngày trong tháng' }
      ]
      this.setCheckAll()
    },
    onThueBaoChangeSelected(keys) {},

    onThueBaoRowClick(row) {
      // const itemSelected =  this.dsThueBao.data[row];
      // const key = itemSelected.hdtb_id
      // this.loadThueBaoInfo(itemSelected)
      // if (this.dsThueBao.checked.includes(key)) {
      //   this.dsThueBao.checked = this.dsThueBao.checked.filter((item) => item !== key)
      // } else {
      //   this.dsThueBao.checked.push(key)
      // }
    },

    async onGhiLai() {
      try {
        if (this.dsThueBao.checked.length <= 0) {
          this.$toast.error('Chưa chọn thuê bao!')
          return
        }
        if (this.loaiTien.selected == '') {
          this.$toast.error('Chưa chọn loại tiền!')
          return
        }
        if (this.tyGia.selected == '') {
          this.$toast.error('Chưa chọn loại tỷ giá!')
          return
        }
        if (this.kyCuoc.selected == '') {
          this.$toast.error('Chưa chọn kỳ cước!')
          return
        }
        if (this.txtTyGiaEnable && this.txtTyGia == '') {
          this.$toast.error('Chưa nhập tỷ giá!')
          return
        }
        if (this.nganHang.enable && this.nganHang.selected == '') {
          this.$toast.error('Chưa chọn ngân hàng!')
          return
        }

        this.loading(true)
        var input = {
          HDKH_ID: parseInt(this.hdkhId),
          LOAITIEN_ID: parseInt(this.loaiTien.selected),
          MUCTHUE: this.txtMucThue,
          SONGAY_KC: parseInt(this.kyCuoc.selected),
          LOAITYGIA_ID: parseInt(this.tyGia.selected),
          DS_HDTB: this.dsThueBao.checked,
          TIGIA: null,
          NGANHANG_TYGIA_ID: null
        }
        console.log('txtTyGiaEnable', this.txtTyGiaEnable)
        console.log('this.nganHang.enable', this.nganHang.enable)
        if (this.txtTyGiaEnable) {
          input.TIGIA = parseFloat(this.txtTyGia)
        }
        if (this.nganHang.enable) {
          input.NGANHANG_TYGIA_ID = parseInt(this.nganHang.selected)
        }
        const response = await api.fn_update_thongtin_thanhtoan_bs_v2(this.axios, { p_ds_para: JSON.stringify(input) })
        if (response.data.error_code === 'BSS-00000000') {
          this.$toast.success('Cập nhật thành công!')
          await this.layThongTinTyGiaDSHDTB()
        } else {
          this.$toast.error('Cập nhật thất bại: ' + response.data.message_detail)
        }
        this.loading(false)
      } catch (e) {
        this.loading(false)
        console.log(e)
      }
    },

    async layThongTinTyGiaDSHDTB() {
      try {
        const response = await api.sp_lay_ds_thuebao_tygia(this.axios, { vhdkh_id: this.hdkhId })
        if (response.data.error_code === 'BSS-00000000') {
          this.dsThueBao.data = response.data.data
        } else {
          this.$toast.error(response.data.message_detail)
        }
        if (this.dsThueBao.data.length > 0) {
          this.dsThueBao.data = this.dsThueBao.data.map((item) => {
            item.ky_cuoc_name = item.songay_kc == null ? '' : item.songay_kc == 1 ? '30 ngày' : 'Số ngày trong tháng'
            return item
          })

          this.loadThueBaoInfo(this.dsThueBao.data[0])
        }
      } catch (e) {
        this.loading(false)
        console.log(e)
      }
    },

    setCheckAll() {
      this.dsThueBao.data.forEach((item) => {
        item.checked = true
      })
      this.dsThueBao.checked = [this.dsThueBao.data.map((item) => item.hdtb_id)]
      this.dsThueBao.isCheckAll = true
    },
    loadThueBaoInfo(item) {
      this.loaiTien.selected = item.loaitien_id ?? ''
      this.txtMucThue = item.mucthue ?? ''
      this.kyCuoc.selected = item.songay_kc ?? ''
      this.tyGia.selected = item.loaitygia_id ?? ''
      this.nganHang.selected = item.nganhang_tygia_id ?? ''
      this.txtTyGia = item.tigia ?? ''
    }
  }
}
</script>
