<template>
  <div class="col-sm-4 col-12">
    <div class="box-form">
      <div class="legend-title">Thông tin thuê bao</div>

      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w80">Mã GD</div>
            <div class="value">
              <input type="text" ref="inputMaGD" v-model="hdtb.ma_gd" @keyup.enter="onEnterMaGD" class="form-control highlight" />
            </div>
          </div>
        </div>

        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w80">Ngày YC</div>
            <div class="value">
              <div class="input-icon-right">
                <k-date-picker v-model="hdtb.ngay_yc"/>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w80">Số máy/Acc</div>
            <div class="value">
              <input type="text" v-model="hdtb.ma_tb" class="form-control highlight" />
            </div>
          </div>
        </div>

        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w80">Tên TB</div>
            <div class="value">
              <input type="text" v-model="hdtb.ten_tb" class="form-control" />
            </div>
          </div>
        </div>

      </div>

      <div class="info-row">
        <div class="key w80">Tên CTV</div>
        <div class="value">
          <input type="text" v-model="hdtb.nhanvien" class="form-control" />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
export default {
  name: "ThongTinThueBao",
  components:{
    KDatePicker
  },
  props:{
      hdtb:{
          type:Object,
          default:() => ({})
      }
  },
  data() {
    return {

    }
  },
  watch:{
    hdtb(val){
      console.log('val',val.ngay_yc)
    }
  },
  methods:{
    getMaGD(){
        if(this.hdtb.ma_gd&&this.hdtb.ma_gd.trim()!=''){
            return this.hdtb.ma_gd.trim()
        }
        return 0
    },
    focusMaGD(){
      this.$nextTick(()=>{
        this.$refs.inputMaGD.focus()
      })
    },
    onEnterMaGD(){
      this.$emit('onEnterMaGD')
    }
  }
}
</script>