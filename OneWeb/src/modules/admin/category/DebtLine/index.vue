<template src="./template.html"></template>
<script>
import api from './api'
import collums from './define/collunm.js'
import { TRANGTHAI_DONGBO } from '@/constants.js'
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
export default {
  data() {
    return {
      header: {
        title: 'QUẢN LÝ TUYẾN THU',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      txtTimKiem: '',
      ...collums,
      ckbTuyenThu: 1,
      donviql_id: 0,
      options: {
        donviQL: [],
        kieuTuyenthu: [],
        kieuThu: [],
        httt: [],
        dsTuyenthu: [],
        dsNap: [],
        tinh: [],
        quan: [],
        phuong: [],
        pho_chua_gan: [],
        pho_da_gan: [],
        ap_da_gan: [],
        khu_da_gan: [],
        ap_chua_gan: [],
        khu_chua_gan: [],
        dacdiem_chua_gan: [],
        dacdiem_da_gan: [],
        nhanvien_TC: []
      },
      model: {
        donvi: null,
        kieutuyenthu: null,
        tuyenthu: null,
        nhanvien_tc: null
      },
      timkiem: {
        tinh: 0,
        quan: null,
        phuong: null
      },
      tuyenthu: {
        donvi_id: null,
        donviql_id: null,
        ghichu: null,
        httt_id: null,
        kieu: null,
        kieuthu_id: null,
        ma_nvtc: null,
        ma_tuyen: '',
        may_cn: '',
        ngay_cn: '',
        nguoi_cn: '',
        nhanvien_id: null,
        phanvung_id: null,
        sudung: null,
        tainha: null,
        tentuyen: '',
        tuyenthu_id: null
      },
      button: {
        nhapmoi: false,
        ghilai: false,
        huy: false,
        xoa: false,
        napdl: true,
        xoanap: true
      }
    }
  },
  components: {
    breadcrumb
  },
  mounted() {
    let Tag = this.$route.query.tag

    let str = ''
    try {
      if (Tag) str = Tag
    } catch (err) {}
    //Hiển thị giao diện
    if (str != '') {
      this.$refs.menu.style.display = 'none'
      this.$refs.tsbtnNhapMoi.style.display = 'none'
      this.$refs.tsbtnGhiLai.style.display = 'none'
      this.$refs.tsbtnHuyBo.style.display = 'none'
      this.$refs.tsbtnNapDL.style.display = 'none'
      this.$refs.tsbtnXoa.style.display = 'none'
      this.$refs.tsbtnXoaDL_Nap.style.display = 'none'
    }

    this.SetButton(-1)
    this.init()
  },
  computed: {
    changeNV() {
      return this.donviql_id * this.tuyenthu.kieutuyenthu
    },
    huyenByTinh() {
      return this.options.quan.filter((item) => item.TINH_ID == this.timkiem.tinh)
    },
    dsTuyenthu() {
      if (this.ckbTuyenThu == 1)
        return this.options.dsTuyenthu
          .map((item) => ({
            ...item,
            ten_httt: this.options.httt.filter((ht) => ht.HTTT_ID == item.httt_id).length > 0 ? this.options.httt.filter((ht) => ht.HTTT_ID == item.httt_id)[0].HINHTHUC : ''
          }))
          .filter((item) => item.sudung != '0')
      else
        return this.options.dsTuyenthu.map((item) => ({
          ...item,
          ten_httt: this.options.httt.filter((ht) => ht.HTTT_ID == item.httt_id).length > 0 ? this.options.httt.filter((ht) => ht.HTTT_ID == item.httt_id)[0].HINHTHUC : ''
        }))
    },
    getTenPhuong() {
      return this.options.phuong.filter((item) => item.PHUONG_ID == this.timkiem.phuong).length > 0 ? this.options.phuong.filter((item) => item.PHUONG_ID == this.timkiem.phuong)[0].TEN_PHUONG : ''
    }
  },
  watch: {
    changeNV(val) {
      // this.getNVTC();
    },
    'tuyenthu.tuyenthu_id'(val) {
      if (val) {
        this.getDsachNap()
      }
    },
    async 'model.nhanvien_tc'(val) {
      this.clear()
      if (val) {
        let nhanvien = this.options.nhanvien_TC.filter((item) => item.nhanvien_id == val)
        if (nhanvien.length > 0) {
          this.tuyenthu.ma_nvtc = nhanvien[0].ma_nv
        }
        this.getDsachtuyenthu()
      }
    },
    'timkiem.tinh'(val) {
      this.resetDiadanh()
      this.timkiem.quan = this.huyenByTinh[0] ? this.huyenByTinh[0].QUAN_ID : null
      //this.getQuan();
    },
    'timkiem.quan'(val) {
      this.resetDiadanh()
      this.getPhuong()
    },
    'timkiem.phuong'(val) {
      this.resetDiadanh()
      if (val) {
        this.getPhochuagan()
      } else {
        this.options.pho_chua_gan = []
      }
    },
    'donviql_id'(val) {
      console.log('donviql_id')
      console.log(val)
      this.getNVTC()
    }
  },
  methods: {
    clear() {
      this.resetForm()
      this.resetDiadanh()
      this.options.dsTuyenthu = []
      this.options.dsNap = []
    },
    SetButton(kieu) {
      this.button.ghilai = false
      this.button.huy = false
      this.button.nhapmoi = false
      this.button.xoa = false

      if (kieu == -1) {
        //Bat dau
        this.button.ghilai = true
        this.button.huy = true
      }

      if (kieu == 0) {
        //Bat dau
        this.button.nhapmoi = true
        this.clear()
      }

      if (kieu == 1) {
        //Them moi
        this.button.ghilai = true
        this.button.huy = true
        this.clear()
      }

      if (kieu == 2) {
        //Huy
        this.button.nhapmoi = true
        this.button.xoa = true
        this.clear()
      }

      if (kieu == 3) {
        //Edit
        this.button.nhapmoi = true
        this.button.xoa = true
        this.button.ghilai = true
        this.button.huy = true
      }
    },
    xoanap() {
      let records = this.$refs.danh_sach_nap.getSelectedRecords()
      this.options.dsNap = this.options.dsNap.filter((item) => {
        return !records.includes(item)
      })
      this.loading(true)
      setTimeout(() => {
        this.loading(false)
      }, 500)
    },
    resetDiadanh() {
      this.options.khu_da_gan = []
      this.options.khu_chua_gan = []
      this.options.ap_da_gan = []
      this.options.ap_chua_gan = []
      this.options.pho_da_gan = []
      this.options.pho_chua_gan = []
      this.options.dacdiem_da_gan = []
      this.options.dacdiem_chua_gan = []
    },
    async xoa() {
      if (!this.button.xoa) {
        return false
      }
      this.$confirm(`Bạn có muốn xóa tuyến thu đang chọn không ?`, 'Giản đồ mạng cáp', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning'
      }).then(async () => {
        let row = this.$refs.danh_sach_tuyen_thu.getSelectedRecords()
        if (row.length < 0) {
          this.$toast.error('Bạn chưa chọn tuyến thu')
        }
        let check = await this.axios.post('web-hopdong/hopdong/get_app_config', {
          keyname: 'OPEN_SYNC_VINA_TRASAU'
        })
        if (check && check.data && check.data.data.keyvalue == 'TRUE') {
            let mabc_neo = await this.axios.post('web-quantri/tuyenthu/sp_lay_mabc_neo', {donvi_id : this.donviql_id})
          await api.dongbo_diadanh_ccbs(this.axios).then((response) => {
            if (response.data.error_code === 'BSS-00000000') {
              api
                .ts_danhmuc_delete_tuyenthus(this.axios, {
                  ghichu: '',
                  mabc: mabc_neo.data.data.mabc_id_neo,
                  tentuyen: this.tuyenthu.tentuyen,
                  matuyen: this.tuyenthu.ma_tuyen,
                  manv: this.$root.token.getMaNhanVien()
                })
                .then((response) => {
                  if (response.data.error_code === 'BSS-00000500') {
                    this.$toast.error('Lỗi khi xóa dữ liệu trên Vinaphone')
                  } else {
                  }
                })
            }
          })
        }
        try {
          let xoa = api.xoa_tuyenthu(this.axios, {
            tuyenthu_id: this.$refs.danh_sach_tuyen_thu.getSelectedRecords()[0].tuyenthu_id
          })
          this.$toast.success('Xóa thành công')
        } catch (e) {
          this.$toast.error(e.message)
        }
        await this.getDsachtuyenthu()
      })
    },
    napDl_click() {
      var dsNap = []
      let phuong = this.options.phuong.filter((item) => item.PHUONG_ID == this.timkiem.phuong)
      let quan = this.options.quan.filter((item) => item.QUAN_ID == this.timkiem.quan)
      var ten_phuong = phuong.length > 0 ? phuong[0].TEN_PHUONG : ''
      var ten_quan = quan.length > 0 ? quan[0].TEN_QUAN : ''

      if (this.options.pho_da_gan.length > 0) {
        this.options.pho_da_gan.forEach((pho) => {
          if (this.options.ap_da_gan.length > 0) {
            this.options.ap_da_gan.forEach((ap) => {
              if (this.options.khu_da_gan.length > 0) {
                this.options.khu_da_gan.forEach((khu) => {
                  if (this.options.dacdiem_da_gan.length > 0) {
                    this.options.dacdiem_da_gan.forEach((dacdiem) => {
                      dsNap.push({
                        ap_id: ap.PHO_ID,
                        dacdiem: dacdiem.DACDIEM,
                        dacdiem_id: dacdiem.DACDIEM_ID,
                        khu_id: khu.PHO_ID,
                        pho_id: pho.PHO_ID,
                        phuong_id: this.timkiem.phuong,
                        quan_id: this.timkiem.quan,
                        selected: 0,
                        ten_ap: ap.TEN_PHO,
                        ten_khu: khu.TEN_PHO,
                        ten_pho: pho.TEN_PHO,
                        ten_phuong: ten_phuong,
                        ten_quan: ten_quan
                      })
                    })
                  } else {
                    dsNap.push({
                      ap_id: ap.PHO_ID,
                      dacdiem: '',
                      dacdiem_id: 0,
                      khu_id: khu.PHO_ID,
                      pho_id: pho.PHO_ID,
                      phuong_id: this.timkiem.phuong,
                      quan_id: this.timkiem.quan,
                      selected: 0,
                      ten_ap: ap.TEN_PHO,
                      ten_khu: khu.TEN_PHO,
                      ten_pho: pho.TEN_PHO,
                      ten_phuong: ten_phuong,
                      ten_quan: ten_quan
                    })
                  }
                })
              } else {
                if (this.options.dacdiem_da_gan.length > 0) {
                  this.options.dacdiem_da_gan.forEach((dacdiem) => {
                    dsNap.push({
                      ap_id: ap.PHO_ID,
                      dacdiem: dacdiem.DACDIEM,
                      dacdiem_id: dacdiem.DACDIEM_ID,
                      khu_id: 0,
                      pho_id: pho.PHO_ID,
                      phuong_id: this.timkiem.phuong,
                      quan_id: this.timkiem.quan,
                      selected: 0,
                      ten_ap: ap.TEN_PHO,
                      ten_khu: '',
                      ten_pho: pho.TEN_PHO,
                      ten_phuong: ten_phuong,
                      ten_quan: ten_quan
                    })
                  })
                } else {
                  dsNap.push({
                    ap_id: ap.PHO_ID,
                    dacdiem: '',
                    dacdiem_id: 0,
                    khu_id: 0,
                    pho_id: pho.PHO_ID,
                    phuong_id: this.timkiem.phuong,
                    quan_id: this.timkiem.quan,
                    selected: 0,
                    ten_ap: ap.TEN_PHO,
                    ten_khu: '',
                    ten_pho: pho.TEN_PHO,
                    ten_phuong: ten_phuong,
                    ten_quan: ten_quan
                  })
                }
              }
            })
          } else {
            if (this.options.khu_da_gan.length > 0) {
                this.options.khu_da_gan.forEach((khu) => {
                  if (this.options.dacdiem_da_gan.length > 0) {
                    this.options.dacdiem_da_gan.forEach((dacdiem) => {
                      dsNap.push({
                        ap_id: 0,
                        dacdiem: dacdiem.DACDIEM,
                        dacdiem_id: dacdiem.DACDIEM_ID,
                        khu_id: khu.PHO_ID,
                        pho_id: pho.PHO_ID,
                        phuong_id: this.timkiem.phuong,
                        quan_id: this.timkiem.quan,
                        selected: 0,
                        ten_ap: "",
                        ten_khu: khu.TEN_PHO,
                        ten_pho: pho.TEN_PHO,
                        ten_phuong: ten_phuong,
                        ten_quan: ten_quan
                      })
                    })
                  } else {
                    dsNap.push({
                      ap_id: 0,
                      dacdiem: '',
                      dacdiem_id: 0,
                      khu_id: khu.PHO_ID,
                      pho_id: pho.PHO_ID,
                      phuong_id: this.timkiem.phuong,
                      quan_id: this.timkiem.quan,
                      selected: 0,
                      ten_ap: "",
                      ten_khu: khu.TEN_PHO,
                      ten_pho: pho.TEN_PHO,
                      ten_phuong: ten_phuong,
                      ten_quan: ten_quan
                    })
                  }
                })
              } else {
                if (this.options.dacdiem_da_gan.length > 0) {
                  this.options.dacdiem_da_gan.forEach((dacdiem) => {
                    dsNap.push({
                      ap_id: 0,
                      dacdiem: dacdiem.DACDIEM,
                      dacdiem_id: dacdiem.DACDIEM_ID,
                      khu_id: 0,
                      pho_id: pho.PHO_ID,
                      phuong_id: this.timkiem.phuong,
                      quan_id: this.timkiem.quan,
                      selected: 0,
                      ten_ap: "",
                      ten_khu: '',
                      ten_pho: pho.TEN_PHO,
                      ten_phuong: ten_phuong,
                      ten_quan: ten_quan
                    })
                  })
                } else {
                  dsNap.push({
                    ap_id: 0,
                    dacdiem: '',
                    dacdiem_id: 0,
                    khu_id: 0,
                    pho_id: pho.PHO_ID,
                    phuong_id: this.timkiem.phuong,
                    quan_id: this.timkiem.quan,
                    selected: 0,
                    ten_ap: "",
                    ten_khu: '',
                    ten_pho: pho.TEN_PHO,
                    ten_phuong: ten_phuong,
                    ten_quan: ten_quan
                  })
                }
              }
          }
        })
      } else {
        if (this.options.ap_da_gan.length > 0) {
          this.options.ap_da_gan.forEach((ap) => {
            if (this.options.khu_da_gan.length > 0) {
              this.options.khu_da_gan.forEach((khu) => {
                if (this.options.dacdiem_da_gan.length > 0) {
                  this.options.dacdiem_da_gan.forEach((dacdiem) => {
                    dsNap.push({
                      ap_id: ap.PHO_ID,
                      dacdiem: dacdiem.DACDIEM,
                      dacdiem_id: dacdiem.DACDIEM_ID,
                      khu_id: khu.PHO_ID,
                      pho_id: 0,
                      phuong_id: this.timkiem.phuong,
                      quan_id: this.timkiem.quan,
                      selected: 0,
                      ten_ap: ap.TEN_PHO,
                      ten_khu: khu.TEN_PHO,
                      ten_pho: '',
                      ten_phuong: ten_phuong,
                      ten_quan: ten_quan
                    })
                  })
                } else {
                  dsNap.push({
                    ap_id: ap.PHO_ID,
                    dacdiem: '',
                    dacdiem_id: 0,
                    khu_id: khu.PHO_ID,
                    pho_id: 0,
                    phuong_id: this.timkiem.phuong,
                    quan_id: this.timkiem.quan,
                    selected: 0,
                    ten_ap: ap.TEN_PHO,
                    ten_khu: khu.TEN_PHO,
                    ten_pho: '',
                    ten_phuong: ten_phuong,
                    ten_quan: ten_quan
                  })
                }
              })
            } else {
              if (this.options.dacdiem_da_gan.length > 0) {
                this.options.dacdiem_da_gan.forEach((dacdiem) => {
                  dsNap.push({
                    ap_id: ap.PHO_ID,
                    dacdiem: dacdiem.DACDIEM,
                    dacdiem_id: dacdiem.DACDIEM_ID,
                    khu_id: 0,
                    pho_id: 0,
                    phuong_id: this.timkiem.phuong,
                    quan_id: this.timkiem.quan,
                    selected: 0,
                    ten_ap: ap.TEN_PHO,
                    ten_khu: '',
                    ten_pho: '',
                    ten_phuong: ten_phuong,
                    ten_quan: ten_quan
                  })
                })
              } else {
                dsNap.push({
                  ap_id: ap.PHO_ID,
                  dacdiem: '',
                  dacdiem_id: 0,
                  khu_id: 0,
                  pho_id: 0,
                  phuong_id: this.timkiem.phuong,
                  quan_id: this.timkiem.quan,
                  selected: 0,
                  ten_ap: ap.TEN_PHO,
                  ten_khu: '',
                  ten_pho: '',
                  ten_phuong: ten_phuong,
                  ten_quan: ten_quan
                })

                console.log(dsNap)
              }
            }
          })
        } else {
          if (this.options.khu_da_gan.length > 0) {
            this.options.khu_da_gan.forEach((khu) => {
              if (this.options.dacdiem_da_gan.length > 0) {
                this.options.dacdiem_da_gan.forEach((dacdiem) => {
                  dsNap.push({
                    ap_id: 0,
                    dacdiem: dacdiem.DACDIEM,
                    dacdiem_id: dacdiem.DACDIEM_ID,
                    khu_id: khu.PHO_ID,
                    pho_id: 0,
                    phuong_id: this.timkiem.phuong,
                    quan_id: this.timkiem.quan,
                    selected: 0,
                    ten_ap: '',
                    ten_khu: khu.TEN_PHO,
                    ten_pho: pho.TEN_PHO,
                    ten_phuong: ten_phuong,
                    ten_quan: ten_quan
                  })
                })
              } else {
                dsNap.push({
                  ap_id: 0,
                  dacdiem: '',
                  dacdiem_id: 0,
                  khu_id: khu.PHO_ID,
                  pho_id: 0,
                  phuong_id: this.timkiem.phuong,
                  quan_id: this.timkiem.quan,
                  selected: 0,
                  ten_ap: '',
                  ten_khu: khu.TEN_PHO,
                  ten_pho: '',
                  ten_phuong: ten_phuong,
                  ten_quan: ten_quan
                })
              }
            })
          } else {
            if (this.options.dacdiem_da_gan.length > 0) {
              this.options.dacdiem_da_gan.forEach((dacdiem) => {
                dsNap.push({
                  ap_id: 0,
                  dacdiem: dacdiem.DACDIEM,
                  dacdiem_id: dacdiem.DACDIEM_ID,
                  khu_id: 0,
                  pho_id: 0,
                  phuong_id: this.timkiem.phuong,
                  quan_id: this.timkiem.quan,
                  selected: 0,
                  ten_ap: '',
                  ten_khu: '',
                  ten_pho: '',
                  ten_phuong: ten_phuong,
                  ten_quan: ten_quan
                })
              })
            } else {
              dsNap.push({
                ap_id: 0,
                dacdiem: '',
                dacdiem_id: 0,
                khu_id: 0,
                pho_id: 0,
                phuong_id: this.timkiem.phuong,
                quan_id: this.timkiem.quan,
                selected: 0,
                ten_ap: '',
                ten_khu: '',
                ten_pho: '',
                ten_phuong: ten_phuong,
                ten_quan: ten_quan
              })
            }
          }
        }
      }
      this.options.dsNap = this.options.dsNap.concat(dsNap)

      this.loading(true)
      setTimeout(() => {
        this.loading(false)
      }, 500)
    },
    resetForm() {
      // this.donviql_id = this.options.donviQL[0].donvi_id
      this.tuyenthu.ma_tuyen = ''
      this.tuyenthu.may_cn = ''
      this.tuyenthu.ngay_cn = ''
      this.tuyenthu.nguoi_cn = ''
      this.tuyenthu.nhanvien_id = null
      this.tuyenthu.phanvung_id = null
      this.tuyenthu.sudung = null
      this.tuyenthu.tainha = null
      this.tuyenthu.tentuyen = ''
      this.tuyenthu.tuyenthu_id = ''
      this.tuyenthu.ghichu = ''
    },
    huy() {
      if (!this.button.huy) {
        return false
      }
      this.SetButton(0)
    },
    nhapmoi() {
      if (!this.button.nhapmoi) {
        return false
      }
      this.$refs.txtMatuyen.focus()
      this.SetButton(1)
    },
    KiemTraDuLieuNhap() {
      if (!this.tuyenthu.ma_tuyen) {
        this.$toast.error('Mã tuyến không được để trống !')
        return false
      }
      if (!this.tuyenthu.tentuyen) {
        this.$toast.error('Tên tuyến không được để trống !')
        return false
      }
      return true
    },
    async ghilai() {
      if (!this.button.ghilai) {
        return false
      }
      try {
        if (!this.KiemTraDuLieuNhap()) return false
        this.loading(true)
        var rs = ''

        var data_convert = []
        this.options.dsNap.forEach((item) => {
          data_convert.push({
            AP_ID: item.ap_id,
            DACDIEM_ID: item.dacdiem_id,
            KHU_ID: item.khu_id,
            PHO_ID: item.pho_id,
            PHUONG_ID: item.phuong_id,
            QUAN_ID: item.quan_id
          })
        })
        if (this.button.nhapmoi) {
          let check = await this.axios.post('web-hopdong/hopdong/get_app_config', {
          keyname: 'OPEN_SYNC_VINA_TRASAU'
        })
        if (check && check.data && check.data.data.keyvalue == 'TRUE') {
            let mabc_neo = await this.axios.post('web-quantri/tuyenthu/sp_lay_mabc_neo', {donvi_id : this.donviql_id})
            await api.dongbo_diadanh_ccbs(this.axios).then((response) => {
            if (response.data.error_code === 'BSS-00000000') {
              api
                .ts_danhmuc_update_tuyenthus(this.axios, {
                  ghichu: this.tuyenthu.ghichu,
                  mabc: mabc_neo.data.data.mabc_id_neo,
                  tentuyen: this.tuyenthu.tentuyen,
                  matuyen: this.tuyenthu.ma_tuyen,
                  manv: this.$root.token.getMaNhanVien()
                })
                .then((response) => {
                  if (response.data.error_code === 'BSS-00000500') {
                    this.$toast.error('Lỗi khi cập nhật dữ liệu trên Vinaphone')
                  } else {
                  }
                })
            }
          })
          }
          rs = await api.fn_capnhat_tuyenthu(this.axios, {
            donvi_id: 1,
            donviql_id: this.donviql_id,
            ghichu: this.tuyenthu.ghichu,
            httt_id: this.tuyenthu.httt_id,
            kieu: this.tuyenthu.kieutuyenthu,
            kieuthu_id: this.tuyenthu.kieuthu_id,
            ma_nvtc: this.tuyenthu.ma_nvtc,
            ma_tuyen: this.tuyenthu.ma_tuyen,
            nhanvien_id: this.model.nhanvien_tc,
            sudung: '1',
            tainha: '0',
            tentuyen: this.tuyenthu.tentuyen,
            tuyenthu_id: this.tuyenthu.tuyenthu_id,
            json_tuyenthupx: data_convert
          })
        } else {
          let check = await this.axios.post('web-hopdong/hopdong/get_app_config', {
          keyname: 'OPEN_SYNC_VINA_TRASAU'
        })
        if (check && check.data && check.data.data.keyvalue == 'TRUE') {
            await api.dongbo_diadanh_ccbs(this.axios).then(async (response) => {
            if (response.data.error_code === 'BSS-00000000') {
              let mabc_neo = await this.axios.post('web-quantri/tuyenthu/sp_lay_mabc_neo', {donvi_id : this.donviql_id})
              api
                .ts_danhmuc_insert_tuyenthus(this.axios, {
                  ghichu: this.tuyenthu.ghichu,
                  mabc: mabc_neo.data.data.mabc_id_neo,
                  tentuyen: this.tuyenthu.tentuyen,
                  matuyen: this.tuyenthu.ma_tuyen,
                  manv: this.$root.token.getMaNhanVien()
                })
                .then((response) => {
                  if (response.data.error_code === 'BSS-00000500') {
                    this.$toast.error('Lỗi khi thêm dữ liệu trên Vinaphone')
                  } else {
                  }
                })
            }
          })
          }
          rs = await api.fn_themmoi_tuyenthu(this.axios, {
            json_tuyenthu: [
              {
                DONVIQL_ID: this.donviql_id,
                DONVI_ID: 1,
                GHICHU: this.tuyenthu.ghichu,
                HTTT_ID: this.tuyenthu.httt_id,
                KIEU: this.tuyenthu.kieutuyenthu,
                KIEUTHU_ID: this.tuyenthu.kieuthu_id,
                MA_NVTC: this.tuyenthu.ma_nvtc,
                MA_TUYEN: this.tuyenthu.ma_tuyen,
                NHANVIEN_ID: this.model.nhanvien_tc,
                SUDUNG: '1',
                TAINHA: 0,
                TENTUYEN: this.tuyenthu.tentuyen
              }
            ],
            json_tuyenthupx: data_convert,
            ma_tuyen: this.tuyenthu.ma_tuyen
          })
        }
        this.loading(false)
        if (rs.data) {
          if (rs.data.data == 'ok') {
            this.$toast.success('Ghi lại thành công')
            this.SetButton(3)
          } else this.$toast.error(rs.data.data)
        }
      } catch (e) {
        console.log(e)
        this.loading(false)
        this.$toast.error('Ghi lại thất bại')
      }
      await this.getDsachtuyenthu()
    },
    dacdiem_to_right() {
      this.options.dacdiem_chua_gan = this.options.dacdiem_chua_gan.concat(this.$refs.dacdiem_da_gan.getSelectedRecords())
      let temp = this.$refs.dacdiem_da_gan.getSelectedRecords()
      this.options.dacdiem_da_gan = this.options.dacdiem_da_gan.filter((item) => !temp.includes(item))
    },
    dacdiem_to_right_all() {
      this.options.dacdiem_chua_gan = this.options.dacdiem_chua_gan.concat(this.options.dacdiem_da_gan)
      this.options.dacdiem_da_gan = []
    },
    dacdiem_to_left() {
      this.options.dacdiem_da_gan = this.options.dacdiem_da_gan.concat(this.$refs.dacdiem_chua_gan.getSelectedRecords())
      let temp = this.$refs.dacdiem_chua_gan.getSelectedRecords()
      this.options.dacdiem_chua_gan = this.options.dacdiem_chua_gan.filter((item) => !temp.includes(item))
    },
    dacdiem_to_left_all() {
      this.options.dacdiem_da_gan = this.options.dacdiem_da_gan.concat(this.options.dacdiem_chua_gan)
      this.options.dacdiem_chua_gan = []
    },
    khu_to_right() {
      this.options.khu_chua_gan = this.options.khu_chua_gan.concat(this.$refs.khu_da_gan.getSelectedRecords())
      let temp = this.$refs.khu_da_gan.getSelectedRecords()
      this.options.khu_da_gan = this.options.khu_da_gan.filter((item) => !temp.includes(item))
    },
    khu_to_right_all() {
      this.options.khu_chua_gan = this.options.khu_chua_gan.concat(this.options.khu_da_gan)
      this.options.khu_da_gan = []
    },
    khu_to_left() {
      this.options.khu_da_gan = this.options.khu_da_gan.concat(this.$refs.khu_chua_gan.getSelectedRecords())
      let temp = this.$refs.khu_chua_gan.getSelectedRecords()
      this.options.khu_chua_gan = this.options.khu_chua_gan.filter((item) => !temp.includes(item))
    },
    khu_to_left_all() {
      this.options.khu_da_gan = this.options.khu_da_gan.concat(this.options.khu_chua_gan)
      this.options.khu_chua_gan = []
    },
    pho_to_right() {
      this.options.pho_chua_gan = this.options.pho_chua_gan.concat(this.$refs.pho_da_gan.getSelectedRecords())
      let temp = this.$refs.pho_da_gan.getSelectedRecords()
      this.options.pho_da_gan = this.options.pho_da_gan.filter((item) => !temp.includes(item))
    },
    pho_to_right_all() {
      this.options.pho_chua_gan = this.options.pho_chua_gan.concat(this.options.pho_da_gan)
      this.options.pho_da_gan = []
    },
    pho_to_left() {
      this.options.pho_da_gan = this.options.pho_da_gan.concat(this.$refs.pho_chua_gan.getSelectedRecords())
      let temp = this.$refs.pho_chua_gan.getSelectedRecords()
      this.options.pho_chua_gan = this.options.pho_chua_gan.filter((item) => !temp.includes(item))
    },
    pho_to_left_all() {
      this.options.pho_da_gan = this.options.pho_da_gan.concat(this.options.pho_chua_gan)
      this.options.pho_chua_gan = []
    },
    ap_to_right() {
      this.options.ap_chua_gan = this.options.ap_chua_gan.concat(this.$refs.ap_da_gan.getSelectedRecords())
      let temp = this.$refs.ap_da_gan.getSelectedRecords()
      this.options.ap_da_gan = this.options.ap_da_gan.filter((item) => !temp.includes(item))
    },
    ap_to_right_all() {
      this.options.ap_chua_gan = this.options.ap_chua_gan.concat(this.options.ap_da_gan)
      this.options.ap_da_gan = []
    },
    ap_to_left() {
      this.options.ap_da_gan = this.options.ap_da_gan.concat(this.$refs.ap_chua_gan.getSelectedRecords())
      let temp = this.$refs.ap_chua_gan.getSelectedRecords()
      this.options.ap_chua_gan = this.options.ap_chua_gan.filter((item) => !temp.includes(item))
    },
    ap_to_left_all() {
      this.options.ap_da_gan = this.options.ap_da_gan.concat(this.options.ap_chua_gan)
      this.options.ap_chua_gan = []
    },
    focusTuyenthu(data) {
      if (data.data) {
        for (let key in data.data) {
          if (data.data[key]) this.$set(this.tuyenthu, key, data.data[key])
        }
        this.SetButton(3)
      }
    },
    async init() {
      try {
        let rsDV = await api.getDonviQL(this.axios, { loaidv_id: 2 })
        this.options.donviQL = rsDV.data ? rsDV.data.data : []
        if (this.options.donviQL.length > 0) {
          this.donviql_id = this.options.donviQL[0].donvi_id
        }

        let rsTT = await api.getKieuTuyenthu(this.axios)
        this.options.kieuTuyenthu = rsTT.data ? rsTT.data.data : []
        if (this.options.kieuTuyenthu.length > 0) {
          this.tuyenthu.kieutuyenthu = this.options.kieuTuyenthu[0].kieu
        }

        let rsKT = await api.getKieuthu(this.axios)
        this.options.kieuThu = rsKT.data ? rsKT.data.data : []
        if (this.options.kieuThu.length > 0) {
          this.tuyenthu.kieuthu_id = this.options.kieuThu[0].KIEUTHU_ID
        }

        let rsHTTT = await api.getHTTT(this.axios)
        this.options.httt = rsHTTT.data && rsHTTT.data.data ? rsHTTT.data.data : []
        if (this.options.httt.length > 0) {
          this.tuyenthu.httt_id = this.options.httt[0].httt_id
        }

        await this.getTinh()
        this.timkiem.tinh = this.$root.token.getPhanVungID()
        await this.getQuan()
      } catch (error) {
        console.log(error)
      }
    },
    cboDonVi_QL_SelectedValueChanged(value) {
      this.donviql_id = value
      // this.getNVTC()
    },
    cboNhanVienTC_EditValueChanged_1(value) {
      this.model.nhanvien_tc = value
      this.getDsachtuyenthu()
    },
    async getNVTC() {
      let loainv = 25
      if (this.tuyenthu.kieutuyenthu == 2) loainv = 39
      if (this.tuyenthu.kieutuyenthu == 3) loainv = 34
      let rs = await api.sp_lay_ds_nhanvien_tc_theo_donvi_dl(this.axios, {
        donvidl_id: this.donviql_id,
        loainv_id: loainv
      })
      this.options.nhanvien_TC = rs.data ? rs.data.data : []
    },
    async getDsachtuyenthu() {
      let rs = await api.sp_lay_ds_tuyenthu_nhanvientc_id(this.axios, {
        nhanvientc_id: this.model.nhanvien_tc,
        kieu: this.tuyenthu.kieutuyenthu
      })
      this.options.dsTuyenthu = rs.data ? rs.data.data : []
    },
    async getDsachNap() {
      let rs = await api.sp_lay_ds_tuyenthu_theo_tuyenthu_id(this.axios, {
        tuyenthu_id: this.tuyenthu.tuyenthu_id
      })
      this.options.dsNap = rs.data ? rs.data.data : []
      console.log(this.options.dsNap)
    },
    async getTinh() {
      let rs = await this.axios.get('web-quantri/danhmuc-chung/CSS_TINH')
      this.options.tinh = rs.data ? rs.data.data : []
    },
    async getQuan() {
      let rs = await this.axios.get('web-quantri/danhmuc-chung/CSS_QUAN_CA_NUOC')
      this.options.quan = rs.data ? rs.data.data : []
      this.timkiem.quan = this.options.quan[0].QUAN_ID
    },
    async getPhuong() {
      let rs = await this.axios.post('web-quantri/danhmuc-chung/CSS_PHUONG', {
        ID: this.timkiem.quan
      })
      this.options.phuong = rs.data ? rs.data.data : []
      this.timkiem.phuong = this.options.phuong[0].PHUONG_ID
    },
    async getPhochuagan() {
      let rs = await this.axios.post('web-quantri/danhmuc-chung/CSS_PHO', {
        ID: this.timkiem.phuong,
        CATEGORY_NAME: 1
      })
      this.options.pho_chua_gan = rs.data ? rs.data.data : []
      this.options.pho_chua_gan = this.options.pho_chua_gan.map((item) => ({
        ...item,
        TEN_PHUONG: this.getTenPhuong
      }))
      this.getApchuagan()
      this.getKhuchuagan()
    },
    async getApchuagan(data) {
      let rs = await this.axios.post('web-quantri/danhmuc-chung/CSS_PHO', {
        ID: this.timkiem.phuong,
        CATEGORY_NAME: 2
      })
      this.options.ap_chua_gan = rs.data ? rs.data.data : []
      this.options.ap_chua_gan = this.options.ap_chua_gan.map((item) => ({
        ...item,
        TEN_PHUONG: this.getTenPhuong
      }))
    },
    async getKhuchuagan(data) {
      let rs = await this.axios.post('web-quantri/danhmuc-chung/CSS_PHO', {
        ID: this.timkiem.phuong,
        CATEGORY_NAME: 3
      })
      this.options.khu_chua_gan = rs.data ? rs.data.data : []
      this.options.khu_chua_gan = this.options.khu_chua_gan.map((item) => ({
        ...item,
        TEN_PHUONG: this.getTenPhuong
      }))
    },
    async getDaciemchuagan(data) {
      this.options.dacdiem_chua_gan = []
      let left = this.$refs.pho_chua_gan.getSelectedRecords()
      let right = this.$refs.pho_da_gan.getSelectedRecords()
      let all = left.concat(right)
      for(let i = 0; i < all.length; i++) {
        let rs = await this.axios.post('web-quantri/danhmuc-chung/CSS_DACDIEM', {
          ID: this.timkiem.phuong,
          CODE: all[i].PHO_ID
        })
        if(rs.data && rs.data.data && rs.data.data.length > 0) {
          this.options.dacdiem_chua_gan = this.options.dacdiem_chua_gan.concat(rs.data.data)
        }
      }
    },
    async txtTimKiem_KeyPress() {
      try {
        let matuyen = this.txtTimKiem
        if (matuyen == '') {
          this.$toast.error('Chưa nhập mã tuyến thu cần tìm kiếm !')
          return
        }

        var tk_ma_rs = await this.axios.post('web-quantri/tuyenthu/sp_lay_ds_tuyenthu_ma_nvtc', {
          ma_nvtc: matuyen
        })

        let tk_ma = tk_ma_rs.data && tk_ma_rs.data.data ? tk_ma_rs.data.data : []
        console.log(tk_ma_rs)
        if (tk_ma.length == 0) {
          this.$toast.error('Mã tuyến tìm kiếm không tồn tại !')
          return
        }
        if (tk_ma[0]['donvi_dl_id']) this.donviql_id = Number(tk_ma[0]['donvi_dl_id'])
        if (tk_ma[0]['nhanvien_id']) this.model.nhanvien_tc = Number(tk_ma[0]['nhanvien_id'])
      } catch (err) {
        this.$toast.error('Lỗi tìm kiếm tuyến thu: ' + err.message)
      }
    }
  }
}
</script>
<style>
li.non-active a {
  color: #d3d3d3 !important;
}
</style>
