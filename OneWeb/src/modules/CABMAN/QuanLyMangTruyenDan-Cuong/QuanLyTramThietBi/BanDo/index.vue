<template src="./index.template.html"></template>
<script>

import '@/utils/gmaps'
import BanDo from './components/BanDo.vue'
import BanDoVNPT from './components/BanDoDiaChiVNPT.vue'
import EventBus from '@/utils/eventBus'
import html2canvas from "html2canvas";

export default {
  components: { BanDo, html2canvas, BanDoVNPT },
  name: 'ModalChonToaDo',
  props: {
    idModalChonToaDo: String,
    vitriToaDo: Object,
    isShow: Boolean
  },
  computed: {
    getPosition () {
      if (this.position === null || (this.position.lat * this.position.lng === 0)) {
        return {...this.positionDefault}
      } else return {...this.position}
    }
  },
  async mounted () {
    this.position = Object.assign({}, this.vitriToaDo);
    this.positionKetCuoi = Object.assign({}, this.vitriToaDo);
  },
  data () {
    return {
      position: {
        lat: 0, lng: 0
      },
      diachi: null,
      mapTypeId: 'ROADMAP',
      positionDefault: {
        lat: 0, lng: 0
      },
      zoomDefault: 15,
      positionKetCuoi: {
        lat: 0, lng: 0
      },
      thongtinbando: {},
      searchAddressInput: this.positionDefault,
      resolvePromise: null,
      rejectPromise: null
    }
  },
  methods: {
    InBanDo () {
      // var file = 'map_' + new Date().toISOString().slice(0, 10)
      // EventBus.$emit('IN_BANDO', file)
      const el = this.$refs.map;
      html2canvas(el, {
        useCORS: true,
      }).then((canvas) => {
        let imgData = canvas.toDataURL("image/png");
        this.fileDownload(imgData);
      });
    },
    fileDownload(downloadUrl) {
      let aLink = document.createElement("a");
      aLink.style.display = "none";
      aLink.href = downloadUrl;
      aLink.download = "map.png";
      document.body.appendChild(aLink);
      aLink.click();
      document.body.removeChild(aLink);
    },
    closeModal: function () {
      this.$emit('closeModalToaDo');
    },
    searchLocation () {
      var map = this.$refs.BanDo
      map.searchLocation(this.searchAddressInput)
    },
    XacNhanToaDo () {
      this.$bvModal.msgBoxConfirm('Cập nhật tọa độ trạm thiết bị?', {
          size: 'sm',
          centered: true,
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
      })
      .then(async val => {
          if(val) {
            this.$emit('XacNhanToaDo', {...this.positionKetCuoi})
          }
      })
      .catch(err => {
          this.$root.toastError('Cập nhật tọa độ thất bại.');
      })
    },
    changetypeMap () {
      if (this.mapTypeId === 'ROADMAP') {
        this.mapTypeId = 'SATELLITE'
      } else {
        this.mapTypeId = 'ROADMAP'
      }
      this.$refs.BanDo.thaydoiMapType()
    },
    dblclickMap (event) {
      console.log(event);
      this.positionKetCuoi.lat = event.lat
      this.positionKetCuoi.lng = event.lng
    }
  },
  watch: {
    diachi (val) {
      this.searchAddressInput = val
    }
  },
  created () {

  }
}
</script>

<style scoped>
.modal-fullscreen .modal-dialog {
    max-width: 100%;
    margin: 0;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100vh;
    display: flex;
    position: fixed;
    z-index: 100000;
}
.btn-lg {
    background: #AED6F1 ;
    color: #000;
    border:2px solid;
}
</style>
