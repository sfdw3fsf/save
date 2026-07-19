<template>
  <div id="goiTuVan">
    <div class="page-content">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key w100">Gói ưu tiên 1</div>
              <div class="value">
                <div v-html="formattedGoiUuTien1" class="formatted-content"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key w100">Gói ưu tiên 2</div>
              <div class="value">
                <div v-html="formattedGoiUuTien2" class="formatted-content"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key w100">Gói ưu tiên 3</div>
              <div class="value">
                <div v-html="formattedGoiUuTien3" class="formatted-content"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>
.formatted-content {
  white-space: pre-wrap; /* Preserve line breaks */
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f9f9f9;
  font-size: 14px;
  color: #333;
}
.key {
  font-weight: bold;
  margin-bottom: 5px;
}
.tabbed-content {
  margin-left: 20px; /* Indent the detailed content */
}
</style>


<script>
import api from '../API.js';
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue';
import KDatePicker from '@/components/kylq_components/KDatePicker.vue';
import ActionTop from '@/components/kylq_components/ActionTop.vue';

export default {
  name: 'goiTuVan',
  components: {
    ActionTop,
    KDatePicker,
    KRequiredMarker
  },
  props: ["dulieu"],
  data() {
    return {
      goiuutien_1: '',
      goiuutien_2: '',
      goiuutien_3: '',
      khachhang_id: 0,
      tocdo_id: 0,
    };
  },
  computed: {
    formattedGoiUuTien1() {
      return this.formatContent(this.goiuutien_1);
    },
    formattedGoiUuTien2() {
      return this.formatContent(this.goiuutien_2);
    },
    formattedGoiUuTien3() {
      return this.formatContent(this.goiuutien_3);
    }
  },
  mounted() {
    this.laygoi_TuVan();
  },
  methods: {
    async laygoi_TuVan() {
      console.log('Dữ liệu popup gói tư vấn nhận được:', this.dulieu);
      try {
        this.loading(true);
        const Input = {
          p_khachhang_id: this.dulieu.khachhang_id,
          p_tocdo_id: this.dulieu.tocdo_id
        };
        const response = await api.laygoi_TuVan(this.axios, Input);
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.goiuutien_1 = `${response.data.data[0].ut_1}: \n${response.data.data[0].chitiet_ut_1}`;
          this.goiuutien_2 = `${response.data.data[0].ut_2}: \n${response.data.data[0].chitiet_ut_2}`;
          this.goiuutien_3 = `${response.data.data[0].ut_3}: \n${response.data.data[0].chitiet_ut_3}`;
        }
      } catch (error) {
        this.$toast.error('Xảy ra lỗi khi lấy gói tư vấn');
      } finally {
        this.loading(false);
      }
    },
    formatContent(content) {
      if (!content) return '';
      const [title, details] = content.split(': \n');
      return `<div>${title}</div><div class="tabbed-content">${details.replace(/\r\n/g, '<br>')}</div>`;
    }
  }
};
</script>

</script>