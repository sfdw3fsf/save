<template src="./index.html"></template>
<script>
import { Sort, Filter } from "@syncfusion/ej2-vue-grids";
import { L10n } from "@syncfusion/ej2-base";
L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
      Item: "Bản ghi",
      Items: "Bản ghi",
      FilterbarTitle: "",
    },
  },
});
export default {
  name: "IP_LAN_WAN",
  props:{
    id:String,
    obj:{
      bras_id:Number,
      hdtb_id:Number,
      thuebao_id:Number,
      ma_tb:Number,
      tenbras:Number,
      loaitb_id:Number,
      dichvuvt_id:Number,
      ip_lan_wan:Number,
      trangthaidl_id:Number,
    },
  },
  provide: {
    grid: [Sort, Filter],
  },
  data:function (){
    return{
      bras_id:null,
      hdtb_id:null,
      thuebao_id:null,
      ma_tb:null,
      tenbras:null,
      loaitb_id:null,
      dichvuvt_id:null,
      ip_lan_wan:null,
      titleGrid:'Danh sách ip cấp cho thuê bao - bras',

      dsGridPrefix:[],
      dsPrefix:[],
      dsBlock:[],
      rowTemplate: function (){
        return {
          template:{
            template: `

              <tr v-if="data.index == 0" class="e-row" v-click-outside="onClickOutside">
              <td v-on:click="active = true" v-show="!active" class="e-rowcell" colspan="4"
                  style="text-align: center; color: darkgrey">
                <div>Nhấn vào đây để thêm mới</div>
              </td>
              <td class="e-rowcell" v-show="active">
                <!--                  <ejs-dropdownlist :allowFiltering="false" :dataSource="this.parent.dsPrefix" class="form-control" @change="changePrefix"-->
                <!--                    :fields="{ text: 'prefix_loai', value: 'prefix' }" placeholder="&#45;&#45;Chọn prefix&#45;&#45;" v-model="data.prefix"></ejs-dropdownlist>-->
                <SelectExt
                  v-model.trim="data.prefix"
                  :settings="{dropdownParent: $refs['cboPrefix']}"
                  :dataSource="dsPrefix"
                  @change="changePrefix"
                  placeholder="--Chọn prefix--"
                  dataTextField="prefix_loai"
                  dataValueField="prefix"
                />
              </td>
              <td class="e-rowcell" v-show="active">
                <SelectExt
                  v-model.trim="data.block"
                  :settings="{dropdownParent: $refs['cboBlock']}"
                  :dataSource="this.dsBlock"
                  placeholder="--Chọn block--"
                  dataTextField="block"
                  dataValueField="block"
                />
                <!--                  <ejs-dropdownlist :allowFiltering="false" :dataSource="this.dsBlock" class="form-control"-->
                <!--                    :fields="{ text: 'block', value: 'block' }" placeholder="&#45;&#45;Chọn block&#45;&#45;" v-model="data.block"></ejs-dropdownlist>-->
              </td>
              <td class="e-rowcell" v-show="active">
                <input type="text" class="form-control" @keydown.enter="eventEnter(data)" v-model="data.ip"/>
              </td>
              <td class="e-rowcell" v-show="active">
                <button class="nc-icon-glyph ui-1_circle-remove red" @click="removePrefix(data)"></button>
              </td>
              </tr>
              <tr class="e-row" v-else>
              <td class="e-rowcell" ref="cboPrefix">
                <!--                <ejs-dropdownlist :allowFiltering="false" :dataSource="this.parent.dsPrefix" class="form-control" @change="changePrefix"-->
                <!--                  :fields="{ text: 'prefix_loai', value: 'prefix' }" placeholder="&#45;&#45;Chọn prefix&#45;&#45;" v-model="data.prefix"></ejs-dropdownlist>-->
                <SelectExt
                  v-model.trim="data.prefix"
                  :settings="{dropdownParent: $refs['cboPrefix']}"
                  :dataSource="dsPrefix"
                  @change="changePrefix"
                  placeholder="--Chọn prefix--"
                  dataTextField="prefix_loai"
                  dataValueField="prefix"
                />
              </td>
              <td class="e-rowcell" ref="cboBlock">
                <SelectExt
                  v-model.trim="data.block"
                  :settings="{dropdownParent: $refs['cboBlock']}"
                  :dataSource="this.dsBlock"
                  placeholder="--Chọn block--"
                  dataTextField="block"
                  dataValueField="block"
                />
                <!--                <ejs-dropdownlist :allowFiltering="false" :dataSource="dsBlock" class="form-control"-->
                <!--                  :fields="{ text: 'block', value: 'block' }" placeholder="&#45;&#45;Chọn block&#45;&#45;" v-model="data.block"></ejs-dropdownlist>-->
              </td>
              <td class="e-rowcell">
                <input type="text" class="form-control" @keydown.enter="eventEnter(data)" v-model="data.ip"/>
              </td>
              <td class="e-rowcell">
                <button class="nc-icon-glyph ui-1_circle-remove red" @click="removePrefix(data)"></button>
              </td>
              </tr>
            `,
            data() {
              return {
                data: { },
                active: false,
                dsBlock: [],
                dsPrefix: []
              }
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent.$parent
              },
            },
            mounted() {
              this.dsBlock = [...this.parent.dsBlock]
              this.dsPrefix = [...this.parent.dsPrefix]
            },
            methods:{
              onClickOutside (args) {
                this.active = false
              },
              eventEnter(data){
                this.parent.updateDataSource(this.data);
              },
              changePrefix(prefix){
                console.log(prefix)
                // this.data.prefix_loai = args.itemData.prefix_loai
                let pr = this.dsPrefix.find(x => x.prefix == prefix)
                this.data.prefix_loai = pr.prefix_loai
                if (pr.prefix_loai.includes('WAN IP')) this.dsBlock = [
                  {
                    block:4,
                  },
                ]
                else this.dsBlock = [
                  {
                    block:8,
                  },
                  {
                    block:16,
                  },
                  {
                    block:32,
                  },
                ]
              },
              removePrefix(data){
                this.parent.deleteDataSource(data);
              }
            }
          }
        }
      }
    }
  },
  methods:{
    async onShownModal() {
      try {
        this.bras_id = this.obj.bras_id
        this.hdtb_id = this.obj.hdtb_id
        this.thuebao_id = this.obj.thuebao_id
        this.ma_tb = this.obj.ma_tb
        this.tenbras = this.obj.tenbras
        this.loaitb_id = this.obj.loaitb_id
        this.dichvuvt_id = this.obj.dichvuvt_id
        this.ip_lan_wan = this.obj.ip_lan_wan
        this.loading(true)
        this.titleGrid = "Danh sách IP cấp cho: " + this.ma_tb + " - bras: " + this.tenbras;
        this.dsPrefix = await this.lay_ds_prefix_bras(this.bras_id, this.loaitb_id)
        this.dsBlock = [
          {
            block:4,
          },
          {
            block:8,
          },
          {
            block:16,
          },
          {
            block:32,
          },
        ]
        await this.HT_DS_PREFIX()
      } catch (e) {
        if (e.response && e.response.data.message_detail)
          this.$root.toastError("Lỗi khi load dữ liệu: "+ e.response.data.message_detail)
      } finally {
        this.loading(false)
      }
    },
    onHiddenModal(){

    },
    UpperCaseData (ds) {
      let _ds = []
      for (let item of ds) {
        let row = {}
        for (let key in item) {
          let k = key.toUpperCase()
          row[k] = item[key]
        }
        _ds.push(row)
      }
      return _ds
    },
    async btnChapNhan_Click() {
      try {
        this.loading(true)
        let ip_list = [...this.dsGridPrefix]
        ip_list.splice(0, 1) // loại bỏ row template insert
        let kq = await this.cn_wan_lan_ip_v2(this.hdtb_id, this.obj.trangthaidl_id, this.UpperCaseData(ip_list))
        if (!(kq+'').startsWith('ok')){
          this.$root.toastError(kq)
          return
        }
        let arrKQ = kq.split('-')
        this.ip_lan_wan = arrKQ[1]
        this.$emit("reload")
        this.$root.toastSuccess("Cập nhật IP WAN LAN thành công")
        this.$bvModal.hide(this.id)
      }catch (e) {
        console.log(e)
        if (e.response && e.response.data.message){
          if (e.response.data.message.includes('không đúng định dạng'))
            this.$root.toastError(e.response.data.message.split('-')[1])
          else this.$root.toastError(e.response.data.message)
        } else this.$root.toastError(e.response.data.message_detail)

      }finally {
        this.loading(false)
      }
    },
    async HT_DS_PREFIX(){
      try {
        this.dsGridPrefix = await this.lay_ds_prefix_hd_v2(this.hdtb_id, this.dichvuvt_id, this.obj.trangthaidl_id)
        // add row template to insert target
        this.dsGridPrefix.unshift({
          block:null,
          ip:null,
          prefix:null,
          prefix_loai:null,
        })
      }catch (e) {
        if (e.response && e.response.data.message_detail)
          this.$root.toastError("Lỗi khi load dữ liệu: "+ e.response.data.message_detail)
      }
    },
    async lay_ds_prefix_bras(bras_id, loaitb_id){
      let rs = await this.$root.context.get("web-quantri/ip-wan-lan/lay-ds-prefix-bras",{
        bras_id:bras_id,
        loaitb_id:loaitb_id
      })
      return rs.data
    },
    async lay_ds_prefix_hd_v2(hdtb_id, dichvuvt_id, trangthaidl_id){
      let rs = await this.$root.context.get("web-quantri/ip-wan-lan/lay-ds-prefix-hd-v2",{
        hdtb_id:hdtb_id,
        dichvuvt_id:dichvuvt_id,
        loai:trangthaidl_id
      })
      return rs.data
    },
    async lay_prefix_block_v2(prefix, block, hdtb_id, trangthaidl_id, list_prefix_block){
      let rs = await this.$root.context.post("web-quantri/ip-wan-lan/lay-prefix-block-v2",{
        PREFIX:prefix,
        BLOCK:block,
        HDTB_ID:hdtb_id,
        TRANGTHAI_DL:trangthaidl_id,
        LIST_PREFIX_BLOCK:list_prefix_block
      })
      return rs.data
    },
    async cn_wan_lan_ip_v2(hdtb_id, trangthaidl_id, ip_list){
      let rs = await this.$root.context.post("web-quantri/ip-wan-lan/cn-wan-lan-ip-v2",{
        HDTB_ID:hdtb_id,
        TRANGTHAI_DL:trangthaidl_id,
        IP_LIST:ip_list
      })
      return rs.data
    },
    async updateDataSource(dataItem) {
      try {
        this.loading(true)
        if (!dataItem.index) {
          // vlist_prefix_block: ds còn lại
          let vlist_prefix_block = [...this.dsGridPrefix]
          vlist_prefix_block.splice(0, 1); // loại bỏ row template insert
          let ip = await this.lay_prefix_block_v2(dataItem.prefix, dataItem.block, this.hdtb_id, this.obj.trangthaidl_id, vlist_prefix_block)
          // insert row
          this.dsGridPrefix.push({
            block: dataItem.block,
            ip: ip,
            prefix: dataItem.prefix,
            prefix_loai: dataItem.prefix_loai,
          })

        } else {
          // update row
          let idx = this.dsGridPrefix.findIndex((i, index) => index == dataItem.index)
          if (idx > -1) {
            this.updateData(this.dsGridPrefix[idx], dataItem)
            // vlist_prefix_block: ds còn lại ngoại trừ row đang update
            let vlist_prefix_block = [...this.dsGridPrefix]
            vlist_prefix_block.splice(idx, 1); // remove row update
            vlist_prefix_block.splice(0, 1); // remove row template insert
            let ip = await this.lay_prefix_block_v2(dataItem.prefix, dataItem.block, this.hdtb_id, this.obj.trangthaidl_id, vlist_prefix_block)
            this.dsGridPrefix[idx].ip = ip
          }
        }
        this.$refs.gridPrefix.refresh()
      }catch (e) {
        console.log(e)
      }finally {
        this.loading(false)
      }
    },
    updateData: function (item, newItem) {
      if (item != null) {
        let keys = Object.keys(item);
        for (let i = 0; i < keys.length; i++) {
          const key = keys[i];
          if (item[key] != newItem[key]) item[key] = newItem[key];
        }
      }
    },
    deleteDataSource(dataItem){
      if (dataItem.index)
        this.dsGridPrefix = this.dsGridPrefix.filter((item,index) => index != dataItem.index)
      this.$refs.gridPrefix.refresh()
    }
  }
}
</script>

<style scoped>

</style>
