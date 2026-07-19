<template src="./index.template.html"></template>

<script>
import api from './api.js'

export default {
  name: 'SoDoMatSan',
  props: {
    toaNhaId: {
      type: Number,
      default: null
    }
  },
  data: function() {
    return {
      dsMatSan: [],
      searchKeyword: ''
    }
  },
  computed: {
    filteredMatSan() {
      if (!this.searchKeyword || this.searchKeyword.trim() === '') {
        return this.dsMatSan
      }

      const keyword = this.searchKeyword.toLowerCase().trim()
      return this.dsMatSan.filter((matSan) => {
        return matSan.matSanName && matSan.matSanName.toLowerCase().includes(keyword)
      })
    }
  },
  watch: {},
  async mounted() {
    const id = this.toaNhaId || this.$route.params.toaNhaId
    if (id) {
      await this.loadData(id)
    }
  },
  methods: {
    async loadData(toaNhaId) {
      try {
        this.$root.loading(true)
        const response = await api.getDanhSachMatSan(this.$root.context, toaNhaId)

        // Response trả về data là array
        if (response.data) {
          this.dsMatSan = response.data.map((item) => ({
            matSanId: item.matSanId || '',
            idcId: item.idcId || '',
            idcName: item.idcName || '',
            toaNhaId: item.toanhaId || '',
            toaNhaName: item.toaNhaName || '',
            matSanName: item.matSanName || '',
            dienTich: item.dienTich || 0,
            soPhong: item.soPhong || 0,
            tiLeSuDung: item.tiLeSuDung || 0,
            hinhAnh: item.hinhAnh || '',
            imageUrl: ''
          }))

          // Lấy presigned URL cho tất cả các mặt sàn có hình ảnh
          await Promise.all(
            this.dsMatSan.map(async (matSan) => {
              if (matSan.hinhAnh) {
                await this.getPresignedUrl(matSan)
              }
            })
          )
        }

        console.log('Loaded dsMatSan:', this.dsMatSan)
      } catch (error) {
        console.error('Error fetching danh sach mat san:', error)
      } finally {
        this.$root.loading(false)
      }
    },
    getRandomColor() {
      const colors = [
        '#4A90E2', // blue
        '#50C878', // green
        '#9B59B6', // purple
        '#E74C3C', // red
        '#F39C12', // orange
        '#1ABC9C', // teal
        '#3498DB', // light blue
        '#E67E22', // dark orange
        '#16A085', // dark teal
        '#8E44AD' // dark purple
      ]
      return colors[Math.floor(Math.random() * colors.length)]
    },
    getUsageBadgeClass(usage) {
      if (usage >= 80) return 'badge-danger'
      if (usage >= 60) return 'badge-warning'
      return 'badge-success'
    },
    onViewDetail(matSan) {
      this.$router.push(`/tainguyen-idc/chi-tiet-mat-san/${matSan.matSanId}`)
    },
    onAddMatSan() {
      this.$router.push(`/tainguyen-idc/danhmuc-matsan`)
    },
    onSearch() {
      // The filtering is handled by the computed property
      // This method is here in case you need to add debouncing or other logic
    },
    // Lấy presigned URL từ relative URL
    async getPresignedUrl(matSan) {
      try {
        const response = await this.$root.context.post('/web-ecms/storage/getPresignedUrl', {
          fileSource: matSan.hinhAnh
        })

        if (response && response.error === '200' && response.data && response.data.result) {
          matSan.imageUrl = response.data.result
          console.log('Presigned URL loaded for', matSan.matSanName, ':', matSan.imageUrl)
        } else {
          console.error('Failed to get presigned URL:', response)
        }
      } catch (error) {
        console.error('Error getting presigned URL:', error)
      }
    }
  }
}
</script>

<style src="./style.css" scoped></style>
