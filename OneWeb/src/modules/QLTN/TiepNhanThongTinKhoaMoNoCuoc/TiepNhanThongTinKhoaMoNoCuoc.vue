<template src="./TiepNhanThongTinKhoaMoNoCuoc.html"></template>
<style scoped src="./style.scss"></style>
<script>
import { mapActions, mapGetters } from 'vuex'
import TNAPI from '../api/TiepNhanTTKhoaMoMayNoCuocAPI.js'
import KMAPI from '../api/KhoaMayNoCuocTTAPI.js'
import MMAPI from '../api/MoMayTheoDSAPI.js'
import CommonsAPI from '../api/Commons.js'
import XuatFile from '../../CSKH/utils/XuatFile.vue'
import PopupGiaoPhieu from './components/PopupGiaoPhieu.vue'
import { required, maxLength } from 'vuelidate/lib/validators'
import EventBus from '@/utils/eventBus'
import CreateIssue_V1 from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupCreateIssue/CreateIssue.vue'
import CreateIssue from './popupCreateIssue_v2/CreateIssue_V2.vue'
import PopupCapNhatDV from '@/modules/contract/setup/Liquidate/components/popupCapNhatDV.vue'
import {
  TrangThaiHD,
  TRANGTHAI_DONGBO,
  DichVuVienThong,
  LoaiHopDong
}
  from '@/utils/Enum'
import moment from 'moment'
import xlsx from 'xlsx'
import Vue from 'vue'
import CompleteProfileAPI from '../../contract/complete_profile/CompleteProfileITVAS/API/CompleteProfileAPI'
import jira from '../../contract/complete_profile/CompleteProfileITVAS/popups/popupCreateIssue/PopUpApi'
import { defineAsyncComponent } from '@vue/composition-api'

