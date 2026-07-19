<template src="./ToaDo.html"></template>
<script>
import GMap from '../FormToaDo'
export default {
  name: "ToaDo",
  props: ["modalId", "data"],
  components: { GMap },
  computed: {
    lng: function () {
      if (this.toado == "0,0" || this.toado == 'null,null' || this.toado == ',' || this.toado == "" || this.toado == undefined)
        return 0;
      else
        return this.toado.split(",")[1];
    },
    lat: function () {
      if (this.toado == "0,0" || this.toado == 'null,null' || this.toado == ',' || this.toado == "" || this.toado == undefined)
        return 0;
      else
        return this.toado.split(",")[0];
    }
  },
  methods: {
    focusMyElement: async function () {
      this.nodes = []
      this.edit = false;
      let macdinh = "21.019324322184655,105.80942518391265"
      let diachi = ""
      if (this.data == null) {
        this.toado = macdinh
      }
      else {
        if (this.data.toado == "0,0" || this.data.toado == 'null,null' || this.data.toado == ',')
          this.toado = macdinh
        else {
          this.toado = this.data.toado
        }
        diachi = this.data.diachi != "" ? this.data.diachi : "Vị trí mặc định theo tỉnh"
      }
      this.$refs.mapcontrol.bindData(this.lng, this.lat, diachi);
    },
    xacnhan: async function () {
      try {
        this.$root.showLoading(true);
        this.$emit('xacnhan', this.lat + "," + this.lng);
        this.$bvModal.hide(this.modalId)
      } catch (error) {
        this.$toast.error(error.messeage);
      } finally {
        this.$root.showLoading(false);
      }
    },
    print: function () {
      this.$refs.mapcontrol.printMap();
    },
    mapcontrol_pointChanged: function(args){
      this.toado = args.lat + "," + args.lng;
    }
  },
  data: function () {
    return {
      toado: "",
      query: "",
      nodes: []
    };
  },
};
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}

.dashboard-header {
  z-index: 9 !important;
}

a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>