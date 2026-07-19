<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li @click="clickButton('capnhat')" :class="this.button.capnhat ? 'active' : 'non-active'">
          <a> <span class="icon one-search"></span>Ghi lại </a>
        </li>
        <li @click="clickButton('xuatfile')" :class="this.button.xuatfile ? 'active' : 'non-active'">
          <a> <span class="icon one-reload1"></span>Xuất file</a>
        </li>
        <li>
          <a href="javascript:void(0)" @click="onXuatFileMau" title="File Excel mẫu"> <span class="icon one-xlsx"></span>File Excel mẫu</a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <div class="info-row marb10">
        <div class="key w20 padt0">
          <div class="one-alert text-warning f20"></div>
        </div>
        <div class="value text-main">
          File excel gồm 3 trường: MA_TB, MA_KV, KIEUKV_ID, LOAITB_ID <br />
          KIEUKV_ID = 1 (Đơn vị đầu), 2 (Đơn vị cuối). Áp dụng cho dịch vụTruyền số liệu (Các dịch vụ khác mặc định 2)
        </div>
      </div>

      <div class="info-row">
        <div class="key w80">Đường dẫn</div>
        <div class="value">
          <div class="input-more-button -right">
            <label class="btn" for="upload-file">
              <span class="-ap icon-more_horiz"></span>
              <input type="file" ref="files" class="hidden" id="upload-file" @change="importExcel" />
            </label>
            <input type="text" class="form-control highlight" v-model="file_up_load" />
          </div>
        </div>
      </div>

      <div class="table-content">
        <DataGrid :columns="cols_grid" v-bind:dataSource="this.danhsach" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="false"> </DataGrid>
      </div>
    </div>

    <b-modal id="DanhSachLoi" modal-class="popup-box" header-class="popup-header" body-class="none-padding" size="xl" hide-footer>
      <template #modal-header="{ close }">
        <div class="title"><span class="icon nc-icon-outline ui-1_settings"></span> Danh sách lỗi</div>
        <div @click="close" class="close -ap icon-close" data-dismiss="modal"></div>
      </template>

      <div class="popup-body mart10">
        <div class="box-form">
          <div class="legend-title">Danh sách lỗi</div>
          <div class="table-content">
            <DataGrid :columns="cols_grid_loi" v-bind:dataSource="this.danhsach_loi" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="false"> </DataGrid>
          </div>
        </div>
      </div>
    </b-modal>
  </div>
