<template src="./RegisterBusinessNumberRange.html"></template>
<style scoped src="./RegisterBusinessNumberRange.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from './API'
import Vue from 'vue'
import { DropDownListPlugin } from '@syncfusion/ej2-vue-dropdowns'
import KTable from '@/components/KTable.vue'
import ActionTop from '@/components/ActionTop.vue'
import { TRANGTHAI_DONGBO } from '@/constants.js'
import popPhieuDangKy from './PhieuDangKy.vue'
Vue.use(DropDownListPlugin)
export default {
  components: { breadcrumb, KTable, ActionTop, popPhieuDangKy },
  data: function () {
    return {
      header: {
        title: 'Đăng ký dải số doanh nghiệp',
        list: [
          {
            name: 'Lập hợp đồng',
            link: { name: 'Ui.cards' }
          },
          {
            name: 'Đăng ký dải số doanh nghiệp',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      dsDaiSo: [],
      params: {
        SLThuebao: '',
        soThuebao: '',
        p_kieudayso_option: [],
        p_kieudayso: null,
        p_kieulay_option: [],
        p_kieulay: null
      },
      tableThueBaoConfig: {
        tableCollumns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'dai_so',
            headerText: 'Dải số',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tinh_trang',
            headerText: 'Tình trạng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'so_luong',
            headerText: 'Số lượng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'loai_daiso',
            headerText: 'Loại dải số',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'cuoccamket',
            headerText: 'Cước tháng cam kết',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'thoigiancamket',
            headerText: 'Thời gian cam kết	',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tientratruoc',
            headerText: 'Tiền trả trước',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'hinhthuc_hm',
            headerText: 'Hình thức hòa mạng',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          },
          {
            fieldName: 'ID',
            headerText: 'Chọn',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'Center',
            width: 90,
            template: function () {
              return {
                template: Vue.component('ChonTemplate', {
                  template: `<a class="btn btn-main pad3 lh14" @click="handleChon" >
                          <span class="one-check"></span></a>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    handleChon() {
                      this.parent.onClickLoadPhieuDangky()
                      //this.$refs.Phieu_dang_ky.showModal();
                    }
                  }
                })
              }
            }
          }
        ],
        tableData: []
      },
      soThueBaoSelected: '',
      thongTinNguoiDung: {},
      daisotbGocSelected: '',
      LoaiDaiSoSelected: '',
      CuocCamKetSelected: '',
      TgianCamKetSelected: '',
      p_hinhthuchoamang: '',
      p_SoLuongDK: ''
    }
  },
  validations: {},
  computed: {},
  watch: {},
  created() {
    this.LoadKieuDaySo()
    this.loadKieuLay()

    let dataMaTinh = {
      id_neo: 'tentat',
      table: 'css.TINH',
      dk: 'where TINH_ID=' + this.$root.token.getPhanVungID()
    }

    api.layMaTinh(this.axios, dataMaTinh).then((res) => {
      console.log(res.data.data)
    })

    api.layThongTinNguoiDung(this.axios, {}).then((res) => {
      if (res.data.data) {
        this.thongTinNguoiDung = res.data.data
      }
    })
  },
  async mounted() {},
  methods: {
    async LoadKieuDaySo() {
      let items = []
      if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU) {
        api.ts_kieu_daiso_dn(this.axios).then((response) => {
          if (response.data.error_code === 'BSS-00000000') {
            response.data.data.forEach(function (item) {
              items.push({ id: item.ID, text: item.TEN_DAISO })
            })
            this.params.p_kieudayso_option = items
            this.params.p_kieudayso = items[0].id
          }
        })
      } else {
        items = [{ id: 1, text: 'Dải số cho Doanh nghiệp' }]
        this.params.p_kieudayso_option = items
        this.params.p_kieudayso = items[0].id
      }
    },
    async loadKieuLay() {
      let items = []

      items = [
        { id: 1, text: 'Lấy từ đầu dãy' },
        { id: 2, text: 'Lấy từ cuối dãy' }
      ]
      this.params.p_kieulay_option = items
      this.params.p_kieulay = items[0].id
    },
    onChangeKieuDaySo(agrs) {
      this.params.p_kieudayso = agrs.id
    },
    onChangeKieuLay(agrs) {
      this.params.p_kieulay = agrs.id
    },
    timKiemThongTin() {
      let dataDS = {
        soluong_tb: this.params.SLThuebao,
        search: this.params.soThuebao,
        daiso_id: this.params.p_kieudayso,
        page_num: this.params.p_kieulay,
        userid: this.$root.token.getUserName(), //this.$root.token.getNhanVienID()
        page_rec: ''
      }
      api.layDSChiTietDaiSo(this.axios, dataDS).then((res) => {
        if (res.data.data.length > 0) {
          this.dsDaiSo = res.data.data
          for (let i = 0; i < this.dsDaiSo.length; i++) {
            let obj = {
              stt: this.dsDaiSo[i].ROW_STT,
              dai_so: this.dsDaiSo[i].TEN_DAISOS,
              tinh_trang: this.dsDaiSo[i].STATUS,
              so_luong: this.dsDaiSo[i].SO_LUONG,
              loai_daiso: this.dsDaiSo[i].TEN_DAISO,
              cuoccamket: this.dsDaiSo[i].CUOCTHANG_TOITHIEU,
              thoigiancamket: this.dsDaiSo[i].THOIGIAN_SUDUNG,
              tientratruoc: this.dsDaiSo[i].TIEN_TRATRUOC,
              hinhthuc_hm: this.dsDaiSo[i].HINHTHUC_HM
            }
            this.tableThueBaoConfig.tableData.push(obj)
          }
        } else {
          this.dsDaiSo = []
          this.$toast.error('Không có dữ liệu!')
        }
      })
    },
    chonThueBao(item) {
      this.daisotbGocSelected = item.dai_so
      this.LoaiDaiSoSelected = item.loai_daiso
      this.CuocCamKetSelected = item.cuoccamket
      this.TgianCamKetSelected = item.thoigiancamket
      this.soThueBaoSelected = item.dai_so
      this.p_hinhthuchoamang = item.hinhthuc_hm
      this.p_SoLuongDK = item.so_luong
    },
    SaveThongtin() {
      let vID = 0
      let data = {
        vDaiSoGocTB: this.daisotbGocSelected,
        vSoLuongDK: this.params.SLThuebao,
        vLoaiDaiSo: this.LoaiDaiSoSelected,
        vCuocCamKet: this.CuocCamKetSelected,
        vTGCamKet: this.TgianCamKetSelected,
        vID: 0
      }
      this.KHOITAO_DS(data)
    },
    async KHOITAO_DS(data) {
      let res = await api.DangKyDaiSo(this.axios, data)
      if (res.data && res.data.error_code == 'BSS-00000000') {
        this.$toast.success('Đăng ký dải số doanh nghiệp thành công')
      } else {
        this.$toast.error('Có lỗi xảy ra!')
      }
    },
    changeSoTB() {
      let SL = this.params.soThuebao
      if (SL != '') {
        if (this.validateInput(SL) == false) {
          this.$toast.error("Nhập '*' và số bất kỳ!")
        }
      }
    },
    changeSLTB() {
      let SL = this.params.SLThuebao
      if (SL < 10) {
        this.$toast.error('Số lượng thuê bao chọn không nhỏ hơn 10')
      }
    },
    validateInput: function (sothuebao) {
      // debugger;
      if (/^\w+/.test(sothuebao)) {
        return false
      }
      return true
    },
    onClickLoadPhieuDangky() {
      this.$refs.Phieu_dang_ky.showModal()
    }
  }
}
</script>
