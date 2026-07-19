<template src="./index.html"></template>
<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import Select2Ext from '@/modules/contract/setup/DeclareLandline/components/Select2Ext'
import DataGridCustom from '@/modules/contract/setup/DeclareLandline/components/DataGridCustom'
import VueDateExt from '@/modules/contract/setup/DeclareLandline/components/VueDateExt'
import downloadexcel from '@/modules/report/BI/JsonExcel'
import { formatCurrencyNoVND } from '@/utils/format'
import moment from 'moment'

export default {
  data() {
    return {
      dataExport: [],
      fieldExport: {
        "Thời gian" : "chukyno",
        "Đơn vị" : "ten_donvi",
        "Kênh bán" : "kenhban",
        "Hợp đồng" : "sohd",
        "Thuê bao" : "ma_tb",
        "Dịch vụ" : "ten_dvvt",
        "Loại hình" : "loaihinh_tb",
        "Công đoạn" : "congdoan",
        "Doanh thu" : "doanhthu",
        "Tỷ lệ tham gia" :  "tyle_thamgia",
        "Tỷ lệ hoa hồng" : "tyle_hh",
        "Tiền hoa hồng" : "tien_hh",
        "Số tiền thù lao"  : "tien_thulao",
        "Số tiền còn nợ" : "tien_conno"
      },
      thoigian: null,
      columns: [
        {
          fieldName: 'chukyno',
          headerText: 'Thời gian',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto',
          template: () => {
            return {
              template: {
                template: `<span>{{formatDate(data.chukyno)}}</span>`,
                data: function () {
                  return {
                    data: {},
                  };
                },
                methods: {
                  formatDate(date) {
                    return moment(date, "YYYYMM").format("MM/YYYY")
                  }
                }
              },
            };
          },
        },
        {
          fieldName: 'ten_donvi',
          headerText: 'Đơn vị',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'kenhban',
          headerText: 'Kênh bán',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'sotienthulao',
          headerText: 'Số tiền thù lao',
          allowFiltering: true,
          allowSorting: true,
          template: () => {
            return {
              template: {
                template: `<span>{{formatCurrencyNoVND(data.sotienthulao)}}</span>`,
                data: function () {
                  return {
                    data: {},
                  };
                },
                methods: {
                  formatCurrencyNoVND(money) {
                    return formatCurrencyNoVND(money)
                  }
                }
              },
            };
          },
          width: 'auto'
        },
        {
          fieldName: 'sotienconno',
          headerText: 'Số tiền còn nợ',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto',
          template: () => {
            return {
              template: {
                template: `<span>{{formatCurrencyNoVND(data.sotienconno)}}</span>`,
                data: function () {
                  return {
                    data: {},
                  };
                },
                methods: {
                  formatCurrencyNoVND(money) {
                    return formatCurrencyNoVND(money)
                  }
                }
              },
            };
          },
        }
      ]
    }
  },
  components: {
    breadcrumb,
    Select2Ext,
    DataGridCustom,
    VueDateExt,
    downloadexcel
  },
  mounted() {
    this.form_load()
  },
  methods: {
    async form_load() {
      this.loading(true)
      try {
        // let dsDonvi = await this.axios.post("web-khdn/chuyennokenh/getDonVi", {})
        // this.$refs.cboDonVi.dataSource = dsDonvi.data ? dsDonvi.data.data : []
        // this.$refs.cboDonVi.dataTextField = "ten_dv"
        // this.$refs.cboDonVi.dataValueField = "donvi_id"
      } catch (err) {}
      this.loading(false)
    },
    async tsbtnXemSoLieu_Click() {
      this.loading(true)
      try {
        let data = await this.axios.post('/web-khdn/chuyennokenh/xemsolieu', {
          p_thoigiantu: this.thoigian ? moment(this.thoigian).format('YYYYMM') : moment().format('YYYYMM'),
          p_donviid: '' //this.$refs.cboDonVi.value
        })
        this.$refs.dtGrid.dataSource = data.data.data
      } catch (err) {
        this.$refs.dtGrid.dataSource = []
        console.log(err)
      }
      this.loading(false)
    },
    async tsbtnChotSoLieu_Click() {
      this.loading(true)
      let data = await this.axios.post('/web-khdn/chuyennokenh/chotsolieu', {
        p_thoigiantu: this.thoigian ? moment(this.thoigian).format('YYYYMM') : moment().format('YYYYMM'),
        p_donviid: '' //this.$refs.cboDonVi.value
      })
      try {
        if (data.data.error_code == 'BSS-00000000') this.$toast.success('Công bố số liệu thành công')
        else this.$toast.error('Có lỗi xảy ra: ' + data.data.message_detail)
      } catch (err) {
        this.$toast.error(err.message)
      }
      this.loading(false)
    },
    async tsbtnExport_Click() {
      return new Promise(async (rs, rj) => {
        this.loading(true)
        try {
          let data = await this.axios.post('/web-khdn/chuyennokenh/xemsolieuexcel', {
            p_thoigiantu: this.thoigian ? moment(this.thoigian).format('YYYYMM') : moment().format('YYYYMM'),
            p_donviid: '' //this.$refs.cboDonVi.value
          })
          this.dataExport = data && data.data ? data.data.data : []
        } catch (err) {
          this.dataExport = []
          console.log(err)
        }
        rs(true)
        this.loading(false)
        console.log('Xuất excel')
      })
    }
  }
}
</script>