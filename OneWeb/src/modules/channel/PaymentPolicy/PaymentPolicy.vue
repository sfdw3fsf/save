<template src="./PaymentPolicy.html"></template>
<style src="./PaymentPolicy.scss"></style>

<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import Vue from 'vue'
import api from './API'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import * as moment from 'moment'
import TailenFileModal from './parts/TaiLenFile/TaiLenFile.vue'
import {Icon} from '@iconify/vue2'
export default {
  data() {
    return {
      datetimeFormat: 'dd/MM/yyyy',
      mainPanel: 1,
      tbFileHS_data: [],
      tbFileHS_columns: [
        { fieldName: 'stt', headerText: 'STT', allowFiltering: true, allowSorting: true, width: 70, textAlign: 'center' },
        { fieldName: 'loai_file', headerText: 'Loại file', allowFiltering: true, allowSorting: true, width: 150 },
        { fieldName: 'ten_file', headerText: 'Tên File', allowFiltering: true, allowSorting: true, width: 200 },
        { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true, allowSorting: true },
        {
          fieldName: 'thaotac',
          headerText: 'Thao tác',
          textAlign: 'center',
          allowHtml: true,
          width: 100,
          template: this.colThaoTacFile(this)
        }
      ],
      tempCountSum: 0,
      tbCoChe_columns: [
        { fieldName: 'so_qd', headerText: 'Số quyết định', allowFiltering: true, allowSorting: true },
        { fieldName: 'ngay_qd', headerText: 'Ngày quyết định', allowFiltering: true, allowSorting: true },
        { fieldName: 'ngay_apdung', headerText: 'Áp dụng từ ngày', allowFiltering: true, allowSorting: true },
        { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true, allowSorting: true },
        { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, allowSorting: true },
        { fieldName: 'trangthai', headerText: 'Tình trạng', allowFiltering: true, allowSorting: true }
      ],
      tbCoChe_data: [],
      tbCongDoan_data: [],
      tbCongDoan_columns: [
        {
          fieldName: 'stt',
          headerText: 'STT',
          width: 60,
          textAlign: 'center'
        },
        {
          fieldName: 'ten_congdoan',
          headerText: 'Công đoạn',
          template: this.colCongDoanTemplate(this)
        },
        {
          fieldName: 'noidung',
          headerText: 'Nội dung',
          template: this.colNoiDungTemplate(this)
        },
        {
          fieldName: 'tyle',
          headerText: 'Tỷ lệ % thù lao',
          template: this.colTyLeTemplate(this)
        },
        {
          fieldName: 'thaotac',
          headerText: 'Thao tác',
          width: 80,
          template: this.colThaoTacTemplate(this),
          textAlign: 'center'
        }
      ],
      user_change_data: false,
      startListenEvent: false,
      controls: {
        tbtnNhapMoi: {
          enable: true
        },
        tbtnGhiLai: {
          enable: false
        },
        tbtnSua: {
          enable: false
        },
        tbtnHuy: {
          enable: false
        },
        tbtnXoa: {
          enable: false
        },
        txtSoQuyetDinh: {
          readonly: true,
          value: ''
        },
        cbbDichVu: {
          readonly: true,
          value: null,
          options: []
        },
        cbbGoiCuoc: {
          readonly: true,
          value: null,
          options: []
        },
        txtMaSPDV: {
          readonly: true,
          value: ''
        },
        cbbDVBanHanh: {
          readonly: true,
          value: null,
          options: []
        },
        cbbLoaiThuLao: {
          readonly: true,
          value: null,
          options: []
        },
        cbbCongVan: {
          readonly: false,
          value: null,
          options: [],
          text: false
        },
        txtGhiChu: {
          readonly: true,
          value: ''
        },
        dtpNgayQuyetDinh: {
          readonly: true,
          value: ''
        },
        cbbLoaiHinh: {
          readonly: true,
          value: null,
          options: []
        },
        cbbDoiTuongBan: {
          readonly: true,
          value: null,
          options: []
        },
        cbbPVApDung: {
          readonly: true,
          value: [],
          options: []
        },
        dtpApDungTuNgay: {
          readonly: true,
          value: ''
        },
        cbbCongDoan: {
          readonly: true,
          value: null,
          options: []
        },
        cbbTinhTrang: {
          readonly: true,
          value: null,
          options:
            [
    {
        "text": "Còn hiệu lực",
        "id": 1
    },
    {
        "text": "Hết hiệu lực",
        "id": 0
    }
]
        },
        txtThuLao: {
          readonly: true,
          value: 0
        },
        txtThuLaoCD: {
          readonly: true
        }
      },
      countTree: 0,
      currentTLT: 0,
      state: 'free',
      currentCoChe_id: null
    }
  },
  components: {
    breadcrumb,
    ModalChonDiaChi,
    TailenFileModal,
    Icon
  },
  methods: {
    openFileDialog() {
      this.$refs.TailenFileModal.showModal()
    },
    async getDSChinhSachThuLao() {
      this.currentCoChe_id = null
      let res = await api.getDSChinhSachThuLao(this.axios, {
        so_qd: '',
        ngay_qd: ''
      })
      if (res.data.error_code == 'BSS-00000000') {
        this.tbCoChe_data = res.data.data
      } 
      else if(res.data.error_code == 'BSS-00000204')
        return
      else this.$toast.error(res.data.message)
    },
    loadAfterUploadFile() {
      this.reload_coChe(this.currentCoChe_id)
    },
    async reload_coChe(id) {
      try {
        this.loading(true)
        this.currentCoChe_id = id
        // this.controls.txtSoQuyetDinh.value = this.tbCoChe_data.find((i) => i.coche_id == id).so_qd;
        let res = await api.chiTietCoChe(this.axios, { coche_id: id })
        if (!res || res.data.error_code != 'BSS-00000000') {
          this.$toast.error('Lỗi khi lấy thông tin chính sách')
          return
        }
        let data = res.data.data
        this.controls.cbbDichVu.value = data.ttKhaiBao[0].dichvu_id
        this.controls.txtSoQuyetDinh.value = data.ttKhaiBao[0].so_qd
        // this.controls.cbbCongVan.value = data.ttKhaiBao[0].congvan_id
        this.controls.cbbLoaiThuLao.value = data.ttKhaiBao[0].loaithulao_id
        this.controls.cbbLoaiHinh.value = data.ttKhaiBao[0].loaihinh_id
        this.controls.cbbGoiCuoc.value = data.ttKhaiBao[0].goicuoc_id
        this.controls.txtThuLao.value = data.ttKhaiBao[0].tong_thulao
        this.currentTLT = data.ttKhaiBao[0].tong_thulao
        this.controls.cbbDoiTuongBan.value = data.ttKhaiBao[0].doituongban_id
        this.controls.cbbDVBanHanh.value = data.ttKhaiBao[0].dvbanhanh_id
        this.controls.txtGhiChu.value = data.ttKhaiBao[0].ghichu
        this.controls.cbbTinhTrang.value = data.ttKhaiBao[0].tt_coche_id
        this.controls.cbbPVApDung.value = data.dsPhamvi.map((i) => i.donvi_id)
        this.controls.txtMaSPDV.value = data.ttKhaiBao[0].maspda
        this.controls.dtpApDungTuNgay.value = moment(data.ttKhaiBao[0].ngay_apdung, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY')
        const found = this.controls.cbbCongVan.options.find((i) => i.id == data.ttKhaiBao[0].congvan_id)
        if(found) {
          this.controls.cbbCongVan.text = false;
          this.controls.cbbCongVan.value = found.id
          this.controls.cbbCongVan.readonly = false
          this.controls.dtpNgayQuyetDinh.value = moment(found.ngay_qd, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY')
        }
        else{
          this.controls.cbbCongVan.text = true;
          this.controls.dtpNgayQuyetDinh.value = moment(data.ttKhaiBao[0].ngay_qd, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY')
          this.controls.cbbCongVan.value = data.ttKhaiBao[0].socongvan
          this.controls.cbbCongVan.readonly = true
      }
        // this.controls.dtpNgayQuyetDinh.value = moment(data.ttKhaiBao[0].ngay_qd, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY')
        this.tbCongDoan_data = []
        const dsCD = [...data.dsCongDoan]
        for (let i = 0; i < dsCD.length; i++) {
          const x = {
            stt: i + 1,
            congdoan_id: dsCD[i].congdoan_id,
            noidung: dsCD[i].noidung,
            tyle: dsCD[i].tyle,
            origin: true
          }
          this.tbCongDoan_data.push(x)
        }
        this.tbCongDoan_data.push({origin: false})
        if (this.controls.cbbLoaiThuLao.value == 2) {
          this.calcuTongThuLao()
          this.controls.txtThuLao.value = null
        } else {
          this.tempCountSum = 0
        }

        this.tbFileHS_data = data.dsFiles.map((i, index) => {
          return {
            stt: index + 1,
            loai_file: i.loai_file,
            ten_file: i.ten_file,
            ghichu: i.ghichu,
            file_id: i.file_id,
            url: i.url
          }
        })
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    open_frmNoDocument() {
      this.$router.push({
        path: '/admin/NoDocument'
      })
    },
    async tbCoChe_selectedRow(row) {
      try {
        this.loading(true)
        // if (this.state != 'free' && this.state != 'selecting') {
        //   this.$toast.error('Vui lòng hoàn thành hoặc huỷ thao tác hiện tại trước !')
        //   return
        // }
        this.startListenEvent = false;
        this.clearForm()
        this.currentCoChe_id = row.data.coche_id
        this.controls.txtSoQuyetDinh.value = row.data.so_qd
        let cc_id = row.data.coche_id
        await this.reload_coChe(cc_id)
        // console.log(this.controls.cbbLoaiHinh.options);
        const res = await api.getDSDVBanHanh(this.axios, {
          coche_id: cc_id
        })
        this.controls.cbbDVBanHanh.options = res.data.data.map((i) => {
          return {
            id: i.donvi_id,
            text: i.ten_dv
          }
        })
        if(this.controls.cbbTinhTrang.value == 1)

        this.tbtnSua_click()
        // this.selectingState()
      } catch (error) {
      } finally {
        this.loading(false)
        this.startListenEvent = true;
      }
    },

    async reloadDsFileHS(){
      let res = await api.chiTietCoChe(this.axios, { coche_id: this.currentCoChe_id })
      if (!res || res.data.error_code != 'BSS-00000000') {
        this.$toast.error('Lỗi khi lấy thông tin chính sách')
        return
      }
      let data = res.data.data
      this.tbFileHS_data = data.dsFiles.map((i, index) => {
        return {
          stt: index + 1,
          loai_file: i.loai_file,
          ten_file: i.ten_file,
          ghichu: i.ghichu,
          file_id: i.file_id,
          url: i.url
        }
      })
    },

    //====Chuyển form sang chế độ xem====
    selectingState() {
      this.state = 'selecting'
      this.controls.tbtnNhapMoi.enable = true
      this.controls.tbtnGhiLai.enable = false
      this.controls.tbtnSua.enable = true
      this.controls.tbtnHuy.enable = false
      this.controls.tbtnXoa.enable = true
    },

    //====Clear form========
    clearForm() {
      this.tempCountSum = 0
      this.controls.txtSoQuyetDinh.value = ''
      this.controls.cbbDichVu.value = null
      this.controls.cbbGoiCuoc.value = null
      this.controls.txtMaSPDV.value = ''
      this.controls.cbbDVBanHanh.value = null
      this.controls.cbbLoaiThuLao.value = null
      this.controls.txtGhiChu.value = ''
      this.controls.dtpNgayQuyetDinh.value = ''
      this.controls.cbbLoaiHinh.value = null
      this.controls.cbbDoiTuongBan.value = null
      this.controls.cbbPVApDung.value = []
      this.controls.dtpApDungTuNgay.value = ''
      this.tbCongDoan_data = []
      this.controls.cbbTinhTrang.value = this.controls.cbbTinhTrang.options[0].id ? this.controls.cbbTinhTrang.options[0].id : null
      this.controls.txtThuLao.value = 0
      // this.currentCoChe_id = null
      this.controls.txtSoQuyetDinh.readonly = true
      this.controls.cbbDichVu.readonly = true
      this.controls.cbbGoiCuoc.readonly = true
      this.controls.txtMaSPDV.readonly = true
      this.controls.cbbDVBanHanh.readonly = true
      this.controls.cbbLoaiThuLao.readonly = true
      this.controls.txtGhiChu.readonly = true
      this.controls.dtpNgayQuyetDinh.readonly = true
      this.controls.cbbLoaiHinh.readonly = true
      this.controls.cbbDoiTuongBan.readonly = true
      this.controls.cbbPVApDung.readonly = true
      this.controls.dtpApDungTuNgay.readonly = true
      this.controls.cbbCongVan.readonly = false;
      this.controls.cbbCongVan.text = false;
      this.controls.cbbCongVan.value = this.controls.cbbCongVan.options[0].id ? this.controls.cbbCongVan.options[0].id : null
      this.controls.cbbCongDoan.readonly = true
      this.controls.cbbTinhTrang.readonly = true
      this.controls.txtThuLao.readonly = true
      this.tbFileHS_data = []
    },
    convertToTree1(arr, orphanNodes){
      const tree = []
      const returnArr = [...arr]
      for(const item of arr){
        if(orphanNodes.includes(item.donvi_cha_id))
        {
          const children = this.convertToTree(arr, item.donvi_id)
          if (children.length) {
            item.children = children
            tree.push({
              ...item,
              id: item.donvi_id,
              label: item.ten_dv,
              children: children
            })
            this.countTree++;
          } else {
            tree.push({
              ...item,
              id: item.donvi_id,
              label: item.ten_dv
            })
            this.countTree++;
          }
          returnArr.splice(returnArr.findIndex(i => i.donvi_id == item.donvi_id), 1)
        }
      }
      return {
        tree:tree,
        arr: returnArr
      };
    },


    convertToTree(arr, parentId = null) {
      const tree = []
      for (const item of arr) {
        if (item.donvi_cha_id === parentId ) {
          const children = this.convertToTree(arr, item.donvi_id)
          if (children.length) {
            item.children = children
            tree.push({
              ...item,
              id: item.donvi_id,
              label: item.ten_dv,
              children: children
            })
            this.countTree++
          } else {
            tree.push({
              ...item,
              id: item.donvi_id,
              label: item.ten_dv
            })
            this.countTree++
          }
        }
      }
      return tree
    },

    //====Load các combobox====
    async loadData() {
      try {
        this.loading(true)
        let res = await api.getDSDichVu(this.axios, {})
        if (res.data.error_code == 'BSS-00000000')
          this.controls.cbbDichVu.options = res.data.data.map((i) => {
            return {
              id: i.dichvuvt_id,
              text: i.ten_dvvt
            }
          })
        this.controls.cbbDichVu.value = this.controls.cbbDichVu.options[0].id
        res = await api.getDSDoiTuongBan(this.axios, {})
        this.controls.cbbDoiTuongBan.options = res.data.data.map((i) => {
          return {
            id: i.dt_ban_id,
            text: i.doituongban
          }
        })
        this.controls.cbbDoiTuongBan.value = this.controls.cbbDoiTuongBan.options[0].id
        res = await api.getDSPVApDung(this.axios, {})
        const dt = res.data.data
        const orphanNodes = [];
        for (const item of dt) {
          if(orphanNodes.includes(item.donvi_cha_id)) continue;
          if(!dt.find(i => i.donvi_id == item.donvi_cha_id)) orphanNodes.push(item.donvi_cha_id);
        }
        this.countTree = 0;
        this.controls.cbbPVApDung.options = this.convertToTree1(dt, orphanNodes).tree
        res = await api.getDSDVBanHanh(this.axios, {
          coche_id: null
        })
        if(res.data.error_code == 'BSS-00000000')
        {
          this.controls.cbbDVBanHanh.options = res.data.data.map((i) => {
          return {
            id: i.donvi_id,
            text: i.ten_dv
          }
        })
        this.controls.cbbDVBanHanh.value = this.controls.cbbDVBanHanh.options[0].id
        }
        res = await api.getDSLoaiThuLao(this.axios, {})
        this.controls.cbbLoaiThuLao.options = res.data.data.map((i) => {
          return {
            id: i.loaithulao_id,
            text: i.loaithulao
          }
        })
        this.controls.cbbLoaiThuLao.value = this.controls.cbbLoaiThuLao.options[0].id
        // res = await api.getDSTinhTrang(this.axios, {})
        // this.controls.cbbTinhTrang.options = res.data.data.map((i) => {
        //   return {
        //     id: i.trangthai_id,
        //     text: i.trangthai
        //   }
        // })
        // this.controls.cbbTinhTrang.value = this.controls.cbbTinhTrang.options[0].id
        const res1 = await api.getDSCongVan(this.axios, {})
        this.controls.cbbCongVan.options = res1.data.data.map((i) => {
          return {
            id: i.congvan_id,
            text: i.congvan,
            ngay_qd: i.ngay_qd,
          }
        })
        this.controls.cbbCongVan.value = this.controls.cbbCongVan.options[0].id
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    testFunc() {
    },
    colThaoTacFile(parent) {
      return function() {
        return {
          template: Vue.component('tbFileHS_thaotac', {
            template: '<div><span @click="previewFile" class="btn-tb-file icon fa fa-eye"></span><span @click="deleteFile" class="btn-tb-file icon one-trash"></span></div>',
            methods: {
              async deleteFile() {
                let isConfirm = await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn xoá file ?', {
                  title: 'Xác nhận',
                  size: 'sm',
                  buttonSize: 'sm',
                  okVariant: 'danger',
                  okTitle: 'Đồng ý',
                  cancelTitle: 'Hủy bỏ',
                  footerClass: 'p-2',
                  hideHeaderClose: false,
                  centered: true
                })
                if (isConfirm) {
                  try {
                    this.loading(true)
                    let index = parent.tbFileHS_data.findIndex((item) => item.stt == this.data.stt)
                    let res = await api.deleteFile(parent.axios, {
                      coche_id: parent.currentCoChe_id,
                      filehs_id: parent.tbFileHS_data[index].file_id
                    })
                    if (res.data.error_code == 'BSS-00000000') {
                      parent.$toast.success('Xoá file thành công !')
                      parent.reload_coChe(parent.currentCoChe_id)
                    } else parent.$toast.error(res.data.message)
                  } catch (error) {
                  } finally {
                    this.loading(false)
                  }
                }
              },
              async previewFile() {
                const res = await api.link_view_file(this.axios, { list_file: this.data.url })
                try {
                  var old_url = new URL(res.data.data[0].file_name)
                } catch (_) {
                  old_url = null
                }
                window.open(old_url ? old_url : res.data.data[0].file_url, '_blank')
              }
            }
          })
        }
      }
    },
    colThaoTacFile(parent) {
      return function() {
        return {
          template: Vue.component('tbFileHS_thaotac', {
            template: '<div><span @click="previewFile" class="btn-tb-file icon fa fa-eye"></span><span @click="deleteFile" class="btn-tb-file icon one-trash"></span></div>',
            methods: {
              async deleteFile() {
                let isConfirm = await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn xoá file ?', {
                  title: 'Xác nhận',
                  size: 'sm',
                  buttonSize: 'sm',
                  okVariant: 'danger',
                  okTitle: 'Đồng ý',
                  cancelTitle: 'Hủy bỏ',
                  footerClass: 'p-2',
                  hideHeaderClose: false,
                  centered: true
                })
                if (isConfirm) {
                  try {
                    this.loading(true)
                    let index = parent.tbFileHS_data.findIndex((item) => item.stt == this.data.stt)
                    let res = await api.deleteFile(parent.axios, {
                      coche_id: parent.currentCoChe_id,
                      filehs_id: parent.tbFileHS_data[index].file_id
                    })
                    if (res.data.error_code == 'BSS-00000000') {
                      parent.$toast.success('Xoá file thành công !')
                      parent.reload_coChe(parent.currentCoChe_id)
                    } else parent.$toast.error(res.data.message)
                  } catch (error) {
                  } finally {
                    this.loading(false)
                  }
                }
              },
              async previewFile() {
                const res = await api.link_view_file(this.axios, { list_file: this.data.url })
                try {
                  var old_url = new URL(res.data.data[0].file_name)
                } catch (_) {
                  old_url = null
                }
                window.open(old_url ? old_url : res.data.data[0].file_url, '_blank')
              }
            }
          })
        }
      }
    },
    colCongDoanTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<select2  :disabled="readonly" v-show="show" :options="options" v-model="model" />`,
            data() {
              return {
                model: null
              }
            },
            mounted() {
              this.model = this.data.congdoan_id
            },
            computed: {
              options() {
                return parent.controls.cbbCongDoan.options
              },
              readonly() {
                return parent.controls.cbbCongDoan.readonly || parent.controls.txtThuLaoCD.readonly
              },
              show(){
                return this.data.stt
              }
            },
            watch: {
              model: {
                handler() {
                  let index = parent.tbCongDoan_data.findIndex((item) => item.stt == this.data.stt)
                  if (this.model) parent.tbCongDoan_data[index].congdoan_id = this.model
                },
                deep: true
              }
            }
          }
        }
      }
    },
    colNoiDungTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<input :disabled="readonly" v-show="show" type="text" v-model="model" class="form-control" />`,
            data() {
              return {
                model: null
              }
            },
            mounted() {
              this.model = this.data.noidung
            },
            watch: {
              model: {
                handler() {
                  let index = parent.tbCongDoan_data.findIndex((item) => item.stt == this.data.stt)
                  if (this.model) parent.tbCongDoan_data[index].noidung = this.model
                },
                deep: true
              }
            },
            computed: {
              readonly() {
                return parent.controls.cbbCongDoan.readonly || parent.controls.txtThuLaoCD.readonly
              },
              show() {
                return this.data.stt
              }
            }
          }
        }
      }
    },
    colTyLeTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<input v-if="show" style="text-align:right" :disabled="readonly" @keyup="change" type="number" v-model="model" class="form-control" />
            <span class="temp_sum" style="text-align:right" v-else>{{tempCountSum}}</span>   `,
            data() {
              return {
                model: null
              }
            },
            mounted() {
              this.model = this.data.tyle
            },
            methods: {
              change() {
                parent.calcuTongThuLao()
              }
            },
            watch: {
              model: {
                handler() {
                  let index = parent.tbCongDoan_data.findIndex((item) => item.stt == this.data.stt)
                  if (this.model) parent.tbCongDoan_data[index].tyle = this.model
                },
                deep: true
              }
            },
            computed: {
              readonly() {
                if (parent.state != 'updating' && parent.state != 'inserting') return true
                return parent.controls.txtThuLaoCD.readonly
              },
              show(){
                return this.data.stt
              },
              tempCountSum(){
                return parent.tempCountSum
              }
            }
          }
        }
      }
    },
    colThaoTacTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<div v-show="show">
              <span @click="updateCD" :class="readonly?'disabled': ''"  class="btn-tb-file icon one-save"></span>
              <span @click="deleteCD" :class="readonly?'disabled': ''" class="btn-tb-file icon one-trash"></span>
              </div>`,
            computed: {
              readonly() {
                return parent.controls.cbbCongDoan.readonly
              },
              index() {
                return parent.tbCongDoan_data.findIndex((item) => item.stt == this.data.stt)
              },
              show(){
                return this.data.stt
              }
            },
            methods: {
              async updateCD() {
                if (!parent.tbCongDoan_data[this.index].congdoan_id) {
                  parent.$toast.error('Chưa chọn công đoạn !')
                  return
                }
                if (!parent.tbCongDoan_data[this.index].tyle && parent.controls.cbbLoaiThuLao.value == 2) {
                  parent.$toast.error('Yêu cầu nhập tỷ lệ thù lao !')
                  return
                }
                let check = parent.tbCongDoan_data.findIndex((item) => item.congdoan_id == parent.tbCongDoan_data[this.index].congdoan_id && item.stt != this.data.stt)
                if (check != -1) {
                  parent.$toast.error('Công đoạn tương tự đã tồn tại! Vui lòng kiểm tra lại')
                  return
                }
                if (parent.controls.cbbLoaiThuLao.value == 1) {
                  if (this.data.origin) {
                    //cập nhật
                    if(!parent.tbCongDoan_data[this.index].congdoan_id){
                      parent.$toast.error('Chưa chọn công đoạn !')
                      return
                    }
                    let res = await api.updateCongDoan(parent.axios, {
                      coche_id: parent.currentCoChe_id,
                      congdoan_id: parent.tbCongDoan_data[this.index].congdoan_id,
                      tyle: null,
                      noidung: parent.tbCongDoan_data[this.index].noidung
                    })
                    if (res.data.error_code == 'BSS-00000000') {
                      parent.$toast.success('Cập nhật công đoạn thành công !')
                      parent.reload_coChe(parent.currentCoChe_id)
                    } else parent.$toast.error(res.data.message)
                  } else {
                    //thêm mới
                    if(!parent.tbCongDoan_data[this.index].congdoan_id){
                      parent.$toast.error('Chưa chọn công đoạn !')
                      return
                    }
                    let res = await api.insertCongDoan(parent.axios, {
                      coche_id: parent.currentCoChe_id,
                      congdoan_id: parent.tbCongDoan_data[this.index].congdoan_id,
                      tyle: null,
                      noidung: parent.tbCongDoan_data[this.index].noidung
                    })
                    if (res.data.error_code == 'BSS-00000000') {
                      parent.$toast.success('Thêm mới công đoạn thành công !')
                      parent.reload_coChe(parent.currentCoChe_id)
                    } else parent.$toast.error(res.data.message)
                  }
                } else {
                  if (!this.data.origin) {
                    //thêm mới
                    let res = await api.insertCongDoan(parent.axios, {
                      coche_id: parent.currentCoChe_id,
                      congdoan_id: parent.tbCongDoan_data[this.index].congdoan_id,
                      tyle: parent.tbCongDoan_data[this.index].tyle * 1,
                      noidung: parent.tbCongDoan_data[this.index].noidung
                    })
                    if (res.data.error_code == 'BSS-00000000') {
                      parent.$toast.success('Thêm mới công đoạn thành công !')
                      parent.reload_coChe(parent.currentCoChe_id)
                    } else parent.$toast.error(res.data.message)
                  } else {
                    //cập nhật
                    let res = await api.updateCongDoan(parent.axios, {
                      coche_id: parent.currentCoChe_id,
                      congdoan_id: parent.tbCongDoan_data[this.index].congdoan_id,
                      tyle: parent.tbCongDoan_data[this.index].tyle * 1,
                      noidung: parent.tbCongDoan_data[this.index].noidung
                    })
                    if (res.data.error_code == 'BSS-00000000') {
                      parent.$toast.success('Cập nhật công đoạn thành công !')
                      parent.reload_coChe(parent.currentCoChe_id)
                    } else parent.$toast.error(res.data.message)
                  }
                }
              },
              async deleteCD() {
                let isConfirm = await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn xóa công đoạn ?', {
                  title: 'Xác nhận',
                  size: 'sm',
                  buttonSize: 'sm',
                  okVariant: 'danger',
                  okTitle: 'Đồng ý',
                  cancelTitle: 'Hủy bỏ',
                  footerClass: 'p-2',
                  hideHeaderClose: false,
                  centered: true
                })
                if (isConfirm) {
                  try {
                    this.loading(true)
                    let res = await api.deleteCongDoan(parent.axios, { coche_id: parent.currentCoChe_id, congdoan_id: parent.tbCongDoan_data[this.index].congdoan_id })
                    if (res.data.error_code == 'BSS-00000000') {
                      this.$toast.success('Xóa công đoạn thành công')
                      parent.reload_coChe(parent.currentCoChe_id)
                    } else this.$toast.error(res.data.message)
                  } catch (error) {
                  } finally {
                    this.loading(false)
                  }
                } else return
              }
            }
          }
        }
      }
    },
    addCongDoan(){
      this.tbCongDoan_data.pop()
      this.tbCongDoan_data.push({stt: this.tbCongDoan_data.length + 1, origin: false})
      this.tbCongDoan_data.push({origin: false})
      this.$refs.tbCongDoan.refresh()
    },
    refreshCoChe(){
      this.reload_coChe(this.currentCoChe_id)
    },
    async tbtnXoa_click() {
      if (!this.currentCoChe_id) {
        this.$toast.error('Vui lòng chọn chính sách cần xóa')
        return
      }
      let isConfirm = await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn xóa chính sách này ?', {
        title: 'Xác nhận',
        size: 'sm',
        buttonSize: 'sm',
        okVariant: 'danger',
        okTitle: 'Đồng ý',
        cancelTitle: 'Hủy bỏ',
        footerClass: 'p-2',
        hideHeaderClose: false,
        centered: true
      })
      if (isConfirm) {
        try {
          this.loading(true)
          let res = await api.deleteCoChe(this.axios, { coche_id: this.currentCoChe_id })
          if (res.data.error_code == 'BSS-00000000') {
            this.$toast.success('Xóa chính sách thành công')
            this.getDSChinhSachThuLao()
            // this.clearForm()
          } else this.$toast.error(res.data.message)
        } catch (error) {
        } finally {
          this.loading(false)
        }
      } else return
    },
    calcuTongThuLao() {
      let sum = 0
      if (this.controls.cbbLoaiThuLao.value == 1) return
      this.tbCongDoan_data.forEach((item) => {
        if (item.tyle && item.congdoan_id) sum += item.tyle * 1
      })
      this.tempCountSum = sum
    },
    tbtnHuy_click() {
      this.state = 'free'
      this.clearForm()
      this.getDSChinhSachThuLao()
      // this.reload_coChe(this.currentCoChe_id)
    },
    tbtnSua_click() {
      if (this.currentCoChe_id == null) {
        this.$toast.error('Vui lòng chọn chính sách cần sửa !')
        return
      }
      this.state = 'updating'
      this.controls.tbtnNhapMoi.enable = false
      this.controls.tbtnGhiLai.enable = true
      this.controls.tbtnSua.enable = false
      this.controls.tbtnHuy.enable = true
      this.controls.tbtnXoa.enable = false
      this.controls.txtSoQuyetDinh.readonly = false
      this.controls.cbbDichVu.readonly = false
      this.controls.cbbGoiCuoc.readonly = false
      this.controls.txtMaSPDV.readonly = false
      this.controls.cbbDVBanHanh.readonly = false
      this.controls.cbbLoaiThuLao.readonly = false
      this.controls.txtGhiChu.readonly = false
      this.controls.dtpNgayQuyetDinh.readonly = true
      this.controls.cbbLoaiHinh.readonly = false
      this.controls.cbbDoiTuongBan.readonly = false
      this.controls.cbbPVApDung.readonly = false
      this.controls.dtpApDungTuNgay.readonly = false
      this.controls.cbbCongDoan.readonly = false
      this.controls.cbbTinhTrang.readonly = false
      if (this.controls.cbbLoaiThuLao.value == 1) this.controls.txtThuLao.readonly = false
    },
    async tbtnNhapMoi_click() {
      this.state = 'inserting'
      this.clearForm()
      this.controls.tbtnNhapMoi.enable = false
      this.controls.tbtnGhiLai.enable = true
      this.controls.tbtnSua.enable = false
      this.controls.tbtnHuy.enable = true
      this.controls.tbtnXoa.enable = false
      this.controls.txtSoQuyetDinh.readonly = false
      this.controls.cbbDichVu.readonly = false
      this.controls.cbbGoiCuoc.readonly = false
      this.controls.txtMaSPDV.readonly = false
      this.controls.cbbDVBanHanh.readonly = false
      this.controls.cbbLoaiThuLao.readonly = false
      this.controls.cbbLoaiThuLao.value = this.controls.cbbLoaiThuLao.options[0].id
      this.controls.txtGhiChu.readonly = false
      this.controls.dtpNgayQuyetDinh.readonly = true
      this.controls.cbbLoaiHinh.readonly = false
      this.controls.cbbDoiTuongBan.readonly = false
      this.controls.cbbPVApDung.readonly = false
      this.controls.dtpApDungTuNgay.readonly = false
      this.controls.cbbCongVan.readonly = false
      this.controls.cbbCongVan.text = false;
      this.controls.cbbCongVan.value = this.controls.cbbCongVan.options[0].id ? this.controls.cbbCongVan.options[0].id : null
      this.controls.dtpNgayQuyetDinh.value = moment(this.controls.cbbCongVan.options[0].ngay_qd,'DD/MM/YYYY HH:mm:SS').format('DD/MM/YYYY')
      this.controls.cbbCongDoan.readonly = false
      this.controls.cbbTinhTrang.readonly = false
      this.controls.txtThuLao.readonly = false
      const res = await api.getDSDVBanHanh(this.axios, {
        coche_id: null
      })
      this.controls.cbbDVBanHanh.options = res.data.data.map((i) => {
        return {
          id: i.donvi_id,
          text: i.ten_dv
        }
      })
      this.controls.cbbDVBanHanh.value = this.controls.cbbDVBanHanh.options[0].id
      this.currentCoChe_id = null
    },
    async tbtnGhiLai_click() {
      if (this.state != 'inserting' && this.state != 'updating') {
        this.$toast.error('Vui lòng hoàn thành hoặc huỷ thao tác hiện tại trước !')
        return
      }
      // this.controls.txtSoQuyetDinh.value = this.controls.txtSoQuyetDinh.value.trim()
      this.controls.txtMaSPDV.value = this.controls.txtMaSPDV.value.trim()
      if (!this.controls.cbbCongVan.value) {
        this.$toast.error('Vui lòng nhập số quyết định !')
        return
      }
      if (!this.controls.dtpNgayQuyetDinh.value) {
        this.$toast.error('Vui lòng chọn ngày quyết định !')
        return
      }
      if (!this.controls.cbbDichVu.value) {
        this.$toast.error('Vui lòng chọn dịch vụ !')
        return
      }
      if (!this.controls.cbbLoaiHinh.value) {
        this.$toast.error('Vui lòng chọn loại hình !')
        return
      }
      // if (!this.controls.cbbGoiCuoc.value) {
      //   this.$toast.error('Vui lòng chọn gói cước !')
      //   return
      // }
      if (!this.controls.txtMaSPDV.value) {
        this.$toast.error('Vui lòng nhập mã sản phẩm dịch vụ !')
        return
      }
      if (!this.controls.cbbDVBanHanh.value) {
        this.$toast.error('Vui lòng chọn đơn vị ban hành !')
        return
      }
      if (!this.controls.cbbDoiTuongBan.value) {
        this.$toast.error('Vui lòng chọn đối tượng bán !')
        return
      }
      if (!this.controls.dtpApDungTuNgay.value) {
        this.$toast.error('Vui lòng chọn ngày áp dụng !')
        return
      }
      if (this.controls.cbbLoaiThuLao.value == 1 && this.controls.txtThuLao.value <= 0) {
        this.$toast.error('Chưa nhập thù lao tổng hoặc giá trị nhập vào không hợp lệ !')
        return
      }
      if (!this.controls.cbbTinhTrang.value) {
        this.$toast.error('Vui lòng chọn trạng thái !')
        return
      }
      if(this.controls.cbbPVApDung.value.length == 0 || !this.controls.cbbPVApDung.value[0]){
        this.$toast.error('Vui lòng chọn phạm vi áp dụng !')
        return
      }
      // if(this.controls.txtThuLao.value <= 0 && this.controls.cbbLoaiThuLao.value == 1){
      //   this.$toast.error('Tỉ lệ thù lao phải lớn hơn 0')
      //   return
      // }
      if(this.tbCongDoan_data.length){
        let check = false;
        for(let i = 0; i < this.tbCongDoan_data.length; i++){
          if(this.tbCongDoan_data[i].tyle <=0 || this.tbCongDoan_data[i].tyle > 100){
            this.$toast.error('Tỉ lệ thù lao phải lớn hơn 0 và bé hơn 100 !')
            check = true
            break
          }
        }
        if(check) return
      }
      if(this.controls.cbbLoaiThuLao.value == 2 && this.tbCongDoan_data.length<=1){
          this.$toast.error('Phải có ít nhất một công đoạn. Vui lòng kiểm tra lại !')
          return
      }
      if (this.state == 'inserting') {
  try {
    this.loading(true)
    // if (this.controls.cbbLoaiThuLao.value == 2) this.controls.txtThuLao.value = 0
    let res = await api.insertCoChe(this.axios, {
      congvan_id: this.controls.cbbCongVan.value,
      ngay_qd: moment(this.controls.dtpNgayQuyetDinh.value).format('DD/MM/YYYY'),
      dichvu_id: this.controls.cbbDichVu.value * 1,
      loaihinh_id: this.controls.cbbLoaiHinh.value * 1,
      goicuoc_id: this.controls.cbbGoiCuoc.value * 1,
      masppa: this.controls.txtMaSPDV.value,
      dvbanhanh_id: this.controls.cbbDVBanHanh.value * 1,
      doituongban_id: this.controls.cbbDoiTuongBan.value * 1,
      ngay_apdung: moment(this.controls.dtpApDungTuNgay.value).format('DD/MM/YYYY'),
      ghichu: this.controls.txtGhiChu.value,
      loaithulao_id: this.controls.cbbLoaiThuLao.value * 1,
      tongthulao: this.controls.cbbLoaiThuLao.value == 2 ? null : this.controls.txtThuLao.value * 1,
      tt_coche_id: this.controls.cbbTinhTrang.value * 1,
      donvvi_ins: JSON.stringify(this.controls.cbbPVApDung.value).toString(),
      nguoi_ins: this.$root.token.getNhanVienID() * 1,
      ngay_ins: moment().format('DD/MM/YYYY'),
      phamvi_id: null
    })
    if (res.data.error_code == 'BSS-00000201') {
      this.$toast.success('Thêm mới chính sách thành công')
      //thêm danh sách công đoạn
      let cc_id = res.data.data
      for (let i = 0; i < this.tbCongDoan_data.length; i++) {
        if (this.tbCongDoan_data[i].congdoan_id) {
          let res = await api.insertCongDoan(this.axios, {
            coche_id: cc_id,
            congdoan_id: this.tbCongDoan_data[i].congdoan_id,
            tyle: this.tbCongDoan_data[i].tyle == 0 ? null : this.tbCongDoan_data[i].tyle * 1,
            noidung: this.tbCongDoan_data[i].noidung
          })
          if (res.data.error_code != 'BSS-00000000') {
            this.$toast.error('Thêm mới công đoạn thất bại ! ')
          }
        }
      }
      this.state = 'free'
      this.clearForm()
      this.getDSChinhSachThuLao()
    } else {
      this.$toast.error(res.data.message)
      return
    }
  } catch (error) {
  } finally {
    this.loading(false)

  }
} else if (this.state == 'updating') {
  try {
    this.loading(true)
    this.state = 'free'
    // if (this.controls.cbbLoaiThuLao.value == 2) this.controls.txtThuLao.value = 0
    let res = await api.updateCoChe(this.axios, {
      coche_id: this.currentCoChe_id,
      congvan_id: this.controls.cbbCongVan.text? null: this.controls.cbbCongVan.value,
      ngay_qd: moment(this.controls.dtpNgayQuyetDinh.value, 'DD/MM/YYYY HH:mm:SS').format('DD/MM/YYYY'),
      dichvu_id: this.controls.cbbDichVu.value * 1,
      loaihinh_id: this.controls.cbbLoaiHinh.value * 1,
      goicuoc_id: this.controls.cbbGoiCuoc.value * 1,
      masppa: this.controls.txtMaSPDV.value,
      dvbanhanh_id: this.controls.cbbDVBanHanh.value * 1,
      doituongban_id: this.controls.cbbDoiTuongBan.value * 1,
      ngay_apdung: moment(this.controls.dtpApDungTuNgay.value, 'DD/MM/YYYY HH:mm:SS').format('DD/MM/YYYY'),
      ghichu: this.controls.txtGhiChu.value,
      loaithulao_id: this.controls.cbbLoaiThuLao.value * 1,
      tongthulao: this.controls.cbbLoaiThuLao.value == 2 ? null : this.controls.txtThuLao.value * 1,
      tt_coche_id: this.controls.cbbTinhTrang.value * 1,
      donvvi_ins: JSON.stringify(this.controls.cbbPVApDung.value).toString(),
      nguoi_ins: this.$root.token.getNhanVienID() * 1,
      ngay_ins: moment().format('DD/MM/YYYY'),
      phamvi_id: null
    })
    if (res.data.error_code == 'BSS-00000000') {
      this.$toast.success('Cập nhất chính sách thành công')
      //cập nhật công đoạn
      for (let i = 0; i < this.tbCongDoan_data.length; i++) {
        let res
        if (this.tbCongDoan_data[i].congdoan_id && this.tbCongDoan_data[i].origin) {
          res = await api.updateCongDoan(this.axios, {
            coche_id: this.currentCoChe_id,
            congdoan_id: this.tbCongDoan_data[i].congdoan_id,
            tyle: this.tbCongDoan_data[i].tyle == 0 ? null : this.tbCongDoan_data[i].tyle * 1,
            noidung: this.tbCongDoan_data[i].noidung
          })
        }
        if (this.tbCongDoan_data[i].congdoan_id && !this.tbCongDoan_data[i].origin) {
          //thêm mới công đoạn
          if(!this.tbCongDoan_data[i].congdoan_id){
            this.$toast.error('Chưa chọn công đoạn ! Huỷ quá trình khai báo công đoạn')
            break
          }
          else{
            res = await api.insertCongDoan(this.axios, {
            coche_id: this.currentCoChe_id,
            congdoan_id: this.tbCongDoan_data[i].congdoan_id,
            tyle: this.tbCongDoan_data[i].tyle == 0 ? null : this.tbCongDoan_data[i].tyle * 1,
            noidung: this.tbCongDoan_data[i].noidung
          })
        }
        if (res.data.error_code != 'BSS-00000000') {
          this.$toast.error('Cập nhật hoặc thêm mới công đoạn thất bại ! ')
          break
        }
          }
      }

    } else this.$toast.error(res.data.message)
  } catch (error) {
  } finally {
    this.loading(false)
    this.state = 'free'
      this.clearForm()
      this.getDSChinhSachThuLao()
  }
}

    }
  },
  async mounted() {
    this.loading(true)
    this.state = 'free'
    await this.loadData()
    await this.getDSChinhSachThuLao()
    this.loading(false)
    // this.tbCongDoan_data.push(this.tbCongDoan_add);
  },
  watch: {

    controlsWatch: {
      handler(newControls,oldControls){
        if(this.startListenEvent)
        for(const control in newControls){
          if(newControls[control].value != oldControls[control].value){

              console.log('somthing change');
            this.tempData = oldControls;
            this.user_change_data = true;
            break
          }
        }
      },
      deep: true
    },
    //====== Combobox Dịch vụ change ======
    'controls.cbbDichVu.value': async function(val) {
      try {
        this.loading(true)
        let res = await api.getDSLoaiHinh(this.axios, { dichvuvt_id: val })
        this.controls.cbbLoaiHinh.options = res.data.data.map((i) => {
          return {
            id: i.loaitb_id,
            text: i.loaihinh_tb,
            ma_loaihinh: i.ma_lhtb
          }
        })
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },

    //====== Combobox Loại hình change ======
    'controls.cbbLoaiHinh.value': async function(val) {
      // this.controls.cbbGoiCuoc.value = null
      this.controls.cbbGoiCuoc.options = []
      for(let i = 0;i < this.controls.cbbLoaiHinh.options.length; i++){
        if(this.controls.cbbLoaiHinh.options[i].id == val){
          this.controls.txtMaSPDV.value = this.controls.cbbLoaiHinh.options[i].ma_loaihinh
          console.log(this.controls.cbbLoaiHinh.options[i].ma_loaihinh);
          break
        }
      }
      try {
        this.loading(true)
        let res = await api.getDSGoiCuoc(this.axios, { loaihinh_id: val * 1, dichvu_id: this.controls.cbbDichVu.value * 1 })
        this.controls.cbbGoiCuoc.options = res.data.data;
        // if(this.controls.cbbGoiCuoc.options.length && ){
        //   this.controls.cbbGoiCuoc.value = this.controls.cbbGoiCuoc.options[0].id
        // }

      } catch (error) {
      } finally {
        this.loading(false)
      }
    },

    //====Change đối tượng bán=======
    'controls.cbbDoiTuongBan.value': async function(val) {
      try {
        this.loading(true)
        let res = await api.getDSCongDoan(this.axios, { dtban_id: val })
        this.controls.cbbCongDoan.options = res.data.data.map((i) => {
          return {
            id: i.congdoan_id,
            text: i.ten_congdoan
          }
        })
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },

    //====Thù lao tổng thì khoá nhập thù lao công đoạn=====
    'controls.cbbLoaiThuLao.value': async function(val) {
      //1: thù lao tổng, 2: thù lao công đoạn
      if (val == 1) {
        console.log(this.state);
        if (this.state == 'updating' || this.state == 'inserting') {
          this.controls.txtThuLao.readonly = false
        }
        this.controls.txtThuLaoCD.readonly = true
        this.controls.txtThuLao.value = this.currentTLT
      } else {
        this.controls.txtThuLaoCD.readonly = false
        this.controls.txtThuLao.readonly = true
        this.controls.txtThuLao.value = null
      }
    },
    'controls.cbbCongVan.value': function(val){
      const index = this.controls.cbbCongVan.options.findIndex((item) => item.id == val)

      console.log('ngay_qd',this.controls.cbbCongVan.options[index].ngay_qd);
      this.controls.dtpNgayQuyetDinh.value = moment(this.controls.cbbCongVan.options[index].ngay_qd, 'DD/MM/YYYY HH:mm:SS').format('DD/MM/YYYY')
    }

    // totalTyLe(newVal) {
    //   this.controls.txtThuLao.value = newVal
    // }
  },
  computed: {
    totalTyLe() {
      if (this.controls.cbbLoaiThuLao.value == 2) {
        return this.tbCongDoan_data.reduce((total, item) => total + (item.tyle * 1 || 0), 0)
      }
      return this.controls.txtThuLao.value
    },
    controlsWatch() {
      return JSON.parse(JSON.stringify(this.controls))
    },
  }
}
</script>