</template>
<script>
import VueInput from '../../../search/subscriber/components/form/VueInput.vue'
import api from './api'
import define from './define'
import VueCheckbox from '../../../search/subscriber/components/form/VueCheckbox.vue'
import XLSX from 'xlsx'
export default {
  components: { VueInput, VueCheckbox },
  data() {
    return {
      ...define,
      file_up_load: '',
      danhsach: [],
      danhsach_loi: [],
      cols_grid: [
        {
          fieldName: 'ma_tt',
          headerText: 'Mã TB',
          allowSorting: true,
          autoFit: true,
          textAlign: 'left'
        },
        {
          fieldName: 'giatri',
          headerText: 'Mã KV',
          allowSorting: true,
          autoFit: true,
          textAlign: 'left'
        },
        {
          fieldName: 'ghichu',
          headerText: 'Kiểu KV',
          allowSorting: true,
          autoFit: true,
          textAlign: 'center'
        },
        {
          fieldName: 'loaitb_id',
          headerText: 'Loại TB',
          allowSorting: true,
          autoFit: true,
          textAlign: 'center'
        }
      ],

      cols_grid_loi: [
        {
          fieldName: 'STT',
          headerText: 'STT',
          allowSorting: true,
          autoFit: true,
          width: '80',
          textAlign: 'center'
        },
        {
          fieldName: 'MA_TB',
          headerText: 'Mã TB',
          allowSorting: true,
          autoFit: true,
          textAlign: 'left'
        },
        {
          fieldName: 'MA_KV',
          headerText: 'Mã KV',
          allowSorting: true,
          autoFit: true,
          textAlign: 'left'
        },
        {
          fieldName: 'KIEUKV_ID',
          headerText: 'Kiểu KV',
          allowSorting: true,
          autoFit: true,
          textAlign: 'center'
        },
        {
          fieldName: 'LOAITB_ID',
          headerText: 'Loại TB',
          allowSorting: true,
          autoFit: true,
          textAlign: 'center'
        },
        {
          fieldName: 'LYDO_LOI',
          headerText: 'Lý do lỗi',
          allowSorting: true,
          autoFit: true,
          textAlign: 'left'
        }
      ]
    }
  },

  methods: {
    async importExcel(e) {
      const files = e.target.files
      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return alert('File upload chưa đúng định dạng (.xls/.xlsx)')
      }
      this.file_up_load = files[0].name
      const fileReader = new FileReader()
      fileReader.onload = (ev) => {
        try {
          const data = ev.target.result
          const workbook = XLSX.read(data, {
            type: 'binary'
          })
          const wsname = workbook.SheetNames[0] // Take the first sheet，wb.SheetNames[0] :Take the name of the first sheet in the sheets
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname])
          // Generate JSON table content，wb.Sheets[Sheet名]    Get the data of the first sheet
          const excellist = []
          this.danhsach_loi = [] // Clear received data
          this.danhsach = [] // Clear received data
          // Edit data + //Kiểm tra dữ liệu null
          for (var i = 0; i < ws.length; i++) {
            excellist.push(ws[i])
            this.danhsach.push({
              ma_tt: ws[i].MA_TB,
              giatri: ws[i].MA_KV,
              ghichu: ws[i].KIEUKV_ID,
              loaitb_id: ws[i].LOAITB_ID
            })
          }

          var bien = Object.keys(excellist[0])
          if (bien[0] != 'MA_TB' || bien[1] != 'MA_KV' || bien[2] != 'KIEUKV_ID' || bien[3] != 'LOAITB_ID') {
            this.danhsach = []
            this.danhsach_loi = []
            return alert('Tên cột trong file không đúng định dạng: MA_TB, MA_KV, KIEUKV_ID, LOAITB_ID')
          }

          var stt = 0
          for (var i = 0; i < excellist.length; i++) {
            stt++
            var loi = ''
            if (ws[i].MA_TB == '' || ws[i].MA_TB == null) {
              loi = loi + 'Mã TB null; '
            }
            if (ws[i].MA_KV == '' || ws[i].MA_KV == null) {
              loi = loi + 'Mã KV null; '
            }
            if (ws[i].KIEUKV_ID == '' || ws[i].KIEUKV_ID == null) {
              loi = loi + 'Kiểu KV null; '
            }
            if (ws[i].KIEUKV_ID != 1 && ws[i].KIEUKV_ID != 2) {
              loi = loi + 'Kiểu KV khác 1 và 2; '
            }
            if (loi != '') {
              this.danhsach_loi.push({
                STT: stt,
                MA_TB: ws[i].MA_TB,
                MA_KV: ws[i].MA_KV,
                KIEUKV_ID: ws[i].KIEUKV_ID,
                LOAITB_ID: ws[i].LOAITB_ID,
                LYDO_LOI: loi
              })
            }
          }

          //Kiểm tra dữ liệu trùng
          const uniqueArray1 = excellist.filter((thing, index) => {
            const _thing = JSON.stringify(thing)
            return (
              index !==
              excellist.findIndex((obj) => {
                return JSON.stringify(obj) === _thing
              })
            )
          })

          if (uniqueArray1.length > 0) {
            for (var i = uniqueArray1.length - 1; i >= 0; i--) {
              stt++
              this.danhsach_loi.push({
                STT: stt,
                MA_TB: uniqueArray1[i].MA_TB,
                MA_KV: uniqueArray1[i].MA_KV,
                KIEUKV_ID: uniqueArray1[i].KIEUKV_ID,
                LOAITB_ID: uniqueArray1[i].LOAITB_ID,
                LYDO_LOI: 'MA_TB, MA_KV, KIEUKV_ID, LOAITB_ID bị lặp lại'
              })
            }
          }
          if (this.danhsach_loi.length > 0) {
            console.log(this.danhsach_loi)
            this.$bvModal.show('DanhSachLoi')
            //this.danhsach_loi=[];
            this.danhsach = []
          } else {
            this.InsertThongTin()
          }
        } catch (e) {
          return alert('File chưa có dữ liệu !!!')
        }
      }
      fileReader.readAsBinaryString(files[0])
      var input = document.getElementById('upload-file')
      input.value = ''
    },
    async onXuatFileMau() {
      try {
        const workbook = XLSX.utils.book_new()
        const headers = ['MA_TB', 'MA_KV', 'KIEUKV_ID', 'LOAITB_ID']
        const sampleData = ['test01082499', 'HBT.DNP.88', '2', '58']

        const ws = XLSX.utils.aoa_to_sheet([headers, sampleData])
        XLSX.utils.book_append_sheet(workbook, ws, 'Sheet1')

        const wbout = XLSX.write(workbook, { bookType: 'xlsx', type: 'binary' })

        // Define the s2ab function
        const s2ab = (s) => {
          const buf = new ArrayBuffer(s.length)
          const view = new Uint8Array(buf)
          for (let i = 0; i < s.length; i++) view[i] = s.charCodeAt(i) & 0xff
          return buf
        }

        const blob = new Blob([s2ab(wbout)], { type: 'application/octet-stream' })

        const link = document.createElement('a')
        link.href = URL.createObjectURL(blob)
        link.download = 'FileMau.xlsx'
        link.click()
      } catch (error) {
        console.error('Error exporting file:', error)
        this.$toast.error('Có lỗi xảy ra khi xuất file mẫu')
      }
    },

    async InsertThongTin() {
      console.log('kieu', 15, 'json_data', this.danhsach)
      const res = await api.InsertThongTin(this.axios, {
        kieu: 15,
        json_data: this.danhsach
      })
      var mes = res.data.error_code ? res.data.error_code : 0
      console.log('mes', mes)
      if (mes != 'BSS-00000000') {
        this.$toast.error('Có lỗi xảy ra trong quá trình nạp dữ liệu từ file excel!')
      }
    },

    async Update() {
      const res = await api.Update_KV_File(this.axios, {
        kieu: 15
      })
      let mes = res.data.error_code ? res.data.error_code : ''

      console.log('ghilai', mes, res.data.error_code)
      if (mes != 'BSS-00000000') {
        this.$toast.error('Lỗi: ' + res.data.message)
      } else {
        this.$toast.success('Gán khu vực quản lý thuê bao thành công!”')
      }
    },

    async CapNhat() {
      console.log('token', this.$root.token.getPhanVungID(), this.$root.token.getMaTinh(), this.$root.token.getNguoiDungID(), this.$root.token.getNhanVienID(), await this.$root.token.getMachine(), this.$root.token.getUserName(), await this.$root.token.getIP())

      await this.Update()
    },

    clickButton(key) {
      if (key == 'xuatfile') {
        this.$bvModal.show('DanhSachLoi')
      }

      if (key == 'capnhat') {
        this.CapNhat()
      }
    }
  }
}
</script>
<style>
.none-padding {
  padding: 0 !important;
}
.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  max-width: 1220px !important;
}
li.non-active a {
  color: #d3d3d3 !important;
}
</style>
