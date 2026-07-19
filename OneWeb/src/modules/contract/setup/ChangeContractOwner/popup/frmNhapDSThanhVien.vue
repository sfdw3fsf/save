<template>
  <ejs-dialog target=".main-wrapper" :position="{ X: 'center', Y: 'top' }" :visible="false" ref="frmNhapDSThanhVien" width="50%" id="frmNhapDSThanhVien_form" :showCloseIcon="false" :header="headerTemplate" style="z-index=1000">
    <div class="popup-content">
      <div class="list-actions-top">
        <ul class="list">
          <!-- <li class="font-weight-bold ml-3">NHẬP DANH SÁCH SỐ HIỂN THỊ</li> -->
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="row">
            <div class="col-sm-6">
              <div class="info-row">
                <div class="key w90">Nhập SĐT</div>
                <div class="value">
                  <input v-if="donvikg_id != 17" ref="txtSoDT" type="text" class="form-control" v-model="so_tb"/>
                  <div v-else-if="donvikg_id == 17" class="form-group">
                    <select2 placeholder="Chọn số" :options="list_stk_cd" id="input-datalist" :allow-edit="true" v-model="so_tb" class="select-custom" style="width: 200px; margin-right: 20px" @change="cboChonSoChanged" />
                  </div>
                </div>
                <div class="key w90" v-show="!hide_cf">Số CF</div>
                <div class="value" v-show="!hide_cf">
                  <input ref="txtSoCF" type="text" class="form-control" />
                </div>

                <div class="key w90" v-show="donvikg_id ==17">Địa chỉ IP</div>
                <div class="value" v-show="donvikg_id ==17" >
                  <input ref="txtDiachiIP" width="100%" v-model="ip" type="text" class="form-control" />
                </div>
              </div>
            </div>
            <div class="col-sm-6">
              <div class="btn-group" role="group">
                <button type="button" class="btn btn-light border" @click="btnGhiLai_Click">
                  Ghi lại
                </button>
                <button style="display: none" type="button" @click="btnSua_Click" class="btn btn-light border">
                  Sửa
                </button>
                <button type="button" @click="btnXoa_Click" class="btn btn-light border">
                  Xóa
                </button>
                <button @click="chooseFiles()" type="button" class="btn btn-light border">
                  Import Excel
                </button>
                <input ref="fileUpload" type="file" @click="$refs.fileUpload.value = ''" @change="onChange" hidden />
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="table-content">
            <DataGridCustom v-bind:columns="columns" ref="grcDS" :showFilter="false" :allowPaging="false" @rowSelected="grvDS_FocusedRowChanged" :enablePagingServer="false" :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }" panelDataHeight="80"></DataGridCustom>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Select2Ext from '@/modules/contract/setup/DeclareLandline/components/Select2Ext'
import DataGridCustom from '@/modules/contract/setup/DeclareLandline/components/DataGridCustom'
import commonFn from '@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions'
import Vue from 'vue'
import XLSX from 'xlsx'
var headerTemplateVue = Vue.component('demo1', {
  template:
    '<span><span class="title font-weight-bold" id="frmNhapDSThanhVienTitle">Danh sách số hiển thị</span><button id="closeButton" class="e-dlg-closeicon-btn e-control e-btn e-lib e-flat e-icon-btn" type="button" title="Close" aria-label="Close"><span class="e-btn-icon e-icon-dlg-close e-icons"></span></button></span>',
  data() {
    return {
      data: {}
    }
  }
})

