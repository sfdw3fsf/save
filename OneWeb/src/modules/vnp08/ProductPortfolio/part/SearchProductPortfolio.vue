<template>
  <div class="information-search">
    <div class="infor-search box-form">
      <div class="row">
        <div class="legend-title">Thông tin tìm kiếm</div>
        <div class="col-sm-3 col-12">
          <b-form-group>
            <div class="info-row item-radio">
              <div class="value">
                <el-tooltip effect="light" content="Lấy tất cả thuê bao đặt cọc" placement="bottom">
                  <b-form-radio v-model="radioDataFilter" value="0"> Tất cả </b-form-radio>
                </el-tooltip>
              </div>
            </div>
            <div class="info-row item-radio">
              <div class="value">
                <el-tooltip effect="light" content="Lấy danh sách các thuê bao vẫn trong thời gian đặt cọc" placement="bottom">
                  <b-form-radio v-model="radioDataFilter" value="1"> Đang có hiệu lực </b-form-radio>
                </el-tooltip>
              </div>
            </div>
            <div class="info-row value item-radio">
              <div class="row">
                <div class="col-sm-4">
                  <b-form-radio v-model="radioDataFilter" value="2">User CN </b-form-radio>
                </div>
                <div class="col-sm-8">
                  <vue-input @keydown.enter.native="searchTBUD('USERCN')" :disable="!enableUserCN" class="" :required="true" v-model="nguoi_cn"> </vue-input>
                </div>
              </div>
            </div>
          </b-form-group>
        </div>
        <div class="col-sm-9 col-12">
          <div class="info-row">
            <div class="key w95">Tháng BĐ</div>
            <div class="value date-icon mask-date">
              <ejs-maskedtextbox mask="00/0000" placeholder="__/____" v-model="thangBĐUuDaiSearch" @keydown.enter.native="searchTBUD('THANGBD')"></ejs-maskedtextbox>
              <!-- <vue-input style="width: 100%" placeholder="__/__" :formatter="momentFormat" valueType="format" v-model="thangBĐUuDaiSearch" @keydown.enter.native="searchThangBD"> </vue-input>
              <div class="icon-date"><i class="el-icon-date"></i></div> -->
            </div>
          </div>

          <div class="info-row">
            <div class="key w95">Tháng KT</div>
            <div class="value date-icon mask-date">
              <ejs-maskedtextbox mask="00/0000" placeholder="__/____" v-model="thangKTUuDaiSearch" @keydown.enter.native="searchTBUD('THANGKT')"></ejs-maskedtextbox>
              <!-- <ejs-datepicker id="datepicker"  placeholder="mm/yyyy"  @keyup.enter="thangKTUuDaiSearch" v-model="thangKTUuDaiSearch"  :format="datePickerFormat"></ejs-datepicker> -->
              <!-- <vue-input style="width: 100%" placeholder="__/___" :formatter="momentFormat" valueType="format" v-model="thangKTUuDaiSearch" @keydown.enter.native="searchThangKT"> </vue-input>
              <div class="icon-date"><i class="el-icon-date"></i></div> -->
            </div>
          </div>
          <div class="info-row value">
            <vue-input :labelWidth="'100'" labelField="tendslam" valueField="dslam_id" label="Số máy/Acc" v-model="soMayAccSearch" @keydown.enter.native="searchTBUD('SOMAYACC')"></vue-input>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import moment from 'moment'
import DatePicker from 'vue2-datepicker';
import helper from'../helper/UtilsJS'

