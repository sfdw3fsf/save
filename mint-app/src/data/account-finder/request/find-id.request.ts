export type FindIdRequestOtpREQ =
  | {
      name: string;
      email: string;
    }
  | {
      name: string;
      phone: string;
    };

export type FindIdConfirmOtpREQ =
  | {
      otp: string;
      email: string;
    }
  | {
      otp: string;
      phone: string;
    };
