<template>
  <div class="modal-content popup-box">
    <div class="popup-header"></div>
    <ActionTop :actions="actions" @onActionClick="onActionClick" />
    <div class="popup-body">
      <div class="row">
        <div class="col-sm-12 col-12">
          <div class="box-form">
            <div class="info-row p-0 m-0 mb-3">
              <div class="w-100 mb-3">
                <div class="key w60">Mẫu số</div>
                <div class="value p-0 m-0" style="width: 550px">
                  <select2
                    v-model="cbo_mauso.value"
                    @change="updateOptions2"
                    :options="
                      cbo_mauso.list.map((e) => ({
                        id: e.id,
                        text: e.trang_thai
                      }))
                    "
                  ></select2>
                </div>
              </div>
              <div class="w-100">
                <div class="key w60 ml-2">Ký hiệu</div>
                <div class="value p-0 m-0" style="width: 550px">
                  <select2
                    v-model="cbo_kyhieu.value"
                    :options="
                      filteredOptions2.map((e) => ({
                        id: e.id,
                        text: e.trang_thai
                      }))
                    "
                  ></select2>
                </div>
              </div>
            </div>
            <div class="col overflow-hidden p-0 m-0">
              <div class="box-form">
                <div class="legend-title">Danh sách hoá đơn cần phát hành</div>
                <DataGrid :toolbar="null" v-bind:columns="xemtruoc_hoadon.headers" v-bind:dataSource="xemtruoc_hoadon.list" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="false" :showFilter="true" ref="refNhanVienDaGan" panelDataHeight="200" :enabledSelectFirstRow="false"> </DataGrid>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import API from './APIPOPUP'
