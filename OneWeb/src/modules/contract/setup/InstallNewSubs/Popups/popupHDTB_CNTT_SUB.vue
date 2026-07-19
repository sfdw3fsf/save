<template>
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span>Thông tin bổ sung hợp đồng thuê bao CNTT</div>
            <div class="close -ap icon-close hidden" data-dismiss="modal">
            </div>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="#" @click.prevent="tsbtnNhapMoi_Click" :class="{ disabled:xem==1 || !tsbtnNhapMoi }">
                        <span class="icon nc-icon-glyph ui-2_fat-add"></span> Nhập mới
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="tsbtnGhiLai_Click" :class="{ disabled:xem==1 || !tsbtnGhiLai }">
                        <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Ghi lại
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="tsbtnXoa_Click" :class="{ disabled:xem==1 || !tsbtnXoa }">
                        <span class="icon nc-icon-glyph ui-2_fat-remove"></span> Xoá
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="tsbtnHuyBo_Click" :class="{ disabled:xem==1 || !tsbtnHuyBo }">
                        <span class="icon nc-icon-glyph arrows-1_refresh-69"></span> Huỷ
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="closeForm">
                        <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
                    </a>
                </li>
            </ul>
        </div>
        <div class="popup-body">
            <div class="row">
                <div class="col-12">
                    <div class="box-form">
                        <div class="legend-title mart20">Thông tin bổ sung</div>
                        <DataGrid
                            v-bind:columns="grcMacdinh.cols"
                            v-bind:dataSource="grcMacdinh.list" :showFilter="false" :showColumnCheckbox="false"  :editSettings="{ allowEditing: true, mode: 'Normal' }"
                            :dataKeyField="grcMacdinh.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="'200'" @selectedItemsChanged="selectedItemsChanged" @actionComplete="actionComplete">
                        </DataGrid>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="box-form">
                        <div class="legend-title mart20">Danh sách bổ sung</div>
                        <DataGrid
                            v-bind:columns="gridDanhSach.cols"
                            v-bind:dataSource="gridDanhSach.list" :showFilter="false" :showColumnCheckbox="false"
                            :dataKeyField="gridDanhSach.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="'200'" @selectedItemsChanged="viewDanhSach_FocusedRowChanged">
                        </DataGrid>
                    </div>
                </div>
            </div>
        </div>
        <!-- <b-modal id="popupComponents" size="xl"
            hide-footer
            hide-header
            hide-header-close
            body-class="modal-body p-0">
            <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr" isPopup="true" v-on="popupComponentEvts"></component> <!--v-dynamic-events="popupComponentEvents"-->
        <!-- </b-modal> -->
    </div>
    </template>
    <style scoped>

    </style>
    <script>
    import moment from 'moment';
    import {
      LoaiHinhTB,
      DatabaseConstants,
      KIEU_DL
    } from "../Enums";
    import {
      Sort,
      Filter,
      Resize,
      Group,
      Freeze,
      CommandColumn,
      ExcelExport,
      Edit,
      Aggregate,
      Page,
      Toolbar,
      GridPlugin,
    } from "@syncfusion/ej2-vue-grids";
    export default {
        name: 'popupHDTB_CNTT_SUB',
        props: [ 'dulieu' ],
        provide: {
            grid: [
                Sort,
                Filter,
                Resize,
                Group,
                Freeze,
                CommandColumn,
                ExcelExport,
                Edit,
                Aggregate,
                Toolbar,
                Page,
            ],
        },
        watch: {
        },
        data() {
            return {
                grcMacdinh: {
                    list: [],
                    cols: [
                        {fieldName: 'Name', headerText: 'Tham số', allowFiltering: false, allowSorting: false, allowEditing: false, },
                        {fieldName: 'Value', headerText: 'Giá trị', allowFiltering: false, allowSorting: false, allowEditing: true, },
                    ],
                    keycol: "",
                    value: {},
                    values: [],
                    Columns: [],
                },
                gridDanhSach: { // Co ve cai nay trung voi viewDanhSach
                    list: [],
                    cols: [],
                    keycol: "",
                    value: {},
                    values: [],
                    Columns: [],
                },
                dsTables: [],
                vloaitb_id: 0,
                vhdtb_id: 0,
                vappend_name: '',
                dctTenHienThi: {},
                vtrangthai: 0,
                xem: 0,
                tsbtnNhapMoi: true,
                tsbtnGhiLai: true,
                tsbtnXoa: true,
                tsbtnHuyBo: true,
                ItemList: [],
                INVISIBLE_COLUMN: [ "HDTB_ID", "FIELD_ID_CHA", "HDTB_SUB_ID" ],
                vhdtb_sub_id: 0,
                tien_ld: 0,
            }
        },
        mounted() {
            if(this.dulieu.vloaitb_id) this.vloaitb_id = this.dulieu.vloaitb_id;
            if(this.dulieu.vhdtb_id) this.vhdtb_id = this.dulieu.vhdtb_id;
            if(this.dulieu.vappend_name) this.vappend_name = this.dulieu.vappend_name;

            if(this.dulieu.vtrangthai) this.vtrangthai = this.dulieu.vtrangthai;
            //     for(var item of this.ItemList) {
            //         var it = {};
            //         it.sFieldName = item.sFieldName;
            //         it.sValue = (this.dulieu.DicInputControl_giahan["txt" + it.sFieldName].Value ?this.dulieu.DicInputControl_giahan["txt" + it.sFieldName].Value: "");
            //         it.sAPIHienThiBang = item.sAPIHienThiBang;
            //         it.sLookupApi = item.sLookupAPI;
            //     }
            // };
            this.frmHDTB_CNTT_SUB_Load();
        },
        computed: {
        },
        methods: {
            closeForm() {
                this.$emit('form-close',{ "tien_ld":this.tien_ld } );
            },
            tsbtnChapNhan_ItemClick() {
                this.closeForm();
            },
            terminateForm() {
                this.$emit('form-close',null);
            },
            UpperCasePropertyList(obj) {
            return obj.map( function( item ){
                for(var key in item){
                    var upper = key.toUpperCase();
                    // check if it already wasn't uppercase
                    if( upper !== key ){
                        item[ upper ] = item[key];
                        delete item[key];
                    }
                }
                return item;
            });
            },
            async frmHDTB_CNTT_SUB_Load() {
                try
                {
                    if (this.vtrangthai == 1)
                    {
                        this.tsbtnGhiLai = true;
                        this.tsbtnHuyBo = true;
                        this.tsbtnNhapMoi = true;
                        this.tsbtnXoa = true;
                    }

                    // var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                    //     "command":`select field_name, ten_hienthi from ${DatabaseConstants.DB1}.ct_tscn where loaitb_id = ${this.vloaitb_id}`
                    // })
                    var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/ct_tscn",{"id":this.vloaitb_id});
                    var dt = this.UpperCasePropertyList(rs.data);

                    this.dsTables = [];

                    //rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{ "ds_para": JSON.stringify({"LOAITB_ID":this.vloaitb_id}) });
                    rs = await this.$root.context.post("/web-hopdong/lapdatmoi/lay_thongtin_bosung_hdtb_cntt",{"id":this.vloaitb_id});
                    if(rs.data) {
                        this.dsTables = this.UpperCasePropertyList(rs.data);
                        // rs = JSON.parse(rs.data);
                        // if(rs['ERROR_CODE']==1)
                        //     this.dsTables = rs['RESULT'];
                        // else {
                        //     this.$toast.error(rs['MESSAGE']);
                        // }
                    }

                    // dsTables = property.Lay_ThongTin_BoSung_HDTB_CNTT(vloaitb_id);
                    for (var dr of dt)
                    {
                        if (!dr["field_name".toUpperCase()] || !dr["ten_hienthi".toUpperCase()])
                            continue;
                        this.dctTenHienThi[dr["field_name".toUpperCase()]] = dr["ten_hienthi".toUpperCase()];
                    }

                    await this.LoadThongTin_DV_CNTT_SUB(this.vloaitb_id);
                    await this.Load_DS_HDTB_CNTT_SUB();
                    if (this.gridDanhSach.list.length <= 0)
                    {
                        this.SetButton(1);
                    }

                    if (this.xem == 1)
                    {
                        this.tsbtnGhiLai = false;
                        this.tsbtnHuyBo = false;
                        this.tsbtnNhapMoi = false;
                        this.tsbtnXoa = false;
                    }
                }
                catch (ex)
                {
                    this.$toast.error('Lỗi 2: '+ex);
                }
            },
            SetButton(kieu) {
                this.tsbtnNhapMoi = false;
                this.tsbtnGhiLai = false;
                this.tsbtnXoa = false;
                this.tsbtnHuyBo = false;

                if (this.vtrangthai == 1)
                    return;

                if (kieu == -1)//Bat dau
                {
                    this.tsbtnGhiLai = true;
                    this.tsbtnHuyBo = true;
                }
                if (kieu == 0)//Bat dau
                {
                    this.tsbtnNhapMoi = true;
                    //Clear();
                }
                if (kieu == 1)//Them moi
                {
                    this.tsbtnGhiLai = true;
                    this.tsbtnHuyBo = true;
                    //Clear();
                }
                if (kieu == 2)//Huy
                {
                    this.tsbtnNhapMoi = true;
                    this.tsbtnXoa = true;
                    //Clear();
                }
                if (kieu == 3)//Edit
                {
                    this.tsbtnNhapMoi = true;
                    this.tsbtnXoa = true;
                    this.tsbtnGhiLai = true;
                    this.tsbtnHuyBo = true;
                }
            },
            async LoadThongTin_DV_CNTT_SUB(vloaitb_id) {
                try
                {
                    await this.Init_Properties(vloaitb_id, "HDTB_CNTT_SUB", this.vappend_name, "00000");
                    this.gridEditors = [];

                    for (var index1 = 0; index1 < this.ItemList.length; ++index1)
                    {
                        var repositoryItemTextEdit = { "Name": "repositoryItemTextEdit" }
                        var repositoryItemCalcEdit = { "Name": "repositoryItemCalcEdit" }

                        if (this.ItemList[index1].iAtt == 0)
                        {
                            repositoryItemTextEdit.Tag = this.ItemList[index1].sFieldName;
                            repositoryItemTextEdit.Name = this.ItemList[index1].sCaption;
                            repositoryItemTextEdit.Group = "Tham số báo cáo";
                            repositoryItemTextEdit.ID = this.ItemList[index1].sFieldName;
                            repositoryItemTextEdit.Value = this.ItemList[index1].sValue;
                            this.gridEditors.push(repositoryItemTextEdit);
                            // this.gridEditors.Add((RepositoryItem)this.repositoryItemTextEdit, this.ItemList[index1].sCaption, (object)this.ItemList[index1].sValue, "Tham số báo cáo", this.ItemList[index1].sFieldName);
                        }
                        if (this.ItemList[index1].iAtt == 1)
                        {
                            repositoryItemCalcEdit.Tag = this.ItemList[index1].sFieldName;
                            repositoryItemCalcEdit.Name = this.ItemList[index1].sCaption;
                            repositoryItemCalcEdit.Group = "Tham số báo cáo";
                            repositoryItemCalcEdit.ID = this.ItemList[index1].sFieldName;
                            repositoryItemCalcEdit.Value = this.ItemList[index1].sValue;
                            this.gridEditors.push(repositoryItemCalcEdit);
                            // this.gridEditors.Add((RepositoryItem)this.repositoryItemCalcEdit, this.ItemList[index1].sCaption, (object)this.ItemList[index1].sValue, "Tham số báo cáo", this.ItemList[index1].sFieldName);
                        }
                        if (this.ItemList[index1].iAtt == 2)
                        {
                            this.ItemList[index1].sValue = moment(new Date()).format("DD/MM/YYYY");//new ThongTinNguoiDung().ngay_cn.ToString("dd/MM/yyyy");
                            var repositoryItemDateEdit = {};
                            repositoryItemDateEdit.Tag = this.ItemList[index1].sFieldName;
                            this.ItemList[index1].sValue = moment(new Date()).format("DD/MM/YYYY");
                            // repositoryItemDateEdit.DisplayFormat.FormatString = "dd/MM/yyyy";
                            // repositoryItemDateEdit.EditFormat.FormatString = "dd/MM/yyyy";
                            repositoryItemDateEdit.EditMask = "dd/MM/yyyy";
                            // repositoryItemDateEdit.Mask.UseMaskAsDisplayFormat = true;
                            repositoryItemDateEdit.Name = this.ItemList[index1].sCaption;
                            repositoryItemDateEdit.Group = "Tham số báo cáo";
                            repositoryItemDateEdit.ID = this.ItemList[index1].sFieldName;
                            repositoryItemDateEdit.Value = this.ItemList[index1].sValue;
                            this.gridEditors.push(repositoryItemDateEdit);
                            // this.gridEditors.Add((RepositoryItem)repositoryItemDateEdit, this.ItemList[index1].sCaption, DateTime.Now, "Tham số báo cáo", this.ItemList[index1].sFieldName);

                        }
                        var str1;
                        var dataSet;
                        if (this.ItemList[index1].iAtt == 4)
                        {
                            var repositoryItemLookUpEdit = {};
                            repositoryItemLookUpEdit.Tag = this.ItemList[index1].sFieldName;
                            str1 = "";
                            var SQL = this.ItemList[index1].StrSql;
                            if (SQL)
                            {
                                // var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                                //     "command":  SQL
                                // })
                                // var data = rs.data;//DataSet data = this.bangts.GetSQL(SQL, "");
                                repositoryItemLookUpEdit.ValueMember = this.ItemList[index1].sLookUpKeyField;
                                repositoryItemLookUpEdit.DisplayMember = this.ItemList[index1].sLookUpValueField;
                                repositoryItemLookUpEdit.DataSource = this.ItemList[index1].SqlData;//data;
                                repositoryItemLookUpEdit.ShowHeader = false;
                                repositoryItemLookUpEdit.ShowFooter = false;
                                repositoryItemLookUpEdit.Columns = [
                                    {fieldName: this.ItemList[index1].sLookUpKeyField, headerText: '', allowFiltering: false, allowSorting: false},
                                    {fieldName: this.ItemList[index1].sLookUpValueField, headerText: '', allowFiltering: false, allowSorting: false},
                                ],
                                // repositoryItemLookUpEdit.Columns.Add(new DevExpress.XtraEditors.Controls.LookUpColumnInfo(this.ItemList[index1].sLookUpKeyField, "", 0, DevExpress.Utils.FormatType.None, "", false, DevExpress.Utils.HorzAlignment.Default));
                                // repositoryItemLookUpEdit.Columns.Add(new DevExpress.XtraEditors.Controls.LookUpColumnInfo(this.ItemList[index1].sLookUpValueField, "", 100, DevExpress.Utils.FormatType.None, "", true, DevExpress.Utils.HorzAlignment.Default));

                                repositoryItemLookUpEdit.AutoSearchColumnIndex = 1;
                                repositoryItemLookUpEdit.NullText = "";
                            }
                            else
                            {
                                SQL = this.ReturnSqlStr(this.ItemList, index1);
                                // dataSet = new DataSet();
                                // DataSet data = this.bangts.GetSQL(SQL, "");
                                // var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                                //     "command":  SQL
                                // })
                                // var data = rs.data;//DataSet data = this.bangts.GetSQL(SQL, "");
                                repositoryItemLookUpEdit.ValueMember = this.ItemList[index1].sLookUpKeyField;
                                repositoryItemLookUpEdit.DisplayMember = this.ItemList[index1].sLookUpValueField;
                                repositoryItemLookUpEdit.DataSource = this.ItemList[index1].SqlData;//data;
                                repositoryItemLookUpEdit.ShowHeader = false;
                                repositoryItemLookUpEdit.ShowFooter = false;
                                repositoryItemLookUpEdit.Columns = [
                                    {fieldName: this.ItemList[index1].sLookUpKeyField, headerText: '', allowFiltering: false, allowSorting: false},
                                    {fieldName: this.ItemList[index1].sLookUpValueField, headerText: '', allowFiltering: false, allowSorting: false},
                                ],
                                // repositoryItemLookUpEdit.Columns.Add(new DevExpress.XtraEditors.Controls.LookUpColumnInfo(this.ItemList[index1].sLookUpKeyField, "", 0, DevExpress.Utils.FormatType.None, "", false, DevExpress.Utils.HorzAlignment.Default));
                                // repositoryItemLookUpEdit.Columns.Add(new DevExpress.XtraEditors.Controls.LookUpColumnInfo(this.ItemList[index1].sLookUpValueField, "", 100, DevExpress.Utils.FormatType.None, "", true, DevExpress.Utils.HorzAlignment.Default));
                                repositoryItemLookUpEdit.AutoSearchColumnIndex = 1;
                                repositoryItemLookUpEdit.NullText = "";
                            }
                            repositoryItemLookUpEdit.EditValueChanged = this.repositoryItemLookUpEdit_EditValueChanged;
                            repositoryItemLookUpEdit.Name = this.ItemList[index1].sCaption;
                            repositoryItemLookUpEdit.Group = "Tham số báo cáo";
                            repositoryItemLookUpEdit.ID = this.ItemList[index1].sFieldName;
                            repositoryItemLookUpEdit.Value = this.ItemList[index1].sValue;
                            this.gridEditors.push(repositoryItemLookUpEdit);
                            // this.gridEditors.Add((RepositoryItem)repositoryItemLookUpEdit, this.ItemList[index1].sCaption, (object)this.ItemList[index1].sValue, "Tham số báo cáo", this.ItemList[index1].sFieldName);
                        }
                        if (this.ItemList[index1].iAtt == 5) //Button Edit
                        {
                            var repositoryItemButtonEdit = { "Name": "repositoryItemButtonEdit" }
                            repositoryItemButtonEdit.Tag = this.ItemList[index1].sFieldName;
                            repositoryItemButtonEdit.ButtonClick = this.repositoryItemButtonEdit_ButtonClick;
                            repositoryItemButtonEdit.Name = this.ItemList[index1].sCaption;
                            repositoryItemButtonEdit.Group = "Tham số báo cáo";
                            repositoryItemButtonEdit.ID = this.ItemList[index1].sFieldName;
                            repositoryItemButtonEdit.Value = this.ItemList[index1].sValue;
                            this.gridEditors.push(repositoryItemButtonEdit);
                            // this.gridEditors.Add((RepositoryItem)this.repositoryItemButtonEdit, this.ItemList[index1].sCaption, (object)this.ItemList[index1].sValue, "Tham số báo cáo", this.ItemList[index1].sFieldName);
                        }
                        if (this.ItemList[index1].iAtt == 7)
                        {
                            var repositoryItemCheckEdit = { "Name":"repositoryItemCheckEdit"};
                            repositoryItemCheckEdit.Tag = this.ItemList[index1].sFieldName;
                            repositoryItemCheckEdit.ValueChecked = "1";
                            repositoryItemCheckEdit.ValueUnchecked = "0";
                            repositoryItemCheckEdit.Name = this.ItemList[index1].sCaption;
                            repositoryItemCheckEdit.Group = "Tham số báo cáo";
                            repositoryItemCheckEdit.ID = this.ItemList[index1].sFieldName;
                            repositoryItemCheckEdit.Value = this.ItemList[index1].sValue;
                            this.gridEditors.push(repositoryItemCheckEdit);
                            // this.gridEditors.Add((RepositoryItem)this.repositoryItemCheckEdit, this.ItemList[index1].sCaption, (object)this.ItemList[index1].sValue, "Tham số báo cáo", this.ItemList[index1].sFieldName);
                        }
                    }
                    this.grcMacdinh.list = this.gridEditors;
                }
                catch (ex)
                {
                    this.$toast.error("Lỗi 1: " + ex);
                }
            },
            async repositoryItemLookUpEdit_EditValueChanged() {
                //Gan nhu khong lam gi o day, khong code
            },
            async repositoryItemButtonEdit_ButtonClick() {
                //Gan nhu khong lam gi o day, khong code
            },
            async Init_Properties(_loaitb_id, _table_name, _append_name, _opt_list)
            {
                try
                {
                    // DataSet ds = new DataSet();
                    // ds = property.Lay_danhsach_thuoctinh_sub(vloaitb_id, _table_name, _append_name, _opt_list);
                    var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/lay_danhsach_thuoctinh_sub",{
                        "vloaitb_id":_loaitb_id,
                        "vten_bang":_table_name,
                        "vappend_name": _append_name,
                        "vopt_list": _opt_list,
                    })
                    var ds = rs.data;
                    this.ItemList = new Array(ds.length);
                    if (this.ItemList.length == 0)
                        return;

                    for (var i = 0; i < this.ItemList.length; i++)
                    {
                        var row = ds[i];
                        this.ItemList[i] = {};
                        this.ItemList[i].sFieldName = row["field_name"];
                        this.ItemList[i].sCaption = row["ten_hienthi"];
                        this.ItemList[i].iAtt = row["att"];
                        this.ItemList[i].iDataType = row["data_type"];
                        this.ItemList[i].sMaxValue = row["max_value"];
                        this.ItemList[i].sMinvalue = row["min_value"];
                        this.ItemList[i].sValue = row["field_value"]?row["field_value"]:null;
                        this.ItemList[i].nFieldLength = (!row["field_length"] ? 0 : row["field_length"]);
                        this.ItemList[i].bIsNotNull = (!row["field_nullable"] ? false : (row["field_nullable"] == 0 ? true : false));
                        this.ItemList[i].bIsUnique = (!row["field_unique"] ? false : (row["field_unique"] == 0 ? false : true));
                        this.ItemList[i].sLookUpTableName = row["parent_table"];
                        this.ItemList[i].sLookUpKeyField = row["parent_keyfield"];
                        this.ItemList[i].sLookUpValueField = row["parent_descfield"];
                        this.ItemList[i].strDepandField = row["depend_field"];
                        this.ItemList[i].StrSql = row["lookup_sql"];
                        if(row["lookup_sql_response"]) this.ItemList[i].SqlData = this.UpperCasePropertyList(row["lookup_sql_response"]);
                    }
                }
                catch (ex)
                {
                    this.$toast.error("Có lỗi: " + ex);
                }
            },
            async Load_DS_HDTB_CNTT_SUB() {
                try
                {
                    
                    var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/lay_ds_hdtb_cntt_sub",{
                        "vhdtb_id":this.vhdtb_id,
                        "vloaitb_id":this.vloaitb_id,
                        "vtable_name": 'HDTB_CNTT_SUB',
                        "vappend_name": this.vappend_name,
                    })
                    var dt = rs.data;//ds_sub = property.lay_ds_hdtb_cntt_sub(vhdtb_id, vloaitb_id, "HDTB_CNTT_SUB", vappend_name);
                    var keys = dt && dt.length>0?Object.keys(dt[0]):[];
                    var colNum = keys.length;
                    this.gridDanhSach.cols = [];//viewDanhSach.Columns.Clear(); // viewDanhSach la datasource cua gridDanhSach
                    for (var i = 0; i < colNum; i++)
                        if (!(this.INVISIBLE_COLUMN.indexOf(keys[i])>=0))
                        {
                            var gcol = {};
                            var rows = this.dsTables.filter(x=>(x.PARENT_KEYFIELD && x.PARENT_KEYFIELD.toUpperCase() == keys[i].toUpperCase()) || x.PARENT_KEYFIELD == keys[i] );//this.dsTables[Object.keys(this.dsTables)[0]*1].filter(x=>(x.PARENT_KEYFIELD && x.PARENT_KEYFIELD.toUpperCase() == keys[i].toUpperCase()) || x.PARENT_KEYFIELD == keys[i] );
                            var row = null;
                            if(rows && rows.length>0) row = rows[0];
                            // console.info(i,rows,row,keys,keys[i]);
                            if (row != null)
                            {
                                // dt.Columns.Add(keys[i] + "_Val", typeof(string)); // Chua ro muc dich add them cot vao dt de lam gi???
                                // gcol.fieldName = keys[i].toUpperCase() + "_VAL";
                                gcol.fieldName = keys[i].toUpperCase();
                            }
                            else
                                gcol.fieldName = keys[i].toUpperCase();

                            if (Object.prototype.hasOwnProperty.call(this.dctTenHienThi, keys[i].toUpperCase()))
                                gcol.headerText = this.dctTenHienThi[keys[i].toUpperCase()];
                            else gcol.headerText = keys[i].toUpperCase(); // FIXME them dong nay de xu ly truong hop khong co ten
                            // gcol.OptionsColumn.AllowEdit = false;
                            // gcol.OptionsColumn.ReadOnly = true;
                            // gcol = true;
                            gcol.allowFiltering = true;
                            gcol.allowSorting = true;
                            //{fieldName: 'Name', headerText: 'Tham số', allowFiltering: false, allowSorting: false},
                            // console.info('col: ',gcol);
                            this.gridDanhSach.cols.push(gcol);
                        }

                    dt.forEach((e) => {
                      console.log(e)
                      e.ngay_kt = moment(e.ngay_kt).format("DD/MM/YYYY");
                      e.ngay_bd = moment(e.ngay_bd).format("DD/MM/YYYY");
                    })

                    this.gridDanhSach.list = this.UpperCasePropertyList(dt);

                    for (var dr of dt)
                        for (var i = 0; i < colNum; i++)
                            if (!(this.INVISIBLE_COLUMN.indexOf(keys[i]))>=0)
                            {
                                var dc = keys[i];
                                var tableName = "";
                                var rows = this.dsTables.filter(x=>x.PARENT_KEYFIELD==dc || (x.PARENT_KEYFIELD && x.PARENT_KEYFIELD.toUpperCase()==dc.toUpperCase()))//this.dsTables[Object.keys(this.dsTables)[0]].filter(x=>x.PARENT_KEYFIELD==dc);
                                var row = null;
                                if(rows && rows.length>0) row = rows[0];
                                // console.info(row,rows,this.dsTables);
                                // DataRow row = dsTables.Tables[0].Select("PARENT_KEYFIELD = '" + dc.ColumnName + '\'').FirstOrDefault();
                                if (row != null)
                                {
                                    if (row["PARENT_TABLE"]) {
                                        if(row['PARENT_TABLE'].indexOf('.')>=0) {
                                            tableName = row["PARENT_TABLE"].split('.');
                                            tableName = tableName[tableName.length-1];
                                        } else tableName = row['PARENT_TABLE'];
                                    }
                                    var captionrow = this.dsTables.filter(x=>x.PARENT_KEYFIELD == dr[dc]);
                                    if(captionrow && captionrow.length>0) captionrow = captionrow[0];
                                    else captionrow = null;
                                    if (captionrow != null)
                                        dr[dc.toUpperCase() + "_VAL"] = captionrow[Object.keys(captionrow)[1]];
                                    // console.info('row: ',captionrow,dr);
                                }
                            }
                    this.gridDanhSach.list = this.UpperCasePropertyList(dt); // bo sung them dong nay, gan lai de chuyen du lieu ve lai list, thay cho ds_sub
                }
                catch (ex)
                {
                    this.$toast.error("Lỗi : " + ex);
                }
            },
            async ReturnSqlStr(ListItem, index) {
                var s = "";
                switch (ListItem[index].sLookUpTableName)
                {
                    case "PHANLOAI_DV":
                        s = "select " + ListItem[index].sLookUpKeyField + "," + ListItem[index].sLookUpValueField + " from " + DatabaseConstants.DB2 + "." + ListItem[index].sLookUpTableName + " WHERE LOAITB_ID = " + this.listbox.loaihinh_tb.value + " ORDER BY " + ListItem[index].sLookUpKeyField;
                        break;
                    case "TOCDO_ADSL":
                        s = "Select TOCDO_ID, THUONGHIEU FROM " + DatabaseConstants.DB2 + "." + ListItem[index].sLookUpTableName;
                        s += " WHERE SUDUNG = 1 and LOAITB_ID = " + this.listbox.loaihinh_tb.value;
                        if (this.listbox.loaihinh_tb.value == LoaiHinhTB.Web_Hosting || this.listbox.loaihinh_tb.value == LoaiHinhTB.CA
                            || this.listbox.loaihinh_tb.value == LoaiHinhTB.CA_IVAN || this.listbox.loaihinh_tb.value == LoaiHinhTB.IVAN
                            || this.listbox.loaihinh_tb.value == LoaiHinhTB.IOFFICE)
                            for (var type of ListItem)
                                if (type.sFieldName == "DAILY")
                                    s += " and kieu_mega in (2," + type.sValue + ")";
                        // var checkTD =  await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                        //     "command":`select ${DatabaseConstants.DB2}.TOCDO_LOADTHEO_PHANLOAI(${this.listbox.loaihinh_tb.value}) kq from dual`
                        // })
                        var checkTD = await API.post_dvcntt_tocdo_adsl(this.axios,{"loaitb_id":this.listbox.loaihinh_tb.value}).then(async (response) => { //.post_sp_huonggiao_runsql(this.axios, { "command": `select ${DatabaseConstants.DB2}.TOCDO_LOADTHEO_PHANLOAI(${this.listbox.loaihinh_tb.value}) kq from dual` }).then(async (response) => {
                            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                            return response.data.data;//.data_json;
                            }
                            else return [];
                        });
                        if (checkTD && checkTD.length>0 && checkTD[0].kq && checkTD[0].kq == "ok")
                            for (var type of ListItem)
                                if (type.sFieldName == "PHANLOAI_ID" || (type.sFieldName == "LOAICTS_ID" && this.listbox.loaihinh_tb.value == LoaiHinhTB.SMART_CA))
                                    s += " and kieu_mega = " + (!type.sValue || type.sValue == "" ? -10 : type.sValue);
                        s += " ORDER BY TOCDO_ID";

                        break;
                    case "HE_DIEU_HANH":
                        for (var type of ListItem)
                        {
                            if (type.sFieldName == "TOCDO_ID")
                            {
                                if (!type.sValue || type.sValue == "")
                                {
                                    s = "select " + ListItem[index].sLookUpKeyField + "," + ListItem[index].sLookUpValueField + " from " + DatabaseConstants.DB2 + "." + ListItem[index].sLookUpTableName + " ORDER BY " + ListItem[index].sLookUpKeyField;
                                }
                                else
                                {
                                    s = "select a.hedieuhanh_id, a.he_dieu_hanh  ";
                                    s += " from " + DatabaseConstants.DB2 + ".he_dieu_hanh a, " + DatabaseConstants.DB2 + ".hdh_tocdo b ";
                                    s += " where a.hedieuhanh_id = b.hedieuhanh_id ";
                                    s += " and b.tocdo_id = " + type.sValue;
                                    s += " order by a.hedieuhanh_id ";
                                }
                            }
                        }
                        break;
                    case "ABC":
                        s = "Select vungpv_id, tenvung_pv FROM " + DatabaseConstants.DB5 + ".vungphucvu WHERE trangthai='1' order by tenvung_pv";
                        break;
                    case "DUNGLUONG":
                        s = "Select DUNGLUONG AS MA_SO,DUNGLUONG AS TEN from " + DatabaseConstants.DB5 + ".DUNGLUONG order by to_number(ten)";
                        break;
                    case "TUYEN_CAP":
                        s = "Select tuyencap_id, tuyen_cap from " + DatabaseConstants.DB5 + "." + ListItem[index].sLookUpTableName;
                        break;
                    case "TUYEN_CB":
                        s = "Select tuyencb_id, tuyen_cb from " + DatabaseConstants.DB5 + "." + ListItem[index].sLookUpTableName;
                        break;
                    case "TUYEN_COT":
                        s = "Select tuyencot_id, tuyen_cot from " + DatabaseConstants.DB5 + "." + ListItem[index].sLookUpTableName;
                        break;
                    case "{?CSS}.LINHVUC":
                        s = "Select linhvuc_id, linhvuc from " + DatabaseConstants.DB2 + ".LINHVUC where phanloai_sms is null or phanloai_sms like '%" + this.PhanLoai_ID + "%'";//PhanLoai_ID
                        break;
                    default:
                        s = "select " + ListItem[index].sLookUpKeyField + "," + ListItem[index].sLookUpValueField + " from " + ListItem[index].sLookUpTableName;
                        break;
                }
                s = s.replace("{?ADMIN}", DatabaseConstants.DB1);
                s = s.replace("{?CSS}", DatabaseConstants.DB2);
                return s;
            },
            tsbtnNhapMoi_Click() {
                this.loading(true);
                this.SetButton(1);
                this.LoadThongTin_DV_CNTT_SUB(this.vloaitb_id);
                this.loading(false);
            },
            KiemTra_Gio(time)
            {
                try
                {
                    if((time+'').match(/^\d{2}:\d{2}:\d{2}$/g))
                        return moment(time,"HH:mm:ss").format("HH:mm:ss");
                    else {
                        if(!moment(time).isValid()) this.$toast.error(`Dữ liệu ${time} đang chưa đúng format giờ HH:mm:ss`);
                        return moment(time).format("HH:mm:ss"); //24h
                    }
                }
                catch (ex)
                {
                    return "error";
                }

            },
            CheckDataError(NetItems,ItemCount) {
                try
                {
                    var flag = false;
                    var i = 0;
                    var TempNum = 0;
                    for (var k = 0; k < ItemCount; k++)
                    {
                        switch (NetItems[k].iDataType)
                        {
                            case KIEU_DL.CST_TEXT:
                                if (NetItems[k].bIsNotNull && NetItems[k].sValue == "")
                                {
                                    this.$toast.error("Phải nhập " + NetItems[k].sCaption + ".");
                                    return true;
                                }
                                i = NetItems[k].sValue.Length;
                                if (NetItems[k].bIsNotNull)
                                {
                                    if (i == 0)
                                    {
                                        this.$toast.error("Phải nhập " + NetItems[k].sCaption + ".");
                                        return true;
                                    }
                                }
                                if (NetItems[k].sMaxValue && NetItems[k].sMinvalue)
                                {
                                    if (i > NetItems[k].sMaxValue || i < NetItems[k].sMinvalue)
                                    {
                                        if (NetItems[k].sMaxValue > 0 && NetItems[k].sMinvalue > 0)
                                            if (NetItems[k].sMaxValue > 0)
                                            {
                                                this.$toast.error(NetItems[k].sCaption + " không thể lớn hơn " + NetItems[k].sMaxValue + " ký tự.");
                                                return true;
                                            }
                                            else
                                            {
                                                this.$toast.error("Phải nhập " + NetItems[k].sCaption + ".");
                                                return true;
                                            }
                                    }
                                }
                                break;
                            case KIEU_DL.CST_NUMBER:
                                if (NetItems[k].sValue.Length > 0 && isNaN(NetItems[k].sValue))
                                {
                                    this.$toast.error(NetItems[k].sCaption + " phải là kiểu số.");
                                    return true;
                                }
                                TempNum = NetItems[k].sValue ? NetItems[k].sValue: -1;

                                if (NetItems[k].bIsNotNull && !NetItems[k].sValue)
                                {
                                    this.$toast.error("Phải nhập " + NetItems[k].sCaption + ".");
                                    return true;
                                }
                                i = NetItems[k].sValue.length;
                                if (NetItems[k].sMaxValue && NetItems[k].sMinvalue)
                                {
                                    if (i > NetItems[k].sMaxValue || i > NetItems[k].sMinvalue)
                                    {
                                        if (NetItems[k].sMaxValue > 0 && NetItems[k].sMinvalue > 0)
                                            if (NetItems[k].sMaxValue > 0)
                                            {
                                                this.$toast.error(NetItems[k].sCaption + " không thể lớn hơn " + NetItems[k].sMaxValue + " ký tự.");
                                                return true;
                                            }
                                    }
                                }
                                if (NetItems[k].sValue < 0)
                                {
                                    this.$toast.error(NetItems[k].sCaption + " không được nhỏ hơn 0.");
                                    return true;
                                }
                                break;
                            case KIEU_DL.CST_DATE:
                                if (!NetItems[k].sValue)
                                {
                                    this.$toast.error(NetItems[k].sCaption + " phải có giá trị.");
                                    return true;
                                }
                                break;
                            default:
                                flag = false;
                                break;
                        }
                        if (flag) return flag;
                    }
                    return false;
                }
                catch (ex)
                {
                    this.$toast.error("Có lỗi: " + ex);
                    return true;
                }
            },
            async tsbtnGhiLai_Click() {
                var kt_sl = false;
                var sl_tu = 0;
                var sl_den = 0;
                var dongia = 0;
                var gia_trongoi = 0;
                var gia_vnmobile = 0;
                var gia_vina = 0;
                var gia_viettel = 0;
                var gia_mobi = 0;
                var gia_gtel = 0;
                var gia_trongnuoc = 0;
                var gia_quocte = 0;
                var gia_luutru = 0;
                var luuluong_id = 0;
                var loaihinh_tb = "";
                var ten_tb = "";

                for (var index = 0; index < this.gridEditors.length; ++index)
                {
                    this.ItemList[index].sValue = this.gridEditors[index].Value?(this.gridEditors[index].Value+'').trim():0;
                    if (this.ItemList[index].sFieldName == "SL_TU")
                        kt_sl = true;
                }

                if (this.CheckDataError(this.ItemList, this.ItemList.length))
                {
                    return;
                }

                // Kiểm tra số lượng trước khi insert
                if (kt_sl && this.gridDanhSach.list.length > 0)
                {
                    for (var index = 0; index < this.gridEditors.length; ++index)
                    {
                        if (this.ItemList[index].sFieldName == "SL_TU")
                            sl_tu = this.ItemList[index].sValue;
                        if (this.ItemList[index].sFieldName == "SL_DEN")
                            sl_den = this.ItemList[index].sValue;

                        if (this.ItemList[index].sFieldName == "DONGIA")
                            dongia = this.ItemList[index].sValue;
                        if (this.ItemList[index].sFieldName == "GIA_TRONGOI")
                            gia_trongoi = this.ItemList[index].sValue;

                        if (this.ItemList[index].sFieldName == "GIA_VNMOBILE")
                            gia_vnmobile = this.ItemList[index].sValue;
                        if (this.ItemList[index].sFieldName == "GIA_VINA")
                            gia_vina = this.ItemList[index].sValue;
                        if (this.ItemList[index].sFieldName == "GIA_VIETTEL")
                            gia_viettel = this.ItemList[index].sValue;
                        if (this.ItemList[index].sFieldName == "GIA_MOBI")
                            gia_mobi = this.ItemList[index].sValue;
                        if (this.ItemList[index].sFieldName == "GIA_GTEL")
                            gia_gtel = this.ItemList[index].sValue;

                        if (this.ItemList[index].sFieldName == "GIA_DOWNLOAD_TN")
                            gia_trongnuoc = this.ItemList[index].sValue;
                        if (this.ItemList[index].sFieldName == "GIA_DOWNLOAD_QT")
                            gia_quocte = this.ItemList[index].sValue;
                        if (this.ItemList[index].sFieldName == "GIA_LUUTRU")
                            gia_luutru = this.ItemList[index].sValue;

                    }

                    if (dongia != 0 && gia_trongoi != 0)
                    {
                        this.$toast.error("Chỉ được nhập 1 trong 2 loại Đơn giá hoặc Giá trọn gói !");
                        return;
                    }

                    if (gia_trongoi != 0 && (gia_vnmobile != 0 || gia_vina != 0 || gia_viettel != 0 || gia_mobi != 0 || gia_gtel != 0))
                    {
                        this.$toast.error("Chỉ được nhập 1 trong 2 loại Giá trọn gói hoặc đơn giá từng nhà mạng !");
                        return;
                    }

                    if (gia_trongoi != 0 && (gia_trongnuoc != 0 || gia_quocte != 0 || gia_luutru != 0))
                    {
                        this.$toast.error("Chỉ được nhập 1 trong 2 loại Giá trọn gói hoặc đơn giá thành phần !");
                        return;
                    }

                    if (this.vloaitb_id == LoaiHinhTB.CDN)
                    {
                        if (gia_trongnuoc != 0 && (gia_trongoi != 0 || gia_quocte != 0 || gia_luutru != 0))
                        {
                            this.$toast.error("Giá trong nước khác 0. Các Đơn giá còn lại phải = 0 !");
                            return;
                        }

                        if (gia_quocte != 0 && (gia_trongoi != 0 || gia_trongnuoc != 0 || gia_luutru != 0))
                        {
                            this.$toast.error("Giá quốc tế khác 0. Các Đơn giá còn lại phải = 0 !");
                            return;
                        }

                        if (gia_luutru != 0 && (gia_trongoi != 0 || gia_trongnuoc != 0 || gia_quocte != 0))
                        {
                            this.$toast.error("Giá lưu trữ khác 0. Các Đơn giá còn lại phải = 0 !");
                            return;
                        }
                    }

                    if (sl_tu >= sl_den)
                    {
                        this.$toast.error("Số lượng từ không được lớn hơn số lượng đến !");
                        return;
                    }



                    var _sl_tu = 0;
                    var _sl_den = 0;
                    var _luuluong_id = 0;

                    if (this.tsbtnNhapMoi == false) // Nhập mới
                    {
                        if (this.vloaitb_id == LoaiHinhTB.CDN)
                        {
                            for (var i = 0; i < this.gridDanhSach.list.length; i++)
                            {
                                _luuluong_id = this.gridDanhSach.list[i]['LUULUONG_ID'];
                                _sl_tu = this.gridDanhSach.list[i]['SL_TU'];
                                _sl_den = this.gridDanhSach.list[i]['SL_DEN'];
                                if (_luuluong_id == luuluong_id) // fix bỏi qua kiểm tra khi trùng hình thức lưu lượng
                                {
                                    if ((sl_tu >= _sl_tu && sl_tu <= _sl_den) || (sl_den >= _sl_tu && sl_den <= _sl_den) || (sl_tu <= _sl_tu && sl_den >= _sl_den))
                                    {
                                        this.$toast.error("Trùng khoảng số lượng Từ - Đến. Bạn hãy kiểm tra lại !");
                                        return;
                                    }
                                }
                            }
                        }
                        else if (this.vloaitb_id == LoaiHinhTB.VNPT_COLOCATION)
                        {
                            // ko kiểm tra gì hết
                        }
                        else
                        {
                            for (var i = 0; i < this.gridDanhSach.list.length; i++)
                            {
                                _sl_tu = this.gridDanhSach.list[i]['SL_TU'];
                                _sl_den = this.gridDanhSach.list[i]['SL_DEN'];

                                if ((sl_tu >= _sl_tu && sl_tu <= _sl_den)
                                    || (sl_den >= _sl_tu && sl_den <= _sl_den)
                                    || (sl_tu <= _sl_tu && sl_den >= _sl_den))
                                {
                                    this.$toast.error("Trùng khoảng số lượng Từ - Đến. Bạn hãy kiểm tra lại !");
                                    return;
                                }
                            }
                        }
                    }
                    else
                    {
                        if (this.vloaitb_id != LoaiHinhTB.CDN && this.vloaitb_id != LoaiHinhTB.VNPT_COLOCATION)
                        {
                            for (var i = 0; i < this.gridDanhSach.list.length; i++)
                            {
                                if (this.vhdtb_sub_id != this.gridDanhSach.list[i]['HDTB_SUB_ID'])
                                {
                                    _sl_tu = this.gridDanhSach.list[i]['SL_TU'];
                                    _sl_den = this.gridDanhSach.list[i]['SL_DEN'];

                                    if ((sl_tu >= _sl_tu && sl_tu <= _sl_den)
                                        || (sl_den >= _sl_tu && sl_den <= _sl_den)
                                        || (sl_tu <= _sl_tu && sl_den >= _sl_den))
                                    {
                                        this.$toast.error("Trùng khoảng số lượng Từ - Đến. Bạn hãy kiểm tra lại !");
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
                // end
                if (this.vloaitb_id == LoaiHinhTB.TD_1800 || this.vloaitb_id == LoaiHinhTB.TD_1900)
                {
                    for (var index = 0; index < this.gridEditors.length; ++index)
                    {
                        if (this.ItemList[index].sFieldName == "LOAIHINH_TB") // giờ bắt đầu của 1800/1900Z
                            loaihinh_tb = this.ItemList[index].sValue;
                        if (this.ItemList[index].sFieldName == "TEN_TB")// giờ kết thúc của 1800/1900Z
                            ten_tb = this.ItemList[index].sValue;
                    }

                    loaihinh_tb = this.KiemTra_Gio(loaihinh_tb);
                    ten_tb = this.KiemTra_Gio(ten_tb);
                    if (ten_tb == "error" || loaihinh_tb == "error")
                    {
                        this.$toast.error("Sai định dạng giờ bắt đầu hoặc giờ kết thúc. Định dạng đúng là HH:mm:ss (VD: 02:30:00, 13:00:01,...).");
                        return;
                    }
                    // var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                    //     "command":`select ${DatabaseConstants.DB2}.kiemtra_thoigianbd_kt_sodich(${loaihinh_tb},${ten_tb},${this.vhdtb_id}) kq from dual`
                    // })
                    var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/kiemtra_thoigianbd_kt_sodich",{"loaihinh_tb":loaihinh_tb+'',"ten_tb":ten_tb+'',"vhdtb_id":this.vhdtb_id});
                    var s = "";
                    if (rs && rs.data && rs.data.length>0 && rs.data[0].kq)
                        s = rs.data[0].kq;
                    // var s = new BanCheoBase().GET_STRING_FUNCTION("{?DB2}.kiemtra_thoigianbd_kt_sodich", "vtg_bd", loaihinh_tb, "vtg_kt", ten_tb, "vhdtb_id", vhdtb_id);
                    if (s != "OK")
                    {
                        this.$toast.error(s);
                        return;
                    }
                    for (var index = 0; index < this.gridEditors.length; ++index)
                    {
                        if (this.ItemList[index].sFieldName == "LOAIHINH_TB") // giờ bắt đầu của 1800/1900Z
                        {
                            this.gridEditors[index].Value = loaihinh_tb;
                            this.ItemList[index].sValue = loaihinh_tb;
                        }
                        if (this.ItemList[index].sFieldName == "TEN_TB")// giờ kết thúc của 1800/1900Z
                        {
                            this.gridEditors[index].Value = ten_tb;
                            this.ItemList[index].sValue = ten_tb;
                        }

                    }

                    // grvMacdinh.CloseEditor();
                }
                var dsHDTB_CNTT_SUB = this.ItemList;

                if (this.tsbtnNhapMoi == false) // Nhập mới
                {
                    if (dsHDTB_CNTT_SUB.length > 0)
                        await this.generateInsert(this.vhdtb_id, dsHDTB_CNTT_SUB); //Da xu ly insert o ham generate insert luon roi, khong can chay doan duoi.
                        // await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                        //     "command":await this.generateInsert(this.vhdtb_id, dsHDTB_CNTT_SUB)
                        // });
                        //bangts.ExeSQL(generateInsert(vhdtb_id, dsHDTB_CNTT_SUB));
                }
                else // Cập nhật
                {
                    if (dsHDTB_CNTT_SUB.length > 0)
                        await this.generateUpdate(this.vhdtb_id, dsHDTB_CNTT_SUB); //Da xu ly update o ham generate update luon roi, khong can chay doan duoi.
                        // await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                        //     "command":this.generateUpdate(this.vhdtb_id, dsHDTB_CNTT_SUB)
                        // });//bangts.ExeSQL(generateUpdate(vhdtb_id, dsHDTB_CNTT_SUB));
                }
                if (this.vloaitb_id == LoaiHinhTB.TD_1800 || this.vloaitb_id == LoaiHinhTB.TD_1900) {
                    // var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                    //     "command":`select ${DatabaseConstants.DB2}.capnhat_cuoc_ld_theo_sl_sodich(${this.vhdtb_id},${this.vloaitb_id}) kq from dual`
                    // });
                    var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/capnhat_cuoc_ld_theo_sl_sodich",{"id":this.vhdtb_id,"id1":this.vloaitb_id});
                    var d = 0;
                    if (rs && rs.data)
                        d = rs.data*1;
                    this.tien_ld = d;//new BanCheoBase().GET_STRING_FUNCTION("{?DB2}.capnhat_cuoc_ld_theo_sl_sodich", "vhdtb_id", vhdtb_id, "vloaitb_id", vloaitb_id);
                }

                await this.Load_DS_HDTB_CNTT_SUB();

            },
            async generateInsert(hdtb_id, ItemList)
            {

                var rs = await this.$root.context.post("/web-quantri/donvitinh/get_keys",{
                    "keyname":"HDTB_CNTT_SUB"
                });//getkey.GetKey("HDTB_CNTT_SUB");
                this.vhdtb_sub_id = rs.data;//

                var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/sp_insert_hdtb_cntt_sub", {
                    "append_name": this.vappend_name,
                    "hdtb_id": hdtb_id,
                    "hdtb_sub_id": this.vhdtb_sub_id,
                    "js_hdtb_cntt": JSON.stringify(ItemList),
                });

                // var str = "insert into " + DatabaseConstants.DB2 + ".HDTB_CNTT_SUB(";
                // var field = "";
                // var values = "";
                // for (var item of ItemList)
                // {
                //     field += item.sFieldName + " ,";
                //     if (item.iDataType == KIEU_DL.CST_DATE)
                //         values += "to_date(substr('" + item.sValue + "', 0, 10), 'dd/MM/yyyy'), ";
                //     else
                //         values += "'" + item.sValue + "', ";
                // }
                // field = "hdtb_id, field_id_cha, hdtb_sub_id, " + field;
                // field = field.substring(0, field.length - 2);
                // values = hdtb_id + ", '" + this.vappend_name + "'," + this.vhdtb_sub_id + "," + values;
                // values = values.substring(0, values.length - 2);
                // str += field + ") VALUES (" + values + ")";
                // return str;
            },

            async generateUpdate(hdtb_id, ItemList)
            {
                var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/sp_update_hdtb_cntt_sub_gensql", {
                    "hdtb_sub_id": this.vhdtb_sub_id,
                    "js_hdtb_cntt": JSON.stringify(ItemList),
                });
                // var str = "update " + DatabaseConstants.DB2 + ".HDTB_CNTT_SUB SET ";
                // for (var item of ItemList)
                // {
                //     if (item.iDataType == KIEU_DL.CST_DATE)
                //         str += item.sFieldName + " = to_date(substr('" + item.sValue + "', 0, 10), 'dd/MM/yyyy') ,";
                //     else
                //         str += item.sFieldName + " = '" + item.sValue + "' ,";
                // }
                // str = str.substring(0, str.length - 1);
                // str += " where hdtb_sub_id = " + this.vhdtb_sub_id;
                // return str;
            },
            tsbtnHuyBo_Click() {
                this.SetButton(2);
                this.LoadThongTin_DV_CNTT_SUB(this.vloaitb_id);
            },
            async tsbtnXoa_Click() {
                try
                {
                    await this.$root.context.post("/web-hopdong/lapdatmoi/sp_delete_hdtb_cntt_sub_gensql", {
                        "hdtb_sub_id": this.vhdtb_sub_id,
                    });
                    // var str = " delete " + DatabaseConstants.DB2 + ".hdtb_cntt_sub";
                    // str += " where hdtb_sub_id = " + this.vhdtb_sub_id;
                    // bangts.ExeSQL(str); //BUG thay cho nay bang ham delete
                    if (this.vloaitb_id == LoaiHinhTB.TD_1800 || this.vloaitb_id == LoaiHinhTB.TD_1900)
                    {
                        // var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                        //     "command":`select ${DatabaseConstants.DB2}.capnhat_cuoc_ld_theo_sl_sodich(${this.vhdtb_id},${this.vloaitb_id}) kq from dual`
                        // });
                        var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/capnhat_cuoc_ld_theo_sl_sodich",{"id":this.vhdtb_id,"id1":this.vloaitb_id});
                        var d = 0;
                        if (rs && rs.data && rs.data.length>0 && rs.data[0].kq)
                            d = rs.data[0].kq;
                        // var d = 0;
                        // if (rs && rs.length>0 && rs[0].kq)
                        this.tien_ld = d;//new BanCheoBase().GET_STRING_FUNCTION("{?DB2}.capnhat_cuoc_ld_theo_sl_sodich", "vhdtb_id", vhdtb_id, "vloaitb_id", vloaitb_id);
                    }
                    this.$toast.success("Xóa thông tin thành công !");
                    this.LoadThongTin_DV_CNTT_SUB(this.vloaitb_id);
                    this.Load_DS_HDTB_CNTT_SUB();
                    this.viewDanhSach_FocusedRowChanged();
                    if (this.gridDanhSach.list.length <= 0)
                    {
                        this.SetButton(1);
                    }
                    //SetButton(1);
                }
                catch (ex)
                {
                    this.$toast.error("Không thể xóa bản ghi này," + ex);
                }
            },
            async selectedItemsChanged() {},
            async viewDanhSach_FocusedRowChanged(dr) {
                try
                {
                    if(dr!= null)
                        dr = this.UpperCasePropertyList(dr);
                    this.vhdtb_sub_id = dr[0]["HDTB_SUB_ID"];
                    await this.LoadThongTin_DV_CNTT_SUB(this.vloaitb_id);

                    for (var index = 0; index < this.gridEditors.length; ++index)
                    {
                        //this.gridEditors[index].Value = dr[this.gridEditors[index].ID];
                        this.gridEditors[index].Value =  dr[0][this.gridEditors[index].ID]
                    }
                    this.grcMacdinh.list = this.gridEditors;
                    // this.grvMacdinh.BestFitColumns();
                    this.SetButton(3);
                }
                catch (ex)
                {
                    //this.$toast.error("Có lỗi: " + ex);
                }
            },

            actionComplete(args) {
                if(args.requestType=="save") {
                    this.gridEditors.find(x=>x.ID == args.data.ID).Value = args.data.Value;
                }
            },
        }
    }
    </script>

