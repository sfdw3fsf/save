<template>
   <b-modal
    id="suaLuongPhieu"
    title="Sửa luồng phiếu"
    modal-class="sua-luong-phieu"
    header-class="popup-header"
    content-class="sua-luong-phieu-content"
    body-class="sua-luong-phieu-body"
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
            <div class="info-row" v-if="cboDonViCha.list.length > 0">
                <div class="key">Loại đơn vị cha</div>
                <div class="value">
                    <div class="select-custom">
                        <Select2 ref="nhomQuyTrinh" :disabled='false' v-model="cboDonViCha.value"
                             :options="cboDonViCha.list" :settings="settingSelect2"/>
                    </div>
                </div>
            </div>
            <div class="info-row" v-if="cboDonViCon.list.length > 0">
                <div class="key">Loại đơn vị con</div>
                <div class="value">
                    <div class="select-custom">
                        <Select2 ref="nhomQuyTrinh" :disabled='false' v-model="cboDonViCon.value"
                             :options="cboDonViCon.list" :settings="settingSelect2"/>
                    </div>
                </div>
            </div>
            <div class="info-row" v-if="cboHuongGiao.list.length > 0">
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
  props: ['quyTrinhId', 'dtTemp', 'loaidv_id_con', 'loaidv_id_cha', 'huonggiao_id', 'loaidv_id', 'stt'],
  name: 'frmSuaLuongPhieu',
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
      this.dtTempData = val
    },
    loaidv_id_con(val) {
      this.loaidvIdCon = val
    },
    loaidv_id_cha(val) {
      this.loaidvIdCha = val
    },
    huonggiao_id(val) {
      this.huonggiaoId = val
    },
    loaidv_id(val) {
        this.loaidv_id = val
    }
  },
  mounted () {
    this.frmSuaLuongPhieu_Load()
  },
  methods: {
    show() {
      this.$bvModal.show("suaLuongPhieu")
      this.frmSuaLuongPhieu_Load()
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
    async frmSuaLuongPhieu_Load() {
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
            if (this.loaidv_id != 0) {
                this.cboDonVi.value = this.loaidv_id
            } else {
                this.cboDonVi.value = this.cboDonVi.list[0].id
            }
          }
          if (data.RESULT.HUONGGIAO && data.RESULT.HUONGGIAO.length > 0) {
            this.cboHuongGiao.list = data.RESULT.HUONGGIAO.map(item => {
              return {
                'id' : item.HUONGGIAO_ID,
                'text' : item.HUONGGIAO
              }
            })
            if (this.huonggiao_id != 0) {
                this.cboHuongGiao.value = this.huonggiao_id
            } else {
                this.cboHuongGiao.value = this.cboHuongGiao.list[0].id
            }
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
            this.ShowAlert("Chưa chọn loại đơn vị !");
            return;
        }
        if (this.cboHuongGiao.value == "")
        {
            this.ShowAlert("Hãy chọn hướng giao!");
            return;
        }
        if (this.cboDonViCha.value != 0)
        {
            if (this.cboDonVi.value == this.cboDonViCha.value)
            {
                this.ShowAlert("Loại đơn vị cần thêm không được trùng với loại đơn vị cha!");
                return;
            }
        }
        if (this.cboDonViCon.value != 0)
        {
            if (this.cboDonVi.value == this.cboDonViCon.value)
            {
                this.ShowAlert("Loại đơn vị cần thêm không được trùng với loại đơn vị con!");
                return;
            }
        }
        if (this.stt > 1)
        {
            if (this.cboDonViCha.value == null)
            {
                this.ShowAlert("Chưa chọn loại đơn vị nguồn !");
                return;
            }
            if (this.cboHuongGiao.value == null)
            {
                this.ShowAlert("Chưa chọn hướng giao !");
                return;
            }
        }
        if (this.dtTemp.length > 1)
        {
            if (this.cboDonViCon.list.length > 0)
            {
                if (this.huonggiaoId != this.cboHuongGiao.value)
                {
                    for (let j = 0; j < this.dtTemp.length - 1; j++)
                    {
                        let in_huonggiao_id = this.dtTemp[j].huonggiao_id;
                        if (this.cboHuongGiao.value == in_huonggiao_id)
                        {
                            this.ShowAlert("Trùng hướng giao. ID:" + in_huonggiao_id + ". " + this.dtTemp[j]["huonggiao"].ToString());
                            return;
                        }
                    }
                }
            }
        }

        if (this.dtTemp.lenght > 1)
        {
            if (this.huonggiaoId != this.cboHuongGiao.value)
            {
                for (let i = 0; i < this.dtTemp.length - 1; i++)
                {
                    let vhuonggiao_id = this.dtTemp[i].huonggiao_id
                    for (let j = 0; j < this.dtTemp.length - 1; j++)
                    {
                        if (j != i)
                        {
                            let in_huonggiao_id = this.dtTemp[j].huonggiao_id
                            if (vhuonggiao_id == in_huonggiao_id)
                            {
                                this.ShowAlert("Trùng hướng giao. ID:" + in_huonggiao_id + ". " + dtTemp.Rows[j]["huonggiao"].ToString());
                                return;
                            }
                        }
                    }
                }
            }
        }
        // dtTemp
        let dr = [];
        let dr_nguon = [];
        let dr_dich = []
        //dr = dtTemp.Select("loaidv_id = '" + _loaidv_id + "' and loaidv_dich_id = '" + _loaidv_id_con + "' and loaidv_nguon_id = '" + _loaidv_id_cha + "'");
        dr = this.dtTemp.filter(item => {
            return item.loaidv_id == this.loaidv_id && item.loaidv_dich_id == this.loaidv_id_con && item.loaidv_nguon_id == this.loaidv_id_cha
        })
        //dr_nguon = dtTemp.Select("loaidv_id = '" + _loaidv_id_cha + "' and loaidv_dich_id = '" + _loaidv_id + "'");
        dr_nguon = this.dtTemp.filter(item => {
            return item.loaidv_id == this.loaidv_cha_id && item.loaidv_dich_id == this.loaidv_id
        })
        //dr_dich = dtTemp.Select("loaidv_id = '" + _loaidv_id_con + "' and loaidv_nguon_id = '" + _loaidv_id + "'");
        dr_dich = this.dtTemp.filter(item => {
            return item.loaidv_id == this.loaidv_id_con && item.loaidv_nguon_id == this.loaidv_id
        })
        if (dr.length > 0)
        {
            if (this.loaidv_id != this.cboDonVi.value)
            {
                dr[0]["loaidv_id"] = this.cboDonVi.value;
                let textDonVi = this.cboDonVi.list.filter(item => {
                    return item.id == this.cboDonVi.value
                })
                dr[0]["ten_loaidv"] = textDonVi[0].text;
            }

            if (this.huonggiaoId != this.cboHuongGiao.value)
            {
                dr[0]["huonggiao_id"] = this.cboHuongGiao.value;
                let textHuongGiao = this.cboHuongGiao.list.filter(item => {
                    return item.id == this.cboHuongGiao.value
                })
                dr[0]["huonggiao"] = textHuongGiao[0].text
            }
            if (dr_nguon.length > 0)
            {
                dr_nguon[0]["loaidv_dich_id"] = this.cboDonVi.value;
            }
            if (dr_dich.length > 0)
            {
                dr_dich[0]["loaidv_nguon_id"] = this.cboDonVi.value;
            }
        }

    //   this.$emit('update-luong-phieu', this.dtTemp)
      this.$bvModal.hide("suaLuongPhieu")
      console.log('dt temp is ', this.dtTemp)
    }
  }
}
</script>
