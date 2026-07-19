<template src="./DanhSachThueBaoKhongKhoaMo.html"></template>
<style scoped src="../TraCuuPhieuTraTheoNgay/TraCuuPhieuTraTheoNgay.scss"></style>
<script>
import PopupDocFileDSKhoaMo from '../components/PopupDocFile/PopupDocFileDSKhoaMo.vue'
import PopupXoaFileDSKhoaMo from '../components/PopupDocFile/PopupXoaFileDSKhoaMo.vue'
import PopupDanhSachThueBao from '../TraCuuKhoaMoMay/component/PopupDanhSachThueBao'
import PopupTraCuuDanhBa from '../components/PopupTraCuuDanhBa/PopupTraCuuDanhBa.vue'
import KyCuoc from '@/components/KyCuoc'
import API from '@/modules/QLTN/api/DSThueBaoKhongKhoaMoAPI.js'
import moment from 'moment'
import EventBus from '@/utils/eventBus'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, maxLength } from 'vuelidate/lib/validators'
import XuatFile from '../../CSKH/utils/XuatFile.vue'
import { mapActions, mapGetters } from 'vuex'
import Vue from 'vue'

import {
  DS_KHONG_XULY
} from '@/const/enums'

export default {
  components: {
    PopupDocFileDSKhoaMo,
    appKyCuoc: KyCuoc,
    PopupDanhSachThueBao,
    PopupXoaFileDSKhoaMo,
    BssRequiredMarker,
    appModalXuatDuLieu: XuatFile,
    PopupTraCuuDanhBa
  },
  name: 'DanhSachThueBaoKhongKhoaMo',

  async created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)
    if (Object.keys(this.kyHoaDonHienTaiGetter).length <= 0) { await this.getKyHoaDonHienTai() }
    if (Object.keys(this.kyHoaDonHienTaiGetter).length > 0) { this.thongtinKyHoaDon.kyHoaDonSelected = this.kyHoaDonHienTaiGetter.KYCUOC }

    let thamSo = this.$route.query
    for(let key in thamSo) {
        thamSo[key.toLowerCase()] = thamSo[key];
    }
    if (this.$route.query.tag !== undefined && this.$route.query.tag !== null) {
      this.tag = this.$route.query.tag
      this.dsloaiid = this.tag
    } else {
      this.dsloaiid = ''
    }

    await this.createDSLoai()
    await this.getDanhSachThamSoMD(0)
  },

  async mounted () {
    this.TTTraCuu_grid.items = await this.getDanhSachKhongKhoaMo('', moment(this.params.ngayTH).format('DD/MM/YYYY'), null)
    if (this.TTTraCuu_grid.items.length <= 0) { this.setButton(1) }
    this.$refs.soMayAcc.focus()
    this.changeTitle()
  },

  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
  },

  computed: {
    ...mapGetters('qltnCommon', [ 'kyHoaDonHienTaiGetter' ]),
    TBaggregatesColumns () {
      return [{ field: 'MA_TB', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.TBcustomAggregate }]
    },
    TCaggregatesColumns () {
      return [{ field: 'MA_TB', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.TCcustomAggregate }]
    }
  },

  data () {
    return {
      header: {
        title: 'DANH SÁCH THUÊ BAO KHÔNG XỬ LÝ',
        list: [
          { name: 'Xử lý thuê bao', link: { name: 'Ui.cards' } },
          { name: 'Danh sách thuê bao không khóa mở', link: { name: 'Ui.buttons' } }
        ]
      },
      tag: null,
      dsloaiid: null,
      thongtinKyHoaDon: {
        kyHoaDonSelected: null,
        chuKySelected: null
      },

      params: {
        loaiDS_Options: [],
        loaiDS_Selected: 0,
        soMayAcc: '',
        tenTB: '',
        ngayTH_Checked: true,
        ngayTH: new Date(),
        denNgay_Checked: false,
        denNgay: new Date(),
        today: new Date(),
        lyDo: '',
        ghiChu: ''
      },

      docFileThem_params: {
        kyHoaDon: '',
        loaiDSId: null,
        title: '',
        tuNgay: null,
        denNgay_Checked: null,
        denNgay: null,
        lyDo: null
      },

      nhapMoiBtnEnable: false,
      ghiLaiBtnEnable: false,
      xoaBtnEnable: false,
      huyBoBtnEnable: false,

      commons_params: {
        thueBao_Id: 0,
        thanhToan_Id: 0,
        kiemTraQuyen: false,
        keyRDS_ID: 0
      },

      TTThueBao_Items: [],
      TTThueBao_grid: {
        items: [],
        checked: [],
        key: 1
      },
      TTTraCuu_grid: {
        items: [],
        checked: []
      },
      customTemplate: function () {
        return { template: Vue.component('footerTemplate', {
          template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
          data () { return { data: { data: {} } } }
        })
        }
      },
      checkAll: false,
      columnsCheckboxAll: function(){
        return {
          template: {
            template: `
                        <div class="check-action">
                            <input type="checkbox" v-model="parent.checkAll" class="uncheck"/>
                            <span class="name"></span>
                        </div>
                        `,
            data: function () {
              return {};
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      columnsCheckbox: function(){
        return {
          template: {
            template: `
                        <div class="check-action">
                            <input
                                type="checkbox"
                                class="uncheck"
                                v-model="parent.TTTraCuu_grid.checked"
                                :value="data.MA_TB"/>
                            <span class="name"></span>
                        </div>
                        `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
    }
  },

  validations: {
    params: {
      soMayAcc: {
        maxLength: maxLength(50)
      },
      lyDo: {
        required,
        maxLength: maxLength(500)
      },
      ghiChu: {
        maxLength: maxLength(500)
      }
    }
  },

  methods: {
    ...mapActions('qltnCommon', [ 'getKyHoaDonHienTai' ]),

    chukycuocChangeHandler (e) {
      this.thongtinKyHoaDon.kyHoaDonSelected = moment(e.p_kyhoadon).format('YYYYMM') + '01'
      this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no
    },

    async createDSLoai () {
      if (this.dsloaiid !== '') {
        let rs = this.dsloaiid.split(';')
        console.log(rs)
        if (rs.length > 0 &&
                (rs.includes(DS_KHONG_XULY.THUEBAO_KHONG_SMS_TBC.toString()) ||
                rs.includes(DS_KHONG_XULY.THUEBAO_KHONG_SMS_EMAIL.toString()) ||
                rs.includes(DS_KHONG_XULY.THUEBAO_KHONG_NHACNO_TBC.toString()) ||
                rs.includes(DS_KHONG_XULY.THUEBAO_KHONG_KHOAMAY_TUDONG.toString()) ||
                rs.includes(DS_KHONG_XULY.THUEBAO_KHONG_XNTT_SMS.toString()))) {
          this.params.loaiDS_Options = [
            { id: DS_KHONG_XULY.THUEBAO_KHONG_SMS_TBC, text: 'Thuê bao không nhận thông báo cước qua SMS' },
            { id: DS_KHONG_XULY.THUEBAO_KHONG_SMS_EMAIL, text: 'Thuê bao không nhận thông báo cước qua Email' },
            { id: DS_KHONG_XULY.THUEBAO_KHONG_NHACNO_TBC, text: 'Thuê bao không nhắc nợ cước qua SMS' },
            { id: DS_KHONG_XULY.THUEBAO_KHONG_KHOAMAY_TUDONG, text: 'Thuê bao không khóa máy tự động' },
            { id: DS_KHONG_XULY.THUEBAO_KHONG_XNTT_SMS, text: 'Thuê bao không xác nhận thanh toán SMS' }
          ]
        } else if (this.dsloaiid == DS_KHONG_XULY.THUEBAO_KHONG_NHANHONG.toString()) {
          this.params.loaiDS_Options = [
            { id: DS_KHONG_XULY.THUEBAO_KHONG_NHANHONG, text: 'Thuê bao không nhận hỏng' }
          ]
        } else if (rs.length > 0 &&
                            (rs.includes(DS_KHONG_XULY.THUEBAO_KHONG_OB_LM.toString()) ||
                            rs.includes(DS_KHONG_XULY.THUEBAO_KHONG_OB_BH.toString()))) {
          this.params.loaiDS_Options = [
            { id: DS_KHONG_XULY.THUEBAO_KHONG_OB_LM, text: 'Thuê bao không khảo sát Lắp mới' },
            { id: DS_KHONG_XULY.THUEBAO_KHONG_OB_BH, text: 'Thuê bao không khảo sát Báo hỏng' }
          ]
        } else {
          this.params.loaiDS_Options = [
            { id: DS_KHONG_XULY.THUEBAO_KHONGCHE, text: 'Thuê bao khống chế' },
            { id: DS_KHONG_XULY.THUEBAO_KHONG_HUY, text: 'Thuê bao không hủy' }
          ]

          if (this.dsloaiid === '') {
            this.params.loaiDS_Options.push({ id: DS_KHONG_XULY.THUEBAO_KHONG_KHOA, text: 'Thuê bao không khóa' })
            this.params.loaiDS_Options.push({ id: DS_KHONG_XULY.THUEBAO_KHONG_NHACNO, text: 'Thuê bao không nhắc nợ' })
          }
        }
      } else {
          // this.params.loaiDS_Options = [
          //   { id: DS_KHONG_XULY.THUEBAO_KHONGCHE, text: 'Thuê bao khống chế' },
          //   { id: DS_KHONG_XULY.THUEBAO_KHONG_HUY, text: 'Thuê bao không hủy' }
          // ]

          // if (this.dsloaiid === '') {
          //   this.params.loaiDS_Options.push({ id: DS_KHONG_XULY.THUEBAO_KHONG_KHOA, text: 'Thuê bao không khóa' })
          //   this.params.loaiDS_Options.push({ id: DS_KHONG_XULY.THUEBAO_KHONG_NHACNO, text: 'Thuê bao không nhắc nợ' })
          // }
          await this.getLoaiDs();
        }
    },
    getLoaiDs: async function () {
            this.params.loaiDS_Options=[];
                try {
                    const { data } = await this.axios.get(
                    "/web-thuno/api/thu-no/quan-ly-dai-ly/danh-muc/KHONGKHOAMO"
                    );
                    if (data && data.error == "200") {
                    if (data.data.length > 0) {
                        
                        data.data.forEach(item=>{
                            this.params.loaiDS_Options.push(
                                {
                                    id:item.ID,
                                    text:item.TEXT
                                }
                            );

                        });
                    }
                    }
                } catch (error) { }
                },
    validateData () {
      this.$v.$touch()
      let errorMessage = []
      let focusInput = []

      if (!this.$v.params.soMayAcc.maxLength) {
        errorMessage.push('Trường số máy/Acc dữ liệu vượt quá độ dài cho phép!')
        focusInput.push('soMayAcc')
      }

      if (!this.$v.params.lyDo.required) {
        errorMessage.push('Trường lý do bắt buộc nhập dữ liệu!')
        focusInput.push('lyDo')
      } else if (!this.$v.params.lyDo.maxLength) {
        errorMessage.push('Trường lý do dữ liệu vượt quá độ dài cho phép!')
        focusInput.push('lyDo')
      }

      if (!this.$v.params.ghiChu.maxLength) {
        errorMessage.push('Trường ghi chú dữ liệu vượt quá độ dài cho phép!')
        focusInput.push('ghiChu')
      }

      // if (this.params.ngayTH_Checked && this.params.denNgay_Checked) {
      //     this.params.ngayTH.setSeconds(0, 0);
      //     this.params.denNgay.setSeconds(0, 0);
      //     if (this.params.ngayTH.getTime() > this.params.denNgay.getTime()) {
      //         errors.push('Đến ngày phải lớn hơn ngày TH!');
      //     }
      // }

      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\r\n'))
        this.$refs[focusInput[0]].focus()
        return false
      } else return true
    },

    TBcustomAggregate () {
      return this.TTThueBao_grid.checked.length + '/' + this.TTThueBao_grid.items.length
    },

    TCcustomAggregate () {
      return this.TTTraCuu_grid.checked.length + '/' + this.TTTraCuu_grid.items.length
    },

    showTraCuuDanhBa () {
      // UR2.8.002
      this.$refs.dlgTraCuuDanhBa.showModal()
    },

    async acceptDanhBa (args) {
      console.log(args)
      if (args) {
        this.params.tenTB = ''
        this.params.soMayAcc = args.ma_kh ? args.ma_kh.trim() : ''
        if (this.params.soMayAcc !== '') {
          let tuNgay = this.params.ngayTH_Checked ? moment(this.params.ngayTH).format('DD/MM/YYYY') : null
          let denNgay = this.params.denNgay_Checked ? moment(this.params.denNgay).format('DD/MM/YYYY') : null
          let ds = await this.getDanhSachKhongKhoaMo(this.params.soMayAcc, tuNgay, denNgay)

          if (ds.length > 0) { this.TTTraCuu_grid.items = ds } else {
            this.setButton(1)
            this.TTTraCuu_grid.items = []
            this.commons_params.thueBao_Id = 0
            this.commons_params.thanhToan_Id = 0
            this.params.ghichu = ''
            this.params.tenTB = ''
          }

          if (this.TTTraCuu_grid.items.length > 0) { return this.$toast.error(`Thuê bao ${this.params.soMayAcc} đã tồn tại trong danh sách`) }
          await this.displayTTThueBao(args.ma_kh)
        }
      }
    },

    onTBItemsChecked (items) {
      this.TTThueBao_grid.checked = []
      items.forEach(item => {
        let tb = this.TTThueBao_grid.items.find(e => e.MA_TB === item)
        if (tb !== undefined) { this.TTThueBao_grid.checked.push(tb) }
      })

      let dataGridObj = this.$refs.thueBaoGrid.$refs.grid.ej2Instances
      dataGridObj.aggregateModule.refresh()
    },

    onTCItemsChecked (items) {
      this.TTTraCuu_grid.checked = []
      items.forEach(item => {
        let tb = this.TTTraCuu_grid.items.find(e => e.MA_TB === item)
        if (tb !== undefined) { this.TTTraCuu_grid.checked.push(tb) }
      })

      let dataGridObj = this.$refs.traCuuGrid.$refs.grid.ej2Instances
      dataGridObj.aggregateModule.refresh()
    },

    async onTCRowSelected (args) {
      this.commons_params.thanhToan_Id = args.data.THANHTOAN_ID
      this.commons_params.thueBao_Id = args.data.THUEBAO_ID
      this.params.soMayAcc = args.data.MA_TB
      this.params.tenTB = args.data.TEN_TB
      this.params.lyDo = args.data.LYDO ? args.data.LYDO : ''
      this.params.ghiChu = args.data.GHICHU

      if (this.params.loaiDS_Selected === 5) { // THUEBAO_KHONG_NHANHONG
        this.params.ngayTH = args.data.NGAY_TH
        if (args.data.NGAY_TH_DEN !== null && args.data.NGAY_TH_DEN !== '') { this.params.denNgay = args.data.NGAY_TH_DEN }
      }
      this.setButton(3)
      await this.getTTNoThueBao(this.commons_params.thanhToan_Id)
    },

    async onTCRowClicked (index, args){
      let item = {data : args}
      this.onTCRowSelected(item)
    },

    changeTitle () {
      // if (type === DS_KHONG_XULY.THUEBAO_KHONG_SMS_TBC ||
      //   type === DS_KHONG_XULY.THUEBAO_KHONG_SMS_EMAIL ||
      //   type === DS_KHONG_XULY.THUEBAO_KHONG_NHACNO_TBC ||
      //   type === DS_KHONG_XULY.THUEBAO_KHONG_KHOAMAY_TUDONG ||
      //   type === DS_KHONG_XULY.THUEBAO_KHONG_XNTT_SMS) {
      //     this.header.title = 'DANH SÁCH THUÊ BAO KHÔNG NHẬN THÔNG BÁO CƯỚC QUA SMS/EMAIL'
      // } else if (type === DS_KHONG_XULY.THUEBAO_KHONG_NHANHONG) {
      //   this.header.title = 'DANH SÁCH THUÊ BAO KHÔNG NHẬN HỎNG'
      // } else if (type === DS_KHONG_XULY.THUEBAO_KHONG_OB_LM || type === DS_KHONG_XULY.THUEBAO_KHONG_OB_BH) {
      //   this.header.title = 'DANH SÁCH THUÊ BAO KHÔNG KHẢO SÁT'
      // } else {
      //   this.header.title = 'DANH SÁCH THUÊ BAO KHÔNG XỬ LÝ'
      // }
      let title = 'Danh sách ' + this.params.loaiDS_Options.find(x => x.id === this.params.loaiDS_Selected).text
      this.header.title = title.toUpperCase()
    },

    /* type: 1 - Lấy DS click; 3 - call from another function */
    async onLayDS (type) {

      this.changeTitle()

      this.$v.$reset() // Reset validator
      if (!this.dateValidate()) { return }

      let tuNgay = this.params.ngayTH_Checked ? moment(this.params.ngayTH).format('DD/MM/YYYY') : null
      let denNgay = this.params.denNgay_Checked ? moment(this.params.denNgay).format('DD/MM/YYYY') : null

      this.TTTraCuu_grid.items = await this.getDanhSachKhongKhoaMo('', tuNgay, denNgay)
      if (this.TTTraCuu_grid.items.length <= 0) {
        this.setButton(1)
        this.commons_params.thanhToan_Id = 0
        this.commons_params.thueBao_Id = 0
        this.params.tenTB = ''
        this.params.ghiChu = ''

        if (type !== 3) { this.$toast.error('Không có dữ liệu, vui lòng thử lại!') }
      }
    },

    async onKeyPress () {
      this.params.soMayAcc = this.params.soMayAcc.trim()
      if (this.params.soMayAcc !== '') {
        let ds = await this.getDanhSachKhongKhoaMo(this.params.soMayAcc, '', '')

        if (ds.length > 0) {
          this.TTTraCuu_grid.items = ds
          return this.$toast.error('Thuê bao ' + this.params.soMayAcc + ' đã tồn tại trong danh sách!')
        } else {
          this.commons_params.thanhToan_Id = 0
          this.commons_params.thueBao_Id = 0
          this.params.tenTB = ''
          this.params.ghiChu = ''
        }

        await this.displayTTThueBao(this.params.soMayAcc)
      } else { this.$toast.error('Vui lòng nhập số máy/Acc để tra cứu!') }
    },

    async displayTTThueBao (maTB) {
      await this.getTTThueBaoTheoMaTB(maTB)
      if (this.TTThueBao_Items.length === 1) {
        this.commons_params.thanhToan_Id = this.TTThueBao_Items[0].THANHTOAN_ID
        this.commons_params.thueBao_Id = this.TTThueBao_Items[0].THUEBAO_ID
        this.params.tenTB = this.TTThueBao_Items[0].TEN_TB
      } else if (this.TTThueBao_Items.length > 1) {
        this.$refs.dlgDanhsachthuebao.openDialog()
        return false
      } else {
        this.commons_params.thanhToan_Id = 0
        this.commons_params.thueBao_Id = 0
        this.params.tenTB = ''
      }

      await this.getTTNoThueBao(this.commons_params.thanhToan_Id)
      if (this.TTThueBao_grid.items.length <= 0) { this.$toast.error('Không có dữ liệu, vui lòng thử lại!') }
    },

    async selectThueBao (index) {
      if (index >= 0) {
        this.commons_params.thanhToan_Id = this.TTThueBao_Items[index].THANHTOAN_ID
        this.commons_params.thueBao_Id = this.TTThueBao_Items[index].THUEBAO_ID
        this.params.tenTB = this.TTThueBao_Items[index].TEN_TB
        await this.getTTNoThueBao(this.commons_params.thanhToan_Id)
        if (this.TTThueBao_grid.items.length <= 0) { this.$toast.error('Không có dữ liệu, vui lòng thử lại!') }
      }
    },

    onNhapMoi () {
      this.setButton(1)
    },

    async onGhiLai () {
      this.params.lyDo = this.params.lyDo.trim()
      if (!this.validateData()) { return }

      if (this.commons_params.kiemTraQuyen && !this.params.denNgay_Checked && this.params.loaiDS_Selected === 5) { return this.$toast.error('Hãy chọn thông tin đến ngày!') }

      if (!this.nhapMoiBtnEnable) {
        // Them moi
        if (this.params.loaiDS_Selected !== 6 && this.params.loaiDS_Selected !== 7) {
          if (this.TTThueBao_grid.items.length <= 0 || this.commons_params.thanhToan_Id === 0 || this.commons_params.thueBao_Id == 0) { return this.$toast.error('Chưa có dữ liệu, vui lòng kiểm tra lại!') }

          if (this.params.loaiDS_Selected === 0) {
            if (this.TTThueBao_grid.checked.length <= 0) { return this.$toast.error('Chưa chọn thuê bao!') }

            if (this.TTThueBao_grid.checked.filter(item => item.TRANGTHAITB_ID === 1).length > 0) {
              let maTB = this.TTThueBao_grid.checked.find(item => item.TRANGTHAITB_ID === 1).MA_TB
              return this.$toast.error(`Thuê bao ${maTB} đang hoạt động bình thường. \r\nBạn không được phép thêm thuê bao này vào danh sách khống chế`)
            }
          }

          await this.onUpdateProcess(1, this.TTThueBao_grid.checked)
        } else if (this.TTThueBao_grid.items.length <= 0) {
          // Add 1 item to active for loop in onUpdateProcess function:
          await this.onUpdateProcess(3, { LYDO: this.params.lyDo })
        }
      } else {
        if (this.TTTraCuu_grid.checked.length <= 0 || this.TTTraCuu_grid.items.length <= 0) { return this.$toast.error('Chưa chọn thuê bao!') }
        const dsResult = this.TTTraCuu_grid.items.filter(item=>this.TTTraCuu_grid.checked.includes(item.MA_TB))

        await this.onUpdateProcess(2, dsResult)
      }
    },

    onXuatFile () {
      if (this.TTTraCuu_grid.items.length <= 0) { this.$toast.error('Chưa có danh sách để xuất excel!') } else { this.$bvModal.show('modal-xuat-file') }
    },

    /* type: 1 - Them moi; 2 - Cap nhat; 3 - THUEBAO_KHONG_OB_LM & THUEBAO_KHONG_OB_BH */
    async onUpdateProcess (type, list) {
      if (!this.dateValidate()) { return false }

      try {
        await this.$bvModal.msgBoxConfirm(`Bạn chắc chắn muốn ${type === 1 ? 'thêm mới' : 'cập nhật'} dữ liệu không?`, {
          'title': 'Thông báo',
          'size': 'lg',
          'buttonSize': 'lg',
          'okVariant': 'primary',
          'okTitle': 'Đồng ý',
          'cancelTitle': 'Hủy bỏ',
          'hideHeaderClose': false,
          'centered': true,
          'modal-class': ['f18', 'text-center']
        })
          .then(async (value) => {
            if (value) {
              let ds = []
              for (let item of list) {
                if (type === 1) { await this.getKey('RDS_ID') } else if (type === 3) { await this.getDanhSachRDSIDKhongXuLy() }
                let data = {
                  rds_id: type === 2 ? item.RDS_ID : this.commons_params.keyRDS_ID,
                  thuebao_id: type === 3 ? 0 : item.THUEBAO_ID,
                  thanhtoan_id: type === 3 ? 0 : item.THANHTOAN_ID,
                  dichvuvt_id: type === 3 ? 0 : item.DICHVUVT_ID,
                  loaitb_id: type === 3 ? 0 : item.LOAITB_ID,
                  loaids: this.params.loaiDS_Selected,
                  lydo: this.params.lyDo ? this.params.lyDo.trim() : '',
                  ngay_th: moment(this.params.ngayTH).format('DD/MM/YYYY HH:mm'),
                  ghichu: this.params.ghiChu ? this.params.ghiChu.trim() : '',
                  may_cn: '-',
                  ngay_cn: moment(new Date()).format('DD/MM/YYYY'),
                  ip_cn: null,
                  nguoi_cn: this.$auth.getUserName() !== -1 ? this.$auth.getUserName() : '-',
                  nhanvien_id: this.$auth.getNguoiDungID() !== -1 ? this.$auth.getNguoiDungID() : 3231,
                  ngay_th_den: this.params.denNgay_Checked ? moment(this.params.denNgay).format('DD/MM/YYYY HH:mm') : null,
                  ma_tb: type === 3 ? (this.params.soMayAcc ? this.params.soMayAcc.trim() : '') : item.MA_TB
                }
                ds.push(data)
              }

              let result = false
              if (type === 1) { result = await this.postDanhSachKhongXuLy(ds) } else { result = await this.updateDanhSachKhongXuLy(ds) }

              if (result) { this.$toast.success('Cập nhật dữ liệu viễn thông tỉnh thành công!') }
              await this.onLayDS(3)
            }
          })
          .catch(err => {
            // An error occurred
            console.log(err)
          })
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
      }
    },

    onHuy () {
      this.clear()
    },

    async onXoa () {
      if (this.TTTraCuu_grid.items.length <= 0) {
        this.$toast.error('Chưa có thuê bao trên lưới để thực hiện!')
        return
      }
      if (this.TTTraCuu_grid.checked.length <= 0) {
        this.$toast.error('Bạn chưa chọn thuê bao!')
        return
      }

      try {
        let loaiDSTitle = this.params.loaiDS_Options.find(e => e.id === this.params.loaiDS_Selected).text
        await this.$bvModal.msgBoxConfirm('Bạn muốn xóa thuê bao này khỏi danh sách ' + loaiDSTitle + ' không?', {
          'title': 'Thông báo',
          'size': 'lg',
          'buttonSize': 'lg',
          'okVariant': 'primary',
          'okTitle': 'Đồng ý',
          'cancelTitle': 'Hủy bỏ',
          'hideHeaderClose': false,
          'centered': true,
          'modal-class': ['f18', 'text-center']
        })
          .then(async (value) => {
            if (value) {
              let dsRDS = ''
              const dsResult = this.TTTraCuu_grid.items.filter(item=>this.TTTraCuu_grid.checked.includes(item.MA_TB))

              dsResult.forEach(item => {
                dsRDS += item.RDS_ID + ','
              })
              let result = await this.deleteDanhSachKhongXuLy(dsRDS.substring(0, dsRDS.length - 1))
              if (result) { this.$toast.success('Xóa dữ liệu trên viễn thông tỉnh thành công!') }

              await this.onLayDS(3)
            }
          })
          .catch(err => {
            // An error occurred
          })
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
      }
    },

    openDocFileThem () {
      let title = 'Thêm danh sách ' + this.params.loaiDS_Options.find(x => x.id === this.params.loaiDS_Selected).text
      // if (this.params.loaiDS_Selected === DS_KHONG_XULY.THUEBAO_KHONGCHE) {
      //   title = 'Thêm danh sách thuê bao chêm khống chế'
      // } else {
      //   title = 'Thêm danh sách thuê bao không được phép mở chêm'
      // }

      // if (this.params.loaiDS_Selected === DS_KHONG_XULY.THUEBAO_KHONG_NHANHONG) {
      //   title = 'Thêm danh sách thuê bao không nhận hỏng'
      // }

      // if (this.params.loaiDS_Selected === DS_KHONG_XULY.THUEBAO_KHONG_OB_LM || this.params.loaiDS_Selected === DS_KHONG_XULY.THUEBAO_KHONG_OB_BH) {
      //   title = 'Thêm danh sách thuê bao không khảo sát'
      // }
      // if (this.params.loaiDS_Selected === DS_KHONG_XULY.THUEBAO_KHONG_SMS_TBC || this.params.loaiDS_Selected === DS_KHONG_XULY.THUEBAO_KHONG_SMS_EMAIL) {
      //   title = 'Thêm danh sách thuê bao không nhận thông báo cước qua SMS/Email'
      // }
      // if (this.params.loaiDS_Selected === DS_KHONG_XULY.THUEBAO_KHONG_NHACNO_TBC) {
      //   title = 'Thêm danh sách thuê bao không gửi thông báo nhắc nợ'
      // }
      // if (this.params.loaiDS_Selected === DS_KHONG_XULY.THUEBAO_KHONG_KHOAMAY_TUDONG) {
      //   title = 'Thêm danh sách thuê bao không khóa máy tự động'
      // }
      // if (this.params.loaiDS_Selected === DS_KHONG_XULY.THUEBAO_KHONG_XNTT_SMS) {
      //   title = 'Thêm danh sách thuê bao không xác nhận thanh toán SMS'
      // }

      this.docFileThem_params = {
        kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
        loaiDSId: this.params.loaiDS_Selected,
        title: title,
        tuNgay: this.params.ngayTH,
        denNgay_Checked: this.params.denNgay_Checked,
        denNgay: this.params.denNgay,
        lyDo: this.params.lyDo
      }
      this.$refs.dlgDocFileDSKhoaMo.openDialog()
    },

    async onDocFileThem (value) {
      if (value) {
        await this.onLayDS(3)
      }
    },

    openXoaFileThem () {
      let title = 'Xoá danh sách ' + this.params.loaiDS_Options.find(x => x.id === this.params.loaiDS_Selected).text
      this.docFileThem_params = {
        kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
        loaiDSId: this.params.loaiDS_Selected,
        title: title,
        tuNgay: this.params.ngayTH,
        denNgay_Checked: this.params.denNgay_Checked,
        denNgay: this.params.denNgay,
        lyDo: this.params.lyDo
      }
      this.$refs.dlgXoaFileDSKhoaMo.openDialog()
    },

    async onXoaFileThem (value) {
      if (value) {
        await this.onLayDS(3)
      }
    },

    dateValidate () {
      if (this.params.ngayTH_Checked && this.params.ngayTH === null) {
        this.$toast.error("Vui lòng chọn trường ngày TH hợp lệ, dạng 'dd/MM/yyyy hh:mm'!")
        return false
      }
      if (this.params.denNgay_Checked && this.params.denNgay === null) {
        this.$toast.error("Vui lòng chọn trường đến ngày hợp lệ, dạng 'dd/MM/yyyy hh:mm'!")
        return false
      }
      return true
    },

    setButton (kieu) {
      this.nhapMoiBtnEnable = false
      this.xoaBtnEnable = false
      this.ghiLaiBtnEnable = false
      this.huyBoBtnEnable = false

      switch (kieu) {
        case 0: // Bat dau
          this.nhapMoiBtnEnable = true
          break
        case 1: // Them moi
          this.ghiLaiBtnEnable = true
          this.huyBoBtnEnable = true
          this.clear()
          break
        case 2: // Huy
          this.nhapMoiBtnEnable = true
          this.xoaBtnEnable = true
          break
        case 3: // Edit
          this.nhapMoiBtnEnable = true
          this.xoaBtnEnable = true
          this.ghiLaiBtnEnable = true
          this.huyBoBtnEnable = true
          break
        default: // -1
          this.ghiLaiBtnEnable = true
          this.huyBoBtnEnable = true
          break
      }
    },

    clear () {
      this.params.soMayAcc = '',
      this.params.tenTB = '',
      this.params.lyDo = '',
      this.params.ghiChu = '',
      this.TTThueBao_grid.items = [],
      this.commons_params.thanhToan_Id = 0,
      this.commons_params.thueBao_Id = 0
    },

    async getDanhSachThamSoMD (id) {
      try {
        this.loading(true)
        this.commons_params.kiemTraQuyen = false
        let response = await API.getDanhSachThamSoMD(this.axios, id)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          response.data.data.forEach(item => {
            if (item.MA_TS === 'DOCFILE_DSTB_KHONGNHANHONG' && item.TEN_TS.trim() === '1') {
              this.commons_params.kiemTraQuyen = true
            }
          })
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getKey (key) {
      try {
        this.loading(true)
        this.commons_params.keyRDS_ID = 0
        let response = await API.getKey(this.axios, key)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
          this.commons_params.keyRDS_ID = response.data.data[0]
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDanhSachKhongKhoaMo (maTB, tuNgay, denNgay) {
      try {
        this.loading(true)
        // this.TTTraCuu_grid.items = [];
        let apiParams = {
          pLoaiDS: this.params.loaiDS_Selected,
          pMaTB: maTB,
          pTuNgay: tuNgay,
          pDenNgay: denNgay,
          pageNo: 0,
          pageSize: 100000,
          totalRow: 0
        }
        let response = await API.getDanhSachKhongKhoaMo(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data.data.data !== undefined) {
          //    this.TTTraCuu_grid.items = response.data.data.data;
          return response.data.data.data
        }
        return []
      } catch (error) {
        return []
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDanhSachRDSIDKhongXuLy () {
      try {
        this.loading(true)
        this.commons_params.keyRDS_ID = 0
        let apiParams = {
          pLoaiDS: this.params.loaiDS_Selected,
          pMaTB: this.params.soMayAcc
        }
        let response = await API.getDanhSachRDSIDKhongXuLy(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.commons_params.keyRDS_ID = response.data.data[0]
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getTTNoThueBao (thanhToanId) {
      try {
        this.loading(true)
        this.TTThueBao_grid.key++
        this.TTThueBao_grid.items = []
        let apiParams = {
          pKyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
          pThanhToanId: thanhToanId,
          pLoaiDSId: this.params.loaiDS_Selected
        }
        let response = await API.getTTNoThueBao(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.TTThueBao_grid.items = response.data.data
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getTTThueBaoTheoMaTB (maTB) {
      try {
        this.loading(true)
        this.TTThueBao_Items = []
        let response = await API.getTTThueBaoTheoMaTB(this.axios, maTB)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.TTThueBao_Items = response.data.data
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    /* rdsIds: "id,id" */
    async deleteDanhSachKhongXuLy (rdsIds) {
      try {
        this.loading(true)
        let apiBody = {
          pMayHuy: 'GDKH',
          pNguoiHuy: this.$auth.getUserName() !== -1 ? this.$auth.getUserName() : 'GDKH',
          pIpHuy: '192.168.55.10',
          pRDSIds: rdsIds
        }
        let response = await API.deleteDanhSachKhongXuLy(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000' && response.data.data === true) {
          return true
        } else {
          this.$toast.success(response.data.message_detail)
          return false
        }
      } catch (error) {
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    },

    async updateDanhSachKhongXuLy (ds) {
      try {
        this.loading(true)
        let apiBody = {
          pData: ds
        }
        let response = await API.updateDanhSachKhongXuLy(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000' && response.data.data === true) {
          return true
        } else {
          this.$toast.success(response.data.message_detail)
          return false
        }
      } catch (error) {
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    },

    async postDanhSachKhongXuLy (ds) {
      try {
        this.loading(true)
        let apiBody = {
          pData: ds
        }
        let response = await API.postDanhSachKhongXuLy(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000' && response.data.data === true) {
          return true
        } else {
          this.$toast.success(response.data.message_detail)
          return false
        }
      } catch (error) {
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    }
  },

  watch: {
    'params.loaiDS_Selected': async function (newVal, oldVal) {
      await this.onLayDS(3)
    },
    'TTTraCuu_grid.checked': function (value) {
      this.TTTraCuu_grid.checked = value
      this.$refs.traCuuGrid.$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    checkAll: function (value) {
        this.TTTraCuu_grid.checked = [];
        if (value) {
            for (const e of this.TTTraCuu_grid.items) {
                this.TTTraCuu_grid.checked.push(e.MA_TB);
            }
        }
    },
  }
}
</script>
