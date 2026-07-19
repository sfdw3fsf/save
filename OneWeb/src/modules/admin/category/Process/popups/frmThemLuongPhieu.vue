<template>
   <b-modal
    id="themLuongPhieu"
    title="Thêm luồng phiếu"
    modal-class="them-luong-phieu"
    header-class="popup-header"
    content-class="them-luong-phieu-content"
    body-class="them-luong-phieu-body"
    size="md"
    hide-footer
  >
    <div class="popup-body">
        <div class="box-form">
            <div class="info-row" v-if="cboDonVi.list.length > 0">
                <div class="key">Loại đơn vị</div>
                <div class="value">
                    <div class="select-custom">
                        <Select2 ref="nhomQuyTrinh" :disabled='false' v-model="cboDonVi.value"
                             :options="cboDonVi.list" :settings="settingSelect2"/>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key">Loại đơn vị cha</div>
                <div class="value">
                    <div class="select-custom">
                        <Select2 ref="nhomQuyTrinh" :disabled='false' v-model="cboDonViCha.value"
                             :options="cboDonViCha.list" :settings="settingSelect2"/>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key">Loại đơn vị con</div>
                <div class="value">
                    <div class="select-custom">
                        <Select2 ref="nhomQuyTrinh" :disabled='false' v-model="cboDonViCon.value"
                             :options="cboDonViCon.list" :settings="settingSelect2"/>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key">Hướng giao</div>
                <div class="value">
                    <div class="select-custom">
                        <Select2 ref="nhomQuyTrinh" :disabled='false' v-model="cboHuongGiao.value"
                             :options="cboHuongGiao.list" :settings="settingSelect2"/>
                    </div>
                </div>
            </div>
            <div class="group-buttons tright">
                <button class="btn btn-main" @click="btnChapNhan_Click">
                    <span class="nc-icon-glyph ui-1_check-circle-08 f20 inline vcenter lh20"></span> Chấp nhận
                </button>
            </div>
        </div>
    </div>
  </b-modal>
