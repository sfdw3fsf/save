export type FindPwdRequestOtpREQ =
  | {
      name: string;
      username: string;
      email: string;
    }
  | {
      name: string;
      username: string;
      phone: string;
    };

export type FindPwdConfirmOtpREQ =
  | {
      otp: string;
      email: string;
    }
  | {
      otp: string;
      phone: string;
    };