import moment from 'moment'
import xlsx from 'xlsx'
export default {
  name: 'PhatHanhHoaDon',
  components: {
    ActionTop
  },
  data() {
    return {
      actions: [
        {
          id: 'xuat_hd',
          name: 'Xuất hoá đơn',
          active: true,
          icon_class: 'one-export'
        },
        {
          id: 'xuat_file',
          name: 'Xuất Excel',
          active: true,
          icon_class: 'one-download'
        }
      ],
      ejsDialog: {
        header: 'Tiêu đề',
        name: null,
        visible: false,
        config: {
          width: '95%',
          height: 'auto',
          minHeight: '150',
          enableResize: true,
          allowDragging: true,
          position: { X: 'center', Y: 'center' },
          animationSettings: { effect: 'Fade' }
        }
      },
      cbo_mauso: {
        value: '',
        list: [
          { id: '1/001', trang_thai: '1/001' },
          { id: '1/002', trang_thai: '1/002' },
          { id: '1/003', trang_thai: '1/003' },
          { id: '1/004', trang_thai: '1/004' }
        ]
      },
      cbo_kyhieu: {
        value: '',
        list: [
          { id: 'K23TDA', trang_thai: 'K23TDA', parent: '1/001' },
          { id: 'K23MCC', trang_thai: 'K23MCC', parent: '1/001' },
          { id: 'K24MCC', trang_thai: 'K24MCC', parent: '1/001' },
          { id: 'K24TDA', trang_thai: 'K24TDA', parent: '1/001' },
          { id: 'K24TBA', trang_thai: 'K24TBA', parent: '1/001' },
          { id: 'K23MAB', trang_thai: 'K23MAB', parent: '1/001' },
          { id: 'K24MAB', trang_thai: 'K24MAB', parent: '1/001' },
          { id: 'K23TBA', trang_thai: 'K23TBA', parent: '1/001' },
          { id: 'K24TFD', trang_thai: 'K24TFD', parent: '1/001' },
          { id: 'K24TFC', trang_thai: 'K24TFC', parent: '1/001' },
          { id: 'K24TFN', trang_thai: 'K24TFN', parent: '1/001' },
          { id: 'K23TAA', trang_thai: 'K23TAA', parent: '1/002' },
          { id: 'K24TAA', trang_thai: 'K24TAA', parent: '1/002' },
          { id: 'K22TAA', trang_thai: 'K22TAA', parent: '1/002' },
          { id: 'K23TCA', trang_thai: 'K23TCA', parent: '1/003' },
          { id: 'K23TIC', trang_thai: 'K23TIC', parent: '1/003' },
          { id: 'K24TIC', trang_thai: 'K24TIC', parent: '1/003' },
          { id: 'K24TCA', trang_thai: 'K24TCA', parent: '1/003' },
          { id: 'K23TDE', trang_thai: 'K23TDE', parent: '1/004' },
          { id: 'K24TDE', trang_thai: 'K24TDE', parent: '1/004' }
        ]
      },
      xemtruoc_hoadon: {
        list: [],
        headers: [
          { fieldName: 'ma_tt', headerText: 'Mã thanh toán', allowfiltering: true ,width : 120},
          { fieldName: 'ky_cuoc', headerText: 'Kì cước', allowfiltering: true, width : 90 },
          { fieldName: 'dot', headerText: 'Đợt', allowfiltering: true , width : 70},
          { fieldName: 'nam', headerText: 'Năm', allowfiltering: true ,width : 70},
          { fieldName: 'thang', headerText: 'Tháng', allowfiltering: true, width : 70 },
          { fieldName: 'diachi', headerText: 'Địa chỉ thanh toán', allowfiltering: true },
          { fieldName: 'ms_thue', headerText: 'Mã số thuế', allowfiltering: true },
          { fieldName: 'tien', headerText: 'Tiền', format: 'n3', type: 'number', allowfiltering: true },
          { fieldName: 'thue', headerText: 'Thuế', format: 'n3', type: 'number', allowfiltering: true },
          { fieldName: 'tongtien', headerText: 'Tổng tiền', format: 'n3', type: 'number', allowfiltering: true },
        ]
      }
    }
  },

  mounted() {
    this.xemtruoc_hoadon_canph()
  },

  computed: {
    filteredOptions2() {
      return this.cbo_kyhieu.list.filter((option) => option.parent === this.cbo_mauso.value)
    }
  },

  methods: {
    formatCurrency(amount) {
      return amount.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
    },
    formatCurrencyFields(data) {
      let keysToKeep = ['dot', 'nam', 'thang', 'ky_cuoc', 'thang', 'status']
      let newData = data.map(obj => {
      let newObj = {};
      for (let key in obj) {
        if (keysToKeep.includes(key)) {
          newObj[key] = obj[key];
        } else if (typeof obj[key] === 'number') {
          newObj[key] = this.formatCurrency(obj[key]);
        } else {
          newObj[key] = obj[key];
        }
      }
      return newObj;
    });

  return newData;
    },
    updateOptions2: function () {
      this.cbo_kyhieu.value = ''
    },
    async xemtruoc_hoadon_canph() {
      let kq = await API.xemtruoc_hoadon(this.axios, {})
      console.log(kq.data.data)
      if (kq.data.error && kq.data.error_code === 'BSS-00000000' && kq.data.data.data != null) {
        this.xemtruoc_hoadon.list = kq.data.data.data
      }
    },
    async PhatHanhHoaDonSangInvioce(pass, user, ds_hoadon) {
      let ds = []
      for (const e of ds_hoadon) {
        let kq = await API.importAndPublishInv(this.axios, {
          acPass: pass,
          account: user,
          convert: 0,
          pattern: this.cbo_mauso.value,
          serial: this.cbo_kyhieu.value,
          xmlInvData: e.xml,
          cusCode: e.ma_tt
        })
        console.log(kq.data.data)
        if (kq.data.data && kq.data.data.startsWith('OK')) {
          let kq_chuoi = this.catchuoikhamlo(kq.data.data)
          console.log(kq_chuoi)
          let kq_1 = await API.capnhat_trangthai_phathanh(this.axios, {
            p_ma_tt: e.ma_tt,
            p_ky_cuoc: e.ky_cuoc,
            p_no: kq_chuoi.no,
            p_serial: kq_chuoi.serial,
            p_pattern: kq_chuoi.pattern,
            p_ngay_ph: moment(new Date(), 'DD/MM/YYYY')
          })
          if (kq_1.data.error_code === 'BSS-00000000' && kq_1.data.data.data == 'OK') {
            console.log(1)
            ds.push(e.ma_tt)
          }
        }
      }
      console.log(ds)
      return ds
    },
    catchuoikhamlo(chuoixl) {
      let data_string = {}

      let parts = chuoixl.replace('OK:', '')
      parts = parts.split(/[;_-]/)
      data_string.pattern = parts[0]
      data_string.serial = parts[1]
      data_string.fkey = parts[2]
      data_string.no = parts[3]
      console.log(data_string)
      return data_string
    },
    async XuatHD() {
      this.loading(true)
      let kq = await API.kiemtra_ds_ma_tt_can_phathanh(this.axios, {})
      console.log(kq.data.data)
      if (kq.data.error && kq.data.error_code === 'BSS-00000000') {
        console.log(kq.data.data.data)
        if (kq.data.data.user && kq.data.data.pass) {
          if (this.cbo_mauso.value == '' || this.cbo_mauso.value == null) {
            this.$toast.warning(`Bạn chưa chọn mẫu số`)
          }
          if (this.cbo_kyhieu.value == '' || this.cbo_kyhieu.value == null) {
            this.$toast.warning(`Bạn chưa chọn kí hiệu`)
          }
          let result = []
          if ((this.cbo_mauso.value != '' && this.cbo_mauso.value != null) && (this.cbo_kyhieu.value != '' && this.cbo_kyhieu.value != null)) {
            result = await this.PhatHanhHoaDonSangInvioce(kq.data.data.pass, kq.data.data.user, kq.data.data.data)
            console.log(result.length)
            if (result.length > 0) {
              this.$toast.success(`Xuất hoá đơn thành công`)
            } else {
              this.$toast.warning(`Xuất hoá đơn không thành công`)
            }
          }
        } else {
          this.$toast.warning(`${kq.data.data.error_message}`)
        }
      }
      this.loading(false)
    },
    Xuat_File() {
      let wb = xlsx.utils.book_new()
      console.log(this.xemtruoc_hoadon.list)
      if (this.xemtruoc_hoadon.list == null) {
        this.$toast.warning(`Không có dữ liệu để xuất file`)
      }
      if (this.xemtruoc_hoadon.list.length > 0) {
        let exportData = []
        exportData = this.xemtruoc_hoadon.list.map((e) => {
          let exportEntity = {}
          this.xemtruoc_hoadon.headers.map((x) => {
            exportEntity[x.headerText] = e[x.fieldName]
          })
          return exportEntity
        })
        if (exportData.length > 0) {
          const ws1 = xlsx.utils.json_to_sheet(exportData)
          xlsx.utils.book_append_sheet(wb, ws1, 'Danh sách mã TT cần phát hành')
          xlsx.writeFile(wb, 'danhsachmattphathanh.xlsx')
        }
      }
    },

    showModal() {
      this.$refs['popupPhathanh'].show()
    },
    hideModal() {
      this.$refs['popupPhathanh'].hide()
    },
    onActionClick(action) {
      switch (action.id) {
        case 'upload':
          break
        case 'xuat_hd':
          this.XuatHD()
          break
        case 'xuat_file':
          this.Xuat_File()
          break
        case 'xoa':
          this.Xoa()
          break
        case 'tracuu':
          this.TraCuu()
          break
        case 'default':
          break
      }
    }
  }
}
</script>
<style>
</style>