export default {
  components: {
    appModalXuatDuLieu: XuatFile,
    PopupGiaoPhieu,
    CreateIssue,
    CreateIssue_V1,
    ppWorkflowActivation: defineAsyncComponent(() => import('./popup/WorkflowActivation/WorkflowActivation.vue')),
    PopupCapNhatDV
  },
  name: 'TiepNhanThongTinKhoaMoNoCuoc',
  async created () {
    EventBus.$on('onDeleteRow', this.onDeleteLyDo)
    await this.initForm()
  },

  destroyed () {
    EventBus.$off('onDeleteRow', this.onDeleteLyDo)
  },

  mounted () {
    this.$refs.soMayAcc.focus()
    this.changeDisplay(this.pyc_Params.dichVu_Selected)
    this.LAYDS_TRANGTHAI_JIRA();
  },

  computed: {
    ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter'])
  },

  data () {
    return {
      jira_resolution_name: "",
      tieude_jira: "",
      trangthai_jira_value: "",
      list_it360: [],
      ds_trangthai_jira: [],
      issue_id: "",
      // Common parameters:
      common_Params: {
        tag: '1+7+1;4;7;8;9;10;11;12;6;13;14;15;16',
        TTHD_Id: 0,
        dsLoaiHD_Id: [],
        dsDichVu_Id: [],
        giaoTheoQD_Title: 'Giao theo QĐ (15 ngày)',
        nhanTin_NhanVien_DiaBan: 0,
        xacMinh_TheoYeuCau: 0,
        KT_TTHD_KichHoat: false,
        KT_ThamSo_ThuHoi: false,
        huongGiao_Id: 0,
        luong_Id: 0,
        huongGiao: '',
        controls: [
          'tsbtnKichHoat',
          'tsbtnGiaoPhieu',
          'tsbtnHoanCong',
          'tsbtnHoanThanh',
          'tsbtnGiaoViec',
          'tsbtnHoanThienHS',
          'tsbtnTraPhieu',
          'tsbtn_KichHoat_CA',
          'tsbtnCreatIssue'
        ],
        huongGiaoId_GP: 0
      },

      // Thong tin phieu yeu cau:
      pyc_Params: {
        dichVu_Selected: null,
        dichVu_Options: [],
        quyTrinh_Selected: null,
        quyTrinh_Options: [],
        loaiHinh_Options: [],
        loaiHinh_Selected: null,
        loaiHinh_Checked: false,
        soMayAcc: '',
        kieuYC_Options: [
          { id: 0, text: 'Mở máy nợ cước' },
          { id: 1, text: 'Khóa máy do nợ cước' }
        ],
        kieuYC_Selected: 1,
        thuHoiTB: 1,
        thuHoiTB_Enable: true,
        noiDungTH: '',
        boQuaLoiKhiKichHoat: true,
        boQuaLoiKhiHoanCong: true,
        loaiPhieu: 0
      },

      // Thong tin nguoi giao viec:
      gv_Params: {
        nguoiGV_Options: [],
        nguoiGV_Selected: 0,
        ngayGV_Checked: false,
        ngayGV: new Date(),
        today: new Date(),
        ngayHT_Checked: false,
        ngayHT: new Date()
      },

      display_Params: {
        lyDoHuyBtn_Visible: false,
        hoanThienBtn_Enable: false,
        hoanThienBtn_Visible: false,
        xuatFileBtn_Enable: false,
        xoaHDBtn_Visible: false,
        hoanCongBtn_Enable: false,
        hoanCongBtn_Visible: false,
        kichHoatBtn_Enable: false,
        kichHoatBtn_Visible: false,
        kichhoatCnttBtn_Enable: false,
        kichhoatCnttBtn_Visible: false,
        tsbtnCreatIssue_Enable: false,
        tsbtnCreatIssue_Visible: false,
        giaoPhieuBtn_Enable: true,
        giaoPhieuBtn_Visible: false,
        giaoViecBtn_Enable: false,
        giaoViecBtn_Visible: false,
        traPhieuBtn_Enable: false,
        traPhieuBtn_Visible: false,
        hoanThanhBtn_Enable: false,
        hoanThanhBtn_Visible: false,
        xuatTextBtn_Title: 'In phiếu',
        kichHoatBtn_Tooltip: 'Kích hoạt tổng đài',
        kichHoatCnttBtn_Tooltip: 'Kích hoạt backend CNTT',
        isShow: {
          ppWorkflowActivation: false,
        }
      },
      thaoTac: {
        items: [],
        controls: []
      },
      nhanVien_grid: {
        items: [],
        selected: {}
      },
      lyDo_grid: {
        items: []
      },
      thueBao_grid: {
        items: [],
        checked: [],
        selected: {},
        fields: [],
        commonFields: [
          { fieldName: 'ISCHECK', headerText: '', allowSorting: false, allowFiltering: false, textAlign: 'Center', width: 40, headerTemplate: () => { return this.columnsCheckboxAll() }, template: () => { return this.columnsCheckbox() }},
          {
            fieldName: 'MA_TB',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'LOAIHINH_TB',
            headerText: 'Loại hình',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'TEN_TB',
            headerText: 'Tên thuê bao',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'DIACHI_LD',
            headerText: 'Địa chỉ lắp đặt',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'TEN_DV',
            headerText: 'Đơn vị gửi',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'CHIEU_KM',
            headerText: 'Chiều khóa/mở',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'HOST_LD',
            headerText: 'Host LĐ',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'NGAYGIAO',
            headerText: 'Ngày giao',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'CACH_KM',
            headerText: 'Cách khóa/mở',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'NV_XACNHAN',
            headerText: 'NV xác nhận',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'KQ_XACNHAN',
            headerText: 'KQ xác nhận',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'ND_THUCHIEN',
            headerText: 'ND thực hiện',
            allowFiltering: true,
            allowSorting: false,
            width: 130
          },
          {
            fieldName: 'NGAY_KYTHUAT_XM',
            headerText: 'Ngày xác nhận',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          }
        ]
      },
      checkAll: false,
      columnsCheckboxAll: function () {
        return {
          template: {
            template: `
                        <div class="check-action">
                            <input type="checkbox" v-model="parent.checkAll" class="uncheck"/>
                            <span class="name"></span>
                        </div>
                        `,
            data: function () {
              return {}
            },
            computed: {
              parent () {
                return this.$parent.$parent.$parent
              }
            }
          }
        }
      },
      columnsCheckbox: function () {
        return {
          template: {
            template: `
                        <div class="check-action">
                            <input
                                type="checkbox"
                                class="uncheck"
                                v-model="parent.thueBao_grid.checked"
                                :value="data.MA_TB"/>
                            <span class="name"></span>
                        </div>
                        `,
            data () {
              return {
                data: {}
              }
            },
            computed: {
              parent () {
                return this.$parent.$parent.$parent
              }
            }
          }
        }
      },
      header: {
        title: 'TIẾP NHẬN KHÓA MỞ DỊCH VỤ CỐ ĐỊNH',
        list: [
          { name: 'Xử lý thuê bao', link: { name: 'Ui.cards' } },
          { name: 'Tiếp nhận thông tin Khóa/Mở nợ cước', link: { name: 'Ui.buttons' } }
        ]
      },
      deleteTemplate: function () {
        return {
          template: Vue.component('columnTemplate', {
            template: `<div><button class="btn btn-danger lh14 pad3" @click="onDeleteRow" title="Xóa lý do hủy">
                                    <span class="-ap icon-close"></span>
                                </button></div>`,
            data () {
              return { data: {} }
            },
            methods: {
              onDeleteRow () {
                EventBus.$emit('onDeleteRow', this.data)
              }
            }
          })
        }
      }
    }
  },

  validations: {
    pyc_Params: {
      noiDungTH: {
        required,
        maxLength: maxLength(500)
      }
    }
  },

  methods: {
    ...mapActions('qltnCommon', ['getDanhSachDichVuVienThong']),

    loadCommonParams (tag) {
      try {
        if (tag !== '') {
          let tagArr = tag.split('+')
          if (tagArr.length >= 1) { this.common_Params.TTHD_Id = Number(tagArr[0]) }
          if (tagArr.length >= 2) { this.common_Params.dsLoaiHD_Id = tagArr[1].split(';') }
          if (tagArr.length >= 3) { this.common_Params.dsDichVu_Id = tagArr[2].split(';') }
        } else {
          this.common_Params.TTHD_Id = TrangThaiHD.DANG_THI_CONG
          this.common_Params.dsLoaiHD_Id = ['1']
          this.common_Params.dsDichVu_Id = ['1']
        }
      } catch (err) {
        this.common_Params.TTHD_Id = TrangThaiHD.DANG_THI_CONG
        this.common_Params.dsLoaiHD_Id = ['1']
        this.common_Params.dsDichVu_Id = ['1']
        console.log(err)
      }
    },

    async initForm () {
      // ĐỌC THAM SỐ:
      console.log(this.$route)

      if (this.$route.params && this.$route.params.tag !== null && this.$route.params.tag !== undefined) { this.common_Params.tag = this.$route.params.tag }
      // console.log(this.$route.query)
      let tag_form = this.$route.query.tag
      if(tag_form)
      {
        console.log(tag_form);
        console.log(decodeURIComponent(tag_form));
        if(tag_form.includes('%3B7'))
        {
          this.common_Params.tag = decodeURIComponent(tag_form)
        }
        else
        {
          this.common_Params.tag = tag_form
        }
        //this.common_Params.tag = tag_form
      }
      console.log(this.common_Params.tag);
      this.loadCommonParams(this.common_Params.tag)
      if (this.common_Params.TTHD_Id === TrangThaiHD.DANG_THI_CONG) { this.display_Params.lyDoHuyBtn_Visible = true }

      if (this.gv_Params.nguoiGV_Options.length === 0) { await this.getDanhSachNguoiGiaoViec() }
      this.gv_Params.nguoiGV_Selected = this.$auth.getNhanVienID()

      if (this.danhSachDichVuVienThongGetter === undefined || this.danhSachDichVuVienThongGetter.length === 0) { await this.getDanhSachDichVuVienThong() }
      this.danhSachDichVuVienThongGetter.forEach(item => {
        if (this.common_Params.dsDichVu_Id.indexOf(item.DICHVUVT_ID.toString()) !== -1) { this.pyc_Params.dichVu_Options.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT }) }
      })
      this.pyc_Params.dichVu_Options.sort(this.compare)
      this.pyc_Params.dichVu_Selected = this.pyc_Params.dichVu_Options !== null && this.pyc_Params.dichVu_Options.length > 0 ? this.pyc_Params.dichVu_Options[0].id : null

      if (this.$route.params) {
        if (this.$route.params.dichvuID) {
          this.pyc_Params.dichVu_Selected = this.$route.params.dichvuID
        }
        if (this.$route.params.kieuYCID !== null && this.$route.params.kieuYCID !== undefined) {
          this.pyc_Params.kieuYC_Selected = this.$route.params.kieuYCID
        }
        if (this.$route.params.tthdID !== null && this.$route.params.tthdID !== undefined) {
          this.common_Params.TTHD_Id = this.$route.params.tthdID
        }
      }

      await this.getThamSoMD()
      await this.getThamSoMDMATS()

      if (this.common_Params.TTHD_Id === TrangThaiHD.DA_THI_CONG_XONG) {
        this.display_Params.hoanThienBtn_Enable = true
        this.display_Params.hoanThienBtn_Visible = true
      }
    },

    async onGetTT () {
      await this.onHienThiDanhSach()
      if (this.thueBao_grid.items.length > 0) {
        this.display_Params.xuatFileBtn_Enable = true
      } else {
        this.pyc_Params.soMayAcc = ''
        this.gv_Params.ngayGV_Checked = false
        this.gv_Params.ngayHT_Checked = false
        this.nhanVien_grid.items = []
        this.display_Params.xuatFileBtn_Enable = false
        this.display_Params.xoaHDBtn_Visible = false
        this.$toast.error('Không có dữ liệu, vui lòng thử lại!')
      }
    },

    async onKeyPress () {
      this.pyc_Params.soMayAcc = this.pyc_Params.soMayAcc.trim()
      await this.getDanhSachHopDongTBKhoaMoMayHC(this.pyc_Params.soMayAcc)
      if (this.thueBao_grid.items.length > 0) { this.display_Params.hoanThienBtn_Enable = true }
    },

    validateData () {
      this.$v.$touch()
      let errorMessage = []

      if (!this.$v.pyc_Params.noiDungTH.required) { errorMessage.push('Trường nội dung thực hiện không được để trống!') }

      if (!this.$v.pyc_Params.noiDungTH.maxLength) { errorMessage.push('Trường nội dung thực hiện vượt quá độ dài cho phép!') }

      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return false
      } else return true
    },

    async onUpdateNoiDungTH () {
      if (Object.keys(this.thueBao_grid.selected).length > 0) {
        if (!this.validateData()) { return this.$refs.noiDungTH.focus() }

        let result = await this.updateNoiDungTH(this.thueBao_grid.selected.PHIEU_ID, this.pyc_Params.noiDungTH)
        if (result) { this.$toast.success('Cập nhật nội dung thực hiện cho phiếu thành công!') }

        await this.onHienThiDanhSach2()
      } else { this.$toast.error('Chưa có phiếu để cập nhật!') }
    },

    async onUpdateTT () {
      if (!this.onCheckDieuKienTB(0)) { return }
      if (!this.gv_Params.ngayHT_Checked) { return this.$toast.error('Hãy chọn ngày hoàn thành!') }
      if (this.gv_Params.ngayGV > this.gv_Params.ngayHT) { return this.$toast.error('Ngày giao việc không được lớn hơn ngày hoàn thành!') }
      if (this.gv_Params.ngayHT > this.gv_Params.today) { return this.$toast.error('Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại!') }
      let phaiGiaoViec = this.phaiLam('GIAOVIEC')
      if (phaiGiaoViec) {
        if (this.nhanVien_grid.items.length <= 0) { return this.$toast.error('Hãy nhập nhân viên thực hiện!') }
      }

      let dsgiaoViecNV = []
      this.thueBao_grid.checked.forEach(item => {
        let thueBao = this.thueBao_grid.items.find(e => e.MA_TB === item)
        let kieuYC_Text = this.pyc_Params.kieuYC_Options.find(e => e.id === this.pyc_Params.kieuYC_Selected).text
        let giaoViecNV = {
          phieuId: thueBao.PHIEU_ID,
          nhanVienTHId: this.$auth.getNhanVienID(),
          nhiemVu: kieuYC_Text,
          nhienVuId: 7,
          ghiChu: kieuYC_Text,
          nhanVienGiaoId: this.gv_Params.nguoiGV_Selected,
          ngayGiao: moment(this.gv_Params.ngayGV).format('DD/MM/YYYY'),
          hdtbid: thueBao.HDTB_ID
        }
        dsgiaoViecNV.push(giaoViecNV)
      })
      let result = await this.updateGiaoViec(dsgiaoViecNV)
      if (result) { this.$toast.success('Cập nhật dữ liệu thành công') }

      await this.onGetTT()
    },

    async onDeleteHD () {
      if (this.onCheckDieuKienTB(1)) {
        try {
          await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn xóa hợp đồng?', {
            'title': 'Thông báo',
            'size': 'lg',
            'buttonSize': 'lg',
            'okVariant': 'primary',
            'okTitle': 'Đồng ý',
            'cancelTitle': 'Không đồng ý',
            'hideHeaderClose': false,
            'centered': true,
            'modal-class': ['f18', 'text-center']
          })
            .then(async (value) => {
              if (value) {
                let count = 0
                for (let item of this.thueBao_grid.checked) {
                  let thueBao = this.thueBao_grid.items.find(e => e.MA_TB === item)
                  let rs = await this.deleteHopDongKhachHang(thueBao.HDKH_ID)
                  if (!rs) { break } else count++
                }

                if (count === this.thueBao_grid.checked.length) { this.$toast.success('Xóa hợp đồng khách hàng thành công!') }
                await this.onHienThiDanhSach2()
              }
            })
            .catch(err => {
              // An error occurred
              console.log(err)
            })
        } catch (error) {
          this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
        }
      }
    },
    // kichHoat: async function (body) {
    //     try {
    //         const { data } = await this.axios.put(`/web-thuno/api/thu-no/xu-ly-thue-bao/kich-hoat-dich-vu/hop-dong-thue-bao/${body.HDTB_ID}`);
    //         if (data && data.error == "200") {
    //             return 1;
    //         } else {
    //             return 0;
    //         }
    //     } catch (error) {
    //         this.$toast.error(error.data.message_detail);
    //     }
    // },

    kichHoat: async function (body) {
      try {
        const res = await this.axios.post(`/web-thuno/api/thu-no/xu-ly-thue-bao/kich-hoat`, body)
        const data = await res.data
        // console.log(data);
        if (data && data.error == '200') {
        //   this.$toast.success('Kích hoạt dịch vụ thành công!')
          return true
        } else {
        //   this.loading(false)
          // return false
          if (data.message_detail !== undefined) return '- ' + body.ds[0].maTB + ': ' + data.message_detail
          else return '- ' + body.ds[0].maTB + ': Kích hoạt bị lỗi.'
        }
      } catch (error) {
        // this.$toast.error(error.data.message_detail)
        // this.loading(false)
        if (error.data.message_detail !== undefined) return '- ' + body.ds[0].maTB + ': ' + error.data.message_detail
        else return '- Kích hoạt bị lỗi.'
        // return false
      }
    },

    async grvIssues_FocusedRowChanged(e) {
      if (!e) return
      console.log('grvIssues_FocusedRowChanged', e)
      this.issue_id = e.key
      await this.NAP_ISSUE_INFO(this.issue_id)
    },

    async xTabControl_SelectedPageChanged(tabName) {
      switch (tabName) {
        case 'TabIT360':
          await this.NAP_DS_ISSUES(this.thueBao_grid.selected.HDTB_ID)
          break
      }
    },

    async LAYDS_TRANGTHAI_JIRA() {
      await TNAPI.lay_ds_trangthai_jira(this.axios, {
        donvi_id:  this.$root.token.getDonViID() * 1
      }).then((res) => {
        if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
          this.ds_trangthai_jira = res.data.data
            // .filter((x) => x.ttjr_id > 0)
            .map((x) => ({ id: x.trangthai_jira, text: x.trangthai_jira }))
        }
      })
    },

    async NAP_DS_ISSUES(hdtb_id) {
      var dataPost = {
        hdtb_id: hdtb_id
      }
      await TNAPI.lay_ds_issue_cua_hopdongtb(this.axios, dataPost).then((res) => {
        this.list_it360 = res.data.data.map((x) => {
          x.url = `<a target="_blank" href=${x.url}>${x.url}</a>`
          return x
        })
      })
    },

    async Get_Issue_Info(id) {
      var req = {
        issueKeyOrID: id
      }
      let data = null
      await TNAPI.Get_Issue_Info(this.axios, req).then((res) => {
        data = res.data.data
      })
      return data
    },

    async NAP_ISSUE_INFO(id) {
      var data = await this.Get_Issue_Info(id)
      if (!data) return
      try {
        if (data.errorMessages && data.errorMessages.length > 0) {
          this.$toast.error('Có lỗi lấy thông tin phiếu yêu cầu. ' + data.errorMessages[0])
          return
        }
        console.log(data);
        this.trangthai_jira_value = data.fields.status.name.toString()
        this.tieude_jira = data.key + ': ' + data.fields.summary
        // this.state.vkey_ht = data.key
        // this.state.vid_ht = data.id
        // this.state.vproject_id = data.fields.project.id
        // this.state.txtSummary = data.key + ': ' + data.fields.summary
        // this.state.txtDescription = data.fields.description
        // this.state.txtReporter = data.fields.reporter.name
        // this.state.txtIssuseType = data.fields.issuetype.name
        // if (data.fields.resolutiondate != null) {
        //   this.controls.dtpResolved.value = moment(data.fields.resolutiondate, 'YYYY-MM-DD').format('DD/MM/YYYY')
        // }
        // this.controls.dtpDueDate.value = moment(data.fields.duedate, 'YYYY-MM-DD').format('DD/MM/YYYY')
        // this.state.trangthai_iss = ''
        if (data.fields.status.name) {
            this.jira_resolution_name = data.fields.resolution ? data.fields.resolution.name.toString() : ''
        //   let resolution_id = data.fields.resolution ? data.fields.resolution.id.toString() : ''
        //   this.listbox.ds_trangthai_jira.value = data.fields.status.name.toString()
        //   this.listbox.resolution_name = resolution_name
        //   this.listbox.resolution_id = resolution_id
        //   this.state.trangthai_iss = data.fields.status.name.toString()
        }

      } catch (ex) {
        throw  ex;
      }
    },

    async onCreateIssue()
    {
      // try {
      //   if (this.thueBao_grid.checked.length == 0)
      //   {
      //     this.$toast.error("Bạn chưa chọn phiếu nào!")
      //   }
      //   let ds_hdtb = []
      //   this.thueBao_grid.checked.forEach(item => {
      //     let thueBao = this.thueBao_grid.items.find(e => e.MA_TB === item)
      //     let data = {
      //       hdtb_id: thueBao.HDTB_ID,
      //       phieu_id: thueBao.PHIEU_ID,
      //       loai: 1,
      //       kieu: 1,
      //       vip_giamsat: false
      //     }
      //     ds_hdtb.push(data)
      //
      //   })
      //   console.log('tsbtnCreatIssue_Click', ds_hdtb)
      //   this.$refs.dialogCreateIssue.openDialog(ds_hdtb)
      // }
      // catch (ex)
      // {
      //   console.log(ex)
      //   this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
      // }
      this.$bvModal
        .msgBoxConfirm(`Bạn muốn gửi IT360 CHỈ cho phiếu đang chọn hay gửi DANH SÁCH phiếu (yêu cầu tích chọn các phiếu ở bảng "danh sách thuê bao")`, {
          title: '',
          size: 'lg',
          okTitle: 'Gửi phiếu đang chọn',
          okVariant: 'success',
          cancelVariant: 'danger',
          cancelTitle: 'Gửi danh sách phiếu (hàng loạt)',
          centered: true
        })
        .then(async (v) => {
          if (!v) {
            this.GuiDSPhieuIT360()
            return
          }
          else
          {
            this.GuiMotPhieuIT360()
            return
          }
        })
    },
    async GuiMotPhieuIT360()
    {
      try {

        let thueBao = this.thueBao_grid.selected
        if(!thueBao)
        {
          this.$toast.error("Ban chua chon phieu nao!");
          return;
        }
        let data = {
          hdtb_id: thueBao.HDTB_ID,
          phieu_id: thueBao.PHIEU_ID,
          loai: 1,
          kieu: 1,
          vip_giamsat: false
        }

        this.$refs.dialogCreateIssue_v1.openDialog(data)
      }
      catch (ex)
      {
        console.log(ex)
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
      }
    },
    async GuiDSPhieuIT360()
    {
      try {
        if (this.thueBao_grid.checked.length == 0)
        {
          this.$toast.error("Bạn chưa chọn phiếu nào!")
        }
        let ds_hdtb = []
        this.thueBao_grid.checked.forEach(item => {
          let thueBao = this.thueBao_grid.items.find(e => e.MA_TB === item)
          let data = {
            hdtb_id: thueBao.HDTB_ID,
            phieu_id: thueBao.PHIEU_ID,
            loai: 1,
            kieu: 1,
            vip_giamsat: false
          }
          ds_hdtb.push(data)

        })
        console.log('tsbtnCreatIssue_Click', ds_hdtb)
        this.$refs.dialogCreateIssue.openDialog(ds_hdtb)
      }
      catch (ex)
      {
        console.log(ex)
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
      }
    },

    async onKichHoat () {
      if (this.onCheckDieuKienTB(2)) {
        try {
          await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn kích hoạt các thuê bao đã chọn hay không?', {
            'title': 'Thông báo',
            'size': 'lg',
            'buttonSize': 'lg',
            'okVariant': 'primary',
            'okTitle': 'Đồng ý',
            'cancelTitle': 'Không đồng ý',
            'hideHeaderClose': false,
            'centered': true,
            'modal-class': ['f18', 'text-center']
          })
            .then(async (value) => {
              if (value) {
                // let count = 0
                // let ds = []

                this.loading(true)
                let promises = []

                this.thueBao_grid.checked.forEach(item => {
                  let thueBao = this.thueBao_grid.items.find(e => e.MA_TB === item)

                  let body = {
                    luongId: this.common_Params.luong_Id,
                    kieuYC: this.pyc_Params.kieuYC_Selected,
                    dichVuVTId: this.pyc_Params.dichVu_Selected,
                    ds: [{
                      maTB: item,
                      thueBaoId: thueBao.THUEBAO_ID,
                      hdtbId: thueBao.HDTB_ID,
                      chieuKhoa: thueBao.CHIEUKHOA
                    }]
                  }
                  promises.push(this.kichHoat(body))
                })

                const res = await Promise.all(promises)

                // await Promise.all(this.thueBao_grid.checked.map(async item => {
                //   let thueBao = this.thueBao_grid.items.find(e => e.MA_TB === item)

                //   let body = {
                //     luongId: this.common_Params.luong_Id,
                //     kieuYC: this.pyc_Params.kieuYC_Selected,
                //     dichVuVTId: this.pyc_Params.dichVu_Selected,
                //     ds: [{
                //       maTB: thueBao.MA_TB,
                //       thueBaoId: thueBao.THUEBAO_ID,
                //       hdtbId: thueBao.HDTB_ID,
                //       chieuKhoa: thueBao.CHIEUKHOA
                //     }]
                //   }
                //   if (await this.kichHoat(body) == 1) {
                //     count++
                //     ds.push({
                //       maTB: item,
                //       thueBaoId: thueBao.THUEBAO_ID,
                //       hdtbId: thueBao.HDTB_ID,
                //       chieuKhoa: thueBao.CHIEUKHOA
                //     })
                //   }
                // }))

                // this.thueBao_grid.checked.forEach(async item => {
                //   let thueBao = this.thueBao_grid.items.find(e => e.MA_TB === item)

                //   let body = {
                //     luongId: this.common_Params.luong_Id,
                //     kieuYC: this.pyc_Params.kieuYC_Selected,
                //     dichVuVTId: this.pyc_Params.dichVu_Selected,
                //     ds: [{
                //       maTB: thueBao.MA_TB,
                //       thueBaoId: thueBao.THUEBAO_ID,
                //       hdtbId: thueBao.HDTB_ID,
                //       chieuKhoa: thueBao.CHIEUKHOA
                //     }]
                //   }
                //   if (await this.kichHoat(body) == 1) {
                //     ds.push({
                //       maTB: item,
                //       thueBaoId: thueBao.THUEBAO_ID,
                //       hdtbId: thueBao.HDTB_ID,
                //       chieuKhoa: thueBao.CHIEUKHOA
                //     })
                //   }
                // })

                // let rs = await this.updateKichHoat(ds);
                // if (!rs) return;

                // Cap nhat ngay hoan thien:
                // for (let item of this.thueBao_grid.checked) {
                //     let thueBao = this.thueBao_grid.items.find(e => e.MA_TB === item);
                //     let result = await this.updateNgayHoanThien(thueBao.HDTB_ID);
                //     if (!result)
                //         return;
                //     else count++;
                // }

                // if (count === this.thueBao_grid.checked.length)
                // this.$toast.success('Đã thực hiện kích hoạt dịch vụ thành công!');
                // if (this.thueBao_grid.checked.length == count) {
                //   this.$toast.success('Kích hoạt dịch vụ thành công!')
                // }

                let ok = res.filter(x => x === true).length

                if (ok === res.length) {
                  this.$toast.success('Kích hoạt dịch vụ thành công!')
                } else {
                  let failed = res.filter(x => x !== true)
                  this.$toast.error(`Đã phát sinh lỗi khi kích hoạt. Tổng số kích hoạt thành công/ tổng số (${ok} / ${res.length})`)
                  this.$toast.error(failed.join('\r\n'))
                }
                await this.onHienThiDanhSach2()
                this.loading(false)
              }
            })
            .catch(err => {
              // An error occurred
              console.log(err)
            })
        } catch (error) {
          this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
        }
      }
    },

    async onHoanCong () {
      try {
        if (this.onCheckDieuKienTB(4)) {
          // let rs = await this.kiemTraHoanCong(this.thueBao_grid.selected.HDTB_ID);
          // if (rs) {
          let danhSach = []
          this.thueBao_grid.checked.forEach(item => {
            let thueBao = this.thueBao_grid.items.find(e => e.MA_TB === item)
            let ds = {
              chieuKhoa: Number(thueBao.CHIEUKHOA),
              dichVuVTId: Number(thueBao.DICHVUVT_ID),
              giaoOMC: 2,
              hdkhId: Number(thueBao.HDKH_ID),
              hdtbId: Number(thueBao.HDTB_ID),
              kieuQuyTrinhId: 0,
              loaiTBId: Number(thueBao.LOAITB_ID),
              maTB: thueBao.MA_TB,
              phieuId: Number(thueBao.PHIEU_ID),
              status: Number(thueBao.STATUS),
              thueBaoId: Number(thueBao.THUEBAO_ID)
            }
            danhSach.push(ds)
          })

          let result = await this.updateHoanCong(danhSach)
          if (result) { this.$toast.success('Cập nhật dữ liệu và hoàn thiện hồ sơ thành công!') }

          await this.onHienThiDanhSach2()
          // }
        }
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra khi hoàn công!')
        console.log(error)
      }
    },

    async onVer2_HoanCong () {
      try {
        if (this.onCheckDieuKienTB(4)) {
          this.loading(true)
          let count = 0
          await Promise.all(this.thueBao_grid.checked.map(async item => {
            let thueBao = this.thueBao_grid.items.find(e => e.MA_TB === item)
            let ds = {
              chieuKhoa: Number(thueBao.CHIEUKHOA),
              dichVuVTId: Number(thueBao.DICHVUVT_ID),
              giaoOMC: 2,
              hdkhId: Number(thueBao.HDKH_ID),
              hdtbId: Number(thueBao.HDTB_ID),
              kieuQuyTrinhId: 0,
              loaiTBId: Number(thueBao.LOAITB_ID),
              maTB: thueBao.MA_TB,
              phieuId: Number(thueBao.PHIEU_ID),
              status: Number(thueBao.STATUS),
              thueBaoId: Number(thueBao.THUEBAO_ID)
            }
            if (thueBao.STATUS == 5 || thueBao.STATUS == 7) {
              count++
              await this.updateHoanCong([ds])
            }
            // if(!await this.updateHoanCong([ds])){
            //     count++
            // }
          }))

          if (count > 0) this.$toast.success('Cập nhật dữ liệu và hoàn thiện hồ sơ thành công!')

          await this.onHienThiDanhSach2()
          this.loading(false)
          // }
        }
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra khi hoàn công!')
        console.log(error)
      }
    },

    onHoanThanh () {

    },

    async onHoanThien () {
      console.log('Hoàn thiện');
      try {
        let thueBao = this.thueBao_grid.selected
        if(!thueBao)
        {
          this.$toast.error("Ban chua chon phieu nao!");
          return;
        }
        this.loading(true)
        let kyhoadon = moment(this.gv_Params.today).format('YYYYMM')
        let data = { }

        data = {
          ...data,
          vhdtb_id: thueBao.HDTB_ID,
          vphieu_id: thueBao.PHIEU_ID,
          vhdkh_id: thueBao.HDKH_ID,
          vkyhoadon: kyhoadon,
          vngaygv: moment(this.gv_Params.ngayGV).format('DD/MM/YYYY HH:mm:ss'),
          vngayht: moment(this.gv_Params.ngayHT).format('DD/MM/YYYY HH:mm:ss'),
          vngaykh: moment(this.gv_Params.ngayHT).format('DD/MM/YYYY HH:mm:ss'),
          vdonvi_giao_id: this.$auth.getDonViID(),
          vnhanvien_hc_id: this.$auth.getNhanVienID(),
          vnhanvien_gv_id: this.gv_Params.nguoiGV_Selected
        }
        if (data.vhdtb_id)
        {
          let kq_hc = await TNAPI.fn_an_hoanthienhoso_dichvu_cntt(this.axios, data)
          if (kq_hc.data.data && (kq_hc.data.data.indexOf('thành công') > 0 || kq_hc.data.data == 'ok')) {
            await this.onHienThiDanhSach2()
            this.$toast.success('Hoàn thiện hồ sơ thành công!')
          }
          else
          {
            throw Error(JSON.stringify(kq_hc));
          }
        }
      }
      catch (ex)
      {
        console.log(ex)
        console.log(ex.data.message)
        this.$toast.error(ex.data.message)
      }
      finally {
        this.loading(false)
      }
    },

    async onGiaoPhieu () {
      let rs = await this.getHuongGiaoTheoLuong(this.common_Params.luong_Id)
      if (rs.length > 0) {
        this.common_Params.huongGiaoId_GP = Number(rs[0].huonggiao_id)
        this.$refs.popupGiaoPhieu.showModal()
      } else { this.$toast.error('Chưa có hướng giao được gán!') }
      await this.onHienThiDanhSach2()
    },

    onGiaoViec () {
      if (this.thueBao_grid.items.length <= 0) { return this.$toast.error('Chưa có dữ liệu thuê bao để thực hiện!') }
      if (!this.gv_Params.ngayGV_Checked) { return this.$toast.error('Hãy nhập ngày giao việc!') }
      // frnGiaoPhieuNV
    },

    async onTraPhieu () {
      // frmTraPhieuTC
      await this.onHienThiDanhSach2()
    },

    onChangeLyDoHuy () {
      // Mở form frmThayDoiLyDoHuy
    },

    async onThueBaoChecked (e) {
      this.thueBao_grid.checked = e
    },

    onNhanVienTHSelected (e) {
      this.nhanVien_grid.selected = e
    },

    onLyDoSelected (e) {
      console.log(e)
    },

    async onThueBaoSelected (arg) {
      try {
        this.thueBao_grid.selected = arg.data
        let status = this.thueBao_grid.selected.STATUS != null && this.thueBao_grid.selected.STATUS.toString() !== '' ? Number(this.thueBao_grid.selected.STATUS) : -1

        // Display:
        this.pyc_Params.noiDungTH = this.thueBao_grid.selected.ND_THUCHIEN
        this.pyc_Params.soMayAcc = this.thueBao_grid.selected.MA_TB
        if (this.thueBao_grid.selected.NGAY_HT_GP !== '' && this.thueBao_grid.selected.NGAY_HT_GP !== null) {
          this.gv_Params.ngayHT_Checked = true
          this.gv_Params.ngayHT = moment(this.thueBao_grid.selected.NGAY_HT_GP, 'DD/MM/YYYY HH:mm:ss').toDate()
        } else {
          this.gv_Params.ngayHT_Checked = false
          this.gv_Params.ngayHT = this.gv_Params.today
        }
        this.display_Params.xoaHDBtn_Visible = this.common_Params.TTHD_Id === TrangThaiHD.MOI && this.thueBao_grid.items.length > 0

        // Load NhanVien table:
        await this.getDanhSachNhanVienTheoPhieuId(this.thueBao_grid.selected.PHIEU_ID)
        if (this.nhanVien_grid.items.length > 0) {
          this.gv_Params.ngayGV_Checked = true
          this.gv_Params.ngayGV = moment(this.nhanVien_grid.items[0].NGAYGIAO, 'DD/MM/YYYY HH:mm:ss').toDate()
          this.gv_Params.nguoiGV_Selected = this.nhanVien_grid.items[0].NHANVIEN_GIAO_ID
        } else {
          this.gv_Params.ngayGV_Checked = false
          this.gv_Params.nguoiGV_Selected = this.$auth.getNhanVienID()
          this.gv_Params.ngayGV = this.gv_Params.today
          this.gv_Params.ngayHT = this.gv_Params.today
        }
        // Load Ly do huy:
        await this.getDanhSachLyDoHuy(this.thueBao_grid.selected.HDTB_ID)

        // Nếu phải kích hoạt tổng đài:
        if (this.phaiLam('KICHHOAT_TD')) {
          if (this.nhanVien_grid.items.length > 0) {
            if (this.thueBao_grid.selected.NGAY_HT_GP !== '' && this.thueBao_grid.selected.NGAY_HT_GP !== null) {
              this.gv_Params.ngayHT_Checked = true
              this.gv_Params.ngayHT = moment(this.thueBao_grid.selected.NGAY_HT_GP, 'DD/MM/YYYY HH:mm:ss').toDate()
              let trangThai_DB = Number(this.pyc_Params.dichVu_Selected) === DichVuVienThong.CO_DINH ? TRANGTHAI_DONGBO.DONGBO_SERVICE : TRANGTHAI_DONGBO.DONGBO_CM
              if (status === trangThai_DB) {
                if (this.phaiLam('GIAOPHIEU')) {
                  this.display_Params.giaoPhieuBtn_Enable = true
                  this.display_Params.kichHoatBtn_Enable = false
                } else {
                  this.display_Params.hoanCongBtn_Enable = true
                  this.display_Params.kichHoatBtn_Enable = false
                }

                if (this.phaiLam('CHECK_OMC')) { // Nếu có thao tác kiểm tra OMC thì -> làm
                  let kq = this.thueBao_grid.selected.GIAO_OMC !== '' && this.thueBao_grid.selected.GIAO_OMC !== null ? Number(this.thueBao_grid.selected.GIAO_OMC) : -1
                  if (kq === -1) { return this.$toast.error('Không lấy được thông tin kiểm tra giao phiếu cho bộ phận tiếp theo') } else if (kq === 2) {
                    this.display_Params.hoanCongBtn_Enable = true
                    this.display_Params.kichHoatBtn_Enable = false
                    this.display_Params.giaoPhieuBtn_Enable = false
                    this.display_Params.giaoPhieuBtn_Visible = false
                  } else if (kq === 1) {
                    // Giaophieu
                    this.display_Params.hoanCongBtn_Enable = false
                    this.display_Params.kichHoatBtn_Enable = false
                    this.display_Params.giaoPhieuBtn_Enable = true
                    this.display_Params.giaoPhieuBtn_Visible = true
                  } else return
                }
              } else {
                if (this.phaiLam('CHECK_OMC')) { // Nếu có thao tác kiểm tra OMC thì -> làm
                  let kq = this.thueBao_grid.selected.GIAO_OMC !== '' && this.thueBao_grid.selected.GIAO_OMC !== null ? Number(this.thueBao_grid.selected.GIAO_OMC) : -1
                  if (kq === -1) { return this.$toast.error('Không lấy được thông tin kiểm tra giao phiếu cho bộ phận tiếp theo') } else if (kq === 2) { // Gpon
                    this.display_Params.hoanCongBtn_Enable = false
                    this.display_Params.kichHoatBtn_Enable = true
                    this.display_Params.giaoPhieuBtn_Enable = false
                    this.display_Params.giaoPhieuBtn_Visible = false
                  } else if (kq === 1) { // Aon
                    // Giaophieu
                    this.display_Params.hoanCongBtn_Enable = false
                    this.display_Params.kichHoatBtn_Enable = false
                    this.display_Params.giaoPhieuBtn_Enable = true
                    this.display_Params.giaoPhieuBtn_Visible = true
                  } else return
                } else {
                  this.display_Params.hoanCongBtn_Enable = false
                  this.display_Params.kichHoatBtn_Enable = true
                  this.display_Params.giaoPhieuBtn_Enable = false
                }
              }
            } else {
              this.gv_Params.ngayHT_Checked = false
              this.gv_Params.ngayHT = this.gv_Params.today
              this.display_Params.giaoPhieuBtn_Enable = false
              this.display_Params.hoanCongBtn_Enable = false
              this.display_Params.kichHoatBtn_Enable = false
            }
          } else {
            this.display_Params.giaoPhieuBtn_Enable = false
            this.display_Params.hoanCongBtn_Enable = false
            this.display_Params.kichHoatBtn_Enable = false
          }
        } else { // Nếu không phải kích hoạt
          // Nếu phải giao phiếu
          if (this.phaiLam('GIAOPHIEU')) {
            if (this.nhanVien_grid.items.length > 0) {
              if (this.thueBao_grid.selected.NGAY_HT_GP !== '' && this.thueBao_grid.selected.NGAY_HT_GP !== null) {
                this.gv_Params.ngayHT_Checked = true
                this.gv_Params.ngayHT = moment(this.thueBao_grid.selected.NGAY_HT_GP, 'DD/MM/YYYY HH:mm:ss').toDate()
                this.display_Params.giaoPhieuBtn_Enable = true
                this.display_Params.hoanCongBtn_Enable = false
                this.display_Params.kichHoatBtn_Enable = false
              } else {
                this.gv_Params.ngayHT_Checked = false
                this.gv_Params.ngayHT = this.gv_Params.today
                this.display_Params.giaoPhieuBtn_Enable = false
                this.display_Params.hoanCongBtn_Enable = false
                this.display_Params.kichHoatBtn_Enable = false
              }
            } else {
              this.display_Params.giaoPhieuBtn_Enable = false
              this.display_Params.hoanCongBtn_Enable = false
              this.display_Params.kichHoatBtn_Enable = false
            }
          } else { // Nếu không phải giao phiếu
            // Nếu phải kích hoạt tổng đài
            if (this.phaiLam('HOANCONG')) {
              if (this.nhanVien_grid.items.length > 0) {
                if (this.thueBao_grid.selected.NGAY_HT_GP !== '' && this.thueBao_grid.selected.NGAY_HT_GP !== null) {
                  this.gv_Params.ngayHT_Checked = true
                  this.gv_Params.ngayHT = moment(this.thueBao_grid.selected.NGAY_HT_GP, 'DD/MM/YYYY HH:mm:ss').toDate()
                  this.display_Params.hoanCongBtn_Enable = true
                } else {
                  this.gv_Params.ngayHT_Checked = false
                  this.gv_Params.ngayHT = this.gv_Params.today
                  this.display_Params.hoanCongBtn_Enable = false
                }
              } else {
                this.display_Params.giaoPhieuBtn_Enable = false
                this.display_Params.hoanCongBtn_Enable = false
                this.display_Params.kichHoatBtn_Enable = false
              }
            }
          }
        }
        if (this.phaiLam('HOANTHIENHOSO')) {
          if (this.nhanVien_grid.items.length > 0) {
            if (this.thueBao_grid.selected.NGAY_HT_GP !== '' && this.thueBao_grid.selected.NGAY_HT_GP !== null) {
              this.gv_Params.ngayHT_Checked = true
              this.gv_Params.ngayHT = moment(this.thueBao_grid.selected.NGAY_HT_GP, 'DD/MM/YYYY HH:mm:ss').toDate()
              this.display_Params.hoanThienBtn_Enable = true
            } else {
              this.gv_Params.ngayHT_Checked = false
              this.gv_Params.ngayHT = this.gv_Params.today
              this.display_Params.hoanThienBtn_Enable = false
            }
          } else {
            this.display_Params.giaoPhieuBtn_Enable = false
            this.display_Params.hoanCongBtn_Enable = false
            this.display_Params.kichHoatBtn_Enable = false
          }
        }
      } catch (err) {
        console.log(err)
      }

      if(this.phaiLam( 'CREAT_ISSUE')) {
      //  this.LAYDS_TRANGTHAI_JIRA()
        this.xTabControl_SelectedPageChanged('TabIT360')
      }
    },

    async onHienThiGiaoDien (luong_Id) {
      this.display_Params.hoanCongBtn_Visible = false
      this.display_Params.hoanThienBtn_Visible = false
      this.display_Params.giaoPhieuBtn_Visible = false
      this.display_Params.hoanThanhBtn_Visible = false
      this.display_Params.giaoViecBtn_Visible = false
      this.display_Params.kichHoatBtn_Visible = false
      this.display_Params.kichHoatBtn_Enable = false
      this.display_Params.kichhoatCnttBtn_Visible = false
      this.display_Params.tsbtnCreatIssue_Enable = false
      this.display_Params.tsbtnCreatIssue_Visible = false

      await this.getDSThaoTacTheoLuong(luong_Id)
      if (this.thaoTac.items.length > 0) {
        await this.getDSThaoTacControl()
        if (this.thaoTac.controls.length > 0) {
          this.thaoTac.items.forEach(item => {
            let controlArr = this.thaoTac.controls.filter(e => e.THAOTAC_ID === item.THAOTAC_ID)
            if (controlArr.length > 0) {
              console.log(controlArr)
              controlArr.forEach(elemt => {
                let idx = this.common_Params.controls.findIndex(e => e === elemt.CONTROL_NAME)
                if (idx !== -1) {
                  let initEnable = item.ENABLE === 1
                  this.displayControl(idx, initEnable)
                }
              })
            }
          })
        }
      }
    },

    displayControl (id, value) {
      switch (id) {
        case 0:
          this.display_Params.kichHoatBtn_Visible = value
          break
        case 1:
          this.display_Params.giaoPhieuBtn_Visible = value
          break
        case 2:
          this.display_Params.hoanCongBtn_Visible = value
          break
        case 3:
          this.display_Params.hoanThanhBtn_Visible = value
          break
        case 4:
          this.display_Params.giaoViecBtn_Visible = value
          break
        case 5:
          this.display_Params.hoanThienBtn_Visible = value
          break
        case 6:
          this.display_Params.traPhieuBtn_Visible = value
          break
        case 7:
          this.display_Params.kichhoatCnttBtn_Visible = value
          this.display_Params.kichhoatCnttBtn_Enable = value
          break
        case 8:
          this.display_Params.tsbtnCreatIssue_Visible = value
          this.display_Params.tsbtnCreatIssue_Enable = value
          break
        default: break
      }
    },

    async onHienThiDanhSach () {
      if (this.common_Params.xacMinh_TheoYeuCau === 1) { await this.getDSHDTBThanhLyHoanCong() } else { await this.getDanhSachHopDongTBKhoaMoMayHC('') }
    },

    async onHienThiDanhSach2 () {
      await this.getDanhSachHopDongTBKhoaMoMayHC('')
      if (this.thueBao_grid.items.length > 0) {
        this.display_Params.xuatFileBtn_Enable = true
      } else {
        this.pyc_Params.soMayAcc = ''
        this.gv_Params.ngayGV_Checked = false
        this.gv_Params.ngayHT_Checked = false
        this.gv_Params.ngayHT = this.gv_Params.today
        this.gv_Params.ngayGV = this.gv_Params.today
        this.nhanVien_grid.items = []
        this.display_Params.xuatFileBtn_Enable = false
        this.display_Params.xoaHDBtn_Visible = false
        this.display_Params.traPhieuBtn_Visible = false
      }
    },

    async onDeleteLyDo (item) {
      if (item.KIEU === 1) { this.$toast.error('Bạn không được phép xóa lý do hủy của TTKD nhập!') } else {
        try {
          await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn xóa lý do hủy?', {
            'title': 'Thông báo',
            'size': 'lg',
            'buttonSize': 'lg',
            'okVariant': 'primary',
            'okTitle': 'Đồng ý',
            'cancelTitle': 'Không đồng ý',
            'hideHeaderClose': false,
            'centered': true,
            'modal-class': ['f18', 'text-center']
          })
            .then(async (value) => {
              if (value) {
                let result = await this.deleteLyDoHuy(this.thueBao_grid.selected.HDTB_ID, item.LYDOHUY_ID)
                if (result) { this.$toast.success('Xóa lý do hủy thành công!') }

                await this.getDanhSachLyDoHuy(this.thueBao_grid.selected.HDTB_ID)
              }
            })
            .catch(err => {
              // An error occurred
              console.log(err)
            })
        } catch (error) {
          this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
        }
      }
    },

    async onXuatFile () {
      if (this.thueBao_grid.items.length <= 0) { this.$toast.error('Chưa có dữ liệu để thực hiện xuất file!') } else { this.$bvModal.show('modal-xuat-file') }
    },

    onXuatText () {
      let fileName_Suffix = moment(this.gv_Params.today).format('DDMMYYY_hhmm') + '.txt'
      switch (Number(this.pyc_Params.dichVu_Selected)) {
        case DichVuVienThong.CO_DINH:
          this.XuatFileCoDinh('CoDinh_' + fileName_Suffix, -1, -1)
          break
        case DichVuVienThong.GPHONE:
          this.XuatFileGPhone('GPhone_' + fileName_Suffix)
          break
        case DichVuVienThong.ADSL:
          if (Number(this.pyc_Params.loaiHinh_Selected) === 61) { this.XuatFileMyTV('MyTV_' + fileName_Suffix.replace('txt', 'xlsx')) } else this.XuatFileInternet('Internet_' + fileName_Suffix)
          break
        case DichVuVienThong.DI_DONG:
          this.XuatFileDiDong('DiDong_' + fileName_Suffix)
          break
        default: break
      }

      if (this.pyc_Params.kieuYC_Selected === 1) { this.$toast.success('Xuất danh sách khóa máy thành công') } else this.$toast.success('Xuất danh sách mở máy thành công')
    },

    XuatFileCoDinh (fileName, khoaMo, dvgt) {
      let content = `<CMDFILE;\r\n`
      content += `<SETCFOPT:ACKCFS=ERROR,DANCMD=EXEC,ACKREQ=POS;<<\r\n`
      let suffix = ''
      if (dvgt === 232) { suffix += khoaMo === 1 ? ',BLK=ACCSPORI;<<' : ',CBLK=ACCSPORI;<<' } else if (dvgt === 38) { suffix += khoaMo === 1 ? ',BLK=ADMIN1;<<' : ',CBLK=ADMIN1;<<' }

      this.thueBao_grid.items.forEach(item => {
        content += `<MODSUB:DN=${item.MA_TB}${suffix}\r\n`
      })
      content += `<RESETCFOPT;<<\r\n`
      content += `<ENDFILE;<<`
      var FileSaver = require('file-saver')
      var file = new File([content], fileName, {
        type: 'text/plain;charset=utf-8'
      })
      FileSaver.saveAs(file)
    },

    XuatFileGPhone (fileName) {
      let content = ''
      this.thueBao_grid.items.forEach(item => {
        content += `${item.MA_TB}\r\n`
      })
      var FileSaver = require('file-saver')
      var file = new File([content], fileName, {
        type: 'text/plain;charset=utf-8'
      })
      FileSaver.saveAs(file)
    },

    XuatFileMyTV (fileName) {
      let arr = []
      let suffix = this.pyc_Params.kieuYC_Selected === 1 ? 'Tạm ngưng nợ cước' : 'Khôi phục nợ cước'
      this.loading(true)
      this.thueBao_grid.items.forEach(item => {
        arr.push({
          MA_TB: item.MA_TB,
          LYDO: suffix
        })
      })
      try {
        var dsach = xlsx.utils.json_to_sheet(arr)
        var wb = xlsx.utils.book_new()
        xlsx.utils.book_append_sheet(wb, dsach, 'MyTV')
        xlsx.writeFile(wb, fileName)
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },

    XuatFileInternet (fileName) {
      let content = ''
      let suffix = this.pyc_Params.kieuYC_Selected === 1 ? 'Nợ đọng' : 'Mở máy nợ đọng'
      this.thueBao_grid.items.forEach(item => {
        content += `${item.MA_TB}:${suffix}\r\n`
      })
      var FileSaver = require('file-saver')
      var file = new File([content], fileName, {
        type: 'text/plain;charset=utf-8'
      })
      FileSaver.saveAs(file)
    },

    XuatFileDiDong (fileName) {
      let content = ''
      this.thueBao_grid.items.forEach(item => {
        content += `84${item.MA_TB},`
      })
      content = content.slice(0, -1)
      var FileSaver = require('file-saver')
      var file = new File([content], fileName, {
        type: 'text/plain;charset=utf-8'
      })
      FileSaver.saveAs(file)
    },

    /* type: 1 - Xóa HĐ; 2 - Kích hoạt; 4: Hoàn công; 5 - Hoàn thiện HS */
    onCheckDieuKienTB (type) {
      if (this.thueBao_grid.items.length <= 0) {
        this.$toast.error('Chưa có dữ liệu thuê bao để thực hiện!')
        return false
      } else if (this.thueBao_grid.checked.length <= 0) {
        this.$toast.error('Chưa chọn thuê bao để thực hiện!')
        return false
      } else {
        this.thueBao_grid.checked.forEach(item => {
          let thueBao = this.thueBao_grid.items.find(e => e.MA_TB === item)
          if (type === 1) { // Xóa HĐ
            if (thueBao.TTHD_ID.toString() !== '1') {
              this.$toast.error(`Trạng thái hợp đồng của thuê bao ${thueBao.MA_TB} không thỏa mãn điều kiện`)
              return false
            }
            if (thueBao.STATUS != null && thueBao.STATUS.toString() !== '' && thueBao.STATUS.toString() !== '0') {
              this.$toast.error(`Thuê bao ${thueBao.MA_TB} đã được kích hoạt. Bạn không được xóa!`)
              return false
            }
            if (thueBao.NGUOI_CN !== this.$auth.getUserName()) {
              this.$toast.error(`Bạn không được phép xóa ${thueBao.MA_TB} do user ${thueBao.NGUOI_CN} cập nhật!`)
              return false
            }
          } else if (type === 2) { // Kích hoạt
            if (thueBao.NGAY_HT_GP === '') {
              this.$toast.error(`Thuê bao ${thueBao.MA_TB} chưa cập nhật thông tin. Bạn không thể kích hoạt thuê bao này!`)
              return false
            }
            if (thueBao.STATUS != null && (thueBao.STATUS.toString() === '5' || thueBao.STATUS.toString() === '7')) {
              this.$toast.error(`Thuê bao ${thueBao.MA_TB} đã được kích hoạt!`)
              return false
            }
            if (thueBao.TTHD_ID.toString() === '6' || thueBao.TTHD_ID.toString() === '7') {
              this.$toast.error(`Hợp đồng đã hoàn thiện. Hãy lấy lại thông tin trước khi kích hoạt!`)
              return false
            }
            if (this.nhanVien_grid.items.length <= 0) {
              this.$toast.error('Bạn chưa cập nhật thông tin giao việc cho nhân viên!')
              return false
            }
          } else if (type === 4) { // Hoàn công
            if (thueBao.NGAY_HT_GP === '') {
              this.$toast.error(`Thuê bao ${thueBao.MA_TB} chưa cập nhật thông tin. Bạn không thể hoàn công thuê bao này!`)
              return false
            }
            if (thueBao.STATUS != null && thueBao.STATUS.toString() !== TRANGTHAI_DONGBO.DONGBO_CM.toString() &&
                            (thueBao.DICHVUVT_ID.toString() === DichVuVienThong.ADSL.toString() ||
                                thueBao.DICHVUVT_ID.toString() === DichVuVienThong.IMS.toString()) &&
                            thueBao.NGAY_HT_GP === '') {
              this.$toast.error(`Thuê bao ${thueBao.MA_TB} chưa cập nhật thông tin. Bạn không thể hoàn công thuê bao này!`)
              return false
            }
            if (this.nhanVien_grid.items.length <= 0) {
              this.$toast.error('Bạn chưa cập nhật thông tin giao việc cho nhân viên!')
              return false
            }
          } else if (type === 5) { // Hoàn thiện HS
            if (thueBao.NGAY_HT_GP === '') {
              this.$toast.error(`Thuê bao ${thueBao.MA_TB} chưa cập nhật thông tin. Bạn không thể hoàn thiện hồ sơ thuê bao này!`)
              return false
            }
          }
        })
      }
      return true
    },

    phaiLam (code) {
      let dr = this.thaoTac.items.filter(e => e.ENABLE === 1 && e.CODE === code)
      return dr.length > 0
    },

    compare (a, b) {
      if (a.text < b.text) {
        return -1
      }
      if (a.text > b.text) {
        return 1
      }
      return 0
    },

    async getThamSoMD () {
      try {
        this.loading(true)
        let response = await KMAPI.getAllThamSoMD(this.axios)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
          let tsData = response.data.data.find(e => e.MA_TS === 'NGAY_THUHOI')
          if (tsData !== undefined) {
            if (tsData.TEN_TS !== '') {
              this.common_Params.giaoTheoQD_Title = `Giao theo QĐ (${tsData.TEN_TS} ngày)`
              this.common_Params.KT_ThamSo_ThuHoi = true
            }
          } else {
            this.pyc_Params.thuHoiTB = 0
            this.pyc_Params.thuHoiTB_Enable = false
          }
          tsData = response.data.data.find(e => e.MA_TS === 'LHD_NHANTIN_DIABAN')
          if (tsData !== undefined) {
            if (tsData.TEN_TS.trim() === '1') { this.common_Params.nhanTin_NhanVien_DiaBan = 1 }
          }

          tsData = response.data.data.find(e => e.MA_TS === 'TS_KIEMTRA_TTHD_KICHHOAT')
          if (tsData !== undefined) {
            if (tsData.TEN_TS.trim() === '1') { this.common_Params.KT_TTHD_KichHoat = true }
          }
        } else {
          this.pyc_Params.thuHoiTB = 0
          this.pyc_Params.thuHoiTB_Enable = false
        }
      } catch (error) {
        this.pyc_Params.thuHoiTB = 0
        this.pyc_Params.thuHoiTB_Enable = false
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getThamSoMDMATS () {
      try {
        this.loading(true)
        let apiParams = {
          pMaThamSo: 'LAY_QUYTRINH_PROC'
        }
        let response = await MMAPI.getDSThamSoMDMATS(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          let tsData = response.data.data.find(e => e.MA_TS === 'LAY_QUYTRINH_PROC')
          if (tsData !== undefined) { this.common_Params.xacMinh_TheoYeuCau = Number(tsData.MACDINH) }
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDanhSachNguoiGiaoViec () {
      try {
        this.loading(true)
        this.gv_Params.nguoiGV_Options = []
        let donViId = this.$auth.getDonViID() !== -1 ? this.$auth.getDonViID() : 26
        let response = await CommonsAPI.getDanhSachNguoiGiaoViec(this.axios, donViId)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          if (response.data.data.length > 0) {
            response.data.data.forEach(item => {
              this.gv_Params.nguoiGV_Options.push({ id: item.NHANVIEN_ID, text: item.TEN_NV })
            })
            this.gv_Params.nguoiGV_Options.sort(this.compare)
            // this.gv_Params.nguoiGV_Selected = this.gv_Params.nguoiGV_Options[0].id;
          }
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDanhSachQuyTrinh () {
      try {
        this.loading(true)
        this.pyc_Params.quyTrinh_Options = []
        this.pyc_Params.loaiHinh_Options = []
        let apiParams = {
          dichVuVTId: this.pyc_Params.dichVu_Selected,
          kieuYC: this.common_Params.xacMinh_TheoYeuCau === 1 ? null : 1,
          loaiHDId: Number(this.common_Params.dsLoaiHD_Id[0]),
          TTHDId: this.common_Params.TTHD_Id
        }
        let response = await TNAPI.getDanhSachQuyTrinh(this.axios, apiParams)
        console.log("quytrinh", response)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          if (response.data.data.length > 0) {
            response.data.data.forEach(item => {
              if(apiParams.dichVuVTId == 13 || apiParams.dichVuVTId == 14 || apiParams.dichVuVTId == 15 || apiParams.dichVuVTId == 16)
              {
                if(item.QUYTRINH.includes('Khóa mở dịch vụ'))
                {
                  this.pyc_Params.quyTrinh_Options.push({ id: item.QUYTRINH_ID, text: item.QUYTRINH })
                }
              }
              else
              {
                this.pyc_Params.quyTrinh_Options.push({ id: item.QUYTRINH_ID, text: item.QUYTRINH })
              }
            })
            this.pyc_Params.quyTrinh_Options.sort(this.compare)
            if (this.$route.params && this.$route.params.quytrinhId) {
              this.pyc_Params.quyTrinh_Selected = this.$route.params.quytrinhId
            } else {
              this.pyc_Params.quyTrinh_Selected = this.pyc_Params.quyTrinh_Options[0].id
            }
          }
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDanhSachLoaiHinhTBTheoQuyTrinh (id) {
      try {
        this.loading(true)
        this.pyc_Params.loaiHinh_Options = []
        let response = await CommonsAPI.getDanhSachLoaiHinhTBTheoQuyTrinh(this.axios, id)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          if (response.data.data.length > 0) {
            response.data.data.forEach(item => {
              this.pyc_Params.loaiHinh_Options.push({ id: item.LOAITB_ID, text: item.LOAIHINH_TB })
            })
            this.pyc_Params.loaiHinh_Options.sort(this.compare)
            this.pyc_Params.loaiHinh_Selected = this.pyc_Params.loaiHinh_Options[0].id
          }
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getHuongGiaoTheoQuyTrinh () {
      try {
        this.loading(true)
        this.common_Params.huongGiao_Id = 0
        this.common_Params.luong_Id = 0
        this.common_Params.huongGiao = ''
        let apiParams = {
          quyTrinhId: this.pyc_Params.quyTrinh_Selected,
          tthdId: this.common_Params.TTHD_Id
        }
        let response = await TNAPI.getHuongGiaoTheoQuyTrinh(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.common_Params.huongGiao_Id = response.data.data[0].HUONGGIAO_ID
          this.common_Params.luong_Id = response.data.data[0].LUONG_ID
          this.common_Params.huongGiao = response.data.data[0].HUONGGIAO
          return response.data.data.length
        } else return 0
      } catch (error) {
        console.log(error)
        return 0
      } finally {
        this.loading(false)
      }
    },

    async getDanhSachNhanVienTheoPhieuId (phieuId) {
      try {
        this.loading(true)
        this.nhanVien_grid.items = []
        let apiParams = {
          phieuId: phieuId,
          id: this.$auth.getNhanVienID(),
          kieuId: 2,
          size: 100000,
          page: 0
        }
        let response = await TNAPI.getDanhSachNhanVienTheoPhieuId(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.nhanVien_grid.items = response.data.data.data
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDanhSachLyDoHuy (hdtbId) {
      try {
        this.loading(true)
        this.lyDo_grid.items = []
        let response = await TNAPI.getDanhSachLyDoHuy(this.axios, hdtbId)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.lyDo_grid.items = response.data.data
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDanhSachHopDongTBKhoaMoMayHC (maGD) {
      try {
        this.loading(true)
        this.thueBao_grid.items = []
        this.thueBao_grid.checked = []
        this.thueBao_grid.selected = {}
        this.checkAll = false
        let apiParams = {
          dichVuVTId: this.pyc_Params.dichVu_Selected,
          huongGiao: this.common_Params.huongGiao_Id,
          kieuYC: this.pyc_Params.kieuYC_Selected,
          loaiId: this.pyc_Params.loaiPhieu,
          loaiTBId: this.pyc_Params.loaiHinh_Checked ? this.pyc_Params.loaiHinh_Selected : null,
          maGD: maGD,
          nhanVienId: this.$auth.getNhanVienID(),
          TTHDId: this.common_Params.TTHD_Id,
          size: 100000,
          page: 0
        }
        let response = await TNAPI.getDanhSachHopDongTBKhoaMoMayHC(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.thueBao_grid.items = response.data.data.data
        }
        if (this.thueBao_grid.items.length > 0) this.checkAll = true
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDSHDTBThanhLyHoanCong () {
      try {
        this.loading(true)
        this.thueBao_grid.items = []
        this.thueBao_grid.checked = []
        this.thueBao_grid.selected = {}
        this.checkAll = false
        let apiParams = {
          maGD: '0',
          dichVuVTId: this.pyc_Params.dichVu_Selected,
          loaiTBId: this.pyc_Params.loaiHinh_Checked ? this.pyc_Params.loaiHinh_Selected : null,
          huongGiaoId: this.common_Params.huongGiao_Id,
          kieuYC: this.pyc_Params.kieuYC_Selected,
          loaiId: this.pyc_Params.loaiPhieu,
          nhanVienId: this.$auth.getNhanVienID(),
          tthdId: this.common_Params.TTHD_Id
        }
        let response = await TNAPI.getDSHDTBThanhLyHoanCong(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data !== undefined) {
          this.thueBao_grid.items = response.data.data
        }
        if (this.thueBao_grid.items.length > 0) this.checkAll = true
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    /* Return: true/false */
    async updateGiaoViec (dsgiaoViecNV) {
      try {
        this.loading(true)
        let apiBody = {
          dsGiaoViecNV: dsgiaoViecNV,
          quyTrinhId: this.pyc_Params.quyTrinh_Selected,
          tthdid: this.common_Params.TTHD_Id
        }
        let response = await TNAPI.updateGiaoViec(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000') { return true } else { return false }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    },

    /* Return: true/false */
    async deleteHopDongKhachHang (hopDongKHId) {
      try {
        this.loading(true)
        let apiParams = {
          hopDongKHId: hopDongKHId,
          loaiHdId: LoaiHopDong.THAY_DOI_DV
        }
        let response = await TNAPI.deleteHopDongKhachHang(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000') { return true } else { return false }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    },

    /* Return: true/false */
    async updateHoanCong (ds) {
      try {
        this.loading(true)
        let apiBody = {
          boQuaLoiHoanCong: this.pyc_Params.boQuaLoiKhiHoanCong,
          donViId: this.$auth.getDonViID(),
          ds: ds,
          hdtbId: this.thueBao_grid.selected.HDTB_ID,
          huongGiao: this.common_Params.huongGiao_Id,
          kieuYC: this.pyc_Params.kieuYC_Selected,
          luongId: this.common_Params.luong_Id,
          maNguoiDung: this.$auth.getMaNhanVien(),
          ngayGV: moment(this.gv_Params.ngayGV).format('DD/MM/YYYY HH:MM'),
          ngayHT: moment(this.gv_Params.ngayHT).format('DD/MM/YYYY HH:MM'),
          nhanVienId: this.$auth.getNhanVienID(),
          thuHoiThietBi: true
        }
        let response = await TNAPI.updateHoanCong(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000') {
          if (response.data.data.length > 0) {
            let count = 0
            response.data.data.forEach(e => {
              if (!e.status) {
                this.$toast.error(`Thuê bao ${e.hoanCongDTO.maTB}: ${e.result}`)
                count++
              }
            })
            return count === 0
          }
          return false
        } else { return false }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        // this.loading(false)
      }
    },

    async updateNgayHoanThien (hdtbId) {
      try {
        this.loading(true)
        let apiBody = {
          pHDTBId: hdtbId,
          pNgayKH: moment(this.gv_Params.today).format('DD/MM/YYYY'),
          pNgayHT: moment(this.gv_Params.today).format('DD/MM/YYYY'),
          pVIPKH: '10.0.0.1',
          pNguoiKH: this.$auth.getMaNhanVien(),
          pStatus: Number(this.pyc_Params.dichVu_Selected) === DichVuVienThong.CO_DINH ? TRANGTHAI_DONGBO.DONGBO_SERVICE : TRANGTHAI_DONGBO.DONGBO_CM

        }
        let response = await TNAPI.updateNgayHoanThien(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000') { return true } else { return false }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    },

    async updateKichHoat (ds) {
      try {
        this.loading(true)
        let apiBody = {
          luongId: this.common_Params.luong_Id,
          kieuYC: this.pyc_Params.kieuYC_Selected,
          dichVuVTId: this.pyc_Params.dichVu_Selected,
          ds: ds
        }
        let response = await TNAPI.updateKichHoat(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000') { return true } else { return false }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    },

    /* Return: true/false */
    async updateNoiDungTH (phieuId, noiDung) {
      try {
        this.loading(true)
        let apiBody = {
          phieuId: phieuId,
          noiDung: noiDung
        }
        let response = await TNAPI.updateNoiDungTH(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000') { return true } else { return false }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    },

    /* Return: true/false */
    async kiemTraHoanCong (hdtbId) {
      try {
        this.loading(true)
        let response = await TNAPI.updateKiemTraHoanCong(this.axios, hdtbId)
        if (response.data.error_code === 'BSS-00000000') { return true } else { return false }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    },

    /* Return: true/false */
    async deleteLyDoHuy (hdtbId, ldhId) {
      try {
        this.loading(true)
        let apiParams = {
          hdtbId: hdtbId,
          lyDoHuyId: ldhId
        }
        let response = await TNAPI.deleteLyDoHuy(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000') { return true } else { return false }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    },

    async getDSThaoTacTheoLuong (luongId) {
      try {
        this.loading(true)
        this.thaoTac.items = []
        let response = await CommonsAPI.getDSThaoTacTheoLuong(this.axios, luongId)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
          this.thaoTac.items = response.data.data
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDSThaoTacControl () {
      try {
        this.loading(true)
        this.thaoTac.controls = []
        let response = await TNAPI.getDSThaoTacControl(this.axios)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
          this.thaoTac.controls = response.data.data
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getHuongGiaoTheoLuong (luongId) {
      try {
        this.loading(true)
        this.common_Params.huongGiaoId_GP = 0
        let apiBody = {
          luong_id: luongId
        }
        let response = await TNAPI.getHuongGiaoTheoLuong(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
          return response.data.data
        } else return []
      } catch (error) {
        console.log(error)
        return []
      } finally {
        this.loading(false)
      }
    },

    changeDisplay (dichVuId) {
      // Render table header:
      this.thueBao_grid.fields = this.thueBao_grid.commonFields.slice() // shallow clone
      switch (Number(dichVuId)) {
        case DichVuVienThong.CO_DINH:
          this.header.title = 'TIẾP NHẬN KHÓA MỞ DỊCH VỤ CỐ ĐỊNH'
          this.display_Params.kichHoatBtn_Tooltip = 'Kích hoạt tổng đài'
          this.display_Params.kichHoatBtn_Visible = true
          this.display_Params.xoaHDBtn_Visible = true
          this.display_Params.hoanCongBtn_Visible = true
          this.display_Params.giaoPhieuBtn_Visible = false
          this.thueBao_grid.fields.splice(6, 0, {
            fieldName: 'TEN_STATUS',
            headerText: 'Kích hoạt tổng đài',
            allowFiltering: true,
            allowSorting: false,
            width: 180
          })
          break
        case DichVuVienThong.ADSL:
          this.header.title = 'TIẾP NHẬN KHÓA/MỞ DỊCH VỤ ADSL, FTTH, MYTV, SÀI GÒN TV'
          this.display_Params.kichHoatBtn_Tooltip = 'Kích hoạt Visa/Vasc'
          this.display_Params.kichHoatBtn_Visible = false
          this.display_Params.xoaHDBtn_Visible = false
          this.display_Params.hoanCongBtn_Visible = false
          this.display_Params.giaoPhieuBtn_Visible = true
          this.thueBao_grid.fields.splice(6, 0, {
            fieldName: 'TEN_STATUS',
            headerText: 'Kích hoạt Visa/Vasc',
            allowFiltering: true,
            allowSorting: false,
            width: 180
          })
          break
        case DichVuVienThong.EMAIL:
          this.header.title = 'TIẾP NHẬN KHÓA/MỞ EMAIL'
          this.display_Params.kichHoatBtn_Tooltip = ''
          this.display_Params.kichHoatBtn_Visible = true
          this.display_Params.xoaHDBtn_Visible = false
          this.display_Params.hoanCongBtn_Visible = true
          this.display_Params.giaoPhieuBtn_Visible = false
          break
        case DichVuVienThong.MEGAWAN:
          this.header.title = 'TIẾP NHẬN KHÓA MÁY MEGAWAN'
          this.display_Params.kichHoatBtn_Visible = false
          this.display_Params.xoaHDBtn_Visible = false
          this.display_Params.hoanCongBtn_Visible = false
          this.display_Params.giaoPhieuBtn_Visible = true
          this.display_Params.kichHoatBtn_Tooltip = ''
          break
        case DichVuVienThong.TSL:
          this.header.title = 'TIẾP NHẬN YÊU CẦU KHÓA MÁY TSL'
          this.display_Params.kichHoatBtn_Visible = false
          this.display_Params.xoaHDBtn_Visible = false
          this.display_Params.hoanCongBtn_Visible = false
          this.display_Params.giaoPhieuBtn_Visible = true
          this.display_Params.kichHoatBtn_Tooltip = ''
          break
        case DichVuVienThong.GPHONE:
          this.header.title = 'TIẾP NHẬN YÊU CẦU KHÓA MÁY GPHONE'
          this.display_Params.kichHoatBtn_Tooltip = 'Kích hoạt CCBS'
          this.display_Params.kichHoatBtn_Visible = false
          this.display_Params.xoaHDBtn_Visible = false
          this.display_Params.hoanCongBtn_Visible = false
          this.display_Params.giaoPhieuBtn_Visible = false
          this.thueBao_grid.fields.splice(7, 0, {
            fieldName: 'TEN_STATUS',
            headerText: 'Kích hoạt CCBS',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          })
          break
        case DichVuVienThong.IMS:
          this.header.title = 'TIẾP NHẬN YÊU CẦU KHÓA MÁY IMS'
          this.display_Params.kichHoatBtn_Tooltip = 'Kích hoạt VTN'
          this.display_Params.kichHoatBtn_Visible = false
          this.display_Params.xoaHDBtn_Visible = false
          this.display_Params.hoanCongBtn_Visible = false
          this.display_Params.giaoPhieuBtn_Visible = false
          this.thueBao_grid.fields.splice(6, 0, {
            fieldName: 'TEN_STATUS',
            headerText: 'Kích hoạt VTN',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          })
          break
        case DichVuVienThong.MEGA_EYES:
          this.header.title = 'TIẾP NHẬN KHÓA/MỞ MEGAEYES'
          this.display_Params.kichHoatBtn_Tooltip = 'Kích hoạt MegaEyes'
          this.display_Params.kichHoatBtn_Visible = true
          this.display_Params.xoaHDBtn_Visible = false
          this.display_Params.hoanCongBtn_Visible = true
          this.display_Params.giaoPhieuBtn_Visible = false
          this.thueBao_grid.fields.splice(7, 0, {
            fieldName: 'TEN_STATUS',
            headerText: 'Kích hoạt MegaEyes',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          })
          break
        case DichVuVienThong.DI_DONG:
          this.display_Params.xuatTextBtn_Title = 'Xuất file'
          break
        default:
          this.header.title = 'TIẾP NHẬN YÊU CẦU KHÓA/MỞ DỊCH VỤ'
          this.display_Params.kichHoatBtn_Tooltip = ''
          break
      };
    },
    async closeDialogHandler (args) {
      console.log(args)
      await this.onGetTT()
    },
    show_ppWorkflowActivation() {
      this.display_Params.isShow.ppWorkflowActivation = true;
      try {
        if (this.thueBao_grid.checked.length == 0)
        {
          // return this.$toast.error("Bạn chưa chọn phiếu nào!")
          this.$toast.error("Bạn chưa chọn phiếu nào!")
        }
        let ds_hdtb = []
        this.thueBao_grid.checked.forEach(item => {
          let thueBao = this.thueBao_grid.items.find(e => e.MA_TB === item)
          let data = {
            hdtb_id: thueBao.HDTB_ID,
            MA_TB: thueBao.MA_TB,
            TEN_TB: thueBao.TEN_TB,
            DIACHI_LD: thueBao.DIACHI_LD,
            CHIEU_KM: thueBao.CHIEU_KM,
            phieu_id: thueBao.PHIEU_ID,
            STATUS: "Chưa kích hoạt",
            STATUSID: 0,
            loai: 1,
            kieu: 1,
            vip_giamsat: false
          }
          ds_hdtb.push(data)

        })


        let data_test = [
          // {
          //   hdtb_id: 100001,
          //   MA_TB: 'tb_test_1',
          //   TEN_TB: 'tb_test_1',
          //   DIACHI_LD: 'tb_test_1',
          //   CHIEU_KM: 'tb_test_1',
          //   phieu_id: 'tb_test_1',
          //   STATUS: "Chưa kích hoạt",
          //   STATUSID: 0,
          //   loai: 1,
          //   kieu: 1,
          //   vip_giamsat: false
          // },
          // {
          //   hdtb_id: 100002,
          //   MA_TB: 'tb_test_2',
          //   TEN_TB: 'tb_test_2',
          //   DIACHI_LD: 'tb_test_2',
          //   CHIEU_KM: 'tb_test_2',
          //   phieu_id: 'tb_test_2',
          //   STATUS: "Chưa kích hoạt",
          //   STATUSID: 0,
          //   loai: 1,
          //   kieu: 1,
          //   vip_giamsat: false
          // },
          // {
          //   hdtb_id: 100003,
          //   MA_TB: 'tb_test_3',
          //   TEN_TB: 'tb_test_3',
          //   DIACHI_LD: 'tb_test_3',
          //   CHIEU_KM: 'tb_test_3',
          //   phieu_id: 'tb_test_3',
          //   STATUS: "Chưa kích hoạt",
          //   STATUSID: 0,
          //   loai: 1,
          //   kieu: 1,
          //   vip_giamsat: false
          // },
        ]
        ds_hdtb = [...ds_hdtb, ...data_test]

        console.log('tsbtnCreatIssue_Click', ds_hdtb)
        this.$refs.ppWorkflowActivation.openDialog(ds_hdtb)
        // console.log( this.$refs.ppWorkflowActivation)
      }
      catch (ex)
      {
        console.log(ex)
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
      }
    },


    async closeFormKichhoatToantrinh() {
      console.log('closeFormKichhoatToantrinh')
      await this.onGetTT()
    },
    btnCapNhatDVClick:function () {
      let dulieu = {
        hdtb_id: this.thueBao_grid.selected.HDTB_ID
      };
      this.$refs.popupCapNhatDV.openDialog(dulieu)
    }
  },

  watch: {
    'pyc_Params.dichVu_Selected': async function (newVal, oldVal) {
      await this.getDanhSachQuyTrinh()
      this.changeDisplay(newVal)
    },

    'pyc_Params.quyTrinh_Selected': async function (newVal, oldVal) {
      await this.getDanhSachLoaiHinhTBTheoQuyTrinh(newVal)
      let rs = await this.getHuongGiaoTheoQuyTrinh()
      if (rs > 0) { this.header.title = this.common_Params.huongGiao } else {
        this.$toast.error(`Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại`)
        this.common_Params.huongGiao_Id = -1
        await this.onHienThiDanhSach2()
        return
      }
      await this.onHienThiGiaoDien(this.common_Params.luong_Id)
      if (!this.pyc_Params.loaiHinh_Checked) { await this.onHienThiDanhSach2() }
    },

    'pyc_Params.loaiHinh_Checked': async function (newVal, oldVal) {
      await this.onHienThiDanhSach2()
    },

    'pyc_Params.loaiPhieu': async function (newVal, oldVal) {
      await this.onHienThiDanhSach2()
    },

    'pyc_Params.kieuYC_Selected': async function (newVal, oldVal) {
      if (this.common_Params.TTHD_Id === TrangThaiHD.THANH_TOAN &&
                (Number(this.pyc_Params.dichVu_Selected) === DichVuVienThong.CO_DINH ||
                    Number(this.pyc_Params.dichVu_Selected) === DichVuVienThong.IMS)) {
        if (newVal === 1) {
          this.display_Params.giaoPhieuBtn_Visible = false
          this.display_Params.hoanCongBtn_Visible = true
        } else {
          this.display_Params.giaoPhieuBtn_Visible = true
          this.display_Params.hoanCongBtn_Visible = Number(this.pyc_Params.dichVu_Selected) === DichVuVienThong.CO_DINH
        }
      }

      if (this.phaiLam('TUDONG_THUHOI_KHI_HOANCONG') && newVal === 1 &&
                this.common_Params.KT_ThamSo_ThuHoi) {
        this.pyc_Params.thuHoiTB = 1
        this.pyc_Params.thuHoiTB_Enable = true
      } else {
        this.pyc_Params.thuHoiTB = 0
        this.pyc_Params.thuHoiTB_Enable = false
      }
      await this.onHienThiDanhSach2()
    },
    'thueBao_grid.checked': function (value) {
      this.thueBao_grid.checked = value
    },
    checkAll: function (value) {
      this.thueBao_grid.checked = []
      let dsFilter = this.$refs.thueBaoGrid.filteredDataSource || []
      if (dsFilter.length <= 0) dsFilter = this.thueBao_grid.items
      if (value) {
        for (const e of dsFilter) {
          this.thueBao_grid.checked.push(e.MA_TB)
        }
      }
    },
  }
}
</script>
