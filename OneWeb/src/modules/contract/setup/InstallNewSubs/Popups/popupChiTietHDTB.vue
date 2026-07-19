<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>{{Text}}</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal" @click.prevent="terminateForm">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="btnThem_ItemClick">
                    <span class="icon nc-icon-glyph ui-2_fat-add"></span> Thêm
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="tsbtnChapNhan_ItemClick">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnXoa_ItemClick">
                    <span class="icon nc-icon-glyph ui-2_fat-remove"></span> Xoá
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnNhapLai_ItemClick">
                    <span class="icon nc-icon-glyph arrows-1_refresh-69"></span> Nhập lại
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnNhapTuAPI_ItemClick">
                    <span class="icon nc-icon-glyph travel_world"></span> Nhập từ API
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnXuatExcel_ItemClick">
                    <span class="icon nc-icon-glyph travel_world"></span> Xuất Excel
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnNhapExcel_ButtonClick">
                    <span class="icon nc-icon-glyph travel_world"></span> Nhập từ Excel
                </a>
            </li>
            <!-- <li :class="{hidden:!btnThoat}">
                <a href="#" @click.prevent="terminateForm">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Huỷ bỏ
                </a>
            </li> -->
        </ul>
    </div>
    <div class="popup-body">
        <div class="row">
            <div class="col-12">
                <div class="box-form">
                    <div class="legend-title mart20">Drop controls here</div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <div class="box-form">
                    <div class="legend-title mart20">Danh mục mẫu hoá đơn</div>
                    <DataGrid
                        v-bind:columns="grcHDThuebaoCT.cols"
                        v-bind:dataSource="grcHDThuebaoCT.list" :showFilter="false" :showColumnCheckbox="false"
                        :dataKeyField="grcHDThuebaoCT.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="200" @selectedItemsChanged="selectedItemsChanged">
                    </DataGrid>
                </div>
            </div>
        </div>
    </div>
    <b-modal id="popupComponentsChiTietHDTB" size="xl"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0">
        <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr" isPopup="true" v-on="popupComponentEvts"></component> <!--v-dynamic-events="popupComponentEvents"-->
    </b-modal>
</div>
</template>
<style scoped>

