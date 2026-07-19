<template src="./Terminals.html"></template>
<style src="./Terminals.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import TerminalsAPI from './TerminalsAPI'
import gridview from './gridview'
import xlsx from 'xlsx'
import { L10n } from '@syncfusion/ej2-base'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//

L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})

export default {
  components: { breadcrumb, gridview },
  name: 'TerminalsAPI',
  mounted() {
    //load ds dịch vụ VT
    TerminalsAPI.lay_ds_dvvt(this.axios, {}).then((response) => {
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.ds_dvvt.list = response.data.data
      }
    })

    // Load dữ liệu ban đầu
    this.Load()
    // Load ds loại hình ban đầu
    var pdata = {}
    pdata.kieu = 0
    pdata.dichvuvt_id = this.listbox.ds_dvvt.value
    TerminalsAPI.lay_ds_gan(this.axios, pdata).then((response) => {
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.thietbichuagan.list = response.data.data
        for (var i in this.thietbichuagan.list) {
          this.thietbichuagan.list[i].STT = (parseInt(i) + 1).toString()
          this.listbox.loaihinh_chuagan.header = [
            { name: 'selected', text: 'Chọn' },
            { name: 'loaihinh_tb', text: 'Loại hình chưa gán' }
          ]
        }
      }
    })
  },
  destroyed() {},
  data() {
    return {
      header: {
        title: 'THIẾT BỊ ĐẦU CUỐI',
        list: [
          {
            name: 'Dư liệu',
            link: { name: 'Ui.cards' }
          },
          {
            name: 'Phát triển thuê bao',
            link: { name: 'Ui.buttons' }
          },
          {
            name: 'Quản lý thiết bị đầu cuối',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      danhsachthietbi: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        columns: [
          { fieldName: 'stt', headerText: 'Số TT', allowFiltering: true, width: '80px' },
          { fieldName: 'thietbidc_id', headerText: 'ID', allowFiltering: true },
          { fieldName: 'matbi_dc', headerText: 'Mã thiết bị', allowFiltering: true },
          { fieldName: 'ten_tbi', headerText: 'Tên thiết bị', allowFiltering: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true }
        ]
      },
      thietbidagan: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        checked: [],
        columns: [
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình đã gán', allowFiltering: true },
          { fieldName: 'selected', headerText: 'Hiển thị', allowFiltering: true }
        ]
      },
      thietbichuagan: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        checked: [],
        columns: [
          // { headerText: 'Chọn', allowFiltering: true, width: '30px', type: 'boolean', displayAsCheckBox: true,  },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình chưa gán', allowFiltering: true }
        ]
      },
      txtMaThietBi: '',
      txtTenThietBi: '',
      txtGhiChu: '',
      txtNam: '',
      hieuluc: '',
      CongVan: '',
      statusNhapMoi: true,
      statusXoa: true,
      statusGhiLai: true,
      statusHuy: true,
      listbox: {
        ds_dvvt: {
          list: [],
          value: 1,
          isEnabled: false
        },
        loai_cv_vnp: {
          list: [],
          value: '',
          isEnabled: false
        },
        ds_kieu_so: {
          list: [],
          value: '',
          isEnabled: false
        },
        ds_host: {
          list: [],
          value: '',
          isEnabled: false
        },
        ketquatimkiem: {
          list: [],
          value: '',
          header: []
        },
        loaihinh_dagan: {
          list: [],
          value: '',
          header: []
        },
        loaihinh_chuagan: {
          list: [],
          value: '',
          header: []
        },
        ketquatimkiem1: {
          list: [],
          value: '',
          header: []
        },
        ketquatimkiem_temp: {
          list: [],
          header: [],
          value: {},
          isEnabled: true
        }
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date()
    }
  },
  methods: {
    NhapMoi() {
      this
      this.statusNhapMoi = false
      this.statusXoa = false
      this.statusGhiLai = true
      this.statusHuy = true
      this.txtMaThietBi = ''
      this.txtTenThietBi = ''
      this.txtGhiChu = ''
      this.thietbidagan.list = ''
      this.$refs.txtMaThietBi.focus()
    },

    select_selectedItemsChangedCG: function(dataKeys) {
      this.thietbichuagan.checked = dataKeys
      this.thietbichuagan.list.forEach(function(item) {
        if (dataKeys.filter((x) => x === item.loaitb_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },
    select_selectedItemsChangedDG: function(dataKeys) {
      this.thietbidagan.checked = dataKeys
      this.thietbidagan.list.forEach(function(item) {
        if (dataKeys.filter((x) => x === item.loaitb_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },
    // Thay đổi select box dịch vụ

    // Load
    Load() {
      TerminalsAPI.lay_ds_thiet_bi(this.axios, {}).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.danhsachthietbi.list = response.data.data
          this.txtMaThietBi = this.danhsachthietbi.list[0].matbi_dc
          this.txtTenThietBi = this.danhsachthietbi.list[0].ten_tbi
          this.txtGhiChu = this.danhsachthietbi.list[0].ghichu
          // console.log(this.danhsachthietbi.list[0].thietbidc_id);
          var pdata = {}
          pdata.thietbidc_id = this.danhsachthietbi.list[0].thietbidc_id
          pdata.kieu = 0
          pdata.dichvuvt_id = this.listbox.ds_dvvt.value
          TerminalsAPI.lay_ds_gan(this.axios, pdata).then((response) => {
            if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
              this.thietbichuagan.list = response.data.data
            }
          })
          var pdata1 = {}
          pdata1.thietbidc_id = this.danhsachthietbi.list[0].thietbidc_id
          pdata1.kieu = 1
          pdata1.dichvuvt_id = this.listbox.ds_dvvt.value
          TerminalsAPI.lay_ds_gan(this.axios, pdata1).then((response) => {
            if (response && response.data && response.data.data && response.data.error && response.data.data) {
              this.thietbidagan.list = response.data.data
            }
          })
        }
      })
    },

    DichVuChange() {
      this.thietbidagan.list = ''
      var pdata = {}
      if (this.statusNhapMoi == false) {
        pdata.thietbidc_id = 0
      } else pdata.thietbidc_id = this.listbox.ketquatimkiem1.list.thietbidc_id
      pdata.kieu = 0
      pdata.dichvuvt_id = this.listbox.ds_dvvt.value
      TerminalsAPI.lay_ds_gan(this.axios, pdata).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.thietbichuagan.list = response.data.data
          for (var i in this.thietbichuagan.list) {
            this.thietbichuagan.list[i].STT = (parseInt(i) + 1).toString()
            this.listbox.loaihinh_chuagan.header = [
              { name: 'selected', text: 'Chọn' },
              { name: 'loaihinh_tb', text: 'Loại hình chưa gán' }
            ]
          }
        }
      })
      var pdata1 = {}
      if (this.statusNhapMoi == false) {
        pdata.thietbidc_id = 0
      } else pdata1.thietbidc_id = this.listbox.ketquatimkiem1.list.thietbidc_id
      pdata1.kieu = 1
      pdata1.dichvuvt_id = this.listbox.ds_dvvt.value
      TerminalsAPI.lay_ds_gan(this.axios, pdata1).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.data) {
          this.thietbidagan.list = response.data.data
        }
      })
    },

    // Click ghi lại

    GhiLai() {
      var pdata = {}
      var js_tbdc = {}
      var js_lhtb = {}
      console.log(this.thietbidagan.list.loaitb_id)
      if (this.statusNhapMoi == false) {
        pdata.thietbidc_id = ''
        pdata.is_insert = 1
        js_tbdc.THIETBIDC_ID = ''
        js_lhtb.THIETBIDC_ID = ''
      } else {
        pdata.thietbidc_id = this.listbox.ketquatimkiem1.list.thietbidc_id
        js_tbdc.THIETBIDC_ID = this.listbox.ketquatimkiem1.list.thietbidc_id
        js_lhtb.THIETBIDC_ID = this.listbox.ketquatimkiem1.list.thietbidc_id
        pdata.is_insert = 0
      }
      var data = this.thietbidagan.list
      js_tbdc.TEN_TBI = this.txtTenThietBi.trim()
      js_tbdc.MATBI_DC = this.txtMaThietBi.trim()
      js_tbdc.GHICHU = this.txtGhiChu

      let thuebao_id = ''
      for (var i = 0; i < data.length; i++) {
        if (this.statusNhapMoi == false) {
          if (i == data.length - 1) thuebao_id = thuebao_id + '{"THIETBIDC_ID": "","LOAITB_ID": ' + data[i].loaitb_id + ',"HIENTHI_MD": 1}'
          else thuebao_id = thuebao_id + '{"THIETBIDC_ID": "","LOAITB_ID": ' + data[i].loaitb_id + ',"HIENTHI_MD":"1"},'
        } else {
          if (i == data.length - 1) thuebao_id = thuebao_id + '{"THIETBIDC_ID": ' + js_lhtb.THIETBIDC_ID + ',"LOAITB_ID": ' + data[i].loaitb_id + ',"HIENTHI_MD": 1}'
          else thuebao_id = thuebao_id + '{"THIETBIDC_ID": ' + js_lhtb.THIETBIDC_ID + ',"LOAITB_ID": ' + data[i].loaitb_id + ',"HIENTHI_MD":"1"},'
        }
      }
      thuebao_id = '[' + thuebao_id + ']'
      console.log(thuebao_id)
      js_lhtb = thuebao_id

      js_tbdc = JSON.stringify(js_tbdc)
      // js_lhtb = JSON.stringify(js_lhtb);

      pdata.js_tbdc = js_tbdc
      pdata.js_lhtb = js_lhtb
      console.log(pdata)
      TerminalsAPI.ghi_lai(this.axios, pdata).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.Load()
          this.$toast.success('Thêm dữ liệu thành công')
          this.statusNhapMoi = true
          this.statusXoa = true
        }
      })
    },

    // Click vào lướt ds thiết bị

    grdLoaiHinhChanged(obj) {
      this.statusNhapMoi = true
      this.statusXoa = true
      this.statusGhiLai = true
      this.statusHuy = true
      this.listbox.ketquatimkiem.value = obj
      this.listbox.ketquatimkiem1.list = obj
      this.txtMaThietBi = this.listbox.ketquatimkiem1.list.matbi_dc
      this.txtTenThietBi = this.listbox.ketquatimkiem1.list.ten_tbi
      this.txtGhiChu = this.listbox.ketquatimkiem1.list.ghichu
      var pdata = {}
      pdata.thietbidc_id = this.listbox.ketquatimkiem1.list.thietbidc_id
      pdata.kieu = 0
      pdata.dichvuvt_id = this.listbox.ds_dvvt.value
      TerminalsAPI.lay_ds_gan(this.axios, pdata).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.thietbichuagan.list = response.data.data
        }
      })
      var pdata1 = {}
      pdata1.thietbidc_id = this.listbox.ketquatimkiem1.list.thietbidc_id
      pdata1.kieu = 1
      pdata1.dichvuvt_id = this.listbox.ds_dvvt.value
      TerminalsAPI.lay_ds_gan(this.axios, pdata1).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.data) {
          this.thietbidagan.list = response.data.data
        }
      })
    },
    MoveLeft: function() {
      var tempData = this.thietbidagan.list
      this.thietbidagan.list = []
      var data = this.thietbichuagan.list.filter((x) => x.ischecked)
      if (data.length > 0) {
        data.forEach((item) => {
          tempData.push(item)
        })
      }
      this.thietbidagan.list = tempData.slice(0)
      this.thietbichuagan.list = this.thietbichuagan.list.filter((x) => !x.ischecked)
    },
    MoveLeftAll: function() {
      var tempData = this.thietbidagan.list
      var data = this.thietbichuagan.list
      if (data.length > 0) {
        data.forEach((item) => {
          tempData.push(item)
        })
        this.thietbidagan.list = tempData.slice(0)
      }
      this.thietbichuagan.list = []
    },
    MoveRight: function() {
      var tempData = this.thietbichuagan.list
      var data = this.thietbidagan.list.filter((x) => x.ischecked)
      if (data.length > 0) {
        data.forEach((item) => {
          tempData.push(item)
        })
        this.thietbichuagan.list = tempData.slice(0)
      } else {
        //   this.$root.toastError("Bạn chưa chọn thông tin để gán!");
      }
      this.thietbidagan.list = this.thietbidagan.list.filter((x) => !x.ischecked)
    },
    MoveRightAll: function() {
      var tempData = this.thietbichuagan.list
      var data = this.thietbidagan.list
      if (data.length > 0) {
        data.forEach((item) => {
          tempData.push(item)
        })
        this.thietbichuagan.list = tempData.slice(0)
      }
      this.thietbidagan.list = []
    },
    ChangeDaGan(obj) {
      //this.thietbidagan.list = obj;
      console.log(obj.loaihinh_tb)
    },
    ChangeChuaGan(obj) {
      // this.thietbichuagan.list = obj;
      console.log(obj.loaitb_id)
    },
    BtnXoa() {
      var pdata = {}
      pdata.thietbidc_id = this.listbox.ketquatimkiem1.list.thietbidc_id
      TerminalsAPI.xoa_tb(this.axios, pdata).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.Load()
          this.statusNhapMoi = true
          this.$bvModal.hide('ModalXacNhan')
          this.$toast.success('Xóa dữ liệu thành công')
        }
      })
    },
    PopupXacNhan() {
      this.$bvModal.show('ModalXacNhan')
    },
    Huy() {
      this.statusNhapMoi = true
      this.statusXoa = true
      this.statusGhiLai = false
      this.statusHuy = false
      this.sra
      this.txtMaThietBi = ''
      this.txtTenThietBi = ''
      this.txtGhiChu = ''
    },
    created: async function() {
      this.loading(true)
      this.loadData()
      this.loading(false)
    }
  }
}
</script>
