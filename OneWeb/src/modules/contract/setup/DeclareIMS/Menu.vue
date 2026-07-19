<!--TODO: this route only for testing, plz remove on prod-->
<template>
  <div>
    <h3>Menu IMS</h3>
    <div class="row">
      <div class="col-lg-6">
        <div class="control-section">
          <ul>
            <li>
              <router-link active-class="" :to="{
                name: 'declareIMS',
              }">[UR2.5.015] - Khai báo IMS</router-link>
            </li>
            <li>
              <router-link active-class="" :to="{
                name: 'FinishIMSSiptrunk',
              }">[UR2.4.057] - Hoàn công Ims Siptrunk</router-link>
            </li>
            <li>
              <router-link active-class="" :to="{
                name: 'OperateSetupNewMoveCDLHIMSSiptrunk',
              }">[UR2.4.115] - Điều hành lắp mới/dịch chuyển/CĐLH - IMS SipTrunking</router-link>
            </li>
            <li>
              <router-link active-class="" :to="{
                name: 'GrantPortIMSSubcriber_TeleStation',
              }">[UR2.4.098] - Trạm VT - Cấp Port thuê bao IMS</router-link>
            </li>
            <li>
              <router-link active-class="" :to="{
                name: 'SetupIMS_OMC',
              }">[UR2.5.024] - OMC - Khai báo Ims</router-link>
            </li>
            <li>
              <router-link active-class="" :to="{
                name: 'SetupIMS_TTDH',
              }">[UR2.5.038] - TTĐH - Khai báo IMS</router-link>
            </li>
            <li>
              <router-link active-class="" :to="{
                name: 'HTHSIMSOtherProvinceCrossSale',
              }">[UR2.6.058] - HTHS IMS tỉnh khác (Bán chéo)</router-link>
            </li>
            <li>
              <router-link active-class="" :to="{
                name: 'InstallIMS_KTVT',
              }">[UR2.4.046] - Tổ KTVT - Thi công IMS</router-link>
            </li>
            <li>
              <router-link active-class="" :to="{
                name: 'ConfigIMSSiptrunking_Net',
              }">[UR2.4.111] - VNPT Net - Cấu hình thuê bao IMS Siptrunking</router-link>
            </li>
            <li>
            <router-link active-class="" :to="{
                name: 'CheckIMSServicePartner',
              }">[UR2.4.065] - Đối soát dịch vụ IMS đối tác</router-link>
          </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from 'vue'

export default Vue.extend({
  name: 'MenuIMS',
  components: {
  },
  data () {
    return {
    }
  },
  mounted () {
  },
  methods: {
  }
})
</script>
