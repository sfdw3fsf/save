<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Chọn mẫu hóa đơn</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="btnChapNhan_ItemClick">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận (F5)
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnXemMau_ItemClick">
                    Xem mẫu
                </a>
            </li>
            <li>
                Số lượng hoá đơn <input type="number" v-model="soluong" readonly />
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
            <div class="col-4">
                <div class="box-form">
                    <div class="legend-title mart20">Danh mục loại hoá đơn</div>
                    <DataGrid
                        v-bind:columns="grcDanhMucLoaiHoaDon.cols"
                        v-bind:dataSource="grcDanhMucLoaiHoaDon.list" :showFilter="false" :showColumnCheckbox="false"
                        :dataKeyField="grcDanhMucLoaiHoaDon.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="'500'" @rowClicked="gdvDanhMucLoaiHoaDon_FocusedRowChanged">
                    </DataGrid>
                    <!-- <DataGrid :columns="listbox.dgvDanhSach.cols" :dataSource="listbox.dgvDanhSach.list" :showFilter="showFilter" @selectedRowChanged="dgvDanhSach_selectedRowChanged"></DataGrid> -->
                </div>
            </div>
            <div class="col-8">
                <div class="box-form">
                    <div class="legend-title mart20">Danh mục mẫu hoá đơn</div>
                    <DataGrid
                        v-bind:columns="grcMauHoaDon.cols"
                        v-bind:dataSource="grcMauHoaDon.list" :showFilter="false" :showColumnCheckbox="false"
                        :dataKeyField="grcMauHoaDon.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="'500'" @rowClicked="gdvMauHoaDon_FocusedRowChanged" @rowDoubleClicked="gdvMauHoaDon_DoubleClick">
                    </DataGrid>
                </div>
            </div>
        </div>
    </div>
    <popupPreviewHTML :dulieu="prevhtml" ref="popupPreviewHTML"></popupPreviewHTML>
</div>
</template>
<style scoped>

