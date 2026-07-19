<template src="./gridCNTT.html">
</template>
<script>

import {
  LoaiHinhTB,
  CAConstanst,
} from '../../../InstallNewSubs/Enums'
import API from '../../InstallNewSubsCrossSaleAPI'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
export default {
    components: {
        moment,
        DatePicker
    },
    props: [ 'vloaitb_id','dvvt','phanvung_id','kieuld','thongso','thongso_cntt','khachhang' ],
    data() {
        return {
            //ItemList : [],
            gridEditors: [],
            debug:true,
            ItemList: this.thongso_cntt,
            popupComponent: null,
            popupComponentData: null,
            popupComponentAttr: {},
            popupComponentEvts: {
                'form-close': this.popupClosed,
                acceptChangeCTV: this.popupClosed,
                acceptChangeNGT: this.popupClosed,
                PopUpCongTacVien_Thoat: this.popupClosed
            },
            popupRetVal: null,
            popupComponentName: '',
            muccuoc_id: 0,
            dsHDTB_CT: [],
            watcherActive: false,
        }
    },
    watch: {
        gridEditors: {
            handler: function(newVal) {
                if(!this.watcherActive) return;
                console.info('running handler 2');
                for (const key in newVal) {
                    if(this.ItemList[key].sFieldName=="NGAY_BD") console.info(newVal[key].Value, this.ItemList[key].sValue,newVal[key].Value != this.ItemList[key].sValue)
                    if (newVal[key].Value != this.ItemList[key].sValue) {
                        console.log(`Property ${key} changed from ${this.ItemList[key].sValue} to ${newVal[key].Value}`,this.ItemList);
                        this.ItemList[key].sValue = this.gridEditors[key].Value;
                        this.$emit('DataChanged',this.ItemList[key]);
                    }
                }
            },
            deep: true
        },
    },
    compute: {
    },
    async mounted() {
        await this.LoadThongTin_DV_CNTT();
        for(const idx in this.gridEditors) { 
            this.ItemList[idx].sValue = this.gridEditors[idx].Value;
        }
        this.watcherActive = true;
    },
    methods: {
        async LoadThongTin_DV_CNTT() {
            try {

                var DS_CHUQUAN_THEO_QUYEN = await API.get_lay_dk_bang(this.axios, { params: { tenBang: 'CHUQUAN' } }).then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        return response.data.data
                    }
                })
                
                this.ItemList = (!this.thongso_cntt || this.thongso_cntt.length<=0)?await this.Init_Properties(this.vloaitb_id, this.phanvung_id):this.thongso_cntt;
                this.gridEditors = []
                for (var item of this.ItemList) {
                    if (item.iAtt == 0) {
                        let repositoryItemTextEdit = { Type: 'TextEdit' }
                        repositoryItemTextEdit.Tag = item.sFieldName
                        repositoryItemTextEdit.ReadOnly = item.sReadOnly == 1
                        repositoryItemTextEdit.DataType = item.iDataType
                        repositoryItemTextEdit.MaxValue = item.sMaxValue
                        repositoryItemTextEdit.MinValue = item.sMinValue
                        repositoryItemTextEdit.EditValueChanged = this.repositoryItemTextEdit_EditValueChanged
                        repositoryItemTextEdit.EditValueChanging += this.repositoryItemTextEdit_EditValueChanging
                        repositoryItemTextEdit.Leave += this.repositoryItemTextEdit_Leave

                        if (CAConstanst.isCADev) {
                        if (this.vloaitb_id == 116) {
                            if (item.sFieldName == 'SO_THANG') {
                            repositoryItemTextEdit.ReadOnly = true
                            }
                        }

                        let default_serial_value = item.sValue
                        if (item.sFieldName != 'SERIAL') {
                            default_serial_value = item.sValue
                        } else {
                            default_serial_value = this.generateUUID()
                        }
                        repositoryItemTextEdit.Value = default_serial_value
                        } else {
                        repositoryItemTextEdit.Value = item.sValue
                        }
                        repositoryItemTextEdit.Name = item.sCaption
                        repositoryItemTextEdit.Group = 'Tham số báo cáo'
                        repositoryItemTextEdit.ID = item.sFieldName
                        // repositoryItemTextEdit.RepositoryItem = repositoryItemTextEdit
                        this.gridEditors.push(repositoryItemTextEdit)
                    }
                    if (item.iAtt == 1) {
                        let repositoryItemCalcEdit = { Type: 'CalcEdit' }
                        repositoryItemCalcEdit.MaxValue = item.sMaxValue
                        repositoryItemCalcEdit.MinValue = item.sMinValue
                        repositoryItemCalcEdit.Tag = item.sFieldName
                        repositoryItemCalcEdit.ReadOnly = item.sReadOnly == 1
                        repositoryItemCalcEdit.Name = item.sCaption
                        repositoryItemCalcEdit.Group = 'Tham số báo cáo'
                        repositoryItemCalcEdit.ID = item.sFieldName
                        repositoryItemCalcEdit.Value = item.sValue
                        // repositoryItemCalcEdit.RepositoryItem = repositoryItemCalcEdit
                        this.gridEditors.push(repositoryItemCalcEdit)
                        //this.gridEditors.Add((RepositoryItem)this.repositoryItemCalcEdit, item.sCaption, (object)item.sValue, "Tham số báo cáo", item.sFieldName);
                    }
                    if (item.iAtt == 2) {
                        item.sValue = moment(new Date()).format('DD/MM/YYYY')
                        let repositoryItemDateEdit = { Type: 'DateEdit' }
                        repositoryItemDateEdit.ReadOnly = item.sReadOnly == 1
                        repositoryItemDateEdit.Tag = item.sFieldName
                        item.sValue = moment(new Date()).format('DD/MM/YYYY')
                        // repositoryItemDateEdit.DisplayFormat.FormatString = "DD/MM/YYYY";
                        // repositoryItemDateEdit.EditFormat.FormatString = "DD/MM/YYYY";
                        repositoryItemDateEdit.FormatString = 'DD/MM/YYYY'
                        repositoryItemDateEdit.EditMask = 'DD/MM/YYYY'
                        // repositoryItemDateEdit.Mask.UseMaskAsDisplayFormat = true;
                        repositoryItemDateEdit.EditValueChanged = this.repositoryItemDateEdit_EditValueChanged
                        repositoryItemDateEdit.Name = item.sCaption
                        repositoryItemDateEdit.Group = 'Tham số báo cáo'
                        repositoryItemDateEdit.ID = item.sFieldName
                        repositoryItemDateEdit.Value = moment(new Date()).format('DD/MM/YYYY')
                        // repositoryItemDateEdit.RepositoryItem = repositoryItemDateEdit
                        this.gridEditors.push(repositoryItemDateEdit)
                        // this.gridEditors.Add((RepositoryItem)repositoryItemDateEdit, item.sCaption, DateTime.Now, "Tham số báo cáo", item.sFieldName);
                    }

                    if (item.iAtt == 9) {
                        item.sValue = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
                        let repositoryItemDateEdit = { Type: 'DateEdit' }
                        repositoryItemDateEdit.ReadOnly = item.sReadOnly == 1
                        repositoryItemDateEdit.Tag = item.sFieldName
                        item.sValue = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
                        // repositoryItemDateEdit.DisplayFormat.FormatString = "DD/MM/YYYY HH:mm:ss";
                        // repositoryItemDateEdit.EditFormat.FormatString = "DD/MM/YYYY HH:mm:ss";
                        repositoryItemDateEdit.FormatString = 'DD/MM/YYYY HH:mm:ss'
                        repositoryItemDateEdit.EditMask = 'DD/MM/YYYY HH:mm:ss'
                        // repositoryItemDateEdit.Mask.UseMaskAsDisplayFormat = true;
                        repositoryItemDateEdit.EditValueChanged = this.repositoryItemDateEdit_EditValueChanged
                        repositoryItemDateEdit.Name = item.sCaption
                        repositoryItemDateEdit.Group = 'Tham số báo cáo'
                        repositoryItemDateEdit.ID = item.sFieldName
                        repositoryItemDateEdit.Value = moment(new Date()).format('DD/MM/YYYY')
                        // repositoryItemDateEdit.RepositoryItem = repositoryItemDateEdit
                        this.gridEditors.push(repositoryItemDateEdit)
                        // this.gridEditors.Add((RepositoryItem)repositoryItemDateEdit, item.sCaption, DateTime.Now, "Tham số báo cáo", item.sFieldName);
                    }

                    // let str1;
                    // let dataSet;
                    if (item.iAtt == 4) {
                        let repositoryItemLookUpEdit = { Type: 'LookUpEdit' }
                        repositoryItemLookUpEdit.ReadOnly = item.sReadOnly == 1
                        // repositoryItemLookUpEdit.View.OptionsView.ShowAutoFilterRow = true;
                        repositoryItemLookUpEdit.Tag = item.sFieldName
                        // str1 = "";
                        let SQL = item.StrSql
                        if (SQL && SQL != '' && SQL.indexOf('{') < 0) {
                        // dataSet = new DataSet();
                        let data = item.SqlData
                        if (!data || data.length <= 0) this.$toast.error('Không lấy được dữ liệu cho ' + repositoryItemLookUpEdit.Tag)
                        // let data = await API.post_sp_huonggiao_runsql(this.axios, { "command": SQL }).then((response) => {
                        //   if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        //     return response.data.data.data_json;
                        //   }
                        //   else return [];
                        // }).catch(err=> { throw err.data.message+': '+err.data.message_detail; }); //this.bangts.GetSQL(SQL, "");
                        repositoryItemLookUpEdit.ValueMember = item.sLookUpKeyField
                        repositoryItemLookUpEdit.DisplayMember = item.sLookUpValueField
                        if (this.DS_CHUQUAN_THEO_QUYEN != null && this.DS_CHUQUAN_THEO_QUYEN != '' && item.sLookUpKeyField.toUpperCase() == 'CHUQUAN_ID') {
                            repositoryItemLookUpEdit.DataSource = data.filter((x) => this.DS_CHUQUAN_THEO_QUYEN.indexOf(x.CHUQUAN_ID) >= 0) //(object)data.Tables[0].Select("CHUQUAN_ID in " + this.DS_CHUQUAN_THEO_QUYEN).CopyToDataTable();
                        }
                        // #region cuongpq them load lai cbo huyen ivan
                        else if (item.sLookUpKeyField && item.sLookUpKeyField.toUpperCase() == 'CAPDO_ID' && this.vloaitb_id == LoaiHinhTB.IVAN) {
                            let tinh_ivan = await API.post_getivan_tinh(this.axios, { tinh_id: this.diachiTB.TINH_ID }).then((response) => {
                            //.post_sp_huonggiao_runsql(this.axios, { "command": this.ReplaceSQL(`select tinh_ivan_id from ${DatabaseConstants.DB2}.tinh where tinh_id = ${this.diachiTB.TINH_ID}`) }).then((response) => {
                            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                                return response.data.data //.data_json;
                            } else return []
                            }) //bangts.getDataFromSQL(ReplaceSQL("select tinh_ivan_id from {?DB2}.tinh where tinh_id = " + tinhtb_id), "tinh_ivan").Tables[0];
                            if (tinh_ivan.length > 0) {
                            if (tinh_ivan[0].tinh_ivan_id != null) {
                                let vtinh_ivan = tinh_ivan[0].tinh_ivan_id
                                repositoryItemLookUpEdit.DataSource = data.filter((x) => x.MAP_ID == vtinh_ivan) //(object)data.Tables[0].Select("MAP_ID = " + vtinh_ivan).CopyToDataTable();
                            } else throw 'Địa chỉ thuê bao tỉnh chưa có trên danh mục dịch vụ IVAN. Liên hệ admin để được hỗ trợ' //this.$toast.error("Địa chỉ thuê bao tỉnh chưa có trên danh mục dịch vụ IVAN. Liên hệ admin để được hỗ trợ");
                            } else this.$toast.error('Hãy chọn địa chỉ thuê bao') //this.$toast.error("Hãy chọn địa chỉ thuê bao");
                        }
                        // #endregion
                        else repositoryItemLookUpEdit.DataSource = data
                        // repositoryItemLookUpEdit.View.OptionsView.ShowColumnHeaders = false;
                        //if (repositoryItemLookUpEdit.View.Columns.Count > 1)
                        // if(Object.keys(data).length > 1) //Doan nay khong can vi combobox da hoat dong dung nhu the nay roi!!!
                        // {
                        //     for (var ic = 0; ic < Object.keys(data).length; ic++)
                        //     {
                        //         repositoryItemLookUpEdit.View.Columns[ic].Visible = false;
                        //     }
                        //     repositoryItemLookUpEdit.View.Columns[1].Visible = true;
                        // }
                        repositoryItemLookUpEdit.NullText = ''
                        } else {
                        // SQL = await this.ReturnSqlStr(ItemList, index1)
                        // dataSet = new DataSet();
                        // DataSet data = this.bangts.GetSQL(SQL, "");
                        let data = item.SqlData
                        if (!data || data.length <= 0) this.$toast.error('Không lấy được dữ liệu cho ' + repositoryItemLookUpEdit.Tag)
                        // let data = await API.post_sp_huonggiao_runsql(this.axios, { "command": SQL }).then((response) => {
                        //   if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        //     return response.data.data.data_json;
                        //   }
                        //   else return [];
                        // }).catch(err=> { throw err.data.message+': '+err.data.message_detail; });;
                        repositoryItemLookUpEdit.ValueMember = item.sLookUpKeyField
                        repositoryItemLookUpEdit.DisplayMember = item.sLookUpValueField
                        if (this.DS_CHUQUAN_THEO_QUYEN != null && this.DS_CHUQUAN_THEO_QUYEN != '' && item.sLookUpKeyField.toUpperCase() == 'CHUQUAN_ID') {
                            repositoryItemLookUpEdit.DataSource = data.filter((x) => this.DS_CHUQUAN_THEO_QUYEN.indexOf(x.CHUQUAN_ID) >= 0) //(object)data.Tables[0].Select("CHUQUAN_ID in " + this.DS_CHUQUAN_THEO_QUYEN).CopyToDataTable();
                        } else repositoryItemLookUpEdit.DataSource = data //(object)data.Tables[0];
                        // repositoryItemLookUpEdit.View.OptionsView.ShowColumnHeaders = false;
                        // if (Object.keys(data).length > 1)
                        // {
                        //     for (var ic = 0; ic < Object.keys(data).length; ic++)
                        //     {
                        //         repositoryItemLookUpEdit.View.Columns[ic].Visible = false;
                        //     }
                        //     repositoryItemLookUpEdit.View.Columns[1].Visible = true;
                        // }

                        repositoryItemLookUpEdit.NullText = ''
                        }
                        repositoryItemLookUpEdit.EditValueChanged = this.repositoryItemLookUpEdit_EditValueChanged
                        repositoryItemLookUpEdit.Leave = this.repositoryItemLookUpEdit_leave
                        repositoryItemLookUpEdit.EditValueChanging = this.repositoryItemLookUpEdit_EditValueChanging
                        //  this.gridEditors.Add((RepositoryItem)repositoryItemLookUpEdit, item.sCaption, (object)item.sValue, "Tham số báo cáo", item.sFieldName);
                        repositoryItemLookUpEdit.Name = item.sCaption
                        repositoryItemLookUpEdit.Group = 'Tham số báo cáo'
                        repositoryItemLookUpEdit.ID = item.sFieldName
                        repositoryItemLookUpEdit.Value = item.sValue
                        if (!repositoryItemLookUpEdit.Value && repositoryItemLookUpEdit.DataSource && repositoryItemLookUpEdit.DataSource.length > 0) repositoryItemLookUpEdit.Value = repositoryItemLookUpEdit.DataSource[0][repositoryItemLookUpEdit.ValueMember]
                        if (repositoryItemLookUpEdit.DataSource && repositoryItemLookUpEdit.DataSource.length > 0) {
                        repositoryItemLookUpEdit.DataSource = repositoryItemLookUpEdit.DataSource.map((x) => ({ text: x[item.sLookUpValueField], id: x[item.sLookUpKeyField] }))
                        }
                        // repositoryItemLookUpEdit.RepositoryItem = repositoryItemLookUpEdit
                        this.gridEditors.push(repositoryItemLookUpEdit)
                        // await this.repositoryItemLookUpEdit_EditValueChanged(repositoryItemLookUpEdit)
                    }
                    if (item.iAtt == 5) {
                        //Button Edit
                        let repositoryItemButtonEdit = { Type: 'ButtonEdit' }
                        repositoryItemButtonEdit.ReadOnly = item.sReadOnly == 1
                        repositoryItemButtonEdit.Tag = item.sFieldName
                        if (item.sFieldName == 'MATB_TN') {
                        repositoryItemButtonEdit.ReadOnly = true
                        }

                        // repositoryItemButtonEdit.ButtonClick -= new DevExpress.XtraEditors.Controls.ButtonPressedEventHandler(this.repositoryItemButtonEdit_ButtonClick);
                        repositoryItemButtonEdit.ButtonClick = this.repositoryItemButtonEdit_ButtonClick
                        repositoryItemButtonEdit.Name = item.sCaption
                        repositoryItemButtonEdit.Group = 'Tham số báo cáo'
                        repositoryItemButtonEdit.ID = item.sFieldName
                        repositoryItemButtonEdit.Value = item.sValue
                        // repositoryItemButtonEdit.RepositoryItem = repositoryItemButtonEdit
                        this.gridEditors.push(repositoryItemButtonEdit)
                        // this.gridEditors.Add((RepositoryItem)repositoryItemButtonEdit, item.sCaption, (object)item.sValue, "Tham số báo cáo", item.sFieldName);
                    }
                    if (item.iAtt == 7) {
                        let repositoryItemCheckEdit = { Type: 'CheckEdit' }
                        repositoryItemCheckEdit.Tag = item.sFieldName
                        repositoryItemCheckEdit.ReadOnly = item.sReadOnly == 1
                        repositoryItemCheckEdit.ValueChecked = 1
                        repositoryItemCheckEdit.ValueUnchecked = 0
                        repositoryItemCheckEdit.Name = item.sCaption
                        repositoryItemCheckEdit.Group = 'Tham số báo cáo'
                        repositoryItemCheckEdit.ID = item.sFieldName
                        repositoryItemCheckEdit.Value = item.sValue
                        // repositoryItemCheckEdit.RepositoryItem = repositoryItemCheckEdit
                        this.gridEditors.push(repositoryItemCheckEdit)
                        // this.gridEditors.Add((RepositoryItem)this.repositoryItemCheckEdit, item.sCaption, (object)item.sValue, "Tham số báo cáo", item.sFieldName);
                    }
                    if (item.iAtt == 10) {
                        //InitialItemCheckedRepository();// Thay bang doan code ben duoi
                        let repositoryItemCheckedComboBoxEdit1 = { Type: 'CheckedComboBoxEdit1' }
                        repositoryItemCheckedComboBoxEdit1.AutoHeight = false
                        repositoryItemCheckedComboBoxEdit1.Name = 'repositoryItemCheckedComboBoxEdit1'

                        repositoryItemCheckedComboBoxEdit1.ReadOnly = item.sReadOnly == 1
                        let SQL_CheckListBox = item.StrSql
                        let data = item.SqlData
                        // let data = await API.post_sp_huonggiao_runsql(this.axios, { "command": SQL_CheckListBox }).then((response) => {
                        //     if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        //       return response.data.data.data_json;
                        //     }
                        //     else return [];
                        //   });//this.objCheckData.GET_DATA_SQL(SQL_CheckListBox, "");
                        repositoryItemCheckedComboBoxEdit1.ValueMember = item.sLookUpKeyField
                        repositoryItemCheckedComboBoxEdit1.DisplayMember = item.sLookUpValueField
                        repositoryItemCheckedComboBoxEdit1.DataSource = data

                        repositoryItemCheckedComboBoxEdit1.Name = item.sCaption
                        repositoryItemCheckedComboBoxEdit1.Group = 'Tham số báo cáo'
                        repositoryItemCheckedComboBoxEdit1.ID = item.sFieldName
                        repositoryItemCheckedComboBoxEdit1.Value = item.sValue ? item.sValue.split(',') : item.sValue
                        // repositoryItemCheckedComboBoxEdit1.RepositoryItem = repositoryItemCheckedComboBoxEdit1
                        this.gridEditors.push(repositoryItemCheckedComboBoxEdit1)

                        // this.gridEditors.Add((RepositoryItem)repositoryItemCheckedComboBoxEdit1, item.sCaption, (object)item.sValue, "Tham số báo cáo", item.sFieldName);
                    }
                }
            } catch (ex) {
                this.$toast.error('' + ex)
                if (this.debug) console.error(ex)
            }
        },
        async Init_Properties(vloaitb_id, phanvung_id) {
            return await API.post_lay_danhsach_thuoctinh_datmoi(this.axios, {
                vloaitb_id: this.vloaitb_id,
                vopt_list: "00000",
                vten_bang: "HDTB_CNTT",
                phanvung_id: phanvung_id,
            }).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                var ds = response.data.data
                var ItemList = []
                for (var row of ds) {
                    var item = {}
                    item.sFieldName = row['field_name']
                    item.sCaption = row['ten_hienthi']
                    item.iAtt = row['att']
                    item.iDataType = row['data_type']
                    item.sMaxValue = row['max_value']
                    item.sMinvalue = row['min_value']
                    item.sLookupApi = row['lookup_api'] ? row['lookup_api'] : ''
                    item.sAPIDisplayMember = row['api_display_member'] ? row['api_display_member'] : ''
                    item.sAPIValueMember = row['api_value_member'] ? row['api_value_member'] : ''
                    item.sAPIHienThiBang = row['api_hienthi_bang'] ? row['api_hienthi_bang'] : ''
                    item.sAPIMethod = row['api_method'] ? row['api_method'] : ''
                    item.sReadOnly = row['read_only']
                    item.sValue = row['field_value']
                    item.nFieldLength = row['field_length'] ? row['field_length'] : 0
                    item.bIsNotNull = row['field_nullable'] && row['field_nullable'] == 0 ? true : false
                    item.bIsUnique = row['field_unique'] && row['field_unique'] == 0 ? true : false
                    item.sLookUpTableName = row['parent_table']
                    item.sLookUpKeyField = row['parent_keyfield']
                    item.sLookUpValueField = row['parent_descfield']
                    item.strDepandField = row['depend_field']
                    item.StrSql = row['lookup_sql']
                    if (row['lookup_sql_response']) item.SqlData = this.UpperCasePropertyList(row['lookup_sql_response'])
                    ItemList.push(item)
                }
                return ItemList //response.data.data;
                } else return []
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
        async repositoryItemTextEdit_EditValueChanged(repositoryItemTextEdit) {
            //region cuongpq thêm code  sms  brandname
            let ItemList = this.ItemList
            if (this.vloaitb_id == LoaiHinhTB.Voice_Brandname && kieuld == 793 && repositoryItemTextEdit.Tag == 'MA_PL' && repositoryItemTextEdit.Value) {
                let columns = ['MA_PL', 'NGAY_KY_HD', 'MA_CHUAN', 'LOAICTS_ID', 'CHUQUAN_ID', 'MST', 'PHANLOAI_ID', 'DONVIKG_ID', 'TOCDO_ID', 'TEN_CH', 'SO_DT', 'EMAIL', 'DOMAIN', 'USERNAME']
                let vma_pl = repositoryItemTextEdit.Value
                let dt = await this.axios.get(`/web-hopdong/vbn/lay_thongtin_hopdong_cu?vma_pl=${vma_pl}`)

                if (dt && dt.data && dt.data.data && dt.data.data.length > 0) {
                for (var i = ItemList.length - 1; i >= 0; i--) {
                    if (columns.includes(ItemList[i].sFieldName)) {
                    // ItemList[i].sValue = dt.data.data[0][ItemList[i].sFieldName.toLowerCase()]
                    this.gridEditors[i].Value = dt.data.data[0][ItemList[i].sFieldName.toLowerCase()]
                    //sothang = Convert.ToInt32(textValue);
                    }
                }
                } 
                else {
                this.$toast.error('Không tồn tại hợp đồng hoặc hợp đồng đã bị hủy. Hãy kiểm tra lại.')
                }
            }

            else if (this.vloaitb_id == LoaiHinhTB.Voice_Brandname && repositoryItemTextEdit.Tag == 'MA_CHUAN' && repositoryItemTextEdit.Value) {
                let so_tb = repositoryItemTextEdit.Value
                let source_hotline = "";
                for (var i = ItemList.length - 1; i >= 0; i--) {
                if (ItemList[i].sFieldName == "DONVIKG_ID") {
                    source_hotline = this.gridEditors[i].Value;
                }
                }

                if (source_hotline == "") this.$toast.error('Chưa nhập Source hotline.');
                if (source_hotline == "12" /*meg*/) {
                let response_kt_meg = await this.axios.post('/ccbs/executor/meg_laytt_tb', { so_tb });
                if (response_kt_meg.data.data.length == 0) {
                    this.$toast.error('Số thuê bao chủ nhóm không phải là số chính (số hotline) của dịch vụ MEG, đề nghị kiểm tra lại');
                    repositoryItemTextEdit.Value = "";
                    return;
                }
                let kt_meg = response_kt_meg.data.data;
                } 
                else if (source_hotline == "11" /*VCC*/) {
                let response_kt_vcc = await this.axios.post('/ccbs/executor/ts_vcc_lay_tttb', { so_tb });
                if (response_kt_vcc.data.data.length == 0) {
                    this.$toast.error('Số thuê bao chủ nhóm không phải là số chính (số hotline) của dịch vụ VCC, đề nghị kiểm tra lại');
                    repositoryItemTextEdit.Value = "";
                    return;
                }
                let kt_meg = response_kt_vcc.data.data;
                }
            }
            //thêm code  load thông tin hiện hữu sms brandname 
            if (this.vloaitb_id == LoaiHinhTB.BRANDNAME && kieuld == 13224 && repositoryItemTextEdit.Tag == 'LABEL_ID' && repositoryItemTextEdit.Value) {
                let label_id = repositoryItemTextEdit.Value
                let phanloai_id = -1
                for (var i = ItemList.length - 1; i >= 0; i--) {
                if (ItemList[i].sFieldName == 'PHANLOAI_ID') {
                    phanloai_id = this.gridEditors[i].Value
                    //sothang = Convert.ToInt32(textValue);
                }
                }
                var vphanvung_id = Number(this.$root.token.getPhanVungID())
                let response = await this.axios.post(`/tichhop/brandname/load_thongtin_brandname`, { vphanvung_id, vlabel_id: label_id + '', vphanloai_id: phanloai_id + '' })
                if (response.data.data && response.data.data.status == 200) {
                let columns_brn = ['LABEL_ID', 'AGENT_ID', 'CONTRACT_ID', 'LINHVUC_ID', 'LINHVUC_VMS_ID', 'LINHVUC_VTL_ID', 'LINHVUC_GTEL_ID', 'LINHVUC_VNM_ID', 'LINHVUC_ITEL_ID', 'LINHVUC_MOBICAST_ID']
                var label_data = response.data.data.data.items[0]
                for (var i = ItemList.length - 1; i >= 0; i--) {
                    if (columns_brn.includes(ItemList[i].sFieldName)) {
                    // ItemList[i].sValue = label_data[ItemList[i].sFieldName.toLowerCase()]
                    this.gridEditors[i].Value = label_data[ItemList[i].sFieldName.toLowerCase()]
                    } else if (ItemList[i].sFieldName == 'THUONGHIEU') {
                    // ItemList[i].sValue = label_data['label']
                    this.gridEditors[i].Value = label_data['label']
                    } else if (ItemList[i].sFieldName == 'NGAY_BD') {
                    // ItemList[i].sValue = label_data['created_date']
                    this.gridEditors[i].Value = label_data['created_date']
                    } else if (ItemList[i].sFieldName == 'MST') {
                    // ItemList[i].sValue = label_data['tax_code']
                    this.gridEditors[i].Value = label_data['tax_code']
                    } else if (ItemList[i].sFieldName == 'ADSER_ID') {
                    // ItemList[i].sValue = '0'
                    this.gridEditors[i].Value = '0'
                    }
                }
                } else {
                this.$toast.error(response.data.data.message)
                return
                }
            }

            //  if (tEditor.Properties.Tag.ToString() == "MA_PL" && cboKieuLD.SelectedValue.ToString() == "793"/*thêm mới brandname*/
            //     && Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.Voice_Brandname && tEditor.Text != "") {
            //             DataTable dt = tdan.GET_DATA_PROC("{?DB2}.toantrinh_vbn.lay_thongtin_hopdong_cu", "vma_pl", tEditor.Text);
            //     if (dt.Rows.Count == 0) return;
            //     let kq = Message_Box.Show("Bạn có muốn sử dụng thông tin hợp đồng cũ", "Thông báo");
            //     if (!kq) return;
            //     string[] columns = new string[] {
            //       "MA_PL", "NGAY_KY_HD", "MA_CHUAN", "LOAICTS_ID", "CHUQUAN_ID",
            //       "MST", "PHANLOAI_ID", "DONVIKG_ID", "TOCDO_ID", "TEN_CH", "SO_DT", "EMAIL", "DOMAIN",
            //       "USERNAME"
            //     };
            //     for (int i = 0; i < ItemList.Length; i++)
            //     {
            //       foreach(string fieldname in columns)
            //       {
            //         if (ItemList[i].sFieldName == fieldname) {
            //           ItemList[i].sValue = dt.Rows[0][fieldname].ToString();
            //           gridEditors[i].Value = dt.Rows[0][fieldname];
            //         }
            //       }
            //     }
            //     grcMacdinh.RefreshDataSource();
            //     grcMacdinh.EndUpdate();
            //   }

            //end region
            // FIXME Can xem lai cho tham so nay xem nen truyen o code khoi tao doi tuong hay tu form ????
            //RepositoryItemTextEdit repositoryItemTextEdit = !(sender is TextEdit) ? sender as RepositoryItemTextEdit : (sender as TextEdit).Properties;

            if (this.vloaitb_id == LoaiHinhTB.ELEARNING) {
                if (repositoryItemTextEdit.Tag == 'SOLUONG') {
                let textValue = repositoryItemTextEdit.Value
                for (var i = ItemList.length - 1; i >= 0; i--) {
                    if (ItemList[i].sFieldName == 'SOLUONG') {
                    // ItemList[i].sValue = textValue
                    //sothang = Convert.ToInt32(textValue);
                    break
                    }
                }
                }

                if (repositoryItemTextEdit.Tag == 'DOMAIN') {
                let textValue = repositoryItemTextEdit.Value
                for (var i = ItemList.length - 1; i >= 0; i--) {
                    if (ItemList[i].sFieldName == 'DOMAIN') {
                    // ItemList[i].sValue = textValue
                    //sothang = Convert.ToInt32(textValue);
                    break
                    }
                }
                }

                if (repositoryItemTextEdit.Tag == 'MA_PHU') {
                let textValue = repositoryItemTextEdit.Value
                for (var i = ItemList.length - 1; i >= 0; i--) {
                    if (ItemList[i].sFieldName == 'MA_PHU') {
                    // ItemList[i].sValue = textValue
                    //sothang = Convert.ToInt32(textValue);
                    break
                    }
                }
                }
            }

            if (repositoryItemTextEdit.Tag == 'SO_THANG') {
                let b_ngay_bd = false
                let b_ngay_kt = false
                let sothang = 0
                let ngay_bd = new Date() //tt_nd.ngay_cn;
                let ngay_kt = new Date() //tt_nd.ngay_cn;

                let textValue = repositoryItemTextEdit.Value
                sothang = textValue * 1
                if (!textValue || textValue == '' || isNaN(textValue)) return
                // Da chay o duoi > khong can chay lai o day!!!
                // for (var i = ItemList.length - 1; i >= 0; i--)
                // {
                //     if (ItemList[i].sFieldName == "SO_THANG")
                //     {
                //         ItemList[i].sValue = textValue;
                //         sothang = textValue;
                //         break;
                //     }
                // }
                for (var i = 0; i < ItemList.length; i++) {
                if (ItemList[i].sFieldName == 'SO_THANG') {
                    // ItemList[i].sValue = textValue
                    sothang = textValue * 1
                }
                if (ItemList[i].sFieldName == 'NGAY_BD') {
                    b_ngay_bd = true
                    // ItemList[i].sValue = this.gridEditors[i].Value
                    ngay_bd = this.gridEditors[i].Value
                    try {
                    ngay_bd = moment(ngay_bd, ngay_bd.length > 10 ? 'DD/MM/YYYY HH:mm:ss' : 'DD/MM/YYYY').toDate()
                    } catch (ex) {}
                }
                if (ItemList[i].sFieldName == 'NGAY_KT') {
                    b_ngay_kt = true
                    // ItemList[i].sValue = this.gridEditors[i].Value
                    ngay_kt = this.gridEditors[i].Value
                    if (ngay_kt && (ngay_kt + '').indexOf('/') > 0)
                    try {
                        ngay_kt = moment(ngay_kt, ngay_kt.length > 10 ? 'DD/MM/YYYY HH:mm:ss' : 'DD/MM/YYYY').toDate()
                    } catch (ex) {}
                    if (ngay_kt)
                    try {
                        ngay_kt = moment(ngay_kt).toDate()
                    } catch (ex) {}
                }
                if (ItemList[i].sFieldName == 'THOIGIAN_BD') {
                    b_ngay_bd = true
                    // ItemList[i].sValue = this.gridEditors[i].Value
                    ngay_bd = this.gridEditors[i].Value
                    try {
                    ngay_bd = moment(ngay_bd, ngay_bd.length > 10 ? 'DD/MM/YYYY HH:mm:ss' : 'DD/MM/YYYY').toDate()
                    } catch (ex) {}
                }
                if (ItemList[i].sFieldName == 'THOIGIAN_KT') {
                    b_ngay_kt = true
                    // ItemList[i].sValue = this.gridEditors[i].Value
                    ngay_kt = this.gridEditors[i].Value

                    if (ngay_kt && (ngay_kt + '').indexOf('/') > 0)
                    try {
                        ngay_kt = moment(ngay_kt, ngay_kt.length > 10 ? 'DD/MM/YYYY HH:mm:ss' : 'DD/MM/YYYY').toDate()
                    } catch (ex) {}
                    if (ngay_kt)
                    try {
                        ngay_kt = moment(ngay_kt).toDate()
                    } catch (ex) {}
                }
                if (b_ngay_bd == true && b_ngay_kt == true) break
                }
                //moment(new Date()).format("DD/MM/YYYY")
                if (b_ngay_bd == true && b_ngay_kt == true) {
                ngay_kt = new Date(ngay_bd)
                ngay_kt = ngay_kt.setMonth(ngay_kt.getMonth() + sothang * 1) //ngay_bd.AddMonths(sothang);
                ngay_kt = new Date(ngay_kt)
                //ngay_kt = moment(ngay_bd).add(sothang*1,'M').toDate();
                for (var index_1 = 0; index_1 < ItemList.length; ++index_1) {
                    if (ItemList[index_1].sFieldName == 'NGAY_KT') {
                    try {
                        ngay_kt = moment(ngay_kt).format('DD/MM/YYYY')
                    } catch (ex) {
                        ngay_kt = ngay_kt
                    }
                    this.gridEditors[index_1].Value = ngay_kt //moment(ngay_kt).format("DD/MM/YYYY");
                    }

                    if (ItemList[index_1].sFieldName == 'THOIGIAN_KT') {
                    try {
                        ngay_kt = moment(ngay_kt).format('DD/MM/YYYY HH:mm:ss')
                    } catch (ex) {
                        ngay_kt = ngay_kt
                    }
                    this.gridEditors[index_1].Value = ngay_kt //moment(ngay_kt).format("DD/MM/YYYY HH:mm:ss");
                    }
                }
                }
            }

            if (repositoryItemTextEdit.Tag == 'TONGSO_ND') {
                if (this.vloaitb_id == LoaiHinhTB.IOFFICE) {
                let textValue = repositoryItemTextEdit.Value

                if (!textValue || textValue == '' || isNaN(textValue)) return

                this._sl_cha = textValue
                this.HT_ThietBi()
                this._sl_cha = -1
                }
            }
            if (repositoryItemTextEdit.Tag == 'SL_MAILING') {
                if (this.vloaitb_id == LoaiHinhTB.VNPT_MEETING || vloaitb_id == LoaiHinhTB.HNTH_NGN) {
                let textValue = repositoryItemTextEdit.Value

                if (textValue == '' || isNaN(textValue)) return

                this._sl_cha = textValue
                this.HT_ThietBi()
                this._sl_cha = -1
                }
                if (this.vloaitb_id == LoaiHinhTB.VNPT_MCAP) {
                let textValue = repositoryItemTextEdit.Value

                if (textValue == '' || isNaN(textValue)) return
                let sothang_mcap = 0
                for (let k = 0; k < this.gridEditors.length; k++) {
                    if (ItemList[k].sFieldName == 'SO_THANG') {
                    sothang_mcap = this.gridEditors[k].Value == '' ? 0 : this.gridEditors[k].Value
                    }
                }
                if (sothang_mcap == 0) {
                    sothang_mcap = 12
                }
                let tyle_thoigian = sothang_mcap / 12

                this._sl_cha = textValue * tyle_thoigian
                this.HT_ThietBi()
                this._sl_cha = -1
                }
            }

            if (repositoryItemTextEdit.Tag == 'SO_THANG') {
                if (this.vloaitb_id == LoaiHinhTB.VNPT_MCAP) {
                let textValue = repositoryItemTextEdit.Value

                if (!textValue || textValue == '' || isNaN(textValue)) return
                let soluong_mcap = 0
                for (let k = 0; k < this.gridEditors.length; k++) {
                    if (ItemList[k].sFieldName == 'SL_MAILING') {
                    soluong_mcap = !this.gridEditors[k].Value || this.gridEditors[k].Value == '' ? 0 : this.gridEditors[k].Value
                    }
                }
                if (soluong_mcap == 0) {
                    soluong_mcap = 1
                }

                let so_thang_mcap = 0
                if (textValue % 12 != 0) {
                    so_thang_mcap = 12
                } else {
                    so_thang_mcap = textValue
                }
                this._sl_cha = (so_thang_mcap / 12) * soluong_mcap
                // this.HT_ThietBi()
                this._sl_cha = -1
                }
            }
            },
            repositoryItemTextEdit_EditValueChanging(repoTextEdit) {
            // TextEdit repoTextEdit = (sender as TextEdit);
            let textValue = repoTextEdit.Value //repoTextEdit.EditValue.ToString();
            if (this.vloaitb_id == LoaiHinhTB.ELEARNING) {
                let phanloai_dv = ''
                for (var item of this.ItemList) {
                if (item.sFieldName == 'PHANLOAI_ID') {
                    phanloai_dv = item.sValue
                    break
                }
                }
                if (phanloai_dv == '97') {
                if (repoTextEdit.Tag == 'TONGSO_ND') {
                    if (repoTextEdit.Value != '0') {
                    this.$toast.error('Bạn không cần nhập số lượng giáo viên cho gói doanh nghiệp')
                    repoTextEdit.Cancel = true //FIXME can tim cach cancel thay doi
                    repoTextEdit.Value = 0 //FIXME fix tam cho van de o dong tren, thay vi cancel thay doi thi cho ve gia tri dung mac dinh.
                    }
                }
                if (repoTextEdit.Tag == 'SOLUONG') {
                    if (repoTextEdit.NewValue != '0') {
                    this.$toast.error('Bạn không cần nhập số lượng giáo viên cho gói doanh nghiệp')
                    repoTextEdit.Cancel = true //FIXME can tim cach cancel thay doi
                    repoTextEdit.Value = 0 //FIXME fix tam cho van de o dong tren, thay vi cancel thay doi thi cho ve gia tri dung mac dinh.
                    }
                }
                }
            }
            },
            repositoryItemTextEdit_Leave() {
            //Cho nay kha nang khong can vi binding 2 chieu nen data update san roi. Neu van can thi bo comment dong duoi, check lai xem co this.$ hay this.gr....$
            //this.grvMacdinh.$forceUpdate();
            },
            repositoryItemDateEdit_EditValueChanged(repositoryItemDateEdit) {
            try {
                //DateEdit repositoryItemDateEdit = (sender as DateEdit);
                // this.tlstpTop.Focus();
                // this.grvMacdinh.Focus();
                // RepositoryItemDateEdit repositoryItemDateEdit = !(sender is DateEdit) ? sender as RepositoryItemDateEdit : (sender as DateEdit).Properties;
                let ItemList = this.ItemList
                if (repositoryItemDateEdit.Tag == 'NGAY_BD') {
                let b_ngay_bd = false
                let b_ngay_kt = false
                let b_sothang = false
                let d_ngay_bd = new Date() //tt_nd.ngay_cn;
                let d_ngay_kt = new Date() //tt_nd.ngay_cn;
                let sothang = 0
                //int thanghuong_dc = Convert.ToInt32(dsDC_HDTB.Tables[0].Rows[0]["thang_huongdc"].ToString());

                for (var i = 0; i < ItemList.length; i++) {
                    if (this.gridEditors[i].Value && this.gridEditors[i].Value != '' && ItemList[i].sFieldName == 'SO_THANG') {
                    b_sothang = true
                    sothang = this.gridEditors[i].Value
                    }
                    if (ItemList[i].sFieldName == 'NGAY_BD' && this.gridEditors[i].Value != null) {
                    b_ngay_bd = true
                    // ItemList[i].sValue = this.gridEditors[i].Value
                    d_ngay_bd = this.gridEditors[i].Value
                    try {
                        d_ngay_bd = moment(d_ngay_bd, d_ngay_bd.length > 10 ? 'DD/MM/YYYY HH:mm:ss' : 'DD/MM/YYYY').toDate()
                    } catch (ex) {}
                    }
                    if (ItemList[i].sFieldName == 'NGAY_KT') b_ngay_kt = true

                    if (b_ngay_bd == true && b_ngay_kt == true && b_sothang == true) break
                }
                if (b_ngay_bd == true && b_ngay_kt == true && b_sothang == true) {
                    d_ngay_kt = new Date(d_ngay_bd)
                    d_ngay_kt = new Date(d_ngay_kt.setMonth(d_ngay_kt.getMonth() + sothang * 1)) //d_ngay_bd.AddMonths(sothang);
                    for (var index_1 = 0; index_1 < ItemList.length; ++index_1) {
                    if (ItemList[index_1].sFieldName == 'NGAY_KT') {
                        // ItemList[index_1].sValue = d_ngay_kt.ToString("dd/MM/yyyy");
                        try {
                            d_ngay_kt = moment(d_ngay_kt).format('DD/MM/YYYY')
                        } catch (ex) {
                            d_ngay_kt = d_ngay_kt
                        }
                        this.gridEditors[index_1].Value = d_ngay_kt
                    }
                    if (ItemList[index_1].sFieldName == 'NGAY_DUYTRI' && vloaitb_id == LoaiHinhTB.VEDU_LIB) {
                        // ItemList[index_1].sValue = d_ngay_kt.ToString("dd/MM/yyyy");
                        // try {
                        //     d_ngay_kt = moment(d_ngay_kt).format('DD/MM/YYYY')
                        // } catch (ex) {
                        //     d_ngay_kt = d_ngay_kt
                        // }
                        d_ngay_kt = new Date(d_ngay_bd)
                        this.gridEditors[index_1].Value = moment(new Date(d_ngay_kt.setMonth(d_ngay_kt.getMonth() + 12))).format('DD/MM/YYYY') //d_ngay_bd.AddMonths(12);
                    }
                    }
                }
                // grcMacdinh.RefreshDataSource();
                //Cho nay kha nang khong can vi binding 2 chieu nen data update san roi. Neu van can thi bo comment dong duoi, check lai xem co this.$ hay this.gr....$
                //this.grvMacdinh.$forceUpdate();
                }
            } catch (ex) {
                this.$toast.error('Có lỗi: ' + ex)
                if (this.debug) console.error(ex)
            }
            },
            async repositoryItemLookUpEdit_EditValueChanged(repositoryItemLookUpEdit) {
            if (!(await this.repositoryItemLookUpEdit_EditValueChanging(repositoryItemLookUpEdit))) return
            //Neu la linhvuc_id thi reset lai thong tin dich vu gia tang dang ky (Neu co) Do DVGT phu thuoc linh vuc
            try {
                let ItemList = this.ItemList
                // RepositoryItemGridLookUpEdit repositoryItemLookUpEdit = !(sender is GridLookUpEdit) ? sender as RepositoryItemGridLookUpEdit : (sender as GridLookUpEdit).Properties;
                this.PhanLoai_ID = ',' + repositoryItemLookUpEdit.Value + ',' //PhanLoai_ID = "," + ((GridLookUpEdit)sender).EditValue.ToString() + ",";
                //if (repositoryItemLookUpEdit.ValueMember == "LINHVUC_ID")
                if (repositoryItemLookUpEdit.ID == 'LINHVUC_ID') {
                //FIXME can test ky lai cho nay, ValueMember co kha nang ~ ID, neu khong phai tim cach khac xu ly!!!
                this.LAY_DS_DVGT_MACDINH(this.vloaitb_id)
                }
                if (repositoryItemLookUpEdit.ID == 'THOIHAN_ID') {
                //FIXME can test ky lai cho nay, ValueMember co kha nang ~ ID, neu khong phai tim cach khac xu ly!!!
                let thoihan = repositoryItemLookUpEdit.Value //((GridLookUpEdit)sender).EditValue.ToString();
                if (thoihan == '2') {
                    for (var ii = 0; ii < ItemList.length; ii++) {
                        if (!this.gridEditors[ii] || !ItemList[ii]) continue
                        if (ItemList[ii].sFieldName == 'SO_THANG' && (!this.gridEditors[ii].Value || this.gridEditors[ii].Value <= 0)) {
                            // ItemList[ii].sValue = '1200'
                            this.gridEditors[ii].Value = 1200
                        }
                        if (ItemList[ii].sFieldName == 'NGAY_KT' && !this.gridEditors[ii].Value) {
                            let ngaycn = new Date()
                            ngaycn = new Date(ngaycn.setMonth(ngaycn.getMonth() + 1200))
                            // ItemList[ii].sValue = moment(ngaycn).format('DD/MM/YYYY')
                            this.gridEditors[ii].Value = moment(ngaycn).format('DD/MM/YYYY')
                        }
                    }
                } else {
                    for (var ii = 0; ii < ItemList.length; ii++) {
                        if (ItemList[ii].sFieldName == 'SO_THANG' && (!this.gridEditors[ii].Value || this.gridEditors[ii].Value <= 0)) {
                            // ItemList[ii].sValue = '0'
                            this.gridEditors[ii].Value = 0
                        }
                        if (ItemList[ii].sFieldName == 'NGAY_KT' && !this.gridEditors[ii].Value) {
                            let ngaycn = new Date()
                            // ItemList[ii].sValue = moment(ngaycn).format('DD/MM/YYYY')
                            this.gridEditors[ii].Value = moment(ngaycn).format('DD/MM/YYYY')
                            // ItemList[ii].sValue = tt_nd.ngay_cn.ToString("dd/MM/yyyy");
                            // this.gridEditors[ii].Value = tt_nd.ngay_cn;
                        }
                    }
                }
                }
                if (repositoryItemLookUpEdit.ID == 'PHANLOAI_ID' || (repositoryItemLookUpEdit.ID == 'LOAICTS_ID' && this.vloaitb_id == LoaiHinhTB.SMART_CA)) {
                ////FIXME can test ky lai cho nay, ValueMember co kha nang ~ ID, neu khong phai tim cach khac xu ly!!!
                //Nếu dịch vụ là 5002 thì lấy lại thông tin combo box tốc độ
                for (var i = 0; i < ItemList.length; i++) {
                    if (this.vloaitb_id == LoaiHinhTB.BRANDNAME && repositoryItemLookUpEdit.Value && repositoryItemLookUpEdit.Value != '0')
                    if (ItemList[i].sFieldName.indexOf('LINHVUC_') == 0 && ItemList[i].sFieldName.indexOf('_ID') == ItemList[i].sFieldName.length - 3)
                        if (ItemList[i].SqlData && ItemList[i].SqlData.length > 0) {
                        let newds = ItemList[i].SqlData.filter((x) => x.PHANLOAI_ID == repositoryItemLookUpEdit.Value)
                        if (newds && newds.length > 0) {
                            newds.unshift({ LINHVUC_ID: 0, LABEL_TYPE_NAME: '', PHANLOAI_ID: 0 })
                            this.gridEditors[i].DataSource = newds.map((x) => ({ text: x[ItemList[i].sLookUpValueField], id: x[ItemList[i].sLookUpKeyField] }))
                            if (!this.gridEditors[i].DataSource.find((x) => x.id == this.gridEditors[i].Value)) {
                            this.gridEditors[i].Value = this.gridEditors[i].DataSource[0].id
                            // ItemList[i].sValue = this.gridEditors[i].DataSource[0].id
                            // ItemList[i].Value = this.gridEditors[i].DataSource[0].id
                            }
                        } else {
                            this.gridEditors[i].DataSource = [{ text: 'Không áp dụng', id: 0 }]
                            this.gridEditors[i].Value = 0
                            // ItemList[i].sValue = 0
                            // ItemList[i].Value = 0
                        }
                        }
                    if (ItemList[i].sFieldName == 'PHANLOAI_ID' || ItemList[i].sFieldName == 'LOAICTS_ID') {
                        // ItemList[i].sValue = repositoryItemLookUpEdit.Value
                    }
                    if (ItemList[i].strDepandField == 'DAILY' || (ItemList[i].sFieldName == 'LINHVUC_ID' && this.vloaitb_id != LoaiHinhTB.BRANDNAME)) {
                    let data = ItemList[i].SqlData
                    if (this.gridEditors[i]) this.gridEditors[i].DataSource = data.map((x) => ({ text: x[ItemList[i].sLookUpValueField], id: x[ItemList[i].sLookUpKeyField] }))
                    // await API.post_sp_huonggiao_runsql(this.axios, { "command": SQL }).then((response) => {
                    //   if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    //     return response.data.data.data_json;
                    //   }
                    //   else return [];
                    // });//this.bangts.GetSQL(SQL, "");
                    //((RepositoryItemGridLookUpEdit)gridEditors[i].RepositoryItem).DataSource = (object)data.Tables[0];
                    // break;
                    }
                    if (this.vloaitb_id == LoaiHinhTB.ELEARNING) {
                    let phanloai_id_elearning = repositoryItemLookUpEdit.Value //((GridLookUpEdit)sender).EditValue.ToString();
                    if (phanloai_id_elearning == '97') {
                        // gói doanh nghiệp
                        for (var ii = 0; ii < ItemList.length; ii++) {
                        if (ItemList[ii].sFieldName == 'TONGSO_ND') {
                            // ItemList[ii].sValue = '0'
                            this.gridEditors[ii].Value = '0'
                        }
                        if (ItemList[ii].sFieldName == 'SOLUONG') {
                            // ItemList[ii].sValue = '0'
                            this.gridEditors[ii].Value = '0'
                        }
                        }
                    }
                    }
                }
                }
                if (repositoryItemLookUpEdit.ID == 'TOCDO_ID') {
                //FIXME can test ky lai cho nay, ValueMember co kha nang ~ ID, neu khong phai tim cach khac xu ly!!!
                if (this.vloaitb_id == LoaiHinhTB.TTDL_HDDT) {
                    // VNPT Invoice
                    var invoiceTocdo_id = repositoryItemLookUpEdit.Value
                    API.get_tocdo_adsl(this.axios, {})
                    .then((response) => {
                        //.post_sp_huonggiao_runsql(this.axios, { "command": `select ${DatabaseConstants.DB2}.TUDONG_TINHSOTHANG_SD_THEO_GOICUOC(${this.vloaitb_id}) kq from dual` }).then((response) => {
                        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                            let datalist = response.data.data //.data_json;
                            if (!datalist) datalist = []
                            let currentData = datalist.find((x) => x.tocdo_id == invoiceTocdo_id)
                            for (var i = 0; i < ItemList.length; i++)
                                if (ItemList[i].sFieldName == 'SL_MAILING') {
                                    if (currentData && currentData.ca_key_length && currentData.ca_key_length > 0) {
                                        // ItemList[i].sValue = currentData.ca_key_length + ''
                                        // ItemList[i].Value = currentData.ca_key_length
                                        this.gridEditors[i].Value = currentData.ca_key_length
                                    } else {
                                        // ItemList[i].sValue = ''
                                        // ItemList[i].Value = ''
                                        this.gridEditors[i].Value = ''
                                    }
                                }
                        }
                    })
                    .catch(() => {})
                }
                let vtocdo_id = 0
                let vmuccuoc_id = 0
                for (var i = 0; i < ItemList.length; i++) {
                    if (ItemList[i].sFieldName == 'TOCDO_ID') {
                        vtocdo_id = repositoryItemLookUpEdit.Value //((GridLookUpEdit)sender).EditValue.ToString();
                    // vtocdo_id = ItemList[i].sValue

                    this.tocdo_id = vtocdo_id
                    // this.listbox.cboGoiCuocAdsl.value = vtocdo_id
                    // await this.HT_GIAODIEN_DONVI();
                    // await this.HT_DS_KhuyenMai_Combobox()
                    try {
                        let checkTD = await API.post_tudong_tinhsothang_sd_theo_goicuoc(this.axios, { vloaitb_id: this.vloaitb_id }).then((response) => {
                        //.post_sp_huonggiao_runsql(this.axios, { "command": `select ${DatabaseConstants.DB2}.TUDONG_TINHSOTHANG_SD_THEO_GOICUOC(${this.vloaitb_id}) kq from dual` }).then((response) => {
                        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                            return response.data.data //.data_json;
                        } else return []
                        })
                        // if (tdan.GET_VALUE_FUNC("{?DB2}.TUDONG_TINHSOTHANG_SD_THEO_GOICUOC", "vloaitb_id", vloaitb_id).ToString() == "ok")
                        // if (checkTD && checkTD.length>0 && checkTD[0].kq && checkTD[0].kq == "ok")
                        //if (checkTD && checkTD.length>0 && checkTD[0].kq && checkTD[0].kq == "ok")
                        if (checkTD && checkTD == 'ok') {
                        let dt_tocdo_goicuoc = await API.post_dt_tocdo_goicuoc(this.axios, { id: vtocdo_id }).then((response) => {
                            //.post_sp_huonggiao_runsql(this.axios, { "command": `Select sothang_sd, rc_id from ${DatabaseConstants.DB2}.tocdo_adsl where tocdo_id = ${vtocdo_id}` }).then((response) => {
                            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                            return response.data.data //.data_json;
                            } else return []
                        }) //objCheckData.GET_DATA_SQL(@"Select sothang_sd, rc_id from {?DB2}.tocdo_adsl where tocdo_id = :tocdo_id", "tocdo_id", vtocdo_id);
                        let so_thang_sd = dt_tocdo_goicuoc[0]['sothang_sd']
                        var index_so_thang = -1
                        var index_ngay_bd = -1
                        var index_ngay_kt = -1
                        for (var index_1 = 0; index_1 < ItemList.length; ++index_1) {
                            if (ItemList[index_1].sFieldName == 'SO_THANG') {
                            index_so_thang = index_1
                            }
                            if (ItemList[index_1].sFieldName == 'NGAY_BD') {
                            index_ngay_bd = index_1
                            }
                            if (ItemList[index_1].sFieldName == 'NGAY_KT') {
                            index_ngay_kt = index_1
                            }
                        }
                        try {
                            if (!(index_so_thang == -1 || index_ngay_bd == -1 || index_ngay_kt == -1)) {
                            if (this.gridEditors[index_so_thang]) this.gridEditors[index_so_thang].Value = so_thang_sd
                            ItemList[index_so_thang].sValue = so_thang_sd
                            if (!ItemList[index_ngay_bd].sValue && this.gridEditors[index_ngay_bd] && this.gridEditors[index_ngay_bd].Value) ItemList[index_ngay_bd].sValue = this.gridEditors[index_ngay_bd].Value
                            let tmpdate = moment(ItemList[index_ngay_bd].sValue, 'DD/MM/YYYY').toDate()
                            tmpdate = new Date(tmpdate.setMonth(tmpdate.getMonth() + so_thang_sd))
                            let ngay_kt_orm = moment(tmpdate).format('DD/MM/YYYY') //(DateTime.ParseExact(ItemList[index_ngay_bd].sValue, "dd/MM/yyyy", CultureInfo.InvariantCulture).AddMonths(Convert.ToInt32(so_thang_sd))).ToString("dd/MM/yyyy");
                            if (this.gridEditors[index_ngay_kt]) this.gridEditors[index_ngay_kt].Value = ngay_kt_orm
                            // ItemList[index_ngay_kt].sValue = ngay_kt_orm
                            }
                        } catch (ex) {
                            if (this.debug) console.error('DEBUG', ex)
                        }
                        }
                        let kq_kt_tt_tocdo = await API.post_tudong_tinhthuoctinh_tocdo_adsl(this.axios, { id: this.vloaitb_id, id1: vtocdo_id }).then((response) => {
                        //.post_sp_huonggiao_runsql(this.axios, { "command": `select ${DatabaseConstants.DB2}.TUDONG_TINHTHUOCTINH_TOCDO_ADSL(${this.vloaitb_id,vtocdo_id}) kq from dual` }).then((response) => {
                        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data.data_json && response.data.data.data_json.kq) {
                            return response.data.data.kq ? response.data.data.kq : -1 //.data_json.kq;
                        } else return -1
                        }) //tdan.GET_VALUE_FUNC("{?DB2}.TUDONG_TINHTHUOCTINH_TOCDO_ADSL", "vloaitb_id", vloaitb_id, "vtocdo_id", vtocdo_id).ToString();
                        if (kq_kt_tt_tocdo != '-1') {
                        // DataTable dt_tocdo_goicuoc = objCheckData.GET_DATA_SQL(@"Select sothang_sd, rc_id from {?DB2}.tocdo_adsl where tocdo_id = :tocdo_id", "tocdo_id", vtocdo_id);
                        let thuoctinh = kq_kt_tt_tocdo.split(';')[0]
                        let giatri = kq_kt_tt_tocdo.split(';')[1]
                        var index_thuoctinh = -1
                        for (var index_1 = 0; index_1 < ItemList.length; ++index_1) {
                            if (ItemList[index_1].sFieldName == thuoctinh) {
                            if (kq_kt_tt_tocdo.split(';')[2] == '1') {
                                this.gridEditors[index_1].Value = giatri
                            } else {
                                this.gridEditors[index_1].Value = giatri
                            }
                            // ItemList[index_1].sValue = giatri
                            }
                        }
                        }

                        checkTD = await API.post_kiemtra_dichvu_tudong_thuoctinh_goicuoc(this.axios, { vloaitb_id: this.vloaitb_id }).then((response) => {
                        //.post_sp_huonggiao_runsql(this.axios, { "command": `select ${DatabaseConstants.DB2}.KIEMTRA_DICHVU_TUDONG_THUOCTINH_GOICUOC(${this.vloaitb_id}) kq from dual` }).then((response) => {
                        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                            return response.data.data //.data_json;
                        } else return []
                        })
                        //if (tdan.GET_VALUE_FUNC("{?DB2}.KIEMTRA_DICHVU_TUDONG_THUOCTINH_GOICUOC", "vloaitb_id", vloaitb_id).ToString() == "ok")
                        if (checkTD && checkTD == 'ok') {
                        let dt_tskt_goicuoc = await API.post_lay_thongso_kt_goicuoc(this.axios, { vtocdo_id: this.tocdo_id }).then((response) => {
                            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                            return this.UpperCasePropertyList(response.data.data)
                            } else return []
                        }) //tdan.GET_DATA_PROC("{?DB2}.LAY_THONGSO_KT_GOICUOC", "vtocdo_id", tocdo_id);
                        if (dt_tskt_goicuoc && dt_tskt_goicuoc.length > 0) {
                            for (let r of dt_tskt_goicuoc) {
                            for (var i_ts = 0; i_ts < ItemList.length; i_ts++) {
                                if (ItemList[i_ts].sFieldName == r['FIELD_NAME']) {
                                if (r['DATA_TYPE'] == '0') {
                                    // ItemList[i_ts].sValue = r['FIELD_VALUE']
                                    this.gridEditors[i_ts].Value = r['FIELD_VALUE']
                                }

                                if (r['DATA_TYPE'] == '1') {
                                    // ItemList[i_ts].sValue = r['FIELD_VALUE']
                                    this.gridEditors[i_ts].Value = r['FIELD_VALUE']
                                    if (r['FIELD_NAME'] == 'SO_THANG') {
                                    let vt_ngaybd = -1,
                                        vt_ngaykt = -1
                                    for (var i_gr = 0; i_gr < ItemList.length; i_gr++) {
                                        if (ItemList[i_gr].sFieldName == 'NGAY_BD') {
                                        vt_ngaybd = i_gr
                                        }
                                        if (ItemList[i_gr].sFieldName == 'NGAY_KT') {
                                        vt_ngaykt = i_gr
                                        //break;
                                        }
                                    }
                                    if (vt_ngaybd != -1 || vt_ngaykt != -1) {
                                        let tmpdate = moment(this.gridEditors[vt_ngaybd].Value, 'DD/MM/YYYY').toDate()
                                        tmpdate = new Date(tmpdate.setMonth(tmpdate.getMonth() + r['FIELD_VALUE'] * 1))
                                        let ngay_kt = moment(tmpdate).format('DD/MM/YYYY')
                                        // DateTime ngay_kt = DateTime.ParseExact(ItemList[vt_ngaybd].sValue, "dd/MM/yyyy", CultureInfo.InvariantCulture).AddMonths(Convert.ToInt32(r["FIELD_VALUE"].ToString()));
                                        // ItemList[vt_ngaykt].sValue = ngay_kt
                                        this.gridEditors[vt_ngaykt].Value = ngay_kt
                                    }
                                    }
                                }

                                if (r['DATA_TYPE'] == 2) {
                                    if (r['FIELD_VALUE'].indexOf('NOW') >= 0) {
                                    let gt_ngay = r['FIELD_VALUE'].split('+')
                                    if (gt_ngay.length == 1) {
                                        // ItemList[i_ts].sValue = moment(new Date()).format('DD/MM/YYYY') //tt_nd.ngay_cn.ToString("dd/MM/yyyy");
                                        this.gridEditors[i_ts].Value = moment().format('DD/MM/YYYY') //tt_nd.ngay_cn;
                                    } else {
                                        let add = gt_ngay[1] * 1
                                        let gt_ngay_gc = moment()
                                        .add(1, gt_ngay[2] ? gt_ngay[2] : 'D')
                                        .toDate()
                                        // if (gt_ngay[2] == "Y")
                                        // {
                                        //     gt_ngay_gc = moment().add(1, 'Y');//tt_nd.ngay_cn.AddYears(add);
                                        // }
                                        // else if (gt_ngay[2] == "M")
                                        // {
                                        //     gt_ngay_gc = tt_nd.ngay_cn.AddMonths(add);
                                        // }
                                        // else
                                        // {
                                        //     gt_ngay_gc = tt_nd.ngay_cn.AddDays(add);
                                        // }
                                        // ItemList[i_ts].sValue = moment(gt_ngay_gc).format('DD/MM/YYYY')
                                        this.gridEditors[i_ts].Value = moment(gt_ngay_gc).format('DD/MM/YYYY')
                                    }
                                    } else {
                                    // ItemList[i_ts].sValue = r['FIELD_VALUE']
                                    this.gridEditors[i_ts].Value = moment(r['FIELD_VALUE'], 'DD/MM/YYYY').toDate() //DateTime.ParseExact(r["FIELD_VALUE"].ToString(), "dd/MM/yyyy", CultureInfo.InvariantCulture);
                                    }
                                }
                                }
                            }
                            }
                        }
                        }
                        // this.listbox.cboGoiCuocAdsl.value = vtocdo_id
                        //FIXME 2 dong nay chi de thong bao gia tri combobox da thay doi, tam bo qua
                        // if (this.listbox.cboGoiCuocAdsl.value == 0)
                        //     cboGoiCuocAdsl.Properties.GetKeyValue(0);
                        repositoryItemLookUpEdit.Value = vtocdo_id

                        // this.HT_GIAODIEN_DONVI();
                        // this.HT_DS_KhuyenMai_Combobox()
                    } catch (ex) {
                        if (this.debug) console.error(ex)
                        // Message_Box.Show(ex.Message);
                    }
                    }
                    if (ItemList[i].sFieldName == 'MUCCUOC_ID') {
                    vmuccuoc_id = this.gridEditors[i].Value
                    this.muccuoc_id = vmuccuoc_id
                    
                    }
                    if (ItemList[i].sFieldName == 'HEDIEUHANH_ID') {
                    let data = ItemList[i].SqlData
                    if (this.gridEditors[i]) {
                        if (data && data.length > 0) {
                        data = data.map((x) => ({ text: x[this.gridEditors[i].DisplayMember], id: x[this.gridEditors[i].ValueMember] }))
                        }
                        this.gridEditors[i].DataSource = data
                    }
                    // await API.post_sp_huonggiao_runsql(this.axios, { "command": SQL }).then((response) => {
                    //   if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    //     return response.data.data.data_json;
                    //   }
                    //   else return [];
                    // });
                    // DataSet data = this.bangts.GetSQL(SQL, "");
                    // ((RepositoryItemGridLookUpEdit)gridEditors[i].RepositoryItem).DataSource = (object)data.Tables[0];
                    // break
                    }
                }
                // let pdata = {
                //     vapld_id: 0,
                //     vdoituong_id: this.thongso && this.thongso.doituong_id ? this.thongso.doituong_id : 0,
                //     vkhuld_id: 0,
                //     vloaitb_id: vloaitb_id,
                //     vmuccuoc_id: vmuccuoc_id,
                //     vngay: moment().format("DD/MM/YYYY"),//this.NgayYeuCau.indexOf(' ') > 0 ? this.NgayYeuCau.substring(0, this.NgayYeuCau.indexOf(' ')) : this.NgayYeuCau,
                //     vphold_id: 0,
                //     vphuongld_id: 0,
                //     vtocdo_id: vtocdo_id
                // }
                // let api = cache.getters.getFromCache('post_lay_ds_mucuoc_tb', pdata)

                // if (!api) api = API.post_lay_ds_mucuoc_tb(this.axios, pdata)
                // else
                //     api = new Promise((resolve, reject) => {
                //     resolve(api)
                //     })

                // let ds = await api.then((response) => {
                //     //API.post_lay_ds_mucuoc_tb(this.axios, pdata).then((response) => {
                //     cache.dispatch('addToCache', { key: 'post_lay_ds_mucuoc_tb', subkey: pdata, data: response })
                //     if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                //     return response.data.data
                //     } else return []
                // }) //tcdanhba.LAY_DS_MUCUOC_TB(khuld_id, apld_id, phold_id, phuongld_id, vloaitb_id, this.listbox.doituong.value, vtocdo_id, vmuccuoc_id, dtpNgayYeuCau.Value.ToString("dd/MM/yyyy"));
                // if (ds.length > 0) {
                //     this.listbox.muccuoc_tb.list = ds
                //     if (!this.listbox.muccuoc_tb.value || !this.listbox.muccuoc_tb.list.find((x) => (x.muccuoctb_id ? x.muccuoctb_id : x.mucuoctb_id) == this.listbox.muccuoc_tb.value)) this.listbox.muccuoc_tb.value = ds[0].mucuoctb_id
                //     // cboMucCuocTB.ValueMember = "mucuoctb_id";
                //     // cboMucCuocTB.DisplayMember = "tenmuccuoc";
                // } else this.listbox.muccuoc_tb.list = []
                // this.LayTienTheoKhoanMuc()
                // this.LAY_DS_DVGT_MACDINH(this.vloaitb_id)
                }
                if (repositoryItemLookUpEdit.ID == 'MUCCUOC_ID') {
                // FIXME thay ValueMember = ID, can test lai
                let vtocdo_id = 0
                let vmuccuoc_id = 0
                for (var i = 0; i < ItemList.length; i++) {
                    if (ItemList[i].sFieldName == 'MUCCUOC_ID') {
                        vmuccuoc_id = repositoryItemLookUpEdit.Value //((GridLookUpEdit)sender).EditValue.ToString();
                    // vmuccuoc_id = ItemList[i].sValue * 1
                    this.muccuoc_id = vmuccuoc_id
                    
                    }
                    if (ItemList[i].sFieldName == 'TOCDO_ID') {
                    vtocdo_id = this.gridEditors[i].Value * 1
                    this.tocdo_id = vtocdo_id
                    }
                }

                // let pdata = {
                //     vapld_id: this.diachiLD.AP_ID ? this.diachiLD.AP_ID : 0,
                //     vdoituong_id: this.listbox.doituong.value ? this.listbox.doituong.value : this.listbox.doituong.list && this.listbox.doituong.list[0] ? this.listbox.doituong.list[0].doituong_id : 0,
                //     vkhuld_id: this.diachiLD.KHU_ID ? this.diachiLD.KHU_ID : 0,
                //     vloaitb_id: vloaitb_id,
                //     vmuccuoc_id: vmuccuoc_id,
                //     vngay: this.NgayYeuCau.indexOf(' ') > 0 ? this.NgayYeuCau.substring(0, this.NgayYeuCau.indexOf(' ')) : this.NgayYeuCau,
                //     vphold_id: this.diachiLD.PHO_ID ? this.diachiLD.PHO_ID : 0,
                //     vphuongld_id: this.diachiLD.PHUONG_ID ? this.diachiLD.PHUONG_ID : 0,
                //     vtocdo_id: vtocdo_id
                // }
                // let api = cache.getters.getFromCache('post_lay_ds_mucuoc_tb', pdata)

                // if (!api) api = API.post_lay_ds_mucuoc_tb(this.axios, pdata)
                // else
                //     api = new Promise((resolve, reject) => {
                //     resolve(api)
                //     })

                // let ds = await api.then((response) => {
                //     //API.post_lay_ds_mucuoc_tb(this.axios, pdata).then((response) => {
                //     cache.dispatch('addToCache', { key: 'post_lay_ds_mucuoc_tb', subkey: pdata, data: response })
                //     // let ds = await API.post_lay_ds_mucuoc_tb(this.axios, pdata).then((response) => {
                //     if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                //     return response.data.data
                //     } else return []
                // })
                // // let ds = tcdanhba.LAY_DS_MUCUOC_TB(khuld_id, apld_id, phold_id, phuongld_id, vloaitb_id, this.listbox.doituong.value, vtocdo_id, vmuccuoc_id, dtpNgayYeuCau.Value.ToString("dd/MM/yyyy"));
                // if (ds.length > 0) {
                //     this.listbox.muccuoc_tb.list = ds
                //     if (!this.listbox.muccuoc_tb.value || !this.listbox.muccuoc_tb.list.find((mc) => (mc.muccuoctb_id ? mc.muccuoctb_id : mc.mucuoctb_id) == this.listbox.muccuoc_tb.value)) this.listbox.muccuoc_tb.value = ds[0].mucuoctb_id
                //     // cboMucCuocTB.ValueMember = "mucuoctb_id";
                //     // cboMucCuocTB.DisplayMember = "tenmuccuoc";
                // } else this.listbox.muccuoc_tb.list = [] //cboMucCuocTB.DataSource = null;
                }
            } catch (ex) {
                this.$toast.error('Có lỗi: ' + ex)
                if (this.debug) console.error(ex)
            }
            },
            repositoryItemLookUpEdit_leave() {
            //Cho nay kha nang khong can vi binding 2 chieu nen data update san roi. Neu van can thi bo comment dong duoi, check lai xem co this.$ hay this.gr....$
            //this.grvMacdinh.$forceUpdate();
            },
            async repositoryItemLookUpEdit_EditValueChanging(repositoryItemLookUpEdit) {
            let ItemList = this.ItemList
            if (this.vloaitb_id == LoaiHinhTB.CA) {
                // RepositoryItemGridLookUpEdit repositoryItemLookUpEdit = !(sender is GridLookUpEdit) ? sender as RepositoryItemGridLookUpEdit : (sender as GridLookUpEdit).Properties;
                //FIXME comment tạm lại vì dữ liệu phân loại khách hàng thiếu field loại
                // if (repositoryItemLookUpEdit.ID == "LOAICTS_ID")
                // {
                //     if (this.vkieukh_id_ == 0)
                //     {
                //         if (repositoryItemLookUpEdit.Value != 1)
                //         {
                //             repositoryItemLookUpEdit.Cancel = false;
                //             this.$toast.error("Bạn chỉ được chọn loại khách hàng cá nhân!");
                //             return false;
                //         }
                //     }
                //     else
                //     {
                //         if (repositoryItemLookUpEdit.Value == 1)
                //         {
                //             repositoryItemLookUpEdit.Cancel = true;
                //             this.$toast.error("Bạn chỉ được chọn loại khách hàng doanh nghiệp hoặc cá nhân thuộc tổ chức/doanh nghiệp!");
                //             return false;
                //         }
                //     }
                // }

                if (repositoryItemLookUpEdit.ID == 'TOCDO_ID') {
                let vtocdo_id = repositoryItemLookUpEdit.Value
                let dt_tocdo_goicuoc = await API.post_dt_tocdo_goicuoc(this.axios, { id: vtocdo_id }).then((response) => {
                    //.post_sp_huonggiao_runsql(this.axios, { "command": `Select sothang_sd, rc_id from ${DatabaseConstants.DB2}.tocdo_adsl where tocdo_id = ${vtocdo_id}` }).then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    return response.data.data //.data_json;
                    } else return []
                }) //objCheckData.GET_DATA_SQL(@"Select sothang_sd, rc_id from {?DB2}.tocdo_adsl where tocdo_id = :tocdo_id", "tocdo_id", vtocdo_id);

                let rc_id = dt_tocdo_goicuoc[0]['rc_id'] * 1
                let vloaicts_id = 0
                for (var index_1 = 0; index_1 < ItemList.length; index_1++) {
                    if (ItemList[index_1].sFieldName == 'LOAICTS_ID') {
                    if (!this.gridEditors[index_1].Value) {
                        vloaicts_id = 0
                    } else {
                        vloaicts_id = this.gridEditors[index_1].Value * 1
                    }
                    break
                    }
                }
                let kt_chon_goicuoc_ca = await API.post_kt_chon_goicuoc_ca(this.axios, { id: rc_id, id1: vloaicts_id }).then((response) => {
                    //.post_sp_huonggiao_runsql(this.axios, { "command": `Select ${DatabaseConstants.DB2}.KT_CHON_GOICUOC_CA(${rc_id},${vloaicts_id}) kq from dual` }).then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    return response.data.data //.data_json;
                    } else return response.data.message_detail
                }) //tdan.GET_VALUE_FUNC("{?DB2}.KT_CHON_GOICUOC_CA", "vrc_id", rc_id, "vloaicts_id", vloaicts_id).ToString();
                if (!kt_chon_goicuoc_ca || kt_chon_goicuoc_ca != 'ok') {
                    repositoryItemLookUpEdit.Cancel = true //FIXME can xem cach cancel phan xu ly nay!
                    this.$toast.error(kt_chon_goicuoc_ca)
                    // return false; // FIXME Ham check nay dang loi nen comment tam lai de chay, sua xong bo comment sau.
                }
                }
            }
            return true
            },
            async repositoryItemButtonEdit_ButtonClick(repositoryItemButtonEdit) {
            try {
                let ItemList = this.ItemList
                // BO SUNG 2022-11-02 THANGDM1
                // #region Báo hiệu quốc tế
                // if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.BaoHieuQuocTe ||
                //     Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.THOAIQT)
                // {
                //     if (repositoryItemButtonEdit.Properties.Tag.ToString() == "THIETBI")
                //     {
                //         string ma_pos = "";
                //         try
                //         {
                //             string sql = "select ma_pos from " + DatabaseConstants.DB2 + ".hdtb_cntt where hdtb_id = " + hdtb_id + "";
                //             ma_pos = tdan.GET_VALUE_SQL(sql).ToString();
                //             if (ma_pos == "")
                //             {
                //                 Message_Box.ShowTB("Không có thông tin loại cước. Hãy kiểm tra lại thông tin loại cước");
                //             }
                //         }
                //         catch
                //         {
                //             Message_Box.ShowTB("Lỗi lấy thông tin loại cước. Hãy kiểm tra lại thông tin loại cước");
                //         }
                //         string[] loaicuoc = ma_pos.Split(',');
                //         int point = 0;
                //         for (int i = 0; i < loaicuoc.Count(); i++)
                //         {
                //             if ((int.Parse(loaicuoc[i]) == 841 || int.Parse(loaicuoc[i]) == 844) && hdtb_id >= 0 && ma_tb != "")
                //             {
                //                 frmTronGoiBaoHieuQT frm = new frmTronGoiBaoHieuQT(hdtb_id, ma_tb, luong_id, int.Parse(loaicuoc[i].ToString()));
                //                 frm.ShowDialog();
                //                 if (frm.is_ghilai) point++;

                //             }
                //             if ((int.Parse(loaicuoc[i]) == 842 || int.Parse(loaicuoc[i]) == 845) && hdtb_id >= 0 && ma_tb != "")
                //             {
                //                 frmGoiSanLuong frm = new frmGoiSanLuong(hdtb_id, ma_tb, luong_id, int.Parse(loaicuoc[i].ToString()));
                //                 frm.ShowDialog();
                //                 if (frm.is_ghilai) point++;
                //             }
                //             if ((int.Parse(loaicuoc[i]) == 843 || int.Parse(loaicuoc[i]) == 846) && hdtb_id >= 0 && ma_tb != "")
                //             {
                //                 frmLuyTienSanLuong frm = new frmLuyTienSanLuong(hdtb_id, ma_tb, luong_id, int.Parse(loaicuoc[i].ToString()));
                //                 frm.ShowDialog();
                //                 if (frm.is_ghilai) point++;
                //             }
                //             if (point == loaicuoc.Count())
                //             {
                //                 for (int j = 0; j < ItemList.Length; j++)
                //                 {
                //                     if (this.ItemList[j].sFieldName == "THIETBI")
                //                     {
                //                         gridEditors[j].Value = tt_nd.ma_nd + " Đã cập nhật";
                //                         ItemList[j].sValue = tt_nd.ma_nd + " Đã cập nhật";
                //                     }
                //                 }
                //             }

                //         }
                //     }
                // }
                // #endregion
                // #region VNedu Content
                // if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.EduContent)
                // {
                //     string id_truong = "";
                //     string ten_truong = "";
                //     try
                //     {
                //         id_truong = ItemList.ToList().Find(x => x.sFieldName == "MA_PHU").sValue;
                //         ten_truong = ItemList.ToList().Find(x => x.sFieldName == "THUONGHIEU").sValue;
                //     }
                //     catch
                //     {
                //     }

                //     if (repositoryItemButtonEdit.Properties.Tag.ToString() == "ROUTER" && id_truong != "" && ten_truong != "")
                //     {
                //         string kq_check = tdan.GET_VALUE_FUNC("{?DB2}.FN_CHECK_DK_DV_VNEDU_CONTENT", "vloaitb_id", 323, "vtocdo_id", tocdo_id, "vid_truong", id_truong, "vtentruong", ten_truong).ToString();
                //         if (kq_check != "OK")
                //         {
                //             Message_Box.ShowTB(kq_check.ToString());
                //             return;
                //         }
                //         else
                //         {
                //             for (int i = 0; i < ItemList.Length; i++)
                //             {
                //                 if (ItemList[i].sFieldName == "ROUTER")
                //                 {
                //                     gridEditors[i].Value = "Đủ điều kiện";
                //                     ItemList[i].sValue = "Đủ điều kiện";
                //                     grvMacdinh.RefreshData();
                //                 }
                //             }
                //         }
                //     }
                // }
                // #endregion
                // if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.VNPT_HKD)
                // {
                //     frmDiaChiCNTT fdccntt = new frmDiaChiCNTT();
                //     fdccntt.loaitb_id = LoaiHinhTB.VNPT_HKD;
                //     for (int i = 0; i < ItemList.Length; i++)
                //     {
                //         if (ItemList[i].sFieldName == "DIACHI_DT")
                //         {
                //             fdccntt.diachi_text = gridEditors[i].Value.ToString();
                //         }
                //         if (ItemList[i].sFieldName == "MUCDICH_IDS")
                //         {
                //             fdccntt.diachi_phuong = gridEditors[i].Value.ToString();
                //         }
                //     }
                //     fdccntt.ShowDialog();
                //     for (int i = 0; i < ItemList.Length; i++)
                //     {
                //         if (ItemList[i].sFieldName == "DIACHI_DT")
                //         {
                //             gridEditors[i].Value = fdccntt.diachi_text;
                //             ItemList[i].sValue = fdccntt.diachi_text;
                //         }
                //         if (ItemList[i].sFieldName == "MUCDICH_IDS")
                //         {
                //             gridEditors[i].Value = fdccntt.diachi_phuong;
                //             ItemList[i].sValue = fdccntt.diachi_phuong;
                //         }
                //     }
                // }
                // if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.VNPT_IQMS)
                // {
                //     frmDiaChiCNTT fdccntt = new frmDiaChiCNTT();
                //     fdccntt.loaitb_id = LoaiHinhTB.VNPT_IQMS;
                //     for (int i = 0; i < ItemList.Length; i++)
                //     {
                //         if (ItemList[i].sFieldName == "DIACHI_DT")
                //         {
                //             fdccntt.diachi_text = gridEditors[i].Value.ToString();
                //         }
                //         if (ItemList[i].sFieldName == "MUCDICH_IDS")
                //         {
                //             fdccntt.diachi_phuong = gridEditors[i].Value.ToString();
                //         }
                //     }
                //     fdccntt.ShowDialog();
                //     for (int i = 0; i < ItemList.Length; i++)
                //     {
                //         if (ItemList[i].sFieldName == "DIACHI_DT")
                //         {
                //             gridEditors[i].Value = fdccntt.diachi_text;
                //             ItemList[i].sValue = fdccntt.diachi_text;
                //         }
                //         if (ItemList[i].sFieldName == "MUCDICH_IDS")
                //         {
                //             gridEditors[i].Value = fdccntt.diachi_phuong;
                //             ItemList[i].sValue = fdccntt.diachi_phuong;
                //         }
                //     }
                // }

                // if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.SMART_CA)
                // {
                //     if (repositoryItemButtonEdit.Properties.Tag.ToString() == "MA_PHU")
                //     {
                //         frmChonViNganSach f = new frmChonViNganSach();
                //         f.vma_kh = txtMaKH.Text;
                //         f.ShowDialog();
                //         for (int i = 0; i < ItemList.Length; i++)
                //         {
                //             if (ItemList[i].sFieldName == "MA_PHU")
                //             {
                //                 ItemList[i].sValue = f.out_mavi;
                //                 gridEditors[i].Value = f.out_mavi;
                //                 break;
                //             }

                //         }
                //     }
                // }
                // END BO SUNG 2022-11-02 THANGDM1
                // ButtonEdit repositoryItemButtonEdit = (sender as ButtonEdit);
                // if (this.vloaitb_id == LoaiHinhTB.VNPT_SMCC) // bo do khong co chuc nang gi
                // {

                // }
                let kt_api = await API.post_loaihinh_tb_edu_api(this.axios, { id: this.vloaitb_id }).then((response) => {
                //.post_sp_huonggiao_runsql(this.axios, { "command": `Select ${DatabaseConstants.DB2}.LOAHINH_TB_EDU_API(${this.vloaitb_id}) kq from dual` }).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    return response.data.data[0] && response.data.data[0].kq ? response.data.data[0].kq : 'no' //.data_json.kq;
                } else return 'no'
                }) //tdan.GET_VALUE_FUNC("{?DB2}.LOAHINH_TB_EDU_API", "vloaitb_id", vloaitb_id).ToString();
                if (kt_api == 'ok') {
                if (repositoryItemButtonEdit.Tag == 'SL_MAILING') {
                    for (var i = 0; i < ItemList.length; i++) {
                    if (ItemList[i].sFieldName == 'SL_MAILING') {
                        if (this.vloaitb_id == LoaiHinhTB.TTDL_HDDT) {
                        this.popupComponentData = { dsHDTB_CT: this.dsHDTB_CT, soluong: this.gridEditors[i].Value || this.gridEditors[i].Value == '' ? 0 : this.gridEditors[i].Value }
                        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChonMauHDDT')
                        this.popupComponentName = 'popupChonMauHDDT'
                        this.Popup('popupComponentsCNTT')
                        break
                        }
                    }
                    }
                }
                if (repositoryItemButtonEdit.Tag == 'MA_PHU') {
                    if (this.vloaitb_id == LoaiHinhTB.Voice_Brandname) {
                    this.popup.DSThanhVien = true;
                    if (this.buttonSet != 1)
                        this.$refs.frmNhapDSThanhVien.hdtb_id = this.hdtb_id
                    else
                        this.$toast.error('Chưa ghi lại hợp đồng. Hãy ghi lại hợp đồng trước');
                    this.$refs.frmNhapDSThanhVien._ds_hdtbct = this.dsHDTB_CT;
                    this.$refs.frmNhapDSThanhVien.ShowDialog()
                    
                    // this.popupComponentData = {"hdtb_id":this.hdtb_id, _ds_hdtbct: this.dsHDTB_CT};
                    // this.popupComponentData.Text = "Nhập danh sách số hiển thị";
                    // this.popupComponent = ()=>import('@/modules/contract/setup/ChangeContractOwner/popup/frmNhapDSThanhVien.vue');
                    // this.popupComponentName = "frmNhapDSThanhVien";
                    // this.Popup('popupComponentsCNTT');
                    } else
                    for (var i = 0; i < ItemList.length; i++) {
                        if (ItemList[i].sFieldName == 'MA_PHU') {
                        if (ItemList[i].sLookupApi != '') {
                            this.popupComponentData = {
                            loaihinh_tb: this.vloaitb_id,
                            field_name: ItemList[i].sFieldName,
                            api_url: ItemList[i].sLookupApi,
                            method: ItemList[i].sAPIMethod,
                            hienthi_bang: ItemList[i].sAPIHienThiBang,
                            giatri_trave: ItemList[i].sAPIValueMember,
                            ItemList: ItemList,
                            idx: i
                            }
                            if (this.vloaitb_id == LoaiHinhTB.VNPT_SLL) {
                            this.popupComponentData.Text = 'Lấy thông tin ID trường'
                            }
                            this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupLayDuLieuTuAPI')
                            this.popupComponentName = 'popupLayDuLieuTuAPI'
                            this.Popup('popupComponentsCNTT')
                        }
                        break
                        }
                    }
                }

                if (repositoryItemButtonEdit.Tag == 'SOLUONG') {
                    for (var i = 0; i < ItemList.length; i++) {
                    if (ItemList[i].sFieldName == 'SOLUONG') {
                        try {
                        var id_truong = ItemList.filter((x) => x.sFieldName == 'MA_PHU')[0].sValue
                        let tocdo_id = ItemList.filter((x) => x.sFieldName == 'TOCDO_ID')[0].sValue
                        this.popupComponentData = { id_truong: id_truong, dsHDTB_CT: this.dsHDTB_CT, tocdo_id: tocdo_id }
                        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupDangKySoLienLac')
                        this.popupComponentName = 'popupDangKySoLienLac'
                        this.Popup('popupComponentsCNTT')
                        } catch (ex) {
                        this.toast.error('' + ex)
                        if (this.debug) console.error(ex)
                        }
                    }
                    }
                }
                }

                if (this.vloaitb_id == LoaiHinhTB.VNEDU_SMARTID) {
                if (repositoryItemButtonEdit.Tag == 'SL_MAILING') {
                    for (var i = 0; i < ItemList.length; i++) {
                    if (ItemList[i].sFieldName == 'SL_MAILING') {
                        this.popupComponentData = { loaitb_id: this.vloaitb_id, append_name: 'SL_MAILING', saveList: this.dsHDTB_CT, ItemList: ItemList }
                        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChiTietHDTB')
                        this.popupComponentName = 'popupChiTietHDTB'
                        this.Popup('popupComponentsCNTT')
                    }
                    }
                }
                }
                if (repositoryItemButtonEdit.Tag == 'MATB_TN') {
                let kieu = this.kieu_ld_kieu
                let dulieu = {}
                switch (kieu * 1) {
                    case 2:
                    if (this.khachhang.khachhang_id && this.khachhang.khachhang_id > 0) {
                        dulieu.khachhang_id = this.khachhang.khachhang_id
                        dulieu.kieuld_id = kieuld
                        dulieu.loaitb_id = this.vloaitb_id
                        dulieu.dichvuvt_id = this.dvvt
                        this.popupComponentData = dulieu
                        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupDSTBCungDoiCap')
                        this.popupComponentName = 'popupDSTBCungDoiCapCNTT'
                        this.popupRetVal = null
                        this.Popup('popupComponentsCNTT')
                        let xlykq = setInterval(() => {
                        if (this.popupRetVal) {
                            clearInterval(xlykq)
                            for (var i = 0; i < ItemList.length; i++) {
                            if (ItemList[i].sFieldName == 'MATB_TN') {
                                ItemList[i].sValue = this.popupRetVal.MA_TB
                                this.gridEditors[i].Value = this.popupRetVal.MA_TB
                                repositoryItemButtonEdit.Text = this.popupRetVal.MA_TB
                            }
                            if (ItemList[i].sFieldName == 'MADOICAP') {
                                ItemList[i].sValue = this.popupRetVal.MADOICAP
                                this.gridEditors[i].Value = this.popupRetVal.MADOICAP
                            }
                            }
                            // grcMacdinh.RefreshDataSource();
                        }
                        }, 1000)
                        // while(this.popupRetVal==null) {
                        //   //KHong lam gi, cho popup hoan thanh!
                        // }
                        // if (this.popupRetVal)
                        // {
                        //     for (var i = 0; i < ItemList.length; i++)
                        //     {
                        //         if (ItemList[i].sFieldName == "MATB_TN")
                        //         {
                        //             ItemList[i].sValue = this.popupRetVal.ma_tn;
                        //             this.gridEditors[i].Value = this.popupRetVal.ma_tn;
                        //             repositoryItemButtonEdit.Text = this.popupRetVal.ma_tn;
                        //         }
                        //         if (ItemList[i].sFieldName == "MADOICAP")
                        //         {
                        //             ItemList[i].sValue = this.popupRetVal.ma_doicap;
                        //             this.gridEditors[i].Value = this.popupRetVal.ma_doicap;
                        //         }
                        //     }
                        //     // grcMacdinh.RefreshDataSource();
                        // }
                    } else {
                        this.$toast.error('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !')
                        return
                    }
                    break
                    case 1:
                    case 3:
                    if (this.khachhang.hdkh_id && this.khachhang.hdkh_id > 0) {
                        dulieu.hdkh_id = this.khachhang.hdkh_id
                        dulieu.vkhachhang_id = this.khachhang.khachhang_id
                        dulieu.vkieuld_id = kieuld
                        dulieu.loaitb_id = this.vloaitb_id
                        this.popupComponentData = dulieu
                        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupDSTBLapKem')
                        this.popupComponentName = 'popupDSTBLapKemCNTT'
                        this.popupRetVal = null
                        this.Popup('popupComponentsCNTT')
                        let xlykq = setInterval(() => {
                        if (this.popupRetVal) {
                            clearInterval(xlykq)
                            if (this.popupRetVal.MA_TB)
                            for (var i = 0; i < ItemList.length; i++) {
                                if (ItemList[i].sFieldName == 'MATB_TN') {
                                ItemList[i].sValue = this.popupRetVal.MA_TB
                                this.gridEditors[i].Value = this.popupRetVal.MA_TB
                                repositoryItemButtonEdit.Text = this.popupRetVal.MA_TB
                                }
                                if (ItemList[i].sFieldName == 'MADOICAP') {
                                ItemList[i].sValue = this.popupRetVal.MADOICAP
                                this.gridEditors[i].Value = this.popupRetVal.MADOICAP
                                }
                            }
                            else if (kieu * 1 == 3) {
                            for (var i = 0; i < ItemList.length; i++) {
                                if (ItemList[i].sFieldName == 'MATB_TN') {
                                ItemList[i].sValue = ''
                                this.gridEditors[i].Value = ''
                                repositoryItemButtonEdit.Text = ''
                                }
                                if (ItemList[i].sFieldName == 'MADOICAP') {
                                ItemList[i].sValue = '0'
                                this.gridEditors[i].Value = '0'
                                }
                            }
                            // grcMacdinh.RefreshDataSource();
                            }
                            // grcMacdinh.RefreshDataSource();
                        }
                        }, 1000)
                        // while(this.popupRetVal==null) {
                        //   //KHong lam gi, cho popup hoan thanh!
                        // }
                        // if (this.popupRetVal)
                        // {
                        //   for (var i = 0; i < ItemList.length; i++)
                        //   {
                        //     if (ItemList[i].sFieldName == "MATB_TN")
                        //     {
                        //         ItemList[i].sValue = this.popupRetVal.ma_tn;
                        //         this.gridEditors[i].Value = this.popupRetVal.ma_tn;
                        //         repositoryItemButtonEdit.Text = this.popupRetVal.ma_tn;
                        //     }
                        //     if (ItemList[i].sFieldName == "MADOICAP")
                        //     {
                        //         ItemList[i].sValue = this.popupRetVal.ma_doicap;
                        //         this.gridEditors[i].Value = this.popupRetVal.ma_doicap;
                        //     }
                        //   }
                        //   // grcMacdinh.RefreshDataSource();
                        // }
                        // else if(kieu*1==3)
                        // {
                        //     for (var i = 0; i < ItemList.length; i++)
                        //     {
                        //         if (ItemList[i].sFieldName == "MATB_TN")
                        //         {
                        //             ItemList[i].sValue = "";
                        //             this.gridEditors[i].Value = "";
                        //             repositoryItemButtonEdit.Text = "";
                        //         }
                        //         if (ItemList[i].sFieldName == "MADOICAP")
                        //         {
                        //             ItemList[i].sValue = "0";
                        //             this.gridEditors[i].Value = "0";
                        //         }
                        //     }
                        //     // grcMacdinh.RefreshDataSource();
                        // }
                    } else {
                        this.$toast.error('Chưa có thông tin thuê  bao để lấy danh sách mã truy nhập lắp kèm !')
                        return
                    }
                    break
                }
                }
                if (repositoryItemButtonEdit.Tag == 'THONGTIN_CT' && this.vloaitb_id == LoaiHinhTB.IT_CARE) {
                this.popupComponentData = { vloaitb_id: this.vloaitb_id, vhdtb_id: this.hdtb_id, vappend_name: repositoryItemButtonEdit.Tag }
                this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupHDTB_CNTT_SUB')
                this.popupComponentName = 'popupHDTB_CNTT_SUB'
                this.Popup('popupComponentsCNTT')
                }
                if (repositoryItemButtonEdit.Tag == 'DONGIA' || repositoryItemButtonEdit.Tag == 'DINHMUC_SMS' || repositoryItemButtonEdit.Tag == 'DOITAC' || repositoryItemButtonEdit.Tag == 'DINHMUC_LL' || repositoryItemButtonEdit.Tag == 'DINHTUYEN') {
                if (this.hdtb_id != 0) {
                    // && loaihinh_tb == vloaitb_id) // dieu kien 2 nay luon dung, khong xu ly
                    if (this.vloaitb_id == LoaiHinhTB.VNPT_SMCC) {
                    if (repositoryItemButtonEdit.Tag == 'DOITAC') {
                        this.popupComponentData = { loaitb_id: this.vloaitb_id, append_name: 'DOITAC', saveList: this.dsHDTB_CT, ItemList: ItemList }
                        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChiTietHDTB')
                        this.popupComponentName = 'popupChiTietHDTB' // Chung code xu ly voi ham phia tren do do khong can code doan nay.
                        this.Popup('popupComponentsCNTT')
                    }
                    } else {
                    this.popupRetVal = null
                    this.popupComponentData = { vloaitb_id: this.vloaitb_id, vhdtb_id: this.hdtb_id, vappend_name: repositoryItemButtonEdit.Tag }
                    this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupHDTB_CNTT_SUB')
                    this.popupComponentName = 'popupHDTB_CNTT_SUB'
                    this.Popup('popupComponentsCNTT')
                    let xlykq = setInterval(() => {
                        if (this.popupRetVal) {
                        clearInterval(xlykq)
                        if ((this.vloaitb_id == LoaiHinhTB.TD_1800 || this.vloaitb_id == LoaiHinhTB.TD_1900) && this.thamsomacdinh['TRIENKHAI_1800_1900'] == 1) {
                            let tien_ld = this.popupRetVal.tien_ld
                            // for (var item of this.ds_tien_khoanmuc) {
                            // if (item['khoanmuctt_id'] == 142) {
                            //     //Cước Cài Đặt Đầu Số 1800 - 1900
                            //     if (tien_ld != '-1') {
                            //     item['tien'] = tien_ld
                            //     item['vat'] = tien_ld * 0.1
                            //     //HienThiTienKhuyenMaiLapDat(dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);
                            //     //this.HienThiTienKhuyenMaiLapDat()
                            //     //this.LayTongTien_HDTB()
                            //     }
                            // }
                            // }
                        }
                        }
                    }, 1000)
                    // while(this.popupRetVal==null) {
                    //   //KHong lam gi, cho popup hoan thanh!
                    // }
                    // if(this.popupRetVal)
                    //   if ((this.vloaitb_id == LoaiHinhTB.TD_1800
                    //       || vloaitb_id == LoaiHinhTB.TD_1900) && this.thamsomacdinh['TRIENKHAI_1800_1900'] == 1)
                    //   {
                    //       let tien_ld = this.popupRetVal.tien_ld;
                    //       for (var item of this.ds_tien_khoanmuc)
                    //       {
                    //           if (item["khoanmuctt_id"] == 142) //Cước Cài Đặt Đầu Số 1800 - 1900
                    //           {
                    //               if (tien_ld != "-1")
                    //               {
                    //                   item["tien"] = tien_ld;
                    //                   item["vat"] = tien_ld * 0.1;
                    //                   //HienThiTienKhuyenMaiLapDat(dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);
                    //                   this.HienThiTienKhuyenMaiLapDat();
                    //                   this.LayTongTien_HDTB();
                    //               }
                    //           }
                    //       }
                    //   }
                    }
                }
                }
                if (repositoryItemButtonEdit.Tag == 'CT_DIEMCAU') {
                if (this.hdtb_id != 0) {
                    this.popupComponentData = { vhdtb_id: this.hdtb_id, vhdkh_id: this.khachhang.hdkh_id, ma_gd: this.khachhang.magd, vloaitb_id: this.vloaitb_id }
                    this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupDanhSach_DiemCau')
                    this.popupComponentName = 'popupDanhSach_DiemCau'
                    this.Popup('popupComponentsCNTT')
                    //khong thay xu ly sau khi dong popup -> chua lam gi!
                }
                }

                if (this.vloaitb_id == LoaiHinhTB.TD_1800 || this.vloaitb_id == LoaiHinhTB.TD_1900) {
                if (repositoryItemButtonEdit.Tag == 'MA_TB') {
                    var vsomay = ''
                    for (var i = 0; i < ItemList.length; i++) {
                    if (ItemList[i].sFieldName == 'MA_TB') {
                        vsomay = ItemList[i].sValue
                    }
                    }

                    this.popupComponentData = {
                    vloaitb_id: Number(this.vloaitb_id),
                    vdichvuvt_id: Number(this.dvvt),
                    vsomay: vsomay
                    }
                    this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChonSo_CNTT')
                    this.popupComponentName = 'popupChonSo_CNTT'
                    this.popupComponentHeader = ''
                    this.Popup('popupComponentsCNTT')
                }
                }
            } catch (ex) {
                this.$toast.error('Có lỗi: ' + ex)
                if (this.debug) console.error(ex)
            }
            },

        Popup(modalId) {
        this.loading(false)
        if (this.$refs[modalId]) this.$refs[modalId].show()
        else this.$bvModal.show(modalId)
        },
        ClosePopup(modalId) {
        this.$bvModal.hide(modalId)
        this.popupComponent = null
        },
        async popupClosed(val) {
            if (!val) {
                this.popupRetVal = {}
                this.ClosePopup('popupComponents')
                return
            }
            this.loading(true)
            try {
                switch (this.popupComponentName) {
                case 'popupDSTBCungDoiCapCNTT':
                    if (val) this.popupRetVal = val
                    else this.popupRetVal = {}
                    // grcMacdinh.RefreshDataSource();
                    break
                case 'popupChonMauHDDT':
                    if (val && val.soluong) {
                        for(var i = 0; i < this.ItemList.length; i++) {
                            if (ItemList[i].sFieldName == 'SL_MAILING') {
                                this.ItemList[i].sValue = val.soluong
                                this.gridEditors[i].Value = val.soluong
                            }
                        }
                    }
                    break
                case 'popupLayDuLieuTuAPI':
                    this.ItemList[val.vitri].sValue = val.giatrichon
                    this.gridEditors[val.vitri].Value = val.giatrichon
                    const get_loaitb_id = this.vloaitb_id
                    //Hoannc
                    const data_api = await API.fn_kiemtra_hienthi_tentruong_edu(this.axios, { loaitb_id: get_loaitb_id })
                    if (data_api.data.data == 'OK') {
                    const tentruong = val.bangtrave[0].ten_truong
                    for (let j = 0; j < this.ItemList.length; j++) {
                        if (this.ItemList[j].sFieldName == 'THUONGHIEU') {
                        this.ItemList[j].sValue = tentruong
                        this.gridEditors[j].Value = tentruong
                        break
                        }
                    }
                    }
                    break
                case 'popupDangKySoLienLac':
                    for (let j = 0; j < this.ItemList.length; j++) {
                        if (this.ItemList[j].sFieldName == 'SL_MAILING') {
                            this.ItemList[j].sValue = val.tong_tien
                            this.gridEditors[j].Value = val.tong_tien
                            break
                        }
                    }
                    for (let j = 0; j < this.ItemList.length; j++) {
                        if (this.ItemList[j].sFieldName == 'SOLUONG') {
                            this.ItemList[j].sValue = val.tongso_hs
                            this.gridEditors[j].Value = val.tongso_hs
                            break
                        }
                    }
                    break
                case 'popupChiTietHDTB':
                    this.dsHDTB_CT = val.dsHDTB_CT
                    this.ItemList[i].sValue = this.dsHDTB_CT.length
                    this.gridEditors[i].Value = this.dsHDTB_CT.length
                    break

                case 'popupChonSo_CNTT':
                    for (var item of this.ItemList) {
                        if (item.sFieldName == 'MA_TB') {
                            item.sValue = val.somay
                        }
                        if (item.sFieldName == 'CUOC_SD') {
                            item.sValue = val.tiensodep
                        }
                    }

                    break
                case 'popupHDTB_CNTT_SUB':
                case 'frmChonDoiTuong_CT':
                default:
                    this.popupRetVal = val
                    //Chuc nang popup nay chua thay xu ly gi sau khi dong popup, tam thoi ko code
                    break
                }
            } catch (ex) {
                this.$toast.error('' + ex)
                if (this.debug) console.error(ex)
            } finally {
                this.ClosePopup('popupComponents')
                this.loading(false)
            }
        },
        ChangeData(obj) {
            this.watcherActive = false;
            try {
                console.info('ChangeData',this.gridEditors);
                this.ItemList.find(x=>x.sFieldName == obj.sFieldName).sValue = obj.sValue;
                this.gridEditors.find(x=>x.ID == obj.sFieldName).Value = obj.sValue;
                console.info('ChangedData',this.ItemList.find(x=>x.sFieldName == obj.sFieldName),this.ItemList.find(x=>x.sFieldName == obj.sFieldName).sValue,this.gridEditors.find(x=>x.ID == obj.sFieldName),this.gridEditors.find(x=>x.ID == obj.sFieldName).Value);
            } catch(ex) {
                this.$toast.error(`Không thay đổi được giá trị của ${obj.sFieldName}: ` + ex)
                if (this.debug) console.error(ex)
            }
            finally {
                this.watcherActive = true;
            }
        }
    },
}
</script>
