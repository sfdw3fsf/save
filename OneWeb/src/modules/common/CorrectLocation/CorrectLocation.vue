
<template src="./CorrectLocation.html"></template>

<style src="./CorrectLocation.scss"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import CorrectLocationAPI from './CorrectLocationAPI'
import gridview from '@/components/Shared/gridview'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
import EventBus from '@/utils/eventBus'
export default {
    components: {
        breadcrumb,
        VueFlatPickr,
        gridview,
        CorrectLocationAPI
    },
    name: 'CorrectLocation',
    mounted() {
        this.getdmquanhuyen();
        this.listbox.ds_data.list = [];
        this.listbox.ds_data_fill.list = [];
        document.onclick = function (e) {
            var hideMe = document.getElementById('move');
            var optionccbs = document.getElementById('optionccbs');
            if (e.name !== "click") {
                hideMe.style.display = 'none';
                optionccbs.style.display = 'none';
            }
            if (e.target.id == 'Phuongccbs') {
                $('#optionccbs').show()
            }
        }
    },
    data() {
        return {
            dateconfig: {
                altFormat: 'd/m/Y',
                altInput: true,
                dateFormat: 'd/m/Y',
                allowInput: true,
            },
            dsIdChon: [],
            gridDS_rowSelected: [],
            showTTHD: "",
            ngay_ht: new Date(),
            header: {
                title: 'CHUẨN HÓA ĐỊA BÀN - CCBS',
                list: [{
                        name: 'Lập hợp đồng',
                        link: {
                            name: 'Ui.cards'
                        }
                    },
                    {
                        name: 'Lắp đặt mới',
                        link: {
                            name: 'Ui.buttons'
                        }
                    }
                ]
            },
            isChuanHoaPhuong: false,
            id_pho_dhsx: '',
            tenphuong:'',
            listbox: {
                ds_quanhuyen: {
                    list: [],
                    value: {
                        QUAN_ID: "",
                        TEN_QUAN: ""
                    },
                    isEnabled: false,
                },
                ds_phuongxa: {
                    list: [],
                    value: {
                        PHUONG_ID: "",
                        TEN_PHUONG: ""
                    },
                    isEnabled: false,
                },
                ds_phuongxadhsxkd: {
                    list: [],
                    value: {
                        PHUONG_ID: "",
                        TENPHUONG: ""
                    },
                    isEnabled: false,
                },
                ds_data: {
                    list: [],
                    value: {},
                    isEnabled: true,
                },
                ds_data_fill: {
                    list: [],
                    value: {},
                    isEnabled: true,
                },
                dsthuebao: {
                    list: [],
                    value: {},
                    isEnabled: true,
                },
                ds_data_popup: {
                    list: [],
                    header: [{
                            fieldName: 'id_phoccbs',
                            headerText: 'ID Phố DHSX',
                            allowFiltering: true,
                            width: 90
                        },
                        {
                            fieldName: 'tenpho_ccbs',
                            headerText: 'Tên phố DHSX',
                            allowFiltering: true,
                            width: 90
                        },

                    ],
                    value: {},
                    isEnabled: true,
                },
                ds_data_phuongccbs: {
                    list: [],
                    header: [{
                            fieldName: 'id',
                            headerText: 'ID',
                            allowFiltering: true,
                            width: 90
                        },
                        {
                            fieldName: 'text',
                            headerText: 'Tên phường',
                            allowFiltering: true,
                            width: 90
                        },

                    ],
                    value: {},
                    isEnabled: true,
                },
            },
            dateConfig: {
                altInput: true,
                dateFormat: 'd/m/Y',
                altFormat: 'd/m/Y'
            },
            success_code: "BSS-00000000",
            from_date: new Date(),
            to_date: new Date(),
            thoihancon_lonhon: 0,
            thoihancon_nhohon: 0,
            somay: '',
        }
    },
    computed: {
        chuanhoaphuong: {
            get() {
                return this.isChuanHoaPhuong;
            },
            set(value) {
                this.isChuanHoaPhuong = value;
                if (this.isChuanHoaPhuong) {
                    this.phuongxachange();
                }
            }
        }

    },
    methods: {

        ShowAlert(mesage, val) {
            if (val == 0) {
                this.$toast.success(mesage);
            } else if (val == 1) {
                this.$toast.warning(mesage);
            } else if (val == 2) {
                this.$toast.error(mesage);
            }
        },
        GetData: function (response) {
            if (response.data.error === 200 || response.data.error === '200') {
                return response.data.data
            } else {
                console.log(response.data.error_code)
            }
            return []
        },

        grid_dataGridCellCreated(e) {
            e.cellAllowHtml = true;
            $(e.cellElement).append(
                '<span class="btn btn-main btn-dropdown -ap icon-ion-android-arrow-dropdown"></span>'
            );
            $(".btn-dropdown").on("click", (e) => {
                console.log(e)
            });
        },
        getColumnTemplateChonPho(parent) {
            return function () {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input 
                                    type="checkbox" 
                                    class="check"
                                    v-model="parent.dsIdChon"
                                    :value="data.ID_PHODHSX"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: {}
                            }
                        }
                    }
                }
            }
        },
        grid_selectedRowChanged: function (data) {
            if (data != null) {
                this.listbox.ds_data_fill.list.forEach(function (obj) {
                    if (obj.ID_PHODHSX == data.ID_PHODHSX) {
                        delete obj.ISCHECK;
                        obj.ISCHECK = "1";
                    }
                });
                this.listbox.ds_data.list = this.listbox.ds_data_fill.list;
                console.log(this.listbox.ds_data.list)
            }
        },
        async grid_selectedRowChanged_phuongccbs (data) {
           this.listbox.ds_phuongxadhsxkd.value.PHUONG_ID = data.id;
            this.tenphuong = data.text;
            var container = $("#optionccbs");
            container.hide();
        },
        grid_selectedRowChanged_chitiet(data) {
            var id = this.id_pho_dhsx;
            if (data != null) {
                this.listbox.ds_data_fill.list.forEach(function (obj) {
                    if (obj.ID_PHODHSX == id) {
                        delete obj.ID_PHOCCBS;
                        delete obj.TENPHO_CCBS;
                        obj.ID_PHOCCBS = data.id_phoccbs.toString();
                        obj.TENPHO_CCBS = data.tenpho_ccbs.toString();
                    }
                });
                this.listbox.ds_data.list = this.listbox.ds_data_fill.list
                //this.CloseDropDown();
                this.$refs.gridDS_Pho.refresh();
                console.log(this.listbox.ds_data.list)
            }
        },
        async recordClick(args) {
            if (args.cellIndex == 4) {
                if (args.column.field == "ID_PHOCCBS" && args.cell.childNodes.length == 1) {
                    $(args.cell).append(
                        '<button class="btn btn-primary btn-dropdown" v-bind:data-toggle="showTTHD" @click="view_dropdown"><span class="-ap icon-ion-android-arrow-dropdown"></span></button>'
                    );
                }
                this.showTTHD = "dropdown";
                var container = $("#move");
                container.show();
                var input = {};
                input.vquan_id = 0;
                input.vphuong_id = this.listbox.ds_phuongxadhsxkd.value.PHUONG_ID;
                this.listbox.ds_data_popup.list = this.GetData(await CorrectLocationAPI.MPCCBS_LAYDS_PHOCCBS_V2(this.axios, input));
                this.id_pho_dhsx = args.rowData.ID_PHODHSX;

            }
        },
        CloseDropDown() {
            var container = $("#move");
            container.hide();
        },
        CloseDropDown_phuongccbs() {
            var container = $("#optionccbs");
            container.hide();
        },
        getdmquanhuyen: async function () {
            var input = {};
            this.listbox.ds_quanhuyen.list = this.GetData(await CorrectLocationAPI.MPCCBS_LAYDS_QUANDHSX(this.axios, input)).filter(x => x.QUAN_ID > 0).map(x => ({
                id: x.QUAN_ID,
                text: x.TEN_QUAN
            }));
            if (this.listbox.ds_quanhuyen.list.length > 0) {
                this.listbox.ds_quanhuyen.value.QUAN_ID = this.listbox.ds_quanhuyen.list[0].id;
                this.quanhuyenchange();
            }
        },

        getdmphuongxa: async function () {
            var input = {};
            input.vparam = this.listbox.ds_quanhuyen.value.QUAN_ID;
            input.vloai = 3;
            this.listbox.ds_phuongxa.list = this.GetData(await CorrectLocationAPI.MPCCBS_LAYDS_PHUONGDHSX(this.axios, input)).filter(x => x.PHUONG_ID > 0).map(x => ({
                id: x.PHUONG_ID,
                text: x.TEN_PHUONG
            }));
            if (this.listbox.ds_phuongxa.list.length > 0) {
                this.listbox.ds_phuongxa.value.PHUONG_ID = this.listbox.ds_phuongxa.list[0].id;
                this.phuongxachange();
            }
        },
        phuongxachange: async function () {
            if (this.isChuanHoaPhuong) //Chuẩn hóa phường
            {
                var input1 = {};
                input1.vquan_id = this.listbox.ds_quanhuyen.value.QUAN_ID; //this.listbox.ds_phuongxa.value.PHUONG_ID;
                input1.vloai = 2;

                this.listbox.ds_phuongxadhsxkd.list = this.GetData(await CorrectLocationAPI.MPCCBS_LAYDS_PHUONGCCBS_V2(this.axios, input1)).filter(x => x.phuong_id > 0).map(x => ({
                    id: x.phuong_id,
                    text: x.tenphuong
                }));
                this.listbox.ds_phuongxadhsxkd.value.PHUONG_ID = 0;
                 this.listbox.ds_phuongxadhsxkd.value.TEN_PHUONG =null
            } else {
                var input = {};
                input.vquan_id = this.listbox.ds_phuongxa.value.PHUONG_ID;
                input.vloai = 1;
                this.listbox.ds_phuongxadhsxkd.list = this.GetData(await CorrectLocationAPI.MPCCBS_LAYDS_PHUONGCCBS_V2(this.axios, input)).filter(x => x.phuong_id > 0).map(x => ({
                    id: x.phuong_id,
                    text: x.tenphuong
                }));
                if (this.listbox.ds_phuongxadhsxkd.list.length > 0) {
                    this.listbox.ds_phuongxadhsxkd.value.PHUONG_ID = this.listbox.ds_phuongxadhsxkd.list[0].id;
                    this.tenphuong = this.listbox.ds_phuongxadhsxkd.list[0].text;
                }
            }

            //Lấy dữ liệu lên grid
            var input = {};
            input.vquan_id = this.listbox.ds_quanhuyen.value.QUAN_ID;
            input.vphuong_id = this.listbox.ds_phuongxa.value.PHUONG_ID;
            this.listbox.ds_data.list = this.GetData(await CorrectLocationAPI.MPCCBS_LAYDS_PHODHSX(this.axios, input));
            this.listbox.ds_data_fill.list = this.listbox.ds_data.list;
        },
        async laythongtin_ghep() {
            var input = {};
            input.vquan_id = this.listbox.ds_quanhuyen.value.QUAN_ID;
            input.vphuong_id = this.listbox.ds_phuongxa.value.PHUONG_ID;
            this.listbox.ds_data.list = this.GetData(await CorrectLocationAPI.MPCCBS_LAYDS_PHODHSX(this.axios, input));
        },
        btnLayDS() {
            this.phuongxachange();
        },
        quanhuyenchange() {
            this.getdmphuongxa();
        },
        phuongxaccbschange() {
            console.log(11111)
            var container = $("#optionccbs");
            container.show();
        },
        chkChuanHoaClicked: async function () {
            if (!this.isChuanHoaPhuong) {
                var input1 = {};
                input1.vparam = this.listbox.ds_quanhuyen.value.QUAN_ID; //this.listbox.ds_phuongxa.value.PHUONG_ID;
                input1.vloai = 2;
                this.listbox.ds_phuongxadhsxkd.list = this.GetData(await CorrectLocationAPI.MPCCBS_LAYDS_PHUONGDHSX(this.axios, input1));
                this.laythongtin_ghep()
            } else {
                var input = {};
                input.vparam = this.listbox.ds_phuongxa.value.PHUONG_ID;
                input.vloai = 1;
                this.listbox.ds_phuongxadhsxkd.list = this.GetData(await CorrectLocationAPI.MPCCBS_LAYDS_PHUONGDHSX(this.axios, input));
                this.laythongtin_ghep()
            }
        },
        btnSave: async function () {
            if (this.isChuanHoaPhuong) {
                if (this.listbox.ds_phuongxadhsxkd.value.PHUONG_ID == null || this.listbox.ds_phuongxadhsxkd.value.PHUONG_ID == "") {
                    this.ShowAlert("Bạn cần chọn phường CCBS", 2);
                    return
                }
                var input = {};
                input.vphuongdhsx_id = this.listbox.ds_phuongxa.value.PHUONG_ID;
                input.vphuongccbs_id = (this.listbox.ds_phuongxadhsxkd.value.PHUONG_ID == null ? "" : this.listbox.ds_phuongxadhsxkd.value.PHUONG_ID);
                input.vphodhsx_id = 0;
                input.vphoccbs_id = 0;
                input.vloai = 1;
                CorrectLocationAPI.MPCCBS_CHUANHOA(this.axios, input).then((response) => {
                    if (response.data.error_code && response.data.error_code === this.success_code) {
                        this.ShowAlert("Cập nhật phường thành công", 0);
                    } else {
                        this.ShowAlert("Cập nhật phường thất bại", 2);
                    }
                });
            }
            var log = "";
            for (var data of this.listbox.ds_data.list) {
                if(data.ISCHECK == "1"){
                  console.log(1)
                  var input = {};
                  input.vphuongdhsx_id = this.listbox.ds_phuongxa.value.PHUONG_ID;
                  input.vphuongccbs_id = (this.listbox.ds_phuongxadhsxkd.value.PHUONG_ID == null ? "" : this.listbox.ds_phuongxadhsxkd.value.PHUONG_ID);
                  input.vphodhsx_id = (data.ID_PHODHSX == null ? "" : data.ID_PHODHSX);
                  input.vphoccbs_id = (data.ID_PHOCCBS == null ? "" : data.ID_PHOCCBS);
                  input.vloai = 2;
                  CorrectLocationAPI.MPCCBS_CHUANHOA(this.axios, input).then((response) => {
                      if (response.data.error_code && response.data.error_code === this.success_code) {
                          this.listbox.dsthuebao.list = response.data.data;
                      } else {
                          log = log + data.ID_PHODHSX + ",";
                      }
                  }).finally(() => {
                      this.log = this.log + (data.ID_PHODHSX == null ? "" : data.ID_PHODHSX) + ",";
                      this.loading(false);
                  });
                }
            }

            if (log.length > 0) {
                this.ShowAlert("Có lỗi cập nhật!", 2);
            } else {
                //Lấy dữ liệu lên grid
                var input = {};
                input.vquan_id = this.listbox.ds_quanhuyen.value.QUAN_ID;
                input.vphuong_id = this.listbox.ds_phuongxa.value.PHUONG_ID;
                this.listbox.ds_data.list = this.GetData(await CorrectLocationAPI.MPCCBS_LAYDS_PHODHSX(this.axios, input));

                this.ShowAlert("Cập nhật phố thành công!", 0);
                this.$refs.gridDS_Pho.refresh();
            }
        }
    },
};
</script>
<style>
.btn-dropdown {
    margin-left:5px;
    width: 25px;
    height: 25px;
}
.info-row .mg-bt{
  margin-bottom:-200px !important
}
</style>
