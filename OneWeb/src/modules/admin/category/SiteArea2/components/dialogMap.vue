<template>
  <b-modal size="xl" :id="idModalChonToaDo" no-close-on-backdrop hide-footer hide-header hide-header-close
    v-model="visible" body-class="modal-body p-0">
    <div class="modal-content popup-box" v-if="visible">
      <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Khu vực: Test</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide(idModalChonToaDo)">
        </div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:void(0)" @click="XacNhanToaDo()">
              <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Xác nhận
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon one-design-per"></span> Tạo vùng
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon one-edit"></span> Sửa vùng
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon one-trash"></span> Xoá vùng
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon one-reload1"></span> Cập nhật
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon one-file-refresh"></span> Nạp lại
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon one-circle-question"></span> Trợ giúp
            </a>
          </li>
          <!-- <li>
                          <a href="javascript:void(0)" @click="InBanDo()">
                              <span class="icon one-print"></span> In bản đồ
                          </a>
                      </li> -->
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row marb0">
                <div class="key w30 nowrap">Địa chỉ</div>
                <div class="value">
                  <input type="text" class="form-control">
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row marb0">
                <div class="key w30 nowrap">Vĩ độ</div>
                <div class="value">
                  <input type="text" class="form-control" :value="positionKetCuoi.lat">
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row marb0">
                <div class="key w30 nowrap">Kinh độ</div>
                <div class="value">
                  <input type="text" class="form-control" :value="positionKetCuoi.lng">
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="map-canvas-box">
            <BanDo ref="BanDo" :diachi="diachi" :ten_kc="ten_kc" @dblclickMap="dblclickMap" :position="getPosition"
              :zoomDefault="zoomDefault" :mapTypeId="mapTypeId"></BanDo>
            <div class="search-box">
              <div class="input-group">
                <div class="input-text">
                  <div class="input-icon-left">
                    <input type="text" class="form-control" v-model="searchAddressInput"
                      v-on:keyup.enter="searchLocation">
                    <span class="icon one-search"></span>
                  </div>
                </div>
                <div class="input-addon">
                  <button class="btn" @click="searchLocation()">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                </div>
              </div>
            </div>
            <div class="action-right">
              <a href="javascript:void(0)" class="change-type" @click="changetypeMap">
                <img src="@/assets/img/icons/image3.png" alt="">
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
  <script>
import "@/utils/gmaps"
import BanDo from './BanDo.vue'
export default {
  components: {
    BanDo
  },
  data: () => ({
    visible: false,
    idModalChonToaDo: "idModalChonToaDo",
    diachi: null,
    ten_kc: null,
    getPosition: { lat: 1.380, lng: 103.800 },
    zoomDefault: 15,
    mapTypeId: 'ROADMAP',
    positionKetCuoi: {
      lat: null,
      lng: null
    },
    khuvuc_id: null,
    searchAddressInput: ''
  }),
  computed: {
  },
  async mounted() {
    this.loadData()
  },
  methods: {
    show(visible, data) {
      this.visible = visible
      this.khuvuc_id = data.khuvuc_id
      console.log('khuvuc_id', this.khuvuc_id)
    },
    async loadData() {
    },
    XacNhanToaDo() {

    },
    InBanDo() {

    },
    dblclickMap() {

    },
    searchLocation() { },
    changetypeMap() { }
  },
  watch: {
  }
}

</script>
<style>
/* Override default control style */
.mapbox-gl-draw_ctrl-bottom-left,
.mapbox-gl-draw_ctrl-top-left {
  margin-left: 0;
  border-radius: 0 4px 4px 0;
}

.mapbox-gl-draw_ctrl-top-right,
.mapbox-gl-draw_ctrl-bottom-right {
  margin-right: 0;
  border-radius: 4px 0 0 4px;
}

.mapbox-gl-draw_ctrl-draw {
  background-color: rgba(0, 0, 0, 0.75);
  border-color: rgba(0, 0, 0, 0.9);
}

