<template>
  <div class="item">
    <div class="top">
      <span class="number">
      {{ index + 1 }}
      </span>
      <div class="title-bg-main">
        <div class="pull-left ten-kh">
          {{ item.ten_kh }}
        </div>
        <div class="pull-right font-weight-bold" v-bind:class="[classTTDuyet]">
          {{ trangThaiDuyet }}
        </div>
        <div class="clearfix"></div>
      </div>
      <div class="actions">
        <a @click="onShowHoDan(item)"
           class="btn btn-main">
          <span class="one-file-bookmark1 lh14 "></span>
        </a>
        <a @click="onShowMap(item)"
           class="btn btn-main">
          <span class="one-map-location lh14 "></span>
        </a>
        <a @click="onDeleteHoDan(item)"
           class="btn btn-main">
          <span class="one-trash lh14 "></span>
        </a>
        <a href="javascript:void(0)" class="btn btn-main" @click="checked">
          <span class="nc-icon-outline lh14 " :class="[classBtnCheck]"></span>
        </a>
      </div>
    </div>
    <div class="item-content">
      <div class="image">
        <img v-if="this.item.current_img > 0" class="thumbnail" :src="item.listImage[currentImgIndex - 1].url" alt="">
        <img v-if="this.item.current_img === 0" class="thumbnail" src="static/icons/no-image.png" alt="">
      </div>
      <div class="info">
        <p class="owner">Chủ hộ: {{ item.ten_kh }}</p>
        <p>Số người: {{ item.songuoi }}</p>
        <p>Phân loại: {{ item.loai_hodan }}</p>
        <p>Mức sống: {{ item.mucsong }}</p>
        <p>Địa chỉ: {{ item.diachi }}</p>
      </div>

    </div>
    <div class="tc-pagination">
      <a href="javascript:void(0)"
         class="btn fa fa-angle-left"
         @click="previousImage()"
         :class="{'disabled': currentImgIndex === 0 ? true: (currentImgIndex === 1)}"></a>
      <div class="dropdown inline">
        <div class="fw6 input-number" data-toggle="dropdown">
          <span class="text-main">{{ currentImgIndex }}</span>/{{ numberOfImg }}
        </div>
        <!--        <div class="dropdown-menu">-->
        <!--          <a href="#" class="dropdown-item">10</a>-->
        <!--          <a href="#" class="dropdown-item">20</a>-->
        <!--        </div>-->
      </div>
      <a class="btn fa fa-angle-right"
         @click="nextImage()"
         :class="{'disabled': currentImgIndex === 0 ? true: (currentImgIndex === numberOfImg)}"></a>
    </div>
  </div>
</template>
<style scoped>
.btn-main {
  background: #0176FF;
  color: #fff;
}

.btn-main:hover {
  background: #035FCC;
  color: #fff;
}

.bss992-info .top .title-bg-main, .bss992-info .top .actions .btn {
  height: 33px;
}

.bss992-info .top .title-bg-main .ten-kh {
  max-width: 70%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.owner {
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
<script>
import EventBus from '@/utils/eventBus';
import API from "../API";
import Define from '../define';

export default {
  computed: {
    trangThaiDuyet() {
      switch (this.item.tt_duyet) {
        case Define.stt.cho_duyet:
          return 'Chờ duyệt';
        case Define.stt.khong_dat:
          return 'Không đạt';
        case Define.stt.dat:
          return 'Đã duyệt';
      }

      return '';
    },
    classTTDuyet() {
      switch (this.item.tt_duyet) {
        case Define.stt.cho_duyet:
          return 'text-success';
        case Define.stt.khong_dat:
          return 'text-danger';
        case Define.stt.dat:
          return 'text-primary';
      }

      return '';
    },
    numberOfImg() {
      return this.item.listImage.length;
    },
  },
  props: {
    index: Number,
    item: Object
  },
  data() {
    return {
      currentImgIndex: this.item.current_img,
      classBtnCheck: 'media-1_button-stop'
    }
  },
  methods: {
    onDeleteHoDan(hoDan) {
      this.$bvModal.msgBoxConfirm('Bạn có muốn xóa thông tin hộ dân này không?',
        {
          title: "Xóa thông tin hộ dân",
          centered: true,
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
        }).then(async (value) => {
        if (value) {
          let rs = await API.sp_hodan_xoa(this.axios, hoDan.sdkh_id);
          if (rs.data.error === '200') {
            this.$toast.success('Xóa thông tin hộ dân thành công');
            EventBus.$emit('reload', hoDan);
          }
        }
      });
    },
    onShowHoDan(hoDan) {
      EventBus.$emit('onShowHoDan', hoDan)
    },
    onShowMap(hoDan) {
      EventBus.$emit('onShowMap', hoDan);
    },
    previousImage() {
      if (this.currentImgIndex > 1) {
        this.currentImgIndex--;
      }
    },
    nextImage() {
      if (this.currentImgIndex < this.numberOfImg) {
        this.currentImgIndex++;
      }
    },
    checked() {
      this.classBtnCheck = this.classBtnCheck === 'media-1_button-stop' ? 'one-check' : 'media-1_button-stop';
    }
  }
}
</script>
