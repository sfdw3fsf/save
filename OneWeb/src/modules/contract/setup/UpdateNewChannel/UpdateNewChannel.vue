<template>
  <div class="main-wrapper">
    <breadcrumb :header="header" />

    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="loadDsKenhTrang">
            <span class="icon one-refresh"></span>Lấy DS
          </a>
        </li>
        <li>
          <a href="#" @click.prevent="onClickSearch">
            <span class="icon one-search"></span>Tìm kiếm
          </a>
          <SearchAccountModal
            ref="searchAccountModal"
            @form-close="searchAccountModalClose"
          ></SearchAccountModal>
        </li>
        <li>
          <a href="#" @click.prevent="onClickNhapThueBao">
            <span class="icon one-card-plus"></span>Nhập thuê bao
          </a>
        </li>
        <b-modal
          id="popup-ThongTinKenhTrang"
          hide-footer
          hide-header
          hide-header-close
          size="xl"
          body-class="modal-body p-0"
          :no-close-on-backdrop="true"
        >
          <ThongTinKenhTrangPopup
            :dichvuvt_id="dichvuvt_id"
            :ma_vnpt="ma_vnpt"
            :ten_tb="ten_tb"
            :hdtb_id_hni_prop="hdtb_id_hni"
            :diachi_ld="diachi_ld"
            :diachi_tb="diachi_tb"
            :sodt_lh="sodt_lh"
            @accept="acceptThongTinKenhTrang"
            :loaitb_id="loaitb_id">
          </ThongTinKenhTrangPopup>
        </b-modal>
        <li>
          <a href="#" @click.prevent="onClickDongBo">
            <span class="icon one-sync"></span>Đồng bộ
          </a>
        </li>
        <li>
          <a href="#" @click.prevent="onClickTraPhieu">
            <span class="icon one-list-arrow-right"></span>Trả phiếu
          </a>
        </li>
      </ul>
    </div>

    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">
          <span class="mr-3">Danh sách thuê bao cần đồng bộ với tỉnh</span>
          <b-form-radio-group
            class="d-inline-block"
            v-model="radioValue"
            :options="radioOptions"
            @change="radioChange"
          ></b-form-radio-group>
        </div>
        <DataGrid
          ref="grid"
          v-bind:columns="gridHeaders"
          v-bind:dataSource="gridData"
          :showColumnCheckbox="false"
          :enable-paging-server="false"
          :allowPaging="true"
          :showFilter="true"
          @rowClicked="dataGridRecordClick"
          @selectedRowChanged="selectedRowChanged"
          :enabledSelectFirstRow="true"
        ></DataGrid>
      </div>
    </div>
    <!-- Modal -->
    <ThoaiTraHDBCModal ref="thoaiTraHDBCModal" @success="successTraHDBC" 
      :vtinhtc="paramTTHDBC.vtinh_tc" 
      :vtinhban="paramTTHDBC.vtinhban"
      :vhdtbid="paramTTHDBC.vhdtbid"
    />
  </div>
</template>

<script>
import breadcrumb from "@/components/breadcrumb";
import api from "./api";
import SearchAccountModal from "./popups/SearchAccountModal";
import ThongTinKenhTrangPopup from "./popups/ThongTinKenhTrangPopup";
import * as _ from "lodash";
import {DichVuVienThong, LoaiHinhTB} from "../../../../const/enums";
import ThoaiTraHDBCModal from './popups/ThoaiTraHDBCModal.vue'

