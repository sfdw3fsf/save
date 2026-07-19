import Vue from "vue";
import * as VueGoogleMaps from "gmap-vue";

Vue.use(VueGoogleMaps, {
  load: {
    // key: "AIzaSyANYVpeOpsNN4DqdKR4AKAyd03IQ3_9PvU", // OK
    // key: "AIzaSyBU7Jj9b4KUdoe8z4VyW7f3nzzqZdeIgaM", // OK
    // key: "AIzaSyApCLF8MvTwHpCIb6cDk9N56xifxufmwnE", // OK
    // key: "AIzaSyAft-2prbTpSAgKXOYy6W-15NGxOtsLfAE", // OK
    key: "AIzaSyBgZ1_U_7ju6PC-4aYULJN3Mexa6kkiE9Q",
    libraries: ["places", "geometry", "visualization", "drawing"],
    region: "VI",
    language: "vi"
  },
  installComponents: true
});