.mapbox-gl-draw_ctrl-draw>button {
  border-color: rgba(0, 0, 0, 0.9);
  color: rgba(255, 255, 255, 0.5);
  width: 30px;
  height: 30px;
}

.mapbox-gl-draw_ctrl-draw>button:hover {
  background-color: rgba(0, 0, 0, 0.85);
  color: rgba(255, 255, 255, 0.75);
}

.mapbox-gl-draw_ctrl-draw>button.active,
.mapbox-gl-draw_ctrl-draw>button.active:hover {
  background-color: rgba(0, 0, 0, 0.95);
  color: #fff;
}

.mapbox-gl-draw_ctrl-draw-btn {
  background-repeat: no-repeat;
  background-position: center;
}

.mapbox-gl-draw_point {
  background-image: url('data:image/svg+xml;utf8,%3Csvg xmlns="http://www.w3.org/2000/svg" width="20" height="20">%3Cpath d="m10 2c-3.3 0-6 2.7-6 6s6 9 6 9 6-5.7 6-9-2.7-6-6-6zm0 2c2.1 0 3.8 1.7 3.8 3.8 0 1.5-1.8 3.9-2.9 5.2h-1.7c-1.1-1.4-2.9-3.8-2.9-5.2-.1-2.1 1.6-3.8 3.7-3.8z"/>%3C/svg>');
}

.mapbox-gl-draw_polygon {
  background-image: url('data:image/svg+xml;utf8,%3Csvg xmlns="http://www.w3.org/2000/svg" width="20" height="20">%3Cpath d="m15 12.3v-4.6c.6-.3 1-1 1-1.7 0-1.1-.9-2-2-2-.7 0-1.4.4-1.7 1h-4.6c-.3-.6-1-1-1.7-1-1.1 0-2 .9-2 2 0 .7.4 1.4 1 1.7v4.6c-.6.3-1 1-1 1.7 0 1.1.9 2 2 2 .7 0 1.4-.4 1.7-1h4.6c.3.6 1 1 1.7 1 1.1 0 2-.9 2-2 0-.7-.4-1.4-1-1.7zm-8-.3v-4l1-1h4l1 1v4l-1 1h-4z"/>%3C/svg>');
}

.mapbox-gl-draw_line {
  background-image: url('data:image/svg+xml;utf8,%3Csvg xmlns="http://www.w3.org/2000/svg" width="20" height="20">%3Cpath d="m13.5 3.5c-1.4 0-2.5 1.1-2.5 2.5 0 .3 0 .6.2.9l-3.8 3.8c-.3-.1-.6-.2-.9-.2-1.4 0-2.5 1.1-2.5 2.5s1.1 2.5 2.5 2.5 2.5-1.1 2.5-2.5c0-.3 0-.6-.2-.9l3.8-3.8c.3.1.6.2.9.2 1.4 0 2.5-1.1 2.5-2.5s-1.1-2.5-2.5-2.5z"/>%3C/svg>');
}

.mapbox-gl-draw_trash {
  background-image: url('data:image/svg+xml;utf8,%3Csvg xmlns="http://www.w3.org/2000/svg" width="20" height="20">%3Cpath d="M10,3.4 c-0.8,0-1.5,0.5-1.8,1.2H5l-1,1v1h12v-1l-1-1h-3.2C11.5,3.9,10.8,3.4,10,3.4z M5,8v7c0,1,1,2,2,2h6c1,0,2-1,2-2V8h-2v5.5h-1.5V8h-3 v5.5H7V8H5z"/>%3C/svg>');
}

