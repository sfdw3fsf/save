<template>
  <div class="map-canvas-box">
    <div class="map-canvas-main">
      <div id="map" class="map"></div>
      <div class="map-position bg-white">
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row marb0">
              <div class="key w30 nowrap">Kinh độ</div>
              <div class="value">
                <input type="text" v-model="lng" class="form-control bg-white text-primary" readonly />
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row marb0">
              <div class="key w30 nowrap">Vĩ độ</div>
              <div class="value">
                <input type="text" v-model="lat" class="form-control bg-white text-primary" readonly />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="map-header">
      <div class="map-header-right">
        <div class="input-group input-group-search">
          <div class="input-text">
            <input type="text" class="form-control" v-model="txtSearch" @keyup.enter="btnSearch_Click" />
          </div>
          <div class="input-addon">
            <button class="btn" @click="btnSearch_Click">
              <span class="icon one-search"></span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import VnptMap from '@vnpt/maps-onebss';
import html2canvas from "html2canvas";
export default {
  components: {
    html2canvas
  },
  data: function () {
    return {
      lat: 0,
      lng: 0,
      address: "",
      txtSearch: "",
      map: null,
      point: null,
      token: null,
      iconUrl: "/static/icons/location.png"
    };
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
  computed: {
    currentLocation: function () {
      return { lng: this.lng, lat: this.lat };
    },
  },
  async created() {
  },
  methods: {
    bindData: function (kinhDo, viDo, diaChi) {
      this.setPoint(kinhDo, viDo, diaChi);
    },
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
          this.$emit("pointChanged", this.currentLocation);
        });
      }
      else {
        this.point.setData(coord);
      }
      this.map.flyTo(coord, 13);
      this.point.addPopup(address);
    },
    btnSearch_Click(arg) {
      try{
      this.$root.showLoading(true);
      let basemapApi = VnptMap.initBasemapApi();
      basemapApi.getLonLatByAddress(this.txtSearch, this.token, (evt) => {
        if (evt.status) {
          let result;
          for (let i in evt.result) {
            if (evt.result[i].address === this.txtSearch) result = evt.result[i];
          }
          if (!result) result = evt.result[0];
          if (!(result.type === "ward" || result.type === "dist" || result.type === "city")) {
            this.setPoint(result.long, result.lat, result.address);
          }
        }
      });
    }
    catch(e) {}
    finally {
      this.$root.showLoading(false);
    }
    },
    printMap: function () {
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
  },
};
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