export default {
  name: "UpdateNewChannel",
  components: {
    breadcrumb,
    SearchAccountModal,
    ThongTinKenhTrangPopup,
    ThoaiTraHDBCModal
  },

  mounted() {
    this.loadDsKenhTrang();
  },

  data() {

    return {
      dichvuvt_id : 0,
      ma_vnpt:"",
      ten_tb:"",
      hdtb_id_hni_prop:0,
      hdtb_id_hni:0,
      diachi_ld:"",
      diachi_tb:"",
      sodt_lh:"",
      loaitb_id: 0,
      header: {
        title: "Đồng bộ thuê bao kênh trắng",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Đồng bộ thuê bao kênh trắng",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      radioValue: 1,
      radioOptions: [
        { text: "PTTB", value: 1 },
        { text: "Báo hỏng", value: 2 },
      ],

      gridData: [],
      gridHeaders: [
        {
          fieldName: "ten_loaihd",
          headerText: "Loại hợp đồng",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "ma_tb_ban",
          headerText: "Số máy/Acc tỉnh bán",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "ma_tb",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "ten_tb",
          headerText: "Tên TB",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "ten_dvvt",
          headerText: "Dịch vụ VT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "loaihinh_tb",
          headerText: "Loại hình TB",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "diachi_ld",
          headerText: "Địa chỉ lắp đặt",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: 'ghichu',
          headerText: 'Ghi chú KHDN',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'ghichutinh',
          headerText: 'Ghi chú tỉnh',
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "ma_vnpt",
          headerText: "Mã VNPT",
          allowFiltering: true,
          allowSorting: false,
        },
      ],
      gridSelectedIndex: 0,
      searchAccountModalRet: null,
      paramTTHDBC:{
        vhdtbid:0,
        vtinhban:0,
        vtinhtc:0
      }
    };
  },

  methods: {
    radioChange() {
      this.loadDsKenhTrang();
    },

    async loadDsKenhTrang() {
      this.gridData = [];
      const kieu = this.radioValue;

      try {
        this.loading(true);
        const res = await api.lay_ds_kenh_trang(this.axios, {
          kieu, // 2: báo hỏng, 1: pttb
        });

        if (this.radioValue === kieu) {
          this.gridData = res.data.data;
          this.$refs.grid.selectRow(0);
          this.gridSelectedIndex = 0;
        }
      } catch (e) {
        console.error(e);
      } finally {
        this.loading(false);
      }
    },

    onClickSearch() {
      this.$refs.searchAccountModal.showModal();
    },

    searchAccountModalClose(data) {
      console.log("UpdateNewChannel searchAccountModalClose", data);
      this.searchAccountModalRet = data;
      const selectedIndex = this.gridSelectedIndex;
      console.log('searchAccountModalClose selectedIndex',selectedIndex)

      if (!_.isNil(selectedIndex) && selectedIndex >= 0) {
        
        // const item = this.gridData[selectedIndex];
        // console.log('searchAccountModalClose', item)
        if (this.dichvuvt_id.toString() !== data.thuebao.dichvuvt_id.toString()) {
          this.$bvModal.msgBoxOk("Hãy kiểm tra lại chính xác dịch vụ");
          return;
        }


        // if (item.ma_tb !== data.ma_tb) {
        //   item.ma_tb = data.ma_tb;
        //   item.ma_tb_changed = true;
        // }
        // this.gridData = JSON.parse(JSON.stringify(this.gridData));
        // setTimeout(() => this.$refs.grid.selectRow(selectedIndex), 150);
        this.$refs.grid.flagSelectedRowIndexes=[selectedIndex]
        let rowSelected=this.$refs.grid.getSelectedRecords();
        console.log('searchAccountModalClose rowSelected',rowSelected)
        var key='hdtb_id'
        if(this.radioValue == 2){
          key='baohong_id'
        }
        const index=this.gridData.findIndex(x=>x[key]==rowSelected[0][key]);
        if(index>-1){
          this.gridData[index].ma_tb=data.ma_tb
          this.gridData[index].ma_tb_changed=true
          this.gridData[index].thuebao_id=data.thuebao.thuebao_id

          this.gridData=[].concat(this.gridData)
        }
      }
    },
    acceptThongTinKenhTrang(data){
      console.log('acceptThongTinKenhTrang', data)
      //hdtb_id
      this.$refs.grid.flagSelectedRowIndexes=[this.gridSelectedIndex]
      let rowSelected=this.$refs.grid.getSelectedRecords();
      var key='hdtb_id'
      if(this.radioValue == 2){
        key='baohong_id'
      }
      const index=this.gridData.findIndex(x=>x[key]==rowSelected[0][key]);
      if(index>-1){
        this.gridData[index].ma_tb=data.ma_tb
        this.gridData[index].thuebao_id=data.thuebao_id

        this.gridData=[].concat(this.gridData)
      }
      this.$bvModal.hide("popup-ThongTinKenhTrang");

    },
    dataGridRecordClick(index, item) {
      console.log("UpdateNewChannel dataGridRecordClick", index, item);
      this.gridSelectedIndex = index;
    },
    selectedRowChanged(item){
       console.log("UpdateNewChannel selectedRowChanged", item);
      this.dichvuvt_id = item.dichvuvt_id;
      this.ma_vnpt=item.ma_vnpt;
      this.ten_tb=item.ten_tb;
      this.hdtb_id_hni_prop=0;
      this.hdtb_id_hni=item.hdtb_id?item.hdtb_id:0
      this.diachi_ld=item.diachi_ld;
      this.diachi_tb=item.diachi_tb;
      this.sodt_lh=item.sodt_lh;
      this.loaitb_id = item.loaitb_id;
    },
    async onClickNhapThueBao() {
      const modalRet = await this.$bvModal.msgBoxConfirm(
        "Chỉ thêm thuê bao khi chắc chắn không có thuê bao tại tỉnh?",
        {
          okTitle: "Đồng ý",
          cancelTitle: "Không đồng ý",
        }
      );

      if (modalRet) {
        this.$bvModal.show("popup-ThongTinKenhTrang");
      }
    },

    async onClickDongBo() {
      let rowSelected=this.$refs.grid.getSelectedRecords();

      var key='hdtb_id'
      if(this.radioValue == 2){
        key='baohong_id'
      }
      const index=this.gridData.findIndex(x=>x[key]==rowSelected[0][key]);

      if (_.isNil(index)) {
        await this.$bvModal.msgBoxOk("Vui lòng chọn thuê bao!");
        return;
      }


      const item = this.gridData[index];
      if (!item.ma_tb) {
        await this.$bvModal.msgBoxOk(
          "Chưa có thuê bao tương ứng tại tỉnh thi công để đồng bộ"
        );
        return;
      }

      try {
        this.loading(true);
        if (this.radioValue === 1) { // pttb
          await Promise.all([
            api.capnhat_kenhtrang_thicong(this.axios, {
              vhdtb_id: item.hdtb_id,
              vthuebao_id_ban: item.thuebao_id_ban,
              vthuebao_id_tc: item.thuebao_id,
              vma_tb: item.ma_tb,
              vma_tb_ban: item.ma_tb_ban,
              vtinh_ban: item.tinh_ban,
              vtinh_tc: item.tinh_thicong
            }),
            api.capnhat_kenhtrang_ban(this.axios, {
              tinhthicong_id: item.tinh_thicong,
              vhdtb_id: item.hdtb_id,
              vthuebao_id_ban: item.thuebao_id_ban,
              vthuebao_id_tc: item.thuebao_id,
              vma_tb: item.ma_tb,
              vtinh_ban: item.tinh_ban,
              vtinh_tc: item.tinh_thicong
            })
          ]);
        } else if (this.radioValue === 2) { // báo hỏng
          await Promise.all([
            api.bancheo_capnhat_kenhtrang_thicong_bh(this.axios, {
              vbaohong_id: item.baohong_id_thicong,
              vthuebao_id_ban: item.thuebao_id_ban,
              vthuebao_id_tc: item.thuebao_id,
              vma_tb: item.ma_tb,
              vma_tb_ban: item.ma_tb_ban,
              vtinh_ban: item.tinh_ban,
              vtinh_tc: item.tinh_thicong
            }),
            api.bancheo_capnhat_kenhtrang_ban_bh(this.axios, {
              tinhthicong_id: item.tinh_thicong,
              vbaohong_id: item.baohong_id,
              vthuebao_id_ban: item.thuebao_id_ban,
              vthuebao_id_tc: item.thuebao_id,
              vma_tb: item.ma_tb,
              vtinh_ban: item.tinh_ban,
              vtinh_tc: item.tinh_thicong
            })
          ]);
        }
        this.$toast.success("Đồng bộ kênh trắng thành công!");
        this.gridSelectedIndex=0
        this.loadDsKenhTrang();
      } catch (e) {
        console.error(e);
        this.$toast.error("Có lỗi xảy ra khi đồng bộ");
        this.loading(false);
      }
    },

    async onClickTraPhieu() {
      if(this.radioValue==2){
        this.$toast.error('Không được trả phiếu phần báo hỏng!')
        return
      }

      let rowSelected=this.$refs.grid.getSelectedRecords();
      console.log('onClickTraPhieu', rowSelected)
      if(rowSelected.length==0){
        this.$toast.error('Vui lòng chọn thuê bao!')
        return
      }

      const modalRet = await this.$bvModal.msgBoxConfirm(
        "Bạn có muốn huỷ phụ lục KHDN không?",
        {
          okTitle: "Đồng ý",
          cancelTitle: "Không đồng ý",
        }
      );
      if (!modalRet) return;

      this.paramTTHDBC.vtinh_tc=rowSelected[0].tinh_thicong
      this.paramTTHDBC.vtinhban=rowSelected[0].tinh_ban
      this.paramTTHDBC.vhdtbid=rowSelected[0].hdtb_id

      setTimeout(()=>{
        this.$refs.thoaiTraHDBCModal.showModal()
      }, 500)
      

      
    },
    async successTraHDBC(data){
      console.log('successTraHDBC kdsfkdfsk', data.hdtb_id)
      try {
        this.loading(true);
        await api.capnhat_thoaitra_kenhtrang(this.axios, {
          hdtb_id: data.hdtb_id,
          may_cn: 0,
          metaClass: null,
          nguoi_cn: this.$root.token.getNguoiDungID(),
          nhanvien_id: this.$root.token.getNguoiDungID(),
        });
        this.$toast.success("Hủy phụ lục KHDN thành công");
        this.loadDsKenhTrang();
      } catch (e) {
        this.$toast.error("Hủy phụ lục KHDN lỗi");
        this.loading(false);
      }
    }
  },
};
</script>
