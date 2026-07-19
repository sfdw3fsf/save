<template>
    <div class="box-form">
            <div class="legend-title active">
                <span class="icon fa fa-angle-up hand_click"></span> Thông tin tra cứu
            </div>
                    <div class="row">
                        <div class="col-sm-3 col-12">
                            <div class="info-row">
                                <div class="key">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" v-model="searchInfo.chkSoCv"
                                            v-on:change="thayDoiCheck(1)">
                                        <span class="name">Số công văn</span>
                                    </div>
                                </div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="form_temp.p_socongvan" @keyup.enter="traCuuCongVan"
                                        :disabled="!searchInfo.chkSoCv">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3 col-12">
                            <div class="info-row">
                                <div class="key">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" v-model="searchInfo.chkDonViPhatHanh"
                                            v-on:change="thayDoiCheck(2)">
                                        <span class="name">ĐV phát hành</span>
                                    </div>
                                </div>
                                <div class="value">
                                  <SelectExt v-model="form_temp.p_donvi_ph_id" dataTextField="ten_dv" dataValueField="donvi_id"
                                        :dataSource="donvi_phathanh.list" :disabled="!searchInfo.chkDonViPhatHanh" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w130 nowrap">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" v-model="searchInfo.chkLoaiCv"
                                            v-on:change="thayDoiCheck(5)">
                                        <span class="name">Loại công văn</span>
                                    </div>
                                </div>
                                <div class="value">
                                  <SelectExt v-model="form_temp.p_loai_cv_id" dataTextField="ten_loai" dataValueField="loaicv_id"
                                        :dataSource="loai_cv.list" :disabled="!searchInfo.chkLoaiCv" />


                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" v-model="searchInfo.chkTenCv"
                                            v-on:change="thayDoiCheck(3)">
                                        <span class="name">Tên công văn</span>
                                    </div>
                                </div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="form_temp.p_ten_congvan" @keyup.enter="traCuuCongVan"
                                        :disabled="!searchInfo.chkTenCv">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3 col-12">
                            <div class="info-row">
                                <div class="key w110 nowrap">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" v-model="searchInfo.chkNgayQd"
                                            v-on:change="thayDoiCheck(4)">
                                        <span class="name">Ngày quyết định</span>
                                    </div>
                                </div>
                                <div class="value">
                                    <vue-date format="DD/MM/YYYY" type="date" v-model="form_temp.p_tungay"
                                        :class="{'disabled': !searchInfo.chkNgayQd}"
                                        :disable="!searchInfo.chkNgayQd"></vue-date>
                                </div>
                            </div>

                        </div>
                        <div class="col-sm-3 col-12">
                            <div class="info-row">
                                <div class="key w80 nowrap">
                                    <span class="name">Đến ngày</span>
                                </div>
                                <div class="value">
                                    <vue-date format="DD/MM/YYYY" type="date" v-model="form_temp.p_denngay"
                                        :class="{'disabled': !searchInfo.chkNgayQd}"
                                        :disable="!searchInfo.chkNgayQd"></vue-date>
                                </div>
                            </div>
                    </div>
                </div>
            </div>
        </div>
  </template>
  <script>
  import api from '../api'
  export default {
    props: {
    traCuuCongVan: {
      type: Function,
      required: true
    },
    value: Object
  },
    data: () => ({
    p_donvi_ph_id: 0,
      options: {
        options_dsdv: [],
        options_dsdonvi: [],
        options_dslhd: []
      },
      donvi_phathanh:{
        list: [],
      },
      form: {
        p_socongvan:'',
        p_donvi_ph_id: 0,
        p_ten_congvan: '',
        p_loai_cv_id: 0,
        p_tungay: '0',
        p_denngay: '0',
        
        
      },
      loai_cv:{
        list: [],
      },
      searchInfo: {
          chkSoCv: false,
          chkDonViPhatHanh: false,
          chkTenCv: false,
          chkNgayQd: false,
          chkLoaiCv:false
      },
      form_temp: {
        p_denngay: '0',
        p_ten_congvan: '',
        p_loai_cv_id: 0,
        p_tungay: '0',
        p_socongvan: '',
        p_donvi_ph_id: 0
      }
    }),
    watch: {
    'form_temp.p_donvi_ph_id'(newValue) {
      // Nếu giá trị của p_donvi_ph_id là khác 0, đặt giá trị của checkbox thành true (1)
      if (newValue !== 0) {
        this.form_temp.p_donvi_ph_id = newValue;
      }
    }
  },
    // computed: {
    //   vmodel: {
    //     get() {
    //       return this.value
    //     },
    //     set(key, value) {
    //       this.$emit('input', this.form_temp)
    //     }
    //   }
    // },
    // props: {
    //   value: Object
    // },
    created() {
      this.$nextTick(async () => {
        this.form_temp = this.value
        this.loading(true)
        await this.getDVPhathanh()
        this.loading(false)
      })
    },
    mounted () {
        this.getDVPhathanh()
        //load loại công văn
        api.lay_loai_cv(this.axios, {}).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.loai_cv.list = response.data.data
            }
        }
        );
    },
    methods: {
      validate() {
  
      },
      thayDoiCheck(num) {
            //1: số công văn; 2: đơn vị phát hành; 3: tên công văn; 4: ngày quyết định
            // 5: tra cứu theo; 6. đơn vị bán hàng; 7.phân loại hđ
            if (num == 1) this.chkSoCv = !this.chkSoCv;
            if (num == 2) this.chkDonViPhatHanh = !this.chkDonViPhatHanh;
            if (num == 3) this.chkTenCv = !this.chkTenCv;
            if (num == 4) this.chkNgayQd = !this.chkNgayQd;
            if (num == 5) this.chkLoaiCV = !this.chkLoaiCV;
        },

      getDVPhathanh(){
      var pdata = {};
      api.lay_danhsach_donvi_phathanh(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.donvi_phathanh.list = response.data.data;
        }
      });
    },
    }
  }
  </script>
  