.mapbox-gl-draw_uncombine {
  background-image: url('data:image/svg+xml;utf8,%3Csvg xmlns="http://www.w3.org/2000/svg" width="20" height="20">%3Cpath d="m12 2c-.3 0-.5.1-.7.3l-1 1c-.4.4-.4 1 0 1.4l1 1c.4.4 1 .4 1.4 0l1-1c.4-.4.4-1 0-1.4l-1-1c-.2-.2-.4-.3-.7-.3zm4 4c-.3 0-.5.1-.7.3l-1 1c-.4.4-.4 1 0 1.4l1 1c.4.4 1 .4 1.4 0l1-1c.4-.4.4-1 0-1.4l-1-1c-.2-.2-.4-.3-.7-.3zm-7 1c-1 0-1 1-.5 1.5.3.3 1 1 1 1l-1 1s-.5.5 0 1 1 0 1 0l1-1 1 1c.5.5 1.5.5 1.5-.5v-4zm-5 3c-.3 0-.5.1-.7.3l-1 1c-.4.4-.4 1 0 1.4l4.9 4.9c.4.4 1 .4 1.4 0l1-1c.4-.4.4-1 0-1.4l-4.9-4.9c-.1-.2-.4-.3-.7-.3z"/>%3C/svg>');
}

.mapbox-gl-draw_combine {
  background-image: url('data:image/svg+xml;utf8,%3Csvg xmlns="http://www.w3.org/2000/svg" width="20" height="20">%3Cpath d="M12.1,2c-0.3,0-0.5,0.1-0.7,0.3l-1,1c-0.4,0.4-0.4,1,0,1.4l4.9,4.9c0.4,0.4,1,0.4,1.4,0l1-1 c0.4-0.4,0.4-1,0-1.4l-4.9-4.9C12.6,2.1,12.3,2,12.1,2z M8,8C7,8,7,9,7.5,9.5c0.3,0.3,1,1,1,1l-1,1c0,0-0.5,0.5,0,1s1,0,1,0l1-1l1,1 C11,13,12,13,12,12V8H8z M4,10c-0.3,0-0.5,0.1-0.7,0.3l-1,1c-0.4,0.4-0.4,1,0,1.4l1,1c0.4,0.4,1,0.4,1.4,0l1-1c0.4-0.4,0.4-1,0-1.4 l-1-1C4.5,10.1,4.3,10,4,10z M8,14c-0.3,0-0.5,0.1-0.7,0.3l-1,1c-0.4,0.4-0.4,1,0,1.4l1,1c0.4,0.4,1,0.4,1.4,0l1-1 c0.4-0.4,0.4-1,0-1.4l-1-1C8.5,14.1,8.3,14,8,14z"/>%3C/svg>');
}

.vnptmaps-map.mouse-pointer .vnptmaps-canvas-container.vnptmaps-interactive {
  cursor: pointer;
}

.vnptmaps-map.mouse-move .vnptmaps-canvas-container.vnptmaps-interactive {
  cursor: move;
}

.vnptmaps-map.mouse-add .vnptmaps-canvas-container.vnptmaps-interactive {
  cursor: crosshair;
}

.vnptmaps-map.mouse-move.mode-direct_select .vnptmaps-canvas-container.vnptmaps-interactive {
  cursor: grab;
  cursor: -moz-grab;
  cursor: -webkit-grab;
}

.vnptmaps-map.mode-direct_select.feature-vertex.mouse-move .vnptmaps-canvas-container.vnptmaps-interactive {
  cursor: move;
}

.vnptmaps-map.mode-direct_select.feature-midpoint.mouse-pointer .vnptmaps-canvas-container.vnptmaps-interactive {
  cursor: cell;
}

.vnptmaps-map.mode-direct_select.feature-feature.mouse-move .vnptmaps-canvas-container.vnptmaps-interactive {
  cursor: move;
}

.vnptmaps-map.mode-static.mouse-pointer .vnptmaps-canvas-container.vnptmaps-interactive {
  cursor: grab;
  cursor: -moz-grab;
  cursor: -webkit-grab;
}

.mapbox-gl-draw_boxselect {
  pointer-events: none;
  position: absolute;
  top: 0;
  left: 0;
  width: 0;
  height: 0;
  background: rgba(0, 0, 0, .1);
  border: 2px dotted #fff;
  opacity: 0.5;
}
</style>
