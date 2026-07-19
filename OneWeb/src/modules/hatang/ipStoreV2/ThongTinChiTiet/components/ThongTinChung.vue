<template>
  <div class="thong-tin-chung">
    <div class="row">
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">ID</div>
          <div class="value">
            <input
              type="text"
              class="form-control"
              :placeholder="currentItem.id > 0 ? '' : 'Tự sinh'"
              :value="currentItem.id > 0 ? currentItem.id : ''"
              disabled
            />
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Hiệu lực</div>
          <div class="value padt4">
            <div class="check-action">
              <input type="checkbox" class="check" v-model="currentItem.hieuLuc" :true-value="1" :false-value="0" />
              <span class="name"></span>
            </div>
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Quảng bá IP</div>
          <div class="value padt4">
            <div class="check-action">
              <input type="checkbox" class="check" v-model="currentItem.quangBaIP" :true-value="1" :false-value="0" />
              <span class="name"></span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">IP <span class="red">*</span></div>
          <div class="value">
            <input type="text" class="form-control" v-model="currentItem.ip" :class="{ 'is-invalid': ipInvalid }" />
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Trạng thái CCDV <span class="red">*</span></div>
          <div class="value">
            <SelectExt
              v-model="currentItem.trangThaiCCDV"
              dataTextField="text"
              dataValueField="value"
              :dataSource="trangThaiCCDVOptions"
            />
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">IDC <span class="red">*</span></div>
          <div class="value">
            <SelectExt dataTextField="TEN" dataValueField="ID" :dataSource="IDCList" v-model="currentItem.idc">
              <option value="0">Chọn IDC</option>
            </SelectExt>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Loại IP <span class="red">*</span></div>
          <div class="value">
            <SelectExt dataTextField="TEN" dataValueField="ID" :dataSource="loaiIPList" v-model="currentItem.loaiIP">
              <option value="0">Chọn loại IP</option>
            </SelectExt>
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Phân loại IP <span class="red">*</span></div>
          <div class="value">
            <SelectExt dataTextField="TEN" dataValueField="ID" :dataSource="phanLoaiIPList" v-model="currentItem.phanLoaiIP">
              <option value="0">Chọn phân loại IP</option>
            </SelectExt>
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Hạ tầng mạng</div>
          <div class="value">
            <input type="text" class="form-control" v-model="currentItem.haTangMang" disabled />
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Thuộc VRF <span class="red">*</span></div>
          <div class="value">
            <SelectExt dataTextField="TEN" dataValueField="ID" :dataSource="VRFList" v-model="currentItem.vrf">
              <option value="0">Chọn VRF</option>
            </SelectExt>
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Zone <span class="red">*</span></div>
          <div class="value">
            <SelectExt dataTextField="TEN" dataValueField="ID" :dataSource="ZoneList" v-model="currentItem.zone">
              <option value="0">Chọn Zone</option>
            </SelectExt>
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">VLAN <span class="red">*</span></div>
          <div class="value">
            <SelectExt dataTextField="TEN" dataValueField="ID" :dataSource="VlanList" v-model="currentItem.vlan">
              <option value="0">Chọn VLAN</option>
            </SelectExt>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Subnet <span class="red">*</span></div>
          <div class="value">
            <SelectExt dataTextField="TEN" dataValueField="ID" :dataSource="SubnetList" v-model="currentItem.subnet">
              <option value="0">Chọn subnet</option>
            </SelectExt>
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Thuộc thiết bị</div>
          <div class="value">
            <input type="text" class="form-control" v-model="currentItem.thietBi" />
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Mục đích sử dụng</div>
          <div class="value">
            <SelectExt dataTextField="TEN" dataValueField="ID" :dataSource="QuyHoachSubnetList" v-model="currentItem.mucDichSuDung">
              <option value="0">Chọn mục đích sử dụng</option>
            </SelectExt>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Đối tượng CCDV <span class="red">*</span></div>
          <div class="value">
            <SelectExt
              dataTextField="text"
              dataValueField="value"
              :dataSource="doiTuongCCDVOptions"
              v-model="currentItem.doiTuongCCDV"
            />
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Đơn vị quản lý <span class="red">*</span></div>
          <div class="value">
            <SelectExt
              dataTextField="text"
              dataValueField="value"
              :dataSource="donViQuanLyOptions"
              v-model="currentItem.donViQuanLy"
            />
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-12">
        <div class="info-row">
          <div class="key w160">Sở cứ tham chiếu</div>
          <div class="value">
            <input type="text" class="form-control" v-model="currentItem.soCuThamChieu" />
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-12">
        <div class="info-row">
          <div class="key w160">Ghi chú</div>
          <div class="value">
            <textarea class="form-control" rows="2" v-model="currentItem.ghiChu"></textarea>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ThongTinChungTab',
  props: {
    currentItem: {
      type: Object,
      required: true,
    },
    loaiIPList: {
      type: Array,
      default: () => [],
    },
    phanLoaiIPList: {
      type: Array,
      default: () => [],
    },
    IDCList: {
      type: Array,
      default: () => [],
    },
    VRFList: {
      type: Array,
      default: () => [],
    },
    ZoneList: {
      type: Array,
      default: () => [],
    },
    VlanList: {
      type: Array,
      default: () => [],
    },
    SubnetList: {
      type: Array,
      default: () => [],
    },
    QuyHoachSubnetList: {
      type: Array,
      default: () => [],
    },
    ipInvalid: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      trangThaiCCDVOptions: [
        { value: 'provided', text: 'Đã cung cấp' },
        { value: 'not_provided', text: 'Chưa cung cấp' },
        { value: 'reserved', text: 'Dành riêng' },
      ],
      doiTuongCCDVOptions: [
        { value: 'internal', text: 'Nội bộ / Khách hàng' },
        { value: 'cloud', text: 'Đơn vị Cloud' },
        { value: 'customer', text: 'Khách hàng khác' },
      ],
      donViQuanLyOptions: [
        { value: 'idc', text: 'Đơn vị IDC' },
        { value: 'cloud', text: 'Đơn vị Cloud' },
        { value: 'customer', text: 'Khách hàng' },
      ],
    };
  },
  computed: {},
};
</script>

