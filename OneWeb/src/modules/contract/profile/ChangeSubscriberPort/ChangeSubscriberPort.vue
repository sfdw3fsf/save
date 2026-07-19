<template src='./ChangeSubscriberPort.html'></template>
<style scoped src='./ChangeSubscriberPort.scss'></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import API from './API'
import Utils from './const'

export default {
  components: {breadcrumb},
  name: 'ChangeSubscriberPort',
  data() {
    return {
      header: {
        title: 'ĐỔI PORT THUÊ BAO',
        list: [
          {name: 'Lập hợp đồng', link: {name: 'Ui.cards'}},
          {name: 'Lắp đặt mới', link: {name: 'Ui.cards'}}
        ]
      },
      data: {
        subscribers: []
      },
      columns: Utils.gridCols,
      current: 0,
      selectedRows: [],
      vkieu: 2,
      btnVisible: {
        btnKichHoat: false,
        btnHoanThien: false,
        btnXuatFile: false
      }
    }
  },
  async mounted() {
    await this.layDsThueBao()
  },
  methods: {
    clear() {
      this.selectedRows = []
    },

    focusItem(index, data) {
      this.current = index
    },

    selectedItemsChanged(data) {
      // console.log(data)
    },

    getSelectedRecords() {
      return this.$refs.subscribers.getSelectedRecords()
    },

    kiemTraDuLieu() {
      let rows = this.getSelectedRecords()
      if (rows.length <= 0) {
        this.$toast.error('Bạn chưa chọn thuê bao nào!')
        return false
      }

      return true
    },

    async reload() {
      await this.layDsThueBao()
    },

    async layDsThueBao() {
      this.loading(true)
      try {
        let rs = await API.lay_ds_thuebao_doiport(this.axios, {
          vnguoidung_id: this.$root.token.getNguoiDungID(),
          vkieu: this.vkieu
        })
        this.$set(this.data, 'subscribers', rs.data.data)
        return rs.data.message
      } catch (err) {
        this.$toast.error(err.message)
      } finally {
        this.loading(false)
      }
    },

    async btnGhiLai() {
      if (this.kiemTraDuLieu()) {
        this.loading(true)
        try {
          let rows = this.getSelectedRecords()
          let pData = []
          rows.forEach((item, index) => {
            pData.push({'DOIPORT_ID': item.doiport_id})
          })

          let data = {
            data: JSON.stringify(pData),
            nguoi_cn: '',
            may_cn: '',
            ip_cn: '',
            id_phanvung: this.$root.token.getPhanVungID()
          }

          let rs = await API.capnhat_doiport_thuebao(this.axios, data)

          if (rs.data.error === '200') {
            this.$toast.success('Cập nhật thuê bao đổi port thành công')
            this.reload()
          } else {
            this.$toast.error('Cập nhật thuê bao đổi port thất bại')
          }
        } catch (err) {
          this.$toast.error(err.message)
        } finally {
          this.loading(false)
        }
      }
    },

    async btnHoanThien() {
      if (this.kiemTraDuLieu()) {
        this.loading(true)
        try {
          let rows = this.getSelectedRecords()
          let pData = []
          rows.forEach((item, index) => {
            pData.push({
              DOIPORT_ID: item.doiport_id,
              VCI_VPI_ID_CU: item.vci_vpi_id_cu,
              PORT_ID_CU: item.port_id_cu,
              VCI_VPI_ID: item.vci_vpi_id,
              PORT_ID: item.port_id
            })
          })

          let data = {
            data: JSON.stringify(pData),
            nguoi_cn: '',
            may_cn: '',
            ip_cn: '',
            id_phanvung: this.$root.token.getPhanVungID()
          }

          let rs = await API.hoanthien_doiport_thuebao2(this.axios, data)
          if (rs.data.error === '200') {
            this.reload()
            this.$toast.success('Cập nhật thành công')
          } else {
            this.$toast.error('Cập nhật lỗi')
          }
        } catch (err) {
          console.log(err.message)
          this.$toast.error('Cập nhật lỗi')
        } finally {
          this.loading(false)
        }
      }
    },

    async btnKichHoat() {
      if (this.kiemTraDuLieu()) {
        this.loading(true)
        try {
          let rows = this.getSelectedRecords()
          let rowsSelected = []
          for (let row of rows) {
            if (row.hople == 0) {
              this.$root.$toast.error(`Thuê bao ${row.ma_tb} port cũ không đúng`)
              return
            }

            if (row.saiportmoi == 1) {
              this.$root.$toast.error(`Thuê bao ${row.ma_tb} port mới không tồn tại`)
              return
            }

            // if (row.selected.toString() === 's1') {
            rowsSelected.push(row)
            // }
          }

          if (rowsSelected.length <= 0) {
            this.$root.$toast.error(`Bạn chưa chọn thuê bao nào`)
            return
          }

          for (let item of rowsSelected) {
            if (item.bras_id_moi == null) {
              this.$root.$toast.error(`Lỗi khi thực hiện với account ${item.ma_tb}: port mới (bras mới) không tồn tại`)
              return
            }

            if (!await this.DoiCongAcccountVisa(
              item.ma_tb,
              item.port_moi,
              item.vci_moi,
              item.vpi_moi,
              item.slot_moi,
              item.bras_id_moi,
              item.ma_tram_moi,
              item.system_moi,
              item.rack_moi,
              item.shelf_moi,
              item.selfid01_moi,
              item.selfid02_moi,
              item.selfid03_moi,
              item.selfid04_moi)) {
              return
            }
          }
        } catch (err) {
          console.log(err.message)
          this.$toast.error('Kích hoạt thông số port lên hệ thống visas/portal lỗi : ' + err.message)
        } finally {
          this.loading(false)
        }
      }
    },

    async DoiCongAcccountVisa(ma_tb,
                              port_moi,
                              vci_moi,
                              vpi_moi,
                              slot_moi,
                              bras_id_moi,
                              ma_tram_moi,
                              system_moi,
                              rack_moi,
                              shelf_moi,
                              selfid01_moi,
                              selfid02_moi,
                              selfid03_moi,
                              selfid04_moi) {
      try {
        let dslamId = ma_tram_moi
        //Kiem tra xem dang ky 4 tham so hay 7 tham so
        let dslam = await this.lay_tt_dslam(dslamId)
        let {thamso, dslam_vdc_id} = dslam
        if (thamso === 4) {
          if (!await this.doiCong({
            'accountModel': {
              'accountName': ma_tb
            },
            'attributes': [
              {
                'type': 'PORT',
                'value': port_moi,
              },
              {
                'type': 'VCI',
                'value': vci_moi,
              },
              {
                'type': 'VPI',
                'value': vpi_moi,
              },
              {
                'type': 'SLOT',
                'value': slot_moi,
              },
              {
                'type': 'DSLAM-ID',
                'value': dslam_vdc_id,
              },
              {
                'type': 'BRAS-ID',
                'value': bras_id_moi,
              },
              {
                'type': 'PARAMETER',
                'value': 'VISANEW',
              }
            ],
          })) {
            return false
          } else {
            this.$root.$toast.success('Đổi cổng Account ' + ma_tb + ' trên Visa thành công !')
            return true
          }
        } else if (thamso === 7) {
          if (!await this.doiCong(
            {
              'accountModel': {
                'accountName': ma_tb
              },
              'attributes': [
                {
                  'type': 'PORT',
                  'value': port_moi,
                },
                {
                  'type': 'VCI',
                  'value': vci_moi,
                },
                {
                  'type': 'VPI',
                  'value': vpi_moi,
                },
                {
                  'type': 'SLOT',
                  'value': slot_moi,
                },
                {
                  'type': 'DSLAM-ID',
                  'value': dslam_vdc_id,
                },
                {
                  'type': 'BRAS-ID',
                  'value': bras_id_moi,
                },
                {
                  'type': 'RACKID',
                  'value': rack_moi,
                },
                {
                  'type': 'SYSTEMID',
                  'value': system_moi,
                },
                {
                  'type': 'SELFID',
                  'value': shelf_moi,
                },
                {
                  'type': 'PARAMETER',
                  'value': 'VISANEW',
                }
              ],
            })) {
            return false
          } else {
            this.$root.$toast.success('Đổi cổng Account ' + ma_tb + ' trên Visa thành công !')
            return true
          }
        } else if (thamso === 9) {
          if (!await this.doiCong({
            'accountModel': {
              'accountName': ma_tb
            },
            'attributes': [
              {
                'type': 'PORT',
                'value': port_moi,
              },
              {
                'type': 'VLAN_ID',
                'value': vci_moi,
              },
              {
                'type': 'AXCI',
                'value': vpi_moi,
              },
              {
                'type': 'SLOT',
                'value': slot_moi,
              },
              {
                'type': 'DSLAM-ID',
                'value': dslam_vdc_id,
              },
              {
                'type': 'BRAS-ID',
                'value': bras_id_moi,
              },
              {
                'type': 'SYSTEMID',
                'value': system_moi,
              },
              {
                'type': 'SELFID',
                'value': selfid01_moi,
              },
              {
                'type': 'OLT_SLOT',
                'value': selfid02_moi,
              },
              {
                'type': 'OLT_PORT',
                'value': selfid03_moi,
              },
              {
                'type': 'ONUID',
                'value': selfid04_moi,
              },
              {
                'type': 'PARAMETER',
                'value': 'VISANEW',
              }
            ],
          })) {
            return false
          } else {
            this.$root.$toast.success('Đổi cổng Account ' + ma_tb + ' trên Visa thành công !')
            return true
          }
        } else {
          this.$root.$toast.error('Tham số port của thuê bao ' + ma_tb + ' không thuộc loại 4, 7, 9. Vui lòng kiểm tra lại !')
          return false
        }
        // else {
        //   this.$root.$toast.error('Tham số port của thuê bao ' + ma_tb + ' không thuộc loại 4, 7, 9. Vui lòng kiểm tra lại !')
        //     return false
        // }
        return true
      } catch (ex) {
        this.$root.$toast.error('Có lỗi xảy ra trong quá trình đổi cổng thuê bao ' + ma_tb + ' trên Visa: ' + ex.message)
        return false
      }
    },

    async lay_tt_dslam(dslam_id) {
      try {
        let response = await API.lay_tt_dslam(this.axios, dslam_id)
        if (response.data != null && response.data.data != null) {
          return response.data.data
        }
      } catch (err) {
        console.log(err.message)
        this.$root.$toast.error('Lỗi ở hàm lay_tt_dslam: ' + err.message)
      }

      return {}
    },

    async doiCong(objParam) {
      try {
        let response = await API.changePortMegaVNN(this.axios, objParam)
        if (response.data != null && response.data.data === 'ok' && response.data.errorCode === 0) {
          return true
        } else {
          //console.log(objParam)
          this.$root.$toast.error('Lỗi khi đổi port thuê bao ' + objParam.accountModel.accountName + ': ' + response.data.faultString)
        }
      } catch (err) {
        console.log(err.message)
        this.$root.$toast.error('Lỗi ở hàm doiCong của thuê bao ' + objParam.accountModel.accountName + ': ' + err.message)
      }

      return false
    },

    btnExportExcel() {
      if (this.data.subscribers.length <= 0) {
        this.$root.$toast.error('Chưa có dữ liệu trên lưới để xuất excel!')
        return
      }

      //ẩn cột checkbox nếu allowCheckBox
      let cols = this.$refs.subscribers.grid.getColumns()
      cols[0].visible = false
      let excelExportProperties = {
        dataSource: this.data.subscribers,
        enableFilter: true,
        fileName: 'ds_thuebao_doiport.xlsx'
      }
      this.$refs.subscribers.excelExport(excelExportProperties)
        .then((e) => cols[0].visible = true)//hiện lại khi export xong
    },

    btnOpenFileDialog() {
      $('#fileDialog').click()
    },

    onChangeFile(event) {
      let files = event.target.files
      if (files.length > 0) {
        let file = files[0]
        $('#filePath').val(file.name)
        $('#fileDialog').val('')

        this.readExcel(file)
      } else {
        $('#filePath').val('')
      }
    },

    readExcel(file) {
      if (file.type !== 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {
        this.$toast.error('Định dạng file không đúng! Vui lòng tải file Biểu mẫu')
        return
      }

      let reader = new FileReader()
      reader.onload = (e) => {
        try {
          this.loading(true)
          this.processDataImport(Utils.readFile(e))
        } catch (ex) {
          this.$toast.error('Cấu trúc file excel không chính xác, vui lòng tải mẫu!')
        } finally {
          this.loading(false)
        }
      }

      reader.onerror = function (ex) {
        console.log(ex)
      }


      reader.readAsBinaryString(file)
    },

    async processDataImport(data) {
      if (data.length <= 0) {
        this.$toast.error('Vui lòng nhập đầy đủ dữ liệu trong file Excel')
        return
      } else {
        let jsData = this.formatData(data)
        let reqData = this.getDoiPortFileReqData(jsData)
        await API.sp_doiport_tmp_file(this.axios, reqData)
          .then(rs => {
            if (rs.data.error === '200') {
              this.$toast.success('Lưu thành công')
              this.reload()
            } else {
              this.$toast.error('Lưu không thành công')
            }
          })
          .catch(err => {
            this.$toast.error('Lưu không thành công')
          })

      }
    },

    formatData(data) {
      return data.map(item => {
        let sample = {
          'MA_TB': '',
          'MA_TRAM_CU': 0,
          'SYSTEM_CU': '',
          'RACK_CU': 0,
          'SHELF_CU': 0,
          'SELFID01_CU': item.SELFID_CU != null ? item.SELFID_CU : 0,
          'SELFID02_CU': item.OLT_SLOT_CU != null ? item.OLT_SLOT_CU : 0,
          'SELFID03_CU': item.OLT_PORT_CU != null ? item.OLT_PORT_CU : 0,
          'SELFID04_CU': item.ONUID_CU != null ? item.ONUID_CU : 0,
          'SLOT_CU': 0,
          'PORT_CU': 0,
          'VPI_CU': 0,
          'VCI_CU': 0,
          'MA_TRAM_MOI': 0,
          'SYSTEM_MOI': '',
          'RACK_MOI': 0,
          'SHELF_MOI': 0,
          'SELFID01_MOI': item.SELFID_MOI != null ? item.SELFID_MOI : 0,
          'SELFID02_MOI': item.OLT_SLOT_MOI != null ? item.OLT_SLOT_MOI : 0,
          'SELFID03_MOI': item.OLT_PORT_MOI != null ? item.OLT_PORT_MOI : 0,
          'SELFID04_MOI': item.ONUID_MOI != null ? item.ONUID_MOI : 0,
          'SLOT_MOI': 0,
          'PORT_MOI': 0,
          'VPI_MOI': 0,
          'VCI_MOI': 0,
          'THAMSO': 0,
          'TRANGTHAI': 0
        }

        return {...sample, ...item}
      })
    },

    getDoiPortFileReqData(jsData) {
      return {
        nguoidung_id: this.$root.token.getNguoiDungID(),
        js_data: JSON.stringify(jsData),
        nguoi_cn: '',
        may_cn: '',
        ip_cn: ''
      }
    },

    daGhiLaiChange(e) {
      let isChecked = e.target.checked
      this.btnVisible.btnHoanThien = isChecked
      this.btnVisible.btnKichHoat = this.btnVisible.btnXuatFile = false
      if (isChecked) {
        this.vkieu = 0
      } else {
        this.vkieu = 2
      }

      this.reload()
    },

    daHoanThienChange(e) {
      let isChecked = e.target.checked
      this.btnVisible.btnHoanThien = false
      this.btnVisible.btnKichHoat = this.btnVisible.btnXuatFile = isChecked
      if (isChecked) {
        this.vkieu = 1
      } else {
        this.vkieu = 2
      }

      this.reload()
    },

    dataBound() {
      this.$refs.subscribers.grid.autoFitColumns()
    },
    queryCellInfo(args) {
      if (args.data.hople !== 1) {
        $(args.cell).css({'color': 'red'})
      }

      if (args.data.saiportmoi == 1) {
        $(args.cell).css({'color': '#ff00a1'})
      }

      switch (args.column.field) {
        case 'hople':
          if (args.data.hople == 1) {
            args.cell.innerText = 'Hợp lệ'
          } else {
            args.cell.innerText = 'Không hợp lệ'
          }
          break
        case 'saiportmoi':
          if (args.data.saiportmoi == 1) {
            args.cell.innerText = 'Trùng'
          } else {
            args.cell.innerText = 'Không trùng'
          }
          break
      }
    }
  }
}
</script>
