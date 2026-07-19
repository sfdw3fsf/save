<template src="./GanDinhDangChoBanKe.html"></template>
<style src="./GanDinhDangChoBanKe.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import { mapActions, mapState } from 'vuex'
import API from '../api/GanDinhDangChoBanKe'
export default {
    components: { breadcrumb },
    computed: {
        ...mapState("gandinhdangchobanke", [
            "dsVersion",
            "dsInv",
            "dsGroup",
            "dsFormat",
            "dsDataLayout"
        ]),
        getFields: function() {
            return {
                dataSource: this.dataTree,
                id: 'key',
                value: 'id',
                text: 'label',
                child: "nodes"
            }
        }
    },
    async mounted() {
        await this.fetchData()
    },
    data() {
        return {
            header: {
                title: "GÁN ĐỊNH DẠNG CỦA BẢN KÊ",
                list: [
                    { name: "Hoá đơn", link: { name: "Ui.cards" } },
                    {
                        name: "Gán định dạng của bản kê", 
                        link: { name: "Ui.cards" }
                    }
                ]
            },
            fields: [
                {
                    fieldName: "",
                    headerText: "Tên Format",
                    allowFiltering: false,
                },
                {
                    fieldName: "VER_ID",
                    headerText: "Ver_ID",
                    allowFiltering: false,
                    cellCssClass: 'text-right'
                },
                {
                    fieldName: "INV_ID",
                    headerText: "INV_ID",
                    allowFiltering: false,
                    cellCssClass: 'text-right'
                },
                {
                    fieldName: "GROUP_ID",
                    headerText: "Group_ID",
                    allowFiltering: false,
                    cellCssClass: 'text-right'
                },
                {
                    fieldName: "NOTE",
                    headerText: "Ghi chú",
                    allowFiltering: false,
                },
                {
                    fieldName: "BINDING_VALUE",
                    headerText: "Giá thị trường group",
                    allowFiltering: false,
                },
            ],
            fieldTree: {},
            dataSelect: {
                verId: 0,
                invId: 0,
                groupId: 0
            },
            dataDSFormat: {
                formatId: null,
                bindingValue: 0,
                note: null
            },
            dataUpdate: {
                verId: 0,
                invId: 0,
                groupId: 0,
                // có group_id
                dsFormat: [
                    // {
                    //     formatId: null,
                    //     bindingValue: 0,
                    //     note: null
                    // }
                ],
                // không có group_id
                dsFormatId: []
            },
            dataDelete: {
                verId: 0,
                invId: 0,
                groupId: 0,
                dsFormat: [],
                dsFormatId: []
            },
            dataTree: [],
            isShowModalCapNhap: false,
            isShowModalXoa: false,
            index: 0,
        }
    },
    methods: {
        ...mapActions("gandinhdangchobanke", [
            "getdsVersion",
            "getdsInv",
            "getdsGroup",
            "getdsFormat",
            "getdsDataLayout",
        ]),
        async fetchData() {
            this.$root.loading(true)
            await this.getdsVersion()
            await this.getdsInv(this.dataSelect.verId)
            // await this.getdsGroup(this.dataSelect)
            await this.getdsFormat()
            await this.getdsDataLayout(this.dataSelect)
            console.log("format: ", this.dsFormat)
            console.log("inv: ",this.dsInv)
            console.log("group: ", this.dsGroup)
            // this.dsInv.forEach(async (element) => {
            //     this.dataSelect.invId = element.INV_ID
            //     await this.getdsGroup(this.dataSelect)
            //     let hasChild = false
            //     if (this.dsGroup.length > 0) {
            //         hasChild = true
            //     } else {
            //         hasChild = false
            //     }
            //     this.dataTree.push({
            //         ID: element.INV_ID,
            //         NAME: element.INV_ID + " " + element.INV_NAME,
            //         PID: null,
            //         hasChildren: hasChild
            //     })
            //     this.dsGroup.forEach(element => {
            //         this.dataTree.push({
            //             ID: element.GROUP_ID,
            //             NAME: element.GROUP_NAME,
            //             PID: this.dataSelect.invId,
            //             hasChildren: false
            //         })
            //     })
            // })
            this.getRootLst()
            console.log(this.dataTree)
            this.$root.loading(false)
        },
        async getRootLst() {
            this.dataTree = []
            this.dataTree = await Promise.all (
                this.dsInv.map(async (item) => ({
                    id: item.INV_ID,
                    label: item.INV_ID + " " + item.INV_NAME,
                    key: `p-${item.INV_ID}`,
                    ...item,
                    nodes:  await this.getRootGroupLst(item.INV_ID),
                }))
            )
        },
        async getRootGroupLst(invId) {
            this.dataSelect.invId = invId
            await this.getdsGroup(this.dataSelect)
            const tmpData = this.dsGroup.map(item=> ({
                id: item.GROUP_ID,
                label: item.GROUP_ID + " " + item.GROUP_NAME,
                key: `c-${invId}-${item.GROUP_ID}`,
                parentId: item.OWNER_ID == 0 ? `p-${invId}` : `c-${invId}-${item.OWNER_ID}`,
                nodes: [],
                expanded: false
            }))
            return tmpData
        },
        async getDataTree() {
            this.dataUpdate.verId = this.dataSelect.verId
            await this.getdsInv(this.dataSelect.verId)
            this.getRootLst()
            console.log("inv: ", this.dsInv)
        },
        async nodeClicked(args) {
            let id = args.node.dataset.uid
            if (id.startsWith('p')) {
                this.dataSelect.invId = id.replace("p-", "")
                this.dataSelect.groupId = null
            } else if (id.startsWith('c')) {
                let tmp = id.replace('c-', '')
                let index = tmp.search('-')
                console.log(tmp)
                this.dataSelect.invId = tmp.slice(0, index)
                this.dataSelect.groupId = tmp.slice(index).replace('-', '')
            }
            // console.log(this.dataSelect.invId)
            // console.log(this.dataSelect.groupId)
            console.log(this.dataSelect)
            this.dataUpdate.verId = this.dataSelect.verId
            this.dataUpdate.invId = this.dataSelect.invId
            this.dataUpdate.groupId = this.dataSelect.groupId
            this.dataDelete.verId = this.dataSelect.verId
            this.dataDelete.invId = this.dataSelect.invId
            this.dataDelete.groupId = this.dataSelect.groupId
            await this.getdsDataLayout(this.dataSelect)
            this.huy()
            this.index = 0
        },
        selectItem (item, index) {
            this.index = index;         
        },
        save () {
            if (this.dataDSFormat.formatId === null) {
                this.$toast.error("Trường Tên Format bắt buộc chọn!")
                return
            }
            this.isShowModalCapNhap = true
        },
        ghiLai() {
            if (this.dataUpdate.groupId === null) {
                this.dataUpdate.dsFormatId = []
                this.dsDataLayout.forEach(element => {
                    this.dataUpdate.dsFormatId.push(element.FORMAT_ID)
                })
                console.log(parseInt(this.dataDSFormat.formatId))
                this.dataUpdate.dsFormatId.push(parseInt(this.dataDSFormat.formatId))
            } else {
                this.dataUpdate.dsFormat = []
                this.dsDataLayout.forEach(element => {
                    this.dataUpdate.dsFormat.push({
                        formatId: element.FORMAT_ID,
                        bindingValue: element.BINDING_VALUE,
                        note: element.NOTE    
                    })
                })
                this.dataUpdate.dsFormat.push({
                    formatId: this.dataDSFormat.formatId,
                    bindingValue: this.dataDSFormat.bindingValue,
                    note: this.dataDSFormat.note
                })
            }
            API.capNhapDinhDangBanKe(this.axios, this.dataUpdate).then(async (response) => {
                console.log(response.data)
                if (response.data.error_code === "BSS-00000000")
                {
                    await this.getdsDataLayout(this.dataSelect)
                    console.log(this.dsDataLayout)
                    this.huy()
                    this.$toast.success("Cập nhập định dạng bản kê thành công")
                } else {
                    this.$toast.error(response.data.message_detail)
                }    
            }).catch(error => {
                console.log(error)
                this.$toast.error(error.data.message_detail)
            })
            console.log("data update: ", this.dataUpdate)
        },
        del() {
            if (this.dsDataLayout.length == 0) {
                this.$toast.error("Không có định dạng của bản kê để xoá!")
                return
            }
            this.isShowModalXoa = true
        },
        xoa() {
            if (this.dataUpdate.groupId === null) {
                this.dataDelete.dsFormatId = []
                this.dsDataLayout.forEach(element => {
                    this.dataDelete.dsFormatId.push(element.FORMAT_ID)
                })
                this.dataDelete.dsFormatId.splice(this.index, 1)
            } else {
                this.dataDelete.dsFormat = []
                this.dsDataLayout.forEach(element => {
                    this.dataDelete.dsFormat.push({
                        formatId: element.FORMAT_ID,
                        bindingValue: element.BINDING_VALUE,
                        note: element.NOTE    
                    })
                })
                this.dataDelete.dsFormat.splice(this.index, 1)
            }
            // this.dataDelete.dsFormat.splice(this.index, 1)
            console.log(this.dataDelete)
            API.capNhapDinhDangBanKe(this.axios, this.dataDelete).then(async (response) => {
                if (response.data.error_code === "BSS-00000000")
                {
                    await this.getdsDataLayout(this.dataSelect)
                    this.huy()
                    this.$toast.success("Xoá định dạng bản kê thành công")
                    if (this.index === 0) {
                        this.index = 0
                    } else {
                        this.index--
                    }
                } else {
                    this.$toast.error("Đã có lỗi xảy ra")
                }    
            }).catch(error => {
                this.$toast.error("Đã có lỗi xảy ra")
            })    
        },
        huy() {
            this.dataDSFormat.formatId = null
            this.dataDSFormat.bindingValue = 0
            this.dataDSFormat.note = null
        },
    },
}
</script>
