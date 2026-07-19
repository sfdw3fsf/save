<script>
import Vue from 'vue'
import moment from 'moment/moment'
import { DropDownList } from '@syncfusion/ej2-dropdowns'
import { Query } from '@syncfusion/ej2-data'
import API_KEY from './API_frmDynamic'
import {
  GridComponent,
  ColumnsDirective,
  ColumnDirective,
  AggregatesDirective,
  AggregateDirective,
  Toolbar,
  Edit,
  Filter,
  Group,
  Page,
  Sort,
  Search,
  Selection,
  ToolbarService,
  EditSettingsModel,
  ToolbarOptions,
  CommandColumn,
  Grid,
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

let objParams_Dynamic = {}
let params_Dynamic_Source = {}
let elemParams_Dynamic = {}

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
      ds_CTSTCN_Dynamic: {
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
            placeholder: 'Chọn loại dịch vụ'
            // change: this.valChange()
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
      toolbar: ['Add', 'Delete', 'Update', 'Cancel'],
      editSettings: { allowEditing: false, allowAdding: true, allowDeleting: true, allowEditOnDblClick: false },
      ds_Tscn_Dynamic: {
        header: [],
        list: [],
        selected: {}
      },
      params_Dyanmic: {},

      commands:  [
        { type: 'Delete', buttonOption: { cssClass: 'e-flat', iconCss: 'e-delete e-icons', name: "DeleteCustom" } },
      ],

      limitDelete_flag: false //allow deletion
    }
  },
  provide: {
    grid: [Edit, Toolbar, Filter, Group, Page, Sort, CommandColumn]
  },

  watch: {
    'ds_CTSTCN_Dynamic.list': function (newVal, oldVal){
      console.log(newVal.length);
      console.log(this.dulieu.limit_data);
      if(!this.dulieu.enableEdit) {
        return
      }
      if(this.dulieu.limit_data && this.dulieu.limit_data != 0 && this.dulieu.limit_data <= newVal.length) {
        this.dulieu.enableEdit = false
        this.limitDelete_flag = true
      }
      else {
        this.dulieu.enableEdit = true
        this.limitDelete_flag = false
      }
    }
  },

  methods: {
    async loadData() {
      try {
        this.loading(true)
        await this.layDS_Cttscn_Hopdong_thuebao_ct()
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
      // console.log(args)
      // let row_hdtb_ct_id = args.rowData.HDTB_CT_ID
      // let index = this.ds_CTSTCN_Dynamic.list.findIndex(item => item.HDTB_CT_ID == row_hdtb_ct_id)
      // if (index !== -1) {
      //   this.ds_CTSTCN_Dynamic.list.splice(index, 1)
      // }
      // this.update_hd_thuebao_ct(this.ds_CTSTCN_Dynamic.list)
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
        this.ds_CTSTCN_Dynamic.list = [...this.ds_CTSTCN_Dynamic.list, ...hdtb_ct_list]
        console.log('this.ds_CTSTCN_Dynamic.list')
        console.log(this.ds_CTSTCN_Dynamic.list)

        await this.update_hd_thuebao_ct(this.ds_CTSTCN_Dynamic.list)
        // this.ds_CTSTCN_Dynamic.list = [...this.ds_CTSTCN_Dynamic.list]
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
        let updatedDs = ds.map(e => {
          return { ...e, PARENT_FIELD: this.dulieu.vappend_name };
        });

        console.log(updatedDs)

        let rs = await this.axios.post('web-hopdong/nhap_ds_thanh_vien/capnhat_hd_thuebao_ct_common', {
          vhdtb_id: this.dulieu.hdtb_id,
          vds_hdtb_ct: JSON.stringify(updatedDs)
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
        let url = 'web-hopdong/nhap_ds_thanh_vien/sp_lay_hdtb_ct_theo_hdtb_id_v2'
        if(this.dulieu.vtinh_id) url = 'web-hopdong/nhap_ds_thanh_vien/sp_lay_hdtb_ct_theo_hdtb_id_v2'

        let vphanvung_id = Number(this.$root.token.getPhanVungID())

        console.log('this.dulieu.vtinh_id ?? vphanvung_id')
        console.log(this.dulieu.vtinh_id ?? vphanvung_id)
        let rs = await this.axios.get(url, {
          params: {
            hdtb_id: this.dulieu.hdtb_id,
            phanvung_id: this.dulieu.vtinh_id ?? vphanvung_id,
            vfield_name: this.dulieu.vappend_name
          }
        })
        if (rs.data.data.length > 0) {
          this.ds_CTSTCN_Dynamic.list = this.ConvertUpper(rs.data.data)
        } else {
          this.ds_CTSTCN_Dynamic.list = []
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

      // const foundItem = this.ds_CTSTCN_Dynamic.list.find(item => item.SO_DT_CT === this.formData.type.value);
      // if(foundItem) {
      //   this.$toast.error("Đã tồn tại loại dịch vụ " + this.formData.type.value)
      //   return false
      // }

      return true
    },

    //start Import file
    async importFile() {
      this.loading(true)
      await this.createAndTriggerFileInput()
      this.loading(false)
      // console.log(this.ds_CTSTCN_Dynamic.list)
      // await this.update_hd_thuebao_ct(this.ds_CTSTCN_Dynamic.list)
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

        this.ds_CTSTCN_Dynamic.list = [...arrjsonData_withSameInput]

      }

      reader.readAsArrayBuffer(file)

      setTimeout(() => {
        console.log(this.ds_CTSTCN_Dynamic.list)
        this.update_hd_thuebao_ct(this.ds_CTSTCN_Dynamic.list)
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
                continue
            }
            newItem[newKey] = item[key]
          }
        }
        item.HDTB_ID = this.dulieu.hdtb_id
        return newItem
      })
    },
    onToolbarClick(args) {

      console.log(args)
      // const element = document.querySelector('.e-toolbar-item.e-overlay[aria-disabled="true"][title="Update"]');
      //
      // console.log(element)
      //
      //
      // if((args.item.text == "Update") || args.item.text == "Delete")
      // {
      //   this.update_hd_thuebao_ct(this.ds_CTSTCN_Dynamic.list)
      // }

    },
    //end Import file

    //start get dynamic ct_tscn
    async layDS_Cttscn_Hopdong_thuebao_ct() {
      try {
        let obj = {
          'vloaitb_id': this.dulieu.vfield_detail.vloaitb_id,
          'vopt_list': this.dulieu.vfield_detail.vopt_list,
          'vten_bang': 'HD_THUEBAO_CT',
          'vfield_name_cha': this.dulieu.vfield_detail.vfield_name_cha
        }
        let rs = await API_KEY.lay_danhsach_thuoctinh_datmoi_hd_thuebao_ct(obj)
        if (rs.data.error_code && rs.data.error_code === 'BSS-00000000') {
          this.ds_Tscn_Dynamic.header = rs.data.data
          this.setObj_Param_Dynamic()
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    setObj_Param_Dynamic() {
      this.ds_Tscn_Dynamic.header.forEach(e => {
        if (e.att == 4) {
          params_Dynamic_Source[e.field_name] = e.lookup_sql_response
        }
        else return
        elemParams_Dynamic[e.field_name] = undefined
        objParams_Dynamic[e.field_name] = undefined
        this.params_Dyanmic[e.field_name] = {
          create: () => {
            elemParams_Dynamic[e.field_name] = document.createElement('input')
            return elemParams_Dynamic[e.field_name]
          },
          read: () => {
            return objParams_Dynamic[e.field_name].text
          },
          destroy: () => {
            objParams_Dynamic[e.field_name].destroy()
          },
          write: () => {
            objParams_Dynamic[e.field_name] = new DropDownList({
              dataSource: params_Dynamic_Source[e.field_name],
              fields: { value: e.parent_keyfield.toLowerCase(), text: e.parent_descfield.toLowerCase() },
              enabled: true
            })
            objParams_Dynamic[e.field_name].appendTo(elemParams_Dynamic[e.field_name])
          }
        }
      })

    },
    //end get dynamic ct_tscn

    async tesst() {
      // this.$refs.gridDS_ThamSo.editSettings.newRowPosition = 'Top'
      // this.$refs.gridDS_ThamSo.refresh();
      // console.log(this.$refs.gridDS_ThamSo.editSettings)


      //this.$refs.gridDS_ThamSo.refresh();
      console.log(this.ds_CTSTCN_Dynamic.list)
      return
      await this.update_hd_thuebao_ct(this.ds_CTSTCN_Dynamic.list)
    }
  },

  async mounted() {
    await this.loadData()
  },
  async beforeDestroy () {
    if(this.dulieu.vtinh_id) return
    let datas = this.$refs.gridDS_ThamSo.dataSource
    await this.update_hd_thuebao_ct(datas)
  }
}

</script>

<template src="./frmDynamic_Hd_thuebao_ct.html">

</template>

<style scoped>

</style>
