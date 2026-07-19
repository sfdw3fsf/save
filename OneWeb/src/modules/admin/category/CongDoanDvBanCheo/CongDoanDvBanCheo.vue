<template src="./CongDoanDvBanCheo.html"></template>
<style src="./CongDoanDvBanCheo.scss" scoped></style>
<style>
.hide {
  display: none;
}

.hide .breadcrumb {
  display: none;
}
</style>
<script>
import '@/assets/vendor/jquery/split'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import breadcrumb from '@/components/breadcrumb'
import api from './API.js'
import YesNoModal from '../../../CSKH/utils/YesNoModal.vue'
import DanhMucApi from '../../../CSKH/api/DanhMucApi'

export default {
  components: {
    YesNoModal,
    breadcrumb
  },
  name: 'CongDoanDvBanCheo',
  props: {},
  async mounted() {

  },
  data() {
    return {
      model: {
        congdoan_id: 0,
        congdoan: '',
        khdn: 1,
        ma_cd: '',
        mota: '',
        ttcd_id: 1
      },
      gridbox: {
        list: [],
        cols: [
          { fieldName: 'ma_cd', headerText: 'Mã công đoạn', allowFiltering: true },
          { fieldName: 'congdoan', headerText: 'Tên công đoạn', allowFiltering: true },
          { fieldName: 'loai_kh', headerText: 'Đối tượng bán', allowFiltering: true },
          { fieldName: 'ttcd', headerText: 'Tình trạng', allowFiltering: true }
        ]
      },
      dtbList: [],
      tinhtrangList: [],
      listSelected: [],
      xoaContent: '',
    }
  },
  computed: {},
  async created() {
    await this.fetchDtbList(),
      await this.fetchTinhtrangList(),
      await this.fetchDsCongDoan()
  },
  methods: {
    async fetchDtbList() {
      try {
        let ds = await api.getDSDoiTuongBan()
        if (ds) {
          this.dtbList = ds
        }
      } catch (ex) {
        console.log(ex)
      }
    },
    async fetchTinhtrangList() {
      try {
        let ds = await api.getTinhTrangCddv()
        if (ds) {
          this.tinhtrangList = ds
        }
      } catch (ex) {
        console.log(ex)
      }
    },
    async fetchDsCongDoan() {
      try {
        let ds = await api.getDsCongDoan()
        if (ds) {
          this.gridbox.list = ds
          this.listSelected = []
        }
      } catch (ex) {
        console.log(ex)
      }
    },
    changeDtb(val) {
      console.log('Change đối tượng bán: ', val)
    },
    changeTinhtrang(val) {
      console.log('Change đối tinh trạng: ', val)
    },
    dsCongDoanChange(val) {
      this.listSelected = val
      if (val.length == 1) {
        this.model = val[0]
      }
    },
    onClickNew() {
      this.model = {
        congdoan_id: 0,
        congdoan: '',
        khdn: 1,
        ma_cd: '',
        mota: '',
        ttcd_id: 1
      }
    },
    async onClickSave() {
      if (this.model.congdoan == '') {
        this.$toast.error('Bạn chưa nhập thông tin Tên công đoạn!')
        return
      }
      this.$root.loading(true)
      let data = {
        p_insert: this.model.congdoan_id > 0 ? 2 : 1,
        p_json_data: JSON.stringify(this.model)
      }
      await api.capNhapCongDoan(data)
        .then(async (response) => {
          console.log(response)
          if (response.data == 'OK') {
            if (data.p_insert == 1) {
              this.$toast.success('Thêm mới thành công !')
              this.onClickNew()
            } else {
              this.$toast.success('Cập nhập thành công !')
            }
            this.fetchDsCongDoan()
          } else {
            this.$toast.error(response.message)
          }
        })
        .finally(final => {
          this.$root.loading(false)
        })
    },
    async onClickRemove() {
      if (this.listSelected.length == 0) {
        this.$toast.error('Bạn chưa chọn công đoạn bán chéo cần xóa!')
        return
      }
      this.xoaContent = "Bạn có chắc chắn muốn xóa công đoạn không?"
      this.$bvModal.show('popup-confirmXoa')

    },
    async onXoaConfirm() {
      try {
        this.$root.loading(true)
        await Promise.all(this.listSelected.map(async (element) => {
          let data = {
            p_congdoan_id: element.congdoan_id
          }
          await api.xoaCongDoan(data)
            .then(async (response) => {
              if (response.data == 'OK') {
                this.$root.loading(false)
                this.$toast.success('Xoá thành công!')
                this.listSelected = []
                this.fetchDsCongDoan()
              } else {
                this.$root.loading(false)
                this.$toast.error(response.data)
              }
            })
        }))

      }
      catch (e) {

      }
      finally {
        this.$root.showLoading(false)
      }

    }
  },

  watch: {}
}
</script>
