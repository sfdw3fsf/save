<script>
import Vue from 'vue'
import moment from 'moment/moment'
import { DropDownList } from '@syncfusion/ej2-dropdowns'
import { Query } from '@syncfusion/ej2-data'
import {
  GridComponent,
  ColumnsDirective,
  ColumnDirective,
  AggregatesDirective,
  AggregateDirective,
  Toolbar,
  Edit
} from '@syncfusion/ej2-vue-grids'
import XLSX from 'xlsx'

let state = [
  { id: 'Lắp mới', text: 'Lắp mới' },
  { id: 'Cắt hủy', text: 'Cắt hủy' },
  { id: 'Biến động', text: 'Biến động' }
]
let ipType = [
  { id: 'Tĩnh', text: 'Tĩnh' },
  { id: 'Động', text: 'Động' }
]
let ipTypeElem, stateElem, ipTypeObj, stateObj

export default {
  props: {
    dulieu: {
      type: [Object],
      require: true
    }
  },
  // computed: {
  //   hdtb_id() {
  //     return this.dulieu.hdtb_id;
  //   }
  // },
  data() {
    return {
      ds_GanSim: {
        header: [
          {
            headerText: 'Loại yêu cầu',
            fieldName: 'SO_DT_CT',
            allowSorting: false,
            width: '60',
            textAlign: 'left',
            autoFit: 'true'
          },
          {
            headerText: 'Số điện thoại /Serial number',
            fieldName: 'TRANGTHAI_CT',
            allowSorting: false,
            width: '60',
            textAlign: 'left',
            autoFit: 'true'
          },
          {
            headerText: 'IP (tĩnh/ động)',
            fieldName: 'DOMAIN_CT',
            allowSorting: false,
            width: '100',
            textAlign: 'left',
            autoFit: 'true',
            edit: this.stateParams
          },

          {
            headerText: 'APN Name/HSS Profile/PDPCP',
            fieldName: 'TEN_TB_CT',
            allowFiltering: true,
            allowSorting: false,
            width: 100,
            cssClass: 'text-center',
            allowEditing: true
          },

          {
            headerText: 'Thao tác',
            width: 50,
            commands: [{ buttonOption: { content: 'Xóa', cssClass: 'e-flat' } }],
            textAlign: 'center'
          }
        ],
        list: [],
        selected: {}
      },
      formData: {
        type: {
          list: [
            { id: 'Lắp mới', text: 'Lắp mới' },
            { id: 'Cắt hủy', text: 'Cắt hủy' },
            { id: 'Biến động', text: 'Biến động' }
          ],
          selected: {},
          value: '1'
        },
        ip_type: {
          list: [
            { id: 'Tĩnh', text: 'Tĩnh' },
            { id: 'Động', text: 'Động' }
          ],
          selected: {},
          value: '1'
        },
        serial: '',
        apn_txt: ''
      },
      stateParams: {
        create: () => {
          stateElem = document.createElement('input')
          return stateElem
        },
        read: () => {
          return stateObj.text
        },
        destroy: () => {
          stateObj.destroy()
        },
        write: () => {
          stateObj = new DropDownList({
            dataSource: state,
            fields: { value: 'id', text: 'text' },
            enabled: true,
            placeholder: 'Chọn loại dịch vụ',
            change: this.valChange()
          })
          stateObj.appendTo(stateElem)
        }
      },
      ipTypeParams: {
        create: () => {
          ipTypeElem = document.createElement('input')
          return ipTypeElem
        },
        read: () => {
          return ipTypeObj.text
        },
        destroy: () => {
          ipTypeObj.destroy()
        },
        write: () => {
          ipTypeObj = new DropDownList({
            dataSource: ipType,
            fields: { value: 'id', text: 'text' },
            enabled: true,
            placeholder: 'Chọn loại IP',
            floatLabelType: 'Never'
          })
          ipTypeObj.appendTo(ipTypeElem)
        }
      },
      toolbar: ['Add', 'Edit', 'Delete', 'Update', 'Cancel'],
      editSettings: { allowEditing: true, allowAdding: true, allowDeleting: true }
    }
  },
  // provide: {
  //   grid: [Edit, Toolbar]
  // },
  methods: {
    async loadData() {
      try {
        this.loading(true)
        await this.loadDanhSach()
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async ChonMau(args) {
      try {
        console.log(this.mau_chon)
        this.$emit('form-close', this.mau_chon)
        this.$emit('emit_func', this.mau_chon)
        this.$bvModal.hide('popupComponents')
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    // templateHyperlink() {
    //   return {
    //     template: Vue.component('columnTemplate', {
    //       template: `
    //         <div>
    //           <a href="#" @click="onClick(data.preview_link)">{{ data.preview_link }}</a>
    //         </div>`,
    //       data() {
    //         return { data: {} }
    //       },
    //       methods: {
    //         onClick(preview_link) {
    //           window.open(preview_link)
    //         }
    //       }
    //     })
    //   }
    // },
    commandClick(args) {
      let row_hdtb_ct_id = args.rowData.HDTB_CT_ID
      let index = this.ds_GanSim.list.findIndex(item => item.HDTB_CT_ID == row_hdtb_ct_id)
      if (index !== -1) {
        this.ds_GanSim.list.splice(index, 1)
      }
      this.update_hd_thuebao_ct(this.ds_GanSim.list)
    },
    grid_FocusedRowChanged(args) {
      this.mau_chon = args.data
    },
    closedlgImgEdu() {
      this.isShow.frmIMG = false
    },
    async cmdThemMoi() {
      let check = this.validateInput()
      if (!check) return
      try {
        let hdtb_ct_list = [{
          HDTB_ID: this.dulieu.hdtb_id,
          SO_DT_CT: this.formData.type.value,
          TEN_TB_CT: this.formData.apn_txt,
          LOAITB_ID: 404,
          DOMAIN_CT: this.formData.ip_type.value,
          TRANGTHAI_CT: this.formData.serial
        }]

        // this.loading(true)
        console.log(hdtb_ct_list)
        this.ds_GanSim.list = [...this.ds_GanSim.list, ...hdtb_ct_list]
        console.log('this.ds_GanSim.list')
        console.log(this.ds_GanSim.list)

        await this.update_hd_thuebao_ct(this.ds_GanSim.list)
        // this.ds_GanSim.list = [...this.ds_GanSim.list]
      } catch (e) {
        console.log(e)
        // this.$toast.error('Thêm mới thất bại!')
      } finally {
        // await this.update_hd_thuebao_ct()
        this.loading(false)
        // this.$toast.success('Thêm mới thành công!')
      }
    },
    async update_hd_thuebao_ct(ds) {
      try {
        this.loading(true)
        let rs = await this.axios.post('web-hopdong/nhap_ds_thanh_vien/capnhat_hd_thuebao_ct_common', {
          vhdtb_id: this.dulieu.hdtb_id,
          vds_hdtb_ct: JSON.stringify(ds)
        })
        console.log(rs)
        if (rs.data.data == 'ok') {
          this.$toast.success('Cập nhật thành công!')
        } else {
          this.$toast.error('Cập nhật thất bại!')
        }

        await this.loadDanhSach()
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },

    async loadDanhSach() {
      try {
        this.loading(true)

        let rs = await this.axios.get('web-hopdong/nhap_ds_thanh_vien/sp_lay_hdtb_ct_theo_hdtb_id', {
          params: {
            hdtb_id: this.dulieu.hdtb_id
          }
        })
        if (rs.data.data.length > 0) {
          this.ds_GanSim.list = this.ConvertUpper(rs.data.data)
        } else {
          this.ds_GanSim.list = []
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },

    ConvertUpper(arr) {
      return arr.map(function(item) {
        return Object.fromEntries(
          Object.entries(item).map(([k, v]) => [k.toUpperCase(), v])
        )
      })
    },
    validateInput() {
      if (this.formData.type.value == '') {
        this.$toast.error('Chưa chọn loại dịch vụ!')
        return false
      }

      if (this.formData.ip_type.value == '') {
        this.$toast.error('Chưa chọn loại IP!')
        return false
      }

      if (this.formData.serial == '') {
        this.$toast.error('Chưa nhập serial!')
        return false
      }

      if (this.formData.apn_txt == '') {
        this.$toast.error('Chưa nhập APN!')
        return false
      }

      // const foundItem = this.ds_GanSim.list.find(item => item.SO_DT_CT === this.formData.type.value);
      // if(foundItem) {
      //   this.$toast.error("Đã tồn tại loại dịch vụ " + this.formData.type.value)
      //   return false
      // }

      return true
    },

    async importFile() {
      this.loading(true)
      await this.createAndTriggerFileInput()
      this.loading(false)
      // console.log(this.ds_GanSim.list)
      // await this.update_hd_thuebao_ct(this.ds_GanSim.list)
    },
    checkFile() {
      const file = this.$refs['fileInput'].files[0]
      console.log(file)
      if (!file) {
        this.$toast.warning('Hãy chọn file để upload!')
        return false
      }
      console.log('file.type')
      console.log(file.type)
      if (file.type == 'text/plain') {
        return true
      } else {
        this.$toast.warning('File không đúng. Bạn phải chọn file Text(.txt) hoặc Excel(.xlxs)!\'')
        return false
      }

    },
    createAndTriggerFileInput() {
      // Tạo thẻ input mới
      const input = document.createElement('input')
      input.type = 'file'
      input.style.display = 'none'
      input.accept = '.xlsx, .xls' // Chỉ chấp nhận file Excel

      // Gán sự kiện thay đổi cho input
      input.addEventListener('change', this.handleFileUpload)

      // Thêm input vào body
      document.body.appendChild(input)

      // Kích hoạt input file
      input.click()

      // Xóa input sau khi sử dụng
      input.addEventListener('change', () => {
        document.body.removeChild(input)
      })
    },
    async handleFileUpload(event) {
      const file = event.target.files[0]
      if (!file) return

      const reader = new FileReader()
      reader.onload = (e) => {
        // Đọc file Excel
        const data = new Uint8Array(e.target.result)
        const workbook = XLSX.read(data, { type: 'array' })

        // Chọn sheet đầu tiên
        const sheetName = workbook.SheetNames[0]
        const worksheet = workbook.Sheets[sheetName]

        // Chuyển đổi sheet thành JSON
        const arrjsonData = XLSX.utils.sheet_to_json(worksheet)
        // console.log(arrjsonData)
        const arrjsonData_withSameInput = this.ConvertUpperWithSameInput(arrjsonData)

        this.ds_GanSim.list = [...arrjsonData_withSameInput]

      }

      reader.readAsArrayBuffer(file)

      setTimeout(() => {
        console.log(this.ds_GanSim.list)
        this.update_hd_thuebao_ct(this.ds_GanSim.list)
      }, 1000)
    },
    ConvertUpperWithSameInput(arrjsonData) {
      // Đảm bảo rằng arrjsonData là một đối tượng
      if (!Array.isArray(arrjsonData)) {
        throw new Error('Input data must be an array')
      }

      // Lặp qua từng đối tượng trong mảng
      return arrjsonData.map(item => {
        // Đảm bảo rằng item là một đối tượng
        if (typeof item !== 'object' || item === null) {
          throw new Error('Array element must be an object')
        }

        // Tạo một đối tượng mới cho từng phần tử với các khóa đã được đổi tên
        const newItem = {}
        for (const key in item) {
          if (item.hasOwnProperty(key)) {
            let newKey
            switch (key) {
              case 'Loaiyeucau':
                newKey = 'SO_DT_CT'
                break
              case 'SDT':
                newKey = 'TRANGTHAI_CT'
                break
              case 'IP':
                newKey = 'DOMAIN_CT'
                break
              case 'APN':
                newKey = 'TEN_TB_CT'
                break
              default:
                newKey = key // Giữ nguyên khóa nếu không có thay đổi
                break
            }
            newItem[newKey] = item[key]
          }
        }
        item.HDTB_ID = this.dulieu.hdtb_id
        return newItem
      })
    },
    RevertUpperWithSameInput(arrjsonData) {
      // Đảm bảo rằng arrjsonData là một đối tượng
      if (!Array.isArray(arrjsonData)) {
        throw new Error('Input data must be an array')
      }

      // Lặp qua từng đối tượng trong mảng
      return arrjsonData.map(item => {
        // Đảm bảo rằng item là một đối tượng
        if (typeof item !== 'object' || item === null) {
          throw new Error('Array element must be an object')
        }

        // Tạo một đối tượng mới cho từng phần tử với các khóa đã được đổi tên
        const newItem = {}
        for (const key in item) {
          if (item.hasOwnProperty(key)) {
            let newKey
            switch (key) {
              case 'SO_DT_CT':
                newKey = 'Loaiyeucau'
                break
              case 'TRANGTHAI_CT':
                newKey = 'SDT'
                break
              case 'DOMAIN_CT':
                newKey = 'IP'
                break
              case 'TEN_TB_CT':
                newKey = 'APN'
                break
              default:
                continue;
            }
            newItem[newKey] = item[key]
          }
        }
        item.HDTB_ID = this.dulieu.hdtb_id
        return newItem
      })
    },
    onToolbarClick() {
      // Xử lý sự kiện khi click vào toolbar
      console.log('Toolbar clicked')
      let arrData = this.RevertUpperWithSameInput(this.ds_GanSim.list)
      // Chuyển đổi mảng đối tượng thành worksheet
      const ws = XLSX.utils.json_to_sheet(arrData)

      // Tạo một workbook mới và thêm worksheet vào đó
      const wb = XLSX.utils.book_new()
      XLSX.utils.book_append_sheet(wb, ws, 'Sheet1')

      // Xuất workbook thành file Excel
      XLSX.writeFile(wb, 'data.xlsx')
    },

    exportFileMau() {
      window.location.href = "/static/files/3gvpn_mau.xlsx"
    }
  },

  async mounted() {
    await this.loadData()
  }
}

</script>

<template src="./frmGanSimVaoKenh.html">

</template>

<style scoped>

</style>