</style>
<script>
import popupPreviewHTML from './popupPreviewHTML'
export default {
    name: 'popupChonMauHDDT',
    props: [ 'dulieu' ],
    components: { popupPreviewHTML },
    watch: {
    },
    data() {
        return {
            ds: {
                list: [],
                cols: [],
                keycol: "",
                value: {},
            },
            btnThoat: false,
            dsHDTB_CT: [],
            soluong: 0,
            dm_mhd: {},
            grcDanhMucLoaiHoaDon: {
                list: [],
                value: {},
                cols: [
                        {fieldName: 'Lable', headerText: 'Tên mẫu', allowFiltering: true,cellCssClass: 'text-left',},
                        {fieldName: 'Value', headerText: 'ID', allowFiltering: true, cellCssClass: 'text-left',},
                    ],
                keycol: '',
            },
            grcMauHoaDon: {
                list: [],
                value: {},
                cols: [
                        {fieldName: 'chon', headerText: '', allowFiltering: true,cellCssClass: 'text-center',},
                        {fieldName: 'Templatename', headerText: 'Tên mẫu', allowFiltering: true, cellCssClass: 'text-left',},
                        {fieldName: 'Identification', headerText: 'IdMau', allowFiltering: true, cellCssClass: 'text-left',},
                        {fieldName: 'Invcatename', headerText: 'Loại mẫu', allowFiltering: true, cellCssClass: 'text-left',},
                    ],
                keycol: '',
            },
            prevhtml: {
                html: '',
            }
        }
    },
    mounted() {
        if(this.dulieu.dsHDTB_CT) this.dsHDTB_CT = this.dulieu.dsHDTB_CT;
        if(this.dulieu.soluong) this.soluong = this.dulieu.soluong;
        this.frmChonMauHDDT_Load();
    },
    computed: {
    },
    methods: {
        closeForm() {
            this.$emit('form-close',this.ds.value);
        },
        terminateForm() {
            this.$emit('form-close',null);
        },
        rowChanged(idx,item) {
            this.ds.value = item;
        },
        GanMauHoaDonDaDK()
        {
            for (var item of this.dm_mhd.data)
            {
                if (item.chon)
                {
                    var hdtbct = {};
                    hdtbct.MA_CHUAN_CT = item.Identification;
                    hdtbct.DOMAIN_CT = item.Templatename;
                    hdtbct.MA_PHU_CT = item.Invcateid;
                    hdtbct.WEBSITE_CT = item.Invcatename;
                    hdtbct.IP_CT = "0";
                    hdtbct.LOAITB_ID = LoaiHinhTB.TTDL_HDDT;
                    if (this.dsHDTB_CT.filter(x=>x.MA_CHUAN_CT == hdtbct.MA_CHUAN_CT).length<=0)
                        this.dsHDTB_CT.push(hdtbct);
                }
                else
                {
                    this.dsHDTB_CT = this.dsHDTB_CT.filter(x => x.MA_CHUAN_CT != item.Identification);
                }
            }
        },
        LoadMauHoaDonDaDK()
        {
            for (var i = 0; i < this.dm_mhd.data.length; i++)
            {
                if (this.dsHDTB_CT.filter(x => x.MA_CHUAN_CT == this.dm_mhd.data[i].Identification).length > 0)
                    this.dm_mhd.data[i].chon = true;
                else this.dm_mhd.data[i].chon = false;
            }
        },
        gdvMauHoaDon_DoubleClick(idx,item) {
            this.grcMauHoaDon.value = item;
            this.btnXemMau_ItemClick(); //FIXME khi code den day thi bo ham nay, binding thang nut vao la xong!
        },
        gdvMauHoaDon_FocusedRowChanged(idx,item) {
            this.grcMauHoaDon.value = item;
        },
        async gdvDanhMucLoaiHoaDon_FocusedRowChanged(idx,item) {
            this.grcDanhMucLoaiHoaDon.value = item;
            var id = this.grcDanhMucLoaiHoaDon.value['Value'];//GetRowCellValue(e.FocusedRowHandle, "Value").ToString();
            if (this.dm_mhd && this.dm_mhd.data)
            {
                this.GanMauHoaDonDaDK();
            }
            this.dm_mhd = await this.$root.context.get(`/tichhop/hddt/LayDanhMucMauHoaDon?category_id=${id}`);//_hddtServices.LayDanhMucMauHoaDon(id); //TODO gio vao API HDDT service
            if(!this.dm_mhd || !this.dm_mhd.data) return;
            this.LoadMauHoaDonDaDK();
            // BindingList<HDDTServices.DanhMucMauHoaDon.DuLieu> bl = new BindingList<HDDTServices.DanhMucMauHoaDon.DuLieu>(dm_mhd.data); // tuy du lieu, xem lai lenh nay lam gi????
            this.grcMauHoaDon.list = this.dm_mhd.data; //FIXME xem lai cho nay!  gia tri goc = bl, xu ly o tren.
            if(this.grcMauHoaDon.list && this.grcMauHoaDon.list.length>0) this.grcMauHoaDon.value = this.grcMauHoaDon.list[0];
        },
        async frmChonMauHDDT_Load() {
            var dm_lhd = await this.$root.context.get("/tichhop/hddt/LayDanhMucLoaiHoaDon", { });
            this.grcDanhMucLoaiHoaDon.list = dm_lhd.data;
            if(this.grcDanhMucLoaiHoaDon.list && this.grcDanhMucLoaiHoaDon.list.length>0) {
                this.grcDanhMucLoaiHoaDon.value = this.grcDanhMucLoaiHoaDon.list[0];
                this.gdvDanhMucLoaiHoaDon_FocusedRowChanged(0,this.grcDanhMucLoaiHoaDon.value);
            }
            // Khong can thao tac nay!
            // for (int i = 0; i < gdvMauHoaDon.Columns.Count; i++)
            // {
            //     if (gdvMauHoaDon.Columns[i].FieldName != "chon")
            //     {
            //         gdvMauHoaDon.Columns[i].OptionsColumn.ReadOnly = true;
            //         gdvMauHoaDon.Columns[i].OptionsColumn.AllowEdit = false;
            //     }
            // }
        },
        async btnXemMau_ItemClick() {
            var id = this.grcMauHoaDon.value['Identification'];//.GetFocusedRowCellValue("Identification").ToString();
            var cthoadon = await this.$root.context.get(`/tichhop/hddt/LayChiTietMauHoaDon?inv_id=${id}`);//_hddtServices.HTMLMauHoaDon(id); //TODO gio vao API HDDT service
            var prettifyXml = function(sourceXml, sourcexslt)
            {
                var xmlDoc = new DOMParser().parseFromString(sourceXml, 'application/xml');
                var xsltDoc = new DOMParser().parseFromString(sourcexslt, 'application/xml');
                var xsltProcessor = new XSLTProcessor();
                xsltProcessor.importStylesheet(xsltDoc);
                var resultDoc = xsltProcessor.transformToFragment(xmlDoc,document);
                var resultXml = new XMLSerializer().serializeToString(resultDoc);
                
                return resultXml;
            };
            var html = prettifyXml(cthoadon.data.XmlFile,cthoadon.data.XsltFile);
            
            this.prevhtml.html = html;
            this.$refs.popupPreviewHTML.openDialog();
            // frmPreviewHTML f = new frmPreviewHTML(html);
            // f.Show();
        },
        async btnChapNhan_ItemClick()
        {
            // isChapNhan = true;
            // this.mau_id = gdvMauHoaDon.GetFocusedRowCellValue("Identification").ToString();
            this.GanMauHoaDonDaDK();
            this.$emit('form-close',{soluong:this.soluong,dsHDTB_CT:this.dsHDTB_CT});
        },
    }
}
</script>
