<template>
  <div class="d-flex align-items-center">
    <!-- Nút chọn ảnh -->
    <button class="btn btn-primary lh14 mr-2" :disabled="disabled" @click="openFileDialog">
      <span class="-ap icon-more_horiz"></span>
      Chọn ảnh
    </button>

    <!-- Ảnh preview nhỏ -->
    <div v-if="value" class="position-relative ml-2">
      <img
        :src="objectUrl || presignedUrl || value"
        alt="preview"
        class="img-fluid pointer preview-img"
        @click="showPreview = true"
      />
      <button v-if="!disabled" type="button" class="btn btn-sm btn-light close-btn" @click.stop="clearImage">
        &times;
      </button>
    </div>

    <input ref="fileInput" type="file" accept="image/jpeg, image/png" style="display: none" @change="onFileChange" />

    <!-- Overlay xem ảnh lớn -->
    <div
      v-if="showPreview"
      class="img-preview-overlay d-flex justify-content-center align-items-center"
      @click.self="showPreview = false"
    >
      <img
        :src="objectUrl || presignedUrl || value"
        alt="preview-large"
        class="img-fluid rounded shadow"
        style="max-width: 80%; max-height: 80%;"
      />
    </div>
  </div>
</template>

<script>
import api from './api'

export default {
  name: 'ImageSelector',
  props: {
    value: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      objectUrl: null,
      presignedUrl: null,
      showPreview: false
    }
  },
  methods: {
    async loadPresignedUrl() {
      if (!this.value || this.objectUrl) return // already have objectUrl or no value

      try {
        const url = await api.getPresignedUrlFromMinio(this.value)
        this.presignedUrl = url
      } catch (error) {
        console.error('Error loading presigned URL:', error)
      }
    },
    openFileDialog() {
      if (this.disabled) return
      this.$refs.fileInput.click()
    },
    clearImage() {
      this.revokeUrl()
      this.presignedUrl = null
      this.$emit('input', '')
      this.$emit('file-cleared')
    },
    onFileChange(event) {
      const file = event.target.files && event.target.files[0]
      if (!file) return

      // create object URL for preview
      this.revokeUrl()
      this.objectUrl = URL.createObjectURL(file)

      // emit event to parent to update v-model
      this.$emit('input', this.objectUrl)
      this.$emit('file-selected', file)
    },
    revokeUrl() {
      if (this.objectUrl) {
        URL.revokeObjectURL(this.objectUrl)
        this.objectUrl = null
      }
    },
    async uploadImage(file) {
      const formData = new FormData()
      formData.append('file', file)

      return await api.uploadImage(formData)
    }
  },
  watch: {
    value: {
      immediate: true,
      async handler (newValue) {
        console.log('ImageSelector value changed:', newValue)
        if (newValue && !newValue.startsWith('blob:')) {
          this.revokeUrl()
          this.presignedUrl = null
          await this.loadPresignedUrl()
        }
      }
    }
  },
  beforeDestroy() {
    this.revokeUrl()
  }
}
</script>

<style scoped>
.preview-img {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 6px;
  border: 1px solid #ddd;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.preview-img:hover {
  transform: scale(1.05);
}

.img-preview-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  z-index: 1050;
}

.img-preview-overlay img {
  border-radius: 8px;
  max-width: 90%;
  max-height: 90%;
}
</style>
