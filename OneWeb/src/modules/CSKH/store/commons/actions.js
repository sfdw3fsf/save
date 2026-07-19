import DanhSachThueBaoKhongKhaoSatApi from "../../api/DanhSachThueBaoKhongKhaoSatApi";
import MoPhieuKhaoSatApi from "../../api/MoPhieuKhaoSatApi";
import DanhMucApi from "../../api/DanhMucApi";
export const actions = {
  async getLoaiDSThueBaoKhongKhaoSat({ commit, state }, data) {
    const data1 = [
      { value: "6", text: "Thuê bao không khảo sát Lắp mới" },
      { value: "7", text: "Thuê bao không khảo sát Báo hỏng" }
    ];
    commit("setLoaiDSThueBaoKhongKhaoSat", data1);
  },
  async getDanhMucVT({ commit, state }) {
    let response = await DanhSachThueBaoKhongKhaoSatApi.getDichVuVT(
      this._vm.axios
    );
    let ds = [];
    let dm = [];
    let dmDocFileDSKhoaMo = [1, 2, 4, 7, 8, 9, 10, 11, 12, 14, 15, 16];
    let page = {};
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined) {
        ds = response.data.data;
      } else {
        this._vm.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this._vm.$root.toastError(respone.data.message);
    }
    ds.forEach(element => {
      if (dmDocFileDSKhoaMo.includes(element.DICHVUVT_ID)) {
        dm.push({ value: element.DICHVUVT_ID, text: element.TEN_DVVT });
      }
    });
    commit("setLoaiDanhMucVT", dm);
  },
  async getCayDonViData({ commit, state }, data) {
    let ds = [];
    MoPhieuKhaoSatApi.getDsDonVi(this._vm.axios, data)
      .then(response => {
        let page = {};
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data !== undefined) {
            ds = response.data.data;
          } else {
            this._vm.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
          }
        } else {
          this._vm.$root.toastError(respone.data.message);
        }

        // // ds = this.removeDuplicates(ds, "DONVI_ID")

        // ds = ds.filter(x => x.UNITLEVEL < 2)

        // var newArray = [];
        // var lookupObject = {};

        // for (var i in ds) {
        //   lookupObject[ds[i]['DONVI_ID']] = ds[i];
        // }

        // for (i in lookupObject) {
        //   newArray.push(lookupObject[i]);
        // }
        // ds = newArray

        let parentIds = ds.map(x => x.DONVI_CHA_ID);

        ds.forEach(element => {
          if (parentIds.includes(element.DONVI_ID)) {
            element.hasChild = true;
            element.expanded = true;
          }
          if (element.UNITLEVEL >= 2) {
            element.expanded = false
          }
        });

        // let dsDVKoDonViCha = ds.filter(x => !x.DONVI_CHA_ID);
        // if (dsDVKoDonViCha.length > 0) {
        //   ds.push({
        //     UNITLEVEL: 1,
        //     TEN_DV: "VNPT",
        //     DONVI_ID: 0,
        //     DONVI_CHA_ID: null,
        //     hasChild: true,
        //     expanded: true,
        //     selected: true
        //   });
        //   ds.filter(x => !x.DONVI_CHA_ID && x.DONVI_ID != 0).forEach(x => x.DONVI_CHA_ID = 0)
        // }
        commit("setCayDonVi", ds);
      })
      .catch(reject => {
        // console.log(reject);
        // if (
        //   reject.response &&
        //   reject.response.data &&
        //   reject.response.data.message
        // )
        //   this.$root.toastError(reject.response.data.message);
        // else this.$root.toastError("Có lỗi xảy ra!");
        commit("setCayDonVi", []);
      });
  },
  async getCayDonViData_ver2({ commit, state }, data) {
    let ds = [];
    MoPhieuKhaoSatApi.getDsDonVi(this._vm.axios, data)
      .then(response => {
        let page = {};
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data !== undefined) {
            ds = response.data.data;
          } else {
            this._vm.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
          }
        } else {
          this._vm.$root.toastError(respone.data.message);
        }

        let parentIds = ds.map(x => x.DONVI_CHA_ID);

        ds.forEach(element => {
          if (parentIds.includes(element.DONVI_ID)) {
            element.hasChild = true;
            element.expanded = true;
          }
          if (element.UNITLEVEL >= 2) {
            element.expanded = false
          }
        });
        let result = ds.reverse();
        commit("setCayDonVi", result);
      })
      .catch(reject => {
        commit("setCayDonVi", []);
      });
  },
  async getDsDichVu({ commit, state }, data) {
    let ds = [];
    let response = await MoPhieuKhaoSatApi.getDsDichVu(this._vm.axios);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined) {
        response.data.data.forEach(element => {
          ds.push({
            text: element.TEN_DVVT,
            value: element.DICHVUVT_ID
          });
        });
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit("setDsDichVu", ds);
  },
  async getThamSoMacDinh({ commit, state }, data) {
    try {
      let response = await DanhMucApi.getThamSoMacDinh(this._vm.axios, data);
      if (response.data.error_code === "BSS-00000000") {
        let rs = response.data.data || []
        if (rs.length == 0) return false
        return rs[0].TEN_TS === '1'
      } else {
        return false
      }
    }
    catch (e) {
      return false
    }
  },
  async getListThamSoMacDinh({ commit, state }, data) {
    try {
      let response = await DanhMucApi.getThamSoMacDinh(this._vm.axios, data);
      if (response.data.error_code === "BSS-00000000") {
        return response.data.data || []
      } else {
        return []
      }
    }
    catch (e) {
      return []
    }
  }
};
