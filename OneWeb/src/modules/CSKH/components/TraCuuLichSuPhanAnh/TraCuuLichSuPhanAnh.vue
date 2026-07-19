<template src="./TraCuuLichSuPhanAnh.html"></template>
<style src="./TraCuuLichSuPhanAnh.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
//import TraCuuAPI from '../../api/TraCuuAPI'
import TraCuuDanhBa from '../TraCuuDanhBa/'
import { mapActions, mapState, mapGetters } from 'vuex'
//import moment from 'moment'

export default {
  components: { breadcrumb, TraCuuDanhBa },
  name: 'TraCuuLichSuPhanAnh',
  destroyed () {},
  mounted () {
    // this.pageNo = 1
    // this.pageSize = 50
    this.doSearch()
  },
  computed: {
    ...mapState('tracuulichsuphananh', ['dsLichSuPhanAnh']),
    ...mapGetters('tracuulichsuphananh', ['dsLichSuPhanAnhGetter', 'totalItemsGetter'])
  },
  created () {
    this.clearData()
  },
  methods: {
    ...mapActions('tracuulichsuphananh', ['getLichSuPhanAnh', 'clearData', 'getObjSearch']),
    onRowSelected (items) {
      console.log('items', items)
    },
    onChangeText (txt, key) {
      let data = {
        key: key || '',
        value: txt || ''
      }
      this.getObjSearch(data)
    },
    async fetchData() {
      this.loading(true)
      try {
        this.items = []
        let postData =
        {
          ngayTiepNhan: this.$data.chk_from_date ? this.$data.from_date : "",
          denNgay: this.$data.chk_to_date ? this.$data.to_date : "",
          maKh: this.$data.ma_kh,
          pageNo: this.pageNo,
          pageSize: this.pageSize
        }
        await this.getLichSuPhanAnh(postData)
      }
      catch (e) {
        console.log(e)
      }
      this.loading(false)
    },
    async doSearch () {
      this.pageNo = 1
      await this.fetchData()
    }
  },
  data () {
    return {
      header: {
        title: 'TRA CỨU LỊCH SỬ PHẢN ÁNH',
        list: [
          { name: 'Chăm sóc khách hàng', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu lịch sử phản ánh',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: moment(new Date()).format('DD/MM/YYYY'),
      to_date: moment(new Date()).format('DD/MM/YYYY'),
      chk_from_date: false,
      chk_to_date: false,
      pageNo: 1,
      pageSize: 10,
      ma_kh: '',
      fields: [
        {
          key: 'MA_KH',
          label: 'Mã Khách Hàng'
        },
        {
          key: 'NGAYTIEPNHAN',
          label: 'Ngày tiếp nhận',
          formatter: value => moment(value).format('DD/MM/YYYY hh:mm:ss')
        },
        {
          key: 'NGAY_GQ',
          label: 'Ngày giải quyết',
          formatter: value => moment(value).format('DD/MM/YYYY hh:mm:ss')
        },
        {
          key: 'GHICHU',
          label: 'Ghi chú'
        },
        {
          key: 'TRANGTHAI_PA',
          label: 'Trạng thái'
        }
      ],
      items: [],
      arraySearchInput: [
        {key: 'MA_KH', value: ''},
        {key: 'NGAYTIEPNHAN', value: ''},
        {key: 'NGAY_GQ', value: ''},
        {key: 'GHICHU', value: ''},
        {key: 'TRANGTHAI_PA', value: ''}
      ]
    }
  },
  watch: {
    pageNo: {
      handler: async function (value) {
        console.log(value)
        await this.fetchData()
      }
    }
  }
}
</script>
