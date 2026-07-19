<template src="./index.html"></template>
<script>
import moment from "moment"
import Enum from '../../../Enum.js'
const DatabaseConstants = { DB2: "CSS" };
export default {
    props: ["idLoaihinhTb", "idKieuLd", "parent"],
    data() {
        return {
            _tocdo_id: null,
            dsComboDonVi: [],
            gridEditors: [],
            ItemList: [],
            ca_serial: null
        }
    },
    methods: {
        async HienThiTTMoRongHD(hdtb_id) {
            let ds = await this.LAY_DS_HDTB_CNTT_THEO_HDTB_ID(hdtb_id)
            console.log("LAY_DS_HDTB_CNTT_THEO_HDTB_ID: ", ds)

            if (ds.length <= 0) return

            let ItemList = await this.lay_danhsach_thuoctinh_v3(this.idLoaihinhTb)
            this.gridEditors = []

            for (let index1 = 0; index1 < ItemList.length; ++index1)
            {
                if (ItemList[index1].field_name == "MA_TB") {
                    ItemList[index1].field_value = this.parent.modelInput.txtMaTB
                    ItemList[index1].sValue = this.parent.modelInput.txtMaTB
                } else {
                    ItemList[index1].field_value = ds[0][ItemList[index1].field_name.toLowerCase()]
                    ItemList[index1].sValue = ds[0][ItemList[index1].field_name.toLowerCase()]
                }
                if (ItemList[index1].field_name == "SERIAL") {
                    this.ca_serial = ItemList[index1].field_value
                }
                if (ItemList[index1].att == 0)
                {
                    let repositoryItemTextEdit = {'Type':'TextEdit'};
                    repositoryItemTextEdit.Tag = ItemList[index1].field_name;
                    repositoryItemTextEdit.EditValueChanged = this.repositoryItemTextEdit_EditValueChanged;
                    repositoryItemTextEdit.Leave += this.repositoryItemTextEdit_Leave;
                    repositoryItemTextEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemTextEdit.Group = "Tham số báo cáo";
                    repositoryItemTextEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemTextEdit.sValue = ItemList[index1].field_value;
                    repositoryItemTextEdit.RepositoryItem = repositoryItemTextEdit;
                    this.gridEditors.push(repositoryItemTextEdit);
                }

                if (ItemList[index1].att == 1) {
                    let repositoryItemCalcEdit = {'Type':'CalcEdit'};
                    repositoryItemCalcEdit.Tag = ItemList[index1].field_name;
                    repositoryItemCalcEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemCalcEdit.Group = "Tham số báo cáo";
                    repositoryItemCalcEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemCalcEdit.sValue = ItemList[index1].field_value;
                    repositoryItemCalcEdit.RepositoryItem = repositoryItemCalcEdit;
                    this.gridEditors.push(repositoryItemCalcEdit);
                }

                if (ItemList[index1].att == 2) {
                    ItemList[index1].sValue = ItemList[index1].field_value;
                    let repositoryItemDateEdit = {'Type':'DateEdit'};
                    repositoryItemDateEdit.Tag = ItemList[index1].field_name;
                    repositoryItemDateEdit.FormatString = "dd/MM/yyyy";
                    repositoryItemDateEdit.EditMask = "dd/MM/yyyy";
                    repositoryItemDateEdit.EditValueChanged = this.repositoryItemDateEdit_EditValueChanged;
                    repositoryItemDateEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemDateEdit.Group = "Tham số báo cáo";
                    repositoryItemDateEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemDateEdit.sValue = (ItemList[index1].field_value && moment(ItemList[index1].field_value).format("DD/MM/yyyy"));
                    repositoryItemDateEdit.RepositoryItem = repositoryItemDateEdit;
                    this.gridEditors.push(repositoryItemDateEdit);
                }

                if (ItemList[index1].att == 9) {
                    ItemList[index1].sValue = ItemList[index1].field_value;
                    let repositoryItemDateEdit = {'Type':'DateEdit'};
                    repositoryItemDateEdit.Tag = ItemList[index1].field_name;
                    repositoryItemDateEdit.FormatString = "dd/MM/yyyy HH:mm:ss";
                    repositoryItemDateEdit.EditMask = "dd/MM/yyyy HH:mm:ss";
                    repositoryItemDateEdit.EditValueChanged = this.repositoryItemDateEdit_EditValueChanged;
                    repositoryItemDateEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemDateEdit.Group = "Tham số báo cáo";
                    repositoryItemDateEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemDateEdit.sValue = ItemList[index1].field_value;
                    repositoryItemDateEdit.RepositoryItem = repositoryItemDateEdit;
                    this.gridEditors.push(repositoryItemDateEdit);
                }

                if (ItemList[index1].att == 4)
                {
                    let repositoryItemLookUpEdit = {'Type':'LookUpEdit'};
                    repositoryItemLookUpEdit.Tag = ItemList[index1].field_name;
                    if (ItemList[index1].field_name == "PHANLOAI_ID" || ItemList[index1].field_name == "TOCDO_ID") {
                        repositoryItemLookUpEdit.ReadOnly = true
                    }
                    let SQL = ItemList[index1].lookup_sql;
                    // if (SQL && SQL != "" && SQL.indexOf("{") < 0)
                    // {
                    //     let tableName = ItemList[index1].parent_table.replace("{?CSS}", DatabaseConstants.DB2)
                    //     let data = await this.post_sp_runsql(tableName)
                    //     if (!data) {
                    //         repositoryItemLookUpEdit.DataSource = [];
                    //     } else {
                    //         repositoryItemLookUpEdit.DataSource = data;
                    //     }
                    // }
                    // else
                    // {
                        SQL = await this.ReturnSqlStr(ItemList, index1);
                        let db = "";
                        if (SQL.indexOf("CSS.") > 0) {
                            db = SQL.slice(SQL.indexOf("CSS."))
                        } else {
                            db = SQL.slice(SQL.indexOf("from "))
                            db = db.replace("from ", "")
                        }
                        // let db = SQL.slice(SQL.indexOf("CSS."))
                        if (db == "null") {
                            db = "HDTB_CNTT"
                        }
                        if (db.indexOf(" ") > 0) {
                            db = db.substring(0, db.indexOf(" "))
                        }
                        let data = await this.post_sp_runsql(db)
                        if (!data) {
                            repositoryItemLookUpEdit.DataSource = [];
                        } else {
                            repositoryItemLookUpEdit.DataSource = data;
                        }
                    // }
                    repositoryItemLookUpEdit.EditValueChanged = this.repositoryItemLookUpEdit_EditValueChanged;
                    repositoryItemLookUpEdit.ValueMember = ItemList[index1].parent_keyfield.toLowerCase();
                    repositoryItemLookUpEdit.DisplayMember = ItemList[index1].parent_descfield.toLowerCase();
                    repositoryItemLookUpEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemLookUpEdit.Group = "Tham số báo cáo";
                    repositoryItemLookUpEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemLookUpEdit.sValue = ItemList[index1].field_value;
                    repositoryItemLookUpEdit.NullText = "";
                    repositoryItemLookUpEdit.RepositoryItem = repositoryItemLookUpEdit;
                    this.gridEditors.push(repositoryItemLookUpEdit);
                }

                if (ItemList[index1].att == 5) //Button Edit
                {
                    let repositoryItemButtonEdit = {'Type':'ButtonEdit'};
                    repositoryItemButtonEdit.Tag = ItemList[index1].field_name;
                    repositoryItemButtonEdit.ButtonClick = this.repositoryItemButtonEdit_ButtonClick;
                    repositoryItemButtonEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemButtonEdit.Group = "Tham số báo cáo";
                    repositoryItemButtonEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemButtonEdit.sValue = ItemList[index1].field_value;
                    repositoryItemButtonEdit.RepositoryItem = repositoryItemButtonEdit;
                    this.gridEditors.push(repositoryItemButtonEdit);
                }

                if (ItemList[index1].att == 7)
                {
                    let repositoryItemCheckEdit = {'Type':'CheckEdit'};
                    repositoryItemCheckEdit.Tag = ItemList[index1].field_name;
                    repositoryItemCheckEdit.ValueChecked = 1;
                    repositoryItemCheckEdit.ValueUnchecked = 0;
                    repositoryItemCheckEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemCheckEdit.Group = "Tham số báo cáo";
                    repositoryItemCheckEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemCheckEdit.sValue = ItemList[index1].field_value;
                    repositoryItemCheckEdit.RepositoryItem = repositoryItemCheckEdit;
                    this.gridEditors.push(repositoryItemCheckEdit);
                }
            }

            for (let index = 0; index < this.gridEditors.length; ++index) {
                if (this.gridEditors[index].ID.toUpperCase() == "MA_TB") {
                    this.gridEditors[index].sValue = this.parent.modelInput.txtMaTB
                    ItemList[index].field_value = this.parent.modelInput.txtMaTB
                } else {
                    if (typeof ds[0][this.gridEditors[index].ID] == "object") {
                        this.gridEditors[index].sValue = ds[0][this.gridEditors[index].ID]
                    } else {
                        this.gridEditors[index].sValue = ds[0][this.gridEditors[index].ID]
                    }
                    if (ItemList[index].att == 2 && ds[0][this.gridEditors[index].ID]) {
                        ItemList[index].field_value = moment(ds[0][this.gridEditors[index].ID]).format("DD/MM/yyyy")
                        ItemList[index].sValue = moment(ds[0][this.gridEditors[index].ID]).format("DD/MM/yyyy")
                    } else {
                        ItemList[index].field_value = ds[0][this.gridEditors[index].ID]
                        ItemList[index].sValue = ds[0][this.gridEditors[index].ID]
                    }
                }
            }

            if (ds.length > 0) {
                if (ds[0]["tocdo_id"] != "") {
                    this.parent.inputValue._tocdo_id = parseInt(ds[0]["tocdo_id"])
                } else {
                    this.parent.inputValue._tocdo_id = 0
                }
            }
            this.ItemList = ItemList
            console.log("HienThiTTMoRongHD: ", this.ItemList)
        },
        async LAY_DS_DB_CNTT_THEO_TBID(thuebao_id) {
            try {
                let rs = await this.$root.context.get('/web-hopdong/chuyendich-cntt/lay_ds_db_cntt_by_tbid?thuebao_id=' + thuebao_id)
                return rs.data
            } catch (error) {
                return []
            }
        },
        async LAY_DS_HDTB_CNTT_THEO_HDTB_ID(hdtb_id) {
            try {
                let rs = await this.$root.context.get('/web-hopdong/chuyendich-cntt/lay_ds_hdtb_thuebao_theo_hdtbid?hdtb_id=' + hdtb_id)
                return rs.data
            } catch (error) {
                return []
            }
        },
        async HienThiTTMoRongTB(thuebao_id) {
            let ds = await this.LAY_DS_DB_CNTT_THEO_TBID(thuebao_id)
            console.log('LAY_DS_DB_CNTT_THEO_TBID: ', ds);
            if (ds.length <= 0 ) {
                return
            }

            let ItemList = await this.lay_danhsach_thuoctinh_v3(this.idLoaihinhTb)
            this.gridEditors = []

            for (let index1 = 0; index1 < ItemList.length; ++index1)
            {
                if (ItemList[index1].field_name == "MA_TB") {
                    ItemList[index1].field_value = this.parent.modelInput.txtMaTB
                    ItemList[index1].sValue = this.parent.modelInput.txtMaTB
                } else {
                    ItemList[index1].field_value = ds[0][ItemList[index1].field_name.toLowerCase()]
                    ItemList[index1].sValue = ds[0][ItemList[index1].field_name.toLowerCase()]
                }
                if (ItemList[index1].field_name == "SERIAL") {
                    this.ca_serial = ItemList[index1].field_value
                }
                if (ItemList[index1].att == 0)
                {
                    let repositoryItemTextEdit = {'Type':'TextEdit'};
                    repositoryItemTextEdit.Tag = ItemList[index1].field_name;
                    repositoryItemTextEdit.EditValueChanged = this.repositoryItemTextEdit_EditValueChanged;
                    repositoryItemTextEdit.Leave += this.repositoryItemTextEdit_Leave;
                    repositoryItemTextEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemTextEdit.Group = "Tham số báo cáo";
                    repositoryItemTextEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemTextEdit.sValue = ItemList[index1].field_value;
                    repositoryItemTextEdit.RepositoryItem = repositoryItemTextEdit;
                    this.gridEditors.push(repositoryItemTextEdit);
                }

                if (ItemList[index1].att == 1) {
                    let repositoryItemCalcEdit = {'Type':'CalcEdit'};
                    repositoryItemCalcEdit.Tag = ItemList[index1].field_name;
                    repositoryItemCalcEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemCalcEdit.Group = "Tham số báo cáo";
                    repositoryItemCalcEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemCalcEdit.sValue = ItemList[index1].field_value;
                    repositoryItemCalcEdit.RepositoryItem = repositoryItemCalcEdit;
                    this.gridEditors.push(repositoryItemCalcEdit);
                }

                if (ItemList[index1].att == 2) {
                    ItemList[index1].sValue = ItemList[index1].field_value;
                    let repositoryItemDateEdit = {'Type':'DateEdit'};
                    repositoryItemDateEdit.Tag = ItemList[index1].field_name;
                    repositoryItemDateEdit.FormatString = "dd/MM/yyyy";
                    repositoryItemDateEdit.EditMask = "dd/MM/yyyy";
                    repositoryItemDateEdit.EditValueChanged = this.repositoryItemDateEdit_EditValueChanged;
                    repositoryItemDateEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemDateEdit.Group = "Tham số báo cáo";
                    repositoryItemDateEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemDateEdit.sValue = (ItemList[index1].field_value && moment(ItemList[index1].field_value).format("DD/MM/yyyy"));
                    repositoryItemDateEdit.RepositoryItem = repositoryItemDateEdit;
                    this.gridEditors.push(repositoryItemDateEdit);
                }

                if (ItemList[index1].att == 9) {
                    ItemList[index1].sValue = ItemList[index1].field_value;
                    let repositoryItemDateEdit = {'Type':'DateEdit'};
                    repositoryItemDateEdit.Tag = ItemList[index1].field_name;
                    repositoryItemDateEdit.FormatString = "dd/MM/yyyy HH:mm:ss";
                    repositoryItemDateEdit.EditMask = "dd/MM/yyyy HH:mm:ss";
                    repositoryItemDateEdit.EditValueChanged = this.repositoryItemDateEdit_EditValueChanged;
                    repositoryItemDateEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemDateEdit.Group = "Tham số báo cáo";
                    repositoryItemDateEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemDateEdit.sValue = ItemList[index1].field_value;
                    repositoryItemDateEdit.RepositoryItem = repositoryItemDateEdit;
                    this.gridEditors.push(repositoryItemDateEdit);
                }

                if (ItemList[index1].att == 4)
                {
                    let repositoryItemLookUpEdit = {'Type':'LookUpEdit'};
                    repositoryItemLookUpEdit.Tag = ItemList[index1].field_name;
                    if (ItemList[index1].field_name == "PHANLOAI_ID" || ItemList[index1].field_name == "TOCDO_ID") {
                        repositoryItemLookUpEdit.ReadOnly = true
                    }
                    let SQL = ItemList[index1].lookup_sql;
                    // if (SQL && SQL != "" && SQL.indexOf("{") < 0)
                    // {
                    //     let tableName = ""
                    //     console.log(ItemList[index1].parent_table.includes("{?CSS}"))
                    //     if (ItemList[index1].parent_table.includes("{?CSS}")) {
                    //         tableName = ItemList[index1].parent_table.replace("{?CSS}", DatabaseConstants.DB2)
                    //     }
                    //     // let tableName = ItemList[index1].parent_table.replace("{?CSS}", DatabaseConstants.DB2)
                    //     let data = await this.post_sp_runsql(tableName)
                    //     if (!data) {
                    //         repositoryItemLookUpEdit.DataSource = [];
                    //     } else {
                    //         repositoryItemLookUpEdit.DataSource = data;
                    //     }
                    // }
                    // else
                    // {
                        SQL = await this.ReturnSqlStr(ItemList, index1);
                        let db = "";
                        if (SQL.indexOf("CSS.") > 0) {
                            db = SQL.slice(SQL.indexOf("CSS."))
                        } else {
                            db = SQL.slice(SQL.indexOf("from "))
                            db = db.replace("from ", "")
                        }
                        // let db = SQL.slice(SQL.indexOf("CSS."))
                        if (db == "null") {
                            db = "HDTB_CNTT"
                        }
                        if (db.indexOf(" ") > 0) {
                            db = db.substring(0, db.indexOf(" "))
                        }
                        let data = await this.post_sp_runsql(db)
                        if (!data) {
                            repositoryItemLookUpEdit.DataSource = [];
                        } else {
                            repositoryItemLookUpEdit.DataSource = data;
                        }
                    // }
                    repositoryItemLookUpEdit.EditValueChanged = this.repositoryItemLookUpEdit_EditValueChanged;
                    repositoryItemLookUpEdit.ValueMember = ItemList[index1].parent_keyfield.toLowerCase();
                    repositoryItemLookUpEdit.DisplayMember = ItemList[index1].parent_descfield.toLowerCase();
                    repositoryItemLookUpEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemLookUpEdit.Group = "Tham số báo cáo";
                    repositoryItemLookUpEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemLookUpEdit.sValue = ItemList[index1].field_value;
                    repositoryItemLookUpEdit.NullText = "";
                    repositoryItemLookUpEdit.RepositoryItem = repositoryItemLookUpEdit;
                    this.gridEditors.push(repositoryItemLookUpEdit);
                }

                if (ItemList[index1].att == 5) //Button Edit
                {
                    let repositoryItemButtonEdit = {'Type':'ButtonEdit'};
                    repositoryItemButtonEdit.Tag = ItemList[index1].field_name;
                    repositoryItemButtonEdit.ButtonClick = this.repositoryItemButtonEdit_ButtonClick;
                    repositoryItemButtonEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemButtonEdit.Group = "Tham số báo cáo";
                    repositoryItemButtonEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemButtonEdit.sValue = ItemList[index1].field_value;
                    repositoryItemButtonEdit.RepositoryItem = repositoryItemButtonEdit;
                    this.gridEditors.push(repositoryItemButtonEdit);
                }

                if (ItemList[index1].att == 7)
                {
                    let repositoryItemCheckEdit = {'Type':'CheckEdit'};
                    repositoryItemCheckEdit.Tag = ItemList[index1].field_name;
                    repositoryItemCheckEdit.ValueChecked = 1;
                    repositoryItemCheckEdit.ValueUnchecked = 0;
                    repositoryItemCheckEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemCheckEdit.Group = "Tham số báo cáo";
                    repositoryItemCheckEdit.ID = ItemList[index1].field_name.toLowerCase();
                    repositoryItemCheckEdit.sValue = ItemList[index1].field_value;
                    repositoryItemCheckEdit.RepositoryItem = repositoryItemCheckEdit;
                    this.gridEditors.push(repositoryItemCheckEdit);
                }
            }

            for (let index = 0; index < this.gridEditors.length; ++index) {
                if (this.gridEditors[index].ID.toUpperCase() == "MA_TB") {
                    this.gridEditors[index].sValue = this.parent.modelInput.txtMaTB
                    ItemList[index].field_value = this.parent.modelInput.txtMaTB
                    ItemList[index].sValue = this.parent.modelInput.txtMaTB
                } else {
                    if (typeof ds[0][this.gridEditors[index].ID] == "object") {
                        this.gridEditors[index].sValue = ds[0][this.gridEditors[index].ID]
                    } else {
                        this.gridEditors[index].sValue = ds[0][this.gridEditors[index].ID]
                    }
                    if (ItemList[index].att == 2 && ds[0][this.gridEditors[index].ID]) {
                        ItemList[index].field_value = moment(ds[0][this.gridEditors[index].ID]).format("DD/MM/yyyy")
                        ItemList[index].sValue = moment(ds[0][this.gridEditors[index].ID]).format("DD/MM/yyyy")
                    } else {
                        ItemList[index].field_value = ds[0][this.gridEditors[index].ID]
                        ItemList[index].sValue = ds[0][this.gridEditors[index].ID]
                    }
                }
            }

            if (ds.length > 0) {
                if (ds[0]["tocdo_id"] != "") {
                    this.parent.inputValue._tocdo_id = parseInt(ds[0]["tocdo_id"])
                } else {
                    this.parent.inputValue._tocdo_id = 0
                }
            }

            this.ItemList = ItemList

            // this.HT_COMBO_DONVI()
        },
        
        async TaoDuLieu_HDTB_CNTT(themmoi) {
            let dsHDTB_CNTT = []
            let dt = await this.LAY_DS_DB_CNTT_THEO_TBID(this.parent.inputValue.thuebao_id)
            for (let i = 0; i < this.ItemList.length; i++) {
                if (this.ItemList[i].field_name.toUpperCase() == "MA_TB") continue
                // this.ItemList[i].sValue = dt[0][this.ItemList[i].field_name.toLowerCase()]
            }
            dsHDTB_CNTT = this.ItemList
            return dsHDTB_CNTT
        },
        TaoChoBang_HDTB_DV() {
            let dsHDTBDV = []

            for (let i = 0; i < this.dsComboDonVi.length; i++)
            {
                let item = this.dsComboDonVi[i]
                if (this.$refs[item.key][0].value) {
                    let row = {
                        HDTB_ID: this.parent.inputValue.hdtb_id,
                        LOAIDV_ID: parseInt(item.loaiDonVi),
                        DONVI_ID: parseInt(this.$refs[item.key][0].value),
                        KIEUDV_ID: 2
                    }
                    
                    dsHDTBDV.push(row);
                }
            }

            return dsHDTBDV
        },
        async lay_ds_loai_donvi(data) {
            try {
                let response = await this.$root.context.post(`/web-hopdong/chuyendich-cntt/lay_ds_loai_donvi`, data)
                return response.data
            } catch (err) {
                return []
            }
        },
        async lay_ds_donvi(loaidv_id) {
            try {
                let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_donvi?loaidv_id=${loaidv_id}`)
                return response.data
            } catch (err) {
                return []
            }
        },
        async post_sp_runsql(tableName) {
            try {
                let sql = {
                    p_tablename: tableName,
                    p_loaitb_id: this.idLoaihinhTb
                }
                let response = await this.$root.context.post(`/web-hopdong/chuyendich-cntt/sp_runsql`, sql)
                return response.data
            } catch (err) {
                return []
            }
        },
        async ReturnSqlStr(ListItem, index) {
            let sql = "";
            switch (ListItem[index].parent_table)
            {
                case "PHANLOAI_DV":
                    sql = "select " + ListItem[index].parent_keyfield + "," + ListItem[index].parent_descfield + " from " + DatabaseConstants.DB2 + "." + ListItem[index].parent_table + " WHERE LOAITB_ID = " + this.idLoaihinhTb + " ORDER BY " + ListItem[index].parent_keyfield;
                    break;
                case "TOCDO_ADSL":
                    sql = "select TOCDO_ID, THUONGHIEU from " + DatabaseConstants.DB2 + "." + ListItem[index].parent_table;
                    sql += " where LOAITB_ID = " + this.idLoaihinhTb;
                    if (this.idLoaihinhTb == Enum.LoaiHinhTB.Web_Hosting || this.idLoaihinhTb == Enum.LoaiHinhTB.CA || this.idLoaihinhTb == Enum.LoaiHinhTB.CA_IVAN 
                            || this.idLoaihinhTb == Enum.LoaiHinhTB.IVAN || this.idLoaihinhTb == Enum.LoaiHinhTB.IOFFICE)
                        for (var type of ListItem)
                            if (type.field_name == "DAILY")
                                sql += " and kieu_mega in (2," + parseInt(type.field_value == "" ? 0 : type.field_value) + ")";
                    
                    if (this.idLoaihinhTb == Enum.LoaiHinhTB.VNPT_COLOCATION || this.idLoaihinhTb == Enum.LoaiHinhTB.DEDICATED_SERVER || this.idLoaihinhTb == Enum.LoaiHinhTB.SSL)
                        for (var type of ListItem)
                            if (type.field_name == "PHANLOAI_ID")
                                sql += " and kieu_mega = " + parseInt(type.field_value);
                    sql += " order by TOCDO_ID";
                    break;
                case "HDTB_CNTT":
                    sql = "select 1 chuyengiao, '30 ngày' ten_chuyengiao from dual union all select 2 chuyengiao, 'Theo số ngày của tháng tính cước'ten_chuyengiao from dual";
                    break
                default:
                    sql = "select " + ListItem[index].parent_keyfield + "," + ListItem[index].parent_descfield + " from " + ListItem[index].parent_table
                    break
            }
            sql = sql.replace("{?CSS}", DatabaseConstants.DB2)
            return sql
        },
        async LoadThongTin_DV_CNTT(vloaitb_id) {
            let ItemList = await this.lay_danhsach_thuoctinh_v3(vloaitb_id)
            this.ItemList = ItemList
            this.gridEditors = [];
            for (let index1 = 0; index1 < ItemList.length; ++index1)
            {
                if (ItemList[index1].att == 0)
                {
                    let repositoryItemTextEdit = {'Type':'TextEdit'};
                    repositoryItemTextEdit.Tag = ItemList[index1].field_name;
                    repositoryItemTextEdit.EditValueChanged = this.repositoryItemTextEdit_EditValueChanged;
                    repositoryItemTextEdit.Leave += this.repositoryItemTextEdit_Leave;
                    repositoryItemTextEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemTextEdit.Group = "Tham số báo cáo";
                    repositoryItemTextEdit.ID = ItemList[index1].field_name;
                    repositoryItemTextEdit.sValue = ItemList[index1].field_value;
                    repositoryItemTextEdit.RepositoryItem = repositoryItemTextEdit;
                    this.gridEditors.push(repositoryItemTextEdit);
                }

                if (ItemList[index1].att == 1) {
                    let repositoryItemCalcEdit = {'Type':'CalcEdit'};
                    repositoryItemCalcEdit.Tag = ItemList[index1].field_name;
                    repositoryItemCalcEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemCalcEdit.Group = "Tham số báo cáo";
                    repositoryItemCalcEdit.ID = ItemList[index1].field_name;
                    repositoryItemCalcEdit.sValue = ItemList[index1].field_value;
                    repositoryItemCalcEdit.RepositoryItem = repositoryItemCalcEdit;
                    this.gridEditors.push(repositoryItemCalcEdit);
                }

                if (ItemList[index1].att == 2) {
                    ItemList[index1].sValue = moment(new Date()).format("DD/MM/yyyy");
                    let repositoryItemDateEdit = {'Type':'DateEdit'};
                    repositoryItemDateEdit.Tag = ItemList[index1].field_name;
                    repositoryItemDateEdit.FormatString = "dd/MM/yyyy";
                    repositoryItemDateEdit.EditMask = "dd/MM/yyyy";
                    repositoryItemDateEdit.EditValueChanged = this.repositoryItemDateEdit_EditValueChanged;
                    repositoryItemDateEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemDateEdit.Group = "Tham số báo cáo";
                    repositoryItemDateEdit.ID = ItemList[index1].field_name;
                    repositoryItemDateEdit.sValue = moment(new Date()).format("DD/MM/yyyy");
                    repositoryItemDateEdit.RepositoryItem = repositoryItemDateEdit;
                    this.gridEditors.push(repositoryItemDateEdit);
                }

                if (ItemList[index1].att == 9) {
                    ItemList[index1].sValue = moment(new Date()).format("DD/MM/yyyy HH:mm:ss");
                    let repositoryItemDateEdit = {'Type':'DateEdit'};
                    repositoryItemDateEdit.Tag = ItemList[index1].field_name;
                    repositoryItemDateEdit.FormatString = "dd/MM/yyyy HH:mm:ss";
                    repositoryItemDateEdit.EditMask = "dd/MM/yyyy HH:mm:ss";
                    repositoryItemDateEdit.EditValueChanged = this.repositoryItemDateEdit_EditValueChanged;
                    repositoryItemDateEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemDateEdit.Group = "Tham số báo cáo";
                    repositoryItemDateEdit.ID = ItemList[index1].field_name;
                    repositoryItemDateEdit.sValue = moment(new Date()).format("DD/MM/yyyy HH:mm:ss");
                    repositoryItemDateEdit.RepositoryItem = repositoryItemDateEdit;
                    this.gridEditors.push(repositoryItemDateEdit);
                }

                if (ItemList[index1].att == 4)
                {
                    let repositoryItemLookUpEdit = {'Type':'LookUpEdit'};
                    repositoryItemLookUpEdit.Tag = ItemList[index1].field_name;
                    let SQL = ItemList[index1].lookup_sql;
                    // if (SQL && SQL != "" && SQL.indexOf("{") < 0)
                    // {
                    //     let tableName = ""
                    //     console.log(ItemList[index1].parent_table.includes("{?CSS}"))
                    //     if (ItemList[index1].parent_table.includes("{?CSS}")) {
                    //         tableName = ItemList[index1].parent_table.replace("{?CSS}", DatabaseConstants.DB2)
                    //     }
                    //     // let tableName = ItemList[index1].parent_table.replace("{?CSS}", DatabaseConstants.DB2)
                    //     let data = await this.post_sp_runsql(tableName)
                    //     if (!data) {
                    //         repositoryItemLookUpEdit.DataSource = [];
                    //     } else {
                    //         repositoryItemLookUpEdit.DataSource = data;
                    //     }
                    // }
                    // else
                    // {
                        SQL = await this.ReturnSqlStr(ItemList, index1);
                        let db = "";
                        if (SQL.indexOf("CSS.") > 0) {
                            db = SQL.slice(SQL.indexOf("CSS."))
                        } else {
                            db = SQL.slice(SQL.indexOf("from "))
                            db = db.replace("from ", "")
                        }
                        // let db = SQL.slice(SQL.indexOf("CSS."))
                        if (db == "null") {
                            db = "HDTB_CNTT"
                        }
                        if (db.indexOf(" ") > 0) {
                            db = db.substring(0, db.indexOf(" "))
                        }
                        let data = await this.post_sp_runsql(db)
                        if (!data) {
                            repositoryItemLookUpEdit.DataSource = [];
                        } else {
                            repositoryItemLookUpEdit.DataSource = data;
                        }
                    // }
                    repositoryItemLookUpEdit.EditValueChanged = this.repositoryItemLookUpEdit_EditValueChanged;
                    repositoryItemLookUpEdit.ValueMember = ItemList[index1].parent_keyfield.toLowerCase();
                    repositoryItemLookUpEdit.DisplayMember = ItemList[index1].parent_descfield.toLowerCase();
                    repositoryItemLookUpEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemLookUpEdit.Group = "Tham số báo cáo";
                    repositoryItemLookUpEdit.ID = ItemList[index1].field_name;
                    repositoryItemLookUpEdit.sValue = ItemList[index1].field_value;
                    repositoryItemLookUpEdit.NullText = "";
                    if (!repositoryItemLookUpEdit.Value && repositoryItemLookUpEdit.DataSource && repositoryItemLookUpEdit.DataSource.length>0)
                        repositoryItemLookUpEdit.Value = repositoryItemLookUpEdit.DataSource[0][repositoryItemLookUpEdit.ValueMember];
                    repositoryItemLookUpEdit.RepositoryItem = repositoryItemLookUpEdit;
                    this.gridEditors.push(repositoryItemLookUpEdit);
                }

                if (ItemList[index1].att == 5) //Button Edit
                {
                    let repositoryItemButtonEdit = {'Type':'ButtonEdit'};
                    // repositoryItemButtonEdit.ReadOnly = (ItemList[index1].sReadOnly == 1);
                    repositoryItemButtonEdit.Tag = ItemList[index1].field_name;
                    // if (ItemList[index1].field_name == "MATB_TN")
                    // {
                    //     repositoryItemButtonEdit.ReadOnly = true;
                    // }

                    repositoryItemButtonEdit.ButtonClick = this.repositoryItemButtonEdit_ButtonClick;
                    repositoryItemButtonEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemButtonEdit.Group = "Tham số báo cáo";
                    repositoryItemButtonEdit.ID = ItemList[index1].field_name;
                    repositoryItemButtonEdit.sValue = ItemList[index1].field_value;
                    repositoryItemButtonEdit.RepositoryItem = repositoryItemButtonEdit;
                    this.gridEditors.push(repositoryItemButtonEdit);
                }

                if (ItemList[index1].att == 7)
                {
                    let repositoryItemCheckEdit = {'Type':'CheckEdit'};
                    repositoryItemCheckEdit.Tag = ItemList[index1].field_name;
                    repositoryItemCheckEdit.ValueChecked = 1;
                    repositoryItemCheckEdit.ValueUnchecked = 0;
                    repositoryItemCheckEdit.Name = ItemList[index1].ten_hienthi;
                    repositoryItemCheckEdit.Group = "Tham số báo cáo";
                    repositoryItemCheckEdit.ID = ItemList[index1].field_name;
                    repositoryItemCheckEdit.sValue = ItemList[index1].field_value;
                    repositoryItemCheckEdit.RepositoryItem = repositoryItemCheckEdit;
                    this.gridEditors.push(repositoryItemCheckEdit);
                }
            }

        },
        repositoryItemTextEdit_EditValueChanged(repositoryItemTextEdit) {

        },
        repositoryItemLookUpEdit_EditValueChanged(repositoryItemLookUpEdit) {
            console.log(repositoryItemLookUpEdit)
            for (let i = 0; i < this.ItemList.length; i++) {
                if (this.ItemList[i].att == 4) {
                    this.ItemList[i].sValue = this.gridEditors[i].sValue
                }
            }
        },
        repositoryItemButtonEdit_ButtonClick(repositoryItemButtonEdit) {
            console.log(repositoryItemButtonEdit)
        },
        repositoryItemDateEdit_EditValueChanged(repositoryItemDateEdit) {
            try
            {
                for (let index = 0; index < this.gridEditors.length; index++) {
                    if (this.gridEditors[index].Tag == "NGAY_BD") {
                        let b_ngay_bd = false;
                        let b_ngay_kt = false;
                        let b_sothang = false;
                        let d_ngay_bd = new Date();
                        let d_ngay_kt = new Date();
                        let sothang = 0;

                        for (let i = 0; i < this.ItemList.length; i++)
                        {
                            if (this.ItemList[i].sValue && this.ItemList[i].sValue != "" && this.ItemList[i].field_name == "SO_THANG")
                            {
                                b_sothang = true;
                                sothang = this.gridEditors[i].sValue;
                            }
                            if (this.ItemList[i].field_name == "NGAY_BD" && this.gridEditors[i].sValue != null)
                            {
                                b_ngay_bd = true;
                                this.ItemList[i].field_value = moment(this.gridEditors[i].sValue).format("DD/MM/yyyy");
                                this.ItemList[i].sValue = moment(this.gridEditors[i].sValue).format("DD/MM/yyyy");
                                d_ngay_bd = this.gridEditors[i].sValue;
                            }
                            if (this.ItemList[i].field_name == "NGAY_KT")
                                b_ngay_kt = true;

                            if (b_ngay_bd == true && b_ngay_kt == true && b_sothang == true)
                                break;
                        }
                        if (b_ngay_bd == true && b_ngay_kt == true && b_sothang == true)
                        {
                            d_ngay_kt = moment(d_ngay_bd).add(sothang, 'M')
                            for (let index_1 = 0; index_1 < this.ItemList.length; ++index_1)
                            {
                                if (this.ItemList[index_1].field_name == "NGAY_KT")
                                {
                                    try {
                                        this.ItemList[index_1].field_value = moment(d_ngay_kt).format("DD/MM/yyyy");
                                        this.ItemList[index_1].sValue = moment(d_ngay_kt).format("DD/MM/yyyy");
                                    } catch (ex) { 
                                        this.ItemList[index_1].field_value = d_ngay_kt; 
                                        this.ItemList[index_1].sValue = d_ngay_kt; 
                                    }
                                    this.gridEditors[index_1].sValue = moment(d_ngay_kt).format("DD/MM/yyyy");
                                }
                            }
                        }
                    }
                    if (this.ItemList[index].att == 2 && this.ItemList[index].field_name != "NGAY_BD" && this.ItemList[index].field_name != "NGAY_KT") {
                        this.ItemList[index].sValue = moment(this.gridEditors[index].sValue).format("DD/MM/yyyy")
                    }
                }
                console.log(this.ItemList)
            }
            catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async lay_danhsach_thuoctinh_v3(vloaitb_id) {
            try {
                let data = {
                    vloaitb_id: vloaitb_id,
                    vten_bang: "HDTB_CNTT",
                    vopt_list: "00000"
                }
                let response = await this.$root.context.post(`/web-hopdong/chuyendich-cntt/lay_danhsach_thuoctinh_v3`, data)
                console.log("lay_danhsach_thuoctinh_v3: ", response.data)
                return response.data
            } catch (err) {
                return []
            }
        },
        newComboDonVi(loaiDonVi) {
            return {
                key: `combo-id-${loaiDonVi.loaidv_dich_id}`,
                loaiDonVi: loaiDonVi.loaidv_dich_id,
                template: `
                    <div class="info-row">
                        <div class="key">${loaiDonVi.ten_loaidv}</div>
                        <div class="value">
                            <SelectExt
                                v-model="value" 
                                :dataSource="dataSource" 
                                dataValueField="donvi_id"
                                dataTextField="ten_dv" />
                        </div>
                    </div>
                `,
                data() {
                    return {
                        value: null,
                        dataSource: []
                    }
                },
                mounted() {
                    if (loaiDonVi.dsDonVi.length > 0) {
                        this.dataSource = [...loaiDonVi.dsDonVi]
                        this.value = this.dataSource[0].donvi_id
                    }
                }
            }
        },
        async HT_COMBO_DONVI() {
            let dsLoaiDonVi = await this.lay_ds_loai_donvi({
                loaihinh_tb: this.idLoaihinhTb,
                kieu_ld: this.idKieuLd,
                muccuoctn_id: this.parent.modelCbo.cboMucCuocTB,
                dichvuvt_id: this.parent.modelCbo.cboDichVuVT
            })

            if (this.parent.modelCbo.cboDichVuVT != Enum.DichVuVienThong.ANTOAN_BAOMAT_TT && this.parent.modelCbo.cboDichVuVT != Enum.DichVuVienThong.DICHVU_CNTT 
                    && this.parent.modelCbo.cboDichVuVT != Enum.DichVuVienThong.TRUNGTAM_DULIEU && this.parent.modelCbo.cboDichVuVT != Enum.DichVuVienThong.HOINGHI_TRUYENHINH) return

            let dsComboDonVi = []

            for (let i = 0; i < dsLoaiDonVi.length; i++) {
                let loaiDonVi = dsLoaiDonVi[i]

                loaiDonVi.dsDonVi = await this.lay_ds_donvi(loaiDonVi["loaidv_dich_id"])

                dsComboDonVi.push(this.newComboDonVi(loaiDonVi))
            }

            this.dsComboDonVi = dsComboDonVi
        }
    },
    watch: {
        idLoaihinhTb: async function(value) {
            await this.LoadThongTin_DV_CNTT(value)

            if (!value) {
                this.dsComboDonVi = []
                return
            }

            this.HT_COMBO_DONVI()
        }
    }
}
</script>