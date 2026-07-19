<script>
import api from '../api'

export default {
  name: "Details Information",
  props: {
    detailData: {
      type: Object,
      required: true
    }
  },

  data() {
    return {
      images: []
    }
  },
  async mounted() {
    await this.sp_tracuu_file_yagi();
  },
  methods: {
    async downloadImage(url, fileName) {
      try {
        // Fetch the image data
        const response = await fetch(url);
        const blob = await response.blob();

        // Create a temporary URL for the blob
        const blobUrl = window.URL.createObjectURL(blob);

        // Create an anchor element and simulate a click to trigger download
        const link = document.createElement('a');
        link.href = blobUrl;
        link.download = fileName;
        document.body.appendChild(link);
        link.click();

        // Clean up and remove the link after download
        document.body.removeChild(link);
        window.URL.revokeObjectURL(blobUrl);
      } catch (error) {
        console.error('Error downloading the image:', error);
      }
    },
    checkStatus: status => {
      let string = '';
      switch (status) {
        case 1:
          string = 'Chân dung';
          break;
        case 2:
          string = 'CCCD mặt trước'
          break;
        case 3:
          string = 'CCCD mặt sau';
          break;
        case 4:
          string = 'Chữ ký';
          break;
        case 5:
          string = 'Biên bản quà tặng';
          break;
        case 6:
          string = 'Ảnh khác';
          break;
        default:
          break;
      }
      return string;
    },
    async sp_tracuu_file_yagi() {
      this.loading(true)
      try {
        const response = await api.sp_tracuu_file_yagi(this.axios, {
          'p_ma_gd': this.detailData.ma_gd, //this.p_ma_gd
        })

        if (response.data.error_code === 'BSS-00000000') {
          this.images = response.data.data.map((item, index) => ({
            ...item,
            name: this.checkStatus(item.type_id)
          }));

          console.log(this.images);
        }



      } catch (e) {
        this.$toast.error(e.message)
      } finally {
        this.loading(false)
      }
    },
    async capnhat() {
      this.loading(true)
      try {
         const response = await api.fn_capnhat_trangthai_phieu_tangqua(this.axios, {
           'p_ma_gd': this.detailData.ma_gd, //this.p_ma_gd
         })

         if (response.data.error_code === 'BSS-00000000') {
           this.$toast.success('Cập nhật thành công')
           this.$emit("formClose")
         }

      } catch (e) {
        this.$toast.error(e.message)
      } finally {
        this.loading(false)
      }
    },
  },
}
</script>

<template>
  <div class="card-body popup-body">
    <div class="box-form">
      <button :disabled="detailData.trangthai == 1" class="btn btn-primary" @click="capnhat()" style="display: block">Cập nhật</button>
      <hr>
      <vue-card label="Thông tin giao dịch">
        <div class="col-12">
          <div class="info-item d-flex justify-content-between border-bottom py-2">
            <span class="label">Mã GD:</span>
            <span class="value">{{detailData.ma_gd}}</span>
          </div>
        </div>
        <div class="col-12">
          <div class="info-item d-flex justify-content-between border-bottom py-2">
            <span class="label">Tên KH:</span>
            <span class="value">{{detailData.ten_kh}}</span>
          </div>
        </div>
        <div class="col-12">
          <div class="info-item d-flex justify-content-between border-bottom py-2">
            <span class="label">Số giấy tờ:</span>
            <span class="value">{{detailData.so_gt}}</span>
          </div>
        </div>
        <div class="col-12">
          <div class="info-item d-flex justify-content-between border-bottom py-2">
            <span class="label">SĐT liên hệ:</span>
            <span class="value"><a href="">{{detailData.sdt_kh}}</a></span>
          </div>
        </div>
        <div class="col-12">
          <div class="info-item d-flex justify-content-between border-bottom py-2">
            <span class="label">Địa chỉ KH:</span>
            <span class="value">{{detailData.diachi_kh}}</span>
          </div>
        </div>
        <div class="col-12">
          <div class="info-item d-flex justify-content-between border-bottom py-2">
            <span class="label">HT tặng quà:</span>
            <span class="value">{{detailData.hinhthuc}}</span>
          </div>
        </div>
        <div class="col-12">
          <div class="info-item d-flex justify-content-between border-bottom py-2">
            <span class="label">Mã thuế bao:</span>
            <span class="value">{{detailData.msisdn}}</span>
          </div>
        </div>
        <div class="col-12">
          <div class="info-item d-flex justify-content-between border-bottom py-2">
            <span class="label">Số serial SIM:</span>
            <span class="value">{{detailData.msin}}</span>
          </div>
        </div>
        <div class="col-12">
          <div class="info-item d-flex justify-content-between border-bottom py-2">
            <span class="label">Ngày tạo:</span>
            <span class="value">{{detailData.ngay_cn}}</span>
          </div>
        </div>
      </vue-card>

      <vue-card label="Thông tin ảnh chụp">
        <div v-for="(item, index) in images" :key="index" class="image-container">
          <!-- Display the image with a fixed width of 100px -->
          <img :src="item.url1[0].url" @click="downloadImage(item.url1[0].url, item.ten_file)" class="image">
          <p>{{ item.name }}</p>
        </div>
      </vue-card>

      <vue-card label="Ghi chú">
        {{ detailData.ghi_chu }}
      </vue-card>
    </div>

  </div>
</template>

<style scoped>
  .image-container {
    display: inline-block;
    margin: 10px;
  }

  .image {
    width: 100px;
    height: auto;
    cursor: pointer;
  }
</style>
