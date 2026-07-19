export default {
    getAreaCode(context) {
        return context.$root.context.user.getProperty('tentat_vasc')
    },
    async ChangePromotions(context, IPTVAccount, sPromo_id) { // Thay doi nhom khach hang uu tien (y te) VASC

        let data = {
            iptvaccount: IPTVAccount,
            spromo_ID: sPromo_id
        }

        let response = await context.$root.context.post(`/tichhop/vasc/changePromotions`, data)

        if (response.errorCode == 0 && response.data.result == 0) {
            return {
                code: 0
            }
        }

        return {
            code: 1,
            message: response.data.errordesc
        }
    },
    async UpdateUserParent(context, ChildUser, ParentUser) {

        let data = {
            objRequest: {
                childUser: ChildUser,
                parentUser: ParentUser
            }
        }

        let response = await context.$root.context.post(`/tichhop/vasc/updateUserParent`, data)

        if (response.errorCode == 0 && response.data.result == 0) {
            return {
                code: 0
            }
        }

        return {
            code: 1,
            message: "Có lỗi trong quá trình đồng bộ thuê bao " + ChildUser + " lên hệ thống VASC \n Chi tiết lỗi: " + response.data.errordesc
        }
    },
    async Cust_UserMulti_UpdateInfo(context, username_first, username_multi, multi_flag, method_flg) {

        let data = {
            custDTO: {
                method_FLG: method_flg,
                multi_FLG: multi_flag,
                username_FIRST: username_first,
                username_MULTI: username_multi
            }
        }

        let response = await context.$root.context.post(`/tichhop/vasc/cust_UserMulti_UpdateInfo`, data)

        if (response.errorCode == 0 && response.data.result == 0) {
            return {
                code: 1
            }
        }

        return {
            code: 0,
            message: "Nguyên nhân gây lỗi: " + response.data.errordesc
        }
    },
    async STB_PROM_Active(context, username, stb_serial, stb_type, stb_date, note, isfirst, program) {

        let data = {
            stbDTO: {
                is_FIRST: isfirst,
                note: note,
                program: program,
                stb_DATE: stb_date,
                stb_SERIAL: stb_serial.toUpperCase(),
                stb_TYPE: stb_type,
                username: username
            }
        }

        let response = await context.$root.context.post(`/tichhop/vasc/sTB_PROM_Active`, data)

        if (response.errorCode == 0 && response.data.result == 0) {
            return {
                code: 1
            }
        }

        return {
            code: 0,
            message: "Nguyên nhân gây lỗi: " + response.data.errordesc
        }
    },
    async STB_PROM_Register(context, username, stb_serial, fiber_mytv, stb_type, program, note) {

        let data = {
            stbDTO: {
                fiber_MYTV: fiber_mytv == "0" ? "1" : "0",
                note: note,
                program: program,
                stb_SERIAL: stb_serial,
                stb_TYPE: stb_type,
                username: username
            }
        }

        let response = await context.$root.context.post(`/tichhop/vasc/sTB_PROM_Register`, data)

        if (response.errorCode == 0 && response.data.result == 0) {
            return {
                code: 1
            }
        }

        return {
            code: 0,
            message: "Nguyên nhân gây lỗi: " + response.data.errordesc
        }
    },
    async BlockPayTV(context, Operator, MytvID)
    {
        let data = {
            objRequest: {
                fafilmFLG: Operator == 1 ? "1" : "0",
                hboflg: Operator == 1 ? "1" : "0",
                kplusFLG: Operator == 1 ? "1" : "0",
                myTVID: MytvID,
                operator: Operator,
                reason: "",
                vtvcabFlg: Operator == 1 ? "1" : "0"
            }
        }

        let response = await context.$root.context.post(`/tichhop/vasc/blockPayTV`, data)

      if (response.errorCode != 0)
        {
            throw "Có lỗi trong quá trình đồng bộ thuê bao " + MytvID + " lên hệ thống VASC \n Chi tiết lỗi: " + response.data.errordesc;
        }
    },
    async UpdateKplusInfo(context, IPTVAccount, strOperator) {

        let data = {
            myTVAccount: IPTVAccount,
            strOperator: strOperator
        }

        let response = await context.$root.context.post(`/tichhop/vasc/updateKplusInfo`, data)

      if (response.errorCode != 0)
        {
            throw "Có lỗi trong quá trình đồng bộ thuê bao " + IPTVAccount + " lên hệ thống VASC \n Chi tiết lỗi: " + response.data.errordesc
        }
    },
    async changeSubscriber(context, Name, Birthday, Sex, FirstAdd, SecondAdd, IDNo, TelNo, CellNo, Email, YM, Website, FaxNo, IPTVAccount) {

        let data = {
            areaCode: this.getAreaCode(context),
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

        let response = await context.$root.context.post(`/tichhop/vasc/changeSubscriber`, data)

        if (response.errorCode != 0) {
            throw "Có lỗi trong quá trình đồng bộ thuê bao " + IPTVAccount + " lên hệ thống VASC \n Chi tiết lỗi: " + response.data.errordesc
        }
    },
    async B2B_EditHotel(context, CODE, TEN_KS, TEN_CHU_KS, CMT, TAXCODE, TYPE, ADDRESS, TEL, CONTRACT_DATE, CONTRACT_CODE, BILLDATE, REG_TYPE, PAYMENT_TYPE, ROOM_NO, DIEMGIAODICH_VASC) {

        let data = {
            hotelDTO: {
                address: ADDRESS,
                areacd: DIEMGIAODICH_VASC,
                billdate: BILLDATE,
                code: CODE,
                contract_CODE: CONTRACT_CODE,
                contract_DATE: CONTRACT_DATE,
                hotelier: TEN_CHU_KS,
                idcard: CMT,
                name: TEN_KS,
                payment_TYPE: PAYMENT_TYPE,
                reg_TYPE: REG_TYPE,
                room_NO: ROOM_NO,
                taxcode: TAXCODE,
                tel: TEL,
                type: TYPE,
                zipcd: this.getAreaCode(context)
            }
        }

        let response = await context.$root.context.post(`/tichhop/vasc/b2B_EditHotel`, data)

      if (response.errorCode != 0) {
            throw "[B2B-EH] "+ response.data.errordesc
        }
    }
}
