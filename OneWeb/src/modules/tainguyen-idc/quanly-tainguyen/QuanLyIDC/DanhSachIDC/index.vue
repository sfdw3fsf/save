<template src="./index.template.html"></template>

<script>
import defaultIdcImage from '@/assets/img/idc/idcdanang_demo.jpg'

export default {
  name: 'DanhSachIDC',
  props: {
    items: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      defaultIdcImage
    }
  },
  watch: {
    items: {
      handler: async function(newItems) {
        if (!newItems || !Array.isArray(newItems)) return
        await this.processItemImages(newItems)
      },
      immediate: true,
      deep: false
    }
  },
  methods: {
    onImgErr(event) {
      event.target.onerror = null
      event.target.src = this.defaultIdcImage
    },
    handleIdcClick(item) {
      this.$emit('idc-details-clicked', { data: { id: item.ID } })
    },
    async processItemImages(items) {
      const tasks = (items || []).map(async (it) => {
        try {
          if (!it || it._imgLoaded === true) return
          if (it._imgLoading !== true) this.$set(it, '_imgLoading', true)
          const src = it.IMAGE_URL
          const url = await this.getPresignedUrl(src)
          if (url) this.$set(it, 'IMAGE_URL', url)
        } catch (e) {
          // ignore
        } finally {
          this.$set(it, '_imgLoading', false)
          this.$set(it, '_imgLoaded', true)
        }
      })
      await Promise.allSettled(tasks)
    },
    async getPresignedUrl(src) {
      try {
        if (!src) return null
        const rs = await this.$root.context.post('/web-ecms/storage/getPresignedUrl', { fileSource: src })
        if (rs && rs.error_code === 'BSS-00000000' && rs.data && rs.data.result) {
          return rs.data.result
        }
        return null
      } catch (e) {
        return null
      }
    }
  }
}
</script>

<style scoped>
.idc-list-panel-wrapper {
  height: 100%;
  overflow-y: auto;
  padding: 8px 12px;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

/* Ẩn scrollbar cho Chrome, Safari, Edge */
.idc-list-panel-wrapper::-webkit-scrollbar {
  display: none;
}

.idc-list-body {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(230px, 1fr));
  gap: 12px;
  padding: 0;
}

.idc-item {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  gap: 12px;
  border: 1px solid #d7d7d7;
  border-radius: 8px;
  background-color: #fff;
  padding: 15px;
  transition: transform 0.2s, box-shadow 0.2s, border-color 0.2s, background-color 0.2s;
  cursor: pointer;
}

.idc-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-color: #98b2eb;
  background-color: #e0f2fe; /* Light blue background on hover */
}

.idc-avatar {
  width: 48px;
  height: 48px;
  min-width: 48px;
  border-radius: 6px;
  object-fit: cover;
  flex-shrink: 0;
}

.idc-avatar-skeleton {
  width: 48px;
  height: 48px;
  min-width: 48px;
  border-radius: 6px;
  background: linear-gradient(90deg, #f3f4f6 25%, #e5e7eb 37%, #f3f4f6 63%);
  background-size: 400% 100%;
  animation: idc-skeleton-loading 1.2s ease-in-out infinite;
  flex-shrink: 0;
}

@keyframes idc-skeleton-loading {
  0% {
    background-position: 100% 0;
  }
  100% {
    background-position: -100% 0;
  }
}

.idc-name {
  font-weight: bold;
  color: #111827;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-bottom: 4px;
  font-size: 14px;
  line-height: 1.4;
  width: 100%;
  max-width: 100%;
}

.idc-address {
  font-size: 12px;
  color: #6b7280;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-line-clamp: 1;
  line-clamp: 1;
  -webkit-box-orient: vertical;
  display: -webkit-box;
  line-height: 1.4;
  width: 100%;
  max-width: 100%;
}

.idc-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  text-align: left;
  min-width: 0; /* Cho phép text truncate */
  overflow: hidden; /* Đảm bảo không tràn */
  width: 100%;
}
</style>
