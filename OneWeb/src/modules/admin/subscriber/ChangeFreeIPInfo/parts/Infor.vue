<template>
    <div class="box-form">
        <div class="legend-title">Thông tin thuê bao</div>
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w90">Account</div>
                    <div class="value">
                        <input :value="subcriber.subcriberCode"
                        @input="updateSubcriberCode"
                        v-on:keyup.enter="loadSubcriber"
                         type="text" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w90">Loại hình TB</div>
                    <div class="value">
                        <div class="select-custom">
                            <select :disabled="isDisabledSubcriberTypes" name="" id="" class="form-control" :value="subcriber.selectedSubcriberType" @change="updateSelectedSubcriberType">
                                <option v-for="type in subcriber.subcriberTypes"
                                :key="type.value"
                                v-bind:value="type.value">{{ type.text }}</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12 col-12">
                <div class="info-row">
                    <div class="key w90">Tên thuê bao</div>
                    <div class="value">
                        <input :value="subcriber.subcriberName" @input="updateSubcriberName" type="text" class="form-control">
                    </div>
                </div>
            </div>
            <!-- <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w90">Tốc độ cũ</div>
                    <div class="value">
                        <div class="select-custom">
                            <select disabled name="" id="" class="form-control" :value="subcriber.selectedOldSpeed" @change="updateOldSpeed">
                                <option v-for="speed in subcriber.oldSpeeds"
                                :key="speed.value"
                                v-bind:value="speed.value">{{ speed.text }}</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div> -->
        </div>
        <div class="info-row">
            <div class="key w90">Địa chỉ LĐ</div>
            <div class="value">
                <input :value="subcriber.address" @input="updateAddress" type="text" class="form-control">
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
import VueDate from '../../../../../components/form/VueDate.vue'

export default {
  components: { VueDate },
  props: {
    isEnableAddNew: Boolean
  },
  data () {
    return {
      isDisabledSubcriberTypes: true
    }
  },
  computed: {
    subcriber () {
      return this.$store.state.changeFreeIPInfo.changeFreeIPInfoCommon.subcriberInfo
    }
  },
  created: function () {
    this.$store.dispatch('lhtbCommon/getDanhSachLoaiHinhTB')
      .then((loadedSubcriberTypeList) => {
        this.setSubcriberTypes(loadedSubcriberTypeList)
      })
  },
  methods: {
    async loadSubcriber (e) {
      this.$root.showLoading(true)
      try {
        await this.setSubcriberInfo({
          dichvuvt_id: 4,
          donvi_dl_id: 0,
          ma_tb: this.subcriber.subcriberCode
        })
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$emit('loadSubcriber', e)
      this.$root.showLoading(false)
    },
    ...mapActions('changeFreeIPInfoCommon', [
      'setSubcriberCode',
      'setSubcriberInfo',
      'setSubcriberTypes',
      'setAddress',
      'setSubcriberName',
      'setSelectedSubcriberType'
    ]),
    createSubcriberTypes: function (loadedSubcriberTypeList) {
      this.setSubcriberTypes(loadedSubcriberTypeList)
    },
    updateSubcriberCode: function (e) {
      this.setSubcriberCode(e.target.value)
    },
    updateAddress: function (e) {
      this.setAddress(e.target.value)
    },
    updateOldSpeed: function (e) {
    },
    updateSubcriberName: function (e) {
      this.setSubcriberName(e.target.value)
    },
    updateSelectedSubcriberType: function (e) {
      this.setSelectedSubcriberType(e.target.value)
    }
  },
  watch: {
    isEnableAddNew: function () {
      this.isDisabledSubcriberTypes = this.isEnableAddNew
    }
  }
}
</script>
