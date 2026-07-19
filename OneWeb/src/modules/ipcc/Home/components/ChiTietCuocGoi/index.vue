<template src="./index.html">
</template>

<script>
  import moment from 'moment'
  import api from '../../API.js'

  export default {
    props: ['thongtincuocgoi', 'thongtincuocgoi_end', 'isCalling'],
    data() {
      return {
        giobatdau: null,
        gioketthuc: null,
        thoiluong: null,
        maynhan: null,
        noidung: null,
        phanloai: { value: null, Items: [
          {
            id: 1,
            code: 'KhachDiDao',
            text: "Khách hàng gọi nhầm tổng đài, khách hàng quấy rối",
          },
          {
            id: 2,
            code: 'KhachCamLang',
            text: "Khách hàng không nói gì, bỏ máy",
          },
          {
            id: 4,
            code: 'GoiNhamNhanh',
            text: "Khách hàng gọi nhầm nhánh, đã chuyển cuộc gọi thành công",
          },
          {
            id: 8,
            code: 'GiaiDapSuCo',
            text: "Giải đáp sự cố bất khả kháng, xoa dịu khách hàng",
          },
          {
            id: 16,
            code: 'TuVanKyThuat',
            text: "Giải đáp thông tin kỹ thuật, tư vấn kỹ thuật",
          },
          {
            id: 32,
            code: 'TuVanGoiCuoc',
            text: "Giải đáp thông tin thuê bao, gói cước, hóa đơn",
          },
          {
            id: 64,
            code: 'TuVanChinhSach',
            text: "Tư vấn chính sách, gói cước",
          },
          {
            id: 128,
            code: 'Other',
            text: "Nội dung khác",
          },
        ] },
        ds_XuLy: {
          list: [],
          header: [
            {
              fieldName: 'agent_',
              headerText: 'Tỉnh',
              allowFiltering: true,
              allowHtml: true,
              width: 100,
              textAlign: 'center'
            },
            {
              fieldName: 'product_',
              headerText: 'Kiểu xử lý',
              allowFiltering: true,
              allowHtml: true,
              width: 200,
              textAlign: 'center'
            },
            {
              fieldName: 'format_',
              headerText: 'Tên thuê bao',
              allowFiltering: true,
              allowHtml: true,
              width: 200,
              textAlign: 'center'
            },
            {
              fieldName: 'user_',
              headerText: 'Số máy',
              allowFiltering: true,
              allowHtml: true,
              width: 200,
              textAlign: 'center'
            },
            {
              fieldName: 'date_',
              headerText: 'Ngày thêm',
              allowFiltering: true,
              allowHtml: true,
              width: 100,
              textAlign: 'center'
            }
          ],
          selected: {}
        },
        dateFormat: 'DD/MM/YYYY',
        typeFormat: 'date'
      }
    },
    watch: {
      isCalling: async function(newVal, oldVal) { // watch it
        console.log(newVal)
        if (newVal == 1) {
          this.giobatdau = moment(new Date).format('DD/MM/YYYY HH:mm:ss')
          this.gioketthuc = null
          this.thoiluong = null
          this.maynhan = await this.$root.token.getMachine()
        } else if (newVal == 2) {
          this.gioketthuc = moment(new Date).format('DD/MM/YYYY HH:mm:ss')          
          this.maynhan = await this.$root.token.getMachine()
        } else {
          this.giobatdau = null
          this.gioketthuc = null
          this.thoiluong = null
          this.maynhan = null
        }
      },
      async 'thongtincuocgoi_end.CallID'(val) {
        if (val) {
          var tt_cuocgoi = await this.LayChiTietCuocGoi(Number(val))
          this.thoiluong = tt_cuocgoi ? tt_cuocgoi['thoiLuong'] : null  // this.dateDiffInSeconds(this.giobatdau, this.gioketthuc);
        }
      }
    },
    methods: {
      dateDiffInSeconds(dateString1, dateString2) {
        const date1 = this.parseDate(dateString1)
        const date2 = this.parseDate(dateString2)
        const timeDiff = date2 - date1
        return timeDiff / 1000
      },
      parseDate(dateString) {
        const [datePart, timePart] = dateString.split(' ')
        const [day, month, year] = datePart.split('/').map(Number)
        const [hours, minutes, seconds] = timePart.split(':').map(Number)

        // Months in JavaScript Date object are 0-indexed, so subtract 1 from the month
        return new Date(year, month - 1, day, hours, minutes, seconds)
      },
      LayChiTietCuocGoi: async function (cuocgoi_id) {
        try {
          var input = { cuocgoi_id: cuocgoi_id }
          var res = await api.sp_lay_ds_cuocgoi(this.axios, input)
          console.log('sp_lay_ds_cuocgoi', res.data.data)
          if (res.data && res.data.error_code === 'BSS-00000000') {
            if (!res.data.data || res.data.data.length == 0) {
              this.$toast.error('Không tìm thấy thông tin cuộc gọi')
              return {}
            }
            return res.data.data
          } else {
            this.$toast.error('Lỗi thông tin cuộc gọi ' + res.data.message_detail ? res.data.message_detail : res.data.message)
            return {}
          }
              
        } catch (e) {
          if (e && e.data) {
            this.$toast.error('Lỗi thông tin cuộc gọi ' + (e.data.message_detail ? e.data.message_detail : e.data.message))
          } else {
            this.$toast.error('Lỗi thông tin cuộc gọi ' + JSON.stringify(e))
          }
          return {}
        } finally {

        }
      },
      btnGhiLai_click: function () {
        this.$emit("btnGhiLai_click", {
          giobatdau: this.giobatdau,
          gioketthuc: this.gioketthuc,
          thoiluong: this.thoiluong,
          maynhan: this.maynhan,
          phanloai: this.phanloai.value,
          noidung: this.noidung
        })
      }
    }
  }
</script>
<style>
  .fieldset-action {
    position: absolute;
    top: -8px;
    right: 10px;
    padding: 0px 10px;
    background-color: #fff;
    z-index: 2;
  }
</style>
