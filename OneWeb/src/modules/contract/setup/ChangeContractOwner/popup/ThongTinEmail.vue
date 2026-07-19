<template src="./ThongTinEmail.html"></template>
<script>
import DataGridCustom from '@/modules/contract/setup/DeclareLandline/components/DataGridCustom'
const PopupThongTinUser = () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinUser/PopupThongTinUser')
import { THONGTIN_KH } from '@/const/enums'
var rs
export default {
  name: 'ThongTinEmail',
  data() {
    return {
      DsEmail: {
        headers: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            textAlign: 'Center',
            allowSorting: false,
            width: 50
          },
          {
            fieldName: 'email',
            headerText: 'Email',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },

      DsMucDichCG: {
        headers: [
          {
            fieldName: 'mucdich_sd',
            headerText: 'Mục đích',
            allowFiltering: true,
            width: 150
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },

      DsMucDichDG: {
        headers: [
          {
            fieldName: 'mucdich_sd',
            headerText: 'Mục đích',
            allowFiltering: true,
            width: 150
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },

      DsLienHeKH: {
        headers: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            textAlign: 'Center',
            allowSorting: false,
            width: 60
          },
          {
            fieldName: 'ma_kh',
            headerText: 'Mã KH',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'ten_kh',
            headerText: 'Tên KH',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'trangthai',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'dangky',
            headerText: 'Đăng ký',
            textAlign: 'Center',
            template: this.getCheckboxTemplate(this, 'dangky', 'dgvKhachHang'),
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'huy',
            headerText: 'Hủy',
            textAlign: 'Center',
            template: this.getCheckboxTemplate(this, 'huy', 'dgvKhachHang'),
            allowFiltering: true,
            allowSorting: false,
            width: 100
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },

      DsLienHeTT: {
        headers: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            textAlign: 'Center',
            allowSorting: false,
            width: 60
          },
          {
            fieldName: 'ma_tt',
            headerText: 'Mã TT',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'ten_tt',
            headerText: 'Tên TT',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'trangthai',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'dangky',
            headerText: 'Đăng ký',
            textAlign: 'Center',
            template: this.getCheckboxTemplate(this, 'dangky', 'dgvThanhToan'),
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'huy',
            headerText: 'Hủy',
            textAlign: 'Center',
            template: this.getCheckboxTemplate(this, 'huy', 'dgvThanhToan'),
            allowFiltering: true,
            allowSorting: false,
            width: 100
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },

      DsLienHeTB: {
        headers: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            textAlign: 'Center',
            allowSorting: false,
            width: 60
          },
          {
            fieldName: 'ma_tt',
            headerText: 'Mã TT',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'ma_tb',
            headerText: 'Mã TB',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên TB',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'trangthai',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'dangky',
            headerText: 'Đăng ký',
            textAlign: 'Center',
            template: this.getCheckboxTemplate(this, 'dangky', 'dgvThueBao'),
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'huy',
            headerText: 'Hủy',
            textAlign: 'Center',
            template: this.getCheckboxTemplate(this, 'huy', 'dgvThueBao'),
            allowFiltering: true,
            allowSorting: false,
            width: 100
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },
      vhdkh_id: 0,
      vkhachhang_id: 0,
      hdemail_id: 0,
      email_id: 0,
      tthd_id: 0,
      ds_hd_email_md: [],
      ds_hd_email_kh: [],
      ds_hd_email_tb: [],
      ds_hd_email_lh: []
    }
  },
  components: {
    DataGridCustom,
    PopupThongTinUser
  },
  props: {
    mode: {
      type: String,
      default: 'create'
    },
    hdkh_id: {
      type: Number,
      default: 0
    },
    khachhang_id: {
      type: Number,
      default: 0
    },
    viewOnly: {
      type: Boolean,
      default: false
    }
  },
  mounted() {
    console.log('frmThongTinEmail_Load')
    this.frmThongTinEmail_Load()
  },
  methods: {
    getCheckboxTemplate(parent, field, grid) {
      return function () {
        return {
          template: {
            template: `<div class="template_checkbox"> <input v-modal="data[field]" type="checkbox" @change="change" :checked="data[field] == 1"> </div>`,
            data: function () {
              return { data: {}, field }
            },
            methods: {
              change(event) {
                // if (this.field == 'dangky') {
                //   this.data[field] = event.target.checked ? '1' : '0'
                //   this.data.huy = false
                //   console.log(this.data)
                // }
                // console.log(this.field)
                // console.log(this.data.index)
                // this.data.huy = !this.data.huy
                // this.data.dangky = !this.data.huy
                parent.$refs[grid].dataSource[this.data.index][field] = event.target.checked ? '1' : '0'
                // parent.$refs[grid].dataSource[this.data.index].huy = '0'
              }
            },
            mounted() {
              // console.log('data')
              // console.log('grid')
              // console.log(grid)
              // console.log('datasourc')
              // console.log(parent.$refs[grid].dataSource)
              // console.log(parent.$refs[grid].dataSource[this.data.index]['dangky'])
            }
          }
        }
      }
    },
    tsbtnNhapMoi_Click() {
      this.SetButton(1)
      this.HienThi_ds_hopdong_email(0, 0)
    },
    getDsThongTinEmail() {
      this.frmThongTinEmail_Load()
    },
    frmThongTinEmail_Load() {
      ///nhapt 07082015
      ///Áp dụng cho form frmTraCuuPhieuTC
      ///Thực hiện hiển thị cập nhật khi this.tthd_id <> 6,7
      this.vhdkh_id = this.hdkh_id
      this.vkhachhang_id = this.khachhang_id
      if ((this.tthd_id != 6 && this.tthd_id != 7) || this.tthd_id == 0) {
        this.$refs.tsbtnNhapMoi.style.display = null
        this.$refs.tsbtnCapNhat.style.display = null
        this.$refs.tsbtnXoa.style.display = null
        this.$refs.tsbtnUser.style.display = null
      } else {
        this.$refs.tsbtnNhapMoi.style.display = 'none'
        this.$refs.tsbtnCapNhat.style.display = 'none'
        this.$refs.tsbtnXoa.style.display = 'none'
        this.$refs.tsbtnUser.style.display = 'none'
      }
      this.HienThi_DS_Email()
    },

    async HienThi_DS_Email() {
      try {
        rs = await this.axios.post('web-ccdv/tienhopdong/sp_lay_ds_email', { hdkh_id: this.vhdkh_id, khachhang_id: this.khachhang_id })
        let tt_email = rs.data.data
        if (tt_email && tt_email.length > 0) {
          this.$refs.dgvEmail.dataSource = tt_email
          this.gridview_Email_FocusedRowChanged()
        } else {
          this.$refs.dgvEmail.dataSource = null
          this.HienThi_ds_hopdong_email(0, 0)
          this.SetButton(1)
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },

    async HienThi_ds_hopdong_email(vhdemail_id, vemail_id) {
      try {
        if (!this.vhdkh_id) return

        // khách hàng
        rs = await this.axios.post('web-ccdv/tienhopdong/lay_ds_hopdong_email', { khachhang_id: 0, hdkh_id: this.vhdkh_id, ttkh_id: THONGTIN_KH.KHACHHANG, hdemail_id: vhdemail_id, email_id: vemail_id })
        var email_kh = rs.data.data
        // email_kh.map((item) => {
        //   item.dangky = false
        //   item.huy = false
        // })

        if (email_kh.length > 0) {
          this.$refs.dgvKhachHang.dataSource = email_kh
        } else this.$refs.dgvKhachHang.dataSource = null

        // thanh toán
        rs = await this.axios.post('web-ccdv/tienhopdong/lay_ds_hopdong_email', { khachhang_id: 0, hdkh_id: this.vhdkh_id, ttkh_id: THONGTIN_KH.THANHTOAN, hdemail_id: vhdemail_id, email_id: vemail_id })

        var email_tt = rs.data.data

        if (email_tt.length > 0) {
          this.$refs.dgvThanhToan.dataSource = email_tt
        } else this.$refs.dgvThanhToan.dataSource = []

        // thuê bao
        rs = await this.axios.post('web-ccdv/tienhopdong/lay_ds_hopdong_email', { khachhang_id: 0, hdkh_id: this.vhdkh_id, ttkh_id: THONGTIN_KH.THUEBAO, hdemail_id: vhdemail_id, email_id: vemail_id })
        var email_tb = rs.data.data
        if (email_tb.length > 0) {
          this.$refs.dgvThueBao.dataSource = email_tb
        } else this.$refs.dgvThueBao.dataSource = []

        this.HienThiListView_MD(vhdemail_id, vemail_id)
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },

    async HienThiListView_MD(vhdemail_id, vemail_id) {
      var myDataTables = []
      if (vhdemail_id != 0) {
        //Lấy ds loại hình thuê bao đã gán & chưa gán
        this.$refs.lvwMDchuagan.dataSource = []
        //myDataTables = banggia.Lay_DS_LoaiHinhTB(banggia_id, 0, dichvuvt_id).Tables[0];
        let myDataTables = []
        rs = await this.axios.post('web-ccdv/tienhopdong/sp_lay_ds_email_md', { hdemail_id: vhdemail_id, kieu: 0 })
        myDataTables = rs.data.data
        if (myDataTables.length > 0) {
          this.$refs.lvwMDchuagan.dataSource = myDataTables
        }

        this.$refs.lvwMDdagan.dataSource = []
        rs = await this.axios.post('web-ccdv/tienhopdong/sp_lay_ds_email_md', { hdemail_id: vhdemail_id, kieu: 1 })
        myDataTables = rs.data.data
        if (myDataTables.length > 0) {
          this.$refs.lvwMDdagan.dataSource = myDataTables
        }
      } else if (vemail_id != 0) {
        //Lấy ds loại hình thuê bao đã gán & chưa gán
        this.$refs.lvwMDchuagan.dataSource = []
        //myDataTables = banggia.Lay_DS_LoaiHinhTB(banggia_id, 0, dichvuvt_id).Tables[0];
        rs = await this.axios.post('web-ccdv/tienhopdong/sp_lay_ds_email_md_db', { email_id: vhdemail_id, kieu: 1 })
        myDataTables = rs.data.data
        if (myDataTables.length > 0) {
          this.$refs.lvwMDchuagan.dataSource = myDataTables
        }

        this.$refs.lvwMDdagan.dataSource = []
        rs = await this.axios.post('web-ccdv/tienhopdong/sp_lay_ds_email_md_db', { email_id: vhdemail_id, kieu: 1 })
        myDataTables = rs.data.data
        if (myDataTables.length > 0) {
          this.$refs.lvwMDdagan.dataSource = myDataTables
        }
      } else {
        this.$refs.lvwMDdagan.dataSource = []
        this.$refs.lvwMDchuagan.dataSource = []
        rs = await this.axios.post('web-ccdv/tienhopdong/sp_lay_ds_email_md_db', { email_id: 0, kieu: 0 })
        myDataTables = rs.data.data
        if (myDataTables.length > 0) {
          this.$refs.lvwMDchuagan.dataSource = myDataTables
        }
      }
    },

    tsbtnNhapMoi_Click() {
      this.SetButton(1)
      this.HienThi_ds_hopdong_email(0, 0)
    },

    Clean() {
      this.$refs.txtEmail.value = ''
      this.hdemail_id = 0
      this.email_id = 0
    },

    //region Set button
    SetButton(kieu) {
      this.$refs.tsbtnNhapMoi.classList.add('disabled')
      this.$refs.tsbtnCapNhat.classList.add('disabled')
      this.$refs.tsbtnXoa.classList.add('disabled')
      if (kieu == -1) {
        //Bat dau
        this.$refs.tsbtnCapNhat.classList.remove('disabled')
      }
      if (kieu == 0) {
        //Bat dau
        this.$refs.txtEmail.focus()
        this.$refs.tsbtnNhapMoi.classList.remove('disabled')
        this.Clean()
      }
      if (kieu == 1) {
        //Them moi
        this.$refs.txtEmail.focus()
        this.$refs.tsbtnCapNhat.classList.remove('disabled')
        this.Clean()
      }
      if (kieu == 2) {
        //Huy
        this.$refs.tsbtnNhapMoi.classList.remove('disabled')
        this.$refs.tsbtnXoa.classList.remove('disabled')
        this.Clean()
      }
      if (kieu == 3) {
        //Edit
        this.$refs.tsbtnNhapMoi.classList.remove('disabled')
        this.$refs.tsbtnXoa.classList.remove('disabled')
        this.$refs.tsbtnCapNhat.classList.remove('disabled')
      }
    },
    //endregion

    tsbtnCapNhat_Click() {
      this.CapNhat()
    },

    async CapNhat() {
      try {
        let action_type
        if (!this.kt_dulieu()) return
        if (this.$refs.tsbtnNhapMoi.Enabled == false || !this.hdemail_id) {
          action_type = 'A'
          await this.TaoDuLieu_HD_EMAIL(true)
          await this.TaoDuLieu_HD_EMAIL_KH()
        } else {
          action_type = 'U'
          await this.TaoDuLieu_HD_EMAIL(false)
          await this.TaoDuLieu_HD_EMAIL_KH()
        }

        await this.axios.post('web-ccdv/tienhopdong/capnhat_email', {
          action_type: action_type,
          hdemail_id: this.hdemail_id,
          json_hd_email: JSON.stringify(this.toLowerCaseKey(this.ds_hd_email)),
          json_hd_email_kh: JSON.stringify(this.toLowerCaseKey(this.ds_hd_email_kh)),
          json_hd_email_md: JSON.stringify(this.toLowerCaseKey(this.ds_hd_email_md))
        })
        this.HienThi_DS_Email()
        this.$toast.success('Cập nhật thông tin Email thành công !')
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },
    toLowerCaseKey(arr) {
      let data = []
      arr.forEach((obj) => {
        let item = Object.keys(obj).reduce((accumulator, key) => {
          accumulator[key.toLowerCase()] = obj[key]
          return accumulator
        }, {})

        data.push(item)
      })

      return data
    },

    kt_dulieu() {
      if (this.$refs.txtEmail.value.trim() == '') {
        this.$toast.error('Bạn chưa nhập thông tin Email !')
        this.$refs.txtEmail.focus()
        return false
      }
      if (this.$refs.lvwMDdagan.dataSource.length == 0) {
        this.$toast.error('Bạn chưa chọn mục đích sử dụng nào !')
        return false
      }
      if (
        this.$refs.dgvKhachHang.dataSource.filter((x) => x['dangky'] == '1' || x['huy'] == '1').length == 0 &&
        this.$refs.dgvThanhToan.dataSource.filter((x) => x['dangky'] == '1' || x['huy'] == '1').length == 0 &&
        this.$refs.dgvThueBao.dataSource.filter((x) => x['dangky'] == '1' || x['huy'] == '1').length == 0
      ) {
        this.$toast.error('Bạn chưa đăng ký liên hệ nào.')
        return false
      }
      return true
    },

    async TaoDuLieu_HD_EMAIL(themmoi) {
      return new Promise(async (resolve, reject) => {
        this.ds_hd_email = []
        let row = {}
        if (themmoi) {
          rs = await this.axios.post('web-hopdong/khoiphucthanhly/get_keys', {
            keyname: 'HD_EMAIL'
          })

          this.hdemail_id = rs.data.data
        }

        row.HDEMAIL_ID = this.hdemail_id
        row.EMAIL_ID = this.email_id
        row.EMAIL = this.$refs.txtEmail.value.trim()
        row.HDKH_ID = this.vhdkh_id
        this.ds_hd_email.push(row)
        for (let i = 0; i < this.$refs.lvwMDdagan.dataSource.length; i++) {
          let row1 = {}
          row1.MUCDICH_ID = this.$refs.lvwMDdagan.dataSource[i].mucdich_id
          row1.HDEMAIL_ID = this.hdemail_id
          this.ds_hd_email_md.push(row1)
        }

        resolve(true)
      })
    },

    TaoDuLieu_HD_EMAIL_KH() {
      return new Promise((resolve, reject) => {
        this.ds_hd_email_kh = []
        // khách hàng
        for (let i = 0; i < this.$refs.dgvKhachHang.dataSource.length; i++) {
          console.log('dgvKhachHang')
          console.log(this.$refs.dgvKhachHang.dataSource[i])
          if (this.$refs.dgvKhachHang.dataSource[i].dangky || this.$refs.dgvKhachHang.dataSource[i].huy) {
            let row = {}
            let selected = this.$refs.dgvKhachHang.dataSource[i]
            row.HDEMAIL_ID = this.hdemail_id
            row.HD_ID = selected.hdkh_id
            row.TTKH_ID = THONGTIN_KH.KHACHHANG
            if (selected.dangky == true || selected.dangky == '1') {
              row.KIEU_YC = 1
            } else if (selected.huy == true || selected.huy == '1') {
              row.KIEU_YC = 0
            }
            this.ds_hd_email_kh.push(row)
          }
        }

        // Thanh toán
        for (let i = 0; i < this.$refs.dgvThanhToan.dataSource.length; i++) {
          if (this.$refs.dgvThanhToan.dataSource[i].dangky || this.$refs.dgvThanhToan.dataSource[i].huy) {
            let row = {}
            row.HDEMAIL_ID = this.hdemail_id
            let selected = this.$refs.dgvThanhToan.dataSource[i]
            row.HD_ID = selected.hdtt_id
            row.TTKH_ID = THONGTIN_KH.THANHTOAN
            if (selected.dangky) {
              row.KIEU_YC = 1
            } else if (selected.huy) {
              row.KIEU_YC = 0
            }
            this.ds_hd_email_kh.push(row)
          }
        }

        // Thuê bao
        for (let i = 0; i < this.$refs.dgvThueBao.dataSource.length; i++) {
          if (this.$refs.dgvThueBao.dataSource[i].dangky || this.$refs.dgvThueBao.dataSource[i].huy) {
            let selected = this.$refs.dgvThueBao.dataSource[i]
            let row = {}
            row.HDEMAIL_ID = this.hdemail_id
            row.HD_ID = selected.hdtb_id
            row.TTKH_ID = THONGTIN_KH.THUEBAO
            if (selected.dangky) {
              row.KIEU_YC = 1
            } else if (selected.huy) {
              row.KIEU_YC = 0
            }
            this.ds_hd_email_kh.push(row)
          }
        }

        resolve(true)
      })
    },

    async gridview_Email_FocusedRowChanged() {
      try {
        let selected = this.$refs.dgvEmail.getSelectedRecords()
        if (selected.length > 0) {
          this.hdemail_id = Number(selected[0].hdemail_id)
          this.email_id = Number(selected[0].email_id)
          this.$refs.txtEmail.value = selected[0].email
          this.HienThi_ds_hopdong_email(this.hdemail_id, this.email_id)
          this.SetButton(3)
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },

    async tsbtnXoa_Click() {
      try {
        let selected = this.$refs.dgvEmail.getSelectedRecords()
        let inhdemail_id = Number(selected[0].hdemail_id)

        let ds_user = []
        if (inhdemail_id != 0) {
          await this.axios.post('web-ccdv/tienhopdong/sp_delete_hd_email_del', { hdemail_id: inhdemail_id })
          //this.$toast.success("Hủy hợp đồng Email thành công !");
          this.HienThi_DS_Email()
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },

    tsbtnUser_Click() {
      this.$refs['ThongTinUser'].showModal = true
    },

    gridview_khachhang_CellValueChanging() {
      try {
        if (e.Column.FieldName == 'DANGKY_BOOL') {
          if (gridview_khachhang.GetRowCellValue(e.RowHandle, 'SUDUNG') != '') {
            gridview_khachhang.SetFocusedRowCellValue(gridview_khachhang.FocusedColumn, false)
            gridview_khachhang.FocusedColumn = gridview_khachhang.Columns['MA_KH']
          }
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },

    gridview_thanhtoan_CellValueChanging() {
      try {
        if (e.Column.FieldName == 'DANGKY_BOOL') {
          if (gridview_thanhtoan.GetRowCellValue(e.RowHandle, 'SUDUNG') != '') {
            gridview_thanhtoan.SetFocusedRowCellValue(gridview_thanhtoan.FocusedColumn, false)
            gridview_thanhtoan.FocusedColumn = gridview_thanhtoan.Columns['MA_TT']
          }
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },

    gridview_thuebao_CellValueChanging() {
      try {
        if (e.Column.FieldName == 'DANGKY_BOOL') {
          if (gridview_thuebao.GetRowCellValue(e.RowHandle, 'SUDUNG') != '') {
            gridview_thuebao.SetFocusedRowCellValue(gridview_thuebao.FocusedColumn, false)
            gridview_thuebao.FocusedColumn = gridview_thuebao.Columns['MA_TB']
          }
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },

    chkgridview_Huy_TT_CheckedChanged() {
      let index = Convert.ToInt32(gridview_thanhtoan.FocusedRowHandle)
      let _dt = dgvThanhToan.dataSource
      label4.focus()
      if (gridview_Email.DataRowCount == 0) {
        _dt.Rows[index]['HUY_BOOL'] = false
        return
      } else {
        if (_dt.Rows[index]['SUDUNG'] == '') {
          _dt.Rows[index]['HUY_BOOL'] = false
          return
        }
      }
    },

    chkgrid_TT_CheckedChanged() {
      let index = Convert.ToInt32(gridview_thanhtoan.FocusedRowHandle)
      let _dt = dgvThanhToan.dataSource
      label4.focus()
    },

    repositoryItemCheckEdit1_CheckedChanged() {
      let index = Convert.ToInt32(gridview_khachhang.FocusedRowHandle)
      let _dt = dgvKhachHang.dataSource
      label4.focus()
    },

    chk_Huy_KH1_CheckedChanged() {
      let index = Convert.ToInt32(gridview_khachhang.FocusedRowHandle)
      let _dt = dgvKhachHang.dataSource
      label4.focus()
      if (gridview_Email.DataRowCount == 0) {
        _dt.Rows[index]['HUY_BOOL'] = false
        return
      } else {
        if (_dt.Rows[index]['SUDUNG'] == '') {
          _dt.Rows[index]['HUY_BOOL'] = false
          return
        }
      }
    },

    chkgridview_Huy_TB_CheckedChanged() {
      let index = Convert.ToInt32(gridview_thuebao.FocusedRowHandle)
      let _dt = dgvThueBao.dataSource
      label4.focus()
      if (gridview_Email.DataRowCount == 0) {
        _dt.Rows[index]['HUY_BOOL'] = false
        return
      } else {
        if (_dt.Rows[index]['SUDUNG'] == '') {
          _dt.Rows[index]['HUY_BOOL'] = false
          return
        }
      }
    },

    chkgrid_TB_CheckedChanged() {
      let index = Convert.ToInt32(gridview_thuebao.FocusedRowHandle)
      let _dt = dgvThueBao.dataSource
      label4.focus()
    },

    tsbtnXoaTTEmail_Click() {
      this.loading(true)
      try {
        if (this.hdemail_id != 0 || this.$refs.tsbtnNhapMoi.classList.has('disabled')) {
          this.$toast.error('Bạn không thực hiện đc thao tác này')
          return
        }
        this.$confirm('Bạn có chắc chắn muốn xóa liên hệ ko?', 'Thông báo', {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Không đồng ý'
        }).then(async () => {
          this.ds_hd_email = []
          let row1 = {}
          rs = await this.axios.post('web-hopdong/khoiphucthanhly/get_keys', {
            keyname: 'HD_EMAIL'
          })
          this.hdemail_id = rs.data.data
          row1.HDEMAIL_ID = rs.data.data
          row1.EMAIL = this.$refs.txtEmail.value == '' ? ' ' : this.$refs.txtEmail.value
          row1.EMAIL_ID = this.email_id
          row1.HDKH_ID = this.vhdkh_id
          row1.KIEU_YC = 0
          this.ds_hd_email.push(row1)
          this.ds_hd_email_kh = []
          let row
          // khách hàng
          for (let i = 0; i < this.$refs.dgvKhachHang.dataSource.length; i++) {
            if (this.$refs.dgvKhachHang.dataSource[i].sudung) {
              row = {}
              row.HDEMAIL_ID = this.hdemail_id
              row.HD_ID = this.$refs.dgvKhachHang.dataSource[i].hdkh_id
              row.TTKH_ID = THONGTIN_KH.KHACHHANG

              row.KIEU_YC = 0

              this.ds_hd_email_kh.push(row)
            }
          }

          // Thanh toán
          for (let i = 0; i < this.$refs.dgvThanhToan.dataSource.length; i++) {
            if (this.$refs.dgvThanhToan.dataSource[i].sudung) {
              row = {}
              row.HDEMAIL_ID = this.hdemail_id
              row.HD_ID = this.$refs.dgvThanhToan.dataSource[i].hdtt_id
              row.TTKH_ID = THONGTIN_KH.THANHTOAN
              row.KIEU_YC = 0
              this.ds_hd_email_kh.push(row)
            }
          }

          // Thuê bao
          for (let i = 0; i < this.$refs.dgvThueBao.dataSource.length; i++) {
            if (this.$refs.dgvThueBao.dataSource[i].sudung) {
              row = {}
              row.HDEMAIL_ID = this.hdemail_id
              row.HD_ID = this.$refs.dgvThueBao.dataSource[i].hdtb_id
              row.TTKH_ID = THONGTIN_KH.THUEBAO
              row.KIEU_YC = 0
              this.ds_hd_email_kh.push(row)
            }
          }

          await this.axios.post('web-ccdv/tienhopdong/capnhat_email', {
            action_type: "U",
            hdemail_id: this.hdemail_id,
            json_hd_email: JSON.stringify(this.toLowerCaseKey(this.ds_hd_email)),
            json_hd_email_kh: JSON.stringify(this.toLowerCaseKey(this.ds_hd_email_kh)),
            json_hd_email_md: JSON.stringify(this.toLowerCaseKey(this.ds_hd_email_md))
          })
          this.HienThi_DS_Email()
          this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
        })
      } catch (err) {
        this.$toast.error(err.message)
      }
      this.loading(fasle)
    },
    MoveRight() {
      let selected = this.$refs.lvwMDchuagan.getSelectedRecords()
      selected.forEach((item) => {
        let index = this.$refs.lvwMDchuagan.dataSource.indexOf(item)
        this.$refs.lvwMDchuagan.dataSource.splice(index, 1)
      })
      this.$refs.lvwMDdagan.dataSource = selected.concat(this.$refs.lvwMDdagan.dataSource)
    },
    MoveLeft() {
      let selected = this.$refs.lvwMDdagan.getSelectedRecords()
      selected.forEach((item) => {
        let index = this.$refs.lvwMDdagan.dataSource.indexOf(item)
        this.$refs.lvwMDdagan.dataSource.splice(index, 1)
      })
      this.$refs.lvwMDchuagan.dataSource = selected.concat(this.$refs.lvwMDchuagan.dataSource)
    },
    MoveRightAll() {
      this.$refs.lvwMDdagan.dataSource = this.$refs.lvwMDchuagan.dataSource
      this.$refs.lvwMDchuagan.dataSource = []
    },
    MoveLeftAll() {
      this.$refs.lvwMDchuagan.dataSource = this.$refs.lvwMDdagan.dataSource
      this.$refs.lvwMDdagan.dataSource = []
    }
  }
}
</script>
