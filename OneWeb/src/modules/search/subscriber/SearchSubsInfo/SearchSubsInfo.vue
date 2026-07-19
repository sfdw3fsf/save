<template src='./SearchSubsInfo.html'></template>
<style src='./SearchSubsInfo.scss'></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component'
import API from './API'
import gridview from '@/components/Shared/gridview'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//modal
import Log from './Log'
import moment from 'moment'

export default {
  components: { breadcrumb, VueFlatPickr, gridview, API, Log },
  name: 'SearchSubsInfo',
  props: ['taikhoan'],
  async mounted() {
    if (this.taikhoan) {
      this.Account = this.taikhoan
      this.tracuuADSL()
    } else if (this.$route.query.taikhoan) {
      this.Account = this.$route.query.taikhoan
      this.tracuuADSL()
    }

    try {
      this.tt_nd = await this.$root.context.get(
        '/web-ccdv/hoanthien-hs-tachnhap-tbao/thongtin_nguoidung'
      )
    } catch (error) {
    } finally {
    }
  },
  data() {
    return {
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput: true
      },
      ngay_ht: new Date(),
      header: {
        title: 'TRA THÔNG TIN TÀI KHOẢN ADSL',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      listbox: {
        ds_data: {
          list: [],
          header: [],
          value: {}
        }

      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date(),
      success_code: 0,
      LoaiTaiKhoan: '',
      Account: '',
      TinhTP: '',
      MaTaiKhoan: '',
      NgayTao: '',
      NgayBanGiao: '',
      MaHopDong: '',
      TenKH: '',
      LoaiKH: '',
      SDT: '',
      DiaChiKH: '',
      Bras: '',
      DSLam: '',
      HinhThucTT: '',
      BrasIP: '',
      TrangThai: '',
      GoiCuoc: '',
      DichVuSD: '',
      NasportID: '',
      SlotPort: '',
      MaTruyCap: '',
      ThongTinTC: '',
      GhiChu: '',
      IPTinh: '',
      dt: [],
      tt_nd: [],
      tsbtnRollBack: {
        visible: true
      },
      lblCGNAT: '',
      greetnetstatus:''
    }
  },
  computed: {
    chuanhoaphuong: {
      get() {
        return this.isChuanHoaPhuong
      },
      set(value) {
        this.isChuanHoaPhuong = value
      }
    }
  },
  methods: {
    ShowAlert(mesage, val) {
      if (val == 0) {
        this.$toast.success(mesage)
      } else if (val == 1) {
        this.$toast.warning(mesage)
      } else if (val == 2) {
        this.$toast.error(mesage)
      }
    },
    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },

    clear() {
      this.LoaiTaiKhoan = ''
      this.MaTaiKhoan = ''
      this.Email = ''
      this.NgayBanGiao = ''
      this.NgayTao = ''

      //Thông tin khách hàng
      this.MaHopDong = ''
      this.TinhTP = ''
      this.LoaiKH = ''
      this.TenKH = ''
      this.DiaChiKH = ''
      this.SDT = ''

      //Thông tin chi tiết dịch vụ
      this.Bras = ''
      this.BrasIP = ''
      this.DSLam = ''
      this.TrangThai = ''
      this.HinhThucTT = ''
      this.GoiCuoc = ''
      this.GhiChu = ''
      this.DichVuSD = ''
      this.NasportID = ''
      this.SlotPort = ''
      this.IPTinh = ''
      this.MaTruyCap = ''
      this.ThongTinTC = ''
      $('#Account').focus()

      this.dt = []
    },

    tracuuADSL: async function() {
      this.clear()

      var output = []
      var Account = []
      var Customer = []
      var AccountService = []
      var log = []
      let isClear = false
      let accountName = this.Account
      this.loading(true)
      try {
        let response = await API.TraCuuThueBao(this.axios, { accountName })
        this.loading(false)
        if (response.data.errorCode === 0) {
          let { data } = response.data

          if (data.length <= 1) {
            if (rs.data[0].includes('The system cannot find the file specified'))
              this.$root.toastError('Hệ thống chưa được cài đặt chứng chỉ kết nối VISA')
            else
              this.$root.toastError('Đối tượng dịch vụ không tồn tại!')

            isClear = true
            return
          }

          Account = data[0]
          Customer = data[1]
          AccountService = data[2]

          //hiển thị thông tin tài khoản
          this.LoaiTaiKhoan = AccountService.serviceName
          this.MaTaiKhoan = AccountService.accountId
          this.NgayBanGiao = ''
          this.NgayTao = AccountService.createdDate
          this.TinhTP = Customer.city
          //thông tin khách hàng
          this.MaHopDong = ''
          try {
            let customerType = await API.getCustomerType(this, Customer.customerType)
            this.LoaiKH = customerType.data[0].doituong_id == '-1' ? '' : customerType.data[0].ten_dt
          } catch (e) {
            this.LoaiKH = ''
          }
          this.TenKH = Customer.firstName
          this.DiaChiKH = Customer.address
          this.SDT = Customer.phone
          //thông tin dịch vụ
          var slot = ''
          var port = ''
          var vpi = ''
          var vci = ''
          for (var at of AccountService.attributes) {
            switch (at.type) {
              case 'HD_VDC':
                this.MaHopDong = at.value
                break
              case 'BRAS-ID':
                var bras_ = at.value
                try {
                  let brasitem = await API.getTenBras(this, bras_)
                  if (brasitem.data.length > 0) {
                    this.Bras = brasitem.data[0].tenbras
                  }
                } catch (e) {

                }
                break
              case 'BRAS-IPADDRESS':
                this.BrasIP = at.value
                break
              case 'DSLAM-ID':
                let dslam = at.value
                try {
                  let dslamItem = await API.getTenDslam(this, dslam)
                  if (dslamItem.data.length > 0)
                    this.DSLam = dslamItem.data[0].tendslam
                } catch (e) {

                }
                break
              case 'SLOT':
                slot = at.value
                break
              case 'PORT':
                port = at.value
                break
              case 'VPI':
                vpi = at.value
                break
              case 'VCI':
                vci = at.value
                break
              case 'STRBILLING':
                this.ThongTinTC = at.value
                break
              case 'NAS-PORT-ID':
                if (at.value != '0')
                  this.NasportID = at.value
                break
              case 'NAS-PORT-ID-ID':
                if (at.value != '0')
                  this.NasportID = at.value
                break
              case 'FRAMED-ROUTE':
              case 'frame-ip-address':
                this.IPTinh = at.value
                break
            }
          }

          let tariffType = AccountService.tariffType
          try {
            let tariffTypeItem = await API.getTariffType(this, tariffType)
            console.log('tariffType', tariffTypeItem)
            this.HinhThucTT = tariffTypeItem.data[0].tenmuc
          } catch (e) {

          }
          this.TrangThai = AccountService.status
          this.SlotPort = slot + '/' + port + '/' + vpi + '/' + vci
          this.DichVuSD = AccountService.serviceName
          /*Start Thitv.hue bổ sung*/
          this.tsbtnRollBack.visible = false
          if (AccountService.cgnat == 1) {
            this.lblCGNAT = 'Thuê bao chuyển đổi CGNAT'
            this.tsbtnRollBack.visible = true
          } else
            this.lblCGNAT = ''
          console.log(AccountService)
          /*End Thitv.hue bổ sung*/
          //hiển thị thông tin log object
          log = data[4]
          this.dt = []
          if (log.length > 0) {
            for (let i = 0; i < log.length; i++) {
              let logModel = log[i]
              let tmp = await API.map_id(this.axios, {
                in_id_neo: 'code_name',
                in_table: 'vdc_action_list',
                in_dk: 'where id = ' + logModel.actionId
              })
              let action = tmp.data.data

              let dr = {
                thoigian: moment(logModel.dateTime, 'YYYY-MM-DD[T]HH:mm:ss.SSS[Z]').add(7, 'hours').format('DD/MM/YYYY HH:mm:ss'),
                admin: logModel.userName,
                tacdong: action == '-1' ? '' : action,
                ghichu: logModel.actionDesc
              }
              this.dt.push(dr)
            }
            //lnkLichSu.Visible = true;
          }
        } else {
          this.$root.$toast.error(response.data.faultString)
          isClear = true
        }
      } catch (e) {
        console.log(e)
      } finally {
        if (isClear) {
          this.clear()
        }
        this.loading(false)
      }
    },
    /*Start thitv_hue bổ sung hàm*/
    tsbtnRollBack_Click() {
      this.$bvModal.msgBoxConfirm('Bạn muốn chuyển đổi thuê bao từ IP private (CGNat) ->  IP động public ?', {
        title: '',
        size: 'sm',
        okTitle: 'Đồng ý',
        cancelTitle: 'Hủy'
      }).then(async v => {
        if (v) {
          let input_chuyendoi = {
            'accountName': this.Account,
            'status': 0
          }
          let changeCGNAT = this.GetData(await API.ChangeCGNAT(this.axios, input_chuyendoi))
          if (changeCGNAT == 'OK') {
            this.ShowAlert('Chuyển đổi thuê bao thành công !', 0)
            //Gọi api thành công loai = 1
            let input_luulog = {
              'ma_tb': this.Account,
              'loai': 1
            }
            let kq = this.GetData(await API.Capnhat_log_cgnat(this.axios, input_luulog))
            //Thành công
            this.tracuuADSL()
          } else {
            //Bị lỗi
            //Gọi api thành công loai = 2
            let input_luulog = {
              'ma_tb': this.Account,
              'loai': 2
            }
            let kq = this.GetData(await API.Capnhat_log_cgnat(this.axios, input_luulog))
          }
        }
      })
    },
    /*End thitv_hue bổ sung hàm*/
    btnTraCuu() {
      if (this.Account.toString().trim().length > 0) {
        if (this.Account.toString().trim().length > 200) {
          this.$toast.error('Tài khoản chỉ nhập tối đa 200 ký tự')
          return
        }

        this.tracuuADSL()
      }
    },
    async tsbtnGetStatus_GRN_Click(){
      let st = await API.GetStatus(this, this.Account)
      console.log('st', JSON.stringify(st.data))
      this.greetnetstatus =  JSON.stringify(st.data)
      this.$refs['ref-xem-green-net'].show()
    },
    getPass: async function() {
      var sPass = ''
      let accountName = this.Account
      let response = await API.TraCuuThueBao(this.axios, { accountName })
      let dt = response.data.data[4]

      for (var olog of dt) {
        if (olog.actionId == 2812 || olog.actionId == 29112025) //action đổi mk
        {
          sPass = olog.actionDesc
          break
        }
        if (olog.actionId == 29112018) //action dang ky - cũ
        {
          sPass = olog.actionDesc
          break
        }
        if (olog.actionId == 2823) //action dang ky
        {
          sPass = olog.actionDesc
          break
        }
      }

      if (sPass.includes('Mật khẩu:')) {
        let searchTerm = '\n\t'
        let ntIndex = sPass.indexOf(searchTerm)
        let mkIndex = sPass.indexOf('Mật khẩu: [')
        while (ntIndex <= mkIndex) {
          ntIndex = sPass.indexOf(searchTerm, (ntIndex + 1))
        }

        this.$root.toastSuccess('Mật khẩu của tài khoản là : ' + sPass.substring(mkIndex + 11, ntIndex - 1))
      }

      if (sPass.includes('Mật khẩu mới:')) {
        this.$root.toastSuccess('Mật khẩu của tài khoản là : ' + sPass.substring(sPass.indexOf('i: [') + 4, sPass.length - 1))
      }

      if (sPass.includes('Mật khẩu thay đổi thành:')) {
        this.$root.toastSuccess('Mật khẩu của tài khoản là : ' + sPass.substring(sPass.indexOf('h: [') + 4, sPass.length - 1))
      }
    },

    btnXemMK() {
      this.getPass()
    },

    lnkLichSu_Click() {
      if (this.dt.length > 0) {
        this.$refs.log.showModal(this.dt)
      }
    }
  }

}


</script>