</style>
<script>
import {
  LoaiHinhTB,
  DatabaseConstants,
} from "../Enums";
export default {
    name: 'popupChiTietHDTB',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            grcHDThuebaoCT: {
                list: [],
                cols: [], // truong hop form nay kha nang cols se phai tu tao dong
                keycol: "",
                value: {},
                values: [],
                Columns: [],
            },
            btnThoat: false,
            loaitb_id: 0,
            append_name: '',
            saveList: [],
            ItemList: [],
            DicInputControl_giahan: [],
            dsControl: [],
            dynamicLayout: [],
            popupComponent: null,
            popupComponentData: null,
            popupComponentAttr: {},
            popupComponentEvts: {
                'form-close':this.popupClosed,
                'acceptChangeCTV':this.popupClosed,
                'acceptChangeNGT':this.popupClosed,
                'PopUpCongTacVien_Thoat':this.popupClosed,
            },
            popupComponentName: '',
            curIdx: -1,
            retVal: null,
            // loaihinh_tb: 0,
            // field_name: '',
            // api_url: '',
            // method: '',
            // hienthi_bang: '',
            // giatri_trave: '',
            // ItemList: [],
            // Text: 'Lấy dữ liệu từ API',
            // DicInputControl: [],
            // dynamicLayout: [],
            // thamsocontrol: [],
        }
    },
    mounted() {
        if(this.dulieu.loaitb_id) this.loaitb_id = this.dulieu.loaitb_id;
        if(this.dulieu.append_name) this.append_name = this.dulieu.append_name;
        if(this.dulieu.saveList) this.saveList = this.dulieu.saveList;
        if(this.dulieu.ItemList) this.ItemList = this.dulieu.ItemList;
        if(this.dulieu.DicInputControl_giahan) {
            this.ItemList = [this.dulieu.ItemList.length];
            for(var item of this.ItemList) {
                var it = {};
                it.sFieldName = item.sFieldName;
                it.sValue = (this.dulieu.DicInputControl_giahan["txt" + it.sFieldName].Value ?this.dulieu.DicInputControl_giahan["txt" + it.sFieldName].Value: "");
                it.sAPIHienThiBang = item.sAPIHienThiBang;
                it.sLookupApi = item.sLookupAPI;
            }
        };
        this.frmChiTietHDTB_Load();
    },
    computed: {
    },
    methods: {
        closeForm() {
            this.$emit('form-close',{ "dsHDTB_CT":this.saveList } );
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
        async frmChiTietHDTB_Load() {
            await this.LoadDuLieu();
            this.TaoForm();
            this.TaoBang();
        },
        async LoadDuLieu() {
            var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/lay_ds_thuoctinh_hdtb_ct",{
                "vloaitb_id":this.loaitb_id,
                "vappend_name":this.append_name
            })
            this.dsControl = this.UpperCasePropertyList(rs.data);//hdtbServices.LayDSControl_HDTBCT(loaitb_id, append_name);
        },
        async TaoForm() {
            var lstLayoutControlItem = [];
            for (var item of this.dsControl)
            {
                var item1 = {};
                var edit = {};
                switch (item.ATT)
                {
                    case 0:
                        edit.Type = 'TextEdit';
                        item1.Control = edit;
                        break;
                    case 1:
                        edit.Type = 'CalcEdit';
                        break;
                    case 2:
                        edit.Type = 'DateEdit';
                        edit.DisplayFormat.FormatString = "dd/MM/yyyy";
                        edit.EditFormat.FormatString = "dd/MM/yyyy";
                        edit.EditMask = "dd/MM/yyyy";
                        edit.Mask.UseMaskAsDisplayFormat = true;
                        edit.Mask.MaskType = DevExpress.XtraEditors.Mask.MaskType.DateTimeAdvancingCaret;
                        break;
                    case 4:
                        edit.Type = 'GridLookUpEdit';
                        edit.ValueMember = item.PARENT_KEYFIELD;
                        edit.DisplayMember = item.PARENT_DESCFIELD;
                        edit.View.OptionsView.ShowAutoFilterRow = true;
                        // var sql = item.LOOKUP_SQL;
                        // if (sql)
                        // {
                        //     var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                        //         "command":sql
                        //     });//DataTable dt_sql = tdan.GET_DATA_SQL(sql);
                        //     edit.DataSource = rs.data;
                        // }
                        edit.DataSource = item.LOOKUP_SQL_RESPONSE;//lookup_sql_response
                        break;
                    case 5:
                        edit.Type = 'ButtonEdit';
                        break;
                    case 7:
                        edit.Type = 'CheckEdit';
                        edit.ValueChecked = 1;
                        edit.ValueUnchecked = 0;
                        edit.Text = "";
                        break;
                    case 9:
                        edit.Type = 'DateEdit';
                        edit.DisplayFormat.FormatString = "dd/MM/yyyy HH:mm:ss";
                        edit.EditFormat.FormatString = "dd/MM/yyyy HH:mm:ss";
                        edit.EditMask = "dd/MM/yyyy HH:mm:ss";
                        edit.Mask.UseMaskAsDisplayFormat = true;
                        // edit.Mask.MaskType = DevExpress.XtraEditors.Mask.MaskType.DateTimeAdvancingCaret;
                        break;
                    case 10:
                        edit.Type = 'CheckedComboBoxEdit';
                        edit.ValueMember = item.PARENT_KEYFIELD;
                        edit.DisplayMember = item.PARENT_DESCFIELD;
                        // var sql2 = item.LOOKUP_SQL;
                        // if (sql2)
                        // {
                        //     var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                        //         "command":sql2
                        //     });
                        //     edit.DataSource = rs.data;
                        // }
                        edit.DataSource = item.LOOKUP_SQL_RESPONSE
                        break;
                    default:
                        edit.Type = 'TextEdit';
                        break;
                }
                item1.Control = edit;
                edit.Name = "txt" + item.FIELD_NAME;
                this.DicInputControl.push(edit);

                // item1.Location = new Point(0, 0);
                item1.Text = item.TEN_HIENTHI;
                this.dynamicLayout.push(item1);
            }
        },
        async TaoBang()
        {
            var lstCol = [];
            var i = 0;
            for (var item of this.dsControl)
            {
                var col = {};
                col.Name = item.FIELD_NAME;
                col.FieldName = item.FIELD_NAME;
                col.Caption = item.TEN_HIENTHI;
                col.Visible = true;
                col.VisibleIndex = i;

                var edit = {};
                switch (item.ATT)
                {
                    case 0:
                        edit.Type = 'RepositoryItemTextEdit';
                        break;
                    case 1:
                        edit.Type = 'RepositoryItemTextEdit';
                        break;
                    case 2:
                        edit.Type = 'RepositoryItemDateEdit';
                        edit.DisplayFormat.FormatString = "dd/MM/yyyy";
                        edit.EditFormat.FormatString = "dd/MM/yyyy";
                        edit.EditMask = "dd/MM/yyyy";
                        edit.Mask.UseMaskAsDisplayFormat = true;
                        edit.Mask.MaskType = DevExpress.XtraEditors.Mask.MaskType.DateTimeAdvancingCaret;
                        break;
                    case 4:
                        edit.Type = 'RepositoryItemGridLookUpEdit';
                        edit.ValueMember = item.PARENT_KEYFIELD;
                        edit.DisplayMember = item.PARENT_DESCFIELD;
                        edit.View.OptionsView.ShowAutoFilterRow = true;
                        // var sql = item.LOOKUP_SQL;
                        // if (sql)
                        // {
                        //     var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                        //         "command":sql
                        //     });//DataTable dt_sql = tdan.GET_DATA_SQL(sql);
                        //     edit.DataSource = rs.data;
                        // }
                        edit.DataSource = item.LOOKUP_SQL_RESPONSE
                        break;
                    case 5:
                        edit.Type = 'RepositoryItemTextEdit';
                        break;
                    case 7:
                        edit = 'RepositoryItemCheckEdit';
                        edit.ValueChecked = "1";
                        edit.ValueUnchecked = "0";
                        break;
                    case 9:
                        edit.Type = 'RepositoryItemDateEdit';
                        edit.DisplayFormat.FormatString = "dd/MM/yyyy HH:mm:ss";
                        edit.EditFormat.FormatString = "dd/MM/yyyy HH:mm:ss";
                        edit.EditMask = "dd/MM/yyyy HH:mm:ss";
                        edit.Mask.UseMaskAsDisplayFormat = true;
                        // edit.Mask.MaskType = DevExpress.XtraEditors.Mask.MaskType.DateTimeAdvancingCaret;
                        break;
                    case 10:
                        edit.Type = 'RepositoryItemCheckedComboBoxEdit';
                        edit.ValueMember = item.PARENT_KEYFIELD;
                        edit.DisplayMember = item.PARENT_DESCFIELD;
                        // var sql2 = item.LOOKUP_SQL;
                        // if (sql2)
                        // {
                        //     var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                        //         "command":sql2
                        //     });
                        //     edit.DataSource = rs.data;
                        // }
                        edit.DataSource = item.LOOKUP_SQL_RESPONSE
                        break;
                    default:
                        edit.Type = 'RepositoryItemTextEdit';
                        break;
                }

                col.ColumnEdit = edit;
                lstCol.push(col);
                i++;
            }
            this.grcHDThuebaoCT.Columns.push(lstCol);

            this.grcHDThuebaoCT.list = this.saveList;
        },
        removeFunction(xoa,dk) {
            for (var prop of Object.keys(xoa))
            {
                var a = xoa[prop];
                var b = dk[prop];
                if ((a?a:0) != (b?b:0))
                    return false;
            }
            return true;
        },
        async btnThem_ItemClick() {
            var hdtbCT = {};
            for(var c of this.DicInputControl)
                try { hdtbCT[c.Name.substring(3)] = c.Value; }
                catch(ex) { continue; }
            this.saveList.push(hdtbCT);
            this.grcHDThuebaoCT.list = this.saveList;
        },
        btnXoa_ItemClick() {
            var del = this.grcHDThuebaoCT.value;
            this.saveList = this.saveList.filter(x=>!this.removeFunction(x,del));
            this.grcHDThuebaoCT.list = this.saveList;
        },
        btnNhapLai_ItemClick() {
            for (var i = 0; i < this.DicInputControl.length; i++)
            {
                i.Value = null;
            }
        },
        btnXuatExcel_ItemClick() {
            //TODO chua lam
        },
        NhapTuExcel() {
            //TODO chua lam
        },
        btnNhapExcel_ButtonClick() {
            //TODO chua lam
            try {
                //TODO chua lam
                this.NhapTuExcel();
            } catch (ex) { this.$toast.error(''+ex); }
        },
        laydulieutu_itemlist(field_name)
        {
            for (var item of this.ItemList)
                if (item.sFieldName == field_name)
                    return item.sValue;
            return "";
        },
        btnNhapTuAPI_ItemClick() {
            var thamso = { "Name":"idtruong", "Value":this.laydulieutu_itemlist("MA_PHU"), };
            if (this.loaitb_id == LoaiHinhTB.VNPT_SLL)
                for (var i = 0; i < this.ItemList.length; i++)
                    if (this.ItemList[i].sFieldName == this.append_name)
                    {
                        this.retVal = null;
                        this.popupComponentData = {"loaihinh_tb":LoaiHinhTB.VNPT_SLL,"field_name":this.append_name,"api_url":this.ItemList[i].sLookupApi,"method":this.ItemList[i].sAPIMethod?this.ItemList[i].sAPIMethod:"GET","hienthi_bang":this.ItemList[i].sAPIHienThiBang,"giatri_trave":this.ItemList[i].sAPIValueMember,"ItemList":this.ItemList,"thamsocontrol":thamso};
                        if (this.listbox.loaihinh_tb.value == LoaiHinhTB.VNPT_SLL)
                        {
                            this.popupComponentData.Text = "Lấy thông tin ID trường";
                        }
                        this.popupComponent = ()=>import('./popupLayDuLieuTuAPI');
                        this.popupComponentName = "popupLayDuLieuTuAPI";
                        this.Popup('popupComponentsChiTietHDTB');
                        while(this.retVal==null) {
                            //Khong lam gi, tao cai nay de dong bo popup cho vong for!
                        }

                        var lst = JSON.parse(this.ItemList[i].sAPIHienThiBang);
                        for (var r of this.retVal.bangtrave)
                        {
                            var dl = {};
                            for (var avt of lst)
                            {
                                dl[avt.TENCOT_DHSX] = r[avt.TENCOT_API]?r[avt.TENCOT_API]:null;
                            }
                            this.saveList.push(dl);
                        }
                    }
            this.grcHDThuebaoCT.list = this.saveList;
        },
        Popup(modalId) {
        this.$bvModal.show(modalId);
        },
        ClosePopup(modalId) {
            this.$bvModal.hide(modalId);
            this.popupComponent = null;
        },
        async popupClosed(val) {
            if(!val) {
                this.retVal = {};
                this.ClosePopup('popupComponentsChiTietHDTB');
                return;
            }
            this.loading(true);
            try {
                switch(this.popupComponentName) {
                    case "popupLayDuLieuTuAPI":
                        this.retVal = val?val:{};
                        break;
                }
            } catch(ex) { }
            this.loading(false);
        },
    }
}
</script>
