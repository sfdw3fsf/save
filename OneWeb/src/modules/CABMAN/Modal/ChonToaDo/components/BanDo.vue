<template>
  <div class="map-canvas-box">
    <div class="map-canvas-main">
      <div id="map" class="map"></div>
    </div>
  </div>
</template>
<script>

import VnptMap from '@vnpt/maps-onebss';
import html2canvas from 'html2canvas'
import EventBus from '@/utils/eventBus'
export default {
  components: { html2canvas },
  name: 'BanDo',
  props: {
    position: null,
    diachi: null,
    mapTypeId: null,
    zoomDefault: null,
  },
  watch: {
    position(val) {

    }
  },
  computed: {
    getPositionCenter() { return this.position },
    getNodes() {
      return this.nodes
    }
  },
  destroyed() {
    EventBus.$off('IN_BANDO', this.inBando)
  },
  mounted: async function () {
    let tt = await this.getThongTinBanDo();
    if (tt == null || tt == undefined) {
      this.token = null;
    }
    else {
      this.token = tt.TOKEN;
      this.map = VnptMap.initMap("map");
      setTimeout(() => this.map.updateSized(), 10);
    }
    if (this.token == null) {
      this.$root.toastError("Lỗi truy cập đến thông tin bản đồ");
    }
  },
  data() {
    return {
      zoomLevel: this.zoomDefault,
      mapOptions: {
        zoomControl: false,
        scaleControl: true,
        disableDefaultUi: true,
        streetViewControl: false,
        mapTypeControl: false,
        fullscreenControl: false,
        maxZoom: this.maxZoom,
        draggableCursor: 'default',
        draggingCursor: 'pointer',
        disableDoubleClickZoom: true
      },
      maxZoom: 20,
      map: null,
      nodes: [],
      positionCenter: this.position,
      point: null,
      token: null,
      iconUrl: "/static/icons/location.png",
      lat: 0,
      lng: 0,
      address: "",
    }
  },
  watch: {
    mapTypeId(val) {
      if (val === 'ROADMAP') {
        this.map.setMapTypeId(window.google.maps.MapTypeId.ROADMAP)
      } else {
        this.map.setMapTypeId(window.google.maps.MapTypeId.SATELLITE)
      }
    }
  },
  methods: {
    setPoint: function (lng, lat, address) {
      this.lng = lng;
      this.lat = lat;
      let coord = [this.lng, this.lat];
      if (this.point == null) {
        this.point = VnptMap.initPoint(coord, {
          iconUrl: this.iconUrl,
          id: "Point",
          isDraggable: true,
          iconWidth: 48,
          iconHeight: 48
        });
        this.point.addLayerTo(this.map);
        this.point.onDrag('dragend', (e) => {
          this.lat = e.coordinates.lat;
          this.lng = e.coordinates.lng;
        });
      }
      else {
        this.point.setData(coord);
      }
      this.map.flyTo(coord, 13);
      this.point.addPopup(address);
    },
    searchLocation: function (searchAddressInput) {
      let basemapApi = VnptMap.initBasemapApi();
      basemapApi.getLonLatByAddress(searchAddressInput, this.token, (evt) => {
        if (evt.status) {
          let result;
          for (let i in evt.result) {
            if (evt.result[i].address === this.txtSearch) result = evt.result[i];
          }
          if (!result) result = evt.result[0];
          if (!(result.type === "ward" || result.type === "dist" || result.type === "city")) {
            this.setPoint(result.long, result.lat, result.address);

            const idx = this.nodes.findIndex(obj => obj.id === 'x')
            if (idx > -1) {
              this.nodes[idx].position.lat = this.lat
              this.nodes[idx].position.lng = this.lng
            } else {
              this.nodes.push({
                id: 'x',
                position: {
                  lat: this.lat,
                  lng: this.lng
                }
              })
            }
            this.$emit('dblclickMap', { lat: lat, lng: lng })
          }
        }
      });
    },
    dragendPositionNode(event, node) {
      node.position.lat = event.latLng.lat()
      node.position.lng = event.latLng.lng()
      this.$emit('dblclickMap', { lat: event.latLng.lat(), lng: event.latLng.lng() })
    },
    dblclickMap(event) {
      const idx = this.nodes.findIndex(obj => obj.id === 'x')
      if (idx > -1) {
        this.nodes[idx].position.lat = event.latLng.lat()
        this.nodes[idx].position.lng = event.latLng.lng()
      } else {
        this.nodes.push({
          id: 'x',
          position: {
            lat: event.latLng.lat(),
            lng: event.latLng.lng()
          }
        })
      }
      this.$emit('dblclickMap', { lat: event.latLng.lat(), lng: event.latLng.lng() })
    },
    rightclickMap(event) { },
    reload() {
      this.setPoint(this.location.lng, this.location.lat, "");
    },
    inBando(file) {
      HTMLCanvasElement.prototype.getContext = function (origFn) {
        return function (type, attribs) {
          attribs = attribs || {};
          attribs.preserveDrawingBuffer = true;
          return origFn.call(this, type, attribs);
        };
      }(HTMLCanvasElement.prototype.getContext);

      html2canvas($("#map.map")[0], {
        useCORS: true
      }).then((canvas) => {
        let imgData = canvas.toDataURL("image/png");
        var newWindow = window.open("");
        $(newWindow.document.body)
          .html(
            "<img src=" +
            imgData +
            " style='width: " +
            canvas.width +
            "px'></img>"
          )
          .ready(function () {
            newWindow.focus();
            newWindow.print();
          });
      });
    },
    getThongTinBanDo: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/quanlyhatang/getThongTinBanDo");
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến thông tin bản đồ");
        return null;
      }
    },
  }
}
</script>
<style>
.map {
  position: initial;
  height: 400px;
  width: 100%;
}
.map-canvas-box {
  overflow: hidden;
}
</style>
