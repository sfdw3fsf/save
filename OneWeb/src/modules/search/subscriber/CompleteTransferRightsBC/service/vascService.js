import DateTimeLib from "../../../../../utils/DateTimeLib";
let root = {}
export default  {
   setupRoot:function(_root){
    root = _root
    },
    GetPackageEPG:async function(ma_tb,ngay_epg){
        try {
            let rs = await root.context.post(
              "/tichhop/vasc/getPackageEPG",
              {
                endtime: DateTimeLib.toDateDisplay(new Date()),
                iptvaccount:ma_tb,
                starttime:ngay_epg,//
                strAll:"0"
              }
            );
            return rs.data.getPackageEPGResult;
          } catch (error) {
            return null;
          } finally {
          }
    },
    ChangePromotions:async function(IPTVAccount, sPromo_id){
      try {
        let rs = await root.context.post(
          "/tichhop/vasc/changePromotions",
          {
            iptvaccount:IPTVAccount,
            spromo_ID:sPromo_id
          }
        );
        return rs;
      } catch (error) {
        return null;
      } finally {
      }
    },
    ChangeSubscriber:async function(Name, Birthday, Sex, FirstAdd, SecondAdd, IDNo, TelNo,
      CellNo, AreaCode, Email, YM, Website, FaxNo, IPTVAccount){
      try {
        let rs = await root.context.post(
          "/tichhop/vasc/changeSubscriber",
          {
            areaCode: AreaCode,
            birthday: Birthday,
            cellNo: CellNo,
            email: Email,
            faxNo: FaxNo,
            firstAdd: FirstAdd,
            idno: IDNo,
            iptvaccount: IPTVAccount,
            name: Name,
            secondAdd: SecondAdd,
            sex: Sex,
            telNo: TelNo,
            website: Website,
            ym: YM

          }
        );
        console.log("ChangeSubscriber:", rs)
        return rs.data
        //return rs.data.changePromotionsResult;
        // return 0;
      } catch (error) {
        console.log(error)
        return null;
      } finally {
      }
    },
    ChangePackage: async function( tb_mytv,
        vmatocdo,
        ServiceCode,
        ServiceVASCode,
        lydo){
          try {
            let rs = await root.context.post(
              "/tichhop/vasc/changePackage",
              {
                iptvaccount: tb_mytv,
                packageNo: vmatocdo,
                reason: lydo,
                serviceCD: ServiceCode,
                serviceVAS:ServiceVASCode
              }
            );
            return rs.data.changePackageResult;
          } catch (error) {
            return null;
          } finally {
          }
        },
    ChangeMegaVNN: async function(IPTVAccount, megaMyTV, vCombo_FiberCD){
        try {
          let rs = await root.context.post(
            "/tichhop/vasc/changeMegaVNN",
            {
              combo_FiberCD: vCombo_FiberCD,
              iptvaccount: IPTVAccount,
              megaMyTV: megaMyTV
            }
          );
            return rs.data.changeMegaVNNResult;
          } catch (error) {
            return null;
          } finally {
          }
   },
   STB_Rent_UpdateInfo: async function(request){
    try {
        //Gọi api ngoài
        let rs = await root.context.post(
          "/tichhop/vasc/sTB_Rent_UpdateInfo",{
           stbDTO: {
            rent_STATUS: request.RENT_STATUS,
            username: request.USERNAME
          }
        }
        );
        return rs.data.sTB_Rent_UpdateInfoResult;
      } catch (error) {
        return null;
      } finally {
      }
   },
   MethodChangePay:async function(tb_mytv, vhinhthuc, vthang){
    try {
        //Gọi api ngoài
        let rs = await root.context.post(
          "/tichhop/vasc/methodChangePay",{
            iptvaccount:tb_mytv,
            method: vhinhthuc,
            no_Month: vthang
          }
        );
        return rs.data.methodChangePayResult;
      } catch (error) {
        return null;
      } finally {
      }
   },
   SuspendSubscriberV2: async function(IPTVAccount, ReasonID){
    try {
        let rs = await root.context.post(
          "/tichhop/vasc/suspendSubscriberV2",{
            iptvaccount:IPTVAccount,
            reasonID: ReasonID
          }
        );
        return rs.data.suspendSubscriberV2Result;
      } catch (error) {
        return null;
      } finally {
      }
   },
   ResumeSubscriber: async function(IPTVAccount, Reason){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/resumeSubscriber",{
          iptvaccount:IPTVAccount,
          reason: Reason
        }
      );
      return rs.data.resumeSubscriberResult;
    } catch (error) {
      return null;
    } finally {
    }
   },
   GetSubscriberInfoV2: async function(objRequest){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/getSubscriberInfoV2",{
          objRequest: objRequest
        }
      );
      return rs.data.getSubscriberInfoV2Result;
    } catch (error) {
      return null;
    } finally {
    }
   },
   ChangePackageV3: async function(objRequest){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/changePackageV3",{
          objRequest: objRequest
        }
      );
      return rs.data.changePackageV3Result;
    } catch (error) {
      return null;
    } finally {
    }
   },
   TotalMyTVPlus :async function(objRequest){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/totalMyTVPlus",{
          objRequest: objRequest
        }
      );
      return rs.data.totalMyTVPlusResult;
    } catch (error) {
      return null;
    } finally {
    }
    },
   PayTVFull_Register:async function(MYTVAccount, month){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/payTVFull_Register",{
          month: month,
          myTVAccount:MYTVAccount
        }
      );
      return rs.data.payTVFull_RegisterResult;
    } catch (error) {
      return null;
    } finally {
    }
   },
   ComboFAF_Update:async function(MyTVAccount, MonthFree, Operator){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/comboFAF_Update",{
          monthFree: MonthFree,
          myTVAccount:MyTVAccount,
          operator:Operator
        }
      );
      return rs.data.comboFAF_UpdateResult;
    } catch (error) {
      return null;
    } finally {
    }
   },
   B2B_Room_RegistAddOn:async function(room){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/b2B_Room_RegistAddOn",{
          raddOnDTO:room
        }
      );
      return rs.data.b2B_Room_RegistAddOnResult;
    } catch (error) {
      return null;
    } finally {
    }
   },
   B2B_Room_CancelAddOn:async function(room){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/b2B_Room_CancelAddOn",{
          raddOnDTO:room
        }
      );
      return rs.data.b2B_Room_CancelAddOnResult;
    } catch (error) {
      return null;
    } finally {
    }
   },
   CancelSubscriberV2:async function( root ,account, lydohuy_id){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/cancelSubscriberV2",{
          iptvaccount:account,
          reasonID:lydohuy_id
        }
      );
      return rs.data.cancelSubscriberV2Result;
    } catch (error) {
      console.log('error',error)
      return null;
    } finally {
    }
   },
   getSubscriberInfo: async function(iptvaccount){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/getSubscriberInfo",{
          iptvaccount: iptvaccount
        }
      );
      return rs.data.getSubscriberInfoResult;
    } catch (error) {
      return null;
    } finally {
    }
   },
}