import moment from 'moment'
export default {
  components: {
    Select2Ext,
    DataGridCustom
  },
  data() {
    return {
      headerTemplate: function() {
        return { template: headerTemplateVue }
      },
      hdtb_id: 0,
      tdan: {},
      dt: [],
      file: null,
      donvikg_id: 0,
      hdtb_ct_id: null,
      so_tb: '',
      sodt_khonghople: '',
      hide_cf: false,
      list_stk_cd: [],
      is_kh_daily: false,
      ip: null,
      columns: [
        {
          fieldName: 'HDTB_ID',
          headerText: 'ID',
          allowSorting: false,
          width: '70',
          textAlign: 'center',
          autoFit: 'true'
        },
        {
          fieldName: 'SO_DT_CT',
          headerText: 'Số điện thoại',
          allowSorting: false,
          width: '100',
          textAlign: 'center',
          autoFit: 'true'
        },
       
      ]
    }
  },
  mounted() {},
  props: ['_ds_hdtbct'],
  methods: {
    ShowDialog() {
      this.$nextTick(() => {
        this.frmNhapDSThanhVien_Load()
        document.getElementById('closeButton').addEventListener('click', () => {
          this.closeForm()
        })
        this.$refs.frmNhapDSThanhVien.show()
      })
    },
    closeForm() {
      console.log('vào close form')
      this.$emit('frmNhapDSThanhVien_close', false)
      this.$refs.frmNhapDSThanhVien.hide()
    },
    cboChonSoChanged(e) {
      try {
        const ip = this.list_stk_cd.filter(x => x.id == e)[0].ip;
        console.log(ip, '✅IP')
        this.ip = ip;
      } catch (ex) {
        // this.$toast.error(ex.toString());
      }
    },
    async frmNhapDSThanhVien_Load() {
      return new Promise(async (resole, reject) => {
        let rs = await this.axios.get('web-hopdong/nhap_ds_thanh_vien/sp_lay_hdtb_ct_theo_hdtb_id', {
          params: {
            hdtb_id: this.hdtb_id
          }
        })
        var main_datasource = []
        if (this._ds_hdtbct && this._ds_hdtbct.length > 0) {
          main_datasource = this._ds_hdtbct
          console.log('1')
        } else {
          main_datasource = rs.data && rs.data.data ? this.ConvertUpper(rs.data.data) : []
          // this._ds_hdtbct = main_datasource
        }
        const req = {
          P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
          p_nghiepvu: 'LAY_DULIEU_CNTT',
          p_ds_para: JSON.stringify({ HDTB_ID: this.hdtb_id, TINH_ID: this.$root.token.getPhanVungID() })
        }
        const hdtb_cntt = (await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', req)).data.data[0]
        this.donvikg_id = hdtb_cntt['donvikg_id']
        const loaitb_id = hdtb_cntt['loaitb_id'];


        if (this.donvikg_id == 11 || this.donvikg_id == 12 || loaitb_id == 369 || this.donvikg_id == 14) {
          this.hide_cf = true
          this.columns.splice(2, 1)
        } else if (this.donvikg_id == 17) {
          this.hide_cf = true
          this.columns.splice(2, 1)
       
          if(this.list_stk_cd.length == 0 ) {
            this.dt = (await this.axios.post('/web-thicong/dvcntt/lay_tt_siptrunking_cd', { mst:  hdtb_cntt['mst'] })).data.data
            this.list_stk_cd = this.dt
            .filter((item) => item.kt_chunhom != 1)
            .filter((item) => !main_datasource.some((i) => i.SO_DT_CT == item.so_tb))
            .map((item) => ({ id: item.so_tb, text: item.so_tb, ip: item.ip }))
           
          }
          if(this.columns.filter(x=> x.fieldName == "EMAIL_CT" ). length ==0 )
          this.columns.push( {
              fieldName: 'EMAIL_CT',
              headerText: 'Địa chỉ IP',
              allowSorting: false,
              width: '100',
              textAlign: 'center',
              autoFit: 'true'
            })
            ;
            this.cboChonSoChanged(this.so_tb);
         
        }
        this.$refs.grcDS.dataSource = main_datasource
    

        if (this.hide_cf != true) {
          let res = await this.axios.get('web-hopdong/vbn/layso_cf', {
            params: {
              hdtb_id: this.hdtb_id
            }
          })
          if (res.data && res.data.data && res.data.data.length > 0) {
            this.$refs.txtSoCF.value = res.data.data[0]['somay']
          }
        }

        //fix lỗi _this._ds_hdtbct undefine không push được
        if(!this._ds_hdtbct) {
          this._ds_hdtbct = [];
        }

        resole(true)
      })
    },

    ConvertLower(arr) {
      return arr.map(function(item) {
        return Object.fromEntries(Object.entries(item).map(([k, v]) => [k.toLowerCase(), v]))
      })
    },

    ConvertUpper(arr) {
      return arr.map(function(item) {
        return Object.fromEntries(Object.entries(item).map(([k, v]) => [k.toUpperCase(), v]))
      })
    },
    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },

    async btnGhiLai_Click() {
      if (!this.so_tb) {
        this.$toast.error('Chưa nhập số điện thoại, vui lòng nhập số điện thoại trước khi ghi lại')
        return
      }
      //"/(84|0[3|5|7|8|9])+([0-9]{8})\b/g"
      let isphone = this.so_tb.match('(84)+([0-9]{8,10})')
      if (!isphone) {
        this.$toast.error('Số điện thoại không đúng định dạng 84xxxxxxxxx. Hãy kiểm tra lại.')
        return
      }
      //  //kiem tra meg
      //  let response_kt_meg = await this.axios.post('/ccbs/executor/meg_laytt_tb', { so_tb: this.so_tb });
      //   if (response_kt_meg.data.data.length == 0) {
      //     this.$toast.error('Số thuê bao chủ nhóm không phải là số chính (số hotline) của dịch vụ MEG, đề nghị kiểm tra lại');
      //     this.sodt_khonghople += vso_dt + ";";
      //     return;
      //   }

      // let this.donvikg_id = await commonFn.MAP_ID(this.axios, 'this.donvikg_id', 'css.hdtb_cntt', 'where hdtb_id=' + this.hdtb_id);

      console.log(this.donvikg_id)
      if (this.donvikg_id == 12) {
        let response_kt_meg = await this.axios.post('/ccbs/executor/meg_laytt_tb', { so_tb: this.so_tb })
        if (response_kt_meg.data.data.length == 0) {
          this.$toast.error('Số thuê bao chủ nhóm không phải là số chính (số hotline) của dịch vụ MEG, đề nghị kiểm tra lại')
          this.sodt_khonghople += this.so_tb + ';'
          return
        }
      } else if (this.donvikg_id == 11) {
        let response_kt_vcc = await this.axios.post('/ccbs/executor/ts_vcc_lay_tttb', { so_tb: this.so_tb })
        if (response_kt_vcc.data.data.length == 0) {
          this.$toast.error('Số thuê bao chủ nhóm không phải là số chính (số hotline) của dịch vụ VCC, đề nghị kiểm tra lại')
          this.sodt_khonghople += this.so_tb + ';'
          return
        } 
      } else if(this.donvikg_id != 17) {
        let response_kt_trasau = await this.axios.post('/ccbs/tracuu/app_tracuu_tt_thuebao', { so_tb: this.so_tb })
        if (response_kt_trasau.data.data.length == 0 || response_kt_trasau.data.data.tra_sau == 0) {
          this.$toast.error('Số thuê bao là trả trước không thể lắp được dịch vụ')
          this.sodt_khonghople += this.so_tb + ';'
          return
        }
      }

      //kiem tra so thue bao da dang ky voice brandname chua
      const req = {
        P_PHANVUNG_ID : this.$root.token.getPhanVungID(),
        p_nghiepvu: 'KIEMTRA_DANGKY_VBN',
        p_ds_para: JSON.stringify({HDTB_ID: this.hdtb_id, TINH_ID: this.$root.token.getPhanVungID(), SO_DT: this.so_tb})

      }
          
      const kt = (await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', req)).data.data;
      if(kt.length > 0) {
        this.$toast.error('Số thuê bao đã được đăng ký ở Brandname khác');
        return;
      }


     

      // else if (this.donvikg_id == 14  || this.donvikg_id == 15 || this.donvikg_id == 16/*Voice Brandname*/) {
      //   let response_kt_vbr = await this.axios.post('/ccbs/tracuu/ts_tracuu_laytt_tb', { so_tb: this.so_tb });
      //   if (!response_kt_vbr.data.data || response_kt_vbr.data.data.length == 0) {
      //     this.$toast.error('Số thuê bao không tồn tại trên CCBS . Hãy kiểm tra lại.');
      //     this.sodt_khonghople += vso_dt + ";";
      //     return;
      //   }
      //   if ((response_kt_vbr.data.data[0].LOAI_TB != 'Post-STK_VBR2' &&  this.donvikg_id == 14 ) ||
      //       (response_kt_vbr.data.data[0].LOAI_TB != 'Post-STK_VBR3' && response_kt_vbr.data.data[0].LOAI_TB != 'Post-STK'
      //         && (this.donvikg_id == 15 || this.donvikg_id == 16))
      //   ) {
      //     this.$toast.error('Số thuê bao chưa thuộc loại hình Post-STK trên CCBS . Hãy kiểm tra lại.');
      //     this.sodt_khonghople += vso_dt + ";";
      //     return;
      //   }
      // }
      //kiem tra meg

      let hdtb_ct = {
        HDTB_ID: this.hdtb_id,
        SO_DT_CT: this.so_tb,
        TEN_TB_CT: this.$refs.txtSoCF.value,
        LOAITB_ID: 358,
        NGAY_NGHIEMTHU_CT: moment().format('DD/MM/yyyy'),
        TRANGTHAI_CT: 'Mới tiếp nhận', 
        EMAIL_CT: this.ip
      }

      this.$refs.grcDS.dataSource.push(hdtb_ct)
      if (this.hdtb_id != 0) {
        this.loading(true)
        await this.axios.post('web-hopdong/nhap_ds_thanh_vien/capnhat_hd_thuebao_ct_common', {
          vhdtb_id: this.hdtb_id,
          vds_hdtb_ct: JSON.stringify(this.ConvertUpper(this.$refs.grcDS.dataSource))
        })
        await this.frmNhapDSThanhVien_Load()
        this.loading(false)

        this.$toast.success('Ghi lại thành công')
      }

      this.so_tb = ''
    },

    async btnXoa_Click() {
      if (this.$refs.grcDS.dataSource.length <= 0) {
        this.$toast.error('Không tồn tại số điện thoại trong danh sách để xóa')
        return
      }
      let selected = this.$refs.grcDS.getSelectedRecords()
      const index = this.$refs.grcDS.dataSource.indexOf(selected[0])
      if (index > -1) {
        // only splice array when item is found
        this.$refs.grcDS.dataSource.splice(index, 1) // 2nd parameter means remove one item only
      }
      if (this.hdtb_id != 0) {
        this.loading(true)
        await this.axios.post('web-hopdong/nhap_ds_thanh_vien/capnhat_hd_thuebao_ct_common', {
          vhdtb_id: this.hdtb_id,
          vds_hdtb_ct: JSON.stringify(this.ConvertUpper(this.$refs.grcDS.dataSource))
        })
        await this.frmNhapDSThanhVien_Load()
        this.loading(false)

        this.$toast.success('Xóa thành công')
      }
    },

    async btnSua_Click() {
      if (!this.so_tb) {
        this.$toast.error('Chưa nhập số điện thoại, vui lòng nhập số điện thoại trước khi sửa')
        return
      }

      let isphone = this.so_tb.match('(84)+([0-9]{8,10})')
      if (!isphone) {
        this.$toast.error('Số điện thoại không đúng định dạng 84xxxxxxxxx. Hãy kiểm tra lại.')
        return
      }

      if (this._ds_hdtbct.length <= 0) {
        this.$toast.error('Không còn phần tử nào để sửa')
        return
      }
      let selected = this.$refs.grcDS.getSelectedRecords()
      const result = this._ds_hdtbct.indexOf(selected[0])

      this._ds_hdtbct[result].SO_DT_CT = this.so_tb
      this._ds_hdtbct[result].NGAY_NGHIEMTHU_CT = moment().format('DD/MM/yyyy')
      this.$refs.grcDS.dataSource = this._ds_hdtbct
      if (this.hdtb_id != 0) {
        this.loading(true)
        await this.axios.post('web-hopdong/nhap_ds_thanh_vien/capnhat_hd_thuebao_ct_common', {
          vhdtb_id: this.hdtb_id,
          vds_hdtb_ct: JSON.stringify(this.ConvertUpper(this.$refs.grcDS.dataSource))
        })
        await this.frmNhapDSThanhVien_Load()
        this.loading(false)

        this.$toast.success('Sửa thành công')
      }
    },
    grvDS_FocusedRowChanged() {
      let selected = this.$refs.grcDS.getSelectedRecords()
      if (selected.length > 0) {
        this.hdtb_ct_id = selected[0]['HDTB_CT_ID']
        this.so_tb = selected[0]['SO_DT_CT']
      }
    },
    chooseFiles() {
      this.$refs.fileUpload.click()
    },
    onChange(event) {
      console.log(event)
      this.loading(true)
      try {
        this.file = event.target.files ? event.target.files[0] : null

        if (this.file) {
          const reader = new FileReader()

          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: 'binary' })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            this.sodt_khonghople = ''
            console.log(data)
            data.map(async (item) => {
              console.log(item)
              await this.ThemSoDT(item[0], item[1])
            })
            if (this.hdtb_id != 0) {
              await this.axios.post('web-hopdong/nhap_ds_thanh_vien/capnhat_hd_thuebao_ct_common', {
                vhdtb_id: this.hdtb_id,
                vds_hdtb_ct: JSON.stringify(this.ConvertUpper(this._ds_hdtbct))
              })
            }
            await this.frmNhapDSThanhVien_Load()
            if (this.sodt_khonghople.length > 1) this.$toast.warning(this.sodt_khonghople + ' không hợp lệ')
            else this.$toast.success('Import thành công')
          }

          reader.readAsBinaryString(this.file)
        }
      } catch (err) {
        console.log(err)
      }
      this.loading(false)
    },
    ThemSoDT(vso_dt, vcfu) {
      //"/(84|0[3|5|7|8|9])+([0-9]{8})\b/g"
      return new Promise(async (resolve, reject) => {
        let isphone = vso_dt.toString().match('(84)+([0-9]{8,10})')
        if (!isphone) {
          this.$toast.error("Số thuê bao không đúng định dạng");
          return;
        }
        
        let ip = '';
        if(this.donvikg_id == 17) {
          const dt = this.list_stk_cd.filter(x => x.id == vso_dt);
          if(dt.length > 0) {
            ip = dt[0].ip;
          }
          if(dt.length == 0 ) {
            this.$toast.warning("Số thuê bao không hợp lệ: " + vso_dt);
            return;
          }
        }

        //let this.donvikg_id = commonFn.MAP_ID(this.axios, 'this.donvikg_id', 'css.hdtb_cntt', 'where hdtb_id=' + this.hdtb_id);
        // if (this.donvikg_id == 12) {
        //   //kiem tra meg
        //   let response_kt_meg = await this.axios.post('/ccbs/executor/meg_laytt_tb', { so_tb: this.so_tb });
        //   if (response_kt_meg.data.data.length == 0) {
        //     this.sodt_khonghople += vso_dt + ";";
        //     return;
        //   }
        // }
        // else if (this.donvikg_id == 11) {
        //   let response_kt_vcc = await this.axios.post('/ccbs/executor/ts_vcc_lay_tttb', { so_tb: this.so_tb });
        //   if (response_kt_vcc.data.data.length == 0) {
        //     this.sodt_khonghople += vso_dt + ";";
        //     return;
        //   }
        // }
        // let response_kt_trasau = await this.axios.post('/ccbs/tracuu/app_tracuu_tt_thuebao', { so_tb: this.so_tb });
        // if (response_kt_trasau.data.data && (response_kt_trasau.data.data.length == 0 || response_kt_trasau.data.data.tra_sau == 0)) {
        //   this.$toast.error('Số thuê bao là trả trước không thể lắp được dịch vụ');
        //   this.sodt_khonghople += vso_dt + ";";
        //   return;
        // }
        // else if (this.donvikg_id == 14  || this.donvikg_id == 15 || this.donvikg_id == 16/*Voice Brandname*/) {
        //   let response_kt_vbr = await this.axios.post('/ccbs/tracuu/ts_tracuu_laytt_tb', { so_tb: this.so_tb });
        //   if (response_kt_vbr.data.data.length == 0) {
        //     this.$toast.error('Số thuê bao không tồn tại trên CCBS . Hãy kiểm tra lại.');
        //     this.sodt_khonghople += vso_dt + ";";
        //     return;
        //   }
        //   // if (response_kt_vbr.data.data[0].LOAI_TB != 'Post-STK_VBR2'
        //   //   && response_kt_vbr.data.data[0].LOAI_TB != 'Post-STK_VBR3'
        //   //   && response_kt_vbr.data.data[0].LOAI_TB != 'Post-STK'
        //   // ) {
        //   //   this.$toast.error('Số thuê bao chưa thuộc loại hình Post-STK trên CCBS . Hãy kiểm tra lại.');
        //   //   this.sodt_khonghople += vso_dt + ";";
        //   //   return;
        //   // }
        // }

        let hdtb_ct = {
          HDTB_ID: this.hdtb_id,
          SO_DT_CT: vso_dt,
          TEN_TB_CT: vcfu,
          LOAITB_ID: 358,
          NGAY_NGHIEMTHU_CT: moment().format('DD/MM/yyyy'),
          TRANGTHAI_CT: 'Mới tiếp nhận',
          EMAIL_CT: ip
        }
        this._ds_hdtbct.push(hdtb_ct)

        vso_dt = ''
        resolve(true)
      })
    }
  }
}
</script>
<style>
#frmNhapDSThanhVien_form_title {
  width: 100% !important;
  font-weight: bold;
}
</style>
