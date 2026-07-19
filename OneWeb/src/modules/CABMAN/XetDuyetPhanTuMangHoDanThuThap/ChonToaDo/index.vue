/* eslint-disable standard/object-curly-even-spacing */
/* eslint-disable camelcase */
<template src="./index.template.html"></template>
<script>

import './../../../../utils/gmaps'
// import BanDo from './components/BanDo.vue'
import BanDo from './components/BanDoVNPT.vue'
import html2canvas from "html2canvas";

import EventBus from "../../../../utils/eventBus";

export default {
  components: {BanDo },
  name: 'ModalChonToaDo',
  props: {
    idModalChonToaDo: null,
    position: null,
    diachi: null,
    ten_kc:null,
  },
  computed: {
    getPosition () {
      if (isNaN(this.position.lat) || isNaN(this.position.lng) || this.position.lat * this.position.lng === 0) {
        return {...this.positionDefault}
      } else return {...this.position}
    }
  },
  async mounted () {
    try {
      this.getThongTinBanDo()

    } catch (error) {
      console.log('error', error)
    }
  },
  data () {
    return {
      mapTypeId: 'ROADMAP',
      positionDefault: {
        lat: 0, lng: 0
      },
      zoomDefault: 15,
      positionKetCuoi: {
        lat: 0, lng: 0
      },
      thongtinbando: {},
      searchAddressInput: null,
    }
  },
  methods: {
    getThongTinBanDo: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/bando_matdo/thongtin_bando'
        )
        if (rs.data != null && rs.data.length > 0) {
          this.thongtinbando = rs.data[0]
          this.positionDefault.lat = parseFloat(this.thongtinbando.C_LATITUS)
          this.positionDefault.lng = parseFloat(this.thongtinbando.C_LONGTITUS)
          this.zoomDefault = parseFloat(this.thongtinbando.DEFAULT_ZOOM)
          console.log('getThongTinBanDo', this.positionDefault)
        }
      } catch (error) {
        console.log('getThongTinBanDo', error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    searchLocation () {
      console.log('searchLocation', this.searchAddressInput)
      var map = this.$refs.BanDo
      map.searchLocation(this.searchAddressInput.trim())
    },
    XacNhanToaDo () {
      const position = {...this.positionKetCuoi}
      console.log("🚀 ~ file: index.vue:77 ~ XacNhanToaDo ~ position:", position)
      this.$emit('XacNhanToaDo', position)
      this.$nextTick(() => {
        this.$bvModal.hide(this.idModalChonToaDo)
      })
    },
    changetypeMap () {
      console.log('this.mapTypeId', this.mapTypeId)
      if (this.mapTypeId === 'ROADMAP') {
        this.mapTypeId = 'SATELLITE'
      } else {
        this.mapTypeId = 'ROADMAP'
      }
    },
    dblclickMap (event) {
      this.positionKetCuoi.lat = event.lat
      this.positionKetCuoi.lng = event.lng
    },
    InBanDo(){
      $(".vue-map").children().children().children("div:last-child").hide();
      var element = document.getElementById("map");

      html2canvas(element, {
        useCORS: true,
      }).then(function(canvas) {
        let dataUrl= canvas.toDataURL("image/png");

        // DO SOMETHING WITH THE DATAURL
        // Eg. write it to the page
        let aLink = document.createElement("a");
        aLink.style.display = "none";
        aLink.href = dataUrl;
        let date = new Date()
        let name = "map_" + date.getDate().toString() + (date.getMonth() +1).toString() + date.getFullYear().toString() + date.getHours().toString() + date.getMinutes().toString() + date.getSeconds().toString()
        aLink.download = name+".png";
        document.body.appendChild(aLink);
        aLink.click();
        document.body.removeChild(aLink);
      })

      // const prtHtml = document.getElementById("map").innerHTML;
      // // Get all stylesheets HTML
      // let stylesHtml = "";
      // for (const node of [
      //   ...document.querySelectorAll('link[rel="stylesheet"], style'),
      // ]) {
      //   stylesHtml += node.outerHTML;
      // }
      //
      // // Open the print window
      // const WinPrint = window.open(
      //   "",
      //   "",
      //   "left=0,top=0,width=800,height=900,toolbar=0,scrollbars=0,status=0"
      // );
      //
      // WinPrint.document.write(`<!DOCTYPE html>
      //     <html>
      //     <title>My title</title>
      //       <head>
      //         ${stylesHtml}
      //       </head>
      //       <body>
      //         ${prtHtml}
      //       </body>
      //     </html>`);
      //
      // WinPrint.document.close();
      // WinPrint.focus();
      // WinPrint.print();
      // WinPrint.close();
    }
  },
  watch: {
    position (val) {
      if (val.lat * val.lng === 0) {
        this.positionKetCuoi = {...this.positionDefault}
      } else {
        this.searchAddressInput = this.position.lat + ',' + this.position.lng
        this.positionKetCuoi = {...this.position}
      }
    },
    // diachi (val) {
    //   this.searchAddressInput = val
    // }
  },
  created () {

  }
}
</script>

<style>
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
</style>
