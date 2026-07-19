<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li @click="clickButton('capnhat')" :class="this.button.capnhat ? 'active' : 'non-active'">
          <a> <span class="icon one-save"></span>Ghi lại </a>
        </li>
        <li @click="clickButton('xuatfile')" :class="this.button.xuatfile ? 'active' : 'non-active'">
          <a> <span class="icon one-download"></span>Danh sách lỗi</a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <div class="info-row marb10">
        <div class="key w20 padt0">
          <div class="one-alert text-warning f20"></div>
        </div>
        <div class="value text-main">File excel gồm 3 trường: MA_TB, MA_KV, MA_BTS <br /></div>
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
      <div class="mb-6">Danh sách thuê bao được gán</div>
      <div class="table-content">
        <DataGrid :columns="cols_grid" v-bind:dataSource="this.danhsach" :enable-paging-server="false" allowPaging showFilter :enabledSelectFirstRow="false"> </DataGrid>
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
          fieldName: 'MA_TB',
          headerText: 'Mã TB',
          allowSorting: true,
          autoFit: true,
          textAlign: 'left'
        },
        {
          fieldName: 'MA_KV',
          headerText: 'Mã địa bàn',
          allowSorting: true,
          autoFit: true,
          textAlign: 'left'
        },
        {
          fieldName: 'MA_BTS',
          headerText: 'Mã BTS',
          allowSorting: true,
          autoFit: true,
          textAlign: 'left'
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
          fieldName: 'MA_BTS',
          headerText: 'Mã BTS',
          allowSorting: true,
          autoFit: true,
          textAlign: 'left'
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
              MA_TB: ws[i].MA_TB,
              MA_KV: ws[i].MA_KV,
              MA_BTS: ws[i].MA_BTS
            })
          }

          var bien = Object.keys(excellist[0])
          if (bien[0] != 'MA_TB' || bien[1] != 'MA_KV' || bien[2] != 'MA_BTS') {
            this.danhsach = []
            this.danhsach_loi = []
            return alert('Tên cột trong file không đúng định dạng: MA_TB, MA_KV, MA_BTS hoặc 1 trong 3 cột không có dữ liệu!')
          }

          var stt = 0
          for (var i = 0; i < excellist.length; i++) {
            var bien_loi = Object.keys(excellist[i])
            if (bien_loi.length < 3) {
              stt++
              var loi = ''
              if (ws[i].MA_TB == '' || ws[i].MA_TB == null) {
                loi = loi + 'Mã TB null; '
              }
              if (ws[i].MA_KV == '' || ws[i].MA_KV == null) {
                loi = loi + 'Mã KV null; '
              }
              if (ws[i].MA_BTN == '' || ws[i].MA_BTN == null) {
                loi = loi + 'Mã BTS null; '
              }

              this.danhsach_loi.push({
                STT: stt,
                MA_TB: ws[i].MA_TB,
                MA_KV: ws[i].MA_KV,
                MA_BTS: ws[i].MA_BTS,
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
                MA_BTS: uniqueArray1[i].MA_BTS,
                LYDO_LOI: 'MA_TB, MA_KV, MA_BTS bị lặp lại'
              })
            }
          }

          if (this.danhsach_loi.length > 0) {
            console.log(this.danhsach_loi)
            this.$bvModal.show('DanhSachLoi')
            //this.danhsach_loi=[];
            this.danhsach = []
          }
          // else {
          //   this.InsertThongTin()
          // }
        } catch (e) {
          return alert('File chưa có dữ liệu !!!')
        }
      }
      fileReader.readAsBinaryString(files[0])
      var input = document.getElementById('upload-file')
      input.value = ''
    },

    // async InsertThongTin() {
    //   console.log('kieu', 15, 'json_data', this.danhsach)
    //   const res = await api.InsertThongTin(this.axios, {
    //     kieu: 15,
    //     json_data: this.danhsach
    //   })
    //   var mes = res.data.error_code ? res.data.error_code : 0
    //   console.log('mes', mes)
    //   if (mes != 'BSS-00000000') {
    //     this.$toast.error('Có lỗi xảy ra trong quá trình nạp dữ liệu từ file excel!')
    //   } else {
    //     this.$toast.success('Gán khu vực quản lý thuê bao thành công!”')
    //   }
    // },

    async CapNhat() {
      const res = await api.Update_KV_File(this.axios, {
        p_js_bts: JSON.stringify(this.danhsach)
      })
      var mes = res.data.error_code ? res.data.error_code : 0
      if (mes != 'BSS-00000000') {
        this.$toast.error('Gán khu vực quản lý thuê bao lỗi!')
      } else {
        this.$emit('saveSuccessfully')
        this.$toast.success('Gán khu vực quản lý thuê bao thành công!')
      }
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
