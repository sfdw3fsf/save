<template src="./frmDangKyDVGTBC.html"></template>
<script>
import Vue from 'vue'
import DataGridCustom from './DataGrid'

export default {
  name: 'frmDangKyDVGTBC',
  props: {
    dsDK_DVGT: {
      type: Object,
      default: {
        dsdvgt_ban: [],
        dsdvgt_tc: []
      }
    },
    dichvuvt_id: {
      type: Number,
      default: null
    },
    lapmoi: {
      type: Boolean,
      default: false
    }
  },
  components: {
    DataGridCustom
  },
  data () {
    return {
      editSettings: { allowEditing: true },
      selectOptions: { type: 'Single', mode: 'row', allowColumnSelection: false },
      clickDangKyOnGrid: false,
      clickHuyOnGrid: false,
      clickSuaOnGrid: false,
      clickDangKyOnGridTC: false,
      clickHuyOnGridTC: false,
      clickSuaOnGridTC: false,
      dgvDanhSach: {
        checked_DK: [],
        isCheckAll_DK: false,
        checked_Huy: [],
        isCheckAll_Huy: false,
        checked_Sua: [],
        isCheckAll_Sua: false,
        value: {},
        tableKey: 0,
        headers: [
          { fieldName: 'stt', headerText: 'STT', allowFiltering: true, allowSorting: false, textAlign: 'Center', width: 80 },
          { fieldName: 'maDvGt', headerText: 'Mã dịch vụ', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'tenDvGt', headerText: 'Tên dịch vụ', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'suDung', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, autoFit: 'true', visible: true},
          { fieldName: 'dangKy',
            headerText: '',
            allowFiltering: false,
            allowSorting: false,
            width: '120px',
            textAlign: 'Center',
            headerTemplate: () => {
              return {
                template: Vue.component('action-check-all', {
                  template: `
                                            <div class="check-action">
                                                <input type="checkbox" v-model="parent.dgvDanhSach.isCheckAll_DK" class="check"
                                                    @change="onChangeProcessed(data)" />
                                                <span class="name">Đăng ký</span>
                                            </div>
                                        `,
                  data: function () {
                    return { data: {} }
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onChangeProcessed (value) {
                      this.parent.clickDangKyOnGrid = false
                    }
                  }
                })
              }
            },
            template: function () {
              return {
                template: Vue.component('action-check', {
                  template: `
                                        <div class="check-action">
                                            <input v-model="parent.dgvDanhSach.checked_DK" :value="data.maDvGt"
                                                type="checkbox" class="check" @change="onChangeProcessed(data)"
                                            />
                                            <span class="name"></span>
                                        </div>
                                    `,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onChangeProcessed (value) {
                      var obj = this.parent.dgvDanhSach.data.find(
                        (f) => f.maDvGt == value.maDvGt
                      )
                      obj.checkedDK = true
                    }
                  }
                })
              }
            }
          },
          { fieldName: 'huy',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            width: '90px',
            textAlign: 'Center',
            headerTemplate: () => {
              return {
                template: Vue.component('action-check-all', {
                  template: `
                                            <div class="check-action">
                                                <input type="checkbox" v-model="parent.dgvDanhSach.isCheckAll_Huy" class="check"
                                                    @change="onChangeProcessed(data)" />
                                                <span class="name">Hủy</span>
                                            </div>
                                        `,
                  data: function () {
                    return { data: {} }
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onChangeProcessed (value) {
                      this.parent.clickHuyOnGrid = false
                    }
                  }
                })
              }
            },
            template: function () {
              return {
                template: Vue.component('action-check', {
                  template: `
                                        <div class="check-action">
                                            <input v-model="parent.dgvDanhSach.checked_Huy" :value="data.maDvGt"
                                                type="checkbox" class="check" @change="onChangeProcessed(data)"
                                            />
                                            <span class="name"></span>
                                        </div>
                                    `,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onChangeProcessed (value) {
                      var obj = this.parent.dgvDanhSach.data.find(
                        (f) => f.maDvGt == value.maDvGt
                      )
                      obj.checkedHuy = true
                    }
                  }
                })
              }
            }
          },
          { fieldName: 'sua',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            width: '90px',
            textAlign: 'Center',
            headerTemplate: () => {
              return {
                template: Vue.component('action-check-all', {
                  template: `
                                            <div class="check-action">
                                                <input type="checkbox" v-model="parent.dgvDanhSach.isCheckAll_Sua" class="check"
                                                    @change="onChangeProcessed(data)" />
                                                <span class="name">Sửa</span>
                                            </div>
                                        `,
                  data: function () {
                    return { data: {} }
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onChangeProcessed (value) {
                      this.parent.clickSuaOnGrid = false
                    }
                  }
                })
              }
            },
            template: function () {
              return {
                template: Vue.component('action-check', {
                  template: `
                                        <div class="check-action">
                                            <input v-model="parent.dgvDanhSach.checked_Sua" :value="data.maDvGt"
                                                type="checkbox" class="check" @change="onChangeProcessed(data)"
                                            />
                                            <span class="name"></span>
                                        </div>
                                    `,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onChangeProcessed (value) {
                      var obj = this.parent.dgvDanhSach.data.find(
                        (f) => f.maDvGt == value.maDvGt
                      )
                      obj.checkedSua = true
                    }
                  }
                })
              }
            }
          },
          { fieldName: 'cuoc_ld', headerText: 'Cước lắp đặt', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'vat_ld', headerText: 'Vat lắp đặt', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'cuoc_sd', headerText: 'Phí duy trì', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'vat_sd', headerText: 'Vat duy trì', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'tien', headerText: 'Tổng tiền', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'noidung', headerText: 'Nội dung/Số lượng', allowFiltering: true, allowSorting: false, allowEditing: true, autoFit: 'true' },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true, allowSorting: false, autoFit: 'true' }
        ],
        data: []
      },
      dgvDanhSachTC: {
        checked_DKTC: [],
        isCheckAll_DKTC: false,
        checked_HuyTC: [],
        isCheckAll_HuyTC: false,
        checked_SuaTC: [],
        isCheckAll_SuaTC: false,
        value: {},
        tableKey: 0,
        headers: [
          { fieldName: 'stt', headerText: 'STT', allowFiltering: true, allowSorting: false, textAlign: 'Center', width: 80 },
          { fieldName: 'maDvGt', headerText: 'Mã dịch vụ', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'tenDvGt', headerText: 'Tên dịch vụ', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'suDung', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, autoFit: 'true', visible: true },
          { fieldName: 'dangKy',
            headerText: '',
            allowFiltering: false,
            allowSorting: false,
            width: '120px',
            textAlign: 'Center',
            headerTemplate: () => {
              return {
                template: Vue.component('action-check-all', {
                  template: `
                                            <div class="check-action">
                                                <input type="checkbox" v-model="parent.dgvDanhSach.isCheckAll_DKTC" class="check"
                                                    @change="onChangeProcessed(data)" />
                                                <span class="name">Đăng ký</span>
                                            </div>
                                        `,
                  data: function () {
                    return { data: {} }
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onChangeProcessed (value) {
                      this.parent.clickDangKyOnGridTC = false
                    }
                  }
                })
              }
            },
            template: function () {
              return {
                template: Vue.component('action-check', {
                  template: `
                                        <div class="check-action">
                                            <input v-model="parent.dgvDanhSachTC.checked_DKTC" :value="data.maDvGt"
                                                type="checkbox" class="check" @change="onChangeProcessed(data)"
                                            />
                                            <span class="name"></span>
                                        </div>
                                    `,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onChangeProcessed (value) {
                      var obj = this.parent.dgvDanhSachTC.data.find(
                        (f) => f.maDvGt == value.maDvGt
                      )
                      obj.checkedDK = true
                    }
                  }
                })
              }
            }
          },
          { fieldName: 'huy',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            width: '90px',
            textAlign: 'Center',
            headerTemplate: () => {
              return {
                template: Vue.component('action-check-all', {
                  template: `
                                            <div class="check-action">
                                                <input type="checkbox" v-model="parent.dgvDanhSachTC.isCheckAll_HuyTC" class="check"
                                                    @change="onChangeProcessed(data)" />
                                                <span class="name">Hủy</span>
                                            </div>
                                        `,
                  data: function () {
                    return { data: {} }
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onChangeProcessed (value) {
                      this.parent.clickHuyOnGridTC = false
                    }
                  }
                })
              }
            },
            template: function () {
              return {
                template: Vue.component('action-check', {
                  template: `
                                        <div class="check-action">
                                            <input v-model="parent.dgvDanhSachTC.checked_HuyTC" :value="data.maDvGt"
                                                type="checkbox" class="check" @change="onChangeProcessed(data)"
                                            />
                                            <span class="name"></span>
                                        </div>
                                    `,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onChangeProcessed (value) {
                      var obj = this.parent.dgvDanhSachTC.data.find(
                        (f) => f.maDvGt == value.maDvGt
                      )
                      obj.checkedHuy = true
                    }
                  }
                })
              }
            }
          },
          { fieldName: 'sua',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            width: '90px',
            textAlign: 'Center',
            headerTemplate: () => {
              return {
                template: Vue.component('action-check-all', {
                  template: `
                                            <div class="check-action">
                                                <input type="checkbox" v-model="parent.dgvDanhSachTC.isCheckAll_SuaTC" class="check"
                                                    @change="onChangeProcessed(data)" />
                                                <span class="name">Sửa</span>
                                            </div>
                                        `,
                  data: function () {
                    return { data: {} }
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onChangeProcessed (value) {
                      this.parent.clickSuaOnGridTC = false
                    }
                  }
                })
              }
            },
            template: function () {
              return {
                template: Vue.component('action-check', {
                  template: `
                                        <div class="check-action">
                                            <input v-model="parent.dgvDanhSachTC.checked_SuaTC" :value="data.maDvGt"
                                                type="checkbox" class="check" @change="onChangeProcessed(data)"
                                            />
                                            <span class="name"></span>
                                        </div>
                                    `,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onChangeProcessed (value) {
                      var obj = this.parent.dgvDanhSachTC.data.find(
                        (f) => f.maDvGt == value.maDvGt
                      )
                      obj.checkedSua = true
                    }
                  }
                })
              }
            }
          },
          { fieldName: 'cuoc_ld', headerText: 'Cước lắp đặt', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'vat_ld', headerText: 'Vat lắp đặt', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'cuoc_sd', headerText: 'Phí duy trì', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'vat_sd', headerText: 'Vat duy trì', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'tien', headerText: 'Tổng tiền', allowFiltering: true, allowSorting: false, autoFit: 'true' },
          { fieldName: 'noidung', headerText: 'Nội dung/Số lượng', allowFiltering: true, allowSorting: false, allowEditing: true, autoFit: 'true' },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true, allowSorting: false, autoFit: 'true' }
        ],
        data: []
      }
    }
  },
  methods: {
    refresh () {
      this.dgvDanhSach.data = []
      this.dgvDanhSachTC.data = []
    },
    async openDialog () {
      try {
        this.loading(true)
        this.refresh()
        this.$refs.dlg_DangKyDVGTBC.show()
        await this.DanhSach()
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async DanhSach () {
      try {
        this.dgvDanhSach.data = this.dsDK_DVGT.dsdvgt_ban.slice()
        if (this.lapmoi) {
          let objIndex = this.dgvDanhSach.headers.findIndex(obj => obj.fieldName == 'suDung'); this.dgvDanhSach.headers[objIndex].visible = false
          objIndex = this.dgvDanhSach.headers.findIndex(obj => obj.fieldName == 'huy'); this.dgvDanhSach.headers[objIndex].visible = false
          objIndex = this.dgvDanhSach.headers.findIndex(obj => obj.fieldName == 'sua'); this.dgvDanhSach.headers[objIndex].visible = false
        }
        this.dgvDanhSachTC.data = this.dsDK_DVGT.dsdvgt_tc.slice()
        if (this.lapmoi) {
          let objIndex = this.dgvDanhSachTC.headers.findIndex(obj => obj.fieldName == 'suDung'); this.columnsPYC[objIndex].visible = false
          objIndex = this.dgvDanhSachTC.headers.findIndex(obj => obj.fieldName == 'huy'); this.dgvDanhSachTC.headers[objIndex].visible = false
          objIndex = this.dgvDanhSachTC.headers.findIndex(obj => obj.fieldName == 'sua'); this.dgvDanhSachTC.headers[objIndex].visible = false
        }
      } catch (exp) {
        console.log(exp)
      }
    },
    Unformat_tien (s) {
      s = s.toLowerCase().trim()
      let ketqua = ''
      for (let i = 0; i < s.length; i++) {
        if (String(s[i]) != ',' && String(s[i]) != '.' && String(s[i]) != ' ') {
          ketqua = ketqua + s[i]
        }
      }
      return ketqua.trim()
    },
    btnChapNhan_Click () {
      let _flagdvgtdk = false
      let _flagdvgt_kodk = true
      for (let i = 0; i < this.dgvDanhSach.data.length; i++) {
        let isDK = false, isSua = false
        let checkDK = this.dgvDanhSach.checked_DK.find((item) => item == this.dgvDanhSach.data[i].maDvGt)
        if (checkDK != undefined && checkDK != null) isDK = true
        let checkSua = this.dgvDanhSach.checked_Sua.find((item) => item == this.dgvDanhSach.data[i].maDvGt)
        if (checkSua != undefined && checkSua != null) isSua = true

        if ((isDK || isSua) && this.dgvDanhSach.data[i].flag == '1' && (this.dgvDanhSach.data[i].noidung == undefined || this.dgvDanhSach.data[i].noidung == '')) {
          this.$toast.error("Bạn chưa nhập '" + this.dgvDanhSach.data[i].thongbao + "' vào ô Nội dung cho dịch vụ '" + this.dgvDanhSach.data[i].ten_dvgt + "' !")
          return
        }

        if ((isDK || isSua) && this.dgvDanhSach.data[i].flag == '2' && (this.dgvDanhSach.data[i].noidung == undefined || this.dgvDanhSach.data[i].noidung == '')) {
          this.$confirm("Bạn có nhập '" + this.dgvDanhSach.data[i].thongbao + "' cho dịch vụ '" + this.dgvDanhSach.data[i].ten_dvgt + "' hay không ?", {
            confirmButtonText: 'Có', cancelButtonText: 'Không'
          }).then(async v => {
            if (v) {

            } else {
              if ((isDK || isSua) && this.dgvDanhSach.data[i].kieu_dl == '1' &&
                                    this.dgvDanhSach.data[i].noidung != undefined && this.dgvDanhSach.data[i].noidung != '') {
                let s = this.dgvDanhSach.data[i].noidung
                if (isNAN(this.Unformat_tien(s))) {
                  this.$toast.error("Nội dung của dịch vụ '" + this.dgvDanhSach.data[i].ten_dvgt + ' phải là kiểu số !')
                  return
                }
              }

              /* nhapt them 28/11/2016
                            * Thực hiện kiểm tra xem có chọn dịch vụ mặc định không?
                            * Nếu trường flag =3(dichvu_khac) thì bắt buộc chọn 1 trong các mặc định đó
                            * Nếu không muốn mặc định chọn, có thể bỏ các mặc định trong bảng dichvu_khac di (đưa flag về 0)
                            */
              if (this.dgvDanhSach.data[i].flag == '3') {
                _flagdvgt_kodk = false
                if (isDK) { _flagdvgtdk = true }
              }

              if (this.dgvDanhSach.data[i].cuoc_ld == '') { this.dgvDanhSach.data[i].cuoc_ld = '0' }
              if (this.dgvDanhSach.data[i].vat_ld == '') { this.dgvDanhSach.data[i].vat_ld = '0' }
              if (this.dgvDanhSach.data[i].cuoc_sd == '') { this.dgvDanhSach.data[i].cuoc_sd = '0' }
              if (this.dgvDanhSach.data[i].vat_sd == '') { this.dgvDanhSach.data[i].vat_sd = '0' }
              if (this.dgvDanhSach.data[i].tien == '') { this.dgvDanhSach.data[i].tien = '0' }

              if (isDK && this.dgvDanhSach.data[i].maDvGt == 'PAYTV' &&
                                    this.dgvDanhSach.data[i].noidung != undefined && this.dgvDanhSach.data[i].noidung != '') {
                try {
                  if (Number(this.dgvDanhSach.data[i].noidung) < 3) {
                    this.$toast.error('Thông tin đăng ký PAYTV trọn gói 20.000 không hợp lệ!')
                    return
                  }
                } catch (e) {
                  this.$toast.error('Thông tin đăng ký PAYTV trọn gói 20.000 không hợp lệ!')
                }
              }
            }
          })
        }
      }

      if (!_flagdvgtdk && !_flagdvgt_kodk) {
        this.$toast.error('Hãy chọn một trong các dịch vụ gia tăng mặc định bắt buộc')
        return
      }

      if (!this.Set_dsDK_DVGT_TC()) return

      this.dsDK_DVGT.dsdvgt_ban = this.dgvDanhSach.data.slice()
      this.dsDK_DVGT.dsdvgt_tc = this.dgvDanhSachTC.data.slice()
      this.$emit('chapnhan', this.dsDK_DVGT)
      this.$refs.dlg_DangKyDVGTBC.hide()
    },
    Set_dsDK_DVGT_TC () {
      let _flagdvgtdk = false
      let _flagdvgt_kodk = true
      for (let i = 0; i < this.dgvDanhSachTC.data.length; i++) {
        let isDK = false, isSua = false
        let checkDK = this.dgvDanhSachTC.checked_DKTC.find((item) => item == this.dgvDanhSachTC.data[i].maDvGt)
        if (checkDK != undefined && checkDK != null) isDK = true
        let checkSua = this.dgvDanhSachTC.checked_SuaTC.find((item) => item == this.dgvDanhSachTC.data[i].maDvGt)
        if (checkSua != undefined && checkSua != null) isSua = true

        if ((isDK || isSua) && this.dgvDanhSachTC.data[i].flag == '1' && (this.dgvDanhSach.data[i].noidung == undefined || this.dgvDanhSachTC.data[i].noidung == '')) {
          this.$toast.error("Bạn chưa nhập '" + this.dgvDanhSachTC.data[i].thongbao + "' vào ô Nội dung cho dịch vụ '" + this.dgvDanhSachTC.data[i].ten_dvgt + "' !")
          return false
        }

        if ((isDK || isSua) && this.dgvDanhSachTC.data[i].flag == '2' && (this.dgvDanhSach.data[i].noidung == undefined || this.dgvDanhSachTC.data[i].noidung == '')) {
          this.$confirm("Bạn có nhập '" + this.dgvDanhSachTC.data[i].thongbao + "' cho dịch vụ '" + this.dgvDanhSachTC.data[i].ten_dvgt + "' hay không ?", {
            confirmButtonText: 'Có', cancelButtonText: 'Không'
          }).then(async v => {
            if (v) {
              return false
            } else {
              if ((isDK || isSua) && this.dgvDanhSachTC.data[i].kieu_dl == '1' &&
                                    this.dgvDanhSachTC.data[i].noidung != undefined && this.dgvDanhSachTC.data[i].noidung != '') {
                let s = this.dgvDanhSachTC.data[i].noidung
                if (isNAN(this.Unformat_tien(s))) {
                  this.$toast.error("Nội dung của dịch vụ '" + this.dgvDanhSachTC.data[i].ten_dvgt + ' phải là kiểu số !')
                  return false
                }
              }

              /* nhapt them 28/11/2016
                            * Thực hiện kiểm tra xem có chọn dịch vụ mặc định không?
                            * Nếu trường flag =3(dichvu_khac) thì bắt buộc chọn 1 trong các mặc định đó
                            * Nếu không muốn mặc định chọn, có thể bỏ các mặc định trong bảng dichvu_khac di (đưa flag về 0)
                            */
              if (this.dgvDanhSachTC.data[i].flag == '3') {
                _flagdvgt_kodk = false
                if (isDK) { _flagdvgtdk = true }
              }

              if (this.dgvDanhSachTC.data[i].cuoc_ld == '') { this.dgvDanhSachTC.data[i].cuoc_ld = '0' }
              if (this.dgvDanhSachTC.data[i].vat_ld == '') { this.dgvDanhSachTC.data[i].vat_ld = '0' }
              if (this.dgvDanhSachTC.data[i].cuoc_sd == '') { this.dgvDanhSachTC.data[i].cuoc_sd = '0' }
              if (this.dgvDanhSachTC.data[i].vat_sd == '') { this.dgvDanhSachTC.data[i].vat_sd = '0' }
              if (this.dgvDanhSachTC.data[i].tien == '') { this.dgvDanhSachTC.data[i].tien = '0' }

              if (isDK && this.dgvDanhSachTC.data[i].maDvGt == 'PAYTV' &&
                                    this.dgvDanhSachTC.data[i].noidung != undefined && this.dgvDanhSachTC.data[i].noidung != '') {
                try {
                  if (Number(this.dgvDanhSachTC.data[i].noidung) < 3) {
                    this.$toast.error('Thông tin đăng ký PAYTV trọn gói 20.000 không hợp lệ!')
                    return false
                  }
                } catch (e) {
                  this.$toast.error('Thông tin đăng ký PAYTV trọn gói 20.000 không hợp lệ!')
                  return false
                }
              }
            }
          })
        }
      }

      return true
    },
    gview_DanhSach_queryCellInfo (args) {
      if (args.data != undefined) {
        let iFind = -1
        for (let i = 0; i < this.dgvDanhSach.data.length; i++) {
          if (this.dgvDanhSach.data[i].maDvGt == args.data.maDvGt) {
            iFind = i
            break
          }
        }

        this.dgvDanhSach.data[iFind].noidung = args.data.noidung != undefined ? args.data.noidung : ''
      }
    },
    gview_DanhSachTC_queryCellInfo (args) {
      if (args.data != undefined) {
        let iFind = -1
        for (let i = 0; i < this.dgvDanhSachTC.data.length; i++) {
          if (this.dgvDanhSachTC.data[i].maDvGt == args.data.maDvGt) {
            iFind = i
            break
          }
        }

        this.dgvDanhSachTC.data[iFind].noidung = args.data.noidung != undefined ? args.data.noidung : ''
      }
    }
  },
  watch: {
    'dgvDanhSach.isCheckAll_DK': {
      handler: function (value) {
        if (value) {
          this.dgvDanhSach.data.forEach((f) => {
            f.checked = true
          })
          this.dgvDanhSach.checked_DK = this.dgvDanhSach.data.map(
            (f) => f.maDvGt
          )
        } else {
          if (!this.clickDangKyOnGrid) {
            this.dgvDanhSach.data.forEach((f) => {
              f.checked = false
            })
            this.dgvDanhSach.checked_DK = []
          }
        }
      },
      deep: true
    },
    'dgvDanhSach.checked_DK': {
      handler: function (value) {
        this.clickDangKyOnGrid = true
        this.dgvDanhSach.isCheckAll_DK = value.length > 0 && value.length === this.dgvDanhSach.data.length
      },
      deep: true
    },
    'dgvDanhSach.isCheckAll_Huy': {
      handler: function (value) {
        if (value) {
          this.dgvDanhSach.data.forEach((f) => {
            f.checked = true
          })
          this.dgvDanhSach.checked_Huy = this.dgvDanhSach.data.map(
            (f) => f.maDvGt
          )
        } else {
          if (!this.clickHuyOnGrid) {
            this.dgvDanhSach.data.forEach((f) => {
              f.checked = false
            })
            this.dgvDanhSach.checked_Huy = []
          }
        }
      },
      deep: true
    },
    'dgvDanhSach.checked_Huy': {
      handler: function (value) {
        this.clickHuyOnGrid = true
        this.dgvDanhSach.isCheckAll_Huy = value.length > 0 && value.length === this.dgvDanhSach.data.length
      },
      deep: true
    },
    'dgvDanhSach.isCheckAll_Sua': {
      handler: function (value) {
        if (value) {
          this.dgvDanhSach.data.forEach((f) => {
            f.checked = true
          })
          this.dgvDanhSach.checked_Sua = this.dgvDanhSach.data.map(
            (f) => f.maDvGt
          )
        } else {
          if (!this.clickSuaOnGrid) {
            this.dgvDanhSach.data.forEach((f) => {
              f.checked = false
            })
            this.dgvDanhSach.checked_Sua = []
          }
        }
      },
      deep: true
    },
    'dgvDanhSach.checked_Sua': {
      handler: function (value) {
        this.clickSuaOnGrid = true
        this.dgvDanhSach.isCheckAll_Sua = value.length > 0 && value.length === this.dgvDanhSach.data.length
      },
      deep: true
    },
    'dgvDanhSachTC.isCheckAll_DKTC': {
      handler: function (value) {
        if (value) {
          this.dgvDanhSachTC.data.forEach((f) => {
            f.checked = true
          })
          this.dgvDanhSachTC.checked_DKTC = this.dgvDanhSachTC.data.map(
            (f) => f.maDvGt
          )
        } else {
          if (!this.clickDangKyOnGridTC) {
            this.dgvDanhSachTC.data.forEach((f) => {
              f.checked = false
            })
            this.dgvDanhSachTC.checked_DKTC = []
          }
        }
      },
      deep: true
    },
    'dgvDanhSachTC.checked_DKTC': {
      handler: function (value) {
        this.clickDangKyOnGridTC = true
        this.dgvDanhSachTC.isCheckAll_DKTC = value.length > 0 && value.length === this.dgvDanhSachTC.data.length
      },
      deep: true
    },
    'dgvDanhSachTC.isCheckAll_HuyTC': {
      handler: function (value) {
        if (value) {
          this.dgvDanhSachTC.data.forEach((f) => {
            f.checked = true
          })
          this.dgvDanhSachTC.checked_HuyTC = this.dgvDanhSachTC.data.map(
            (f) => f.maDvGt
          )
        } else {
          if (!this.clickHuyOnGridTC) {
            this.dgvDanhSachTC.data.forEach((f) => {
              f.checked = false
            })
            this.dgvDanhSachTC.checked_HuyTC = []
          }
        }
      },
      deep: true
    },
    'dgvDanhSachTC.checked_HuyTC': {
      handler: function (value) {
        this.clickHuyOnGrid = true
        this.dgvDanhSachTC.isCheckAll_HuyTC = value.length > 0 && value.length === this.dgvDanhSachTC.data.length
      },
      deep: true
    },
    'dgvDanhSachTC.isCheckAll_SuaTC': {
      handler: function (value) {
        if (value) {
          this.dgvDanhSachTC.data.forEach((f) => {
            f.checked = true
          })
          this.dgvDanhSachTC.checked_SuaTC = this.dgvDanhSachTC.data.map(
            (f) => f.maDvGt
          )
        } else {
          if (!this.clickSuaOnGridTC) {
            this.dgvDanhSachTC.data.forEach((f) => {
              f.checked = false
            })
            this.dgvDanhSachTC.checked_SuaTC = []
          }
        }
      },
      deep: true
    },
    'dgvDanhSachTC.checked_SuaTC': {
      handler: function (value) {
        this.clickSuaOnGridTC = true
        this.dgvDanhSachTC.isCheckAll_SuaTC = value.length > 0 && value.length === this.dgvDanhSachTC.data.length
      },
      deep: true
    }
  }
}
</script>
