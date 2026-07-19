<template>
  <div class="file-viewer">
    <iframe v-if="fileType === 'pdf'"
            :src="fileUrl"
            class="file-frame">
    </iframe>

    <iframe v-else-if="['doc', 'docx', 'xls', 'xlsx'].includes(fileType)"
            :src="getOfficeViewerUrl"
            class="file-frame"
            allowfullscreen="true"
            scrolling="no">
    </iframe>

    <iframe v-else
            :src="'https://docs.google.com/viewer?url=' + encodeURIComponent(fileUrl) + '&embedded=true'"
            class="file-frame">
    </iframe>

    <div v-if="isLoading" class="loading-overlay">
      <div class="spinner-border text-primary" role="status">
        <span class="sr-only">Đang tải...</span>
      </div>
    </div>

    <div v-if="hasError" class="error-message">
      <p>Không thể tải file. Vui lòng thử tải xuống để xem.</p>
      <a :href="fileUrl" target="_blank" class="btn btn-primary mt-2">
        <i class="icon one-download"></i> Tải xuống
      </a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FileViewerMinio',
  props: {
    fileUrl: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      isLoading: true,
      hasError: false,
      retryCount: 0
    }
  },
  computed: {
    fileType() {
      const extension = this.fileUrl.split('.').pop().toLowerCase().split('?')[0];
      return extension;
    },
    getOfficeViewerUrl() {
      try {
        const originalUrl = this.fileUrl;
        const baseViewerUrl = 'https://view.officeapps.live.com/op/view.aspx';

        const encodedUrl = encodeURIComponent(encodeURIComponent(originalUrl));

        const params = new URLSearchParams({
          src: originalUrl,
          wdAllowInteractivity: 'True',
          wdHideGridlines: 'False',
          wdHideHeaders: 'False',
          wdDownloadButton: 'True',
          wdInConfigurator: 'True',
          edesktop: 'true',
          ui: 'vi-VN',
          rs: 'vi-VN',
          wdScaleBreakpoints: '767,1199'
        });

        return `${baseViewerUrl}?${params.toString()}`;
      } catch (error) {
        console.error('Error generating Office Viewer URL:', error);
        this.hasError = true;
        return this.fileUrl;
      }
    }
  },
  methods: {
    checkIframeLoaded() {
      try {
        const iframe = this.$el.querySelector('iframe');
        if (iframe) {
          iframe.onload = () => {
            this.isLoading = false;
            try {
              if (iframe.contentWindow.document.body.innerHTML.length < 100) {
                this.hasError = true;
              }
            } catch (e) {
              console.log('Cannot access iframe content - might be CORS related');
            }
          };

          iframe.onerror = () => {
            this.hasError = true;
            this.isLoading = false;
          };
        }
      } catch (error) {
        console.error('Error checking iframe:', error);
        this.hasError = true;
      }
    }
  },
  mounted() {
    this.checkIframeLoaded();
    setTimeout(() => {
      if (this.isLoading) {
        this.hasError = true;
        this.isLoading = false;
      }
    }, 15000);
  }
}
</script>

<style scoped>
.file-viewer {
  width: 100%;
  height: 100%;
  position: relative;
}

.file-frame {
  width: 100%;
  height: 80vh;
  border: none;
}

.loading-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
}

.error-message {
  text-align: center;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 4px;
  margin: 20px;
}

.btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
}

.btn i {
  font-size: 18px;
}
</style>
