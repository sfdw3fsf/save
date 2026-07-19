<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>Chọn mã thuê bao lắp kèm</div>
      <div class="close -ap icon-close hidden" data-dismiss="modal">
      </div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="closeForm"
             :class="{ disabled:!(DSTBTruyNhap.list && DSTBTruyNhap.list.length>0) }">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận (F5)
          </a>
        </li>
        <li :class="{hidden:!btnThoat}">
          <a href="#" @click.prevent="terminateForm">
            <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="row">
        <div class="col-12">
          <div class="box-form">
            <!-- <div class="legend-title mart20">{{tieudebang}}</div> -->
            <DataGrid2
              v-bind:columns="DSTBTruyNhap.cols"
              v-bind:dataSource="DSTBTruyNhap.list" :showFilter="false" :showColumnCheckbox="false"
              :dataKeyField="DSTBTruyNhap.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="200"
              @selectedRowChanged="rowChanged">
            </DataGrid2>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>

</style>
<script>
import { FN_LAY_DS_THUEBAO_THEO_HDKHID, FN_LAY_DS_THUEBAO_THEO_KHACHHANGID } from '../../../z_common_func/setup_common'
import api from '../../../../complete_profile/CompleteProfileITVAS/API/CompleteProfileAPI'

export default {
  name: 'popupDSTBLapKem',
  props: ['dulieu'],
  watch: {},
  data() {
    return {
      ds: {
        list: [],
        cols: [],
        keycol: '',
        value: {}
      },
      hdkh_id: 0,
      vkhachhang_id: 0,
      vkieuld_id: 0,
      loaitb_id: 0,
      btnThoat: true,
      vkieu: 0,
      DSTBTruyNhap: {
        list: [],
        value: {},
        cols: [
          { fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: true },
          { fieldName: 'MA_TB', headerText: 'Số máy / Acc', allowFiltering: true, allowSorting: true },
          { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, allowSorting: true },
          // {fieldName: 'MADOICAP', headerText: 'Mã đôi cáp', allowFiltering: true, allowSorting: true, },
          { fieldName: 'TEN_KIEULD', headerText: 'Kiểu lắp đặt', allowFiltering: true, allowSorting: true }
        ]
      }
    }
  },
  async mounted() {

    console.log('this.dulieu: ' )
    console.log(this.dulieu)


    if (this.dulieu.hdkh_id) this.hdkh_id = this.dulieu.hdkh_id
    if (this.dulieu.khachhang_id) this.vkhachhang_id = this.dulieu.khachhang_id
    if (this.dulieu.kieuld_id) this.vkieuld_id = this.dulieu.kieuld_id
    if (this.dulieu.loaitb_id) this.loaitb_id = this.dulieu.loaitb_id
    if (this.dulieu.kieu) this.vkieu = this.dulieu.kieu

    this.$root.showLoading(true)
    if (this.dulieu.bancheo) {
      const UpperCasePropertyList = (obj) => {
        return obj.map(function(item) {
          for (let key in item) {
            let upper = key.toUpperCase()
            // check if it already wasn't uppercase
            if (upper !== key) {
              item[upper] = item[key]
              delete item[key]
            }
          }
          return item
        })
      }

      var rs = await this.$root.context.post('/web-bancheo/tracuu/lay_ds_somay_tn_theo_hdkhid', {
        hdkh_id: this.hdkh_id,
        tinhthicong_id: this.dulieu.tinhthicong_id
      })
      if (rs && rs.data) {
        this.DSTBTruyNhap.list = UpperCasePropertyList(rs.data)
        this.rowChanged(0, this.DSTBTruyNhap.list[0])
      } else {
        this.$toast.error(`${rs.message}: ${rs.message_detail}`)
      }
    } else {
      // var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/fn_lay_ds_somay_tn_lapkem",{"ds_para": JSON.stringify({"HDKH_ID":this.hdkh_id,"KHACHHANG_ID":this.vkhachhang_id,"KIEULD_ID":this.vkieuld_id})});
      // var kq = JSON.parse(rs.data);
      // if(kq && kq['ERROR_CODE']==1) {
      //   this.DSTBTruyNhap.list = kq['RESULT']['DS_SM_TN'];
      //   this.rowChanged(0,this.DSTBTruyNhap.list[0])
      // }
      // else this.$toast.error(kq['MESSAGE']);
      if (this.dulieu.kieu == 1) {
        await this.FN_LAY_DS_THUEBAO_THEO_HDKHID()
      } else {
        await this.FN_LAY_DS_THUEBAO_THEO_KHACHHANGID()
      }
    }
    this.$root.showLoading(false)
  },
  computed: {},
  methods: {
    async closeForm() {

      // this.$root.showLoading(true);
      // if(this.dulieu.bancheo) {
      //   this.$root.showLoading(false);
      //   this.$emit('form-close',this.DSTBTruyNhap.value);
      //   return;
      // }
      // var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/fn_kt_tt_dstb_lapkem",{"ds_para": JSON.stringify({"LOAITB_ID":this.loaitb_id,"MADOICAP":this.DSTBTruyNhap.value['MADOICAP'],"MA_TB":this.DSTBTruyNhap.value['MA_TB']})});
      // var kq = JSON.parse(rs.data);
      // this.$root.showLoading(false);
      // if(kq && kq['ERROR_CODE']==1 && kq['RESULT']['CHAPNHAN']){
      //   console.log(',this.DSTBTruyNhap.value')
      //   console.log(this.DSTBTruyNhap.value)
      //   this.$emit('form-close',this.DSTBTruyNhap.value);
      // }
      // else if(kq && kq['ERROR_CODE']==-2) {// && kq['RESULT']['CHAPNHAN']) {
      //   var cfr = await this.$bvModal.msgBoxConfirm(kq['MESSAGE']+'', {
      //     title: 'Thông báo',
      //     size: 'sm',
      //     okTitle: 'Đồng ý',
      //     cancelTitle: 'Hủy',
      //   }).then(v => {
      //     return v;
      //   });
      //   if (cfr)
      //   {
      this.$emit('form-close', this.DSTBTruyNhap.value)
      //   }
      // }
      // else {
      //   this.$toast.error(kq['MESSAGE']);
      // }
    },
    terminateForm() {
      this.$emit('form-close', null)
    },
    rowChanged(item) {
      this.DSTBTruyNhap.value = item
    },

    async FN_LAY_DS_THUEBAO_THEO_HDKHID() {
      try {
        this.$root.showLoading(true)

        const loaitb_sudung_api = await this.ConvertResApiHBDT(
          api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
            p_nghiepvu: 'LAY_LHTB_BOSUNG',
            p_ds_para: '{' +
              'LOAITB_ID: ' + this.loaitb_id +
              '}'
          })
        )

        let obj = {
          'vhdkh_id': this.hdkh_id,
          'vloaitb_id': loaitb_sudung_api[0].ketqua,
          'vkieuld_id': this.vkieuld_id
        }
        let rs = await FN_LAY_DS_THUEBAO_THEO_HDKHID(obj)

        console.log('FN_LAY_DS_THUEBAO_THEO_HDKHID')
        console.log(rs)
        if (rs) {
          this.DSTBTruyNhap.list = rs
          // this.rowChanged(0,this.DSTBTruyNhap.list[0])
        }
      } catch (err) {
        this.$toast.error(err)
      } finally {
        this.$root.showLoading(false)
      }
    },
    async FN_LAY_DS_THUEBAO_THEO_KHACHHANGID() {
      try {
        this.$root.showLoading(true)

        const loaitb_sudung_api = await this.ConvertResApiHBDT(
          api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
            p_nghiepvu: 'LAY_LHTB_BOSUNG',
            p_ds_para: '{' +
              'LOAITB_ID: ' + this.loaitb_id +
              '}'
          })
        )

        let obj = {
          'vkhachhang_id': this.vkhachhang_id,
          'vloaitb_id': loaitb_sudung_api[0].ketqua,
          'vkieuld_id': this.vkieuld_id
        }

        console.log('FN_LAY_DS_THUEBAO_THEO_KHACHHANGID')
        console.log(obj)
        let rs = await FN_LAY_DS_THUEBAO_THEO_KHACHHANGID(obj)

        console.log(rs)
        if (rs) {
          this.DSTBTruyNhap.list = rs
          // this.rowChanged(0,this.DSTBTruyNhap.list[0])
        }
      } catch (err) {
        this.$toast.error(err)
      } finally {
        this.$root.showLoading(false)
      }
    },

    async ConvertResApiHBDT(response_input) {
      let response = await response_input
      if (response.data.errorCode && response.data.errorCode != '' && response.data.errorCode != '0' && response.data.faultString && response.data.faultString != '') {
        throw response.data.faultString
      }
      if (response.data.error === 200 || response.data.error === '200' || response.data.errorCode === 0) {
        if (response.data.error_code === 'BSS-00000000') {
          return response.data.data
        }
      } else {
        if (response.data.error === '204' || response.data.error === 204) {
          return []
        } else {
          return response.data.message
        }
      }
    },
  }
}
</script>
