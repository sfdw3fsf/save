let root = {}
let login = {}
export default {
    setupRoot:function(_root){
    root = _root
    },
    setLogin: function(_login){
      Object.assign(login,_login)
    },
    ChangeBillingInfo:async function(account, billingInfo, note){
      try {
        let rs = await root.context.post(
          "/tichhop/visa-app/changeBillingInfo",
          {
            accountModel: {accountName:account},
            billingInfo: billingInfo,
            login:login,
            note: note,
          }
        );
        return rs.data;
      } catch (error) {
        return false;
      } finally {
      }
    },
    Kiemtra_doituong_vdc: async function (hdtb_id, loaihinh_id) {
        try {
          let rs = await root.context.get(
            "/web-ccdv/dongbo-visa-vasc/kiemtra_doituong_vdc",
            {
              hdtb_id: hdtb_id,
              loaihinh_id: loaihinh_id,
            }
          );
          return rs.data;
        } catch (error) {
          return false;
        } finally {
        }
      },
      // Hàm này chưa có trên tích hợp
    ChangeCustomerType: async function (account, activeDate, cusType) {
      try {
        return await root.context.post(
          "/tichhop/visa-app/changeCustomerType",
          {
            customerModel: {
              accountName: account,
              customerType: cusType
            },
            // login: login,
            note: "Thay doi loai khach hang qua WS ngay : " + activeDate,
          }
        );
      } catch (error) {
      } finally {
      }
    },
      ThaydoiGoiCuoc: async function(account, tocdo_id, ip, tucthi, autoIP, giuIP){
        try
        {

            //Thay doi tuc thi
            if (tucthi == 0)
            {
                modifyServiceTypeMegaVNN(account, tocdo_id, ip, autoIP, giuIP)
            }
            //Sang thang sau moi ap dung
            if (tucthi == 1)
            {
                modifyServiceTypeMegaVNN1(account, tocdo_id, ip, autoIP, giuIP)
            }
        }
        catch (ex)
        {
            if (ex.Message.includes("Không có sự thay đổi")) //Đã tồn tại ngày rồi
                return true;
            else
            {
                return false;
            }
        }
        return true;
      },
      modifyServiceTypeMegaVNN(account, tocdo_id, ip, autoIP, giuIP){

      },
      updateStatusVNN: async function(myaccount, lydo, ghichu){
        try {
          let rs = await root.context.post(
            "/tichhop/visa-app/updateStatusVNN",
            {
              accountModel: {accountName:myaccount},
              login:login,
              note: ghichu,
              reason:lydo
            }
          );
          return rs.data;
        } catch (error) {
          return false;
        } finally {
        }
      },
      HuyDatChoMegaVNN: async function(accountmoi){
       return true
      },
      ModifyAccountNameVNN: async function(accountcu, accountmoi){
        return true
      },
      ModifyMailRASName: async function(vmail_cu, accountmoi){
          return  true
      },
      RemoveVNN: async function(account,  lydohuy,  chuthich){
        try {
          let rs = await root.context.post(
            "/tichhop/visa-app/removeVNN",
            {
              accountModel: {accountName:account},
              login:login,
              note: chuthich,
              reason:lydohuy
            }
          );
          return rs.data;
        } catch (error) {
          return false;
        } finally {
        }
    },
}
