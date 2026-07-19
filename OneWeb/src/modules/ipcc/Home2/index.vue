<template src="./index.html">
</template>

<script>
  import ChiTietCuocGoi from './components/ChiTietCuocGoi/index.vue'
  import LichSuBaoHong from '../SearchIncidentUpdateAppointment/SearchIncidentUpdateAppointment.vue'
  import LichSuKhieuNai from '../SearchComplainDetail/index.vue'
  import TraCuuKhachHang from "@/modules/ipcc/ReceivingCallsSwitchboard/components/TraCuuDataMig.vue"
  import DanhSachThueBaoKhachHang from "@/modules/ipcc/ReceivingCallsSwitchboard/components/DanhSachThueBaoKhachHangV2.vue"
  import LichSuCuocGoiKhachHang from "@/modules/ipcc/ReceivingCallsSwitchboard/components/LichSuCuocGoiKhachHang.vue"
  import TimKiemMoRong from "@/modules/ipcc/ReceivingCallsSwitchboard/components/TraCuuDataMig.vue"
  import ReceivingCallsSwitchboardApi from "@/modules/ipcc/ReceivingCallsSwitchboard/ReceivingCallsSwitchboardApi.js"
  import API from './API.js'
  import SockJS from 'sockjs-client' // Import SockJS
  import { Stomp } from '@stomp/stompjs' // Import Stomp

  const actions = [
    {
      text:"Đăng nhập IPCC",
      status:"LOGIN"
    },
    {
      text:"Sẵn sàng nhận cuộc gọi",
      status:"READY_IB"
    },
    {
      text:"Logout IPCC",
      status:"LOGOUT",
      reason_code: "logout"
    },
    {
      text:"Đang bận",
      status:"DANG_BAN",
      hasChild: true,
      expanded: false,
      selectable: false,
      nodeChild: [
        {
          text:"Đi vệ sinh",
          status:"NOT_READY",
          reason_code: "time_in_wc"
        },
        {
          text:"Đi ăn trưa",
          status:"NOT_READY",
          reason_code: "time_in_lunch"
        },
        {
          text:"Đi văn tối",
          status:"NOT_READY",
          reason_code: "time_in_dinner"
        },
        {
          text:"Đi đào tạo",
          status:"NOT_READY",
          reason_code: "time_in_training"
        },
        {
          text:"Teamview",
          status:"NOT_READY",
          reason_code: "time_in_teamview"
        },
        {
          text:"Công việc khác",
          status:"NOT_READY",
          reason_code: "time_in_others"
        },
      ]
    },
    {
      text:"Chuẩn bị Outbound",
      status:"READY_OB"
    },
    {
      text:"Hoàn thành cuộc gọi",
      status:"WRAP_UP"
    }
  ]

  export default {
    components: {
      LichSuBaoHong,
      LichSuKhieuNai,
      ChiTietCuocGoi,
      DanhSachThueBaoKhachHang,
      LichSuCuocGoiKhachHang,
      TraCuuKhachHang,
      TimKiemMoRong
    },
    data() {
      return {
        statusConnect: 'Ngắt kết nối',
        timer: {
          hours: 0,
          minutes: 0,
          seconds: 0
        },
        ma_tb: '',
        currentTab: 1,
        tt_nd: {},
        user_ipcc: {
          list: [],
          commands: [
            { name: 'active', title: 'Active Line mới', cssClass: 'pad3 mar5 lh14 icon -ap one-check btn-plus btn-primary', iconCss: 'fa fa-times', width: 100 },
          ],
          header: [
            {
              fieldName: 'user_ipcc',
              headerText: 'Agent',
              allowFiltering: false,
              allowHtml: true,
            },
            {
              fieldName: 'line',
              headerText: 'Line',
              allowFiltering: false,
              allowHtml: true,
              width: 100,
              textAlign: 'center',
              template: () => {
                return {
                  //test Thuan
                  template: {
                    template: `<div>
                        <input type="text" class="form-control text" :value="data.line" @keypress="isNumber($event)" @change="onChangeText" >
                      </div>`,
                    data: function () {
                      return {
                        data: {},
                      };
                    },
                    computed: {
                      parent() {
                        return this.$parent.$parent.$parent;
                      },
                    },
                    methods: {
                      onChangeText: function (e) {
                        var index = this.parent.user_ipcc.list.findIndex(e => e.user_ipcc == this.data.user_ipcc)
                        this.parent.user_ipcc.list[index].line = e.target.value
                      },
                      isNumber(evt) {
                        evt = evt ? evt : window.event;
                        var charCode = evt.which ? evt.which : evt.keyCode;
                        if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                          evt.preventDefault();
                        } else {
                          return true;
                        }
                      },
                    },
                  },
                };
              },
            }
          ],
          selected: {}
        },
        stompClient: null,
        domainlocal: 'https://api-onebss.vnpt.vn/web-ipcc',
        socket: null,
        reconnect: true,
        intervalId: null,
        isCalling: 0,
        thongtincuocgoi: {
          CallID: null,
          UserCode: null,
          Extension: null,
          Caller: null,
          Status: null,
          IbCall: null,
          ObCall: null,
          TimeStamp: null,
          Direction: null,
          NumberService: null,
          Hotline: null,
          BranchName: null
        },
        thongtincuocgoi_end: {
          CallID: null,
          UserCode: null,
          Extension: null,
          Caller: null,
          Status: null,
          Duration: null,
          WaitTime: null,
          TimeStamp: null
        },
        DanhMuc: {},
        khachhang_id: 0,
        tinh_id: 0,
        ten_kh: '',
        diachi_kh: '',
        ghichu: '',
        fields: { dataSource: actions, value: 'status', text: 'text', child: 'nodeChild' },
        currentReasonCode: null,
        currentStatus: null,
        currentText: null,
        soluong_cuocgoi: '0',
        node: {
          value: '0',
          list: [
            {id: '0', text: 'Chọn site'},
            {id: 'node_1', text: 'inbound'},
            {id: 'node_2', text: 'inbound-hcm'},
          ]
        }
      }
    },
    computed: {
      formattedTime() {
        let time = `${String(this.timer.hours).padStart(2, '0')}:${String(this.timer.minutes).padStart(2, '0')}:${String(this.timer.seconds).padStart(2, '0')}`

        if (time == '00:00:00') return null

        return time
      }
    },
    async created() {
      this.DanhMuc = await this.LayDSThamSo()
      if (this.DanhMuc) this.DanhMuc.TRANGTHAITB = await this.LayDSTrangThai()
    },
    async mounted() {

      this.node.value =  localStorage.getItem("nodeItem") ? localStorage.getItem("nodeItem") : '0';

      this.loading(true)
      try {
        let nguoidung_id = this.$root.token.getNguoiDungID()
        await this.sp_lay_user_ipcc_theo_nguoidung_id(nguoidung_id)
        this.tt_nd = await this.$root.token.getThongTinNguoiDung()
        this.tt_nd.machine = await this.$root.token.getMachine()

        let new_user_ipcc = this.user_ipcc.list
        var user_ipcc = sessionStorage.getItem("user_ipcc")
        console.log('sessionStorage > user_ipcc',sessionStorage.getItem("user_ipcc"))
        if (user_ipcc && user_ipcc.length > 0) {
          user_ipcc = JSON.parse(user_ipcc)
          this.user_ipcc.list = user_ipcc.list == new_user_ipcc ? user_ipcc.list : new_user_ipcc
          this.user_ipcc.selected = user_ipcc.list == new_user_ipcc ? user_ipcc.selected : new_user_ipcc[0]
          await this.changeAgentState('LOGIN', null)
          this.currentStatus = 'LOGIN'
        }
      } catch (err) {
        console.log(err)
      } finally {
        this.loading(false)
      }
    },
    watch: {
      currentTab: function (newVal, oldval) {
        // this.tab_Detail_SelectedPageChanged({Page: newVal})
      },
      reconnect() {
        if (this.reconnect == true)
          this.statusConnect = 'Đã kết nối'
        else
          this.statusConnect = 'Ngắt kết nối'
      },
    },
    methods: {
      async selectedNode() {
        localStorage.setItem('nodeItem', this.node.value)

        this.loading(true)

        try {
          let params = {
            "userCode": this.user_ipcc.selected.user_ipcc,
            "node": this.node.value
          }

          let rs = await API.setNode(this.axios, params)

        } catch (e) {
          console.log(e)
        } finally {
          this.loading(false)
        }
      },
      connect() {
        const headers = {
          Authorization: 'Bearer ' + JSON.parse(localStorage.getItem('OneBSS-Token')).access_token,
          //userCode: this.user_ipcc.selected.user_ipcc,
        }

        let url = this.domainlocal + '/ipcc-endpoint'

        this.socket = new SockJS(url)
        this.stompClient = Stomp.over(this.socket)

        this.stompClient.connect(headers, () => {
          this.reconnect = true
          console.log('Connected')

          this.socket.onclose = () => {
            if (!this.reconnect) {
              return
            }

            setTimeout(() => {
              if (this.reconnect) {
                this.connect()
              }
            }, 5000)
          }

          this.stompClient.subscribe('/topic/' + this.user_ipcc.selected.user_ipcc, async (message) => {
            console.log(message.body);
            await this.ipcc(message.body)
          })
        })
      },
      disconnect() {
        this.reconnect = false
        if (this.socket) this.socket.close()
        console.log('Disconnected')
      },
      gridUserIpccSelected(row) {
        if (this.thongtincuocgoi.Status != 4 || this.thongtincuocgoi_end.Status == 5) {
          try {
            this.disconnect()
          } catch (e) {
            console.log(e)
          }
        }

        if (row != null && this.thongtincuocgoi.Status != 4) {
          this.user_ipcc.selected = row.data
          this.connect()
        } else if (this.thongtincuocgoi.Status == 4) {
          this.$root.toastError('Đang trong cuộc gọi !')
        }
      },
      gridCommand_user_ipcc: async function (type, dataItem) {
        if (type == 'active') {
          await this.changeAgentState('LOGIN', null)
        }
      },
      async sp_lay_user_ipcc_theo_nguoidung_id(nguoidung_id) {
        try {
          let user_ipcc = await API.sp_lay_user_ipcc_theo_nguoidung_id(this.axios, nguoidung_id)

          if (user_ipcc.data.error_code == 'BSS-00000000'
            && user_ipcc.data.data !== undefined
            && user_ipcc.data.data.length > 0) {
            this.user_ipcc.list = user_ipcc.data.data
          } else {
            this.$root.toastWarning('Không tồn tại user ipcc')
          }

        } catch (e) {
          console.log(e)
        } finally {

        }
      },
      async ipcc(data) {
        if (data != null) {
          let obj = JSON.parse(data)
          // Thông báo chuyển trạng thái
          if (obj.Status == 'NOTIFICATION') {
            this.$toast.warning("Thông báo từ IPCC - " + obj.Message);
          }

          // Thông báo cuộc gọi đến
          if (obj.Status != undefined && obj.Status == 4) {
            this.thongtincuocgoi_end.Status = 0
            this.thongtincuocgoi = obj
            this.isCalling = 1
            console.log('ipcc > thongtincuocgoi',this.thongtincuocgoi)
            // reset time
            this.timer.seconds = 0
            this.timer.minutes = 0
            this.timer.hours = 0

            this.intervalId = setInterval(() => {
              this.incrementTime()
            }, 1000)

            if (obj['Caller']) {
              let input = {
                p_cuocgoi_id: Number(obj['CallID']),
                p_so_dt: obj['Caller'],
                p_nv_nghe_id: this.tt_nd.nhanvien_id,
                p_maynghe: this.tt_nd.machine,
                p_noidung: this.$refs.ChiTietCuocGoi.noidung ? this.$refs.ChiTietCuocGoi.noidung : '',
                p_gio_bd: obj['TimeStamp'] ? obj['TimeStamp'] : '',
              }
              await this.sp_batdau_cuocgoi_ipcc(input)
              await this.hienthi_tt_kh_goiden(obj['Caller'])
              await this.$refs.DanhSachThueBaoKhachHang.frm_Load(obj['Caller'], '', '', null)
              if (this.$refs.DanhSachThueBaoKhachHang.grid_DanhSach.DataSource && this.$refs.DanhSachThueBaoKhachHang.grid_DanhSach.DataSource.length > 0) {
                this.tab_Detail_SelectedPageChanged(2)
              } else {
                this.tab_Detail_SelectedPageChanged(4)
              }
            }

          // Kết thúc cuộc gọi
          } else if (obj.Status != undefined && obj.Status == 5) {
            let input = {
              p_cuocgoi_id: Number(obj['CallID']),
              p_thoigian_cho: obj['WaitTime'] ? obj['WaitTime'] : '',
              p_thoiluong: obj['Duration'] ? obj['Duration'] : '',
            }
            await this.sp_ketthuc_cuocgoi_ipcc(input)

            this.thongtincuocgoi.Status = 0
            this.thongtincuocgoi_end = obj
            this.isCalling = 2
            console.log('ipcc > thongtincuocgoi_end',this.thongtincuocgoi_end)
            clearInterval(this.intervalId)

            this.tab_Detail_SelectedPageChanged(4)
          }

        }
      },
      async sp_batdau_cuocgoi_ipcc(input) {
        try {
          let res = await API.sp_batdau_cuocgoi_ipcc(this.axios, input)

          if (res.data && res.data.error_code == 'BSS-00000000') {
            //this.$toast.success('Bắt đầu cuộc gọi IPCC thành công!')
          } else {
            this.$toast.error('Lỗi bắt đầu cuộc gọi IPCC ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
          }

        } catch (e) {
          console.log(e)
          this.$toast.error('Lỗi bắt đầu cuộc gọi IPCC ' + JSON.stringify(e))
        } finally {

        }
      },
      hienthi_tt_kh_goiden: async function (so_tb) {
        try {
          this.ten_kh = ''
          this.diachi_kh = ''
          this.ghichu = ''
          if (so_tb) {
            let mavung = ""
            var check = this.KiemTraSoCoDinh(so_tb, mavung)
            console.log('hienthi_tt_kh_goiden > KiemTraSoCoDinh', check)
            if (check.ketqua) {
              // TODO: CALL API
              var input = {
                sdt: check.so_dt,
                matinh: check.mavung,
                MIG_HNI: false
              }
              let tt_khachhang = this.GetDataList(await ReceivingCallsSwitchboardApi.sp_lay_ds_lienhe(this.axios, input));
              if (tt_khachhang && tt_khachhang.length > 0) {
                this.ten_kh = tt_khachhang[0]['ten_tb']
                this.diachi_kh = tt_khachhang[0]['diachi_ld']
                this.tinh_id = tt_khachhang[0]['tinh_id']
              }
              return
            } else {
              // TODO: CALL API
              var input = {
                sdt: so_tb,
                matinh: mavung,
                MIG_HNI: false
              }
              let tt_khachhang = this.GetDataList(await ReceivingCallsSwitchboardApi.sp_lay_ds_lienhe(this.axios, input));
              if (tt_khachhang && tt_khachhang.length > 0) {
                this.ten_kh = tt_khachhang[0]['ten_tb']
                this.diachi_kh = tt_khachhang[0]['diachi_ld']
                this.tinh_id = tt_khachhang[0]['tinh_id']
              }
              return
            }
          }
        } catch (e) {
          console.log('LayDSLienhe', e)
          if (e && e.data) {
            this.$toast.error('Lỗi lấy thông tin khách hàng gọi đến ' + (e.data.message_detail ? e.data.message_detail : e.data.message))
          } else {
            this.$toast.error('Lỗi lấy thông tin khách hàng gọi đến ' + JSON.stringify(e))
          }
        } finally {

        }
      },
      KiemTraSoCoDinh: function (so_dt, ma_tinh) {
        var vma_tinh = ma_tinh
        var sodt_chuanhoa = so_dt
        var ketqua = true
        if (!so_dt || so_dt.length == 0) ketqua = false
        let codeLength = 4
        if (so_dt.substring(0, 3) == '028' || so_dt.substring(0, 3) == '024') codeLength = 3
        if (ReceivingCallsSwitchboardApi.dctDSMaVung().hasOwnProperty(so_dt.substring(0, codeLength)) == false) {
          ketqua = false
        } else {
          vma_tinh = ReceivingCallsSwitchboardApi.dctDSMaVung()[so_dt.substring(0, codeLength)]
          sodt_chuanhoa = sodt_chuanhoa.substring(codeLength)
          ketqua = true
        }

        return {ketqua: ketqua, so_dt: sodt_chuanhoa, mavung: vma_tinh }
      },
      GetDataList: function (response) {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else if (response && response.data && response.data.error_code == 'BSS-00000204') {
          return []
        } else {
          this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
          return []
        }
      },
      GetData: function (response) {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else if (response && response.data && response.data.error_code == 'BSS-00000204') {
          return ''
        } else {
          this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
          return ''
        }
      },
      async sp_ketthuc_cuocgoi_ipcc(input, msg = 'Kết thúc cuộc gọi IPCC thành công!') {
        try {
          let res = await API.sp_ketthuc_cuocgoi_ipcc(this.axios, input)

          if (res.data && res.data.error_code == 'BSS-00000000') {
            this.$toast.success(msg)
          } else {
            this.$toast.error('Lỗi kết thúc cuộc gọi IPCC ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
          }

        } catch (e) {
          console.log(e)
          this.$toast.error('Lỗi kết thúc cuộc gọi IPCC ' + JSON.stringify(e))
        } finally {

        }
      },
      btnGhiLaiCTCG_click: async function (data) {
        try {
          this.loading(true)
          if (!this.thongtincuocgoi.CallID) {
            this.$toast.error('Không có thông tin cuộc gọi!')
            return
          }
          // if (!this.$refs.DanhSachThueBaoKhachHang.grid_DanhSach.DataSource || this.$refs.DanhSachThueBaoKhachHang.grid_DanhSach.DataSource.length == 0) {
          //   this.$toast.error('Cần xác định tỉnh cho cuộc gọi, danh sách thuê bao khách hàng không tìm thấy dữ liệu!')
          //   return
          // }
          await this.sp_capnhat_cuocgoi_ipcc()
        } catch (e) {} finally {
          this.loading(false)
        }
      },
      async sp_capnhat_cuocgoi_ipcc() {
        try {
          var input = {
            p_cuocgoi_id: Number(this.thongtincuocgoi.CallID),
            p_tinh_id: this.tinh_id,
            p_phanloai: this.$refs.ChiTietCuocGoi.phanloai.value ? this.$refs.ChiTietCuocGoi.phanloai.value : 0,
            p_noidung: this.$refs.ChiTietCuocGoi.noidung ? this.$refs.ChiTietCuocGoi.noidung : '',
          }
          let res = await API.sp_capnhat_cuocgoi_ipcc(this.axios, input)

          if (res.data && res.data.error_code == 'BSS-00000000') {
            this.$toast.success('Ghi lại cuộc gọi IPCC thành công!')
          } else {
            this.$toast.error('Lỗi ghi lại cuộc gọi IPCC ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
          }

        } catch (e) {
          console.log(e)
          this.$toast.error('Lỗi ghi lại cuộc gọi IPCC ' + JSON.stringify(e))
        } finally {

        }
      },
      incrementTime() {
        this.timer.seconds++

        if (this.timer.seconds >= 60) {
          this.timer.seconds = 0
          this.timer.minutes++

          if (this.timer.minutes >= 60) {
            this.timer.minutes = 0
            this.timer.hours++
          }
        }
      },
      LayDSThamSo: async function() {
        try {
          var input = {}
          var data = ''
          var res = await ReceivingCallsSwitchboardApi.sp_lay_ds_thamso(this.axios, input);
          if (res.data && res.data.error_code == 'BSS-00000000') {
            data = res.data.data
          }
          var json_data = JSON.parse(data)
          if (json_data.ERROR_CODE == 1 || json_data.ERROR_CODE == '1') {
            return json_data.RESULT
          } else return {}
        } catch (e) {
          if (e && e.data) {
            this.$toast.error('Lỗi sp_lay_ds_thamso ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
          } else {
            this.$toast.error('Lỗi sp_lay_ds_thamso ' + JSON.stringify(e))
          }
          return {}
        } finally {

        }
      },
      LayDSTrangThai: async function() {
        try {
          var res = await ReceivingCallsSwitchboardApi.CSS_TRANGTHAI_TB(this.axios);
          if (res.data && res.data.error_code == 'BSS-00000000') {
            return res.data.data
          } else return []
        } catch (e) {
          return []
        } finally {

        }
      },
      TraCuu_FocusedRowChanged: async function (data) {
        if (data) {
          console.log('TraCuu_FocusedRowChanged',data)
          if (data['isConfirm']) {
            // this.ten_kh = data.dtDanhSach['ten_tb']
            // this.diachi_kh = data.dtDanhSach["diachi_ld"]
            this.khachhang_id = await data.dtDanhSach['khachhang_id']
            this.tinh_id = await data.dtDanhSach['tinh_id']
            let ma_tb = await data.dtDanhSach['ma_tb']
            // this.thongtincuocgoi.Caller = null
            await this.$refs.DanhSachThueBaoKhachHang.frm_Load('', this.khachhang_id, this.tinh_id, ma_tb)
            await this.tab_Detail_SelectedPageChanged(2)
          }
        }
      },
      TraCuuMoRong_FocusedRowChanged: async function (data) {
        if (data) {
          console.log('TraCuuMoRong_FocusedRowChanged',data)
          if (data['isConfirm']) {
            // this.ten_kh = data.dtDanhSach['ten_tb']
            // this.diachi_kh = data.dtDanhSach["diachi_ld"]
            this.khachhang_id = await data.dtDanhSach['khachhang_id']
            this.tinh_id = await data.dtDanhSach['tinh_id']
            let ma_tb = await data.dtDanhSach['ma_tb']
            // this.thongtincuocgoi.Caller = null
            await this.$refs.DanhSachThueBaoKhachHang.frm_Load('', this.khachhang_id, this.tinh_id, ma_tb)
            await this.tab_Detail_SelectedPageChanged(2)
          }
        }
      },
      txt_PhoneNo_KeyPress: async function () {
        try {
          this.loading(true)
          if (!this.thongtincuocgoi.Caller) {
            this.$toast.error('Số máy không được để trống!')
            return
          }
          if (this.thongtincuocgoi && this.thongtincuocgoi.Caller && this.thongtincuocgoi.Caller.length > 7) {
            // TODO: fun HienThiThongTinKhachGoiDen()
            this.khachhang_id = 0
            this.tinh_id = 0
            this.ten_kh = ''
            this.diachi_kh = ''
            await this.HienThiThongTinKhachGoiDen(this.thongtincuocgoi.Caller)
          }
        } catch (e) {
          console.log('txt_PhoneNo_KeyPress',e)
        } finally {
          this.loading(false)
        }
      },
      HienThiThongTinKhachGoiDen: async function (sochugoi) {
        try {
          if (sochugoi) {
            await this.$refs.DanhSachThueBaoKhachHang.frm_Load(sochugoi, '', '', null)
          }
          await this.tab_Detail_SelectedPageChanged(2)
        } catch (e) {
          console.log('HienThiThongTinKhachGoiDen',e)
        } finally {}
      },
      gview_DanhSach_FocusedRowChanged: function (dr) {
        console.log('gview_DanhSach_FocusedRowChanged',dr)
        if (dr && dr.length > 0) {
          // this.thongtincuocgoi.Caller = dr[0]["so_dt"] ? dr[0]["so_dt"] : this.thongtincuocgoi.Caller
          this.khachhang_id = dr[0]['khachhang_id'] ? dr[0]['khachhang_id'] : this.khachhang_id
          this.tinh_id = dr[0]['tinh_id'] ? dr[0]['tinh_id'] : this.tinh_id
          // this.ten_kh = dr[0]['ten_tb']
          // this.diachi_kh = dr[0]["diachi_ld"]
          // this.tab_Detail_SelectedPageChanged(3)

          this.ma_tb = dr[0]['ma_tb']
        }
      },
      // Change current tab
      tab_Detail_SelectedPageChanged: async function (currentTab) {
        try {
          this.loading(true)
          if (currentTab == 2) {
            if ((this.khachhang_id && this.tinh_id) || this.thongtincuocgoi.Caller) {
              // await this.$refs.DanhSachThueBaoKhachHang.frm_Load(this.thongtincuocgoi.Caller, this.khachhang_id, this.tinh_id, null)
            }
          } else if (currentTab == 3) {
            this.soluong_cuocgoi = '0'
            await this.$refs.LichSuCuocGoiKhachHang.frm_Load()
            this.soluong_cuocgoi = this.$refs.LichSuCuocGoiKhachHang?.grid_History?.DataSource?.length || '0'
          } else if (currentTab == 6) {
            this.$refs.LichSuBaoHong.tsbtnTimKiem_Click_V2()
          } else if (currentTab == 7) {
            this.$refs.LichSuKhieuNai.btnTimKiem_Click_V2()
          }

          this.currentTab = currentTab
        } catch (e) {
          console.log('tab_Detail_SelectedPageChanged',e)
        } finally {
          this.loading(false)
        }
      },
      async changeActions(row) {
        if (!row) return false;

        if (this.thongtincuocgoi.Status == 4) {
          this.$root.toastError('Đang trong cuộc gọi !')

          let defaultObj = this.$refs.refActions.ej2Instances
          defaultObj.value = this.currentStatus
          defaultObj.text = this.currentText

          return;
        }

        let reason_code = ''
        let text = row.itemData.text
        let status = row.itemData.id

        this.currentReasonCode = ''
        if (status == 'NOT_READY') {
          let obj = actions[3].nodeChild;
          let data = obj.filter(element => element.text == text)[0];
          reason_code = data.reason_code
          this.currentReasonCode = data.reason_code
        } else if (status == 'DANG_BAN') {
          this.$root.toastError('Bạn Phải chọn hành động đang bận cụ thể!')
          return;
        }

        await this.changeAgentState(status, reason_code)
      },
      async changeAgentState(status, reason_code) {
        this.loading(true)

        try {
          if (this.node.value == 0 || this.node.value == null || this.node.value == '') {
            return this.$root.toastError("Bạn phải chọn site để làm việc")
          }

          let params = {
            "username": this.user_ipcc.selected.user_ipcc,
            "password": "",
            "extension": this.user_ipcc.selected.line,
            "active_type": "inbound",
            "reason_code": reason_code ? reason_code : this.currentReasonCode,
            "status": status,
            "node": this.node.value
          }

          let rs = await API.changeAgentState(this.axios, params)

          if (rs.data.errorCode != 0) {
            this.$root.toastError("Thông báo từ IPCC - " + rs.data.faultString)

            console.log('changeAgentState > currentStatus', this.currentStatus);
            console.log('changeAgentState > currentText', this.currentText);

            let defaultObj = this.$refs.refActions.ej2Instances
            defaultObj.value = this.currentStatus
            defaultObj.text = this.currentText
          } else {
            this.currentStatus = status;
            this.currentText = actions.find(e => e.status == status).text;
            this.$toast.success("Thông báo từ IPCC - " + this.user_ipcc.selected.user_ipcc + ' ' + this.user_ipcc.selected.line + ' ' + this.currentText)
            sessionStorage.setItem("user_ipcc",JSON.stringify(this.user_ipcc))
          }

        } catch (e) {
          console.log(e)
        } finally {
          this.loading(false)
        }
      },
      parseDateString(dateString) {
        const [datePart, timePart] = dateString.split(' ');
        const [day, month, year] = datePart.split('/').map(Number);
        const [hours, minutes, seconds] = timePart.split(':').map(Number);
        return new Date(year, month - 1, day, hours, minutes, seconds);
      },
      formatTime(seconds) {
        const hours = Math.floor(seconds / 3600);
        const minutes = Math.floor((seconds % 3600) / 60);
        const remainingSeconds = seconds % 60;

        const formattedHours = String(hours).padStart(2, '0');
        const formattedMinutes = String(minutes).padStart(2, '0');
        const formattedSeconds = String(remainingSeconds).padStart(2, '0');

        return [formattedHours, formattedMinutes, formattedSeconds];
      },
      async loadCall() {
        try {
          this.disconnect();
          this.connect();

          let params = {
            "username": this.user_ipcc.selected.user_ipcc,
            "extension": this.user_ipcc.selected.line,
            "node": this.node.value,
            "call_direction": "inbound"
          }

          /*let params = {
            "username": "han_agent_1033",
            "extension": "240010",
            "node": this.node.value,
            "call_direction": "inbound"
          }*/

          if (this.intervalId) clearInterval(this.intervalId);

          let response = await API.getListRecentCallByUser(this.axios, params)

          if (response.data.errorCode == '0' && response.data.data != null) {
            const currentCall  = response.data.data[0]

            console.log('currentCall', currentCall);

            this.thongtincuocgoi.CallID = currentCall.CallID
            this.thongtincuocgoi.UserCode = currentCall.UserCode
            this.thongtincuocgoi.Extension = currentCall.Extension
            this.thongtincuocgoi.Caller = currentCall.Caller
            this.thongtincuocgoi.TimeStamp = currentCall.StartTimeStamp
            this.thongtincuocgoi.Direction = currentCall.Direction
            this.thongtincuocgoi.NumberService = currentCall.NumberService
            this.thongtincuocgoi.Hotline = currentCall.Hotline
            this.thongtincuocgoi.BranchName = currentCall.BranchName

            // Nếu đang trong cuộc gọi
            if (currentCall.Status == 4) {
              this.thongtincuocgoi_end.Status = 0
              this.thongtincuocgoi.Status = 4
              this.isCalling = 4

              console.log('ipcc > thongtincuocgoi_start',this.thongtincuocgoi)

              // nếu timestamp hiện tại - timestamp bắt đầu và đếm tăng dần
              const givenDate = this.parseDateString(this.thongtincuocgoi.TimeStamp);
              const currentDate = new Date();
              const differenceInMilliseconds = currentDate - givenDate;
              const differenceInSeconds = Math.floor(differenceInMilliseconds / 1000);
              let [h, m, s] = this.formatTime(differenceInSeconds);

              this.timer.hours = h;
              this.timer.minutes = m;
              this.timer.seconds = s;

              this.intervalId = setInterval(() => {
                this.incrementTime()
              }, 1000)

              if (currentCall.Caller) {
                let input = {
                  p_cuocgoi_id: Number(currentCall.CallID),
                  p_so_dt: currentCall.Caller,
                  p_nv_nghe_id: this.tt_nd.nhanvien_id,
                  p_maynghe: this.tt_nd.machine,
                  p_noidung: this.$refs.ChiTietCuocGoi.noidung ? this.$refs.ChiTietCuocGoi.noidung : '',
                  p_gio_bd: currentCall.StartTimeStamp ?? ''
                }
                await this.sp_batdau_cuocgoi_ipcc(input)
                await this.hienthi_tt_kh_goiden(currentCall.Caller)
                await this.$refs.DanhSachThueBaoKhachHang.frm_Load(currentCall.Caller, '', '', null)
                if (this.$refs.DanhSachThueBaoKhachHang.grid_DanhSach.DataSource && this.$refs.DanhSachThueBaoKhachHang.grid_DanhSach.DataSource.length > 0) {
                  this.tab_Detail_SelectedPageChanged(2)
                } else {
                  this.tab_Detail_SelectedPageChanged(4)
                }
              }

              return this.$root.toastWarning('Đang trong cuộc gọi');
            }

            // Nếu cuộc gọi đã kết thúc
            if (currentCall.Status == 5) {

              let input = {
                p_cuocgoi_id: Number(currentCall.CallID),
                p_thoigian_cho: currentCall.WaitTime ?? '',
                p_thoiluong: currentCall.Duration ?? ''
              }

              await this.sp_ketthuc_cuocgoi_ipcc(input, 'Cuộc gọi đã kết thúc')

              this.thongtincuocgoi.Status = 0

              this.thongtincuocgoi_end.CallID = currentCall.CallID
              this.thongtincuocgoi_end.UserCode = currentCall.UserCode
              this.thongtincuocgoi_end.Extension = currentCall.Extension
              this.thongtincuocgoi_end.Caller = currentCall.Caller
              this.thongtincuocgoi_end.Status = currentCall.Status
              this.thongtincuocgoi_end.Duration = currentCall.Duration
              this.thongtincuocgoi_end.WaitTime = currentCall.WaitTime
              this.thongtincuocgoi_end.TimeStamp = currentCall.EndTimeStamp

              this.isCalling = 5

              let [h, m, s] = this.formatTime(this.thongtincuocgoi_end.Duration);
              this.timer.hours = h;
              this.timer.minutes = m;
              this.timer.seconds = s;

              this.tab_Detail_SelectedPageChanged(4)

              return;
            }

          } else {
            return this.$root.toastError('Không tìm thấy cuộc gọi');
          }

        } catch (e) {
          console.log(e)
        } finally {

        }
      },
    },
    beforeDestroy() {

      this.disconnect()
    },
  }
</script>

<style scoped>
  .list-actions-top h4 {
    display: inline-block;
    padding: 5px 10px;
    margin: 0px;
  }

  .notification-icon {
    z-index: 2;
    position: fixed;
    bottom: 70px;
    right: 70px;
    background-color: tomato;
    border-radius: 50%;
    width: 60px;
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    animation: bounce 1s infinite; /* Animation for motion */

    /* Add a drop shadow for depth */
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.6);
  }

  .notification-icon-inner {
    font-size: 30px;
    color: #fff;
  }

  /* Keyframes animation for motion */
  @keyframes bounce {
    0%, 100% {
      transform: translateY(0);
    }
    50% {
      transform: translateY(-25px);
    }
  }

  /* Style for the phone icon (font-awesome icon in this case) */
  .fa-phone::before {
    content: "\f095"; /* Unicode code for the phone icon */
  }

  .list-actions .e-has-child div.e-fullrow {
    cursor: not-allowed;
  }
</style>
