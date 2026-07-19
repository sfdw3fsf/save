<template>
  <div>
    <DataGrid :allowPaging="true"
      :enablePagingServer="false"
      ref="grid" @selectedItemsChanged="grd_SelectionChanged"
      v-if="dataSource.length > 0" :showColumnCheckbox="false"
      :enabledSelectFirstRow="false"
      :dataSource="dataSource"
      v-bind:columns="inputEdit? gridColumns_Edit : gridColumns" />
  </div>
</template>

<script>
import moment from "moment"

export default {
    data() {
        return {
            dataSource : [],
            gridColumns : [
                {
                fieldName: "chon",
                textAlign:"center",
                width: "50px",
                headerTemplate: this.templateCheckboxAll(this),
                allowFiltering: false,
                template: this.templateCheckbox(this, "so_hoadon")
                },
                {
                fieldName: "ma_duan",
                headerText: "Mã dự án",
                allowFiltering: true,
                },
                {
                fieldName: "ten_duan",
                headerText: "Tên dự án",
                allowFiltering: true,
                },
                {
                fieldName: "ma_toanha",
                headerText: "Mã Tòa nhà",
                allowFiltering: true,
                },
                {
                fieldName: "ten_toanha",
                headerText: "Tên tòa nhà",
                allowFiltering: true,
                },
                {
                fieldName: "ten_chudtu",
                headerText: "Tên chủ đầu tư",
                allowFiltering: true,
                },
                {
                fieldName: "ttvt",
                headerText: "TTVT",
                allowFiltering: true,
                }
            ],
            gridColumns_Edit : [

                {
                fieldName: "chon",
                textAlign:"center",
                width: "50px",
                headerTemplate: this.templateCheckboxAll(this),
                allowFiltering: false,
                template: this.templateCheckbox(this, "so_hoadon")
                },
                {
                fieldName: "so_hoadon",
                headerText: "Số hóa đơn",
                allowFiltering: false,
                template: this.templateSo(this, "so_hoadon")
                },{
                fieldName: "ngay_hoadon",
                headerText: "Ngày hóa đơn",
                allowFiltering: false,
                template: this.templateNgayHoaDon(this, "ngay_hoadon")
                },
                {
                fieldName: "ma_duan",
                headerText: "Mã dự án",
                allowFiltering: true,
                },
                {
                fieldName: "ten_duan",
                headerText: "Tên dự án",
                allowFiltering: true,
                },
                {
                fieldName: "ma_toanha",
                headerText: "Mã Tòa nhà",
                allowFiltering: true,
                },
                {
                fieldName: "ten_toanha",
                headerText: "Tên tòa nhà",
                allowFiltering: true,
                },
                {
                fieldName: "ten_chudtu",
                headerText: "Tên chủ đầu tư",
                allowFiltering: true,
                },
                {
                fieldName: "ttvt",
                headerText: "TTVT",
                allowFiltering: true,
                }
            ]
        }
    },
    props: ['listOfParamNameValues', "inputEdit"],
    watch: {
        "listOfParamNameValues" : {
            async handler(value) {
                console.log(value)
                try {
                    const P_CHUDTU_ID    = value.find(i => i.name == 'p_chudtu_id').defaultValue
                    const P_DONVI_CHA_ID = value.find(i => i.name == 'p_donvi_cha_id').defaultValue
                    let data = await this.axios.post("web-tracuu/tracuu-thuebao/sp_lay_tt_toanha_baocao", {
                        ds_para : JSON.stringify({
                            P_CHUDTU_ID,
                            P_DONVI_CHA_ID
                        })
                    })
                    let datasource = data.data.error_code == "BSS-00000000" ? data.data.data : []
                    this.dataSource = datasource.map(item => ({...item, "so_hoadon" : "", "ngay_hoadon" : ""}))
                    // console.log(this.datasource, "fuck that shit")
                } catch(err) {
                    this.dataSource = []
                }
            },
            deep: true
        }
    },
    methods: {
        templateCheckboxAll(parent) {
            return function () {
                return {
                    template: {
                        template: `<div>
                            <input class="check" type="checkbox" :value="data.chon" @change="ChangeInput" />
                        </div>`,
                        data() {
                            return {
                                data: {
                                },
                            };
                        },
                        methods: {
                            ChangeInput(value) {
                                console.log(event.target)
                                if(event.target.checked)
                                    parent.dataSource = parent.dataSource.map(i => ({...i, chon : 1}))
                                else
                                    parent.dataSource = parent.dataSource.map(i => ({...i, chon : 0}))

                                parent.grd_SelectionChanged()
                            }
                        },
                    }
                }
            }
        },
        templateCheckbox(parent, key) {
            return function () {
                return {
                    template: {
                        template: `<div>
                            <input class="check"  type="checkbox" :checked="data.chon == 1 ? true : false" @change="ChangeInput" />
                        </div>`,
                        data() {
                            return {
                                data: {
                                    chon: 0
                                },
                            };
                        },
                        watch: {
                            "data.chon": function(value) {
                                console.log(value)
                            }
                        },
                        methods: {
                            ChangeInput(value) {
                                console.log(value)
                               let index = parent.dataSource.findIndex(i => i.toanha_id == this.data.toanha_id)
                               parent.dataSource[index].chon = parent.dataSource[index].chon == 1 ? 0 : 1
                               parent.grd_SelectionChanged()
                            }
                        },
                    }
                }
            }
        },
        templateNgayHoaDon(parent, key) {
            return function () {
                return {
                    template: {
                        template: `<div>
                            <ejs-datepicker :value="data.ngay_hoadon" :format="'dd/MM/y'" @change="ChangeInput" :placeholder="'Chọn ngày hóa đơn'" />
                        </div>`,
                        data() {
                            return {
                                data: {},
                            };
                        },
                        methods: {
                            ChangeInput(v) {
                              if(v.value) {
                               let index = parent.dataSource.findIndex(i => i.toanha_id == this.data.toanha_id)
                               parent.dataSource[index].ngay_hoadon = moment(v.value).format("DD/MM/YYYY")
                               parent.grd_SelectionChanged()
                              }
                            }
                        },
                    }
                }
            }
        },
        templateSo(parent, key) {
            return function () {
                return {
                    template: {
                        template: `<div>
                            <input :value="data.so_hoadon" class="form-control" @change="ChangeInput" />
                        </div>`,
                        data() {
                            return {
                                data: {},
                            };
                        },
                        methods: {
                            ChangeInput(event) {
                               let index = parent.dataSource.findIndex(i => i.toanha_id == this.data.toanha_id)
                               parent.dataSource[index].so_hoadon = event.target.value
                               parent.grd_SelectionChanged()
                            }
                        },
                    }
                }
            }
        },
        grd_SelectionChanged() {
            var selected = this.dataSource.filter(i => i.chon == 1)
            selected = selected.map(i => ({toanha_id: i.toanha_id, so_hoadon: i.so_hoadon ? i.so_hoadon : null, ngay_hoadon: i.ngay_hoadon ? i.ngay_hoadon : null}))
            console.log(selected)
            this.$emit("change", selected.map(i => ({value : i.toanha_id})))
            this.$emit("gridData", selected)
        }
    }
}
</script>

<style>
</style>
