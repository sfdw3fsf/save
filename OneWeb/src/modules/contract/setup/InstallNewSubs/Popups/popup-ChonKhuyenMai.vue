<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title">
        <span class="icon one-notepad"></span> Chọn {{ loai==0?"khuyến mại":"trả trước" }}
      </div>
      <!-- <div class="close -ap icon-close" data-dismiss="modal"></div> -->
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="HienThiKhuyenMai"> <span class="icon one-search"></span>Tìm kiếm </a>
        </li>
        <li>
          <a href="#" @click.prevent="closeForm">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp
            nhận
          </a>
        </li>
        <li>
            <a href="#" @click.prevent="terminateForm">
                <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
            </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">Thông tin tìm kiếm</div>
        <div class="info-row">
          <div class="key w140">Tên {{ loai==0?"khuyến mại":"trả trước" }}</div>
          <div class="value">
            <input type="text" class="form-control" v-model="filterbox.tenkm" @change="HienThiKhuyenMai" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w140">
            <div class="check-action">
              <input type="checkbox" class="check" v-model="filterbox.congvan.isenabled" />
              <span class="name">Số công văn</span>
            </div>
          </div>
          <div class="value" :class="{disabled:!filterbox.congvan.isenabled}">
            <div class="select-custom">
              <select2 v-model="filterbox.congvan.value" class="select-custom" :options="filterbox.congvan.list.map(ls=>({id:ls.congvan_id,text:ls.ten_cv}))" @change="HienThiKhuyenMai"></select2>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w140">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="filterbox.sothang.isenabled" />
                  <span class="name">Thời gian cam kết</span>
                </div>
              </div>
              <div class="value" :class="{disabled:!filterbox.sothang.isenabled}">
                <div class="input-number-custom">
                  <input type="number" class="form-control" v-model="filterbox.sothang.value" @change="HienThiKhuyenMai" />
                </div>
              </div>
              <div class="value w30 padt7">(Tháng)</div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w140">
                <div class="check-action" :class="{disabled:!filterbox.khuyenmai.list || filterbox.khuyenmai.list.length<=0}">
                  <input type="checkbox" class="check" v-model="nhomcongvan" />
                  <span class="name">Nhóm theo số công văn</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Danh sách {{ loai==0?"khuyến mại":"trả trước" }}</div>
        <div class="table-content">
            <DataGrid2 class="table-result table-gachle table-filter"
                v-bind:columns="khuyenmai_cols"
                v-bind:dataSource="filterbox.khuyenmai.list" :showFilter="true" :showColumnCheckbox="false"
                dataKeyField="khuyenmai_id" :defaultColumnCheckboxChecked="false"  @rowClicked="khuyenmaiChanged">
            </DataGrid2>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
</style>
<script>
import {
  Loai_KM,
} from "../Enums";
export default {
    name: 'popup-ChonKhuyenMai',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            dt: [],
            loai: 0,
            _dsKm: [],
            filterbox: {
                congvan: {
                    list: [],
                    value: 0,
                    isenabled: false,
                },
                sothang: {
                    value: 0,
                    isenabled: false,
                },
                tenkm: "",
                khuyenmai: {
                    list: [],
                    value: {},
                }
            },
            nhomcongvan:false,
        }
    },
    async mounted() {
        if(this.dulieu.loai) this.loai = this.dulieu.loai;
        if(this.dulieu.dt) this.dt = this.dulieu.dt;
        try {
            this._dsKm = this.dt.map(x=>x.khuyenmai_id);
            var rs = await this.$root.context.post(
                "/web-hopdong/lapdatmoi/sp_lay_cong_van_theo_danh_sach_km", { "ds_km_id":this._dsKm.join(',') }
            );
            this.filterbox.congvan.list = rs.data;
            if(this.filterbox.congvan.list && this.filterbox.congvan.list.length>0) {
              this.filterbox.congvan.value = this.filterbox.congvan.list[0].congvan_id;
              this.HienThiKhuyenMai();
            }
        } catch(ex) { this.$toast.error(''+ex); }
    },
    computed: {
        khuyenmai_cols: function() {
            if(this.nhomcongvan)
                return [
                    {fieldName: 'ten_km', headerText: 'Tên khuyến mại', allowFiltering: true, allowSorting: true},
                    {fieldName: 'congvan_id', headerText: 'Số công văn', allowFiltering: true, allowSorting: true,isGroupedColumn:this.nhomcongvan, },
                    ];
            else return [
                    {fieldName: 'ten_km', headerText: 'Tên khuyến mại', allowFiltering: true, allowSorting: true},
                    ]
        }
    },
    methods: {
        closeForm() {
            this.$emit('form-close',{ loaikm: this.loai, khuyenmai: this.filterbox.khuyenmai.value });
        },
        terminateForm() {
            this.$emit('form-close',null);
        },
        // rowChanged(item) {
        //     this.filterbox.khuyenmai.value = item;
        // },
        async HienThiKhuyenMai() {
            try {
                var so_cv = -1;
                if(this.filterbox.congvan.isenabled && this.filterbox.congvan.value>0) so_cv = this.filterbox.congvan.value;
                var so_thang = -1;
                if(this.filterbox.sothang.isenabled && this.filterbox.sothang.value>0) so_thang = this.filterbox.sothang.value;
                var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/sp_lay_km_theo_danh_sach',{
                    "ds": this._dsKm.join(','),
                    "so_cv": so_cv,
                    "sothang": so_thang,
                    "ten": this.filterbox.tenkm });
                this.filterbox.khuyenmai.list = rs.data;
                if(this.filterbox.khuyenmai.list && this.filterbox.khuyenmai.list.length>0) this.filterbox.khuyenmai.value = this.filterbox.khuyenmai.list[0];
            } catch(ex) { this.$toast.error(''+ex); }
        },
        khuyenmaiChanged(idx,item) {
            this.filterbox.khuyenmai.value = item;
        },
    }
}
</script>
