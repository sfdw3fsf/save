<template src="./template.html"></template>
<style src="./style.scss" lang="scss" scope></style>

<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import VueDateExt from '../DeclareLandline/components/VueDateExt'
import * as moment from 'moment'
import api from './API.js'
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue'
import InBienBan from '@/modules/print/InBienBan/InBienBan.vue'
import DownloadFileNghiemThu from './popups/DownloadFileNghiemThu.vue'

export default {
  components: {
    breadcrumb,
    VueDateExt,
    GiaoPhieuView,
    InBienBan,
    DownloadFileNghiemThu
  },
  data() {
    return {
      datetimeFormat: 'dd/MM/yyyy HH:mm:ss',
      momentFormat: {
        stringify: (date) => {
          return date ? moment(date).format('DD/MM/YYYY hh:mm:ss') : ''
        },
        parse: (value) => {
          return value ? moment(value, 'DD/MM/YYYY hh:mm:ss').toDate() : null
        }
      },
      dhThiCong: true,
      luongThaoTac: [],
      tbNhanvien_data: [],
      tbDanhSachThueBao_data: [],
      tbTTTC_columns: [
        { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, allowSorting: true },
        { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, allowSorting: true },
        { fieldName: 'noidung', headerText: 'Nội dung', allowFiltering: true, allowSorting: true },
        { fieldName: 'donvi_cn', headerText: 'Đơn vị', allowFiltering: true, allowSorting: true },
        {
          fieldName: 'sms',
          headerText: 'SMS',
          allowFiltering: true,
          allowSorting: true,
          template: function() {
            return {
              template: {
                template: `<button class='btn btn-primary w40' @click="btnTTTC_Click">sms</button>`,
                methods: {
                  btnTTTC_Click: function(e) {
                    this.$bvModal.msgBoxConfirm(`Bạn không thể thao tác trên tiến trình của người khác !`, {
                      title: 'Thông báo',
                      size: 'lg',
                      okTitle: 'Đồng ý',
                      centered: true
                    }).then((value) => {
                      return
                    })
                  }
                }
              }
            }
          },
          width: 65,
          textAlign: 'center'
        },
        {
          fieldName: 'sua',
          headerText: 'Sửa',
          allowFiltering: true,
          allowSorting: true,
          template: function() {
            return {
              template: {
                template: `<button class='btn btn-primary w40' @click="btnTTTC_Click"><span class="icon one-edit" ></span></button>`,
                methods: {
                  btnTTTC_Click: function(e) {
                    this.$bvModal.msgBoxConfirm(`Bạn không thể thao tác trên tiến trình của người khác !`, {
                      title: 'Thông báo',
                      size: 'lg',
                      okTitle: 'Đồng ý',
                      centered: true
                    }).then((value) => {
                      return
                    })
                  }
                }
              }
            }
          },
          width: 65,
          textAlign: 'center'
        },
        {
          fieldName: 'xoa',
          headerText: 'Xoá',
          allowFiltering: true,
          allowSorting: true,
          template: function() {
            return {
              template: {
                template: `<button class='btn btn-primary w40' @click="btnTTTC_Click"><span class="icon one-trash" ></span></button>`,
                methods: {
                  btnTTTC_Click: function(e) {
                    this.$bvModal.msgBoxConfirm(`Bạn không thể thao tác trên tiến trình của người khác !`, {
                      title: 'Thông báo',
                      size: 'lg',
                      okTitle: 'Đồng ý',
                      centered: true
                    }).then((value) => {
                      return
                    })
                  }
                }
              }
            }
          },
          width: 65,
          textAlign: 'center'
        }
      ],
      tbChiTietDHTC_data: [],
      position: { X: 'center', Y: 'top' },
      dialogEvts: {
        close: this.dlgClose
      },
      animationSettings: { effect: 'None' },
      dialogComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed
      },
      tbTTTC_data: [],
      current_phieu_id: null,
      xuatPhieuShow: false,
      tthd_id: 4,
      dsloaihd_id: '1',
      dsdichvuvt_id: '9',
      isHenLD: true,
      ttKyThuat: '',
      donvi_id: null,
      phanvung_id: null,
      lay_phieu_con: false,
      isDisplayMenu: false,
      dsDoiTac_options: [],
      may_cn: '',
      username: '',
      ip: '',
      tinh_id: 100,
      tocDoKenh_options: [],
      loaiHinh_options: [],
      quyTrinh_options: [],
      dsNguoiGV: [],
      dsChuQuan: [],
      luong_id: null,
      modelIn: {},
      huonggiao_id: null,
      nhanvien_id: null,
      state: {},
      params: {
        kenhThu: '',
        dsKenhThu_options: [],
        kieuHD: '',
        dsKieuHD_options: [],
        loaiHD: null,
        dsLoaiHD_options: [],
        loai_id: 0,
        maGD: ''
      },
      clientInfo: {
        maTB: '',
        tenKieuLD: '',
        tenTB: '',
        diaChiTB: '',
        tocDoKenh: null,
        chuQuan: null,
        isDoiTac: true,
        loaiHinh: null,
        doiTac: null,
        dcDau: '',
        dcCuoi: '',
        quyTrinh: null
      },
      gvInfo: {
        dtpNgayGiao: '',
        dtpNgayHT: '',
        nguoiGV: null,
        nvYC: '',
        ndGiao: '',
        ndTH: '',
        lydoTra: '',
        ndTra: ''
      },
      giaoPhieuViewDialog: {
        isVisiable: false,
        input: {
          vhuonggiao_id: null,
          vloai_hd: null,
          vdichvuvt_id: null,
          vma_gd: null,
          vquytrinh: null,
          vtrangthaiphieu: 1
        }
      },
      form_events: {
        isEnterMaGD: false
      },
      controls: {
        btnHuy_HC: {
          enabled: false
        },
        btnGiaoPhieu: {
          enabled: true
        },
        btnGiaoViec: {
          enabled: true
        },
        btnHoanThanh: {
          enabled: true
        },
        btnCapNhat: {
          enabled: true
        },
        dtpNgayHT: {
          enabled: false
        },
        chkNgayHT: {
          checked: false,
          enabled: false
        }
      }
    }
  },
  methods: {
    clearForm() {
      this.clientInfo.maTB = ''
      this.clientInfo.maGD = ''
      this.clientInfo.tenKieuLD = ''
      this.clientInfo.tenTB = ''
      this.clientInfo.diaChiTB = ''
      this.clientInfo.dcDau = ''
      this.clientInfo.dcCuoi = ''
      this.clientInfo.loaiHinh = null
      this.clientInfo.tocDoKenh = null
      this.clientInfo.chuQuan = null
      this.clientInfo.isDoiTac = false
      this.clientInfo.doiTac = null
      this.gvInfo.dtpNgayGiao = ''
      this.gvInfo.dtpNgayHT = ''
      this.gvInfo.nguoiGV = null
      this.gvInfo.nvYC = ''
      this.gvInfo.ndGiao = ''
      this.gvInfo.ndTH = ''
      this.gvInfo.lydoTra = ''
      this.gvInfo.ndTra = ''
      this.tbNhanvien_data = []
      this.ttKyThuat = ''
      this.tbChiTietDHTC_data = []
      this.current_phieu_id = null
      this.tbTTTC_data = []
      this.isHenLD = false
    },
    async dlgClose(e) {
      try {
        this.loading(true)
        // console.log('dlgClose', e)
        this.tbNhanvien_data = []
        let res = await api.getDSNhanVienTheoPhieuID(this.axios, { phieu_id: this.current_phieu_id })
        if (res.data.error_code == 'BSS-00000000') {
          this.tbNhanvien_data = res.data.data
        } else {
        }
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    async popupClosed(val) {
      // console.log('popupClosed', val)
    },
    enterMaGD() {
      if (!this.form_events.isEnterMaGD) this.form_events.isEnterMaGD = true
    },
    async loadComboBox() {
      try {
        this.loading(true)
        //get tag
        const tag = this.$route.query.tag
        if (tag) {
          let words = tag.split('+')
          if (words.length >= 1) this.tthd_id = Number(words[0])
          if (words.length >= 2) this.dsloaihd_id = words[1].replaceAll(';', ',')
          if (words.length >= 3) this.dsdichvuvt_id = words[2].replaceAll(';', ',')
          if (words.length >= 4 && words[3] == '1') this.lay_phieu_con = true
        } else {
          this.tthd_id = 4
          this.dsloaihd_id = '1'
          this.dsdichvuvt_id = '1'
        }
        let res
        //lấy danh sách kênh thu
        res = await api.getDSKenhThu(this.axios)
        if (res.data.error_code == 'BSS-00000000') {
          this.params.dsKenhThu_options = res.data.data.map((item) => {
            return {
              id: item.KENHTHU_ID,
              text: item.KENHTHU
            }
          })
        } else {
          this.$toast.error(res.data.message)
        }
        //lấy danh sách kiểu hợp đồng
        res = await api.getDSKieuHD(this.axios)
        if (res.data.error_code == 'BSS-00000000') {
          this.params.dsKieuHD_options = res.data.data.map((item) => {
            return {
              id: item.KIEUHD_ID,
              text: item.KIEU_HD
            }
          })
        } else {
          this.$toast.error(res.data.message)
        }
        //lấy danh sách loại hợp đồng
        res = await api.getDSLoaiHD(this.axios)
        if (res.data.error_code == 'BSS-00000000') {
          this.params.dsLoaiHD_options = res.data.data
            .filter((item) => this.dsloaihd_id.split(',').includes(item.LOAIHD_ID.toString()))
            .map((item) => {
              return {
                id: item.LOAIHD_ID,
                text: item.TEN_LOAIHD
              }
            })
          this.params.loaiHD = this.params.dsLoaiHD_options[0].id
        } else {
          this.$toast.error(res.data.message)
        }
        //lấy danh sách quy trình
        this.getDSQuyTrinh()
        //lấy danh sách tốc độ kênh
        res = await api.getTocDoKenh(this.axios, {})
        if (res.data.error_code == 'BSS-00000000') {
          this.tocDoKenh_options = res.data.data.map((item) => {
            return {
              id: item.tocdo_id,
              text: item.toc_do
            }
          })
        } else {
          this.$toast.error(res.data.message)
        }
        //lấy danh sách loại hình
        res = await api.getDSLoaiHinh(this.axios)
        if (res.data.error_code == 'BSS-00000000') {
          this.loaiHinh_options = res.data.data
            .filter((item) => this.dsdichvuvt_id.split(',').includes(item.DICHVUVT_ID.toString()))
            .map((item) => {
              return {
                id: item.LOAITB_ID,
                text: item.LOAIHINH_TB
              }
            })
        } else {
          this.$toast.error(res.data.message)
        }
        //lấy ds người GV
        if (!this.donvi_id) this.donvi_id = (await this.$root.token.getDonViID()) * 1
        if (!this.phanvung_id) this.phanvung_id = (await this.$root.token.getPhanVungID()) * 1
        res = await api.getDSNguoiGV(this.axios, { donvi_id: this.donvi_id, phanvung_id: this.phanvung_id })
        // // console.log(res.data);
        if (res.data.error_code == 'BSS-00000000') {
          this.dsNguoiGV = res.data.data.map((item) => {
            return {
              id: item.nhanvien_id,
              text: item.ten_nv
            }
          })
        } else {
          this.$toast.error(res.data.message)
        }
        //lấy ds chủ quản
        res = await api.getDSChuQuan(this.axios)
        if (res.data.error_code == 'BSS-00000000') {
          this.dsChuQuan = res.data.data.map((item) => {
            return {
              id: item.CHUQUAN_ID,
              text: item.TENCHUQUAN
            }
          })
        } else {
          this.$toast.error(res.data.message)
        }
        //lấy ds đối tác
        res = await api.getDSDoiTac(this.axios, {})
        if (res.data.error_code == 'BSS-00000000') {
          this.dsDoiTac_options = res.data.data.map((item) => {
            return {
              id: item.doitac_id,
              text: item.ten_dt
            }
          })
        } else {
          this.$toast.error(res.data.message)
        }
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    async loadDgvDanhSachThueBao(loaiID, phieuID) {
      try {
        this.loading(true)
        this.tbDanhSachThueBao_data = []
        this.state = {}
        // // console.log(this.params.dsLoaiHD_options);
        let maGD = ''
        if (this.form_events.isEnterMaGD) maGD = this.params.maGD
        let res = await api.getDgvDanhSachThueBao(this.axios, {
          vdichvuvt_id: this.dsdichvuvt_id,
          vtthd_id: this.tthd_id,
          vhuonggiao_id: 0,
          vnhanvien_id: this.$root.token.getNhanVienID(),
          vma_gd: '',
          vphieu_id: 0,
          vloaihd_id: Number(this.params.loaiHD),
          vloai_id: loaiID
        })
        if (res.data.error_code == 'BSS-00000000') {
          this.tbDanhSachThueBao_data = res.data.data.map((x) => {
            return {
              ...x,
              loai_hd: this.params.dsLoaiHD_options.find((y) => y.id == x.loaihd_id).text,
              ngay_yc: moment(x.ngay_yc).format('DD/MM/yyyy'),
              ngaygiao1: moment(x.ngaygiao, 'DD/MM/yyyy hh:mm:ss').format('DD/MM/yyyy')
            }
          })
        } else {
          this.$toast.error(res.data.message)
        }
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    loadDataDSThueBao() {
      this.loadDgvDanhSachThueBao(this.params.loai_id, 0)
    },
    uploadFileNT_showDialog(){
      if(!this.state.hdtb_id || !this.clientInfo.maTB.trim())
        {
          this.$toast.error('Không có thông tin thuê bao')
          return
        }
        this.$refs.uploadFileNT.click();
    },
    async uploadFileNT_OnChange(event){
      // if(event.target.files.length==0) return
      try {
        this.loading(true)
        //upload file
        // console.log(event.target.files.length)
        let formData = new FormData();
        const files = event.target.files;
        for(let i = 0;i<files.length;i++){
          formData.append('files',files[i])
        }
        let res = await api.uploadFile(this.axios,formData);
        if(res.data.error_code != "BSS-00000000")
        {
          this.$toast.error("Xảy ra lỗi khi upload file: "+res.data.message)
          return
        }
        const urls = res.data.data;
        const dt = [];
        for(let i = 0;i<urls.length;i++){
          dt.push({
            URL: urls[i],
            FILE_NAME: files[i].name,
            MAY_CN: this.may_cn,
            NGUOI_CN: this.username,
            THUEBAO_ID: this.state.hdtb_id,
            THANHTOAN_ID: this.state.thanhtoan_id,
            MA_TB: this.clientInfo.maTB,
            HDTB_ID: this.state.hdtb_id,
          })
        }
        res = await api.uploadFileNT(this.axios,{
          p_ds: JSON.stringify(dt)
        });
        if(res.data.error_code != "BSS-00000000")
        {
          this.$toast.error("Xảy ra lỗi khi upload file: "+res.data.message)
          return
        }
        this.$toast.success("Upload file thành công")
      } catch (error) {
        
      }
      finally{
        this.loading(false)
      }
    },
    async tbDanhSachThueBao_rowSelected(row) {
      try {
        this.loading(true)
        this.clearForm()
        this.state = row.data
        const rowDT = row.data
        let res = await api.getThongTinKyThuat(this.axios, { vphanvung_id: this.phanvung_id, vhdtb_id: rowDT.hdtb_id })
        // console.log(res.data)
        this.ttKyThuat = ''
        res.data.data.forEach((item) => {
          this.ttKyThuat += `<b>${item.ten_truong}</b>: ${item.giatri ? item.giatri: ''}<br/>`
        })
        if (rowDT.ghichu) this.ttKyThuat += `<b>Ghi chú</b>: ${rowDT.ghichu}<br/>`
        this.params.maGD = rowDT.ma_gd
        this.clientInfo.maTB = rowDT.ma_tb
        this.clientInfo.tenKieuLD = rowDT.ten_kieuld
        this.clientInfo.tenTB = rowDT.ten_tb
        this.clientInfo.diaChiTB = rowDT.diachi_tb
        this.clientInfo.loaiHinh = rowDT.loaitb_id
        this.params.loaiHD = rowDT.loaihd_id
        this.gvInfo.ndGiao = rowDT.nd_giao || ''
        this.gvInfo.ndTH = rowDT.nd_thuchien || ''
        this.current_phieu_id = rowDT.phieu_id
        res = await api.getThongTinTB(this.axios, rowDT.hdtb_id)
        if (res.data.error_code == 'BSS-00000000') {
          this.clientInfo.tocDoKenh = res.data.data[0].tocdo_id
          this.clientInfo.chuQuan = res.data.data[0].chuquan_id
          this.clientInfo.dcDau = res.data.data[0].diachi
          this.clientInfo.dcCuoi = res.data.data[res.data.data.length - 1].diachi
          if (!!res.data.data[0].doitac_id) {
            this.clientInfo.isDoiTac = true
            this.clientInfo.doiTac = res.data.data[0].doitac_id
          } else {
            this.clientInfo.isDoiTac = false
            this.clientInfo.doiTac = null
          }
        } else this.$toast.error('Lỗi khi lấy thông tin HĐTB')
        this.tbNhanvien_data = []
        res = await api.getDSNhanVienTheoPhieuID(this.axios, { phieu_id: rowDT.phieu_id })
        if (res.data.error_code == 'BSS-00000000') {
          this.tbNhanvien_data = res.data.data
        } else {
        }
        this.gvInfo.dtpNgayGiao = rowDT.ngaygiao
        this.gvInfo.dtpNgayHT = rowDT.ngay_ht_gp
        if (rowDT.ngay_ht_gp) {
          this.controls.dtpNgayHT.enabled = true
          this.controls.chkNgayHT.checked = true
          this.controls.chkNgayHT.enabled = true
        } else {
          this.controls.chkNgayHT.checked = false
          this.controls.chkNgayHT.enabled = true
          this.controls.dtpNgayHT.enabled = true
        }
        this.gvInfo.nguoiGV = rowDT.nhanvien_id
        res = await api.getThongTinNV(this.axios, rowDT.nhanvien_id)
        if (res.data.error_code == 'BSS-00000000') {
          this.gvInfo.nvYC = res.data.data[0].NAME
          // console.log(this.gvInfo.nvYC)
        }
        res = await api.getThongTinPhieuTra(this.axios, { phieu_id: rowDT.phieu_id, ds_daucuoi_id: '0,1' })
        res = await api.getThongTinHen(this.axios, { hdtb_id: rowDT.hdtb_id })
        if (res.data.error_code == 'BSS-00000000') {
          this.isHenLD = res.data.data[0].c == 0 ? false : true
        }
        this.tbChiTietDHTC_data = []
        res = await api.getDSChiTietDHTC(this.axios, { vphieu_cha_id: rowDT.phieu_id })
        if (res.data.error_code == 'BSS-00000000') {
          this.tbChiTietDHTC_data = res.data.data
        }
        res = await api.getDSTienTinhDHTC(this.axios, { vhdtb_id: rowDT.hdtb_id, vphanvung_id: this.phanvung_id })
        if (res.data.data.length) this.tbTTTC_data = res.data.data
        // console.log(rowDT.hdtb_id);
        //delete after QC 
        else if(rowDT.hdtb_id == 599892) 
        {
          res = await api.getDSTienTinhDHTC(this.axios, { vhdtb_id: 634438, vphanvung_id: this.phanvung_id })
          this.tbTTTC_data = res.data.data
        }

        if (this.params.loai_id != 2) {
          this.controls.btnHoanThanh.enabled = true
          this.controls.btnGiaoViec.enabled = true
          this.controls.btnCapNhat.enabled = true
        }

        if (this.luongThaoTac.filter((x) => x.code == 'CAPNHAT_THONGSO_KYTHUAT').length && this.luongThaoTac.filter((x) => x.code == 'CAPNHAT_THONGSO_KYTHUAT')[0].enable == 1) {
          if (rowDT.ngay_ht_gp) {
            if (this.luongThaoTac.filter((x) => x.code == 'GIAOPHIEU').length && this.luongThaoTac.filter((x) => x.code == 'GIAOPHIEU')[0].enable == 1) this.controls.btnGiaoPhieu.enabled = true
            else this.controls.btnGiaoPhieu.enabled = false
          } else this.controls.btnGiaoPhieu.enabled = false
        }

        if (this.luongThaoTac.filter((x) => x.code == 'CAPNHAT_CAP_TSL').length && this.luongThaoTac.filter((x) => x.code == 'CAPNHAT_CAP_TSL')[0].enable == 1) {
          if (rowDT.ngay_ht_gp) {
            if (this.luongThaoTac.filter((x) => x.code == 'GIAOPHIEU').length && this.luongThaoTac.filter((x) => x.code == 'GIAOPHIEU')[0].enable == 1) this.controls.btnGiaoPhieu.enabled = true
            else this.controls.btnGiaoPhieu.enabled = false

            if (this.luongThaoTac.filter((x) => x.code == 'HOANTHANH_PHIEU').length && this.luongThaoTac.filter((x) => x.code == 'HOANTHANH_PHIEU')[0].enable == 1) this.controls.btnHoanThanh.enabled = true
            else this.controls.btnHoanThanh.enabled = false
          } else {
            this.controls.btnHoanThanh.enabled = false
            this.controls.btnGiaoPhieu.enabled = false
          }
        }

        if (this.params.loai_id == 2) {
          this.controls.btnGiaoViec.enabled = false
          // tsbtnTraPhieu.Enabled = false
          this.controls.btnGiaoPhieu.enabled = true
          this.controls.btnHoanThanh.enabled = false
          this.controls.btnCapNhat.enabled = false
        }

        // console.log(rowDT.ngay_ht_gp)
        if (rowDT.ngay_ht_gp) {
          this.controls.btnHoanThanh.enabled = true
          this.controls.btnGiaoPhieu.enabled = true
        } else this.controls.btnGiaoPhieu.enabled = false
      } catch (error) {
        // console.log(error)
      } finally {
        this.loading(false)
      }
    },
    async cboLoaiHDChange() {
      try {
        this.loading(true)
        await this.getDSQuyTrinh()
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    async btnGiaoViec_Click() {
      if (!this.current_phieu_id || this.current_phieu_id <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu. Hãy kiểm tra lại!')
        return
      }
      if (this.gvInfo.nguoiGV == null) {
        this.$toast.error('Bạn chưa chọn người giao việc. Hãy kiểm tra lại!')
        return
      }
      let vnhanvien_giao_id = Number(this.state.nhanvien_id)
      let vngaygiao = moment(this.state.ngaygiao).format('DD/MM/yyyy HH:mm:ss')

      this.state.modalTitle = 'Giao phiếu nhân viên'
      this.dialogComponent = () => import('@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV')
      this.popupComponentName = 'popupfrnGiaoPhieuNV'
      // this.popupComponentEvts["giaoviec_success"] =  this.giaoviec_success()
      await this.dialogComponent().then((x) => {})
      this.$nextTick(() => {
        this.$refs.generalComponent.frnGiaoPhieuNV(this.state.phieu_id, this.$root.token.getNhanVienID(), vnhanvien_giao_id, vngaygiao)
        this.$refs.generalComponent.vma_tb = this.state.ma_tb
        this.$refs.generalComponent.vhdtb_id = this.state.hdtb_id
        this.$refs.generalComponent.huonggiao_id = this.state.huonggiao_id

        this.$nextTick(() => {
          this.$refs.generalComponent.load()
          this.$refs.generalDialog.show()
        })
      })
    },
    async tsbtnGiaoPhieu_Click(e) {
      if (!this.current_phieu_id || this.current_phieu_id <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu. Hãy kiểm tra lại!')
        return
      }
      if (this.params.maGD == '') {
        this.$toast.error('Mã giao dịch trống. Hãy kiểm tra lại!')
        return
      }
      let huonggiao = await api.getHuonggiaoTheoLuongID(this.axios, { luong_id: this.luong_id })
      huonggiao = huonggiao.data.data[0]
      if (!huonggiao) {
        this.$toast.error('Không tìm thấy hướng giao')
        return
      }
      this.giaoPhieuViewDialog.input = {
        vhuonggiao_id: huonggiao.huonggiao_id * 1,
        vloai_hd: this.params.loaiHD,
        vdichvuvt_id: this.dsdichvuvt_id,
        vma_gd: this.params.maGD,
        vquytrinh: this.clientInfo.quyTrinh,
        vtrangthaiphieu: 1
      }
      this.GiaoPhieuView_OnClick()
    },
    async btnPhieuTC_Click() {
      console.log('btnPhieuTC_Click');
      let selectedRows = this.$refs.dgvHDTB.getSelectedRecords()
      if (!selectedRows || selectedRows.length == 0) {
        this.$toast.error('Bạn chưa chọn phiếu!')
        return
      }
      let res = await api.getDSHopDongTBTheoHDTBID(this.axios, { hdtb_id: this.state.hdtb_id })
      // console.log(res.data.data[0]);
      // let nhdkhid = []
      //       let nhdtbid = []
      //       let nphieuid = []
      //       nhdkhid = selectedRows.map((item) => item.hdkh_id)
      //       nhdtbid = selectedRows.map((item) => item.hdtb_id)
      //       nphieuid = selectedRows.map((item) => item.phieu_id)

      //       this.modelIn.n_phieu_id = nphieuid.join(',')
      //       this.modelIn.ma_gd = this.params.maGD;
      //       this.modelIn.n_hdkh_id = nhdkhid.join(',')
      //       this.modelIn.n_hdtb_id = nhdtbid.join(',')
      //       this.modelIn.nvth_id = -1
      //       this.modelIn.huonggiao_id = this.huonggiao_id
      //       this.modelIn.tentram_tb = ''
      //       // // console.log(this.modelIn)
      //       this.$refs.popupInBB.showModal()
    },
    async btnCapNhat_Click() {
      if (!this.state.phieu_id) {
        this.$toast.error('Bạn chưa chọn phiếu. Hãy kiểm tra lại!')
        return
      }
      let res = await api.kiemtraDL(this.axios, { vphieu_id: this.state.phieu_id })
      if (res.data.error_code != 'BSS-00000000') {
        this.$toast.error('Dữ liệu không đúng!')
        return
      }
      let ngay_gv = moment(this.gvInfo.dtpNgayGiao).format('DD/MM/yyyy')
      let ngay_bg = moment(this.gvInfo.dtpNgayHT).format('DD/MM/yyyy') 
      let data = [
        {
          HDTB_ID: this.state.hdtb_id,
          LUONG_ID: this.luong_id,
          PHIEU_ID: this.state.phieu_id,
          NHANVIEN_ID: this.nhanvien_id,
          NGUOIGV_ID: this.gvInfo.nguoiGV,
          NGAY_GV: ngay_gv,
          NGAY_BG: ngay_bg
        }
      ]
      try {
        this.loading(true)
        res = await api.capnhatPhieuGiao(this.axios, {
          vphanvung_id: this.phanvung_id,
          vds: JSON.stringify(data).toString()
        })
        if (res.data.data) {
          this.$toast.success('Cập nhật phiếu giao thành công!')
          this.loadDataDSThueBao()
        } else this.$toast.error('Xảy ra lỗi khi cập nhật')
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    excelExport() {
      this.$refs.dgvHDTB.excelExport()
      // console.log(this.$refs.dgvHDTB);
    },
    async btnHoanThanh_click() {
      let data = [
        {
          LUONG_ID: this.luong_id,
          HDTB_ID: this.state.hdtb_id,
          MA_TB: this.state.ma_tb,
          PHIEU_ID: this.state.phieu_id,
          NHANVIEN_ID: this.nhanvien_id,
          DONVI_ID: this.donvi_id,
          MAY_CN: this.may_cn,
          NGUOI_CN: this.username,
          IP_CN: this.ip,
          HUONGGIAO_ID: this.state.huonggiao_id,
          LOAIHD_ID: this.params.loaiHD,
          NGAY_BG: this.momentFormat.stringify(this.gvInfo.dtpNgayHT) || '',
          TINH_ID: this.state.tinh_id,
          PHIEU_TINH_ID: this.state.phieu_tinh_id,
          XULY: this.state.xuly,
          QUYTRINH_ID: this.state.quytrinh_id,
          MA_GD: this.state.ma_gd
        }
      ]
      let res = await api.hoanThanhHoanCong(this.axios, {
        vphanvung_id: this.phanvung_id,
        vkieu: 2,
        vds: JSON.stringify(data).toString()
      })
      if (res.data.data == 1) {
        this.$toast.success('Hoàn thành hoàn công thành công!')
        this.loadDataDSThueBao()
      } else this.$toast.error(res.data.data)
    },
    async GiaoPhieuView_OnClick() {
      this.giaoPhieuViewDialog.isVisiable = true
      this.$refs.GiaoPhieuView.show()
    },
    async GiaoPhieuView_OnClose() {
      this.giaoPhieuViewDialog.isVisiable = false
    },
    async btnDownload_click() {
      // let res = await api.downloadFileNT(this.axios, { vhdtb_id: this.state.hdtb_id, vphanvung_id: this.phanvung_id })
      // if(res.data.error_code =="BSS-00000204")
      // {
      //   this.$toast.error("Không có dữ liệu file !")
      //   return
      // }
      this.$refs.DownloadFileNghiemThu.showModal()
      // console.log(res);
    },
    async getDSQuyTrinh() {
      this.quyTrinh_options = []
      let res = await api.getDSQuyTrinh(this.axios, {
        dsloaihd_id: this.params.loaiHD,
        dichvuvt_id: this.dsdichvuvt_id,
        kieu: 0,
        tthd_id: this.tthd_id
      })
      if (res.data.error_code == 'BSS-00000000') {
        this.quyTrinh_options = res.data.data.map((item) => {
          return {
            id: item.quytrinh_id,
            text: item.quytrinh
          }
        })
      } else {
        this.quyTrinh_options = [
          {
            id: 0,
            text: '--Không có quy trình--'
          }
        ]
      }
      this.clientInfo.quyTrinh = this.quyTrinh_options[0].id
      // // console.log(this.clientInfo.quyTrinh);
    },
    async HienThiGiaoDien(luong_id) {
      let res = await api.getLuongThaoTac(this.axios, { luong_id: this.luong_id })
      this.luongThaoTac = res.data.data
    },
    async capNhatNDTH() {
      if (!this.current_phieu_id) return
      try {
        this.loading(true)
        let res = await api.updateNoiDungTH(this.axios, { phieu_id: this.current_phieu_id, noidung: this.gvInfo.ndTH })
        if (res.data.error_code == 'BSS-00000000') {
          this.$toast.success('Cập nhật nội dung thực hiện thành công!')
          await this.loadDgvDanhSachThueBao(this.params.loai_id, 0)
        } else {
          this.$toast.error(res.data.message)
        }
      } catch (error) {
        // console.log(error)
      } finally {
        this.loading(false)
      }
    },
    hideDropdownMenu(e) {
      if (e.target !== this.$refs.mainWrap) {
        this.isDisplayMenu = false
      }
      if (e.target !== this.$refs.xuatphieu) {
        this.xuatPhieuShow = false
      }
    },
    setDisplayDropDownMenu() {
      this.isDisplayMenu = !this.isDisplayMenu
    },
    async btnHuy_HC_Click() {
      if (this.tbDanhSachThueBao_data.length == 0) {
        this.$toast.error('Chưa có danh sách thuê bao. Hãy kiểm tra lại!')
        return
      }
      const isConfirm = await this.$bvModal.msgBoxConfirm(`Bạn thật sự muốn hủy hoàn công không ?`, {
        title: 'Thông báo',
        size: 'sm',
        okTitle: 'Đồng ý',
        cancelTitle: 'Hủy',
        centered: true
      })
      if (isConfirm) {
        let res = await api.updateTTPhieuGiao(this.axios, {
          phieu_id: this.current_phieu_id,
          ttph_id: 1,
          ngay_th: moment().format('DD/MM/YYYY HH:mm:ss')
        })
        if (res.data.error_code == 'BSS-00000000') {
          this.$toast.success('Hủy hoàn công thành công!')
          await this.loadDgvDanhSachThueBao(this.params.loai_id, 0)
        } else {
          this.$toast.error(res.data.message)
        }
      }
    }
  },
  async mounted() {
    this.donvi_id = this.$root.token.getDonViID() * 1
    this.phanvung_id = this.$root.token.getPhanVungID() * 1
    this.may_cn = await this.$root.token.getMachine()
    this.username = await this.$root.token.getUserName()
    this.nhanvien_id = await this.$root.token.getNhanVienID()
    this.tinh_id = await this.$root.token.getMaTinh()
    // console.log(this.tinh_id);
    this.ip = await this.$root.token.getIP()
    await this.loadComboBox()
    await this.loadDgvDanhSachThueBao(this.params.loai_id, 0)
    // // console.log(this.$refs.dsPhieuCon)
  },
  watch: {
    'params.loai_id': function(newVal, oldVal) {
      this.loadDgvDanhSachThueBao(newVal, 0)
      if (newVal == 2) {
        this.controls.btnHuy_HC.enabled = true
      } else {
        this.controls.btnHuy_HC.enabled = false
      }
    },
    'clientInfo.quyTrinh': async function(n, o) {
      let res = await api.getLuongTheoQuyTrinh(this.axios, { quytrinh_id: this.clientInfo.quyTrinh, tthd_id: this.tthd_id })
      this.luong_id = res.data.data[0].luong_id
      this.huonggiao_id = res.data.data[0].huonggiao_id
      this.HienThiGiaoDien(this.luong_id)
    }
  }
}
</script>