</template>
<script>
import moment from "moment";
import Vue from 'vue'
import API from '../API'
export default {
  props: ['quyTrinhId', 'dtTemp', 'loaidv_id_con', 'loaidv_id_cha', 'huonggiao_id'],
  name: 'frmThemLuongPhieu',
  data() {
    return {
      chapnhan : false,
      loaidv_id : 0,
      loaidv_cha_id : 0,
      loaidv_con_id : 0,
      quytrinh_id : 0,
      loaidvIdCha : 0,//Loại đơn vị nguồn
      loaidvIdCon : 0,//Loại đơn vị đích
      huonggiaoId : 0,//Hướng giao từ loại đơn vị giao đi
      dtTempData : [],
      settingSelect2: {
        language: 'vi'
      },
      cboLoaiHinhThueBao : {
        list :[],
        value : "",
        disable : false
      },
      cboDonVi : {
        list : [],
        value : 0
      },
      cboDonViCha : {
        list : [],
        value : 0,
      },
      cboDonViCon : {
        list : [],
        value : 0,
      },
      cboHuongGiao : {
        list : [],
        value : 0,
      }
    }
  },
  watch: {
    quyTrinhId(val) {
      this.quytrinh_id = val
    },
    dtTemp(val) {
      // console.log('this.dtTempData is ', this.dtTempData)
      this.dtTempData = val
    },
    loaidv_id_con(val) {
      this.loaidvIdCon = val
      this.frmThemLuongPhieu_Load()
    },
    loaidv_id_cha(val) {
      this.loaidvIdCha = val
      this.frmThemLuongPhieu_Load()
    },
    huonggiao_id(val) {
      this.huonggiaoId = val
    }
  },
  mounted () {
    //this.frmThemLuongPhieu_Load()
  },
  methods: {
    show() {
      this.$bvModal.show("themLuongPhieu")
      this.frmThemLuongPhieu_Load()
    },
    ShowAlert(mesage)
    {
      this.$toast.error(mesage);
    },
    showSuccess(mesage) {
      this.$toast.success(mesage);
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async frmThemLuongPhieu_Load() {
        let obj = {
            "LOAIDV_ID_CHA" : this.loaidv_id_cha, 
            "LOAIDV_ID_CON" : this.loaidv_id_con, 
            "QUYTRINH_ID" : this.quyTrinhId
        }

        let input = {
          "p_ds_para" : JSON.stringify(obj)
        }

        let data = this.GetData(await API.fn_lay_tt_themluong_phieu(this.axios, input))
        data = JSON.parse(data)
        if (data && data.ERROR_CODE) {
          if (data.RESULT.LOAI_DVI && data.RESULT.LOAI_DVI.length > 0) {
            this.cboDonVi.list = data.RESULT.LOAI_DVI.map(item => {
              return {
                'id' : item.LOAIDV_ID,
                'text' : item.TEN_LOAIDV
              }
            })
            this.cboDonVi.value = this.cboDonVi.list[0].id
          }
          if (data.RESULT.HUONGGIAO && data.RESULT.HUONGGIAO.length > 0) {
            this.cboHuongGiao.list = data.RESULT.HUONGGIAO.map(item => {
              return {
                'id' : item.HUONGGIAO_ID,
                'text' : item.HUONGGIAO
              }
            })
            this.cboHuongGiao.value = this.cboHuongGiao.list[0].id
          }

          if (data.RESULT.LOAI_DVI_CHA && data.RESULT.LOAI_DVI_CHA.length > 0) {
            this.cboDonViCha.list = data.RESULT.LOAI_DVI_CHA.map(item => {
              return {
                'id' : item.LOAIDV_ID,
                'text' : item.TEN_LOAIDV
              }
            })
            this.cboDonViCha.value = this.cboDonViCha.list[0].id
          }

          if (data.RESULT.LOAI_DVI_CON && data.RESULT.LOAI_DVI_CON.length > 0) {
            this.cboDonViCon.list = data.RESULT.LOAI_DVI_CON.map(item => {
              return {
                'id' : item.LOAIDV_ID,
                'text' : item.TEN_LOAIDV
              }
            })
            this.cboDonViCon.value = this.cboDonViCon.list[0].id
          }
        }
    },
    btnChapNhan_Click() {
      if (this.cboDonVi.value == 0)
      {
          this.ShowAlert('Chưa chọn loại đơn vị !')
          return;
      }
      if (this.cboHuongGiao.list[this.cboHuongGiao.value] == "")
      {
          this.ShowAlert('Hãy chọn hướng giao!')
          return;
      }
      if (this.cboDonViCha.list.length > 0)
      {
          if (this.cboDonVi.value == this.cboDonViCha.value)
          {
              this.ShowAlert('Loại đơn vị cần thêm không được trùng với loại đơn vị cha!')
              return;
          }
      }
      if (this.cboDonViCon.length > 0)
      {
          if (this.cboDonVi.value == this.cboDonViCon.value)
          {
              this.ShowAlert('Loại đơn vị cần thêm không được trùng với loại đơn vị con!')
              return;
          }
      }
      if (this.dtTempData && this.dtTempData.length > 0)
      {
          if (this.cboDonViCha.value == null)
          {
            this.ShowAlert('Chưa chọn loại đơn vị nguồn !')
            return;
          }
          if (this.cboHuongGiao.value == null)
          {
            this.ShowAlert('Chưa chọn hướng giao !')
            return;
          }
      }
      if (this.dtTempData && this.dtTempData.length > 1)
      {
          if (this.cboDonViCon.length > 0)
          {
              for (let j = 0; j < this.dtTempData.length - 1; j++)
              {
                  let in_huonggiao_id = this.dtTempData[j].huonggiao_id;
                  if (this.cboHuongGiao.value == in_huonggiao_id)
                  {
                    this.ShowAlert("Trùng hướng giao. ID:" + in_huonggiao_id + ". " + this.dtTempData[j].huonggiao)
                    return;
                  }
              }
          }
      }

      if (this.dtTempData && this.dtTempData.length > 1)
      {
          for (let i = 0; i < this.dtTempData.length - 1; i++)
          {
              let vhuonggiao_id = this.dtTempData[i].huonggiao_id
              for (let j = 0; j < this.dtTempData.length - 1; j++)
              {
                  if (j != i)
                  {
                      let in_huonggiao_id = this.dtTempData[j].huonggiao_id;
                      if (vhuonggiao_id == in_huonggiao_id)
                      {
                        this.ShowAlert("Trùng hướng giao. ID:" + in_huonggiao_id + ". " + this.dtTempData[j].huonggiao)
                        return;
                      }
                  }
              }
          }
      }

      let loaidv_id = this.cboDonVi.value
      let loaidv_cha_id = 0
      let loaidv_con_id = 0
      if (this.cboDonViCha.list.length > 0)
      {
          loaidv_cha_id = this.cboDonViCha.value
      }
      if (this.cboDonViCon.list.length > 0)
      {
          loaidv_con_id = this.cboDonViCon.value
      }
      let drcha = []
      let dr = []
      let drcon = []
      if (loaidv_cha_id != 0)
      {
          // drcha = dtTemp.Select("loaidv_id = '" + loaidv_cha_id + "'");
          drcha = this.dtTempData.filter(item => {
            return item.loaidv_id == loaidv_cha_id
          })
          if (drcha.Length > 0) {
            drcha[0].loaidv_dich_id = loaidv_id;
          }
      }

      if (loaidv_con_id != 0)
      {
          // drcon = dtTemp.Select("loaidv_id = '" + loaidv_con_id + "'");
          drcon = this.dtTempData.filter(item => {
            return item.loaidv_id == loaidv_con_id
          })
          if (drcon.Length > 0)
              drcon[0].loaidv_nguon_id = loaidv_id;
      }
      let huongGiaoText = ""
      let donViText = ""
      if (this.cboHuongGiao.list.length > 0) {
        let huongGiaoArr = this.cboHuongGiao.list.filter(item => {
          return item.id == this.cboHuongGiao.value
        })
        huongGiaoText = huongGiaoArr[0].text ? huongGiaoArr[0].text : ''
      }
      if (this.cboDonVi.list.length > 0) {
        let donViArr = this.cboDonVi.list.filter(item => {
          return item.id == loaidv_id
        })
        donViText = donViArr[0].text ? donViArr[0].text : ''
      }
      this.dtTempData.push({
        'loaidv_id' : loaidv_id,
        'loaidv_nguon_id' : loaidv_cha_id,
        'loaidv_dich_id' : loaidv_con_id,
        'huonggiao_id' : this.cboHuongGiao.value,
        'huonggiao' : huongGiaoText,
        'ten_loaidv' : donViText,
        'luong_id' : 0
      })
      console.log('this.dtTemp is 111 ', this.dtTempData)
      this.$emit('update-luong-phieu', this.dtTempData)
      this.$bvModal.hide("themLuongPhieu")
    }
  }
}
</script>
