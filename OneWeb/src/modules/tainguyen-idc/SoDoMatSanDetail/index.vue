<template src="./index.template.html"></template>

<script>
import api from './api.js'
import NangLucKhongGian from './components/nang-luc-khong-gian.vue'
import NangLucDien from './components/nang-luc-dien.vue'
import PhongZone from './components/phong-zone.vue'
// import PopupMatSanDetail from '../DanhMucMatSan/components/PopupMatSanDetail/PopupMatSanDetail.vue'
import ThuocTinhBoSung from '../DanhMucMatSan/components/ThuocTinhBoSung'

export default {
  name: 'SoDoMatSanDetail',
  components: {
    NangLucKhongGian,
    NangLucDien,
    PhongZone,
    ThuocTinhBoSung
  },
  props: {
    matSanId: {
      type: Number,
      default: null
    }
  },
  data: function() {
    return {
      tab_index: 1,
      selectedMatSanId: 0,
      dialogMode: 'EDIT',
      matSan: {
        id: 0,
        idcId: 0,
        toaNhaId: 0,
        toaNhaName: '',
        idcName: '',
        matSanName: '',
        dienTich: 0,
        tongDtZone: 0,
        chieuDai: 0,
        chieuRong: 0,
        ghiChu: '',
        hieuLuc: true,
        tamSanRong: 0,
        tamSanDai: 0,
        thuocTinhBoSung: [],
        hinhAnh: '',
        imageUrl: ''
      }
    }
  },
  computed: {},
  watch: {},
  async mounted() {
    const id = this.matSanId || this.$route.params.matSanId
    if (id) {
      await this.loadData(id)
    }
  },
  methods: {
    async loadData(id) {
      try {
        this.$root.loading(true)
        const response = await api.getMatSanDetail(this.$root.context, id)

        if (response.data) {
          const apiData = response.data

          // Map dữ liệu từ API sang matSan
          this.matSan = {
            id: Number(apiData.id) || 0,
            idcId: Number(apiData.idcId) || 0,
            idcName: apiData.idcName || '',
            toaNhaId: Number(apiData.toaNhaId) || 0,
            toaNhaName: apiData.toaNhaName || '',
            matSanName: apiData.matSanName || '',
            dienTich: Number(apiData.dienTich) || 0,
            tongDtZone: Number(apiData.tongDtZone) || 0,
            chieuDai: Number(apiData.chieuDai) || 0,
            chieuRong: Number(apiData.chieuRong) || 0,
            ghiChu: apiData.ghiChu || '',
            hieuLuc: apiData.hieuLuc !== undefined ? Boolean(apiData.hieuLuc) : true,
            thuocTinhBoSung: [],
            tamSanRong: Number(apiData.tamSanRong) || 0,
            tamSanDai: Number(apiData.tamSanDai) || 0,
            hinhAnh: apiData.hinhAnh || '',
            imageUrl: ''
          }

          // Lấy presigned URL nếu có hình ảnh
          if (this.matSan.hinhAnh) {
            await this.getPresignedUrl(this.matSan.hinhAnh)
          }
        }

        // Gọi API lấy thuộc tính bổ sung riêng
        await this.loadThuocTinh(id)
      } catch (error) {
        console.error('Error fetching mat san detail:', error)
      } finally {
        this.$root.loading(false)
      }
    },

    async loadThuocTinh(matSanId) {
      try {
        const response = await api.getThuocTinhMatSan(this.$root.context, matSanId)
        if (response.data) {
          this.matSan.thuocTinhBoSung = response.data || []
        }
      } catch (error) {
        console.error('Error fetching thuoc tinh:', error)
        this.matSan.thuocTinhBoSung = []
      }
    },

    // Lấy presigned URL từ relative URL
    async getPresignedUrl(relativeUrl) {
      try {
        const response = await this.$root.context.post('/web-ecms/storage/getPresignedUrl', {
          fileSource: relativeUrl
        })

        if (response && response.error === '200' && response.data && response.data.result) {
          this.matSan.imageUrl = response.data.result
          console.log('Presigned URL loaded:', this.matSan.imageUrl)
        } else {
          console.error('Failed to get presigned URL:', response)
        }
      } catch (error) {
        console.error('Error getting presigned URL:', error)
      }
    },
    async handlePopupSaved(data) {
      // Reload lại dữ liệu sau khi lưu từ popup
      if (data && data.id) {
        await this.loadData(data.id)
      }
    },
    onManageZone() {},
    async onDelete() {
      let flag = await this.$confirm('Có chắc bạn muốn xoá mặt sàn?', 'Xác nhận xoá', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'
      })
      if (flag) {
        try {
          const rs = await api.deleteMatSan(this.$root.context, this.matSan.id)
          if ((rs.data = -1)) {
            this.$toast.error('Mặt sàn đang được sử dụng, không thể xoá')
            return
          } else {
            this.$toast.success('Xoá mặt sàn thành công')
            this.$router.push(`/tainguyen-idc/so-do-mat-san/${this.matSan.toaNhaId}`)
          }
        } catch (e) {
          this.$toast.error('Xoá mặt sàn thất bại')
          console.error(e)
        }
      }
    },
    onChangeTab: function(index) {
      this.tab_index = index
      switch (index) {
        case 1:
          this.$refs.nangLucKhongGian.fetchNangLucKhongGian()
          break
        case 2:
          this.$refs.nangLucDien.fetchNangLucDien()
          break
        case 3:
          this.$refs.phongZone.fetchPhongZone()
          break
      }
    }
  }
}
</script>

<style src="./style.css" scoped></style>