export default {
  components: {
    DatePicker
  },

  data() {
    return {
      thangBĐUuDai: 0,
      thangKTUuDai: 0,
      soMayAcc: '',
      nguoi_cn: '',
      radioBtnFilter: [],
      enableUserCN: false,
      radioDataFilter: 1,
      vkieuTemp: 1, //0: tat ca, 1: lấy trong danh bạ, 2: lấy trong hợp đồng.
      thangBĐUuDaiSearch: 0,
      thangKTUuDaiSearch: 0,
      soMayAccSearch: '',
      datePickerFormat: 'MM/yyyy',
      blurOn: '',

      momentFormat: {
        stringify: (date) => {
          return date ? moment(date).format('MM/YYYY') : ''
        },
        parse: (value) => {
          return value ? moment(value, 'MM/YYYY').toDate() : null
        }
      }
    }
  },

  async created() {
    //tim kiem thue bao
    const currentTime = moment().format('yyyyMM')
    const variables = {
      nguoi_cn: this.nguoi_cn,
      vdichvuvt_id: 0,
      vkieu: this.radioDataFilter, //0: tat ca, 1: lấy trong danh bạ, 2: lấy trong hợp đồng.
      vloaitb_id: 0,
      vmatb: this.soMayAccSearch,
      vthang_bd: parseInt(currentTime),
      vthang_kt: parseInt(currentTime)
    }
    try {
      this.$root.loading(true)
      await this.fetchDanhSachThueBaoUuDai(variables)
    } catch (error) {
      console.log(error)
      this.$root.loading(false)
    }
    this.$root.loading(false)
  },
  computed: {
    ...mapGetters('mapPreferentialSubscriber', []),
    userCN() {
      // return this.$store.getters['user/user'];
      return this.$root.token.getUserName()
    }
  },

  methods: {
    ...mapActions('mapPreferentialSubscriber', ['fetchHopDongTheoMaThueBao', 'fetchDanhSachThueBaoUuDai']),
    async onChangeRadioFilter(val) {
      let variables = {
        nguoi_cn: "",
        vdichvuvt_id: 0,
        vkieu: 0,
        vloaitb_id: 0,
        vmatb: '',
        vthang_bd: 0,
        vthang_kt: 0
      }
      // mac dinh la tat ca

      if (val == 0) {

        // thay doi thoi bug so BSS-72385
        if(!this.soMayAccSearch && !this.thangBĐUuDaiSearch && !this.thangKTUuDaiSearch)
            return this.$toast.error("Cần nhập tháng bắt đầu, tháng kết thúc hoặc số máy")
        

        let thangDB2Search = 0;
        let thangKT2Search = 0;
        //check thang BD Uu dai neu nhap vao thang BD
        if(this.thangBĐUuDaiSearch){
          if(!this.isMonthInputValidate(this.thangBĐUuDaiSearch)) return this.$toast.error("Kiểm tra lại tháng bắt đầu!");
          thangDB2Search = helper.convertMaskTextToDate(this.thangBĐUuDaiSearch);
        }

         

        
        //check thang KT Uu dai
        if(this.thangKTUuDaiSearch){
          if(!this.isMonthInputValidate(this.thangKTUuDaiSearch)) return this.$toast.error("Kiểm tra lại tháng kết thúc!");
          thangKT2Search = helper.convertMaskTextToDate(this.thangKTUuDaiSearch);
        }
          

        
        this.enableUserCN = false
        this.vkieuTemp = val
        variables['vkieu'] = val
        variables['vthang_bd'] = parseInt(thangDB2Search);
        variables['vthang_kt'] = parseInt(thangKT2Search);
        variables['vma_tb'] = this.soMayAccSearch;
        variables['nguoi_cn'] = "";
        /* code C#*
          txtNguoiCN.Enabled = false;
          txtNguoiCN.Text = "";
          vuser_cn = "";
          vdichvuvt_id = 0;
          vloaitb_id = 0;
          vthang_bd = 0;
          vthang_kt = 0;
          vma_tb = "";
          vkieu = 0;
         */
      }
      if (val == 1) {
        this.vkieuTemp = val
        const currentTime = moment().format('yyyyMM')
        this.enableUserCN = false
        variables['vkieu'] = val
        variables['nguoi_cn'] = ''
        variables['vthang_bd'] = parseInt(currentTime)
        variables['vthang_kt'] = parseInt(currentTime);
        this.nguoi_cn = "";
        /* code C#*
          txtNguoiCN.Enabled = false;
          txtNguoiCN.Text = "";
          vuser_cn = "";
          vdichvuvt_id = 0;
          vloaitb_id = 0;
          vthang_bd = Convert.ToInt32(tt_nd.ngay_cn.ToString("yyyyMM"));
          vthang_kt = Convert.ToInt32(tt_nd.ngay_cn.ToString("yyyyMM"));
          vma_tb = "";
          vkieu = 1;
        */
      }
      if (val == 2) {
        this.enableUserCN = true
        this.nguoi_cn = this.userCN.trim()
        this.vkieuTemp = 0
        variables['vkieu'] = 0
        variables['nguoi_cn'] = this.userCN.trim();
        /**
          txtNguoiCN.Enabled = true;
          txtNguoiCN.Text = tt_nd.ma_nd;
          vuser_cn = tt_nd.ma_nd;
          vdichvuvt_id = 0;
          vloaitb_id = 0;
          vthang_bd = 0;
          vthang_kt = 0;
          vma_tb = "";
          vkieu = 0;
         */
      }

      await this.fetchDanhSachThueBaoUuDai(variables)
    },

    async searchTBUD(blurOn) {
      let thangBD = 0
      let thangKT = 0
      // let kieuTK = 0
      //kiem tra value radio button

      //kiem tra du lieu thang BD
      
      const errorMessageTBD = 'Kiểm tra lại tháng bắt đầu!'
      if (blurOn === 'THANGBD' && this.thangBĐUuDaiSearch.length != 6) {
        return this.$toast.error(errorMessageTBD)
      }
      // neu thang dac biet co du lieu thi validate
      if (this.thangBĐUuDaiSearch) {
        //check validate 
         if(!this.isMonthInputValidate(this.thangBĐUuDaiSearch)) return this.$toast.error(errorMessageTBD);
          thangBD = helper.convertMaskTextToDate(this.thangBĐUuDaiSearch);
      }

      // kiem tra du lieu thang ket thuc
      const errorMessageTKT = 'Kiểm tra lại tháng kết thúc!'
      if (this.thangKTUuDaiSearch.length != 6 && blurOn === 'THANGKT') {
        return this.$toast.error(errorMessageTKT)
      }

      //neu thang kt co du lieu thi validate
       if (this.thangKTUuDaiSearch) {
        if(!this.isMonthInputValidate(this.thangKTUuDaiSearch)) return this.$toast.error("Kiểm tra lại tháng kết thúc!");
          thangKT = helper.convertMaskTextToDate(this.thangKTUuDaiSearch);
      }

      // kiem tra so may Acc neu dang blur tai so may
      // if (!this.soMayAccSearch && blurOn === 'SOMAYACC') return this.$toast.error('Số máy không được bỏ trống!')

      //kiem tra userCN
      if (!this.nguoi_cn && blurOn === 'USERCN') return this.$toast.error('User CN không được bỏ trống!')

      //check filter 0, 1 , 2
      // neu val == 2 thi

      const params = {
        nguoi_cn: this.nguoi_cn,
        vdichvuvt_id: 0,
        vkieu: this.vkieuTemp,
        vloaitb_id: 0,
        vmatb: this.soMayAccSearch.trim(),
        vthang_bd: thangBD,
        vthang_kt: thangKT
      }

      try {
        this.$root.loading(true)
        await this.fetchDanhSachThueBaoUuDai(params)
      } catch (error) {
        this.$root.loading(false)
        this.$toast.error('Có lỗi xảy ra!')
      }
      this.$root.loading(false)
    },
    isMonthInputValidate(month){
     
       if (month.length === 6) {
        const valueMonthBD = month.slice(0, 2)
        const valueYearBD = month.slice(2, 6)
        
        if (parseInt(valueMonthBD) > 12 || 1970 > parseInt(valueYearBD)) return false;
        else return true
      }
      return false;
    }

  },
  watch: {
    async radioDataFilter(val) {
      this.$root.loading(true)
      try {
        await this.onChangeRadioFilter(val)
      } catch (e) {
        this.$root.loading(false)
        throw e
      }

      this.$root.loading(false)
    }
  }
}
</script>
<style scoped>
.legend-title {
  display: flex;
  justify-content: space-between;
}

.title-sub,
.color-number-input input {
  color: #d32f2f !important;
}

.so-may-acc {
  background-color: #fff9eb;
}

.item-radio {
  margin-bottom: 20px;
}

.disableInput {
  cursor: pointer;
  pointer-events: none;
}

.date-icon {
  position: relative;
}
.icon-date {
  position: absolute;
  right: 16px;
  transform: translateY(-150%);
}
.icon-date i {
  font-size: 16px;
}

.text-bold {
  font-weight: 600 !important;
}

.somay {
  width: 105px;
}
.mask-date {
  padding-left: 10px;
}
.mask-date > span > input {
  margin-left: 5px;
  padding-left: 15px;
}
</style>

