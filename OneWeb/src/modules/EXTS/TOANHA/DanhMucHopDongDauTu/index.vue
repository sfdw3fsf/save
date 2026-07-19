<template src="./index.template.html"></template>
<style scoped src="./style.scss"></style>
<script>
import ComboboxGrid from '../../../../components/Controls/ComboboxGrid'
import Calculator from '@/components/Calculator.vue'
import { required, maxLength, minLength, numeric, between, email } from 'vuelidate/lib/validators'
import moment from 'moment'
import PopupChiTietHDDT from './modal/ModalChiTietHDDT.vue'
import PopupTienTrinhDT from './modal/ModalTienTrinhDauTu.vue'
import PopupUploadFile from './modal/ModalUploadFile.vue'
import PopupChayLaiDTPC from './modal/ModalChayLaiDTPC.vue'
import DateTimeLib from '@/utils/DateTimeLib'
import frmChonTinhHopDong from './modal/chon-tinh/frmChonTinh.vue'

export default {
  components: {
    appCombobox: ComboboxGrid,
    PopupChiTietHDDT,
    PopupTienTrinhDT,
    PopupUploadFile,
    PopupChayLaiDTPC,
    Calculator,
    frmChonTinhHopDong
  },
  name: 'DanhMucHopDongDauTu',

  computed: {
    isRequiredHT: function () {
      return this.$root.token.getPhanVungID() == 28
    },
    isHopDongCha: function () {
      return this.cbo_HopDongCha.selected == null || this.cbo_HopDongCha.selected == 0
    }
  },

  async created() {
    this.setButton(-1)
    await this.loadCombobox()
    await this.onLayTT(false)
    await this.onLoad()
    this.tinhHienTai = this.$auth.getPhanVungID()
  },

  beforeUpdate() {
    this.hideComboboxTableHeader()
  },

  data() {
    return {
      txt_maduan: null,
      isRowSelected: false,
      isShowChonTinh: false,
      tinh: null,
      listTinh: [],
      tinhHienTai: null,
      isViewOnly: false,
      txt_MaHDDT: null,
      txt_TenHDDT: null,
      txt_NguoiKy: null,
      txt_ChucVu: null,
      txt_SoHD: null,
      txt_TienDo: null,
      txt_MST: null,
      txt_STK: null,
      txt_TenTK: null,
      txt_TenBPDS: null,
      txt_SdtBPDS: null,
      txt_EmailBPDS: null,
      txt_DiaChiBPDS: null,
      txt_GhiChu: null,
      txt_TongMucDT: 0,
      txt_ThiPhanToiThieu: 0,
      txt_APRUToiThieu: 0,
      txt_GiaTriTS: 0,
      txt_TyLePCDT: 0,
      txt_TyLePCDT_old: 0,
      txt_ChuDauTu_id: 0,
      tylePCDT: {
        precision: 2,
        min: 0,
        max: 100.01,
        readonly: false,
        currentValue: 0,
        changeValue: 0
      },
      isHiddenTyLePCDT: false,
      txt_ChiPhiTSL: 0,
      date_NgayKy: null,
      date_ThoiHan: null,
      date_ThoiHanDoiTac: null,
      date_NgayThanhToan: null,
      date_NgayHieuLuc: null,
      date_TGTinhHQDT: null,
      today: null,
      isCalcShow: false,
      cbo_ChuKy: {
        items: [],
        selected: null
      },
      cbo_NVDoiSoat: {
        items: [],
        selected: null
      },
      cbo_NVQuanLy: {
        items: [],
        selected: null
      },
      cbo_NganHang: {
        items: [],
        selected: null
      },
      cbo_ToaNha: {
        items: [],
        selected: null,
        text: ''
      },
      cbo_ChuQuan: {
        items: [],
        selected: null
      },
      cbo_NhomDA: {
        items: [],
        selected: null
      },
      cbo_HopDongCha: {
        items: [],
        selected: null
      },
      cbo_HinhThucDauTu: {
        items: [],
        selected: null
      },
      cbo_HinhThucPCDT: {
        items: [],
        selected: null
      },
      cbo_HinhThucHoaDon: {
        items: [],
        selected: null
      },
      cbo_TinhTrang: {
        items: [],
        selected: null
      },
      cbo_PLDACapDong: {
        items: [],
        selected: null
      },
      cbo_PLDACapQuang: {
        items: [],
        selected: null
      },
      chb_GoiCuocTichHop: false,
      chb_DTPCTheoCuocPS: false,
      chb_ChiPhiToiDa: false,
      chb_MangNoiBo: false,
      grid_HopDong: {
        items: [],
        selected: {},
        key: 1
      },
      btn_Enabled: {
        nhapMoi: false,
        ghiLai: false,
        xoa: false,
        huy: false,
        chiTiet: false,
        tienTrinh: false,
        chayLaiDTPC: false
      },
      common_params: {
        hddtuId: 0,
        toaNhaId: 0,
        mst_ErrorMessage: '',
        mst_valid: false
      },
      checkThangChot: false,
      thangChot: {
        value: new Date(),
        currentDate: new Date(),
        changeDate: new Date()
      },
      waterMark: 'Chọn ngày',
      dateFormat: 'MM/yyyy',
      phanVungID: this.$root.token.getPhanVungID()
    }
  },

  validations: {
    txt_TenHDDT: {
      required,
      maxLength: maxLength(500)
    },
    cbo_ToaNha: {
      selected: {
        required
      }
    },
    cbo_ChuQuan: {
      selected: {
        required
      }
    },
    txt_TyLePCDT: {
      required,
      between: between(0, 100)
    },
    txt_NguoiKy: {
      maxLength: maxLength(100)
    },
    txt_ChucVu: {
      maxLength: maxLength(100)
    },
    txt_SoHD: {
      maxLength: maxLength(200)
    },
    txt_TienDo: {
      maxLength: maxLength(200)
    },
    txt_MST: {
      isValid() {
        return this.common_params.mst_valid
      }
    },
    txt_GhiChu: {
      maxLength: maxLength(500)
    },
    txt_TongMucDT: {
      maxLength: maxLength(12)
    },
    txt_ThiPhanToiThieu: {
      maxLength: maxLength(12),
      required,
      between: between(1, 999999999)
    },
    txt_GiaTriTS: {
      maxLength: maxLength(12)
    },
    txt_APRUToiThieu: {
      maxLength: maxLength(12),
      required,
      between: between(1, 999999999)
    },
    txt_ChiPhiTSL: {
      maxLength: maxLength(12)
    },
    txt_EmailBPDS: {
      email
    },
    txt_SdtBPDS: {
      numeric,
      maxLength: maxLength(11),
      minLength: minLength(7)
    },
    date_ThoiHan: {
      required,
    },
    // date_NgayKy: {
    //   date: {
    //     format: 'dd/MM/yyyy'
    //   }
    // },
    // date_NgayThanhToan: {
    //   date: {
    //     format: 'dd/MM/yyyy'
    //   }
    // },
    // date_NgayHieuLuc: {
    //   date: {
    //     format: 'dd/MM/yyyy'
    //   }
    // },
    // date_TGTinhHQDT: {
    //   date: {
    //     format: 'dd/MM/yyyy'
    //   }
    // },
    // date_ThoiHanDoiTac: {
    //   date: {
    //     format: 'dd/MM/yyyy'
    //   }
    // }
  },

  methods: {
    cboTinh_EditValueChanged: async function (args) {
      if (args == null) {
        this.tinh = {}
        return
      }
      this.tinh = args
      if (this.tinh !== this.tinhHienTai) {
        this.isViewOnly = true
      } else {
        this.isViewOnly = false
      }
      this.clear()
      this.setButton(-1)
      await this.loadCombobox()
      await this.onLayTT(false)
    },
    async onLoad() {
      this.axios.post('web-hopdong/bss_group1/lay_ds_tinh_thicong_combobox', {}).then((rs) => {
        this.listTinh = rs.data.data
        if (this.listTinh.length > 0) {
          this.tinh = this.$auth.getPhanVungID()
        }
      })
    },
    checkShowModal: async function () {
      this.$root.showLoading(true)
      let data = await this.$root.context.post('web-toanha/capnhat-doanhthu-bc/fn_kiemtra_quyen_capnhat_doanhthu_bc', {
        kieu: 1
      })
      if (data.data == 1) {
        this.isShowChonTinh = true
        this.$root.showLoading(false)
        await this.$refs.ref_frmChonTinhHopDong.showModal()
      } else {
        this.$root.showLoading(false)
      }
    },
    onChapNhanModalChonTinh: async function (data) {
      this.tinh = data
      this.clear()
      this.setButton(-1)
      await this.loadCombobox()
      await this.onLayTT(false)
      if (this.tinh !== this.tinhHienTai) {
        this.isViewOnly = true
      } else {
        this.isViewOnly = false
      }
    },
    hideComboboxTableHeader() {
      let cbbRef = ['nganHangCbo', 'toaNhaCbo', 'hdchaCbo']
      cbbRef.forEach((item) => {
        let elemts = this.$refs[item].$refs.grid.$el.querySelectorAll('.e-headercell')
        elemts.forEach((elment) => {
          elment.style.display = 'none'
        })
      })
    },

    onCheckThangChot(args) {
      if (args != null) {
        this.btn_Enabled.xoa = !args
        this.btn_Enabled.nhapMoi = !args
        //this.btn_Enabled.ghiLai=!args;
      }
    },

    async onChangeThangChot(args) {
      await this.onLayTT(false)
      // if (args != null && this.isRequiredHT) {
      //     var selectedYear = args.getFullYear();
      //     var selectedMonth = args.getMonth() + 1;
      //     var currentDate = new Date();
      //     this.thangChot.changeValue = args;
      //     if (selectedYear == currentDate.getFullYear() || (selectedMonth == 12 && selectedYear == currentDate.getFullYear() - 1)) {
      //         this.tylePCDT.readonly = false;
      //     } else {
      //         this.tylePCDT.readonly = true;
      //     }
      //     //! Load danh sách khi thay đổi kỳ cước
      //     if (this.thangChot.currentDate.getFullYear() != selectedYear || this.thangChot.currentDate.getMonth() + 1 != selectedMonth) {
      //         //! goi ham nay dang loi infinite loop
      //         // await this.onLayTT(false);
      //     }

      // }
    },

    onChangeTyLePCDT(args) {
      if (args != null && this.isRequiredHT) {
        this.tylePCDT.changeValue = args
        if (this.tylePCDT.changeValue != this.tylePCDT.currentValue) {
          this.btn_Enabled.chayLaiDTPC = true
        } else {
          this.btn_Enabled.chayLaiDTPC = false
        }
      }
    },

    onNganHangSelected(args) {
      if (args != null) this.cbo_NganHang.selected = args.nganhang_id
    },

    // onHDChaSelected(args) {
    //     if (args != null) {
    //         this.cbo_HopDongCha.selected = args.hddtu_id;
    //         if ((this.cbo_HopDongCha.selected == 0 || this.cbo_HopDongCha.selected == null) && this.isRequiredHT) {
    //             // console.log(this.cbo_HopDongCha.selected)
    //             this.isHiddenTyLePCDT = false;
    //         } else {
    //             this.isHiddenTyLePCDT = true;
    //         }
    //     }
    // },

    onToaNhaSelected(args) {
      if (args != null) {
        this.cbo_ToaNha.selected = args.toanha_id
        this.cbo_ToaNha.text = args.ten_toanha
        this.$refs.toaNhaCbo.$refs.comboboxGridInput.classList.remove('is-invalid')
      }
    },
    async validateData() {
      this.common_params.mst_valid = this.txt_MST !== null && this.txt_MST !== '' ? await this.getKiemTraMST(this.txt_MST) : true
      this.$v.$touch()
      let errorMessage = []
      let focusInput = []
      if (!this.$v.txt_TenHDDT.required) {
        errorMessage.push('Tên hợp đồng đầu tư không được để trống!')
        focusInput.push('tenHddt')
      } else if (!this.$v.txt_TenHDDT.maxLength) {
        errorMessage.push('Tên hợp đồng đầu tư không được lớn hơn 500 ký tự!')
        focusInput.push('tenHddt')
      }

      if (!this.$v.txt_NguoiKy.maxLength) {
        errorMessage.push('Người ký không được lớn hơn 100 ký tự!')
        focusInput.push('nguoiKy')
      }

      if (!this.$v.txt_ChucVu.maxLength) {
        errorMessage.push('Chức vụ không được lớn hơn 100 ký tự!')
        focusInput.push('chucVu')
      }

      if (!this.$v.txt_SoHD.maxLength) {
        errorMessage.push('Số hợp đồng không được lớn hơn 200 ký tự!')
        focusInput.push('soHD')
      }

      if (!this.$v.txt_TienDo.maxLength) {
        errorMessage.push('Tiến độ không được lớn hơn 200 ký tự!')
        focusInput.push('tienDo')
      }

      if (!this.$v.txt_MST.isValid) {
        errorMessage.push(this.common_params.mst_ErrorMessage)
        focusInput.push('mst')
      }

      if (this.phanVungID != 28) {
        if (!this.$v.txt_SdtBPDS.numeric) {
          errorMessage.push('Số điện thoại phải là dạng số!')
          focusInput.push('phone')
        } else if (!this.$v.txt_SdtBPDS.maxLength) {
          errorMessage.push('Số điện thoại không được lớn hơn 11 ký tự!')
          focusInput.push('phone')
        } else if (!this.$v.txt_SdtBPDS.minLength) {
          errorMessage.push('Số điện thoại không được nhỏ hơn 7 ký tự!')
          focusInput.push('phone')
        }
      }

      if (this.phanVungID != 28 && !this.$v.txt_EmailBPDS.email) {
        errorMessage.push('Email không đúng định dạng!')
        focusInput.push('email')
      }

      if (!this.$v.txt_GhiChu.maxLength) {
        errorMessage.push('Ghi chú không được lớn hơn 500 ký tự!')
        focusInput.push('ghiChu')
      }

      if (!this.$v.cbo_ToaNha.selected.required) {
        errorMessage.push('Tòa nhà không được để trống!')
        this.$refs.toaNhaCbo.$refs.comboboxGridInput.classList.add('is-invalid')
      }

      if (!this.$v.cbo_ChuQuan.selected.required) errorMessage.push('Chủ quản không được để trống!')

      if (!this.$v.txt_TongMucDT.maxLength) {
        errorMessage.push('Tổng mức đầu tư không được lớn hơn 12 chữ số!')
        focusInput.push('tongMucDT')
      }

      if (!this.$v.txt_ThiPhanToiThieu.maxLength) {
        errorMessage.push('Thị phần tối thiểu không được lớn hơn 12 chữ số!')
        focusInput.push('thiPhanTT')
      }

      if (!this.$v.txt_APRUToiThieu.maxLength) {
        errorMessage.push('APRU tối thiểu không được lớn hơn 12 chữ số!')
        focusInput.push('apruTT')
      }

      if (!this.$v.txt_GiaTriTS.maxLength) {
        errorMessage.push('Giá trị tài sản không được lớn hơn 12 chữ số!')
        focusInput.push('giaTriTS')
      }

      if (!this.$v.txt_TyLePCDT.required) {
        errorMessage.push('Tỷ lệ phân chia doanh thu không được để trống!')
        focusInput.push('tyLePCDT')
      } else if (!this.$v.txt_TyLePCDT.between) {
        errorMessage.push('Tỷ lệ phân chia doanh thu phải từ 0.00% -> 100.00%!')
        focusInput.push('tyLePCDT')
      }

      if (!this.$v.txt_ChiPhiTSL.maxLength) {
        errorMessage.push('Chi phí tối đa KTR-TSL không được lớn hơn 12 chữ số!')
        focusInput.push('chiPhiTSL')
      }

      if (!this.$v.date_ThoiHan.required) {
          errorMessage.push('Thời hạn không được để trống!')
          focusInput.push('thoiHan')
        }

        if (!this.$v.txt_ThiPhanToiThieu.required) {
          errorMessage.push('Thị phần tối thiểu không được để trống!')
          focusInput.push('thiPhanTT')
        }

        if (!this.$v.txt_APRUToiThieu.required) {
          errorMessage.push('APRU tối thiểu không được để trống!')
          focusInput.push('apruTT')
        }

      if  (!this.btn_Enabled.nhapMoi){
        if (!this.$v.txt_ThiPhanToiThieu.between) {
          errorMessage.push('Thị phần tối thiểu phải lớn hơn 0!')
          focusInput.push('thiPhanTT')
        }

        if (!this.$v.txt_APRUToiThieu.between) {
          errorMessage.push('APRU tối thiểu phải lớn hơn 0!')
          focusInput.push('apruTT')
        }
      }

      if (this.isRequiredHT) {
        if (this.cbo_HinhThucPCDT.selected == 0) {
          errorMessage.push('Chưa chọn hình thức PCDT')
        }
        if (this.cbo_HinhThucHoaDon.selected == 0) {
          errorMessage.push('Chưa chọn hình thức hóa đơn')
        }
        if (!this.date_NgayKy) {
          errorMessage.push('Ngày ký không được để trống!')
        }
        if (this.cbo_HinhThucDauTu.selected == 0) {
          errorMessage.push('Chưa chọn hình thức đầu tư')
        }
        if (this.cbo_PLDACapDong.selected == 0) {
          errorMessage.push('Chưa chọn PLDA Cáp đồng')
        }
        if (this.cbo_PLDACapQuang.selected == 0) {
          errorMessage.push('Chưa chọn PLDA Cáp quang')
        }
      }

      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\r\n'))
        if (focusInput.length > 0) this.$refs[focusInput[0]].focus()
        return false
      } else return true
    },

    async onLayTT(isMessage = true) {
      let d = 0
      if (this.checkThangChot) {
        if (this.thangChot.value != null) {
          d = parseInt(DateTimeLib.toDateDisplayWithFormat(this.thangChot.value, 'YYYYMM'))
        }
      }
      await this.getDsHopDongDauTu(d)
      if (this.grid_HopDong.items.length <= 0) {
        this.setButton(1)
        if (isMessage) this.$toast.error('Không có dữ liệu, vui lòng thử lại')
      }
      this.$v.$reset()
    },

    onNhapMoi() {
      this.setButton(1)
    },

    async onGhiLai() {
      // if (this.isRequiredHT) {
      //     // call api hcm
      // } else {
      let kieu = !this.btn_Enabled.nhapMoi ? 1 : 2
      let rs = await this.getKiemTraQuyenToaNha(kieu, this.common_params.hddtuId, this.common_params.toaNhaId)
      if (rs.startsWith('OK')) {
        if (await this.validateData()) {
          if (!this.btn_Enabled.nhapMoi) {
            let rs1 = await this.addHopDongDauTu2()
            rs1 = JSON.parse(rs1)
            if (rs1.KETQUA == 1) this.$root.toastSuccess('Thêm mới hợp đồng đầu tư thành công!')
            else this.$root.toastError(rs1.THONGBAO)
          } else {
            let rs1 = await this.updateHopDongDauTu2()
            rs1 = JSON.parse(rs1)
            if (rs1.KETQUA == 1) {
              let p = { ...this.grid_HopDong.selected }
              let idSelected = p.hddtu_id
              await this.onLayTT(false)
              try {
                this.grid_HopDong.selected = p
                this.onHopDongSelected(
                  {
                    data: this.grid_HopDong.items.find((e) => e.hddtu_id == idSelected)
                  }
                )
              } catch (ex) {}
              this.$root.toastSuccess('Cập nhật hợp đồng đầu tư thành công!')
            } else {
              this.$root.toastError(rs1.THONGBAO)
            }
            // if (this.txt_TyLePCDT != this.txt_TyLePCDT_old) {
            //     this.$bvModal
            //         .msgBoxConfirm("Bạn đang thay đổi tỷ lệ PCDT! Bạn có muốn cập nhật lại tỷ lệ PCDT trong chi tiết hợp đồng đầu tư?", {
            //             title: "Thông báo",
            //             size: "sm",
            //             okTitle: "Đồng ý",
            //             cancelTitle: "Hủy",
            //             centered: true,
            //         })
            //         .then(async (v) => {
            //             if (v) {
            //                 let rs1 = await this.updateHopDongDauTu();
            //                 //this.grid_HopDong.selected.hddtu_id
            //                 let p = { ...this.grid_HopDong.selected };
            //                 if (rs1.startsWith('OK')) {
            //                     //await this.updateTyLePCDT();
            //                     this.$root.toastSuccess('Cập nhật hợp đồng đầu tư thành công!');
            //                     await this.onLayTT(false);
            //                     try {
            //                         this.grid_HopDong.selected = p;
            //                         this.onHopDongSelected({ data: this.grid_HopDong.selected });
            //                     }
            //                     catch (ex) {
            //                     }
            //                 } else {
            //                     this.$root.toastError(rs1);
            //                 }
            //             }
            //         });
            // }
            // else {
            //     let rs1 = await this.updateHopDongDauTu();
            //     if (rs1.startsWith('OK')) {
            //         this.$root.toastSuccess('Cập nhật hợp đồng đầu tư thành công!');
            //         await this.onLayTT(false);
            //     } else {
            //         this.$root.toastError(rs1);
            //     }
            // }
          }
        }
      } else this.$root.toastError(rs)
      //}
    },

    onHuy() {
      if (!this.btn_Enabled.nhapMoi)
        // Hủy nhập mới
        this.onNhapMoi()
      else {
        //Hủy cập nhật
        this.onHopDongSelected({ data: this.grid_HopDong.selected })
        this.setButton(2)
      }
    },

    async onXoa() {
      let rs = await this.getKiemTraQuyenToaNha(3, this.common_params.hddtuId, this.common_params.toaNhaId)
      if (rs.startsWith('OK')) {
        try {
          await this.$bvModal
            .msgBoxConfirm('Bạn có chắc chắn muốn xóa?', {
              title: 'Thông báo',
              size: 'lg',
              buttonSize: 'lg',
              okVariant: 'primary',
              okTitle: 'Đồng ý',
              cancelTitle: 'Không đồng ý',
              hideHeaderClose: false,
              centered: true,
              'modal-class': ['f18', 'text-center']
            })
            .then(async (value) => {
              if (value) {
                let rs1 = await this.deleteHopDongDauTu2()
                console.log(rs1)
                if (rs1) this.$toast.success('Xóa dữ liệu thành công!')
                await this.onLayTT(false)
              }
            })
            .catch((err) => {
              console.log(err)
            })
        } catch (error) {
          console.log(error)
          this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
        }
      } else this.$toast.error(rs)
    },

    onChiTiet() {
      this.$refs.popupChiTietHDDT.showModal()
    },

    onTienTrinh() {
      this.$refs.popupTienTrinhDT.showModal()
    },

    onFileHS() {
      this.$refs.popupUploadFile.vhopdong_id = this.common_params.hddtuId
      this.$refs.popupUploadFile.vloai_hd = 1
      this.$refs.popupUploadFile.vServerName = 'HD_DAUTU'
      this.$refs.popupUploadFile.showModal()
    },

    onXuatExcel() {
      if (this.grid_HopDong.items.length <= 0) this.$toast.error('Chưa có danh sách để xuất excel!')
      else {
        let excelExportProperties = {
          fileName: 'Danh-muc-hop-dong-dau-tu.xlsx',
          dataSource: this.grid_HopDong.items
        }
        this.$refs.hopDongGrid.excelExport(excelExportProperties)
      }
    },
    onChayLaiDTPCModal: function () {
      this.$refs.ref_popupChayLaiDTPC.showModal()
    },

    onChayLaiDTPC: async function () {
      if (this.isRequiredHT) {
        var checkValid = false
        var message = 'Chỉ cho phép thực hiện thao tác trong năm hiện tại hoặc tháng 12 năm trước đó'
        var selectedYear = this.thangChot.value.getFullYear()
        var selectedMonth = this.thangChot.value.getMonth() + 1
        var currentDate = new Date()
        if (selectedYear == currentDate.getFullYear() || (selectedMonth == 12 && selectedYear == currentDate.getFullYear() - 1)) {
          checkValid = true
        } else {
          checkValid = false
        }
        if (!checkValid) {
          this.$root.toastError(message)
          return
        }
        //if (this.tylePCDT.currentValue != this.tylePCDT.changeValue) {

        let msg = 'Bạn có chắc chắn cập nhật lại doanh thu phân chia tháng ' + DateTimeLib.toDateDisplayWithFormat(this.thangChot.value, 'MM/YYYY') + ' ?'
        this.$bvModal
          .msgBoxConfirm(msg, {
            title: 'Thông báo',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
            centered: true
          })
          .then(async (v) => {
            if (v) {
              try {
                this.$root.showLoading(true)
                let chuKy = parseInt(DateTimeLib.toDateDisplayWithFormat(this.thangChot.value, 'YYYYMM'))
                let rs = await this.doanhthu_toanha_thaydoi(chuKy, this.common_params.hddtuId, this.txt_TyLePCDT, this.chb_GoiCuocTichHop ? 1 : 0)
                if (rs != null) {
                  if (rs[0].kq == 1) {
                    this.$root.toastSuccess(rs[0].thongbao)
                  } else {
                    this.$root.toastError(rs[0].thongbao)
                  }
                }
              } catch (error) {
                this.$root.toastError(error)
              } finally {
                this.$root.showLoading(false)
              }
            }
          })
        //}
      }
    },

    selectCboRow(nganHangId, toaNhaId, hdChaId) {
      let idx1 = nganHangId ? this.cbo_NganHang.items.findIndex((e) => e.nganhang_id === nganHangId) : 0
      let idx2 = toaNhaId ? this.cbo_ToaNha.items.findIndex((e) => e.toanha_id === toaNhaId) : 0
      let idx3 = hdChaId ? this.cbo_HopDongCha.items.findIndex((e) => e.hddtu_id === hdChaId) : 0
      this.$refs.nganHangCbo.$refs.grid.setCurrentSelectedRow(idx1 !== -1 ? idx1 : 0)
      this.$refs.toaNhaCbo.$refs.grid.setCurrentSelectedRow(idx2 !== -1 ? idx2 : 0)
      this.$refs.hdchaCbo.$refs.grid.setCurrentSelectedRow(idx3 !== -1 ? idx3 : 0)
    },
    onHopDongSelected(args) {
      this.isRowSelected = true
      this.$refs.nganHangCbo.clearFilterConditions()
      this.$refs.toaNhaCbo.clearFilterConditions()
      if (args) {
        this.txt_maduan = args.data.ma_duan
        this.txt_ChuDauTu_id = args.data.chudtu_id
        this.grid_HopDong.selected = args.data
        this.common_params.hddtuId = args.data.hddtu_id
        this.txt_MaHDDT = args.data.ma_hddtu
        this.txt_TenHDDT = args.data.ten_hddtu
        this.txt_SoHD = args.data.so_hd
        this.date_NgayKy = args.data.ngayky1 !== '' && args.data.ngayky1 != null ? moment(args.data.ngayky1, 'DD/MM/YYYY').toDate() : null
        this.date_ThoiHan = args.data.thoihan1 !== '' && args.data.thoihan1 != null ? moment(args.data.thoihan1, 'DD/MM/YYYY').toDate() : null
        this.date_ThoiHanDoiTac = args.data.thoihan_doitac !== '' && args.data.thoihan_doitac != null ? moment(args.data.thoihan_doitac, 'DD/MM/YYYY').toDate() : null
        this.date_NgayThanhToan = args.data.ngay_tt1 !== '' && args.data.ngay_tt1 != null ? moment(args.data.ngay_tt1, 'DD/MM/YYYY').toDate() : null
        this.date_NgayHieuLuc = args.data.ngay_hieuluc1 !== '' && args.data.ngay_hieuluc1 != null ? moment(args.data.ngay_hieuluc1, 'DD/MM/YYYY').toDate() : null
        this.date_TGTinhHQDT = args.data.tg_hqdt1 !== '' && args.data.tg_hqdt1 != null ? moment(args.data.tg_hqdt1, 'DD/MM/YYYY').toDate() : null
        this.txt_TienDo = args.data.tiendo
        this.txt_GhiChu = args.data.ghichu
        this.txt_TongMucDT = args.data.tongmuc_dtu
        this.txt_ThiPhanToiThieu = args.data.thiphan_toithieu
        this.txt_APRUToiThieu = args.data.apru_toithieu
        this.txt_NguoiKy = args.data.nguoiky
        this.txt_ChucVu = args.data.chucvu
        this.txt_GiaTriTS = args.data.giatri_taisan
        this.txt_MST = args.data.mst
        this.txt_STK = args.data.stk
        this.txt_TyLePCDT = args.data.tyle_pcdt ? args.data.tyle_pcdt : 0
        this.txt_TyLePCDT_old = args.data.tyle_pcdt ? args.data.tyle_pcdt : 0
        this.tylePCDT.currentValue = this.txt_TyLePCDT
        this.txt_ChiPhiTSL = args.data.cp_toida_tsl ? args.data.cp_toida_tsl : 0
        this.txt_TenTK = args.data.ten_tk
        this.txt_TenBPDS = args.data.ten_bpds
        this.txt_EmailBPDS = args.data.email_bpds
        this.txt_DiaChiBPDS = args.data.diachi_bpds
        this.txt_SdtBPDS = args.data.sdt_bpds
        this.chb_GoiCuocTichHop = args.data.goicuoc_tichhop ? args.data.goicuoc_tichhop === 1 : false
        this.chb_DTPCTheoCuocPS = args.data.dtpc_cps ? args.data.dtpc_cps === 1 : false
        this.chb_ChiPhiToiDa = args.data.cp_toida_tsl_id ? args.data.cp_toida_tsl_id === 1 : false
        this.chb_MangNoiBo = args.data.mang_noibo ? args.data.mang_noibo === 1 : false
        this.cbo_NVDoiSoat.selected = args.data.nv_doisoat_id
        this.cbo_NVQuanLy.selected = args.data.nv_quanly_id
        this.cbo_NhomDA.selected = args.data.nhom_da
        this.cbo_ChuQuan.selected = args.data.chuquan_id
        this.cbo_HinhThucHoaDon.selected = args.data.ht_hoadon_id ? args.data.ht_hoadon_id : 0
        this.cbo_HinhThucDauTu.selected = args.data.ht_dautu_id ? args.data.ht_dautu_id : 0
        this.cbo_HinhThucPCDT.selected = args.data.ht_pcdt_id ? args.data.ht_pcdt_id : 0
        this.cbo_TinhTrang.selected = args.data.tinhtrang_id
        this.cbo_ChuKy.selected = args.data.chuky_tt ? args.data.chuky_tt : 0
        this.cbo_PLDACapDong.selected = args.data.plda_dong ? args.data.plda_dong : 0
        this.cbo_PLDACapQuang.selected = args.data.plda_quang ? args.data.plda_quang : 0

        this.selectCboRow(args.data.nganhang_id, args.data.toanha_id, args.data.hddtu_cha_id)
        this.setButton(2)
      }
    },

    setButton(kieu) {
      this.btn_Enabled = {
        nhapMoi: false,
        ghiLai: false,
        xoa: false,
        huy: false,
        chiTiet: false,
        tienTrinh: false
      }
      this.$v.$reset()
      switch (kieu) {
        case -1:
          this.btn_Enabled.ghiLai = true
          this.btn_Enabled.huy = true
          break
        case 0:
          this.btn_Enabled.nhapMoi = true
          this.clear()
          break
        case 1:
          this.clear()
          this.btn_Enabled.ghiLai = true
          this.btn_Enabled.huy = true
          this.txt_MaHDDT = ''
          break
        case 2:
          this.btn_Enabled.nhapMoi = true
          this.btn_Enabled.xoa = true
          this.btn_Enabled.ghiLai = true
          this.btn_Enabled.huy = true
          this.btn_Enabled.chiTiet = true
          this.btn_Enabled.tienTrinh = true
          break
        default:
          break
      }
    },

    clear() {
      this.cbo_HopDongCha.items = []
      this.common_params.hddtuId = 0
      this.txt_MaHDDT = ''
      this.txt_TenHDDT = ''
      this.txt_SoHD = ''
      this.txt_TienDo = ''
      this.txt_GhiChu = ''
      this.txt_NguoiKy = ''
      this.txt_ChucVu = ''
      this.date_NgayKy = null
      this.date_ThoiHan = null
      this.date_ThoiHanDoiTac = null
      this.date_NgayThanhToan = null
      this.date_NgayHieuLuc = null
      this.date_TGTinhHQDT = null
      this.txt_TongMucDT = 0
      this.txt_ThiPhanToiThieu = 0
      this.txt_APRUToiThieu = 0
      this.txt_GiaTriTS = 0
      this.txt_TyLePCDT = 0
      this.txt_STK = ''
      this.txt_MST = ''
      this.chb_GoiCuocTichHop = false
      this.chb_DTPCTheoCuocPS = false
      this.chb_ChiPhiToiDa = false
      this.chb_MangNoiBo = false
      this.txt_ChiPhiTSL = 0
      this.txt_TenBPDS = ''
      this.txt_SdtBPDS = ''
      this.txt_EmailBPDS = ''
      this.txt_DiaChiBPDS = ''
      this.txt_TenTK = ''
      this.cbo_NhomDA.selected = null
      this.cbo_NVDoiSoat.selected = null
      this.cbo_NVQuanLy.selected = null
      this.cbo_ChuQuan.selected = null
      this.cbo_HinhThucHoaDon.selected = 0
      this.cbo_HinhThucDauTu.selected = 0
      this.cbo_HinhThucPCDT.selected = 0
      this.cbo_TinhTrang.selected = 1
      this.cbo_ChuKy.selected = 0
      this.cbo_PLDACapDong.selected = 0
      this.cbo_PLDACapQuang.selected = 0
      this.selectCboRow(null, null, null)
      this.$refs.nganHangCbo.selectedItems = [] //Clear text of comboboxGrid
      this.$refs.toaNhaCbo.selectedItems = []
      this.cbo_ToaNha.selected = null
      this.$v.$reset()
      this.$refs.tenHddt.focus()
    },

    async loadCombobox() {
      await this.getDanhMucHopDongDauTu()
      await this.getDsChuQuan()
      if (this.cbo_NhomDA.items.length > 0) this.cbo_NhomDA.selected = this.cbo_NhomDA.items[0].nhomda_id
      if (this.cbo_ToaNha.items.length > 0) this.cbo_ToaNha.selected = this.cbo_ToaNha.items[0].toanha_id
      if (this.cbo_HinhThucHoaDon.items.length > 0) this.cbo_HinhThucHoaDon.selected = this.cbo_HinhThucHoaDon.items[0].vid
      if (this.cbo_HinhThucPCDT.items.length > 0) this.cbo_HinhThucPCDT.selected = this.cbo_HinhThucPCDT.items[0].vid
      if (this.cbo_HinhThucDauTu.items.length > 0) this.cbo_HinhThucDauTu.selected = this.cbo_HinhThucDauTu.items[0].vid
      if (this.cbo_TinhTrang.items.length > 0) this.cbo_TinhTrang.selected = this.cbo_TinhTrang.items[0].vid
      if (this.cbo_NganHang.items.length > 0) this.cbo_NganHang.selected = this.cbo_NganHang.items[0].nganhang_id
      if (this.cbo_NVQuanLy.items.length > 0) this.cbo_NVQuanLy.selected = this.cbo_NVQuanLy.items[0].nhanvien_id
      if (this.cbo_NVDoiSoat.items.length > 0) this.cbo_NVDoiSoat.selected = this.cbo_NVDoiSoat.items[0].nhanvien_id
      if (this.cbo_ChuKy.items.length > 0) this.cbo_ChuKy.selected = this.cbo_ChuKy.items[0].vid
      if (this.cbo_PLDACapDong.items.length > 0) this.cbo_PLDACapDong.selected = this.cbo_PLDACapDong.items[0].vid
      if (this.cbo_PLDACapQuang.items.length > 0) this.cbo_PLDACapQuang.selected = this.cbo_PLDACapQuang.items[0].vid
      if (this.cbo_ChuQuan.items.length > 0) this.cbo_ChuQuan.selected = this.cbo_ChuQuan.items[0].id
    },

    async getDanhMucHopDongDauTu() {
      try {
        this.loading(true)
        let chuoi = 'NHOM_DUAN|TOANHA|HINHTHUC_HT|HINHTHUC_PCDT|HINHTHUC_DAUTU|TINHTRANG|NGANHANG|NHANVIEN_QL|NHANVIEN_DOISOAT|CHUKY_TT|PLDA_DONG|PLDA_QUANG'
        let url = `/web-toanha/danhmuc-hopdong-dautu/thamso-danhmuc-v2?chuoi=${chuoi.replaceAll('|', '%7C')}&phanvung_id=${this.tinh ?? 0}`
        let response = await this.$root.context.get(url)
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          this.cbo_NhomDA.items = response.data.nhom_da ? response.data.nhom_da : []
          this.cbo_ToaNha.items = response.data.toanha ? response.data.toanha : []
          this.cbo_HinhThucHoaDon.items = response.data.hinhthuc_ht ? response.data.hinhthuc_ht : []
          this.cbo_HinhThucPCDT.items = response.data.hinhthuc_pcdt ? response.data.hinhthuc_pcdt : []
          this.cbo_HinhThucDauTu.items = response.data.hinhthuc_dautu ? response.data.hinhthuc_dautu : []
          this.cbo_TinhTrang.items = response.data.tinhtrang ? response.data.tinhtrang : []
          this.cbo_NganHang.items = response.data.nganhang ? response.data.nganhang : []
          this.cbo_NVQuanLy.items = response.data.nhanvien_ql ? response.data.nhanvien_ql : []
          this.cbo_NVDoiSoat.items = response.data.nhanvien_doisoat ? response.data.nhanvien_doisoat : []
          this.cbo_ChuKy.items = response.data.chuky_tt ? response.data.chuky_tt : []
          this.cbo_PLDACapDong.items = response.data.plda_dong ? response.data.plda_dong : []
          this.cbo_PLDACapQuang.items = response.data.plda_quang ? response.data.plda_quang : []
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDsChuQuan() {
      try {
        this.loading(true)
        this.cbo_ChuQuan.items = []
        let url = `/web-toanha/danhmuc-hopdong-dautu/danhsach-chuquan`
        let response = await this.$root.context.get(url)
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          response.data.forEach((item) => {
            this.cbo_ChuQuan.items.push({ id: item.chuquan_id, text: item.ten_tat })
          })
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDsHopDongDauTuCha(hddtuId, toaNhaId) {
      try {
        this.loading(true)
        this.cbo_HopDongCha.items = []
        let url = `/web-toanha/danhmuc-hopdong-dautu/danhsach-hopdong-dautu-cha-v2?hddtuId=${hddtuId}&toaNhaId=${toaNhaId}&phanvung_id=${this.tinh ?? 0}`
        let response = await this.$root.context.get(url)
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          this.cbo_HopDongCha.items = response.data
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDsHopDongDauTu(chuKy) {
      try {
        this.loading(true)
        this.grid_HopDong.items = []
        let url = `/web-toanha/danhmuc-hopdong-dautu/danhsach-hopdong-dautu_v2`
        let response = await this.$root.context.post(url, { chuky: chuKy, phanvung_id: this.tinh ?? 0 })
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          this.grid_HopDong.items = response.data
          this.grid_HopDong.key++
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getKiemTraQuyenToaNha(kieu, hddtuId, toaNhaId) {
      try {
        let url = `/web-toanha/danhmuc-hopdong-dautu/kiemtra-quyen-toanha?chucNang=HD_DAUTU&kieu=${kieu}&id=${hddtuId}&ttvtId=${toaNhaId}`
        let response = await this.$root.context.get(url)
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          return response.data
        } else {
          return 'Có lỗi xảy ra khi gọi api kiểm tra quyền tòa nhà'
        }
      } catch (error) {
        console.log(error)
        return 'Có lỗi xảy ra khi gọi api kiểm tra quyền tòa nhà'
      }
    },

    async getKiemTraMST(mst) {
      try {
        let url = `/web-toanha/danhmuc-hopdong-dautu/kiemtra-mst?vmst=${mst}`
        let response = await this.$root.context.get(url)
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          if (response.data.startsWith('OK')) return true
          else {
            this.common_params.mst_ErrorMessage = response.data
            return false
          }
        } else {
          this.common_params.mst_ErrorMessage = 'Không kiểm tra được mã số thuế'
          return false
        }
      } catch (error) {
        console.log(error)
        this.common_params.mst_ErrorMessage = 'Không kiểm tra được mã số thuế'
        return false
      }
    },

    generateRequestBody(isUpdate = true) {
      let apiBody = {
        tenHddtu: this.txt_TenHDDT,
        ngayKy: this.date_NgayKy != null ? moment(this.date_NgayKy).format('DD/MM/YYYY') : null,
        soHD: this.txt_SoHD,
        thoiHan: this.date_ThoiHan != null ? moment(this.date_ThoiHan).format('DD/MM/YYYY') : null,
        thoiHanDoiTac: this.date_ThoiHan != null ? moment(this.date_ThoiHan).format('DD/MM/YYYY') : null,
        tienDo: this.txt_TienDo,
        ghiChu: this.txt_GhiChu,
        toaNhaId: this.cbo_ToaNha.selected,
        nhomDA: this.cbo_NhomDA.selected,
        tgHqdt: this.date_TGTinhHQDT != null ? moment(this.date_TGTinhHQDT).format('DD/MM/YYYY') : null,
        tongMucDtu: this.txt_TongMucDT,
        thiPhanToiThieu: this.txt_ThiPhanToiThieu,
        apruToiThieu: this.txt_APRUToiThieu,
        nguoiKy: this.txt_NguoiKy,
        chucVu: this.txt_ChucVu,
        giaTriTaiSan: this.txt_GiaTriTS,
        tyLePcdt: this.txt_TyLePCDT,
        trangThai: 1,
        ngayTT: this.date_NgayThanhToan != null ? moment(this.date_NgayThanhToan).format('DD/MM/YYYY') : null,
        nganHangId: this.cbo_NganHang.selected,
        mst: this.txt_MST,
        stk: this.txt_STK,
        nvDoiSoatId: this.cbo_NVDoiSoat.selected,
        nvQuanLyId: this.cbo_NVQuanLy.selected,
        htHoaDonId: this.cbo_HinhThucHoaDon.selected,
        htPcdtId: this.cbo_HinhThucPCDT.selected,
        htDauTuId: this.cbo_HinhThucDauTu.selected,
        tinhTrangId: this.cbo_TinhTrang.selected,
        chuKyTT: this.cbo_ChuKy.selected,
        ngayHieuLuc: this.date_NgayHieuLuc != null ? moment(this.date_NgayHieuLuc).format('DD/MM/YYYY') : null,
        pldaDong: this.cbo_PLDACapDong.selected,
        pldaQuang: this.cbo_PLDACapQuang.selected,
        goiCuocTichHop: this.chb_GoiCuocTichHop ? 1 : 0,
        dtpcCps: this.chb_DTPCTheoCuocPS ? 1 : 0,
        tenTK: this.txt_TenTK,
        tenBpds: this.txt_TenBPDS,
        emailBpds: this.txt_EmailBPDS,
        diaChiBpds: this.txt_DiaChiBPDS,
        sdtBpds: this.txt_SdtBPDS,
        hddtuChaId: this.cbo_HopDongCha.selected,
        chuQuanId: this.cbo_ChuQuan.selected,
        cpToiDaTslId: this.chb_ChiPhiToiDa ? 1 : 0,
        cpToiDaTsl: this.chb_ChiPhiToiDa ? (this.txt_ChiPhiTSL != null ? this.txt_ChiPhiTSL : 0) : 0,
        mangNoiBo: this.chb_MangNoiBo ? 1 : 0,
      }
      if (isUpdate) {
        apiBody.hddtuId = this.grid_HopDong.selected.hddtu_id
        apiBody.maHddtu = this.txt_MaHDDT
        if (!this.checkThangChot) {
          apiBody.chuky = 0
        } else {
          apiBody.chuky = parseInt(DateTimeLib.toDateDisplayWithFormat(this.thangChot.value, 'YYYYMM'))
        }
      }

      return apiBody
    },

    generateRequestBody2(isUpdate = true) {
      let apiBody = {
        TEN_HDDTU: this.txt_TenHDDT,
        NGAYKY: this.date_NgayKy != null ? moment(this.date_NgayKy).format('DD/MM/YYYY') : null,
        SO_HD: this.txt_SoHD,
        THOIHAN: this.date_ThoiHan != null ? moment(this.date_ThoiHan).format('DD/MM/YYYY') : null,
        THOIHAN_DOITAC: this.date_ThoiHanDoiTac != null ? moment(this.date_ThoiHanDoiTac).format('DD/MM/YYYY') : null,
        TIENDO: this.txt_TienDo,
        GHICHU: this.txt_GhiChu,
        TOANHA_ID: this.cbo_ToaNha.selected,
        NHOM_DA: this.cbo_NhomDA.selected,
        TG_HQDT: this.date_TGTinhHQDT != null ? moment(this.date_TGTinhHQDT).format('DD/MM/YYYY') : null,
        TONGMUC_DTU: this.txt_TongMucDT,
        THIPHAN_TOITHIEU: this.txt_ThiPhanToiThieu,
        APRU_TOITHIEU: this.txt_APRUToiThieu,
        NGUOIKY: this.txt_NguoiKy,
        CHUCVU: this.txt_ChucVu,
        GIATRI_TAISAN: this.txt_GiaTriTS,
        TYLE_PCDT: this.txt_TyLePCDT,
        TRANGTHAI: 1,
        NGAY_TT: this.date_NgayThanhToan != null ? moment(this.date_NgayThanhToan).format('DD/MM/YYYY') : null,
        NGANHANG_ID: this.cbo_NganHang.selected,
        MST: this.txt_MST,
        STK: this.txt_STK,
        NV_DOISOAT_ID: this.cbo_NVDoiSoat.selected,
        NV_QUANLY_ID: this.cbo_NVQuanLy.selected,
        HT_HOADON_ID: this.cbo_HinhThucHoaDon.selected,
        HT_PCDT_ID: this.cbo_HinhThucPCDT.selected,
        HT_DAUTU_ID: this.cbo_HinhThucDauTu.selected,
        TINHTRANG_ID: this.cbo_TinhTrang.selected,
        CHUKY_TT: this.cbo_ChuKy.selected,
        NGAY_HIEULUC: this.date_NgayHieuLuc != null ? moment(this.date_NgayHieuLuc).format('DD/MM/YYYY') : null,
        PLDA_DONG: this.cbo_PLDACapDong.selected,
        PLDA_QUANG: this.cbo_PLDACapQuang.selected,
        GOICUOC_TICHHOP: this.chb_GoiCuocTichHop ? 1 : 0,
        DTPC_CPS: this.chb_DTPCTheoCuocPS ? 1 : 0,
        TEN_TK: this.txt_TenTK,
        TEN_BPDS: this.txt_TenBPDS,
        EMAIL_BPDS: this.txt_EmailBPDS,
        DIACHI_BPDS: this.txt_DiaChiBPDS,
        SDT_BPDS: this.txt_SdtBPDS,
        HDDTU_CHA_ID: this.cbo_HopDongCha.selected,
        CHUQUAN_ID: this.cbo_ChuQuan.selected,
        CP_TOIDA_TSL_ID: this.chb_ChiPhiToiDa ? 1 : 0,
        CP_TOIDA_TSL: this.chb_ChiPhiToiDa ? (this.txt_ChiPhiTSL != null ? this.txt_ChiPhiTSL : 0) : 0,
        MANG_NOIBO: this.chb_MangNoiBo ? 1 : 0,
        NHANVIEN_ID: this.$root.token.getNhanVienID(),
        NGUOI_CN: this.$root.token.getUserName(),
        MAY_CN: this.$root.token.getMachine(),
        IP_CN: this.$root.token.getIP(),
        DONVI_CN: this.$root.token.getDonViID(),
      }
      if (Object.keys(this.$root.token.getMachine()).length === 0) {
        delete apiBody.MAY_CN
      }
      if (Object.keys(this.$root.token.getIP()).length === 0) {
        delete apiBody.IP_CN
      }
      if (isUpdate) {
        apiBody.HDDTU_ID = this.grid_HopDong.selected.hddtu_id
        apiBody.MA_HDDTU = this.txt_MaHDDT
        if (!this.checkThangChot) {
          apiBody.CHUKY = 0
        } else {
          apiBody.CHUKY = parseInt(DateTimeLib.toDateDisplayWithFormat(this.thangChot.value, 'YYYYMM'))
        }
      }

      return apiBody
    },

    async addHopDongDauTu() {
      try {
        this.loading(true)
        let apiBody = this.generateRequestBody(false)
        let url = `/web-toanha/danhmuc-hopdong-dautu/them-hopdong-dautu`
        let response = await this.$root.context.post(url, apiBody)
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          return response.data
        } else {
          this.$toast.error(response.data)
          return false
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
        return false
      } finally {
        this.loading(false)
      }
    },

    async addHopDongDauTu2() {
      try {
        this.loading(true)
        let apiBody = this.generateRequestBody2(false)
        const apiBodyString = JSON.stringify(apiBody)
        let url = `/web-toanha/danhmuc-hopdong-dautu/capnhat-hopdong-dautu-v2`
        let response = await this.$root.context.post(url, {
          p_kieu: 1,
          p_ds_para: apiBodyString
        })
        if (response.error_code == 'BSS-00000000' && response.data != undefined) {
            return response.data
        } else {
          this.$toast.error(response.data)
          return false
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
        return false
      } finally {
        this.loading(false)
      }
    },

    async updateHopDongDauTu() {
      try {
        this.loading(true)
        let apiBody = this.generateRequestBody(true)
        let url = `/web-toanha/danhmuc-hopdong-dautu/capnhat-hopdong-dautu`
        let response = await this.$root.context.post(url, apiBody)
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          return response.data
        } else {
          this.$toast.error(response.data)
          return false
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
        return false
      } finally {
        this.loading(false)
      }
    },

    async updateHopDongDauTu2() {
      try {
        this.loading(true)
        let apiBody = this.generateRequestBody2(true)
        const apiBodyString = JSON.stringify(apiBody)
        let url = `/web-toanha/danhmuc-hopdong-dautu/capnhat-hopdong-dautu-v2`
        let response = await this.$root.context.post(url, {
          p_kieu: 2,
          p_ds_para: apiBodyString
        })
        if (response.error_code == 'BSS-00000000' && response.data != undefined) {
          return response.data
        } else {
          this.$toast.error(response.data)
          return false
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
        return false
      } finally {
        this.loading(false)
      }
    },

    async updateTyLePCDT() {
      try {
        let chuky = 0
        if (this.checkThangChot) {
          chuky = parseInt(DateTimeLib.toDateDisplayWithFormat(this.thangChot.value, 'YYYYMM'))
        }
        let url = `/web-toanha/danhmuc-hopdong-dautu/capnhat-tyle-pcdt`
        let response = await this.$root.context.post(url, {
          hddtu_id: this.grid_HopDong.selected.hddtu_id,
          chuky: chuky,
          tyle_pcdt: this.txt_TyLePCDT
        })
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          return response.data.Result
        } else {
          this.$toast.error(response.data)
          return false
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
        return false
      }
    },

    async deleteHopDongDauTu(hddtuId, toaNhaId) {
      try {
        this.loading(true)
        let apiBody = {
          hddtuId: hddtuId,
          toaNhaId: toaNhaId
        }
        let url = `/web-toanha/danhmuc-hopdong-dautu/xoa-hopdong-dautu`
        let response = await this.$root.context.post(url, apiBody)
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          return response.data.startsWith('OK')
        } else {
          this.$toast.error(response.data)
          return false
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
        return false
      } finally {
        this.loading(false)
      }
    },

    async deleteHopDongDauTu2() {
      try {
        this.loading(true)
        let apiBody = this.generateRequestBody2(true)
        const apiBodyString = JSON.stringify(apiBody)
        let url = `/web-toanha/danhmuc-hopdong-dautu/capnhat-hopdong-dautu-v2`
        let response = await this.$root.context.post(url, {
          p_kieu: 3,
          p_ds_para: apiBodyString
        })
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          return JSON.parse(response.data).KETQUA == 1
        } else {
          this.$toast.error(response.data)
          return false
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
        return false
      } finally {
        this.loading(false)
      }1
    },
    btnCapNhatTyLe_Click: async function (args) {
      this.$bvModal
        .msgBoxConfirm('Bạn có muốn cập nhật lại tỷ lệ PCDT trong chi tiết hợp đồng đầu tư?', {
          title: 'Thông báo',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
          centered: true
        })
        .then(async (v) => {
          if (v) {
            try {
              this.loading(true)
              let r = await this.updateTyLePCDT()
              if (r) this.$root.toastSuccess('Cập nhật lại tỷ lệ PCDT trong chi tiết hợp đồng đầu tư thành công!')
              else this.$root.toastError('Cập nhật lại tỷ lệ PCDT trong chi tiết hợp đồng đầu tư thất bại!')
            } catch (error) {
              console.log(error)
              this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
            } finally {
              this.loading(false)
            }
          }
        })
    },
    onChapNhanChayLaiDTPC: async function () {
      //
    },
    isValidDate(date) {
      if (this.date != null && this.date.toString().trim() !== '') {
        if (this.date.toString().trim().length !== 10) {
          return false
        }
        let date = this.date.toString().trim().split('/')
        if (date.length !== 3) {
          return false
        }
        if (date[0].length !== 2 || date[1].length !== 2 || date[2].length !== 4) {
          return false
        }
        if (isNaN(date[0]) || isNaN(date[1]) || isNaN(date[2])) {
          return false
        }
        if (parseInt(date[0]) < 1 || parseInt(date[0]) > 31) {
          return false
        }
        if (parseInt(date[1]) < 1 || parseInt(date[1]) > 12) {
          return false
        }
      }
      return true;
    }
  },
  async mounted() {
    this.checkShowModal()
  },
  watch: {
    'cbo_ToaNha.selected': async function (newVal, oldVal) {
      if (newVal) {
        this.common_params.toaNhaId = newVal
        await this.getDsHopDongDauTuCha(this.common_params.hddtuId, newVal)
      }
    }
  }
}
</script>
